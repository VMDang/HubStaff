package controller.timekeeping.personal.monthly;

public class GenaralTableRow {
	private int total_day_work;
	private float total_hour_work;
	private float total_overtime;
	private int salary;
	
	
	
	public GenaralTableRow(int total_day_work, float total_hour_work, float total_overtime, int salary) {
		super();
		this.total_day_work = total_day_work;
		this.total_hour_work = total_hour_work;
		this.total_overtime = total_overtime;
		this.salary = salary;
	}
	public int getTotal_day_work() {
		return total_day_work;
	}
	public void setTotal_day_work(int total_day_work) {
		this.total_day_work = total_day_work;
	}
	public float getTotal_hour_work() {
		return total_hour_work;
	}
	public void setTotal_hour_work(float total_hour_work) {
		this.total_hour_work = total_hour_work;
	}
	public float getTotal_overtime() {
		return total_overtime;
	}
	public void setTotal_overtime(float total_overtime) {
		this.total_overtime = total_overtime;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	
}
