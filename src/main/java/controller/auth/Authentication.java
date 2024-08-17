package controller.auth;


import model.employee.Employee;
import model.employee.HRManager;
import model.employee.Role;
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

		if (authentication.getRole_id() == Role.Worker.getId()){
			authentication = new Worker(authentication.getId(), authentication.getName(), authentication.getIdentifier(), authentication.getBirthday(), authentication.getAddress(),
					authentication.getGender(), authentication.getPhone(), authentication.getDepartment(), authentication.getUnit_id(), authentication.getPassword(),authentication.getStatus());
		}else if(authentication.getRole_id() == Role.Officer.getId()){
			authentication = new Officer(authentication.getId(), authentication.getName(), authentication.getIdentifier(), authentication.getBirthday(), authentication.getAddress(),
					authentication.getGender(), authentication.getPhone(), authentication.getDepartment(), authentication.getUnit_id(), authentication.getPassword(), authentication.getStatus());
		}else if(authentication.getRole_id() == Role.WorkerUnitManager.getId()){
			authentication = new WorkerUnitManager(authentication.getId(), authentication.getName(), authentication.getIdentifier(), authentication.getBirthday(), authentication.getAddress(),
					authentication.getGender(), authentication.getPhone(), authentication.getDepartment(), authentication.getUnit_id(), authentication.getPassword(), authentication.getStatus());
		}else if (authentication.getRole_id() == Role.OfficerUnitManager.getId()){
			authentication = new OfficerUnitManager(authentication.getId(), authentication.getName(), authentication.getIdentifier(), authentication.getBirthday(), authentication.getAddress(),
					authentication.getGender(), authentication.getPhone(), authentication.getDepartment(), authentication.getUnit_id(), authentication.getPassword(),authentication.getStatus());
		}else if (authentication.getRole_id() == Role.HRManager.getId()){
			authentication = new HRManager(authentication.getId(), authentication.getName(), authentication.getIdentifier(), authentication.getBirthday(), authentication.getAddress(),
					authentication.getGender(), authentication.getPhone(), authentication.getDepartment(), authentication.getUnit_id(), authentication.getPassword(),authentication.getStatus());
		}
	}

	public Employee getAuthentication(){
		return authentication;
	}

	public void destroyAuthencation(){
		authentication = null;
	}
}
