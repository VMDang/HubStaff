package controller.importdata.excel;

public class ExcelImportRow {
	private Integer id ;
	private String employee_id;
	private String date;
	private String time_in;
	private String time_out;
	private String name;
	private String status;
	private int role_id;
	
	public ExcelImportRow() {
		super();
	}
	
	public ExcelImportRow(Integer id, String employee_id, String date, String time_in, String time_out, String name,
                          String status, int role_id) {
		super();
		this.id = id;
		this.employee_id = employee_id;
		this.date = date;
		this.time_in = time_in;
		this.time_out = time_out;
		this.name = name;
		this.status = status;
		this.role_id = role_id;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime_in() {
		return time_in;
	}

	public void setTime_in(String time_in) {
		this.time_in = time_in;
	}

	public String getTime_out() {
		return time_out;
	}

	public void setTime_out(String time_out) {
		this.time_out = time_out;
	}

	@Override
    public String toString() {
        return "Book [id=" + id + ", employeeid=" + employee_id + ", date=" + date + ", time_in=" + time_in + ", timeout="
                + time_out + "]";
    }
	
	
}
