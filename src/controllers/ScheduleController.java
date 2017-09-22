package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import cellItems.ClassCellItems;
import cells.ClassListViewCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ScheduleController implements Initializable {
	@FXML
	public ListView<ClassCellItems> classesList;
	public List<ClassCellItems> classCellItems = new ArrayList<>(40);
	ObservableList<ClassCellItems> classObservableList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		for(int i = 0; i < 40; i++){
			ClassCellItems item = new ClassCellItems("Class" + i, "Professor " + i, "Day " + i);
			classCellItems.add(item);
		}
		classObservableList.setAll(classCellItems);
		classesList.setItems(classObservableList);
		classesList.setCellFactory(new Callback<ListView<ClassCellItems>, ListCell<ClassCellItems>>() {

			@Override
			public ListCell<ClassCellItems> call(ListView<ClassCellItems> pastList) {
				// TODO Auto-generated method stub
				return new ClassListViewCell();
			}

		});
	}

}
