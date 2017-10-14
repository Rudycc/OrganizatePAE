package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> languages = FXCollections.observableArrayList("English", "Español");
		langChoice.setItems(languages);
		langChoice.getSelectionModel().selectFirst();
		ObservableList<String> themes = FXCollections.observableArrayList("Tranquility");
		themeChoice.setItems(themes);
		themeChoice.getSelectionModel().selectFirst();
	}
	
	public void aboutAction() {
		System.out.println("Abouting stuff");
	}
	
	public void acceptAction() {
		System.out.println("Accepting stuff");
	}
}
