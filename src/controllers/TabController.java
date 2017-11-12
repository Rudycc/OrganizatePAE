package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class TabController implements Initializable, Refreshable {
	 @FXML
	 private DashboardController dashboardController ;
	 @FXML
	 private TaskController taskController ;
	 @FXML
	 private ExamController examController ;
	 @FXML
	 private ScheduleController scheduleController ;
	 
	 private Refreshable self = this;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		taskController.setParent(self);
		examController.setParent(self);
		scheduleController.setParent(self);
	}

	@Override
	public void refreshData() {
		dashboardController.refreshData();
	}

}
