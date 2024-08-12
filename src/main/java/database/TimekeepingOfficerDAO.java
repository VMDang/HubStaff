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

                log = new LogTimekeepingOfficer(logID, employee_id, date, time_in, time_out, morning, afternoon, hour_late, hour_early);
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

                String logID = rs.getString("ID");
                String employee_id=rs.getString("EmployeeID");
                Date date = rs.getDate("Date");
                Time time_in = rs.getTime("TimeIn");
                Time time_out =rs.getTime("TimeOut");
                boolean morning = rs.getBoolean("Morning");
                boolean afternoon = rs.getBoolean("Afternoon");
                Float hour_late = (float) rs.getDouble("HourLate");
                Float hour_early = (float) rs.getDouble("HourEarly");

                LogTimekeepingOfficer log = new LogTimekeepingOfficer(logID, employee_id, date, time_in, time_out, morning, afternoon, hour_late, hour_early);
                logTimekeepingOfficers.add(log);
            }

            JDBCUtil.closeConnection(con);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return logTimekeepingOfficers;
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

                LogTimekeepingOfficer log = new LogTimekeepingOfficer(logID, employee_id, date, time_in, time_out, morning, afternoon, hour_late, hour_early);
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
            String sql = "INSERT INTO logtimekeepingofficer\r\n"
                    + "VALUES ( ?, ?, ?, ?, ?, ?, ? , ? ,?)";

            PreparedStatement st = con.prepareStatement(sql);

            st.setString(1,t.getLogID());
            st.setString(2,t.getEmployee_id());
            st.setDate(3,t.getDate());
            st.setTime(4,t.getTime_in());
            st.setTime(5,t.getTime_out());
            st.setBoolean(6, t.isMorning());
            st.setBoolean(7, t.isAfternoon());
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

    }

    @Override
    public void delete(LogTimekeepingOfficer logTimekeepingOfficer) {

    }
}
