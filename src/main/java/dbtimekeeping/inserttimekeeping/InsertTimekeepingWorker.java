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
import model.logtimekeeping.LogTimekeepingWorker;

public class InsertTimekeepingWorker implements IInsertTimekeeping<LogTimekeepingWorker> {
		
	public static InsertTimekeepingWorker getInstance() {
		return new InsertTimekeepingWorker();
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

}
