package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ClassInfoController implements Initializable {
	
	@FXML
	Button btnAccept;
	@FXML
	TextArea txtAreaClassInfo;
	
	private ResourceBundle rb;
	//Pointer to the Stage that contains the Pane
	Stage dialogStage = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.rb = resources;
	}
	
	public void btnAcceptAction(){
		dialogStage = (Stage) btnAccept.getScene().getWindow();
		dialogStage.close();
	}
	
	public void btnEditAction(){
		System.out.println("This is edit subject");
	}
	
	public void btnNext(){
		
	}
	
	public void btnPrevious(){
		
	}
}
