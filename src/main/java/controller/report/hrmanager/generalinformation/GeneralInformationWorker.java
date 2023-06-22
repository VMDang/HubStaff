package controller.report.hrmanager.generalinformation;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import dbtimekeeping.GetTimekeepingWorker;
import model.logtimekeeping.LogTimekeepingWorker;

public class GeneralInformationWorker {
	private static ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = GetTimekeepingWorker.getInstance().getAllTimekeepings();
	
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
    
	public static double countTimeShift1ByMonth(int month, int year) {
		double count = 0;
		for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
			if(getMonthFromDate(logTimekeepingWorker.getDate()) == month && getYearFromDate(logTimekeepingWorker.getDate()) == year) {
				count = count + logTimekeepingWorker.getShift1();
			}
		}
		return count;
	}

	public static double countTimeShift2ByMonth(int month, int year) {
		double count = 0;
		for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
			if(getMonthFromDate(logTimekeepingWorker.getDate()) == month && getYearFromDate(logTimekeepingWorker.getDate()) == year) {
				count = count + logTimekeepingWorker.getShift2();
			}
		}
		return count;
	}
	
	public static double countTimeShift3ByMonth(int month, int year) {
		double count = 0;
		for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
			if(getMonthFromDate(logTimekeepingWorker.getDate()) == month && getYearFromDate(logTimekeepingWorker.getDate()) == year) {
				count = count + logTimekeepingWorker.getShift3();
			}
		}
		return count;
	}
	
	public static double countTimeShift1ByQuarter(int quarter, int year) {
		double count = 0;
		for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
			if(getQuarterFromDate(logTimekeepingWorker.getDate()) == quarter && getYearFromDate(logTimekeepingWorker.getDate()) == year) {
				count = count + logTimekeepingWorker.getShift1();
			}
		}
		return count;
	}
	
	public static double countTimeShift2ByQuarter(int quarter, int year) {
		double count = 0;
		for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
			if(getQuarterFromDate(logTimekeepingWorker.getDate()) == quarter && getYearFromDate(logTimekeepingWorker.getDate()) == year) {
				count = count + logTimekeepingWorker.getShift2();
			}
		}
		return count;
	}
	
	public static double countTimeShift3ByQuarter(int quarter, int year) {
		double count = 0;
		for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
			if(getQuarterFromDate(logTimekeepingWorker.getDate()) == quarter && getYearFromDate(logTimekeepingWorker.getDate()) == year) {
				count = count + logTimekeepingWorker.getShift3();
			}
		}
		return count;
	}
	
	public static double countTimeShift1ByYear(int year) {
		double count = 0;
		for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
			if(getYearFromDate(logTimekeepingWorker.getDate()) == year) {
				count = count + logTimekeepingWorker.getShift1();
			}
		}
		return count;
	}
	
	public static double countTimeShift2ByYear(int year) {
		double count = 0;
		for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
			if(getYearFromDate(logTimekeepingWorker.getDate()) == year) {
				count = count + logTimekeepingWorker.getShift2();
			}
		}
		return count;
	}
	
	public static double countTimeShift3ByYear(int year) {
		double count = 0;
		for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
			if(getYearFromDate(logTimekeepingWorker.getDate()) == year) {
				count = count + logTimekeepingWorker.getShift3();
			}
		}
		return count;
	}
	public static void main(String[] args) {
		System.out.println(getYearFromDate(logTimekeepingWorkers.get(0).getDate()));
	}
}
