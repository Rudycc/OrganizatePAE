package application;

	
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			ResourceBundle rb = ResourceBundle.getBundle("resources.UIResources", new Locale("ES"));
			TabPane pane =  FXMLLoader.load(getClass().getResource("Main.fxml"), rb);
			primaryStage.setTitle(rb.getString("title"));
			primaryStage.setScene(new Scene(pane));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
