package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import database.MyDBConnection;

public class NewTaskDialogController implements Initializable{

	@FXML Button btnCancel;
	@FXML Button btnAccept;
	@FXML ChoiceBox<String> choiceBoxClassChooser;
	@FXML ChoiceBox<String> choiceBoxTypeChooser;
	@FXML TextField txtName;
	@FXML DatePicker datePicker;
	
	private Connection conn;
	private ResultSet rs;
	private PreparedStatement insertTask;
	private PreparedStatement getSubjectId;
	
	//Pointer to the Stage that contains the Pane
	Stage dialogStage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//Sets the classes choiceBox data
		ObservableList<String> classesChoiceBoxData = FXCollections.observableArrayList(
				"Software Design", "Operating Systems", "Networking Fundamentals", "Database Design Fundamentals");
		choiceBoxClassChooser.setItems(classesChoiceBoxData);
		
		//Sets the task type choiceBox data
		ObservableList<String> typeChoiceBoxData = FXCollections.observableArrayList(
				resources.getString("task"), resources.getString("lab"), resources.getString("exam")); 
		choiceBoxTypeChooser.setItems(typeChoiceBoxData);
		
		try {
			
			this.conn = MyDBConnection.getConnection();
			conn.setAutoCommit(false);
			String query = "INSERT INTO Task(Title, Type, IsDone, IDSubject, DueDate) VALUES(?,?,?,?,?)";
			insertTask = conn.prepareStatement(query);
			query = "SELECT IDSubject FROM Subject WHERE Name = ?";
			getSubjectId = conn.prepareStatement(query);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void btnCancelAction(){				
		dialogStage = (Stage) btnCancel.getScene().getWindow();
		dialogStage.close();
	}
	
	//Add a new Task / Exam / Practice in the DB
	public void btnAcceptAction(){
		//Validations
		if(choiceBoxClassChooser.getValue() == null || datePicker.getValue() == null || txtName.getText() == ""){
			//Show an Alert!
			System.out.println("Empty Fields");
			return;
		}
		try {
			// Create a SQL statement
			insertTask.setString(1, txtName.getText());
			insertTask.setString(2,choiceBoxTypeChooser.getValue());
			insertTask.setInt(3, 0);
			insertTask.setInt(4, getIDSubject());
			insertTask.setString(5,datePicker.getValue()+"");
			insertTask.executeUpdate();
			// Commit changes
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Close the window
		dialogStage = (Stage) btnCancel.getScene().getWindow();
		dialogStage.close();
	}
	
	//Get the task's subject id 
	public int getIDSubject(){
		
		try {
			
			getSubjectId.setString(1, choiceBoxClassChooser.getValue());
			rs = getSubjectId.executeQuery();
			//Return an id if it exist 
			while(rs.next()){return rs.getInt("IDSubject");}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("No Id found");
		}
		
		return 0;
	}
}
