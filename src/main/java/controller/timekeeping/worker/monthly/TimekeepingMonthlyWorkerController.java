package controller.timekeeping.worker.monthly;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate; 
import java.sql.Time;
import java.time.Year;
import java.util.ArrayList;
import java.util.ResourceBundle;

import config.Config;
import config.FXMLNavigation;
import controller.auth.Authentication;
import controller.timekeeping.worker.daily.TimekeepingDayWorkerDetailController;

import database.RoleDAO;
import database.TimekeepingWorkerDAO;
import database.UnitDAO;
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

import model.employee.Employee;
import model.employee.Unit;

import model.logtimekeeping.LogTimekeepingWorker;

public class TimekeepingMonthlyWorkerController implements Initializable {
	private static LocalDate today = LocalDate.now();
	private Employee employee = Authentication.getInstance().getAuthentication();
	private String[] listMonth = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
	private Integer[] listDayMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	private ArrayList<String> listYear = new ArrayList<>();
	private int countLateEarly = 0;
	
    @FXML
    private AnchorPane basePane;

	@FXML
	private Label user_name;
    
    @FXML
    private Label department;
    
    @FXML
    private Label employeeID;
    
    @FXML
    private Label unit_id;

	@FXML
	private Label unit_name;
    
    @FXML
    private Label role;

    @FXML
    private ChoiceBox<String> chooseMonth;
    
    @FXML
    private ChoiceBox<String> chooseYear;

	@FXML
    private Label monthLabel;

    @FXML
    private Button searchBtn;

	@FXML
	private Button reloadPageBtn;
    
    @FXML
    private TableView<SummaryWorkerTableRow> tableSummary;

    @FXML
    private TableColumn<SummaryWorkerTableRow, Integer> total_day_workCol;

    @FXML
    private TableColumn<SummaryWorkerTableRow, Float> total_hour_workCol;

    @FXML
    private TableColumn<SummaryWorkerTableRow, Float> total_overtimeCol;

    @FXML
    private TableColumn<SummaryWorkerTableRow, Integer> lateEarlyCol;

    @FXML
    private TableView<TimekeepingWorkerTableRow> tableTimekeepingMonth;
    
    @FXML
    private TableColumn<TimekeepingWorkerTableRow,Date> dateCol;

    @FXML
    private TableColumn<TimekeepingWorkerTableRow, String> statusCol;

    @FXML
    private TableColumn<TimekeepingWorkerTableRow, Time> time_inCol;

    @FXML
    private TableColumn<TimekeepingWorkerTableRow, Time> time_outCol;

    @FXML
    private TableColumn<TimekeepingWorkerTableRow, Float> hour_workCol;
    
    @FXML
    private TableColumn<TimekeepingWorkerTableRow, Float> overtimeCol;
    
    private ObservableList<TimekeepingWorkerTableRow> LogTimekeepingMonthList = FXCollections.observableArrayList();
    private ObservableList<SummaryWorkerTableRow> summaryRows = FXCollections.observableArrayList();
    private ArrayList<LogTimekeepingWorker> arrLTW = TimekeepingWorkerDAO.getInstance().getByEmployeeID(employee.getId());

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setListYear();
    	String month = today.toString().split("-")[1];
    	String year = today.toString().split("-")[0];

		String roleText = RoleDAO.getInstance().getById(String.valueOf(employee.getRole_id())).getName();
    	role.setText(roleText);
    	department.setText(employee.getDepartment());
    	employeeID.setText(employee.getId());
    	user_name.setText(employee.getName());
    	unit_id.setText(employee.getUnit_id());
		Unit unit = UnitDAO.getInstance().getById(employee.getUnit_id());
		unit_name.setText(unit.getName());
    	
		chooseMonth.getItems().addAll(listMonth);
		chooseMonth.setValue(month);
		chooseYear.getItems().addAll(listYear);
		chooseYear.setValue(year);
		monthLabel.setText("Tháng " + month + "/" + year);

