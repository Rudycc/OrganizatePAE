package database;

import java.sql.Connection;
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
	public static List<ClassCellItems> getClasses(){
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
	
	public static List<TaskCellItems> getTasks(){
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
	
	public static List<TaskCellItems> getExams(){
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
