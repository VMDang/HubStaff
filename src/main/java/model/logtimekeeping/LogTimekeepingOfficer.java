package model.logtimekeeping;

import java.util.Date;

public class LogTimekeepingOfficer extends LogTimekeeping{
    private boolean morning;
    private boolean afternoon;
    private float hour_late;
    private float hour_early;
    
	
	
	
	public LogTimekeepingOfficer(String employee_id, Date date, String in, String out, boolean morning,
			boolean afternoon, float hour_late, float hour_early) {
		super(employee_id, date, in, out);
		this.morning = morning;
		this.afternoon = afternoon;
		this.hour_late = hour_late;
		this.hour_early = hour_early;
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
