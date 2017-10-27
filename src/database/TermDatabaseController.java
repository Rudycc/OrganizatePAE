package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TermDatabaseController {
	
	public static void insertNewTerm(String startDate, String endDate, String description){
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = MyDBConnection.getConnection();
			ps = conn.prepareStatement( "INSERT INTO Semester(Start_Date, End_Date, Description)"
					+ 					"VALUES(?,?,?)");
			ps.setString(1, startDate);
			ps.setString(2, endDate);
			ps.setString(3, description);			
			ps.executeUpdate();			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				ps.close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static ObservableList<String> getTerms(){
		Connection conn = null;
		Statement st = null;
		ResultSet rs =null;
		ObservableList<String> terms = null;
		
		try {			
			conn = MyDBConnection.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("SELECT Description FROM Semester");
			terms = FXCollections.observableArrayList();
			
			while(rs.next()){
				terms.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				st.close();
				rs.close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return terms;
	}
	
}
