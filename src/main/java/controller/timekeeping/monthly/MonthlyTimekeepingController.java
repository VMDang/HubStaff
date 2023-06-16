package controller.timekeeping.monthly;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MonthlyTimekeepingController implements Initializable {
    @FXML
    private AnchorPane basePane;

    @FXML
    private DatePicker searchByMonth;
    
    Integer[] list = {1,2,3};
    
    @FXML
    private ChoiceBox<Integer> chooseMonth;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		chooseMonth.getItems().addAll(list);
	}
}
