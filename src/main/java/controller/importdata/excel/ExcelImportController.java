package controller.importdata.excel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.employee.Employee;
import model.logtimekeeping.LogTimekeepingOfficer;
import model.logtimekeeping.LogTimekeepingWorker;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Workbook;
import com.mysql.cj.log.Log;
import dbtimekeeping.gettimekeeping.GetTimekeepingOfficer;
import dbtimekeeping.gettimekeeping.GetTimekeepingWorker;
import dbtimekeeping.inserttimekeeping.InsertTimekeepingOfficer;
import dbtimekeeping.inserttimekeeping.InsertTimekeepingWorker;
import hrsystem.GetAEmployee;

public class ExcelImportController   {
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
	private javafx.scene.control.TextField textFieldIn ;
	@FXML
	private TextField textFieldOut;
	private String url_in;
	private String url_out;
	@FXML
	Button nhapButton;
	@FXML
	Button xemButton;
	public void ChooseFileIn (ActionEvent e) {
		Stage stage =(Stage) basePane.getScene().getWindow();
		FileChooser fc = new FileChooser();
		FileChooser.ExtensionFilter excelFilter = new FileChooser.ExtensionFilter("Excel Files", "*.xlsx");
		fc.getExtensionFilters().add(excelFilter);
		File file = fc.showOpenDialog(stage);
		if(file!=null) {
			url_in = file.toURI().toString();
			url_in = url_in.substring(6);
			String abc = file.getName();
			textFieldIn.setText(abc); 
		}
	}
	public void ChooseFileOut (ActionEvent e) {
		Stage stage =(Stage) basePane.getScene().getWindow();
		FileChooser fc = new FileChooser();
		FileChooser.ExtensionFilter excelFilter = new FileChooser.ExtensionFilter("Excel Files", "*.xlsx");
		fc.getExtensionFilters().add(excelFilter);
		File file = fc.showOpenDialog(stage);
		if(file!=null) {
			url_out = file.toURI().toString();
			url_out = url_out.substring(6);
			String abc = file.getName();
			textFieldOut.setText(abc); 
		}
	}
	public void showAlert(String title , String messeage) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(messeage);
		alert.setOnHidden(event ->{
			alert.close();
			alert.setResult(null);
		});
		alert.showAndWait();
	}
	public void XemFile(ActionEvent e) {
		try {
			  if (url_in==null||url_out==null) {
				  showAlert("Thông báo", "Hãy nhập file ");
				  return;
			  }
			  String excelFileInPath = url_in;
			  String excelFileOutPath = url_out;
		      excelImportRows = controller.importdata.excel.ReadExcelIn.readExcel(excelFileInPath);
		      ReadExcelOut.readExcel(excelFileOutPath, excelImportRows);
			 
	} catch (IOException e1) {
		// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(excelImportRows==null) {
	    	  showAlert("Thông báo", "File đầu vào không đúng ");
	    	  return;
	      }
			excelImportRowList = FXCollections.observableArrayList(excelImportRows);
			idColumn.setCellValueFactory(new PropertyValueFactory<ExcelImportRow, Integer>("id"));
			employee_idColumn.setCellValueFactory(new PropertyValueFactory<ExcelImportRow, String>("employee_id"));
			dateColumn.setCellValueFactory(new PropertyValueFactory<ExcelImportRow, String>("date"));
			time_inColumn.setCellValueFactory(new PropertyValueFactory<ExcelImportRow, String>("time_in"));
			time_outColumn.setCellValueFactory(new PropertyValueFactory<ExcelImportRow, String>("time_out"));
			nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
			statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
			 statusColumn.setCellFactory(column -> {
		            return new TableCell<ExcelImportRow, String>() {
		                @Override
		                protected void updateItem(String item, boolean empty) {
		                    super.updateItem(item, empty);
		                    if (item == null || empty) {
		                        setText(null);
		                        setTextFill(Color.BLACK);
		                    } else {
		                        setText(item);	                        
		                        if (item.equals("Failed")||item.equals("Failed because time is error")||item.equals("Failed because time is error")) {
		                            setTextFill(Color.RED);
		                        } else if (item.equals("Success")) {
		                            setTextFill(Color.GREEN);
		                        } else if (item.equals("Duplicate")) {
		                        	setTextFill(Color.BROWN);
		                        }		                        
		                        else {
		                            setTextFill(Color.BLACK);
		                        }
		                    }
		                }
		            };
		        });
			table.setItems(excelImportRowList);		
	}
	public void delete(ActionEvent e) {
		ExcelImportRow selected = table.getSelectionModel().getSelectedItem();
		if(selected==null) {
			return;
		}
		excelImportRowList.remove(selected);
		excelImportRows.remove(selected);
	}
	public void refresh(ActionEvent e) {
		textFieldIn.setText("");
		textFieldOut.setText("");
		if(excelImportRowList==null) {
			return;
		}
		excelImportRowList.removeAll(excelImportRows);
		excelImportRows=null;
		url_in="";
		url_out="";
	}
	public void NhapFile (ActionEvent e)  {
		 if (url_in==null||url_out==null) {
			  showAlert("Thông báo", "Hãy nhập file ");
			  return;
		  }
		if(excelImportRows==null) {
			showAlert("Thông báo","Chưa có dữ liệu ");
			return ;
		}
		if(excelImportRows.isEmpty()==true) {
			showAlert("Thông báo","Chưa có dữ liệu ");
			return ;
		}
		for (ExcelImportRow excelImportRow : excelImportRows) {
			Employee employee1 = GetAEmployee.getInstance().getAEmployee(excelImportRow.getEmployee_id());
			if (employee1.getName()!=null) {
				excelImportRow.setName(employee1.getName());
				excelImportRow.setRole_id(employee1.getRole_id());
				if(excelImportRow.getEmployee_id()==null||excelImportRow.getDate()==null||excelImportRow.getTime_in()==null||excelImportRow.getTime_out()==null) {
					excelImportRow.setStatus("Failed because some fields being missing");
				}
				else if(Time.valueOf(excelImportRow.getTime_in()).compareTo(Time.valueOf(excelImportRow.getTime_out()))>0){
					excelImportRow.setStatus("Failed because time is error");
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
				if(excelImportRow.getRole_id()==2|| excelImportRow.getRole_id()==4||excelImportRow.getRole_id()==5) {
					ArrayList<LogTimekeepingOfficer> officers = GetTimekeepingOfficer.getInstance().getTimekeepingsByEmployeeID(excelImportRow.getEmployee_id());
					if(officers.isEmpty()==true) {
						excelImportRow.setStatus("Success");
						LogTimekeepingOfficer newlog = new LogTimekeepingOfficer();
						int kkg;
						do {
						kkg = -1;
						LogTimekeepingOfficer checkid = GetTimekeepingOfficer.getInstance().getATimekeepingByID("log"+count_log_office.toString());
					
						if(checkid.getLogID()==null) {
							newlog.setLogID("log"+count_log_office.toString());
							kkg=1;
						}
						else {
							count_log_office++;
						}
						}while(kkg==-1);
						
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
							newlog.setHour_late((float)k/3600000);
						}
						if(Time.valueOf(excelImportRow.getTime_out()).compareTo(Time.valueOf("17:00:00")) >= 0 ) {
							newlog.setHour_early(0);
						}
						else {
							Time time1 = Time.valueOf(excelImportRow.getTime_out());
							Time time2 = Time.valueOf("17:00:00");
							Long k = time2.getTime()-time1.getTime();
							newlog.setHour_early((float)k/3600000);
						}
						InsertTimekeepingOfficer.getInstance().insert(newlog);
						continue;	
					}
					int check=1;
					for (LogTimekeepingOfficer officer : officers) {
						if (officer.getDate().compareTo(Date.valueOf(excelImportRow.getDate()))==0) {
							excelImportRow.setStatus("Duplicate");
							check=-1;
							break;
						}
						else {
							continue;
						}
					}
				  if(check==-1) continue;
				  else {
					  excelImportRow.setStatus("Success");
						LogTimekeepingOfficer newlog = new LogTimekeepingOfficer();
						int kkg;
						do {
						kkg = -1;
						LogTimekeepingOfficer checkid = GetTimekeepingOfficer.getInstance().getATimekeepingByID("log"+count_log_office.toString());
						if(checkid.getLogID()==null) {
							newlog.setLogID("log"+count_log_office.toString());
							kkg=1;
						}
						else {
							count_log_office++;
						}
						}while(kkg==-1);
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
							newlog.setHour_late((float)k/3600000);
						}
						if(Time.valueOf(excelImportRow.getTime_out()).compareTo(Time.valueOf("17:00:00")) >= 0 ) {
							newlog.setHour_early(0);
						}
						else {
							Time time1 = Time.valueOf(excelImportRow.getTime_out());
							Time time2 = Time.valueOf("17:00:00");
							Long k = time2.getTime()-time1.getTime();
							newlog.setHour_early((float)k/3600000);
						}
						InsertTimekeepingOfficer.getInstance().insert(newlog);
						continue;
				  }
				}
				else {
					ArrayList<LogTimekeepingWorker> workers = GetTimekeepingWorker.getInstance().getTimekeepingsByEmployeeID(excelImportRow.getEmployee_id());
					if(workers.isEmpty()==true) {
						excelImportRow.setStatus("Success");
						LogTimekeepingWorker newlog = new LogTimekeepingWorker();
						int kkg;
						do {
						kkg = -1;
						LogTimekeepingWorker checkid = GetTimekeepingWorker.getInstance().getATimekeepingByID("log"+count_log_worker.toString());
						if(checkid.getLogID()==null) {
							newlog.setLogID("log"+count_log_worker.toString());
							kkg=1;
						}
						else {
							count_log_worker++;
						}
						}while(kkg==-1);
						count_log_worker++;
						newlog.setTime_in(Time.valueOf(excelImportRow.getTime_in()));
						newlog.setTime_out(Time.valueOf(excelImportRow.getTime_out()));
						newlog.setDate(Date.valueOf(excelImportRow.getDate()));
						newlog.setEmployee_id(excelImportRow.getEmployee_id());
						Time time1 = Time.valueOf(excelImportRow.getTime_in());
						Time time2 = Time.valueOf(excelImportRow.getTime_out());
						Long k = time2.getTime()-time1.getTime();
						float t=(float)k/3600000;
						if(t<4) {
							newlog.setShift1(t);
							newlog.setShift2(0);
							newlog.setShift3(0);
						}
						else if(t>4&&t<8) {
							newlog.setShift1(4);
							newlog.setShift2(t-4);
							newlog.setShift3(0);
						}
						else {
							newlog.setShift1(4);
							newlog.setShift2(4);
							newlog.setShift3(t-8);
						}
						InsertTimekeepingWorker.getInstance().insert(newlog);
						continue;
					}
					int check=1;
					for (LogTimekeepingWorker worker : workers) {
						if (worker.getDate().compareTo(Date.valueOf(excelImportRow.getDate()))==0) {
							excelImportRow.setStatus("Duplicate");
							check=-1;
							break;
						}
						else {
							continue;
						}
						
					}
				  if(check==-1) continue;
				  else {
					  excelImportRow.setStatus("Success");
						LogTimekeepingWorker newlog = new LogTimekeepingWorker();
						int kkg;
						do {
							kkg = -1;
							LogTimekeepingWorker checkid = GetTimekeepingWorker.getInstance().getATimekeepingByID("log"+count_log_worker.toString());
							if(checkid.getLogID()==null) {
								newlog.setLogID("log"+count_log_worker.toString());
								kkg=1;
							}
							else {
								count_log_worker++;
							}
							}while(kkg==-1);
						count_log_worker++;
						newlog.setTime_in(Time.valueOf(excelImportRow.getTime_in()));
						newlog.setTime_out(Time.valueOf(excelImportRow.getTime_out()));
						newlog.setDate(Date.valueOf(excelImportRow.getDate()));
						newlog.setEmployee_id(excelImportRow.getEmployee_id());
						Time time1 = Time.valueOf(excelImportRow.getTime_in());
						Time time2 = Time.valueOf(excelImportRow.getTime_out());
						Long k = time2.getTime()-time1.getTime();
						float t =(float) k/3600000;
						if(t<4) {
							newlog.setShift1(t);
							newlog.setShift2(0);
							newlog.setShift3(0);
						}
						else if(t>4&&t<8) {
							newlog.setShift1(4);
							newlog.setShift2(t-4);
							newlog.setShift3(0);
						}
						else {
							newlog.setShift1(4);
							newlog.setShift2(4);
							newlog.setShift3(t-8);
						}
						InsertTimekeepingWorker.getInstance().insert(newlog);
						}
				  }
				}
		}
		showAlert("Thông báo", "Nhập thành công ");
	}
	}