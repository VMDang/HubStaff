package hrsystem;

import model.employee.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.JDBCUtil;

public class GetAllEmployees implements IHRSystem<Employee>{
	
	public static GetAllEmployees getInstance() {
		return new GetAllEmployees();
	}

	@Override
	public Employee getAEmployee(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public ArrayList<Employee> getAllEmployees() {
		ArrayList<Employee> allEmployees = new ArrayList<Employee>();
		try {
			Connection con = JDBCUtil.getConnection();
			
			Statement st = con.createStatement();
			
			String sql = "SELECT * FROM Employees";
			
			System.out.println(sql);
			
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				String ID = rs.getString("ID");
				String Name = rs.getString("Name");
				String Department = rs.getString("Department");
				String UnitId = rs.getString("UnitId");
				String Password = rs.getString("Password");
				int Status = rs.getInt("Status");
				int RoleId = rs.getInt("RoleId");
				
				Employee nv = new Employee(ID, Name, Department, UnitId, Password, RoleId, Status);
				allEmployees.add(nv);
					
			}
		
			JDBCUtil.closeConnection(con);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allEmployees;
	}
   
}
