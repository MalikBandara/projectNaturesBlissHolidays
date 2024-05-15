package lk.ijse.controller.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.controller.databse.DBConnection;
import lk.ijse.controller.model.Reservation;
import lk.ijse.controller.model.Room;
import lk.ijse.controller.model.tm.RoomTm;
import lk.ijse.repository.Reservationepo;
import lk.ijse.repository.RoomRepo;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFields;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ReservationController implements Initializable {

    @FXML
    private TextField CheackDate;

    @FXML
    private TextField CheackoutDate;

    @FXML
    private TextField GuestName;

    @FXML
    private TextField NoOfGuest;

    @FXML
    private TextField ResID;

    @FXML
    private TextField RoomType;

    @FXML
    private Button backbtn;

    @FXML
    private JFXButton btnreservation;

    @FXML
    private ComboBox<String> cmbRoom;

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
    private JFXButton receipt;

    @FXML
    private TableView<RoomTm> tblRoom;


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
    void ReceiptOnAction(ActionEvent event) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Report/Reservation.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();
        data.put("Reservation01",ResID.getText());


        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, data, DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);

    }

    @FXML
    void ReservationONAction(ActionEvent event) throws SQLException {
        String guestId = ResID.getText();
        String guestname = GuestName.getText();
        String cheackin = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String cheackout = CheackoutDate.getText();
        String type = RoomType.getText();
        String noofGuest = NoOfGuest.getText();
        String room = cmbRoom.getValue();


        if (guestId.isEmpty() || guestname == null || cheackin == null ||
                cheackout == null || type.isEmpty()||noofGuest.isEmpty()||room.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all fields").show();
            return;
        }

        Reservation reservation = new Reservation(guestId, guestname, cheackin, cheackout, type, noofGuest, room);

        if (isRoomIdBooked(room)) {
            new Alert(Alert.AlertType.ERROR, "Room is already booked Sorry Book an other room !").show();
            return;
        }


        try {
            boolean isSaved = Reservationepo.saveReservation(reservation);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Reservation saved successfully!").show();
                loadAllRooms();


                // Add any necessary actions after successful booking
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    private boolean isRoomIdBooked(String roomId) throws SQLException {
        String sql = "SELECT * FROM Reservation WHERE Room_id = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, roomId);
        ResultSet resultSet = pstm.executeQuery();

        return resultSet.next(); // Returns true if the room is already booked, false otherwise
    }
    private void setCellValueFactory() {



        colid.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        colNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colRate.setCellValueFactory(new PropertyValueFactory<>("rate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

    }

    @FXML
    void backloWelcome(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/welcom_form.fxml"));
            Parent loginPanel = loader.load();

            Stage stage = (Stage) backbtn.getScene().getWindow();
            stage.setTitle("Welcome page");

            // Animate the exit of the welcome page
            Timeline exitTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(1),
                            new KeyValue(backbtn.translateXProperty(), -backbtn.getWidth(), Interpolator.EASE_BOTH))
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

    @FXML
    void cmbRoomOnAction(ActionEvent event) {
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

    public void ReservationOnKey(KeyEvent keyEvent) {

        Regex.setTextColor(TextFields.ResID,ResID);
    }

    public void GuestNameOnKey(KeyEvent keyEvent) {

        Regex.setTextColor(TextFields.NAME,GuestName);
    }

    public void cheackingDate(KeyEvent keyEvent) {

        Regex.setTextColor(TextFields.DATE,CheackDate);

    }

    public void cheackOutDate(KeyEvent keyEvent) {

        Regex.setTextColor(TextFields.DATE,CheackoutDate);

    }

    public void RoomTypeKEy(KeyEvent keyEvent) {

        Regex.setTextColor(TextFields.ONLY_LETTERS,RoomType);

    }

    public void NoOfGuest(KeyEvent keyEvent) {

        Regex.setTextColor(TextFields.INT,NoOfGuest);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        CheackDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        setCellValueFactory();
        getRoomId();
        loadAllRooms();
    }
}
