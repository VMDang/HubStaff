package controller.auth.login;

import java.io.IOException;

import database.EmployeeDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.employee.Employee;
import controller.auth.Authentication;

import static config.FXMLNavigation.*;

public class LoginController {
    @FXML
    private TextField inputUsername, inputPassword;
  
    @FXML
    private Label validate;

    public void handleLogin(ActionEvent event) throws IOException {
        String Id = inputUsername.getText();
        String Password = inputPassword.getText();
        if (Id.trim().equals("") || Password.trim().equals("")) {
            validate.setText("Vui lòng nhập đủ mã nhân viên và mật khẩu");
            validate.setVisible(true);
        }   else {
            Employee employee = EmployeeDAO.getInstance().getById(Id);
            if(employee.getId() != null ) {
            	if (employee.getPassword().equals(Password) && employee.getStatus()==1){
                    Authentication.getInstance().setAuthentication(employee);
//                    LayoutController layout = new LayoutController();
//                    layout.changeScene(event, HOME_VIEW);
                    showNewScreen();
                    Stage currentStage = (Stage) inputUsername.getScene().getWindow();
                    currentStage.close();

            	}else {
            		validate.setText("Mật khẩu sai! Vui lòng nhập lại");
                    validate.setVisible(true);
            	}
            }else {
                validate.setText("Mã nhân viên sai! Vui lòng nhập lại");
                validate.setVisible(true);
            }
        }
    }
    private void showNewScreen() throws IOException {
        Stage newStage = new Stage();
        newStage.setTitle("HubStaff");
        
        Parent root = FXMLLoader.load(getClass().getResource(HOME_VIEW));
        
        Scene scene = new Scene(root);
        
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double newX = (screenBounds.getWidth() - newStage.getWidth()) / 2;
        double newY = 50;
        newStage.setX(newX);
        newStage.setY(newY);
        
        newStage.setScene(scene);
        newStage.show();
    }

}

