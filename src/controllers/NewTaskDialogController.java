package controllers;

import java.net.URL;
import java.sql.Connection;
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
	private Statement st;
	private ResultSet rs;
	private String query;
	
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
			this.st = conn.createStatement();
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
		//Create the SQLSatement 
		query = "INSERT INTO Task(Title, Type, IsDone, IDSubject, DueDate) VALUES(";
		query += "'" + txtName.getText() + "',";
		query += "'" + choiceBoxTypeChooser.getValue() + "',";
		query += "0,";
		query += getIDSubject() + ",";
		query += "'" + datePicker.getValue() +"')";
		System.out.println(query);
		try {
			st.executeUpdate(query);
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
		query = "SELECT IDSubject FROM Subject WHERE Name = '" + choiceBoxClassChooser.getValue() + "'";
		try {
			rs = st.executeQuery(query);
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