		getLogByMonth(month, year);
		setDataSummaryTableRow(LogTimekeepingMonthList);
    	dateCol.setCellValueFactory(new PropertyValueFactory<TimekeepingWorkerTableRow,Date>("date"));
    	time_inCol.setCellValueFactory(new PropertyValueFactory<TimekeepingWorkerTableRow,Time>("time_in"));
    	time_outCol.setCellValueFactory(new PropertyValueFactory<TimekeepingWorkerTableRow,Time>("time_out"));
    	hour_workCol.setCellValueFactory(new PropertyValueFactory<TimekeepingWorkerTableRow,Float>("hour_work"));
    	overtimeCol.setCellValueFactory(new PropertyValueFactory<TimekeepingWorkerTableRow,Float>("overtime"));
    	statusCol.setCellValueFactory(new PropertyValueFactory<TimekeepingWorkerTableRow, String>("status"));
    	tableTimekeepingMonth.setItems(LogTimekeepingMonthList);
		highlightRow();
    	
    	total_day_workCol.setCellValueFactory(new PropertyValueFactory<SummaryWorkerTableRow,Integer>("total_day_work"));
    	total_hour_workCol.setCellValueFactory(new PropertyValueFactory<SummaryWorkerTableRow,Float>("total_hour_work"));
    	total_overtimeCol.setCellValueFactory(new PropertyValueFactory<SummaryWorkerTableRow,Float>("total_overtime"));
		lateEarlyCol.setCellValueFactory(new PropertyValueFactory<SummaryWorkerTableRow,Integer>("countLateEarly"));
    	tableSummary.setItems(summaryRows);
    	
