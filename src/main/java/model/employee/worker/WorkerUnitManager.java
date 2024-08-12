package model.employee.worker;

import model.employee.Role;

public class WorkerUnitManager extends Worker{

	public WorkerUnitManager(String id, String name, String unit_id, String password,int status) {
		super(id, name, unit_id, password,status);
		this.role_id = Role.WorkerUnitManager.getId();
	}
}
