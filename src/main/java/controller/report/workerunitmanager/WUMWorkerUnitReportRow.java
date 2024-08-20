package controller.report.workerunitmanager;

import java.lang.String;

public class WUMWorkerUnitReportRow {
    private String name;
    private String worker_id;
    private String total_hour_work;
    private String total_overtime;
    private int countLateEarly;
    private int status;

    public WUMWorkerUnitReportRow(String name, String worker_id, String total_hour_work, String total_overtime, int countLateEarly, int status) {
        this.name = name;
        this.worker_id = worker_id;
        this.total_hour_work = total_hour_work;
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

    public String getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(String worker_id) {
        this.worker_id = worker_id;
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

    public int getCountLateEarly() {
        return countLateEarly;
    }

    public void setCountLateEarly(int countLateEarly) {
        this.countLateEarly = countLateEarly;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
