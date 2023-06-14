package createDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import database.JDBCUtil;

public class CreateTables {
	
	public static void createTableEmployees() {
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
			st.execute(sql);
			
			
			JDBCUtil.closeConnection(connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void createTableLogTimekeepingWorker() {
		try {
			Connection connection = JDBCUtil.getConnection();
			System.out.println(connection);
			
			Statement st = connection.createStatement();
			
			String sql = "CREATE TABLE LogTimekeepingWorker (\r\n"
					+ "    ID varchar(255) NOT NULL ,\r\n"
					+ "    EmployeeID varchar(255) NOT NULL,\r\n"
					+ "    Date DATE NOT NULL,\r\n"
					+ "    TimeIn TIME NOT NULL,\r\n"
					+ "    TimeOut TIME NOT NULL,\r\n"
					+ "    Shift1 DOUBLE(10, 2) NOT NULL,\r\n"
					+ "    Shift2 DOUBLE(10, 2) NOT NULL,\r\n"
					+ "    Shift3 DOUBLE(10, 2) NOT NULL,\r\n"
					+ "    PRIMARY KEY (ID),\r\n"
					+ "    CONSTRAINT FK_Employees_Table_Worker FOREIGN KEY (EmployeeID)\r\n"
					+ "    REFERENCES Employees(ID)"
					+ ");";
			st.execute(sql);
			
			
			JDBCUtil.closeConnection(connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void createTableLogTimekeepingOfficer() {
		try {
			Connection connection = JDBCUtil.getConnection();
			System.out.println(connection);
			
			Statement st = connection.createStatement();
			
			String sql = "CREATE TABLE LogTimekeepingOfficer (\r\n"
					+ "    ID varchar(255) NOT NULL ,\r\n"
					+ "    EmployeeID varchar(255) NOT NULL,\r\n"
					+ "    Date DATE NOT NULL,\r\n"
					+ "    TimeIn TIME NOT NULL,\r\n"
					+ "    TimeOut TIME NOT NULL,\r\n"
					+ "    Morning BOOL NOT NULL,\r\n"
					+ "    Afternoon BOOL NOT NULL,\r\n"
					+ "    HourLate DOUBLE(3, 2) ,\r\n"
					+ "    HourEarly DOUBLE(3, 2) ,\r\n"
					+ "    PRIMARY KEY (ID),\r\n"
					+ "    CONSTRAINT FK_Employees FOREIGN KEY (EmployeeID)\r\n"
					+ "    REFERENCES Employees(ID)"
					+ ");";
			System.out.println(sql);
			st.execute(sql);
			
			
			JDBCUtil.closeConnection(connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		CreateTables.createTableLogTimekeepingWorker();
	}
}
