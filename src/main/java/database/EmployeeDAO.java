package database;

import model.employee.Employee;

import java.sql.*;
import java.util.ArrayList;

public class EmployeeDAO implements DAOPattern<Employee>{
    public static EmployeeDAO getInstance() {
        return new EmployeeDAO();
    }

    @Override
    public Employee getById(String id) {
        Employee nv = new Employee();
        try {
            Connection con = JDBCUtil.getConnection();

            String sql = "SELECT * FROM employees WHERE id = '"+id+"';";
            PreparedStatement st = con.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                String ID = rs.getString("id");
                String Name = rs.getString("name");
                String Identifier = rs.getString("identifier");
                Date Birthday = rs.getDate("birthday");
                String Address = rs.getString("address");
                String Gender = rs.getString("gender");
                String Phone = rs.getString("phone");
                String Department = rs.getString("department");
                String UnitId = rs.getString("unit_id");
                String Password = rs.getString("password");
                int Status = rs.getInt("status");
                int RoleId = rs.getInt("role_id");

                nv = new Employee(ID, Name, Identifier, Birthday, Address, Gender, Phone, Department, UnitId, Password, RoleId, Status);
            }

            JDBCUtil.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nv;
    }

    @Override
    public ArrayList<Employee> getAll() {
        ArrayList<Employee> allEmployees = new ArrayList<Employee>();
        try {
            Connection con = JDBCUtil.getConnection();

            Statement st = con.createStatement();

            String sql = "SELECT * FROM employees";

            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {
                String ID = rs.getString("id");
                String Name = rs.getString("name");
                String Identifier = rs.getString("identifier");
                Date Birthday = rs.getDate("birthday");
                String Address = rs.getString("address");
                String Gender = rs.getString("gender");
                String Phone = rs.getString("phone");
                String Department = rs.getString("department");
                String UnitId = rs.getString("unit_id");
                String Password = rs.getString("password");
                int Status = rs.getInt("status");
                int RoleId = rs.getInt("role_id");

                Employee nv = new Employee(ID, Name, Identifier, Birthday, Address, Gender, Phone, Department, UnitId, Password, RoleId, Status);
                allEmployees.add(nv);

            }

            JDBCUtil.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allEmployees;
    }

    @Override
    public void insert(Employee employee) {
        try {
            Connection con = JDBCUtil.getConnection();

            String sql = "INSERT INTO employees\r\n" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);

            st.setString(1, employee.getId());
            st.setString(2, employee.getName());
            st.setString(3, employee.getIdentifier());
            st.setDate(4, employee.getBirthday());
            st.setString(5, employee.getGender());
            st.setString(6, employee.getAddress());
            st.setString(7, employee.getPhone());
            st.setString(8, employee.getDepartment());
            st.setString(9, employee.getPassword());
            st.setString(10, employee.getUnit_id());
            st.setInt(11, employee.getRole_id());
            st.setInt(12, employee.getStatus());

            st.executeUpdate();
            JDBCUtil.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Employee employee) {
        try {
            Connection con = JDBCUtil.getConnection();

            String sql = "UPDATE employees SET name = ?, identifier = ?, birthday = ?, gender = ?, address = ?, phone = ?," +
                    "department = ?, unit_id = ?, password = ?, role_id = ?, status = ? WHERE id = ?";
            PreparedStatement st = con.prepareStatement(sql);

            st.setString(1, employee.getName());
            st.setString(2, employee.getIdentifier());
            st.setDate(3, employee.getBirthday());
            st.setString(4, employee.getGender());
            st.setString(5, employee.getAddress());
            st.setString(6, employee.getPhone());
            st.setString(7, employee.getDepartment());
            st.setString(8, employee.getUnit_id());
            st.setString(9, employee.getPassword());
            st.setInt(10, employee.getRole_id());
            st.setInt(11, employee.getStatus());
            st.setString(12, employee.getId());

            System.out.println();

            st.executeUpdate();
            JDBCUtil.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Employee employee) {
        try {
            Connection con = JDBCUtil.getConnection();

            String sql = "DELETE FROM employees WHERE id = ?";
            PreparedStatement st = con.prepareStatement(sql);

            st.setString(1, employee.getId());

            st.executeUpdate();
            JDBCUtil.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
