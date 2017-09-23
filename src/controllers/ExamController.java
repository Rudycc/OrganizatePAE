package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import cellItems.TaskCellItems;
import cells.TaskListViewCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
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
}
