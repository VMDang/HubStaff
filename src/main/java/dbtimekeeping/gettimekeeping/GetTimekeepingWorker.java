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
import model.logtimekeeping.LogTimekeepingWorker;

public class GetTimekeepingWorker implements IGetTimekeeping<LogTimekeepingWorker> {
	
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
}
