package controller.report.hrmanager.generalinformation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import hrsystem.GetAllEmployees;
import model.employee.Employee;

public abstract class GeneralInformation {

	protected static ArrayList<Employee> employees = GetAllEmployees.getInstance().getAllEmployees();
	
	public static ArrayList<Employee> getEmployeesByUnit(String unit_id) {
		ArrayList<Employee> listEmployeesUnit = new ArrayList<>();
		for (Employee employee : employees) {
			if(employee.getUnit_id().equals(unit_id)) {
				listEmployeesUnit.add(employee);
			}
		}
		return listEmployeesUnit;
	}

	public static Set<String> getListUnit(){
		Set<String> listUnit = new HashSet<>();
		for (Employee employee : employees) {
			listUnit.add(employee.getUnit_id());
		}
		return listUnit;
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
			if(employee.getDepartment().equals("Factory")) {
				count = count + 1;
			}
		}
		return count;
	}
	
	public static int countNumberOfficer() {
		int count = employees.size() - countNumberWorker();
		return count;
	}
	
	public static void main(String[] args) {
		System.out.println(employees);
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
