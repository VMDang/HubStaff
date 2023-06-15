package model.employee.worker;

import model.employee.Employee;

public class Worker extends Employee {
	public Worker(String id, String name, String department, String unit_id, String password, int role_id) {
		super(id, name, department, unit_id, password, role_id);
	}
}
