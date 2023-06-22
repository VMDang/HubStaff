package controller.report.unitmanager.workerunitreport;

import java.lang.String;

public class WUMWorkerUnitReportRow {
    private String workerID;
    private String name;
    private String month;
    private String hoursWork;
    private String hoursOT;

    public WUMWorkerUnitReportRow(String workerID, String name, String month, String hoursWork, String hoursOT) {
        this.workerID = workerID;
        this.name = name;
        this.month = month;
        this.hoursWork = hoursWork;
        this.hoursOT = hoursOT;
    }

    public String getWorkerID() {
        return workerID;
    }

    public void setWorkerID(String workerID) {
        this.workerID = workerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getHoursWork() {
        return hoursWork;
    }

    public void setHoursWork(String hoursWork) {
        this.hoursWork = hoursWork;
    }

    public String getHoursOT() {
        return hoursOT;
    }

    public void setHoursOT(String hoursOT) {
        this.hoursOT = hoursOT;
    }

    @Override
    public String toString() {
        return "WUMWorkerUnitReportRow{" +
                "workerID='" + workerID + '\'' +
                ", name='" + name + '\'' +
                ", month=" + month +
                ", hoursWork='" + hoursWork + '\'' +
                ", hoursOT='" + hoursOT + '\'' +
                '}';
    }
}
