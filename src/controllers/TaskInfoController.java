package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import database.TaskDatabaseController;

public class TaskInfoController implements Initializable, Refresher, Runnable {
	@FXML TextArea txtAreaDescription;
	@FXML Button btnAcccept;
	@FXML Label taskId;
	@FXML CheckBox checkBoxDone;
	private Stage dialogStage;
	
	private Refreshable parent;
	private ExecutorService executorService;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		executorService = Executors.newSingleThreadExecutor();
	}
	
	public void btnAcceptAction(){
		executorService.execute(this);
		parent.refreshData();
		dialogStage = (Stage) btnAcccept.getScene().getWindow();
		dialogStage.close();
	}
	
	@Override
	public void setParent(Refreshable parent){
		this.parent = parent;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int isDone = (checkBoxDone.isSelected())? 1 : 0;
		TaskDatabaseController.updateTaskInfo(Integer.parseInt(taskId.getText()), txtAreaDescription.getText(), isDone);
	}
}
