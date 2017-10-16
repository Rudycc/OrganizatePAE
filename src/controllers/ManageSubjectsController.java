package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;

public class ManageSubjectsController implements Initializable {

	@FXML
	TextField txtSubjectName;
	@FXML
	TextField txtProfName;
	@FXML
	ChoiceBox<String> semesterChoiceBox;
	@FXML
	ColorPicker colorPicker;
	@FXML
	ChoiceBox<String> dayChoiceBox;
	@FXML
	TextField txtTime;
	@FXML
	Button btnAddSubjectTime;
	@FXML
	Button btnManageExisting;
	@FXML
	Button btnAccept;
	@FXML
	Button btnCancel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}

}
