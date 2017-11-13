package controllers;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

import database.SettingsDatabaseController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ShareThemeController {
	@FXML
	TextField txtIp;
	private Stage dialogStage;
	public String themeName;

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
			} catch (IOException e) {
				e.printStackTrace();
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
}
