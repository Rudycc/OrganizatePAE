package application;
	
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import database.SettingsDatabaseController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;


public class Main extends Application {
	
	private static String themeString = "";
	
	@Override
	public void start(Stage primaryStage) {
		try {
			ResourceBundle rb = ResourceBundle.getBundle("resources.UIResources", new Locale(SettingsDatabaseController.getLanguage()));
			TabPane pane =  FXMLLoader.load(getClass().getResource("Main.fxml"), rb);
			pane.setStyle(getThemeString());
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
	
	public static String getThemeString() {
		if (themeString.isEmpty()) {
			String name = SettingsDatabaseController.getCurrentThemeName();
			Map<String, String> theme = SettingsDatabaseController.getTheme(name);
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("-fx-color-primary: %s;", theme.get("color-primary")));
			sb.append(String.format("-fx-color-info: %s;", theme.get("color-info")));
			sb.append(String.format("-fx-color-default: %s;", theme.get("color-default")));
			sb.append(String.format("-fx-color-bg: %s;", theme.get("color-bg")));
			sb.append(String.format("-fx-gray-base: %s;", theme.get("gray-base")));
			sb.append(String.format("-fx-gray-dark: %s;", theme.get("gray-dark")));
			sb.append(String.format("-fx-gray-darker: %s;", theme.get("gray-darker")));
			sb.append(String.format("-fx-gray-light: %s;", theme.get("gray-light")));
			sb.append(String.format("-fx-gray-lighter: %s;", theme.get("gray-lighter")));
			themeString = sb.toString();
		}
		return themeString;
	}
}
