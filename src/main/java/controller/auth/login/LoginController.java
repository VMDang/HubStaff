package controller.auth.login;

import java.io.IOException;

import hrsystem.GetAEmployee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.employee.Employee;
import controller.auth.Authentication;
import controller.layouts.LayoutController;

import static assets.navigation.FXMLNavigation.*;

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

            Employee employee = GetAEmployee.getInstance().getAEmployee(Id);

            if (employee.getPassword().equals(Password)){
                    Authentication.setAuthentication(employee);
                    LayoutController layout = new LayoutController();
                    layout.changeScene(event, HOME_VIEW);

            }else {
                validate.setText("Mã nhân viên hoặc mật khẩu sai! Vui lòng nhập lại");
                validate.setVisible(true);
            }
        }
    }

}

