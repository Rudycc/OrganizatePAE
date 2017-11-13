package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cellItems.TaskCellItems;
import cells.TaskListViewCell;
import database.ExamDatabaseController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ExamController implements Initializable, Refreshable, Refresher, Runnable{
	@FXML
	public ListView<TaskCellItems> pastList;
	@FXML
	public ListView<TaskCellItems> futureList;
	ObservableList<TaskCellItems> pastObservableList = FXCollections.observableArrayList();
	ObservableList<TaskCellItems> futureObservableList = FXCollections.observableArrayList();
	@FXML
	private Button btnNewExam;
	private ExecutorService executorService;
	private ResourceBundle rb;
	private Refreshable self = this;
	private Refreshable parent;

	private EventHandler<MouseEvent> cellClick = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			if (event.getClickCount() == 2) {
				try {
					Stage dialogStage = new Stage();
					dialogStage.initOwner(btnNewExam.getScene().getWindow());
					dialogStage.initModality(Modality.APPLICATION_MODAL);
					dialogStage.setTitle(rb.getString("titleExamInfo"));

					FXMLLoader loader = new FXMLLoader(getClass().getResource("ExamInfo.fxml"), rb);
					GridPane newTaskPane = loader.load();
					((Refresher)loader.getController()).setParent(self);

					ListView<TaskCellItems> src = (ListView<TaskCellItems>) event.getSource();
					if (!src.getItems().isEmpty()) {
						String title = src.getSelectionModel().getSelectedItem().getTaskName();
						String description = src.getSelectionModel().getSelectedItem().getDescription();
						String date = src.getSelectionModel().getSelectedItem().getDueDate().toString();
						int taskId = src.getSelectionModel().getSelectedItem().getTaskId();
						((Label) newTaskPane.getChildren().get(5)).setText(taskId+"");
						((Label) newTaskPane.getChildren().get(1)).setText(title);
						((Label) newTaskPane.getChildren().get(3)).setText(rb.getString("due") +": "+ date);
						((TextArea) newTaskPane.getChildren().get(0)).setText(description);
						((CheckBox) newTaskPane.getChildren().get(2)).setSelected(src.getSelectionModel().getSelectedItem().isDone());

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
		executorService = Executors.newSingleThreadExecutor();
		executorService.execute(this);
		
		pastList.setItems(pastObservableList);
		futureList.setItems(futureObservableList);
		pastList.setOnMouseClicked(this.cellClick);
		futureList.setOnMouseClicked(this.cellClick);
		
		pastList.setCellFactory(new Callback<ListView<TaskCellItems>, ListCell<TaskCellItems>>() {

			@Override
			public ListCell<TaskCellItems> call(ListView<TaskCellItems> pastList) {
				return new TaskListViewCell();
			}

		});

		futureList.setCellFactory(new Callback<ListView<TaskCellItems>, ListCell<TaskCellItems>>() {

			@Override
			public ListCell<TaskCellItems> call(ListView<TaskCellItems> param) {
				return new TaskListViewCell();
			}

		});
	}

	public void createNewExam() {

		try {
			Stage dialogStage = new Stage();
			dialogStage.initOwner(btnNewExam.getScene().getWindow());
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle(this.rb.getString("titleNewExam"));

			FXMLLoader loader = new FXMLLoader(getClass().getResource("NewTaskDialog.fxml"), this.rb);
			GridPane newTaskPane = loader.load();
			((Refresher)loader.getController()).setParent(self);
			
			dialogStage.setScene(new Scene(newTaskPane));

			// Sets the task type choiceBox default value
			ChoiceBox<String> paneChoiceBox = (ChoiceBox<String>) newTaskPane.getChildren().get(8);
			paneChoiceBox.setValue(this.rb.getString("exam"));

			dialogStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void refreshData() {
		pastObservableList.setAll(ExamDatabaseController.getPreviousExams());
		futureObservableList.setAll(ExamDatabaseController.getUpcomingExams());
		parent.refreshData();
	}

	@Override
	public void setParent(Refreshable parent) {
		this.parent = parent;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		pastObservableList.setAll(ExamDatabaseController.getPreviousExams());
		futureObservableList.setAll(ExamDatabaseController.getUpcomingExams());
	}
}
