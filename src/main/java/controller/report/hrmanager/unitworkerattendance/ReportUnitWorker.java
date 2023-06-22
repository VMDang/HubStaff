package controller.report.hrmanager.unitworkerattendance;

import controller.report.hrmanager.genaral.ReportGeneral;

public class ReportUnitWorker extends ReportGeneral{
	private float total_hour_work;
	private float total_overtime;
	
	public ReportUnitWorker() {
		
	}
	
	public ReportUnitWorker(String name, String employee_id, String unit_id, int month, float total_hour_work, float total_overtime) {
		super(name, employee_id, unit_id, month);
		this.total_hour_work = total_hour_work;
		this.total_overtime = total_overtime;
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
	
	@Override
	public String toString() {
		return "ReportUnitWorke [name=" + name + ", worker_id=" + employee_id + ", unit_id=" + unit_id + ", month="
				+ month + ", total_hour_work=" + total_hour_work + ", total_overtime=" + total_overtime + "]";
	}
	
}
