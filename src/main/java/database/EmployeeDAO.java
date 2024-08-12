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
                String Department = rs.getString("department");
                String UnitId = rs.getString("unit_id");
                String Password = rs.getString("password");
                int Status = rs.getInt("status");
                int RoleId = rs.getInt("role_id");

                nv = new Employee(ID, Name, Department, UnitId, Password, RoleId,Status);
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
                String Department = rs.getString("department");
                String UnitId = rs.getString("unit_id");
                String Password = rs.getString("password");
                int Status = rs.getInt("status");
                int RoleId = rs.getInt("role_id");

                Employee nv = new Employee(ID, Name, Department, UnitId, Password, RoleId, Status);
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
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);

            st.setString(1, employee.getId());
            st.setString(2, employee.getName());
            st.setString(3, employee.getDepartment());
            st.setString(4, employee.getPassword());
            st.setString(5, employee.getUnit_id());
            st.setInt(6, employee.getRole_id());
            st.setInt(7, employee.getStatus());

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

            String sql = "UPDATE employees SET name = ?, department = ?, unit_id = ?, password = ?, role_id = ?, status = ? WHERE id = ?";
            PreparedStatement st = con.prepareStatement(sql);

            st.setString(1, employee.getName());
            st.setString(2, employee.getDepartment());
            st.setString(3, employee.getUnit_id());
            st.setString(4, employee.getPassword());
            st.setInt(5, employee.getRole_id());
            st.setInt(6, employee.getStatus());
            st.setString(7, employee.getId());

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
