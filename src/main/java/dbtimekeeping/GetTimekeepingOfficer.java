package dbtimekeeping;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;

import database.JDBCUtil;
import hrsystem.GetAllEmployees;
import model.logtimekeeping.LogTimekeepingOfficer;

public class GetTimekeepingOfficer implements IDBTimekeeping<LogTimekeepingOfficer>{
	
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
    
	public double countHourLateByMonth(int month, int year) {
		double count = 0;
		try {
			Connection con = JDBCUtil.getConnection();
			String sql = "SELECT hourLate FROM counthourlatebymonth WHERE month=? AND year=?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, month);
			preparedStatement.setInt(2, year);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				count = rs.getDouble("hourLate");
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;		
	}
	
	public double countHourEarlyByMonth(int month, int year) {
		double count = 0;
		try {
			Connection con = JDBCUtil.getConnection();
			String sql = "SELECT hourEarly FROM counthourearlybymonth WHERE month=? AND year=?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, month);
			preparedStatement.setInt(2, year);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				count = rs.getDouble("hourEarly");
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;		
	}
	
	public double countHourLateByQuarter(int quarter, int year) {
		double count = 0;
		int[] listmonth;
		if(quarter == 1) {
			listmonth = new int[] {1,2,3};
		}
		else if(quarter == 2) {
			listmonth = new int[] {4,5,6};
		}
		else if(quarter == 3) {
			listmonth = new int[] {7,8,9};
		}
		else {
			listmonth = new int[] {10,11,12};
		}
		try {
			Connection con = JDBCUtil.getConnection();
			String sql = "SELECT SUM(hourLate) AS hourLateQuarter FROM counthourlatebymonth WHERE month IN (?,?,?) AND year=?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, listmonth[0]);
			preparedStatement.setInt(2, listmonth[1]);
			preparedStatement.setInt(3, listmonth[2]);
			preparedStatement.setInt(4, year);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				count = rs.getDouble("hourLateQuarter");
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public double countHourEarlyByQuarter(int quarter, int year) {
		double count = 0;
		int[] listmonth;
		if(quarter == 1) {
			listmonth = new int[] {1,2,3};
		}
		else if(quarter == 2) {
			listmonth = new int[] {4,5,6};
		}
		else if(quarter == 3) {
			listmonth = new int[] {7,8,9};
		}
		else {
			listmonth = new int[] {10,11,12};
		}
		try {
			Connection con = JDBCUtil.getConnection();
			String sql = "SELECT SUM(hourEarly) AS hourEarlyQuarter FROM counthourearlybymonth WHERE month IN (?,?,?) AND year=?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, listmonth[0]);
			preparedStatement.setInt(2, listmonth[1]);
			preparedStatement.setInt(3, listmonth[2]);
			preparedStatement.setInt(4, year);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				count = rs.getDouble("hourEarlyQuarter");
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public double countHourLateByYear(int year) {
		double count = 0;
		try {
			Connection con = JDBCUtil.getConnection();
			String sql = "SELECT SUM(hourLate) AS hourLateYear FROM counthourlatebymonth WHERE year = ?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, year);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				count = rs.getDouble("hourLateYear");
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public double countHourEarlyByYear(int year) {
		double count = 0;
		try {
			Connection con = JDBCUtil.getConnection();
			String sql = "SELECT SUM(hourEarly) AS hourEarlyYear FROM counthourearlybymonth WHERE year = ?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, year);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				count = rs.getDouble("hourEarlyYear");
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
}
