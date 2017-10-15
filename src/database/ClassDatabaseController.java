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
		Statement st = null;
		ResultSet rs = null;
		try{
			conn = MyDBConnection.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("Select * from Subject");
			List<ClassCellItems> classes = new ArrayList<>();
			while(rs.next()){
				PreparedStatement ps = conn.prepareStatement("Select Subject_Time.* from Subject_Time Join Subject_Times on Subject_Time.IdSubject_time = Subject_Times.IdSubject_time where Subject_Times.IdSubject = ?");
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
				try{
					ps.close();
					times.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
			return classes;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				st.close();
				rs.close();
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static List<ClassCellItems> getTodayClasses(){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = MyDBConnection.getConnection();
			ps = conn.prepareStatement("Select Subject.* from Subject Join Subject_Times on Subject.IdSubject = Subject_Times.IdSubject Join Subject_Time on Subject_Time.IdSubject_time = Subject_Times.IdSubject_time where Subject_Time.day = ?");
			ps.setString(1, LocalDate.now().getDayOfWeek().name());
			rs = ps.executeQuery();
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
				ps.close();
				rs.close();
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return null;
	}
}
