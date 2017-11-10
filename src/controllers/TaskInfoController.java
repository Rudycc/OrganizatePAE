package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import database.TaskDatabaseController;

public class TaskInfoController implements Initializable {
	@FXML TextArea txtAreaDescription;
	@FXML Button btnAcccept;
	@FXML Label taskId;
	@FXML CheckBox checkBoxDone;
	private Stage dialogStage;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void btnAcceptAction(){
		int isDone = (checkBoxDone.isSelected())? 1 : 0;
		TaskDatabaseController.updateTaskInfo(Integer.parseInt(taskId.getText()), txtAreaDescription.getText(), isDone);
		dialogStage = (Stage) btnAcccept.getScene().getWindow();
		dialogStage.close();
	}
}
