package controller.auth;


import model.employee.Employee;
import model.employee.HRManager;
import model.employee.officer.Officer;
import model.employee.officer.OfficerUnitManager;
import model.employee.worker.Worker;
import model.employee.worker.WorkerUnitManager;

public class Authentication {
	private static Authentication instance;
	private Employee authentication;

	private Authentication() {

	}

	public static Authentication getInstance(){
		if (instance == null) {
			instance = new Authentication();
		}

		return instance;
	}

	public void setAuthentication(Employee auth) {
		if (authentication == null){
			authentication = auth;
		}else return;

		if (authentication.getRole_id() == 1){
			authentication = new Worker(authentication.getId(), authentication.getName(), authentication.getUnit_id(), authentication.getPassword(),authentication.getStatus());
		}else if(authentication.getRole_id() == 2){
			authentication = new Officer(authentication.getId(), authentication.getName(), authentication.getDepartment(), authentication.getUnit_id(), authentication.getPassword(),authentication.getStatus());
		}else if(authentication.getRole_id() == 3){
			authentication = new WorkerUnitManager(authentication.getId(), authentication.getName(), authentication.getUnit_id(), authentication.getPassword(),authentication.getStatus());
		}else if (authentication.getRole_id() == 4){
			authentication = new OfficerUnitManager(authentication.getId(), authentication.getName(), authentication.getDepartment(), authentication.getUnit_id(), authentication.getPassword(),authentication.getStatus());
		}else if (authentication.getRole_id() == 5){
			authentication = new HRManager(authentication.getId(), authentication.getName(), authentication.getDepartment(), authentication.getUnit_id(), authentication.getPassword(),authentication.getStatus());
		}
	}

	public Employee getAuthentication(){
		return authentication;
	}

	public void destroyAuthencation(){
		authentication = null;
	}

//	public static void main(String[] args) {
//		Employee employee = new Employee("a", "b", "c", "d", "1", 5);	// LoginController
//		Authentication.setAuthentication(employee);																// LoginController
//
//		if (getAuth() instanceof HRManager){
//			System.out.println("done");
//			System.out.println(authentication.getDepartment());
//		}
//	}
}
