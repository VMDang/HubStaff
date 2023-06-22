package controller.importdata;

import static assets.navigation.FXMLNavigation.*;

import java.io.IOException;

import controller.layouts.LayoutController;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ImportSelectionController {
    private LayoutController layout = new LayoutController();

    @FXML
    private AnchorPane basePane;

    @FXML
    private Pane btnImportByExcel;

    @FXML
    private Pane btnImportManual;

    @FXML
    void switchToImportByExcel(MouseEvent event) throws IOException {
    	layout.changeAnchorPane(basePane, EXCEL_IMPORT_VIEW);
    }

    @FXML
    void switchToImportManual(MouseEvent event) {

    }
}
