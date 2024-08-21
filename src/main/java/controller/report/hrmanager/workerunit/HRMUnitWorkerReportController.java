package controller.report.hrmanager.workerunit;

import java.net.URL;

import java.sql.Time;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import config.Config;
import config.FXMLNavigation;
import controller.timekeeping.worker.monthly.TimekeepingMonthlyWorkerController;
import database.EmployeeDAO;
import database.TimekeepingWorkerDAO;
import database.UnitDAO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
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
import utility.TimeUtility;

public class HRMUnitWorkerReportController implements Initializable{

	private LocalDate today = LocalDate.now();

	private ObservableList<HRMUnitWorkerReportRow> listRecord = FXCollections.observableArrayList();

	private ArrayList<Worker> workers = new ArrayList<Worker>();
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
	private TableView<HRMUnitWorkerReportRow> tableReport;

	@FXML
	private TableColumn<HRMUnitWorkerReportRow, Integer> index;

	@FXML
	private TableColumn<HRMUnitWorkerReportRow, String> nameCol;

	@FXML
	private TableColumn<HRMUnitWorkerReportRow, String> worker_idCol;

	@FXML
	private TableColumn<HRMUnitWorkerReportRow, Integer> lateEarlyCol;

	@FXML
	private TableColumn<HRMUnitWorkerReportRow, String> total_hour_workCol;

	@FXML
	private TableColumn<HRMUnitWorkerReportRow, String> total_overtime_workCol;

	public HRMUnitWorkerReportController() {
	}

	public HRMUnitWorkerReportController(String unitIdInit, String monthInit, String yearInit) {
		this.unitIdInit = unitIdInit;
		this.monthInit = monthInit;
		this.yearInit = yearInit;
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

		unitNameBox.getItems().addAll(workerUnits);
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
		nameCol.setCellValueFactory(new PropertyValueFactory<HRMUnitWorkerReportRow, String>("name"));
		worker_idCol.setCellValueFactory(new PropertyValueFactory<HRMUnitWorkerReportRow, String>("worker_id"));
		total_hour_workCol.setCellValueFactory(new PropertyValueFactory<HRMUnitWorkerReportRow, String>("total_hour_work"));
		total_overtime_workCol.setCellValueFactory(new PropertyValueFactory<HRMUnitWorkerReportRow, String>("total_overtime"));
		lateEarlyCol.setCellValueFactory(new PropertyValueFactory<HRMUnitWorkerReportRow, Integer>("countLateEarly"));
		tableReport.setItems(listRecord);
		highlightRow();

		tableReport.setOnMouseClicked(event -> {
			if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
				HRMUnitWorkerReportRow selectedRow = tableReport.getSelectionModel().getSelectedItem();
				if (selectedRow != null) {
					try {
						Employee employee = EmployeeDAO.getInstance().getById(selectedRow.getWorker_id());

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
            tableReport.setItems(listRecord);
        }
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

	        Row headerRow = sheet.createRow(0);
			for (int col = 0; col < tableReport.getColumns().size(); col++) {
				headerRow.createCell(col).setCellValue(tableReport.getColumns().get(col).getText());
			}

	        int rowIndex = 1;
	        for (HRMUnitWorkerReportRow row : listRecord) {
	            Row dataRow = sheet.createRow(rowIndex);
	            dataRow.createCell(0).setCellValue(rowIndex);
	            dataRow.createCell(1).setCellValue(row.getWorker_id());
	            dataRow.createCell(2).setCellValue(row.getName());
	            dataRow.createCell(3).setCellValue(row.getTotal_hour_work());
	            dataRow.createCell(4).setCellValue(row.getTotal_overtime());
				dataRow.createCell(5).setCellValue(row.getCountLateEarly());
	            rowIndex++;
	        }

	        String fileName = "Attendance_Report_" + unitNameBox.getValue() + "_" + chooseMonth.getValue() + "_" + chooseYear.getValue() + ".xlsx";
	        String filePath = directoryPath + File.separator + fileName;

	        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
	            workbook.write(fileOutputStream);
	            notifyExportReport("Xuất báo cáo thành công!", AlertType.INFORMATION);
	        } catch (IOException e) {
				notifyExportReport("Xuất báo cáo thất bại!", AlertType.ERROR);
	            e.printStackTrace();
	        }
	        workbook.close();
	    }
	}

	private void notifyExportReport(String message, AlertType type) {
        Alert alert = new Alert(type);
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
		tableReport.setRowFactory(tv -> new TableRow<HRMUnitWorkerReportRow>() {
			@Override
            protected void updateItem(HRMUnitWorkerReportRow item, boolean empty) {
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

        ArrayList<Worker> workersUnit = getAllWorkerUnit(unit_text_id);

        for (Worker w : workersUnit) {
            ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = getTimeKeepingAWorker(w.getId());
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
				hoursOT = String.valueOf(totalHoursOT);

				Time time_in = log.getTime_in();
				Time time_out = log.getTime_out();
				if((time_in.compareTo(Time.valueOf(Config.WORKER_START_SHIFT1)) > 0 && time_in.compareTo(Time.valueOf(Config.WORKER_END_SHIFT1)) < 0)
						|| (time_in.compareTo(Time.valueOf(Config.WORKER_START_SHIFT1)) > 0 && time_in.compareTo(Time.valueOf(Config.WORKER_END_SHIFT2)) < 0)) {
					countLateEarly++;
				}

				if((time_out.compareTo(Time.valueOf(Config.WORKER_START_SHIFT1)) > 0 && time_out.compareTo(Time.valueOf(Config.WORKER_END_SHIFT1)) < 0)
						|| (time_out.compareTo(Time.valueOf(Config.WORKER_START_SHIFT1)) > 0 && time_out.compareTo(Time.valueOf(Config.WORKER_END_SHIFT2)) < 0)) {
					countLateEarly++;
				}
			}

			if(!(w.getStatus() == 0 && logTimekeepingByMonth.isEmpty())) {
				listRecord.add(new HRMUnitWorkerReportRow(w.getName(), w.getId(), unitNameBox.getValue().toString(), w.getStatus(), hoursWork, hoursOT, countLateEarly));
			}
		}
	}

	private void getAllWorker() {
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
		int activeWorker = 0;
		for (Worker w: workers) {
			if (w.getUnit_id().equals(unit_id)) {
				workersUnit.add(w);
				if(w.getRole_id() == Role.WorkerUnitManager.getId()) {
					unit_manager.setText(w.getName());
					department.setText(w.getDepartment());
				}
				if(w.getStatus() == 1) {
					activeWorker++;
				}
			}
		}
		num_worker.setText(String.valueOf(activeWorker));

        return workersUnit;
    }
	
    private ArrayList<LogTimekeepingWorker> getTimeKeepingAWorker(String employee_id){
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
}