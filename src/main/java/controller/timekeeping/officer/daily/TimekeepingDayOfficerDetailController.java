package controller.timekeeping.officer.daily;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.employee.Employee;
import model.logtimekeeping.LogTimekeepingOfficer;

public class TimekeepingDayOfficerDetailController implements Initializable {
	private Employee employee;
	private LogTimekeepingOfficer log;

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
    private TableView<LogTimekeepingOfficer> tableDetail;

    @FXML
    private TableColumn<LogTimekeepingOfficer, String> morningCol;

    @FXML
    private TableColumn<LogTimekeepingOfficer, String> afternoonCol;

    @FXML
    private TableColumn<LogTimekeepingOfficer, Float> overtimeCol;

    @FXML
    private TableColumn<LogTimekeepingOfficer, Time> time_inCol;

    @FXML
    private TableColumn<LogTimekeepingOfficer, Time> time_outCol;

	public TimekeepingDayOfficerDetailController(Employee employee, LogTimekeepingOfficer log) {
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

    	ObservableList<LogTimekeepingOfficer> logRow = FXCollections.observableArrayList();
    	logRow.add(log);
    	time_inCol.setCellValueFactory(new PropertyValueFactory<LogTimekeepingOfficer,Time>("time_in"));
    	time_outCol.setCellValueFactory(new PropertyValueFactory<LogTimekeepingOfficer,Time>("time_out"));
    	morningCol.setCellValueFactory(cellData -> {
    		LogTimekeepingOfficer log = cellData.getValue();

			if (log.getDate().compareTo(Date.valueOf(LocalDate.now().toString())) >= 0) {
				return null;
			} else {
				String morning = log.isMorning() ? "Làm" : "Nghỉ";
				return new SimpleStringProperty(morning);
			}
        });
    	afternoonCol.setCellValueFactory(cellData -> {
    		LogTimekeepingOfficer log = cellData.getValue();
			if (log.getDate().compareTo(Date.valueOf(LocalDate.now().toString())) >= 0) {
				return null;
			} else {
				String afternoon = log.isMorning() ? "Làm" : "Nghỉ";
				return new SimpleStringProperty(afternoon);
			}
        });
		overtimeCol.setCellValueFactory(cellData -> {
			LogTimekeepingOfficer log = cellData.getValue();

			if (log.getDate().compareTo(Date.valueOf(LocalDate.now().toString())) >= 0) {
				return null;
			} else {
				return new SimpleFloatProperty(log.getOvertime()).asObject();

			}
		});
    	tableDetail.setItems(logRow);
    }
}
