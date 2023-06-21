package model.logtimekeeping;

import java.sql.Time;
import java.sql.Date;

public class LogTimekeepingWorker extends LogTimekeeping{
    private float shift1;
    private float shift2;
    private float shift3;
	
	public LogTimekeepingWorker() {}
	public LogTimekeepingWorker(String logID, String employee_id, Date date, Time time_in, Time time_out, float shift1,
			float shift2, float shift3) {
		super(logID, employee_id, date, time_in, time_out);
		this.shift1 = shift1;
		this.shift2 = shift2;
		this.shift3 = shift3;
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
	@Override
	public String toString() {
		return "LogTimekeepingWorker [shift1=" + shift1 + ", shift2=" + shift2 + ", shift3=" + shift3 + ", logID="
				+ logID + ", employee_id=" + employee_id + ", date=" + date + ", time_in=" + time_in + ", time_out="
				+ time_out + "]";
	}
	
    
    
}
