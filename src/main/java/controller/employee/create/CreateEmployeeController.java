package controller.employee.create;

import config.FXMLNavigation;
import controller.employee.show.ShowEmployeeController;
import controller.layouts.LayoutController;
import database.EmployeeDAO;
import database.RoleDAO;
import database.UnitDAO;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.employee.Employee;
import model.employee.Role;
import model.employee.Unit;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateEmployeeController implements Initializable {
    private final LayoutController layout = new LayoutController();
    private Employee employee = null;

    @FXML
    private TextField address;

    @FXML
    private ImageView avatar;

    @FXML
    private Button backBtn;

    @FXML
    private AnchorPane basePane;

    @FXML
    private DatePicker birthday;

    @FXML
    private TextField department;

    @FXML
    private ChoiceBox<String> gender;

    @FXML
    private TextField identifier;

    @FXML
    private TextField location;

    @FXML
    private PasswordField password;

    @FXML
    private TextField phone;

    @FXML
    private ChoiceBox<String> role;

    @FXML
    private Button saveBtn;

    @FXML
    private ToggleButton status;

    @FXML
    private ChoiceBox<String> unit_id;

    @FXML
    private TextField unit_name;

    @FXML
    private TextField user_name;

    public CreateEmployeeController() {
    }

    public CreateEmployeeController(Employee employee) {
        this.employee = employee;
    }

    @FXML
    private void statusToggleAction(ActionEvent event) {
        if (!status.isSelected()) {
            status.setText("Bình thường");
            status.setStyle("-fx-background-color: green; -fx-text-fill: white");
        } else {
            status.setText("Khoá tài khoản");
            status.setStyle("-fx-background-color: red; -fx-text-fill: white");
        }
    }

    @FXML
    void backPrePage(ActionEvent event) throws IOException {
        if (employee != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLNavigation.SHOW_EMPLOYEE_VIEW));
            loader.setControllerFactory(c -> new ShowEmployeeController(employee));
            Node node = loader.load();
            basePane.getChildren().setAll(node);
        } else {
            layout.changeAnchorPane(basePane, FXMLNavigation.LIST_EMPLOYEE_VIEW);
        }
    }

    @FXML
    void save(ActionEvent event) throws IOException {
        if (validateInput()) {
            ArrayList<Role> roles = RoleDAO.getInstance().getAll();

            Employee e = new Employee();
            e.setName(user_name.getText());
            e.setGender(gender.getValue().toString());
            e.setBirthday(Date.valueOf(birthday.getValue().toString()));
            e.setPassword(password.getText());
            e.setIdentifier(identifier.getText());
            e.setPhone(phone.getText());
            e.setAddress(address.getText());
            e.setDepartment(department.getText());

            for (Role r: roles) {
                if (r.getName().equals(role.getValue())) {
                    e.setRole_id(r.getId());
                    break;
                }
            }

            e.setUnit_id(unit_id.getValue());
            if (status.getText().equals("Bình thường")) {
                e.setStatus(1);
            } else {
                e.setStatus(0);
            }

            if (employee == null) {
                ArrayList<Employee> employees = EmployeeDAO.getInstance().getAll();
                e.setId("nv" + (employees.size() + 1));
                create(e);
            } else {
                e.setId(employee.getId());
                update(e);
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Vui lòng nhập tất cả các trường thông tin.", ButtonType.OK);
            alert.setTitle("Lỗi nhập liệu");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> optionsGender = FXCollections.observableArrayList("Nam", "Nữ", "Khác");
        gender.setItems(optionsGender);

        ArrayList<Role> roles = RoleDAO.getInstance().getAll();
        ArrayList<String> optionsRole = new ArrayList<>();
        for (Role role : roles) {
            optionsRole.add(role.getName());
        }
        ObservableList<String> optionsRoleChoice = FXCollections.observableArrayList(optionsRole);
        role.setItems(optionsRoleChoice);

        ArrayList<Unit> units = UnitDAO.getInstance().getAll();
        ArrayList<String> optionsUnit = new ArrayList<>();
        for (Unit unit : units) {
            optionsUnit.add(unit.getId());
        }
        ObservableList<String> optionsUnitChoice = FXCollections.observableArrayList(optionsUnit);
        unit_id.setItems(optionsUnitChoice);
        unit_id.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Unit unit = UnitDAO.getInstance().getById(newValue);
            unit_name.setText(unit.getName());
            location.setText(unit.getLocation());
        });

        if (employee != null) {
            gender.setValue(employee.getGender());
            birthday.setValue(employee.getBirthday().toLocalDate());
            user_name.setText(employee.getName());
            password.setText(employee.getPassword());
            identifier.setText(employee.getIdentifier());
            phone.setText(employee.getPhone());
            address.setText(employee.getAddress());
            department.setText(employee.getDepartment());
            role.setValue(RoleDAO.getInstance().getById(String.valueOf(employee.getRole_id())).getName());
            unit_id.setValue(employee.getUnit_id());
            if (employee.getStatus() == 1) {
                status.setText("Bình thường");
                status.setStyle("-fx-background-color: green; -fx-text-fill: white");
            } else {
                status.setText("Khoá tài khoản");
                status.setStyle("-fx-background-color: red; -fx-text-fill: white");
            }

            Unit unit = UnitDAO.getInstance().getById(employee.getUnit_id());
            unit_name.setText(unit.getName());
            location.setText(unit.getLocation());
        }

    }

    private boolean validateInput() {
        String userNameText = user_name.getText();
        String genderText = gender.getValue() != null ? gender.getValue() : "";
        String birthdayText = birthday.getValue() != null ? birthday.getValue().toString() : "";
        String passwordText = password.getText();
        String identifierText = identifier.getText();
        String phoneText = phone.getText();
        String addressText = address.getText();
        String departmentText = department.getText();
        String roleText = role.getValue() != null ? role.getValue() : "";
        String unitIdText = unit_id.getValue() != null ? unit_id.getValue() : "";

        if (userNameText.isEmpty() || genderText.isEmpty() || birthdayText.isEmpty()
                || passwordText.isEmpty() || identifierText.isEmpty() || phoneText.isEmpty()
                || addressText.isEmpty() || departmentText.isEmpty() || roleText.isEmpty() || unitIdText.isEmpty()) {
            return false;
        }else {
            return true;
        }
    }

    private void create(Employee e) throws IOException {
        EmployeeDAO.getInstance().insert(e);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tạo nhân viên thành công");
        alert.setHeaderText(null);
        alert.setContentText("Đã hoàn thành lưu dữ liệu nhân viên");
        alert.show();

        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(event1 -> alert.close());
        delay.play();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLNavigation.SHOW_EMPLOYEE_VIEW));
        loader.setControllerFactory(c -> new ShowEmployeeController(e));
        Node node = loader.load();
        basePane.getChildren().setAll(node);
    }

    private void update(Employee e) throws IOException {
        EmployeeDAO.getInstance().update(e);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cập nhật thông tin thành công");
        alert.setHeaderText(null);
        alert.setContentText("Đã hoàn thành lưu dữ liệu nhân viên");
        alert.show();

        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(event1 -> alert.close());
        delay.play();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLNavigation.SHOW_EMPLOYEE_VIEW));
        loader.setControllerFactory(c -> new ShowEmployeeController(e));
        Node node = loader.load();
        basePane.getChildren().setAll(node);
    }
}
