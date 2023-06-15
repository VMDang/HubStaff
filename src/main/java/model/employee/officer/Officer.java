package model.employee.officer;

import model.employee.Employee;

public class Officer extends Employee {
	public Officer(String id, String name, String department, String unit_id, String password, int role_id) {
		super(id, name, department, unit_id, password, role_id);
	}
}
