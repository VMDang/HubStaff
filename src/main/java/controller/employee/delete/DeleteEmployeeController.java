package controller.employee.delete;

import database.EmployeeDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.employee.Employee;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DeleteEmployeeController implements Initializable {
    private Employee employee;

    @FXML
    private Label birthday;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Label employee_id;

    @FXML
    private Label user_name;

    public DeleteEmployeeController(Employee employee) {
        this.employee = employee;
    }

    @FXML
    void cancelDelete(ActionEvent event) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void deleteEmployee(ActionEvent event) throws IOException {
        EmployeeDAO.getInstance().delete(employee);
        Stage stage = (Stage) deleteBtn.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        employee_id.setText(employee.getId());
        user_name.setText(employee.getName());
        birthday.setText(employee.getBirthday().toString());
    }
}
