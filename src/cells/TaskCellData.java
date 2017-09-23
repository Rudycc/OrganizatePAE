package cells;

import java.io.IOException;

import cellItems.TaskCellItems;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class TaskCellData {
	@FXML
	private GridPane classGridPane;
	@FXML
	private Label lblTask;
	@FXML
	private Label lblDueDate;
	
	
	public TaskCellData(){
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../application/TaskCell.fxml"));
		fxmlLoader.setController(this);
		try{
			fxmlLoader.load();
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	
	public void setInfo(TaskCellItems cell){
		lblTask.setText(cell.getTaskName());
		lblDueDate.setText("Due: " + cell.getDueDate());
	}
	
	public GridPane getGridPane(){
		return classGridPane;
	}
}
