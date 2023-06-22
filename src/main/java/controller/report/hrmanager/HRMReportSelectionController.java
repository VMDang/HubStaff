package controller.report.hrmanager;

import controller.layouts.LayoutController;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import static controller.fxml.FxmlConstains.*;

import java.io.IOException;

public class HRMReportSelectionController {
    private LayoutController layout = new LayoutController();

    @FXML
    private AnchorPane basePane;

    @FXML
    private Pane btnReportUnitOfficer;

    @FXML
    private Pane btnReportUnitWorker;

    @FXML
    private Pane btnReportGeneral;

    @FXML
    void switchToReportUnitWorker(MouseEvent event) {

    }

    @FXML
    void switchToReportUnitOfficer(MouseEvent event) {

    }

    @FXML
    void switchToReportGeneral(MouseEvent event) {

    }
    
    @FXML
    void switchToGeneralInfo(MouseEvent event) throws IOException {
    	layout.changeAnchorPane(basePane, GENERAL_INFOMATION_VIEW);
    }
}
