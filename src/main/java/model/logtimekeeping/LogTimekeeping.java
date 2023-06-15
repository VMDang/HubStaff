package model.logtimekeeping;

import java.sql.Time;
import java.sql.Date;

public class LogTimekeeping {
	protected String logID ;
    protected String employee_id;
    protected Date date;
    protected Time time_in;
    protected Time time_out;
    
	public LogTimekeeping() {
		
	}
	public LogTimekeeping(String logID, String employee_id, Date date, Time time_in, Time time_out) {
		super();
		this.logID = logID;
		this.employee_id = employee_id;
		this.date = date;
		this.time_in = time_in;
		this.time_out = time_out;
	}
	public String getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
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
	public String getLogID() {
		return logID;
	}
	public void setLogID(String logID) {
		this.logID = logID;
	}
	
    
    
}
