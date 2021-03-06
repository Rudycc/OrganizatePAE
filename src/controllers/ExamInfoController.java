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
import database.ExamDatabaseController;

public class ExamInfoController implements Initializable, Refresher {
	@FXML TextArea txtAreaDescription;
	@FXML Button btnAcccept;
	@FXML Label examId;
	@FXML CheckBox checkBoxDone;
	private Stage dialogStage;
	private Refreshable parent;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void btnAcceptAction(){
		int isDone = (checkBoxDone.isSelected())? 1 : 0;
		ExamDatabaseController.updateExamInfo(Integer.parseInt(examId.getText()), txtAreaDescription.getText(), isDone);
		parent.refreshData();
		dialogStage = (Stage) btnAcccept.getScene().getWindow();
		dialogStage.close();
	}

	@Override
	public void setParent(Refreshable parent) {
		this.parent = parent;
	}
}
