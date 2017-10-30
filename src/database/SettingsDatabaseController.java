package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SettingsDatabaseController {
	public static String getLanguage() {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = MyDBConnection.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("Select Language From User");
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "EN";
	}
	
	public static Boolean setLanguage(String newLang) {
		Connection conn = null;
		PreparedStatement st = null;
		Integer modified = new Integer(0);
		try {
			conn = MyDBConnection.getConnection();
			st = conn.prepareStatement("Update User set Language = ?");
			st.setString(1, newLang);
			modified = st.executeUpdate();
			if (modified == 1) {
				return new Boolean(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return new Boolean(false);
	}
}
