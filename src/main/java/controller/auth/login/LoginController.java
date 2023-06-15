package controller.auth.login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import dao.EmployeeDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.employee.Employee;
import controller.auth.Authentication;
import controller.layouts.LayoutController;
import model.employee.officer.Officer;
import model.employee.worker.Worker;

import static controller.fxml.FxmlConstains.*;

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
            Employee employee = EmployeeDAO.getInstance().selectById(Id);
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

