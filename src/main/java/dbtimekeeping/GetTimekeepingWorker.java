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
import model.logtimekeeping.LogTimekeepingWorker;

public class GetTimekeepingWorker implements IDBTimekeeping<LogTimekeepingWorker>{
	
	public static GetTimekeepingWorker getInstance() {
		return new GetTimekeepingWorker();
	}
    
	@Override
	public LogTimekeepingWorker getATimekeepingByID(String id) {
		
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
	public ArrayList<LogTimekeepingWorker> getTimekeepingsByEmployeeID(String employeeID) {
		ArrayList<LogTimekeepingWorker> LogTimekeepingWorkers = new ArrayList<LogTimekeepingWorker>();
		try {
			Connection con = JDBCUtil.getConnection();
			
			Statement st = con.createStatement();
			
			String sql = "SELECT * FROM LogTimekeepingWorker WHERE EmployeeID = '" + employeeID +"';";
			
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

	@Override
	public ArrayList<LogTimekeepingWorker> getAllTimekeepings() {
		
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
	
	public double countTimeShift1ByMonth(int month, int year) {
		double count = 0;
		try {
			Connection con = JDBCUtil.getConnection();
			String sql = "SELECT timeShift1 FROM counttimeshift1bymonth WHERE month=? AND year=?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, month);
			preparedStatement.setInt(2, year);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				count = rs.getDouble("timeShift1");
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public double countTimeShift2ByMonth(int month, int year) {
		double count = 0;
		try {
			Connection con = JDBCUtil.getConnection();
			String sql = "SELECT timeShift2 FROM counttimeshift2bymonth WHERE month=? AND year=?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, month);
			preparedStatement.setInt(2, year);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				count = rs.getDouble("timeShift2");
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public double countTimeShift3ByMonth(int month, int year) {
		double count = 0;
		try {
			Connection con = JDBCUtil.getConnection();
			String sql = "SELECT timeShift3 FROM counttimeshift3bymonth WHERE month=? AND year=?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, month);
			preparedStatement.setInt(2, year);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				count = rs.getDouble("timeShift3");
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public double countTimeShift1ByQuarter(int quarter, int year) {
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
			String sql = "SELECT SUM(timeShift1) AS timeShift1Quarter FROM counttimeshift1bymonth WHERE month IN (?,?,?) AND year=?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, listmonth[0]);
			preparedStatement.setInt(2, listmonth[1]);
			preparedStatement.setInt(3, listmonth[2]);
			preparedStatement.setInt(4, year);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				count = rs.getDouble("timeShift1Quarter");
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public double countTimeShift2ByQuarter(int quarter, int year) {
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
			String sql = "SELECT SUM(timeShift2) AS timeShift2Quarter FROM counttimeshift2bymonth WHERE month IN (?,?,?) AND year=?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, listmonth[0]);
			preparedStatement.setInt(2, listmonth[1]);
			preparedStatement.setInt(3, listmonth[2]);
			preparedStatement.setInt(4, year);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				count = rs.getDouble("timeShift2Quarter");
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public double countTimeShift3ByQuarter(int quarter, int year) {
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
			String sql = "SELECT SUM(timeShift3) AS timeShift2Quarter FROM counttimeshift3bymonth WHERE month IN (?,?,?) AND year=?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, listmonth[0]);
			preparedStatement.setInt(2, listmonth[1]);
			preparedStatement.setInt(3, listmonth[2]);
			preparedStatement.setInt(4, year);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				count = rs.getDouble("timeShift3Quarter");
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public double countTimeShift1ByYear(int year) {
		double count = 0;
		try {
			Connection con = JDBCUtil.getConnection();
			String sql = "SELECT SUM(timeShift1) AS timeShift1Year FROM counttimeshift1bymonth WHERE year = ?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, year);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				count = rs.getDouble("timeShift1Year");
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public double countTimeShift2ByYear(int year) {
		double count = 0;
		try {
			Connection con = JDBCUtil.getConnection();
			String sql = "SELECT SUM(timeShift2) AS timeShift2Year FROM counttimeshift2bymonth WHERE year = ?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, year);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				count = rs.getDouble("timeShift2Year");
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public double countTimeShift3ByYear(int year) {
		double count = 0;
		try {
			Connection con = JDBCUtil.getConnection();
			String sql = "SELECT SUM(timeShift3) AS timeShift3Year FROM counttimeshift3bymonth WHERE year = ?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, year);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				count = rs.getDouble("timeShift3Year");
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
}
