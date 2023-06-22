package controller.importdata.excel;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import model.logtimekeeping.LogTimekeeping;

import java.awt.TextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

import com.mysql.cj.xdevapi.Table;

import dbtimekeeping.GetTimekeepingOfficer;
import dbtimekeeping.GetTimekeepingWorker;
import employee.services.LogTimekeepingOfficerDAO;
import employee.services.LogTimekeepingWorkerDAO;
import hrsystem.GetAEmployee;
public class ExcelImportController  {
	List<ChamCong> chamCongs;
	
	@FXML
	private TableView<ChamCong> table;
	@FXML
	private TableColumn<ChamCong,Integer> idColumn;
	@FXML
	private TableColumn<ChamCong,String> employee_idColumn;
	@FXML
	private TableColumn<ChamCong,String> dateColumn;
	@FXML
	private TableColumn<ChamCong,String> time_inColumn;
	@FXML
	private TableColumn<ChamCong,String> time_outColumn;
	@FXML
	private TableColumn<ChamCong, String> nameColumn;
	@FXML
	private TableColumn<ChamCong, String> statusColumn;
	private ObservableList<ChamCong> chamCongList;	
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
			  chamCongs = ReadExcelExample.readExcel(excelFilePath);
			 
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		}
		for (ChamCong chamCong : chamCongs) {
			Employee employee1 = GetAEmployee.getInstance().getAEmployee(chamCong.getEmployee_id());
			if (employee1!=null) {
				chamCong.setName(employee1.getName());
				chamCong.setRole_id(employee1.getRole_id());
				if(chamCong.getRole_id()==5) {
					chamCong.setStatus("Failed"); 
				}
			}
			else {
				chamCong.setStatus("Failed");
			}
			
		}
		ArrayList<LogTimekeepingOfficer> Log1 = GetTimekeepingOfficer.getInstance().getAllTimekeepings();
		Integer count_log_office=Log1.size();
		ArrayList<LogTimekeepingWorker> Log2 = GetTimekeepingWorker.getInstance().getAllTimekeepings();
		Integer count_log_worker=Log2.size();
		for (ChamCong chamCong : chamCongs) {
			if(chamCong.getStatus()==null) {
				if(chamCong.getRole_id()==2||chamCong.getRole_id()==4) {
					ArrayList<LogTimekeepingOfficer> officers = GetTimekeepingOfficer.getInstance().getTimekeepingsByEmployeeID(chamCong.getEmployee_id());
					if(officers.isEmpty()==true) {
						chamCong.setStatus("Sucess");
						LogTimekeepingOfficer newlog = new LogTimekeepingOfficer();
						newlog.setLogID("log"+count_log_office.toString());
						count_log_office++;
						newlog.setTime_in(Time.valueOf(chamCong.getTime_in()));
						newlog.setTime_out(Time.valueOf(chamCong.getTime_out()));
						newlog.setDate(Date.valueOf(chamCong.getDate()));
						newlog.setEmployee_id(chamCong.getEmployee_id());
						if(Time.valueOf(chamCong.getTime_in()).compareTo(Time.valueOf("12:00:00")) < 0 ) {
							newlog.setMorning(true);
						}
						else {
							newlog.setMorning(false);
						}
						if(Time.valueOf(chamCong.getTime_out()).compareTo(Time.valueOf("12:00:00")) > 0 ) {
							newlog.setAfternoon(true);
						}
						else {
							newlog.setAfternoon(false);
						}
						if(Time.valueOf(chamCong.getTime_in()).compareTo(Time.valueOf("07:30:00")) <= 0 ) {
							newlog.setHour_late(0);
						}
						else {
							Time time1 = Time.valueOf(chamCong.getTime_in());
							Time time2 = Time.valueOf("07:30:00");
							Long k = time1.getTime()-time2.getTime();
							newlog.setHour_late(k/3600000);
						}
						if(Time.valueOf(chamCong.getTime_out()).compareTo(Time.valueOf("17:00:00")) >= 0 ) {
							newlog.setHour_early(0);
						}
						else {
							Time time1 = Time.valueOf(chamCong.getTime_out());
							Time time2 = Time.valueOf("17:00:00");
							Long k = time2.getTime()-time1.getTime();
							newlog.setHour_early(k/3600000);
						}
						LogTimekeepingOfficerDAO.getInstance().insert(newlog);
						continue;
						
						
					}
					int check=1;
					for (LogTimekeepingOfficer officer : officers) {
						if (officer.getDate().compareTo(Date.valueOf(chamCong.getDate()))==0) {
							chamCong.setStatus("duplicate");
							check=-1;
							break;
						}
						else {
							continue;
						}
						
					}
				  if(check==-1) continue;
				  else {
					  chamCong.setStatus("Sucess");
						LogTimekeepingOfficer newlog = new LogTimekeepingOfficer();
						newlog.setLogID("log"+count_log_office.toString());
						count_log_office++;
						newlog.setTime_in(Time.valueOf(chamCong.getTime_in()));
						newlog.setTime_out(Time.valueOf(chamCong.getTime_out()));
						newlog.setDate(Date.valueOf(chamCong.getDate()));
						newlog.setEmployee_id(chamCong.getEmployee_id());
						if(Time.valueOf(chamCong.getTime_in()).compareTo(Time.valueOf("12:00:00")) < 0 ) {
							newlog.setMorning(true);
						}
						else {
							newlog.setMorning(false);
						}
						if(Time.valueOf(chamCong.getTime_out()).compareTo(Time.valueOf("12:00:00")) > 0 ) {
							newlog.setAfternoon(true);
						}
						else {
							newlog.setAfternoon(false);
						}
						if(Time.valueOf(chamCong.getTime_in()).compareTo(Time.valueOf("07:30:00")) <= 0 ) {
							newlog.setHour_late(0);
						}
						else {
							Time time1 = Time.valueOf(chamCong.getTime_in());
							Time time2 = Time.valueOf("07:30:00");
							Long k = time1.getTime()-time2.getTime();
							newlog.setHour_late(k/3600000);
						}
						if(Time.valueOf(chamCong.getTime_out()).compareTo(Time.valueOf("17:00:00")) >= 0 ) {
							newlog.setHour_early(0);
						}
						else {
							Time time1 = Time.valueOf(chamCong.getTime_out());
							Time time2 = Time.valueOf("17:00:00");
							Long k = time2.getTime()-time1.getTime();
							newlog.setHour_early(k/3600000);
						}
						LogTimekeepingOfficerDAO.getInstance().insert(newlog);
						continue;
				  }
				}
				else {
					ArrayList<LogTimekeepingWorker> workers = GetTimekeepingWorker.getInstance().getTimekeepingsByEmployeeID(chamCong.getEmployee_id());
					if(workers.isEmpty()==true) {
						chamCong.setStatus("Sucess");
						LogTimekeepingWorker newlog = new LogTimekeepingWorker();
						newlog.setLogID("log"+count_log_worker.toString());
						count_log_worker++;
						newlog.setTime_in(Time.valueOf(chamCong.getTime_in()));
						newlog.setTime_out(Time.valueOf(chamCong.getTime_out()));
						newlog.setDate(Date.valueOf(chamCong.getDate()));
						newlog.setEmployee_id(chamCong.getEmployee_id());
						Time time1 = Time.valueOf(chamCong.getTime_in());
						Time time2 = Time.valueOf(chamCong.getTime_out());
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
						LogTimekeepingWorkerDAO.getInstance().insert(newlog);
						continue;
						
						
					}
					int check=1;
					for (LogTimekeepingWorker worker : workers) {
						if (worker.getDate().compareTo(Date.valueOf(chamCong.getDate()))==0) {
							chamCong.setStatus("duplicate");
							check=-1;
							break;
						}
						else {
							continue;
						}
						
					}
				  if(check==-1) continue;
				  else {
					  chamCong.setStatus("Sucess");
						LogTimekeepingWorker newlog = new LogTimekeepingWorker();
						newlog.setLogID("log"+count_log_worker.toString());
						count_log_worker++;
						newlog.setTime_in(Time.valueOf(chamCong.getTime_in()));
						newlog.setTime_out(Time.valueOf(chamCong.getTime_out()));
						newlog.setDate(Date.valueOf(chamCong.getDate()));
						newlog.setEmployee_id(chamCong.getEmployee_id());
						Time time1 = Time.valueOf(chamCong.getTime_in());
						Time time2 = Time.valueOf(chamCong.getTime_out());
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
						LogTimekeepingWorkerDAO.getInstance().insert(newlog);
						}
				  }
				}
		}
	    chamCongList = FXCollections.observableArrayList(chamCongs);
		idColumn.setCellValueFactory(new PropertyValueFactory<ChamCong, Integer>("id"));
		employee_idColumn.setCellValueFactory(new PropertyValueFactory<ChamCong, String>("employee_id"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<ChamCong, String>("date"));
		time_inColumn.setCellValueFactory(new PropertyValueFactory<ChamCong, String>("time_in"));
		time_outColumn.setCellValueFactory(new PropertyValueFactory<ChamCong, String>("time_out"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<ChamCong, String>("name") );
		statusColumn.setCellValueFactory(new PropertyValueFactory<ChamCong, String>("status"));
		table.setItems(chamCongList);
	
	
	}	
}
