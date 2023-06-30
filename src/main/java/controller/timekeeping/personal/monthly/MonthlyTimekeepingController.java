package controller.timekeeping.personal.monthly;

import static assets.navigation.FXMLNavigation.TIMEKEEPING_DAY_WORKER_DETAIL_VIEW;
import static assets.navigation.FXMLNavigation.TIMEKEEPING_DAY_OFFICER_DETAIL_VIEW;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate; 
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;

import controller.auth.Authentication;
import controller.report.unitmanager.workerunitreport.WUMWorkerUnitReportRow;
import controller.timekeeping.personal.monthly.day.officer.TimekeepingDayOfficerDetailController;
import controller.timekeeping.personal.monthly.day.worker.TimekeepingDayWorkerDetailController;
import dbtimekeeping.gettimekeeping.GetTimekeepingOfficer;
import dbtimekeeping.gettimekeeping.GetTimekeepingWorker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import model.employee.HRManager;
import model.employee.officer.Officer;
import model.employee.officer.OfficerUnitManager;
import model.employee.worker.Worker;
import model.employee.worker.WorkerUnitManager;

import model.logtimekeeping.LogTimekeepingOfficer;
import model.logtimekeeping.LogTimekeepingWorker;

public class MonthlyTimekeepingController implements Initializable {
	private static LocalDate today;
	
    @FXML
    private AnchorPane basePane;
    
    @FXML
    private Text department;
    
    @FXML
    private Text employeeID;
    
    @FXML
    private Text nameEmployee;
    
    @FXML
    private Text unit;
    
    @FXML
    private Text role;
    
    private String[] listMonth = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private static Integer[] listDayMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private String[] listYear = {"2023", "2022", "2021", "2020"};
    @FXML
    private ChoiceBox<String> chooseMonth;
    
    @FXML
    private ChoiceBox<String> chooseYear;

	@FXML
    private Label monthBtn;

    @FXML
    private Button refresh;
    
    @FXML
    private TableView<GenaralTableRow> tableGenaral;

    @FXML
    private TableColumn<GenaralTableRow, Integer> total_day_workCol;

    @FXML
    private TableColumn<GenaralTableRow, Float> total_hour_workCol;

    @FXML
    private TableColumn<GenaralTableRow, Float> total_overtimeCol;

    @FXML
    private TableColumn<GenaralTableRow, Integer> salaryCol;

    @FXML
    private TableView<TimekeepingEmployeeTableRow> tableTimekeepingMonth;
    
    @FXML
    private TableColumn<TimekeepingEmployeeTableRow,Date> dateCol;

    @FXML
    private TableColumn<TimekeepingEmployeeTableRow, String> statusCol;

    @FXML
    private TableColumn<TimekeepingEmployeeTableRow, Time> time_inCol;

    @FXML
    private TableColumn<TimekeepingEmployeeTableRow, Time> time_outCol;

    @FXML
    private TableColumn<TimekeepingEmployeeTableRow, Float> hour_workCol;
    
    @FXML
    private TableColumn<TimekeepingEmployeeTableRow, Float> overtimeCol;
    
    private static ObservableList<TimekeepingEmployeeTableRow> LogTimekeepingMonthList = FXCollections.observableArrayList();
    private static ObservableList<GenaralTableRow> genaralRows = FXCollections.observableArrayList();
    private static ArrayList<LogTimekeepingWorker> arrLTW = new ArrayList<LogTimekeepingWorker>();
    private static ArrayList<LogTimekeepingOfficer> arrLTO = new ArrayList<LogTimekeepingOfficer>();
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	updateDay();
    	String month = today.toString().split("-")[1];
    	String year = today.toString().split("-")[0];

    	
    	String chucvu  = "";
    	if(Authentication.getInstance().getAuthentication() instanceof Worker) chucvu = "Công nhân";
    	if(Authentication.getInstance().getAuthentication() instanceof WorkerUnitManager) chucvu = "Trưởng đơn vị công nhân";
    	if(Authentication.getInstance().getAuthentication() instanceof Officer) chucvu = "Nhân viên văn phòng";
    	if(Authentication.getInstance().getAuthentication() instanceof OfficerUnitManager) chucvu = "Trưởng đơn vị nhân viên";
    	if(Authentication.getInstance().getAuthentication() instanceof HRManager) chucvu = "Quản lý nhân sự ";
    	
    	role.setText(chucvu);
    	department.setText(Authentication.getInstance().getAuthentication().getDepartment());
    	employeeID.setText(Authentication.getInstance().getAuthentication().getId());
    	nameEmployee.setText(Authentication.getInstance().getAuthentication().getName());
    	unit.setText(Authentication.getInstance().getAuthentication().getUnit_id());

    	
		chooseMonth.getItems().addAll(listMonth);
		chooseMonth.setValue(month);
		chooseYear.getItems().addAll(listYear);
		chooseYear.setValue(year);
		monthBtn.setText("Tháng "+month);
		LogTimekeepingMonthList = FXCollections.observableArrayList();
		arrLTW = new ArrayList<LogTimekeepingWorker>();
	    arrLTO = new ArrayList<LogTimekeepingOfficer>();
		genaralRows = FXCollections.observableArrayList();
		
