package controller.report.hrmanager.generalinformation;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.xdgf.usermodel.shape.exceptions.StopVisitingThisBranch;

import dbtimekeeping.gettimekeeping.GetTimekeepingOfficer;
import model.employee.Employee;
import model.logtimekeeping.LogTimekeepingOfficer;

public class GeneralInformationOfficer extends GeneralInformation {
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
	
    public double countHourLateByMonthAEmployee(Employee employee, int month, int year) {
    	double count = 0;
		ArrayList<LogTimekeepingOfficer> LogTimekeepingOfficers = GetTimekeepingOfficer.getInstance().getTimekeepingsByEmployeeID(employee.getId());
		for (LogTimekeepingOfficer LogTimekeepingOfficer : LogTimekeepingOfficers) {
			if(getMonthFromDate(LogTimekeepingOfficer.getDate()) == month && getYearFromDate(LogTimekeepingOfficer.getDate()) == year) {
				count = count + ((convertToDouble(LogTimekeepingOfficer.getTime_in().toString()) - 7.5) > 0 ? (convertToDouble(LogTimekeepingOfficer.getTime_in().toString()) - 7.5) : 0);
			}				
		}
		return count;
    }
    
    public double countHourLateByQuarterAEmployee(Employee employee, int quarter, int year) {
    	double count = 0;
		ArrayList<LogTimekeepingOfficer> LogTimekeepingOfficers = GetTimekeepingOfficer.getInstance().getTimekeepingsByEmployeeID(employee.getId());
		for (LogTimekeepingOfficer LogTimekeepingOfficer : LogTimekeepingOfficers) {
			if(getQuarterFromDate(LogTimekeepingOfficer.getDate()) == quarter && getYearFromDate(LogTimekeepingOfficer.getDate()) == year) {
				count = count + ((convertToDouble(LogTimekeepingOfficer.getTime_in().toString()) - 7.5) > 0 ? (convertToDouble(LogTimekeepingOfficer.getTime_in().toString()) - 7.5) : 0);
			}				
		}
		return count;
    }
    
    public double countHourLateByYearAEmployee(Employee employee, int year) {
    	double count = 0;
		ArrayList<LogTimekeepingOfficer> LogTimekeepingOfficers = GetTimekeepingOfficer.getInstance().getTimekeepingsByEmployeeID(employee.getId());
		for (LogTimekeepingOfficer LogTimekeepingOfficer : LogTimekeepingOfficers) {
			if(getYearFromDate(LogTimekeepingOfficer.getDate()) == year) {
				count = count + ((convertToDouble(LogTimekeepingOfficer.getTime_in().toString()) - 7.5) > 0 ? (convertToDouble(LogTimekeepingOfficer.getTime_in().toString()) - 7.5) : 0);
			}				
		}
		return count;
    }
    
    public double countHourEarlyByMonthAEmployee(Employee employee, int month, int year) {
    	double count = 0;
		ArrayList<LogTimekeepingOfficer> LogTimekeepingOfficers = GetTimekeepingOfficer.getInstance().getTimekeepingsByEmployeeID(employee.getId());
		for (LogTimekeepingOfficer LogTimekeepingOfficer : LogTimekeepingOfficers) {
			if(getMonthFromDate(LogTimekeepingOfficer.getDate()) == month && getYearFromDate(LogTimekeepingOfficer.getDate()) == year) {
				count = count + ((17.5 - convertToDouble(LogTimekeepingOfficer.getTime_out().toString())) > 0 ? (17.5 - convertToDouble(LogTimekeepingOfficer.getTime_out().toString())) : 0);
			}				
		}
		return count;
    }
    
    public double countHourEarlyByQuarterAEmployee(Employee employee, int quarter, int year) {
    	double count = 0;
		ArrayList<LogTimekeepingOfficer> LogTimekeepingOfficers = GetTimekeepingOfficer.getInstance().getTimekeepingsByEmployeeID(employee.getId());
		for (LogTimekeepingOfficer LogTimekeepingOfficer : LogTimekeepingOfficers) {
			if(getQuarterFromDate(LogTimekeepingOfficer.getDate()) == quarter && getYearFromDate(LogTimekeepingOfficer.getDate()) == year) {
				count = count + ((17.5 - convertToDouble(LogTimekeepingOfficer.getTime_out().toString())) > 0 ? (17.5 - convertToDouble(LogTimekeepingOfficer.getTime_out().toString())) : 0);
			}				
		}
		return count;
    }
    
