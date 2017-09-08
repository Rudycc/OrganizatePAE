package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class ScheduleController implements Initializable {
	@FXML
	public ListView<String> classesList;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		for(int i = 0; i < 40; i++){
			String s = "Classes " + i;
			classesList.getItems().add(s);
		}
	}

}
