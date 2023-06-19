package employee.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;

import database.JDBCUtil;
import model.logtimekeeping.LogTimekeepingWorker;

public class LogTimekeepingWorkerDAO implements DAOInterface<LogTimekeepingWorker>{
		
	public static LogTimekeepingWorkerDAO getInstance() {
		return new LogTimekeepingWorkerDAO();
	}
	
	// attb : ID , employee_id, date, time_in, time_out, shift1, shift2, shift3
	
	@Override
	public int insert(LogTimekeepingWorker t) {
		// TODO Auto-generated method stub
				try {
					Connection con = JDBCUtil.getConnection();
					String sql = "INSERT INTO logtimekeepingworker\r\n"
							+ "VALUES ( ?, ?, ?, ?, ?, ?, ? , ? )";
									//ID , employee_id, date, time_in, time_out, shift1, shift2, shift3
					PreparedStatement st = con.prepareStatement(sql);
					
					st.setString(1,t.getLogID());
					st.setString(2,t.getEmployee_id());
					st.setDate(3,t.getDate());
					st.setTime(4,t.getTime_in());
					st.setTime(5,t.getTime_out());
					st.setDouble(6, t.getShift1());
					st.setDouble(7, t.getShift2());
					st.setDouble(8, t.getShift3());
					
					int check = st.executeUpdate();
					
					if(check > 0) {
						System.out.println("Thanh cong");
						
					}else {
						System.out.println("That bai");
					}
					
					JDBCUtil.closeConnection(con);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return 0;
	}
	
	@Override
	public int update(LogTimekeepingWorker t) {
		try {
			Connection con = JDBCUtil.getConnection();
			
			String sql = "UPDATE LogTimekeepingWorker\r\n"
					+ "SET EmployeeID = ?"+", Date = ?"+", TimeIn = ?"+", TimeOut = ?"+", Shift1 = ? "+", Shift2 = ? "+", Shift3 = ? "
					+ "WHERE ID = ?";
			
			PreparedStatement st = con.prepareStatement(sql);
			
			st.setString(8,t.getLogID());
			st.setString(1,t.getEmployee_id());
			st.setDate(2,t.getDate());
			st.setTime(3,t.getTime_in());
			st.setTime(4,t.getTime_out());
			st.setDouble(5, t.getShift1());
			st.setDouble(6, t.getShift2());
			st.setDouble(7, t.getShift3());
			
			int check = st.executeUpdate();
			
			if(check > 0) {
				System.out.println("Thanh cong");
			}else {
				System.out.println("That bai");
			}
			
			JDBCUtil.closeConnection(con);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int delete(LogTimekeepingWorker t) {
		try {
			Connection con = JDBCUtil.getConnection();
			
			String sql = "DELETE FROM LogTimekeepingWorker WHERE ID = ?";
			
			PreparedStatement st = con.prepareStatement(sql);
			
			st.setString(1,t.getLogID());
			
			
			int check = st.executeUpdate();
			
			if(check > 0) {
				System.out.println("Thanh cong");
			}else {
				System.out.println("That bai");
			}
			
			JDBCUtil.closeConnection(con);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public ArrayList<LogTimekeepingWorker> selectAll() {
		ArrayList<LogTimekeepingWorker> allLogTimekeepingWorkers = new ArrayList<LogTimekeepingWorker>();
		try {
			Connection con = JDBCUtil.getConnection();
			
			Statement st = con.createStatement();
			
			String sql = "SELECT * FROM LogTimekeepingWorker";
			
			System.out.println(sql);
			
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {

				String logID = rs.getString("ID");
				String employee_id=rs.getString("EmployeeID");
				Date date = rs.getDate("Date");
				Time time_in = rs.getTime("TimeIn");
				Time time_out =rs.getTime("TimeOut");
				Float shift1 = (float) rs.getDouble("Shift1");
				Float shift2 = (float) rs.getDouble("Shift2");
				Float shift3 = (float) rs.getDouble("Shift3");
				
				LogTimekeepingWorker log = new LogTimekeepingWorker(logID, employee_id, date, time_in, time_out, shift1, shift2, shift3);
				allLogTimekeepingWorkers.add(log);
					
			}
		
			JDBCUtil.closeConnection(con);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allLogTimekeepingWorkers;
	}

	@Override
	public LogTimekeepingWorker selectById(String id) {
		LogTimekeepingWorker log = new LogTimekeepingWorker();
		try {
			Connection con = JDBCUtil.getConnection();
			
			String sql = "SELECT * FROM LogTimekeepingWorker WHERE ID = '"+id+"';";
			System.out.println(sql);
			PreparedStatement st = con.prepareStatement(sql);
				
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				String logID = rs.getString("ID");
				String employee_id=rs.getString("EmployeeID");
				Date date = rs.getDate("Date");
				Time time_in = rs.getTime("TimeIn");
				Time time_out =rs.getTime("TimeOut");
				Float shift1 = (float) rs.getDouble("Shift1");
				Float shift2 = (float) rs.getDouble("Shift2");
				Float shift3 = (float) rs.getDouble("Shift3");
				
				log = new LogTimekeepingWorker(logID, employee_id, date, time_in, time_out, shift1, shift2, shift3);
				
			}
			
			JDBCUtil.closeConnection(con);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return log;
	}

	@Override
	public ArrayList<LogTimekeepingWorker> selectByCondition(String condition) {
		ArrayList<LogTimekeepingWorker> LogTimekeepingWorkers = new ArrayList<LogTimekeepingWorker>();
		try {
			Connection con = JDBCUtil.getConnection();
			
			Statement st = con.createStatement();
			
			String sql = "SELECT * FROM LogTimekeepingWorker WHERE " + condition+";";
			
			System.out.println(sql);
			
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {

				String logID = rs.getString("ID");
				String employee_id=rs.getString("EmployeeID");
				Date date = rs.getDate("Date");
				Time time_in = rs.getTime("TimeIn");
				Time time_out =rs.getTime("TimeOut");
				Float shift1 = (float) rs.getDouble("Shift1");
				Float shift2 = (float) rs.getDouble("Shift2");
				Float shift3 = (float) rs.getDouble("Shift3");
				
				LogTimekeepingWorker log = new LogTimekeepingWorker(logID, employee_id, date, time_in, time_out, shift1, shift2, shift3);
				LogTimekeepingWorkers.add(log);
					
			}
		
			JDBCUtil.closeConnection(con);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return LogTimekeepingWorkers;
	}


}
