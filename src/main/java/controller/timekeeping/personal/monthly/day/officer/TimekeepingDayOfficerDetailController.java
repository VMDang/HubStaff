package controller.timekeeping.personal.monthly.day.officer;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.util.ResourceBundle;

import controller.auth.Authentication;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.logtimekeeping.LogTimekeepingOfficer;


public class TimekeepingDayOfficerDetailController {
	private static LogTimekeepingOfficer log; 
	
    public LogTimekeepingOfficer getLog() {
		return log;
	}

	public void setLog(LogTimekeepingOfficer log) {
		TimekeepingDayOfficerDetailController.log = log;
	}

	@FXML
    private Text department;

    @FXML
    private Text employeeID;

    @FXML
    private Text nameEmployee;
    
    @FXML
    private Text unit;

    @FXML
    private TableView<LogTimekeepingOfficer> tableDetail;

    @FXML
    private TableColumn<LogTimekeepingOfficer, String> morningCol;

    @FXML
    private TableColumn<LogTimekeepingOfficer, String> afternoonCol;

    @FXML
    private TableColumn<LogTimekeepingOfficer, Integer> salaryCol;

    @FXML
    private TableColumn<LogTimekeepingOfficer, Time> time_inCol;

    @FXML
    private TableColumn<LogTimekeepingOfficer, Time> time_outCol;

    @FXML
    private Label timebtn;
    
    public void initialize(URL arg0, ResourceBundle arg1) {
    	department.setText(Authentication.getInstance().getAuthentication().getDepartment());
    	employeeID.setText(Authentication.getInstance().getAuthentication().getId());
    	nameEmployee.setText(Authentication.getInstance().getAuthentication().getName());
    	unit.setText(Authentication.getInstance().getAuthentication().getUnit_id());
    	
    	timebtn.setText(log.getDate().toString());
    	ObservableList<LogTimekeepingOfficer> logRow = FXCollections.observableArrayList();
    	logRow.add(log);
    	time_inCol.setCellValueFactory(new PropertyValueFactory<LogTimekeepingOfficer,Time>("time_in"));
    	time_outCol.setCellValueFactory(new PropertyValueFactory<LogTimekeepingOfficer,Time>("time_out"));
    	morningCol.setCellValueFactory(cellData -> {
            // Perform the salary calculation based on the Employee object
    		LogTimekeepingOfficer log = cellData.getValue();
            String morning = log.isMorning() ? "Đi Làm" : "Nghỉ";
            return new SimpleStringProperty(morning);
        });
    	afternoonCol.setCellValueFactory(cellData -> {
            // Perform the salary calculation based on the Employee object
    		LogTimekeepingOfficer log = cellData.getValue();
            String afternoon = log.isMorning() ? "Đi Làm" : "Nghỉ";
            return new SimpleStringProperty(afternoon);
        });
    	
    	
    	salaryCol.setCellValueFactory(cellData -> {
            // Perform the salary calculation based on the Employee object
    		if(log.getTime_in() != null) { 
	    		LogTimekeepingOfficer log = cellData.getValue();
	            int salary = calculateSalary(log);
	            return new SimpleIntegerProperty(salary).asObject();
    		}else return new SimpleIntegerProperty(0).asObject();
        });
    	
    	
    	
    	tableDetail.setItems(logRow);
    }
    
