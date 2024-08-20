package model.employee;

import java.sql.Date;

public class HRManager extends Employee{
  
	public HRManager(String id, String name, String identifier, Date birthday, String address, String gender, String phone,
					 String department, String unit_id, String password, int status) {
		super(id, name, identifier, birthday, address, gender, phone, department, unit_id, password, status);
		this.role_id = Role.HRManager.getId();
	}

}
