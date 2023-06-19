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
import model.logtimekeeping.LogTimekeepingOfficer;

public class LogTimekeepingOfficerDAO implements DAOInterface<LogTimekeepingOfficer>{
		
	public static LogTimekeepingOfficerDAO getInstance() {
		return new LogTimekeepingOfficerDAO();
	}
	
	// attb : String logID, String employee_id, Date date, Time time_in, Time time_out, boolean morning, boolean afternoon, float hour_late, float hour_early
	
	@Override
	public int insert(LogTimekeepingOfficer t) {
		// TODO Auto-generated method stub
				try {
					Connection con = JDBCUtil.getConnection();
					String sql = "INSERT INTO logtimekeepingOfficer\r\n"
							+ "VALUES ( ?, ?, ?, ?, ?, ?, ? , ? ,?)";
									//ID , employee_id, date, time_in, time_out, shift1, shift2, shift3
					PreparedStatement st = con.prepareStatement(sql);
					
					st.setString(1,t.getLogID());
					st.setString(2,t.getEmployee_id());
					st.setDate(3,t.getDate());
					st.setTime(4,t.getTime_in());
					st.setTime(5,t.getTime_out());
					st.setBoolean(6, t.isMorning());
					st.setBoolean(7, t.isAfternoon());
					st.setDouble(8, t.getHour_late());
					st.setDouble(9, t.getHour_early());
					
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
	public int update(LogTimekeepingOfficer t) {
		try {
			Connection con = JDBCUtil.getConnection();
			
			String sql = "UPDATE LogTimekeepingOfficer\r\n"
					+ "SET EmployeeID = ?"+", Date = ?"+", TimeIn = ?"+", TimeOut = ?"+", Morning = ? "+", Afternoon = ? "+", HourLate = ? "+", HourEarly = ? "
					+ "WHERE ID = ?";
			
			PreparedStatement st = con.prepareStatement(sql);
			
			st.setString(9,t.getLogID());
			st.setString(1,t.getEmployee_id());
			st.setDate(2,t.getDate());
			st.setTime(3,t.getTime_in());
			st.setTime(4,t.getTime_out());
			st.setBoolean(5, t.isMorning());
			st.setBoolean(6, t.isAfternoon());
			st.setDouble(7, t.getHour_late());
			st.setDouble(8, t.getHour_early());
			
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
	public int delete(LogTimekeepingOfficer t) {
		try {
			Connection con = JDBCUtil.getConnection();
			
			String sql = "DELETE FROM LogTimekeepingOfficer WHERE ID = ?";
			
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
	public ArrayList<LogTimekeepingOfficer> selectAll() {
		ArrayList<LogTimekeepingOfficer> allLogTimekeepingOfficers = new ArrayList<LogTimekeepingOfficer>();
		try {
			Connection con = JDBCUtil.getConnection();
			
			Statement st = con.createStatement();
			
			String sql = "SELECT * FROM LogTimekeepingOfficer";
			
			System.out.println(sql);
			
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {

				String logID = rs.getString("ID");
				String employee_id=rs.getString("EmployeeID");
				Date date = rs.getDate("Date");
				Time time_in = rs.getTime("TimeIn");
				Time time_out =rs.getTime("TimeOut");
				boolean morning = rs.getBoolean("Morning");
				boolean afternoon = rs.getBoolean("Afternoon");
				Float hour_late = (float) rs.getDouble("HourLate");
				Float hour_early = (float) rs.getDouble("HourEarly");
				
				LogTimekeepingOfficer log = new LogTimekeepingOfficer(logID, employee_id, date, time_in, time_out, morning, afternoon, hour_late, hour_early);
				allLogTimekeepingOfficers.add(log);
					
			}
		
			JDBCUtil.closeConnection(con);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allLogTimekeepingOfficers;
	}

	@Override
	public LogTimekeepingOfficer selectById(String id) {
		LogTimekeepingOfficer log = new LogTimekeepingOfficer();
		try {
			Connection con = JDBCUtil.getConnection();
			
			String sql = "SELECT * FROM LogTimekeepingOfficer WHERE ID = '"+id+"';";
			System.out.println(sql);
			PreparedStatement st = con.prepareStatement(sql);
				
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				String logID = rs.getString("ID");
				String employee_id=rs.getString("EmployeeID");
				Date date = rs.getDate("Date");
				Time time_in = rs.getTime("TimeIn");
				Time time_out =rs.getTime("TimeOut");
				boolean morning = rs.getBoolean("Morning");
				boolean afternoon = rs.getBoolean("Afternoon");
				Float hour_late = (float) rs.getDouble("HourLate");
				Float hour_early = (float) rs.getDouble("HourEarly");
				
				log = new LogTimekeepingOfficer(logID, employee_id, date, time_in, time_out, morning, afternoon, hour_late, hour_early);
				
				
			}
			
			JDBCUtil.closeConnection(con);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return log;
	}

	@Override
	public ArrayList<LogTimekeepingOfficer> selectByCondition(String condition) {
		ArrayList<LogTimekeepingOfficer> logTimekeepingOfficers = new ArrayList<LogTimekeepingOfficer>();
		try {
			Connection con = JDBCUtil.getConnection();
			
			Statement st = con.createStatement();
			
			String sql = "SELECT * FROM LogTimekeepingOfficer WHERE " + condition+";";
			
			System.out.println(sql);
			
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {

				String logID = rs.getString("ID");
				String employee_id=rs.getString("EmployeeID");
				Date date = rs.getDate("Date");
				Time time_in = rs.getTime("TimeIn");
				Time time_out =rs.getTime("TimeOut");
				boolean morning = rs.getBoolean("Morning");
				boolean afternoon = rs.getBoolean("Afternoon");
				Float hour_late = (float) rs.getDouble("HourLate");
				Float hour_early = (float) rs.getDouble("HourEarly");
				
				LogTimekeepingOfficer log = new LogTimekeepingOfficer(logID, employee_id, date, time_in, time_out, morning, afternoon, hour_late, hour_early);
				logTimekeepingOfficers.add(log);
			}
		
			JDBCUtil.closeConnection(con);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return logTimekeepingOfficers;
	}


}