    public int calculateSalary(LogTimekeepingOfficer log) {
    	if(log == null) return 0;
    	float hour_work = 0f;
		if(log.getTime_in().compareTo(Time.valueOf("11:30:00")) < 0 && log.getTime_out().compareTo(Time.valueOf("13:00:00")) > 0) {
			hour_work = setFormatHour(((float)Time.valueOf("11:30:00").getTime() - (float)log.getTime_in().getTime())/(float)3600000 +  ((float)log.getTime_out().getTime()-(float)Time.valueOf("13:00:00").getTime())/(float)3600000);
		}
    	
		if(log.getTime_out().compareTo(Time.valueOf("17:00:00")) < 0 ) {
			if(log.getTime_out().compareTo(Time.valueOf("11:30:00")) < 0) {
				if(log.getTime_in().compareTo(Time.valueOf("07:30:00")) > 0 ) {
					hour_work = setFormatHour((log.getTime_out().getTime()-log.getTime_in().getTime())/3600000);
				}else hour_work = setFormatHour((log.getTime_out().getTime()-Time.valueOf("07:30:00").getTime())/3600000);
			}
		}
		if(log.getTime_in().compareTo(Time.valueOf("11:30:00")) >=0 && log.getTime_in().compareTo(Time.valueOf("13:00:00")) <=0 && log.getTime_out().compareTo(Time.valueOf("13:00:00")) >0 ) {
			if(log.getTime_out().compareTo(Time.valueOf("17:00:00")) < 0) {
				hour_work = setFormatHour(((float)log.getTime_out().getTime() - (float)Time.valueOf("13:00:00").getTime())/(float)3600000 );
			}
			if(log.getTime_out().compareTo(Time.valueOf("17:00:00")) > 0 && log.getTime_out().compareTo(Time.valueOf("17:30:00")) < 0) {
				hour_work = 4.0f;
			}
		}
		
		if(log.getTime_in().compareTo(Time.valueOf("07:30:00")) <= 0 && log.getTime_out().compareTo(Time.valueOf("17:00:00")) >= 0 && log.getTime_out().compareTo(Time.valueOf("17:30:00")) <= 0) {
			hour_work = 8;
		}
		if(log.getTime_in().compareTo(Time.valueOf("07:30:00")) <= 0 && log.getTime_out().compareTo(Time.valueOf("17:30:00")) > 0 ) {
			
			hour_work = setFormatHour(8f +  ((float)log.getTime_out().getTime()-(float)Time.valueOf("17:30:00").getTime())/(float)3600000);
		}
		float overtime = setFormatHour(hour_work-8.0f) < 0 ? 0.0f : setFormatHour(hour_work-8f) ;
		if(log.getTime_in().compareTo(Time.valueOf("13:00:00")) >=0 && log.getTime_out().compareTo(Time.valueOf("13:00:00")) >0 ) {
			if(log.getTime_out().compareTo(Time.valueOf("17:00:00")) < 0) {
				hour_work = setFormatHour(((float)log.getTime_out().getTime() - (float)log.getTime_in().getTime())/(float)3600000 );
			}
			if(log.getTime_out().compareTo(Time.valueOf("17:30:00")) > 0) {
				hour_work = setFormatHour(((float)Time.valueOf("17:00:00").getTime() - (float)log.getTime_in().getTime())/(float)3600000  + ((float)log.getTime_out().getTime() - (float)Time.valueOf("17:30:00").getTime())/(float)3600000);
				overtime = ((float)log.getTime_out().getTime() - (float)Time.valueOf("17:30:00").getTime())/(float)3600000;
			}
		}
		if(log.getTime_in().compareTo(Time.valueOf("17:00:00")) > 0 && log.getTime_in().compareTo(Time.valueOf("17:30:00")) <= 0 && log.getTime_out().compareTo(Time.valueOf("21:30:00")) <= 0 ) {
			overtime = ((float)log.getTime_out().getTime() - (float)Time.valueOf("17:30:00").getTime())/(float)3600000;
			hour_work = overtime;
		}
		if(log.getTime_in().compareTo(Time.valueOf("17:00:00")) > 0 && log.getTime_in().compareTo(Time.valueOf("17:30:00")) <= 0 && log.getTime_out().compareTo(Time.valueOf("21:30:00")) > 0 ) {
			overtime = 4f;
			hour_work = overtime;
		}
    	return (int) Math.round(30000*(hour_work-overtime) + 60000*overtime);
    }
    public static float setFormatHour(float f) {
    	return (float) Math.round(f * 10) / 10;
    }
    public static void main(String[] args) {
    	TimekeepingDayOfficerDetailController controller = new TimekeepingDayOfficerDetailController();
		LogTimekeepingOfficer log = new LogTimekeepingOfficer("LOG01","nv01",Date.valueOf("2023-06-01"), Time.valueOf("13:00:00"),Time.valueOf("21:30:00"), false, true);
		int result = controller.calculateSalary(log);
		System.out.println(result);
	}
}
