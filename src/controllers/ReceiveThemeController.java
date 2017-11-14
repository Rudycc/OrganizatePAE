package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import database.SettingsDatabaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ReceiveThemeController implements Initializable {
	@FXML
	Label address;
	private Stage dialogStage;

	public static class Receive implements Runnable, Refresher {
		private final int port = 5555;
		private Refreshable parent;

		@SuppressWarnings("unchecked")
		@Override
		public void run() {
			Socket client = null;
			try (ServerSocket server = new ServerSocket(port)) {
				client = server.accept();
				InputStream is = client.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				Map<String, String> theme = (Map<String, String>) ois.readObject();
				if (SettingsDatabaseController.addTheme(theme)) {
					parent.refreshData();
					System.out.println("Theme added successfully!");
				}  else {
					System.out.println("Error adding theme");
				}
				ois.close();
				is.close();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					if (client != null)
						client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void setParent(Refreshable parent) {
			this.parent = parent;
		}
	}

	public static class Receiver implements Runnable {
		@Override
		public void run() {
			ExecutorService ex = null;
			try {
				ex = Executors.newSingleThreadExecutor();
				ex.submit(new Receive()).get(15, TimeUnit.SECONDS);
			} catch (InterruptedException | ExecutionException | TimeoutException e) {
				e.printStackTrace();
				if (ex != null) ex.shutdownNow();
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Thread thread = new Thread(new Receiver());
		thread.start();
		try {
			this.address.setText(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public void cancel() {
		dialogStage = (Stage) address.getScene().getWindow();
		dialogStage.close();
	}

}
