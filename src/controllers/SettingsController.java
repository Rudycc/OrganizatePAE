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

public class SettingsController implements Initializable, Refreshable {
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
	private Refreshable self = this;

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
		ObservableList<String> themes = SettingsDatabaseController.getThemeNames();
		themeChoice.setItems(themes);
		themeChoice.getSelectionModel().select(SettingsDatabaseController.getCurrentThemeName());
	}

	public void aboutAction() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(this.rb.getString("aboutDialogTitle"));
		alert.setContentText(this.rb.getString("aboutDialogText"));
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.setStyle(Main.getThemeString());
		dialogPane.getStylesheets().add(getClass().getResource("../styles/global.css").toExternalForm());
		alert.show();
	}

	public void acceptAction() {
		Boolean changedLang = SettingsDatabaseController
				.setLanguage(langChoice.getSelectionModel().getSelectedItem().equals("English") ? "EN" : "ES");
		Boolean changedTheme = SettingsDatabaseController.setTheme(themeChoice.getSelectionModel().getSelectedItem());
		if (changedLang && changedTheme) {
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

	public void addThemeAction() {
		try {
			Stage dialogStage = new Stage();
			dialogStage.initOwner(addThemeBtn.getScene().getWindow());
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle(this.rb.getString("addTheme"));

			FXMLLoader loader = new FXMLLoader(Main.class.getResource("AddThemeDialog.fxml"), this.rb);
			GridPane newThemePane = loader.load();
			((Refresher)loader.getController()).setParent(self);
			
			newThemePane.setStyle(Main.getThemeString());
			dialogStage.setScene(new Scene(newThemePane));
			dialogStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void editThemeAction() {
		try {
			Stage dialogStage = new Stage();
			dialogStage.initOwner(editThemeBtn.getScene().getWindow());
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle(this.rb.getString("editTheme"));

			FXMLLoader loader = new FXMLLoader(Main.class.getResource("EditThemeDialog.fxml"), this.rb);
			GridPane newThemePane = loader.load();
			((Refresher)loader.getController()).setParent(self);
			
			newThemePane.setStyle(Main.getThemeString());
			dialogStage.setScene(new Scene(newThemePane));

			dialogStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void shareTheme() {
		try {
			Stage dialogStage = new Stage();
			dialogStage.initOwner(editThemeBtn.getScene().getWindow());
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle(this.rb.getString("shareTheme"));

			FXMLLoader loader = new FXMLLoader(getClass().getResource("ShareTheme.fxml"), this.rb);
			GridPane newThemePane = loader.load();
			newThemePane.setStyle(Main.getThemeString());

			ShareThemeController ctrl = (ShareThemeController) loader.getController();

			ctrl.themeName = this.themeChoice.getSelectionModel().getSelectedItem();

			dialogStage.setScene(new Scene(newThemePane));

			dialogStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void receiveTheme() {
		try {
			Stage dialogStage = new Stage();
			dialogStage.initOwner(editThemeBtn.getScene().getWindow());
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle(this.rb.getString("shareTheme"));

			FXMLLoader loader = new FXMLLoader(getClass().getResource("ReceiveTheme.fxml"), this.rb);
			GridPane newThemePane = loader.load();
			newThemePane.setStyle(Main.getThemeString());
			((Refresher)loader.getController()).setParent(self);

			dialogStage.setScene(new Scene(newThemePane));

			dialogStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void refreshData() {
		ObservableList<String> themes = SettingsDatabaseController.getThemeNames();
		themeChoice.setItems(themes);
		themeChoice.getSelectionModel().select(SettingsDatabaseController.getCurrentThemeName());
	}
}