		getLogTimkeeping();
		getDataMonth(month, year);
    	calculateGenaralData(LogTimekeepingMonthList);
    	dateCol.setCellValueFactory(new PropertyValueFactory<TimekeepingEmployeeTableRow,Date>("date"));
    	time_inCol.setCellValueFactory(new PropertyValueFactory<TimekeepingEmployeeTableRow,Time>("time_in"));
    	time_outCol.setCellValueFactory(new PropertyValueFactory<TimekeepingEmployeeTableRow,Time>("time_out"));
    	hour_workCol.setCellValueFactory(new PropertyValueFactory<TimekeepingEmployeeTableRow,Float>("hour_work"));
    	overtimeCol.setCellValueFactory(new PropertyValueFactory<TimekeepingEmployeeTableRow,Float>("overtime"));
    	statusCol.setCellValueFactory(new PropertyValueFactory<TimekeepingEmployeeTableRow, String>("status"));
    	tableTimekeepingMonth.setItems(LogTimekeepingMonthList);
    	
    	total_day_workCol.setCellValueFactory(new PropertyValueFactory<GenaralTableRow,Integer>("total_day_work"));
    	total_hour_workCol.setCellValueFactory(new PropertyValueFactory<GenaralTableRow,Float>("total_hour_work"));
    	total_overtimeCol.setCellValueFactory(new PropertyValueFactory<GenaralTableRow,Float>("total_overtime"));
    	salaryCol.setCellValueFactory(new PropertyValueFactory<GenaralTableRow,Integer>("salary"));
    	highlightRow();
    	tableGenaral.setItems(genaralRows);
    	
