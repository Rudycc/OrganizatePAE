package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import cellItems.ClassCellItems;
import cellItems.TaskCellItems;

public class DatabaseController {
	public static List<ClassCellItems> getAllClasses(){
		Connection conn = null;
		try{
			conn = MyDBConnection.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("Select * from Subject");
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
	
	public static List<TaskCellItems> getAllTasks(){
		Connection conn = null;
		try{
			conn = MyDBConnection.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("Select Task.*, Subject.color from Task Join Subject on Subject.IdSubject = Task.IdSubject where Task.type = 'TASK'");
			List<TaskCellItems> tasks = new ArrayList<>();
			while(rs.next()){
				TaskCellItems cell = new TaskCellItems();
				cell.setTaskId(rs.getInt(1));
				cell.setDescription(rs.getString(2));
				cell.setTaskName(rs.getString(3));
				cell.setDone(rs.getBoolean(5));
				cell.setIdSubject(rs.getInt(6));
				cell.setDueDate(rs.getDate(7).toLocalDate()); 
				cell.setColor(rs.getString(8));
				tasks.add(cell);
			}
			return tasks;
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
	
	public static List<TaskCellItems> getUpcomingTasks(){
		Connection conn = null;
		try{
			conn = MyDBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement("Select Task.*, Subject.color from Task Join Subject on Subject.IdSubject = Task.IdSubject where Task.type = 'TASK' and Task.DueDate >= ?");
			ps.setDate(1, Date.valueOf(LocalDate.now()));
			ResultSet rs = ps.executeQuery();
			List<TaskCellItems> tasks = new ArrayList<>();
			while(rs.next()){
				TaskCellItems cell = new TaskCellItems();
				cell.setTaskId(rs.getInt(1));
				cell.setDescription(rs.getString(2));
				cell.setTaskName(rs.getString(3));
				cell.setDone(rs.getBoolean(5));
				cell.setIdSubject(rs.getInt(6));
				cell.setDueDate(rs.getDate(7).toLocalDate()); 
				cell.setColor(rs.getString(8));
				tasks.add(cell);
			}
			return tasks;
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
	
	public static List<TaskCellItems> getPreviousTasks(){
		Connection conn = null;
		try{
			conn = MyDBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement("Select Task.*, Subject.color from Task Join Subject on Subject.IdSubject = Task.IdSubject where Task.type = 'TASK' and Task.DueDate < ?");
			ps.setDate(1, Date.valueOf(LocalDate.now()));
			ResultSet rs = ps.executeQuery();
			List<TaskCellItems> tasks = new ArrayList<>();
			while(rs.next()){
				TaskCellItems cell = new TaskCellItems();
				cell.setTaskId(rs.getInt(1));
				cell.setDescription(rs.getString(2));
				cell.setTaskName(rs.getString(3));
				cell.setDone(rs.getBoolean(5));
				cell.setIdSubject(rs.getInt(6));
				cell.setDueDate(rs.getDate(7).toLocalDate()); 
				cell.setColor(rs.getString(8));
				tasks.add(cell);
			}
			return tasks;
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
	
	public static List<TaskCellItems> getAllExams(){
		Connection conn = null;
		try{
			conn = MyDBConnection.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("Select Task.*, Subject.color from Task Join Subject on Subject.IdSubject = Task.IdSubject where Task.type = 'EXAM'");
			List<TaskCellItems> exams = new ArrayList<>();
			while(rs.next()){
				TaskCellItems cell = new TaskCellItems();
				cell.setTaskId(rs.getInt(1));
				cell.setDescription(rs.getString(2));
				cell.setTaskName(rs.getString(3));
				cell.setDone(rs.getBoolean(5));
				cell.setIdSubject(rs.getInt(6));
				cell.setDueDate(rs.getDate(7).toLocalDate()); 
				cell.setColor(rs.getString(8));
				exams.add(cell);
			}
			return exams;
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
