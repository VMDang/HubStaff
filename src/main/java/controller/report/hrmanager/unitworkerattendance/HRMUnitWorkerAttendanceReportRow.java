package controller.report.hrmanager.unitworkerattendance;

public class HRMUnitWorkerAttendanceReportRow {
	private String name;
	private String worker_id;
	private String unit_id;
    private String month;
    private String total_hour_work;
    private String total_overtime;
    
    public HRMUnitWorkerAttendanceReportRow(String name, String worker_id, String unit_id, String month, String total_hour_work, String total_overtime) {
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

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getTotal_hour_work() {
		return total_hour_work;
	}

	public void setTotal_hour_work(String total_hour_work) {
		this.total_hour_work = total_hour_work;
	}

	public String getTotal_overtime() {
		return total_overtime;
	}

	public void setTotal_overtime(String total_overtime) {
		this.total_overtime = total_overtime;
	}
    
	@Override
	public String toString() {
		return "HRMUnitWorkerAttendanceReportRow{" +
				"name='" + name + '\'' +
				", worker_id='" + worker_id + '\'' +
				", unit_id='" + unit_id + '\'' +
				", month='" + month + '\'' +
				", total_hour_work='" + total_hour_work + '\'' +
				", total_overtime='" + total_overtime + '\'' +
				'}';
	}

}
