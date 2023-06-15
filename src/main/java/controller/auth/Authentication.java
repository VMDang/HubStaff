package controller.auth;


import model.employee.Employee;
import model.employee.HRManager;
import model.employee.officer.Officer;
import model.employee.officer.OfficerUnitManager;
import model.employee.worker.Worker;
import model.employee.worker.WorkerUnitManager;

public class Authentication {
	public static Employee authentication;

	public static void setAuthentication(Employee auth) {
		Authentication.authentication = auth;

		if (authentication.getRole_id() == 1){
			authentication = new Worker(authentication.getId(), authentication.getName(), authentication.getUnit_id(), authentication.getPassword());
		}else if(authentication.getRole_id() == 2){
			authentication = new Officer(authentication.getId(), authentication.getName(), authentication.getDepartment(), authentication.getUnit_id(), authentication.getPassword());
		}else if(authentication.getRole_id() == 3){
			authentication = new WorkerUnitManager(authentication.getId(), authentication.getName(), authentication.getUnit_id(), authentication.getPassword());
		}else if (authentication.getRole_id() == 4){
			authentication = new OfficerUnitManager(authentication.getId(), authentication.getName(), authentication.getDepartment(), authentication.getUnit_id(), authentication.getPassword());
		}else if (authentication.getRole_id() == 5){
			authentication = new HRManager(authentication.getId(), authentication.getName(), authentication.getDepartment(), authentication.getUnit_id(), authentication.getPassword());
		}

	}

	public static void main(String[] args) {
		Employee employee = new Employee("a", "b", "c", "d", "1", 1);	// LoginController
		Authentication.setAuthentication(employee);																// LoginController

		if (authentication instanceof Worker){
			System.out.println("done");
			System.out.println(authentication.getDepartment());
		}
	}
}
