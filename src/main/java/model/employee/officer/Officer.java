package model.employee.officer;

import model.employee.Employee;

public class Officer extends Employee {
	public Officer(String id, String name,String department, String unit_id, String password,int status) {
		super(id, name,department, unit_id, password,status);
		this.role_id = 2;
	}

}
