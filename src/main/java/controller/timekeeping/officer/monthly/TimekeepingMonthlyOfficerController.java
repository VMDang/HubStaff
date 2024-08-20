package controller.timekeeping.officer.monthly;

import config.Config;
import config.FXMLNavigation;
import controller.auth.Authentication;
import controller.report.hrmanager.officerunit.HRMUnitOfficerReportController;
import controller.report.officerunitmanager.OUMOfficerUnitReportController;
import controller.timekeeping.officer.daily.TimekeepingDayOfficerDetailController;
import database.RoleDAO;
import database.TimekeepingOfficerDAO;
import database.UnitDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.employee.Employee;
import model.employee.HRManager;
import model.employee.Unit;
import model.employee.officer.OfficerUnitManager;
import model.logtimekeeping.LogTimekeepingOfficer;
import utility.TimeUtility;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TimekeepingMonthlyOfficerController implements Initializable {
    private LocalDate today = LocalDate.now();
    private Employee employee = Authentication.getInstance().getAuthentication();
    private String monthInit = null;
    private String yearInit = null;

    @FXML
    private TableColumn<TimekeepingOfficerTableRow, Float> amount_workCol;

    @FXML
    private AnchorPane basePane;

    @FXML
    private ChoiceBox<String> chooseMonth;

    @FXML
    private ChoiceBox<String> chooseYear;

    @FXML
    private TableColumn<TimekeepingOfficerTableRow, Date> dateCol;

    @FXML
    private Label department;

    @FXML
    private Label employeeID;

    @FXML
    private TableColumn<SummaryOfficerTableRow, Float> lateEarlyCol;

    @FXML
    private Label monthLabel;

    @FXML
    private TableColumn<TimekeepingOfficerTableRow, Float> overtimeCol;

    @FXML
    private Button reloadPageBtn;

    @FXML
    private Label role;

    @FXML
    private Button searchBtn;

    @FXML
    private Button backBtn;

    @FXML
    private TableColumn<TimekeepingOfficerTableRow, String> statusCol;

    @FXML
    private TableView<SummaryOfficerTableRow> tableSummary;

    @FXML
    private TableView<TimekeepingOfficerTableRow> tableTimekeepingMonth;

    @FXML
    private TableColumn<TimekeepingOfficerTableRow, Time> time_inCol;

    @FXML
    private TableColumn<TimekeepingOfficerTableRow, Time> time_outCol;

    @FXML
    private TableColumn<SummaryOfficerTableRow, Float> total_amount_workCol;

    @FXML
    private TableColumn<SummaryOfficerTableRow, Integer> total_day_workCol;

    @FXML
    private TableColumn<SummaryOfficerTableRow, Float> total_overtimeCol;

    @FXML
    private Label unit_id;

    @FXML
    private Label unit_name;

    @FXML
    private Label user_name;

    private ObservableList<TimekeepingOfficerTableRow> LogTimekeepingMonthList = FXCollections.observableArrayList();
    private ObservableList<SummaryOfficerTableRow> summaryRows = FXCollections.observableArrayList();
    private ArrayList<LogTimekeepingOfficer> logsTO;

    public TimekeepingMonthlyOfficerController() {
        this.employee = Authentication.getInstance().getAuthentication();
    }

    public TimekeepingMonthlyOfficerController(Employee employee, String monthInit, String yearInit) {
        this.employee = employee;
        this.monthInit = monthInit;
        this.yearInit = yearInit;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (monthInit != null && yearInit != null) {
            backBtn.setVisible(true);
        } else {
            backBtn.setVisible(false);
            monthInit = today.toString().split("-")[1];
            yearInit = today.toString().split("-")[0];
        }

        this.logsTO = TimekeepingOfficerDAO.getInstance().getByEmployeeID(employee.getId());

        String roleText = RoleDAO.getInstance().getById(String.valueOf(employee.getRole_id())).getName();
        role.setText(roleText);
        department.setText(employee.getDepartment());
        employeeID.setText(employee.getId());
        user_name.setText(employee.getName());
        unit_id.setText(employee.getUnit_id());
        Unit unit = UnitDAO.getInstance().getById(employee.getUnit_id());
        unit_name.setText(unit.getName());

        chooseMonth.getItems().addAll(TimeUtility.getListMonth());
        chooseMonth.setValue(monthInit);
        chooseYear.getItems().addAll(TimeUtility.getListYear());
        chooseYear.setValue(yearInit);
        monthLabel.setText("Tháng " + monthInit + "/" + yearInit);

        getLogByMonth(monthInit, yearInit);
        setDataSummaryTableRow(LogTimekeepingMonthList);
        dateCol.setCellValueFactory(new PropertyValueFactory<TimekeepingOfficerTableRow, Date>("date"));
        time_inCol.setCellValueFactory(new PropertyValueFactory<TimekeepingOfficerTableRow, Time>("time_in"));
        time_outCol.setCellValueFactory(new PropertyValueFactory<TimekeepingOfficerTableRow, Time>("time_out"));
        amount_workCol.setCellValueFactory(new PropertyValueFactory<TimekeepingOfficerTableRow, Float>("amount_work"));
        overtimeCol.setCellValueFactory(new PropertyValueFactory<TimekeepingOfficerTableRow, Float>("overtime"));
        statusCol.setCellValueFactory(new PropertyValueFactory<TimekeepingOfficerTableRow, String>("status"));
        tableTimekeepingMonth.setItems(LogTimekeepingMonthList);
        highlightRow();

        total_day_workCol.setCellValueFactory(new PropertyValueFactory<SummaryOfficerTableRow, Integer>("total_day_work"));
        total_amount_workCol.setCellValueFactory(new PropertyValueFactory<SummaryOfficerTableRow, Float>("total_amount_work"));
        total_overtimeCol.setCellValueFactory(new PropertyValueFactory<SummaryOfficerTableRow, Float>("total_overtime"));
        lateEarlyCol.setCellValueFactory(new PropertyValueFactory<SummaryOfficerTableRow, Float>("total_late_early"));
        tableSummary.setItems(summaryRows);

        tableTimekeepingMonth.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                TimekeepingOfficerTableRow selectedItem = tableTimekeepingMonth.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    showDetailPopupOfficer(selectedItem);
                }
            }
        });
    }

    @FXML
    void reloadPage(ActionEvent event) {
        String month = today.toString().split("-")[1];
        String year = today.toString().split("-")[0];

        chooseMonth.setValue(month);
        chooseYear.setValue(year);
        monthLabel.setText("Tháng " + month + "/" + year);

        getLogByMonth(month, year);
        tableTimekeepingMonth.setItems(LogTimekeepingMonthList);

        setDataSummaryTableRow(LogTimekeepingMonthList);
        tableSummary.setItems(summaryRows);
    }

    @FXML
    void searchTable(ActionEvent event) {
        String month = chooseMonth.getValue();
        String year = chooseYear.getValue();
        monthLabel.setText("Tháng " + month + "/" + year);

        getLogByMonth(month, year);
        tableTimekeepingMonth.setItems(LogTimekeepingMonthList);

        setDataSummaryTableRow(LogTimekeepingMonthList);
        tableSummary.setItems(summaryRows);
    }

    @FXML
    public void backPrePage(ActionEvent event) {
        Employee auth = Authentication.getInstance().getAuthentication();

        try {
            if (auth instanceof HRManager) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLNavigation.HRM_UNIT_OFFICER_VIEW));
                loader.setControllerFactory(c -> new HRMUnitOfficerReportController(employee.getUnit_id(), monthInit, yearInit));
                Node node = loader.load();
                basePane.getChildren().setAll(node);
            } else if (auth instanceof OfficerUnitManager) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLNavigation.OUM_OFFICER_UNIT_REPORT_VIEW));
                loader.setControllerFactory(c -> new OUMOfficerUnitReportController(monthInit, yearInit));
                Node node = loader.load();
                basePane.getChildren().setAll(node);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void exportExcel(ActionEvent event) {

    }

    public void getLogByMonth(String month, String year) {
        LogTimekeepingMonthList = FXCollections.observableArrayList();
        ArrayList<Integer> listDayMonth = TimeUtility.getListDayMonth(Integer.parseInt(year));
        for (int i = 1; i <= listDayMonth.get(Integer.parseInt(month)-1); i++) {
            String dateFormat = year + "-" + month + "-" + (i<10 ? "0"+i : ""+i);
            boolean checkExistLog = false;

            for (LogTimekeepingOfficer log: logsTO) {
                if (log.getDate().toString().contains(dateFormat))
                {
                    Date date = log.getDate();
                    Time time_in = log.getTime_in();
                    Time time_out = log.getTime_out();

                    float amount_work = 0.0f;
                    if (log.isMorning()) amount_work += 0.5f;
                    if (log.isAfternoon()) amount_work += 0.5f;
                    float overtime = log.getOvertime();

                    String status = "";
                    if((time_in.compareTo(Time.valueOf(Config.OFFICER_START_MORNING)) > 0 && time_in.compareTo(Time.valueOf(Config.OFFICER_END_MORNING)) < 0)
                            || (time_in.compareTo(Time.valueOf(Config.OFFICER_START_AFTERNOON)) > 0 && time_in.compareTo(Time.valueOf(Config.OFFICER_END_AFTERNOON)) < 0)) {
                        status += "Đi muộn ";
                    }

                    if((time_out.compareTo(Time.valueOf(Config.OFFICER_START_MORNING)) > 0 && time_out.compareTo(Time.valueOf(Config.OFFICER_END_MORNING)) < 0)
                            || (time_out.compareTo(Time.valueOf(Config.OFFICER_START_AFTERNOON)) > 0 && time_out.compareTo(Time.valueOf(Config.OFFICER_END_AFTERNOON)) < 0)) {
                        status +="Về sớm ";
                    }

                    if(time_in.compareTo(Time.valueOf(Config.OFFICER_START_MORNING)) <= 0 && time_out.compareTo(Time.valueOf(Config.OFFICER_END_AFTERNOON)) >= 0) status = "Đạt";

                    LogTimekeepingMonthList.add(new TimekeepingOfficerTableRow(date, time_in, time_out, amount_work, overtime, status, log.isMorning(), log.isAfternoon(), log.getHour_late(), log.getHour_early()));
                    checkExistLog = true;
                    break;
                }
            }

            if(!checkExistLog) {
                if(Date.valueOf(dateFormat).compareTo(Date.valueOf(today.toString()))<0) {
                    LogTimekeepingMonthList.add(new TimekeepingOfficerTableRow(Date.valueOf(dateFormat), "Nghỉ"));
                } else {
                    LogTimekeepingMonthList.add(new TimekeepingOfficerTableRow(Date.valueOf(dateFormat), "Chưa làm"));
                }

            }
        }
    }

    public void setDataSummaryTableRow(ObservableList<TimekeepingOfficerTableRow> LogInMonth) {
        int totalDayWork = 0;
        float totalAmountWork = 0.0f;
        float totalOvertime = 0.0f;
        float totalLateEarly = 0.0f;
        for (TimekeepingOfficerTableRow row: LogInMonth) {
            if(!row.getStatus().equals("Nghỉ") && !row.getStatus().equals("Chưa làm")) {
                totalDayWork++;
                totalAmountWork += row.getAmount_work();
                totalOvertime += row.getOvertime();
                totalLateEarly += row.getHour_late() + row.getHour_early();
            }
        }

        summaryRows = FXCollections.observableArrayList();
        summaryRows.add(new SummaryOfficerTableRow(totalDayWork, totalAmountWork, totalOvertime, totalLateEarly));
    }

    public void showDetailPopupOfficer(TimekeepingOfficerTableRow selectedItem) {
        try {
            LogTimekeepingOfficer logDetail = new LogTimekeepingOfficer("", employee.getId(), selectedItem.getDate(), selectedItem.getTime_in(), selectedItem.getTime_out(),
                    selectedItem.isMorning(), selectedItem.isAfternoon(), selectedItem.getHour_late(), selectedItem.getHour_early(), selectedItem.getOvertime());

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLNavigation.TIMEKEEPING_DAY_OFFICER_DETAIL_VIEW));
            fxmlLoader.setControllerFactory(c -> new TimekeepingDayOfficerDetailController(employee, logDetail));
            Parent detailRoot = fxmlLoader.load();

            Stage detailStage = new Stage();
            detailStage.setTitle("Chi tiết ngày công");
            detailStage.setScene(new Scene(detailRoot));
            detailStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void highlightRow() {
        tableTimekeepingMonth.setRowFactory(tv -> new TableRow<TimekeepingOfficerTableRow>() {
            @Override
            protected void updateItem(TimekeepingOfficerTableRow item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty && item != null) {
                    if (item.getStatus().equals("Nghỉ")) {
                        setStyle("-fx-background-color: #f8bcbc;");
                    } else if (item.getStatus().contains("Đi muộn") || item.getStatus().contains("Về sớm")) {
                        setStyle("-fx-background-color: #ffecc9;");
                    } else setStyle("");
                }else setStyle("");
            }
        });
    }
}
