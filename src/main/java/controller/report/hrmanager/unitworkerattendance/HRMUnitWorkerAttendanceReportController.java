package controller.report.hrmanager.unitworkerattendance;

import java.net.URL;

import java.sql.Time;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import config.Config;
import database.EmployeeDAO;
import database.TimekeepingWorkerDAO;
import database.UnitDAO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;

import model.employee.Role;
import model.employee.Unit;
import model.employee.worker.WorkerUnitManager;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;	
import java.io.IOException;

import model.employee.Employee;
import model.employee.worker.Worker;
import model.logtimekeeping.LogTimekeepingWorker;

public class HRMUnitWorkerAttendanceReportController implements Initializable{

	public LocalDate today = LocalDate.now();

	private ObservableList<HRMUnitWorkerAttendanceReportRow> listRecord = FXCollections.observableArrayList();
	private ArrayList<Worker> workers = new ArrayList<Worker>();

	@FXML
    private AnchorPane basePane;

	@FXML
    private ChoiceBox<String> chooseMonth;

	@FXML
    private ChoiceBox<String> chooseYear;

	@FXML
	private Label unit_idText;

	@FXML
	private Label unit_manager;
	
	@FXML
	private Label department;
	
	@FXML
	private Label num_worker;

	@FXML
    private ChoiceBox<String> unitNameBox;

	@FXML
    private Button searchBtn;

	@FXML
	private Button export_excel;

	@FXML
	private Label monthLabel;

	@FXML
	private TableView<HRMUnitWorkerAttendanceReportRow> tableReport;

	@FXML
	private TableColumn<HRMUnitWorkerAttendanceReportRow, Integer> index;

	@FXML
	private TableColumn<HRMUnitWorkerAttendanceReportRow, String> nameCol;

	@FXML
	private TableColumn<HRMUnitWorkerAttendanceReportRow, String> worker_idCol;

	@FXML
	private TableColumn<HRMUnitWorkerAttendanceReportRow, Integer> lateEarlyCol;

	@FXML
	private TableColumn<HRMUnitWorkerAttendanceReportRow, String> total_hour_workCol;

	@FXML
	private TableColumn<HRMUnitWorkerAttendanceReportRow, String> total_overtime_workCol;

	String[] listMonth = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
	String[] listYear = {"2023", "2022", "2021", "2020"};

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

	@FXML
	void export_excel(ActionEvent event) throws IOException {
	    DirectoryChooser directoryChooser = new DirectoryChooser();
	    directoryChooser.setTitle("Choose Directory to Save Attendance Report");

	    File selectedDirectory = directoryChooser.showDialog(basePane.getScene().getWindow());
	    if (selectedDirectory != null) {
	        String directoryPath = selectedDirectory.getAbsolutePath();

	        Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("Attendance Report");

	        Row headerRow = sheet.createRow(0);
	        headerRow.createCell(0).setCellValue("STT");
	        headerRow.createCell(1).setCellValue("Worker ID");
	        headerRow.createCell(2).setCellValue("Name");
	        headerRow.createCell(3).setCellValue("Total Hours Work");
	        headerRow.createCell(4).setCellValue("Total Overtime Work");
			headerRow.createCell(5).setCellValue("Total Late/Early");

	        int rowIndex = 1;
	        for (HRMUnitWorkerAttendanceReportRow row : listRecord) {
	            Row dataRow = sheet.createRow(rowIndex);
	            dataRow.createCell(0).setCellValue(rowIndex);
	            dataRow.createCell(1).setCellValue(row.getWorker_id());
	            dataRow.createCell(2).setCellValue(row.getName());
	            dataRow.createCell(3).setCellValue(row.getTotal_hour_work());
	            dataRow.createCell(4).setCellValue(row.getTotal_overtime());
				dataRow.createCell(5).setCellValue(row.getCountLateEarly());
	            rowIndex++;
	        }

	        String fileName = "Attendance_Report_" + chooseMonth.getValue() + "_" + chooseYear.getValue() + "_" + unitNameBox.getValue().toString() + ".xlsx";
	        String filePath = directoryPath + File.separator + fileName;

	        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
	            workbook.write(fileOutputStream);
	            showSuccessExport("Xuất báo cáo thành công!");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        workbook.close();
	    }
	}

