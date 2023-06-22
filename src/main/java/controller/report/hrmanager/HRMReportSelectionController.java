package controller.report.hrmanager;

import static controller.fxml.FxmlConstains.*;

import controller.layouts.LayoutController;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

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
    void switchToReportUnitWorker(MouseEvent event) throws IOException {
    	layout.changeAnchorPane(basePane, REPORT_MONTHLY_UNIT_WORKER_VIEW);
    }

    @FXML
    void switchToReportUnitOfficer(MouseEvent event) {

    }

    @FXML
    void switchToReportGeneral(MouseEvent event) {

    }
}
