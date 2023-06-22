package controller.importdata.excel;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.employee.Employee;
import model.logtimekeeping.LogTimekeepingOfficer;
import model.logtimekeeping.LogTimekeepingWorker;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import dbtimekeeping.GetTimekeepingOfficer;
import dbtimekeeping.GetTimekeepingWorker;
import services.logtimekeeping.LogTimekeepingOfficerService;
import services.logtimekeeping.LogTimekeepingWorkerService;
import hrsystem.GetAEmployee;
public class ExcelImportController  {
	List<ExcelImportRow> excelImportRows;
	
	@FXML
	private TableView<ExcelImportRow> table;
	@FXML
	private TableColumn<ExcelImportRow,Integer> idColumn;
	@FXML
	private TableColumn<ExcelImportRow,String> employee_idColumn;
	@FXML
	private TableColumn<ExcelImportRow,String> dateColumn;
	@FXML
	private TableColumn<ExcelImportRow,String> time_inColumn;
	@FXML
	private TableColumn<ExcelImportRow,String> time_outColumn;
	@FXML
	private TableColumn<ExcelImportRow, String> nameColumn;
	@FXML
	private TableColumn<ExcelImportRow, String> statusColumn;
	private ObservableList<ExcelImportRow> excelImportRowList;
	@FXML
	private AnchorPane basePane;
	@FXML
	private javafx.scene.control.TextField textField ;
	String url;
	@FXML
	Button Nhap;
	@FXML
	public void ChooseFile (ActionEvent e) {
		Stage stage =(Stage) basePane.getScene().getWindow();
		FileChooser fc = new FileChooser();
		fc.setTitle("Choose Excel File ");
		FileChooser.ExtensionFilter excelFilter = new FileChooser.ExtensionFilter("Excel Files", "*.xlsx");
		Nhap.setDisable(true);
		textField.textProperty().addListener((observable,oldValue,newValue)->{
			Nhap.setDisable(false);
		});
		fc.getExtensionFilters().add(excelFilter);
		File file = fc.showOpenDialog(stage);
		if(file!=null) {
			url = file.toURI().toString();
			url = url.substring(6);
			System.out.println(url);
			String abc = file.getName();
			textField.setText(abc); 
		}
	}
	public void NhapFile (ActionEvent e)  {
		
		 try {
			  if (url==null)return;
			  String excelFilePath = url;
			  excelImportRows = ReadExcel.readExcel(excelFilePath);
			 
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		}
		for (ExcelImportRow excelImportRow : excelImportRows) {
			Employee employee1 = GetAEmployee.getInstance().getAEmployee(excelImportRow.getEmployee_id());
			if (employee1!=null) {
				excelImportRow.setName(employee1.getName());
				excelImportRow.setRole_id(employee1.getRole_id());
				if(excelImportRow.getRole_id()==5) {
					excelImportRow.setStatus("Failed");
				}
			}
			else {
				excelImportRow.setStatus("Failed");
			}
			
		}
		ArrayList<LogTimekeepingOfficer> Log1 = GetTimekeepingOfficer.getInstance().getAllTimekeepings();
		Integer count_log_office=Log1.size();
		ArrayList<LogTimekeepingWorker> Log2 = GetTimekeepingWorker.getInstance().getAllTimekeepings();
		Integer count_log_worker=Log2.size();
		for (ExcelImportRow excelImportRow : excelImportRows) {
			if(excelImportRow.getStatus()==null) {
				if(excelImportRow.getRole_id()==2|| excelImportRow.getRole_id()==4) {
					ArrayList<LogTimekeepingOfficer> officers = GetTimekeepingOfficer.getInstance().getTimekeepingsByEmployeeID(excelImportRow.getEmployee_id());
					if(officers.isEmpty()==true) {
						excelImportRow.setStatus("Sucess");
						LogTimekeepingOfficer newlog = new LogTimekeepingOfficer();
						newlog.setLogID("log"+count_log_office.toString());
						count_log_office++;
						newlog.setTime_in(Time.valueOf(excelImportRow.getTime_in()));
						newlog.setTime_out(Time.valueOf(excelImportRow.getTime_out()));
						newlog.setDate(Date.valueOf(excelImportRow.getDate()));
						newlog.setEmployee_id(excelImportRow.getEmployee_id());
						if(Time.valueOf(excelImportRow.getTime_in()).compareTo(Time.valueOf("12:00:00")) < 0 ) {
							newlog.setMorning(true);
						}
						else {
							newlog.setMorning(false);
						}
						if(Time.valueOf(excelImportRow.getTime_out()).compareTo(Time.valueOf("12:00:00")) > 0 ) {
							newlog.setAfternoon(true);
						}
						else {
							newlog.setAfternoon(false);
						}
						if(Time.valueOf(excelImportRow.getTime_in()).compareTo(Time.valueOf("07:30:00")) <= 0 ) {
							newlog.setHour_late(0);
						}
						else {
							Time time1 = Time.valueOf(excelImportRow.getTime_in());
							Time time2 = Time.valueOf("07:30:00");
							Long k = time1.getTime()-time2.getTime();
							newlog.setHour_late(k/3600000);
						}
						if(Time.valueOf(excelImportRow.getTime_out()).compareTo(Time.valueOf("17:00:00")) >= 0 ) {
							newlog.setHour_early(0);
						}
						else {
							Time time1 = Time.valueOf(excelImportRow.getTime_out());
							Time time2 = Time.valueOf("17:00:00");
							Long k = time2.getTime()-time1.getTime();
							newlog.setHour_early(k/3600000);
						}
						LogTimekeepingOfficerService.getInstance().insert(newlog);
						continue;
						
						
					}
					int check=1;
					for (LogTimekeepingOfficer officer : officers) {
						if (officer.getDate().compareTo(Date.valueOf(excelImportRow.getDate()))==0) {
							excelImportRow.setStatus("duplicate");
							check=-1;
							break;
						}
						else {
							continue;
						}
						
					}
				  if(check==-1) continue;
				  else {
					  excelImportRow.setStatus("Sucess");
						LogTimekeepingOfficer newlog = new LogTimekeepingOfficer();
						newlog.setLogID("log"+count_log_office.toString());
						count_log_office++;
						newlog.setTime_in(Time.valueOf(excelImportRow.getTime_in()));
						newlog.setTime_out(Time.valueOf(excelImportRow.getTime_out()));
						newlog.setDate(Date.valueOf(excelImportRow.getDate()));
						newlog.setEmployee_id(excelImportRow.getEmployee_id());
						if(Time.valueOf(excelImportRow.getTime_in()).compareTo(Time.valueOf("12:00:00")) < 0 ) {
							newlog.setMorning(true);
						}
						else {
							newlog.setMorning(false);
						}
						if(Time.valueOf(excelImportRow.getTime_out()).compareTo(Time.valueOf("12:00:00")) > 0 ) {
							newlog.setAfternoon(true);
						}
						else {
							newlog.setAfternoon(false);
						}
						if(Time.valueOf(excelImportRow.getTime_in()).compareTo(Time.valueOf("07:30:00")) <= 0 ) {
							newlog.setHour_late(0);
						}
						else {
							Time time1 = Time.valueOf(excelImportRow.getTime_in());
							Time time2 = Time.valueOf("07:30:00");
							Long k = time1.getTime()-time2.getTime();
							newlog.setHour_late(k/3600000);
						}
						if(Time.valueOf(excelImportRow.getTime_out()).compareTo(Time.valueOf("17:00:00")) >= 0 ) {
							newlog.setHour_early(0);
						}
						else {
							Time time1 = Time.valueOf(excelImportRow.getTime_out());
							Time time2 = Time.valueOf("17:00:00");
							Long k = time2.getTime()-time1.getTime();
							newlog.setHour_early(k/3600000);
						}
						LogTimekeepingOfficerService.getInstance().insert(newlog);
						continue;
				  }
				}
				else {
					ArrayList<LogTimekeepingWorker> workers = GetTimekeepingWorker.getInstance().getTimekeepingsByEmployeeID(excelImportRow.getEmployee_id());
					if(workers.isEmpty()==true) {
						excelImportRow.setStatus("Sucess");
						LogTimekeepingWorker newlog = new LogTimekeepingWorker();
						newlog.setLogID("log"+count_log_worker.toString());
						count_log_worker++;
						newlog.setTime_in(Time.valueOf(excelImportRow.getTime_in()));
						newlog.setTime_out(Time.valueOf(excelImportRow.getTime_out()));
						newlog.setDate(Date.valueOf(excelImportRow.getDate()));
						newlog.setEmployee_id(excelImportRow.getEmployee_id());
						Time time1 = Time.valueOf(excelImportRow.getTime_in());
						Time time2 = Time.valueOf(excelImportRow.getTime_out());
						Long k = time2.getTime()-time1.getTime();
						k=k/3600000;
						if(k<4) {
							newlog.setShift1(k);
							newlog.setShift2(0);
							newlog.setShift3(0);
						}
						else if(k>4&&k<8) {
							newlog.setShift1(4);
							newlog.setShift2(k-4);
							newlog.setShift3(0);
						}
						else {
							newlog.setShift1(4);
							newlog.setShift2(4);
							newlog.setShift3(k-8);
						}
						LogTimekeepingWorkerService.getInstance().insert(newlog);
						continue;
						
						
					}
					int check=1;
					for (LogTimekeepingWorker worker : workers) {
						if (worker.getDate().compareTo(Date.valueOf(excelImportRow.getDate()))==0) {
							excelImportRow.setStatus("duplicate");
							check=-1;
							break;
						}
						else {
							continue;
						}
						
					}
				  if(check==-1) continue;
				  else {
					  excelImportRow.setStatus("Sucess");
						LogTimekeepingWorker newlog = new LogTimekeepingWorker();
						newlog.setLogID("log"+count_log_worker.toString());
						count_log_worker++;
						newlog.setTime_in(Time.valueOf(excelImportRow.getTime_in()));
						newlog.setTime_out(Time.valueOf(excelImportRow.getTime_out()));
						newlog.setDate(Date.valueOf(excelImportRow.getDate()));
						newlog.setEmployee_id(excelImportRow.getEmployee_id());
						Time time1 = Time.valueOf(excelImportRow.getTime_in());
						Time time2 = Time.valueOf(excelImportRow.getTime_out());
						Long k = time2.getTime()-time1.getTime();
						k = k/3600000;
						if(k<4) {
							newlog.setShift1(k);
							newlog.setShift2(0);
							newlog.setShift3(0);
						}
						else if(k>4&&k<8) {
							newlog.setShift1(4);
							newlog.setShift2(k-4);
							newlog.setShift3(0);
						}
						else {
							newlog.setShift1(4);
							newlog.setShift2(4);
							newlog.setShift3(k-8);
						}
						LogTimekeepingWorkerService.getInstance().insert(newlog);
						}
				  }
				}
		}
	    excelImportRowList = FXCollections.observableArrayList(excelImportRows);
		idColumn.setCellValueFactory(new PropertyValueFactory<ExcelImportRow, Integer>("id"));
		employee_idColumn.setCellValueFactory(new PropertyValueFactory<ExcelImportRow, String>("employee_id"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<ExcelImportRow, String>("date"));
		time_inColumn.setCellValueFactory(new PropertyValueFactory<ExcelImportRow, String>("time_in"));
		time_outColumn.setCellValueFactory(new PropertyValueFactory<ExcelImportRow, String>("time_out"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<ExcelImportRow, String>("name") );
		statusColumn.setCellValueFactory(new PropertyValueFactory<ExcelImportRow, String>("status"));
		table.setItems(excelImportRowList);
	
	
	}	
}