    public double countHourEarlyByYearAEmployee(Employee employee, int year) {
    	double count = 0;
		ArrayList<LogTimekeepingOfficer> LogTimekeepingOfficers = GetTimekeepingOfficer.getInstance().getTimekeepingsByEmployeeID(employee.getId());
		for (LogTimekeepingOfficer LogTimekeepingOfficer : LogTimekeepingOfficers) {
			if(getYearFromDate(LogTimekeepingOfficer.getDate()) == year) {
				count = count + ((17.5 - convertToDouble(LogTimekeepingOfficer.getTime_out().toString())) > 0 ? (17.5 - convertToDouble(LogTimekeepingOfficer.getTime_out().toString())) : 0);
			}				
		}
		return count;
    }
    
	public double countHourLateByMonth(int month, int year) {
		double count = 0;
		for (Employee employee : employees) {
			count = count + countHourLateByMonthAEmployee(employee, month, year);
		}
		return roundouble(count);
	}
	
	public double countHourEarlyByMonth(int month, int year) {
		double count = 0;
		for (Employee employee : employees) {
			count = count + countHourEarlyByMonthAEmployee(employee, month, year);
		}
		return roundouble(count);
	}
	
	public double countHourLateByQuarter(int quarter, int year) {
		double count = 0;
		for (Employee employee : employees) {
			count = count + countHourLateByQuarterAEmployee(employee, quarter, year);
		}
		return roundouble(count);
	}
	
	public double countHourEarlyByQuarter(int quarter, int year) {
		double count = 0;
		for (Employee employee : employees) {
			count = count + countHourEarlyByQuarterAEmployee(employee, quarter, year);
		}
		return roundouble(count);
	}
	
	public double countHourLateByYear(int year) {
		double count = 0;
		for (Employee employee : employees) {
			count = count + countHourLateByYearAEmployee(employee, year);
		}
		return roundouble(count);
	}
	
	public double countHourEarlyByYear(int year) {
		double count = 0;
		for (Employee employee : employees) {
			count = count + countHourEarlyByYearAEmployee(employee, year);
		}
		return roundouble(count);
	}

	@Override
	public int countGoodMonth(int month, int year) {
		int count = 0;
		for (Employee employee : employees) {
			if(employee.getDepartment().equals("Factory") == false)
			if(this.countHourEarlyByMonthAEmployee(employee, month, year) + this.countHourLateByMonthAEmployee(employee, month, year) < 5) {
				count = count + 1;
			}
		}
		return count;
	}

	@Override
	public int countGoodQuarter(int quarter, int year) {
		int count = 0;
		for (Employee employee : employees) {
			if(employee.getDepartment().equals("Factory") == false)
			if(this.countHourEarlyByQuarterAEmployee(employee, quarter, year) + this.countHourLateByQuarterAEmployee(employee, quarter, year) < 15) {
				count = count + 1;
			}
		}
		return count;
	}

	@Override
	public int countGoodYear(int year) {
		int count = 0;
		for (Employee employee : employees) {
			if(employee.getDepartment().equals("Factory") == false)
			if(this.countHourEarlyByYearAEmployee(employee, year) + this.countHourLateByYearAEmployee(employee, year) < 60) {
				count = count + 1;
			}
		}
		return count;
	}

	@Override
	public int countBadMonth(int month, int year) {
		int count = 0;
		for (Employee employee : employees) {
			if(employee.getDepartment().equals("Factory") == false)
			if(this.countHourEarlyByMonthAEmployee(employee, month, year) + this.countHourLateByMonthAEmployee(employee, month, year) >= 5) {
				count = count + 1;
			}
		}
		return count;
	}

	@Override
	public int countBadQuarter(int quarter, int year) {
		int count = 0;
		for (Employee employee : employees) {
			if(employee.getDepartment().equals("Factory") == false)
			if(this.countHourEarlyByQuarterAEmployee(employee, quarter, year) + this.countHourLateByQuarterAEmployee(employee, quarter, year) >= 15) {
				count = count + 1;
			}
		}
		return count;
	}

	@Override
	public int countBadYear(int year) {
		int count = 0;
		for (Employee employee : employees) {
			if(employee.getDepartment().equals("Factory") == false)
			if(this.countHourEarlyByYearAEmployee(employee, year) + this.countHourLateByYearAEmployee(employee, year) >= 60) {
				count = count + 1;
			}
		}
		return count;
	}
	public static void main(String[] args) {
		GeneralInformationOfficer generalInfomation = new GeneralInformationOfficer();
		System.out.println(employees);
		System.out.println(generalInfomation.countHourEarlyByMonthAEmployee(employees.get(2), 6, 2023) + generalInfomation.countHourLateByMonthAEmployee(employees.get(2), 6, 2020));
		System.out.println(generalInfomation.countBadMonth(6, 2023));
	}
}
