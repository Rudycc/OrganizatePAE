package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class NewTaskDialogController implements Initializable{

	@FXML Button btnCancel;
	@FXML Button btnAccept;
	@FXML ChoiceBox<String> choiceBoxClassChooser;
	@FXML ChoiceBox<String> choiceBoxTypeChooser;
	
	//Pointer to the Stage that contains the Pane
	Stage dialogStage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//Sets the classes choiceBox data
		ObservableList<String> classesChoiceBoxData = FXCollections.observableArrayList(
				"Software Design", "Operating Systems", "Networking Fundamentals", "Database Design Fundamentals");
		choiceBoxClassChooser.setItems(classesChoiceBoxData);
		
		//Sets the task type choiceBox data
		ObservableList<String> typeChoiceBoxData = FXCollections.observableArrayList(
				"Task", "Laboratory", "Exam"); 
		choiceBoxTypeChooser.setItems(typeChoiceBoxData);
		
	}

	public void btnCancelAction(){				
		dialogStage = (Stage) btnCancel.getScene().getWindow();
		dialogStage.close();
	}
}