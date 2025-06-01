package application;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
//import slash.metamusic.mp3.*;
//import slash.metamusic.util.Files;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java. util. Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;
import org.controlsfx.control.PopOver;
import org.fxmisc.flowless.Cell;
import org.fxmisc.flowless.VirtualFlow;
import org.fxmisc.flowless.VirtualFlowHit;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.Tag;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import com.sun.javafx.scene.control.skin.ButtonSkin;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent ;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.scene.Scene;
import java.util.prefs.Preferences;

@SuppressWarnings({ "unused" })
public class SampleController extends ListCell <AudioParser> implements Initializable {




    int totalSongs=0,totalSongse=0,listViewDex=0,counter=0,checker=0;

    private int Chosen1=0;

    double position=0;
    boolean playListToEnd=false;
    boolean repeatList=false;
    boolean shuffle=false;



    @FXML
	 ScrollPane rootPane = new ScrollPane();

    @FXML
	 TilePane buttonbar = new TilePane();

    @FXML
    AnchorPane home = new AnchorPane();

    @FXML
    public Label totalSngs;

    @FXML
    public Button reset;

	@FXML
	private MediaView mv;

	@FXML
	private Pane panecut;

	@FXML
	private AnchorPane apanecut;

	@FXML
	private ScrollPane scrollt;

	public MediaPlayer mp=null;


	public Media mo;

	private final boolean repeat = false;

	private boolean stopRequested = false;

	private boolean firstStart = true;

	private boolean atEndOfMedia = false;

//	private boolean smallviewVisible = false;

	private Duration duration;

	@FXML
	private  ImageView image;//=image.setImage(' ');;

	@FXML
	private  ImageView searchimage;

	@FXML
	private MenuButton Details;

	@FXML
	private Label artist;

	@FXML
	public Button albumy;

	@FXML
	private Label album;

	@FXML
	private
	Label title;

	@FXML
	private Label playTime;

	@FXML
	private Slider timeSlider;

	@FXML
	private Slider volumeSlider=new Slider();

	@FXML
	private Button forward;

	@FXML
	private Button backward;


	/////////delete
	@FXML
	private Button Blur;

	@FXML
	private Button Blur2;

	@FXML
	private Button Blur3;
/////////////////delete

	@FXML
	private Button buttond;

	@FXML
	private ImageView playB;

	@FXML
	private Button details;

	@FXML
	private Button exit;

	@FXML
	private Button minimize;

	@FXML
	private Button refresh;

	@FXML
	Button songss = new Button("Songs");

	@FXML
	Button albumss  = new Button("Albums");

	@FXML
	Button artists = new Button("Artists");

	@FXML
	ImageView sorter = new ImageView();

	@FXML
	private static Button play;

	//Button searchButton=new Button();

	TextField searchbar=new TextField();

	TextField newame=new TextField();


	public Status status;

	String current=new String();
	//current=null;
	@FXML
	private Button addNewTrack;

	@FXML
	private ImageView repeatB;



	//private static final Executor PRINT_QUEUE = Executors.newSingleThreadExecutor();

	//BlockingQueue<Image> imgur = new ArrayBlockingQueue<>(20000);
	//BlockingQueue<Runnable> imgur2 = new ArrayBlockingQueue<Runnable>(2);
	 private final ExecutorService executor=Executors.newFixedThreadPool(3);
	 ThreadPoolExecutor pooler = new ThreadPoolExecutor(
			    3,                                     // keep at least two thread ready,
			                                           // even if no Runnables are executed
			    8,                                     // at most five Runnables/Threads
			                                           // executed in parallel
			    20, TimeUnit.MINUTES,                   // idle Threads terminated after one
			                                           // minute, when min Pool size exceeded
			    new ArrayBlockingQueue<Runnable>(9),r ->{
					Thread t=new Thread(r);
					//r.
					t.setName("AudiCell");
				//	t.setDaemon(true);
					return t;

				},new ThreadPoolExecutor.DiscardPolicy());


	 ThreadPoolExecutor pool = new ThreadPoolExecutor(
			    3,                                     // keep at least two thread ready,
			                                           // even if no Runnables are executed
			    7,                                     // at most five Runnables/Threads
			                                           // executed in parallel
			    20, TimeUnit.MINUTES,                   // idle Threads terminated after one
			                                           // minute, when min Pool size exceeded
			    new ArrayBlockingQueue<Runnable>(3),r ->{
					Thread t=new Thread(r);
					//r.
					t.setName("Pool");
				//	t.setDaemon(true);
					return t;

				},new ThreadPoolExecutor.DiscardPolicy()); // outstanding Runnables are kept here
	 ThreadPoolExecutor pooly = new ThreadPoolExecutor(
			    2,                                     // keep at least two thread ready,
			                                           // even if no Runnables are executed
			    5,                                     // at most five Runnables/Threads
			                                           // executed in parallel
			    20, TimeUnit.MINUTES,                   // idle Threads terminated after one
			                                           // minute, when min Pool size exceeded
			    new ArrayBlockingQueue<Runnable>(4),r ->{
					Thread t=new Thread(r);
					//r.
					t.setName("Pooly");
				//	t.setDaemon(true);
					return t;

				},new ThreadPoolExecutor.DiscardOldestPolicy());
	 ThreadPoolExecutor imageSetter = new ThreadPoolExecutor(
			    15,                                     // keep at least two thread ready,
			                                           // even if no Runnables are executed
			    20,                                     // at most five Runnables/Threads
			                                           // executed in parallel
			    20, TimeUnit.MINUTES,                   // idle Threads terminated after one
			                                           // minute, when min Pool size exceeded
			    new ArrayBlockingQueue<Runnable>(1),r ->{
					Thread t=new Thread(r);
					//r.
					t.setName("imageSetter");
				//	t.setDaemon(true);
					return t;

				},new ThreadPoolExecutor.DiscardOldestPolicy());
	//  LinkedBlockingQueue megnew =LinkedBlockingQueue<>(1000);
	  ArrayBlockingQueue<Runnable> abq = new ArrayBlockingQueue<Runnable>(100);
	 ExecutorService exService = new ThreadPoolExecutor(
	            2,
	            5,
	            0L,
	            TimeUnit.MILLISECONDS,
	            abq);


	// ThreadPoolExecutor pool2 = new ThreadPoolExecutor();

	// pool.DiscardOldestPolicy;



	 private final ExecutorService execute=Executors.newFixedThreadPool(4);




//		BOSSES 	//		**	//		**	//**		//			**		//			**			//
	@FXML
	public ListView<AudioParser> trackListView;

	//CUSTOMS
	public ListView<String> tracklist=new ListView<String>();

	ObservableList<String> lists = FXCollections.observableArrayList();

	ObservableList<String> addedSongs = FXCollections.observableArrayList();
//scrollpane
	ObservableList<Albums> albums= FXCollections.observableArrayList();

	ObservableList<Artists> artistry= FXCollections.observableArrayList();

	ObservableList<String> albumsDone= FXCollections.observableArrayList();

	ObservableList<String> artistdone= FXCollections.observableArrayList();

	public ObservableList<AudioParser> list = FXCollections.observableArrayList();

	 ObservableList<AudioParser> playing = FXCollections.observableArrayList();

	 public  ArrayList<AudioParser> listper = new ArrayList<AudioParser>();

	 ObservableList<AudioParser> lazyLoad = FXCollections.observableArrayList();

    ObservableList<AudioParser> refreshList = FXCollections.observableArrayList();

	public  ArrayList<String> Folders = new ArrayList<String>();

	public  ArrayList<String> Folders2 = new ArrayList<String>();

	public  ArrayList<String> foldertoExtract = new ArrayList<String>();

	//public  ArrayList<Double> songsWithHighli = new ArrayList<Double>();

	 FilteredList<AudioParser> filteredData=new FilteredList<>(list,s->true);


	 int beforeAlbum=0;
	  ObservableList<Double> queue = FXCollections.observableArrayList();


//	 private BlockingQueue<Runnable> imalink = new LinkedBlockingQueue <Runnable>(40);

	//Comperators
	  Comparator<? super AudioParser> comparatorMyObject_byDay = new Comparator<AudioParser>() {
	      @Override
	      public int compare(AudioParser o1, AudioParser o2) {
	          return (o1.nameGet()).compareToIgnoreCase(o2.nameGet());
	      }
	  };
	  Comparator<? super AudioParser> comparatorByArtist = new Comparator<AudioParser>() {
	      @Override
	      public int compare(AudioParser o1, AudioParser o2) {
	          return (o1.albumGet()).compareToIgnoreCase(o2.albumGet());
	      }
	  };
	  Comparator<? super AudioParser> comparatorBynamey = new Comparator<AudioParser>() {
	      @Override
	      public int compare(AudioParser o1, AudioParser o2) {
	          return (o1.artistGet()).compareToIgnoreCase(o2.artistGet());
	      }
	  };
	  Comparator<? super AudioParser> comparatorbyTrack = new Comparator<AudioParser>() {
	      @Override
	      public int compare(AudioParser o1, AudioParser o2) {
	          return (o1.AlbumnoGet()).compareTo(o2.AlbumnoGet());
	      }
	  };
	  Comparator<? super AudioParser> comparatorbyDate = new Comparator<AudioParser>() {
	      @Override
	      public int compare(AudioParser o1, AudioParser o2) {


	          return (o1.getDatemodedlast()).compareTo(o2.getDatemodedlast());
	      }
	  };
		Comparator<? super Albums> albumsComparator = new Comparator<Albums>() {
		    @Override
		    public int compare(Albums o1, Albums o2) {


		        return (o1.albumGet()).compareTo(o2.albumGet());
		    }
		};
		Comparator<? super Artists> artistsComparator = new Comparator<Artists>() {
		    @Override
		    public int compare(Artists o1, Artists o2) {


		        return (o1.artistGet()).compareTo(o2.artistGet());
		    }
		};




	// int ki=0;
		public void machin() {
		 Task<Void> consumerTask = new Task<Void>() {

			    @Override

			    protected Void call() throws Exception {



			        //track sets of points that are plotted

			        int plotCount = 0;

			        while (true) {



			        	if(!queue.isEmpty()) {


			        		 for (int ki=queue.size(); ki <= 0; ki--) {


			        			 GridPane gridd = (GridPane) flows.getCell(queue.get(ki).intValue()).getNode();
			      				TextFlow he = (TextFlow)gridd.getChildren().get(2);

			 					Text her =(Text)he.getChildren().get(0);
			 					Text herr =(Text)he.getChildren().get(1);
			      				  Platform.runLater(() -> {
			      					  gridd.setStyle("-fx-background-color: #191D1F;");
			 						her.setFill(Color.WHITE);
			 						herr.setFill(Color.GREY);
			      				      });
				 		        }Thread.sleep(2);


			        	}



			        }



			        //
			    }

			 };
			 pooler.execute(consumerTask);

		}

	// ImageQeue(ImageView icon,Image image)
	// public int awareness=0;
	 boolean TYming = false;
    @SuppressWarnings({ })
	class AudioParserCell implements Cell{

		    AudioParser audiop;
		 	GridPane grid = new GridPane();
		 //	Cell mangro =Cell.wrapNode(grid);
		 	//Image imgurra=new Image(System.getProperty("user.home")+"/ilix/A5.png");
		 	ImageView icon = new ImageView();
		 	//Image legg;
			Text SonfName=new Text();
			Text SonfName2=new Text();
			Text lenght=new Text("");
			TextFlow textFlowPane = new TextFlow();
		//	Pane pane=new Pane();
		//	Rectangle rect = new Rectangle();
			HBox iconAndName = new HBox();
			PauseTransition wait = new PauseTransition(Duration.seconds(0.150));

			Timeline timeline = new Timeline();
		//	Snapshot snappy = new Snapshot()
			//	Image referent ;
		//	ReferenceQueue<Image> referenceQueue = new ReferenceQueue<>();

		//	WeakReference<Image> weakReference1  ;
			{
				//icon.setImage(new Image(new File(System.getProperty("user.home")+"\\ilix\\A5.png").toURI().toString()));
				//textFlowPane.setId("me");
				iconAndName.setPadding(new Insets(0,16,0,0));
				//textFlowPane.setPrefSize(530,30);
				textFlowPane.setMinSize(530,30);
				textFlowPane.setMaxSize(530,30);

				//grid.com
				//grid.setBorder(new Border());
				grid.setMinSize(580,49);
				grid.setMaxSize(580,49);
		    	grid.setPadding(new Insets(2));
		    	grid.setVgap(10);
		       // grid.setHgap(10);
		        grid.getStyleClass().add("gborder");
		        icon.setFitHeight(30);
		        icon.setFitWidth(30);
		        icon.setCache(false);//icon.setCacheHint(CacheHint.SPEED);

		        iconAndName.setAlignment(Pos.CENTER_LEFT);
		        iconAndName.getChildren().add(icon);
		        iconAndName.setPadding(new Insets(0,5,0,5));

		      //  pane.setPrefHeight(35);

		        /*
		        pane.setMaxSize(530, 35);
		        pane.setPrefSize(530,35);
		        pane.setMinSize(530, 35);
*/

		        SonfName2.setFill(Color.GRAY);

		        SonfName2.getStyleClass().add("horder");
		        lenght.setFill(Color.GREY);
		        lenght.getStyleClass().add("horder");
			    SonfName.setFill(Color.WHITE);
			    SonfName.getStyleClass().add("zorder");

		        grid.add(iconAndName, 0, 0);
		        //grid.add(pane, 2, 0);
		        grid.add(lenght,3,0);
		        grid.setHalignment(lenght, HPos.LEFT);

		    /*    rect.widthProperty().bind(textFlowPane.widthProperty());
				rect.heightProperty().bind(textFlowPane.heightProperty());
				textFlowPane.setClip(rect);*/
				//pane.getChildren().add(textFlowPane);
		        //

				grid.add(textFlowPane, 2, 0);
			//	grid.setStyle("-fx-box-border: transparent;");
				textFlowPane.getChildren().addAll(SonfName, SonfName2);
//
				//
//				grid.setOnMouseEntered( ME ->
//	     		{
//	     			 grid.setStyle("-fx-border-color:#32393D");
//	     			// grid.setStyle(" -fx-border-color: -fx-control-inner-background,#32393D");
//	     			 // hit.getCell().
//	     		});
//				grid.setOnMouseExited( ME ->
//	     		{
//	     			grid.setStyle("-fx-border-color: #191D1F");
//	     			//grid.setStyle(" -fx-border-color: -fx-control-inner-border,#191D1F");
//	     			 // hit.getCell().
//	     		});
				grid.setOnMouseClicked(event -> {

					if(event.getButton()==MouseButton.PRIMARY) {
						Platform.runLater(() -> {
							//flows.
							SonfName2.setFill(Color.DARKSLATEGREY);
							SonfName.setFill(Color.BLACK);
					grid.setStyle("-fx-background-color: #39FF9F;"/*-fx-border-radius:2;-fx-border-width: 0.8 1 1.6 1;-fx-border-color: linear-gradient(to right,transparent , #32393D 50%, transparent ) transparent transparent transparent  ;"*/);
						});
						}else {


					}
					//SonfName2.setFill(Color.GHOSTWHITE);
					 //flows.getCell(--listViewDex).updateIndex(--listViewDex);;
				});
				grid.setOnMousePressed(event -> {
					Platform.runLater(() -> {
						grid.setScaleX(0.990);
						grid.setScaleY(0.990);
						grid.setScaleZ(0.990);
							});

					//SonfName2.setFill(Color.GHOSTWHITE);
					 //flows.getCell(--listViewDex).updateIndex(--listViewDex);;
				});
				grid.setOnMouseReleased(event -> {
					Platform.runLater(() -> {
						grid.setScaleX(1.0);
						grid.setScaleY(1.0);
						//grid.setScaleZ(1.0);
							});

					//SonfName2.setFill(Color.GHOSTWHITE);
					 //flows.getCell(--listViewDex).updateIndex(--listViewDex);;
				});
				grid.setOnMouseEntered(event -> {
					//grid.getStyle()

					 //Platform.runLater(() -> {
						// grid.setStyle("-fx-background-color: transparent;");
//					.button:hover {
//				    -fx-scale-x: 1.1;
//				    -fx-scale-y: 1.1;
//				    -fx-scale-z: 1.1;
//				}
					if(audiop!=null) {
					if(audiop.fileLocationGet()==current) {
						//grid.setStyle("-fx-border-color: transparent;");


						new Timeline(
								new KeyFrame(
										Duration.seconds(0.019),ggt ->{


										//	if(flows.visibleCells().contains(this)) {
											if(grid.isHover()) {
												//	Platform.runLater(() -> {
												Platform.runLater(() -> {
													 grid.setScaleX(1.01);
													 grid.setScaleY(1.01);
													// grid.setStyle("-fx-background-color: #32393D ;");
													});
												//	});
											}



										})).play();
					}else {
						new Timeline(
								new KeyFrame(
										Duration.seconds(0.019),gg ->{




												//	Platform.runLater(() -> {
											if(grid.isHover()) {
												Platform.runLater(() -> {
													 grid.setScaleX(1.01);
													 grid.setScaleY(1.01);
													 grid.setStyle("-fx-background-color: #32393D ;");
													});
										}
												//	});



										})).play();

					}}

				    //  });

				});

				grid.setOnMouseExited(event -> {
					if(audiop!=null) {
					if(audiop.fileLocationGet()==current) {
						Platform.runLater(() -> {
							grid.setScaleX(1.0);
							grid.setScaleY(1.0);
							grid.setScaleZ(1.0);
								});
					//	grid.setStyle("-fx-border-color: transparent;");
					}
					else {

					//	grid.setStyle("-fx-background-color: transparent;"/*-fx-border-radius:2;-fx-border-width: 0.8 1 1.6 1;-fx-border-color: linear-gradient(to right,transparent , #32393D 50%, transparent ) transparent transparent transparent  ;*/);
						Platform.runLater(() -> {
							grid.setScaleX(1.0);
							grid.setScaleY(1.0);
							grid.setScaleZ(1.0);
						//	this.
						    grid.setStyle("-fx-background-color: #191D1F" /*linear-gradient(to right,transparent , #32393D 50%, transparent ) transparent transparent transparent  ;"*/);


				      });
					}}
				});

				// if (TYming = true) {
						Platform.runLater(() -> {
							grid.setScaleX(0.2);
							grid.setScaleY(0.2);
							grid.setScaleZ(0.2);

							grid.setScaleX(1.0);
							grid.setScaleY(1.0);
							grid.setScaleZ(1.0);
								});

				//	}
			}

//HBox.setMargin(Node, Insets)
			//@Override



		public AudioParserCell(AudioParser audioparser ,int posnum) {//,String artist,String album) {
			if(audioparser.nameGet() == "Album Header") {

				Vpaen23 = false;
				 Platform.runLater(() -> {
					  grid.setStyle("-fx-background-color: #b;");
					// grid.setMinSize(580,49);
						grid.setMinSize(580,1000);
					  grid.add(getGridder(), 0, 0);
				System.out.println("YES");
				grid.setOnMouseClicked(event -> {


					//  SonfName2.setFill(Color.DARKSLATEGREY);
					 // SonfName.setFill(Color.BLACK);
					 // this.updateItem(getGridder());
				      });
				 });

				// ();
			}else {
			this.audiop=audioparser;
			//queue.add(0, new Image(new File(audioparser.ImageGet()).toURI().toString(),true));
			//if()
			//new EventHandler<ScrollEvent>()
			 if(audioparser.fileLocationGet()==current) {
				  Platform.runLater(() -> {
					 // grid.setStyle("-fx-background-color: #39FF9F;");
					  grid.setStyle(" -fx-background-color: #39FF9F");
					// grid.
					/*  new Timeline(
								new KeyFrame(
										Duration.seconds(0.019),gg ->{




												//	Platform.runLater(() -> {
											if(audioparser.fileLocationGet()!=current) {
												Platform.runLater(() -> {

									      					  grid.setStyle("-fx-background-color: #191D1F;");
									      					SonfName2.setFill(Color.WHITE);
									      					SonfName.setFill(Color.GREY);
									      				      });
													}
										}
												//	});



										)).play();*/

					  SonfName2.setFill(Color.DARKSLATEGREY);
					  SonfName.setFill(Color.BLACK);
				      });
				  wait.setOnFinished((e) -> {
					  if(audioparser.fileLocationGet()!=current) {
					  Platform.runLater(() -> {

      					  grid.setStyle("-fx-background-color: #191D1F;");
      					SonfName.setFill(Color.WHITE);
      					SonfName2.setFill(Color.GREY);
      					wait.stop();
      				      });
						System.gc();
					  }
					wait.playFromStart();
					});
					wait.play();
				//  queue.add(new Double(posnum));
				 //awareness =posnum;
			}

			 //parser.

		    updateItem(audioparser,posnum);
		    //current


			}

			}

		//@Override
		public void updateItem( AudioParser parser,int cellNo )
		{

			//WeakReference<>(legg);

		//	referent=new Image(new File(parser.ImageGet()).toURI().toString(),true);
		//	weakReference1= new WeakReference<Image>(referent);
			//executor.execute(()->{));});
//			 FadeTransition ft = new FadeTransition();
//		      ft.setNode(icon);
//		      ft.setDuration(new Duration(900));
//		      ft.setFromValue(0.0);
//		      ft.setToValue(1.0);
//		      ft.setAutoReverse(false);
//		      ft.play();
//			if (referent != null) {
//				weakReference1.clear();
//			    // GC hasn't removed the instance yet
//			} else {
//			    System.out.println("EMPTied");
//			}
			/*wait.setOnFinished((e) -> {
				//	filteredData.contains(parser);
					if(flows.visibleCells().contains(this)) {
					//	Platform.runLater(() -> {
					icon.setImage(new Image(new File(parser.ImageGet()).toURI().toString() ,30 ,30,false ,false ,true));
					//	});
						}else {

					}

				});
		  wait.play();*/

			SonfName.setText(parser.nameGet());
			lenght.setText(parser.lengthGet());
			SonfName2.setText("\r\n"+parser.artistGet());
			new Timeline(
					new KeyFrame(
							Duration.seconds(0.0109),event ->{

							//	flows.visibleCells().
								if(flows.visibleCells().contains(this)) {

									//	Platform.runLater(() -> {
									icon.setImage(new Image(new File(parser.ImageGet()).toURI().toString() ,30 ,30,false ,false ,true));
									//	});
										}else {
											//System.out.print("DIsposed");
											this.dispose();
										}


							})).play();
//			while(true) {
//				if(parser.fileLocationGet()!=current) {
//
//
//				}
//
//			}
			//if(this.)





				/*		 pooler.execute(new Runnable(){
							 @Override
							 public void run(){

					// Platform.runLater(() -> {

						//return null;
			}});*/
//			 Task<Void> consumerTask = new Task<Void>() {
//
//
//				    @Override
//
//				    protected Void call()  {
//
//				    	try {
		//	f.toURI().toURL().toString(),400,400,true, true, true
		//	icon = new Image//
//System.out.println(flows.visibleCells());


				    	//}
//				    	catch(ArrayIndexOutOfBoundsException man) {
//
//				    	}
//
//						return null;
//				    }
//
//		        //
//
//
//		 };
//		 imageSetter.execute(consumerTask);


//
//
//				 }
//
//
//			 });
		//	Platform.runLater(() -> {


		//	 });







		}

		@Override
		public Node getNode() {
			/*if(Vpaen23 == true ) {
				Vpaen23 = false;
				return getGridder();
	    		//Vpaen23 = true ;
			}*/

			return
			//	Cell filer=new Cell();
				//mangro.

				grid
				; };

		@Override
		public boolean isReusable() { return false; }



	}
    //Used to store value for , Current Playing Size
    int mok=0;
    class AudioParserCell2 implements Cell{

	    AudioParser audiop;

