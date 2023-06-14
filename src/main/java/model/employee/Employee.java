package model.employee;

public class Employee {
    protected String id;
    protected String name;
    protected String department;
    protected String unit_id;
    protected String password;
    protected int role_id;
	public Employee(String i, String name, String password, int role_id) {
		super();
		this.id = i;
		this.name = name;
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