    	tableTimekeepingMonth.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
            	TimekeepingWorkerTableRow selectedItem = tableTimekeepingMonth.getSelectionModel().getSelectedItem();
            	if (selectedItem != null) {
            		showDetailPopupWorker(selectedItem);
            	}
            }
        });
	}
    
    public void showDetailPopupWorker(TimekeepingWorkerTableRow selectedItem) {
        try {
			LogTimekeepingWorker logDetail = new LogTimekeepingWorker("", employee.getId(), selectedItem.getDate(), selectedItem.getTime_in(),
					selectedItem.getTime_out(), selectedItem.getShift1(), selectedItem.getShift2(), selectedItem.getShift3());

			FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLNavigation.TIMEKEEPING_DAY_WORKER_DETAIL_VIEW));
			loader.setControllerFactory(c -> new TimekeepingDayWorkerDetailController(employee, logDetail));
			Parent root = loader.load();

			Stage stage = new Stage();
			stage.setTitle("Chi tiết ngày công");
			stage.setScene(new Scene(root));
			stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void searchTable(ActionEvent event) {
    	String month = chooseMonth.getValue();
    	String year = chooseYear.getValue();
    	monthLabel.setText("Tháng " + month + "/" + year);
    	
    	getLogByMonth(month, year);
    	tableTimekeepingMonth.setItems(LogTimekeepingMonthList);

    	setDataSummaryTableRow(LogTimekeepingMonthList);
    	tableSummary.setItems(summaryRows);
    }

	@FXML
	public void reloadPage(ActionEvent event) {
		String month = today.toString().split("-")[1];
		String year = today.toString().split("-")[0];

		chooseMonth.setValue(month);
		chooseYear.setValue(year);
		monthLabel.setText("Tháng " + month + "/" + year);

		getLogByMonth(month, year);
		tableTimekeepingMonth.setItems(LogTimekeepingMonthList);

		setDataSummaryTableRow(LogTimekeepingMonthList);
		tableSummary.setItems(summaryRows);
	}

    private float setFormatHour(float f) {
    	return (float) Math.round(f * 10) / 10;
    }

	public void getLogByMonth(String month, String year) {
		if(Year.of(Integer.parseInt(year)).isLeap()) listDayMonth[1] = 29;

		LogTimekeepingMonthList = FXCollections.observableArrayList();
		countLateEarly = 0;
		for (int i = 1; i <= listDayMonth[Integer.parseInt(month)-1]; i++) {
			String dateFormat = year + "-" + month + "-" + (i<10 ? "0"+i : ""+i);
			boolean checkExistLog = false;

			for (LogTimekeepingWorker log: arrLTW) {
				if (log.getDate().toString().contains(dateFormat))
				{
					Date date = log.getDate();
					Time time_in = log.getTime_in();
					Time time_out = log.getTime_out();
					float hour_work = setFormatHour(log.getShift1() + log.getShift2());
					float overtime = log.getShift3() ;
					String status = "";

					if((time_in.compareTo(Time.valueOf(Config.WORKER_START_SHIFT1)) > 0 && time_in.compareTo(Time.valueOf(Config.WORKER_END_SHIFT1)) < 0)
							|| (time_in.compareTo(Time.valueOf(Config.WORKER_START_SHIFT2)) > 0 && time_in.compareTo(Time.valueOf(Config.WORKER_END_SHIFT2)) < 0)) {
						status += "Đi muộn ";
						countLateEarly++;
					}

					if((time_out.compareTo(Time.valueOf(Config.WORKER_END_SHIFT1)) < 0 && time_out.compareTo(Time.valueOf(Config.WORKER_START_SHIFT1)) > 0)
							|| (time_out.compareTo(Time.valueOf(Config.WORKER_END_SHIFT2)) < 0 && time_out.compareTo(Time.valueOf(Config.WORKER_START_SHIFT2)) > 0)) {
						status +="Về sớm ";
						countLateEarly++;
					}

					if(time_in.compareTo(Time.valueOf(Config.WORKER_START_SHIFT1)) <= 0 && time_out.compareTo(Time.valueOf(Config.WORKER_END_SHIFT2)) >= 0) status = "Đạt";

					LogTimekeepingMonthList.add(new TimekeepingWorkerTableRow(date, time_in, time_out, hour_work, overtime, status, log.getShift1(), log.getShift2(), log.getShift3()));
					checkExistLog = true;
					break;
				}
			}

			if(!checkExistLog) {
				if(Date.valueOf(dateFormat).compareTo(Date.valueOf(today.toString()))<0) {
					LogTimekeepingMonthList.add(new TimekeepingWorkerTableRow(Date.valueOf(dateFormat), "Nghỉ"));
				} else {
					LogTimekeepingMonthList.add(new TimekeepingWorkerTableRow(Date.valueOf(dateFormat), "Chưa làm"));
				}
			}
		}
	}

	public void setDataSummaryTableRow(ObservableList<TimekeepingWorkerTableRow> LogInMonth) {
		int total_day_work = 0;
		float total_hour_work = 0f;
		float total_overtime= 0f;

		for(TimekeepingWorkerTableRow r : LogInMonth) {
			if(r.getTime_in() != null) total_day_work++;
			total_hour_work += r.getHour_work();
			total_overtime += r.getOvertime();
		}
		total_hour_work = setFormatHour(total_hour_work);
		total_overtime = setFormatHour(total_overtime);

		summaryRows = FXCollections.observableArrayList();
		summaryRows.add(new SummaryWorkerTableRow(total_day_work, total_hour_work, total_overtime, countLateEarly));
	}

	private void highlightRow(){
		tableTimekeepingMonth.setRowFactory(tv -> new TableRow<TimekeepingWorkerTableRow>() {
            @Override
            protected void updateItem(TimekeepingWorkerTableRow item, boolean empty) {
				super.updateItem(item, empty);
				if (!empty && item != null) {
					if (item.getStatus().equals("Nghỉ")) {
						setStyle("-fx-background-color: #f8bcbc;");
					} else if (item.getStatus().equals("Đi muộn ") || item.getStatus().equals("Về sớm") || item.getStatus().equals("Đi muộn Về sớm")){
						setStyle("-fx-background-color: #ffecc9;");
					} else setStyle("");
				}else setStyle("");
            }
        });
    }

	private void setListYear()
	{
		int currentYear = today.getYear();
		for (int year = currentYear; year >= 2000 ; year--) {
			listYear.add(String.valueOf(year));
		}
	}
}