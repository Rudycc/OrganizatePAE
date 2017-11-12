package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;

public class AddThemeController implements Initializable {
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
