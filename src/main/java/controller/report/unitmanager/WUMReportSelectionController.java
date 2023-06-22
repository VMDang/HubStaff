package controller.report.unitmanager;

import controller.layouts.LayoutController;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

import static assets.navigation.FXMLNavigation.WUM_WORKER_UNIT_REPORT_VIEW;

public class WUMReportSelectionController {

    private final LayoutController layout = new LayoutController();

    @FXML
    private AnchorPane basePane;

    @FXML
    private Pane btnReportMyUnitWorker;
    @FXML
    void switchToReportMyUnitWorker(MouseEvent event) throws IOException {
        layout.changeAnchorPane(basePane, WUM_WORKER_UNIT_REPORT_VIEW);
    }
}
