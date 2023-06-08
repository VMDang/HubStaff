package hrsystem;

import model.employee.Employee;

import java.util.ArrayList;

public interface IHRSystem {
    public Employee getAEmployee(String id);
    public ArrayList<Employee> getAllEmployees();
}
