package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class DashboardController implements Initializable {
	@FXML
	public ListView<String> todayList;
	@FXML
	public ListView<String> taskList;
	@FXML
	public ListView<String> examList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 40; i++) {
			todayList.getItems().add("Today " + i);
			taskList.getItems().add("Task " + i);
			examList.getItems().add("Exam " + i);
		}
	}

}
