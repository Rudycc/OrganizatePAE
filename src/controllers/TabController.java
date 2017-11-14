package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class TabController implements Initializable {
	 @FXML
	 private DashboardController dashboardController ;
	 @FXML
	 private TaskController taskController ;
	 @FXML
	 private ExamController examController ;
	 @FXML
	 private ScheduleController scheduleController ;
	 
	 private TabController self = this;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		taskController.setParent(self);
		examController.setParent(self);
		scheduleController.setParent(self);
		dashboardController.setParent(self);
	}

	
	public void refreshFromDashBoard() {
		taskController.refreshFromParent();
		examController.refreshFromParent();
		scheduleController.refreshFromParent();
	}
	
	public void refreshFromTask() {
		dashboardController.refreshFromParent();
	}
	
	public void refreshFromExam() {
		dashboardController.refreshFromParent();
	}
	
	public void refreshFromSchedule() {
		dashboardController.refreshFromParent();
	}

}
