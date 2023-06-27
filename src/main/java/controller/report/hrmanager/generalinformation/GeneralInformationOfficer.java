package controller.report.hrmanager.generalinformation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dbtimekeeping.gettimekeeping.GetTimekeepingOfficer;
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
    
    public double countHourEarlyByMonth(int month, int year) {
		double count = 0;
		for (LogTimekeepingOfficer logTimekeepingOfficer : logTimekeepingOfficers) {
			if(getMonthFromDate(logTimekeepingOfficer.getDate()) == month && getYearFromDate(logTimekeepingOfficer.getDate()) == year) {
				count = count + logTimekeepingOfficer.getHour_early();
			}
		}
		return count;   	
    }
    public double countHourLateByMonth(int month, int year) {
		double count = 0;
		for (LogTimekeepingOfficer logTimekeepingOfficer : logTimekeepingOfficers) {
			if(getMonthFromDate(logTimekeepingOfficer.getDate()) == month && getYearFromDate(logTimekeepingOfficer.getDate()) == year) {
				count = count + logTimekeepingOfficer.getHour_late();
			}
		}
		return count;   	
    }
    public double countHourEarlyByQuarter(int quarter, int year) {
		double count = 0;
		for (LogTimekeepingOfficer logTimekeepingOfficer : logTimekeepingOfficers) {
			if(getQuarterFromDate(logTimekeepingOfficer.getDate()) == quarter && getYearFromDate(logTimekeepingOfficer.getDate()) == year) {
				count = count + logTimekeepingOfficer.getHour_early();
			}
		}
		return count;   	
    }
    public double countHourLateByQuarter(int quarter, int year) {
		double count = 0;
		for (LogTimekeepingOfficer logTimekeepingOfficer : logTimekeepingOfficers) {
			if(getQuarterFromDate(logTimekeepingOfficer.getDate()) == quarter && getYearFromDate(logTimekeepingOfficer.getDate()) == year) {
				count = count + logTimekeepingOfficer.getHour_late();
			}
		}
		return count;   	
    }
    public double countHourEarlyByYear(int year) {
		double count = 0;
		for (LogTimekeepingOfficer logTimekeepingOfficer : logTimekeepingOfficers) {
			if(getYearFromDate(logTimekeepingOfficer.getDate()) == year) {
				count = count + logTimekeepingOfficer.getHour_early();
			}
		}
		return count;   	
    }
    public double countHourLateByYear(int year) {
		double count = 0;
		for (LogTimekeepingOfficer logTimekeepingOfficer : logTimekeepingOfficers) {
			if(getYearFromDate(logTimekeepingOfficer.getDate()) == year) {
				count = count + logTimekeepingOfficer.getHour_late();
			}
		}
		return count;   	
    }
    
    public int countMorningByMonth(int month, int year) {
    	int count = 0;
		for (LogTimekeepingOfficer logTimekeepingOfficer : logTimekeepingOfficers) {
			if(getMonthFromDate(logTimekeepingOfficer.getDate()) == month && getYearFromDate(logTimekeepingOfficer.getDate()) == year) {
				count = count + (logTimekeepingOfficer.isMorning() ? 1 :0);
			}
		}
    	return count;
    }
    
    public int countAfternoonByMonth(int month, int year) {
    	int count = 0;
		for (LogTimekeepingOfficer logTimekeepingOfficer : logTimekeepingOfficers) {
			if(getMonthFromDate(logTimekeepingOfficer.getDate()) == month && getYearFromDate(logTimekeepingOfficer.getDate()) == year) {
				count = count + (logTimekeepingOfficer.isAfternoon() ? 1 :0);
			}
		}
    	return count;
    }
    
    public int countMorningByQuarter(int quarter, int year) {
    	int count = 0;
		for (LogTimekeepingOfficer logTimekeepingOfficer : logTimekeepingOfficers) {
			if(getQuarterFromDate(logTimekeepingOfficer.getDate()) == quarter && getYearFromDate(logTimekeepingOfficer.getDate()) == year) {
				count = count + (logTimekeepingOfficer.isMorning() ? 1 :0);
			}
		}
    	return count;
    }
    
    public int countAfternoonByQuarter(int quarter, int year) {
    	int count = 0;
		for (LogTimekeepingOfficer logTimekeepingOfficer : logTimekeepingOfficers) {
			if(getQuarterFromDate(logTimekeepingOfficer.getDate()) == quarter && getYearFromDate(logTimekeepingOfficer.getDate()) == year) {
				count = count + (logTimekeepingOfficer.isAfternoon() ? 1 :0);
			}
		}
    	return count;
    }
    
    public int countMorningByYear(int year) {
    	int count = 0;
		for (LogTimekeepingOfficer logTimekeepingOfficer : logTimekeepingOfficers) {
			if(getYearFromDate(logTimekeepingOfficer.getDate()) == year) {
				count = count + (logTimekeepingOfficer.isMorning() ? 1 :0);
			}
		}
    	return count;
    }
    
    public int countAfternoonByYear(int year) {
    	int count = 0;
		for (LogTimekeepingOfficer logTimekeepingOfficer : logTimekeepingOfficers) {
			if(getYearFromDate(logTimekeepingOfficer.getDate()) == year) {
				count = count + (logTimekeepingOfficer.isAfternoon() ? 1 :0);
			}
		}
    	return count;
    }
    public void main(String[] args) {
		System.out.println(logTimekeepingOfficers.get(0).isMorning());
	}
}
