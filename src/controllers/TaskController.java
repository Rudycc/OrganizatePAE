package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import cellItems.TaskCellItems;
import cells.TaskListViewCell;
import database.TaskDatabaseController;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.ListCell;
import javafx.util.Callback;

public class TaskController implements Initializable {
	@FXML
	public ListView<TaskCellItems> pastList;
	@FXML
	public ListView<TaskCellItems> futureList;
	ObservableList<TaskCellItems> pastObservableList = FXCollections.observableArrayList();
	ObservableList<TaskCellItems> futureObservableList = FXCollections.observableArrayList();

	@FXML Button btnNewTask;
	private ResourceBundle rb;
	
	private EventHandler<MouseEvent> cellClick = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			if (event.getClickCount() == 2) {
				try {
					Stage dialogStage = new Stage();
					dialogStage.initOwner(btnNewTask.getScene().getWindow());
					dialogStage.initModality(Modality.APPLICATION_MODAL);
					dialogStage.setTitle(rb.getString("titleTaskInfo"));

					GridPane newTaskPane =  FXMLLoader.load(getClass().getResource("TaskInfo.fxml"), rb);
					
					ListView<TaskCellItems> src = (ListView<TaskCellItems>) event.getSource();
					if (!src.getItems().isEmpty()) {
						String title = src.getSelectionModel().getSelectedItem().getTaskName();
						String description = src.getSelectionModel().getSelectedItem().getDescription();
						String date = src.getSelectionModel().getSelectedItem().getDueDate().toString();
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
		pastObservableList.setAll(TaskDatabaseController.getPreviousTasks());
		futureObservableList.setAll(TaskDatabaseController.getUpcomingTasks());
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

	public void createNewTask(){

		try {
			Stage dialogStage = new Stage();
			dialogStage.initOwner(btnNewTask.getScene().getWindow());
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle(this.rb.getString("titleNewTask"));

			GridPane newTaskPane =  FXMLLoader.load(getClass().getResource("NewTaskDialog.fxml"), this.rb);
			dialogStage.setScene(new Scene(newTaskPane));					
			
			//Sets the task type choiceBox default value
			ChoiceBox<String> paneChoiceBox = (ChoiceBox<String>) newTaskPane.getChildren().get(8);
			paneChoiceBox.setValue(this.rb.getString("task"));
			
			dialogStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
