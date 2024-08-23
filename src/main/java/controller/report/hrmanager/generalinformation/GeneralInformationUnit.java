package controller.report.hrmanager.generalinformation;

import java.text.DecimalFormat;
import java.util.ArrayList;
import model.employee.Employee;
import model.employee.Role;

public abstract class GeneralInformationUnit {
	
	protected static String unit_id;
	
	protected static ArrayList<Employee> employees;
	
	public static void setUnitId(String unit_id){
		GeneralInformationUnit.unit_id = unit_id;
		employees = GeneralInformation.getEmployeesByUnit(unit_id);
	}
	
	public static String getDepartment() {
		String departmentName = "";
		for (Employee employee : employees) {
			if(employee.getUnit_id().equals(unit_id)) {
				departmentName = employee.getDepartment();
			}
		}
		return departmentName;
	}
	
	public static int countNumberWorkerUnit() {
		int count = 0;
		for (Employee employee : employees) {
			int role_id = employee.getRole_id();
			if (role_id == Role.Worker.getId() || role_id == Role.WorkerUnitManager.getId()) {
				if(employee.getUnit_id().equals(unit_id)) {
					count = count + 1;
				}
			}
		}
		return count;
	}
	public static int countNumberOfficerUnit() {
		int count = 0;
		for (Employee employee : employees) {
			int role_id = employee.getRole_id();
			if (role_id == Role.Officer.getId() || role_id == Role.OfficerUnitManager.getId() || role_id == Role.HRManager.getId()) {
				if(employee.getUnit_id().equals(unit_id)) {
					count = count + 1;
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
   