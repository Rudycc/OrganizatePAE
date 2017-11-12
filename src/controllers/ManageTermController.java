package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import database.TermDatabaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ManageTermController implements Initializable, Refresher {
	@FXML
	DatePicker startDatePicker;
	@FXML
	DatePicker endDatePicker;
	@FXML
	TextField txtFldDescription;
	private ResourceBundle rb;
	private Refreshable parent;
	//Pointer to the Stage that contains the Pane
	Stage dialogStage = null;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.rb = resources;
	}
	
	
	public void btnCancelAction(){
		dialogStage = (Stage) startDatePicker.getScene().getWindow();
		dialogStage.close();
	}
	
	public void btnAcceptAction(){
		try{
			TermDatabaseController.insertNewTerm(startDatePicker.getValue().toString(), 
					endDatePicker.getValue().toString(), txtFldDescription.getText());
			parent.refreshData();
			dialogStage = (Stage) startDatePicker.getScene().getWindow();
			dialogStage.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}		
	}

	@Override
	public void setParent(Refreshable parent) {
		// TODO Auto-generated method stub
		this.parent = parent;
	}

}
