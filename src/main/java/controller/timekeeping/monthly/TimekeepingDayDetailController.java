package controller.timekeeping.monthly;

import java.net.URL;
import java.sql.Time;
import java.util.ResourceBundle;

import controller.auth.Authentication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.logtimekeeping.LogTimekeepingWorker;

public class TimekeepingDayDetailController {
	private static LogTimekeepingWorker log; 
	
    public LogTimekeepingWorker getLog() {
		return log;
	}

	public void setLog(LogTimekeepingWorker log) {
		TimekeepingDayDetailController.log = log;
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
    private TableColumn<LogTimekeepingWorker, Time> time_inCol;

    @FXML
    private TableColumn<LogTimekeepingWorker, Time> time_outCol;

    @FXML
    private Label timebtn;
    
    public void initialize(URL arg0, ResourceBundle arg1) {
    	department.setText(Authentication.authentication.getDepartment());
    	employeeID.setText(Authentication.authentication.getId());
    	nameEmployee.setText(Authentication.authentication.getName());
    	unit.setText(Authentication.authentication.getUnit_id());
    	
    	timebtn.setText(log.getDate().toString());
    	ObservableList<LogTimekeepingWorker> logRow = FXCollections.observableArrayList();
    	logRow.add(log);
    	time_inCol.setCellValueFactory(new PropertyValueFactory<LogTimekeepingWorker,Time>("time_in"));
    	time_outCol.setCellValueFactory(new PropertyValueFactory<LogTimekeepingWorker,Time>("time_out"));
    	shift1Col.setCellValueFactory(new PropertyValueFactory<LogTimekeepingWorker,Float>("shift1"));
    	shift2Col.setCellValueFactory(new PropertyValueFactory<LogTimekeepingWorker,Float>("shift2"));
    	shift3Col.setCellValueFactory(new PropertyValueFactory<LogTimekeepingWorker,Float>("shift3"));
    	
    	tableDetail.setItems(logRow);
    }
}
