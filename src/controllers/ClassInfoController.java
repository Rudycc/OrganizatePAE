package controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cellItems.ClassCellItems;
import database.SubjectDatabaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ClassInfoController implements Initializable {
	
	@FXML
	Button btnAccept;
	@FXML
	Button btnNext;
	@FXML
	Button btnPrevious;
	@FXML
	TextArea txtAreaClassInfo;
	
	private ResourceBundle rb;
	//Pointer to the Stage that contains the Pane
	private Stage dialogStage = null;
	
	private List<ClassCellItems> classes;
	private int classIndex;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.rb = resources;
		this.classes = SubjectDatabaseController.getAllClasses();
		this.classIndex = 0;
	}
	
	public void btnAcceptAction(){
		dialogStage = (Stage) btnAccept.getScene().getWindow();
		dialogStage.close();
	}
	
	public void btnEditAction(){
		System.out.println("This is edit subject");
	}
	
	public void setClassInfo(String info){
		txtAreaClassInfo.setText(info);
	}
	
	public void btnNextAction(){
		
		if(classIndex+1 < classes.size()){			
			if(btnPrevious.disableProperty().get() == true)
				btnPrevious.disableProperty().set(false);
			
			classIndex++;
			setClassInfo(classes.get(classIndex).toString());
			
			if(classIndex == classes.size()-1)
				btnNext.disableProperty().set(true);
		}		
	}
	
	public void btnPreviousAction(){
		if(classIndex-1 >= 0){
			if(btnNext.disableProperty().get() == true)
				btnNext.disableProperty().set(false);
			
			classIndex--;
			setClassInfo(classes.get(classIndex).toString());
			
			if(classIndex == 0){
				btnPrevious.disableProperty().set(true);
			}
		}	
	}
}
