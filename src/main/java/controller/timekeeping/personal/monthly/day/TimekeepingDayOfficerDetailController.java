package controller.timekeeping.personal.monthly.day;

import java.net.URL;
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
    	department.setText(Authentication.authentication.getDepartment());
    	employeeID.setText(Authentication.authentication.getId());
    	nameEmployee.setText(Authentication.authentication.getName());
    	unit.setText(Authentication.authentication.getUnit_id());
    	
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
    		LogTimekeepingOfficer log = cellData.getValue();
            int salary = calculateSalary(log);
            return new SimpleIntegerProperty(salary).asObject();
        });
    	
    	
    	
    	tableDetail.setItems(logRow);
    }
    
    private int calculateSalary(LogTimekeepingOfficer log) {
    	float hour_work = setFormatHour(((float)Time.valueOf("11:30:00").getTime() - (float)log.getTime_in().getTime())/(float)3600000 +  ((float)log.getTime_out().getTime()-(float)Time.valueOf("13:00:00").getTime())/(float)3600000);
		float overtime = setFormatHour(hour_work-8.0f) < 0 ? 0.0f : setFormatHour(hour_work-8.0f) ;
    	return (int) Math.round(30000*(hour_work-overtime) + 60000*overtime);
    }
    public static float setFormatHour(float f) {
    	return (float) Math.round(f * 10) / 10;
    }
}
