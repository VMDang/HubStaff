package controller.home;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import controller.auth.Authentication;
import controller.layouts.LayoutController;
import static assets.navigation.FXMLNavigation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.employee.HRManager;
import model.employee.officer.OfficerUnitManager;
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
        highlightSidebar(dashboardBtn);
        setTextTitle("Home page");
        layout.changeAnchorPane(basePane, DASHBOARD_VIEW);
    }

    @FXML
    void switchToProfile(MouseEvent event) throws IOException {
        highlightSidebar(profileBtn);
        setTextTitle("Thông tin cá nhân");
//        layout.changeAnchorPane(basePane, TIMEKEEPING_SELECTION_VIEW);
    }
    @FXML
    void switchToTimekeeping(MouseEvent event) throws IOException {
        highlightSidebar(timekeepingBtn);
        setTextTitle("Chấm công");
    	layout.changeAnchorPane(basePane, TIMEKEEPING_SELECTION_VIEW);
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
        setTextTitle("Import");
        if (Authentication.getInstance().getAuthentication() instanceof  HRManager){
            layout.changeAnchorPane(basePane, IMPORT_SELECTION_VIEW);
        }
    }
    
    @FXML
    void logout(MouseEvent event) throws IOException {
    	Authentication.getInstance().destroyAuthencation();
    	Stage currentStage = (Stage) basePane.getScene().getWindow();
    	currentStage.close();
    	
    	Stage newStage = new Stage();
        
        // Tải file FXML của màn hình mới
        Parent root = FXMLLoader.load(getClass().getResource(LOGIN_VIEW));
        
        // Tạo một Scene với nội dung của màn hình mới
        Scene scene = new Scene(root);
        
        // Đặt vị trí của màn hình mới là chính giữa màn hình máy tính
        double newX = 450 ;
        double newY = 200;
        newStage.setX(newX);
        newStage.setY(newY);
        
        // Đặt Scene cho màn hình mới và hiển thị nó
        newStage.setScene(scene);
        newStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	try {
			switchToDashboard(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
        if(!(Authentication.getInstance().getAuthentication() instanceof  HRManager)) {
            importBtn.setVisible(false);
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
        btn.setStyle("-fx-background-color: #054df6");
    }
    
    public void setTextTitle(String t) {
    	title.setText(t);
    }
    
    
}
