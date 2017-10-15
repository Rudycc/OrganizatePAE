package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import cellItems.TaskCellItems;
import cells.TaskListViewCell;
import database.ExamDatabaseController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ExamController implements Initializable {
	@FXML
	public ListView<TaskCellItems> pastList;
	@FXML
	public ListView<TaskCellItems> futureList;
	ObservableList<TaskCellItems> pastObservableList = FXCollections.observableArrayList();
	ObservableList<TaskCellItems> futureObservableList = FXCollections.observableArrayList();
	@FXML Button btnNewExam;
	private ResourceBundle rb;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.rb = resources;

		pastObservableList.setAll(ExamDatabaseController.getPreviousExams());
		futureObservableList.setAll(ExamDatabaseController.getUpcomingExams());
		pastList.setItems(pastObservableList);
		futureList.setItems(futureObservableList);
		pastList.setCellFactory(new Callback<ListView<TaskCellItems>, ListCell<TaskCellItems>>() {

			@Override
			public ListCell<TaskCellItems> call(ListView<TaskCellItems> pastList) {
				// TODO Auto-generated method stub
				return new TaskListViewCell();
			}
			
		});
		
		futureList.setCellFactory(new Callback<ListView<TaskCellItems>, ListCell<TaskCellItems>>() {

			@Override
			public ListCell<TaskCellItems> call(ListView<TaskCellItems> param) {
				// TODO Auto-generated method stub
				return new TaskListViewCell();
			}
			
		});
	}
	
	public void createNewExam(){
		
		try {
			Stage dialogStage = new Stage();
			dialogStage.initOwner(btnNewExam.getScene().getWindow());
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle(this.rb.getString("titleNewExam"));
			
			GridPane newTaskPane =  FXMLLoader.load(getClass().getResource("NewTaskDialog.fxml"), this.rb);			
			dialogStage.setScene(new Scene(newTaskPane));
			
			//Sets the task type choiceBox default value			
			ChoiceBox<String> paneChoiceBox = (ChoiceBox<String>) newTaskPane.getChildren().get(8);
			paneChoiceBox.setValue(this.rb.getString("exam"));
			
			dialogStage.show();
			
		} catch (IOException e) {			
			e.printStackTrace();
		}		
	}
}
