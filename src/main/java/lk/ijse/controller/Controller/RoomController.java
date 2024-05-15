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
import lk.ijse.controller.model.Room;
import lk.ijse.controller.model.tm.RoomTm;
import lk.ijse.repository.RoomRepo;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFields;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class RoomController {

    @FXML
    private TableColumn<?, ?> colNumber;

    @FXML
    private TableColumn<?, ?> colRate;

    @FXML
    private Button uproom;

    @FXML
    private Button saveroom;
    @FXML
    private Button deleteroom;

    @FXML
    private Button clearRoom;

    @FXML
    private Button backroom;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TextField id;

    @FXML
    private TextField number;

    @FXML
    private TextField rate;

    @FXML
    private TextField status;

    @FXML
    private TableView<RoomTm> tblRoom;

    @FXML
    private TextField type;

    @FXML
    private Button btn;



    @FXML
    void UpdateOnAction(ActionEvent event) {
        String roomId = id.getText();
        String roomNumber = number.getText();
        String roomType = type.getText();
        String roomRate = rate.getText();
        String roomStatus = status.getText();

        if (isValied()){
            Room room1 = new Room(roomId, roomNumber, roomType, roomRate, roomStatus);
            try {
                boolean isUpdated = RoomRepo.updateRoom(room1);
                if(isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Room updated successfully!").show();
                    loadAllRooms();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Check your fields correctly !").show();
        }




    }

    @FXML
    void btnBackOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashBord.fxml"));
            Parent Loginpanel = loader.load();

            Stage stage = (Stage) backroom.getScene().getWindow();
            stage.setTitle("Login");

            // Animate the exit of the welcome page
            Timeline exitTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(1),
                            new KeyValue(backroom.translateXProperty(), -backroom.getWidth(), Interpolator.EASE_BOTH))
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
    void clearOnAction(ActionEvent event) {
        clearFields();

    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        String roomid = id.getText();
        if (isValied()){
            try {
                boolean isDeleted = RoomRepo.deleteRoom(roomid);
                if(isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Room deleted!").show();
                    loadAllRooms();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Check your fields correctly !").show();
        }



    }

    @FXML
    void saveOnAction(ActionEvent event) {
        String roomId = id.getText();
        String roomNumber = number.getText();
        String roomType = type.getText();
        String roomRate = rate.getText();
        String roomStatus = status.getText();

        if (isValied()){
            Room room = new Room(roomId, roomNumber, roomType, roomRate, roomStatus);

            try {
                if (roomId.isEmpty() || roomNumber.isEmpty() || roomType.isEmpty() || roomRate.isEmpty() || roomStatus.isEmpty()) {
                    new Alert(Alert.AlertType.INFORMATION, "Empty Fields! Try again").show();
                } else {
                    boolean isSaved = RoomRepo.saveRoom(room);
                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Room saved successfully!").show();
                        loadAllRooms();
                    }
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }


        }else {
            new Alert(Alert.AlertType.ERROR, "Check your fields correctly !").show();
        }



    }
    public void initialize(){
        setCellValueFactory();
        loadAllRooms();
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

    private void setCellValueFactory() {



        colid.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        colNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colRate.setCellValueFactory(new PropertyValueFactory<>("rate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

    }

    private void clearFields() {
        id.setText("");
        number.setText("");
        type.setText("");
        rate.setText("");
        status.setText("");
    }


    public void SearchOnAction(ActionEvent actionEvent) {
        try {
            String roomId = id.getText();

            Room room = RoomRepo.searchRoomById(roomId);
            if (room != null) {
                id.setText(room.getRoomId());
                number.setText(room.getRoomNumber());
                type.setText(room.getType());
                rate.setText(String.valueOf(room.getRate()));
                status.setText(room.getStatus());
            } else {
                new Alert(Alert.AlertType.ERROR, "Room is not found!").show();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle or log the exception as needed
            new Alert(Alert.AlertType.ERROR, "An error occurred while searching for the room.").show();
        }
    }

    public void RoomIdOnAction(KeyEvent keyEvent) {

        Regex.setTextColor(TextFields.rooID,id);

    }

    public void numberOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.LETTERS_AND_NUMBERS,number);


    }

    public void typeONAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.ONLY_LETTERS,type);


    }

    public void RateOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.DOUBLE,rate);


    }

    public void StatusOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.rooStatus,status);


    }
    public boolean isValied(){
        if (! Regex.setTextColor(TextFields.rooID,id)) return false;
        if (!Regex.setTextColor(TextFields.LETTERS_AND_NUMBERS,number)) return false;
        if (!Regex.setTextColor(TextFields.ONLY_LETTERS,type)) return false;
        if (!Regex.setTextColor(TextFields.DOUBLE,rate)) return false;
        if (!Regex.setTextColor(TextFields.rooStatus,status)) return false;

        return true;
    }
}
