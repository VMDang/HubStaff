package controller.auth;

import model.employee.Employee;

public class Authentication {
	public static Employee authentication;

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
}
