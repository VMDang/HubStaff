package database;

import model.employee.Unit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UnitDAO implements DAOPattern<Unit>{
    public static UnitDAO getInstance() {
        return new UnitDAO();
    }

    @Override
    public Unit getById(String id) {
        Unit unit = new Unit();
        try {
            Connection con = JDBCUtil.getConnection();

            String sql = "SELECT * FROM units WHERE id = '"+id+"';";
            PreparedStatement st = con.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                String ID = rs.getString("id");
                String Name = rs.getString("name");
                String Location = rs.getString("location");

                unit = new Unit(ID, Name, Location);
            }

            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return unit;
    }

    @Override
    public ArrayList<Unit> getAll() {
        ArrayList<Unit> units = new ArrayList<Unit>();

        try {
            Connection con = JDBCUtil.getConnection();

            String sql = "SELECT * FROM units";
            PreparedStatement st = con.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                String ID = rs.getString("id");
                String Name = rs.getString("name");
                String Location = rs.getString("location");

                Unit unit = new Unit(ID, Name, Location);
                units.add(unit);
            }

            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return units;
    }

    @Override
    public void insert(Unit unit) {

    }

    @Override
    public void update(Unit unit) {

    }

    @Override
    public void delete(Unit unit) {

    }
}
