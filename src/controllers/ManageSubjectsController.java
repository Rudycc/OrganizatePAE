package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import cellItems.ClassCellItems;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import database.SubjectDatabaseController;

public class ManageSubjectsController implements Initializable {

	@FXML TextField txtSubject;
	@FXML TextField txtProfessor;
	@FXML ChoiceBox<String> semesterChoiceBox;
	@FXML ColorPicker colorPicker;
	@FXML ChoiceBox<String> dayChoiceBox;
	@FXML Spinner<Integer> hourSpinner;
	@FXML Spinner<Integer> minuteSpinner;
	@FXML Spinner<Integer> hourSpinnerDuration;
	@FXML Spinner<Integer> minuteSpinnerDuration;
	@FXML Button btnAddSubjectTime;
	@FXML Button btnAccept;
	@FXML Button btnCancel;
	@FXML Label hourMessage;
	private List<String> days;
	private List<String> hours;
	private String minutes;
	private ResourceBundle resources;
	private Stage dialogStage;
	private ObservableList<Integer> semesterIDs;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> typeChoiceBoxData = FXCollections.observableArrayList(
				resources.getString("monday"), resources.getString("tuesday"), resources.getString("wednesday"),
				resources.getString("thursday"), resources.getString("friday"),resources.getString("saturday"),
				resources.getString("sunday")); 
		dayChoiceBox.setItems(typeChoiceBoxData);
		
		semesterChoiceBox.setItems(SubjectDatabaseController.getAllSemesterNames());
		
		days = new ArrayList<String>();
		hours = new ArrayList<String>();
		
		semesterIDs = SubjectDatabaseController.getAllSemesterIDs();
		
		this.resources = resources;
		
	}
	
	public void btnAddSubjectTime(){
		switch(dayChoiceBox.getSelectionModel().getSelectedIndex()){
			case 0: days.add("MONDAY");
				break;
			case 1:	days.add("TUESDAY");
				break;
			case 2: days.add("WEDNESDAY");
				break;
			case 3: days.add("THURSDAY");
				break;
			case 4: days.add("FRIDAY");
				break;
			case 5: days.add("SATURDAY");
				break;
			case 6: days.add("SUNDAY");
				break;
			default: days.add("N/A");
				break;
		}
		
		minutes = (minuteSpinner.getValue() < 10)?  "0"+minuteSpinner.getValue(): ""+minuteSpinner.getValue();
		hours.add("1000-01-01 " + hourSpinner.getValue() + ":" + minutes +":00");
		hourMessage.setText(resources.getString("hourMessage")+ "-> #" + hours.size());
	}
	
	public void btnAcceptAction(){
		float duration = hourSpinnerDuration.getValue() + (minuteSpinnerDuration.getValue() / 10);		
		if(SubjectDatabaseController.addSubject(txtProfessor.getText(), txtSubject.getText(), semesterIDs.get(semesterChoiceBox.getSelectionModel().getSelectedIndex()), 
												"#" + colorPicker.getValue().toString().substring(2, 8), days, hours, duration)){

			dialogStage = (Stage) btnCancel.getScene().getWindow();
			dialogStage.close();
		}else{
			System.out.println("fatal error");
		}
	}
	
	public void btnCancelAction(){
		dialogStage = (Stage) btnCancel.getScene().getWindow();
		dialogStage.close();
	}
		
	public void btnManageStored(){
		try {
			
			Stage dialogStage = new Stage();
			dialogStage.initOwner(txtSubject.getScene().getWindow());
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle(resources.getString("titleManageStored"));
			
			GridPane pane =  FXMLLoader.load(Main.class.getResource("ClassInfoDialog.fxml"), this.resources);
			
			dialogStage.setScene(new Scene(pane));
			dialogStage.show();
			
			//Sets the textArea default value
			TextArea paneTextArea = (TextArea) pane.getChildren().get(4);
			List<ClassCellItems> classes = SubjectDatabaseController.getAllClasses();
			paneTextArea.setText(classes.get(0).toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
} 
