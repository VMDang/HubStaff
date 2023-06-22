package controller.report.hrmanager.generalinformation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dbtimekeeping.GetTimekeepingOfficer;
import model.logtimekeeping.LogTimekeepingOfficer;

public class GeneralInformationOfficer {
	private static ArrayList<LogTimekeepingOfficer> logTimekeepingOfficers = GetTimekeepingOfficer.getInstance().getAllTimekeepings();
	
    public static int getMonthFromDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        String month = sdf.format(date);
        return Integer.parseInt(month);
    }
	
    public static int getQuarterFromDate(Date date) {
        int month = getMonthFromDate(date);
        int quarter = (month / 3);
        int r = month % 3;
        if(r == 0) return quarter;
        return quarter+1;
    }
    
    public static int getYearFromDate(Date date) {
    	SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
    	String year = sdf.format(date);
    	return Integer.parseInt(year);
    }
    
    public static double countHourEarlyByMonth(int month, int year) {
		double count = 0;
		for (LogTimekeepingOfficer logTimekeepingOfficer : logTimekeepingOfficers) {
			if(getMonthFromDate(logTimekeepingOfficer.getDate()) == month && getYearFromDate(logTimekeepingOfficer.getDate()) == year) {
				count = count + logTimekeepingOfficer.getHour_early();
			}
		}
		return count;   	
    }
    public static double countHourLateByMonth(int month, int year) {
		double count = 0;
		for (LogTimekeepingOfficer logTimekeepingOfficer : logTimekeepingOfficers) {
			if(getMonthFromDate(logTimekeepingOfficer.getDate()) == month && getYearFromDate(logTimekeepingOfficer.getDate()) == year) {
				count = count + logTimekeepingOfficer.getHour_late();
			}
		}
		return count;   	
    }
    public static double countHourEarlyByQuarter(int quarter, int year) {
		double count = 0;
		for (LogTimekeepingOfficer logTimekeepingOfficer : logTimekeepingOfficers) {
			if(getQuarterFromDate(logTimekeepingOfficer.getDate()) == quarter && getYearFromDate(logTimekeepingOfficer.getDate()) == year) {
				count = count + logTimekeepingOfficer.getHour_early();
			}
		}
		return count;   	
    }
    public static double countHourLateByQuarter(int quarter, int year) {
		double count = 0;
		for (LogTimekeepingOfficer logTimekeepingOfficer : logTimekeepingOfficers) {
			if(getQuarterFromDate(logTimekeepingOfficer.getDate()) == quarter && getYearFromDate(logTimekeepingOfficer.getDate()) == year) {
				count = count + logTimekeepingOfficer.getHour_late();
			}
		}
		return count;   	
    }
    public static double countHourEarlyByYear(int year) {
		double count = 0;
		for (LogTimekeepingOfficer logTimekeepingOfficer : logTimekeepingOfficers) {
			if(getYearFromDate(logTimekeepingOfficer.getDate()) == year) {
				count = count + logTimekeepingOfficer.getHour_early();
			}
		}
		return count;   	
    }
    public static double countHourLateByYear(int year) {
		double count = 0;
		for (LogTimekeepingOfficer logTimekeepingOfficer : logTimekeepingOfficers) {
			if(getYearFromDate(logTimekeepingOfficer.getDate()) == year) {
				count = count + logTimekeepingOfficer.getHour_late();
			}
		}
		return count;   	
    }
}