	 	GridPane grid = new GridPane();
	 	ImageView icon = new ImageView();
	 	BufferedImage img= null;
	 	Mp3File song = null;
		HBox iconAndName = new HBox();
		Rectangle rect = new Rectangle();
		{


			grid.setMinSize(200,200);
			grid.setMaxSize(200,200);
			grid.setPadding(new Insets(0,9,0,9));
			//icon.setPreserveRatio(true);


	        //icon.setFitHeight(190);
	    	// icon.setFitWidth(190);

	    //    icon.setEffect(new DropShadow());
			icon.setPreserveRatio(true);
			 icon.setFitHeight(190);
			//icon.setFitHeight(190);
	        icon.setCache(false);
	        rect.setArcWidth(14);
  		    rect.setArcHeight(14);
  		    rect.widthProperty().set(190);
			rect.heightProperty().set(190);
  		    //rect.setWidth(190);
			//rect.setHeight(190);
	        //icon.setCacheHint(CacheHint.SPEED);
	        iconAndName.setAlignment(Pos.CENTER);
	        iconAndName.getChildren().add(icon);
	        iconAndName.setMaxHeight(190);
	        iconAndName.setMinHeight(190);
	        iconAndName.setMaxWidth(190);
	        iconAndName.setMinWidth(190);
	       // iconAndName.w


	        iconAndName.setClip(rect);
	        grid.add(iconAndName, 0, 0);

		}

//HBox.setMargin(Node, Insets)
	public AudioParserCell2(AudioParser audioparser , int posnum) { //,String artist,String album) {
		this.audiop=audioparser;


	    if(posnum==0) {
			  Platform.runLater(() -> {
				 // grid.setStyle("-fx-background-color: #39FF9F;");
				 // grid.setStyle(" -fx-background-color: #39FF9F");
				    grid.setPadding(new Insets(0, 9, 0, 27));
					//grid.setMinWidth(400);
				//	iconAndName.setAlignment(Pos.CENTER_LEFT);
			      });
		}
	    else if(posnum==filteredData.size()-1) {
			  Platform.runLater(() -> {
				 // grid.setStyle("-fx-background-color: #39FF9F;");
				 // grid.setStyle(" -fx-background-color: #39FF9F");
				    grid.setPadding(new Insets(0, 27, 0, 9));
					//grid.setMinWidth(400);
				//	iconAndName.setAlignment(Pos.CENTER_LEFT);
			      });
		}
	    updateItem(audioparser);
	    //current




		}
	public void updateItem( AudioParser parser )
	{


		 Task<Void> consumerTask = new Task<Void>() {


			    @Override

			    protected Void call() throws Exception {




					try {
						song = new Mp3File(parser.fileLocationGet());
					} catch (UnsupportedTagException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvalidDataException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if (song.hasId3v2Tag()){
					     ID3v2 id3v2tag = song.getId3v2Tag();
					     byte[] imageData = id3v2tag.getAlbumImage();
					     if(imageData!=null) {
					     //converting the bytes to an image
					     try {
							img = ImageIO.read(new ByteArrayInputStream(imageData));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					     Image img1 = SwingFXUtils.toFXImage(img,null);
					     if(img1.getHeight()>img1.getWidth()) {
					    		// icon.setFitHeight(190);
					    	// img1.
					 	        icon.setFitWidth(190);
					 	      // icon.setImage(img1);

					    	 }
//					     else if(img1.getHeight()<img1.getWidth()) {
//
//
//
//
//					    	 }

					     Platform.runLater(() -> {

					    	 icon.setImage(img1);



						     FadeTransition ft = new FadeTransition();
						      ft.setNode(icon);
						      ft.setDuration(new Duration(125));
						      ft.setFromValue(0.0);
						      ft.setToValue(1.0);
						      ft.setAutoReverse(false);
						      ft.play();
//						      if(img1.isError()) {
//
//						    	  icon.setImage(img1);
//								      ft.setNode(icon);
//
//								      ft.play();
//						      }
					        });



					}else if (parser.ImageGet().contains("view")){

						 Platform.runLater(() -> {
					    	 icon.setImage(new Image(new File(parser.ImageGet()).toURI().toString(),190 ,190,false ,false ,true));



						 	       // icon.setFitWidth(190);

					    	 FadeTransition ft = new FadeTransition();
						      ft.setNode(icon);
						      ft.setDuration(new Duration(125));
						      ft.setFromValue(0.0);
						      ft.setToValue(1.0);
						      ft.setAutoReverse(false);
						      ft.play();

					        });
					}else {//image.setImage("Image/A5.png");
						 Platform.runLater(() -> {
						icon.setImage(new Image("application/Image/A5.png",190,190,true,true,true));
						 });

					}

					}
					return null;
			    }

	        //


	 };
	 pooly.execute(consumerTask);

	}
	@Override
	public Node getNode() {return
		//	Cell filer=new Cell();
			grid;
	};

	@Override
	public boolean isReusable() { return false; }



}

   boolean running=false;




VirtualFlow<AudioParser, Cell<AudioParser, ?>> flows;// = VirtualFlow.createVertical(filteredData, audioparser ->new AudioParserCell(audioparser) );
VirtualFlow<AudioParser, Cell<AudioParser, ?>> flowsy;





//VirtualFlow<Albums, Cell<Albums, ?>> figurine=VirtualFlow.createVertical(albums, album ->new GridCellr(album) );
GridView<Artists> gridView2 =new GridView<>();
//GridView<Albums> gridView = new GridView<>();





   /* VirtualFlow<AudioParser, Cell<AudioParser, ?>> flow = VirtualFlow.createVertical(filteredData, audioparser -> {
        // make whatever node you want : have to be all the same size otherwise going have problems


    	GridPane grid = new GridPane();
    	grid.setId("grid");
    	//grid.setStyle("-fx-background-color: black;");
    	grid.setPadding(new Insets(5));
    	grid.setCache(true);
    	grid.setCacheHint(CacheHint.SPEED);
    	grid.setMaxHeight(49);
        grid.setHgap(10);
        HBox iconAndName = new HBox(2);
        Label SonfName=new Label(audioparser.nameGet()+ "\r\n"+audioparser.albumGet());
        SonfName.setStyle("-fx-text-fill: white;");
        ImageView icon = new ImageView();//new Image(new File("application/Image/A5.png").toURI().toString(),true));
        icon.setFitHeight(40);
        icon.setFitWidth(40);
       //icon.setPreserveRatio(true);
        icon.setCache(true);
        icon.setCacheHint(CacheHint.SPEED);
        executor.submit(() -> icon.setImage(new Image(new File(audioparser.ImageGet()).toURI().toString(),true)));
        iconAndName.setAlignment(Pos.CENTER_LEFT);
        iconAndName.getChildren().add(icon);
        grid.add(iconAndName, 0, 0);
        grid.add(SonfName, 2, 0);
       // System.out.println("flow");
//        setOnMouseClicked(event -> {
//        	if(!isEmpty())
//        	System.out.println(audioparser.fileLocationGet());
//        	System.out.println("Hello");
//
//        });
        //grid.setOnMouseClicked(handlemouseClickPane(String.valueOf(audioparser.nameGet())));

        // Wrap the node with Cell



        return Cell.wrapNode(grid);
        	//Cell.isReusable(true);
    });
*/

    @FXML
    public VirtualizedScrollPane<VirtualFlow> listScrollPane;//=new VirtualizedScrollPane<> ();

 //   @FXML
 //   public VirtualizedScrollPane<VirtualFlow> SongsView;

   public VirtualFlowHit<Cell<AudioParser,?>> hit ;


    private void load()  {
       // AudioParser po = new AudioParser();

        searchbar.addEventFilter(KeyEvent.KEY_PRESSED,event->{


    		System.out.println("Pressed: "+event.getCode().toString()+" ");
    		if(event.getCode().toString().equalsIgnoreCase("SHIFT")||event.getCode().toString().equalsIgnoreCase("ENTER"))
    		{

    			switch(choicebox.getValue()) {
    			//"Song Name","Artist Name","Album Name");
    			case "Song Name":
    				filteredData.setPredicate(songs -> {
    	            	//songs.
    	                // If all filters are empty then display all songs
    	                if ((searchbar.getText() == null || searchbar.getText().isEmpty()) ) {
    	                	//setterforSonfgs();
    	                    return true;
    	                }


    	                //If fails any given criteria, fail completely
    	                if(searchbar.getText().length()>0)
    	                    if (songs.nameGet().toLowerCase().contains(searchbar.getText().toLowerCase()) == false/*||songs.artistGet().toLowerCase().contains(lowerCaseFilter) == false*/)
    	                        return false;


    	                return true; // Matches given criteria
    	            });
    				break;
    			case "Album Name":
    				filteredData.setPredicate(songs -> {
    	            	//songs.
    	                // If all filters are empty then display all songs
    	                if ((searchbar.getText() == null || searchbar.getText().isEmpty()) ) {
    	                	//setterforSonfgs();
    	                    return true;
    	                }


    	                //If fails any given criteria, fail completely
    	                if(searchbar.getText().length()>0)
    	                    if (songs.albumGet().toLowerCase().contains(searchbar.getText().toLowerCase()) == false/*||songs.artistGet().toLowerCase().contains(lowerCaseFilter) == false*/)
    	                        return false;


    	                return true; // Matches given criteria
    	            });
    				break;
    			case "Artist Name":
    				filteredData.setPredicate(songs -> {
    	            	//songs.
    	                // If all filters are empty then display all songs
    	                if ((searchbar.getText() == null || searchbar.getText().isEmpty()) ) {

    	                    return true;
    	                }



    	                //If fails any given criteria, fail completely
    	                if(searchbar.getText().length()>0)
    	                    if (songs.artistGet().toLowerCase().contains(searchbar.getText().toLowerCase()) == false/*||songs.artistGet().toLowerCase().contains(lowerCaseFilter) == false*/)
    	                        return false;


    	                return true; // Matches given criteria
    	            });
    				break;
    			}

    	            mok=filteredData.size()-1;


    		}

        });

	//	}

	}
    private void filter(FilteredList<AudioParser> filteredData)
    {

//		Service<Void> service = new Service<Void>() {
//			 @Override
//		        protected Task<Void> createTask() {
//		            return new Task<Void>() {
//
//		                @Override
//					protected Void call() {
//	                    //Background work


            filteredData.setPredicate(songs -> {
            	//songs.
                // If all filters are empty then display all songs
                if ((searchbar.getText() == null || searchbar.getText().isEmpty()) ) {
                	//setterforSonfgs();
                    return true;
                }

                // Convert filters to lower case
                String lowerCaseFilter = searchbar.getText().toLowerCase();


                //If fails any given criteria, fail completely
                if(searchbar.getText().length()>0)
                    if (songs.nameGet().toLowerCase().contains(lowerCaseFilter) == false/*||songs.artistGet().toLowerCase().contains(lowerCaseFilter) == false*/)
                        return false;


                return true; // Matches given criteria
            });
//        	return null;
//						};};
//			 };
//			// createTask
//			//execute.submit(createTask);
//			};
//			service.start();
            //trackListView.setItems(filteredData);
    }





	@FXML
	public void actionPerformed (MouseEvent arg0) {
		if(mp!=null&&mp.getStatus()==Status.PLAYING) {
		mp.pause();}
		pool.shutdownNow();
		pooly.shutdownNow();

		//pool.
		Main.stage.hide();





		if (current!=null) {
			try(

				    PrintWriter out4 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/state.txt", false))))
				{

				out4.println(Chosen1+"~"+current);


				//out4.close();
				} catch (IOException e) {
				    //exception handling left as an exercise for the reader
				}

		}else{

			try(

				    PrintWriter out4 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/state.txt", false))))
				{

				out4.println(Chosen1+"~"+"null");


				out4.close();
				} catch (IOException e) {
				    //exception handling left as an exercise for the reader
				}

		}
		try(
				//FileWriter fw = new FileWriter("mydb.txt", true);
			    //BufferedWriter bw = new BufferedWriter(new FileWriter("mydb.txt", true));
			    PrintWriter out4 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/dex.txt", false))))
			{

			out4.println(listViewDex+"~"+position);


			//out4.close();
		} catch (IOException e) {
			    //exception handling left as an exercise for the reader
			}




		  System.exit(0);

		// start(Main.stage);

		 };
	@FXML
	public void minimze() {
//		  minimize.setOnAction(e -> {
//			    ((Stage)((Button)e.getSource()).getScene().getWindow()).setIconified(true);
//			});

		//systemTray.show();
	}

//	@FXML
//	public void search() {
//
//
//		searchButton.setOnAction(e -> {
//			String character_search=searchbar.getText();
//			if(character_search==null||character_search==" "||character_search==""||character_search=="  ")
//			{
//				load();
//
//			}
//			});
//		//systemTray.show();
//	}

//	 @SuppressWarnings("rawtypes")
//	@FXML
//	 VirtualizedScrollPane<VirtualFlow> listScrollPane;
//
//	 listScrollPane = new VirtualizedScrollPane<>(listView);
//

    @FXML
    private Text labelartist;

    @FXML
    private Text labelsongname;

    @FXML
    private Text songKhz;

    @FXML
    private Text songChannel;


    @FXML
    private Label labelduration;

    @FXML
    private GridPane gridPane;

    Duration currentTime = null;
    Duration steppedTime = null;
    String theTime= null ;

    InvalidationListener mpTime=(o->{
    	duration = mp.getTotalDuration();
		currentTime = mp.currentTimeProperty().getValue();
		 theTime = formatTime(currentTime, duration);
    	 Platform.runLater(() -> {



				 playTime.setText(theTime);
				 timeSlider.setValue((mp.getCurrentTime().toMillis()/ mp.getTotalDuration().toMillis())*100);



		});


});
 /*   InvalidationListener upDateSlider=(o->{

   	 Platform.runLater(() -> {



   		timeSlider.setValue((mp.getCurrentTime().toMillis()/ mp.getTotalDuration().toMillis())*100);

		});


});*/

    double volumeGuy = 0.0;
    BufferedWriter outer=null;
    Boolean repeatIson=false;
    Number oldValue1=0,newValue1=0;
	// Here we our play function depending on what status we act accordingly,Mostly Not Used
	//@FXML
	class play{
		boolean somethingInCan = false;


		{



			timeSlider.valueProperty().addListener(new InvalidationListener() {
				@Override
				public void invalidated(Observable ov) {
//					if (!timeSlider.isValueChanging()) {
////						// multiply duration by percentage calculated by slider
////						// position
//						ov.addListener(timchanging);
//					//mp.seek(duration.multiply(timeSlider.getValue() / 100.0));
//					}
					if (timeSlider.isValueChanging()) {
						mp.seek(duration.multiply(timeSlider.getValue() / 100.0));

						//ov.removeListener(timchanging);
					}

//					if (duration.multiply(timeSlider.getValue() / 100.0)!=mp.currentTimeProperty().getValue()) {
//						// multiply duration by percentage calculated by slider
//						// position
//					mp.seek(duration.multiply(timeSlider.getValue() / 100.0));
		//			}
					//Duration seekto = Duration.seconds(timeSlider.getValue());
					// mp.seek(duration.multiply(timeSlider.getValue() / 100.0));

				}
			});
			timeSlider.valueProperty().addListener(new InvalidationListener() {
				@Override
				public void invalidated(Observable ov) {
//					if (!timeSlider.isValueChanging()) {
////						// multiply duration by percentage calculated by slider
////						// position
//						ov.addListener(timchanging);
//					//mp.seek(duration.multiply(timeSlider.getValue() / 100.0));
//					}
					if (timeSlider.isValueChanging()) {
						mp.seek(duration.multiply(timeSlider.getValue() / 100.0));

						//ov.removeListener(timchanging);
					}

//					if (duration.multiply(timeSlider.getValue() / 100.0)!=mp.currentTimeProperty().getValue()) {
//						// multiply duration by percentage calculated by slider
//						// position
//					mp.seek(duration.multiply(timeSlider.getValue() / 100.0));
		//			}
					//Duration seekto = Duration.seconds(timeSlider.getValue());
					// mp.seek(duration.multiply(timeSlider.getValue() / 100.0));

				}
			});

//			timeSlider.valueProperty().addListener((observable, oldValue, newValue) ->
//			{
//			oldValue1=oldValue;
//			newValue1=newValue;
//
//			}
//		);
			timeSlider.setOnMouseClicked(f->{
				//		mp.currentTimeProperty().removeListener(upDateSlider);
			    		System.out.println("Clicked :"+timeSlider.getValue());
						//mp.currentTimeProperty().removeListener(upDateSlider);
			    		// mp.seek(duration.multiply(newValue1.doubleValue()/100.0));
			    		// mp.currentTimeProperty().addListener(upDateSlider);
					});

			if (volumeSlider != null) {
				//volumeSlider.setValue((int) Math.round(mp.getVolume() * 100));
				//volumeSlider.setValue(volumeGuy);
				volumeSlider.valueProperty().addListener(new InvalidationListener() {
					public void invalidated(Observable ov) {
						if (volumeSlider.isValueChanging()) {
							//volumeSlider.chan
							mp.setVolume(volumeSlider.getValue() / 100.0);


						}
						try {
							 outer = new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/volume.txt", false));
							outer.write(""+volumeSlider.getValue());
							//System.out.println(volumeSlider.getValue());
							outer.close();
							}catch(IOException j) {


						}
					}
				});
			}

		}
		public void playPause() {

		status = mp.getStatus();


		if (status == Status.UNKNOWN || status == Status.HALTED) {
			// don't do anything in these states
			return;
		}

		if (status == Status.PAUSED || status == Status.READY || status == Status.STOPPED || mp.getRate() == 2.0) {

			mp.play();


		} else {
			mp.pause();
		}





		}
		public play() {
			status = mp.getStatus();


			if (status == Status.UNKNOWN || status == Status.HALTED) {
				// don't do anything in these states
				return;
			}

			if (status == Status.PAUSED || status == Status.READY || status == Status.STOPPED || mp.getRate() == 2.0) {

				mp.play();


			} else {
				mp.pause();
			}





			}
		public play(String song,Boolean eng) {

			somethingInCan = true;

			 System.gc();
		     Thread Trackmaster =  new Thread(()-> {

				 if(new File(song).getName().endsWith(".mp3")||new File(song).getName().endsWith(".m4a")) {


                mo = new Media(new File(song).toURI().toString());

                try {
    			if(somethingInCan) {

    				mp.pause();
    			    mp.dispose();
    			    System.gc();

    		}

                }catch(NullPointerException e) {
    			System.out.println(".");

    		}

				mp = new MediaPlayer(mo);
				mp.setOnEndOfMedia(new Runnable() {
				    @Override
				    public void run() {
				    	if(repeatIson==true) {
				    		mp.seek(Duration.ZERO);
				    		mp.setRate(1.0);

				    		//mp.play();
							playB.setImage(new ImageView("application/Image/pause.png").getImage());
				    	}else if(repeatList==true){
				    		//System.out.println("Basic2");
				    		nextone();
					    	playB.setImage(new ImageView("application/Image/pause.png").getImage());



				    	}else if(playListToEnd==true){
				    		if(listViewDex+1==playing.size()) {
				    			mp.seek(Duration.ZERO);
				    		}else {
				    			nextone();
						    	playB.setImage(new ImageView("application/Image/pause.png").getImage());
				    		}

				    	}else {
				    		//System.out.println("Basic");
				    		mp.seek(Duration.ZERO);
				    //	nextone();
				    //	playB.setImage(new ImageView("application/Image/pause.png").getImage());

						//mp.play();
				    	}
				    }
				});
				mp.currentTimeProperty().addListener(mpTime);
				mp.setOnPlaying(new Runnable() {
					@Override
					public void run() {
						if (stopRequested) {
							mp.pause();
							stopRequested = false;
						} else {
							playB.setImage(new ImageView("application/Image/pause.png").getImage());


						}
					}
				});

				mp.setOnPaused(new Runnable() {
					@Override
					public void run() {
						System.out.println("Paused");
						playB.setImage(new ImageView("application/Image/Play.png").getImage());
						//play.setStyle("Image/playbuttoninstance.png");
						//play.setId("Image/playbuttoninstance.png");
					}
				});

				if (mv != null)   {
					mv.setMediaPlayer(mp);
					duration = mp.getTotalDuration();
					mp.setVolume(volumeSlider.getValue() / 100.0);
					if(eng==true) {

						mp.play();

					}


				}
								  }

					});
		     Trackmaster.setName("TrackMaster");
		  //   Trackmaster.setPriority(10);
		     Trackmaster.start();

					try(
							//FileWriter fw = new FileWriter("mydb.txt", true);
						    //BufferedWriter bw = new BufferedWriter(new FileWriter("mydb.txt", true));
						    PrintWriter out4 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/state.txt", false))))

						{

						out4.println(Chosen1+"~"+current);
//						PrintWriter out5 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/dex.txt", false)));
//						out5.println(listViewDex);
//						out5.close();
						//out4.close();
					} catch (IOException e) {
						    //exception handling left as an exercise for the reader
						}
				/*	try{
					    PrintWriter out5 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/dex.txt", false)));
						out5.println(listViewDex);
						out5.close();
						//out4.close();
					} catch (IOException j) {


					}*/
					try(
							//FileWriter fw = new FileWriter("mydb.txt", true);
						    //BufferedWriter bw = new BufferedWriter(new FileWriter("mydb.txt", true));
						    PrintWriter out4 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/dex.txt", false))))
						{

						out4.println(listViewDex+"~"+position);


						//out4.close();
					} catch (IOException e) {
						    //exception handling left as an exercise for the reader
						}



			//trackMaster(song);

					}



	}

//Play FUntion Called When Clicked On Something In Listview



public void replacePlay()
{

	if(mp!=null) {
		play log=new play();
		//log.playPause();
	}
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@FXML
	public void forward1(){
		nextone();



	}
	@FXML
	public void backward1(MouseEvent arg01){



}
///////////////////////////////SORTERS/////////////////////////////////////////////////////////////////////////////////////////////////////////
	@FXML
	public void album(){
		Chosen1=1;
		//try {

		System.out.println("Sort by Album or Display All Albums");
		Collections.sort(list, comparatorByArtist);
		if(refreshList.size()!=0)
		{
			Collections.sort(refreshList, comparatorByArtist);


		}
		try(
				//FileWriter fw = new FileWriter("mydb.txt", true);
			    //BufferedWriter bw = new BufferedWriter(new FileWriter("mydb.txt", true));
			    PrintWriter out4 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/state.txt", false))))
			{

			out4.println(Chosen1+"~"+current);


			out4.close();
			} catch (IOException e) {
			    //exception handling left as an exercise for the reader
			}
		try(
				//FileWriter fw = new FileWriter("mydb.txt", true);
			    //BufferedWriter bw = new BufferedWriter(new FileWriter("mydb.txt", true));
			    PrintWriter out4 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/dex.txt", false))))
			{

			out4.println(listViewDex);


			//out4.close();
		} catch (IOException e) {
			    //exception handling left as an exercise for the reader
			}

	//	Albumadder();
	}


	int reverseOrder = 0;
	@FXML
	public void songs(){
		Chosen1=0;
		System.out.println("sort by songs or display all songs");
		Collections.sort(list, comparatorMyObject_byDay);
	//	refreshList
		if(refreshList.size()!=0)
		{
			Collections.sort(refreshList, comparatorMyObject_byDay);


		}		try(
				//FileWriter fw = new FileWriter("mydb.txt", true);
			    //BufferedWriter bw = new BufferedWriter(new FileWriter("mydb.txt", true));
			    PrintWriter out4 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/state.txt", false))))
			{

			out4.println(Chosen1+"~"+current+"~"+reverseOrder);


			out4.close();
			} catch (IOException e) {
			    //exception handling left as an exercise for the reader
			}
		try(
				//FileWriter fw = new FileWriter("mydb.txt", true);
			    //BufferedWriter bw = new BufferedWriter(new FileWriter("mydb.txt", true));
			    PrintWriter out4 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/dex.txt", false))))
			{

			out4.println(listViewDex);


			//out4.close();
		} catch (IOException e) {
			    //exception handling left as an exercise for the reader
			}

	}
	@FXML
	public void artist(){
	/*	if(Chosen1==0) {
			Collections.sort(list, comparatorMyObject_byDay);}
		//by name
		else if(Chosen1==1) {
			Collections.sort(list, comparatorByArtist);}
		//by album

		else if(Chosen1==2) {
			Collections.sort(list, comparatorBynamey );}
		//by aritst
		else if(Chosen1==3) {
			Collections.sort(list, comparatorbyDate );}
		//by date*/
		System.out.println("Sort By Artist");
		Chosen1=2;
		Collections.sort(list, comparatorBynamey);
		if(refreshList.size()!=0)
		{
			Collections.sort(list, comparatorBynamey);


		}		try(
			    PrintWriter out4 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/state.txt", false))))
			{

			out4.println(Chosen1+"~"+current+"~"+reverseOrder);


			out4.close();
			} catch (IOException e) {
			    //exception handling left as an exercise for the reader
			}
		try(

			    PrintWriter out4 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/dex.txt", false))))
			{

			out4.println(listViewDex);


			//out4.close();
		} catch (IOException e) {
			    //exception handling left as an exercise for the reader
			}

	}

	public void dated(){

		Chosen1=3;
		System.out.println("Sort By Date");
		if(reverseOrder==1) {

			list.sort(comparatorbyDate.reversed());
			reverseOrder = 0;

		}else{

		Collections.sort(list, comparatorbyDate);

			if(refreshList.size()!=0){Collections.sort(refreshList, comparatorbyDate);}

			reverseOrder = 1;
		try(
			    PrintWriter out4 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/state.txt", false))))
			{

			out4.println(3+"~"+current+"~"+reverseOrder);


			out4.close();
			} catch (IOException e) {
			    //exception handling left as an exercise for the reader
			}
		try(

			    PrintWriter out4 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/dex.txt", false))))
			{

			out4.println(listViewDex);


			//out4.close();
		} catch (IOException e) {
			    //exception handling left as an exercise for the reader
			}}

	}
	String filter=null;
	PopOver List=new PopOver();

/////////////////////////////////
	@FXML
	public void genreE(){


	}


	public static void deleteFolder(File folder) {
	    File[] files = folder.listFiles();
	    if(files!=null) { //some JVMs return null for empty dirs
	        for(File f: files) {
	            if(f.isDirectory()) {
	                deleteFolder(f);
	            } else {
	                f.delete();
	            }
	        }
	    }
	}


	@FXML
	public void rest() {


		Service<Void> service = new Service<Void>() {
			 @Override
		        protected Task<Void> createTask() {
		            return new Task<Void>() {

		                @Override
					protected Void call() {
	                    //Background work
		System.out.println("Started Clearing");
		//Array jack;
		ArrayList<String> jack = new ArrayList<String>();
		jack.add(System.getProperty("user.home")+"\\ilix\\songs.txt");
		jack.add(System.getProperty("user.home")+"/ilix/mydb.txt");
		jack.add(System.getProperty("user.home")+"/ilix/totalSongs.txt");
		jack.add(System.getProperty("user.home")+"/ilix/folders.txt");

		jack.forEach(mans->{
			File temp = new File(mans);
			if (temp.exists()) {
			   // @SuppressWarnings("resource");
			    try {
				RandomAccessFile raf = new RandomAccessFile(temp, "rw");

			    raf.setLength(0);
			    raf.close();
			    } catch(IOException fj) {
			    	System.err.print("File Deleting Error");

			    }
			}
             		});

		/*File temp = new File(System.getProperty("user.home")+"\\ilix\\songs.txt");
		if (temp.exists()) {
		   // @SuppressWarnings("resource");
		    try {
			RandomAccessFile raf = new RandomAccessFile(temp, "rw");

		    raf.setLength(0);
		    raf.close();
		    } catch(IOException fj) {


		    }
		}
		File temp1 = new File(System.getProperty("user.home")+"/ilix/mydb.txt");
		if (temp1.exists()) {
		//    @SuppressWarnings("resource")
 try {

			RandomAccessFile raf = new RandomAccessFile(temp1, "rw");
		    raf.setLength(0);
		    raf.close();
 }catch(IOException fj) {


 }
		}

		File temp2 = new File(System.getProperty("user.home")+"/ilix/totalSongs.txt");
		if (temp2.exists()) {
		   // @SuppressWarnings("resource")
			 try {
			RandomAccessFile raf = new RandomAccessFile(temp2, "rw");
		    raf.setLength(0);
		    raf.close();
		}catch(IOException fj) {


	    }
		}

		File temp3 = new File(System.getProperty("user.home")+"/ilix/folders.txt");
		if (temp3.exists()) {
		   // @SuppressWarnings("resource")
			 try {
			RandomAccessFile raf = new RandomAccessFile(temp3, "rw");
		    raf.setLength(0);
		    raf.close();
		}catch(IOException fj) {


	    }
		}*/

		deleteFolder(new File(System.getProperty("user.home")+"\\ilix\\cache-images"));



		return null;
						};};
			 };
			// createTask
			//execute.submit(createTask);
			};
			service.setOnSucceeded(event->{
				try {
				ReturnNumbers("Refresh",0);}
				catch(IOException mj) {

				}
				list.clear();
				refreshList.clear();
				filteredData.clear();
				//mp=null;
				System.out.println("Done Clearing");
			});
			service.start();

		//trackListView.setItems(list);
	//	trackListView.refresh();


	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	String path=null;
	public PopOver popover=null;



	//return 1;


	//@FXML
	public void addNewTracks(ActionEvent event) throws IOException {
		String path=null;
		//BringItON2();

		System.out.println("clicked on  AddTrack");
		FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File f, String name) {
                // We want to find only ,mp3 files
                return name.endsWith(".mp3");
            }};
		//directory chooser so user can select folder
		FileChooser directoryChooser = new FileChooser();
		directoryChooser.setTitle("Select Song/s Folder");
	//	directoryChooser.setInitialDirectory(new File("C:\\Users\\blank\\Music"));
//		directoryChooser.setMultiSelectionEnabled(true);
//		directoryChooser.setFileSelectionMode(DirectoryChooser.DIRECTORIES);
		List<File> list=directoryChooser.showOpenMultipleDialog(Main.stage);
		if (list == null) {
			  System.out.println("Exited,List Is sempty");
			 // path = (new File("C:\\Users\\blank\\Music")).getAbsolutePath();

            //  check(new File(path));
			}
			else {

			 for(File file: list) {
				 addsongs(file);
			 }
			 //extract(file);



			}
	//return 1;

	}



	ArrayList<String> tempor = new ArrayList<String>();

	Mp3File songsong=null;
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	int workhorse=0;
    public void Song(String s) {



     //while(s!=null) {

    	try {
    		songsong = new Mp3File(s) ;

		} catch(InvalidPathException e4){

			//System.out.println("InvalidPathException");
			System.err.println("This should never happen: " + e4);
			//continue;

			//continue;

		}catch (UnsupportedTagException e1) {
			// TODO Auto-generated catch block
		//	e1.printStackTrace();
			//break;

		} catch (InvalidDataException e1) {
			// TODO Auto-generated catch block
//			song = null;
//			System.out.println("Set To Null");
			System.out.println("InvalidDataException "+pathFInder);
			//e1.printStackTrace();
			//break;
			//e1.printStackTrace();


		} catch (IOException e1) {
			// TODO Auto-generated catch block
//			song = null;
//			System.out.println("Set To Null");
			//e1.printStackTrace();
			//break;

			System.out.println("IOException");
			//continue;
		}


		if (!songsong.hasId3v2Tag()&&songsong!=null){
			songsong = null;
			System.out.println("Set To Null");


		}
		else if (songsong.hasId3v2Tag()){



		     ID3v2 id3v2tag = songsong.getId3v2Tag();


		     Platform.runLater(()->{


					songKhz.setText(String.valueOf(songsong.getBitrate()+" Kbps"));
					songChannel.setText(String.valueOf(songsong.getChannelMode()));

					if(id3v2tag.getAlbum()==null) {
						album.setText("Untitled");

					}else {
						album.setText(id3v2tag.getAlbum());
					}

					if(id3v2tag.getArtist()==null) {
						artist.setText("Untitled");

					}else {
						artist.setText(id3v2tag.getArtist());
					}

					if(id3v2tag.getTitle()==null) {
						title.setText(new File(s).getName());

					}else {
					title.setText(id3v2tag.getTitle());
					}
			        });


		     album.setOnMouseClicked(dg->{
		    	 if(dg.getClickCount()>=2) {
		    		 listScrollPane=null;
		    	    lazyLoad.clear();
		    	    tempor.clear();
                 filteredData=new FilteredList<>(lazyLoad,g->true);
              //  System.out.println(fag.getText());
             		list.forEach(mans->{
             			if(id3v2tag.getAlbum().equalsIgnoreCase(mans.albumGet())) {

             				 lazyLoad.add(mans);
             				 if(!tempor.contains(mans.artistGet())) {
             					if(workhorse==0) {
             				tempor.add(mans.artistGet());}else {

             					tempor.add("/" +mans.artistGet());
             				}
             				 }
             				 workhorse++;
             			}
             		});
//             		filteredData.clear();
//             		filteredData = new FilteredList<>(lazyLoad, t -> true);
             		Vpane2(filteredData,workhorse);
             		workhorse=0;
             	  //  gridView.setItems(null);
             	    System.gc();
		    	 }
		     });


        }}

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// updates the values for the volume, timeSlider and the current duration. Based On Song Being Played Updated EveryTime
	protected void updateValues() {
		if (playTime != null && timeSlider != null && volumeSlider != null) {
			duration = mp.getTotalDuration();
			currentTime = mp.currentTimeProperty().getValue();
			 theTime = formatTime(currentTime, duration);
			 playTime.setText(theTime);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {



					if (!volumeSlider.isValueChanging()) {
						volumeSlider.setValue((int) Math.round(mp.getVolume() * 100));
					}


				}
				///


				///
			});


		/*	timeSlider.setOnMouseClicked(f->{


			    //exception handling left as an exercise for the reader
            	// timeSlider.setValue(newValue.intValue());
				//steppedTime=
				//timeSlider.setValue(timeSlider.getValue());
//				Duration mangro=duration/duration;
//		         mp.seek(mangro.multiply (timeSlider.getValue()/ 100.0)*duration.toMillis());
//		         System.out.println(timeSlider.getValue());

		//flows = VirtualFlow.createVertical(filteredData, audioparser ->new AudioParserCell(audioparser) );
	});*/
		}
	}


	int finish=0;
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void addsongs(File songA) {
		boolean joke=false;
    	BufferedImage img= null;
		WritableImage wr = null;
		Random rand = new Random();
		String string = null,nowNow = null,pathFInder,arte,album = null;
		Mp3File song = null;
		File x=songA;
		 if(x.getName().endsWith(".mp3")){
				pathFInder=x.getAbsolutePath();
             //trackListView.getItems().add(x);
			    //list.add(x);
			//	saveFolder(x.getAbsolutePath());

				if(Folders.contains(pathFInder)){
					System.out.println("This One Has Already Been Extracted.");
					//System.out.println(x.getAbsolutePath());
					//System.out.println("Must Song Folder , already Extracted!!");
					//break;
				}else{
					counter++;
					Folders.add(pathFInder);
					try(
							//FileWriter fw = new FileWriter("mydb.txt", true);
						    //BufferedWriter bw = new BufferedWriter(new FileWriter("mydb.txt", true));
						    PrintWriter out4 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"\\ilix\\mydb.txt", true))))
						{
						out4.println(pathFInder);


						out4.close();
						} catch (IOException e) {
						    //exception handling left as an exercise for the reader
						}
					//pathFInder=x.getAbsolutePath();

					int n = rand. nextInt(500000654);







					try {
						song = new Mp3File(pathFInder);
					} catch (UnsupportedTagException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InvalidDataException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (song.hasId3v2Tag()){
					     ID3v2 id3v2tag = song.getId3v2Tag();
					     byte[] imageData = id3v2tag.getAlbumImage();
					     if(imageData!=null) {
					     //converting the bytes to an image
					     try {
							img = ImageIO.read(new ByteArrayInputStream(imageData));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					     @SuppressWarnings("rawtypes")
							Iterator iter = ImageIO.getImageWritersByFormatName("jpeg");
						     ImageWriter writer = (ImageWriter)iter.next();
						  // instantiate an ImageWriteParam object with default compression options
						     ImageWriteParam iwp = writer.getDefaultWriteParam();
						     iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
						     iwp.setCompressionQuality(0.3f);
						     //iwp.setCompressionQuality(0);   // an integer between 0 and 1
						     // 1 specifies minimum compression and maximum quality

						     File fileA = new File(System.getProperty("user.home")+"\\ilix\\cache-images\\"+n+"85.jpeg");
 					        string=fileA.getAbsolutePath();
						     FileImageOutputStream output = null;
							try {
								output = new FileImageOutputStream(fileA);

							} catch (FileNotFoundException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							} catch (IOException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}

						     writer.setOutput(output);
						     IIOImage image = new IIOImage(img, null, null);
						     //ImageIO.setUseCache(false);
						     try {
								writer.write(null, image, iwp);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						     writer.dispose();
						     try {
						    	 output.close();

								} catch (FileNotFoundException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								} catch (IOException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}
						     //fileA.;
					     //convertToFxImage(img);
					   //  Image image2 = img;
					   //  image.setImage(img);}
					}else {//image.setImage("Image/A5.png");
					//break;


					}



//String fileLocation = x.getAbsolutePath();

					   try {

					        InputStream input = new FileInputStream(new File(pathFInder));
					        ContentHandler handler = new DefaultHandler();
					        Metadata metadata = new Metadata();
					        Parser parser = new Mp3Parser();
					        ParseContext parseCtx = new ParseContext();
					        parser.parse(input, handler, metadata, parseCtx);
					        input.close();

					        // List all metadata
					        String[] metadataNames = metadata.names();
					        nowNow=metadata.get("title").toString();
					        album=(metadata.get("xmpDM:album").toString());
					        arte=(metadata.get("xmpDM:album").toString());
					     //   list.add(new AudioParser(nowNow, pathFInder,string,album,arte));
					        //////////////////////////////////////////////////////


					        //System.out.println("Album : "+metadata.get("xmpDM:album"));

					        } catch (FileNotFoundException e) {
					        e.printStackTrace();
					        } catch (IOException e) {
					        e.printStackTrace();
					        } catch (SAXException e) {
					        e.printStackTrace();
					        } catch (TikaException e) {
					        e.printStackTrace();
					        }



					   try(
							   //FileWriter fw = new FileWriter("songs.txt", true);
							  //  BufferedWriter bw = new BufferedWriter(new FileWriter("songs.txt", true));
							    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"\\ilix\\songs.txt", true))))
							{
							    out.println(nowNow+"~"+ pathFInder+"~"+string+"~"+album);
							    //more code

							    out.close();
							    totalSongse++;
							    System.out.println("One Done");
							} catch (IOException e) {
							    //exception handling left as an exercise for the reader
							}



			}


		}

		}
		 else {
			 System.out.println("This Is Not An Mp3 File");

		 }

	}
	boolean joke=false;
	int chckon=0;


	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//scans out mp# files into observable list ,then show them on listview
	@SuppressWarnings({ })
	public void extract(File dir) throws IOException {
		//Platform.runLater(() ->{
		totalSongse=0;

//		rootPane.setVisible(false);
//		rootPane.setDisable(true);
		//});
		//searchbar.setVisible(true);



		File l[] = dir.listFiles();
		//FileWriter writer = new FileWriter("songs.txt");
		Task<Void> backgroundTask = new Task<Void>() {
	        @Override
	        protected Void call() throws Exception {





	                                //FX Stuff done here

	                            	BufferedImage img= null;
            						WritableImage wr = null;
            						Random rand = new Random();
            						String string = null,nowNow = null,pathFInder,album = null;
            						Mp3File song = null;







	                    			if (l == null) {
	                    				System.out.println("[skipped] " + dir);

	                    						   }

	                    			for (File x : l) {
	                    				//System.out.println("..");
	                    				if (x.isDirectory()){


	                    					exService.submit(()->
															{

															try{extract(x);} catch (IOException e) {
																System.out.println("failed To Try catch for the extract");
																e.printStackTrace();
															}
															}
																	);

	                    					////////////////////////////////

	                    				}
	                    				if (/*x.isHidden() || */!x.canRead()){
	                    						continue;
	                    				}
	                    				else if(x.getName().endsWith(".mp3")&&x.length()!=0/*||x.getName().endsWith(".flac")&&x.length()!=0*/)
	                    				{

	                    					pathFInder=x.getAbsolutePath();
	                    					if(!foldertoExtract.contains(pathFInder)) {
	                    					foldertoExtract.add(pathFInder);
	                    				/*	try(
                    								//FileWriter fw = new FileWriter("mydb.txt", true);
	                    						    //BufferedWriter bw = new BufferedWriter(new FileWriter("mydb.txt", true));
	                    							BufferedWriter out4 = new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/toExtract.txt", true)))
	                    						{
	                    						out4.write(pathFInder+"\n");
	                    						System.out.println("oW");

	                    						out4.close();
	                    						} catch (IOException e) {
	                    						}*/
	                    					Writer out1 = new BufferedWriter(new OutputStreamWriter(
                    							    new FileOutputStream(System.getProperty("user.home")+"/ilix/toExtract.txt", true), "UTF-8"));
                    							try {
                    							    out1.write(pathFInder+"\n");
                    							    System.out.println(".");
                    							} finally {
                    							    out1.close();
                    							}
	                    				}
	                    					if(!Folders.contains(x.getParent())){

		                    						//break;
	                    						Folders.add(x.getParent());
	                    						Writer out = new BufferedWriter(new OutputStreamWriter(
	                    							    new FileOutputStream(System.getProperty("user.home")+"/ilix/folders.txt", true), "UTF-8"));
	                    							try {
	                    							    out.write(x.getParent()+"\n");
	                    							    chckon++;
	                    							} finally {
	                    							    out.close();
	                    							}
			                    					/*try(
			                    							//FileWriter fw = new FileWriter("mydb.txt", true);
			                    							//FileWriter john=new FileWriter();
			                    						    BufferedWriter bw = new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/folders.txt", true));)
			                    						  //  PrintWriter out4 = new PrintWriter(bw))
			                    						{

			                    						bw.write(x.getParent()+"\n");
			                    						chckon++;
			                    						//Close Buffered Writer
			                    						//bw.close();
			                    						} catch (IOException e) {
			                    						    //exception handling left as an exercise for the reader
			                    						}*/


		                    					}


	                    				}
	                    			}///////

	                    			///////////
									return null;}



	};
	//executor.execute(task);
	backgroundTask.setOnSucceeded(g->{

		Confirm start = new Confirm();
		start.Confirm();


//		extractFolders();
//		System.gc();

	});
	exService.execute(backgroundTask);
	}
	int countdown = 0;
	int blow=0;
	int started = 0;
	class Confirm {



		PauseTransition wait11 = new PauseTransition(Duration.seconds(20));
		{


		}
		public void Confirm() {
			//	System.out.println(abq.size());
			// countdown =;
			if(started==0) {
				started = 1;
				countdown();
			}else {

				if(countdown == 1) {

				}else {
					//wait11.stop();
					System.out.println("Restarted TimeLine");
					wait11.playFromStart();

				}
			}

		}
		private void countdown() {

			wait11.setOnFinished((e) -> {
				if(countdown ==0){

				countdown = 1;
				System.out.println("CountDown Succeded");
				extractFolders();
				System.gc();
				//wait11.st
				}
			});
			wait11.play();
		}


	}

	File imageCover2= null;
	public boolean coverimageExists(File file) {
		boolean imageCover = false;
	//	String strnger = Paths.get(file.getAbsolutePath()).getParent().getFileName().toString();

		File l[] = new File(file.getParent()).listFiles();
		//System.out.println(file.getParent());
		for(File g:l) {
			//Album Art
			if(g.getName().startsWith("cover")||g.getName().startsWith("Album Art")||g.getName().startsWith("Cover")||g.getName().startsWith("COVER")&&(g.getName().endsWith(".jpeg")||g.getName().endsWith(".jpg")||g.getName().endsWith(".png"))) {
				imageCover2  =g;
				//System.out.println("Got One");
				imageCover = true;
			}
			}

			return imageCover;
		}






//Counter for extract foders
int ii=0;



BufferedImage img= null;
WritableImage wr = null;
Random rand = new Random();
String string = null,nowNow = "",pathFInder,album1 = "",artissty = "",ablumNoo=null,lengthy=null;
Mp3File song =null;
MP3File f=null;
String gag;
AudioFile l ;
Tag tag ;
@FXML
ProgressBar bar=new ProgressBar();
	//@SuppressWarnings({ })
	public synchronized void extractFolders(){
		//Platform.runLater(() ->{



		refresh.setDisable(true);
		addNewTrack.setDisable(true);
//		 Rotate rotate = new Rotate();
//	      //Setting pivot points for the rotation
//	      rotate.setPivotX(300);
//	      rotate.setPivotY(100);
//	      //Adding the transformation to rectangle
//	      refresh.getTransforms().addAll(rotate);

		/*PauseTransition wait11 = new PauseTransition(Duration.millis(10));
		wait11.setOnFinished((e) -> {


			refresh.setRotate(30);
			//irf++;
			//wait11.playFromStart();
		});
		wait11.play();*/

		totalSongse=0;
		counter=0;

		//Charset charset="";


	//	try {
		//.close();
		/*if(flows!=null) {
		flows.setDisable(true);}*/
		int numboid=foldertoExtract.size();
		System.out.println(numboid+" This Is Our Size");
		GridPane manJoe=new GridPane();
 		bar.setPrefWidth(300);
 		bar.setMaxWidth(300);
 		bar.setMinWidth(300);
 		//bar.setPadding(new Insets(300,200,300,200));
 		manJoe.getChildren().add(bar);
		manJoe.setAlignment(Pos.CENTER);

		BPane.setCenter(manJoe);
		/*rootPane.setContent(manJoe);
		rootPane.setFitToHeight(true);
		rootPane.setFitToWidth(true);*/
		//rootPane.setContent(bar);
		//rootPane.(Orientation.);
/*

		//FileWriter writer = new FileWriter("songs.txt");
		Service<Void> service = new Service<Void>() {
			 @Override*/


		         Task<Void> createTask = new Task<Void>() {


		                @Override
					protected Void call() {
	                    //Background work
		                	Scanner read1 =null;
		                	BufferedWriter out=null;
		                	try {

		           			 read1 = new Scanner (new BufferedReader(new FileReader(System.getProperty("user.home")+"/ilix/toExtract.txt"), 16*1024));

		           		//	out4.flush();
		           		} catch (IOException e) {
		           		    //exception handling left as an exercise for the reader
		           		}




		                	Writer outing =null;
		            		FileWriter fw =null;
		            		FileWriter fww = null;
		            		String value=null;
		            		BufferedWriter out4 = null;


	                                //FX Stuff done here

		                	       final int max=numboid;
            						//Path path=Paths.get(System.getProperty("user.home")+"/ilix/toExtract.txt");

            				    	while(read1.hasNextLine())
            				    	{
            				    		//read1.flush();
            				    		counter++;
                						ii++;
            				    		pathFInder= read1.nextLine();



            				    			if(!Folders2.contains(pathFInder)) {


                						try {
                							 fww = new FileWriter(System.getProperty("user.home")+"/ilix/mydb.txt", true);
                							 out4 = new BufferedWriter(fww);
                							 out4.write("\n"+pathFInder);
                							// out4.close();

                    						  //  System.out.println("Got The FIrst Buffer");

                    					//	out4.flush();
        								} catch (IOException e) {
        									e.printStackTrace();
        									continue;
        								    //exception handling left as an exercise for the reader
        								}finally {

        											try {

        	        									out4.close();
        	        									fww.close();
        	                    					//	out4.flush();
        	        								} catch (IOException e) {
        	        									e.printStackTrace();

        	        									continue;
        	        								    //exception handling left as an exercise for the reader
        	        								}
        								}



                						int n = rand. nextInt(504000654);
                						File django  = new File(pathFInder);

                						if(django.getName().endsWith(".mp3")&&pathFInder!=null/*&&new File(pathFInder).length()!=0*/&&django.exists()) {
										try {
											song = new Mp3File(django) ;
											//song = new Mp3File(django)
										} catch(InvalidPathException e4){

											//System.out.println("InvalidPathException");
											System.err.println("This should never happen: " + e4);
											//continue;

											continue;

										}catch (UnsupportedTagException e1) {

											System.out.println("TagException");
											//continue;
											continue;

										} catch (InvalidDataException e1) {

											System.out.println("InvalidDataException "+pathFInder);
										//	continue;
											continue;

										} catch (IOException e1) {

											System.out.println("IOException \\ "+e1);
										//	break;
											continue;
										//	continue;
										}catch(IllegalArgumentException hone){
											System.out.println("IllegalArgumentException\\");
											continue;
										}

											if (!song.hasId3v2Tag()&&song!=null){
												// song = null;
												 Folders2.add(pathFInder);
//												System.out.println("Set To Null");
	//
//	                						    	 nowNow=song.getFilename();
	//
	//
//	                						    	 album1="untitled";
//	                						    	 artissty="unkown";
//	                						    	 ablumNoo="0";
//	                						    	 lengthy="0";
	                						    //	 totalSongse++;

											}
											else if (song.hasId3v2Tag()){


												 Folders2.add(pathFInder);
	                						     ID3v2 id3v2tag = song.getId3v2Tag();
	                						     ID3v1 id3v1tag = song.getId3v1Tag();
//	                						     ID3v1 id3v2tagy= song.getId3v1Tag();
//	                						     id3v2tagy.
	                						     //nowNow="empty";album1="empty";artissty="empty";ablumNoo = "empty";
	                						     nowNow=id3v2tag.getTitle();
	                						     album1=id3v2tag.getAlbum();
	                						     artissty=id3v2tag.getArtist();
	                						     ablumNoo = id3v2tag.getTrack();




	                						     //System.out.print(ablumNoo = id3v2tag.getTrack());
	                						     lengthy = String.valueOf(song.getLengthInMilliseconds());


	                						     lengthy=formatTime2(Integer.parseInt(lengthy)/1000);

	                						     if (nowNow==null||nowNow==""||nowNow==" "||nowNow.isEmpty()) {
	                						    	 nowNow=django.getName().replaceAll(".mp3","");
	                						    	 if (nowNow==null) {

	                						    		 nowNow="Untitled";
	                						    	 }

	                						     }
	                						     if (album1==null||album1.isEmpty()) {
	                						    	 album1="untitled";
	                						     }
	                						     if (artissty==null||artissty.isEmpty()) {
	                						    	 artissty="unkown";
	                						     }
	                						     if (ablumNoo==null||artissty.isEmpty()) {
	                						    	 ablumNoo="0";
	                						     }
	                						     if (lengthy==null||artissty.isEmpty()) {
	                						    	 lengthy="0";
	                						     }
	                						     byte[] imageData = id3v2tag.getAlbumImage();


	                						     if(imageData!=null) {
	                						     //converting the bytes to an image
	                						    	// img=
	                						     try {
													img = ImageIO.read(new ByteArrayInputStream(imageData));
												} catch (IOException e) {

													continue;
												}

	                						     @SuppressWarnings("rawtypes")
													 Iterator iter = ImageIO.getImageWritersByFormatName("jpeg");
	                    						     ImageWriter writer = (ImageWriter)iter.next();
	                    						  // instantiate an ImageWriteParam object with default compression options
	                    						     ImageWriteParam iwp = writer.getDefaultWriteParam();
	                    						     iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	                    						     iwp.setCompressionQuality(0.3f);
	                    						     //iwp.setCompressionQuality(0);   // an integer between 0 and 1
	                    						     // 1 specifies minimum compression and maximum quality

	                    						     File fileA = new File(System.getProperty("user.home")+"/ilix/cache-images/"+n+"85.jpeg");
		                    					        string=fileA.getAbsolutePath();
	                    						     FileImageOutputStream output = null;
													try {
														output = new FileImageOutputStream(fileA);
														writer.setOutput(output);
													} catch (FileNotFoundException e2) {
														// TODO Auto-generated catch block
														e2.printStackTrace();
														continue;
													} catch (IOException e2) {
														// TODO Auto-generated catch block
														e2.printStackTrace();
														continue;
													}finally {
														 if(img!=null) {

														IIOImage image = new IIOImage(img, null, null);
		                    						     try {
															writer.write(null, image, iwp);
														} catch (IOException e1) {
															// TODO Auto-generated catch block
															//e1.printStackTrace();
														}finally {
															writer.dispose();
														}

		                    						     try {
		                    						    	 output.close();
		 												} catch (IOException e1) {
		 													// TODO Auto-generated catch block
		 													System.out.println("Failed To Close Output");
		 													continue;
		 													//e1.printStackTrace();
		 												}}

													}
	                    						     //ImageIO.setUseCache(false);





	                    						     //fileA.;
	                						     //convertToFxImage(img);
	                						   //  Image image2 = img;
	                						   //  image.setImage(img);}
	                						}else if(coverimageExists(django)){





	                						     if(django!=null&&imageCover2!=null) {
	                						     //converting the bytes to an image
	                						    	// img=
	                						     try {
													img = ImageIO.read(imageCover2);
												} catch (IOException e) {
													// TODO Auto-generated catch block
													//e.printStackTrace();
													continue;
												}

	                						     @SuppressWarnings("rawtypes")
													 Iterator iter = ImageIO.getImageWritersByFormatName("jpeg");
	                    						     ImageWriter writer = (ImageWriter)iter.next();
	                    						  // instantiate an ImageWriteParam object with default compression options
	                    						     ImageWriteParam iwp = writer.getDefaultWriteParam();
	                    						     iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	                    						     iwp.setCompressionQuality(1);
	                    						     //iwp.setCompressionQuality(0);   // an integer between 0 and 1
	                    						     // 1 specifies minimum compression and maximum quality

	                    						     File fileA = new File(System.getProperty("user.home")+"/ilix/cache-images/view"+n+"85.jpeg");
		                    					        string=fileA.getAbsolutePath();
	                    						     FileImageOutputStream output = null;
													try {
														output = new FileImageOutputStream(fileA);
														writer.setOutput(output);
													} catch (FileNotFoundException e2) {
														// TODO Auto-generated catch block
														e2.printStackTrace();
														continue;
													} catch (IOException e2) {
														// TODO Auto-generated catch block
														e2.printStackTrace();
														continue;
													}finally {
														 if(img!=null) {

														IIOImage image = new IIOImage(img, null, null);
		                    						     try {
															writer.write(null, image, iwp);
														} catch (IOException e1) {
															// TODO Auto-generated catch block
															//e1.printStackTrace();
															continue;
														}finally {
															writer.dispose();
														}

		                    						     try {
		                    						    	 output.close();
		 												} catch (IOException e1) {
		 													// TODO Auto-generated catch block
		 													System.out.println("Failed To Close Output");
		 													continue;
		 													//e1.printStackTrace();
		 												}}

													}
	                						}}
	                						else{
	                							string=System.getProperty("user.home")+"\\ilix\\A5.png";
	                						}




	                						     long dateModified = django.lastModified();

	                						    // String bna =
	                						    // Date d= new Date(dateModified);
	                						     System.out.println(nowNow+" ## "+album1+" ## "+counter);
	                						     list.add(new AudioParser(nowNow, pathFInder, string, album1,artissty,ablumNoo,lengthy,Long.toString(dateModified)));
	                						   try {


	                							//   fw=new FileWriter(System.getProperty("user.home")+"/ilix/songs.txt", true);
	                			            	 //  out=new BufferedWriter(fw) ;
	                			            	    outing = new BufferedWriter(new OutputStreamWriter(
		                    							    new FileOutputStream(System.getProperty("user.home")+"/ilix/songs.txt", true), "UTF-8"));

		                    					outing.write("\n"+nowNow+"(~)"+ pathFInder+"(~)"+string+"(~)"+album1+"(~)"+artissty+"(~)"+ablumNoo+"(~)"+lengthy+"(~)"+dateModified);

	                						totalSongse++;
	                								    //System.out.println("One Done "+counter);


	                								 //   out.flush();

	                								}
	                									 catch (IOException e) {
	                										 System.err.print( e.getMessage());

	                										 continue;
	                								}finally {

	                									try {
	                									//	out.close();
	                									//	fw.close();
	                										outing.close();
	                										}
	                									catch(IOException fh) {
	                										 System.err.print( fh.getMessage());
	                											//fh.printStackTrace();
	                											continue;
	                										}

	                								}




	                								}

									//	}



                						}///////////////Here The Next IF
                						/*else if(new File(pathFInder).getName().endsWith(".flac")) {///
    										try {
    											l = AudioFileIO.read(new File(pathFInder));
    											tag = l.getTag();


    										} catch(InvalidPathException e4){

    											//System.out.println("InvalidPathException");
    											System.err.println("This should never happen: " + e4);
    											//continue;

    											break;

    										}catch(CannotReadException fb) {

    											break;
    										}catch (InvalidAudioFrameException e1) {

    											break;
    										} catch (ReadOnlyFileException e1) {

    											System.out.println("InvalidDataException "+pathFInder);

    											continue;

    										} catch (IOException e1) {
    											// TODO Auto-generated catch block

    											continue;
    											//continue;
    										}catch(TagException l) {
    											continue;

    										}


    										if (l.getAudioHeader()==null){
    											song = null;
    											System.out.println("Set To Null");


    										}
    										else if (l.getAudioHeader()!=null){

    											FlacTag tagy = (FlacTag)l.getTag();
    											VorbisCommentTag vorbisTag = tagy.getVorbisCommentTag();


                    						    // ID3v2 id3v2tag =null;
                    						     nowNow=tagy.getFirst(FieldKey.TITLE);
                    						     album1=tagy.getFirst(FieldKey.ALBUM);

                    						     artissty=tagy.getFirst(FieldKey.ARTIST);

                    						     byte[] imageData = tag.getFirstArtwork().getBinaryData();
                    						     if(imageData!=null) {
                    						     //converting the bytes to an image
                    						     try {
    												img = ImageIO.read(new ByteArrayInputStream(imageData));
    											} catch (IOException e) {
    												// TODO Auto-generated catch block
    												e.printStackTrace();
    											}

                    						     @SuppressWarnings("rawtypes")
    												Iterator iter = ImageIO.getImageWritersByFormatName("jpeg");
                        						     ImageWriter writer = (ImageWriter)iter.next();
                        						  // instantiate an ImageWriteParam object with default compression options
                        						     ImageWriteParam iwp = writer.getDefaultWriteParam();
                        						     iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                        						     iwp.setCompressionQuality(0.3f);
                        						     //iwp.setCompressionQuality(0);   // an integer between 0 and 1
                        						     // 1 specifies minimum compression and maximum quality

                        						     File fileA = new File(System.getProperty("user.home")+"/ilix/cache-images/"+n+"85.jpeg");
    	                    					        string=fileA.getAbsolutePath();
                        						     FileImageOutputStream output = null;
    												try {
    													output = new FileImageOutputStream(fileA);
    												} catch (FileNotFoundException e2) {
    													// TODO Auto-generated catch block
    													e2.printStackTrace();
    												} catch (IOException e2) {
    													// TODO Auto-generated catch block
    													e2.printStackTrace();
    												}

                        						     writer.setOutput(output);
                        						     if( img==null) {
                        						    	 try {
                        						    	 img=ImageIO.read(new File(System.getProperty("user.home")+"/ilix/A5.png"));}catch(IOException e) {
                        						    		 e.printStackTrace();
                        						    	 }
                        						    	 string=System.getProperty("user.home")+"\\ilix\\A5.png";
                        						     }else {
                        						    	 }
                        						     //ImageIO.setUseCache(false);
                        						     IIOImage image = new IIOImage(img, null, null);
                        						     try {
    													writer.write(null, image, iwp);
    												} catch (IOException e1) {
    													// TODO Auto-generated catch block
    													e1.printStackTrace();
    												}
                        						     writer.dispose();
                        						     try {
                        						    	 output.close();
     												} catch (IOException e1) {
     													// TODO Auto-generated catch block
     													System.out.println("Failed To losed Output");
     													e1.printStackTrace();
     												}
                    						}

                    					     System.out.println(nowNow+" ## "+album1);
                    					     list.add(new AudioParser(nowNow, pathFInder,string,album1,artissty,ablumNoo,lengthy));
                    						   try {

                    							   FileWriter fw = new FileWriter(System.getProperty("user.home")+"/ilix/songs.txt", true);

                								 //  BufferedWriter bw = new BufferedWriter();
                    							   out = new BufferedWriter(fw);
                    							  // Path path="C:\\ProgramData\\ilix\\songs.txt"
                    							  // if(nowNow!=null&&pathFInder!=null&&string!=null&&album!=null) {
                    							   out.write("\n"+nowNow+"~"+ pathFInder+"~"+string+"~"+album1+"~"+artissty);



                    								    //more code
                    							//   }
                    							   //System.out.println("Got TO The Second Buffer");

                    								    totalSongse++;
                    								    //System.out.println("One Done "+counter);
                    								    System.out.println(counter);
                    								 //   out.flush();

                    								}
                    									 catch (IOException e) {
                    										 e.printStackTrace();
                    								}

                    						   try{

                    							   out.close();

                    								} //finally {
                    								//	try {   }
                    									 catch (IOException e) {
                    										 e.printStackTrace();
                    								}


                    						   nowNow="";
           								    pathFInder=null;
           								    string=null;
           								    album1="";
                    								}
                    						}*/
                						//read1.close();
                						}else {System.out.println("over");}
            				    			updateProgress(ii,max);
            				    			}

                						//beta+=counter;

                						/*if(ii==foldertoExtract.size()) {



                		                	// rootPane.setDisable(false);
                		                	 Platform.runLater(()->

                		                			 {
                		                				 //read1.close();
                            		                	// bar.setDisable(true);
                		                				// rootPane.setVisible(true);
                		                			//	 list.add(new AudioParser(nowNow,pathFInder,string,album1,artissty,ablumNoo,lengthy));
                		                				// filteredData=new FilteredList<>(list,s->true);
                              				        	// Vpane(filteredData);
                		                				// countdown = 0;

                		                				 rootPane.setContent(listScrollPane);
                		                				 refresh.setDisable(false);
                		                					try {
                       		                				 flows.setDisable(false);}catch(NullPointerException g) {
                       		                					 System.out.println("");
                       		                				 }
                		                				 System.out.println("Done! Reboot Advised,Otherwise all good");
                             							 System.gc();
                             						//	 counter=1;
//                             							try {
//                             						    	refreshButton();
//                             						    	System.out.println("Suceeded");
//                             						    	}catch(IOException f){}


                             							//rootPane.setVisible(true);
                             							//bar.setVisible(false);

//                		                				 rootPane.toFront();
//                		                				 addNewTrack.toFront();
                            		                	 //rootPane.setContent(listScrollPane );
                             				        	//read1.close();
                		                			 } );
                		                	 //rootPane.setVisible(true);
                		                	// System.exit(0);

                						   // Vpane(list);
                							//break;
                						} */

                					return null;
            						};


	    };
	    createTask.setOnSucceeded(g->{
	    	//try {
	    	countdown = 0;
	    	//countdown =1;
	    	//rootPane.setContent(listScrollPane);
	    	BPane.setCenter(listScrollPane);
			 refresh.setDisable(false);
			 addNewTrack.setDisable(false);
//				try {
//  				 flows.setDisable(false);}catch(NullPointerException gr) {
//  					 System.out.println("");
//  				 }
				Collections.sort(list, comparatorMyObject_byDay);
				orderedSongs();
			 System.out.println("Done! Reboot Advised , Otherwise all good");

			// albums





			 ViewByAlbum();
			 ViewByArtist();
			 System.gc();

	    });
	    pool.execute(createTask);
	    bar.progressProperty().bind(createTask.progressProperty());
	   /* };


	    service.start();*/
	    //service.
	}






	private String ConvertSecondToHHMMSSString(int nSecondTime) {
		//int intElapsed = (int) Math.floor(nSecondTime.toSeconds());
	    return LocalTime.MIN.plusSeconds(nSecondTime/1000).toString();
	}
	private String intToStringDuration(int aDuration) {
	    String result = "";

	    int hours = 0, minutes = 0, seconds = 0;

	    hours = aDuration / 3600;
	    minutes = (aDuration - hours * 3600) / 60;
	    seconds = (aDuration - (hours * 3600 + minutes * 60));

	    result = String.format("%02d:%02d:%02d", hours, minutes, seconds);
	    return result;
	}
	private static String formatTime2(int duration) {
String joe=null;
		boolean flag = true;
		//System.out.println(elapsed);
		if (duration>0) {
			///System.out.println(flag);
			int intDuration = (int) duration;
			//System.out.println(duration);
			int durationHours = intDuration / (60 * 60);
			if (durationHours > 0) {
				intDuration -= durationHours * 60 * 60;
			}
			int durationMinutes = intDuration / 60;
			int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;
			if (durationHours > 0) {

				joe= String.format("%d:%02d:%02d",durationHours, durationMinutes, durationSeconds);
			} else {

				joe= String.format("%02d:%02d",  durationMinutes,durationSeconds);
			}
		}
		return joe;
	}

public void checkck() {


	if(finish==totalSongse) {

		rootPane.setVisible(true);
		rootPane.setDisable(false);
		finish=0;
	}

}

public void setter() {
//	if(refreshList.isEmpty()== false) {
//	list.addAll(refreshList);
//	refreshList.clear();
//	Collections.sort(list, comparatorMyObject_byDay);
//	}

}



public void setterforAlbum() {
	new Thread() {
		 @Override
		public void run() {
	if(list.isEmpty()== false) {
		//String oldValue
	Collections.sort(list, comparatorByArtist);
	}
		 }
	}.start();

}
public void sorterByDate() {


	if(refreshList.isEmpty()== false) {
	//	list.addAll(refreshList);
	//	refreshList.clear();
		Collections.sort(list, comparatorbyDate);
		}




}


	// Formats the elapsed and the total duration of the music file
	private static String formatTime(Duration elapsed, Duration duration) {
		int intElapsed = (int) Math.floor(elapsed.toSeconds());
		int elapsedHours = intElapsed / (60 * 60);
		if (elapsedHours > 0) {
			intElapsed -= elapsedHours * 60 * 60;
		}
		int elapsedMinutes = intElapsed / 60;
		int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;

		boolean flag = true;
		//System.out.println(elapsed);
		if (duration.greaterThan(Duration.ZERO)) {
			///System.out.println(flag);
			int intDuration = (int) Math.floor(duration.toSeconds());
			//System.out.println(duration);
			int durationHours = intDuration / (60 * 60);
			if (durationHours > 0) {
				intDuration -= durationHours * 60 * 60;
			}
			int durationMinutes = intDuration / 60;
			int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;
			if (durationHours > 0) {

				return String.format("%d:%02d:%02d                                              %d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds,
						durationHours, durationMinutes, durationSeconds);
			} else {

				return String.format("%02d:%02d                                              %02d:%02d", elapsedMinutes, elapsedSeconds, durationMinutes,
						durationSeconds);
			}
		} else {
			if (elapsedHours > 0) {
				return String.format("%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds);
			} else {
				return String.format("%02d:%02d", elapsedMinutes, elapsedSeconds);
			}
		}
	}






/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	String titlee, filepathe,imageInfoe,albume,artee,albumgete,lengthe,finalAlbume=null,lastDatee;
	public void BringItO(String file) throws IOException {

		 Task<Void> createTask = new Task<Void>() {


             @Override
			protected Void call() {

		int figure=0;
		Scanner read = null;
	//	System.out.println("Came Through!");
		//BufferedReader in = new BufferedReader(new FileReader("songs.txt"), 16*1024);
		try {
			read = new Scanner (new BufferedReader(new FileReader(System.getProperty("user.home")+"\\ilix\\"+file), 16*1024));
		}catch(IOException trap) {


		}finally {if(read!=null) {
	    	read.useDelimiter("~");


	    	figure=0;
	    	while (read.hasNextLine())
	    	{
	    		figure+=1;
	    		System.out.println(figure);
	    	   titlee = read.next();
	    	   filepathe = read.next();
	    	   imageInfoe=read.next();;
	    	   finalAlbume = read.next();
	    	   albumgete = read.next();
	    	   lengthe = read.next();
	    	   lastDatee = read.next();
//	    	   if (album!=null) {
//	    	   StringBuilder sb= new StringBuilder(album);
//	    	   sb.deleteCharAt(0);
//	    	   finalAlbum=sb.toString();
//	    	   }
	    	   artee=read.nextLine();

	    	   Platform.runLater(()->{
	    	   refreshList.add(new AudioParser(titlee, filepathe,imageInfoe,finalAlbume,artee,albumgete,lengthe,lastDatee));
	    	   });
	    	   //System.out.println("Song Name "+title + "    Locale" + filepath+"\n"); // just for debugging
	    	}
	    	read.close();
	    	Vpane(refreshList);
	    	titlee=null; filepathe=null;imageInfoe=null;albume=null;artee=null;albumgete=null;lengthe=null;finalAlbume=null;lastDatee=null;
			}}

    	return null;
     		};


     };
     pool.execute(createTask);
	}
//}


	FileWriter forr=null;
	 BufferedWriter outter=null;
	 synchronized void orderedSongs() {
		File temp = new File(System.getProperty("user.home")+"\\ilix\\songs.txt");
		File temp3 = new File(System.getProperty("user.home")+"\\ilix\\songs.txt");
		//temp.
		//new File(System.getProperty("user.home")+"\\ilix\\songs_backup.txt")
		if (temp.exists()) {
		   // @SuppressWarnings("resource");
			//temp.
		   // try {
		    	temp.renameTo(new File(System.getProperty("user.home")+"\\ilix\\songs_backup.txt"));

		    temp = new File(System.getProperty("user.home")+"\\ilix\\songs_backup.txt");
		    if(temp.exists()) {

		    	System.out.println("BACKUP SUCCESFUL");
		    	//temp = new File(System.getProperty("user.home")+"\\ilix\\songs.txt");
		    	try {
		    		temp3.createNewFile();
					try {
					RandomAccessFile raf = new RandomAccessFile(temp3, "rw");

				    raf.setLength(0);
				    raf.close();
				    } catch(IOException fj) {


				    }
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Error When Creating New Song File -function (orderedSongs)");
					e.printStackTrace();

				}
		    }
		}
		 BufferedWriter outing = null;
	 filteredData.forEach((moch)->{



		 try {

		//	 (String name, String absolutePath,String image,String album,String artist,String albumnun,String Length,String datemod)//

			    outter = new BufferedWriter(new OutputStreamWriter(
					    new FileOutputStream(System.getProperty("user.home")+"/ilix/songs.txt", true), "UTF-8"));

			    outter.write(moch.nameGet()+"(~)"+ moch.fileLocationGet()+"(~)"+moch.ImageGet()+"(~)"+moch.albumGet()+"(~)"+moch.artistGet()+"(~)"+moch.AlbumnoGet()+"(~)"+moch.lengthGet()+"(~)"+moch.getDatemodedlast()+"\n");
			    System.out.println("=");


				}catch(IOException fg) {


				}catch(NumberFormatException rar){


				}finally {
					 try {
						 System.out.println("-");
					outter.close();}catch(IOException fg) {


					}
				//	fw.close();
				}



	 });
	 System.out.println("Copying Done !");


	}




	/////////////////////////////////////////////////////////////////////////
	//........................PROTOTYPE....................................//
	/////////////////////////////////////////////////////////////////////////
	 String molly = null;
	@SuppressWarnings("resource")
	public void BringItON() throws IOException {
	/*	Service<Void> service_1 = new Service<Void>(){

		        @Override
		        protected Task<Void> createTask() {
		            return new Task<Void>() {

		                @Override
						protected Void call() throws Exception {*/


/*
		Task<Void> createTask = new Task<Void>() {


            @Override
			protected Void call() {*/

		//Vpane(list);

		int figure=0;
		Scanner read = null, read1 = null;
	//	System.out.println("Came Through!");
		//BufferedReader in = new BufferedReader(new FileReader("songs.txt"), 16*1024);
		try {
			read = new Scanner (new BufferedReader(new FileReader(System.getProperty("user.home")+"/ilix/songs.txt"), 16*1024));
			read1 = new Scanner (new BufferedReader(new FileReader(System.getProperty("user.home")+"/ilix/toExtract.txt"), 16*1024));
		}catch(IOException trap) {
			System.out.println("songs files or toExtract files are missing or corrupted");
			trap.printStackTrace();

		}finally {if(read!=null) {

		//System.out.println("Came Through!");
		//BufferedReader in = new BufferedReader(new FileReader("songs.txt"), 16*1024);


    	read.useDelimiter("\\(~\\)");
    	StringBuilder sb=null;
        String title, filepath,imageInfo,album,arte,folder,albumget,length,finalAlbum=null,lastDate;
    	//list.clear();
    	while (read.hasNextLine())
    	{

    		//System.out.println(figure);
    	   title = read.next();

    	   filepath = read.next();
    	   imageInfo=read.next();;
    	   finalAlbum = read.next();
    	   arte= read.next();
    	   albumget= read.next();
    	   length= read.next();
    	   lastDate = read.nextLine();
    	   sb=new StringBuilder(lastDate);

			sb.delete(0, 3);
			lastDate=sb.toString();
			//System.out.println(arte);
		//	molly = filepath ;

		if(new File(filepath).exists()) {
			if(addedSongs.contains(filepath)) {
				//System.out.println("Repeat .skip. ");

			}else {
				addedSongs.add(filepath);
				list.add(new AudioParser(title, filepath,imageInfo,finalAlbum,arte,albumget,length,lastDate));
				figure+=1;
			}
			//lastDate.replaceAll("\\(~\\)", "");
			//lastDate.replace("(", "");
			//System.out.println(lastDate);
		//	 Platform.runLater(()->{
			/*list.forEach(e->{

				if(e.fileLocationGet()==filepath) {


				}else {


				}
			});*/




	    	  // });

		}
    	   //System.out.println("Song Name "+title + "    Locale" + filepath+"\n"); // just for debugging
    	}
    	read.close();

    	addedSongs.clear();
    	try {
    	ReturnNumbers("StartUp",figure);}catch(IOException far) {

    	}
    	//filteredData.clear();

    	//read1.useDelimiter("~");

    	//Scanner read1 = new Scanner (new BufferedReader(new FileReader(System.getProperty("user.home")+"/ilix/toExtract.txt"), 16*1024));
    	//figure=0;
    	while (read1.hasNextLine())
    	{

    		folder= read1.nextLine();
    		foldertoExtract.add(folder);
    		figure+=1;
    	}
    	read1.close();

	}}}


		public void phaser(){


				//Vpane(list);
				int figure=0;
				Scanner read = null, read1 = null;
			//	System.out.println("Came Through!");
				//BufferedReader in = new BufferedReader(new FileReader("songs.txt"), 16*1024);
				try {
					read = new Scanner (new BufferedReader(new FileReader(System.getProperty("user.home")+"/ilix/phase.txt"), 16*1024));

				}catch(IOException trap) {
					System.out.println("A file Not Found ,Submit Log ,OTherwise All good");
					trap.printStackTrace();

				}finally {if(read!=null) {

				//System.out.println("Came Through!");
				//BufferedReader in = new BufferedReader(new FileReader("songs.txt"), 16*1024);


		    	read.useDelimiter("~");
		    	StringBuilder sb=null;
		        String title=null, filepath=null,imageInfo=null,album,arte,folder,albumget,length,finalAlbum=null,lastDate;
		    	//list.clear();
		    	while (read.hasNextLine())
		    	{

		    		System.out.println(figure);
		    	   title = read.next();
		    	   filepath = read.next();
		    	   imageInfo=read.next();;

		    	  // System.out.println("State "+title + "    conditon  " + filepath+"  "+imageInfo); // just for debugging
		    	}
		    	read.close();
		    	StateOfApp("moj",filepath,imageInfo);

			}}}
//filteredData = new FilteredList<>(refreshList, t -> true);

//	ReturnNumbers("StartUp",figure);
/*return null;
		};


};
pool.execute(createTask);*/
    	/*return null;}
		                //finally{




		                };










		                	}};service_1.start();*/




	public void refreshButton() throws IOException{



		Scanner read1 = new Scanner (new BufferedReader(new FileReader(System.getProperty("user.home")+"/ilix/toExtract.txt"), 16*1024));
		String folder;
		while (read1.hasNextLine())
    	{

    		folder= read1.nextLine();
    		if(folder!=null) {
    		if(!foldertoExtract.contains(folder)) {
    		  foldertoExtract.add(folder);
    		  }}

    	}
    	read1.close();
    	extractFolders();
	}

//STATE,DEX,VOLUME,REPEAT
	public void upway() throws IOException {

		//System.out.println("Came Through!");
		//BufferedReader in = new BufferedReader(new FileReader("songs.txt"), 16*1024);
		Scanner read = new Scanner (new BufferedReader(new FileReader(System.getProperty("user.home")+"/ilix/state.txt"), 16*1024));
		Scanner read3 = new Scanner (new BufferedReader(new FileReader(System.getProperty("user.home")+"/ilix/dex.txt"), 16*1024));
    	read.useDelimiter("~");
    	read3.useDelimiter("~");
    	Scanner read4 = new Scanner (new BufferedReader(new FileReader(System.getProperty("user.home")+"/ilix/volume.txt"), 16*1024));


    	String chosen="0", lastplaying,itedddd,pos;
    	Scanner read2 = new Scanner (new BufferedReader(new FileReader(System.getProperty("user.home")+"/ilix/repeat.txt"), 16*1024));
    	while (read2.hasNextLine())
    	{


    		String valueb = read2.nextLine();
    		songPlaytime(valueb);

    	}
    	read2.close();
    	while (read.hasNextLine())
    	{
    	//	figure+=1;
    		String hugre ="0";
    		chosen = read.next();
    		lastplaying = read.next();

    		try {

    			hugre = read.next();

    		}catch(NoSuchElementException fr) {  }

    		//int reverseOrder = 0;
    		//itedddd = read.next();
    		if(chosen==null||chosen==""||chosen==" ") {

    		Chosen1=0;
    		}else {
    			try {
    			Chosen1=Integer.parseInt(chosen);}catch(NumberFormatException lg) {
    				Chosen1=0;
    			}
    		}
    		if(hugre==null||hugre==""||hugre==" ") {

    			reverseOrder=0;
        		}else {
        			try {
        				reverseOrder = Integer.parseInt(hugre);
        	    		/*System.out.println("REVERSE ORDER IS  "+reverseOrder);*/;}catch(NumberFormatException lg) {
        	    			reverseOrder=0;
        			}
        		}
    		current=lastplaying;
    		//if(chosen!=null) {
    		//int mam=Integer.parseInt(itedddd);



    		//by date



    		//}
    		//trackMaster2(lastplaying);
    		//listViewDex=mam;


//    	   if (album!=null) {
//    	   StringBuilder sb= new StringBuilder(album);
//    	   sb.deleteCharAt(0);
//    	   finalAlbum=sb.toString();
//    	   }

    	 //  list.add(new AudioParser(title, filepath,imageInfo,finalAlbum));
    	   //System.out.println("Song Name "+title + "    Locale" + filepath+"\n"); // just for debugging
    	}
    	read.close();
    	while (read3.hasNext())
    	{
    	//	figure+=1;
    		int mam=0;
    		double trying=0;
    		try {
    		itedddd = read3.next();
    		pos =  read3.next();
    		 mam = Integer.parseInt(itedddd);
    		 trying = Double.parseDouble(pos);
    		}catch(NoSuchElementException ahh) {

    		}

    		if (mam==0) {
    			listViewDex=0;
    		}else {

    			listViewDex=mam;
    		}
    		if (trying==0) {
    			position=0;
    		}else {

    			position=trying;
    			 System.out.println("position "+ position);
    		}





    	}
    		read3.close();
    		while (read4.hasNext())
        	{
    			String volumer = read4.next();
    			volumeGuy = Double.parseDouble(volumer);
    			volumeSlider.setValue(volumeGuy);
        	}




    //	System.out.println(figure);
    	//ReturnNumbers("StartUp",figure);

	}



	public void BringItON2() throws IOException {
		Service<Void> service_1 = new Service<Void>(){

	        @Override
	        protected Task<Void> createTask() {
	            return new Task<Void>() {

	                @Override
					protected Void call() throws Exception {
	                	 String titlet,filepath,arte,imageData,albumt,ablumNo,Length,lastDate;
	     	            System.out.println("Came Through!");
	     	            BufferedReader in = null;
	     				try {
	     					in = new BufferedReader(new FileReader(System.getProperty("user.home")+"/ilix/songs.txt"), 80*1024);
	     				} catch (FileNotFoundException e) {
	     					// TODO Auto-generated catch block
	     					e.printStackTrace();
	     				}

	     				while (true) {
	     					String line =null;
	     				try {
	     					line = in.readLine();
	     				} catch (IOException e) {
	     					// TODO Auto-generated catch block
	     					e.printStackTrace();
	     				}

	     				if (line == null||line==""||line==" ") {
	     					System.out.println("End");
	     					break;

	     					}

	     				String []data = line.split("\\(~)");
	     				titlet = data[0];
	     				filepath = data[1];
	     				imageData = data[2];
	     				albumt = data[3];
	     				arte = data[4];
	     				ablumNo = data[5];
	     				Length = data[6];
	     				lastDate = data[7];

	     				//refreshList.add(new AudioParser(nowNow, pathFInder,string,album));
	     				refreshList.add(new AudioParser(titlet,filepath,imageData,albumt,arte,ablumNo,Length,lastDate));

	     				//Thread.currentThread();
	     				//Thread.yield();
	     				}
	     				setter();
	     				in.close();




		return null;}
	              //finally{




	              };










	              	}};service_1.start();

//		new Thread() {
//
//
//	        @SuppressWarnings("resource")
//			public void run() {

	        }


	//Collections.sort(lazyLoad ,comparatorbyTrack);
	public void BringItONA()throws IOException  {


//		new Thread() {
//
//
//	        @SuppressWarnings("resource")
//			public void run() {
		/*Service<Void> service_1 = new Service<Void>(){

	        @Override
	        protected Task<Void> createTask() {
	            return new Task<Void>() {

	                @Override
					protected Void call() throws Exception {*/
	                	String titlet,filepath,arte,imageData,albumt;
	    	            System.out.println("Came Through!");
	    	            BufferedReader in = null;
	    				try {
	    					in = new BufferedReader(new FileReader(System.getProperty("user.home")+"/ilix/albums.txt"), 80*1024);
	    				} catch (FileNotFoundException e) {
	    					// TODO Auto-generated catch block
	    					e.printStackTrace();
	    				}

	    				while (true) {
	    					String line =null;
	    				try {
	    					line = in.readLine();
	    				} catch (IOException e) {
	    					// TODO Auto-generated catch block
	    					e.printStackTrace();
	    				}

	    				if (line == null||line==""||line==" ") {
	    					System.out.println("End");
	    					break;

	    					}

	    				String []data = line.split("\\~");
	    				titlet = data[0];
	    				filepath = data[1];


	    				//refreshList.add(new AudioParser(nowNow, pathFInder,string,album));
	    				albums.add(new Albums(titlet,filepath));
	    			//	System.out.println(titlet+" "+filepath);
	    				//Thread.currentThread();
	    				//Thread.yield();
	    				}
	    				//setter();
	    				in.close();


	    		    	Scanner read1 = new Scanner (new BufferedReader(new FileReader(System.getProperty("user.home")+"/ilix/albumsdone.txt"), 16*1024));
	    		    	//figure=0;
	    		    	while (read1.hasNextLine())
	    		    	{

	    		    		String ferf=read1.nextLine();
	    		    		albumsDone.add(ferf);
	    		    		//figure+=1;
	    		    	}
	    		    	read1.close();


	/*	return null;}


	            };
	        }};service_1.start();*/
	        }
	public void BringItONAr() throws IOException {


//		new Thread() {
//
//
//	        @SuppressWarnings("resource")
//			public void run() {
		 String titlet,filepath,arte,imageData,albumt;
	            //System.out.println("Came Through!");
	            BufferedReader in = null;
				try {
					in = new BufferedReader(new FileReader(System.getProperty("user.home")+"/ilix/artists.txt"), 80*1024);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				while (true) {
					String line =null;
				try {
					line = in.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (line == null||line==""||line==" ") {
					System.out.println("End");
					break;

					}

				String []data = line.split("\\~");
				titlet = data[0];
				filepath = data[1];


				//refreshList.add(new AudioParser(nowNow, pathFInder,string,album));
				artistry.add(new Artists(titlet,filepath));
				//System.out.println(titlet+" "+filepath);
				//Thread.currentThread();
				//Thread.yield();
				}
				//setter();
				in.close();


		    	Scanner read1 = new Scanner (new BufferedReader(new FileReader(System.getProperty("user.home")+"/ilix/artistsdone.txt"), 16*1024));
		    	//figure=0;
		    	while (read1.hasNextLine())
		    	{


		    		artistdone.add(read1.nextLine());
		    		//figure+=1;
		    	}
		    	read1.close();

		    	artistry.sort(artistsComparator);
		    	/*return null;}
	  //finally{




	  };










	  	}};service_1.start();*/


	        }
//	    }.start();
//
//
//		    }

	int publtt=0;
	BufferedWriter kosh=null;
	public void ViewByAlbum() {


		  File temp = new File(System.getProperty("user.home")+"/ilix/albums.txt");
			if (temp.exists()) {
			   // @SuppressWarnings("resource");
			    try {
				RandomAccessFile raf = new RandomAccessFile(temp, "rw");

			    raf.setLength(0);
			    raf.close();
			    } catch(IOException fj) {


			    }
			}
		 File temp1 = new File(System.getProperty("user.home")+"/ilix/albumsdone.txt");
			if (temp1.exists()) {
			   // @SuppressWarnings("resource");
			    try {
				RandomAccessFile raf = new RandomAccessFile(temp1, "rw");

			    raf.setLength(0);
			    raf.close();
			    } catch(IOException fj) {


			    }
			}

		  // Path path="C:\\ProgramData\\ilix\\songs.txt"
		  // if(nowNow!=null&&pathFInder!=null&&string!=null&&album!=null) {
/*
		Service<Void> service_1 = new Service<Void>(){

	        @Override
	        protected Task<Void> createTask() {
	            return new Task<Void>() {

	                @Override
					protected Void call() throws Exception {


*/


		list.forEach((AudioParser)->{
			String album=AudioParser.albumGet();
			String imagepic=AudioParser.ImageGet();
		/*	String yearry=AudioParser.;
			String songsNO=AudioParser.ImageGet();*/
			String text=null;
			list.forEach((moch)->{

				String albumd=moch.albumGet();
				if(albumd==album) {

					if(!(albumsDone.contains(albumd)&&!(albums.contains(albumd)))) {
//						list.forEach((hanut)->{
//			            	for(int i=0;i<=1;i++) {
//			            		if(album==hanut.albumGet()) {
//
//			    			   //break;
//			            		}
//			            	}
//			            });
						iamgelocale=moch.ImageGet();
						albumsDone.add(albumd);
		    			albums.add(new Albums(albumd,iamgelocale));
					  publtt++;
					  try(
								//FileWriter fw = new FileWriter("mydb.txt", true);
							    //BufferedWriter bw = new BufferedWriter(new FileWriter("mydb.txt", true));
							    PrintWriter out4 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/albumsdone.txt", true))))
							{
						  out4.println(albumd);
							} catch (IOException e) {
							    //exception handling left as an exercise for the reader
							}

					  try(
								//FileWriter fw = new FileWriter("mydb.txt", true);
							    //BufferedWriter bw = new BufferedWriter(new FileWriter("mydb.txt", true));
							    PrintWriter out4 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/albums.txt", true))))
							{

							out4.println(albumd+"~"+iamgelocale);
							 //System.out.println(iamgelocale+" "+albumd);


							} catch (IOException e) {
							    //exception handling left as an exercise for the reader
							}


					  }
					  }


			});

		});


	//	System.out.print("Done");
/*

		return null;
	            } };
	        }};service_1.start();*/
	        }



//	@FXML
	String iamgelocalee=null;
	public void ViewByArtist() {


		File temp11 = new File(System.getProperty("user.home")+"/ilix/artistsdone.txt");
		if (temp11.exists()) {
		   // @SuppressWarnings("resource");
		    try {
			RandomAccessFile raf = new RandomAccessFile(temp11, "rw");

		    raf.setLength(0);
		    raf.close();
		    } catch(IOException fj) {


		    }
		}
		File temp111 = new File( System.getProperty("user.home")+"/ilix/artists.txt");
		if (temp111.exists()) {
		   // @SuppressWarnings("resource");
		    try {
			RandomAccessFile raf = new RandomAccessFile(temp111, "rw");

		    raf.setLength(0);
		    raf.close();
		    } catch(IOException fj) {


		    }
		}
		  // Path path="C:\\ProgramData\\ilix\\songs.txt"
		  // if(nowNow!=null&&pathFInder!=null&&string!=null&&album!=null) {

/*
		Service<Void> service_1 = new Service<Void>(){

	        @Override
	        protected Task<Void> createTask() {
	            return new Task<Void>() {

	                @Override
					protected Void call() throws Exception {
*/
		list.forEach((AudioParser)->{
			String album=AudioParser.artistGet();
			String imagepic=AudioParser.ImageGet();
			String text=null;
			list.forEach((moch)->{

				String albumd=moch.artistGet();
				if(albumd==album) {

					if(!(artistdone.contains(albumd))) {
//						list.forEach((hanut)->{
//			            	for(int i=0;i<=1;i++) {
//			            		if(album==hanut.albumGet()) {
//
//			    			   //break;
//			            		}
//			            	}
//			            });
						iamgelocalee=moch.ImageGet();
						artistdone.add(albumd);
						artistry.add(new Artists(albumd,iamgelocalee));
					//  publtt++;
					  try(
								//FileWriter fw = new FileWriter("mydb.txt", true);
							    //BufferedWriter bw = new BufferedWriter(new FileWriter("mydb.txt", true));
							    PrintWriter out4 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/artistsdone.txt", true))))
							{
						  out4.println(albumd);
							} catch (IOException e) {
							    //exception handling left as an exercise for the reader
							}

					  try(
								//FileWriter fw = new FileWriter("mydb.txt", true);
							    //BufferedWriter bw = new BufferedWriter(new FileWriter("mydb.txt", true));
							    PrintWriter out4 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/artists.txt", true))))
							{

							out4.println(albumd+"~"+iamgelocalee);
							// System.out.println(iamgelocalee+" "+albumd);


							} catch (IOException e) {
							    //exception handling left as an exercise for the reader
							}


					  }
					  }


			});

		});


		//System.out.print("Done");

	/*	return null;
    } };
}};service_1.start();*/
//service_1.setOnSucceeded(f->{
//	albumAndArtsit();
//
//});


	}



	int shown = 0;

	    private GridPane selectedLabel;
	    int i=0;
	    GridView<Albums> gridView = new GridView<>();

	    //int shown =0;
	    int podd=0;

	    @FXML
	    public  void Albumadder(){
	    //	List = new PopOver();
//	    	artists
//	    	songss
//	    	albumss
	    	albumss.setStyle(" -fx-border-width: 3; -fx-background-color: white ; ");	    //
         //  rootPane.setContent(null);
         //  filteredData = null;
         //  gridView.setItems(null);
          // list.clear
           if(albums.size()<=0) {
        	 //  System.out.println("Called");
        	   ViewByAlbum();

        	   gridView.setItems(null);
               gridView.setItems(albums);
               rootPane.setContent(gridView);
           }else {
        	   //if(!gridView.isVisible()) {

        	   albums.sort(albumsComparator);
        	   gridView.setItems(null);
               gridView.setItems(albums);
               rootPane.setContent(gridView);
        	  // }
           }


           //gridView.setPadding(new Insets(0,0,5,0));
if(podd==0) {
	gridView.setPrefHeight(rootPane.getHeight());
    gridView.setMaxWidth(rootPane.getWidth());
    gridView.setCellWidth(75);
    gridView.setVerticalCellSpacing(20);
    gridView.setHorizontalCellSpacing(15);
    gridView.setCellHeight(75);
    //gridView.setOnMouseExited(value);
 /*   gridView.setOnMouseExited(e -> {

        	if(List.isShowing()){List.hide();}


		});*/
	       Service<Void> service_11 = new Service<Void>(){

	           @Override
	           protected Task<Void> createTask() {
	               return new Task<Void>() {

	                   @Override
	   				protected Void call() throws Exception {
	   	gridView.setCellFactory(new Callback<GridView<Albums>, GridCell<Albums>>() {
	            public GridCell<Albums> call(GridView<Albums> gridView) {

	           	 GridPane grid4=new GridPane() ;
	           	 HBox  iconAndName = new HBox();
	           	 ImageView  icod = new ImageView();
	           	 Label fag= new Label();
	           	 Rectangle clip=new Rectangle();
	           	 {
	           		 // fag=new Label();
	           		  fag.setPadding(new Insets(2,5,0,5));
	       	          fag.setTextFill(Color.WHITE);
	       	          fag.setId("Fag");
	       	          icod.setCache(false);
	           		  iconAndName.setAlignment(Pos.CENTER);
	           		  iconAndName.setPadding(new Insets(0,0,3,0));
	           		  iconAndName.getChildren().add(icod);

	           		  icod.setFitHeight(75);
	     		      icod.setFitWidth(75);


	     		      grid4.setPrefSize(75, 75);
	   	              grid4.maxHeight(75);
	   	              grid4.maxWidth(75);
	   	          //  grid4.setBorder(1);
//	   	            HBox iconAndName = new HBox(2);
//	   	            //ImageView icon = new ImageView();
	   	            //System.out.println(audioParser.nameGet());

	   	              grid4.getStyleClass().add("grid4");
	     	    	  grid4.setPadding(new Insets(0,0,5,0));
	   	              grid4.add(iconAndName, 0, 0);
	   	              grid4.add(fag, 0, 1);

	   	              clip.setArcWidth(10);
	      	          clip.setArcHeight(10);
	      	          clip.widthProperty().bind(grid4.widthProperty());
	      	          clip.heightProperty().bind(grid4.heightProperty());
	           	 }


	                return new GridCell<Albums>() {




	                	    @Override
	                	    protected void updateItem(Albums item, boolean empty) {
	                	        // TODO Auto-generated method stub
	                	        super.updateItem(item, empty);
	                	        icod.setImage(null);
	                	        if (empty || item == null) {
	                	            setText(null);
	                	            setGraphic(null);
	                	        } else {


	                	        	{
	               	        		fag.setText(null);
	               	        		fag.setText(item.albumGet());

	               	        	}

	                	        	 new Timeline(
	      		           					new KeyFrame(
	      		           							Duration.seconds(0.0999),event ->{

	      		           						//	gridView2.vi
	      		           								if(this.isVisible()) {
	      		           									//	Platform.runLater(() -> {
	      		           								//icod.setImage(new Image(new File(item.ImageGet()).toURI().toString(),true));
	      		           									icod.setImage(new Image(new File(item.ImageGet()).toURI().toString() ,75 ,75,false ,false ,true));
	      		           									//	});
	      		           										}

	      		           							})).play();
	                 		        	//icod.setImage(new Image(new File(item.ImageGet()).toURI().toString(),true));

	                	            grid4.setClip(clip);


	                	            grid4.setOnMouseClicked(e -> {
	                	            	if (selectedLabel != grid4) {
	                	                   clearSelection();
	                	                   selectLabel(grid4);
	                	                   lazyLoad.clear();
//	                	                   filteredData=new FilteredList<>(lazyLoad,s->true);
//	                	               		list.forEach(mans->{
//	                	               			if(fag.getText().equalsIgnoreCase(mans.albumGet())) {
//
//	                	               				 lazyLoad.add(mans);
//	                	               			//i++;
//	                	               			}
//	                	               		});
//	                	               		Vpane(filteredData);
	                	                   goTOAlbum(fag.getText());
	                	               	    System.gc();

	                	               } else {
	                	                   clearSelection();
	                	               }
	                		        });
	                	            Label mek = new Label(fag.getText());

	                	          //  mek.setPrefHeight(10);
	                	          /*  grid4.setOnMouseEntered(e -> {

	 		               	        	if(List.isShowing()){List.hide();}
	 		               	        	if(shown==0||grid4.isHover()) {
	 		               	        Platform.runLater(()->{
	 		               			List = new PopOver(mek);
	 		               	       // List.setHeight(8);
	 		               		//	List = new PopOver(mek);

	 		               			//List.setContentNode(mek);
	 		               			//List.setMaxHeight(10);
	 		               		   List.setArrowSize(0);
	 		               			//this.setAutoHide(true);
	 		               		  // List.hide(Duration.millis(90));



	 		               		   //List
	 		               		//	List.setAutoFix(true);
	 		               		  //  List.aut;
	 		               		//	List.arrowSizeProperty().set(1);
	 		               			//List.show(albumy);
	 		               			List.show(albumy,  e.getScreenX(), e.getScreenY());
	 		               			((Parent)List.getSkin().getNode()).getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	 		               			List.show(albumy,  e.getScreenX(), e.getScreenY());
	 		               		 new Timeline(
	      		           					new KeyFrame(
	      		           							Duration.seconds(2),event ->{
	      		           								//if(List.)
	      		           							List.hide();

	      		           							})).play();
	 		               			});

	 		               	        shown =1;
	 		               	        	} });*/

	 		               	    grid4.setOnMouseExited(e -> {
	 		               	  //  shown =0;
	 		               	  //  if(List.isShowing()){List.hide();}


			               		        });
	                	          setGraphic(grid4);
	                	        }
	                	    }


	                };
	            }
	        });
	   	return null;}
	                 //finally{

	                 };



	                 	}};service_11.start();
	                 	podd=1;
}


           try
   		{
   	    PrintWriter out5 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/phase.txt", false)));
   		out5.println("ALBUMs~non~non");
   		out5.close();
   		//out4.close();
   	} catch (IOException j) {


   	}

	      /*  albumsDone.forEach(jobs->{
	        		//String hang=jobs;
	        		albums.forEach(mans->{
	        			if(jobs==mans.albumGet()) {
	        				// tilePane.getChildren().add(addTileNode(albums.get(i)));
	        				 gridView.getItems().add(albums.get(i));
	        			     i++;
	        			}
	        		});
	        	});//*/


	       /* }else {

	        	 rootPane.setContent(null);
	 		    // rootPane=new ScrollPane(scrollpane);;
	 		    rootPane.setContent(scrollpane);
	 		   albumsDone.forEach(jobs->{
	        		//String hang=jobs;
	        		albums.forEach(mans->{
	        			if(jobs==mans.albumGet()) {
	        				 tilePane.getChildren().add(addTileNode(albums.get(i)));
	        			i++;
	        			}
	        		});
	        	});
	        }*/



	    }
	    int iii=0;
	    int kcheeerr=0;
	    @FXML
	    public  void Artistadder(){
	    	  // rootPane.setContent(null);
	    	  // gridView2.setItems(null);

	    		artists.setStyle(" -fx-border-width: 1; -fx-border-color: white ; ");
	    	   if(artistry.size()<=0) {
	    		   System.out.println("Called");
	    		   ViewByArtist();
	           }else {
	        	 //  if(!gridView2.isVisible()) {
	        	   artistry.sort(artistsComparator);
	        	   gridView2.setItems(null);
		    	   gridView2.setItems(artistry);
			       rootPane.setContent(gridView2);
	        	   //}
	           }



		       if(kcheeerr==0) {
		    	   gridView2.setPrefHeight(rootPane.getHeight());
		           gridView2.setMaxWidth(rootPane.getWidth());
		           gridView2.setCellWidth(80);
		           gridView2.setVerticalCellSpacing(20);
		           gridView2.setHorizontalCellSpacing(10);
		           gridView2.setCellHeight(75);
		           gridView2.setPadding(new Insets( 0,0,0,10));
			       gridView2.setCache(false);
 		       gridView2.setCellFactory(new Callback<GridView<Artists>, GridCell<Artists>>() {
 		           public GridCell<Artists> call(GridView<Artists> gridView) {
 		           	 GridPane grid4 = new GridPane();

 		           	 HBox  iconAndName = new HBox();
 		           	 ImageView  icod = new ImageView();
 		           	 Label fag= new Label();
 		           	 Rectangle clip=new Rectangle();
 		           	 {
 		           		 // fag=new Label();
 		           		 fag.setPadding(new Insets(2,5,0,5));
 		       	         fag.setTextFill(Color.WHITE);
 		       	         fag.setId("Fag");
 		           		  iconAndName.setAlignment(Pos.CENTER);
 		           		  iconAndName.setPadding(new Insets(0,0,3,0));
 		           		  icod.setCache(false);
 		           		  iconAndName.getChildren().add(icod);
 		           		  icod.setFitHeight(80);
 		     		      icod.setFitWidth(80);


 		     		      grid4.setPrefSize(80, 75);
 		   	             // grid4.setMinSize(75, 75);
 		   	              //grid4.setMaxSize(75, 75);
 		   	              grid4.getStyleClass().add("grid4");
 		     	    	  grid4.setPadding(new Insets(0,0,5,0));
 		   	              grid4.add(iconAndName, 0, 0);
 		   	              grid4.add(fag, 0, 1);


 		   	              clip.setArcWidth(10);
 		     	            clip.setArcHeight(10);
 		     	            clip.widthProperty().bind(grid4.widthProperty());
 		     	            clip.heightProperty().bind(grid4.heightProperty());
 		           	 }


 		               return new GridCell<Artists>() {

 		               	    @Override
 		               	    protected void updateItem(Artists item, boolean empty) {
 		               	        // TODO Auto-generated method stub
 		               	        super.updateItem(item, empty);
 		               	    icod.setImage(null);
 		               	        if (empty || item == null) {
 		               	            setText(null);
 		               	            setGraphic(null);
 		               	        } else {

 		               	        	{
 		               	        		fag.setText(null);
 		               	        		fag.setText(item.artistGet());

 		               	        	}

 		               		        //pool.execute(()->{
 		               	        new Timeline(
 		           					new KeyFrame(
 		           							Duration.millis(1),event ->{

 		           						//	gridView2.vi
 		           								if(this.isVisible()) {
 		           									//	Platform.runLater(() -> {
 		           								//icod.setImage(new Image(new File(item.ImageGet()).toURI().toString(),true));
 		           									icod.setImage(new Image(new File(item.ImageGet()).toURI().toString() ,75 ,75,false ,false ,true));
 		           									//	});
 		           										}

 		           							})).play();



 		               		    //    });






 		               	            grid4.setClip(clip);
 		               	        	//setGraphic(grid4);
 		               	            grid4.setOnMouseClicked(e -> {
 		               	            	if (selectedLabel != grid4) {
 		               	                    clearSelection();
 		               	                    selectLabel(grid4);

 		               	                	goTOArtist(fag.getText());
 		               	                	System.gc();

 		               	                } else {
 		               	                    clearSelection();
 		               	                }
 		               		        });
 		               	            Label mek = new Label(fag.getText());
 		               	     /*   grid4.setOnMouseEntered(e -> {

 		               	        Platform.runLater(()->{
 		               		//	List = new PopOver(mek);
 		               	       // List.setHeight(50);
 		               			List.setContentNode(mek);
 		               			//List.setAutoFix(true);
 		               		  //  List.aut;
 		               			//List.arrowSizeProperty().set(1);
 		               			//List.show(albumy);
 		               			List.show(albumy,  e.getScreenX(), e.getScreenY());
 		               			((Parent)List.getSkin().getNode()).getStylesheets().add(getClass().getResource("application.css").toExternalForm());
 		               			List.show(albumy,  e.getScreenX(), e.getScreenY());

 		               			});


		               		        });*/
 		               	            setGraphic(grid4);

 		               	        }
 		               	    }


 		               };
 		           }
 		       });
 		      kcheeerr=1;
		       }



		       try
				{
			    PrintWriter out5 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/phase.txt", false)));
				out5.println("ARTISTs~non~non");
				out5.close();
				//out4.close();
			} catch (IOException j) {


			}

	    }



	    public void albumAndArtsit() {


	    }


	    String iamgelocale=null;
	    private void selectLabel(GridPane label) {
	        label.setStyle("-fx-background-color: #AFEEEE ;");
	        selectedLabel = label;
	    }

	    private void clearSelection() {
	        if (selectedLabel != null)
	            selectedLabel.setStyle("-fx-background-color: #32393D ;");
	    }
	    public void StateOfApp(String state,String stateCOnditon,String SongBeingPlayed) {

	    	if(Chosen1==0) {
				//Collections.sort(list, comparatorMyObject_byDay);
				}
			//by name
			else if(Chosen1==1) {
				Collections.sort(list, comparatorByArtist);}
			//by album

			else if(Chosen1==2) {
				Collections.sort(list, comparatorBynamey );}
			//by aritst
			else if(Chosen1==3) {
				Collections.sort(list, comparatorbyDate );}

	    	switch(state) {



	    	case "ALBUM":
	    		//goTOAlbum(stateCOnditon);
	    		System.out.println("Reg");
	    		Platform.runLater(()->{
	    			//list.addAll(listper);
	    	  		Vpane(filteredData);

	    			System.out.println("Last Played "+listViewDex);
	    			row=listViewDex;
	    			if(!filteredData.isEmpty()) {
	    				if(listViewDex<=filteredData.size()) {
	    				AudioParser alpo=filteredData.get(listViewDex);
	    	            current=alpo.fileLocationGet();
	    				flows.show(listViewDex);
	    				flowsy.showAtOffset(listViewDex,24);

	    				onHighLight(listViewDex);
	    				executor.submit(() -> 	Song(alpo.absolutePatht));
		    			play jog=new play(alpo.absolutePatht,false);
	    				}else {
	    					listViewDex=0;
	    					AudioParser alpo=filteredData.get(0);
		    	            current=alpo.fileLocationGet();
		    				flows.show(0);
		    				flowsy.showAtOffset(0,24);
		    				onHighLight(0);
		    				executor.submit(() -> 	Song(alpo.absolutePatht));
			    			play jog=new play(alpo.absolutePatht,false);
	    				}

	    			//	Bounds bounds=image.getBoundsInParent();



//	    			if(listViewDex!=0&&!filteredData.isEmpty()) {



	    			//executor.submit(() -> 	trackMaster2(alpo.absolutePatht));


	    			//}
	    			}
	    	   			//listper.add(new AudioParser(title, filepath,imageInfo,finalAlbum,arte,albumget,length,lastDate));



	    	   	    	   });
	    		break;


	    	case "ALBUMs" :
	    		Albumadder();
	    		break;


	    	case "ARTIST":

	    	 //   goTOArtist(stateCOnditon);
	    		System.out.println("Reg");
	    		Platform.runLater(()->{
	    			//list.addAll(listper);
	    	  		Vpane(filteredData);

	    			System.out.println("Last Played "+listViewDex);
	    			row=listViewDex;
	    			if(!filteredData.isEmpty()) {
	    				if(listViewDex<=filteredData.size()) {
	    				AudioParser alpo=filteredData.get(listViewDex);
	    	            current=alpo.fileLocationGet();
	    				flows.show(listViewDex);
	    				flowsy.showAtOffset(listViewDex,24);

	    				onHighLight(listViewDex);
	    				executor.submit(() -> 	Song(alpo.absolutePatht));
		    			play jog=new play(alpo.absolutePatht,false);
	    				}else {
	    					listViewDex=0;
	    					AudioParser alpo=filteredData.get(0);
		    	            current=alpo.fileLocationGet();
		    				flows.show(0);
		    				flowsy.showAtOffset(0,24);
		    				onHighLight(0);
		    				executor.submit(() -> 	Song(alpo.absolutePatht));
			    			play jog=new play(alpo.absolutePatht,false);
	    				}

	    			//	Bounds bounds=image.getBoundsInParent();



//	    			if(listViewDex!=0&&!filteredData.isEmpty()) {



	    			//executor.submit(() -> 	trackMaster2(alpo.absolutePatht));


	    			//}
	    			}
	    	   			//listper.add(new AudioParser(title, filepath,imageInfo,finalAlbum,arte,albumget,length,lastDate));



	    	   	    	   });
	    	    break;

	    	case "ARTISTs":
	    		Artistadder();
	    		break;

	    	case "MAIN":
	    		System.out.println("MAIN");
	    		Platform.runLater(()->{
	    			//list.addAll(listper);
	    	  		Vpane(filteredData);


	    			System.out.println("Last Played "+listViewDex);
	    			row=listViewDex;
	    			if(!filteredData.isEmpty()) {
	    				AudioParser alpo=filteredData.get(listViewDex);
	    	            current=alpo.fileLocationGet();
	    				flows.show(listViewDex);
	    				flowsy.showAtOffset(listViewDex,24);
	    				onHighLight(listViewDex);
	    				//OnClikedGrid(listViewDex);



	    			//	Bounds bounds=image.getBoundsInParent();



//	    			if(listViewDex!=0&&!filteredData.isEmpty()) {

	    			executor.submit(() -> 	Song(alpo.absolutePatht));
	    			//Song(oldValue);
 	     			play jog=new play(alpo.absolutePatht,false);
	    			//executor.submit(() -> 	trackMaster2(alpo.absolutePatht));


	    			//}
	    			}
	    	   			//listper.add(new AudioParser(title, filepath,imageInfo,finalAlbum,arte,albumget,length,lastDate));



	    	   	    	   });

	    		break;

	    	default:
	    		System.out.println("Reg");
	    		Platform.runLater(()->{
	    			//list.addAll(listper);
	    	  		Vpane(filteredData);

	    			System.out.println("Last Played "+listViewDex);
	    			row=listViewDex;
	    			if(!filteredData.isEmpty()) {
	    				if(listViewDex<=filteredData.size()) {
	    				AudioParser alpo=filteredData.get(listViewDex);
	    	            current=alpo.fileLocationGet();
	    				flows.show(listViewDex);
	    				flowsy.showAtOffset(listViewDex,24);

	    				onHighLight(listViewDex);
	    				executor.submit(() -> 	Song(alpo.absolutePatht));
		    			play jog=new play(alpo.absolutePatht,false);
	    				}else {
	    					listViewDex=0;
	    					AudioParser alpo=filteredData.get(0);
		    	            current=alpo.fileLocationGet();
		    				flows.show(0);
		    				flowsy.showAtOffset(0,24);
		    				onHighLight(0);
		    				executor.submit(() -> 	Song(alpo.absolutePatht));
			    			play jog=new play(alpo.absolutePatht,false);
	    				}

	    			//	Bounds bounds=image.getBoundsInParent();



//	    			if(listViewDex!=0&&!filteredData.isEmpty()) {



	    			//executor.submit(() -> 	trackMaster2(alpo.absolutePatht));


	    			//}
	    			}
	    	   			//listper.add(new AudioParser(title, filepath,imageInfo,finalAlbum,arte,albumget,length,lastDate));



	    	   	    	   });
	    		break;





	    	}



	    }
	    public void goTOAlbum(String albumName) {
	    	albumSongs=0;
			lazyLoad.clear();

			tempor.clear();


        		list.forEach(mans->{
        			if(albumName.equalsIgnoreCase(mans.albumGet())) {
        				 lazyLoad.add(mans);
         				 if(!tempor.contains(mans.artistGet())) {
         					if(albumSongs==0) {
         				tempor.add(mans.artistGet());}else {

         					tempor.add(mans.artistGet());
         				}
         				 }
         				albumSongs++;
        			//i++;
        			}
        		});
        	//	System.out.println(tempor);

        		//Collections.sort(lazyLoad, comparatorbyTrack);
//        		filteredData.clear();
//        		filteredData = new FilteredList<>(lazyLoad, t -> true);
        		try{
        		ReturnNumbers("StartUp",albumSongs);}catch(IOException fi){

        		}

        		filteredData=new FilteredList<>(lazyLoad,s->true);
        		Collections.sort(lazyLoad, comparatorbyTrack);
        		mok=filteredData.size()-1;
        		Vpane2(filteredData,albumSongs);
        		try
				{
			    PrintWriter out5 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/phase.txt", false)));
				out5.println("ABLUM~"+albumName+"~non");
				out5.close();
				//out4.close();
			} catch (IOException j) {
				System.out.println(j);

			}

	    }

	    public void goTOArtist(String artsitName) {
	    	albumSongs=0;
	    	try {
			popover.hide();
	    	}catch(NullPointerException gh){

	    	}

			lazyLoad.clear();
			tempor.clear();

            filteredData=new FilteredList<>(lazyLoad,s->true);
        		list.forEach(mans->{
        			if(artsitName.equalsIgnoreCase(mans.artistGet())) {

        				 lazyLoad.add(mans);
         				 if(!tempor.contains(mans.artistGet())) {
         					if(albumSongs==0) {
         				tempor.add(mans.artistGet());}else {

         					tempor.add("/" +mans.artistGet());
         				}
         				 }
         				albumSongs++;
        			//i++;
        			}
        		});

//        		filteredData.clear();
//        		filteredData = new FilteredList<>(lazyLoad, t -> true);
        		totalSongs=albumSongs;
        				try{
				        		ReturnNumbers("StartUp",albumSongs);}catch(IOException fi){

				        		}
        		Vpane(filteredData);
        		try
				{
			    PrintWriter out5 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/phase.txt", false)));
				out5.println("ARTIST~"+artsitName+"~non");
				out5.close();
				//out4.close();
			} catch (IOException j) {


			}

	    }


	    String tittleIn=" ",albumIn=" ",artistIn=" ",year=" ";
		Mp3File ssong=null ;
		int bpm=0,albumSongs=0,lengthr=0;
		BorderPane BPane = new BorderPane();
		int manogz=0;
		BufferedWriter man=null;
		GridPane nodee =null;
		MouseEvent he=null;
		String thing=null;
		AudioParser app = null;
		String holdValue=null;
	    public void Vpane(ObservableList<AudioParser> h) {
	    //	listScrollPane.setPrefSize(listScrollPane.getMaxWidth(), listScrollPane.getMaxHeight());

	    	if(!(h.isEmpty())) {
	    		TYming = true;
	    		manogz=0;
	    		mok=h.size()-1;
	    		totalSongs=h.size()-1;
	    		// ki=0;
	    		try
				{
			    PrintWriter out5 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/phase.txt", false)));
				out5.println("MAIN~non~non");
				out5.close();
				//out4.close();
			} catch (IOException j) {


			}

	    		//BPane.setTop(gridder);
	      	   //BPane.he
	    		//BPane.setPrefSize(rootPane.getWidth(), rootPane.getHeight());

	      	   // BPane.setRight(choicebox);
	      	  // rootPane.setContent(BPane);


	    		BPane.setTop(null);
	    		flows = VirtualFlow.createVertical(h, audioparser ->new AudioParserCell(audioparser,h.indexOf(audioparser)) );
	    		//flows.setStyle("-fx-background-radius: 15 0 0 15;-fx-border-radius:15;");
	    	//	grid.getStyleClass().add("border");

	    		//rootPane.setStyle("-fx-background-radius: 15 0 0 15;-fx-border-radius: 15 0 0 15;");
	    		listScrollPane=new VirtualizedScrollPane<>(flows);

	    		//listScrollPane.setPrefSize(rootPane.getWidth(),rootPane.getHeight());
	    		//listScrollPane.setStyle("-fx-background-radius: 15 0 0 15;-fx-border-radius: 15 0 0 15;");
	    		listScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER );
	    		listScrollPane.setPrefSize(listScrollPane.getMaxWidth(), listScrollPane.getMaxHeight());
	    		BPane.setCenter(listScrollPane);
	    		GridPane information=new GridPane();
     	     	Text gotAlbum=new Text("Go To Album");
     	     	Text gotArtist=new Text("Go To Artist");
				Text songInfo=new Text("Song Info");
				Text deleteSong=new Text("Delete Song");
				Text fileLocale=new Text(/*thing+"\n"+*/" Mbs"/*+"\n "+new File(thing).lastModified()*/);
				/// / / / / // / / / /





     	     	information.setPrefHeight(110);
			 	information.setPrefWidth(80);
			 	information.setMaxWidth(80);
			 	information.setPadding(new Insets(10));
			 	information.getStyleClass().add("optionsGrid");
			 	gotAlbum.setFill(Color.WHITE);
				gotArtist.setFill(Color.WHITE);
				songInfo.setFill(Color.WHITE);
				deleteSong.setFill(Color.WHITE);
				fileLocale.setFill(Color.GREY);

				gotArtist.getStyleClass().add("infoTxt");
				gotAlbum.getStyleClass().add("infoTxt");
				songInfo.getStyleClass().add("infoTxt");
				deleteSong.getStyleClass().add("infoTxt");
				fileLocale.getStyleClass().add("lowerText2");

				information.setVgap(5);
				information.add(gotAlbum, 0, 0);
				information.add(gotArtist, 0, 1);
				information.add(songInfo, 0, 2);
				information.add(deleteSong, 0, 3);
				information.add(fileLocale, 0, 4);
     	  //  flows.onScrollStartedProperty(()->{System.out.println("Scrolling");});
     	/*    flows.setOnScroll(new EventHandler<ScrollEvent>() {
     	        @Override
     	        public void handle(ScrollEvent event) {
//     	            double deltaY = event.getDeltaY()*6; // *6 to make the scrolling a bit faster
//     	            double width = rootPane.getContent().getBoundsInLocal().getWidth();
//     	            double vvalue = rootPane.getVvalue();
//     	            rootPane.setVvalue(vvalue + -deltaY/width); // deltaY/width to make the scrolling equally fast regardless of the actual width of the component

         	    	if(bar!=null) {
         	    		System.out.println("Found It");
         	    	}

         	    	 ScrollBar bar = (ScrollBar) flows.lookup(".scroll-bar");
         	     	   bar.valueProperty().addListener((src, ov, nv) -> {
         		        	//scroll=true;
         		        	//System.out.println("change on value " + nv);
         		    		System.out.println(nv);

         		            });
     	        	System.out.println("Scrolling");
     	        }
     	    });*/
     //	 listScrollPane.bar

/*
 try {


    	    		       // f.

            	     	    man = new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/songviewpos.txt", false));
            				man.write(""+ bar.getValue());


            				man.close();
            		 }
            	     	   catch(IOException ch) {

            	     	   }
  */


     	    /*flows.setOnScrollStarted(f->{
     	    	ScrollBar bar = (ScrollBar) flows.lookup(".scroll-bar");
     	    	if(bar!=null) {
     	    		System.out.println("Here");
     	    	}

     	    	bar.valueProperty().addListener((src, ov, nv) -> {
		        	//scroll=true;
		        	//System.out.println("change on value " + nv);
     	    		System.out.println(bar.getValue());
		            try {


	    		       // f.

        	     	    man = new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/songviewpos.txt", false));
        				man.write(""+ bar.getValue());


        				man.close();
        		 }
        	     	   catch(IOException ch) {

        	     	   }
		        });

        	  });*/

				fileLocale.setOnMouseClicked(event->{
					if(app!=null) {
						File thisDir = new File(app.fileLocationGet());

					//	DirectoryChooser directoryChooser = new DirectoryChooser();
						FileChooser filechooser = new FileChooser();
						filechooser.setTitle("Song Folder");
						filechooser.setInitialDirectory(thisDir);
						filechooser.setInitialFileName(thisDir.getName());

						filechooser.getExtensionFilters().add(new ExtensionFilter("MP3","*.mp3"));
//						directoryChooser.setMultiSelectionEnabled(true);
					//	filechooser.showDialog(Main.stage);
						//FileChooser();
					 //filechooser.showOpenDialog(Main.stage);
				}
				});

				deleteSong.setOnMouseClicked(event->{
					if(app!=null) {
						File deleteThis = new File(app.fileLocationGet());
						//TODO add Confirmation and Undo for Delete
						if(deleteThis.canWrite()) {
							deleteThis.delete();
							list.remove(app);
						}
				}
				});

					gotAlbum.setOnMouseClicked(event->{
						if(app!=null) {
						beforeAlbum=listViewDex;

						popover.hide();
						albumSongs=0;

						goTOAlbum(app.albumGet());


					}
					});
					gotArtist.setOnMouseClicked(event->{
						if(app!=null) {
						beforeAlbum=listViewDex;

						popover.hide();
						goTOArtist(app.artistGet());


						}});

					GridPane info = new GridPane();
					info.getStyleClass().add("optionsGrid");
 					info.setId("info");
 					info.setPadding(new Insets(10));
 					info.setCache(true);
 					info.setCacheHint(CacheHint.SPEED);
 					info.setHgap(1);

 					Text songinfo=new Text();
 					songinfo.setFill(Color.valueOf("#39FF9F"));
 					info.add(songinfo,0,0);
					songInfo.setOnMouseClicked(event->{
						//beforeAlbum=listViewDex;
						popover.hide();

						if(thing!=null) {
	     					try {
	     						ssong = new Mp3File(thing) ;

	     					} catch(InvalidPathException e4){

	     						//System.out.println("InvalidPathException");
	     						System.err.println("This should never happen: " + e4);
	     						//continue;

	     					//	continue;

	     					}catch (UnsupportedTagException e1) {

	     					} catch (InvalidDataException e1) {

	     						System.out.println("InvalidDataException "+pathFInder);

	     					} catch (IOException e1) {

	     						System.out.println("IOException");
	     						//continue;
	     					}


	     					if (!ssong.hasId3v2Tag()&&ssong!=null){
	     						ssong = null;
	     						System.out.println("Set To Null");


	     					}
	     					else if (ssong.hasId3v2Tag()){

//	     						}
	     					     ID3v2 id3v2tag = ssong.getId3v2Tag();
	     					    tittleIn=id3v2tag.getTitle();
	     					    albumIn=id3v2tag.getAlbum();
	     					    artistIn=id3v2tag.getArtist();
	     					    year=id3v2tag.getYear();
	     					    bpm=id3v2tag.getBPM();
	     					}


	     					//info.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY,Insets.EMPTY)));
	     					songinfo.setText("Artist : "+artistIn+"\nAlbum : "+albumIn+"\nTitle : "+tittleIn+"\nYear : "+year+"\nKbps : "+ssong.getBitrate()+"\n\n "+thing);

	     					//griddy.setFillHeight(track, true);

	     					songInfor=new PopOver();
	     					songInfor.setContentNode(info);
	     					songInfor.setMaxSize(20, 30);
	     					//songInfor.setHeaderAlwaysVisible(false);
	     					songInfor.arrowSizeProperty().set(1);
	     					songInfor.show(nodee,he.getScreenX(),he.getScreenX());
	     					((Parent)songInfor.getSkin().getNode()).getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	     					songInfor.show(nodee,he.getScreenX(),he.getScreenY());
	     					}
	     					nodee.setOnMouseClicked( el ->
	     					{
	     						songInfor.hide();
	     					});


					});

     		flows.setOnMouseClicked( ME ->
     		{

     			 he=ME;
     			//Albumadder();
     			getxx=ME.getX();
     			getyy=ME.getY();

     			//
     			  hit = flows.hit(getxx ,getyy  );
     			  if( !hit.isAfterCells()) {
     			  row = hit.getCellIndex();
     			   app = h.get(row);
     			   holdValue=app.fileLocationGet();
     			   nodee=(GridPane)flows.getCell(row).getNode();

     			// System.out.println(nodee.getChildren().get(2).);
     			if(ME.getButton()==MouseButton.PRIMARY /*& ME.getClickCount()==2*/) {
     			//	flows.getCell(row).selectedCell(app);
     				if(manogz==0) {
     	     			flowsy = VirtualFlow.createHorizontal(h, audioparser ->new AudioParserCell2(audioparser,h.indexOf(audioparser)) );
     	     			scrollt.setContent(flowsy);
     	     			flowsy.addEventFilter(ScrollEvent.SCROLL,new EventHandler<ScrollEvent>(){

     	         			@Override
     	         			public void handle(ScrollEvent event) {

     	         				if(event.getDeltaX() != 0) {

     	         					event.consume();
     	         				}

     	         			}

     	         		});
     	     			flowsy.setOnMouseClicked( eg-> {



     	     				if(eg.getClickCount()>=2) {
     	     					flows.show(row);
     	     				}


     	         		});

     	         		flowsy.setFocusTraversable(false);
     	     			playing=h;
     	     			manogz++;
     	     			}



     			//System.out.println("Clicked on "+row+" "+listViewDex);
     			//System.out.println(Runtime.getRuntime().totalMemory());

     			if(app.fileLocationGet()==current) {//&&mp.getStatus()==Status.PLAYING||mp.getStatus()==Status.PAUSED) {

     				//System.out.println(mp.getStatus());
     				  if(mp.getStatus()==Status.PLAYING) {
     					 //
     				  mp.pause();

     				  }
     				  else if(mp.getStatus()==Status.PAUSED||mp.getStatus()==Status.READY) {
     					 // System.out.println("Got In 2");
     					  mp.play();


     				  }
     				  else if(mp.getStatus()==Status.UNKNOWN||mp.getStatus()==Status.DISPOSED||mp.getStatus()==Status.STALLED){handlemouseClickPane(holdValue,row);
     				  System.out.println("Mp Unknown -");
     				  }
     			  }else {

     				if(holdValue!=null) {

     					Platform.runLater(()->{

        					 flowsy.showAtOffset(row,24);
        					// flowsy.showAtOffset(row,18);
        				});
     	     			Song(holdValue);
     	     			play jog=new play(holdValue,true);
     	     		//	trackMaster(oldValue);
     	     			//trackMaster(oldValue);


     	     	    }

     		   if((listViewDex<h.size())) {


     				GridPane gridd = (GridPane) flows.getCell(listViewDex).getNode();
     				TextFlow he = (TextFlow)gridd.getChildren().get(2);

					Text her =(Text)he.getChildren().get(0);
					Text herr =(Text)he.getChildren().get(1);
     				  Platform.runLater(() -> {
     					  gridd.setStyle("-fx-background-color: #191D1F;");
						her.setFill(Color.WHITE);
						herr.setFill(Color.GREY);
     				      });
     		   }
     		  listViewDex=row;
     		 try(
     				//FileWriter fw = new FileWriter("mydb.txt", true);
     			    //BufferedWriter bw = new BufferedWriter(new FileWriter("mydb.txt", true));
     			    PrintWriter out4 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/dex.txt", false))))
     			{

     			out4.println(listViewDex+"~"+position);


     			//out4.close();
     		} catch (IOException e) {
     			    //exception handling left as an exercise for the reader
     			}
     		//  GridPane grid = (GridPane) nodee;
//     		  Platform.runLater(() -> {
//
//     			  grid.setStyle("-fx-background-color: #39FF9F;");
//     		      });

     		  current=holdValue;

     			  }
     			}else if(ME.getButton()==MouseButton.SECONDARY) {


     				  //System.out.println(row);
     				 //   AudioParser app = h.get(row);
     				    System.gc();
     				 //  System.
     					 thing= app.fileLocationGet();
     					//information.getChildren().remove("fileLocale");

     					//{
     					 fileLocale.setText(/*thing+"\n"+*/(new File(thing).length()/1024)/1024+" Mbs"/*+"\n "+new File(thing).lastModified()*/);
     					//}



     					  popover=new PopOver(information);
							//   popover.setContentNode(tracklist);
							  // popover.setContentNode(shower);
							   //popover.setPrefSize(100, 100);
							   popover.setArrowSize(0);
     						Platform.runLater(()->{



     							   popover.show(sorter,ME.getScreenX(),ME.getScreenY());
     							   ((Parent)popover.getSkin().getNode()).getStylesheets().add(getClass().getResource("application.css").toExternalForm());
     							//	((Parent)popover.getSkin().getNode()).getStylesheets().add(getClass().getResource("application.css").toExternalForm());
     								popover.show(sorter,ME.getScreenX(),ME.getScreenY());
     						});






     			}
     		}} );


    	//rootPane.set

     		rootPane.setContent(BPane);
     		new Timeline(
					new KeyFrame(
							Duration.seconds(2),gg ->{



								TYming = false ;



							})).play();

	    	}else {if(!list.isEmpty()) {Vpane(list);}}
	    }
	    String year1 = null ;
	    String year23 = null ;
	    Text albumYear=new Text();
	    Text albumArtists=new Text();

	    public Node monroe ;
	   // GridPane gridder = new GridPane();
	    //Node

	    public Boolean Vpaen23 = false ;
	    public void Vpane2(ObservableList<AudioParser> h,int SongNumber) {
	    	if(!(h.isEmpty())) {
	    		//h.
	    		lazyLoad.add(0,new AudioParser("Album Header","","","","","","",""));
	    		Vpaen23 = true ;
	    		TYming = true;
	    		albumYear=new Text("");
	    		albumArtists=new Text("");


	    		rootPane.setContent(BPane);
	    		//BPane.cen
		    	// BPane.setPadding(new Insets(3, 0, 3, 0));
	    		//flows.add
		    	 flows = VirtualFlow.createVertical(h, audioparser ->new AudioParserCell(audioparser,0) );

		    	 listScrollPane=new VirtualizedScrollPane<>(flows);

	     	  //   listScrollPane.setPrefSize(rootPane.getWidth(), rootPane.getHeight());
	     	   // listScrollPane.setPrefSize(listScrollPane.getPrefWidth(), rootPane.getHeight()-125.0);
	     	   listScrollPane.setPrefSize(rootPane.getWidth(), rootPane.getHeight()-125.0);
	     	   listScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER );


	     	    ImageView icond = new ImageView();
	     	   Text Albumname=new Text(h.get(1).albumGet());

	    		icond.minHeight(100);
	    		icond.minWidth(100);
	    		icond.maxHeight(100);
	    		icond.maxWidth(100);
	    		icond.setSmooth(true);
				   // icond.setPreserveRatio(true);
				icond.setCache(false);
				icond.setFitHeight(100);
				icond.setFitWidth(100);
	    		icond.setImage(new ImageView("application/Image/A5.png").getImage());
	    		albumYear.getStyleClass().add("albumsNameAndYear");
	    		Albumname.getStyleClass().add("albumsNameAndYear");

	    		HBox goner=new HBox();

	    		//////Artists
	    		year23 = null;
	    		if(!tempor.isEmpty()) {
	    			year23=tempor.toString();
	    		//	year23.re
	    			//year23.replace("[", "");
	    		/*for(int i=0 ;i <=tempor.size();i++) {


	    		//	if(year23==null) {

	    			/*}else {
	    			year23.concat(artist);

    				System.out.print(year23);
	    			}*/

	    		//}

	    		}
	    		Task<Void> consumerTask = new Task<Void>() {


				    @Override

				    protected Void call() throws Exception {



				    	albumYear.setFill(Color.SILVER);
					    albumArtists.setFill(Color.GHOSTWHITE);
				    	BufferedImage img= null;
				    	WritableImage wr = null;
						Mp3File song = null;


				try {
					if(h.get(1)!=null) {
					song = new Mp3File(h.get(1).fileLocationGet());
					}
				} catch (UnsupportedTagException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				} catch (InvalidDataException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}

				if (song.hasId3v2Tag()){
				     ID3v2 id3v2tag = song.getId3v2Tag();
				     byte[] imageData = id3v2tag.getAlbumImage();

				     if(imageData!=null) {
					     //converting the bytes to an image
					     try {
							img = ImageIO.read(new ByteArrayInputStream(imageData));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							icond.setImage(new ImageView("application/Image/A5.png").getImage());
							e.printStackTrace();
						}

					     Image img1 = SwingFXUtils.toFXImage(img,null);

						    // image.setImage(new ImageView(img1).getImage());
						     //FadeTransition ft = new FadeTransition(Duration.millis(3000), image);
						    // Group group = new Group();
						    // int width=(int)image.getFitWidth();
						     //int height=(int)image.getFitHeight();


						    // rect.ArcWidth(30.0);
						     //group.getChildren().addAll(rect);
						     Platform.runLater(() -> {
						    	 icond.setImage(img1);
							     FadeTransition ft = new FadeTransition();
							      ft.setNode(icond);
							      ft.setDuration(new Duration(400));
							      ft.setFromValue(0.0);
							      ft.setToValue(1.0);
							      ft.setAutoReverse(false);
							      ft.play();
						        });

				    String gggg = id3v2tag.getYear();
				    albumArtists.setText(year23);
				     albumYear.setText("("+gggg.toString()+")");
				     if(gggg==null) {
				    	 ID3v1 id3v1tag = song.getId3v2Tag();
				    	 albumYear.setText("("+id3v1tag.getYear()+")");
				     }

				 /*    if(id3v2tag.getArtist()==null) {
				    	 ID3v1 id3v1tag = song.getId3v2Tag();
				    	 albumArtists.setText(id3v1tag.getArtist());
				    	 albumArtists.setFill(Color.GHOSTWHITE);
				    	// System.out.println("id3v1 -"+id3v1tag.getArtist());
				     }*/
				   //  System.out.println("id3v2 -"+id3v2tag.getAlbumArtist());


				     //convertToFxImage(img);
				   //  Image image2 = img;
				   //  image.setImage(img);}





//				     KeyFrame keyFrame1On = new KeyFrame(Duration.seconds(0), new KeyValue(image.imageProperty(), image1));
//				     KeyFrame startFadeOut = new KeyFrame(Duration.seconds(0.2), new KeyValue(image.opacityProperty(), 1.0));
//				     KeyFrame endFadeOut = new KeyFrame(Duration.seconds(0.5), new KeyValue(image.opacityProperty(), 0.0));
//				     KeyFrame keyFrame2On = new KeyFrame(Duration.seconds(0.5), new KeyValue(image.imageProperty(), image2));
//				     KeyFrame endFadeIn = new KeyFrame(Duration.seconds(0.8), new KeyValue(image.opacityProperty(), 1.0));
//				     Timeline timelineOn = new Timeline(keyFrame1On, startFadeOut, endFadeOut, keyFrame2On, endFadeIn);




				}else {//image.setImage("Image/A5.png");


				//Platform.runLater(() -> {
				//	albumYear = new Text("");
				//	albumArtists = new Text("");
					//albumArtists.resize(width, height);
					icond.setImage(new ImageView("application/Image/A5.png").getImage());

				   // goner.getChildren().add(icond);
			     //   });
				}

				}else {//image.setImage("Image/A5.png");

					Platform.runLater(() -> {
				    	 icond.setImage(new ImageView(h.get(1).ImageGet()).getImage());




				    //	 image.setFitWidth(190);

				    	// icon.setPreserveRatio(true);

				    	 //rect=new Rectangle((int)image.getFitWidth(),(int)image.getFitHeight());
					     FadeTransition ft = new FadeTransition();
					      ft.setNode(icond);
					      ft.setDuration(new Duration(400));
					      ft.setFromValue(0.0);
					      ft.setToValue(1.0);
					      ft.setAutoReverse(false);
					      ft.play();
				        });
				     //   });
					}
				return null;

				    }



				};

				imageSetter.execute(consumerTask);

	    		manogz=0;
	    	//	albumYear = new Text("");
			//	albumArtists = new Text("");
	    		Rectangle rect = new Rectangle();
	    		GridPane gridder = new GridPane();
	    		//TextFlow textFlowPane = new TextFlow();
	    		//gridder.setStyle("-fx-background-color: #32393D;");
	    		gridder.setPrefSize(418, 125);
	    		//gridder.setMaxSize(420, 100);
	    		gridder.setMinSize(418, 125);
	    		gridder.setHgap(20);



	    		Text gotArtist=new Text("Go To Artist");
	  		    Text songInfo=new Text("Song Info");
	  		    Text fileLocale=new Text(/*thing+"\n"+*/" Mbs"/*+"\n "+new File(thing).lastModified()*/);

	    		Text SongNo=new Text("\n"+SongNumber+" Songs");
	    		if(SongNumber<=1) {
	    			SongNo=new Text("\n"+SongNumber+" Track");
	    		}

	    		SongNo.setFill(Color.SILVER);
	    		//Albumname.getStyleClass().add("albumsNameAndYear");
	    		SongNo.getStyleClass().add("lowerText");
	    		Albumname.setFill(Color.WHITE);
	    		//Albumname.getStyleClass().add("albumName");



	    		goner.setAlignment(Pos.CENTER_RIGHT);
	    		goner.getChildren().add(icond);
	    	//	goner.setPadding(new Insets(0,0,0,0));
	    	//	goner.setPadding(new Insets(0, 0, 0, 15));




				Albumname.maxWidth(90);
				//albumArtists.maxHeight(10);

				//Albumname
			//	Albumname.maxWidth(90);
				//Albumname.maxHeight(10);
				//textFlowPane.
				//textFlowPane.setPadding(new Insets(20,5,20,0));

				GridPane joker =new GridPane();
				//joker.setGridLinesVisible(true);
				joker.setPrefSize(280,150);
				joker.setMinSize(280,100);
				joker.setMaxSize(280,100);
				//joker.setVgap(4);
				joker.setPadding(new Insets(20,0,0,0));

				//joker.setGridLinesVisible(true);
			 	//Image imgurra=new Image(System.getProperty("user.home")+"/ilix/A5.png");





				//textFlowPane.spa
				//textFlowPane.getChildren().add(Albumname);
				//textFlowPane.getChildren().add(1,albumYear);
				//textFlowPane.getChildren().add(1, albumYear);
				FlowPane jake = new FlowPane();
				//jake.
				//jake.setStyle("-fx-border-color:white;");
				jake.setMaxSize(270, 5);
				jake.setHgap(5);
				jake.getChildren().addAll(Albumname,albumYear);
				//jake.setMinSize(200, 5);
				joker.setHgap(3);
				joker.add(jake, 0, 0);
				//joker.add(albumYear, 1, 0);

				//joker.setRowSpan(textFlowPane, 20);
				//joker.setColumnSpan(Albumname, 20);
				//joker.add(albumYear, 1, 0);
				joker.add(albumArtists, 0, 1);
				//joker.setValignment(albumArtists, VPos.TOP);
				//joker.setRowSpan(albumArtists, 20);
				joker.add(SongNo, 0, 2);
				//joker.setValignment(SongNo, VPos.TOP);

				//joker.setRowSpan(SongNo, 20);

      		    rect.setArcWidth(14);
      		    rect.setArcHeight(14);
				rect.widthProperty().bind(goner.widthProperty());
				rect.heightProperty().bind(goner.heightProperty());
				goner.setClip(rect);

				gridder.add(goner,0,0);
				gridder.add(joker, 1, 0);
				gridder.setPadding(new Insets(12,80,10,30));
				//gridder.setGridLinesVisible(true);
				gridder.getStyleClass().add("kunfu");
				System.gc();
				monroe =gridder;
		//	BPane.setMinSize(606.0, 606.0);


     	  //  BPane.setTop(gridder);
     	    BPane.setCenter(listScrollPane);

     	    //BPane.setBottom(listScrollPane);

     	//   Text gotAlbum=new Text("Go To Album");

     	   GridPane info = new GridPane();
			info.setId("info");
			info.setPadding(new Insets(10));
			info.setCache(true);
			info.setCacheHint(CacheHint.SPEED);
			info.setHgap(1);

			Text songinfo=new Text();
 			songinfo.setFill(Color.valueOf("#39FF9F"));
 			info.add(songinfo,0,0);

	     GridPane information=new GridPane();
		  	information.setPrefHeight(100);
		  	information.setPrefWidth(80);
		  	information.setMaxWidth(80);
		  	information.setPadding(new Insets(10));
		  	information.getStyleClass().add("optionsGrid");
		// gotAlbum.setFill(Color.WHITE);
			gotArtist.setFill(Color.WHITE);
			songInfo.setFill(Color.WHITE);
			fileLocale.setFill(Color.GREY);

			gotArtist.getStyleClass().add("infoTxt");
			songInfo.getStyleClass().add("infoTxt");

			fileLocale.getStyleClass().add("lowerText2");

			information.setVgap(5);
			//information.add(gotAlbum, 0, 0);
			information.add(gotArtist, 0, 1);
			information.add(songInfo, 0, 2);
			information.add(fileLocale, 0, 4);

     		//scrollt.setContent(flowsy);

			gotArtist.setOnMouseClicked(event->{
				if(app!=null) {
				beforeAlbum=listViewDex;

				popover.hide();
				goTOArtist(app.artistGet());


				}});



     		flows.setOnMouseClicked( ME ->
     		{
     			//Albumadder();

     			getxx=ME.getX();
     			getyy=ME.getY();
     			totalSongs=h.size()-1;
     			//
     			 hit = flows.hit(getxx ,getyy  );
     			if(!hit.isAfterCells()/*||hit.isBeforeCells()*/) {
     			  row = hit.getCellIndex();
     			  AudioParser ap = h.get(row);
     			  app=ap;
     			  String oldValue=ap.fileLocationGet();
     			 GridPane node=(GridPane)flows.getCell(row).getNode();

     			// SonfName.setText(parser.nameGet());
     			//lenght.setText(parser.lengthGet());
     			//SonfName2.setText("\r\n"+parser.artistGet());
     			 // AudioParser ap = h.get(number);
     			if(ap.nameGet() == "Album Header") {


 				}else if(ME.getButton()==MouseButton.PRIMARY) {

     				if(manogz==0) {
             			flowsy = VirtualFlow.createHorizontal(h, audioparser ->new AudioParserCell2(audioparser,h.indexOf(audioparser)) );
             			scrollt.setContent(flowsy);
             			flowsy.setFocusTraversable(false);
                 		//flowsy = VirtualFlow.createHorizontal(h, audioparser ->new AudioParserCell2(audioparser) );

            	     		flowsy.addEventFilter(ScrollEvent.SCROLL,new EventHandler<ScrollEvent>(){

            	     			@Override
            	     			public void handle(ScrollEvent event) {

            	     				if(event.getDeltaX() != 0) {

            	     					event.consume();
            	     				}

            	     			}

            	     		});
            	     	beforeAlbum=0;
             			playing=h;
             			manogz++;
             			}
     				//Bounds boundsr=image.getBoundsInParent();

    	   		 if(filteredData.size()-1==row) {
    	   		   flowsy.showAtOffset(row,24);
    	   		// flowsy.showAtOffset(row,18);
    	   			System.out.println("Clicked last");
        		  }else if(0==row) {
        			  System.out.println("Clicked First");
        			  flowsy.showAtOffset(row,24);
        			//  flowsy.showAtOffset(row,18);
        		  }else {
        			  flowsy.showAtOffset(row,24);
        			//  flowsy.showAtOffset(row,18);
        		  }
     			//System.out.println("Clicked "+row+" "+listViewDex);
     			//System.out.println(Runtime.getRuntime().totalMemory());

     			if(ap.fileLocationGet()==current) {//&&mp.getStatus()==Status.PLAYING||mp.getStatus()==Status.PAUSED) {


     				System.out.println(mp.getStatus());
     				  if(mp.getStatus()==Status.PLAYING) {
     					 //
     				  mp.pause();

     				  }
     				  else if(mp.getStatus()==Status.PAUSED||mp.getStatus()==Status.READY) {

     					  mp.play();


     				  }
     				  else if(mp.getStatus()==Status.UNKNOWN||mp.getStatus()==Status.DISPOSED||mp.getStatus()==Status.STALLED){handlemouseClickPane(oldValue,row);
     				  System.out.println("Mp Unknown -");
     				  }
     			  }else {
//     		   if((listViewDex<h.size())) {
//
//     			  // Node nodejs=;
//     				GridPane gridd = (GridPane) flows.getCell(listViewDex).getNode();
//     				  Platform.runLater(() -> {
//     					  gridd.setStyle("-fx-background-color: #191D1F;");
//     				      });
//     		   }else if(awareness<=h.size()) {
//
//
//   				GridPane gridd = (GridPane) flows.getCell(awareness).getNode();
//   				  Platform.runLater(() -> {
//   					  gridd.setStyle("-fx-background-color: #191D1F;");
//   				      });
//     		   }
     				 if((listViewDex<h.size())) {


          				GridPane gridd = (GridPane) flows.getCell(listViewDex).getNode();
          				TextFlow he = (TextFlow)gridd.getChildren().get(2);

     					Text her =(Text)he.getChildren().get(0);
     					Text herr =(Text)he.getChildren().get(1);
          				  Platform.runLater(() -> {
          					  gridd.setStyle("-fx-background-color: #191D1F;");
     						her.setFill(Color.WHITE);
     						herr.setFill(Color.GREY);
          				      });
          		   }


     		    current=oldValue;
     			String thing= ap.fileLocationGet();
     	/*	  GridPane grid = (GridPane) node;
     		  Platform.runLater(() -> {
     			  grid.setStyle("-fx-background-color: #39FF9F;");
     		      });*/


     		//  handlemouseClickPane(oldValue,row);
     		 Song(oldValue);
   			play jog=new play(oldValue,true);
   			//listViewDex=Num;
     		  listViewDex=row;

     		  //System.out.println(row);
     			  }
     			}else if(ME.getButton()==MouseButton.SECONDARY) {




   				  //System.out.println(row);
   				 //   AudioParser ap = h.get(row);
   				    System.gc();
   				 //  System.
   					String thing= ap.fileLocationGet();

   					fileLocale.setText(/*thing+"\n"+*/(new File(thing).length()/1024)/1024+" Mbs"/*+"\n "+new File(thing).lastModified()*/);

   				/*	gotAlbum.setOnMouseClicked(event->{
   						albumSongs=0;
   						popover.hide();
							lazyLoad.clear();

				        		list.forEach(mans->{
				        			if(ap.albumGet().equalsIgnoreCase(mans.albumGet())) {
				        				albumSongs++;
				        				//lengthr+=Integer.parseInt(ap.lengthget);
				        				 lazyLoad.add(mans);
				        			//i++;
				        			}
				        		});
				        		Collections.sort(lazyLoad, comparatorbyTrack);
				        		//Collections.sort(lazyLoad, comparatorbyTrack);
//				        		filteredData.clear();
//				        		filteredData = new FilteredList<>(lazyLoad, t -> true);
				        		try{
				        		ReturnNumbers("StartUp",albumSongs);}catch(IOException fi){

				        		}

				        		filteredData=new FilteredList<>(lazyLoad,s->true);
				        		mok=filteredData.size()-1;
				        		Vpane2(filteredData,albumSongs);


   					});*/




   					songInfo.setOnMouseClicked(event->{

   						popover.hide();
							if(thing!=null) {
		     					try {
		     						ssong = new Mp3File(thing) ;

		     					} catch(InvalidPathException e4){

		     						//System.out.println("InvalidPathException");
		     						System.err.println("This should never happen: " + e4);
		     						//continue;

		     					//	continue;

		     					}catch (UnsupportedTagException e1) {

		     					} catch (InvalidDataException e1) {

		     						System.out.println("InvalidDataException "+pathFInder);

		     					} catch (IOException e1) {

		     						System.out.println("IOException");
		     						//continue;
		     					}


		     					if (!ssong.hasId3v2Tag()&&ssong!=null){
		     						ssong = null;
		     						System.out.println("Set To Null");


		     					}
		     					else if (ssong.hasId3v2Tag()){

//		     						}
		     					     ID3v2 id3v2tag = ssong.getId3v2Tag();
		     					    tittleIn=id3v2tag.getTitle();
		     					    albumIn=id3v2tag.getAlbum();
		     					    artistIn=id3v2tag.getArtist();
		     					    year=id3v2tag.getYear();
		     					    bpm=id3v2tag.getBPM();
		     					}


		     					//info.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY,Insets.EMPTY)));
		     					songinfo.setText("Artist : "+artistIn+"\nAlbum : "+albumIn+"\nTitle : "+tittleIn+"\nYear : "+year+"\nKbps : "+ssong.getBitrate()+"\n\n "+thing);


		     					//griddy.setFillHeight(track, true);

		     					songInfor=new PopOver();
		     					songInfor.setContentNode(info);
		     					songInfor.setMaxSize(20, 30);
		     					//songInfor.setHeaderAlwaysVisible(false);
		     					songInfor.arrowSizeProperty().set(1);
		     					songInfor.show(node,ME.getScreenX(),ME.getScreenX());
		     					((Parent)songInfor.getSkin().getNode()).getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		     					songInfor.show(node,ME.getScreenX(),ME.getScreenY());
		     					}
		     					node.setOnMouseClicked( el ->
		     					{
		     						songInfor.hide();
		     					});


   					});


   						Platform.runLater(()->{


   							  popover=new PopOver(information);
   							//   popover.setContentNode(tracklist);
   							  // popover.setContentNode(shower);
   							   //popover.setPrefSize(100, 100);
   							   popover.setArrowSize(0);
   							   popover.show(sorter,ME.getScreenX(),ME.getScreenY());
   							   ((Parent)popover.getSkin().getNode()).getStylesheets().add(getClass().getResource("application.css").toExternalForm());
   							//	((Parent)popover.getSkin().getNode()).getStylesheets().add(getClass().getResource("application.css").toExternalForm());
   								popover.show(sorter,ME.getScreenX(),ME.getScreenY());
   						});






   			}

     		} });






	    	}

	    }

	    public Node getGridder() {

	    	return monroe;

	    }
	public void BringItON3() throws IOException {
				String title;
				BufferedReader in = null;
				try {
					in = new BufferedReader(new FileReader(System.getProperty("user.home")+"/ilix/mydb.txt"), 16*1024);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}



				while (true) {
				String line = null;
					line = in.readLine();

				if (line == null) break;
				String data = line;
				title = data;
				Folders2.add(title);
				}
				in.close();
	        }
//BRINGS IN FOLDERS EXTACTED
	public void BringItON4() throws IOException {
		String title;
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(System.getProperty("user.home")+"/ilix/folders.txt"), 16*1024);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		while (true) {
		String line = null;
			line = in.readLine();

		if (line == null) break;
		String data = line;
		title = data;
		Folders.add(title);
		}
		in.close();
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	//ReturnNumbers(String key,int numbers)
	public void ReturnNumbers(String key,int numbers) throws IOException {
		int output=0;
		BufferedReader in = null;
		String line = null,data;
		switch (key) {
	case "StartUp":

		Platform.runLater(()->{
		totalSongs=numbers;
		totalSngs.setText(String.valueOf(totalSongs+" Songs"));
		});

		break;
	case "AddTrack":

		try(FileWriter fw = new FileWriter(System.getProperty("user.home")+"/ilix/.txt");
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			{
			out.println(totalSongs);  //exception handling left as an exercise for the reader
			bw.close();
			}
		totalSngs.setText(String.valueOf(String.valueOf(totalSongs+" Songs-added "+numbers)));
		break;
	case "Refresh":

		totalSongs=0;
		totalSngs.setText(String.valueOf(String.valueOf(totalSongs+" Songs")));
		break;


	default:

		break;

		}



	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////]
	String ViewState=null;
	public void StateL(String key) throws IOException {

		switch (key) {
	case "songs":


		break;
	case "albums":


		break;
	case "artist":



		break;


	default:

		break;

		}



	}




	//////////////////////////////////
int rate=-1;

    public Boolean Beauty=false;



//private ObservableList<Line> logLines;



//private Text getNodeFromGridPane(Node node){
//
//	Text bar=null;
//
//
//     Set<Node> gridpaneNodes = node.lookupAll("Text");
//	  for (Node text1 : gridpaneNodes) {
//		  if (text1 instanceof Text) {
//			  bar = (Text) text1;
//			  System.out.println(bar.getId());
//
//          }
//  }
//		 System.out.println("Hooray");
//		 return bar;
//
//
//	  }



//listView = VirtualFlow.createVertical(List, (AudioParser) -> Cell.wrapNode(new LineCell(AudioParser)));
//listScrollPane = new VirtualizedScrollPane<>(listView);
double getxx=0;//7
double getyy=0;
int row =-2;


@FXML
public void SORT() {


}

private void songPlaytime(String key) {
	// As previously shown...
	switch (key) {
	case "shuffle":
		break;
		//shuffle=1;
	case "playListToEnd":


			playListToEnd=true;
			System.out.println("playListToEnd is oN");
			//repeatB.setImage(new Image("application/Image/oofRepeat.png"));
			repeatB.setImage(new Image("application/Image/stopOnlast.png"));
			//repeatB.setImage(new Image("application/Image/oNRepeat.png"));
			break;

	case "repeatList":
		//repeatList=1;

			repeatList=true;
			System.out.println("repeatList is oN");
			//repeatB.setImage(new Image("application/Image/oofRepeat.png"));
			repeatB.setImage(new Image("application/Image/stopOnlast.png"));
			//repeatB.setImage(new Image("application/Image/oNRepeat.png"));
		break;

	case "repeatSong":

			repeatIson=true;
			System.out.println("Repeat is oN");

			repeatB.setImage(new Image("application/Image/oNRepeat.png"));
		break;

	default:
		//image.setImage("Image/A5.png");
		break;
	}
}


Scanner read4 = null;
@FXML
public void SongsView() {

	 System.gc();
	 Platform.runLater(()->{totalSngs.setText(String.valueOf(String.valueOf((list.size()+1)+" Songs")));});
	 filteredData=new FilteredList<>(list,s->true);
	/*else {
		 try {
		;
		 read4 = new Scanner (new BufferedReader(new FileReader(System.getProperty("user.home")+"/ilix/songviewpos.txt"), 16*1024));
		 String jimmy = read4.next();


	    ScrollBar bar = (ScrollBar) flows.lookup(".scroll-bar");
	    bar.setValue(Double.parseDouble(jimmy));
	    System.out.println(Double.parseDouble(jimmy));
		 read4.close();

		 }
		 catch(IOException g) {

		 }
	 }*/


	 Vpane(filteredData);

	// if(playing==filteredData) {
		 System.out.println("beforeAlbum - "+beforeAlbum);
			// flowsy.showAtOffset(listViewDex,18);
			 if(beforeAlbum!=0) {
				 flows.show(beforeAlbum);
			     flowsy.showAtOffset(beforeAlbum,24);
			 }else {

				 flowsy.showAtOffset(0,24);
			 }

		// }
/*	 if(!filteredData.isEmpty()&&listViewDex>=filteredData.size()) {
		 flows.show(listViewDex);

	 }*/

	 try
		{
	    PrintWriter out5 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/phase.txt", false)));
		out5.println("MAIN~non~non");
		out5.close();
		//out4.close();
	} catch (IOException j) {


	}



}


public class MyButtonSkin extends ButtonSkin {

    public MyButtonSkin(Button control) {
        super(control);

        final FadeTransition fadeIn = new FadeTransition(Duration.millis(100));
        fadeIn.setNode(control);
        fadeIn.setToValue(1);
        control.setOnMouseEntered(e -> fadeIn.playFromStart());

        final FadeTransition fadeOut = new FadeTransition(Duration.millis(100));
        fadeOut.setNode(control);
        fadeOut.setToValue(0.5);
        control.setOnMouseExited(e -> fadeOut.playFromStart());

        control.setOpacity(0.5);
    }

}


int roww=0;
ChoiceBox<String> choicebox = new ChoiceBox();
PopOver songInfor=null;
public void setMin() {





	//panela.setHgap(5);
	Text rrss = new Text("Repeat Song");
	Text rrsss = new Text("Repeat List");
	Text rrssss = new Text("Play to List End");
	rrss.setFill(Color.WHITE);
	rrsss.setFill(Color.WHITE);
	rrssss.setFill(Color.WHITE);
//
//	Image firstone = new Image("application/Image/oofRepeat.png",20,20,true,true,true);
//
//	Image firsttwo = new Image("application/Image/repeatList.png",20,20,true,true,true);
//
//	Image firstthree = new Image("application/Image/stopOnlast.png",20,20,true,true,true);
	//repeatList
	//stopOnlast

    songss.setOnMouseClicked(new EventHandler<MouseEvent>(){

		@Override
		public void handle(MouseEvent event) {
			// TODO Auto-generated method stub
			Platform.runLater(()->{
			songss.setStyle("-fx-border-color:#39FF9F;");

			//revert others to normal state  Border->#191D1F
			albumss.setStyle("-fx-border-color:#191D1F;");
			artists.setStyle("-fx-border-color:#191D1F;");

			});
		}


    });


	albumss.setOnMouseClicked(new EventHandler<MouseEvent>(){

		@Override
		public void handle(MouseEvent event) {
			// TODO Auto-generated method stub
			Platform.runLater(()->{
			albumss.setStyle("-fx-border-color:#39FF9F;");

			//revert others to normal state Border->#191D1F
			songss.setStyle("-fx-border-color:#191D1F;");
			artists.setStyle("-fx-border-color:#191D1F;");

			});
		}


    });

	artists.setOnMouseClicked(new EventHandler<MouseEvent>(){

		@Override
		public void handle(MouseEvent event) {
			// TODO Auto-generated method stub
			Platform.runLater(()->{
			artists.setStyle("-fx-border-color:#39FF9F;-fx-border-width:2;");

			//revert others to normal state Border->#191D1F
			songss.setStyle("-fx-border-color:#191D1F;");
			albumss.setStyle("-fx-border-color:#191D1F;");

			});
		}


    });


	rrss.setOnMouseClicked(new EventHandler<MouseEvent>(){
		  @Override
			public void handle(MouseEvent event) {
		if(repeatIson==true) {

			repeatIson=false;
			repeatB.setImage(new Image("application/Image/oofRepeat.png"));

			try(

				    PrintWriter out4 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/repeat.txt", false))))
				{

				out4.println(false);


				out4.close();
				} catch (IOException e1) {
				    //exception handling left as an exercise for the reader
				}
			List.hide();

		}else if(repeatIson==false) {
			if(playListToEnd==true) {
				playListToEnd=false;

			}
			if(repeatList==true) {
				repeatList=false;

			}
			repeatIson=true;
			repeatB.setImage(new Image("application/Image/oNRepeat.png"));
			try(

				    PrintWriter out4 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/repeat.txt", false))))
				{

				out4.println("repeatSong");
				out4.close();
				} catch (IOException e1) {
				    //exception handling left as an exercise for the reader
				}
			List.hide();
		} }
	});


	rrsss.setOnMouseClicked(new EventHandler<MouseEvent>(){
		  @Override
			public void handle(MouseEvent event) {
		if(repeatList==true) {

			repeatList=false;

			repeatB.setImage(new Image("application/Image/stopOnlast.png"));

			try(

				    PrintWriter out4 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/repeat.txt", false))))
				{

				out4.println(false);


				out4.close();
				} catch (IOException e1) {
				    //exception handling left as an exercise for the reader
				}
			List.hide();

		}else if(repeatList==false) {
			if(playListToEnd==true) {
				playListToEnd=false;

			}
			if(repeatIson==true) {
				repeatIson=false;

			}
			repeatList=true;
			repeatB.setImage(new Image("application/Image/repeatList.png"));
			try(

				    PrintWriter out4 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/repeat.txt", false))))
				{

				out4.println("repeatList");
				out4.close();
				} catch (IOException e1) {
				    //exception handling left as an exercise for the reader
				}
			List.hide();
		} }
	});



	rrssss.setOnMouseClicked(new EventHandler<MouseEvent>(){
		  @Override
			public void handle(MouseEvent event) {
		if(playListToEnd==true) {

			playListToEnd=false;
			repeatB.setImage(new Image("application/Image/repeatList.png"));

			try(

				    PrintWriter out4 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/repeat.txt", false))))
				{

				out4.println(false);


				out4.close();
				} catch (IOException e1) {
				    //exception handling left as an exercise for the reader
				}
			List.hide();
		}else if(playListToEnd==false) {


			if(repeatList==true) {
				repeatList=false;

			}
			if(repeatIson==true) {
				repeatIson=false;

			}
			playListToEnd=true;
			repeatB.setImage(new Image("application/Image/stopOnlast.png"));
			try(

				    PrintWriter out4 = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/ilix/repeat.txt", false))))
				{

				out4.println("playListToEnd");
				out4.close();
				} catch (IOException e1) {
				    //exception handling left as an exercise for the reader
				}
			List.hide();
		} }
	});
	GridPane panela=new GridPane();

	panela.setPrefHeight(100);
	panela.setPrefWidth(110);
	panela.setPadding(new Insets(15));
	panela.setVgap(5);
	panela.setHgap(8);
	panela.add(new ImageView(new Image("application/Image/oNRepeat.png",20,20,true,true,true)) , 0, 0);
	panela.add(rrss, 3, 0);
	panela.add( new ImageView(new Image("application/Image/repeatList.png")), 0, 1);
	panela.add(rrsss, 3, 1 );
	panela.add( new ImageView(new Image("application/Image/stopOnlast.png")), 0, 2);
	panela.add(rrssss, 3, 2 );
	repeatB.setOnMouseClicked(eff->{



			Platform.runLater(()->{
			//	List = new PopOver();
			List.setContentNode(panela);
			List.arrowSizeProperty().set(1);
			//List.show(albumy);
			List.show(albumy,  eff.getScreenX(), eff.getScreenY());
			((Parent)List.getSkin().getNode()).getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			List.show(albumy,  eff.getScreenX(), eff.getScreenY());

			});

		});
	minimize.setOnAction(new EventHandler<ActionEvent>() {

        @Override
		public void handle(ActionEvent event) {
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            // is stage minimizable into task bar. (true | false)
            stage.setIconified(true);
        }
    });
//	minimize.setOnMouseEntered(eg->{
//
//		minimize.setStyle("fx-background-color: orange ;fx-background-radius:40;fx-background-insets :3;fx-border-color : orange");
//	});
//	minimize.setOnMouseExited(eg->{
//		minimize.setStyle("fx-background-color: #39FF9F ;fx-background-radius:40;fx-background-insets :3;fx-border-color : #39FF9F");
//		//minimize.setStyle("fx-background-color: #39FF9F");
//	});


	ListView<String> tracklist=new ListView<String>();
	tracklist.setPrefSize(102, 170);
	tracklist.setPadding(new Insets(5));
	tracklist.setCellFactory(new Callback<ListView<String>, ListCell<String>>(){

	    @Override
		public ListCell<String> call(ListView<String> tv) {
	    //    return new ListCell<AudioParser>() {
	    		return new ListCell<String>() {

	            @Override
	            protected void updateItem(String item, boolean empty) {
	                super.updateItem(item, empty);
	                if (item != null) {
//	                    setText(item);
	                    GridPane griddy = new GridPane();
	        			griddy.setId("New Name");
	        			griddy.setPadding(new Insets(5));
	        			griddy.setCache(true);
	        			griddy.setCacheHint(CacheHint.SPEED);
	        			griddy.setHgap(1);
	        			Text name=new Text(item);
	        			//name.setStyle("-fx-fill-color: white;");
	        			name.getStyleClass().add("my-text");
	        			//griddy.setStyle("-fx-background-color: #191D1F;");

	        			//griddy.setFillHeight(track, true);
	        			griddy.add(name,0,0);
	        			//griddy.getStyleClass().add("my-text2");
	        			setGraphic(griddy);
	                }
	            }
	        };


	}});

	sorter.setOnMouseClicked( ME->{
		System.gc();
		tracklist.getStyleClass().add("Listview");
		//List<String> fig=("","","");
		ObservableList<String> fig = FXCollections.observableArrayList();;
		fig.add("Songs");
		fig.add("Albums");
		fig.add("Artists");
		fig.add("Date");
		fig.add("Track");
		//tracklist.setItems(fig);
		 popover=new PopOver();




		// tracklist.
		tracklist.setItems(fig);




	tracklist.setOnMouseClicked(f->{

		if(tracklist.getSelectionModel().getSelectedIndex()==0) {


				songs();
				popover.hide();
				System.gc();


		}
		else if(tracklist.getSelectionModel().getSelectedIndex()==1) {
			album();
			popover.hide();
			System.gc();
		}
		else if(tracklist.getSelectionModel().getSelectedIndex()==2) {
			artist();
			popover.hide();
			System.gc();
}
		else if(tracklist.getSelectionModel().getSelectedIndex()==3) {
			dated();
			popover.hide();
			System.gc();
}else if(tracklist.getSelectionModel().getSelectedIndex()==4) {
	Collections.sort(lazyLoad ,comparatorbyTrack);
	popover.hide();
	System.gc();
}

	});




		Platform.runLater(()->{

			   popover.setContentNode(tracklist);
			   popover.setArrowSize(0);
			   popover.show(sorter,ME.getScreenX(),ME.getScreenX());
			   ((Parent)popover.getSkin().getNode()).getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			//	((Parent)popover.getSkin().getNode()).getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				popover.show(sorter,ME.getScreenX(),ME.getScreenY());
		});



});


addNewTrack.setOnMouseClicked( mouseEvent->{
	tracklist.getStylesheets().add(getClass().getResource("playlist.css").toExternalForm());
		System.out.println("Clicked On AddTrack/S");
		FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File f, String name) {
                // We want to find only ,mp3 files
                return name.endsWith(".mp3");
            }};
		//directory chooser so user can select folder
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Select Music Folder");
		directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")+"\\Music"));
//		directoryChooser.setMultiSelectionEnabled(true);
//		directoryChooser.setFileSelectionMode(DirectoryChooser.DIRECTORIES);
		File selectedDirectory = directoryChooser.showDialog(Main.stage);
		if (selectedDirectory == null) {
			  System.out.println("Defaulted to Music Folder");


			  try {
				  extract(new File(System.getProperty("user.home")+"\\Music"));}catch(IOException f) {
					f.printStackTrace();
				}

			}
			else {
			 path = selectedDirectory.getAbsolutePath();

				try {
					extract(new File(path));
				} catch (IOException f) {
					// TODO Auto-generated catch block
					f.printStackTrace();
				}

			}


	});

Button track1=new Button("+");
track1.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY,Insets.EMPTY)));
track1.getStyleClass().add("gorder");
albumy.setOnMouseClicked(mouseveent->{

	{

		tracklist.getStylesheets().add(getClass().getResource("listvie.css").toExternalForm());
		//tracklist.getStyleClass().add("Listview");
		File dir=new File(System.getProperty("user.home")+"/ilix/playlist");
		File[] playlist=dir.listFiles();
		for(File f:playlist) {
			if(f.getName().endsWith(".txt")){
				if(!lists.contains(f.getName()))
			     lists.add(f.getName());

			}

		//ListActionView view=new ListActionView<>();
		System.out.println("clicked on View Playlist");


		List.hide();
		//if(!List.isShowing()) {


		//	}

		StackPane stuff=new StackPane();
		stuff.setPrefHeight(200);
		stuff.setPrefWidth(200);
		stuff.setPadding(new Insets(10, 10, 10, 10));
		track1.setOnAction(
				new EventHandler<ActionEvent>(){


		@Override
		public void handle(final ActionEvent e) {
			GridPane griddy = new GridPane();
			GridPane text=new GridPane();
			PopOver Listy=new PopOver();
			Button enter=new Button("Done");
			enter.setOnAction(
					new EventHandler<ActionEvent>(){


						@Override
						public void handle(final ActionEvent e) {

							try {
								//FileWriter fw = new FileWriter("mydb.txt", true);
							    //BufferedWriter bw = new BufferedWriter(new FileWriter("mydb.txt", true));
							//	newame.can
								filter=newame.getText();
								File list=new File(System.getProperty("user.home")+"/ilix/playlist/"+filter+".txt");
								System.out.println("Created New File");
								list.createNewFile();
								if(list.exists()) {

									System.out.println("Created New Playlist");
									Listy.hide();
									}

								//List.hide();
						}
							 catch (IOException g) {
							    //exception handling left as an exercise for the reader
							}






						}});

			System.out.println("Create New List");
			newame.autosize();

			griddy.setId("New Name");
			griddy.setPadding(new Insets(5));
			griddy.setCache(true);
			griddy.setCacheHint(CacheHint.SPEED);
			griddy.setHgap(1);

			griddy.add(newame,0,0);
			griddy.add(enter,0,1);

				Platform.runLater(()->{


					Listy.setContentNode(griddy);
					Listy.arrowSizeProperty().set(1);
					Listy.centerOnScreen();
					Listy.show(image);
					((Parent)Listy.getSkin().getNode()).getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					Listy.show(image);
				});





// ENDS HERE
		}
		});
////// // // /// / / / / // // // / // / // / /// / // / / // / /  /  ///////////// // // // / //
		//List.hide();










		System.out.println(playlist);
		if(lists.isEmpty()) {
			System.out.println("Checked ,Playlist list - Its Empty");
		}else {
			tracklist.setItems(lists);
			//tracklist.autosize();
			//tracklist.getStyleClass().add("Listview");

		//	tracklist.
			tracklist.setCellFactory(new Callback<ListView<String>, ListCell<String>>(){

			    @Override
				public ListCell<String> call(ListView<String> tv) {
			    //    return new ListCell<AudioParser>() {
			    		return new ListCell<String>() {

			            @Override
			            protected void updateItem(String item, boolean empty) {
			                super.updateItem(item, empty);
			                if (item != null) {
//			                    setText(item);
			                    GridPane griddy = new GridPane();
			        			griddy.setId("New Name");
			        			griddy.setPadding(new Insets(5));
			        			griddy.setCache(true);
			        			griddy.setCacheHint(CacheHint.SPEED);
			        			griddy.setHgap(1);
			        			Text name=new Text(item);
			        			//name.setStyle("-fx-fill-color: white;");
			        			name.getStyleClass().add("my-text");
			        			//griddy.setStyle("-fx-background-color: transparent;");

			        			//griddy.setFillHeight(track, true);
			        			griddy.add(name,0,0);
			        			setGraphic(griddy);
			                }
			            }
			        };


			}});


		}
		stuff.getChildren().add(tracklist);
		stuff.getChildren().add(track1);
		stuff.setAlignment(track1, Pos.BOTTOM_CENTER);

		tracklist.setOnMouseClicked(gf->{
			List.hide();
			int oldValue= tracklist.getSelectionModel().getSelectedIndex();
			try {

				refreshList.clear();


				System.out.println(lists.get(oldValue));

				BringItO(lists.get(oldValue));
			}
				 catch (IOException g) {
				    //exception handling left as an exercise for the reader
				}
		});





		Platform.runLater(()->{

			//popover.setHeight(3);

	//		List.centerOnScreen();
			List.setContentNode(stuff);
			List.arrowSizeProperty().set(1);
			//List.show(albumy);
			List.show(albumy, mouseveent.getScreenX(), mouseveent.getScreenY());
			((Parent)List.getSkin().getNode()).getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			List.show(albumy,  mouseveent.getScreenX(), mouseveent.getScreenY());
		});


		}


	}


	});
//prefHeight="24.0" prefWidth="128.0" promptText="Search" style="-fx-background-color: #191D1F; -fx-border-color: #39FF9F; -fx-border-radius: 6; -fx-background-radius: 7;"
searchbar.setOnMouseClicked(Me ->{
	load();
});

choicebox.setValue("Song Name");
PopOver imageim=new PopOver();
GridPane joey = new GridPane();
searchimage.setOnMouseClicked(mousevent->{
	if(mousevent.getButton()==MouseButton.PRIMARY) {
		System.out.println("Here");

	if(!imageim.isShowing()) {
	load();
	searchbar.setPrefHeight(24.0);
	searchbar.setPrefWidth(128.0);
	searchbar.setPromptText("Search");
	//searchbar.setStyle("");
	searchbar.setStyle("-fx-background-color: #191D1F; -fx-border-radius: 6; -fx-background-radius: 6;-fx-border-color:#39FF9F;");

	Platform.runLater(()->{

		//imageim.cornerRadiusProperty();
		imageim.setContentNode(searchbar);
		//imageim.setHeight(25);
		imageim.arrowSizeProperty().set(1);
		imageim.show(searchimage);
		((Parent)imageim.getSkin().getNode()).getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		imageim.show(searchimage);
	});


	}else {
		imageim.hide();

	}}else if(mousevent.getButton()==MouseButton.SECONDARY) {




		Platform.runLater(()->{

			//imageim.cornerRadiusProperty();
			imageim.setContentNode(choicebox);
			//imageim.setHeight(25);
			imageim.arrowSizeProperty().set(1);
			imageim.show(searchimage);
			((Parent)imageim.getSkin().getNode()).getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			imageim.show(searchimage);
		});
	}


});



}














public void checkFOrFIles() {
	File folder = new File(System.getProperty("user.home")+"/ilix");
	if(!folder.exists()) {
		try {
		folder.createNewFile();}catch(IOException meg) {


		}

	}
	String flee[]= {"albums.txt","albumsdone.txt" , "artists.txt", "artistsdone.txt","dex.txt" , "folders.txt","mydb.txt" , "phase.txt"
			, "phase.txt","repeat.txt" , "songs.txt", "songs.txt","songviewpos.txt" , "state.txt","toExtract.txt" ,  "totalSongs.txt" ,"volume.txt" ,"zzz.txt"  };

	for(String grar: flee) {
		File dbcheck = new File(System.getProperty("user.home")+"/ilix/"+grar);
		if(dbcheck.exists()) {
			//System.out.println("All Good");

		}else {
			try {
			dbcheck.createNewFile();

			}catch(IOException db) {

				System.out.println("Couldnt Create The File : "+System.getProperty("user.home")+"/ilix/"+grar+"\n"+db);
			}

		}
	}
}

public static void theme() {
	boolean darkMode = false ;
	boolean lightMode =false;

}

// INCREASE READABILTY ON BACKGORUND HOVER OR PLAYING
//


public static void MediaKeyForward() {

	//GlobalScreen.postNativeEvent(new NativeKeyEvent(2401,0,176,57369,org.xvolks.jnative.))
}

public class GlobalListeners implements NativeKeyListener{

	public void nativeKeyPressed(NativeKeyEvent e) {
		// System.out.println("Key Pressed : " + NativeKeyEvent.getKeyText(e.getKeyCode()));
		 if(NativeKeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase("play")) {
			 replacePlay();

		 }
		 if(NativeKeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase("previous")) {
			 backone();

			 }
		 if(NativeKeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase("next")) {

				 nextone();

			 }
			//GlobalScreen.postNativeEvent(new NativeKeyEvent(2401,0,176,57369,org.xvolks.jnative.))
		}

	 public void nativeKeyReleased(NativeKeyEvent e) {

		//GlobalScreen.postNativeEvent(new NativeKeyEvent(2401,0,176,57369,org.xvolks.jnative.))
	}

	 public void nativeKeyTyped(NativeKeyEvent e) {

			//GlobalScreen.postNativeEvent(new NativeKeyEvent(2401,0,176,57369,org.xvolks.jnative.))
		}
}



int algea=0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		System.out.println("Ilix: Welcome ");
		checkFOrFIles();
		choicebox.getItems().addAll("Song Name","Artist Name","Album Name");
		choicebox.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,newVal)->
		{
			if(newVal != null) {
				searchbar.setText(" ");

			}

		});
		BPane.setPrefSize(rootPane.getWidth(), rootPane.getHeight());
		BPane.setMinSize(rootPane.getWidth(), rootPane.getHeight());
		BPane.setRight(buttonbar);
		rootPane.setContent(BPane);
		//BPane.setCenter(buttonbar);


		try {

		upway();//STATE,DEX,VOLUME,REPEAT
		albumAndArtsit();
		BringItON();

		phaser();
		flowsy = VirtualFlow.createHorizontal(filteredData, audioparser ->new AudioParserCell2(audioparser,filteredData.indexOf(audioparser)) );
		scrollt.setContent(flowsy);

		flowsy.addEventFilter(ScrollEvent.SCROLL,new EventHandler<ScrollEvent>(){

  			@Override
  			public void handle(ScrollEvent event) {

  				if(event.getDeltaX() != 0) {

  					event.consume();
  				}

  			}

  		});
//		if(Chosen1==0) {
//			Collections.sort(list, comparatorMyObject_byDay);}
//		//by name
//		else if(Chosen1==1) {
//			Collections.sort(list, comparatorByArtist);}
//		//by album
//
//		else if(Chosen1==2) {
//			Collections.sort(list, comparatorBynamey );}
//		//by aritst
//		else if(Chosen1==3) {
//			Collections.sort(list, comparatorbyDate );}

		}catch(IOException fr) {
			fr.printStackTrace();
		}catch(NullPointerException fgr) {
			fgr.printStackTrace();
		}
		volumeSlider.getStylesheets().add(getClass().getResource("volume.css").toExternalForm());
		backward.setOnMouseClicked(arg0->{
			if(arg0.getClickCount()>=2&&status!=status.UNKNOWN&&status!=status.STALLED) {
				if(mp!=null) {
				mp.seek(Duration.ZERO);
				}
				}else if(arg0.getClickCount()==1) {
				backone();

				}

		});


	//	albumss.setSkin(new MyButtonSkin(albumss));
		//songss.setSkin(new MyButtonSkin(songss));
		//artists.setSkin(new MyButtonSkin(artists));

		/*albumss.setOnMouseEntered(e->{


			albumss.setStyle("-fx-scale-x: 1.1;-fx-scale-y: 1.1;-fx-scale-z: 1.1;");

		});*/
//		rootPane.addEventFilter(KeyEvent.KEY_PRESSED,event->{
//
//
//    		System.out.println("Pressed: "+event.getCode().toString()+" ");
//    		if(event.getCode().toString().equalsIgnoreCase("P")||event.getCode().toString().equalsIgnoreCase("SPACE")||event.getCode().toString().equalsIgnoreCase("F11")) {
//    			//System.out.println("Here");
//    			if(mp!=null) {
//    			status = mp.getStatus();
//    			}
//    			if(status == Status.PLAYING) {
//    				mp.pause();
//    			//	System.out.println("pause");
//    			}else if(status == Status.PAUSED||status == Status.READY) {
//    				mp.play();
//    				//System.out.println("play");
//
//    			}
//    	//	}
//
//    	}
//
//
//    	});
		buttonbar.setHgap(7);
		buttonbar.setVisible(true);
		buttonbar.getChildren().addAll(refresh,searchimage,addNewTrack,sorter);

		buttonbar.setOnMouseClicked(arg0->{
			if(arg0.getClickCount()>=2) {
			if(!filteredData.isEmpty()&&algea==0) {
				flows.show(filteredData.size()-1);
				algea=1;
				//System.out.println("Go To last");
				}else {
					flows.show(0);
					algea=0;
				}
		}

		});

		home.addEventFilter(KeyEvent.KEY_RELEASED,event->{

//			event.getCode().FAST_FWD;
//			event.getCode().TRACK_NEXT;
//			event.getCode().TRACK_PREV;
//			if(event.getCode().name().equalsIgnoreCase("UNDEFINED")) {
//    			//nextone();
//				System.out.println("Pressed: "+event.toString());
//
//
//	}
			//KeyEvent m =  KeyEvent(component ,);
    	//	System.out.println("Pressed: "+event.);
		//	 nativeKeyPressed(event);

    		if(event.getCode().toString().equalsIgnoreCase("SPACE")||event.getCode().toString().equalsIgnoreCase("F11")||event.getCode()==event.getCode().PLAY||event.getCode()==event.getCode().PAUSE) {
    			//System.out.println("Here");
    			if(mp!=null) {
    			status = mp.getStatus();
    			}
    			if(status == Status.PLAYING) {
    				mp.pause();
    			//	System.out.println("pause");
    			}else if(status == Status.PAUSED||status == Status.READY) {
    				mp.play();
    				//System.out.println("play");

    			}
    	//	}

    	}


    		/*if(event.equals(KeyCode.TRACK_PREV.getName())) {
    			System.out.println("Media");
    			backone();

    		}*/

    	});


	/*	home.addEventFilter(KeyEvent.,event->{

		});*/



		//sorter.setImage(new Image(new File("application/Image/sorter.png").toURI().toString(),true));
		//System.setProperty("file.encoding","UTF-8");




//    	ScrollBar bar = (ScrollBar) rootPane.lookup(".scroll-bar");
//        bar.valueProperty().addListener((src, ov, nv) -> {
//        	//scroll=true;
//        	System.out.println("change on value " + nv);
//
//        	if (nv.doubleValue() <0) {
//            	bar.setValue(0.1);
//            }
//            if (nv.doubleValue() == 1.) {
//            	System.out.println("at max");
//            }
//            if (nv.doubleValue() == -1.) {
//            	//nv.
//            	System.out.println("Error");
//            }
//            if (nv.doubleValue() == 0) {
//            	//nv.
//            	System.out.println("At the Beggining");
//            }
//
//        });
	/*	ImageView startuP = new ImageView("application/Image/A5.png");
		Pane foiler = new Pane();
		foiler.setBackground(new Background(new BackgroundFill(Color.valueOf("#191D1F"), CornerRadii.EMPTY,Insets.EMPTY)));
		//foiler.setStyle("-fx-background-color: #191D1F; -fx-border-radius: 6; -fx-background-radius: 6;");
		foiler.setMinWidth(home.getWidth());
		foiler.setMinHeight(home.getHeight());
		foiler.setMaxWidth(foiler.getMinWidth());
		foiler.setMaxHeight(foiler.getMinHeight());
		foiler.setPrefSize(home.getWidth(), home.getHeight());
		foiler.getChildren().add(startuP);

		//foiler.setPadding(new Insets(50,0,50,0));

		foiler.getChildren().add(bar);
		bar.setCenterShape(true);
		foiler.setCenterShape(true);
		home.getChildren().add(foiler);
		//home.
		bar.setPrefWidth(300);
 		bar.setMaxWidth(300);
 		bar.setMinWidth(300);

 		*/
		//foiler.toFront();


		PauseTransition wait = new PauseTransition(Duration.seconds(0.225));
		wait.setOnFinished((e) -> {

			try {
				BringItON3();

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NullPointerException e1) {
				// TODO Auto-generated catch block
			//	e1.printStackTrace();
			}

		});
		wait.play();
	PauseTransition wait1 = new PauseTransition(Duration.seconds(0.5));
		wait1.setOnFinished((e) -> {
			try {

				BringItONA();//BRING IN ALBUMS
				BringItONAr();//BRING IN ARTISTS



				BringItON4();//FOLDERS
				ViewByAlbum();
				ViewByArtist();
				//albumAndArtsit();




//				trackListView3.setOrientation(Orientation.HORIZONTAL);
//				trackListView3.setFocusTraversable(false);
//			    trackListView3.setItems(filteredData);
//				trackListView3.setMaxSize(scrollt.getWidth(), scrollt.getHeight());
//			    trackListView3.setMinSize(scrollt.getWidth(), scrollt.getHeight());
//			   // trackListView3.fit
			    scrollt.getStylesheets().add(getClass().getResource("application2.css").toExternalForm());
			//	 machin();
				//remenisce();h




			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		wait1.play();
		PauseTransition wait5 = new PauseTransition(Duration.seconds(6.0));
		wait5.setOnFinished((e) -> {


			System.out.println(System.getProperty("user.home"));

		});
		wait5.play();
/*
		try {
			GlobalScreen.registerNativeHook();

			}catch(NativeHookException ex) {

				System.err.println("there was a problem registering native hook");

			}
		//NativeKeyListener.
			GlobalScreen.addNativeKeyListener(new GlobalListeners());
*/
PauseTransition wait11 = new PauseTransition(Duration.seconds(100));
wait11.setOnFinished((e) -> {


//	System.out.println("Garbage Collector");
	System.gc();

wait11.playFromStart();
});
wait11.play();
		setMin();







Service<Void> service_ = new Service<Void>(){

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {

                @Override
				protected Void call() throws Exception {


		trackListView.setCellFactory(new Callback<ListView<AudioParser>, ListCell<AudioParser>>(){


		    @Override
			public ListCell<AudioParser> call(ListView<AudioParser> tv) {
		    //    return new ListCell<AudioParser>() {
		    		return new ListCell<AudioParser>() {

					@Override protected void updateItem(AudioParser audioParser, boolean empty) {
						  ScrollBar bar = (ScrollBar) trackListView.lookup(".scroll-bar");
					        bar.valueProperty().addListener((src, ov, nv) -> {
					        	//scroll=true;
					        	//System.out.println("change on value " + nv);
					            if (nv.doubleValue() == 1.) {
					            	System.out.println("at max");
					            }
					        });

		                super.updateItem(audioParser, empty);
		                //System.out.println("Got Somhwere");
		                if(Chosen1==0) {
		               if(audioParser != null ) {




		                		GridPane grid = new GridPane();
		                		grid.setPadding(new Insets(5));
		                		grid.setCache(true);
		                		grid.setCacheHint(CacheHint.SPEED);
			                    grid.setHgap(10);
			                    Text iconAndName=new Text(String.valueOf(audioParser.nameGet())+"\r\n"+String.valueOf(audioParser.albumGet()));

			                    grid.add(iconAndName, 0, 0);

			                    Platform.runLater(() -> setGraphic(grid));

		               	} else{
                    setText(null);


                	  }  ;}

		                /////////////////////other if
		                	   if(Chosen1==1) {
				               if(audioParser != null ) {

//

				                		GridPane grid = new GridPane();
				                		grid.setCache(true);
				                		grid.setPadding(new Insets(5));
					                    grid.setHgap(10);
					                    Label AlbumeNmae=new Label(String.valueOf(audioParser.albumGet())+ "\r\n"+String.valueOf(audioParser.nameGet()));

					                    AlbumeNmae.setAlignment(Pos.CENTER_RIGHT);

					                    grid.add(AlbumeNmae, 2, 0);
					                   // grid.add(AlbumeNmae,3, 0);


					                    Platform.runLater(() -> setGraphic(grid));


				        } else{
		                    setText(null);
		                   // setGraphic(null);



		                	}  ;}


		    }
		};
	}    });//SETCELLFACTORY END HERE AFTER IS THE TASK  MEHTOD


///////////
	return null;}
//finally{
};


	}};//service_.start();





	//*/

		//	}
		//});


			}////////////////////////////////////////////////////////////////////////////END initialize


	public void albumOnClikc(GridPane grid4,Label l) {


		if(l!=null) {
		if (selectedLabel != grid4) {
            clearSelection();
            selectLabel(grid4);
            lazyLoad.clear();
            filteredData=new FilteredList<>(lazyLoad,s->true);
        		list.forEach(mans->{
        			if(l.getText().equalsIgnoreCase(mans.artistGet())) {

        				 lazyLoad.add(mans);
        			//i++;
        			}
        		});
//        		filteredData.clear();
//        		filteredData = new FilteredList<>(lazyLoad, t -> true);
        		Vpane(filteredData);
        	   // flows = VirtualFlow.createVertical(lazyLoad, audioparser ->new AudioParserCell(audioparser) );

        		//doSomething();
        } else {
            clearSelection();
        }}
	}
	private void bor() {

		//service_1.
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void handleMetadata(String key, Object value) {
		// As previously shown...
		switch (key) {
//		case "album":
//			album.setText(value.toString());
//			break;
//		case "artist":
//			artist.setText(value.toString());
//			break;
//		case "title":
//			title.setText(value.toString());

//			break;
		case "image":
			Platform.runLater(() -> {
				int n=0;
				String string=null;
				File fileA = new File(System.getProperty("user.home")+"/ilix/cache-images/"+n+"85.jpeg");
		        string=fileA.getAbsolutePath();
		        Image image=(Image) value;
		        BufferedImage vimage=SwingFXUtils.fromFXImage(image, null);
		        try {
		        	ImageIO.write(vimage, "png", fileA)	;
			     }catch(IOException e){
				throw new RuntimeException(e);
				}


			});

		default:
			//image.setImage("Image/A5.png");
			break;
		}
	}

///////////////////////////////////////////////////////////////////////////////////////////
//This Gets The Album Art Location From AudioParser And Converts its To ByteArray then to Image
//And Cast to Image View


	int fog=0;






//PAUSES SONG THEN DISPOSES IT
	private void chokee(){
						if(mp != null) {
							if(status==Status.PLAYING) {
								mp.pause();}
								mp.dispose();
								//somethingInCan = false;
								System.gc();

										}
						}
//IN CHARGE OF PLAYING NEW SONG,CHANGING IMAGE ON IMAGEVIEW AND UPDATING DATA ON SCREEN-----NEED TO STREAMLINE PROCESS
	private void all_and(String Doug) throws Exception, InvalidDataException, IOException {

		//newThread(Doug);
		execute.submit(() -> 	Song(Doug));
		//execute.submit(() -> 	trackMaster(Doug));
		play jog = new play(Doug,true);

	}



	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void handlemouseClickPane(String song,int Num) {
		if(song!=null) {


    			Song(song);
    		//	trackMaster(song);
    			play jog = new play(song,true);


    		listViewDex=Num;



    	    }


	}

	synchronized void reverttoorginal(int rar){


		Node nodejs=flows.getCell(rar).getNode();
		GridPane griddade = (GridPane) nodejs;
		TextFlow he = (TextFlow)griddade.getChildren().get(2);
		Text her =(Text)he.getChildren().get(0);
		Text herr =(Text)he.getChildren().get(1);
	  Platform.runLater(() -> {
		  System.gc();
		//  griddade.setStyle("-fx-background-color: #191D1F;");
		  griddade.setStyle("-fx-background-color: #transparent;");



				her.setFill(Color.WHITE);
				herr.setFill(Color.GREY);

	      });

	}

	synchronized void onHighLight(int digiter) {
		Node nodejss=flows.getCell(digiter).getNode();

		 GridPane griddd = (GridPane) nodejss;
		 TextFlow he = (TextFlow)griddd.getChildren().get(2);
			Text her =(Text)he.getChildren().get(0);
			Text herr =(Text)he.getChildren().get(1);
		  Platform.runLater(() -> {
			  System.gc();
			//  griddade.setStyle("-fx-background-color: #191D1F;");
			  griddd.setStyle("-fx-background-color: #39FF9F;");



					her.setFill(Color.BLACK);
					herr.setFill(Color.DARKSLATEGREY);

		      });

	}
	@FXML
	synchronized public void nextone() {
		String oldValue=null;
		int number;
		AudioParser ap =  null;
		chokee();
		System.out.println("Next Song");
		if(playing==filteredData) {

			reverttoorginal(listViewDex);

		}


		if(listViewDex==totalSongs) {

			number=0;
			row=0;
			listViewDex=0;

		}else {

			listViewDex++;
			number=listViewDex;
			row=number;

		}

		if(playing==filteredData) {

			onHighLight(number);

				}
	//	  Bounds bounds=image.getBoundsInParent();

		  flowsy.showAtOffset(number,24);





		  try {
		 ap = playing.get(number);
			if( ap!=null) {

				//DO NOTHING

			}else{

				number=number-1;
				ap = playing.get(number);

			 }

			 oldValue = ap.fileLocationGet();
			 current=oldValue;

			 if(oldValue!= null&& ap.nameGet() != "Album Header") {

				try {

				all_and(oldValue); System.gc();}
				catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();}

			 }




		  }catch(IndexOutOfBoundsException log) {
			  try {
					 ap = list.get(number);
						if( ap!=null) {     }

						else{
							number=number-1;
							ap = list.get(number);
						 }
						 oldValue= ap.fileLocationGet();
						 current=oldValue;
						if (oldValue!= null && ap.nameGet() != "Album Header") {

							try {
							all_and(oldValue); System.gc();}
							catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();}

						}

		  }catch(IndexOutOfBoundsException logg) {
			  }
		  }

}


	int number;
	@FXML
	synchronized public void backone() {
		if(!filteredData.isEmpty()) {
		String oldValue=null;
		chokee();
		System.out.println("Previous Song");
		if(playing==filteredData) {
			reverttoorginal(listViewDex);
	}


		if(listViewDex==0) {

				number=totalSongs;
				row=totalSongs;
				listViewDex=totalSongs;

		}else{

				listViewDex--;
				number=listViewDex;
				row=number;
			}


		if(playing==filteredData) {

			onHighLight(number);

			}

		  Platform.runLater(()->{ flowsy.showAtOffset(number,24);} );


		 // int blorg=number;
		  AudioParser ap = null;
		  if(number<=filteredData.size()) {

			  	ap = playing.get(number);
		  }

		  if(ap!=null) {

			  //DO NOTHING

		  }else{

			number=number+1;
			ap = playing.get(number);

		 }

		 oldValue= ap.fileLocationGet();
		 current=oldValue;
		if(oldValue!= null && ap.nameGet() != "Album Header") {

			try {
			all_and(oldValue); System.gc();}
			catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();}

		}

		}


}

   @FXML
   public void onEnteteExit() {





   }
   @FXML
   public void onEnteteMinize() {



		  Platform.runLater(() -> {

			  System.out.println("Song View Minimized");
		//	  System.out.println("MAYENZIWE IS BREAKING MY BONES");
			  System.out.println("Max native mem = " + sun.misc.VM.maxDirectMemory());
			  //-verbose:gc
			  System.gc();
			 // System.console();
			  //System.

		      });



   }


}

