package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import database.SettingsDatabaseController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;

public class SettingsController implements Initializable {
	@FXML
	private Label langLbl;
	@FXML
	private Label themeLbl;
	@FXML
	private ChoiceBox<String> langChoice;
	@FXML
	private ChoiceBox<String> themeChoice;
	@FXML
	private Button aboutBtn;
	@FXML
	private Button acceptBtn;
	private ResourceBundle rb;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.rb = resources;
		ObservableList<String> languages = FXCollections.observableArrayList("English", "Español");
		langChoice.setItems(languages);
		langChoice.getSelectionModel().selectFirst();
		ObservableList<String> themes = FXCollections.observableArrayList("Tranquility");
		themeChoice.setItems(themes);
		themeChoice.getSelectionModel().selectFirst();
	}

	public void aboutAction() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(this.rb.getString("aboutDialogTitle"));
		alert.setContentText(this.rb.getString("aboutDialogText"));
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("../styles/global.css").toExternalForm());
		alert.show();
	}

	public void acceptAction() {
		Boolean changed = SettingsDatabaseController.setLanguage(langChoice.getSelectionModel().getSelectedItem().equals("English") ? "EN" : "ES");
		if (changed) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(this.rb.getString("settDialogTitle"));
			alert.setContentText(this.rb.getString("settDialogText"));
			DialogPane dialogPane = alert.getDialogPane();
			dialogPane.getStylesheets().add(getClass().getResource("../styles/global.css").toExternalForm());
			alert.show();
		}
	}
}
