package application;
	
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import database.SettingsDatabaseController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			ResourceBundle rb = ResourceBundle.getBundle("resources.UIResources", new Locale(SettingsDatabaseController.getLanguage()));
			TabPane pane =  FXMLLoader.load(getClass().getResource("Main.fxml"), rb);
			setTheme(pane);
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
	
	public static void setTheme(Node pane) {
		String name = SettingsDatabaseController.getCurrentThemeName();
		int id = SettingsDatabaseController.getIdForName(name);
		Map<String, String> theme = SettingsDatabaseController.getTheme(id);
		pane.setStyle(String.format("-fx-color-primary: %s;", theme.get("color-primary")));
		pane.setStyle(String.format("-fx-color-info: %s;", theme.get("color-info")));
		pane.setStyle(String.format("-fx-color-default: %s;", theme.get("color-default")));
		pane.setStyle(String.format("-fx-color-bg: %s;", theme.get("color-default")));
		pane.setStyle(String.format("-fx-gray-base: %s;", theme.get("gray-base")));
		pane.setStyle(String.format("-fx-gray-dark: %s;", theme.get("gray-dark")));
		pane.setStyle(String.format("-fx-gray-darker: %s;", theme.get("gray-darker")));
		pane.setStyle(String.format("-fx-gray-light: %s;", theme.get("gray-light")));
		pane.setStyle(String.format("-fx-gray-lighter: %s;", theme.get("gray-lighter")));
	}
}
