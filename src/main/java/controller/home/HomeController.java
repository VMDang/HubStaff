package controller.home;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import controller.auth.Authentication;
import controller.layouts.LayoutController;
import static controller.fxml.FxmlConstains.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class HomeController implements Initializable {
    @FXML
    private AnchorPane basePane;
    
    @FXML
    private Label dashboardBtn;

    @FXML
    private Label home;

    @FXML
    private Pane importBtn;

    @FXML
    private Label profileBtn;

    @FXML
    private Label reportBtn;

    @FXML
    private Label timeKeepingBtn;

    @FXML
    private Label usernameLabel;
    
    private final LayoutController layout = new LayoutController();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(Authentication.authentication.getRole_id() != 1) {
			importBtn.setVisible(false);
		}
		usernameLabel.setText(Authentication.authentication.getName());
	}
    @FXML
    void switchToTimekeeping(MouseEvent event) throws IOException {
    	layout.changeAnchorPane(basePane, TIMEKEEPING_VIEW);
    }
    
}
