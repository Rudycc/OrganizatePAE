package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import application.*;
import cellItems.ClassCellItems;
import database.SubjectDatabaseController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;

public class ScheduleController implements Initializable, Refreshable {
	@FXML
	private Agenda agenda;
	private ResourceBundle rb;
	private Refreshable self = this;
	private TabController parent;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.rb = resources;
		agenda.setAllowDragging(false);
		agenda.setAllowResize(false);
		List<ClassCellItems> subjects = SubjectDatabaseController.getAllClasses();
		List<Appointment> schedule = new ArrayList<>();
		subjects.forEach((subject) -> {
			subject.getTimes().forEach((time) -> {
				LocalDate start = time.getStart();
				while (!time.getDay().equals(start.getDayOfWeek().toString())) {
					start = start.plusDays(1);
				}
				while (start.isBefore(time.getEnd())) {
					schedule.add(new Agenda.AppointmentImplLocal().withStartLocalDateTime(start.atTime(time.getTime()))
							.withEndLocalDateTime(start.atTime(time.getTime().plusHours((long) time.getDuration())
									.plusMinutes((long) ((time.getDuration() - (int) time.getDuration()) * 60))))
							.withSummary(subject.getClassName() + "\n" + subject.getProfessorName())
							.withLocation(subject.getSubjectId() + "")
							.withAppointmentGroup(new Agenda.AppointmentGroupImpl()
									.withStyleClass("group" + ((subject.getSubjectId() % 23) + 1))));
					start = start.plusDays(7);
				}
			});
		});

		agenda.appointments().addAll(schedule);

		agenda.setEditAppointmentCallback(new Callback<Agenda.Appointment, Void>() {

			@Override
			public Void call(Appointment param) {
				return null;
			}
		});

		agenda.setActionCallback(new Callback<Agenda.Appointment, Void>() {

			@Override
			public Void call(Appointment param) {
				List<ClassCellItems> subjects = SubjectDatabaseController.getAllClasses();
				ClassCellItems current = null;
				for (int i = 0; i < subjects.size(); i++) {
					if (Integer.parseInt(param.getLocation()) == subjects.get(i).getSubjectId()) {
						current = subjects.get(i);
						break;
					}
				}
				if (current != null) {
					try {
						Stage dialogStage = new Stage();
						dialogStage.initOwner(agenda.getScene().getWindow());
						dialogStage.initModality(Modality.APPLICATION_MODAL);
						dialogStage.setTitle(rb.getString("titleEditStoredSubjects"));

						FXMLLoader loader = new FXMLLoader(Main.class.getResource("ManageSubjectsDialog.fxml"), rb);
						GridPane pane = loader.load();
						((Refresher) loader.getController()).setParent(self);
						((ManageSubjectsController) loader.getController()).setEditInfo(current);

						dialogStage.setScene(new Scene(pane));
						dialogStage.show();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				agenda.refresh();
				return null;
			}
		});

	}

	public void moveToNextWeek() {
		agenda.setDisplayedLocalDateTime(agenda.getDisplayedLocalDateTime().plusWeeks(1));
	}

	public void moveToPrevWeek() {
		agenda.setDisplayedLocalDateTime(agenda.getDisplayedLocalDateTime().minusWeeks(1));
	}

	public void manageTerm() {
		try {

			Stage dialogStage = new Stage();
			dialogStage.initOwner(agenda.getScene().getWindow());
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle(rb.getString("titleManageTerm"));

			FXMLLoader loader = new FXMLLoader(Main.class.getResource("ManageTermDialog.fxml"), this.rb);
			GridPane pane = loader.load();
			pane.setStyle(Main.getThemeString());
			((Refresher) loader.getController()).setParent(self);

			dialogStage.setScene(new Scene(pane));
			dialogStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void manageSubject() {
		try {

			Stage dialogStage = new Stage();
			dialogStage.initOwner(agenda.getScene().getWindow());
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle(rb.getString("titleManageSubjects"));

			FXMLLoader loader = new FXMLLoader(Main.class.getResource("ManageSubjectsDialog.fxml"), this.rb);
			GridPane pane = loader.load();
			pane.setStyle(Main.getThemeString());
			((Refresher) loader.getController()).setParent(self);

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
				while (!time.getDay().equals(start.getDayOfWeek().toString())) {
					start = start.plusDays(1);
				}
				while (start.isBefore(time.getEnd())) {
					schedule.add(new Agenda.AppointmentImplLocal().withStartLocalDateTime(start.atTime(time.getTime()))
							.withEndLocalDateTime(start.atTime(time.getTime().plusHours((long) time.getDuration())
									.plusMinutes((long) ((time.getDuration() - (int) time.getDuration()) * 60))))
							.withSummary(subject.getClassName() + "\n" + subject.getProfessorName())
							.withLocation(subject.getSubjectId() + "")
							.withAppointmentGroup(new Agenda.AppointmentGroupImpl()
									.withStyleClass("group" + ((subject.getSubjectId() % 23) + 1))));
					start = start.plusDays(7);
				}
			});
		});

		agenda.appointments().clear();
		agenda.appointments().addAll(schedule);
		agenda.refresh();
		parent.refreshFromDashBoard();
	}

	public void refreshFromParent() {
		List<ClassCellItems> subjects = SubjectDatabaseController.getAllClasses();
		List<Appointment> schedule = new ArrayList<>();
		subjects.forEach((subject) -> {
			subject.getTimes().forEach((time) -> {
				LocalDate start = time.getStart();
				while (!time.getDay().equals(start.getDayOfWeek().toString())) {
					start = start.plusDays(1);
				}
				while (start.isBefore(time.getEnd())) {
					schedule.add(new Agenda.AppointmentImplLocal().withStartLocalDateTime(start.atTime(time.getTime()))
							.withEndLocalDateTime(start.atTime(time.getTime().plusHours((long) time.getDuration())
									.plusMinutes((long) ((time.getDuration() - (int) time.getDuration()) * 60))))
							.withSummary(subject.getClassName() + "\n" + subject.getProfessorName())
							.withLocation(subject.getSubjectId() + "")
							.withAppointmentGroup(new Agenda.AppointmentGroupImpl()
									.withStyleClass("group" + ((subject.getSubjectId() % 23) + 1))));
					start = start.plusDays(7);
				}
			});
		});

		agenda.appointments().clear();
		agenda.appointments().addAll(schedule);
		agenda.refresh();
	}

	public void setParent(TabController parent) {
		this.parent = parent;
	}

}
