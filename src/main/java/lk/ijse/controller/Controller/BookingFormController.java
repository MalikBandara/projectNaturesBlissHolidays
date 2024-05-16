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
import lk.ijse.controller.databse.DBConnection;
import lk.ijse.controller.model.*;
import lk.ijse.controller.model.Package;
import lk.ijse.controller.model.tm.BookingTm;
import lk.ijse.controller.model.tm.RoomTm;
import lk.ijse.repository.*;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFields;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingFormController {

    @FXML
    private TableView<RoomTm> tblRoom;

    @FXML
    private Button btnClient;


    @FXML
    private Button PaymentAdd;

    @FXML
    private Button btnreport;


    @FXML
    private TableColumn<?, ?> colNumber;

    @FXML
    private TableColumn<?, ?> colRate;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableColumn<?, ?> bookingDatecol;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private Button backll;

    @FXML
    private TableColumn<?, ?> colIdentity;

    @FXML
    private TableColumn<?, ?> colPackageID;

    @FXML
    private TableColumn<?, ?> colPayID;


    @FXML
    private Button btnbooking;

    @FXML
    private Button btnbooking1;


    @FXML
    private Button btnBack;

    @FXML
    private ComboBox<String> cmbIdentity;

    @FXML
    private ComboBox<String> cmbPackageID;

    @FXML
    private ComboBox<String> cmbPayID;

    @FXML
    private TableColumn<?, ?> roomidcol;

    @FXML
    private TableView<BookingTm> tblBooking;

    @FXML
    private TextField txtBookingId;

    @FXML
    private TextField txtDate;
    @FXML
    private ComboBox<String> roomcmb;


    @FXML
    void btnDeleteBookingOnAction(ActionEvent event) {
        String bookingId = txtBookingId.getText(); // Assuming the ID field corresponds to the booking ID

        if (isValied()) {
            try {
                boolean isDeleted = BookingRepo.deleteBooking(bookingId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Booking deleted!").show();
                    loadAllBooking();
                    loadAllRooms(); // Update rooms availability
                } else {
                    new Alert(Alert.AlertType.ERROR, "Booking not found!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Check your fields correctly!").show();
        }
    }


    @FXML
    void PlaceBookingOnAction(ActionEvent event) {
        String bookingId = txtBookingId.getText();
        String packageId = cmbPackageID.getValue(); // Assuming you're using a ComboBox for package selection
        String identityDetails = cmbIdentity.getValue(); // Assuming you're using a ComboBox for guest identity selection
        String bookingDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String roomId = roomcmb.getValue();
        String payId = cmbPayID.getValue(); // Assuming you're using a ComboBox for payment selection

        if (isValied()) {
            if (bookingId.isEmpty() || packageId == null || identityDetails == null ||
                    bookingDate.isEmpty() || roomId == null || payId == null) {
                new Alert(Alert.AlertType.ERROR, "Please fill all fields").show();
                return;
            }

            try {
                // Check if pay ID has been used before
                if (isPayIdUsed(payId)) {
                    new Alert(Alert.AlertType.ERROR, "Pay ID is already used!").show();
                    return;
                }

                // Check if room is already booked
                if (isRoomIdBooked(roomId)) {
                    new Alert(Alert.AlertType.ERROR, "Room is already booked!").show();
                    return;
                }

                boolean isSaved = BookingRepo.saveBookingAndUpdate(bookingId, packageId, identityDetails, payId, bookingDate, roomId);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Booking saved successfully!").show();
                    loadAllBooking();
                    loadAllRooms();
                    // Add any necessary actions after successful booking
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Check your fields correctly!").show();
        }
    }

    private boolean isRoomIdBooked(String roomId) throws SQLException {
        String sql = "SELECT * FROM Booking WHERE Room_id = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, roomId);
        ResultSet resultSet = pstm.executeQuery();

        return resultSet.next(); // Returns true if the room is already booked, false otherwise
    }



    private boolean isPayIdUsed(String payId) throws SQLException {
        String sql = "SELECT * FROM Booking WHERE payId = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, payId);
        ResultSet resultSet = pstm.executeQuery();

        return resultSet.next(); // Returns true if the pay ID has been used before, false otherwise
    }

    @FXML
    void backBtnOnAction(ActionEvent event) {

    }

    @FXML
    void cmbIdentityOnAction(ActionEvent event) {
        String identity = cmbIdentity.getValue();

        try {
            Client Client = ClientRepo.searchClientById(identity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void getClient() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> GusetIdList = ClientRepo.getID();

            for (String cliId : GusetIdList) {
                obList.add(cliId);
            }
            cmbIdentity.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void initialize(){
        getPackageID();
        getClient();
        getPaymentId();
        getRoomId();
        setCellValueFactory();
        loadAllBooking();
        loadAllRooms();
        txtDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));



    }

    @FXML
    void cmbPackIdOnAction(ActionEvent event) {
        String packageId = cmbPackageID.getValue();

        try {
            Package Package = PackageRepo.searchPackageById(packageId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void getPackageID() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> packageIdList = PackageRepo.getIds();

            for (String employeeId : packageIdList) {
                obList.add(employeeId);
            }
            cmbPackageID.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllRooms() {
        ObservableList<RoomTm> obList = FXCollections.observableArrayList();

        try {
            List<Room> RoomList = RoomRepo.getAll();
            for (Room Room : RoomList) {
                RoomTm tm = new RoomTm(
                        Room.getRoomId(),
                        Room.getRoomNumber(),
                        Room.getType(),
                        Room.getRate(),
                        Room.getStatus()
                );

                obList.add(tm);
            }

            tblRoom.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void cmbPayIdOnAction(ActionEvent event) {
        String paymentId = cmbPayID.getValue();

        try {
            Payment payment = PaymentRepo.searchPaymentById(paymentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    private void getPaymentId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> paymentIdList = PaymentRepo.getId();

            for (String employeeId : paymentIdList) {
                obList.add(employeeId);
            }
            cmbPayID.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

    }

    public void cmbRoomOnAction(ActionEvent actionEvent) {
        String Roomid = roomcmb.getValue();

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
            roomcmb.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colPackageID.setCellValueFactory(new PropertyValueFactory<>("packageId"));
        colIdentity.setCellValueFactory(new PropertyValueFactory<>("identityDetails"));
        colPayID.setCellValueFactory(new PropertyValueFactory<>("payId"));
        roomidcol.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        bookingDatecol.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));

        colid.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        colNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colRate.setCellValueFactory(new PropertyValueFactory<>("rate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));


    }

    private void loadAllBooking() {
        ObservableList<BookingTm> obList = FXCollections.observableArrayList();

        try {
            List<Booking> clientList = BookingRepo.getAllBooking();
            for (Booking Booking : clientList) {
                BookingTm Bookingtm = new BookingTm(
                        Booking.getBookingId(),
                        Booking.getPackageId(),
                        Booking.getIdentityDetails(),
                        Booking.getPayId(),
                        Booking.getRoomId(),
                        Booking.getBookingDate()
                );
                obList.add(Bookingtm);
            }
            tblBooking.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void BookingidkeyOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.Bookigid,txtBookingId);

    }

    public void BookingDateOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.DATE,txtDate);

    }
    public boolean isValied(){
        if (!Regex.setTextColor(TextFields.Bookigid,txtBookingId)) return false;
        if (!Regex.setTextColor(TextFields.DATE,txtDate)) return false;




        return true;
    }

    public void payaddOnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Payment2.fxml"));
            Parent loginPanel = loader.load();

            Stage stage = (Stage) PaymentAdd.getScene().getWindow();
            stage.setTitle("Payment");

            // Animate the exit of the welcome page
            Timeline exitTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(1),
                            new KeyValue(PaymentAdd.translateXProperty(), -PaymentAdd.getWidth(), Interpolator.EASE_BOTH))
            );
            exitTimeline.setOnFinished(e -> {
                // Animate the entrance of the service page
                double sceneWidth = stage.getScene().getWidth(); // or any other suitable width
                loginPanel.translateXProperty().set(sceneWidth);
                stage.getScene().setRoot(loginPanel);
                Timeline entranceTimeline = new Timeline(
                        new KeyFrame(Duration.seconds(1),
                                new KeyValue(loginPanel.translateXProperty(), 0, Interpolator.EASE_BOTH))
                );
                entranceTimeline.play();
            });
            exitTimeline.play();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void clientONAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Client2Form.fxml"));
            Parent loginPanel = loader.load();

            Stage stage = (Stage) btnClient.getScene().getWindow();
            stage.setTitle("Client");

            // Animate the exit of the welcome page
            Timeline exitTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(1),
                            new KeyValue(btnClient.translateXProperty(), -btnClient.getWidth(), Interpolator.EASE_BOTH))
            );
            exitTimeline.setOnFinished(e -> {
                // Animate the entrance of the service page
                double sceneWidth = stage.getScene().getWidth(); // or any other suitable width
                loginPanel.translateXProperty().set(sceneWidth);
                stage.getScene().setRoot(loginPanel);
                Timeline entranceTimeline = new Timeline(
                        new KeyFrame(Duration.seconds(1),
                                new KeyValue(loginPanel.translateXProperty(), 0, Interpolator.EASE_BOTH))
                );
                entranceTimeline.play();
            });
            exitTimeline.play();

        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    public void backonAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashBord.fxml"));
            Parent loginPanel = loader.load();

            Stage stage = (Stage) backll.getScene().getWindow();
            stage.setTitle("DashBoard");

            // Animate the exit of the welcome page
            Timeline exitTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(1),
                            new KeyValue(backll.translateXProperty(), -backll.getWidth(), Interpolator.EASE_BOTH))
            );
            exitTimeline.setOnFinished(e -> {
                // Animate the entrance of the service page
                double sceneWidth = stage.getScene().getWidth(); // or any other suitable width
                loginPanel.translateXProperty().set(sceneWidth);
                stage.getScene().setRoot(loginPanel);
                Timeline entranceTimeline = new Timeline(
                        new KeyFrame(Duration.seconds(1),
                                new KeyValue(loginPanel.translateXProperty(), 0, Interpolator.EASE_BOTH))
                );
                entranceTimeline.play();
            });
            exitTimeline.play();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void btnPayIdOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Report/boooking.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();
        data.put("Booking1",txtBookingId.getText());


        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, data, DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);


    }
}

