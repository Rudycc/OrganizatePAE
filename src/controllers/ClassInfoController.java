package controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import application.Main;
import cellItems.ClassCellItems;
import database.SubjectDatabaseController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
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
	
	@SuppressWarnings("unchecked")
	public void setDialogValues(GridPane pane){
		ClassCellItems currClass = classes.get(classIndex);
		
		//Hides and shows buttons needed for editing a subject
		Button btn = (Button) pane.getChildren().get(24);
		btn.setVisible(true);
		btn = (Button) pane.getChildren().get(19);
		btn.setVisible(false);
		
		TextField txt = (TextField) pane.getChildren().get(1);
		txt.setText(currClass.getClassName());
		
		txt = (TextField) pane.getChildren().get(3);
		txt.setText(currClass.getProfessorName());
		
		ChoiceBox<String> choiceBox = (ChoiceBox<String>) pane.getChildren().get(5);
		choiceBox.setValue(currClass.getSemester());
		
		ManageSubjectsController.setOriginalSubject(currClass.getClassName());
	}
	
	public void btnEditAction(){
		try {
			
			Stage dialogStage = new Stage();
			dialogStage.initOwner(btnNext.getScene().getWindow());
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle(rb.getString("titleEditStoredSubjects"));
			
			GridPane pane =  FXMLLoader.load(Main.class.getResource("ManageSubjectsDialog.fxml"), this.rb);
			int currSubjectID = classes.get(classIndex).getSubjectId();
			setDialogValues(pane);
			
			dialogStage.setScene(new Scene(pane));
			dialogStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
