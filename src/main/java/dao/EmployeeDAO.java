package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.JDBCUtil;
import model.employee.Employee;

public class EmployeeDAO implements DAOInterface<Employee>{
	
	public static EmployeeDAO getInstance() {
		return new EmployeeDAO();
	}

	@Override
	public int insert(Employee t) {
		// TODO Auto-generated method stub
		try {
			Connection con = JDBCUtil.getConnection();
			
			Statement st = con.createStatement();
			
			String sql = "INSERT INTO Employees\r\n"
					+ "VALUES ('"+t.getId()+"','" +t.getName()+"','" +t.getDepartment()+"','" +t.getUnit_id()+"','"+t.getPassword()+"','" +t.getRole_id()+"');";
			
			System.out.println(sql);
			
			int check = st.executeUpdate(sql);
			
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

	@Override
	public int update(Employee t) {
		try {
			Connection con = JDBCUtil.getConnection();
			
			Statement st = con.createStatement();
			
			String sql = "UPDATE Employees\r\n"
					+ "SET Name = '"+t.getName()+"', Department = '"+t.getDepartment()+"', UnitId = '"+t.getUnit_id()+"', Password = '"+t.getPassword()+"', RoleId = "+t.getRole_id()+"\r\n"
					+ "WHERE ID = '"+t.getId()+"';";
			System.out.println(sql);
			
			int check = st.executeUpdate(sql);
			
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

	@Override
	public int delete(Employee t) {
		try {
			Connection con = JDBCUtil.getConnection();
			
			Statement st = con.createStatement();
			
			String sql = "DELETE FROM Employees WHERE ID = '"+t.getId()+"';";
			
			System.out.println(sql);
			
			int check = st.executeUpdate(sql);
			
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

	@Override
	public ArrayList<Employee> selectAll() {
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
				int RoleId = rs.getInt("RoleId");
				
				Employee nv = new Employee(ID, Name, Department, UnitId, Password, RoleId);
				allEmployees.add(nv);
					
			}
		
			JDBCUtil.closeConnection(con);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allEmployees;
	}

	@Override
	public Employee selectById(String id) {
		Employee nv = new Employee();
		try {
			Connection con = JDBCUtil.getConnection();
			
			Statement st = con.createStatement();
			
			String sql = "SELECT * FROM Employees WHERE ID = '"+id+"';";
			
			System.out.println(sql);
			
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				String ID = rs.getString("ID");
				String Name = rs.getString("Name");
				String Department = rs.getString("Department");
				String UnitId = rs.getString("UnitId");
				String Password = rs.getString("Password");
				int RoleId = rs.getInt("RoleId");
			
				nv = new Employee(ID, Name, Department, UnitId, Password, RoleId);
			}
			
			JDBCUtil.closeConnection(con);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nv;
	}

	@Override
	public ArrayList<Employee> selectByCondition(String condition) {
		ArrayList<Employee> employees = new ArrayList<Employee>();
		try {
			Connection con = JDBCUtil.getConnection();
			
			Statement st = con.createStatement();
			
			String sql = "SELECT * FROM Employees WHERE " + condition + ";";
			
			System.out.println(sql);
			
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				String ID = rs.getString("ID");
				String Name = rs.getString("Name");
				String Department = rs.getString("Department");
				String UnitId = rs.getString("UnitId");
				String Password = rs.getString("Password");
				int RoleId = rs.getInt("RoleId");
				
				Employee nv = new Employee(ID, Name, Department, UnitId, Password, RoleId);
				employees.add(nv);
					
			}
		
			JDBCUtil.closeConnection(con);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employees;
	}

}
