package controller.report.hrmanager.generalinformation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dbtimekeeping.GetTimekeepingOfficer;
import model.employee.Employee;
import model.logtimekeeping.LogTimekeepingOfficer;

public class GeneralInformationOfficerUnit {
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
	
	public static double countHourEarlyByMonth(String unit_id, int month, int year) {
		double count = 0;
		ArrayList<Employee> employees = GeneralInformationUnit.getEmployeesByUnit(unit_id);
		for (Employee employee : employees) {
			ArrayList<LogTimekeepingOfficer> logTimekeepingOfficers = GetTimekeepingOfficer.getInstance().getTimekeepingsByEmployeeID(employee.getId());
			for (LogTimekeepingOfficer logTimekeepingOfficer : logTimekeepingOfficers) {
				if(getMonthFromDate(logTimekeepingOfficer.getDate()) == month && getYearFromDate(logTimekeepingOfficer.getDate()) == year) {
					count = count + logTimekeepingOfficer.getHour_early();
				}				
			}
		}
		return count;
	}
	public static double countHourLateByMonth(String unit_id, int month, int year) {
		double count = 0;
		ArrayList<Employee> employees = GeneralInformationUnit.getEmployeesByUnit(unit_id);
		for (Employee employee : employees) {
			ArrayList<LogTimekeepingOfficer> logTimekeepingOfficers = GetTimekeepingOfficer.getInstance().getTimekeepingsByEmployeeID(employee.getId());
			for (LogTimekeepingOfficer logTimekeepingOfficer : logTimekeepingOfficers) {
				if(getMonthFromDate(logTimekeepingOfficer.getDate()) == month && getYearFromDate(logTimekeepingOfficer.getDate()) == year) {
					count = count + logTimekeepingOfficer.getHour_late();
				}				
			}
		}
		return count;
	}
	public static double countHourEarlyByQuarter(String unit_id, int quarter, int year) {
		double count = 0;
		ArrayList<Employee> employees = GeneralInformationUnit.getEmployeesByUnit(unit_id);
		for (Employee employee : employees) {
			ArrayList<LogTimekeepingOfficer> logTimekeepingOfficers = GetTimekeepingOfficer.getInstance().getTimekeepingsByEmployeeID(employee.getId());
			for (LogTimekeepingOfficer logTimekeepingOfficer : logTimekeepingOfficers) {
				if(getQuarterFromDate(logTimekeepingOfficer.getDate()) == quarter && getYearFromDate(logTimekeepingOfficer.getDate()) == year) {
					count = count + logTimekeepingOfficer.getHour_early();
				}				
			}
		}
		return count;
	}
	public static double countHourLateByQuarter(String unit_id, int quarter, int year) {
		double count = 0;
		ArrayList<Employee> employees = GeneralInformationUnit.getEmployeesByUnit(unit_id);
		for (Employee employee : employees) {
			ArrayList<LogTimekeepingOfficer> logTimekeepingOfficers = GetTimekeepingOfficer.getInstance().getTimekeepingsByEmployeeID(employee.getId());
			for (LogTimekeepingOfficer logTimekeepingOfficer : logTimekeepingOfficers) {
				if(getQuarterFromDate(logTimekeepingOfficer.getDate()) == quarter && getYearFromDate(logTimekeepingOfficer.getDate()) == year) {
					count = count + logTimekeepingOfficer.getHour_late();
				}
			}
		}
		return count;
	}
	public static double countHourEarlyByYear(String unit_id, int year) {
		double count = 0;
		ArrayList<Employee> employees = GeneralInformationUnit.getEmployeesByUnit(unit_id);
		for (Employee employee : employees) {
			ArrayList<LogTimekeepingOfficer> logTimekeepingOfficers = GetTimekeepingOfficer.getInstance().getTimekeepingsByEmployeeID(employee.getId());
			for (LogTimekeepingOfficer logTimekeepingOfficer : logTimekeepingOfficers) {
				if(getYearFromDate(logTimekeepingOfficer.getDate()) == year) {
					count = count + logTimekeepingOfficer.getHour_early();
				}				
			}
		}
		return count;
	}
	public static double countHourLateByYear(String unit_id, int year) {
		double count = 0;
		ArrayList<Employee> employees = GeneralInformationUnit.getEmployeesByUnit(unit_id);
		for (Employee employee : employees) {
			ArrayList<LogTimekeepingOfficer> logTimekeepingOfficers = GetTimekeepingOfficer.getInstance().getTimekeepingsByEmployeeID(employee.getId());
			for (LogTimekeepingOfficer logTimekeepingOfficer : logTimekeepingOfficers) {
				if(getYearFromDate(logTimekeepingOfficer.getDate()) == year) {
					count = count + logTimekeepingOfficer.getHour_late();
				}				
			}
		}
		return count;
	}
	public static int countMorningByMonth(String unit_id, int month, int year) {
		int count = 0;
		ArrayList<Employee> employees = GeneralInformationUnit.getEmployeesByUnit(unit_id);
		for (Employee employee : employees) {
			ArrayList<LogTimekeepingOfficer> logTimekeepingOfficers = GetTimekeepingOfficer.getInstance().getTimekeepingsByEmployeeID(employee.getId());
			for (LogTimekeepingOfficer logTimekeepingOfficer : logTimekeepingOfficers) {
				if(getMonthFromDate(logTimekeepingOfficer.getDate()) == month && getYearFromDate(logTimekeepingOfficer.getDate()) == year) {
					count = count + (logTimekeepingOfficer.isMorning() ? 1 :0);
				}				
			}
		}
		return count;
	}
	public static int countAfternoonByMonth(String unit_id, int month, int year) {
		int count = 0;
		ArrayList<Employee> employees = GeneralInformationUnit.getEmployeesByUnit(unit_id);
		for (Employee employee : employees) {
			ArrayList<LogTimekeepingOfficer> logTimekeepingOfficers = GetTimekeepingOfficer.getInstance().getTimekeepingsByEmployeeID(employee.getId());
			for (LogTimekeepingOfficer logTimekeepingOfficer : logTimekeepingOfficers) {
				if(getMonthFromDate(logTimekeepingOfficer.getDate()) == month && getYearFromDate(logTimekeepingOfficer.getDate()) == year) {
					count = count + (logTimekeepingOfficer.isAfternoon() ? 1 :0);
				}				
			}
		}
		return count;
	}
	public static int countMorningByQuarter(String unit_id, int quarter, int year) {
		int count = 0;
		ArrayList<Employee> employees = GeneralInformationUnit.getEmployeesByUnit(unit_id);
		for (Employee employee : employees) {
			ArrayList<LogTimekeepingOfficer> logTimekeepingOfficers = GetTimekeepingOfficer.getInstance().getTimekeepingsByEmployeeID(employee.getId());
			for (LogTimekeepingOfficer logTimekeepingOfficer : logTimekeepingOfficers) {
				if(getQuarterFromDate(logTimekeepingOfficer.getDate()) == quarter && getYearFromDate(logTimekeepingOfficer.getDate()) == year) {
					count = count + (logTimekeepingOfficer.isMorning() ? 1 :0);
				}				
			}
		}
		return count;
	}
	public static int countAfternoonByQuarter(String unit_id, int quarter, int year) {
		int count = 0;
		ArrayList<Employee> employees = GeneralInformationUnit.getEmployeesByUnit(unit_id);
		for (Employee employee : employees) {
			ArrayList<LogTimekeepingOfficer> logTimekeepingOfficers = GetTimekeepingOfficer.getInstance().getTimekeepingsByEmployeeID(employee.getId());
			for (LogTimekeepingOfficer logTimekeepingOfficer : logTimekeepingOfficers) {
				if(getQuarterFromDate(logTimekeepingOfficer.getDate()) == quarter && getYearFromDate(logTimekeepingOfficer.getDate()) == year) {
					count = count + (logTimekeepingOfficer.isAfternoon() ? 1 :0);
				}				
			}
		}
		return count;
	}
	public static int countMorningByYear(String unit_id, int year) {
		int count = 0;
		ArrayList<Employee> employees = GeneralInformationUnit.getEmployeesByUnit(unit_id);
		for (Employee employee : employees) {
			ArrayList<LogTimekeepingOfficer> logTimekeepingOfficers = GetTimekeepingOfficer.getInstance().getTimekeepingsByEmployeeID(employee.getId());
			for (LogTimekeepingOfficer logTimekeepingOfficer : logTimekeepingOfficers) {
				if(getYearFromDate(logTimekeepingOfficer.getDate()) == year) {
					count = count + (logTimekeepingOfficer.isMorning() ? 1 :0);
				}				
			}
		}
		return count;
	}
	public static int countAfternoonByYear(String unit_id, int year) {
		int count = 0;
		ArrayList<Employee> employees = GeneralInformationUnit.getEmployeesByUnit(unit_id);
		for (Employee employee : employees) {
			ArrayList<LogTimekeepingOfficer> logTimekeepingOfficers = GetTimekeepingOfficer.getInstance().getTimekeepingsByEmployeeID(employee.getId());
			for (LogTimekeepingOfficer logTimekeepingOfficer : logTimekeepingOfficers) {
				if(getYearFromDate(logTimekeepingOfficer.getDate()) == year) {
					count = count + (logTimekeepingOfficer.isAfternoon() ? 1 :0);
				}				
			}
		}
		return count;
	}
}
