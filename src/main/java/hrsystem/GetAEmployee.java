package hrsystem;

import model.employee.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.JDBCUtil;

public class GetAEmployee implements IHRSystem<Employee>{
	
	public static GetAEmployee getInstance() {
		return new GetAEmployee();
	}
	
    @Override
    public Employee getAEmployee(String id) {
    	Employee nv = new Employee();
		try {
			Connection con = JDBCUtil.getConnection();
			
			String sql = "SELECT * FROM Employees WHERE ID = '"+id+"';";
			PreparedStatement st = con.prepareStatement(sql);
				
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				String ID = rs.getString("ID");
				String Name = rs.getString("Name");
				String Department = rs.getString("Department");
				String UnitId = rs.getString("UnitId");
				String Password = rs.getString("Password");
				int Status = rs.getInt("Status");
				int RoleId = rs.getInt("RoleId");
			
				nv = new Employee(ID, Name, Department, UnitId, Password, RoleId,Status);
			}
			
			JDBCUtil.closeConnection(con);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nv;
    }

    @Override
    public ArrayList<Employee> getAllEmployees() {
        return null;
    }
//    public static void main(String[] args) {
//		Employee nv = GetAEmployee.getInstance().getAEmployee("nv00");
//		System.out.println(nv);
//		if(nv.getId() == null) System.out.println("ko có");
//	}
}
