package controller.report.hrmanager.generalinformation;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class HRMGeneralInformationReportController implements Initializable {


    @FXML
    private RadioButton allBtn;

    @FXML
    private Label badOfficerLabel;

    @FXML
    private Label badWorkerLabel;

    @FXML
    private AnchorPane basePane;

    @FXML
    private Label countDepartmentLabel;

    @FXML
    private Label countOfficerLabel;

    @FXML
    private Label countWorkerLabel;

    @FXML
    private Label departmentLabel;

    @FXML
    private Label goodOfficerLabel;

    @FXML
    private Label goodWorkerLabel;

    @FXML
    private ChoiceBox<String> monthBox;

    @FXML
    private RadioButton monthBtn;

    @FXML
    private ChoiceBox<String> monthYearBox;

    @FXML
    private Label monthYearLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label nameUnitLabel;

    @FXML
    private Label officerEarlyLabel;

    @FXML
    private Label officerLateLabel;

    @FXML
    private Pane officerPane;

    @FXML
    private ChoiceBox<String> quarterBox;

    @FXML
    private RadioButton quarterBtn;

    @FXML
    private ChoiceBox<String> quarterYearBox;

    @FXML
    private Label quarterYearLabel;

    @FXML
    private Button submitBtn;

    @FXML
    private ToggleGroup timeBy;

    @FXML
    private RadioButton unitBtn;

    @FXML
    private ChoiceBox<String> unitNameBox;

    @FXML
    private ToggleGroup viewBy;

    @FXML
    private Label viewLabel;

    @FXML
    private Label workerEarlyLabel;

    @FXML
    private Label workerLateLabel;

    @FXML
    private Pane workerPane;

    @FXML
    private ChoiceBox<String> yearBox;

    @FXML
    private RadioButton yearBtn;
    
    private GeneralInformation informationOfficer = new GeneralInformationOfficer();
    
    private GeneralInformationUnit informationOfficerUnit = new GeneralInformationOfficerUnit();
    
    private GeneralInformation informationWorker = new GeneralInformationWorker();
    
    private GeneralInformationUnit informationWorkerUnit = new GeneralInformationWorkerUnit();

    
    String[] listMonth = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    String[] listQuarter = {"01", "02", "03", "04"};
    String[] listYear = {"2023", "2022", "2021", "2020"};
    Set<String> listUnit = GeneralInformation.getListUnit();
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		monthBox.getItems().addAll(listMonth);
		monthBox.setValue("06");
		quarterBox.getItems().addAll(listQuarter);
		quarterBox.setValue("02");
		yearBox.getItems().addAll(listYear);
		yearBox.setValue("2023");
		monthYearBox.getItems().addAll(listYear);
		monthYearBox.setValue("2023");
		quarterYearBox.getItems().addAll(listYear);
		quarterYearBox.setValue("2023");
		unitNameBox.getItems().addAll(listUnit);
		unitNameBox.setValue(listUnit.iterator().next());
		
		allBtn.setOnAction(event -> {
			unitNameBox.setVisible(false);
		});
		
		unitBtn.setOnAction(event -> {
			unitNameBox.setVisible(true);
		});
		monthBtn.setOnAction(event -> {
			showMonth();
			hideQuarter();
			hideYear();
		});
		quarterBtn.setOnAction(event -> {
			hideMonth();
			showQuarter();
			hideYear();
		});
		yearBtn.setOnAction(event -> {
			hideMonth();
			hideQuarter();
			showYear();
		});
		
		getAllInfo();
	}
	void showMonth() {
		monthBox.setVisible(true);
		monthYearLabel.setVisible(true);
		monthYearBox.setVisible(true);		
	}
	void hideMonth() {
		monthBox.setVisible(false);
		monthYearLabel.setVisible(false);
		monthYearBox.setVisible(false);
	}
	void showQuarter() {
		quarterBox.setVisible(true);
		quarterYearLabel.setVisible(true);
		quarterYearBox.setVisible(true);			
	}
	void hideQuarter() {
		quarterBox.setVisible(false);
		quarterYearLabel.setVisible(false);
		quarterYearBox.setVisible(false);		
	}
	void showYear() {
		yearBox.setVisible(true);
	}
	void hideYear() {
		yearBox.setVisible(false);
	}
	
	void getMonthInfo() {
		int month = Integer.parseInt(monthBox.getValue().toString());
		int year = Integer.parseInt(monthYearBox.getValue().toString());
		goodWorkerLabel.setText(""+informationWorker.countGoodMonth(month, year));
		badWorkerLabel.setText(""+informationWorker.countBadMonth(month, year));
		workerLateLabel.setText(""+informationWorker.countHourLateByMonth(month, year));
		workerEarlyLabel.setText(""+informationWorker.countHourEarlyByMonth(month, year));
		goodOfficerLabel.setText(""+informationOfficer.countGoodMonth(month, year));
		badOfficerLabel.setText(""+informationOfficer.countBadMonth(month, year));
		officerLateLabel.setText(""+informationOfficer.countHourLateByMonth(month, year));
		officerEarlyLabel.setText(""+informationOfficer.countHourEarlyByMonth(month, year));
	}
	
	void getMonthUnitInfo(String unit_id) {
		int month = Integer.parseInt(monthBox.getValue().toString());
		int year = Integer.parseInt(monthYearBox.getValue().toString());
		goodWorkerLabel.setText(""+informationWorkerUnit.countGoodMonth(month, year));
		badWorkerLabel.setText(""+informationWorkerUnit.countBadMonth(month, year));
		workerLateLabel.setText(""+informationWorkerUnit.countHourLateByMonth(month, year));
		workerEarlyLabel.setText(""+informationWorkerUnit.countHourEarlyByMonth(month, year));
		goodOfficerLabel.setText(""+informationOfficerUnit.countGoodMonth(month, year));
		badOfficerLabel.setText(""+informationOfficerUnit.countBadMonth(month, year));
		officerLateLabel.setText(""+informationOfficerUnit.countHourLateByMonth(month, year));
		officerEarlyLabel.setText(""+informationOfficerUnit.countHourEarlyByMonth(month, year));
	}
	
	void getQuarterInfo() {
		int quarter = Integer.parseInt(quarterBox.getValue().toString());
		int year = Integer.parseInt(quarterYearBox.getValue().toString());
		goodWorkerLabel.setText(""+informationWorker.countGoodQuarter(quarter, year));
		badWorkerLabel.setText(""+informationWorker.countBadQuarter(quarter, year));
		workerLateLabel.setText(""+informationWorker.countHourLateByQuarter(quarter, year));
		workerEarlyLabel.setText(""+informationWorker.countHourEarlyByQuarter(quarter, year));
		goodOfficerLabel.setText(""+informationOfficer.countGoodQuarter(quarter, year));
		badOfficerLabel.setText(""+informationOfficer.countBadQuarter(quarter, year));
		officerLateLabel.setText(""+informationOfficer.countHourLateByQuarter(quarter, year));
		officerEarlyLabel.setText(""+informationOfficer.countHourEarlyByQuarter(quarter, year));
	}
	
	void getQuarterUnitInfo(String unit_id) {
		int quarter = Integer.parseInt(quarterBox.getValue().toString());
		int year = Integer.parseInt(quarterYearBox.getValue().toString());
		goodWorkerLabel.setText(""+informationWorkerUnit.countGoodQuarter(quarter, year));
		badWorkerLabel.setText(""+informationWorkerUnit.countBadQuarter(quarter, year));
		workerLateLabel.setText(""+informationWorkerUnit.countHourLateByQuarter(quarter, year));
		workerEarlyLabel.setText(""+informationWorkerUnit.countHourEarlyByQuarter(quarter, year));
		goodOfficerLabel.setText(""+informationOfficerUnit.countGoodQuarter(quarter, year));
		badOfficerLabel.setText(""+informationOfficerUnit.countBadQuarter(quarter, year));
		officerLateLabel.setText(""+informationOfficerUnit.countHourLateByQuarter(quarter, year));
		officerEarlyLabel.setText(""+informationOfficerUnit.countHourEarlyByQuarter(quarter, year));
	}
	
	void getYearInfo() {
		int year = Integer.parseInt(yearBox.getValue().toString());
		goodWorkerLabel.setText(""+informationWorker.countGoodYear(year));
		badWorkerLabel.setText(""+informationWorker.countBadYear(year));
		workerLateLabel.setText(""+informationWorker.countHourLateByYear(year));
		workerEarlyLabel.setText(""+informationWorker.countHourEarlyByYear(year));
		goodOfficerLabel.setText(""+informationOfficer.countGoodYear(year));
		badOfficerLabel.setText(""+informationOfficer.countBadYear(year));
		officerLateLabel.setText(""+informationOfficer.countHourLateByYear(year));
		officerEarlyLabel.setText(""+informationOfficer.countHourEarlyByYear(year));
	}
	
	void getYearUnitInfo(String unit_id) {
		int year = Integer.parseInt(yearBox.getValue().toString());
		goodWorkerLabel.setText(""+informationWorkerUnit.countGoodYear(year));
		badWorkerLabel.setText(""+informationWorkerUnit.countBadYear(year));
		workerLateLabel.setText(""+informationWorkerUnit.countHourLateByYear(year));
		workerEarlyLabel.setText(""+informationWorkerUnit.countHourEarlyByYear(year));
		goodOfficerLabel.setText(""+informationOfficerUnit.countGoodYear(year));
		badOfficerLabel.setText(""+informationOfficerUnit.countBadYear(year));
		officerLateLabel.setText(""+informationOfficerUnit.countHourLateByYear(year));
		officerEarlyLabel.setText(""+informationOfficerUnit.countHourEarlyByYear(year));		
	}
	
	void getAllInfo() {
		nameLabel.setText("Tên đơn vị:");
		nameUnitLabel.setText("Doanh nghiệp");
		departmentLabel.setText("Tổng số phòng ban:");
		countDepartmentLabel.setText(""+GeneralInformation.countNumberDepartment());
		countWorkerLabel.setText(""+GeneralInformation.countNumberWorker());
		countOfficerLabel.setText(""+GeneralInformation.countNumberOfficer());
		workerPane.setDisable(false);
		officerPane.setDisable(false);
		if(monthBtn.isSelected()) {
			getMonthInfo();
		}
		else if(quarterBtn.isSelected()) {
			getQuarterInfo();
		}
		else {
			getYearInfo();
		}
	}
	
	void getUnitInfo() {
		String unit_id = unitNameBox.getValue().toString();
		nameLabel.setText("Mã đơn vị:");
		nameUnitLabel.setText(unit_id);
		departmentLabel.setText("Thuộc bộ phận:");
		GeneralInformationUnit.setUnitId(unit_id);
		countDepartmentLabel.setText(GeneralInformationUnit.getDepartment());
		countWorkerLabel.setText(""+GeneralInformationUnit.countNumberWorkerUnit());
		countOfficerLabel.setText(""+GeneralInformationUnit.countNumberOfficerUnit());
		if(GeneralInformationUnit.countNumberWorkerUnit() == 0) {
			workerPane.setDisable(true);
			officerPane.setDisable(false);
		}
		else {
			officerPane.setDisable(true);
			workerPane.setDisable(false);
		}
		if(monthBtn.isSelected()) {
			getMonthUnitInfo(unit_id);
		}
		else if(quarterBtn.isSelected()) {
			getQuarterUnitInfo(unit_id);
		}
		else {
			getYearUnitInfo(unit_id);
		}
	}
    @FXML
    void handleSubmit(ActionEvent event) {
    	if(allBtn.isSelected()) {
    		getAllInfo();
    	}
    	else {
    		getUnitInfo();
    	}
    }
}