    	tableTimekeepingMonth.setOnMouseClicked(event -> {
            // Kiểm tra xem người dùng đã nhấp chuột đúng vào một hàng hay không
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                // Lấy hàng được chọn
            	TimekeepingEmployeeTableRow selectedItem = tableTimekeepingMonth.getSelectionModel().getSelectedItem();
            	if (selectedItem != null) {
            		if (Authentication.getInstance().getAuthentication() instanceof Worker || Authentication.getInstance().getAuthentication() instanceof WorkerUnitManager) {
                        showDetailPopupWorker(selectedItem);
                    } else {
                    	showDetailPopupOfficer(selectedItem);
                    }
            	}
                
            }
        });
	}
    
    public void showDetailPopupWorker(TimekeepingEmployeeTableRow selectedItem) {
        try {
            // Tạo một FXMLLoader để tải màn hình chi tiết từ file FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(TIMEKEEPING_DAY_WORKER_DETAIL_VIEW));
            fxmlLoader.setControllerFactory(controllerClass -> {
            	TimekeepingDayWorkerDetailController detailController = new TimekeepingDayWorkerDetailController();
                detailController.setLog(new LogTimekeepingWorker("", Authentication.getInstance().getAuthentication().getId(), selectedItem.getDate() ,selectedItem.getTime_in(), selectedItem.getTime_out(), selectedItem.getShift1(), selectedItem.getShift2(),selectedItem.getShift3() ));
                return detailController;
            });
            Parent detailRoot = fxmlLoader.load();
            
            // Tạo một Stage mới cho màn hình chi tiết
            Stage detailStage = new Stage();
            detailStage.initStyle(StageStyle.UTILITY);
            detailStage.initModality(Modality.APPLICATION_MODAL);
            detailStage.initOwner(tableTimekeepingMonth.getScene().getWindow()); // Đặt màn hình chính là chủ sở hữu

         // Truyền dữ liệu cho màn hình chi tiết nếu cần thiết
            TimekeepingDayWorkerDetailController detailController = fxmlLoader.getController();
            detailController.initialize(null, null);
            
            // Đặt nội dung FXML vào Scene của Stage
            Scene detailScene = new Scene(detailRoot);
            detailStage.setScene(detailScene);
            detailStage.show();
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showDetailPopupOfficer(TimekeepingEmployeeTableRow selectedItem) {
        try {
            // Tạo một FXMLLoader để tải màn hình chi tiết từ file FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(TIMEKEEPING_DAY_OFFICER_DETAIL_VIEW));
            fxmlLoader.setControllerFactory(controllerClass -> {
            	TimekeepingDayOfficerDetailController detailController = new TimekeepingDayOfficerDetailController();
                detailController.setLog(new LogTimekeepingOfficer("", Authentication.getInstance().getAuthentication().getId(), selectedItem.getDate() ,selectedItem.getTime_in(), selectedItem.getTime_out(), selectedItem.isMorning(), selectedItem.isAfternoon() ));
                return detailController;
            });
            Parent detailRoot = fxmlLoader.load();
            
            // Tạo một Stage mới cho màn hình chi tiết
            Stage detailStage = new Stage();
            detailStage.initStyle(StageStyle.UTILITY);
            detailStage.initModality(Modality.APPLICATION_MODAL);
            detailStage.initOwner(tableTimekeepingMonth.getScene().getWindow()); // Đặt màn hình chính là chủ sở hữu

         // Truyền dữ liệu cho màn hình chi tiết nếu cần thiết
            TimekeepingDayOfficerDetailController detailController = fxmlLoader.getController();
            detailController.initialize(null, null);
            
            // Đặt nội dung FXML vào Scene của Stage
            Scene detailScene = new Scene(detailRoot);
            detailStage.setScene(detailScene);
            detailStage.show();
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void resetTable(ActionEvent event) {
    	String month = chooseMonth.getValue();
    	String year = chooseYear.getValue();
    	monthBtn.setText("Tháng " + month);
    	
    	LogTimekeepingMonthList = FXCollections.observableArrayList();
    	getDataMonth(month, year);
    	tableTimekeepingMonth.setItems(LogTimekeepingMonthList);
    	
    	genaralRows = FXCollections.observableArrayList();
    	calculateGenaralData(LogTimekeepingMonthList);
    	tableGenaral.setItems(genaralRows);
    }
    
    public static void updateDay() {
    	today = LocalDate.now();
    	
    }
    public static float setFormatHour(float f) {
    	return (float) Math.round(f * 10) / 10;
    }
    public static void getLogTimkeeping() {
    	if (Authentication.getInstance().getAuthentication() instanceof Worker || Authentication.getInstance().getAuthentication() instanceof WorkerUnitManager) {
    		arrLTW = GetTimekeepingWorker.getInstance().getTimekeepingsByEmployeeID(Authentication.getInstance().getAuthentication().getId());
    	}else {
    		arrLTO = GetTimekeepingOfficer.getInstance().getTimekeepingsByEmployeeID(Authentication.getInstance().getAuthentication().getId());
    	}
    }   
	public static void getDataMonth(String month, String year) {
		if(Integer.parseInt(year) % 4 ==0 ) listDayMonth[1] = 29;
		if (Authentication.getInstance().getAuthentication() instanceof Worker || Authentication.getInstance().getAuthentication() instanceof WorkerUnitManager) {
			System.out.println("worker : " + Authentication.getInstance().getAuthentication().getId());
			for (int i = 1; i <= listDayMonth[Integer.parseInt(month)-1]; i++) {
				boolean check = false; 
				for (LogTimekeepingWorker log : arrLTW) {
					TimekeepingEmployeeTableRow ltwt = createTimekeepingDayWorkerTable(log);
					if (ltwt.getDate().toString().split("-")[1].equals(month) && ltwt.getDate().toString().split("-")[2].equals(i < 10 ? "0"+i : ""+i )) {
						LogTimekeepingMonthList.add(ltwt);
						check = true;
						break;
					}
				}
				if(!check) {
					if(Date.valueOf(year+"-"+month+"-"+(i < 10 ? "0"+i : ""+i )).compareTo(Date.valueOf(today.toString()))>0) LogTimekeepingMonthList.add(new TimekeepingEmployeeTableRow(Date.valueOf(year + "-" + month + "-" +(i < 10 ? "0"+i : ""+i )), "Chưa làm"));
					else LogTimekeepingMonthList.add(new TimekeepingEmployeeTableRow(Date.valueOf(year + "-" + month + "-" +(i < 10 ? "0"+i : ""+i )), "Nghỉ"));
				}
			}
		} else {
			System.out.println("officer : " + Authentication.getInstance().getAuthentication().getId());
			for (int i = 1; i <= listDayMonth[Integer.parseInt(month)-1]; i++) {
				boolean check = false; 
				for (LogTimekeepingOfficer log : arrLTO) {
					TimekeepingEmployeeTableRow ltwt = createTimekeepingDayOfficerTable(log);
					if (ltwt.getDate().toString().split("-")[1].equals(month) && ltwt.getDate().toString().split("-")[2].equals(i < 10 ? "0"+i : ""+i )) {
						LogTimekeepingMonthList.add(ltwt);
						check = true;
						break;
					}
				}
				if(!check) {
					if(Date.valueOf(year+"-"+month+"-"+(i < 10 ? "0"+i : ""+i )).compareTo(Date.valueOf(today.toString()))>0) LogTimekeepingMonthList.add(new TimekeepingEmployeeTableRow(Date.valueOf(year + "-" + month + "-" +(i < 10 ? "0"+i : ""+i )), "Chưa làm"));
					else LogTimekeepingMonthList.add(new TimekeepingEmployeeTableRow(Date.valueOf(year + "-" + month + "-" +(i < 10 ? "0"+i : ""+i )), "Nghỉ"));
				}
			}
		} 
	}
	public static void calculateGenaralData(ObservableList<TimekeepingEmployeeTableRow> Logs) {
		genaralRows.add(createGenaralTableRow(Logs));
	}
	public static TimekeepingEmployeeTableRow createTimekeepingDayWorkerTable(LogTimekeepingWorker log) {
		Date date = log.getDate();
		Time time_in = log.getTime_in();
		Time time_out = log.getTime_out();
		float hour_work = setFormatHour(log.getShift1() + log.getShift2() + log.getShift3());
		float overtime = log.getShift3() ;
		String status = "";
		if(time_in.compareTo(Time.valueOf("07:30:00")) > 0 ) status +="Đi muộn ";
		if(time_out.compareTo(Time.valueOf("17:00:00")) < 0 ) status +="Về sớm";
		if(time_in.compareTo(Time.valueOf("07:30:00")) <= 0 && time_out.compareTo(Time.valueOf("17:00:00")) >= 0) status = "Đạt";
		return new TimekeepingEmployeeTableRow(date, time_in, time_out, hour_work, overtime, status, log.getShift1(), log.getShift2(), log.getShift3());
	}
	public static TimekeepingEmployeeTableRow createTimekeepingDayOfficerTable(LogTimekeepingOfficer log) {
		Date date = log.getDate();
		Time time_in = log.getTime_in();
		Time time_out = log.getTime_out();
		float hour_work = setFormatHour(((float)Time.valueOf("11:30:00").getTime() - (float)time_in.getTime())/(float)3600000 +  ((float)time_out.getTime()-(float)Time.valueOf("13:00:00").getTime())/(float)3600000);
		
		String status = "";
		if(time_in.compareTo(Time.valueOf("07:30:00")) > 0 ) status +="Đi muộn ";
		if(time_out.compareTo(Time.valueOf("17:00:00")) < 0 ) {
			status +="Về sớm";
			if(time_out.compareTo(Time.valueOf("11:30:00")) < 0) {
				if(time_in.compareTo(Time.valueOf("07:30:00")) > 0 ) {
					hour_work = setFormatHour((time_out.getTime()-time_in.getTime())/3600000);
				}else hour_work = setFormatHour((time_out.getTime()-Time.valueOf("07:30:00").getTime())/3600000);
			}
		}
		if(time_in.compareTo(Time.valueOf("07:30:00")) <= 0 && time_out.compareTo(Time.valueOf("17:00:00")) >= 0) {
			status ="Đạt";
			hour_work = setFormatHour(((float)Time.valueOf("11:30:00").getTime() - (float)Time.valueOf("07:30:00").getTime())/(float)3600000 +  ((float)time_out.getTime()-(float)Time.valueOf("13:00:00").getTime())/(float)3600000);
		}
		float overtime = setFormatHour(hour_work-8.0f) < 0 ? 0.0f : setFormatHour(hour_work-8.0f) ;
		return new TimekeepingEmployeeTableRow(date, time_in, time_out, hour_work, overtime, status, log.isMorning(), log.isAfternoon());
	}
	public static GenaralTableRow createGenaralTableRow(ObservableList<TimekeepingEmployeeTableRow> LogInMonth) {
		int total_day_work = 0;
		float total_hour_work = 0f;
		float total_overtime= 0f;
		int salary = 0;
		
		for(TimekeepingEmployeeTableRow r : LogInMonth) {
			if(r.getTime_in() != null) total_day_work++;
			total_hour_work += r.getHour_work();
			total_overtime += r.getOvertime();
		}
		total_hour_work = setFormatHour(total_hour_work);
		total_overtime = setFormatHour(total_overtime);
		salary = (int) Math.round((30000*(total_hour_work - total_overtime) + 60000*total_overtime)*10/10);
		return new GenaralTableRow(total_day_work, total_hour_work, total_overtime, salary);
	}
	private void highlightRow(){
		tableTimekeepingMonth.setRowFactory(tv -> new TableRow<TimekeepingEmployeeTableRow>() {
            @Override
            protected void updateItem(TimekeepingEmployeeTableRow item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty && item != null) {
                    if (item.getStatus().equals("Nghỉ")) {
                        setStyle("-fx-background-color: #FAAFAF;");
                    } else if (item.getStatus().equals("Đi muộn ") || item.getStatus().equals("Về sớm") || item.getStatus().equals("Đi muộn Về sớm")){
                        setStyle("-fx-background-color: #FFECB8;");
                    } else setStyle("");
                }else setStyle("");
            }
        });
    }
}