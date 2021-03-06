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
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import database.SubjectDatabaseController;

public class ManageSubjectsController implements Initializable, Refresher, Refreshable {
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
	Button btnAddSubject;
	@FXML
	Button btnCancel;
	@FXML
	Button btnEditDays;
	@FXML
	Button btnStored;
	@FXML
	Label hourMessage;

	private List<String> days;
	private List<String> hours;
	private List<Float> durations;
	private String minutes;
	private ClassCellItems currentClass;
	private ResourceBundle resources;
	private Stage dialogStage;
	private Refreshable parent;
	private ObservableList<Integer> semesterIDs;
	private Refreshable self = this;

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
		
		// If there are no classes, disable the manage stored button
		if(SubjectDatabaseController.getAllClasses().size() < 1)
			btnStored.setDisable(true);

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
			days.add("MONDAY");
			break;
		}

		minutes = (minuteSpinner.getValue() < 10) ? "0" + minuteSpinner.getValue() : "" + minuteSpinner.getValue();
		hours.add("1000-01-01 " + hourSpinner.getValue() + ":" + minutes + ":00");
		durations.add((float) (hourSpinnerDuration.getValue() + (minuteSpinnerDuration.getValue() / 60.0)));
		hourMessage.setText(resources.getString("hourMessage") + "-> #" + hours.size());

		// Update on DB only on edition of existing subject
		if (btnEditDays.isVisible()) {
			float duration = (float) ((float) hourSpinnerDuration.getValue() + (minuteSpinnerDuration.getValue() / 60.0));
			SubjectDatabaseController.insertSubjectTime(currentClass.getSubjectId(), days.get(days.size() - 1),
					hours.get(hours.size() - 1), duration);
		}
	}

	public void btnAddSubjectAction() {
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
			if (SubjectDatabaseController.updateSubject(currentClass.getSubjectId(), txtProfessor.getText(),
					txtSubject.getText(),
					SubjectDatabaseController.getAllSemesterIDs().get(semesterChoiceBox.getSelectionModel().getSelectedIndex()),
					"#" + colorPicker.getValue().toString().substring(2, 8))) {
				parent.refreshData();
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
		List<ClassCellItems> classes = SubjectDatabaseController.getAllClasses();

		if (classes.size() > 0)
			try {

				Stage dialogStage = new Stage();
				dialogStage.initOwner(txtSubject.getScene().getWindow());
				dialogStage.initModality(Modality.APPLICATION_MODAL);
				dialogStage.setTitle(resources.getString("titleManageStored"));

				FXMLLoader loader = new FXMLLoader(Main.class.getResource("ClassInfoDialog.fxml"), this.resources);
				GridPane pane = loader.load();
				((Refresher) loader.getController()).setParent(self);

				pane.setStyle(Main.getThemeString());
				dialogStage.setScene(new Scene(pane));
				dialogStage.show();

				// Sets the textArea default value
				TextArea paneTextArea = (TextArea) pane.getChildren().get(4);
				paneTextArea.setText(classes.get(0).toString());

				// Only enables the next Button if there is more than 1 class
				if (classes.size() > 1)
					((Button) pane.getChildren().get(1)).setDisable(false);

			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	public void btnEditDaysAction() {
		try {

			Stage dialogStage = new Stage();
			dialogStage.initOwner(txtSubject.getScene().getWindow());
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle(resources.getString("titleEditSubjectDays"));

			FXMLLoader loader = new FXMLLoader(Main.class.getResource("EditSubjectDaysDialog.fxml"), this.resources);
			GridPane pane = loader.load();
			((EditSubjectDaysController) loader.getController()).setInfo(currentClass);
			((EditSubjectDaysController) loader.getController()).setParent(self);

			dialogStage.setScene(new Scene(pane));
			dialogStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setParent(Refreshable parent) {
		this.parent = parent;
	}

	public void setEditInfo(ClassCellItems currentClass) {
		this.currentClass = currentClass;

		btnEditDays.setVisible(true);
		btnStored.setVisible(false);
		btnAddSubject.setText(resources.getString("acceptChanges"));
		txtSubject.setText(currentClass.getClassName());
		txtProfessor.setText(currentClass.getProfessorName());
		colorPicker.setValue(Color.web(currentClass.getColor()));

		semesterChoiceBox.setValue(currentClass.getSemester());
	}

	@Override
	public void refreshData() {
		// Disables the manage stored button if there are no classes
		if (SubjectDatabaseController.getAllClasses().size() < 1)
			btnStored.setDisable(true);
		
		parent.refreshData();
	}
}
