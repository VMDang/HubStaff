package controller.report.officerunitmanager;

import config.FXMLNavigation;
import controller.auth.Authentication;
import controller.report.hrmanager.officerunit.HRMUnitOfficerReportRow;
import controller.timekeeping.officer.monthly.TimekeepingMonthlyOfficerController;
import database.EmployeeDAO;
import database.TimekeepingOfficerDAO;
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
import model.employee.officer.Officer;
import model.employee.officer.OfficerUnitManager;
import model.logtimekeeping.LogTimekeepingOfficer;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utility.TimeUtility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OUMOfficerUnitReportController implements Initializable {
    private LocalDate today = LocalDate.now();

    private ObservableList<OUMOfficerUnitReportRow> listRecord = FXCollections.observableArrayList();

    private ArrayList<Officer> officersUnit = new ArrayList<>();

    private String monthInit = null;

    private String yearInit = null;

    @FXML
    private AnchorPane basePane;

    @FXML
    private ChoiceBox<String> chooseMonth;

    @FXML
    private ChoiceBox<String> chooseYear;

    @FXML
    private Label department;

    @FXML
    private Label unit_id;

    @FXML
    private Label unit_manager;

    @FXML
    private Label num_officer;

    @FXML
    private Label monthLabel;

    @FXML
    private TableView<OUMOfficerUnitReportRow> tableReport;

    @FXML
    private TableColumn<OUMOfficerUnitReportRow, Integer> index;

    @FXML
    private TableColumn<OUMOfficerUnitReportRow, Float> lateEarlyCol;

    @FXML
    private TableColumn<OUMOfficerUnitReportRow, String> nameCol;

    @FXML
    private TableColumn<OUMOfficerUnitReportRow, String> officer_idCol;

    @FXML
    private TableColumn<OUMOfficerUnitReportRow, String> total_day_workCol;

    @FXML
    private TableColumn<OUMOfficerUnitReportRow, Float> total_overtime_workCol;

    public OUMOfficerUnitReportController() {
    }

    public OUMOfficerUnitReportController(String monthInit, String yearInit) {
        this.monthInit = monthInit;
        this.yearInit = yearInit;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Employee authentication = Authentication.getInstance().getAuthentication();
        getAllOfficerUnit(authentication.getUnit_id());
        int activeOfficer = 0;
        for (Officer officer : officersUnit) {
            if (officer.getStatus() == 1) {
                activeOfficer++;
            }
        }

        num_officer.setText(String.valueOf(activeOfficer));
        unit_id.setText(authentication.getUnit_id());
        department.setText(authentication.getDepartment());
        unit_manager.setText(authentication.getName());

        chooseMonth.getItems().addAll(TimeUtility.getListMonth());
        chooseYear.getItems().addAll(TimeUtility.getListYear());

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
        index.setCellValueFactory(index -> new ReadOnlyObjectWrapper<Integer>(tableReport.getItems().indexOf(index.getValue()) + 1));
        nameCol.setCellValueFactory(new PropertyValueFactory<OUMOfficerUnitReportRow, String>("name"));
        officer_idCol.setCellValueFactory(new PropertyValueFactory<OUMOfficerUnitReportRow, String>("officer_id"));
        total_day_workCol.setCellValueFactory(new PropertyValueFactory<OUMOfficerUnitReportRow, String>("total_day_work"));
        total_overtime_workCol.setCellValueFactory(new PropertyValueFactory<OUMOfficerUnitReportRow, Float>("total_overtime"));
        lateEarlyCol.setCellValueFactory(new PropertyValueFactory<OUMOfficerUnitReportRow, Float>("countLateEarly"));
        tableReport.setItems(listRecord);

        highlightRow();

        tableReport.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                OUMOfficerUnitReportRow selected = tableReport.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    try {
                        Employee employee = EmployeeDAO.getInstance().getById(selected.getOfficer_id());

                        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLNavigation.TIMEKEEPING_MONTHLY_OFFICER_VIEW));
                        loader.setControllerFactory(c -> new TimekeepingMonthlyOfficerController(employee, chooseMonth.getValue(), chooseYear.getValue()));
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
            for (OUMOfficerUnitReportRow row : listRecord) {
                Row r = sheet.createRow(rowIndex);
                r.createCell(0).setCellValue(rowIndex);
                r.createCell(1).setCellValue(row.getName());
                r.createCell(2).setCellValue(row.getOfficer_id());
                r.createCell(3).setCellValue(row.getTotal_day_work());
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

        for (Officer officer : officersUnit) {
            ArrayList<LogTimekeepingOfficer> logs = getTimekeepingAOfficer(officer.getId());
            ArrayList<LogTimekeepingOfficer> logsFilterByMonth = getTimekeepingByMonth(logs, month, year);

            if (logsFilterByMonth.isEmpty()) {
                continue;
            }

            float countLateEarly = 0.0f;
            float total_day_work = 0.0f;
            float total_overtime = 0.0f;

            for (LogTimekeepingOfficer log : logsFilterByMonth) {
                if (log.isMorning()) total_day_work += 0.5;
                if (log.isAfternoon()) total_day_work += 0.5;

                countLateEarly += log.getHour_early() + log.getHour_late();
                total_overtime += log.getOvertime();
            }

            if(!(officer.getStatus() == 0 && logsFilterByMonth.isEmpty())) {
                listRecord.add(new OUMOfficerUnitReportRow(officer.getName(), officer.getId(), String.valueOf(total_day_work + "/21"), total_overtime, countLateEarly, officer.getStatus()));
            }
        }
    }

    private void getAllOfficerUnit(String unit_id) {
        ArrayList<Employee> employees = EmployeeDAO.getInstance().getAll();
        for (Employee e : employees) {
            if (e.getUnit_id().equals(unit_id)) {
                if (e.getRole_id() == Role.Officer.getId()) {
                    officersUnit.add(new Officer(e.getId(), e.getName(), e.getIdentifier(), e.getBirthday(), e.getAddress(),
                            e.getGender(), e.getPhone(), e.getDepartment(), e.getUnit_id(), e.getPassword(), e.getStatus()));
                }
                if (e.getRole_id() == Role.OfficerUnitManager.getId()) {
                    officersUnit.add(new OfficerUnitManager(e.getId(), e.getName(), e.getIdentifier(), e.getBirthday(), e.getAddress(),
                            e.getGender(), e.getPhone(), e.getDepartment(), e.getUnit_id(), e.getPassword(), e.getStatus()));
                }
            }
        }
    }

    private ArrayList<LogTimekeepingOfficer> getTimekeepingByMonth(ArrayList<LogTimekeepingOfficer> logs, String month, String year) {
        ArrayList<LogTimekeepingOfficer> logFilterByMonth = new ArrayList<>();
        for (LogTimekeepingOfficer log : logs) {
            String logMonth = log.getDate().toString().split("-")[1];
            String logYear = log.getDate().toString().split("-")[0];
            if (logMonth.equals(month) && logYear.equals(year)){
                logFilterByMonth.add(log);
            }
        }
        return logFilterByMonth;
    }

    private ArrayList<LogTimekeepingOfficer> getTimekeepingAOfficer(String employee_id) {
        ArrayList<LogTimekeepingOfficer> logs = TimekeepingOfficerDAO.getInstance().getByEmployeeID(employee_id);
        return logs;
    }

    private void highlightRow(){
        tableReport.setRowFactory(tv -> new TableRow<OUMOfficerUnitReportRow>() {
            @Override
            protected void updateItem(OUMOfficerUnitReportRow item, boolean empty) {
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
