package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class ManageTermController implements Initializable {
	@FXML
	DatePicker startDatePicker;
	@FXML
	DatePicker endDatePicker;
	private ResourceBundle rb;
	//Pointer to the Stage that contains the Pane
	Stage dialogStage;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.rb = resources;
	}
	
	
	public void btnCancelAction(){				
		dialogStage = (Stage) startDatePicker.getScene().getWindow();
		dialogStage.close();
	}
	
	public void btnAcceptAction(){
		//TODO add functionality with connection to DB
	}

}
