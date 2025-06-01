package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Comparator; // Assuming it might be needed for internal sorting

// Assuming these classes exist in the application package and have the necessary getters/setters
// import application.AudioParser;
// import application.Albums;
// import application.Artists;

/**
 * SongDataManager is responsible for loading, managing, and persisting all song metadata,
 * album information, and artist data for the music player application.
 * It handles file operations for reading and writing application data related to the music library.
 * <p>
 * This class is designed to be used as a service, centralizing data operations
 * that were previously spread out in the SampleController.
 * <p>
 * To use this class, SampleController would typically instantiate it and then call
 * methods like {@link #loadAllInitialData()}, {@link #addSongsFromDirectory(File, Runnable)},
 * {@link #getSongList()}, etc. UI updates based on data changes would be handled
 * by SampleController, possibly by making data exposed by this manager observable
 * or by providing callbacks.
 * <p>
 * Example (conceptual usage in SampleController):
 * <pre>{@code
 * private SongDataManager songDataManager;
 *
 * public void initialize() {
 *     songDataManager = new SongDataManager();
 *     songDataManager.loadAllInitialData(success -> {
 *         if (success) {
 *             updateSongListView(songDataManager.getSongList());
 *             // ... other UI updates
 *         } else {
 *             // Handle data loading error
 *         }
 *     });
 * }
 *
 * @FXML
 * private void handleAddNewSongs() {
 *     File selectedDirectory = // ... get directory from user ...
 *     if (selectedDirectory != null) {
 *         songDataManager.addSongsFromDirectory(selectedDirectory, () -> {
 *             updateSongListView(songDataManager.getSongList());
 *             // Show completion message
 *         });
 *     }
 * }
 * }</pre>
 *
 * Note: This is a new service class and is not yet integrated into SampleController.
 * Reconciliation with SampleController will be done separately.
 */
public class SongDataManager {

    // ObservableLists for live UI updates if SampleController binds to them.
    // Otherwise, SampleController would call getters to retrieve copies or unmodifiable lists.
    private final ObservableList<AudioParser> songList = FXCollections.observableArrayList();
    private final ObservableList<Albums> albumList = FXCollections.observableArrayList();
    private final ObservableList<Artists> artistList = FXCollections.observableArrayList();

    // Internal lists for managing file paths and raw data
    private final List<String> songFilePaths = new ArrayList<>(); // from mydb.txt / Folders2
    private final List<String> musicDirectories = new ArrayList<>(); // from folders.txt / Folders
    private final List<String> filesToExtract = new ArrayList<>(); // from toExtract.txt / foldertoExtract

    private final List<String> processedAlbumNames = new ArrayList<>(); // from albumsDone
    private final List<String> processedArtistNames = new ArrayList<>(); // from artistsdone

    // Executor for background file operations
    private final ExecutorService fileExecutorService = Executors.newFixedThreadPool(2, r -> {
        Thread t = new Thread(r);
        t.setName("SongDataFileThread");
        t.setDaemon(true);
        return t;
    });

    private static final String ILIX_DIR_PATH = System.getProperty("user.home") + "/ilix";
    private static final String SONGS_FILE = ILIX_DIR_PATH + "/songs.txt";
    private static final String MYDB_FILE = ILIX_DIR_PATH + "/mydb.txt"; // Contains song file paths
    private static final String FOLDERS_FILE = ILIX_DIR_PATH + "/folders.txt"; // Contains music directory paths
    private static final String TO_EXTRACT_FILE = ILIX_DIR_PATH + "/toExtract.txt";
    private static final String ALBUMS_FILE = ILIX_DIR_PATH + "/albums.txt";
    private static final String ALBUMS_DONE_FILE = ILIX_DIR_PATH + "/albumsdone.txt";
    private static final String ARTISTS_FILE = ILIX_DIR_PATH + "/artists.txt";
    private static final String ARTISTS_DONE_FILE = ILIX_DIR_PATH + "/artistsdone.txt";
    // Add other file constants as needed (e.g., totalSongs.txt, state.txt for sort order)

