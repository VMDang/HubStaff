package controller.report.hrmanager.unitworkerattendance;

import java.net.URL;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import dbtimekeeping.gettimekeeping.GetTimekeepingWorker;
import hrsystem.GetAllEmployees;
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

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;	
import java.io.IOException;

import model.employee.Employee;
import model.employee.worker.Worker;
import model.logtimekeeping.LogTimekeepingWorker;

public class HRMUnitWorkerAttendanceReportController implements Initializable{

	public static LocalDate today;

	private  static ObservableList<HRMUnitWorkerAttendanceReportRow> listRecord = FXCollections.observableArrayList();
	private HashMap<String, Worker> currentWorkers = new HashMap<>();

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
    private Button refresh;

	@FXML
	private Button export_excel;

	@FXML
	private TableView<HRMUnitWorkerAttendanceReportRow> tableReport;

	@FXML
	private TableColumn<HRMUnitWorkerAttendanceReportRow, Integer> index;

	@FXML
	private TableColumn<HRMUnitWorkerAttendanceReportRow, String> nameCol;

	@FXML
	private TableColumn<HRMUnitWorkerAttendanceReportRow, String> worker_idCol;

	@FXML
	private TableColumn<HRMUnitWorkerAttendanceReportRow, String> monthCol;

	@FXML
	private TableColumn<HRMUnitWorkerAttendanceReportRow, String> total_hour_workCol;

	@FXML
	private TableColumn<HRMUnitWorkerAttendanceReportRow, String> total_overtime_workCol;

	String[] listMonth = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
	String[] listYear = {"2023", "2022", "2021", "2020"};

	String name_unit_manager;
	String name_department;

	@FXML
	void viewreport(ActionEvent event) {
        String unit_id_text = unitNameBox.getValue().toString();
        if(unit_id_text.equals("ID Unit")) {
        	showUnitNotFound("Bạn chưa chọn mã đơn vị");
        } else {
        	unit_idText.setText(""+unit_id_text);
            listRecord = FXCollections.observableArrayList();
            setListRecord();
            
            num_worker.setText(String.valueOf(listRecord.size()));
            
            unit_manager.setText(name_unit_manager);
            
            department.setText(name_department);

            tableReport.setItems(listRecord);
        }
	}

