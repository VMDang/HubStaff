package controller.report.hrmanager.unitworkerattendance;

public class ReportMonthlyWorkerTable {
	private String name;
	private String worker_id;
	private String unit_id;
    private int month;
    private float total_hour_work;
    private float total_overtime;
    
    public ReportMonthlyWorkerTable(String name, String worker_id, String unit_id, int month, float total_hour_work, float total_overtime) {
    	super();
    	this.name = name;
    	this.worker_id = worker_id;
    	this.unit_id = unit_id;
    	this.month = month;
    	this.total_hour_work = total_hour_work;
    	this.total_overtime = total_overtime;
    }
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWorker_id() {
		return worker_id;
	}
	public void setWorker_id(String worker_id) {
		this.worker_id = worker_id;
	}
	public String getUnit_id() {
		return unit_id;
	}
	public void setUnit_id(String unit_id) {
		this.unit_id = unit_id;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
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
}
