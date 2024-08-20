package controller.timekeeping.officer.monthly;

public class SummaryOfficerTableRow {
    private int total_day_work;
    private float total_amount_work;
    private float total_overtime;
    private float total_late_early;

    public SummaryOfficerTableRow(int total_day_work, float total_amount_work, float total_overtime, float total_late_early) {
        this.total_day_work = total_day_work;
        this.total_amount_work = total_amount_work;
        this.total_overtime = total_overtime;
        this.total_late_early = total_late_early;
    }

    public int getTotal_day_work() {
        return total_day_work;
    }

    public void setTotal_day_work(int total_day_work) {
        this.total_day_work = total_day_work;
    }

    public float getTotal_amount_work() {
        return total_amount_work;
    }

    public void setTotal_amount_work(float total_amount_work) {
        this.total_amount_work = total_amount_work;
    }

    public float getTotal_overtime() {
        return total_overtime;
    }

    public void setTotal_overtime(float total_overtime) {
        this.total_overtime = total_overtime;
    }

    public float getTotal_late_early() {
        return total_late_early;
    }

    public void setTotal_late_early(float total_late_early) {
        this.total_late_early = total_late_early;
    }
}
