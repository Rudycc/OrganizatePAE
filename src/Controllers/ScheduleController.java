package Controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;

import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import jfxtras.scene.control.agenda.Agenda;

public class ScheduleController implements Initializable {
	@FXML
	private Agenda agenda;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		agenda.appointments().addAll(
	        new Agenda.AppointmentImplLocal()
	            .withStartLocalDateTime(LocalDate.now().atTime(4, 00))
	            .withEndLocalDateTime(LocalDate.now().atTime(15, 30))
	            .withDescription("It's time")
	            .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group1")) // you should use a map of AppointmentGroups
	    );
		
		// TODO Auto-generated method stub
		/*for(int i = 0; i < 40; i++){
			String s1 = "Future " + i;
			ClassCellItems item = new ClassCellItems("Class" + i, "Professor " + i, "Day " + i);
			classCellItems.add(item);
		}
		classObservableList.setAll(classCellItems);
		classesList.setItems(classObservableList);
		classesList.setCellFactory(new Callback<ListView<ClassCellItems>, ListCell<ClassCellItems>>() {

			@Override
			public ListCell<ClassCellItems> call(ListView<ClassCellItems> pastList) {
				// TODO Auto-generated method stub
				return new ClassListViewCell();
			}
			
		});*/
	}
	
	public void moveToNextWeek(){
		agenda.setDisplayedLocalDateTime(agenda.getDisplayedLocalDateTime().plusWeeks(1));
	}
	
	public void moveToPrevWeek(){
		agenda.setDisplayedLocalDateTime(agenda.getDisplayedLocalDateTime().minusWeeks(1));
	}

}
