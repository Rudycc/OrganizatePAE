package application;
	
import java.util.Locale;
import java.util.ResourceBundle;

import database.SettingsDatabaseController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import resources.I18N;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;


public class Main extends Application {
	
	@Override
	public void init() {
		I18N.setSupportedLocales(Locale.ENGLISH, new Locale("ES"));
		I18N.setResourceBundlePath("resources.UIResources");
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			ResourceBundle rb = ResourceBundle.getBundle("resources.UIResources", new Locale(SettingsDatabaseController.getLanguage()));
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
