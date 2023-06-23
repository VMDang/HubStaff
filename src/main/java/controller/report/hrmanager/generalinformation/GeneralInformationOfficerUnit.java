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
	
	public static double countHourEarlyByMonthUnit(String unit_id, int month, int year) {
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
	public static double countHourLateByMonthUnit(String unit_id, int month, int year) {
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
	public static double countHourEarlyByQuarterUnit(String unit_id, int quarter, int year) {
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
	public static double countHourLateByQuarterUnit(String unit_id, int quarter, int year) {
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
	public static double countHourEarlyByYearUnit(String unit_id, int year) {
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
	public static double countHourLateByYearUnit(String unit_id, int year) {
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
}
