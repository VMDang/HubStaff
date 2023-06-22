package controller.report.hrmanager.unitworkerattendance;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.lang.Float;

import dbreport.GetReportWorker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class UnitWorkerAttendanceController implements Initializable{
	
	public static LocalDate today;
	
	@FXML
    private AnchorPane basePane;
	
	@FXML
    private ChoiceBox<String> chooseMonth;
	
	@FXML
    private ChoiceBox<String> chooseYear;
	
	@FXML
    private Label monthBtn;
	
	@FXML
    private Button refresh;
	
	@FXML
	private TextField unitField;
	
	@FXML
	private TableView<ReportMonthlyWorkerTable> TableReport;
	
	@FXML
	private TableColumn<ReportMonthlyWorkerTable, String> nameCol;
	
	@FXML
	private TableColumn<ReportMonthlyWorkerTable, String> worker_idCol;
	
	@FXML
	private TableColumn<ReportMonthlyWorkerTable, String> unit_idCol;
	
	@FXML
	private TableColumn<ReportMonthlyWorkerTable, Integer> monthCol;
	
	@FXML
	private TableColumn<ReportMonthlyWorkerTable, Float> total_hour_workCol;
	
	@FXML
	private TableColumn<ReportMonthlyWorkerTable, Float> total_overtime_workCol;
	
	String[] listMonth = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
	String[] listYear = {"2023", "2022", "2021", "2020"};
	
	private static ObservableList<ReportMonthlyWorkerTable> reportWorkers = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		updateDay();
    	String month = today.toString().split("-")[1];
    	String year = today.toString().split("-")[0];
    	
    	String unitID = unitField.getText();
    	chooseMonth.getItems().addAll(listMonth);
		chooseMonth.setValue(month);
		chooseYear.getItems().addAll(listYear);
		chooseYear.setValue(year);
		monthBtn.setText("Báo cáo tháng "+month);
		
		getDataMonth(month, year, unitID);
		
		nameCol.setCellValueFactory(new PropertyValueFactory<ReportMonthlyWorkerTable, String>("name"));
		worker_idCol.setCellValueFactory(new PropertyValueFactory<ReportMonthlyWorkerTable, String>("worker_id"));
		unit_idCol.setCellValueFactory(new PropertyValueFactory<ReportMonthlyWorkerTable, String>("unit_id"));
		monthCol.setCellValueFactory(new PropertyValueFactory<ReportMonthlyWorkerTable, Integer>("month"));
		total_hour_workCol.setCellValueFactory(new PropertyValueFactory<ReportMonthlyWorkerTable, Float>("total_hour_work"));
		total_overtime_workCol.setCellValueFactory(new PropertyValueFactory<ReportMonthlyWorkerTable, Float>("total_overtime"));
		
		TableReport.setItems(reportWorkers);
	}
	
	@FXML
    void resetTable(ActionEvent event) {
    	String month = chooseMonth.getValue();
    	String year = chooseYear.getValue();
    	String unitID = unitField.getText();
    	monthBtn.setText("Báo cáo tháng "+month);
    	
    	reportWorkers = FXCollections.observableArrayList();
    	getDataMonth(month, year, unitID);
    	TableReport.setItems(reportWorkers);
    }
	
	public static float setFormatHour(float f) {
    	return (float) Math.round(f * 10) / 10;
    }
	
	public static void updateDay() {
    	today = LocalDate.now();
    }
	
	public static void getDataMonth(String month, String year, String unitID) {
		ArrayList<ReportUnitWorker> arrRUW = GetReportWorker.getInstance().getReportByUnitID(unitID);
		
		for(ReportUnitWorker report : arrRUW) {
			ReportMonthlyWorkerTable ruwt = createReportMonthlyWorkerTable(report);
			if(ruwt.getMonth()==Integer.parseInt(month)) {
				reportWorkers.add(ruwt);
			}
		}
	}
	
	public static ReportMonthlyWorkerTable createReportMonthlyWorkerTable(ReportUnitWorker report) {
		String name = report.getName();
		String worker_id = report.getEmployee_id();
		String unit_id = report.getUnit_id();
		int month = report.getMonth();
		float total_work_hour = report.getTotal_hour_work();
		float total_overtime = report.getTotal_overtime();
		return new ReportMonthlyWorkerTable(name, worker_id, unit_id, month, total_work_hour, total_overtime);
	}
}
