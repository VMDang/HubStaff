package controller.timekeeping;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import static assets.navigation.FXMLNavigation.*;

import java.io.IOException;

import controller.layouts.LayoutController;
import javafx.scene.layout.Pane;

public class TimekeepingSelectionController {
    private LayoutController layout = new LayoutController();

    @FXML
    private AnchorPane basePane;

    @FXML
    private Pane btnTimekeepingDaily;

    @FXML
    private Pane btnTimekeepingMonthly;
    @FXML
    void switchToTimekeepingMonthly(MouseEvent event) throws IOException {
        layout.changeAnchorPane(basePane, TIMEKEEPING_MONTHLY_VIEW);
    }

    @FXML
    void switchToTimekeepingDaily(MouseEvent event) {

    }

}
