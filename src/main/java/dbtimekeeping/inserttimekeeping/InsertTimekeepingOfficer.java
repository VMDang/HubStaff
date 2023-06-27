package dbtimekeeping.inserttimekeeping;

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

public class InsertTimekeepingOfficer implements IInsertTimekeeping<LogTimekeepingOfficer> {
		
	public static InsertTimekeepingOfficer getInstance() {
		return new InsertTimekeepingOfficer();
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

}
