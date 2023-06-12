package model.employee;

public class Employee {
    protected String id;
    protected String name;
    protected String department;
    protected String unit_id;
    protected String password;
    protected int role_id;
    
    public Employee() {
    	
    }
    public Employee(String id, String name, String department, String unit_id, String password, int role_id) {
    	super();
		this.id = id;
		this.name = name;
		this.department = department;
		this.unit_id = unit_id;
		this.password = password;
		this.role_id = role_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getUnit_id() {
		return unit_id;
	}
	public void setUnit_id(String unit_id) {
		this.unit_id = unit_id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
    
    
}
