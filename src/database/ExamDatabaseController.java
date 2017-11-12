package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cellItems.TaskCellItems;

public class ExamDatabaseController {
	public static List<TaskCellItems> getAllExams(){
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try{
			conn = MyDBConnection.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("Select Task.*, Subject.color from Task Join Subject on Subject.IdSubject = Task.IdSubject where Task.type = 'EXAM'");
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
				rs.close();
				st.close();
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static List<TaskCellItems> getUpcomingExams(){
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try{
			conn = MyDBConnection.getConnection();
			ps = conn.prepareStatement("Select Task.*, Subject.color from Task Join Subject on Subject.IdSubject = Task.IdSubject where Task.type = 'EXAM' and Task.DueDate >= ?");
			ps.setDate(1, Date.valueOf(LocalDate.now()));
			rs = ps.executeQuery();
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
				rs.close();
				ps.close();
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static List<TaskCellItems> getTodayExams(){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = MyDBConnection.getConnection();
			ps = conn.prepareStatement("Select Task.*, Subject.color from Task Join Subject on Subject.IdSubject = Task.IdSubject where Task.type = 'EXAM' and Task.DueDate between ? and ?");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(java.util.Date.from(Instant.now()));
			calendar.set(Calendar.DAY_OF_WEEK, 1);
			ps.setDate(1, new Date(calendar.getTimeInMillis()));
			calendar.set(Calendar.DAY_OF_WEEK, 7);
			ps.setDate(2, new Date(calendar.getTimeInMillis()));
			rs = ps.executeQuery();
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
				ps.close();
				rs.close();
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static List<TaskCellItems> getPreviousExams(){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = MyDBConnection.getConnection();
			ps = conn.prepareStatement("Select Task.*, Subject.color from Task Join Subject on Subject.IdSubject = Task.IdSubject where Task.type = 'EXAM' and Task.DueDate < ?");
			ps.setDate(1, Date.valueOf(LocalDate.now()));
			rs = ps.executeQuery();
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
				ps.close();
				rs.close();
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void updateExamInfo(int taskID, String description, int isDone){
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = MyDBConnection.getConnection();
			ps = conn.prepareStatement("UPDATE Task SET Description = ? , IsDone = ? WHERE IDTask = ?");
			ps.setString(1,description);
			ps.setInt(2,isDone);
			ps.setInt(3,taskID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
