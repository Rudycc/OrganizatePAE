package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class ExamController implements Initializable {
	@FXML
	public ListView<String> pastList;
	@FXML
	public ListView<String> futureList;
	
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
}
