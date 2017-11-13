package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import application.*;
import cellItems.ClassCellItems;
import database.SubjectDatabaseController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import jfxtras.scene.control.agenda.Agenda.AppointmentGroup;
import jfxtras.scene.control.agenda.Agenda.AppointmentGroupImpl;

public class ScheduleController implements Initializable, Refreshable, Refresher, Runnable{
	@FXML
	private Agenda agenda;
	private ResourceBundle rb;
	private Refreshable self = this;
	private Refreshable parent;
	private ExecutorService executorService;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.rb = resources;
		agenda.setAllowDragging(false);
		agenda.setAllowResize(false);
		executorService = Executors.newSingleThreadExecutor();
		executorService.execute(this);
		
		agenda.editAppointmentCallbackProperty().set(new Callback<Agenda.Appointment, Void>() {
			
			@Override
			public Void call(Appointment param) {
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
		try {
			
			Stage dialogStage = new Stage();
			dialogStage.initOwner(agenda.getScene().getWindow());
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle(rb.getString("titleManageTerm"));
			
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("ManageTermDialog.fxml"), this.rb);
			GridPane pane =  loader.load();
			((Refresher)loader.getController()).setParent(self);
			
			dialogStage.setScene(new Scene(pane));
			dialogStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void manageSubject(){
		try {
			
			Stage dialogStage = new Stage();
			dialogStage.initOwner(agenda.getScene().getWindow());
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle(rb.getString("titleManageSubjects"));
			
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("ManageSubjectsDialog.fxml"), this.rb);
			GridPane pane =  loader.load();
			((Refresher)loader.getController()).setParent(self);
			
			dialogStage.setScene(new Scene(pane));
			dialogStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void refreshData() {
		List<ClassCellItems> subjects = SubjectDatabaseController.getAllClasses();
		List<Appointment> schedule = new ArrayList<>();
		subjects.forEach((subject) -> {
			subject.getTimes().forEach((time) -> {
				LocalDate start = time.getStart();
				while(!time.getDay().equals(start.getDayOfWeek().toString())){
					start = start.plusDays(1);
				}
				 while(start.isBefore(time.getEnd())){
					 schedule.add(new Agenda.AppointmentImplLocal()
							 .withStartLocalDateTime(start.atTime(time.getTime()))
							 .withEndLocalDateTime(start.atTime(time.getTime().plusHours(2)))
							 .withSummary(subject.getClassName() + "\n" + subject.getProfessorName())
							 .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group" + (subject.getSubjectId()%20) + 1))
							 );
					 start = start.plusDays(7);
				 }
			});
		});
		
		agenda.appointments().clear();
		agenda.appointments().addAll(schedule);
		agenda.refresh();
		parent.refreshData();
	}

	@Override
	public void setParent(Refreshable parent) {
		this.parent = parent;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		List<ClassCellItems> subjects = SubjectDatabaseController.getAllClasses();
		List<Appointment> schedule = new ArrayList<>();
		subjects.forEach((subject) -> {
			subject.getTimes().forEach((time) -> {
				LocalDate start = time.getStart();
				while(!time.getDay().equals(start.getDayOfWeek().toString())){
					start = start.plusDays(1);
				}
				 while(start.isBefore(time.getEnd())){
					 schedule.add(new Agenda.AppointmentImplLocal()
							 .withStartLocalDateTime(start.atTime(time.getTime()))
							 .withEndLocalDateTime(start.atTime(time.getTime().plusHours(2)))
							 .withSummary(subject.getClassName() + "\n" + subject.getProfessorName())
							 .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group" + (subject.getSubjectId()%20) + 1))
							 );
					 start = start.plusDays(7);
				 }
			});
		});

		agenda.appointments().addAll(schedule);
	}

}
