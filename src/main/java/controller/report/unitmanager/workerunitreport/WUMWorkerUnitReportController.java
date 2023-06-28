package controller.report.unitmanager.workerunitreport;

import controller.auth.Authentication;
import dbtimekeeping.gettimekeeping.GetTimekeepingWorker;
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
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class WUMWorkerUnitReportController implements Initializable {
    private ObservableList<WUMWorkerUnitReportRow> listRecord = FXCollections.observableArrayList();

    private HashMap<String, Worker> currentWorkers = new HashMap<String, Worker>();

    @FXML
    private TableView<WUMWorkerUnitReportRow> tableReport;

    @FXML
    private TableColumn<WUMWorkerUnitReportRow, Integer> index;

    @FXML
    private TableColumn<WUMWorkerUnitReportRow, String > worker_id;

    @FXML
    private TableColumn<WUMWorkerUnitReportRow, String> name;

    @FXML
    private TableColumn<WUMWorkerUnitReportRow, String> hoursOT;

    @FXML
    private TableColumn<WUMWorkerUnitReportRow, String> hoursWork;

    @FXML
    private TableColumn<WUMWorkerUnitReportRow, YearMonth> month;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnReloadPage;

    @FXML
    private ChoiceBox<String> chooseMonth;

    @FXML
    private ChoiceBox<String> chooseYear;

    @FXML
    private Label countWorkers;

    @FXML
    private Label department;

    @FXML
    private Label unit_id;

    @FXML
    private Label wum_name;

    String[] listMonth = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    String[] listYear = {"2023", "2022", "2021", "2020"};

    @FXML
    void reloadPage(ActionEvent event) {
        chooseMonth.setValue(LocalDate.now().format(DateTimeFormatter.ofPattern("MM")));
        chooseYear.setValue(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy")));

        listRecord.removeAll();
        tableReport.getItems().clear();

        setListRecord();
        tableReport.setItems(listRecord);
    }
    @FXML
    void searchByMonth(ActionEvent event) {
        listRecord.removeAll();
        tableReport.getItems().clear();

        setListRecord();
        tableReport.setItems(listRecord);

//        for (Map.Entry<String, Worker> w: currentWorkers.entrySet()) {
//            System.out.println(w.getKey() + "  ---  " + w.getValue().toString());
//        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        unit_id.setText(Authentication.getInstance().getAuthentication().getUnit_id());
        wum_name.setText(Authentication.getInstance().getAuthentication().getName());

        chooseMonth.getItems().addAll(listMonth);
        chooseMonth.setValue(LocalDate.now().format(DateTimeFormatter.ofPattern("MM")));
        chooseYear.getItems().addAll(listYear);
        chooseYear.setValue(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy")));

        setListRecord();

        countWorkers.setText(String.valueOf(listRecord.size()));

        index.setCellValueFactory(index -> new ReadOnlyObjectWrapper<Integer>(tableReport.getItems().indexOf(index.getValue())+1));
        index.setSortable(false);
        worker_id.setCellValueFactory(new PropertyValueFactory<WUMWorkerUnitReportRow, String>("workerID"));
        name.setCellValueFactory(new PropertyValueFactory<WUMWorkerUnitReportRow, String>("name"));
        month.setCellValueFactory(new PropertyValueFactory<WUMWorkerUnitReportRow, YearMonth>("month"));
        hoursWork.setCellValueFactory(new PropertyValueFactory<WUMWorkerUnitReportRow, String>("hoursWork"));
        hoursOT.setCellValueFactory(new PropertyValueFactory<WUMWorkerUnitReportRow, String>("hoursOT"));

        tableReport.setItems(listRecord);
    }

    public void setListRecord() {
        ArrayList<Worker> workers = new ArrayList<Worker>();
        workers.addAll(getAllWorkerUnit(Authentication.getInstance().getAuthentication().getUnit_id()));

        String monthFilter = chooseMonth.getValue() + "/" + chooseYear.getValue();

        for (Worker w : workers) {
            ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = new ArrayList<LogTimekeepingWorker>();
            logTimekeepingWorkers.addAll(getTimeKeepingAWorker(w.getId()));

            ArrayList<LogTimekeepingWorker> logTimekeepingByMonth = new ArrayList<LogTimekeepingWorker>();
            logTimekeepingByMonth.addAll(getTimekeepingByMonth(logTimekeepingWorkers, monthFilter));

            if (!logTimekeepingByMonth.isEmpty()){


                if (monthFilter.equals(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/yyyy")))){
                    currentWorkers.put(w.getId(), w);
                }
                float totalHoursWork = 0, totalHoursOT = 0;
                String hoursWork = "", hoursOT = "";

                for (LogTimekeepingWorker log: logTimekeepingByMonth){
                    totalHoursWork += log.getShift1() + log.getShift2();
                    totalHoursOT += log.getShift3();

                    hoursWork = String.valueOf(totalHoursWork) + " / " + String.valueOf(logTimekeepingByMonth.size()*2*4);
                    hoursOT = String.valueOf(totalHoursOT) + " / " + String.valueOf(logTimekeepingByMonth.size()*4);
                }

                listRecord.add(new WUMWorkerUnitReportRow(w.getId(), w.getName(), monthFilter, hoursWork, hoursOT));
            }
        }
    }

    public ArrayList<Worker> getAllWorkerUnit(String unit_id){
        GetAllEmployees getAllEmployees = GetAllEmployees.getInstance();
        ArrayList<Employee> allEmployees = getAllEmployees.getAllEmployees();
        ArrayList<Worker> allWorker = new ArrayList<Worker>();

        for (Employee e: allEmployees) {
            if ((e.getRole_id() == 3 ||e.getRole_id() == 1) && (e.getUnit_id().equals(unit_id))) {
                allWorker.add(new Worker(e.getId(), e.getName(), e.getUnit_id(), e.getPassword(),e.getStatus()));
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
