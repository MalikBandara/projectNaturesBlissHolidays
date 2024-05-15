package lk.ijse.controller.Controller;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.controller.model.Employee;
import lk.ijse.controller.model.Room;
import lk.ijse.controller.model.tm.EmployeeTm;
import lk.ijse.repository.EmployeeRepo;
import lk.ijse.repository.RoomRepo;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFields;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EmployeeFormController {


    

    @FXML
    private Button clear;

    @FXML
    private ComboBox<String> cmbRoom;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colAvailbility;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private Button btnBack;

    @FXML
    private Button emclear;

    @FXML
    private Button empback;

    @FXML
    private Button empdelete;

    @FXML
    private Button empsave;

    @FXML
    private Button empupdate;


    @FXML
    private TableColumn<?, ?> colname;

    @FXML
    private TableColumn<?, ?> colsalary;

    @FXML
    private TableColumn<?, ?> coltype;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtType;

    @FXML
    private TextField txtavaia;

    @FXML
    private TextField txtempid;

    @FXML
    private TextField txtsalary;

    public void initialize(){
        setCellValueFactory();
        loadAllEmployees();
        getRoomId();



    }
    private void loadAllEmployees() {
        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        try {
            List<Employee> employeeList = EmployeeRepo.getAllEmployees();
            for(Employee employee : employeeList){
                EmployeeTm empTm = new EmployeeTm(
                        employee.getEmployeeId(),
                        employee.getName(),
                        employee.getAddress(),
                        employee.getSalary(),
                        employee.getType(),
                        employee.getAvailability(),
                        employee.getRoomId()
                );
                obList.add(empTm);
            }
            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void setCellValueFactory() {
        colid.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colsalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        coltype.setCellValueFactory(new PropertyValueFactory<>("type"));
        colAvailbility.setCellValueFactory(new PropertyValueFactory<>("availability"));



    }

    @FXML
    void btnBackOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashBord.fxml"));
            Parent Loginpanel = loader.load();

            Stage stage = (Stage) empback.getScene().getWindow();


            // Animate the exit of the welcome page
            Timeline exitTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(1),
                            new KeyValue(empback.translateXProperty(), -empback.getWidth(), Interpolator.EASE_BOTH))
            );
            exitTimeline.setOnFinished(e -> {
                // Animate the entrance of the service page
                double sceneWidth = stage.getScene().getWidth(); // or any other suitable width
                Loginpanel.translateXProperty().set(sceneWidth);
                stage.getScene().setRoot(Loginpanel);
                Timeline entranceTimeline = new Timeline(
                        new KeyFrame(Duration.seconds(1),
                                new KeyValue(Loginpanel.translateXProperty(), 0, Interpolator.EASE_BOTH))
                );
                entranceTimeline.play();
            });
            exitTimeline.play();
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        if (isValied()){
            try {
                String employeeId = txtempid.getText();
                boolean isDeleted = EmployeeRepo.deleteEmployee(employeeId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee deleted successfully!").show();
                    loadAllEmployees();
                    // Optionally, clear the form fields or update UI after deletion
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Check your fields correctly !").show();
        }



    }








    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String employeeId = txtempid.getText();
        String employeeName = txtName.getText();
        String employeeAddress = txtAddress.getText();
        String employeeSalaryText = txtsalary.getText();
        String employeeType = txtType.getText();
        String employeeAvailability = txtavaia.getText();
        String rooID = String.valueOf(cmbRoom.getValue());

        if (isValied()){

            if (employeeId.isEmpty() || employeeName.isEmpty() || employeeAddress.isEmpty() ||
                    employeeSalaryText.isEmpty() || employeeType.isEmpty() || employeeAvailability.isEmpty() || rooID.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please fill all fields").show();
                return;
            }


            Double employeeSalary = Double.parseDouble(employeeSalaryText);


            Employee employee = new Employee(employeeId, employeeName, employeeAddress, employeeSalary, employeeType, employeeAvailability, rooID);

            try {
                boolean isSaved = EmployeeRepo.saveEmployee(employee);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved successfully!").show();
                    loadAllEmployees();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

        }else {
            new Alert(Alert.AlertType.ERROR, "Check your fields correctly !").show();
        }


    }


    private void clearFields() {
        txtempid.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtsalary.setText("");
        txtType.setText("");
        txtavaia.setText("");
        cmbRoom.setValue(null);


    }




    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String employeeId = txtempid.getText();
        String employeeName = txtName.getText();
        String employeeAddress = txtAddress.getText();
        String employeeSalaryText = txtsalary.getText();
        String employeeType = txtType.getText();
        String employeeAvailability = txtavaia.getText();
        String rooID = String.valueOf(cmbRoom.getValue());

        if (isValied()){
            if (employeeId.isEmpty() || employeeName.isEmpty() || employeeAddress.isEmpty() ||
                    employeeSalaryText.isEmpty() || employeeType.isEmpty() || employeeAvailability.isEmpty() || rooID.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please fill all fields").show();
                return; // Exit the method if any field is empty
            }

            // Parse the salary to double
            Double employeeSalary = Double.parseDouble(employeeSalaryText);

            // Create the employee object
            Employee employee = new Employee(employeeId, employeeName, employeeAddress, employeeSalary, employeeType, employeeAvailability, rooID);

            try {
                boolean isUpdated = EmployeeRepo.updateEmployee(employee);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee updated successfully!").show();
                    loadAllEmployees();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Check your fields correctly !").show();
        }


    }



    @FXML
    void txtSearchOnAction(ActionEvent event) {

        try {
            String employeeId = txtempid.getText();

            Employee employee = EmployeeRepo.searchEmployeeById(employeeId);
            if (employee != null) {
                txtempid.setText(employee.getEmployeeId());
                txtName.setText(employee.getName());
                txtAddress.setText(employee.getAddress());
                txtsalary.setText(String.valueOf(employee.getSalary()));
                txtType.setText(employee.getType());
                txtavaia.setText(employee.getAvailability());
                // Update additional fields as needed
            } else {
                new Alert(Alert.AlertType.ERROR, "Employee is not found!").show();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle or log the exception as needed
            new Alert(Alert.AlertType.ERROR, "An error occurred while searching for the employee.").show();
        }


    }


    public void cmbRoomOnAction(ActionEvent actionEvent) {
        String Roomid = cmbRoom.getValue();

        try {
            Room room = RoomRepo.searchRoomById(Roomid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getRoomId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> rIDList = RoomRepo.getIds();

            for (String materialId : rIDList) {
                obList.add(materialId);
            }
            cmbRoom.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void ENameOnActionKey(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.NAME,txtName);

    }

    public void EmpAdressOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.ADDRESS,txtAddress);

    }

    public void EmpSalaryOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.DOUBLE,txtsalary);

    }

    public void EmpTypeOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.EmpType,txtType);

    }

    public void AvailaEmOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.EmpAva,txtavaia);


    }

    public boolean isValied(){
        if (!Regex.setTextColor(TextFields.NAME,txtName)) return false;
        if (!Regex.setTextColor(TextFields.ADDRESS,txtAddress)) return false;
        if (!Regex.setTextColor(TextFields.DOUBLE,txtsalary)) return false;
        if (!Regex.setTextColor(TextFields.EmpType,txtType)) return false;
        if (!Regex.setTextColor(TextFields.EmpAva,txtavaia)) return false;

        return true;
    }

    public void EmpOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.EmployeeID,txtempid);
    }
}
