package controller.report.hrmanager.genaral;

public class ReportGeneral {
	protected String name;
	protected String employee_id;
	protected String unit_id;
	protected int month;
	
	public ReportGeneral() {
		
	}
	
	public ReportGeneral(String name, String employee_id, String unit_id, int month) {
		super();
		this.name = name;
		this.employee_id = employee_id;
		this.unit_id = unit_id;
		this.month = month;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
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
	
	
}
