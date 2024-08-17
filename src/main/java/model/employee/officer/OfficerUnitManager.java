package model.employee.officer;

import model.employee.Role;

import java.sql.Date;

public class OfficerUnitManager extends Officer{
  
	public OfficerUnitManager(String id, String name, String identifier, Date birthday, String address, String gender, String phone,
							  String department, String unit_id, String password, int status) {
		super(id, name, identifier, birthday, address, gender, phone, department, unit_id, password,status);
		this.role_id = Role.OfficerUnitManager.getId();
	}
}
