package Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import CellItems.ClassCellItems;
import Cells.ListViewCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;



public class TaskController implements Initializable {
	@FXML
	public ListView<ClassCellItems> pastList;
	@FXML
	public ListView<String> futureList;
	public List<ClassCellItems> cellItems = new ArrayList<>(40);
	ObservableList<ClassCellItems> observableList = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		for(int i = 0; i < 40; i++){
			String s1 = "Future " + i;
			ClassCellItems item = new ClassCellItems("Class" + i, "Professor " + i, "Day " + i);
			cellItems.add(item);
			futureList.getItems().add(s1);
		}
		observableList.setAll(cellItems);
		pastList.setItems(observableList);
		pastList.setCellFactory(new Callback<ListView<ClassCellItems>, ListCell<ClassCellItems>>() {

			@Override
			public ListCell<ClassCellItems> call(ListView<ClassCellItems> pastList) {
				// TODO Auto-generated method stub
				return new ListViewCell();
			}
			
		});
		
	}

}