	private void showSuccessExport(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Export report");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

	private void showUnitNotFound(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("ID of Unit");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
	}
	
	private void highlightRow() {
		tableReport.setRowFactory(tv -> new TableRow<HRMUnitWorkerAttendanceReportRow>() {
			@Override
            protected void updateItem(HRMUnitWorkerAttendanceReportRow item, boolean empty) {
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

	public void setListRecord() {
		listRecord.clear();
		String unit_text_id = unitNameBox.getValue().toString();
        String month = chooseMonth.getValue();
        String year = chooseYear.getValue();
        String monthFilter = month + "/" + year;
        
        ArrayList<Worker> workersUnit = getAllWorkerUnit(unit_text_id);

        for (Worker w : workersUnit) {
            ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = getTimeKeepingAWorker(w.getId());
            ArrayList<LogTimekeepingWorker> logTimekeepingByMonth = getTimekeepingByMonth(logTimekeepingWorkers, month, year);

			float totalHoursWork = 0, totalHoursOT = 0;
			String hoursWork = "", hoursOT = "";
			int countLateEarly = 0;

			for (LogTimekeepingWorker log: logTimekeepingByMonth){
				totalHoursWork += log.getShift1() + log.getShift2();
				totalHoursOT += log.getShift3();

				hoursWork = String.valueOf(totalHoursWork) + " / " + String.valueOf(logTimekeepingByMonth.size()*2*4);
				hoursOT = String.valueOf(totalHoursOT);

				Time time_in = log.getTime_in();
				Time time_out = log.getTime_out();
				if((time_in.compareTo(Time.valueOf(Config.OFFICER_START_MORNING)) > 0 && time_in.compareTo(Time.valueOf(Config.OFFICER_END_MORNING)) < 0)
						|| (time_in.compareTo(Time.valueOf(Config.OFFICER_START_AFTERNOON)) > 0 && time_in.compareTo(Time.valueOf(Config.OFFICER_END_AFTERNOON)) < 0)) {
					countLateEarly++;
				}

				if((time_out.compareTo(Time.valueOf(Config.OFFICER_START_MORNING)) > 0 && time_out.compareTo(Time.valueOf(Config.OFFICER_END_MORNING)) < 0)
						|| (time_out.compareTo(Time.valueOf(Config.OFFICER_START_AFTERNOON)) > 0 && time_out.compareTo(Time.valueOf(Config.OFFICER_END_AFTERNOON)) < 0)) {
					countLateEarly++;
				}
			}

			if(monthFilter.equals(today.format(DateTimeFormatter.ofPattern("MM/yyyy"))) && !(w.getStatus() == 0 && logTimekeepingByMonth.isEmpty())) {
				listRecord.add(new HRMUnitWorkerAttendanceReportRow(w.getName(), w.getId(), unitNameBox.getValue().toString(), w.getStatus(), hoursWork, hoursOT, countLateEarly));
			}else if(!logTimekeepingByMonth.isEmpty()) {
				listRecord.add(new HRMUnitWorkerAttendanceReportRow(w.getName(), w.getId(), unitNameBox.getValue().toString(), w.getStatus(), hoursWork, hoursOT, countLateEarly));
			}
		}
	}

	private void getAllWorker()
	{
		ArrayList<Employee> allEmployees = EmployeeDAO.getInstance().getAll();
		for (Employee e: allEmployees) {
			if (e.getRole_id() == Role.Worker.getId()) {
				workers.add(new Worker(e.getId(), e.getName(), e.getIdentifier(), e.getBirthday(), e.getAddress(), e.getGender(), e.getPhone(),
						e.getDepartment(), e.getUnit_id(), e.getPassword(),e.getStatus()));
			}
			if (e.getRole_id() == Role.WorkerUnitManager.getId()) {
				workers.add(new WorkerUnitManager(e.getId(), e.getName(), e.getIdentifier(), e.getBirthday(), e.getAddress(), e.getGender(), e.getPhone(),
						e.getDepartment(), e.getUnit_id(), e.getPassword(),e.getStatus()));
			}
		}

	}

	public ArrayList<Worker> getAllWorkerUnit(String unit_id){
        ArrayList<Worker> workersUnit = new ArrayList<Worker>();
		for (Worker w: workers) {
			if (w.getUnit_id().equals(unit_id)) {
				workersUnit.add(w);
				if(w.getRole_id() == Role.WorkerUnitManager.getId()) {
					unit_manager.setText(w.getName());
					department.setText(w.getDepartment());
				}
			}
		}

        return workersUnit;
    }
	
    public ArrayList<LogTimekeepingWorker> getTimeKeepingAWorker(String employee_id){
        ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = TimekeepingWorkerDAO.getInstance().getByEmployeeID(employee_id);

        return logTimekeepingWorkers;
    }

    public ArrayList<LogTimekeepingWorker> getTimekeepingByMonth(ArrayList<LogTimekeepingWorker> logs, String month, String year){
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<Unit> allUnit = UnitDAO.getInstance().getAll();
		ArrayList<String> workerUnits = new ArrayList<String>();
		for (Unit u: allUnit) {
			if (u.getId().contains("FAC")) {
				workerUnits.add(u.getId());
			}
		}
		getAllWorker();

		reloadPage(null);
		unitNameBox.getItems().addAll(workerUnits);
		unitNameBox.setValue("ID Unit");
		chooseMonth.getItems().addAll(listMonth);
        chooseMonth.setValue(today.format(DateTimeFormatter.ofPattern("MM")));
        chooseYear.getItems().addAll(listYear);
        chooseYear.setValue(today.format(DateTimeFormatter.ofPattern("yyyy")));
		monthLabel.setText("Tháng " + today.format(DateTimeFormatter.ofPattern("MM/yyyy")));

        listRecord = FXCollections.observableArrayList();

        index.setCellValueFactory(index -> new ReadOnlyObjectWrapper<Integer>(tableReport.getItems().indexOf(index.getValue())+1));
        nameCol.setCellValueFactory(new PropertyValueFactory<HRMUnitWorkerAttendanceReportRow, String>("name"));
		worker_idCol.setCellValueFactory(new PropertyValueFactory<HRMUnitWorkerAttendanceReportRow, String>("worker_id"));
		total_hour_workCol.setCellValueFactory(new PropertyValueFactory<HRMUnitWorkerAttendanceReportRow, String>("total_hour_work"));
		total_overtime_workCol.setCellValueFactory(new PropertyValueFactory<HRMUnitWorkerAttendanceReportRow, String>("total_overtime"));
		lateEarlyCol.setCellValueFactory(new PropertyValueFactory<HRMUnitWorkerAttendanceReportRow, Integer>("countLateEarly"));
		tableReport.setItems(listRecord);
		highlightRow();
	}
}