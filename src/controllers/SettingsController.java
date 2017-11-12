package controllers;


import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import database.SettingsDatabaseController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
	@FXML
	private Button addThemeBtn;
	@FXML
	private Button editThemeBtn;
	private ResourceBundle rb;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.rb = resources;
		String lang = SettingsDatabaseController.getLanguage();
		ObservableList<String> languages = FXCollections.observableArrayList("English", "Español");
		langChoice.setItems(languages);
		if (lang.equals("EN")) {			
			langChoice.getSelectionModel().selectFirst();
		} else {
			langChoice.getSelectionModel().select(1);			
		}
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
		Boolean changed = SettingsDatabaseController
				.setLanguage(langChoice.getSelectionModel().getSelectedItem().equals("English") ? "EN" : "ES");
		if (changed) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(this.rb.getString("settDialogTitle"));
			alert.setContentText(this.rb.getString("settDialogText"));
			DialogPane dialogPane = alert.getDialogPane();
			dialogPane.getStylesheets().add(getClass().getResource("../styles/global.css").toExternalForm());
			alert.show();
			restart();
		}
	}

	private void restart() {
		StringBuilder cmd = new StringBuilder();
		cmd.append(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java ");
		for (String jvmArg : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
			cmd.append(jvmArg + " ");
		}
		cmd.append("-cp ").append(ManagementFactory.getRuntimeMXBean().getClassPath()).append(" ");
		cmd.append(Main.class.getName()).append(" ");

		try {
			Runtime.getRuntime().exec(cmd.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Platform.exit();
	}
	
	public void addThemeAction(){
		try {
			Stage dialogStage = new Stage();
			dialogStage.initOwner(addThemeBtn.getScene().getWindow());
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle(this.rb.getString("addTheme"));

			GridPane newThemePane =  FXMLLoader.load(Main.class.getResource("AddThemeDialog.fxml"), this.rb);
			dialogStage.setScene(new Scene(newThemePane));					
			
			dialogStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void editThemeAction(){
		try {
			Stage dialogStage = new Stage();
			dialogStage.initOwner(editThemeBtn.getScene().getWindow());
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle(this.rb.getString("editTheme"));

			GridPane newThemePane =  FXMLLoader.load(Main.class.getResource("EditThemeDialog.fxml"), this.rb);
			dialogStage.setScene(new Scene(newThemePane));					
			
			dialogStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
