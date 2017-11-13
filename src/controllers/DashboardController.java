package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

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

	private EventHandler<MouseEvent> taskClick = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			if (event.getClickCount() == 2) {
				try {
					Stage dialogStage = new Stage();
					dialogStage.initOwner(taskList.getScene().getWindow());
					dialogStage.initModality(Modality.APPLICATION_MODAL);
					dialogStage.setTitle(rb.getString("titleTaskInfo"));
					
					FXMLLoader loader = new FXMLLoader(getClass().getResource("TaskInfo.fxml"), rb);

					GridPane newTaskPane = loader.load();
					
					((Refresher)loader.getController()).setParent(self);
					
					ListView<TaskCellItems> src = (ListView<TaskCellItems>) event.getSource();
					if (!src.getItems().isEmpty()) {
						TaskCellItems cell = src.getSelectionModel().getSelectedItem();
						((TaskInfoController)loader.getController()).setInfo(cell);

						dialogStage.setScene(new Scene(newTaskPane));
						dialogStage.show();
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
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
		taskList.setItems(taskObservableList);
		taskList.setOnMouseClicked(taskClick);
		examList.setItems(examObservableList);
		examList.setOnMouseClicked(taskClick);

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
}
