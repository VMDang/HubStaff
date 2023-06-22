package controller.home;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import controller.auth.Authentication;
import controller.layouts.LayoutController;
import static assets.navigation.FXMLNavigation.*;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.employee.HRManager;
import model.employee.officer.OfficerUnitManager;
import model.employee.worker.WorkerUnitManager;

public class HomeController implements Initializable {
    private final LayoutController layout = new LayoutController();

    @FXML
    private Pane dashboardBtn;

    @FXML
    private Pane timekeepingBtn;

    @FXML
    private Pane profileBtn;

    @FXML
    private Pane importBtn;

    @FXML
    private Pane reportBtn;
    
    @FXML
    private Pane generalInfoBtn;

    @FXML
    private AnchorPane basePane;

    @FXML
    private Label usernameLabel;
  
    @FXML
    private Button drawerImage;
    
    @FXML
    private AnchorPane drawerPane;
    
    private boolean isDrawerOpen = true;

    @FXML
    void switchToDashboard(MouseEvent event) throws IOException {
        highlightSidebar(dashboardBtn);
//        layout.changeAnchorPane(basePane, TIMEKEEPING_SELECTION_VIEW);
    }

    @FXML
    void switchToProfile(MouseEvent event) throws IOException {
        highlightSidebar(profileBtn);
//        layout.changeAnchorPane(basePane, TIMEKEEPING_SELECTION_VIEW);
    }
    @FXML
    void switchToTimekeeping(MouseEvent event) throws IOException {
        highlightSidebar(timekeepingBtn);
    	layout.changeAnchorPane(basePane, TIMEKEEPING_SELECTION_VIEW);
    }

    @FXML
    void switchToReport(MouseEvent event) throws IOException {
        highlightSidebar(reportBtn);
        if (Authentication.authentication instanceof HRManager){
            layout.changeAnchorPane(basePane, HRM_REPORT_SELECTION_VIEW);
        }
        if (Authentication.authentication instanceof WorkerUnitManager){
            layout.changeAnchorPane(basePane, WUM_REPORT_SELECTION_VIEW);
        }

    }

    @FXML
    void switchToImport(MouseEvent event) throws IOException {
        highlightSidebar(importBtn);
        if (Authentication.authentication instanceof  HRManager){
            layout.changeAnchorPane(basePane, IMPORT_SELECTION_VIEW);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (isDrawerOpen) {
            drawerPane.setTranslateX(0);
        } else {
            drawerPane.setTranslateX(-240);
        }
		    drawerImage.setOnMouseClicked(event -> toggleDrawer());      
      
        if(!(Authentication.authentication instanceof  HRManager)) {
            importBtn.setVisible(false);
        }

        if (!(Authentication.authentication instanceof HRManager || Authentication.authentication instanceof WorkerUnitManager || Authentication.authentication instanceof OfficerUnitManager)){
            reportBtn.setVisible(false);
        }
        usernameLabel.setText(Authentication.authentication.getName());
    }

    public void highlightSidebar(Pane btn) {
        dashboardBtn.setStyle("-fx-background-color: #0A4969");
        profileBtn.setStyle("-fx-background-color: #0A4969");
        timekeepingBtn.setStyle("-fx-background-color: #0A4969");
        reportBtn.setStyle("-fx-background-color: #0A4969");
        importBtn.setStyle("-fx-background-color: #0A4969");
        btn.setStyle("-fx-background-color: #054df6");
    }
  
    private void toggleDrawer() {
        TranslateTransition transition = new TranslateTransition(Duration.millis(200), drawerPane);
        if (isDrawerOpen) {
            transition.setToX(-240);
        } else {
            transition.setToX(0);
        }
        transition.play();

        isDrawerOpen = !isDrawerOpen;
    }
    
}
