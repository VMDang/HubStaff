package controller.timekeeping.worker.daily;

import java.net.URL;
import java.sql.Time;
import java.util.ResourceBundle;

import controller.auth.Authentication;
import database.TimekeepingWorkerDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.employee.Employee;
import model.employee.HRManager;
import model.logtimekeeping.LogTimekeepingWorker;
import utility.TimekeepingUtility;

public class TimekeepingDayWorkerDetailController implements Initializable {
    private Employee employee;
    private LogTimekeepingWorker log;
    private ObservableList<LogTimekeepingWorker> logRow = FXCollections.observableArrayList();

    @FXML
    private Label department;

    @FXML
    private Label employeeID;

    @FXML
    private Label user_name;

    @FXML
    private Label unit_id;

    @FXML
    private Label dateLabel;

    @FXML
    private Button editBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private TableView<LogTimekeepingWorker> tableDetail;

    @FXML
    private TableColumn<LogTimekeepingWorker, Float> shift1Col;

    @FXML
    private TableColumn<LogTimekeepingWorker, Float> shift2Col;

    @FXML
    private TableColumn<LogTimekeepingWorker, Float> shift3Col;

    @FXML
    private TableColumn<LogTimekeepingWorker, Time> time_inCol;

    @FXML
    private TableColumn<LogTimekeepingWorker, Time> time_outCol;

    private Time time_inInit;
    private Time time_outInit;

    public TimekeepingDayWorkerDetailController(Employee employee, LogTimekeepingWorker log) {
        this.employee = employee;
        this.log = log;
        time_inInit = log.getTime_in();
        time_outInit = log.getTime_out();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        department.setText(employee.getDepartment());
        employeeID.setText(employee.getId());
        user_name.setText(employee.getName());
        unit_id.setText(employee.getUnit_id());
        dateLabel.setText(log.getDate().toString());

        cancelBtn.setVisible(false);
        saveBtn.setVisible(false);
        editBtn.setVisible(false);
        if (Authentication.getInstance().getAuthentication() instanceof HRManager) {
            editBtn.setVisible(true);
        }

        logRow.add(log);
        time_inCol.setCellFactory(column -> new EditingCellWorker((worker, newValue) -> {
            worker.setTime_in(newValue);
            handleUpdate(worker);
        }));

        time_outCol.setCellFactory(column -> new EditingCellWorker((worker, newValue) -> {
            worker.setTime_out(newValue);
            handleUpdate(worker);
        }));

        time_inCol.setCellValueFactory(new PropertyValueFactory<LogTimekeepingWorker, Time>("time_in"));
        time_outCol.setCellValueFactory(new PropertyValueFactory<LogTimekeepingWorker, Time>("time_out"));
        shift1Col.setCellValueFactory(new PropertyValueFactory<LogTimekeepingWorker, Float>("shift1"));
        shift2Col.setCellValueFactory(new PropertyValueFactory<LogTimekeepingWorker, Float>("shift2"));
        shift3Col.setCellValueFactory(new PropertyValueFactory<LogTimekeepingWorker, Float>("shift3"));

        tableDetail.setItems(logRow);
    }

    @FXML
    void cancelEdit(ActionEvent event) {
        saveBtn.setVisible(false);
        cancelBtn.setVisible(false);
        tableDetail.setEditable(false);
        editBtn.setVisible(true);

        tableDetail.getItems().clear();
        log = new LogTimekeepingWorker(log.getLogID(), log.getEmployee_id(), log.getDate(), time_inInit, time_outInit, log.getShift1(), log.getShift2(), log.getShift3());

        logRow.clear();
        logRow.add(log);
        tableDetail.setItems(logRow);
    }

    @FXML
    void edit(ActionEvent event) {
        editBtn.setVisible(false);
        saveBtn.setVisible(true);
        cancelBtn.setVisible(true);
        tableDetail.setEditable(true);
    }

    @FXML
    void save(ActionEvent event) {
        saveBtn.setVisible(false);
        cancelBtn.setVisible(false);
        tableDetail.setEditable(false);
        editBtn.setVisible(true);
        saveLogTimekeepingWorker(tableDetail.getItems().get(0));
    }

    private void handleUpdate(LogTimekeepingWorker updatedWorker) {
        if (!logRow.contains(updatedWorker)) {
            logRow.clear();
            logRow.add(updatedWorker);
        }
    }

    private void saveLogTimekeepingWorker(LogTimekeepingWorker log) {
        LogTimekeepingWorker insertOrUpdateLog = new LogTimekeepingWorker();
        if (log.getTime_in() == null || log.getTime_out() == null) {
            insertOrUpdateLog = new LogTimekeepingWorker(log.getLogID(), log.getEmployee_id(), log.getDate(), log.getTime_in(), log.getTime_out(), 0.0f, 0.0f, 0.0f);
        }

        if (log.getTime_in() != null && log.getTime_out() != null) {
            float shift1 = TimekeepingUtility.getHourShift1Worker(log.getTime_in(), log.getTime_out());
            float shift2 = TimekeepingUtility.getHourShift2Worker(log.getTime_in(), log.getTime_out());
            float shift3 = TimekeepingUtility.getHourShift3Worker(log.getTime_in(), log.getTime_out());

            insertOrUpdateLog = new LogTimekeepingWorker(log.getLogID(), log.getEmployee_id(), log.getDate(), log.getTime_in(), log.getTime_out(), shift1, shift2, shift3);
        }

        if (TimekeepingWorkerDAO.getInstance().getByEmployeeIdAndDate(log.getEmployee_id(), log.getDate()) != null) {
            TimekeepingWorkerDAO.getInstance().update(insertOrUpdateLog);
        } else {
            TimekeepingWorkerDAO.getInstance().insert(insertOrUpdateLog);
        }

        tableDetail.getItems().clear();
        logRow.clear();
        logRow.add(insertOrUpdateLog);
        tableDetail.setItems(logRow);
    }
}



