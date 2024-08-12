package model.employee.officer;

import model.employee.Role;

public class OfficerUnitManager extends Officer{
  
	public OfficerUnitManager(String id, String name, String department, String unit_id, String password, int status) {
		super(id, name, department, unit_id, password,status);
		this.role_id = Role.OfficerUnitManager.getId();
	}
}
