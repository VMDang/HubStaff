package controller.report.unitmanager.workerunitreport;

import controller.auth.Authentication;
import dbtimekeeping.GetTimekeepingWorker;
import hrsystem.GetAllEmployees;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.employee.Employee;
import model.employee.worker.Worker;
import model.logtimekeeping.LogTimekeepingWorker;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class WUMWorkerUnitReportController implements Initializable {
    private ObservableList<WUMWorkerUnitReportRecord> listRecord = FXCollections.observableArrayList();

    @FXML
    private TableView<WUMWorkerUnitReportRecord> tableReport;

    @FXML
    private TableColumn<WUMWorkerUnitReportRecord, Integer> index;

    @FXML
    private TableColumn<WUMWorkerUnitReportRecord, String > worker_id;

    @FXML
    private TableColumn<WUMWorkerUnitReportRecord, String> name;

    @FXML
    private TableColumn<WUMWorkerUnitReportRecord, String> hoursOT;

    @FXML
    private TableColumn<WUMWorkerUnitReportRecord, String> hoursWork;

    @FXML
    private TableColumn<WUMWorkerUnitReportRecord, YearMonth> month;

    @FXML
    private Label monthSelected;

    @FXML
    private Button resetPage;

    @FXML
    private DatePicker searchByMonth;

    @FXML
    private Label countWorkers;

    @FXML
    private Label department;

    @FXML
    private Label unit_id;

    @FXML
    private Label wum_name;

    @FXML
    void resetPage(ActionEvent event) {
        searchByMonth.setValue(LocalDate.now());
        monthSelected.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/yyyy")));

        listRecord.removeAll();
        tableReport.getItems().clear();

        setListRecord();
        tableReport.setItems(listRecord);
    }
    @FXML
    void searchByMonth(ActionEvent event) {
        LocalDate date = searchByMonth.getValue();
        String month = date.format(DateTimeFormatter.ofPattern("MM/yyyy"));
        monthSelected.setText(month);

        listRecord.removeAll();
        tableReport.getItems().clear();

        setListRecord();
        tableReport.setItems(listRecord);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setListRecord();

        unit_id.setText(Authentication.authentication.getUnit_id());
        wum_name.setText(Authentication.authentication.getName());
        countWorkers.setText(String.valueOf(listRecord.size()));
        monthSelected.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/yyyy")));

        index.setCellValueFactory(index -> new ReadOnlyObjectWrapper<Integer>(tableReport.getItems().indexOf(index.getValue())+1));
        index.setSortable(false);
        worker_id.setCellValueFactory(new PropertyValueFactory<WUMWorkerUnitReportRecord, String>("workerID"));
        name.setCellValueFactory(new PropertyValueFactory<WUMWorkerUnitReportRecord, String>("name"));
        month.setCellValueFactory(new PropertyValueFactory<WUMWorkerUnitReportRecord, YearMonth>("month"));
        hoursWork.setCellValueFactory(new PropertyValueFactory<WUMWorkerUnitReportRecord, String>("hoursWork"));
        hoursOT.setCellValueFactory(new PropertyValueFactory<WUMWorkerUnitReportRecord, String>("hoursOT"));

        tableReport.setItems(listRecord);

    }

    public void setListRecord() {
        ArrayList<Worker> workers = new ArrayList<Worker>();
        workers.addAll(getAllWorkerUnit(Authentication.authentication.getUnit_id()));

        for (Worker w : workers) {
            if (monthSelected.getText().equals("")){
                LocalDate date = LocalDate.now();
                monthSelected.setText(date.format(DateTimeFormatter.ofPattern("MM/yyyy")));
            }

            ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = new ArrayList<LogTimekeepingWorker>();
            logTimekeepingWorkers.addAll(getTimeKeepingAWorker(w.getId()));

            ArrayList<LogTimekeepingWorker> logTimekeepingByMonth = new ArrayList<LogTimekeepingWorker>();
            logTimekeepingByMonth.addAll(getTimekeepingByMonth(logTimekeepingWorkers, monthSelected.getText()));

            if (!logTimekeepingByMonth.isEmpty()){
                float totalHoursWork = 0, totalHoursOT = 0;
                String hoursWork = "", hoursOT = "";

                for (LogTimekeepingWorker log: logTimekeepingByMonth){
                    totalHoursWork += log.getShift1() + log.getShift2();
                    totalHoursOT += log.getShift3();

                    hoursWork = String.valueOf(totalHoursWork) + " / " + String.valueOf(logTimekeepingByMonth.size()*2*4);
                    hoursOT = String.valueOf(totalHoursOT) + " / " + String.valueOf(logTimekeepingByMonth.size()*4);
                }

                listRecord.add(new WUMWorkerUnitReportRecord(w.getId(), w.getName(), monthSelected.getText(), hoursWork, hoursOT));
            }
        }
    }

    public ArrayList<Worker> getAllWorkerUnit(String unit_id){
        GetAllEmployees getAllEmployees = GetAllEmployees.getInstance();
        ArrayList<Employee> allEmployees = getAllEmployees.getAllEmployees();
        ArrayList<Worker> allWorker = new ArrayList<Worker>();

        for (Employee e: allEmployees) {
            if ((e.getRole_id() == 3) && (e.getUnit_id().equals(unit_id))) {
                allWorker.add(new Worker(e.getId(), e.getName(), e.getUnit_id(), e.getPassword()));
            }
        }
        return allWorker;
    }

    public ArrayList<LogTimekeepingWorker> getTimeKeepingAWorker(String employee_id){
        GetTimekeepingWorker getTimekeepingWorker = GetTimekeepingWorker.getInstance();
        ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = getTimekeepingWorker.getTimekeepingsByEmployeeID(employee_id);

        return logTimekeepingWorkers;
    }

    public ArrayList<LogTimekeepingWorker> getTimekeepingByMonth(ArrayList<LogTimekeepingWorker> logs, String monthFilter){
        ArrayList<LogTimekeepingWorker> logFilterByMonth = new ArrayList<LogTimekeepingWorker>();

        for (LogTimekeepingWorker log : logs) {
            YearMonth yearMonth = YearMonth.of(log.getDate().toLocalDate().getYear(), log.getDate().toLocalDate().getMonth());
            String monthReport = yearMonth.format(DateTimeFormatter.ofPattern("MM/yyyy"));

            if (monthReport.equals(monthFilter)){
                logFilterByMonth.add(log);
            }

        }

        return logFilterByMonth;
    }
}
