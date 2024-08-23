package database;

import model.logtimekeeping.LogTimekeepingOfficer;

import java.sql.*;
import java.util.ArrayList;

public class TimekeepingOfficerDAO implements DAOPattern<LogTimekeepingOfficer>{
    public static TimekeepingOfficerDAO getInstance() {
        return new TimekeepingOfficerDAO();
    }

    @Override
    public LogTimekeepingOfficer getById(String id) {
        LogTimekeepingOfficer log = new LogTimekeepingOfficer();
        try {
            Connection con = JDBCUtil.getConnection();

            String sql = "SELECT * FROM logtimekeepingofficer WHERE id = '"+id+"';";
            PreparedStatement st = con.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                String logID = rs.getString("id");
                String employee_id=rs.getString("employee_id");
                Date date = rs.getDate("date");
                Time time_in = rs.getTime("time_in");
                Time time_out =rs.getTime("time_out");
                boolean morning = rs.getBoolean("morning");
                boolean afternoon = rs.getBoolean("afternoon");
                Float hour_late = (float) rs.getDouble("hour_late");
                Float hour_early = (float) rs.getDouble("hour_early");
                Float overtime = (float) rs.getDouble("overtime");

                log = new LogTimekeepingOfficer(logID, employee_id, date, time_in, time_out, morning, afternoon, hour_late, hour_early, overtime);
            }

            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return log;
    }

    public ArrayList<LogTimekeepingOfficer> getByEmployeeID(String employeeID) {

        ArrayList<LogTimekeepingOfficer> logTimekeepingOfficers = new ArrayList<LogTimekeepingOfficer>();
        try {
            Connection con = JDBCUtil.getConnection();

            String sql = "SELECT * FROM logtimekeepingofficer WHERE employee_id = '" + employeeID+"';";

            PreparedStatement st = con.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {

                String logID = rs.getString("id");
                String employee_id=rs.getString("employee_id");
                Date date = rs.getDate("date");
                Time time_in = rs.getTime("time_in");
                Time time_out =rs.getTime("time_out");
                boolean morning = rs.getBoolean("morning");
                boolean afternoon = rs.getBoolean("afternoon");
                Float hour_late = (float) rs.getDouble("hour_late");
                Float hour_early = (float) rs.getDouble("hour_early");
                Float overtime = (float) rs.getDouble("overtime");

                LogTimekeepingOfficer log = new LogTimekeepingOfficer(logID, employee_id, date, time_in, time_out, morning, afternoon, hour_late, hour_early, overtime);
                logTimekeepingOfficers.add(log);
            }

            JDBCUtil.closeConnection(con);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return logTimekeepingOfficers;
    }

    public LogTimekeepingOfficer getByEmployeeIdAndDate(String employeeID, Date date) {
        LogTimekeepingOfficer log = null;
        try {
            Connection con = JDBCUtil.getConnection();

            String sql = "SELECT * FROM logtimekeepingofficer WHERE employee_id = ? AND date = ?";

            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, employeeID);
            st.setDate(2, date);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                String logID = rs.getString("id");
                String employee_id=rs.getString("employee_id");
                Date date1 = rs.getDate("date");
                Time time_in = rs.getTime("time_in");
                Time time_out =rs.getTime("time_out");
                boolean morning = rs.getBoolean("morning");
                boolean afternoon = rs.getBoolean("afternoon");
                Float hour_late = (float) rs.getDouble("hour_late");
                Float hour_early = (float) rs.getDouble("hour_early");
                Float overtime = (float) rs.getDouble("overtime");

                log = new LogTimekeepingOfficer(logID, employee_id, date1, time_in, time_out, morning, afternoon, hour_late, hour_early, overtime);
            }

            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return log;
    }

    @Override
    public ArrayList<LogTimekeepingOfficer> getAll() {
        ArrayList<LogTimekeepingOfficer> allLogTimekeepingOfficers = new ArrayList<LogTimekeepingOfficer>();
        try {
            Connection con = JDBCUtil.getConnection();

            Statement st = con.createStatement();

            String sql = "SELECT * FROM logtimekeepingofficer";

            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {
                String logID = rs.getString("id");
                String employee_id=rs.getString("employee_id");
                Date date = rs.getDate("date");
                Time time_in = rs.getTime("time_in");
                Time time_out =rs.getTime("time_out");
                boolean morning = rs.getBoolean("morning");
                boolean afternoon = rs.getBoolean("afternoon");
                Float hour_late = (float) rs.getDouble("hour_late");
                Float hour_early = (float) rs.getDouble("hour_early");
                Float overtime = (float) rs.getDouble("overtime");

                LogTimekeepingOfficer log = new LogTimekeepingOfficer(logID, employee_id, date, time_in, time_out, morning, afternoon, hour_late, hour_early, overtime);
                allLogTimekeepingOfficers.add(log);
            }

            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allLogTimekeepingOfficers;
    }

    @Override
    public void insert(LogTimekeepingOfficer t) {
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "INSERT INTO logtimekeepingofficer (employee_id, date, time_in, time_out, morning, afternoon, overtime, hour_late, hour_early) "
                    + "VALUES (?, ?, ?, ?, ?, ? , ?, ? ,?)";

            PreparedStatement st = con.prepareStatement(sql);

            st.setString(1,t.getEmployee_id());
            st.setDate(2,t.getDate());
            st.setTime(3,t.getTime_in());
            st.setTime(4,t.getTime_out());
            st.setBoolean(5, t.isMorning());
            st.setBoolean(6, t.isAfternoon());
            st.setDouble(7, t.getOvertime());
            st.setDouble(8, t.getHour_late());
            st.setDouble(9, t.getHour_early());

            st.executeUpdate();
            JDBCUtil.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(LogTimekeepingOfficer logTimekeepingOfficer) {
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE logtimekeepingofficer SET time_in = ?, time_out = ?, morning = ?, afternoon = ?, overtime = ?, hour_late = ?, hour_early = ? WHERE employee_id = ? AND date = ?";

            PreparedStatement st = con.prepareStatement(sql);

            st.setTime(1, logTimekeepingOfficer.getTime_in());
            st.setTime(2, logTimekeepingOfficer.getTime_out());
            st.setBoolean(3, logTimekeepingOfficer.isMorning());
            st.setBoolean(4, logTimekeepingOfficer.isAfternoon());
            st.setDouble(5, logTimekeepingOfficer.getOvertime());
            st.setDouble(6, logTimekeepingOfficer.getHour_late());
            st.setDouble(7, logTimekeepingOfficer.getHour_early());
            st.setString(8, logTimekeepingOfficer.getEmployee_id());
            st.setDate(9, logTimekeepingOfficer.getDate());

            st.executeUpdate();
            JDBCUtil.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(LogTimekeepingOfficer logTimekeepingOfficer) {

    }
}
