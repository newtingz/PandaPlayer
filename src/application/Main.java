package application;



import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {


	public static Main mainApp;
	public static Stage stage;
	private static final Rectangle2D SCREEN_BOUNDS= Screen.getPrimary().getVisualBounds();
	private static double[] /*pref_WH,*/ offset_XY;

	protected static void allowDrag(Parent root, Stage stage) {
        root.setOnMousePressed((MouseEvent p) -> {
            offset_XY= new double[]{p.getSceneX(), p.getSceneY()};
        });

        root.setOnMouseDragged((MouseEvent d) -> {
            //Ensures the stage is not dragged past the taskbar
            if (d.getScreenY()<(SCREEN_BOUNDS.getMaxY()-20))
                stage.setY(d.getScreenY() - offset_XY[1]);
            stage.setX(d.getScreenX() - offset_XY[0]);
        });

        root.setOnMouseReleased((MouseEvent r)-> {
            //Ensures the stage is not dragged past top of screen
            if (stage.getY()<0.0) stage.setY(0.0);
        });
    }

	@Override
	public void start(Stage primaryStage) {
		try {

			mainApp = this;
			stage=primaryStage;
			primaryStage.getIcons().add(new Image("application/Image/useCase.png"));
			//primaryStage.getIcons().add(new Image(getClass().class.getResourceAsStream( "icon.png" )));
			Parent root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			//scene.setFill(Color.TRANSPARENT);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			Main.allowDrag(root, stage);
			scene.setFill(Color.TRANSPARENT);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Ilix");

			primaryStage.show();


		} catch (Exception e) {
			e.printStackTrace();
		}


	}


	public static void main(String[] args) throws IOException {


		launch(args);



	}






	}





