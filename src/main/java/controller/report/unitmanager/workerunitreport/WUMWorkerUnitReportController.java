package controller.report.unitmanager.workerunitreport;

import controller.auth.Authentication;
import controller.layouts.LayoutController;
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
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import model.employee.Employee;
import model.employee.worker.Worker;
import model.logtimekeeping.LogTimekeepingWorker;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import static assets.navigation.FXMLNavigation.TIMEKEEPING_MONTHLY_VIEW;

public class WUMWorkerUnitReportController implements Initializable {
    private ObservableList<WUMWorkerUnitReportRow> listRecord = FXCollections.observableArrayList();
    private HashMap<String, Worker> currentWorkers = new HashMap<>();

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

    @FXML
    private AnchorPane basePane;

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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        unit_id.setText(Authentication.getInstance().getAuthentication().getUnit_id());
        wum_name.setText(Authentication.getInstance().getAuthentication().getName());
        department.setText(Authentication.getInstance().getAuthentication().getDepartment());

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

        switchToTimekeepingWUM();
    }

    private void setListRecord() {
        String monthFilter = chooseMonth.getValue() + "/" + chooseYear.getValue();

        ArrayList<Worker> workers = new ArrayList<Worker>();
        workers.addAll(getAllWorkerUnit(Authentication.getInstance().getAuthentication().getUnit_id()));

        int indexWUM = 0;
        for (int i = 0; i < workers.size(); i++) {
            if (workers.get(i).getId().equals(Authentication.getInstance().getAuthentication().getId())){
                indexWUM = i;
            }
        }
        Worker workerTmp = workers.get(indexWUM);
        workers.remove(indexWUM);
        workers.add(0, workerTmp);

        for (Worker w : workers) {
            ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = new ArrayList<LogTimekeepingWorker>();
            logTimekeepingWorkers.addAll(getTimekeepingsAWorker(w.getId()));

            ArrayList<LogTimekeepingWorker> logTimekeepingByMonth = new ArrayList<LogTimekeepingWorker>();
            logTimekeepingByMonth.addAll(getTimekeepingByMonth(logTimekeepingWorkers, monthFilter));

            float totalHoursWork = 0, totalHoursOT = 0;
            String hoursWork = "", hoursOT = "";

            for (LogTimekeepingWorker log: logTimekeepingByMonth){
                totalHoursWork += log.getShift1() + log.getShift2();
                totalHoursOT += log.getShift3();

                hoursWork = String.valueOf(totalHoursWork) + " / " + String.valueOf(logTimekeepingByMonth.size()*2*4);
                hoursOT = String.valueOf(totalHoursOT) + " / " + String.valueOf(logTimekeepingByMonth.size()*4);
            }

            if (monthFilter.equals(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/yyyy"))) && !(w.getStatus()==0 && logTimekeepingByMonth.isEmpty())){
                listRecord.add(new WUMWorkerUnitReportRow(w.getId(), w.getName(), monthFilter, hoursWork, hoursOT));
            }else if (!logTimekeepingByMonth.isEmpty()){
                listRecord.add(new WUMWorkerUnitReportRow(w.getId(), w.getName(), monthFilter, hoursWork, hoursOT));
            }
        }
        highlightRow();
    }

    private ArrayList<Worker> getAllWorkerUnit(String unit_id){
        GetAllEmployees getAllEmployees = GetAllEmployees.getInstance();
        ArrayList<Employee> allEmployees = getAllEmployees.getAllEmployees();
        ArrayList<Worker> allWorker = new ArrayList<Worker>();

        for (Employee e: allEmployees) {
            if ((e.getRole_id() == 3 ||e.getRole_id() == 1) && (e.getUnit_id().equals(unit_id))) {
                allWorker.add(new Worker(e.getId(), e.getName(), e.getUnit_id(), e.getPassword(),e.getStatus()));

                if (e.getStatus() == 1){
                    currentWorkers.put( e.getId(), new Worker(e.getId(), e.getName(), e.getUnit_id(), e.getPassword(),e.getStatus()));
                }
            }

        }
        return allWorker;
    }

    public ArrayList<LogTimekeepingWorker> getTimekeepingsAWorker(String employee_id){
        GetTimekeepingWorker getTimekeepingWorker = GetTimekeepingWorker.getInstance();
        ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = getTimekeepingWorker.getTimekeepingsByEmployeeID(employee_id);

        return logTimekeepingWorkers;
    }

    private ArrayList<LogTimekeepingWorker> getTimekeepingByMonth(ArrayList<LogTimekeepingWorker> logs, String monthFilter){
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

    private void switchToTimekeepingWUM() {
        tableReport.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount()==1){
                WUMWorkerUnitReportRow selectedItem = tableReport.getSelectionModel().getSelectedItem();
                if(selectedItem != null && selectedItem.getWorkerID().equals(Authentication.getInstance().getAuthentication().getId())){
                    LayoutController layout = new LayoutController();
                    try {
                        layout.changeAnchorPane(basePane, TIMEKEEPING_MONTHLY_VIEW);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private void highlightRow(){
        tableReport.setRowFactory(tv -> new TableRow<WUMWorkerUnitReportRow>() {
            @Override
            protected void updateItem(WUMWorkerUnitReportRow item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty && item != null) {
                    if (item.getWorkerID().equals(Authentication.getInstance().getAuthentication().getId())) {
                        setStyle("-fx-background-color: #acecea;");
                    } else if (!currentWorkers.containsKey(item.getWorkerID())){
                        setStyle("-fx-background-color: #eaa6ab;");
                    } else setStyle("");
                }else setStyle("");
            }
        });
    }

}
