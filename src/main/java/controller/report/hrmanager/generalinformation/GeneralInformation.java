package controller.report.hrmanager.generalinformation;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import database.EmployeeDAO;
import model.employee.Employee;
import model.employee.Role;

public abstract class GeneralInformation {

	protected static ArrayList<Employee> employees = EmployeeDAO.getInstance().getAll();
	
	public static ArrayList<Employee> getEmployeesByUnit(String unit_id) {
		ArrayList<Employee> listEmployeesUnit = new ArrayList<>();
		for (Employee employee : employees) {
			if(employee.getUnit_id().equals(unit_id) && employee.getStatus() == 1) {
				listEmployeesUnit.add(employee);
			}
		}
		return listEmployeesUnit;
	}
	
	public static int countNumberDepartment() {
		int count = 0;
		Set<String> listDepartment = new HashSet<>();
		for (Employee employee : employees) {
			listDepartment.add(employee.getDepartment());
		}
		count = listDepartment.size();
		return count;
	}
	
	public static int countNumberWorker() {
		int count = 0;
		for (Employee employee : employees) {
			if (employee.getStatus() == 1) {
				if (employee.getRole_id() == Role.Worker.getId() || employee.getRole_id() == Role.WorkerUnitManager.getId()) {
					count++;
				}
			}
		}
		return count;
	}
	
	public static int countNumberOfficer() {
		int count = 0;
		for (Employee employee : employees) {
			if (employee.getStatus() == 1) {
				if (employee.getRole_id() == Role.Officer.getId() || employee.getRole_id() == Role.OfficerUnitManager.getId()
						|| employee.getRole_id() == Role.HRManager.getId()) {
					count++;
				}
			}
		}
		return count;
	}

	public static double roundouble(double db) {
		DecimalFormat df = new DecimalFormat("#.#");
		if(db <= 0) {
			return 0.0;
		}
		return Double.parseDouble(df.format(db));
	}
	
	public abstract int countGoodMonth(int month, int year);
	public abstract int countGoodQuarter(int quarter, int year);
	public abstract int countGoodYear(int year);
	public abstract int countBadMonth(int month, int year);
	public abstract int countBadQuarter(int quarter, int year);
	public abstract int countBadYear(int year);
	public abstract double countHourLateByMonth(int month, int year);
	public abstract double countHourLateByQuarter(int quarter, int year);
	public abstract double countHourLateByYear(int year);
	public abstract double countHourEarlyByMonth(int month, int year);
	public abstract double countHourEarlyByQuarter(int quarter, int year);
	public abstract double countHourEarlyByYear(int year);
}
