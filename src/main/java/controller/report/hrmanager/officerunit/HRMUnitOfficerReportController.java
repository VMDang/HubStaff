package controller.report.hrmanager.officerunit;

import config.FXMLNavigation;
import controller.timekeeping.officer.monthly.TimekeepingMonthlyOfficerController;
import database.EmployeeDAO;
import database.TimekeepingOfficerDAO;
import database.UnitDAO;
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
import model.employee.Employee;
import model.employee.Role;
import model.employee.Unit;
import model.employee.officer.Officer;
import model.employee.officer.OfficerUnitManager;
import model.logtimekeeping.LogTimekeepingOfficer;
import utility.TimeUtility;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HRMUnitOfficerReportController implements Initializable {
    private LocalDate today = LocalDate.now();

    private ArrayList<Officer> officers = new ArrayList<>();

    private ObservableList<HRMUnitOfficerReportRow> listRecord = FXCollections.observableArrayList();

    private String unitIdInit = null;
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
    private ChoiceBox<String> unitNameBox;

    @FXML
    private Label unit_idText;

    @FXML
    private Label unit_manager;

    @FXML
    private Label monthLabel;

    @FXML
    private Label num_worker;

    @FXML
    private TableColumn<HRMUnitOfficerReportRow, Integer> index;

    @FXML
    private TableColumn<HRMUnitOfficerReportRow, Float> lateEarlyCol;

    @FXML
    private TableColumn<HRMUnitOfficerReportRow, String> nameCol;

    @FXML
    private TableView<HRMUnitOfficerReportRow> tableReport;

    @FXML
    private TableColumn<HRMUnitOfficerReportRow, String> total_day_workCol;

    @FXML
    private TableColumn<HRMUnitOfficerReportRow, Float> total_overtime_workCol;

    @FXML
    private TableColumn<HRMUnitOfficerReportRow, String> officer_idCol;

    public HRMUnitOfficerReportController() {
    }

    public HRMUnitOfficerReportController(String unitIdInit, String monthInit, String yearInit) {
        this.unitIdInit = unitIdInit;
        this.monthInit = monthInit;
        this.yearInit = yearInit;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Unit> allUnits = UnitDAO.getInstance().getAll();
        ArrayList<String> officerUnits = new ArrayList<>();
        for (Unit unit : allUnits) {
            if (unit.getId().contains("OFF")) {
                officerUnits.add(unit.getId());
            }
        }
        getAllOfficer();

        unitNameBox.getItems().addAll(officerUnits);
        chooseMonth.getItems().addAll(TimeUtility.getListMonth());
        chooseYear.getItems().addAll(TimeUtility.getListYear());
        reloadPage(null);

        if (unitIdInit != null && monthInit != null && yearInit != null) {
            unitNameBox.setValue(unitIdInit);
            chooseMonth.setValue(monthInit);
            chooseYear.setValue(yearInit);
            search(null);
        }

        index.setCellValueFactory(index -> new ReadOnlyObjectWrapper<Integer>(tableReport.getItems().indexOf(index.getValue())+1));
        nameCol.setCellValueFactory(new PropertyValueFactory<HRMUnitOfficerReportRow, String>("name"));
        officer_idCol.setCellValueFactory(new PropertyValueFactory<HRMUnitOfficerReportRow, String>("officer_id"));
        total_day_workCol.setCellValueFactory(new PropertyValueFactory<HRMUnitOfficerReportRow, String>("total_day_work"));
        total_overtime_workCol.setCellValueFactory(new PropertyValueFactory<HRMUnitOfficerReportRow, Float>("total_overtime"));
        lateEarlyCol.setCellValueFactory(new PropertyValueFactory<HRMUnitOfficerReportRow, Float>("countLateEarly"));
        tableReport.setItems(listRecord);
        highlightRow();

        tableReport.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                HRMUnitOfficerReportRow officerRow = tableReport.getSelectionModel().getSelectedItem();

                if (officerRow != null) {
                    try {
                        Employee employee = EmployeeDAO.getInstance().getById(officerRow.getOfficer_id());

                        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLNavigation.TIMEKEEPING_MONTHLY_OFFICER_VIEW));
                        loader.setControllerFactory(e -> new TimekeepingMonthlyOfficerController(employee, chooseMonth.getValue(), chooseYear.getValue()));
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
    void exportExcel(ActionEvent event) {

    }

    @FXML
    void reloadPage(ActionEvent event) {
        unitNameBox.setValue("ID Unit");
        chooseMonth.setValue(today.format(DateTimeFormatter.ofPattern("MM")));
        chooseYear.setValue(today.format(DateTimeFormatter.ofPattern("yyyy")));
        unit_idText.setText("");
        unit_manager.setText("");
        department.setText("");
        num_worker.setText("");
        monthLabel.setText("Tháng " + today.format(DateTimeFormatter.ofPattern("MM/yyyy")));
        listRecord.clear();
        tableReport.setItems(listRecord);
    }

    @FXML
    void search(ActionEvent event) {
        String unit_id_text = unitNameBox.getValue().toString();
        if(unit_id_text.equals("ID Unit")) {
            showUnitNotFound("Bạn chưa chọn mã đơn vị");
        } else {
            unit_idText.setText(unit_id_text);
            setListRecord();

            monthLabel.setText("Tháng " + chooseMonth.getValue() + "/" + chooseYear.getValue());
            num_worker.setText(String.valueOf(listRecord.size()));
            tableReport.setItems(listRecord);
        }
    }

    private void showUnitNotFound(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ID of Unit");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void setListRecord()
    {
        listRecord.clear();
        String unit_id = unitNameBox.getValue().toString();
        String month = chooseMonth.getValue();
        String year = chooseYear.getValue();

        ArrayList<Officer> officersUnit = getAllOfficerUnit(unit_id);

        for (Officer officer : officersUnit) {
            ArrayList<LogTimekeepingOfficer> logs = getTimekeepingAOfficer(officer.getId());
            ArrayList<LogTimekeepingOfficer> logTimekeepingByMonth = getTimekeepingByMonth(logs, month, year);

            if (logTimekeepingByMonth.isEmpty()) {
                continue;
            }

            float countLateEarly = 0.0f;
            float total_day_work = 0.0f;
            float total_overtime = 0.0f;

            for (LogTimekeepingOfficer log : logTimekeepingByMonth) {
                if (log.isMorning()) total_day_work += 0.5;
                if (log.isAfternoon()) total_day_work += 0.5;

                countLateEarly += log.getHour_early() + log.getHour_late();
                total_overtime += log.getOvertime();
            }

            if(!(officer.getStatus() == 0 && logTimekeepingByMonth.isEmpty()))  {
                listRecord.add(new HRMUnitOfficerReportRow(officer.getName(), officer.getId(), officer.getUnit_id(), String.valueOf(total_day_work + "/21"), total_overtime, countLateEarly, officer.getStatus()));
            }
        }

    }

    private ArrayList<Officer> getAllOfficerUnit(String unit_id)
    {
        ArrayList<Officer> officersUnit = new ArrayList<>();
        for (Officer officer : officers) {
            if (officer.getUnit_id().equals(unit_id)) {
                officersUnit.add(officer);
                if (officer.getRole_id() == Role.OfficerUnitManager.getId()) {
                    unit_manager.setText(officer.getName());
                    department.setText(officer.getDepartment());
                }
            }
        }
        return officersUnit;
    }

    private void getAllOfficer() {
        ArrayList<Employee> employees = EmployeeDAO.getInstance().getAll();

        for (Employee e : employees) {
            if (e.getRole_id() == Role.Officer.getId()) {
                officers.add(new Officer(e.getId(), e.getName(), e.getIdentifier(), e.getBirthday(), e.getAddress(),
                        e.getGender(), e.getPhone(), e.getDepartment(), e.getUnit_id(), e.getPassword(), e.getStatus()));
            }

            if (e.getRole_id() == Role.OfficerUnitManager.getId()) {
                officers.add(new OfficerUnitManager(e.getId(), e.getName(), e.getIdentifier(), e.getBirthday(), e.getAddress(),
                        e.getGender(), e.getPhone(), e.getDepartment(), e.getUnit_id(), e.getPassword(), e.getStatus()));
            }
        }
    }

    private ArrayList<LogTimekeepingOfficer> getTimekeepingAOfficer(String employee_id) {
        return TimekeepingOfficerDAO.getInstance().getByEmployeeID(employee_id);
    }

    private ArrayList<LogTimekeepingOfficer> getTimekeepingByMonth(ArrayList<LogTimekeepingOfficer> logs, String month, String year) {
        ArrayList<LogTimekeepingOfficer> result = new ArrayList<>();
        for (LogTimekeepingOfficer log : logs) {
           String logMonth = log.getDate().toString().split("-")[1];
           String logYear = log.getDate().toString().split("-")[0];
              if (logMonth.equals(month) && logYear.equals(year)) {
                result.add(log);
              }
        }
        return result;
    }

    private void highlightRow() {
        tableReport.setRowFactory(tv -> new TableRow<HRMUnitOfficerReportRow>() {
            @Override
            protected void updateItem(HRMUnitOfficerReportRow item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty && item != null) {
                    if(item.getStatus() == 0) {
                        setStyle("-fx-background-color: #f8bcbc;");
                    }
                    else {
                        setStyle("");
                    }
                }
                else {
                    setStyle("");
                }
            }
        });
    }
}
