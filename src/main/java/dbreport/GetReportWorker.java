package dbreport;

import java.sql.SQLException;
import java.util.ArrayList;

import controller.report.hrmanager.unitworkerattendance.ReportUnitWorker;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import database.JDBCUtil;

public class GetReportWorker implements IDBReport<ReportUnitWorker>{
	
	public static GetReportWorker getInstance() {
		return new GetReportWorker();
	}
	
	@Override
	public ArrayList<ReportUnitWorker> getReportAll() {
		ArrayList<ReportUnitWorker> ReportUnitWorkers = new ArrayList<ReportUnitWorker>();
		
		try {
			Connection con = JDBCUtil.getConnection();
			
			Statement st = con.createStatement();
			
			String sql = "SELECT \r\n"
					+ "    E.Name AS Employee,\r\n"
					+ "    E.ID AS EmployeeID,\r\n"
					+ "    E.UnitId,\r\n"
					+ "    MONTH(LT.Date) AS Month,\r\n"
					+ "    SUM(TIMESTAMPDIFF(HOUR, TimeIn, TimeOut)) AS TotalWorkingHours,\r\n"
					+ "    SUM(TIMESTAMPDIFF(HOUR, TimeIn, TimeOut) - Shift1 - Shift2 - Shift3) AS OvertimeHours\r\n"
					+ "FROM\r\n"
					+ "    LogTimekeepingWorker LT\r\n"
					+ "    JOIN Employees E ON LT.EmployeeID = E.ID\r\n"
					+ "GROUP BY\r\n"
					+ "    E.ID, MONTH(LT.Date);";

			System.out.println(sql);
			
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {

				String name = rs.getString("Employee");
				String employee_id=rs.getString("EmployeeID");
				String unit_id = rs.getString("UnitID");
				Integer month = (int) rs.getInt("Month");
				Float total_hour_work = (float) rs.getDouble("TotalWorkingHours");
				Float total_overtime = (float) rs.getDouble("OvertimeHours");
				
				ReportUnitWorker report = new ReportUnitWorker(name, employee_id, unit_id, month, total_hour_work, total_overtime);
				ReportUnitWorkers.add(report);
					
			}
		
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ReportUnitWorkers;
	}

	@Override
	public ArrayList<ReportUnitWorker> getReportByUnitID(String unitID) {
		ArrayList<ReportUnitWorker> ReportUnitWorkers = new ArrayList<ReportUnitWorker>();
		try {
			Connection con = JDBCUtil.getConnection();
			
			Statement st = con.createStatement();
			
			String sql = "SELECT \r\n"
					+ "    E.Name AS Employee,\r\n"
					+ "    E.ID AS EmployeeID,\r\n"
					+ "    E.UnitId,\r\n"
					+ "    MONTH(LT.Date) AS Month,\r\n"
					+ "    SUM(TIMESTAMPDIFF(HOUR, TimeIn, TimeOut)) AS TotalWorkingHours,\r\n"
					+ "    SUM(TIMESTAMPDIFF(HOUR, TimeIn, TimeOut) - Shift1 - Shift2 - Shift3) AS OvertimeHours\r\n"
					+ "FROM\r\n"
					+ "    LogTimekeepingWorker LT\r\n"
					+ "    JOIN Employees E ON LT.EmployeeID = E.ID\r\n"
					+ "WHERE\r\n"
					+ "E.UnitId = '"+unitID+"'"
					+ "GROUP BY\r\n"
					+ "    E.ID, MONTH(LT.Date);";

			System.out.println(sql);
			
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {

				String name = rs.getString("Employee");
				String employee_id=rs.getString("EmployeeID");
				String unit_id = rs.getString("UnitID");
				Integer month = (int) rs.getInt("Month");
				Float total_hour_work = (float) rs.getDouble("TotalWorkingHours");
				Float total_overtime = (float) rs.getDouble("OvertimeHours");
				
				ReportUnitWorker report = new ReportUnitWorker(name, employee_id, unit_id, month, total_hour_work, total_overtime);
				ReportUnitWorkers.add(report);
					
			}
		
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ReportUnitWorkers;
	}
	
	
}
