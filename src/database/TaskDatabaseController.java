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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TaskDatabaseController {
	public static List<TaskCellItems> getAllTasks() {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = MyDBConnection.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(
					"Select Task.*, Subject.color from Task Join Subject on Subject.IdSubject = Task.IdSubject where Task.type = 'TASK'");
			List<TaskCellItems> tasks = new ArrayList<>();
			while (rs.next()) {
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

	public static List<TaskCellItems> getUpcomingTasks() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = MyDBConnection.getConnection();
			ps = conn.prepareStatement(
					"Select Task.*, Subject.color from Task Join Subject on Subject.IdSubject = Task.IdSubject where Task.type = 'TASK' and Task.DueDate >= ?");
			ps.setDate(1, Date.valueOf(LocalDate.now()));
			rs = ps.executeQuery();
			List<TaskCellItems> tasks = new ArrayList<>();
			while (rs.next()) {
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
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static List<TaskCellItems> getTodayTasks() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = MyDBConnection.getConnection();
			ps = conn.prepareStatement(
					"Select Task.*, Subject.color from Task Join Subject on Subject.IdSubject = Task.IdSubject where Task.type = 'TASK' and Task.DueDate between ? and ?");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(java.util.Date.from(Instant.now()));
			calendar.set(Calendar.DAY_OF_WEEK, 1);
			ps.setDate(1, new Date(calendar.getTimeInMillis()));
			calendar.set(Calendar.DAY_OF_WEEK, 7);
			ps.setDate(2, new Date(calendar.getTimeInMillis()));
			rs = ps.executeQuery();
			List<TaskCellItems> tasks = new ArrayList<>();
			while (rs.next()) {
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
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static List<TaskCellItems> getPreviousTasks() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = MyDBConnection.getConnection();
			ps = conn.prepareStatement(
					"Select Task.*, Subject.color from Task Join Subject on Subject.IdSubject = Task.IdSubject where Task.type = 'TASK' and Task.DueDate < ?");
			ps.setDate(1, Date.valueOf(LocalDate.now()));
			rs = ps.executeQuery();
			List<TaskCellItems> tasks = new ArrayList<>();
			while (rs.next()) {
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
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static ObservableList<String> getSubjects() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ObservableList<String> subjects = null;
		try {
			conn = MyDBConnection.getConnection();
			ps = conn.prepareStatement("SELECT Name FROM Subject");
			rs = ps.executeQuery();
			subjects = FXCollections.observableArrayList();

			while (rs.next()) {
				subjects.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return subjects;
	}

	public static void insertNewTask(String title, String description, String type, int isDone, String dueDate,
			String subject) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = MyDBConnection.getConnection();
			ps = conn.prepareStatement(
					"INSERT INTO Task(Description, Title, Type, IsDone, IDSubject, DueDate) VALUES(?,?,?,?,?,?)");
			ps.setString(1, description);
			ps.setString(2, title);
			ps.setString(3, type);
			ps.setInt(4, isDone);
			ps.setInt(5, getIDSubject(subject));
			ps.setString(6, dueDate);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void updateTaskInfo(int taskID, String description, int isDone) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = MyDBConnection.getConnection();
			ps = conn.prepareStatement("UPDATE Task SET Description = ? , IsDone = ? WHERE IDTask = ?");
			ps.setString(1, description);
			ps.setInt(2, isDone);
			ps.setInt(3, taskID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static int getIDSubject(String subject) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = MyDBConnection.getConnection();
			ps = conn.prepareStatement("SELECT IDSubject FROM Subject WHERE Name = ?");
			ps.setString(1, subject);
			rs = ps.executeQuery();
			// Return an id if it exist
			if (rs.next()) {
				return rs.getInt("IDSubject");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("No Id found");
		} finally {
			try {
				ps.close();
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return 0;
	}
}
