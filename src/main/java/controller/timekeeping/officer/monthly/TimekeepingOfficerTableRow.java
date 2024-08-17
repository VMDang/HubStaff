package controller.timekeeping.officer.monthly;

import java.sql.Date;
import java.sql.Time;

public class TimekeepingOfficerTableRow {
    private Date date;
    private Time time_in;
    private Time time_out;
    private float amount_work;
    private float overtime;
    private String status;
    private boolean morning;
    private boolean afternoon;
    private float hour_late;
    private float hour_early;

    public TimekeepingOfficerTableRow(Date date, Time time_in, Time time_out, float amount_work, float overtime,
                                      String status, boolean morning, boolean afternoon, float hour_late, float hour_early) {
        super();
        this.date = date;
        this.time_in = time_in;
        this.time_out = time_out;
        this.amount_work = amount_work;
        this.overtime = overtime;
        this.status = status;
        this.morning = morning;
        this.afternoon = afternoon;
        this.hour_late = hour_late;
        this.hour_early = hour_early;
    }

    public TimekeepingOfficerTableRow(Date date, String status) {
        this.date = date;
        this.time_in = null;
        this.time_out = null;
        this.amount_work = 0f;
        this.overtime = 0f;
        this.status = status;
        this.morning = false;
        this.afternoon = false;
        this.hour_late = 0f;
        this.hour_early = 0f;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime_in() {
        return time_in;
    }

    public Time getTime_out() {
        return time_out;
    }

    public float getAmount_work() {
        return amount_work;
    }

    public float getOvertime() {
        return overtime;
    }

    public String getStatus() {
        return status;
    }

    public boolean isAfternoon() {
        return afternoon;
    }

    public void setAfternoon(boolean afternoon) {
        this.afternoon = afternoon;
    }

    public boolean isMorning() {
        return morning;
    }

    public void setMorning(boolean morning) {
        this.morning = morning;
    }

    public float getHour_late() {
        return hour_late;
    }

    public void setHour_late(float hour_late) {
        this.hour_late = hour_late;
    }

    public float getHour_early() {
        return hour_early;
    }

    public void setHour_early(float hour_early) {
        this.hour_early = hour_early;
    }
}
