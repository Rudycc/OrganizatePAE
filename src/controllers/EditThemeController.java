package controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import database.SettingsDatabaseController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class EditThemeController implements Initializable, Refresher {
	@FXML
	ChoiceBox<String> namesDrop;
	@FXML
	ColorPicker colorPrimary;
	@FXML
	ColorPicker colorInfo;
	@FXML
	ColorPicker colorDefault;
	@FXML
	ColorPicker colorBg;
	@FXML
	ColorPicker grayBase;
	@FXML
	ColorPicker grayDark;
	@FXML
	ColorPicker grayDarker;
	@FXML
	ColorPicker grayLight;
	@FXML
	ColorPicker grayLighter;
	private Stage dialogStage;
	private Refreshable parent;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		namesDrop.setItems(SettingsDatabaseController.getThemeNames());
		namesDrop.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				String name = namesDrop.getItems().get(newValue.intValue());
				int id = SettingsDatabaseController.getIdForName(name);
				Map<String, String> theme = SettingsDatabaseController.getTheme(id);
				colorPrimary.setValue(Color.web(theme.get("color-primary")));
				colorInfo.setValue(Color.web(theme.get("color-info")));
				colorDefault.setValue(Color.web(theme.get("color-default")));
				colorBg.setValue(Color.web(theme.get("color-bg")));
				grayBase.setValue(Color.web(theme.get("gray-base")));
				grayDark.setValue(Color.web(theme.get("gray-dark")));
				grayDarker.setValue(Color.web(theme.get("gray-darker")));
				grayLight.setValue(Color.web(theme.get("gray-light")));
				grayLighter.setValue(Color.web(theme.get("gray-lighter")));
			}
		});

		namesDrop.getSelectionModel().select(SettingsDatabaseController.getCurrentThemeName());
	}

	public void accept() {
		Map<String, String> theme = new HashMap<>();
		theme.put("Name", namesDrop.getSelectionModel().getSelectedItem());
		theme.put("color-primary", "#" + colorPrimary.getValue().toString().substring(2, 8));
		theme.put("color-info", "#" + colorInfo.getValue().toString().substring(2, 8));
		theme.put("color-default", "#" + colorDefault.getValue().toString().substring(2, 8));
		theme.put("color-bg", "#" + colorBg.getValue().toString().substring(2, 8));
		theme.put("gray-base", "#" + grayBase.getValue().toString().substring(2, 8));
		theme.put("gray-dark", "#" + grayDark.getValue().toString().substring(2, 8));
		theme.put("gray-darker", "#" + grayDarker.getValue().toString().substring(2, 8));
		theme.put("gray-light", "#" + grayLight.getValue().toString().substring(2, 8));
		theme.put("gray-lighter", "#" + grayLighter.getValue().toString().substring(2, 8));
		Boolean success = SettingsDatabaseController.updateTheme(theme);
		parent.refreshData();
		if (success) {
			dialogStage = (Stage) namesDrop.getScene().getWindow();
			dialogStage.close();
		}
	}

	@Override
	public void setParent(Refreshable parent) {
		this.parent = parent;
	}
}