	@FXML
	void export_excel(ActionEvent event) throws IOException {
		// Choose directory
	    DirectoryChooser directoryChooser = new DirectoryChooser();
	    directoryChooser.setTitle("Choose Directory to Save Attendance Report");

	    // Get directory
	    File selectedDirectory = directoryChooser.showDialog(basePane.getScene().getWindow());
	    if (selectedDirectory != null) {
	        String directoryPath = selectedDirectory.getAbsolutePath();

	        // Create workbook and sheet
	        Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("Attendance Report");

	        // Title Column
	        Row headerRow = sheet.createRow(0);
	        headerRow.createCell(0).setCellValue("STT");
	        headerRow.createCell(1).setCellValue("Worker ID");
	        headerRow.createCell(2).setCellValue("Name");
	        headerRow.createCell(3).setCellValue("Total Hours Work");
	        headerRow.createCell(4).setCellValue("Total Overtime Work");

	        // Data
	        int rowIndex = 1;
	        for (HRMUnitWorkerAttendanceReportRow row : listRecord) {
	            Row dataRow = sheet.createRow(rowIndex);
	            dataRow.createCell(0).setCellValue(rowIndex);
	            dataRow.createCell(1).setCellValue(row.getWorker_id());
	            dataRow.createCell(2).setCellValue(row.getName());
	            dataRow.createCell(3).setCellValue(row.getTotal_hour_work());
	            dataRow.createCell(4).setCellValue(row.getTotal_overtime());
	            rowIndex++;
	        }

	        // Create file name
	        String fileName = "Attendance_Report_" + "month_" + chooseMonth.getValue() + "_year_" + chooseYear.getValue() + "_" + unitNameBox.getValue().toString() + ".xlsx";
	        String filePath = directoryPath + File.separator + fileName;

	        // Save workbook into file
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
					if(!empty && item != null) {
						if(!currentWorkers.containsKey(item.getWorker_id())) {
							setStyle("-fx-background-color: #eaa6ab;");
						}
						else {
							setStyle("");
						}
					}
				}
				else {
					setStyle("");
				}
			}
		});
	}

	public void setListRecord() {
		String unit_text_id = unitNameBox.getValue().toString();
        String month = chooseMonth.getValue();
        String year = chooseYear.getValue();
        String monthFilter = month + "/" + year;
        
        ArrayList<Worker> workers = new ArrayList<Worker>();
        workers.addAll(getAllWorkerUnit(unit_text_id));
        
        int indexWU = 0;
        for(int i = 0; i < workers.size(); i++) {
        	if(workers.get(i).getId().equals(unit_text_id)) {
        		indexWU = i;
        	}
        }
        
        Worker workerTmp = workers.get(indexWU);
        workers.remove(indexWU);
        workers.add(0, workerTmp);

        for (Worker w : workers) {

            ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = new ArrayList<LogTimekeepingWorker>();
            logTimekeepingWorkers.addAll(getTimeKeepingAWorker(w.getId()));

            ArrayList<LogTimekeepingWorker> logTimekeepingByMonth = new ArrayList<LogTimekeepingWorker>();
            logTimekeepingByMonth.addAll(getTimekeepingByMonth(logTimekeepingWorkers, month,year ));

            
                float totalHoursWork = 0, totalHoursOT = 0;
                String hoursWork = "", hoursOT = "";

                for (LogTimekeepingWorker log: logTimekeepingByMonth){
                    totalHoursWork += log.getShift1() + log.getShift2();
                    totalHoursOT += log.getShift3();

                    hoursWork = String.valueOf(totalHoursWork) + " / " + String.valueOf(logTimekeepingByMonth.size()*2*4);
                    hoursOT = String.valueOf(totalHoursOT) + " / " + String.valueOf(logTimekeepingByMonth.size()*4);
                }
                
                if(monthFilter.equals(today.format(DateTimeFormatter.ofPattern("MM/yyyy"))) && !(w.getStatus() == 0 && logTimekeepingByMonth.isEmpty())) {
                	listRecord.add(new HRMUnitWorkerAttendanceReportRow(w.getName(), w.getId(), w.getUnit_id(),unitNameBox.getValue().toString(), hoursWork, hoursOT));
                }else if(!logTimekeepingByMonth.isEmpty()) {
                	listRecord.add(new HRMUnitWorkerAttendanceReportRow(w.getName(), w.getId(), w.getUnit_id(),unitNameBox.getValue().toString(), hoursWork, hoursOT));
                }
            }
        
        highlightRow();
	}

	public Set<String> getListUnit(){
		ArrayList<Employee> allEmployees = GetAllEmployees.getInstance().getAllEmployees();
		Set<String> set = new HashSet<>();

        for (Employee e: allEmployees) {
            if ((e.getRole_id() == 1 || e.getRole_id() == 3)) {
            	set.add(e.getUnit_id());
            }
        }

        return set;
	}

	public ArrayList<Worker> getAllWorkerUnit(String unit_id){
		GetAllEmployees getAllEmployees = GetAllEmployees.getInstance();
        ArrayList<Employee> allEmployees = getAllEmployees.getAllEmployees();
        ArrayList<Worker> allWorker = new ArrayList<Worker>();

        for (Employee e: allEmployees) {
            if ((e.getRole_id() == 1 || e.getRole_id() == 3) && (e.getUnit_id().equals(unit_id))) {
            	if(e.getRole_id() == 3) {
            		name_unit_manager = e.getName();
            		name_department = e.getDepartment();
            	}
            	
                allWorker.add(new Worker(e.getId(), e.getName(), e.getUnit_id(), e.getPassword(),e.getStatus()));
                
                if(e.getStatus() == 1) {
            		currentWorkers.put(e.getId(), new Worker(e.getId(), e.getName(), e.getUnit_id(), e.getPassword(),e.getStatus()));
            	}
            }
        }

        return allWorker;
    }
	
    public ArrayList<LogTimekeepingWorker> getTimeKeepingAWorker(String employee_id){
        GetTimekeepingWorker getTimekeepingWorker = GetTimekeepingWorker.getInstance();
        ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = getTimekeepingWorker.getTimekeepingsByEmployeeID(employee_id);

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
		Set<String> listunit = getListUnit();
		unitNameBox.getItems().addAll(listunit);
		unitNameBox.setValue("ID Unit");
		today = LocalDate.now();
		chooseMonth.getItems().addAll(listMonth);
        chooseMonth.setValue(LocalDate.now().format(DateTimeFormatter.ofPattern("MM")));
        chooseYear.getItems().addAll(listYear);
        chooseYear.setValue(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy")));

        listRecord = FXCollections.observableArrayList();

        index.setCellValueFactory(index -> new ReadOnlyObjectWrapper<Integer>(tableReport.getItems().indexOf(index.getValue())+1));
        index.setSortable(false);
        nameCol.setCellValueFactory(new PropertyValueFactory<HRMUnitWorkerAttendanceReportRow, String>("name"));
		worker_idCol.setCellValueFactory(new PropertyValueFactory<HRMUnitWorkerAttendanceReportRow, String>("worker_id"));
		monthCol.setCellValueFactory(new PropertyValueFactory<HRMUnitWorkerAttendanceReportRow, String>("month"));
		total_hour_workCol.setCellValueFactory(new PropertyValueFactory<HRMUnitWorkerAttendanceReportRow, String>("total_hour_work"));
		total_overtime_workCol.setCellValueFactory(new PropertyValueFactory<HRMUnitWorkerAttendanceReportRow, String>("total_overtime"));

		tableReport.setItems(listRecord);

	}

}