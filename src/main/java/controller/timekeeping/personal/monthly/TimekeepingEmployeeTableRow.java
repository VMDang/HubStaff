package controller.timekeeping.personal.monthly;

import java.sql.Date;
import java.sql.Time;

public class TimekeepingEmployeeTableRow {
    private Date date;
    private Time time_in;
    private Time time_out;
    private float hour_work;
    private float overtime;
    private String status;
    private float shift1;
    private float shift2;
    private float shift3;
    private boolean morning;
    private boolean afternoon;
    
	public TimekeepingEmployeeTableRow(Date date, Time time_in, Time time_out, float hour_work, float overtime,
			String status, float shift1, float shift2, float shift3) {
		super();
		this.date = date;
		this.time_in = time_in;
		this.time_out = time_out;
		this.hour_work = hour_work;
		this.overtime = overtime;
		this.status = status;
		this.shift1 = shift1;
		this.shift2 = shift2;
		this.shift3 = shift3;
	}
	public TimekeepingEmployeeTableRow(Date date, Time time_in, Time time_out, float hour_work, float overtime,
			String status,boolean morning, boolean afternoon) {
		super();
		this.date = date;
		this.time_in = time_in;
		this.time_out = time_out;
		this.hour_work = hour_work;
		this.overtime = overtime;
		this.status = status;
		this.shift1 = 0f;
		this.shift2 = 0f;
		this.shift3 = 0f;
		this.morning = morning;
		this.afternoon = afternoon;
	}
	public TimekeepingEmployeeTableRow(Date date, String status) {
		this.date = date;
		this.time_in = null;
		this.time_out = null;
		this.hour_work = 0f;
		this.overtime = 0f;
		this.status = status;
		this.shift1 = 0f;
		this.shift2 = 0f;
		this.shift3 = 0f;
		this.morning = false;
		this.afternoon = false;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Time getTime_in() {
		return time_in;
	}
	public void setTime_in(Time time_in) {
		this.time_in = time_in;
	}
	public Time getTime_out() {
		return time_out;
	}
	public void setTime_out(Time time_out) {
		this.time_out = time_out;
	}
	public float getHour_work() {
		return hour_work;
	}
	public void setHour_work(float hour_work) {
		this.hour_work = hour_work;
	}
	public float getOvertime() {
		return overtime;
	}
	public void setOvertime(float overtime) {
		this.overtime = overtime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public float getShift1() {
		return shift1;
	}
	public void setShift1(float shift1) {
		this.shift1 = shift1;
	}
	public float getShift2() {
		return shift2;
	}
	public void setShift2(float shift2) {
		this.shift2 = shift2;
	}
	public float getShift3() {
		return shift3;
	}
	public void setShift3(float shift3) {
		this.shift3 = shift3;
	}
	public boolean isMorning() {
		return morning;
	}
	public void setMorning(boolean morning) {
		this.morning = morning;
	}
	public boolean isAfternoon() {
		return afternoon;
	}
	public void setAfternoon(boolean afternoon) {
		this.afternoon = afternoon;
	}
    
    
}
