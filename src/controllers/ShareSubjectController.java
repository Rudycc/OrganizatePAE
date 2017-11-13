package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ShareSubjectController implements Initializable {
	@FXML
	TextField txtIp;
	private Stage dialogStage;

	private static class Share implements Runnable {

		@Override
		public void run() {

		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void cancel() {
		dialogStage = (Stage) txtIp.getScene().getWindow();
		dialogStage.close();
	}

	public void share() {

	}
}
