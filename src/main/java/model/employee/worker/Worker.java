package model.employee.worker;

import model.employee.Employee;
import model.employee.Role;

import java.sql.Date;

public class Worker extends Employee {
	public Worker(String id, String name, String identifier, Date birthday, String address, String gender, String phone,
				  String department, String unit_id, String password, int status){
		super(id, name, identifier, birthday, address, gender, phone, department, unit_id, password, status);
		this.role_id = Role.Worker.getId();
		this.department = "Nhà máy";
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
