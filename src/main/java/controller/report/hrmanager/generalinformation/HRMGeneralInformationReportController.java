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

public class HRMGeneralInformationReportController implements Initializable {

    @FXML
    private AnchorPane basePane;

    @FXML
    private RadioButton allBtn;

    @FXML
    private Label officerAfternoonLabel;

    @FXML
    private Label officerEarlyLabel;

    @FXML
    private Label officerLateLabel;

    @FXML
    private Label officerMorningLabel;
    
    @FXML
    private Label workerEarlyLabel;

    @FXML
    private Label workerLateLabel;

    @FXML
    private Label workerShift1Label;

    @FXML
    private Label workerShift2Label;

    @FXML
    private Label workerShift3Label;
    
    @FXML
    private Label viewLabel;

    @FXML
    private ChoiceBox<String> monthBox;

    @FXML
    private RadioButton monthBtn;

    @FXML
    private ChoiceBox<String> monthYearBox;

    @FXML
    private ChoiceBox<String> quarterBox;

    @FXML
    private RadioButton quarterBtn;

    @FXML
    private ChoiceBox<String> quarterYearBox;

    @FXML
    private Button submitBtn;

    @FXML
    private RadioButton unitBtn;

    @FXML
    private ChoiceBox<String> unitNameBox;

    @FXML
    private ToggleGroup viewBy;

    @FXML
    private ChoiceBox<String> yearBox;

    @FXML
    private RadioButton yearBtn;
    
    @FXML
    private Label monthYearLabel;
    
