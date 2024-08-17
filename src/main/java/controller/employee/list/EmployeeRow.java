package controller.employee.list;

public class EmployeeRow {
    private String employee_id;
    private String name;
    private String birthday;
    private String department;
    private String unit;
    private String status;
    private String role;

    public EmployeeRow(String employee_id, String name, String birthday, String department, String unit, String status, String role) {
        this.employee_id = employee_id;
        this.name = name;
        this.birthday = birthday;
        this.department = department;
        this.unit = unit;
        this.status = status;
        this.role = role;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
