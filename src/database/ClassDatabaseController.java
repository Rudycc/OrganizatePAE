package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import cellItems.ClassCellItems;

public class ClassDatabaseController {
	public static List<ClassCellItems> getAllClasses(){
		Connection conn = null;
		try{
			conn = MyDBConnection.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("Select * from Subject");
			List<ClassCellItems> classes = new ArrayList<>();
			while(rs.next()){
				PreparedStatement ps = conn.prepareStatement("Select Subject_time.* from Subject_time Join Subject_times on Subject_time.IdSubject_time = Subject_times.IdSubject_time where Subject_times.IdSubject = ?");
				ps.setInt(1, rs.getInt(1));
				ResultSet times = ps.executeQuery();
				while(times.next()){
					System.out.println(times.getString(2));
				}
				ClassCellItems cell = new ClassCellItems();
				cell.setSubjectId(rs.getInt(1));
				cell.setClassName(rs.getString(2));
				cell.setProfessorName(rs.getString(3));
				cell.setColor(rs.getString(5));
				classes.add(cell);
			}
			return classes;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static List<ClassCellItems> getTodayClasses(){
		Connection conn = null;
		try{
			conn = MyDBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement("Select Subject.* from Subject Join Subject_times on Subject.IdSubject = Subject_times.IdSubject Join Subject_time on Subject_time.IdSubject_time = Subject_times.IdSubject_time where Subject_time.day = ?");
			ps.setString(1, LocalDate.now().getDayOfWeek().name());
			ResultSet rs = ps.executeQuery();
			List<ClassCellItems> classes = new ArrayList<>();
			while(rs.next()){
				ClassCellItems cell = new ClassCellItems();
				cell.setSubjectId(rs.getInt(1));
				cell.setClassName(rs.getString(2));
				cell.setProfessorName(rs.getString(3));
				cell.setColor(rs.getString(5));
				classes.add(cell);
			}
			return classes;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return null;
	}
}
