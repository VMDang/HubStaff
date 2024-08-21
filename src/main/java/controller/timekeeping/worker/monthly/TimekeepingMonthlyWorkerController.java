package controller.timekeeping.worker.monthly;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate; 
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;

import config.Config;
import config.FXMLNavigation;
import controller.auth.Authentication;
import controller.report.hrmanager.workerunit.HRMUnitWorkerReportController;
import controller.report.workerunitmanager.WUMWorkerUnitReportController;
import controller.timekeeping.worker.daily.TimekeepingDayWorkerDetailController;

import database.RoleDAO;
import database.TimekeepingWorkerDAO;
import database.UnitDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;

import model.employee.Employee;
import model.employee.HRManager;
import model.employee.Unit;

import model.employee.worker.WorkerUnitManager;
import model.logtimekeeping.LogTimekeepingWorker;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utility.TimeUtility;

public class TimekeepingMonthlyWorkerController implements Initializable {
	private static LocalDate today = LocalDate.now();
	private Employee employee = Authentication.getInstance().getAuthentication();
	private String monthInit = null;
	private String yearInit = null;
	private int countLateEarly = 0;

    @FXML
    private AnchorPane basePane;

	@FXML
	private Label user_name;
    
    @FXML
    private Label department;
    
    @FXML
    private Label employeeID;
    
    @FXML
    private Label unit_id;

	@FXML
	private Label unit_name;
    
    @FXML
    private Label role;

    @FXML
    private ChoiceBox<String> chooseMonth;
    
    @FXML
    private ChoiceBox<String> chooseYear;

	@FXML
    private Label monthLabel;

    @FXML
    private Button searchBtn;

	@FXML
	private Button reloadPageBtn;

	@FXML
	private Button backBtn;
    
    @FXML
    private TableView<SummaryWorkerTableRow> tableSummary;

    @FXML
    private TableColumn<SummaryWorkerTableRow, Integer> total_day_workCol;

    @FXML
    private TableColumn<SummaryWorkerTableRow, Float> total_hour_workCol;

    @FXML
    private TableColumn<SummaryWorkerTableRow, Float> total_overtimeCol;

    @FXML
    private TableColumn<SummaryWorkerTableRow, Integer> lateEarlyCol;

    @FXML
    private TableView<TimekeepingWorkerTableRow> tableTimekeepingMonth;
    
    @FXML
    private TableColumn<TimekeepingWorkerTableRow,Date> dateCol;

    @FXML
    private TableColumn<TimekeepingWorkerTableRow, String> statusCol;

    @FXML
    private TableColumn<TimekeepingWorkerTableRow, Time> time_inCol;

    @FXML
    private TableColumn<TimekeepingWorkerTableRow, Time> time_outCol;

    @FXML
    private TableColumn<TimekeepingWorkerTableRow, Float> hour_workCol;
    
    @FXML
    private TableColumn<TimekeepingWorkerTableRow, Float> overtimeCol;
    
    private ObservableList<TimekeepingWorkerTableRow> LogTimekeepingMonthList = FXCollections.observableArrayList();
    private ObservableList<SummaryWorkerTableRow> summaryRows = FXCollections.observableArrayList();
    private ArrayList<LogTimekeepingWorker> logsLTW;

	public TimekeepingMonthlyWorkerController() {
		this.logsLTW = TimekeepingWorkerDAO.getInstance().getByEmployeeID(employee.getId());
	}

