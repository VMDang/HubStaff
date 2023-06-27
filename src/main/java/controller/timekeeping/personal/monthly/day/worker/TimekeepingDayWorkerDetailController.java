package controller.timekeeping.personal.monthly.day.worker;

import java.net.URL;
import java.sql.Time;
import java.util.ResourceBundle;

import controller.auth.Authentication;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.logtimekeeping.LogTimekeepingWorker;

public class TimekeepingDayWorkerDetailController {
	private static LogTimekeepingWorker log; 
	
    public LogTimekeepingWorker getLog() {
		return log;
	}

	public void setLog(LogTimekeepingWorker log) {
		TimekeepingDayWorkerDetailController.log = log;
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
    private TableView<LogTimekeepingWorker> tableDetail;

    @FXML
    private TableColumn<LogTimekeepingWorker, Float> shift1Col;

    @FXML
    private TableColumn<LogTimekeepingWorker, Float> shift2Col;

    @FXML
    private TableColumn<LogTimekeepingWorker, Float> shift3Col;
    
    @FXML
    private TableColumn<LogTimekeepingWorker, Integer> salaryCol;

    @FXML
    private TableColumn<LogTimekeepingWorker, Time> time_inCol;

    @FXML
    private TableColumn<LogTimekeepingWorker, Time> time_outCol;

    @FXML
    private Label timebtn;
    
    public void initialize(URL arg0, ResourceBundle arg1) {
    	department.setText(Authentication.getInstance().getAuthentication().getDepartment());
    	employeeID.setText(Authentication.getInstance().getAuthentication().getId());
    	nameEmployee.setText(Authentication.getInstance().getAuthentication().getName());
    	unit.setText(Authentication.getInstance().getAuthentication().getUnit_id());
    	
    	timebtn.setText(log.getDate().toString());
    	ObservableList<LogTimekeepingWorker> logRow = FXCollections.observableArrayList();
    	logRow.add(log);
    	time_inCol.setCellValueFactory(new PropertyValueFactory<LogTimekeepingWorker,Time>("time_in"));
    	time_outCol.setCellValueFactory(new PropertyValueFactory<LogTimekeepingWorker,Time>("time_out"));
    	shift1Col.setCellValueFactory(new PropertyValueFactory<LogTimekeepingWorker,Float>("shift1"));
    	shift2Col.setCellValueFactory(new PropertyValueFactory<LogTimekeepingWorker,Float>("shift2"));
    	shift3Col.setCellValueFactory(new PropertyValueFactory<LogTimekeepingWorker,Float>("shift3"));
    	
    	salaryCol.setCellValueFactory(cellData -> {
            // Perform the salary calculation based on the Employee object
    		LogTimekeepingWorker log = cellData.getValue();
            int salary = calculateSalary(log);
            return new SimpleIntegerProperty(salary).asObject();
        });
    	
    	
    	
    	tableDetail.setItems(logRow);
    }
    
    private int calculateSalary(LogTimekeepingWorker log) {
    	return (int) Math.round(30000*(log.getShift1() + log.getShift2()) + 60000*log.getShift3());
    }
}
