package controller.report.hrmanager.generalinformation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dbtimekeeping.GetTimekeepingWorker;
import model.employee.Employee;
import model.logtimekeeping.LogTimekeepingWorker;

public class GeneralInformationUnitWorker {
	
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
	
	public static double countTimeShift1ByMonthUnit(String unit_id, int month, int year) {
		double count = 0;
		ArrayList<Employee> employees = GeneralInformationUnit.getEmployeesByUnit(unit_id);
		for (Employee employee : employees) {
			ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = GetTimekeepingWorker.getInstance().getTimekeepingsByEmployeeID(employee.getId());
			for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
				if(getMonthFromDate(logTimekeepingWorker.getDate()) == month && getYearFromDate(logTimekeepingWorker.getDate()) == year) {
					count = count + logTimekeepingWorker.getShift1();
				}				
			}
		}
		return count;
	}
	public static double countTimeShift2ByMonthUnit(String unit_id, int month, int year) {
		double count = 0;
		ArrayList<Employee> employees = GeneralInformationUnit.getEmployeesByUnit(unit_id);
		for (Employee employee : employees) {
			ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = GetTimekeepingWorker.getInstance().getTimekeepingsByEmployeeID(employee.getId());
			for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
				if(getMonthFromDate(logTimekeepingWorker.getDate()) == month && getYearFromDate(logTimekeepingWorker.getDate()) == year) {
					count = count + logTimekeepingWorker.getShift2();
				}				
			}
		}
		return count;
	}
	public static double countTimeShift3ByMonthUnit(String unit_id, int month, int year) {
		double count = 0;
		ArrayList<Employee> employees = GeneralInformationUnit.getEmployeesByUnit(unit_id);
		for (Employee employee : employees) {
			ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = GetTimekeepingWorker.getInstance().getTimekeepingsByEmployeeID(employee.getId());
			for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
				if(getMonthFromDate(logTimekeepingWorker.getDate()) == month && getYearFromDate(logTimekeepingWorker.getDate()) == year) {
					count = count + logTimekeepingWorker.getShift3();
				}				
			}
		}
		return count;
	}
	public static double countTimeShift1ByQuarterUnit(String unit_id, int quarter, int year) {
		double count = 0;
		ArrayList<Employee> employees = GeneralInformationUnit.getEmployeesByUnit(unit_id);
		for (Employee employee : employees) {
			ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = GetTimekeepingWorker.getInstance().getTimekeepingsByEmployeeID(employee.getId());
			for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
				if(getQuarterFromDate(logTimekeepingWorker.getDate()) == quarter && getYearFromDate(logTimekeepingWorker.getDate()) == year) {
					count = count + logTimekeepingWorker.getShift1();
				}
			}
		}
		return count;
	}
	public static double countTimeShift2ByQuarterUnit(String unit_id, int quarter, int year) {
		double count = 0;
		ArrayList<Employee> employees = GeneralInformationUnit.getEmployeesByUnit(unit_id);
		for (Employee employee : employees) {
			ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = GetTimekeepingWorker.getInstance().getTimekeepingsByEmployeeID(employee.getId());
			for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
				if(getQuarterFromDate(logTimekeepingWorker.getDate()) == quarter && getYearFromDate(logTimekeepingWorker.getDate()) == year) {
					count = count + logTimekeepingWorker.getShift2();
				}
			}
		}
		return count;
	}
	public static double countTimeShift3ByQuarterUnit(String unit_id, int quarter, int year) {
		double count = 0;
		ArrayList<Employee> employees = GeneralInformationUnit.getEmployeesByUnit(unit_id);
		for (Employee employee : employees) {
			ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = GetTimekeepingWorker.getInstance().getTimekeepingsByEmployeeID(employee.getId());
			for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
				if(getQuarterFromDate(logTimekeepingWorker.getDate()) == quarter && getYearFromDate(logTimekeepingWorker.getDate()) == year) {
					count = count + logTimekeepingWorker.getShift3();
				}
			}
		}
		return count;
	}
	public static double countTimeShift1ByYearUnit(String unit_id, int year) {
		double count = 0;
		ArrayList<Employee> employees = GeneralInformationUnit.getEmployeesByUnit(unit_id);
		for (Employee employee : employees) {
			ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = GetTimekeepingWorker.getInstance().getTimekeepingsByEmployeeID(employee.getId());
			for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
				if(getYearFromDate(logTimekeepingWorker.getDate()) == year) {
					count = count + logTimekeepingWorker.getShift1();
				}
			}
		}
		return count;
	}
	public static double countTimeShift2ByYearUnit(String unit_id, int year) {
		double count = 0;
		ArrayList<Employee> employees = GeneralInformationUnit.getEmployeesByUnit(unit_id);
		for (Employee employee : employees) {
			ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = GetTimekeepingWorker.getInstance().getTimekeepingsByEmployeeID(employee.getId());
			for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
				if(getYearFromDate(logTimekeepingWorker.getDate()) == year) {
					count = count + logTimekeepingWorker.getShift2();
				}
			}
		}
		return count;
	}
	public static double countTimeShift3ByYearUnit(String unit_id, int year) {
		double count = 0;
		ArrayList<Employee> employees = GeneralInformationUnit.getEmployeesByUnit(unit_id);
		for (Employee employee : employees) {
			ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = GetTimekeepingWorker.getInstance().getTimekeepingsByEmployeeID(employee.getId());
			for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
				if(getYearFromDate(logTimekeepingWorker.getDate()) == year) {
					count = count + logTimekeepingWorker.getShift3();
				}
			}
		}
		return count;
	}
}
