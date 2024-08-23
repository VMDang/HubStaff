package controller.report.hrmanager.generalinformation;

import java.util.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import database.TimekeepingWorkerDAO;
import model.employee.Employee;
import model.employee.Role;
import model.logtimekeeping.LogTimekeepingWorker;
import utility.TimeUtility;

public class GeneralInformationWorker extends GeneralInformation {
	
    public double countHourLateByMonthAEmployee(Employee employee, int month, int year) {
    	double count = 0;
		ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = TimekeepingWorkerDAO.getInstance().getByEmployeeID(employee.getId());
		for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
			if(TimeUtility.getMonthFromDate(logTimekeepingWorker.getDate()) == month &&
					TimeUtility.getYearFromDate(logTimekeepingWorker.getDate()) == year) {
				count = count + ((TimeUtility.convertToDouble(logTimekeepingWorker.getTime_in().toString()) - 7.5) > 0 ? (TimeUtility.convertToDouble(logTimekeepingWorker.getTime_in().toString()) - 7.5) : 0);
			}				
		}
		return count;
    }
    
    public double countHourLateByQuarterAEmployee(Employee employee, int quarter, int year) {
    	double count = 0;
		ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = TimekeepingWorkerDAO.getInstance().getByEmployeeID(employee.getId());
		for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
			if(TimeUtility.getQuarterFromDate(logTimekeepingWorker.getDate()) == quarter && TimeUtility.getYearFromDate(logTimekeepingWorker.getDate()) == year) {
				count = count + ((TimeUtility.convertToDouble(logTimekeepingWorker.getTime_in().toString()) - 7.5) > 0 ? (TimeUtility.convertToDouble(logTimekeepingWorker.getTime_in().toString()) - 7.5) : 0);
			}				
		}
		return count;
    }
    
    public double countHourLateByYearAEmployee(Employee employee, int year) {
    	double count = 0;
		ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = TimekeepingWorkerDAO.getInstance().getByEmployeeID(employee.getId());
		for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
			if(TimeUtility.getYearFromDate(logTimekeepingWorker.getDate()) == year) {
				count = count + ((TimeUtility.convertToDouble(logTimekeepingWorker.getTime_in().toString()) - 7.5) > 0 ? (TimeUtility.convertToDouble(logTimekeepingWorker.getTime_in().toString()) - 7.5) : 0);
			}				
		}
		return count;
    }
    
    public double countHourEarlyByMonthAEmployee(Employee employee, int month, int year) {
    	double count = 0;
		ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = TimekeepingWorkerDAO.getInstance().getByEmployeeID(employee.getId());
		for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
			if(TimeUtility.getMonthFromDate(logTimekeepingWorker.getDate()) == month && TimeUtility.getYearFromDate(logTimekeepingWorker.getDate()) == year) {
				count = count + ((17.5 - TimeUtility.convertToDouble(logTimekeepingWorker.getTime_out().toString())) > 0 ? (17.5 - TimeUtility.convertToDouble(logTimekeepingWorker.getTime_out().toString())) : 0);
			}				
		}
		return count;
    }
    
    public double countHourEarlyByQuarterAEmployee(Employee employee, int quarter, int year) {
    	double count = 0;
		ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = TimekeepingWorkerDAO.getInstance().getByEmployeeID(employee.getId());
		for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
			if(TimeUtility.getQuarterFromDate(logTimekeepingWorker.getDate()) == quarter && TimeUtility.getYearFromDate(logTimekeepingWorker.getDate()) == year) {
				count = count + ((17.5 - TimeUtility.convertToDouble(logTimekeepingWorker.getTime_out().toString())) > 0 ? (17.5 - TimeUtility.convertToDouble(logTimekeepingWorker.getTime_out().toString())) : 0);
			}				
		}
		return count;
    }
    
    public double countHourEarlyByYearAEmployee(Employee employee, int year) {
    	double count = 0;
		ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = TimekeepingWorkerDAO.getInstance().getByEmployeeID(employee.getId());
		for (LogTimekeepingWorker logTimekeepingWorker : logTimekeepingWorkers) {
			if(TimeUtility.getYearFromDate(logTimekeepingWorker.getDate()) == year) {
				count = count + ((17.5 - TimeUtility.convertToDouble(logTimekeepingWorker.getTime_out().toString())) > 0 ? (17.5 - TimeUtility.convertToDouble(logTimekeepingWorker.getTime_out().toString())) : 0);
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
			int roleId = employee.getRole_id();
			if(roleId == Role.Worker.getId() || roleId == Role.WorkerUnitManager.getId()) {
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
			int roleId = employee.getRole_id();
			if(roleId == Role.Worker.getId() || roleId == Role.WorkerUnitManager.getId()) {
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
			int roleId = employee.getRole_id();
			if(roleId == Role.Worker.getId() || roleId == Role.WorkerUnitManager.getId()) {
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
			int roleId = employee.getRole_id();
			if(roleId == Role.Worker.getId() || roleId == Role.WorkerUnitManager.getId()) {
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
			int roleId = employee.getRole_id();
			if(roleId == Role.Worker.getId() || roleId == Role.WorkerUnitManager.getId()) {
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
			int roleId = employee.getRole_id();
			if(roleId == Role.Worker.getId() || roleId == Role.WorkerUnitManager.getId()) {
				if(this.countHourEarlyByYearAEmployee(employee, year) + this.countHourLateByYearAEmployee(employee, year) >= 60) {
					count = count + 1;
				}
			}
		}
		return count;
	}
}
