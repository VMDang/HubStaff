package model.logtimekeeping;

import java.util.Date;

public class LogTimekeeping {
    protected String employee_id;
    protected Date date;
    protected String in;
    protected String out;
	public LogTimekeeping(String employee_id, Date date, String in, String out) {
		super();
		this.employee_id = employee_id;
		this.date = date;
		this.in = in;
		this.out = out;
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
	public String getIn() {
		return in;
	}
	public void setIn(String in) {
		this.in = in;
	}
	public String getOut() {
		return out;
	}
	public void setOut(String out) {
		this.out = out;
	}
    
	
    
}
