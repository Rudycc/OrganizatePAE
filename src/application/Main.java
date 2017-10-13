package application;


import java.sql.Connection;

import database.MyDBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			TabPane pane =  FXMLLoader.load(getClass().getResource("Main.fxml"));
			primaryStage.setTitle("Organize...Yourself!");
			primaryStage.setScene(new Scene(pane));
			Connection conn = MyDBConnection.getConnection();
			System.out.println(conn.isClosed());
			conn.close();
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
