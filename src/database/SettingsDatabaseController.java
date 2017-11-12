package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

	public static ObservableList<String> getThemeNames() {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = MyDBConnection.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("Select Name From Theme;");
			ObservableList<String> names = FXCollections.observableArrayList();
			while (rs.next()) {
				names.add(rs.getString(1));
			}
			return names;
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
		return null;
	}

	public static int getIdForName(String name) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = MyDBConnection.getConnection();
			st = conn.prepareStatement("Select IDTheme From Theme Where Name = ?;");
			st.setString(1, name);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
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
		return -1;
	}

	public static String getCurrentThemeName() {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = MyDBConnection.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("Select t.Name From Theme as t join User as u on t.IDTheme = u.SelectedThemeID;");
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
		return null;
	}

	public static Map<String, String> getTheme(int id) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = MyDBConnection.getConnection();
			st = conn.prepareStatement("Select * From Theme Where IDTheme = ?;");
			st.setInt(1, id);
			rs = st.executeQuery();
			Map<String, String> theme = new HashMap<>();
			while (rs.next()) {
				theme.put("Name", rs.getString(2));
				theme.put("color-primary", rs.getString(3));
				theme.put("color-info", rs.getString(4));
				theme.put("color-default", rs.getString(5));
				theme.put("color-bg", rs.getString(6));
				theme.put("gray-base", rs.getString(7));
				theme.put("gray-dark", rs.getString(8));
				theme.put("gray-darker", rs.getString(9));
				theme.put("gray-light", rs.getString(10));
				theme.put("gray-lighter", rs.getString(11));
			}
			return theme;
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
		return null;
	}

	public static Boolean updateTheme(Map<String, String> theme) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = MyDBConnection.getConnection();
			st = conn.prepareStatement(
					"Update Theme Set `color-primary` = ?, `color-info` = ?, `color-default` = ?, `color-bg` = ?, `gray-base` = ?, `gray-dark` = ?, `gray-darker` = ?, `gray-light` = ?, `gray-lighter` = ? Where Name = ?;");
			st.setString(1, theme.get("color-primary"));
			st.setString(2, theme.get("color-info"));
			st.setString(3, theme.get("color-default"));
			st.setString(4, theme.get("color-bg"));
			st.setString(5, theme.get("gray-base"));
			st.setString(6, theme.get("gray-dark"));
			st.setString(7, theme.get("gray-darker"));
			st.setString(8, theme.get("gray-light"));
			st.setString(9, theme.get("gray-lighter"));
			st.setString(10, theme.get("Name"));
			Integer modified = st.executeUpdate();
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

	public static Boolean addTheme(Map<String, String> theme) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = MyDBConnection.getConnection();
			st = conn.prepareStatement(
					"Insert Into Theme (`color-primary`, `color-info`, `color-default`, `color-bg`, `gray-base`, `gray-dark`, `gray-darker`, `gray-light`, `gray-lighter`, Name) Values(?,?,?,?,?,?,?,?,?,?);");
			st.setString(1, theme.get("color-primary"));
			st.setString(2, theme.get("color-info"));
			st.setString(3, theme.get("color-default"));
			st.setString(4, theme.get("color-bg"));
			st.setString(5, theme.get("gray-base"));
			st.setString(6, theme.get("gray-dark"));
			st.setString(7, theme.get("gray-darker"));
			st.setString(8, theme.get("gray-light"));
			st.setString(9, theme.get("gray-lighter"));
			st.setString(10, theme.get("Name"));
			Integer modified = st.executeUpdate();
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
