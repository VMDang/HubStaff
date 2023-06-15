package model.employee.worker;

public class WorkerUnitManager extends Worker{

	public WorkerUnitManager(String id, String name, String unit_id, String password) {
		super(id, name, unit_id, password);
		this.role_id = 3;
	}
}
