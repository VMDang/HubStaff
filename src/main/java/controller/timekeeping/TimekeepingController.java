package controller.timekeeping;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import static controller.fxml.FxmlConstains.*;

import java.io.IOException;

import controller.layouts.LayoutController;

public class TimekeepingController {
    @FXML
    private AnchorPane basePane;
    
    private LayoutController layout = new LayoutController();

    @FXML
    void switchToMonthly(ActionEvent event) throws IOException {
    	layout.changeAnchorPane(basePane, TIMEKEEPING_MONTHLY_VIEW);
    }
}
