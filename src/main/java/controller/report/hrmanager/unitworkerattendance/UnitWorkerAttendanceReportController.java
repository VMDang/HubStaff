package controller.report.hrmanager.unitworkerattendance;

import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import dbtimekeeping.GetTimekeepingWorker;
import hrsystem.GetAllEmployees;
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
import model.employee.Employee;
import model.employee.worker.Worker;
import model.logtimekeeping.LogTimekeepingWorker;

public class UnitWorkerAttendanceReportController implements Initializable{
	
	public static LocalDate today;
	
	private ObservableList<HRMUnitWorkerAttendanceReportRow> listRecord = FXCollections.observableArrayList();
	
	private HashMap<String, Worker> currentWorkers = new HashMap<String, Worker>();
	
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
	private TableView<HRMUnitWorkerAttendanceReportRow> tableReport;
	
	@FXML
	private TableColumn<HRMUnitWorkerAttendanceReportRow, String> nameCol;
	
	@FXML
	private TableColumn<HRMUnitWorkerAttendanceReportRow, String> worker_idCol;
	
	@FXML
	private TableColumn<HRMUnitWorkerAttendanceReportRow, String> unit_idCol;
	
	@FXML
	private TableColumn<HRMUnitWorkerAttendanceReportRow, String> monthCol;
	
	@FXML
	private TableColumn<HRMUnitWorkerAttendanceReportRow, String> total_hour_workCol;
	
	@FXML
	private TableColumn<HRMUnitWorkerAttendanceReportRow, String> total_overtime_workCol;
	
	String[] listMonth = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
	String[] listYear = {"2023", "2022", "2021", "2020"};
	
	@FXML
	void viewreport(ActionEvent event) {
		listRecord.removeAll();
        tableReport.getItems().clear();
        
        String month = chooseMonth.getValue();
        monthBtn.setText("Báo cáo tháng "+month);
        setListRecord();
        tableReport.setItems(listRecord);
	}
	
	public void setListRecord() {
		ArrayList<Worker> workers = new ArrayList<Worker>();
        workers.addAll(getAllWorkerUnit(unitField.getText()));
        
        String monthFilter = chooseMonth.getValue() + "/" + chooseYear.getValue();

        for (Worker w : workers) {
            ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = new ArrayList<LogTimekeepingWorker>();
            logTimekeepingWorkers.addAll(getTimeKeepingAWorker(w.getId()));

            ArrayList<LogTimekeepingWorker> logTimekeepingByMonth = new ArrayList<LogTimekeepingWorker>();
            logTimekeepingByMonth.addAll(getTimekeepingByMonth(logTimekeepingWorkers, monthFilter));

            if (!logTimekeepingByMonth.isEmpty()){


                if (monthFilter.equals(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/yyyy")))){
                    currentWorkers.put(w.getId(), w);
                }
                float totalHoursWork = 0, totalHoursOT = 0;
                String hoursWork = "", hoursOT = "";

                for (LogTimekeepingWorker log: logTimekeepingByMonth){
                    totalHoursWork += log.getShift1() + log.getShift2();
                    totalHoursOT += log.getShift3();

                    hoursWork = String.valueOf(totalHoursWork) + " / " + String.valueOf(logTimekeepingByMonth.size()*2*4);
                    hoursOT = String.valueOf(totalHoursOT) + " / " + String.valueOf(logTimekeepingByMonth.size()*4);
                }

                listRecord.add(new HRMUnitWorkerAttendanceReportRow(w.getName(), w.getId(), w.getUnit_id(),unitField.getText(), hoursWork, hoursOT));
            }
        }
	}
	
	public ArrayList<Worker> getAllWorkerUnit(String unit_id){
        GetAllEmployees getAllEmployees = GetAllEmployees.getInstance();
        ArrayList<Employee> allEmployees = getAllEmployees.getAllEmployees();
        ArrayList<Worker> allWorker = new ArrayList<Worker>();

        for (Employee e: allEmployees) {
            if ((e.getRole_id() == 3) && (e.getUnit_id().equals(unit_id))) {
                allWorker.add(new Worker(e.getId(), e.getName(), e.getUnit_id(), e.getPassword()));
            }
        }
        return allWorker;
    }

    public ArrayList<LogTimekeepingWorker> getTimeKeepingAWorker(String employee_id){
        GetTimekeepingWorker getTimekeepingWorker = GetTimekeepingWorker.getInstance();
        ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = getTimekeepingWorker.getTimekeepingsByEmployeeID(employee_id);

        return logTimekeepingWorkers;
    }

    public ArrayList<LogTimekeepingWorker> getTimekeepingByMonth(ArrayList<LogTimekeepingWorker> logs, String monthFilter){
        ArrayList<LogTimekeepingWorker> logFilterByMonth = new ArrayList<LogTimekeepingWorker>();

        for (LogTimekeepingWorker log : logs) {
            YearMonth yearMonth = YearMonth.of(log.getDate().toLocalDate().getYear(), log.getDate().toLocalDate().getMonth());
            String monthReport = yearMonth.format(DateTimeFormatter.ofPattern("MM/yyyy"));

            if (monthReport.equals(monthFilter)){
                logFilterByMonth.add(log);
            }
        }

        return logFilterByMonth;
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		today = LocalDate.now();
		String month = today.toString().split("-")[1];
		chooseMonth.getItems().addAll(listMonth);
        chooseMonth.setValue(LocalDate.now().format(DateTimeFormatter.ofPattern("MM")));
        chooseYear.getItems().addAll(listYear);
        chooseYear.setValue(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy")));
        
        monthBtn.setText("Báo cáo tháng "+month);

        setListRecord();
        
        nameCol.setCellValueFactory(new PropertyValueFactory<HRMUnitWorkerAttendanceReportRow, String>("name"));
		worker_idCol.setCellValueFactory(new PropertyValueFactory<HRMUnitWorkerAttendanceReportRow, String>("worker_id"));
		unit_idCol.setCellValueFactory(new PropertyValueFactory<HRMUnitWorkerAttendanceReportRow, String>("unit_id"));
		monthCol.setCellValueFactory(new PropertyValueFactory<HRMUnitWorkerAttendanceReportRow, String>("month"));
		total_hour_workCol.setCellValueFactory(new PropertyValueFactory<HRMUnitWorkerAttendanceReportRow, String>("total_hour_work"));
		total_overtime_workCol.setCellValueFactory(new PropertyValueFactory<HRMUnitWorkerAttendanceReportRow, String>("total_overtime"));
		
		tableReport.setItems(listRecord);
		
	}
	
}
