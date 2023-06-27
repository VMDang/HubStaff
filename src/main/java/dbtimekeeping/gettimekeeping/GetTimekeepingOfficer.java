package dbtimekeeping.gettimekeeping;

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

public class GetTimekeepingOfficer implements IGetTimekeeping<LogTimekeepingOfficer> {
	
	public static GetTimekeepingOfficer getInstance() {
		return new GetTimekeepingOfficer();
	}


	@Override
	public LogTimekeepingOfficer getATimekeepingByID(String id) {
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
	public ArrayList<LogTimekeepingOfficer> getTimekeepingsByEmployeeID(String employeeID) {
		
		ArrayList<LogTimekeepingOfficer> logTimekeepingOfficers = new ArrayList<LogTimekeepingOfficer>();
		try {
			Connection con = JDBCUtil.getConnection();
			
			String sql = "SELECT * FROM LogTimekeepingOfficer WHERE EmployeeID = '" + employeeID+"';";
			
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

	@Override
	public ArrayList<LogTimekeepingOfficer> getAllTimekeepings() {
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
}
