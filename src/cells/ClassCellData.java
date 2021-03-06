package cells;

import java.io.IOException;

import cellItems.ClassCellItems;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class ClassCellData {
	@FXML
	private GridPane classGridPane;
	@FXML
	private Label lblClass;
	@FXML
	private Label lblProfessor;
	@FXML
	private Label lblDay;
	@FXML 
	private Pane classColor;


	public ClassCellData(){
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ClassCell.fxml"));
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
		classColor.setStyle("-fx-background-color: " + cell.getColor());
	}

	public GridPane getGridPane(){
		return classGridPane;
	}
}
