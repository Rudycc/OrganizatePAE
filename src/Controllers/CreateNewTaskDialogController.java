package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class CreateNewTaskDialogController implements Initializable{
	
	@FXML Button btnCancel;
	@FXML Button btnAccept;
	@FXML RadioButton rBtnExam;
	@FXML RadioButton rBtnLab;
	@FXML RadioButton rBtnTask;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		final ToggleGroup group = new ToggleGroup();
		rBtnExam.setToggleGroup(group);
		rBtnLab.setToggleGroup(group);
		rBtnTask.setToggleGroup(group);
	}
	
	public void btnCancelAction(){
		Stage dialogStage = (Stage) btnCancel.getScene().getWindow();		
		dialogStage.close();
	}
	
}
