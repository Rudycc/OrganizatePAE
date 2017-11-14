package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import cellItems.TaskCellItems;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import database.TaskDatabaseController;

public class TaskInfoController implements Initializable, Refresher {
	@FXML
	TextArea txtAreaDescription;
	@FXML
	Button btnAcccept;
	@FXML
	Label taskId;
	@FXML
	Label taskTitle;
	@FXML
	Label taskDate;
	@FXML
	CheckBox checkBoxDone;
	@FXML
	GridPane gridPane;
	
	private Stage dialogStage;
	
	private Refreshable parent;
	private ResourceBundle rb;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.rb = resources;
		gridPane.setStyle(Main.getThemeString());
	}
	
	public void btnAcceptAction(){
		int isDone = (checkBoxDone.isSelected())? 1 : 0;
		TaskDatabaseController.updateTaskInfo(Integer.parseInt(taskId.getText()), txtAreaDescription.getText(), isDone);
		parent.refreshData();
		dialogStage = (Stage) btnAcccept.getScene().getWindow();
		dialogStage.close();
	}
	
	public void setInfo(TaskCellItems cell) {
		taskId.setText(cell.getTaskId()+"");
		taskTitle.setText(cell.getTaskName());
		taskDate.setText(rb.getString("due") +": "+ cell.getDueDate());
		txtAreaDescription.setText(cell.getDescription());
		checkBoxDone.setSelected(cell.isDone());
	}
	
	@Override
	public void setParent(Refreshable parent){
		this.parent = parent;
	}
}
