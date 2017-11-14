package controllers;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import application.Main;
import database.SettingsDatabaseController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ShareThemeController implements Initializable {
	@FXML
	TextField txtIp;
	private Stage dialogStage;
	public String themeName;
	private static ResourceBundle rb;

	private static class Share implements Runnable {
		Map<String, String> theme;
		String ip;

		public Share(Map<String, String> theme, String ip) {
			this.theme = theme;
			this.ip = ip;
		}

		@Override
		public void run() {
			final int port = 5555;

			try (Socket client = new Socket(this.ip, port)) {
				OutputStream os = client.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(os);
				oos.writeObject(this.theme);
				oos.close();
				os.close();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {						
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setHeaderText(rb.getString("shareDialogTitle"));
						alert.setContentText(rb.getString("shareDialogText"));
						DialogPane dialogPane = alert.getDialogPane();
						dialogPane.setStyle(Main.getThemeString());
						dialogPane.getStylesheets().add(getClass().getResource("../styles/global.css").toExternalForm());
						alert.show();
					}
				});
			} catch (IOException e) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {						
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setHeaderText(rb.getString("shareDialogTitleErr"));
						alert.setContentText(rb.getString("shareDialogTextErr"));
						DialogPane dialogPane = alert.getDialogPane();
						dialogPane.setStyle(Main.getThemeString());
						dialogPane.getStylesheets().add(getClass().getResource("../styles/global.css").toExternalForm());
						alert.show();
					}
				});
			}
		}
	}

	public void cancel() {
		dialogStage = (Stage) txtIp.getScene().getWindow();
		dialogStage.close();
	}

	public void share() {
		if (!txtIp.getText().isEmpty()) {
			Map<String, String> theme = SettingsDatabaseController.getTheme(themeName);
			Thread send = new Thread(new Share(theme, txtIp.getText()));
			send.start();
		}
		cancel();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ShareThemeController.rb =  resources;
	}
}