    /**
     * Initializes the SongDataManager.
     * (Currently no specific actions needed in constructor beyond field initialization)
     */
    public SongDataManager() {
        // Ensure base directory exists (moved from SampleController's checkFOrFIles)
        new File(ILIX_DIR_PATH).mkdirs();
    }

    /**
     * Loads all initial data required by the application.
     * This includes song metadata, album and artist info, and persisted file paths.
     * This method should be called on startup.
     *
     * @param onCompletion A callback to run on the JavaFX Application thread after loading is complete.
     *                     The boolean parameter indicates success (true) or failure (false).
     */
    public void loadAllInitialData(java.util.function.Consumer<Boolean> onCompletion) {
        Task<Boolean> loadTask = new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                // Equivalent of BringItON3, BringItON4
                loadSongFilePaths(); // Reads MYDB_FILE into songFilePaths (Folders2)
                loadMusicDirectories(); // Reads FOLDERS_FILE into musicDirectories (Folders)
                loadFilesToExtract(); // Reads TO_EXTRACT_FILE into filesToExtract

                // Equivalent of BringItON (loads actual song metadata based on the paths)
                loadSongMetadata(); // Reads SONGS_FILE into songList

                // Equivalent of BringItONA, BringItONAr
                loadAlbumData();
                loadArtistData();
                return true; // Assuming success for now, add error handling
            }
        };

        loadTask.setOnSucceeded(event -> onCompletion.accept(loadTask.getValue()));
        loadTask.setOnFailed(event -> {
            loadTask.getException().printStackTrace();
            onCompletion.accept(false);
        });
        fileExecutorService.submit(loadTask);
    }

    private void loadSongFilePaths() throws IOException {
        // Adapted from BringItON3 (loading Folders2 from mydb.txt)
        songFilePaths.clear();
        File dbFile = new File(MYDB_FILE);
        if (!dbFile.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(dbFile, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    songFilePaths.add(line.trim());
                }
            }
        }
    }

    private void loadMusicDirectories() throws IOException {
        // Adapted from BringItON4 (loading Folders from folders.txt)
        musicDirectories.clear();
        File foldersFile = new File(FOLDERS_FILE);
        if (!foldersFile.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(foldersFile, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    musicDirectories.add(line.trim());
                }
            }
        }
    }

    private void loadFilesToExtract() throws IOException {
        filesToExtract.clear();
        File toExtractFile = new File(TO_EXTRACT_FILE);
        if (!toExtractFile.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(toExtractFile, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    filesToExtract.add(line.trim());
                }
            }
        }
    }


    private void loadSongMetadata() throws IOException {
        // Adapted from BringItON (loading songList from songs.txt)
        ObservableList<AudioParser> tempSongList = FXCollections.observableArrayList();
        File songsFile = new File(SONGS_FILE);
        if (!songsFile.exists()) {
             // If songList is bound to UI, update on FX thread
            javafx.application.Platform.runLater(() -> {
                songList.clear();
            });
            return;
        }

        // Using a temporary list for background processing
        List<AudioParser> loadedSongs = new ArrayList<>();

        try (Scanner read = new Scanner(new BufferedReader(new FileReader(songsFile, StandardCharsets.UTF_8)))) {
            read.useDelimiter("\\(~\\)"); // Delimiter used in SampleController
            String title, filepath, imageInfo, album, artist, albumNum, length, lastDateStr;
            while (read.hasNextLine()) {
                String line = read.nextLine();
                if (line.trim().isEmpty()) continue;

                String[] data = line.split("\\(~\\)"); // Re-split by line as useDelimiter is tricky with nextLine
                if (data.length >= 8) {
                    title = data[0].trim();
                    filepath = data[1].trim();
                    imageInfo = data[2].trim();
                    album = data[3].trim();
                    artist = data[4].trim();
                    albumNum = data[5].trim();
                    length = data[6].trim();
                    lastDateStr = data[7].trim();

                    // Basic validation: ensure filepath exists
                    if (new File(filepath).exists()) {
                        loadedSongs.add(new AudioParser(title, filepath, imageInfo, album, artist, albumNum, length, lastDateStr));
                    } else {
                        System.err.println("Skipping song, file not found: " + filepath);
                    }
                } else {
                     System.err.println("Skipping malformed song line: " + line);
                }
            }
        }
        // Update the main observable list on the JavaFX application thread
        javafx.application.Platform.runLater(() -> {
            songList.setAll(loadedSongs);
            // Potentially notify other parts of the application or update dependent views here
        });
    }

    private void loadAlbumData() throws IOException {
        // Adapted from BringItONA
        ObservableList<Albums> tempAlbumList = FXCollections.observableArrayList();
        processedAlbumNames.clear();

        File file = new File(ALBUMS_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    String[] data = line.split("~");
                    if (data.length >= 2) {
                        tempAlbumList.add(new Albums(data[0].trim(), data[1].trim()));
                    }
                }
            }
        }
        File doneFile = new File(ALBUMS_DONE_FILE);
        if (doneFile.exists()) {
             try (BufferedReader reader = new BufferedReader(new FileReader(doneFile, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                       processedAlbumNames.add(line.trim());
                    }
                }
            }
        }
        javafx.application.Platform.runLater(() -> albumList.setAll(tempAlbumList));
    }

    private void loadArtistData() throws IOException {
        // Adapted from BringItONAr
        ObservableList<Artists> tempArtistList = FXCollections.observableArrayList();
        processedArtistNames.clear();

        File file = new File(ARTISTS_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                     if (line.trim().isEmpty()) continue;
                    String[] data = line.split("~");
                    if (data.length >= 2) {
                        tempArtistList.add(new Artists(data[0].trim(), data[1].trim()));
                    }
                }
            }
        }
        File doneFile = new File(ARTISTS_DONE_FILE);
        if (doneFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(doneFile, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        processedArtistNames.add(line.trim());
                    }
                }
            }
        }
        javafx.application.Platform.runLater(() -> artistList.setAll(tempArtistList));
    }

    /**
     * Returns an observable list of songs.
     * UI components can bind to this list to stay updated.
     * @return The observable list of {@link AudioParser} objects.
     */
    public ObservableList<AudioParser> getSongList() {
        return songList;
    }

    /**
     * Returns an observable list of albums.
     * @return The observable list of {@link Albums} objects.
     */
    public ObservableList<Albums> getAlbumList() {
        return albumList;
    }

    /**
     * Returns an observable list of artists.
     * @return The observable list of {@link Artists} objects.
     */
    public ObservableList<Artists> getArtistList() {
        return artistList;
    }

    /**
     * Sorts the main song list using the provided comparator.
     * This operation is done on a background thread if the list is large,
     * and the UI-bound list is updated on the JavaFX Application thread.
     *
     * @param comparator The comparator to use for sorting.
     * @param onCompletion Callback to run on JavaFX thread after sorting is done.
     */
    public void sortSongList(Comparator<AudioParser> comparator, Runnable onCompletion) {
        Task<Void> sortTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                // Create a temporary list for sorting to avoid concurrent modification issues
                // if songList is directly bound and modified by Collections.sort on FX thread.
                // However, if songList is only updated via setAll, direct sort on a copy is fine.
                List<AudioParser> currentSongs = new ArrayList<>(songList);
                currentSongs.sort(comparator);
                javafx.application.Platform.runLater(() -> {
                    songList.setAll(currentSongs);
                    if (onCompletion != null) {
                        onCompletion.run();
                    }
                });
                return null;
            }
        };
        fileExecutorService.submit(sortTask);
    }

    /**
     * Persists the current order of songs in the songList to the songs.txt file.
     * This is a potentially long-running I/O operation and is executed on a background thread.
     * It first backs up the existing songs.txt file.
     *
     * @param onCompletion A callback to run on the JavaFX Application thread upon completion.
     */
    public void saveOrderedSongs(Runnable onCompletion) {
        Task<Void> saveTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                File songsFile = new File(SONGS_FILE);
                File backupFile = new File(SONGS_FILE + "_backup.txt");

                if (songsFile.exists()) {
                    try {
                        Files.copy(songsFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("Backed up " + SONGS_FILE + " to " + backupFile.getName());
                    } catch (IOException e) {
                        System.err.println("Failed to backup songs.txt: " + e.getMessage());
                        // Decide if you want to proceed without backup or fail
                    }
                }

                // Make a copy for thread-safe iteration
                List<AudioParser> songsToSave = new ArrayList<>(songList);

                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(songsFile, false), StandardCharsets.UTF_8))) { // false to overwrite
                    for (AudioParser song : songsToSave) {
                        writer.write(song.nameGet() + "(~)" +
                                     song.fileLocationGet() + "(~)" +
                                     song.ImageGet() + "(~)" +
                                     song.albumGet() + "(~)" +
                                     song.artistGet() + "(~)" +
                                     song.AlbumnoGet() + "(~)" + // Assuming AlbumnoGet returns a string representation
                                     song.lengthGet() + "(~)" +
                                     song.getDatemodedlast()); // Assuming getDatemodedlast returns a string representation
                        writer.newLine();
                    }
                }
                System.out.println("Song order saved to " + SONGS_FILE);
                return null;
            }
        };
        saveTask.setOnSucceeded(event -> {
            if (onCompletion != null) javafx.application.Platform.runLater(onCompletion);
        });
        saveTask.setOnFailed(event -> {
            System.err.println("Failed to save ordered songs: " + saveTask.getException().getMessage());
            saveTask.getException().printStackTrace();
            if (onCompletion != null) javafx.application.Platform.runLater(onCompletion); // Or indicate failure
        });
        fileExecutorService.submit(saveTask);
    }

    // TODO: Implement methods for addSongsFromDirectory (adapting 'extract' and 'extractFolders')
    // This would involve:
    // 1. Scanning directories for music files (Task).
    // 2. For each new file:
    //    a. Extracting metadata (potentially using Tika/JAudioTagger - this might be a separate utility class).
    //    b. Creating an AudioParser object.
    //    c. Adding to the songList (on FX thread).
    //    d. Persisting the new song to songs.txt and mydb.txt (background task).
    // This method would take a File (directory) and a callback for completion.

    // TODO: Implement methods for managing playlists (reading/writing playlist files).

    /**
     * Shuts down the executor service used for file operations.
     * Should be called when the application is closing.
     */
    public void shutdown() {
        fileExecutorService.shutdown();
        try {
            if (!fileExecutorService.awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS)) {
                fileExecutorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            fileExecutorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

/*
 * ==========================================================================================
 * NEW SERVICE CLASS - NOT YET INTEGRATED INTO SampleController
 * ==========================================================================================
 * This SongDataManager class is a newly created service intended to encapsulate
 * data management logic from the SampleController.
 *
 * How it would work:
 * 1. SampleController would create an instance of SongDataManager.
 * 2. On initialization, SampleController would call `songDataManager.loadAllInitialData(...)`.
 * 3. UI elements (like ListView for songs) in SampleController would either:
 *    a) Observe the ObservableLists provided by `getSongList()`, `getAlbumList()`, etc.
 *    b) Be manually updated by SampleController after data operations complete (using callbacks).
 * 4. Actions like adding new songs, sorting, or saving would be delegated by SampleController
 *    to methods in SongDataManager (e.g., `songDataManager.addSongsFromDirectory(...)`,
 *    `songDataManager.sortSongList(...)`, `songDataManager.saveOrderedSongs(...)`).
 *
 * The methods within this class are based on the logic previously found in
 * SampleController, refactored for clarity and to run I/O operations on background threads
 * using JavaFX Task or ExecutorService.
 *
 * Reconciliation with SampleController to use this service is a separate future step.
 * ==========================================================================================
 */
