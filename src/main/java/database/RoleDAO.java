package database;

import model.employee.Role;
import model.employee.Unit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class RoleDAO implements DAOPattern<Role>{
    public static RoleDAO getInstance(){
        return new RoleDAO();
    }

    @Override
    public Role getById(String id) {
        Role role = new Role();
        try {
            Connection con = JDBCUtil.getConnection();

            String sql = "SELECT * FROM roles WHERE id = '"+id+"';";
            PreparedStatement st = con.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                int ID = rs.getInt("id");
                String Name = rs.getString("name");

                role = new Role(ID, Name);
            }

            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return role;
    }

    @Override
    public ArrayList<Role> getAll() {
        ArrayList<Role> roles = new ArrayList<Role>();

        try {
            Connection con = JDBCUtil.getConnection();

            String sql = "SELECT * FROM roles";
            PreparedStatement st = con.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                int ID = rs.getInt("id");
                String Name = rs.getString("name");

                Role role = new Role(ID, Name);
                roles.add(role);
            }

            JDBCUtil.closeConnection(con);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return roles;
    }

    @Override
    public void insert(Role role) {

    }

    @Override
    public void update(Role role) {

    }

    @Override
    public void delete(Role role) {

    }
}
