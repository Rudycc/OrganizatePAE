package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import database.TaskDatabaseController;

public class NewTaskDialogController implements Initializable{

	@FXML Button btnCancel;
	@FXML Button btnAccept;
	@FXML ChoiceBox<String> choiceBoxClassChooser;
	@FXML ChoiceBox<String> choiceBoxTypeChooser;
	@FXML TextField txtName;
	@FXML DatePicker datePicker;
	
	//Pointer to the Stage that contains the Pane
	Stage dialogStage;
	private String type;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//Sets the classes choiceBox data
		ObservableList<String> classesChoiceBoxData = FXCollections.observableArrayList(
				"Software Design", "Operating Systems", "Networking Fundamentals", "Database Design Fundamentals");
		choiceBoxClassChooser.setItems(classesChoiceBoxData);
		
		//Sets the task type choiceBox data
		ObservableList<String> typeChoiceBoxData = FXCollections.observableArrayList(
				resources.getString("task"), resources.getString("lab"), resources.getString("exam")); 
		choiceBoxTypeChooser.setItems(typeChoiceBoxData);
	}

	public void btnCancelAction(){				
		dialogStage = (Stage) btnCancel.getScene().getWindow();
		dialogStage.close();
	}
	
	//Add a new Task / Exam / Practice in the DB
	public void btnAcceptAction(){
		//Validations
		if(choiceBoxClassChooser.getValue() == null || datePicker.getValue() == null || choiceBoxTypeChooser.getValue() == null || txtName.getText() == ""){
			//Show an Alert!
			System.out.println("Empty Fields");
		}else{
			type = (choiceBoxTypeChooser.getSelectionModel().getSelectedIndex()==2)? "EXAM":"TASK";
			System.out.println(type);
			TaskDatabaseController.insertNewTask(txtName.getText(), type, 0, datePicker.getValue()+"", choiceBoxClassChooser.getValue());
		}			
		//Close the window
		dialogStage = (Stage) btnCancel.getScene().getWindow();
		dialogStage.close();
	}
	
}
