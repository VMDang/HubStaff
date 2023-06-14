package controller.home;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class HomeController implements Initializable{
    @FXML
    private Label dashboardBtn;

    @FXML
    private Label home;

    @FXML
    private Label importBtn;

    @FXML
    private Label profileBtn;

    @FXML
    private Label reportBtn;

    @FXML
    private Label timeKeepingBtn;

    @FXML
    private Label usernameLabel;
    
    @FXML
    private Button drawerImage;
    
    @FXML
    private AnchorPane drawerPane;
    
    private boolean isDrawerOpen = true;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		if (isDrawerOpen) {
            drawerPane.setTranslateX(0);
        } else {
            drawerPane.setTranslateX(-240);
        }
		
		drawerImage.setOnMouseClicked(event -> toggleDrawer());
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
