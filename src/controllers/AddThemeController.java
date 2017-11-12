package controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import database.SettingsDatabaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddThemeController implements Initializable {
	@FXML
	TextField textName;
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void accept() {
		Map<String, String> theme = new HashMap<>();
		theme.put("Name", textName.getText());
		theme.put("color-primary", "#" + colorPrimary.getValue().toString().substring(2, 8));
		theme.put("color-info", "#" + colorInfo.getValue().toString().substring(2, 8));
		theme.put("color-default", "#" + colorDefault.getValue().toString().substring(2, 8));
		theme.put("color-bg", "#" + colorBg.getValue().toString().substring(2, 8));
		theme.put("gray-base", "#" + grayBase.getValue().toString().substring(2, 8));
		theme.put("gray-dark", "#" + grayDark.getValue().toString().substring(2, 8));
		theme.put("gray-darker", "#" + grayDarker.getValue().toString().substring(2, 8));
		theme.put("gray-light", "#" + grayLight.getValue().toString().substring(2, 8));
		theme.put("gray-lighter", "#" + grayLighter.getValue().toString().substring(2, 8));
		Boolean success = SettingsDatabaseController.addTheme(theme);
		if (success) {
			dialogStage = (Stage) textName.getScene().getWindow();
			dialogStage.close();
		}
	}

}
