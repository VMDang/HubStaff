package controller.report.hrmanager.generalinformation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import dbtimekeeping.GetTimekeepingOfficer;
import dbtimekeeping.GetTimekeepingWorker;
import hrsystem.GetAllEmployees;
import model.employee.Employee;
import model.logtimekeeping.LogTimekeepingOfficer;

public class GeneralInformationUnit {
	private static ArrayList<Employee> employees = GetAllEmployees.getInstance().getAllEmployees();
	
	public static Set<String> getListUnit(){
		Set<String> listUnit = new HashSet<>();
		for (Employee employee : employees) {
			listUnit.add(employee.getUnit_id());
		}
		return listUnit;
	}
	
	public static ArrayList<Employee> getEmployeesByUnit(String unit_id) {
		ArrayList<Employee> listEmployeesUnit = new ArrayList<>();
		for (Employee employee : employees) {
			if(employee.getUnit_id().equals(unit_id)) {
				listEmployeesUnit.add(employee);
			}
		}
		return listEmployeesUnit;
	}
}
   