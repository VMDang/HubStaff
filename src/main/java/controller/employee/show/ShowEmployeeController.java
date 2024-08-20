package controller.employee.show;

import config.FXMLNavigation;
import controller.auth.Authentication;
import controller.employee.create.CreateEmployeeController;
import controller.employee.delete.DeleteEmployeeController;
import controller.layouts.LayoutController;
import database.RoleDAO;
import database.UnitDAO;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.employee.Employee;
import model.employee.HRManager;
import model.employee.Role;
import model.employee.Unit;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowEmployeeController implements Initializable {
    private final LayoutController layout = new LayoutController();

    @FXML
    private Label address;

    @FXML
    private ImageView avatar;

    @FXML
    private AnchorPane basePane;

    @FXML
    private Label birthday;

    @FXML
    private Label department;

    @FXML
    private Label employee_id;

    @FXML
    private Label gender;

    @FXML
    private Label identifier;

    @FXML
    private Label location;

    @FXML
    private Label phone;

    @FXML
    private Label role;

    @FXML
    private Label status;

    @FXML
    private Label unit_id;

    @FXML
    private Label unit_name;

    @FXML
    private Label user_name;

    @FXML
    private Button redirectEditBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button backBtn;

    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public ShowEmployeeController() {
        this.employee = Authentication.getInstance().getAuthentication();
    }

    public ShowEmployeeController(Employee employee) {
        this.employee = employee;
    }

    @FXML
    void deleteEmployee(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLNavigation.DELETE_EMPLOYEE_VIEW));
        loader.setControllerFactory(c -> new DeleteEmployeeController(employee));

        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Xóa nhân viên");
        stage.setScene(new Scene(root));

        stage.setOnCloseRequest(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Xoá nhân viên thành công");
            alert.setHeaderText(null);
            alert.setContentText("Đã hoàn thành xoá dữ liệu nhân viên");

            alert.show();

            PauseTransition delay = new PauseTransition(Duration.seconds(5));
            delay.setOnFinished(event1 -> alert.close());
            delay.play();

            try {
                layout.changeAnchorPane(basePane, FXMLNavigation.LIST_EMPLOYEE_VIEW);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        stage.show();
    }

    @FXML
    void redirectEditPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLNavigation.CREATE_EMPLOYEE_VIEW));
            loader.setControllerFactory(c -> new CreateEmployeeController(employee));

            Parent root = loader.load();
            basePane.getChildren().setAll(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void backPrePage(ActionEvent event) throws IOException {
        layout.changeAnchorPane(basePane, FXMLNavigation.LIST_EMPLOYEE_VIEW);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user_name.setText(employee.getName());
        employee_id.setText(employee.getId());
        identifier.setText(employee.getIdentifier());
        birthday.setText(employee.getBirthday().toString());
        gender.setText(employee.getGender());
        phone.setText(employee.getPhone());
        address.setText(employee.getAddress());
        if (employee.getStatus() == 1) {
            status.setText("Đang làm việc");
            status.setStyle("-fx-text-fill: green;");
        } else {
            status.setText("Đã nghỉ");
            status.setStyle("-fx-text-fill: red;");
        }
        department.setText(employee.getDepartment());

        Role roleAuth = RoleDAO.getInstance().getById(String.valueOf(employee.getRole_id()));
        role.setText(roleAuth.getName());

        Unit unit = UnitDAO.getInstance().getById(employee.getUnit_id());
        unit_id.setText(unit.getId());
        unit_name.setText(unit.getName());
        location.setText(unit.getLocation());

        if (Authentication.getInstance().getAuthentication() instanceof HRManager) {
            redirectEditBtn.setVisible(true);
            deleteBtn.setVisible(true);
            backBtn.setVisible(true);
        }

        if (employee.getId().equals(Authentication.getInstance().getAuthentication().getId())) {
            deleteBtn.setVisible(false);
        }
    }
}
