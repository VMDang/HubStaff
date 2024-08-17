package controller.home;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import controller.auth.Authentication;
import controller.layouts.LayoutController;
import static config.FXMLNavigation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.employee.HRManager;
import model.employee.officer.OfficerUnitManager;
import model.employee.worker.Worker;
import model.employee.worker.WorkerUnitManager;

public class HomeController implements Initializable {
    private final LayoutController layout = new LayoutController();
    
    @FXML
    private Label title;

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
    private Pane employeeManageBtn;
    
    @FXML
    private Pane generalInfoBtn;

    @FXML
    private AnchorPane basePane;

    @FXML
    private Label usernameLabel;
  
    @FXML
    private Button drawerImage;
    
    @FXML
    private ImageView logoutBtn;
    
    @FXML
    private AnchorPane drawerPane;
    

    @FXML
    void switchToDashboard(MouseEvent event) throws IOException {
//        highlightSidebar(dashboardBtn);
//        setTextTitle("Home page");
//        layout.changeAnchorPane(basePane, DASHBOARD_VIEW);
    }

    @FXML
    void switchToProfile(MouseEvent event) throws IOException {
        highlightSidebar(profileBtn);
        setTextTitle("Thông tin cá nhân");
        layout.changeAnchorPane(basePane, SHOW_EMPLOYEE_VIEW);
    }
    @FXML
    void switchToTimekeeping(MouseEvent event) throws IOException {
        highlightSidebar(timekeepingBtn);
        setTextTitle("Chấm công cá nhân");
        if (Authentication.getInstance().getAuthentication() instanceof Worker ||
                Authentication.getInstance().getAuthentication() instanceof WorkerUnitManager) {
            layout.changeAnchorPane(basePane, TIMEKEEPING_MONTHLY_WORKER_VIEW);
        } else {
            layout.changeAnchorPane(basePane, TIMEKEEPING_MONTHLY_OFFICER_VIEW);
        }
    }

    @FXML
    void switchToReport(MouseEvent event) throws IOException {
        highlightSidebar(reportBtn);
        setTextTitle("Báo cáo");
        if (Authentication.getInstance().getAuthentication() instanceof HRManager){
            layout.changeAnchorPane(basePane, HRM_REPORT_SELECTION_VIEW);
        }
        if (Authentication.getInstance().getAuthentication() instanceof WorkerUnitManager){
            layout.changeAnchorPane(basePane, WUM_REPORT_SELECTION_VIEW);
        }

    }

    @FXML
    void switchToImport(MouseEvent event) throws IOException {
        highlightSidebar(importBtn);
        setTextTitle("Nhập dữ liệu chấm công");
        if (Authentication.getInstance().getAuthentication() instanceof  HRManager){
            layout.changeAnchorPane(basePane, IMPORT_SELECTION_VIEW);
        }
    }

    @FXML
    void switchToEmployeeManage(MouseEvent event) throws IOException {
        highlightSidebar(employeeManageBtn);
        setTextTitle("Quản lý nhân viên");
        if (Authentication.getInstance().getAuthentication() instanceof HRManager){
            layout.changeAnchorPane(basePane, LIST_EMPLOYEE_VIEW);
        }
    }
    
    @FXML
    void logout(MouseEvent event) throws IOException {
    	Authentication.getInstance().destroyAuthencation();
    	Stage currentStage = (Stage) basePane.getScene().getWindow();
    	currentStage.close();
    	
    	Stage newStage = new Stage();
        newStage.setTitle("HubStaff Login");
        
        Parent root = FXMLLoader.load(getClass().getResource(LOGIN_VIEW));
        
        Scene scene = new Scene(root);
        
        double newX = 450 ;
        double newY = 200;
        newStage.setX(newX);
        newStage.setY(newY);
        
        newStage.setScene(scene);
        newStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	try {
			switchToProfile(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
      
        if(!(Authentication.getInstance().getAuthentication() instanceof  HRManager)) {
            importBtn.setVisible(false);
            employeeManageBtn.setVisible(false);
        }

        if (!(Authentication.getInstance().getAuthentication() instanceof HRManager ||
                Authentication.getInstance().getAuthentication() instanceof WorkerUnitManager ||
                Authentication.getInstance().getAuthentication() instanceof OfficerUnitManager)){
            reportBtn.setVisible(false);
        }
        usernameLabel.setText(Authentication.getInstance().getAuthentication().getName());
        
    }

    public void highlightSidebar(Pane btn) {
        dashboardBtn.setStyle("-fx-background-color: #0A4969");
        profileBtn.setStyle("-fx-background-color: #0A4969");
        timekeepingBtn.setStyle("-fx-background-color: #0A4969");
        reportBtn.setStyle("-fx-background-color: #0A4969");
        importBtn.setStyle("-fx-background-color: #0A4969");
        employeeManageBtn.setStyle("-fx-background-color: #0A4969");
        btn.setStyle("-fx-background-color: #054df6");
    }
    
    public void setTextTitle(String t) {
    	title.setText(t);
    }
}
