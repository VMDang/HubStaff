package controller.report.hrmanager.generalinformation;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import database.TimekeepingOfficerDAO;
import model.employee.Employee;
import model.employee.Role;
import model.logtimekeeping.LogTimekeepingOfficer;
import utility.TimeUtility;

public class GeneralInformationOfficer extends GeneralInformation {
	
    public double countHourLateByMonthAEmployee(Employee employee, int month, int year) {
    	double count = 0;
		ArrayList<LogTimekeepingOfficer> LogTimekeepingOfficers = TimekeepingOfficerDAO.getInstance().getByEmployeeID(employee.getId());
		for (LogTimekeepingOfficer LogTimekeepingOfficer : LogTimekeepingOfficers) {
			if(TimeUtility.getMonthFromDate(LogTimekeepingOfficer.getDate()) == month && TimeUtility.getYearFromDate(LogTimekeepingOfficer.getDate()) == year) {
				count = count + ((TimeUtility.convertToDouble(LogTimekeepingOfficer.getTime_in().toString()) - 7.5) > 0 ? (TimeUtility.convertToDouble(LogTimekeepingOfficer.getTime_in().toString()) - 7.5) : 0);
			}				
		}
		return count;
    }
    
    public double countHourLateByQuarterAEmployee(Employee employee, int quarter, int year) {
    	double count = 0;
		ArrayList<LogTimekeepingOfficer> LogTimekeepingOfficers = TimekeepingOfficerDAO.getInstance().getByEmployeeID(employee.getId());
		for (LogTimekeepingOfficer LogTimekeepingOfficer : LogTimekeepingOfficers) {
			if(TimeUtility.getQuarterFromDate(LogTimekeepingOfficer.getDate()) == quarter && TimeUtility.getYearFromDate(LogTimekeepingOfficer.getDate()) == year) {
				count = count + ((TimeUtility.convertToDouble(LogTimekeepingOfficer.getTime_in().toString()) - 7.5) > 0 ? (TimeUtility.convertToDouble(LogTimekeepingOfficer.getTime_in().toString()) - 7.5) : 0);
			}				
		}
		return count;
    }
    
    public double countHourLateByYearAEmployee(Employee employee, int year) {
    	double count = 0;
		ArrayList<LogTimekeepingOfficer> LogTimekeepingOfficers =TimekeepingOfficerDAO.getInstance().getByEmployeeID(employee.getId());
		for (LogTimekeepingOfficer LogTimekeepingOfficer : LogTimekeepingOfficers) {
			if(TimeUtility.getYearFromDate(LogTimekeepingOfficer.getDate()) == year) {
				count = count + ((TimeUtility.convertToDouble(LogTimekeepingOfficer.getTime_in().toString()) - 7.5) > 0 ? (TimeUtility.convertToDouble(LogTimekeepingOfficer.getTime_in().toString()) - 7.5) : 0);
			}				
		}
		return count;
    }
    
    public double countHourEarlyByMonthAEmployee(Employee employee, int month, int year) {
    	double count = 0;
		ArrayList<LogTimekeepingOfficer> LogTimekeepingOfficers = TimekeepingOfficerDAO.getInstance().getByEmployeeID(employee.getId());
		for (LogTimekeepingOfficer LogTimekeepingOfficer : LogTimekeepingOfficers) {
			if(TimeUtility.getMonthFromDate(LogTimekeepingOfficer.getDate()) == month && TimeUtility.getYearFromDate(LogTimekeepingOfficer.getDate()) == year) {
				count = count + ((17.5 - TimeUtility.convertToDouble(LogTimekeepingOfficer.getTime_out().toString())) > 0 ? (17.5 - TimeUtility.convertToDouble(LogTimekeepingOfficer.getTime_out().toString())) : 0);
			}				
		}
		return count;
    }
    
