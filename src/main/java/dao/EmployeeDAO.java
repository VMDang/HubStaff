package dao;

import java.sql.Connection;
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
					+ "VALUES ("++"," +value1+"," +value1+"," +value2+"," +value3+");";
			
			JDBCUtil.closeConnection(con);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public int update(Employee t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Employee t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Employee> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee selectById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Employee> selectByCondition(String condition) {
		// TODO Auto-generated method stub
		return null;
	}

}
