package controller.employee.list;

import config.FXMLNavigation;
import controller.employee.show.ShowEmployeeController;
import controller.layouts.LayoutController;
import database.EmployeeDAO;
import database.RoleDAO;
import database.UnitDAO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import model.employee.Employee;
import model.employee.Role;
import model.employee.Unit;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ListEmployeeController implements Initializable {
    private ObservableList<EmployeeRow> listEmployee = FXCollections.observableArrayList();
    private LayoutController layout = new LayoutController();

    @FXML
    private Button addEmployeeBtn;

    @FXML
    private AnchorPane basePane;

    @FXML
    private Button btnReloadPage;

    @FXML
    private Button btnSearch;

    @FXML
    private TextField searchInput;

    @FXML
    private TableView<EmployeeRow> listEmployeeTable;

    @FXML
    private TableColumn<EmployeeRow, String> department;

    @FXML
    private TableColumn<EmployeeRow, String> employee_id;

    @FXML
    private TableColumn<EmployeeRow, Integer> index;

    @FXML
    private TableColumn<EmployeeRow, String> name;

    @FXML
    private TableColumn<EmployeeRow, String> role;

    @FXML
    private TableColumn<EmployeeRow, String> unit;

    @FXML
    private TableColumn<EmployeeRow, String> status;

    @FXML
    private TableColumn<EmployeeRow, String> birthday;

    @FXML
    private ChoiceBox<String> unitNameBox;

    @FXML
    void addEmployee(ActionEvent event) throws IOException {
        layout.changeAnchorPane(basePane, FXMLNavigation.CREATE_EMPLOYEE_VIEW);
    }

    @FXML
    void reloadPage(ActionEvent event) {
        setListEmployee();
        searchInput.clear();
        unitNameBox.setValue("Tất cả");
        listEmployeeTable.setItems(listEmployee);
    }

    @FXML
    void search(ActionEvent event) {
        FilteredList<EmployeeRow> filteredData = listEmployee.filtered(p -> true);
        String finalSearchInput = searchInput.getText().toLowerCase();
        String finalUnit_id = unitNameBox.getValue().toLowerCase();

        filteredData.setPredicate(row -> {
            if (finalUnit_id.equals("tất cả")) {
                return row.getEmployee_id().toLowerCase().contains(finalSearchInput) ||
                        row.getName().toLowerCase().contains(finalSearchInput) ||
                        row.getBirthday().toLowerCase().contains(finalSearchInput) ||
                        row.getDepartment().toLowerCase().contains(finalSearchInput) ||
                        row.getUnit().toLowerCase().contains(finalSearchInput) ||
                        row.getStatus().toLowerCase().contains(finalSearchInput) ||
                        row.getRole().toLowerCase().contains(finalSearchInput);
            } else {
                return row.getUnit().toLowerCase().contains(finalUnit_id) &&
                        (row.getEmployee_id().toLowerCase().contains(finalSearchInput) ||
                                row.getName().toLowerCase().contains(finalSearchInput) ||
                                row.getBirthday().toLowerCase().contains(finalSearchInput) ||
                                row.getDepartment().toLowerCase().contains(finalSearchInput) ||
                                row.getStatus().toLowerCase().contains(finalSearchInput) ||
                                row.getRole().toLowerCase().contains(finalSearchInput));
            }
        });

        listEmployeeTable.setItems(filteredData);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> listUnit = new ArrayList<>();
        ArrayList<Unit> units = UnitDAO.getInstance().getAll();
        for (Unit unit : units) {
            listUnit.add(unit.getId());
        }

        unitNameBox.getItems().add("Tất cả");
        unitNameBox.getItems().addAll(listUnit);
        unitNameBox.setValue("Tất cả");

        setListEmployee();
        index.setCellValueFactory(index -> new ReadOnlyObjectWrapper<Integer>(listEmployeeTable.getItems().indexOf(index.getValue())+1));
        index.setSortable(false);
        employee_id.setCellValueFactory(new PropertyValueFactory<EmployeeRow, String>("employee_id"));
        name.setCellValueFactory(new PropertyValueFactory<EmployeeRow, String>("name"));
        birthday.setCellValueFactory(new PropertyValueFactory<EmployeeRow, String>("birthday"));
        department.setCellValueFactory(new PropertyValueFactory<EmployeeRow, String>("department"));
        unit.setCellValueFactory(new PropertyValueFactory<EmployeeRow, String>("unit"));
        status.setCellValueFactory(new PropertyValueFactory<EmployeeRow, String>("status"));
        role.setCellValueFactory(new PropertyValueFactory<EmployeeRow, String>("role"));

        highlightRow();
        listEmployeeTable.setItems(listEmployee);

        listEmployeeTable.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                EmployeeRow employeeRow = listEmployeeTable.getSelectionModel().getSelectedItem();

                if (employeeRow != null) {
                    try {
                        Employee employee = EmployeeDAO.getInstance().getById(employeeRow.getEmployee_id());

                        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLNavigation.SHOW_EMPLOYEE_VIEW));
                        loader.setControllerFactory(c -> new ShowEmployeeController(employee));
                        Node node = loader.load();
                        basePane.getChildren().setAll(node);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private void setListEmployee()
    {
        listEmployee.clear();
        ArrayList<Employee> employees = EmployeeDAO.getInstance().getAll();
        for (Employee e : employees) {
            Role role = RoleDAO.getInstance().getById(String.valueOf(e.getRole_id()));
            Unit unit = UnitDAO.getInstance().getById(e.getUnit_id());

            String textStatus = "";
            if (e.getStatus() == 1) {
                textStatus = "Đang làm việc";
            } else {
                textStatus = "Đã nghỉ";
            }
            listEmployee.add(new EmployeeRow(e.getId(), e.getName(), String.valueOf(e.getBirthday()), e.getDepartment(), e.getUnit_id(), textStatus, role.getName()));
        }
    }

    private void highlightRow(){
        listEmployeeTable.setRowFactory(tv -> new TableRow<EmployeeRow>() {
            @Override
            protected void updateItem(EmployeeRow item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty && item != null) {
                    if (item.getStatus().equals("Đã nghỉ")) {
                        setStyle("-fx-background-color: #f8bcbc;");
                    } else setStyle("");
                }else setStyle("");
            }
        });
    }

}
