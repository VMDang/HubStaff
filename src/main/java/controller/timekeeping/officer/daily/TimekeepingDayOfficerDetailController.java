package controller.timekeeping.officer.daily;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ResourceBundle;

import controller.auth.Authentication;
import database.TimekeepingOfficerDAO;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.employee.Employee;
import model.employee.HRManager;
import model.logtimekeeping.LogTimekeepingOfficer;
import utility.TimekeepingUtility;

public class TimekeepingDayOfficerDetailController implements Initializable {
	private Employee employee;
	private LogTimekeepingOfficer log;
	private ObservableList<LogTimekeepingOfficer> logRow = FXCollections.observableArrayList();

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

	private Time time_inInit;
	private Time time_outInit;

	public TimekeepingDayOfficerDetailController(Employee employee, LogTimekeepingOfficer log) {
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
		time_inCol.setCellFactory(column -> new EditingCellOfficer((officer, newValue) -> {
			officer.setTime_in(newValue);
			handleUpdate(officer);
		}));

		time_outCol.setCellFactory(column -> new EditingCellOfficer((officer, newValue) -> {
			officer.setTime_out(newValue);
			handleUpdate(officer);
		}));

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

	@FXML
	void cancelEdit(ActionEvent event) {
		saveBtn.setVisible(false);
		cancelBtn.setVisible(false);
		tableDetail.setEditable(false);
		editBtn.setVisible(true);

		tableDetail.getItems().clear();
		log = new LogTimekeepingOfficer(log.getLogID(), log.getEmployee_id(), log.getDate(), time_inInit, time_outInit, log.isMorning(), log.isAfternoon(), log.getOvertime());

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
		saveLogTimekeepingOfficer(tableDetail.getItems().get(0));
	}

	private void handleUpdate(LogTimekeepingOfficer updateOfficer) {
		if (!logRow.contains(updateOfficer)) {
			logRow.clear();
			logRow.add(updateOfficer);
		}
	}

	private void saveLogTimekeepingOfficer(LogTimekeepingOfficer log) {
		LogTimekeepingOfficer insertOrUpdateLog = new LogTimekeepingOfficer();
		if (log.getTime_in() == null || log.getTime_out() == null) {
			insertOrUpdateLog = new LogTimekeepingOfficer(log.getLogID(), log.getEmployee_id(), log.getDate(), log.getTime_in(), log.getTime_out(), false, false, 0.0f);
		}

		if (log.getTime_in() != null && log.getTime_out() != null) {
			boolean morning = TimekeepingUtility.isMorningOfficer(log.getTime_in(), log.getTime_out());
			boolean afternoon = TimekeepingUtility.isAfternoonOfficer(log.getTime_in(), log.getTime_out());
			float hourEarly = TimekeepingUtility.getHourEarlyOfficer(log.getTime_out());
			float hourLate = TimekeepingUtility.getHourLateOfficer(log.getTime_in());
			float overtime = TimekeepingUtility.getHourOvertimeOfficer(log.getTime_out());

			insertOrUpdateLog = new LogTimekeepingOfficer(log.getLogID(), log.getEmployee_id(), log.getDate(), log.getTime_in(), log.getTime_out(), morning, afternoon, hourLate, hourEarly, overtime);
		}

		if (TimekeepingOfficerDAO.getInstance().getByEmployeeIdAndDate(log.getEmployee_id(), log.getDate()) != null) {
			TimekeepingOfficerDAO.getInstance().update(insertOrUpdateLog);
		} else {
			TimekeepingOfficerDAO.getInstance().insert(insertOrUpdateLog);
		}

		tableDetail.getItems().clear();
		logRow.clear();
		logRow.add(insertOrUpdateLog);
		tableDetail.setItems(logRow);
	}
}
