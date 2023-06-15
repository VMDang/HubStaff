package controller.auth.login;

import static controller.database.DBconstains.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.employee.Employee;
import controller.auth.Authentication;
import controller.layouts.LayoutController;
import static controller.fxml.FxmlConstains.*;

public class LoginController {
    @FXML
    private TextField inputUsername, inputPassword;
  
    @FXML
    private Label validate;

    public void handleLogin(ActionEvent event) throws IOException {
        String SELECT_QUERY = "SELECT * FROM employee WHERE id = ? AND password = ?";
        String Id = inputUsername.getText();
        String Password = inputPassword.getText();
        if (Id.trim().equals("") || Password.trim().equals("")) {
            validate.setText("Vui lòng nhập đủ mã nhân viên và mật khẩu");
            validate.setVisible(true);
        }   else {
            try {
                //Khai bao ket noi sql
                Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
                PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
                preparedStatement.setString(1, Id);
                preparedStatement.setString(2, Password);
                ResultSet result = preparedStatement.executeQuery();
                if (result.next()) {
                    Preferences userPreferences = Preferences.userRoot();
                    userPreferences.put("role", result.getString(4));
                    userPreferences.put("id", result.getString(1));
                    Authentication.authentication = new Employee(result.getString(1), result.getString(2), Password, result.getInt(4));
                    LayoutController layout = new LayoutController();
                    layout.changeScene(event, HOME_VIEW);
                                    
                }else {
                    validate.setText("Mã nhân viên hoặc mật khẩu sai! Vui lòng nhập lại");
                    validate.setVisible(true);
                }
            }   catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}

