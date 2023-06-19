package controller.timekeeping.monthly;

import java.net.URL;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;

import controller.auth.Authentication;
import dbtimekeeping.GetTimekeepingWorker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.employee.Employee;
import model.employee.worker.Worker;
import model.logtimekeeping.LogTimekeeping;
import model.logtimekeeping.LogTimekeepingWorker;

public class MonthlyTimekeepingController implements Initializable {
    @FXML
    private AnchorPane basePane;
    
    Integer[] list = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    
    @FXML
    private ChoiceBox<Integer> chooseMonth;

	@FXML
    private Label monthBtn;

    @FXML
    private Button refresh;
    
    @FXML
    private TableView tableGenaral;

    @FXML
    private TableColumn totalDayWork;

    @FXML
    private TableColumn totalTime;

    @FXML
    private TableColumn totalTimeTangCa;

    @FXML
    private TableColumn totalTimeWorkMonth;

    @FXML
    private TableView<TimekeepingDayWorkerTable> tableTimekeepingMonth;
    
    @FXML
    private TableColumn<TimekeepingDayWorkerTable,Date> dateCol;

    @FXML
    private TableColumn<TimekeepingDayWorkerTable, String> statusCol;

    @FXML
    private TableColumn<TimekeepingDayWorkerTable, Time> time_inCol;

    @FXML
    private TableColumn<TimekeepingDayWorkerTable, Time> time_outCol;

    @FXML
    private TableColumn<TimekeepingDayWorkerTable, Float> hour_workCol;
    
    @FXML
    private TableColumn<TimekeepingDayWorkerTable, Float> overtimeCol;
    
    private static ObservableList<TimekeepingDayWorkerTable> LogTimekeepingMonthList = FXCollections.observableArrayList();
    
    public static void getData() {
    	if(Authentication.authentication instanceof Worker) {
    		System.out.println("worker" + Authentication.authentication.getId());
    		ArrayList<LogTimekeepingWorker> arrLTW = GetTimekeepingWorker.getInstance().getTimekeepingsByEmployeeID(Authentication.authentication.getId());
    		if(!arrLTW.isEmpty()) {
    			for(LogTimekeepingWorker log : arrLTW) {
    				Date date = log.getDate();
    				Time time_in = log.getTime_in();
    				Time time_out = log.getTime_out();
    				float hour_work = log.getShift1() + log.getShift2() + log.getShift3();
    				float overtime = hour_work - 8;
    				String status = "OK";
    				LogTimekeepingMonthList.add(new TimekeepingDayWorkerTable(date, time_in, time_out, overtime, overtime, status));
    			}
    		}
    	}else {
    		
    	}
    }
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		chooseMonth.getItems().addAll(list);
		
    	getData();
    	
    	dateCol.setCellValueFactory(new PropertyValueFactory<TimekeepingDayWorkerTable,Date>("date"));
    	time_inCol.setCellValueFactory(new PropertyValueFactory<TimekeepingDayWorkerTable,Time>("time_in"));
    	time_outCol.setCellValueFactory(new PropertyValueFactory<TimekeepingDayWorkerTable,Time>("time_out"));
    	hour_workCol.setCellValueFactory(new PropertyValueFactory<TimekeepingDayWorkerTable,Float>("hour_work"));
    	overtimeCol.setCellValueFactory(new PropertyValueFactory<TimekeepingDayWorkerTable,Float>("overtime"));
    	statusCol.setCellValueFactory(new PropertyValueFactory<TimekeepingDayWorkerTable,String>("status"));
    	
    	tableTimekeepingMonth.setItems(LogTimekeepingMonthList);
	}
    
    @FXML
    void resetTable(ActionEvent event) {
    	Integer month = chooseMonth.getValue();
    	monthBtn.setText("Tháng " + month);
    	
    }
}
