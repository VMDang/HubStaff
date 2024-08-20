package controller.timekeeping.worker.daily;

import java.net.URL;
import java.sql.Time;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.employee.Employee;
import model.logtimekeeping.LogTimekeepingWorker;

public class TimekeepingDayWorkerDetailController implements Initializable {
    private Employee employee;
    private LogTimekeepingWorker log;

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

    public TimekeepingDayWorkerDetailController(Employee employee, LogTimekeepingWorker log) {
        this.employee = employee;
        this.log = log;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        department.setText(employee.getDepartment());
        employeeID.setText(employee.getId());
        user_name.setText(employee.getName());
        unit_id.setText(employee.getUnit_id());

        dateLabel.setText(log.getDate().toString());
        ObservableList<LogTimekeepingWorker> logRow = FXCollections.observableArrayList();
        logRow.add(log);
        time_inCol.setCellValueFactory(new PropertyValueFactory<LogTimekeepingWorker, Time>("time_in"));
        time_outCol.setCellValueFactory(new PropertyValueFactory<LogTimekeepingWorker, Time>("time_out"));
        shift1Col.setCellValueFactory(new PropertyValueFactory<LogTimekeepingWorker, Float>("shift1"));
        shift2Col.setCellValueFactory(new PropertyValueFactory<LogTimekeepingWorker, Float>("shift2"));
        shift3Col.setCellValueFactory(new PropertyValueFactory<LogTimekeepingWorker, Float>("shift3"));

        tableDetail.setItems(logRow);
    }
}
