package model.employee.worker;

import model.employee.Employee;

public class Worker extends Employee {
	public Worker(String id, String name, String unit_id, String password){
		super(id, name, unit_id, password);
		this.role_id = 1;
		this.department = "Factory";
	}

}
