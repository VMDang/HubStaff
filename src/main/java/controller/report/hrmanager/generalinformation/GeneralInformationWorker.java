package controller.report.hrmanager.generalinformation;

import java.util.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import dbtimekeeping.GetTimekeepingWorker;
import model.logtimekeeping.LogTimekeepingWorker;

public class GeneralInformationWorker {
	private ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = GetTimekeepingWorker.getInstance().getAllTimekeepings();
	
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
    
    public static double convertToDouble(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int seconds = Integer.parseInt(parts[2]);
        double decimalTime = hours + (minutes / 60.0) + (seconds / 3600.0);
        DecimalFormat df = new DecimalFormat("#.#");
        return Double.parseDouble(df.format(decimalTime));
    }
    
    public static double roundouble(double db) {
        DecimalFormat df = new DecimalFormat("#.#");
        if(db <= 0) {
        	return 0.0;
        }
        return Double.parseDouble(df.format(db));
    }
    
	public double countTimeShift1ByMonth(int month, int year) {
		double count = 0;
		for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
			if(getMonthFromDate(logTimekeepingWorker.getDate()) == month && getYearFromDate(logTimekeepingWorker.getDate()) == year) {
				count = count + logTimekeepingWorker.getShift1();
			}
		}
		return count;
	}

	public double countTimeShift2ByMonth(int month, int year) {
		double count = 0;
		for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
			if(getMonthFromDate(logTimekeepingWorker.getDate()) == month && getYearFromDate(logTimekeepingWorker.getDate()) == year) {
				count = count + logTimekeepingWorker.getShift2();
			}
		}
		return count;
	}
	
	public double countTimeShift3ByMonth(int month, int year) {
		double count = 0;
		for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
			if(getMonthFromDate(logTimekeepingWorker.getDate()) == month && getYearFromDate(logTimekeepingWorker.getDate()) == year) {
				count = count + logTimekeepingWorker.getShift3();
			}
		}
		return count;
	}
	
	public double countTimeShift1ByQuarter(int quarter, int year) {
		double count = 0;
		for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
			if(getQuarterFromDate(logTimekeepingWorker.getDate()) == quarter && getYearFromDate(logTimekeepingWorker.getDate()) == year) {
				count = count + logTimekeepingWorker.getShift1();
			}
		}
		return count;
	}
	
	public double countTimeShift2ByQuarter(int quarter, int year) {
		double count = 0;
		for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
			if(getQuarterFromDate(logTimekeepingWorker.getDate()) == quarter && getYearFromDate(logTimekeepingWorker.getDate()) == year) {
				count = count + logTimekeepingWorker.getShift2();
			}
		}
		return count;
	}
	
	public double countTimeShift3ByQuarter(int quarter, int year) {
		double count = 0;
		for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
			if(getQuarterFromDate(logTimekeepingWorker.getDate()) == quarter && getYearFromDate(logTimekeepingWorker.getDate()) == year) {
				count = count + logTimekeepingWorker.getShift3();
			}
		}
		return count;
	}
	
	public double countTimeShift1ByYear(int year) {
		double count = 0;
		for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
			if(getYearFromDate(logTimekeepingWorker.getDate()) == year) {
				count = count + logTimekeepingWorker.getShift1();
			}
		}
		return count;
	}
	
	public double countTimeShift2ByYear(int year) {
		double count = 0;
		for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
			if(getYearFromDate(logTimekeepingWorker.getDate()) == year) {
				count = count + logTimekeepingWorker.getShift2();
			}
		}
		return count;
	}
	
	public double countTimeShift3ByYear(int year) {
		double count = 0;
		for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
			if(getYearFromDate(logTimekeepingWorker.getDate()) == year) {
				count = count + logTimekeepingWorker.getShift3();
			}
		}
		return count;
	}
	
	public double countHourLateByMonth(int month, int year) {
		double count = 0;
		for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
			if(getMonthFromDate(logTimekeepingWorker.getDate()) == month && getYearFromDate(logTimekeepingWorker.getDate()) == year) {
				count = count + ((convertToDouble(logTimekeepingWorker.getTime_in().toString()) - 7.5) > 0 ? (convertToDouble(logTimekeepingWorker.getTime_in().toString()) - 7.5) : 0);
			}
		}
		return roundouble(count);
	}
	
	public double countHourEarlyByMonth(int month, int year) {
		double count = 0;
		for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
			if(getMonthFromDate(logTimekeepingWorker.getDate()) == month && getYearFromDate(logTimekeepingWorker.getDate()) == year) {
				count = count + ((17.5 - convertToDouble(logTimekeepingWorker.getTime_out().toString())) > 0 ? (17.5 - convertToDouble(logTimekeepingWorker.getTime_out().toString())) : 0);
			}
		}
		return roundouble(count);
	}
	
	public double countHourLateByQuarter(int quarter, int year) {
		double count = 0;
		for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
			if(getQuarterFromDate(logTimekeepingWorker.getDate()) == quarter && getYearFromDate(logTimekeepingWorker.getDate()) == year) {
				count = count + ((convertToDouble(logTimekeepingWorker.getTime_in().toString()) - 7.5) > 0 ? (convertToDouble(logTimekeepingWorker.getTime_in().toString()) - 7.5) : 0);
			}
		}
		return roundouble(count);
	}
	
	public double countHourEarlyByQuarter(int quarter, int year) {
		double count = 0;
		for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
			if(getQuarterFromDate(logTimekeepingWorker.getDate()) == quarter && getYearFromDate(logTimekeepingWorker.getDate()) == year) {
				count = count + ((17.5 - convertToDouble(logTimekeepingWorker.getTime_out().toString())) > 0 ? (17.5 - convertToDouble(logTimekeepingWorker.getTime_out().toString())) : 0);
			}
		}
		return roundouble(count);
	}
	
	public double countHourLateByYear(int year) {
		double count = 0;
		for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
			if(getYearFromDate(logTimekeepingWorker.getDate()) == year) {
				count = count + ((convertToDouble(logTimekeepingWorker.getTime_in().toString()) - 7.5) > 0 ? (convertToDouble(logTimekeepingWorker.getTime_in().toString()) - 7.5) : 0);
			}
		}
		return roundouble(count);
	}
	
	public double countHourEarlyByYear(int year) {
		double count = 0;
		for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
			if(getYearFromDate(logTimekeepingWorker.getDate()) == year) {
				count = count + ((17.5 - convertToDouble(logTimekeepingWorker.getTime_out().toString())) > 0 ? (17.5 - convertToDouble(logTimekeepingWorker.getTime_out().toString())) : 0);
			}
		}
		return roundouble(count);
	}
}