    @FXML
    private Label quarterYearLabel;

    
    String[] listMonth = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    String[] listQuarter = {"01", "02", "03", "04"};
    String[] listYear = {"2023", "2022", "2021", "2020"};
    Set<String> listUnit = GeneralInformationUnit.getListUnit();
    
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
		double timeShift1W = GeneralInformationWorker.countTimeShift1ByMonth(month, year);
		workerShift1Label.setText(""+timeShift1W);
		double timeShift2W = GeneralInformationWorker.countTimeShift2ByMonth(month, year);
		workerShift2Label.setText(""+timeShift2W);
		double timeShift3W = GeneralInformationWorker.countTimeShift3ByMonth(month, year);
		workerShift3Label.setText(""+timeShift3W);
		double hourLateO = GeneralInformationOfficer.countHourLateByMonth(month, year);
		officerLateLabel.setText(""+hourLateO);
		double hourEarlyO = GeneralInformationOfficer.countHourEarlyByMonth(month, year);
		officerEarlyLabel.setText(""+hourEarlyO);
		double hourLateW = GeneralInformationWorker.countHourLateByMonth(month, year);
		workerLateLabel.setText(""+hourLateW);
		double hourEarlyW = GeneralInformationWorker.countHourEarlyByMonth(month, year);
		workerEarlyLabel.setText(""+hourEarlyW);
		int morningO = GeneralInformationOfficer.countMorningByMonth(month, year);
		officerMorningLabel.setText(""+morningO);
		int afternoonO = GeneralInformationOfficer.countAfternoonByMonth(month, year);
		officerAfternoonLabel.setText(""+afternoonO);
	}
	
	void getMonthUnitInfo(String unit_id) {
		int month = Integer.parseInt(monthBox.getValue().toString());
		int year = Integer.parseInt(monthYearBox.getValue().toString());
		double timeShift1W = GeneralInformationUnitWorker.countTimeShift1ByMonth(unit_id, month, year);
		workerShift1Label.setText(""+timeShift1W);
		double timeShift2W = GeneralInformationUnitWorker.countTimeShift2ByMonth(unit_id, month, year);
		workerShift2Label.setText(""+timeShift2W);
		double timeShift3W = GeneralInformationUnitWorker.countTimeShift3ByMonth(unit_id, month, year);
		workerShift3Label.setText(""+timeShift3W);
		double hourLateO = GeneralInformationOfficerUnit.countHourLateByMonth(unit_id, month, year);
		officerLateLabel.setText(""+hourLateO);
		double hourEarlyO = GeneralInformationOfficerUnit.countHourEarlyByMonth(unit_id, month, year);
		officerEarlyLabel.setText(""+hourEarlyO);
		double hourLateW = GeneralInformationUnitWorker.countHourLateByMonth(unit_id, month, year);
		workerLateLabel.setText(""+hourLateW);
		double hourEarlyW = GeneralInformationUnitWorker.countHourEarlyByMonth(unit_id, month, year);
		workerEarlyLabel.setText(""+hourEarlyW);
		int morningO = GeneralInformationOfficerUnit.countMorningByMonth(unit_id, month, year);
		officerMorningLabel.setText(""+morningO);
		int afternoonO = GeneralInformationOfficerUnit.countAfternoonByMonth(unit_id, month, year);
		officerAfternoonLabel.setText(""+afternoonO);
	}
	
	void getQuarterInfo() {
		int quarter = Integer.parseInt(quarterBox.getValue().toString());
		int year = Integer.parseInt(quarterYearBox.getValue().toString());
		double timeShift1W = GeneralInformationWorker.countTimeShift1ByQuarter(quarter, year);
		workerShift1Label.setText(""+timeShift1W);
		double timeShift2W = GeneralInformationWorker.countTimeShift2ByQuarter(quarter, year);
		workerShift2Label.setText(""+timeShift2W);
		double timeShift3W = GeneralInformationWorker.countTimeShift3ByQuarter(quarter, year);
		workerShift3Label.setText(""+timeShift3W);
		double hourLateO = GeneralInformationOfficer.countHourLateByQuarter(quarter, year);
		officerLateLabel.setText(""+hourLateO);
		double hourEarlyO = GeneralInformationOfficer.countHourEarlyByQuarter(quarter, year);
		officerEarlyLabel.setText(""+hourEarlyO);
		double hourLateW = GeneralInformationWorker.countHourLateByQuarter(quarter, year);
		workerLateLabel.setText(""+hourLateW);
		double hourEarlyW = GeneralInformationWorker.countHourEarlyByMonth(quarter, year);
		workerEarlyLabel.setText(""+hourEarlyW);
		int morningO = GeneralInformationOfficer.countMorningByQuarter(quarter, year);
		officerMorningLabel.setText(""+morningO);
		int afternoonO = GeneralInformationOfficer.countAfternoonByQuarter(quarter, year);
		officerAfternoonLabel.setText(""+afternoonO);
	}
	
	void getQuarterUnitInfo(String unit_id) {
		int quarter = Integer.parseInt(quarterBox.getValue().toString());
		int year = Integer.parseInt(quarterYearBox.getValue().toString());
		double timeShift1W = GeneralInformationUnitWorker.countTimeShift1ByQuarter(unit_id, quarter, year);
		workerShift1Label.setText(""+timeShift1W);
		double timeShift2W = GeneralInformationUnitWorker.countTimeShift2ByQuarter(unit_id, quarter, year);
		workerShift2Label.setText(""+timeShift2W);
		double timeShift3W = GeneralInformationUnitWorker.countTimeShift3ByQuarter(unit_id, quarter, year);
		workerShift3Label.setText(""+timeShift3W);
		double hourLateO = GeneralInformationOfficerUnit.countHourLateByQuarter(unit_id, quarter, year);
		officerLateLabel.setText(""+hourLateO);
		double hourEarlyO = GeneralInformationOfficerUnit.countHourEarlyByQuarter(unit_id, quarter, year);
		officerEarlyLabel.setText(""+hourEarlyO);
		double hourLateW = GeneralInformationUnitWorker.countHourLateByQuarter(unit_id, quarter, year);
		workerLateLabel.setText(""+hourLateW);
		double hourEarlyW = GeneralInformationUnitWorker.countHourEarlyByMonth(unit_id, quarter, year);
		workerEarlyLabel.setText(""+hourEarlyW);
		int morningO = GeneralInformationOfficerUnit.countMorningByQuarter(unit_id, quarter, year);
		officerMorningLabel.setText(""+morningO);
		int afternoonO = GeneralInformationOfficerUnit.countAfternoonByQuarter(unit_id, quarter, year);
		officerAfternoonLabel.setText(""+afternoonO);
	}
	
	void getYearInfo() {
		int year = Integer.parseInt(yearBox.getValue().toString());
		double timeShift1W = GeneralInformationWorker.countTimeShift1ByYear(year);
		workerShift1Label.setText(""+timeShift1W);
		double timeShift2W = GeneralInformationWorker.countTimeShift2ByYear(year);
		workerShift2Label.setText(""+timeShift2W);
		double timeShift3W = GeneralInformationWorker.countTimeShift3ByYear(year);
		workerShift3Label.setText(""+timeShift3W);
		double hourLateO = GeneralInformationOfficer.countHourLateByYear(year);
		officerLateLabel.setText(""+hourLateO);
		double hourEarlyO = GeneralInformationOfficer.countHourEarlyByYear(year);
		officerEarlyLabel.setText(""+hourEarlyO);
		double hourLateW = GeneralInformationWorker.countHourLateByYear(year);
		workerLateLabel.setText(""+hourLateW);
		double hourEarlyW = GeneralInformationWorker.countHourLateByYear(year);
		workerEarlyLabel.setText(""+hourEarlyW);
		int morningO = GeneralInformationOfficer.countMorningByYear(year);
		officerMorningLabel.setText(""+morningO);
		int afternoonO = GeneralInformationOfficer.countAfternoonByYear(year);
		officerAfternoonLabel.setText(""+afternoonO);
	}
	
	void getYearUnitInfo(String unit_id) {
		int year = Integer.parseInt(yearBox.getValue().toString());
		double timeShift1W = GeneralInformationUnitWorker.countTimeShift1ByYear(unit_id, year);
		workerShift1Label.setText(""+timeShift1W);
		double timeShift2W = GeneralInformationUnitWorker.countTimeShift2ByYear(unit_id, year);
		workerShift2Label.setText(""+timeShift2W);
		double timeShift3W = GeneralInformationUnitWorker.countTimeShift3ByYear(unit_id, year);
		workerShift3Label.setText(""+timeShift3W);
		double hourLateO = GeneralInformationOfficerUnit.countHourLateByYear(unit_id, year);
		officerLateLabel.setText(""+hourLateO);
		double hourEarlyO = GeneralInformationOfficerUnit.countHourEarlyByYear(unit_id, year);
		officerEarlyLabel.setText(""+hourEarlyO);
		double hourLateW = GeneralInformationUnitWorker.countHourLateByYear(unit_id, year);
		workerLateLabel.setText(""+hourLateW);
		double hourEarlyW = GeneralInformationUnitWorker.countHourLateByYear(unit_id, year);
		workerEarlyLabel.setText(""+hourEarlyW);
		int morningO = GeneralInformationOfficerUnit.countMorningByYear(unit_id, year);
		officerMorningLabel.setText(""+morningO);
		int afternoonO = GeneralInformationOfficerUnit.countAfternoonByYear(unit_id, year);
		officerAfternoonLabel.setText(""+afternoonO);
	}
	
	void getAllInfo() {
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
