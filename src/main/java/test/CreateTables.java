package test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import database.JDBCUtil;

public class CreateTables {
	public static void main(String[] args) {
		try {
			Connection connection = JDBCUtil.getConnection();
			System.out.println(connection);
			
			Statement st = connection.createStatement();
			
			String sql = "CREATE TABLE Employees (\r\n"
					+ "    ID varchar(255) NOT NULL ,\r\n"
					+ "    Name varchar(255) NOT NULL,\r\n"
					+ "    Department varchar(255) NOT NULL,\r\n"
					+ "    UnitId varchar(255) NOT NULL,\r\n"
					+ "    Password varchar(255) NOT NULL,\r\n"
					+ "    RoleId int NOT NULL,\r\n"
					+ "    PRIMARY KEY (ID)\r\n"
					+ ");";
			st.executeUpdate(sql);
			
			
			JDBCUtil.closeConnection(connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
