package model.employee.worker;

import model.employee.Employee;

public class Worker extends Employee {
	public Worker(String id, String name, String unit_id, String password,int status){
		super(id, name, unit_id, password,status);
		this.role_id = 1;
		this.department = "Factory";
	}

	@Override
	public String toString() {
		return "Worker{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", department='" + department + '\'' +
				", unit_id='" + unit_id + '\'' +
				", password='" + password + '\'' +
				", role_id=" + role_id +
				", status=" + status +
				'}';
	}
}