	public TimekeepingMonthlyWorkerController(Employee employee, String monthInit, String yearInit) {
		this.employee = employee;
		this.monthInit = monthInit;
		this.yearInit = yearInit;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (monthInit != null && yearInit != null) {
			backBtn.setVisible(true);
		} else {
			backBtn.setVisible(false);
			monthInit = today.toString().split("-")[1];
			yearInit = today.toString().split("-")[0];
		}

    	this.logsLTW = TimekeepingWorkerDAO.getInstance().getByEmployeeID(employee.getId());

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
    	dateCol.setCellValueFactory(new PropertyValueFactory<TimekeepingWorkerTableRow,Date>("date"));
    	time_inCol.setCellValueFactory(new PropertyValueFactory<TimekeepingWorkerTableRow,Time>("time_in"));
    	time_outCol.setCellValueFactory(new PropertyValueFactory<TimekeepingWorkerTableRow,Time>("time_out"));
    	hour_workCol.setCellValueFactory(new PropertyValueFactory<TimekeepingWorkerTableRow,Float>("hour_work"));
    	overtimeCol.setCellValueFactory(new PropertyValueFactory<TimekeepingWorkerTableRow,Float>("overtime"));
    	statusCol.setCellValueFactory(new PropertyValueFactory<TimekeepingWorkerTableRow, String>("status"));
    	tableTimekeepingMonth.setItems(LogTimekeepingMonthList);
		highlightRow();
    	
    	total_day_workCol.setCellValueFactory(new PropertyValueFactory<SummaryWorkerTableRow,Integer>("total_day_work"));
    	total_hour_workCol.setCellValueFactory(new PropertyValueFactory<SummaryWorkerTableRow,Float>("total_hour_work"));
    	total_overtimeCol.setCellValueFactory(new PropertyValueFactory<SummaryWorkerTableRow,Float>("total_overtime"));
		lateEarlyCol.setCellValueFactory(new PropertyValueFactory<SummaryWorkerTableRow,Integer>("countLateEarly"));
    	tableSummary.setItems(summaryRows);
    	
    	tableTimekeepingMonth.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
            	TimekeepingWorkerTableRow selectedItem = tableTimekeepingMonth.getSelectionModel().getSelectedItem();
            	if (selectedItem != null) {
            		showDetailPopupWorker(selectedItem);
            	}
            }
        });
	}
    
    @FXML
    public void searchTable(ActionEvent event) {
    	String month = chooseMonth.getValue();
    	String year = chooseYear.getValue();
    	monthLabel.setText("Tháng " + month + "/" + year);
    	
    	getLogByMonth(month, year);
    	tableTimekeepingMonth.setItems(LogTimekeepingMonthList);

    	setDataSummaryTableRow(LogTimekeepingMonthList);
    	tableSummary.setItems(summaryRows);
    }

	@FXML
	public void reloadPage(ActionEvent event) {
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
	public void backPrePage(ActionEvent event) {
		Employee auth = Authentication.getInstance().getAuthentication();

		try {
			if (auth instanceof HRManager) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLNavigation.HRM_UNIT_WORKER_VIEW));
				loader.setControllerFactory(c -> new HRMUnitWorkerReportController(employee.getUnit_id(), monthInit, yearInit));
				Node node = loader.load();
				basePane.getChildren().setAll(node);
			} else if (auth instanceof WorkerUnitManager) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLNavigation.WUM_WORKER_UNIT_REPORT_VIEW));
				loader.setControllerFactory(c -> new WUMWorkerUnitReportController(monthInit, yearInit));
				Node node = loader.load();
				basePane.getChildren().setAll(node);
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@FXML
	public void exportExcel(ActionEvent event) throws IOException {
		if (LogTimekeepingMonthList.isEmpty()) {
			notifyExportReport("Không có dữ liệu để xuất file", Alert.AlertType.ERROR);
			return;
		}

		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Chọn thư mục lưu file");

		File selectedDirectory = directoryChooser.showDialog(basePane.getScene().getWindow());
		if (selectedDirectory != null) {
			String directoryPath = selectedDirectory.getAbsolutePath();

			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Dữ liệu chấm công");

			Row header = sheet.createRow(0);
			for (int col = 0; col < tableTimekeepingMonth.getColumns().size(); col++) {
				header.createCell(col).setCellValue(tableTimekeepingMonth.getColumns().get(col).getText());
			}

			int rowIndex = 1;
			for (TimekeepingWorkerTableRow row : LogTimekeepingMonthList) {
				Row r = sheet.createRow(rowIndex);
				r.createCell(0).setCellValue(row.getDate().toString());
				r.createCell(1).setCellValue(row.getTime_in() != null ? row.getTime_in().toString() : "");
				r.createCell(2).setCellValue(row.getTime_out() != null ? row.getTime_out().toString() : "");
				r.createCell(3).setCellValue(row.getHour_work());
				r.createCell(4).setCellValue(row.getOvertime());
				r.createCell(5).setCellValue(row.getStatus());
				rowIndex++;
			}

			String fileName = "Timekeeping_" + employeeID.getText() + "_" + chooseMonth.getValue() + "_" + chooseYear.getValue() + ".xlsx";
			String filePath = directoryPath + File.separator + fileName;

			try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
				workbook.write(fileOutputStream);
				notifyExportReport("Xuất dữ liệu chấm công thành công!", Alert.AlertType.INFORMATION);
			} catch (IOException e) {
				notifyExportReport("Xuất dữ liệu chấm công thất bại!", Alert.AlertType.ERROR);
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

	public void showDetailPopupWorker(TimekeepingWorkerTableRow selectedItem) {
		try {
			LogTimekeepingWorker logDetail = new LogTimekeepingWorker("", employee.getId(), selectedItem.getDate(), selectedItem.getTime_in(),
					selectedItem.getTime_out(), selectedItem.getShift1(), selectedItem.getShift2(), selectedItem.getShift3());

			FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLNavigation.TIMEKEEPING_DAY_WORKER_DETAIL_VIEW));
			loader.setControllerFactory(c -> new TimekeepingDayWorkerDetailController(employee, logDetail));
			Parent root = loader.load();

			Stage stage = new Stage();
			stage.setTitle("Chi tiết ngày công");
			stage.setScene(new Scene(root));
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    private float setFormatHour(float f) {
    	return (float) Math.round(f * 10) / 10;
    }

	public void getLogByMonth(String month, String year) {
		LogTimekeepingMonthList = FXCollections.observableArrayList();
		countLateEarly = 0;
		ArrayList<Integer> listDayMonth = TimeUtility.getListDayMonth(Integer.parseInt(year));
		for (int i = 1; i <= listDayMonth.get(Integer.parseInt(month)-1); i++) {
			String dateFormat = year + "-" + month + "-" + (i<10 ? "0"+i : ""+i);
			boolean checkExistLog = false;

			for (LogTimekeepingWorker log: logsLTW) {
				if (log.getDate().toString().contains(dateFormat))
				{
					Date date = log.getDate();
					Time time_in = log.getTime_in();
					Time time_out = log.getTime_out();
					float hour_work = setFormatHour(log.getShift1() + log.getShift2());
					float overtime = log.getShift3() ;
					String status = "";

					if((time_in.compareTo(Time.valueOf(Config.WORKER_START_SHIFT1)) > 0 && time_in.compareTo(Time.valueOf(Config.WORKER_END_SHIFT1)) < 0)
							|| (time_in.compareTo(Time.valueOf(Config.WORKER_START_SHIFT2)) > 0 && time_in.compareTo(Time.valueOf(Config.WORKER_END_SHIFT2)) < 0)) {
						status += "Đi muộn ";
						countLateEarly++;
					}

					if((time_out.compareTo(Time.valueOf(Config.WORKER_END_SHIFT1)) < 0 && time_out.compareTo(Time.valueOf(Config.WORKER_START_SHIFT1)) > 0)
							|| (time_out.compareTo(Time.valueOf(Config.WORKER_END_SHIFT2)) < 0 && time_out.compareTo(Time.valueOf(Config.WORKER_START_SHIFT2)) > 0)) {
						status +="Về sớm ";
						countLateEarly++;
					}

					if(time_in.compareTo(Time.valueOf(Config.WORKER_START_SHIFT1)) <= 0 && time_out.compareTo(Time.valueOf(Config.WORKER_END_SHIFT2)) >= 0) status = "Đạt";

					LogTimekeepingMonthList.add(new TimekeepingWorkerTableRow(date, time_in, time_out, hour_work, overtime, status, log.getShift1(), log.getShift2(), log.getShift3()));
					checkExistLog = true;
					break;
				}
			}

			if(!checkExistLog) {
				if(Date.valueOf(dateFormat).compareTo(Date.valueOf(today.toString()))<0) {
					LogTimekeepingMonthList.add(new TimekeepingWorkerTableRow(Date.valueOf(dateFormat), "Nghỉ"));
				} else {
					LogTimekeepingMonthList.add(new TimekeepingWorkerTableRow(Date.valueOf(dateFormat), "Chưa làm"));
				}
			}
		}
	}

	public void setDataSummaryTableRow(ObservableList<TimekeepingWorkerTableRow> LogInMonth) {
		int total_day_work = 0;
		float total_hour_work = 0f;
		float total_overtime= 0f;

		for(TimekeepingWorkerTableRow r : LogInMonth) {
			if(r.getTime_in() != null) total_day_work++;
			total_hour_work += r.getHour_work();
			total_overtime += r.getOvertime();
		}
		total_hour_work = setFormatHour(total_hour_work);
		total_overtime = setFormatHour(total_overtime);

		summaryRows = FXCollections.observableArrayList();
		summaryRows.add(new SummaryWorkerTableRow(total_day_work, total_hour_work, total_overtime, countLateEarly));
	}

	private void highlightRow(){
		tableTimekeepingMonth.setRowFactory(tv -> new TableRow<TimekeepingWorkerTableRow>() {
            @Override
            protected void updateItem(TimekeepingWorkerTableRow item, boolean empty) {
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