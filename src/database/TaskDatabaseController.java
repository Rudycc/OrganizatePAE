package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import cellItems.TaskCellItems;

public class TaskDatabaseController {
	public static List<TaskCellItems> getAllTasks(){
		Connection conn = null;
		Statement st = null;
		ResultSet rs =null;
		try{
			conn = MyDBConnection.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("Select Task.*, Subject.color from Task Join Subject on Subject.IdSubject = Task.IdSubject where Task.type = 'TASK'");
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
				rs.close();
				st.close();
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static List<TaskCellItems> getUpcomingTasks(){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = MyDBConnection.getConnection();
			ps = conn.prepareStatement("Select Task.*, Subject.color from Task Join Subject on Subject.IdSubject = Task.IdSubject where Task.type = 'TASK' and Task.DueDate >= ?");
			ps.setDate(1, Date.valueOf(LocalDate.now()));
			rs = ps.executeQuery();
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
				ps.close();
				rs.close();
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static List<TaskCellItems> getTodayTasks(){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = MyDBConnection.getConnection();
			ps = conn.prepareStatement("Select Task.*, Subject.color from Task Join Subject on Subject.IdSubject = Task.IdSubject where Task.type = 'TASK' and Task.DueDate = ?");
			ps.setDate(1, Date.valueOf(LocalDate.now()));
			rs = ps.executeQuery();
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
				ps.close();
				rs.close();
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static List<TaskCellItems> getPreviousTasks(){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = MyDBConnection.getConnection();
			ps = conn.prepareStatement("Select Task.*, Subject.color from Task Join Subject on Subject.IdSubject = Task.IdSubject where Task.type = 'TASK' and Task.DueDate < ?");
			ps.setDate(1, Date.valueOf(LocalDate.now()));
			rs = ps.executeQuery();
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
