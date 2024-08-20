package controller.report.officerunitmanager;

public class OUMOfficerUnitReportRow {
    private String name;
    private String officer_id;
    private String total_day_work;
    private float total_overtime;
    private float countLateEarly;
    private int status;

    public OUMOfficerUnitReportRow(String name, String officer_id, String total_day_work, float total_overtime, float countLateEarly, int status) {
        this.name = name;
        this.officer_id = officer_id;
        this.total_day_work = total_day_work;
        this.total_overtime = total_overtime;
        this.countLateEarly = countLateEarly;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfficer_id() {
        return officer_id;
    }

    public void setOfficer_id(String officer_id) {
        this.officer_id = officer_id;
    }

    public String getTotal_day_work() {
        return total_day_work;
    }

    public void setTotal_day_work(String total_day_work) {
        this.total_day_work = total_day_work;
    }

    public float getTotal_overtime() {
        return total_overtime;
    }

    public void setTotal_overtime(float total_overtime) {
        this.total_overtime = total_overtime;
    }

    public float getCountLateEarly() {
        return countLateEarly;
    }

    public void setCountLateEarly(float countLateEarly) {
        this.countLateEarly = countLateEarly;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
