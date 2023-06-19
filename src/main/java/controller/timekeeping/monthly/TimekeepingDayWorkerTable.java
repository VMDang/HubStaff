package controller.timekeeping.monthly;

import java.sql.Date;
import java.sql.Time;

public class TimekeepingDayWorkerTable {
    protected Date date;
    protected Time time_in;
    protected Time time_out;
    protected float hour_work;
    protected float overtime;
    protected String status;
    
    
    
	public TimekeepingDayWorkerTable(Date date, Time time_in, Time time_out, float hour_work, float overtime,
			String status) {
		super();
		this.date = date;
		this.time_in = time_in;
		this.time_out = time_out;
		this.hour_work = hour_work;
		this.overtime = overtime;
		this.status = status;
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
    
    
}
