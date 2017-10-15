package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class MyDBConnection {
	private static MysqlDataSource ds;
	
	
	private MyDBConnection(){
		
	}
	
	public static Connection getConnection() throws SQLException{
		ResourceBundle rs = ResourceBundle.getBundle("database.settings");
		if(ds == null){
			ds = new MysqlDataSource();
		}
		ds.setUser(rs.getString("user"));
		ds.setDatabaseName(rs.getString("databaseName"));
		ds.setPassword(rs.getString("password"));
		return ds.getConnection();
	}
}
