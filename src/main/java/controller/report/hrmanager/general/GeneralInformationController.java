package controller.report.hrmanager.general;

import java.net.URL;
import java.util.ResourceBundle;

import dbtimekeeping.GetTimekeepingOfficer;
import dbtimekeeping.GetTimekeepingWorker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class GeneralInformationController implements Initializable {
    @FXML
    private RadioButton allBtn;

    @FXML
    private Label info1;

    @FXML
    private Label info2;

    @FXML
    private Label info3;

    @FXML
    private Label info4;

    @FXML
    private Label info5;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private Label label4;

    @FXML
    private Label label5;
    
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
    @FXML
    void handleSubmit(ActionEvent event) {
    	if(allBtn.isSelected()) {
    		if(monthBtn.isSelected()) {
    			int month = Integer.parseInt(monthBox.getValue().toString());
    			int year = Integer.parseInt(monthYearBox.getValue().toString());
    			viewLabel.setText("Toàn doanh nghiệp Tháng "+ month + " Năm " + year);
    			
    			label1.setText("Chấm công ca 1");
    			double timeShift1Month = GeneralInformationWorker.countTimeShift1ByMonth(month, year);
    			info1.setText(""+timeShift1Month);
    			
    			label2.setText("Chấm công ca 2");
    			double timeShift2Month = GeneralInformationWorker.countTimeShift2ByMonth(month, year);
    			info2.setText(""+timeShift2Month);
    			
    			label3.setText("Chấm công ca 3");
    			double timeShift3Month = GeneralInformationWorker.countTimeShift3ByMonth(month, year);
    			info3.setText(""+timeShift3Month);
    			
    			label4.setText("Số giờ đi muộn");
    			double hourLate = GeneralInformationOfficer.countHourLateByMonth(month, year);
    			info4.setText(""+hourLate);
    			
    			label5.setText("Số giờ về sớm");
    			double hourEarly = GeneralInformationOfficer.countHourEarlyByMonth(month, year);
    			info5.setText(""+hourEarly);
    		}
    		else if(quarterBtn.isSelected()) {
    			int quarter = Integer.parseInt(quarterBox.getValue().toString());
    			int year = Integer.parseInt(quarterYearBox.getValue().toString());
    			viewLabel.setText("Toàn doanh nghiệp Quý "+ quarter + " Năm " + year);
    			
    			label1.setText("Chấm công ca 1");
    			double timeShift1Quarter = GeneralInformationWorker.countTimeShift1ByQuarter(quarter, year);
    			info1.setText(""+timeShift1Quarter);
    			
    			label2.setText("Chấm công ca 2");
    			double timeShift2Quarter = GeneralInformationWorker.countTimeShift2ByQuarter(quarter, year);
    			info2.setText(""+timeShift2Quarter);
    			
    			label3.setText("Chấm công ca 3");
    			double timeShift3Quarter = GeneralInformationWorker.countTimeShift3ByQuarter(quarter, year);
    			info3.setText(""+timeShift3Quarter);
    			
    			label4.setText("Số giờ đi muộn");
    			double hourLateQuarter = GeneralInformationOfficer.countHourLateByQuarter(quarter, year);
    			info4.setText(""+hourLateQuarter);
    			
    			label5.setText("Số giờ về sớm");
    			double hourEarlyQuarter = GeneralInformationOfficer.countHourEarlyByQuarter(quarter, year);
    			info5.setText(""+hourEarlyQuarter);
    		}
    		else {
    			int year = Integer.parseInt(yearBox.getValue().toString());
    			viewLabel.setText("Toàn doanh nghiệp" + " Năm " + year);
    			
    			label1.setText("Chấm công ca 1");
    			double timeShift1Year = GeneralInformationWorker.countTimeShift1ByYear(year);
    			info1.setText(""+timeShift1Year);
    			
    			label2.setText("Chấm công ca 2");
    			double timeShift2Year = GeneralInformationWorker.countTimeShift2ByYear(year);
    			info2.setText(""+timeShift2Year);
    			
    			label3.setText("Chấm công ca 3");
    			double timeShift3Year = GeneralInformationWorker.countTimeShift3ByYear(year);
    			info3.setText(""+timeShift3Year);
    			
    			label4.setText("Số giờ đi muộn");
    			double hourLateYear = GeneralInformationOfficer.countHourLateByYear(year);
    			info4.setText(""+hourLateYear);
    			
    			label5.setText("Số giờ về sớm");
    			double hourEarlyYear = GeneralInformationOfficer.countHourEarlyByYear(year);
    			info5.setText(""+hourEarlyYear);
    		}
    	}
    }
}
