package createDB;

import java.util.ArrayList;

import dao.EmployeeDAO;
import model.employee.Employee;


public class InsertIntoTableTest {
	public static void main(String[] args) {
		Employee nv = new Employee("nv11","Nguyen Duy Hung@11" ,"Van Phong","UN02", "170102",3);
		EmployeeDAO.getInstance().update(nv);
//		for(int i = 1 ; i <=10 ; i++) {
//			Employee nv = new Employee("nv"+i,"Nguyen Duy Hung@" + i,"Van Phong","UN02", "170102",i%5+1);
//			EmployeeDAO.getInstance().insert(nv);
//		}
//		
//		ArrayList<Employee> allEmployees = EmployeeDAO.getInstance().selectAll(); 
//		
//		for(Employee e : allEmployees) {
//			System.out.println(e.getName());
//		}
		
//		Employee nv = EmployeeDAO.getInstance().selectById("nv3");
//		System.out.println(nv.getName());
		
//		String condition = "RoleId = 5";
//		ArrayList<Employee> employees = EmployeeDAO.getInstance().selectByCondition(condition);
//		
//		for(Employee e : employees) {
//			System.out.println(e.getName());
//		}
		
	}
}
