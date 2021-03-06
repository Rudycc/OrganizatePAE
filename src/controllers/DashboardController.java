package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import cellItems.ClassCellItems;
import cellItems.TaskCellItems;
import cells.ClassListViewCell;
import cells.TaskListViewCell;
import database.SubjectDatabaseController;
import database.ExamDatabaseController;
import database.TaskDatabaseController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DashboardController implements Initializable, Refreshable {
	@FXML
	public ListView<ClassCellItems> todayList;
	@FXML
	public ListView<TaskCellItems> taskList;
	@FXML
	public ListView<TaskCellItems> examList;

	ObservableList<ClassCellItems> classObservableList = FXCollections.observableArrayList();
	ObservableList<TaskCellItems> taskObservableList = FXCollections.observableArrayList();
	ObservableList<TaskCellItems> examObservableList = FXCollections.observableArrayList();
	private ResourceBundle rb;
	private Refreshable self = this;
	private TabController parent;

	private EventHandler<MouseEvent> taskClick = event -> {
		if (event.getClickCount() == 2) {
			try {
				Stage dialogStage = new Stage();
				dialogStage.initOwner(taskList.getScene().getWindow());
				dialogStage.initModality(Modality.APPLICATION_MODAL);
				dialogStage.setTitle(rb.getString("titleTaskInfo"));
				FXMLLoader loader = new FXMLLoader(getClass().getResource("TaskInfo.fxml"), rb);
				GridPane newTaskPane = loader.load();
				((Refresher) loader.getController()).setParent(self);

				ListView<TaskCellItems> src = (ListView<TaskCellItems>) event.getSource();
				if (!src.getItems().isEmpty()) {
					TaskCellItems cell = src.getSelectionModel().getSelectedItem();
					((TaskInfoController) loader.getController()).setInfo(cell);
					dialogStage.setScene(new Scene(newTaskPane));
					dialogStage.show();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

	private EventHandler<MouseEvent> classClick = event -> {
		if (event.getClickCount() == 2) {
			try {
				Stage dialogStage = new Stage();
				dialogStage.initOwner(taskList.getScene().getWindow());
				dialogStage.initModality(Modality.APPLICATION_MODAL);
				dialogStage.setTitle(rb.getString("titleEditStoredSubjects"));

				FXMLLoader loader = new FXMLLoader(Main.class.getResource("ManageSubjectsDialog.fxml"), rb);
				GridPane pane = loader.load();
				((Refresher) loader.getController()).setParent(self);

				ListView<ClassCellItems> src = (ListView<ClassCellItems>) event.getSource();
				if (!src.getItems().isEmpty()) {
					ClassCellItems cell = src.getSelectionModel().getSelectedItem();
					((ManageSubjectsController) loader.getController()).setEditInfo(cell);
					dialogStage.setScene(new Scene(pane));
					dialogStage.show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.rb = resources;
		classObservableList.setAll(SubjectDatabaseController.getTodayClasses());
		taskObservableList.setAll(TaskDatabaseController.getTodayTasks());
		examObservableList.setAll(ExamDatabaseController.getTodayExams());

		todayList.setItems(classObservableList);
		todayList.setOnMouseClicked(classClick);
		taskList.setItems(taskObservableList);
		taskList.setOnMouseClicked(taskClick);
		examList.setItems(examObservableList);
		examList.setOnMouseClicked(taskClick);

		todayList.setCellFactory(classList -> new ClassListViewCell());
		taskList.setCellFactory(taskList -> new TaskListViewCell());
		examList.setCellFactory(taskList -> new TaskListViewCell());
	}

	@Override
	public void refreshData() {
		classObservableList.setAll(SubjectDatabaseController.getTodayClasses());
		taskObservableList.setAll(TaskDatabaseController.getTodayTasks());
		examObservableList.setAll(ExamDatabaseController.getTodayExams());
		parent.refreshFromDashBoard();
	}

	public void refreshFromParent() {
		classObservableList.setAll(SubjectDatabaseController.getTodayClasses());
		taskObservableList.setAll(TaskDatabaseController.getTodayTasks());
		examObservableList.setAll(ExamDatabaseController.getTodayExams());
	}

	public void setParent(TabController parent) {
		this.parent = parent;
	}
}
