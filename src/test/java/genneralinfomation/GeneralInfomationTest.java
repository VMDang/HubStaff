package genneralinfomation;

import java.util.ArrayList;

import org.junit.Test;

import controller.report.hrmanager.generalinformation.GeneralInformation;
import controller.report.hrmanager.generalinformation.GeneralInformationUnit;
import junit.framework.TestCase;
import model.employee.Employee;

public class GeneralInfomationTest extends TestCase {

	public void testGetEmployeeByUnitId_NotExistUnitId_Blackbox() {
		String unit_id = null;
		
		ArrayList<Employee> listEmployee = GeneralInformation.getEmployeesByUnit(unit_id);
		
		assertTrue(listEmployee == null || listEmployee.isEmpty());
	}
	
	public void testGetEmployeeByUnitId_ExsistUnitId_Blackbox() {
		String unit_id = "un1";
		
		ArrayList<Employee> listEmployee = GeneralInformation.getEmployeesByUnit(unit_id);
		
		assertTrue(listEmployee.size() > 0);
	}
	
	public void testGetEmployeeByUnitId_NotExistEmployee_Blackbox() {
		String unit_id = "un100";
		
		ArrayList<Employee> listEmployee = GeneralInformation.getEmployeesByUnit(unit_id);
		
		assertTrue(listEmployee.size() == 0);	
	}
	
	public void testGetEmployeeByUnitId_NotExistUnitId_Whitebox() {
		String unit_id = null;
		
		ArrayList<Employee> listEmployee = GeneralInformation.getEmployeesByUnit(unit_id);
		
		assertTrue(listEmployee.isEmpty());
	}
	
	public void testGetEmployeeByUnitId_ExsistUnitId_Whitebox() {
		String unit_id = "un1";
		
		ArrayList<Employee> listEmployee = GeneralInformation.getEmployeesByUnit(unit_id);
		
		assertTrue(listEmployee.size() > 0);
		
		for (Employee employee : listEmployee) {
			
			assertTrue(employee.getUnit_id().equals(unit_id));
			
		}
	}
	
	public void testGetEmployeeByUnitId_NotExistEmployee_Whitebox() {
		String unit_id = "un100";
		
		ArrayList<Employee> listEmployee = GeneralInformation.getEmployeesByUnit(unit_id);
		
		assertTrue(listEmployee.size() == 0);	
	}
}
