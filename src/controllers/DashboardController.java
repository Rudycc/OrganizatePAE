package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import cellItems.ClassCellItems;
import cellItems.TaskCellItems;
import cells.ClassListViewCell;
import cells.TaskListViewCell;
import database.ClassDatabaseController;
import database.ExamDatabaseController;
import database.TaskDatabaseController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class DashboardController implements Initializable {
	@FXML
	public ListView<ClassCellItems> todayList;
	@FXML
	public ListView<TaskCellItems> taskList;
	@FXML
	public ListView<TaskCellItems> examList;
	
	ObservableList<ClassCellItems> classObservableList = FXCollections.observableArrayList();
	ObservableList<TaskCellItems> taskObservableList = FXCollections.observableArrayList();
	ObservableList<TaskCellItems> examObservableList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		classObservableList.setAll(ClassDatabaseController.getTodayClasses());
		taskObservableList.setAll(TaskDatabaseController.getTodayTasks());
		examObservableList.setAll(ExamDatabaseController.getTodayExams());
		
		todayList.setItems(classObservableList);
		taskList.setItems(taskObservableList);
		examList.setItems(examObservableList);
		
		todayList.setCellFactory(new Callback<ListView<ClassCellItems>, ListCell<ClassCellItems>>() {

			@Override
			public ListCell<ClassCellItems> call(ListView<ClassCellItems> todayList) {
				// TODO Auto-generated method stub
				return new ClassListViewCell();
			}
			
		});
		
		taskList.setCellFactory(new Callback<ListView<TaskCellItems>, ListCell<TaskCellItems>>() {

			@Override
			public ListCell<TaskCellItems> call(ListView<TaskCellItems> taskList) {
				// TODO Auto-generated method stub
				return new TaskListViewCell();
			}
			
		});
		
		examList.setCellFactory(new Callback<ListView<TaskCellItems>, ListCell<TaskCellItems>>() {

			@Override
			public ListCell<TaskCellItems> call(ListView<TaskCellItems> examList) {
				// TODO Auto-generated method stub
				return new TaskListViewCell();
			}
			
		});
		
		
	}

}
