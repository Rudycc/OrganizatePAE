package controllers;

import java.io.IOException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cellItems.ClassCellItems;
import cellItems.TaskCellItems;
import cells.ClassListViewCell;
import cells.TaskListViewCell;
import database.SubjectDatabaseController;
import database.ExamDatabaseController;
import database.TaskDatabaseController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class DashboardController implements Initializable, Refreshable, Runnable {
	@FXML
	public ListView<ClassCellItems> todayList;
	@FXML
	public ListView<TaskCellItems> taskList;
	@FXML
	public ListView<TaskCellItems> examList;
	private ExecutorService executorService;
	ObservableList<ClassCellItems> classObservableList = FXCollections.observableArrayList();
	ObservableList<TaskCellItems> taskObservableList = FXCollections.observableArrayList();
	ObservableList<TaskCellItems> examObservableList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		executorService = Executors.newSingleThreadExecutor();
		executorService.execute(this);
		
		todayList.setItems(classObservableList);
		taskList.setItems(taskObservableList);
		examList.setItems(examObservableList);

		todayList.setCellFactory(new Callback<ListView<ClassCellItems>, ListCell<ClassCellItems>>() {

			@Override
			public ListCell<ClassCellItems> call(ListView<ClassCellItems> todayList) {
				return new ClassListViewCell();
			}

		});

		taskList.setCellFactory(new Callback<ListView<TaskCellItems>, ListCell<TaskCellItems>>() {

			@Override
			public ListCell<TaskCellItems> call(ListView<TaskCellItems> taskList) {
				return new TaskListViewCell();
			}

		});

		examList.setCellFactory(new Callback<ListView<TaskCellItems>, ListCell<TaskCellItems>>() {

			@Override
			public ListCell<TaskCellItems> call(ListView<TaskCellItems> examList) {
				return new TaskListViewCell();
			}

		});
	}

	@Override
	public void refreshData() {
		classObservableList.setAll(SubjectDatabaseController.getTodayClasses());
		taskObservableList.setAll(TaskDatabaseController.getTodayTasks());
		examObservableList.setAll(ExamDatabaseController.getTodayExams());
	}

	@Override
	public void run() {
		classObservableList.setAll(SubjectDatabaseController.getTodayClasses());
		taskObservableList.setAll(TaskDatabaseController.getTodayTasks());
		examObservableList.setAll(ExamDatabaseController.getTodayExams());
	}
}
