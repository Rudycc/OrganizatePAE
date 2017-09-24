package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class CreateNewTaskDialogController implements Initializable{

	@FXML Button btnCancel;
	@FXML Button btnAccept;
	@FXML ChoiceBox choiceBoxTypeChooser;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}

	public void btnCancelAction(){
		Stage dialogStage = (Stage) btnCancel.getScene().getWindow();
		dialogStage.close();
	}
}
