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
import javafx.scene.control.TextField;

import controller.layouts.LayoutController;
import static controller.fxml.FxmlConstains.*;

public class LoginController {
    @FXML
    private TextField inputUsername, inputPassword;
    
    public void handleLogin(ActionEvent event) throws IOException {
        String SELECT_QUERY = "SELECT * FROM employee WHERE name = ? AND password = ?";
        String Username = inputUsername.getText();
        String Password = inputPassword.getText();
        if (Username.trim().equals("") || Password.trim().equals("")) {
//            createDialog(
//                    Alert.AlertType.WARNING,
//                    "Cảnh báo!",
//                    "Khoan nào cán bộ!",
//                    "Vui lòng nhập đầy đủ username và password!"
//            );
        }   else {
            try {
                //Khai bao ket noi sql
                Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
                PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
                preparedStatement.setString(1, Username);
                preparedStatement.setString(2, Password);
                ResultSet result = preparedStatement.executeQuery();
                if (result.next()) {
                    Preferences userPreferences = Preferences.userRoot();
                    userPreferences.put("role", result.getString(4));
                    userPreferences.put("username", result.getString(2));
                    LayoutController layout = new LayoutController();
                    layout.changeScene(event, HOME_VIEW);
                }   else {
//                    createDialog(
//                            Alert.AlertType.ERROR,
//                            "Cảnh báo!",
//                            "Khoan nào cán bộ!",
//                            "Sai username hoặc password!"
//                    );
                }
            }   catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}