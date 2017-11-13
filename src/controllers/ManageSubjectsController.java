package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import application.Main;
import cellItems.ClassCellItems;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import database.SubjectDatabaseController;

public class ManageSubjectsController implements Initializable, Refresher {
	@FXML
	GridPane rootGridPane;
	@FXML
	TextField txtSubject;
	@FXML
	TextField txtProfessor;
	@FXML
	ChoiceBox<String> semesterChoiceBox;
	@FXML
	ColorPicker colorPicker;
	@FXML
	ChoiceBox<String> dayChoiceBox;
	@FXML
	Spinner<Integer> hourSpinner;
	@FXML
	Spinner<Integer> minuteSpinner;
	@FXML
	Spinner<Integer> hourSpinnerDuration;
	@FXML
	Spinner<Integer> minuteSpinnerDuration;
	@FXML
	Button btnAddSubjectTime;
	@FXML
	Button btnAccept;
	@FXML
	Button btnCancel;
	@FXML
	Button btnEditDays;
	@FXML
	Label hourMessage;

	private List<String> days;
	private List<String> hours;
	private List<Float> durations;
	private String minutes;
	private static String originalSubject;
	private ResourceBundle resources;
	private Stage dialogStage;
	private Refreshable parent;
	private ObservableList<Integer> semesterIDs;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rootGridPane.setStyle(Main.getThemeString());
		ObservableList<String> typeChoiceBoxData = FXCollections.observableArrayList(resources.getString("monday"),
				resources.getString("tuesday"), resources.getString("wednesday"), resources.getString("thursday"),
				resources.getString("friday"), resources.getString("saturday"), resources.getString("sunday"));
		dayChoiceBox.setItems(typeChoiceBoxData);
		dayChoiceBox.getSelectionModel().select(0);

		semesterChoiceBox.setItems(SubjectDatabaseController.getAllSemesterNames());

		days = new ArrayList<String>();
		hours = new ArrayList<String>();
		durations = new ArrayList<>();

		semesterIDs = SubjectDatabaseController.getAllSemesterIDs();