    public double countHourEarlyByQuarterAEmployee(Employee employee, int quarter, int year) {
    	double count = 0;
		ArrayList<LogTimekeepingOfficer> LogTimekeepingOfficers = TimekeepingOfficerDAO.getInstance().getByEmployeeID(employee.getId());
		for (LogTimekeepingOfficer LogTimekeepingOfficer : LogTimekeepingOfficers) {
			if(TimeUtility.getQuarterFromDate(LogTimekeepingOfficer.getDate()) == quarter && TimeUtility.getYearFromDate(LogTimekeepingOfficer.getDate()) == year) {
				count = count + ((17.5 - TimeUtility.convertToDouble(LogTimekeepingOfficer.getTime_out().toString())) > 0 ? (17.5 - TimeUtility.convertToDouble(LogTimekeepingOfficer.getTime_out().toString())) : 0);
			}				
		}
		return count;
    }
    
    public double countHourEarlyByYearAEmployee(Employee employee, int year) {
    	double count = 0;
		ArrayList<LogTimekeepingOfficer> LogTimekeepingOfficers = TimekeepingOfficerDAO.getInstance().getByEmployeeID(employee.getId());
		for (LogTimekeepingOfficer LogTimekeepingOfficer : LogTimekeepingOfficers) {
			if(TimeUtility.getYearFromDate(LogTimekeepingOfficer.getDate()) == year) {
				count = count + ((17.5 - TimeUtility.convertToDouble(LogTimekeepingOfficer.getTime_out().toString())) > 0 ? (17.5 - TimeUtility.convertToDouble(LogTimekeepingOfficer.getTime_out().toString())) : 0);
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
			int role_id = employee.getRole_id();
			if(role_id == Role.Officer.getId() || role_id == Role.OfficerUnitManager.getId() || role_id == Role.HRManager.getId()) {
				if(this.countHourEarlyByMonthAEmployee(employee, month, year) + this.countHourLateByMonthAEmployee(employee, month, year) < 5) {
					count = count + 1;
				}

			}
		}
		return count;
	}

	@Override
	public int countGoodQuarter(int quarter, int year) {
		int count = 0;
		for (Employee employee : employees) {
			int role_id = employee.getRole_id();
			if(role_id == Role.Officer.getId() || role_id == Role.OfficerUnitManager.getId() || role_id == Role.HRManager.getId()) {
				if(this.countHourEarlyByQuarterAEmployee(employee, quarter, year) + this.countHourLateByQuarterAEmployee(employee, quarter, year) < 15) {
					count = count + 1;
				}
			}
		}
		return count;
	}

	@Override
	public int countGoodYear(int year) {
		int count = 0;
		for (Employee employee : employees) {
			int role_id = employee.getRole_id();
			if(role_id == Role.Officer.getId() || role_id == Role.OfficerUnitManager.getId() || role_id == Role.HRManager.getId()) {
				if(this.countHourEarlyByYearAEmployee(employee, year) + this.countHourLateByYearAEmployee(employee, year) < 60) {
					count = count + 1;
				}
			}
		}
		return count;
	}

	@Override
	public int countBadMonth(int month, int year) {
		int count = 0;
		for (Employee employee : employees) {
			int role_id = employee.getRole_id();
			if(role_id == Role.Officer.getId() || role_id == Role.OfficerUnitManager.getId() || role_id == Role.HRManager.getId()) {
				if(this.countHourEarlyByMonthAEmployee(employee, month, year) + this.countHourLateByMonthAEmployee(employee, month, year) >= 5) {
					count = count + 1;
				}
			}
		}
		return count;
	}

	@Override
	public int countBadQuarter(int quarter, int year) {
		int count = 0;
		for (Employee employee : employees) {
			int role_id = employee.getRole_id();
			if(role_id == Role.Officer.getId() || role_id == Role.OfficerUnitManager.getId() || role_id == Role.HRManager.getId()) {
				if(this.countHourEarlyByQuarterAEmployee(employee, quarter, year) + this.countHourLateByQuarterAEmployee(employee, quarter, year) >= 15) {
					count = count + 1;
				}
			}
		}
		return count;
	}

	@Override
	public int countBadYear(int year) {
		int count = 0;
		for (Employee employee : employees) {
			int role_id = employee.getRole_id();
			if(role_id == Role.Officer.getId() || role_id == Role.OfficerUnitManager.getId() || role_id == Role.HRManager.getId()) {
				if(this.countHourEarlyByYearAEmployee(employee, year) + this.countHourLateByYearAEmployee(employee, year) >= 60) {
					count = count + 1;
				}
			}
		}
		return count;
	}
}
