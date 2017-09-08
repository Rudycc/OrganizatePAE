package Cells;

import java.io.IOException;

import CellItems.ClassCellItems;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ClassCellData {
	@FXML
	private GridPane classGridPane;
	@FXML
	private Label lblClass;
	@FXML
	private Label lblProfessor;
	@FXML
	private Label lblDay;
	
	
	public ClassCellData(){
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../application/ClassCell.fxml"));
		fxmlLoader.setController(this);
		try{
			fxmlLoader.load();
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	
	public void setInfo(ClassCellItems cell){
		lblClass.setText(cell.getClassName());
		lblProfessor.setText(cell.getProfessorName());
		lblDay.setText(cell.getDay());
	}
	
	public GridPane getGridPane(){
		return classGridPane;
	}
}
