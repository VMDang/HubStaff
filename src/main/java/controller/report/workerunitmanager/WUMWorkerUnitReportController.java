package controller.report.workerunitmanager;

import config.Config;
import config.FXMLNavigation;
import controller.auth.Authentication;
import controller.timekeeping.worker.monthly.TimekeepingMonthlyWorkerController;
import database.EmployeeDAO;
import database.TimekeepingWorkerDAO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import model.employee.Employee;
import model.employee.Role;
import model.employee.worker.Worker;
import model.employee.worker.WorkerUnitManager;
import model.logtimekeeping.LogTimekeepingWorker;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utility.TimeUtility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class WUMWorkerUnitReportController implements Initializable {
    private LocalDate today = LocalDate.now();

    private ObservableList<WUMWorkerUnitReportRow> listRecord = FXCollections.observableArrayList();

    private ArrayList<Worker> workersUnit = new ArrayList<Worker>();

    private String monthInit = null;

    private String yearInit = null;

    @FXML
    private TableView<WUMWorkerUnitReportRow> tableReport;

    @FXML
    private TableColumn<WUMWorkerUnitReportRow, Integer> index;

    @FXML
    private TableColumn<WUMWorkerUnitReportRow, String > worker_idCol;

    @FXML
    private TableColumn<WUMWorkerUnitReportRow, String> nameCol;

    @FXML
    private TableColumn<WUMWorkerUnitReportRow, String> total_overtime_workCol;

    @FXML
    private TableColumn<WUMWorkerUnitReportRow, String> total_hour_workCol;

    @FXML
    private TableColumn<WUMWorkerUnitReportRow, Integer> lateEarlyCol;

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

    @FXML
    private Label monthLabel;

    public WUMWorkerUnitReportController() {
    }

    public WUMWorkerUnitReportController(String monthInit, String yearInit) {
        this.monthInit = monthInit;
        this.yearInit = yearInit;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Employee authentication = Authentication.getInstance().getAuthentication();
        getAllWorkerUnit(authentication.getUnit_id());
        int activeWorkers = 0;
        for (Worker w : workersUnit) {
            if (w.getStatus() == 1) {
                activeWorkers++;
            }
        }
        countWorkers.setText(String.valueOf(activeWorkers));

        unit_id.setText(authentication.getUnit_id());
        wum_name.setText(authentication.getName());
        department.setText(authentication.getDepartment());
        monthLabel.setText("Tháng " + today.format(DateTimeFormatter.ofPattern("MM/yyyy")));

        chooseMonth.getItems().addAll(TimeUtility.getListMonth());
        chooseMonth.setValue(today.format(DateTimeFormatter.ofPattern("MM")));
        chooseYear.getItems().addAll(TimeUtility.getListYear());
        chooseYear.setValue(today.format(DateTimeFormatter.ofPattern("yyyy")));

        if (monthInit != null && yearInit != null) {
            chooseMonth.setValue(monthInit);
            chooseYear.setValue(yearInit);
            monthLabel.setText("Tháng " + chooseMonth.getValue() + "/" + chooseYear.getValue());
        } else {
            chooseMonth.setValue(today.format(DateTimeFormatter.ofPattern("MM")));
            chooseYear.setValue(today.format(DateTimeFormatter.ofPattern("yyyy")));
            monthLabel.setText("Tháng " + today.format(DateTimeFormatter.ofPattern("MM/yyyy")));
        }

        setListRecord();
        index.setCellValueFactory(index -> new ReadOnlyObjectWrapper<Integer>(tableReport.getItems().indexOf(index.getValue())+1));
        nameCol.setCellValueFactory(new PropertyValueFactory<WUMWorkerUnitReportRow, String>("name"));
        worker_idCol.setCellValueFactory(new PropertyValueFactory<WUMWorkerUnitReportRow, String>("worker_id"));
        total_hour_workCol.setCellValueFactory(new PropertyValueFactory<WUMWorkerUnitReportRow, String>("total_hour_work"));
        total_overtime_workCol.setCellValueFactory(new PropertyValueFactory<WUMWorkerUnitReportRow, String>("total_overtime"));
        lateEarlyCol.setCellValueFactory(new PropertyValueFactory<WUMWorkerUnitReportRow, Integer>("countLateEarly"));
        tableReport.setItems(listRecord);
        highlightRow();

        tableReport.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                WUMWorkerUnitReportRow selected = tableReport.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    try {
                        Employee employee = EmployeeDAO.getInstance().getById(selected.getWorker_id());

                        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLNavigation.TIMEKEEPING_MONTHLY_WORKER_VIEW));
                        loader.setControllerFactory(c -> new TimekeepingMonthlyWorkerController(employee, chooseMonth.getValue(), chooseYear.getValue()));
                        Node node = loader.load();
                        basePane.getChildren().setAll(node);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    @FXML
    void reloadPage(ActionEvent event) {
        chooseMonth.setValue(today.format(DateTimeFormatter.ofPattern("MM")));
        chooseYear.setValue(today.format(DateTimeFormatter.ofPattern("yyyy")));
        monthLabel.setText("Tháng " + today.format(DateTimeFormatter.ofPattern("MM/yyyy")));

        setListRecord();
        tableReport.setItems(listRecord);
    }
    @FXML
    void search(ActionEvent event) {
        monthLabel.setText("Tháng " + chooseMonth.getValue() + "/" + chooseYear.getValue());
        setListRecord();
        tableReport.setItems(listRecord);
    }

    @FXML
    void exportExcel(ActionEvent event) throws IOException {
        if (listRecord.isEmpty()) {
            notifyExportReport("Không có dữ liệu để xuất file", Alert.AlertType.ERROR);
            return;
        }

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Chọn thư mục lưu file");

        File selectedDirectory = directoryChooser.showDialog(basePane.getScene().getWindow());
        if (selectedDirectory != null) {
            String directoryPath = selectedDirectory.getAbsolutePath();

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Báo cáo chấm công");

            Row header = sheet.createRow(0);
            for (int col = 0; col < tableReport.getColumns().size(); col++) {
                header.createCell(col).setCellValue(tableReport.getColumns().get(col).getText());
            }

            int rowIndex = 1;
            for (WUMWorkerUnitReportRow row : listRecord) {
                Row r = sheet.createRow(rowIndex);
                r.createCell(0).setCellValue(rowIndex);
                r.createCell(1).setCellValue(row.getWorker_id());
                r.createCell(2).setCellValue(row.getName());
                r.createCell(3).setCellValue(row.getTotal_hour_work());
                r.createCell(4).setCellValue(row.getTotal_overtime());
                r.createCell(5).setCellValue(row.getCountLateEarly());
                rowIndex++;
            }

            String unit_id = Authentication.getInstance().getAuthentication().getUnit_id();
            String fileName = "Attendance_Report_" + unit_id + "_" + chooseMonth.getValue() + "_" + chooseYear.getValue() + ".xlsx";
            String filePath = directoryPath + File.separator + fileName;

            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                workbook.write(fileOutputStream);
                notifyExportReport("Xuất báo cáo thành công!", Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                notifyExportReport("Xuất báo cáo thất bại!", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
            workbook.close();
        }
    }

    private void notifyExportReport(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Export report");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void setListRecord() {
        listRecord.clear();
        String month = chooseMonth.getValue();
        String year = chooseYear.getValue();

        for (Worker w : workersUnit) {
            ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = getTimekeepingAWorker(w.getId());
            ArrayList<LogTimekeepingWorker> logTimekeepingByMonth = getTimekeepingByMonth(logTimekeepingWorkers, month, year);

            if (logTimekeepingByMonth.isEmpty()){
                continue;
            }

            float totalHoursWork = 0, totalHoursOT = 0;
            String hoursWork = "", hoursOT = "";
            int countLateEarly = 0;

            for (LogTimekeepingWorker log: logTimekeepingByMonth){
                totalHoursWork += log.getShift1() + log.getShift2();
                totalHoursOT += log.getShift3();

                hoursWork = String.valueOf(totalHoursWork) + " / " + String.valueOf(logTimekeepingByMonth.size()*2*4);
                hoursOT = String.valueOf(totalHoursOT) + " / " + String.valueOf(logTimekeepingByMonth.size()*4);

                Time time_in = log.getTime_in();
                Time time_out = log.getTime_out();

                if (time_in != null && time_out != null) {
                    if((time_in.compareTo(Time.valueOf(Config.WORKER_START_SHIFT1)) > 0 && time_in.compareTo(Time.valueOf(Config.WORKER_END_SHIFT1)) < 0)
                            || (time_in.compareTo(Time.valueOf(Config.WORKER_START_SHIFT2)) > 0 && time_in.compareTo(Time.valueOf(Config.WORKER_END_SHIFT2)) < 0)) {
                        countLateEarly++;
                    }

                    if((time_out.compareTo(Time.valueOf(Config.WORKER_START_SHIFT1)) > 0 && time_out.compareTo(Time.valueOf(Config.WORKER_END_SHIFT1)) < 0)
                            || (time_out.compareTo(Time.valueOf(Config.WORKER_START_SHIFT2)) > 0 && time_out.compareTo(Time.valueOf(Config.WORKER_END_SHIFT2)) < 0)) {
                        countLateEarly++;
                    }
                }
            }

            if (!(w.getStatus()==0 && logTimekeepingByMonth.isEmpty())){
                listRecord.add(new WUMWorkerUnitReportRow(w.getName(), w.getId(), hoursWork, hoursOT, countLateEarly, w.getStatus()));
            }
        }
    }

    private void getAllWorkerUnit(String unit_id){
        ArrayList<Employee> employees = EmployeeDAO.getInstance().getAll();
        for (Employee e : employees) {
            if (e.getUnit_id().equals(unit_id)) {
                if (e.getRole_id() == Role.Worker.getId()) {
                    workersUnit.add(new Worker(e.getId(), e.getName(), e.getIdentifier(), e.getBirthday(), e.getAddress(), e.getGender(), e.getPhone(),
                            e.getDepartment(), e.getUnit_id(), e.getPassword(),e.getStatus()));
                }
                if (e.getRole_id() == Role.WorkerUnitManager.getId()) {
                    workersUnit.add(new WorkerUnitManager(e.getId(), e.getName(), e.getIdentifier(), e.getBirthday(), e.getAddress(), e.getGender(), e.getPhone(),
                            e.getDepartment(), e.getUnit_id(), e.getPassword(),e.getStatus()));
                }
            }
        }

    }

    public ArrayList<LogTimekeepingWorker> getTimekeepingAWorker(String employee_id){
        ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = TimekeepingWorkerDAO.getInstance().getByEmployeeID(employee_id);

        return logTimekeepingWorkers;
    }

    private ArrayList<LogTimekeepingWorker> getTimekeepingByMonth(ArrayList<LogTimekeepingWorker> logs, String month, String year){
        ArrayList<LogTimekeepingWorker> logFilterByMonth = new ArrayList<LogTimekeepingWorker>();
        for (LogTimekeepingWorker log : logs) {
            String logMonth = log.getDate().toString().split("-")[1];
            String logYear = log.getDate().toString().split("-")[0];
            if (logMonth.equals(month) && logYear.equals(year)){
                logFilterByMonth.add(log);
            }
        }
        return logFilterByMonth;
    }

    private void highlightRow(){
        tableReport.setRowFactory(tv -> new TableRow<WUMWorkerUnitReportRow>() {
            @Override
            protected void updateItem(WUMWorkerUnitReportRow item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty && item != null) {
                    if(item.getStatus() == 0) {
                        setStyle("-fx-background-color: #f8bcbc;");
                    } else {
                        setStyle("");
                    }
                } else {
                    setStyle("");
                }
            }
        });
    }

}
