package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TaskController implements Initializable {
	@FXML
	public ListView<String> pastList;
	@FXML
	public ListView<String> futureList;
	
	@FXML Button btnNewTask;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		for(int i = 0; i < 40; i++){
			String s = "Past " + i;
			String s1 = "Future " + i;
			pastList.getItems().add(s);		
			futureList.getItems().add(s1);
		}
	}
	
	
	public void createNewTask(){
		
		try {
			Stage dialogStage = new Stage();
			dialogStage.initOwner(btnNewTask.getScene().getWindow());
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle("Create New Task");
			
			GridPane newTaskPane =  FXMLLoader.load(getClass().getResource("NewTaskDialog.fxml"));
			
			dialogStage.setScene(new Scene(newTaskPane));	
			dialogStage.show();
			
		} catch (IOException e) {			
			System.out.println("Error in createNewTask. StackTrace:\n");
			e.printStackTrace();
		}		
	}
	

}
