package model.employee.worker;

public class WorkerUnitManager extends Worker{

	public WorkerUnitManager(String id, String name, String unit_id, String password,int status) {
		super(id, name, unit_id, password,status);
		this.role_id = 3;
	}
}
