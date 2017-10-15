package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import cellItems.TaskCellItems;
import cells.TaskListViewCell;
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
	public List<TaskCellItems> pastCellItems = new ArrayList<>(40);
	public List<TaskCellItems> futureCellItems = new ArrayList<>(40);
	ObservableList<TaskCellItems> pastObservableList = FXCollections.observableArrayList();
	ObservableList<TaskCellItems> futureObservableList = FXCollections.observableArrayList();
	@FXML Button btnNewExam;
	private ResourceBundle rb;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.rb = resources;
		
		for(int i = 0; i < 40; i++){
			TaskCellItems pastItem = new TaskCellItems("Exam " + i, "Day " + i);
			pastCellItems.add(pastItem);
			TaskCellItems futureItem = new TaskCellItems("Exam " + i, "Day " + i);
			futureCellItems.add(futureItem);
		}
		pastObservableList.setAll(pastCellItems);
		futureObservableList.setAll(futureCellItems);
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
