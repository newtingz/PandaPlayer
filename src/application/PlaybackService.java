package application;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

import java.io.File;
import java.util.List; // For playlist management
import java.util.Random; // For shuffle

// Assuming AudioParser class exists and has methods like fileLocationGet() and nameGet()
// import application.AudioParser;

/**
 * PlaybackService manages media playback functionalities for the application.
 * It encapsulates the JavaFX MediaPlayer and provides a clean API for controlling
 * playback, managing playlists, and handling playback states like repeat and shuffle.
 * <p>
 * This service is intended to replace direct MediaPlayer manipulation within SampleController.
 * SampleController would interact with this service to play songs, respond to UI controls,
 * and update the UI based on observable properties provided by this service.
 * <p>
 * Example (conceptual usage in SampleController):
 * <pre>{@code
 * private PlaybackService playbackService;
 * private SongDataManager songDataManager; // Assuming this exists
 *
 * public void initialize() {
 *     // ... other initializations ...
 *     playbackService = new PlaybackService();
 *     // songDataManager = new SongDataManager(); // Initialize SongDataManager
 *
 *     // Bind UI elements to PlaybackService properties
 *     playTimeLabel.textProperty().bind(playbackService.currentTimeFormattedProperty());
 *     timeSlider.valueProperty().bindBidirectional(playbackService.currentTimePercentageProperty());
 *     // ... etc. ...
 *
 *     // Set the playlist for the playback service
 *     // playbackService.setPlaylist(songDataManager.getSongList());
 * }
 *
 * @FXML
 * private void handlePlayPause() {
 *     playbackService.playPause();
 * }
 *
 * @FXML
 * private void handleNextSong() {
 *     playbackService.playNext();
 * }
 *
 * @FXML
 * private void handlePreviousSong() {
 *     playbackService.playPrevious();
 * }
 *
 * // When a song is selected in the UI:
 * // AudioParser selectedSong = ... get selected song ...
 * // playbackService.playSong(selectedSong);
 *
 * }</pre>
 *
 * Note: This is a new service class and is not yet integrated into SampleController.
 * Reconciliation with SampleController to use this service is a separate future step.
 */
public class PlaybackService {

    private MediaPlayer mediaPlayer;

    private final ObjectProperty<Status> status = new SimpleObjectProperty<>(Status.UNKNOWN);
    private final ReadOnlyObjectWrapper<Duration> currentTime = new ReadOnlyObjectWrapper<>(Duration.ZERO);
    private final ReadOnlyObjectWrapper<Duration> totalDuration = new ReadOnlyObjectWrapper<>(Duration.ZERO);
    private final StringProperty currentTimeFormatted = new SimpleStringProperty("00:00 / 00:00");
    private final DoubleProperty volume = new SimpleDoubleProperty(0.5); // Default volume 50%
    private final BooleanProperty isMuted = new SimpleBooleanProperty(false); // Not in original, but good addition
    private final ObjectProperty<AudioParser> currentSong = new SimpleObjectProperty<>(null);

    // Playlist and playback mode properties
    private ObservableList<AudioParser> playlist = FXCollections.observableArrayList();
    private int currentPlaylistIndex = -1;
    private final BooleanProperty shuffleActive = new SimpleBooleanProperty(false);
    private final ObjectProperty<RepeatMode> repeatMode = new SimpleObjectProperty<>(RepeatMode.NONE);
    private final Random random = new Random();
    private double storedVolumeBeforeMute = 0.5;


    public enum RepeatMode {
        NONE, // No repeat
        ONE,  // Repeat current song
        ALL   // Repeat entire playlist
    }

    /**
     * Initializes the PlaybackService.
     */
    public PlaybackService() {
        // Listener for volume changes to update MediaPlayer
        volume.addListener((obs, oldVal, newVal) -> {
            if (mediaPlayer != null && !isMuted.get()) {
                mediaPlayer.setVolume(newVal.doubleValue());
            }
        });

        isMuted.addListener((obs, oldVal, newVal) -> {
             if (mediaPlayer != null) {
                if (newVal) { // Muting
                    storedVolumeBeforeMute = mediaPlayer.getVolume();
                    mediaPlayer.setVolume(0);
                } else { // Unmuting
                    mediaPlayer.setVolume(storedVolumeBeforeMute);
                }
            }
        });

        // Listeners to update currentTimePercentage
        currentTime.addListener(obs -> updateCurrentTimePercentage());
        totalDuration.addListener(obs -> updateCurrentTimePercentage());
    }