		this.resources = resources;

	}

	public void btnAddSubjectTime() {
		switch (dayChoiceBox.getSelectionModel().getSelectedIndex()) {
		case 0:
			days.add("MONDAY");
			break;
		case 1:
			days.add("TUESDAY");
			break;
		case 2:
			days.add("WEDNESDAY");
			break;
		case 3:
			days.add("THURSDAY");
			break;
		case 4:
			days.add("FRIDAY");
			break;
		case 5:
			days.add("SATURDAY");
			break;
		case 6:
			days.add("SUNDAY");
			break;
		default:
			days.add("N/A");
			break;
		}

		minutes = (minuteSpinner.getValue() < 10) ? "0" + minuteSpinner.getValue() : "" + minuteSpinner.getValue();
		hours.add("1000-01-01 " + hourSpinner.getValue() + ":" + minutes + ":00");
		durations.add((float) (hourSpinnerDuration.getValue() + (minuteSpinnerDuration.getValue() / 60.0)));
		hourMessage.setText(resources.getString("hourMessage") + "-> #" + hours.size());

		// Update on DB only on edition of existing subject
		if (btnEditDays.isVisible()) {
			float duration = hourSpinnerDuration.getValue() + (minuteSpinnerDuration.getValue() / 10);
			SubjectDatabaseController.insertSubjectTime(SubjectDatabaseController.getIDForSubject(originalSubject),
					days.get(days.size() - 1), hours.get(hours.size() - 1), duration);
		}
	}

	public void btnAcceptAction() {
		// If this accept is not of an edition of an existing subject
		if (!btnEditDays.isVisible()) {
			if (SubjectDatabaseController.addSubject(txtProfessor.getText(), txtSubject.getText(),
					semesterIDs.get(semesterChoiceBox.getSelectionModel().getSelectedIndex()),
					"#" + colorPicker.getValue().toString().substring(2, 8), days, hours, durations)) {
				parent.refreshData();
				dialogStage = (Stage) btnCancel.getScene().getWindow();
				dialogStage.close();
			} else {
				System.out.println("fatal error");
			}
		} else {
			if (SubjectDatabaseController.updateSubject(SubjectDatabaseController.getIDForSubject(originalSubject),
					txtProfessor.getText(), txtSubject.getText(),
					SubjectDatabaseController.getSemesterIDForSubject(semesterChoiceBox.getValue()),
					"#" + colorPicker.getValue().toString().substring(2, 8))) {

				dialogStage = (Stage) btnCancel.getScene().getWindow();
				dialogStage.close();
			} else {
				System.out.println("fatal error");
			}
		}
	}

	public void btnCancelAction() {
		dialogStage = (Stage) btnCancel.getScene().getWindow();
		dialogStage.close();
	}

	public void btnManageStored() {
		try {

			Stage dialogStage = new Stage();
			dialogStage.initOwner(txtSubject.getScene().getWindow());
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle(resources.getString("titleManageStored"));

			GridPane pane = FXMLLoader.load(Main.class.getResource("ClassInfoDialog.fxml"), this.resources);
			pane.setStyle(Main.getThemeString());
			dialogStage.setScene(new Scene(pane));
			dialogStage.show();

			// Sets the textArea default value
			TextArea paneTextArea = (TextArea) pane.getChildren().get(4);
			List<ClassCellItems> classes = SubjectDatabaseController.getAllClasses();
			paneTextArea.setText(classes.get(0).toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void setEditDialogData(GridPane pane) {
		List<ClassCellItems> classes = SubjectDatabaseController.getAllClasses();
		int subjectID = SubjectDatabaseController.getIDForSubject(originalSubject);
		List<String> editDays = new ArrayList<>();
		List<String> editHours = new ArrayList<>();
		List<Integer> editSubjectTime_Ids = new ArrayList<>();
		// Gets the days and hours of only the class to be edited
		for (ClassCellItems c : classes) {
			if (c.getSubjectId() == subjectID) {
				c.getTimes().forEach((t) -> {
					editSubjectTime_Ids.add(t.getIDSubject_Time());
					editDays.add(t.getDay());
					minutes = t.getTime().toString().split(":")[1];
					editHours.add("1000-01-01 " + t.getTime().toString().split(":")[0] + ":" + minutes + " - "
							+ resources.getString("newSubjectDuration") + ": " + t.getDuration());
				});
				break;
			}
		}
		// Sends the info for days and hours of the class to the new dialog
		EditSubjectDaysController.setDays(editDays);
		EditSubjectDaysController.setHours(editHours);

		// Selects the first element of the choicebox
		ChoiceBox<String> daysBox = (ChoiceBox<String>) pane.getChildren().get(4);
		daysBox.getSelectionModel().select(0);

		// Sets the data for the existing days choicebox
		ChoiceBox<String> existingDaysBox = (ChoiceBox<String>) pane.getChildren().get(0);
		ObservableList<String> typeChoiceBoxData = FXCollections.observableArrayList();

		// The for loop below adds data to the observableList
		for (int i = 0; i < editHours.size(); i++) {
			StringBuilder s = new StringBuilder();
			s.append(editSubjectTime_Ids.get(i).toString());
			s.append(" | ");
			s.append(resources.getString(editDays.get(i).toLowerCase()));
			s.append(" - ");
			s.append(editHours.get(i).substring(11));

			typeChoiceBoxData.add(s.toString());
		}

		existingDaysBox.setItems(typeChoiceBoxData);
		EditSubjectDaysController.setOriginalSubject(originalSubject);
	}

	public void btnEditDaysAction() {
		try {

			Stage dialogStage = new Stage();
			dialogStage.initOwner(txtSubject.getScene().getWindow());
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle(resources.getString("titleEditSubjectDays"));

			GridPane pane = FXMLLoader.load(Main.class.getResource("EditSubjectDaysDialog.fxml"), this.resources);
			setEditDialogData(pane);
			pane.setStyle(Main.getThemeString());
			// Once the values are set, display the dialog
			dialogStage.setScene(new Scene(pane));
			dialogStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setOriginalSubject(String subject) {
		originalSubject = subject;
	}

	@Override
	public void setParent(Refreshable parent) {
		this.parent = parent;
	}
}
