package database;

import model.logtimekeeping.LogTimekeepingWorker;

import java.sql.*;
import java.util.ArrayList;

public class TimekeepingWorkerDAO implements DAOPattern<LogTimekeepingWorker>{
    public static TimekeepingWorkerDAO getInstance() {
        return new TimekeepingWorkerDAO();
    }

    @Override
    public LogTimekeepingWorker getById(String id) {
        LogTimekeepingWorker log = new LogTimekeepingWorker();
        try {
            Connection con = JDBCUtil.getConnection();

            String sql = "SELECT * FROM logtimekeepingworker WHERE id = '"+id+"';";

            PreparedStatement st = con.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                String logID = rs.getString("id");
                String employee_id=rs.getString("employee_id");
                Date date = rs.getDate("date");
                Time time_in = rs.getTime("time_in");
                Time time_out =rs.getTime("time_out");
                Float shift1 = (float) rs.getDouble("shift1");
                Float shift2 = (float) rs.getDouble("shift2");
                Float shift3 = (float) rs.getDouble("shift3");

                log = new LogTimekeepingWorker(logID, employee_id, date, time_in, time_out, shift1, shift2, shift3);
            }

            JDBCUtil.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return log;
    }

    public ArrayList<LogTimekeepingWorker> getByEmployeeID(String employeeID) {
        ArrayList<LogTimekeepingWorker> LogTimekeepingWorkers = new ArrayList<LogTimekeepingWorker>();
        try {
            Connection con = JDBCUtil.getConnection();

            Statement st = con.createStatement();

            String sql = "SELECT * FROM logtimekeepingworker WHERE employee_id = '" + employeeID +"';";

            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {

                String logID = rs.getString("id");
                String employee_id=rs.getString("employee_id");
                Date date = rs.getDate("date");
                Time time_in = rs.getTime("time_in");
                Time time_out =rs.getTime("time_out");
                Float shift1 = (float) rs.getDouble("shift1");
                Float shift2 = (float) rs.getDouble("shift2");
                Float shift3 = (float) rs.getDouble("shift3");

                LogTimekeepingWorker log = new LogTimekeepingWorker(logID, employee_id, date, time_in, time_out, shift1, shift2, shift3);
                LogTimekeepingWorkers.add(log);

            }

            JDBCUtil.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return LogTimekeepingWorkers;
    }

    public LogTimekeepingWorker getByEmployeeIdAndDate(String employeeID, Date date) {
        LogTimekeepingWorker log = null;
        try {
            Connection con = JDBCUtil.getConnection();

            String sql = "SELECT * FROM logtimekeepingworker WHERE employee_id = ? AND date = ?";

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
                Float shift1 = (float) rs.getDouble("shift1");
                Float shift2 = (float) rs.getDouble("shift2");
                Float shift3 = (float) rs.getDouble("shift3");

                log = new LogTimekeepingWorker(logID, employee_id, date1, time_in, time_out, shift1, shift2, shift3);
            }

            JDBCUtil.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return log;
    }

    @Override
    public ArrayList<LogTimekeepingWorker> getAll() {
        ArrayList<LogTimekeepingWorker> allLogTimekeepingWorkers = new ArrayList<LogTimekeepingWorker>();
        try {
            Connection con = JDBCUtil.getConnection();

            Statement st = con.createStatement();

            String sql = "SELECT * FROM logtimekeepingworker";

            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {

                String logID = rs.getString("id");
                String employee_id=rs.getString("employee_id");
                Date date = rs.getDate("date");
                Time time_in = rs.getTime("time_in");
                Time time_out =rs.getTime("time_out");
                Float shift1 = (float) rs.getDouble("shift1");
                Float shift2 = (float) rs.getDouble("shift2");
                Float shift3 = (float) rs.getDouble("shift3");

                LogTimekeepingWorker log = new LogTimekeepingWorker(logID, employee_id, date, time_in, time_out, shift1, shift2, shift3);
                allLogTimekeepingWorkers.add(log);

            }

            JDBCUtil.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allLogTimekeepingWorkers;
    }

    @Override
    public void insert(LogTimekeepingWorker t) {
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "INSERT INTO logtimekeepingworker (employee_id, date, time_in, time_out, shift1, shift2, shift3) "
                    + "VALUES ( ?, ?, ?, ?, ?, ? , ? )";
            PreparedStatement st = con.prepareStatement(sql);

            st.setString(1,t.getEmployee_id());
            st.setDate(2,t.getDate());
            st.setTime(3,t.getTime_in());
            st.setTime(4,t.getTime_out());
            st.setDouble(5, t.getShift1());
            st.setDouble(6, t.getShift2());
            st.setDouble(7, t.getShift3());

            st.executeUpdate();
            JDBCUtil.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(LogTimekeepingWorker logTimekeepingWorker) {
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE logtimekeepingworker SET time_in = ?, time_out = ?, shift1 = ?, shift2 = ?, shift3 = ? WHERE employee_id = ? AND date = ?";

            PreparedStatement st = con.prepareStatement(sql);

            st.setTime(1, logTimekeepingWorker.getTime_in());
            st.setTime(2, logTimekeepingWorker.getTime_out());
            st.setDouble(3, logTimekeepingWorker.getShift1());
            st.setDouble(4, logTimekeepingWorker.getShift2());
            st.setDouble(5, logTimekeepingWorker.getShift3());
            st.setString(6, logTimekeepingWorker.getEmployee_id());
            st.setDate(7, logTimekeepingWorker.getDate());

            st.executeUpdate();
            JDBCUtil.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(LogTimekeepingWorker logTimekeepingWorker) {

    }
}