    private void initializeMediaPlayer(String filePath) {
        disposeMediaPlayer(); // Dispose of any existing player

        try {
            Media media = new Media(new File(filePath).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            mediaPlayer.statusProperty().addListener((obs, oldStatus, newStatus) -> status.set(newStatus));
            status.set(mediaPlayer.getStatus()); // Initial status

            mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
                if (newTime != null) {
                    // Ensure updates to currentTime are done on FX thread if property is bound by UI not on FX thread
                    Platform.runLater(() -> currentTime.set(newTime));
                    updateFormattedTime();
                }
            });

            mediaPlayer.totalDurationProperty().addListener((obs, oldDur, newDur) -> {
                if (newDur != null) {
                     Platform.runLater(() -> totalDuration.set(newDur));
                    updateFormattedTime();
                }
            });

            mediaPlayer.setOnReady(() -> {
                Platform.runLater(() -> { // Ensure updates are on FX thread
                    totalDuration.set(mediaPlayer.getMedia().getDuration());
                    updateFormattedTime();
                    mediaPlayer.setVolume(isMuted.get() ? 0 : volume.get());
                    status.set(Status.READY);
                });
            });


            mediaPlayer.setOnEndOfMedia(() -> {
                Platform.runLater(this::handleEndOfMedia);
            });

            mediaPlayer.setOnError(() -> {
                System.err.println("MediaPlayer Error: " + mediaPlayer.getError());
                Platform.runLater(this::playNext); // Example: try next song on error
            });

        } catch (Exception e) {
            System.err.println("Error initializing media: " + e.getMessage());
            e.printStackTrace();
            Platform.runLater(() -> status.set(Status.HALTED)); // Indicate an error status
        }
    }

    private void updateFormattedTime() {
        Duration currentVal = currentTime.get();
        Duration totalVal = totalDuration.get();
        // Ensure this runs on FX thread as it updates an FX property
        Platform.runLater(() -> {
            if (currentVal != null && totalVal != null && totalVal.greaterThan(Duration.ZERO)) {
                currentTimeFormatted.set(formatDuration(currentVal) + " / " + formatDuration(totalVal));
            } else {
                currentTimeFormatted.set("00:00 / 00:00");
            }
        });
    }

    private String formatDuration(Duration duration) {
        long seconds = (long) duration.toSeconds();
        long absSeconds = Math.abs(seconds);
        String positive = String.format(
                "%02d:%02d",
                (absSeconds % 3600) / 60,
                absSeconds % 60);
        return seconds < 0 ? "-" + positive : positive;
    }


    private void handleEndOfMedia() { // Assumed to be called on FX thread from OnEndOfMedia
        switch (repeatMode.get()) {
            case ONE:
                if (mediaPlayer != null) {
                    mediaPlayer.seek(Duration.ZERO);
                    mediaPlayer.play();
                }
                break;
            case ALL:
                playNextInternal(true); // true to loop back to start if at end
                break;
            case NONE:
            default:
                if (mediaPlayer != null && currentPlaylistIndex >= playlist.size() - 1) {
                    mediaPlayer.seek(Duration.ZERO);
                    mediaPlayer.stop();
                    status.set(Status.STOPPED);
                } else {
                    playNextInternal(false);
                }
                break;
        }
    }

    private void disposeMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.dispose();
            mediaPlayer = null; // Important to allow GC and prevent further use
            Platform.runLater(() -> { // Ensure FX properties are updated on FX thread
                status.set(Status.UNKNOWN);
                currentTime.set(Duration.ZERO);
                totalDuration.set(Duration.ZERO);
                updateFormattedTime(); // This will also run on FX thread
            });
        }
    }

    /**
     * Sets the playlist for playback.
     * @param newPlaylist The list of AudioParser objects representing the playlist.
     */
    public void setPlaylist(List<AudioParser> newPlaylist) {
        Platform.runLater(() -> { // Ensure playlist modifications and UI properties are on FX thread
            if (newPlaylist != null) {
                this.playlist.setAll(newPlaylist);
                this.currentPlaylistIndex = -1;
                if (!this.playlist.isEmpty()) {
                    // currentPlaylistIndex = 0; // Optionally select first song
                    // currentSong.set(this.playlist.get(currentPlaylistIndex));
                    // initializeMediaPlayer(currentSong.get().fileLocationGet()); // Don't auto-play
                } else {
                    disposeMediaPlayer();
                    currentSong.set(null);
                }
            }
        });
    }

    /**
     * Plays the specified song. If another song is playing, it's stopped.
     * @param song The AudioParser object representing the song to play.
     */
    public void playSong(AudioParser song) {
        if (song == null || song.fileLocationGet() == null) {
            System.err.println("Cannot play null song or song with null file path.");
            return;
        }

        Platform.runLater(() -> { // Ensure FX thread for currentSong property and index
            int songIndex = playlist.indexOf(song);
            if (songIndex != -1) {
                currentPlaylistIndex = songIndex;
            } else {
                System.err.println("Song not found in current playlist. Playing as standalone.");
                if (playlist.isEmpty()) { // if playlist is empty, add this song and play
                    playlist.add(song);
                    currentPlaylistIndex = 0;
                } else { // if playlist is not empty and song not in it, currentPlaylistIndex remains -1 or its previous value
                   // This means endOfMedia behavior might be unexpected for this song if it's not in a list.
                   // Consider clearing playlist and adding just this song, or a dedicated "playOnce" logic.
                   // For now, we'll set it as current song but index might be off.
                }
            }
            currentSong.set(song); // Update current song
        });

        // Initialize and play. Initialization must happen after currentSong is set if it relies on it.
        // MediaPlayer operations can be sensitive to threading, ensure this is safe or wrap parts.
        // initializeMediaPlayer itself handles some Platform.runLater for its property updates.
        initializeMediaPlayer(song.fileLocationGet());
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    /**
     * Plays the song at the given index in the current playlist.
     * @param index The index of the song in the playlist.
     */
    public void playSongAtIndex(int index) {
        // Access to playlist should be on FX thread if it's an ObservableList bound to UI
        Platform.runLater(() -> {
            if (playlist != null && index >= 0 && index < playlist.size()) {
                // currentPlaylistIndex = index; // playSong will set this via indexOf
                playSong(playlist.get(index));
            } else {
                System.err.println("Invalid index or playlist not set for playSongAtIndex: " + index);
            }
        });
    }


    /**
     * Toggles play/pause state of the current media.
     */
    public void playPause() {
        if (mediaPlayer != null) {
            Status currentStatusVal = getStatus(); // Safe to get, it's an FX property
            if (currentStatusVal == Status.PLAYING) {
                mediaPlayer.pause();
            } else if (currentStatusVal == Status.PAUSED || currentStatusVal == Status.READY || currentStatusVal == Status.STOPPED) {
                mediaPlayer.play();
            } else if (currentStatusVal == Status.UNKNOWN || currentStatusVal == Status.STALLED) {
                if (currentSong.get() != null) { // FX property, get is safe
                    playSong(currentSong.get()); // playSong handles its own threading for FX props
                }
            }
        } else if (currentSong.get() != null) {
            playSong(currentSong.get());
        }
    }

    /**
     * Pauses the current media.
     */
    public void pause() {
        if (mediaPlayer != null && mediaPlayer.getStatus() == Status.PLAYING) {
            mediaPlayer.pause();
        }
    }

    /**
     * Plays the next song in the playlist. Considers shuffle and repeat modes.
     */
    public void playNext() {
        playNextInternal(repeatMode.get() == RepeatMode.ALL);
    }

    private void playNextInternal(boolean loopAtEnd) {
        Platform.runLater(() -> { // Ensure playlist access and index changes are on FX thread
            if (playlist.isEmpty()) return;

            if (shuffleActive.get()) {
                currentPlaylistIndex = random.nextInt(playlist.size());
            } else {
                currentPlaylistIndex++;
                if (currentPlaylistIndex >= playlist.size()) {
                    if (loopAtEnd) {
                        currentPlaylistIndex = 0;
                    } else {
                        // currentPlaylistIndex = playlist.size() - 1; // Stay at last song
                        // Let EOM handler take care of stopping if it's truly the end and no repeat all
                        if (mediaPlayer != null) mediaPlayer.stop(); // Explicitly stop if not looping
                        status.set(Status.STOPPED);
                        return;
                    }
                }
            }
            playSong(playlist.get(currentPlaylistIndex));
        });
    }

    /**
     * Plays the previous song in the playlist.
     */
    public void playPrevious() {
         Platform.runLater(() -> { // Ensure playlist access and index changes are on FX thread
            if (playlist.isEmpty()) return;

            if (shuffleActive.get()) {
                currentPlaylistIndex = random.nextInt(playlist.size());
            } else {
                currentPlaylistIndex--;
                if (currentPlaylistIndex < 0) {
                    currentPlaylistIndex = playlist.size() - 1;
                     if (repeatMode.get() != RepeatMode.ALL) {
                         currentPlaylistIndex = 0; // If not repeating all, loop to first and stay
                     }
                }
            }
            playSong(playlist.get(currentPlaylistIndex));
        });
    }

    /**
     * Seeks the current media to the specified duration.
     * @param duration The duration to seek to.
     */
    public void seek(Duration duration) {
        if (mediaPlayer != null) {
            Status currentStatus = mediaPlayer.getStatus();
            if (currentStatus == Status.PLAYING || currentStatus == Status.PAUSED || currentStatus == Status.READY) {
                 if (totalDuration.get() != null && duration.greaterThan(totalDuration.get())) {
                    // If seeking past end, go to end (or slightly before to avoid issues)
                    mediaPlayer.seek(totalDuration.get().subtract(Duration.millis(100)));
                } else if (duration.lessThan(Duration.ZERO)){
                    mediaPlayer.seek(Duration.ZERO);
                }
                else {
                    mediaPlayer.seek(duration);
                }
            }
        }
    }

    /**
     * Seeks the current media to a percentage of its total duration.
     * @param percentage (0.0 to 1.0)
     */
    public void seekPercentage(double percentage) {
        if (mediaPlayer != null && totalDuration.get() != null && totalDuration.get().greaterThan(Duration.ZERO)) {
            double newTime = totalDuration.get().toMillis() * percentage;
            seek(Duration.millis(newTime));
        }
    }


    // --- Observable Properties for UI Binding ---
    public ReadOnlyObjectProperty<Status> statusProperty() { return status; }
    public Status getStatus() { return status.get(); }

    public ReadOnlyObjectProperty<Duration> currentTimeProperty() { return currentTime.getReadOnlyProperty(); }
    public Duration getCurrentTime() { return currentTime.get(); }

    public ReadOnlyObjectProperty<Duration> totalDurationProperty() { return totalDuration.getReadOnlyProperty(); }
    public Duration getTotalDuration() { return totalDuration.get(); }

    public StringProperty currentTimeFormattedProperty() { return currentTimeFormatted; } // Already FX property
    public String getCurrentTimeFormatted() { return currentTimeFormatted.get(); }

    public DoubleProperty volumeProperty() { return volume; } // Already FX property
    public double getVolume() { return volume.get(); }
    public void setVolume(double newVolume) { volume.set(newVolume); } // FX property handles thread

    public BooleanProperty mutedProperty() { return isMuted; } // Already FX property
    public boolean isMuted() { return isMuted.get(); }
    public void setMuted(boolean mute) { isMuted.set(mute); } // FX property handles thread

    public ReadOnlyObjectProperty<AudioParser> currentSongProperty() { return currentSong.getReadOnlyProperty(); }
    public AudioParser getCurrentSong() { return currentSong.get(); }

    public BooleanProperty shuffleActiveProperty() { return shuffleActive; } // Already FX property
    public boolean isShuffleActive() { return shuffleActive.get(); }
    public void setShuffleActive(boolean active) { shuffleActive.set(active); } // FX property handles thread

    public ObjectProperty<RepeatMode> repeatModeProperty() { return repeatMode; } // Already FX property
    public RepeatMode getRepeatMode() { return repeatMode.get(); }
    public void setRepeatMode(RepeatMode mode) { repeatMode.set(mode); } // FX property handles thread

    private ReadOnlyDoubleWrapper currentTimePercentage = new ReadOnlyDoubleWrapper(0);

    public ReadOnlyDoubleProperty currentTimePercentageProperty() {
        return currentTimePercentage.getReadOnlyProperty();
    }

    private void updateCurrentTimePercentage() {
        Platform.runLater(() -> {
            Duration currentVal = currentTime.get();
            Duration totalVal = totalDuration.get();
            if (totalVal != null && totalVal.greaterThan(Duration.ZERO) && currentVal != null) {
                double percentage = currentVal.toMillis() / totalVal.toMillis();
                 if (percentage < 0) percentage = 0;
                 if (percentage > 1) percentage = 1;
                currentTimePercentage.set(percentage);
            } else {
                currentTimePercentage.set(0);
            }
        });
    }


    /**
     * Cleans up resources, particularly the MediaPlayer.
     */
    public void shutdown() {
        disposeMediaPlayer();
    }
}

/*
 * ==========================================================================================
 * NEW SERVICE CLASS - NOT YET INTEGRATED INTO SampleController
 * ==========================================================================================
 * This PlaybackService class is a newly created service intended to encapsulate
 * media playback logic from the SampleController.
 *
 * How it would work:
 * 1. SampleController would create an instance of PlaybackService.
 * 2. SampleController would provide the playlist to the service using `setPlaylist()`.
 * 3. UI controls in SampleController would call methods like `playPause()`, `playNext()`,
 *    `setVolume()`, `seek()`, etc., on the PlaybackService instance.
 * 4. UI elements in SampleController (e.g., time labels, sliders, play/pause button state)
 *    would bind to the observable properties exposed by PlaybackService (e.g.,
 *    `currentTimeProperty()`, `statusProperty()`, `volumeProperty()`).
 *
 * The methods and properties within this class are based on the logic previously found
 * in SampleController's inner 'play' class and related FXML handlers, refactored for
 * better encapsulation and to use observable properties for UI updates.
 *
 * Reconciliation with SampleController to use this service is a separate future step.
 * ==========================================================================================
 */
