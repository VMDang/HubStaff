package model.logtimekeeping;

import java.sql.Time;
import java.sql.Date;

public class LogTimekeepingOfficer extends LogTimekeeping{
    private boolean morning;
    private boolean afternoon;
    private float hour_late;
    private float hour_early;
    
	public LogTimekeepingOfficer() {}
	
	public LogTimekeepingOfficer(String logID, String employee_id, Date date, Time time_in, Time time_out,
			boolean morning, boolean afternoon, float hour_late, float hour_early) {
		super(logID, employee_id, date, time_in, time_out);
		this.morning = morning;
		this.afternoon = afternoon;
		this.hour_late = hour_late;
		this.hour_early = hour_early;
	}
	public LogTimekeepingOfficer(String logID, String employee_id, Date date, Time time_in, Time time_out,
			boolean morning, boolean afternoon) {
		super(logID, employee_id, date, time_in, time_out);
		this.morning = morning;
		this.afternoon = afternoon;
		this.hour_late = 0f;
		this.hour_early = 0f;
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
