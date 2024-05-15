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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.controller.model.Client;
import lk.ijse.controller.model.tm.ClientTm;
import lk.ijse.repository.ClientRepo;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFields;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ClientFormController {

    @FXML
    private Button ClearBtn;

    @FXML
    private Label CusLable;

    @FXML
    private AnchorPane CustomerForm;

    @FXML
    private Button DeleteBtn;

    @FXML
    private Button UpdateBtn;

    @FXML
    private TextField address;

    @FXML
    private Button backButton;

    @FXML
    private TextField cheackin;

    @FXML
    private TextField cheackout;

    @FXML
    private TableColumn<?, ?> coladdress;

    @FXML
    private TableColumn<?, ?> colcheackin;

    @FXML
    private TableColumn<?, ?> colcheackout;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableColumn<?, ?> colmail;

    @FXML
    private TableColumn<?, ?> colname;

    @FXML
    private TableColumn<?, ?> colphone;

    @FXML
    private TextField email;

    @FXML
    private TextField id;

    @FXML
    private TextField name;

    @FXML
    private TextField phone;

    @FXML
    private Button saveButton;

    @FXML
    private TableView<ClientTm> tblclient;

    public void initialize(){
        setCellValueFactory();
        loadAllClient();
        cheackin.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    private void loadAllClient() {
        ObservableList<ClientTm> obList = FXCollections.observableArrayList();

        try {
            List<Client> clientList = ClientRepo.getAllClients();
            for (Client client : clientList) {
                ClientTm clientTm = new ClientTm(
                        client.getId(),
                        client.getName(),
                        client.getEmail(),
                        client.getPhone(),
                        client.getAddress(),
                        client.getCheckIn(),
                        client.getCheckOut()
                );
                obList.add(clientTm);
            }
            tblclient.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colphone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        coladdress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colcheackin.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
        colcheackout.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
    }


    @FXML
    void btnBackOnAction(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashBord.fxml"));
            Parent Loginpanel = loader.load();

            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setTitle("Login");

            // Animate the exit of the welcome page
            Timeline exitTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(1),
                            new KeyValue(backButton.translateXProperty(), -backButton.getWidth(), Interpolator.EASE_BOTH))
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
    void clearFields() {
        id.setText("");
        name.setText("");
        email.setText("");
        phone.setText("");
        address.setText("");
        cheackin.setText("");
        cheackout.setText("");
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String clientId = id.getText(); // Assuming the ID field corresponds to the client's ID

        if (isValied()){

            try {
                boolean isDeleted = ClientRepo.deleteClient(clientId);
                if(isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Client deleted!").show();
                    loadAllClient();
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
        String clientId = id.getText();
        String clientName = name.getText();
        String clientEmail = email.getText();
        String clientPhone = phone.getText();
        String clientAddress = address.getText();
        String checkInDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String checkOutDate = cheackout.getText();

        if (isValied()){
            if (clientId.isEmpty() || clientName.isEmpty() || clientAddress.isEmpty() || checkInDate.isEmpty() || checkOutDate.isEmpty()) {
                new Alert(Alert.AlertType.INFORMATION, "Empty fields! Please fill all required fields.").show();
                return;
            }

            // Create a new Client object
            Client client = new Client(clientId, clientName, clientEmail, clientPhone, clientAddress, checkInDate, checkOutDate);

            // Save the client
            try {
                boolean isSaved = ClientRepo.saveClient(client);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Client saved successfully!").show();
                    loadAllClient();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
            }

        }else {
            new Alert(Alert.AlertType.ERROR, "Check your fields correctly !").show();
        }

        // Check if any required field is empty

    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String clientId = id.getText();
        String name = this.name.getText();
        String email = this.email.getText();
        String phone = this.phone.getText();
        String address = this.address.getText();
        String checkIn = cheackin.getText();
        String checkOut = cheackout.getText();

        if (isValied()){

            Client client = new Client(clientId, name, email, phone, address, checkIn, checkOut);
            try {
                boolean isUpdated = ClientRepo.updateClient(client);
                if(isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Client updated successfully!").show();
                    loadAllClient();
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
            String clientId = id.getText();

            // Call the method to search for a client by ID
            Client client = ClientRepo.searchClientById(clientId);

            if (client != null) {
                // Populate the fields with the client information if found
                id.setText(client.getId());
                name.setText(client.getName());
                email.setText(client.getEmail());
                phone.setText(client.getPhone());
                address.setText(client.getAddress());
                cheackin.setText(client.getCheckIn().toString());
                cheackout.setText(client.getCheckOut().toString());
            } else {
                // Show an error message if the client is not found
                new Alert(Alert.AlertType.ERROR, "Client is not found!").show();

            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle or log the exception as needed
            new Alert(Alert.AlertType.ERROR, "An error occurred while searching for the client.").show();
        }

    }


    public void ClIdOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.CliID,id);

    }

    public void clientNameOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.NAME,name);

    }

    public void emailOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.EMAIL,email);

    }

    public void phoneOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.PHONE,phone);

    }

    public void addressOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.ADDRESS,address);

    }

    public void cheackInOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.DATE,cheackin);

    }

    public void cheackOutOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.DATE,cheackout);

    }
    public boolean isValied(){
        if (!Regex.setTextColor(TextFields.CliID,id)) return false;
        if (!Regex.setTextColor(TextFields.EMAIL,email)) return false;
        if (!Regex.setTextColor(TextFields.NAME,name)) return false;
        if (!Regex.setTextColor(TextFields.PHONE,phone)) return false;
        if (!Regex.setTextColor(TextFields.ADDRESS,address)) return false;
        if (!Regex.setTextColor(TextFields.DATE,cheackin)) return false;
        if (!Regex.setTextColor(TextFields.DATE,cheackout)) return false;



        return true;
    }
}
