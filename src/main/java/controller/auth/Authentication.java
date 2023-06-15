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

		if (Authentication.isWorker()){
			authentication = new Worker(authentication.getId(), authentication.getName(), authentication.getUnit_id(), authentication.getPassword());
		}else if(Authentication.isOfficer()){
			authentication = new Officer(authentication.getId(), authentication.getName(), authentication.getDepartment(), authentication.getUnit_id(), authentication.getPassword());
		}else if(Authentication.isWorkerUnitManager()){
			authentication = new WorkerUnitManager(authentication.getId(), authentication.getName(), authentication.getUnit_id(), authentication.getPassword());
		}else if (Authentication.isOfficerUnitManager()){
			authentication = new Officer(authentication.getId(), authentication.getName(), authentication.getDepartment(), authentication.getUnit_id(), authentication.getPassword());
		}else if (Authentication.isHRManager()){
			authentication = new HRManager(authentication.getId(), authentication.getName(), authentication.getDepartment(), authentication.getUnit_id(), authentication.getPassword());
		}

	}

	public static boolean isWorker() {
		if (authentication.getRole_id() == 1){
			return true;
		}else return false;
	}

	public static boolean isOfficer() {
		if (authentication.getRole_id() == 2){
			return true;
		}else return false;
	}

	public static boolean isWorkerUnitManager() {
		if (authentication.getRole_id() == 3){
			return true;
		}else return false;
	}

	public static boolean isOfficerUnitManager() {
		if (authentication.getRole_id() == 4){
			return true;
		}else return false;
	}

	public static boolean isHRManager() {
		if (authentication.getRole_id() == 5){
			return true;
		}else return false;
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
