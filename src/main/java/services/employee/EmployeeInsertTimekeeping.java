package services.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.JDBCUtil;
import model.employee.Employee;
import dbtimekeeping.inserttimekeeping.IInsertTimekeeping;

public class EmployeeInsertTimekeeping implements IInsertTimekeeping<Employee> {
	
	public static EmployeeInsertTimekeeping getInstance() {
		return new EmployeeInsertTimekeeping();
	}

	@Override
	public int insert(Employee t) {
		// TODO Auto-generated method stub
		try {
			Connection con = JDBCUtil.getConnection();
			String sql = "INSERT INTO Employees\r\n"
					+ "VALUES ( ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement st = con.prepareStatement(sql);
			
			st.setString(1,t.getId());
			st.setString(2,t.getName());
			st.setString(3,t.getDepartment());
			st.setString(4,t.getUnit_id());
			st.setString(5,t.getPassword());
			st.setInt(6, t.getRole_id());
			
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
