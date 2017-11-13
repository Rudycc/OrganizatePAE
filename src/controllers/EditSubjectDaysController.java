package controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import cellItems.ClassCellItems;
import cellItems.ScheduleItem;
import database.SubjectDatabaseController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EditSubjectDaysController implements Initializable {
	@FXML
	GridPane rootGridPane;
	@FXML
	Button btnUpdateSubjectTime;
	@FXML
	Button btnAccept;
	@FXML
	Button btnDelete;
	@FXML
	ChoiceBox<String> dayChoiceBox;
	@FXML
	ChoiceBox<String> existingDaysChoiceBox;
	@FXML
	Spinner<Integer> hourSpinner;
	@FXML 
	Spinner<Integer> minuteSpinner;
	@FXML 
	Spinner<Integer> hourSpinnerDuration;
	@FXML 
	Spinner<Integer> minuteSpinnerDuration;
	@FXML
	Label hourMessage;
	
	private static List<String> days;
	private static List<String> hours;
	private static String originalSubject;
	private int selectedIDSubjectTime;
	private ResourceBundle resources;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rootGridPane.setStyle(Main.getThemeString());
		ObservableList<String> typeChoiceBoxData = FXCollections.observableArrayList(
				resources.getString("monday"), resources.getString("tuesday"), resources.getString("wednesday"),
				resources.getString("thursday"), resources.getString("friday"),resources.getString("saturday"),
				resources.getString("sunday"));
		dayChoiceBox.setItems(typeChoiceBoxData);
		
		this.resources = resources;
	}
	
	public void btnDeleteAction(){
		int existingDaysSelectedIndex = existingDaysChoiceBox.getSelectionModel().getSelectedIndex(); 
		//Remove day from choiceBox, days, and hours
		days.remove(existingDaysSelectedIndex);
		hours.remove(existingDaysSelectedIndex);
		existingDaysChoiceBox.getItems().remove(existingDaysSelectedIndex);
		
		//Delete from DB
		SubjectDatabaseController.deleteSubjectTime(selectedIDSubjectTime);
		disableControls();
	}
	
	public void btnEditAction(){
		selectedIDSubjectTime = Integer.parseInt(existingDaysChoiceBox.getSelectionModel().getSelectedItem().split("|")[0]);
		List<ClassCellItems> classes = SubjectDatabaseController.getAllClasses();
		List<ScheduleItem> times = null;
		ScheduleItem scheduleItemToEdit = null;
		int subjectID = SubjectDatabaseController.getIDForSubject(originalSubject);
		
		//Both for loops help get the scheduleItem to be edited
		for (ClassCellItems c : classes) {
			if(c.getSubjectId() == subjectID){
				times = c.getTimes();
				break;
			}
		}
		
		for (ScheduleItem t : times) {
			if(t.getIDSubject_Time() == selectedIDSubjectTime){
				scheduleItemToEdit = t;
			}
		}
		
		dayChoiceBox.setValue(resources.getString(scheduleItemToEdit.getDay().toLowerCase()));
		hourSpinner.getValueFactory().setValue(scheduleItemToEdit.getTime().getHour());
		minuteSpinner.getValueFactory().setValue(scheduleItemToEdit.getTime().getMinute());
		float duration = scheduleItemToEdit.getDuration();
		hourSpinnerDuration.getValueFactory().setValue((int) duration);
		minuteSpinnerDuration.getValueFactory().setValue((int) (duration - ((int) duration)) * 10);
		
		enableControls();
		
	}
	
	public void closeDialog(){
		Stage dialogStage = (Stage) btnAccept.getScene().getWindow();
		dialogStage.close();
	}
	
	public void btnUpdateSubjectTimeAction(){
		String day = getChoiceBoxDay(dayChoiceBox.getSelectionModel().getSelectedIndex());
		float duration = hourSpinnerDuration.getValue() + (minuteSpinnerDuration.getValue() / 10);
		int existingDaysSelectedIndex = existingDaysChoiceBox.getSelectionModel().getSelectedIndex();
		
		//Update local variables
		days.set(existingDaysSelectedIndex, day);
		hours.set(existingDaysSelectedIndex, "1000-01-01 " + hourSpinner.getValue() + ":" + 
		(minuteSpinner.getValue() < 10?  "0"+minuteSpinner.getValue(): ""+minuteSpinner.getValue()) + 
		" - Duration: " + duration);
		
		StringBuilder newDay = new StringBuilder();	
		newDay.append(existingDaysChoiceBox.getSelectionModel().getSelectedItem().split("|")[0]);
		newDay.append(" | ");
		newDay.append(resources.getString(day.toLowerCase()));
		newDay.append(" - ");
		newDay.append(hours.get(existingDaysSelectedIndex).substring(11));
		existingDaysChoiceBox.getItems().set(existingDaysSelectedIndex, newDay.toString());
		
		hourMessage.setText(resources.getString("hourMessage")+ "-> #" + hours.size());
		hourMessage.setVisible(true);
		
		//Update DB
		SubjectDatabaseController.updateSubjectTime(selectedIDSubjectTime, days.get(existingDaysSelectedIndex), 
				"1000-01-01 " + hourSpinner.getValue() + ":" + (minuteSpinner.getValue() < 10?  "0"+minuteSpinner.getValue(): ""+minuteSpinner.getValue()),
				duration);
		
		disableControls();
		
	}

	private void disableControls() {
		btnDelete.setDisable(true);
		dayChoiceBox.setDisable(true);
		btnUpdateSubjectTime.setDisable(true);
		hourSpinner.setDisable(true);
		minuteSpinner.setDisable(true);
		hourSpinnerDuration.setDisable(true);
		minuteSpinnerDuration.setDisable(true);
	}
	
	private void enableControls() {
		btnDelete.setDisable(false);
		dayChoiceBox.setDisable(false);
		btnUpdateSubjectTime.setDisable(false);
		hourSpinner.setDisable(false);
		minuteSpinner.setDisable(false);
		hourSpinnerDuration.setDisable(false);
		minuteSpinnerDuration.setDisable(false);
	}
	
	public static void setDays(List<String> daysParent){
		days = daysParent;
	}
	
	public static void setHours(List<String> hoursParent){
		hours = hoursParent;
	}
	
	public static void setOriginalSubject(String subject){
		originalSubject = subject;
	}
	
	public String getChoiceBoxDay(int choiceBoxIndex){
		String day = "";
		switch(choiceBoxIndex){
		case 0: 
			day = "MONDAY"; 
			break;
		case 1:	
			day = "TUESDAY";
			break;
		case 2: 
			day = "WEDNESDAY";
			break;
		case 3: 
			day = "THURSDAY";
			break;
		case 4: 
			day = "FRIDAY";
			break;
		case 5: 
			day = "SATURDAY";
			break;
		case 6: 
			day = "SUNDAY";
			break;
		default: 
			day = "N/A";
			break;
		}
		return day;
	}
}
