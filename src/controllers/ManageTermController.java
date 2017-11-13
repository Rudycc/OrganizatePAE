package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import database.TermDatabaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ManageTermController implements Initializable, Refresher, Runnable{
	@FXML
	DatePicker startDatePicker;
	@FXML
	DatePicker endDatePicker;
	@FXML
	TextField txtFldDescription;
	private ResourceBundle rb;
	private Refreshable parent;
	private ExecutorService executorService;
	//Pointer to the Stage that contains the Pane
	Stage dialogStage = null;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		executorService = Executors.newSingleThreadExecutor();
		this.rb = resources;
	}
	
	
	public void btnCancelAction(){
		dialogStage = (Stage) startDatePicker.getScene().getWindow();
		dialogStage.close();
	}
	
	public void btnAcceptAction(){
		try{
			executorService.execute(this);
			parent.refreshData();
			dialogStage = (Stage) startDatePicker.getScene().getWindow();
			dialogStage.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}		
	}

	@Override
	public void setParent(Refreshable parent) {
		this.parent = parent;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		TermDatabaseController.insertNewTerm(startDatePicker.getValue().toString(), 
				endDatePicker.getValue().toString(), txtFldDescription.getText());
	}

}
