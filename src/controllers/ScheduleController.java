package controllers;

import java.net.URL;
import java.time.LocalDate;

import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.util.Callback;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;

public class ScheduleController implements Initializable {
	@FXML
	private Agenda agenda;
	private int actual = 1;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		agenda.setAllowDragging(false);
		agenda.setAllowResize(false);
		agenda.appointments().addAll(
	        new Agenda.AppointmentImplLocal()
	            .withStartLocalDateTime(LocalDate.now().atTime(4, 00))
	            .withEndLocalDateTime(LocalDate.now().atTime(15, 30))
	            .withDescription("It's time")
	            .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group1")),
            new Agenda.AppointmentImplLocal()
	            .withStartLocalDateTime(LocalDate.now().atTime(2, 00))
	            .withEndLocalDateTime(LocalDate.now().atTime(12, 30))
	            .withDescription("It's time")
	            .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group1")).withSummary("Test")
	            
	    );
		agenda.editAppointmentCallbackProperty().set(new Callback<Agenda.Appointment, Void>() {
			
			@Override
			public Void call(Appointment param) {
				actual++;
				//param.setStartLocalDateTime(LocalDate.now().atStartOfDay());
				//param.setWholeDay(!param.isWholeDay());
				param.setAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group" + actual));
				agenda.refresh();
				return null;
			}
		});
	}
	
	public void moveToNextWeek(){
		agenda.setDisplayedLocalDateTime(agenda.getDisplayedLocalDateTime().plusWeeks(1));
	}
	
	public void moveToPrevWeek(){
		agenda.setDisplayedLocalDateTime(agenda.getDisplayedLocalDateTime().minusWeeks(1));
	}
	
	public void manageTerm(){
		System.out.println("This is manage term");
	}
	
	public void manageSubject(){
		System.out.println("This is manage subject");
	}

}
