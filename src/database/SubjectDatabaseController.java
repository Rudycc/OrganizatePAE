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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SubjectDatabaseController {
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
				PreparedStatement ps = conn.prepareStatement("Select Subject_Time.* from Subject_Time "
						+ "where Subject_Time.IdSubject = ?");
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
			ps = conn.prepareStatement("Select Subject.* from Subject "
					+ "Join Subject_Time on Subject.IdSubject = Subject_Time.IdSubject "
					+ "where Subject_Time.day = ?");
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
	
	public static ObservableList<Integer> getAllSemesterIDs(){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ObservableList<Integer> ids = null;
		try{
			conn = MyDBConnection.getConnection();
			ps = conn.prepareStatement("Select IDSemester from Semester");
			rs = ps.executeQuery();
			ids = FXCollections.observableArrayList();
			
			while(rs.next()){
				ids.add(rs.getInt(1));
			}
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
		
		return ids;
	}
	
	public static boolean addSubject(String professor, String subject, int semester, String color, List<String> days, List<String> hours){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int subjectID = 0;
		try{
			//Inserts subject
			conn = MyDBConnection.getConnection();
			ps = conn.prepareStatement("Insert Into Subject(Name,ProfessorName,IDSemester,Color) Values(?,?,?,?)");
			ps.setString(1, subject);
			ps.setString(2, professor);
			ps.setInt(3, semester);
			ps.setString(4, color);
			ps.executeUpdate();
			
			//Get the new subject's ID
			ps = conn.prepareStatement("Select MAX(IDSubject) as ID From Subject");
			rs = ps.executeQuery();
			if(rs.next())
				subjectID = rs.getInt("ID");
			
			//Inserts subject time
			ps = conn.prepareStatement("Insert into Subject_Time(Day,Time,IDSubject) Values(?,?,?)");
			for(int i=0; i< days.size();i++){
				ps.setString(1, days.get(i));
				ps.setString(2, hours.get(i));
				ps.setInt(3, subjectID);
				ps.executeUpdate();
			}
			
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}finally{
			try{
				ps.close();
				rs.close();
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
}