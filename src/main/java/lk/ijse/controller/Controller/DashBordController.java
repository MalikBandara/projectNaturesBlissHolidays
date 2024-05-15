package lk.ijse.controller.Controller;

import animatefx.animation.SlideInLeft;
import com.jfoenix.controls.JFXButton;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lk.ijse.calender.Main;
import lk.ijse.controller.databse.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashBordController implements Initializable {

    @FXML
    private JFXButton btncal;

    @FXML
    private AnchorPane pane3;


    @FXML
    private PieChart pieChartt;

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private AnchorPane pane4;


    @FXML
    private AnchorPane Dpane1;

    @FXML
    private AnchorPane Dpane2;

    @FXML
    private AnchorPane Dpane3;

    @FXML
    private AnchorPane Dpane4;

    @FXML
    private NumberAxis xaxist;

    @FXML
    private CategoryAxis yaxist;

    @FXML
    private Label lblEmployeecount;


    @FXML
    private JFXButton calender;

    @FXML
    private Label lblRoomcount;

    @FXML
    private Label lblguestCount;

    @FXML
    private Label lblvehicleCount;

    private int GuestCount;

    private int vehicleCount;

    private int roomCount;

    private int empCount;



    @FXML
    private Button Employee;

    @FXML
    private Button packagebtn;

    @FXML
    private Button backindash;

    @FXML
    private Button btnBooking;


    @FXML
    private static Label lblAdminUsername;

    @FXML
    private Button Room1;

    @FXML
    private Button Client;


    @FXML
    private AnchorPane rootNode;

    @FXML
    private Button vehicle;

    @FXML
    private ImageView userimg;

    @FXML
    private Button payment;

    @FXML
    private Button btn;

    @FXML
    private Button Dashboard;

    @FXML
    private AnchorPane CenterNode;



    private void setEmpCount(int empCount) {
        lblEmployeecount.setText(String.valueOf(empCount));
    }

    private int getEmpCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS Emp_count FROM Employee";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("Emp_count");
        }
        return 0;

    }

    private void setRoomCount(int roomCount) {

        lblRoomcount.setText(String.valueOf(roomCount));
    }

    private int getRoomCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS Room_count FROM Room";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("Room_count");
        }
        return 0;


    }

    private void setVehicleCount(int vehicleCount) {
        lblvehicleCount.setText(String.valueOf(vehicleCount));
    }

    private int getVehicleCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS Vehicle_count FROM Vehicle";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("Vehicle_count");
        }
        return 0;

    }

    private void setCustomerCount(int GuestCount) {

        lblguestCount.setText(String.valueOf(GuestCount));
    }

    private int getGuestCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS Guest_count FROM Guest";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("Guest_count");
        }
        return 0;
    }


    @FXML
    void BackbtnOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/welcom_form.fxml"));
            Parent Loginpanel = loader.load();

            Stage stage = (Stage) backindash.getScene().getWindow();
            stage.setTitle("Employee");

            // Animate the exit of the welcome page
            Timeline exitTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(1),
                            new KeyValue(backindash.translateXProperty(), -backindash.getWidth(), Interpolator.EASE_BOTH))
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
    void EmployeeLogIn(ActionEvent event) throws IOException {

        AnchorPane EmployeePane = FXMLLoader.load(this.getClass().getResource("/view/employee.fxml"));
        CenterNode.getChildren().clear();
        CenterNode.getChildren().add(EmployeePane);





    }

    @FXML
    void packageOnAction(ActionEvent event) throws IOException {
        AnchorPane packagepane = FXMLLoader.load(this.getClass().getResource("/view/package.fxml"));
        CenterNode.getChildren().clear();
        CenterNode.getChildren().add(packagepane);

    }

    public void RoomLoginOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane RoomPane = FXMLLoader.load(this.getClass().getResource("/view/room.fxml"));
        CenterNode.getChildren().clear();
        CenterNode.getChildren().add(RoomPane);

    }

    public void VehicleOnAction(ActionEvent actionEvent) throws IOException {

        AnchorPane vehicle = FXMLLoader.load(this.getClass().getResource("/view/vehicle_form.fxml"));
        CenterNode.getChildren().clear();
        CenterNode.getChildren().add(vehicle);


    }

    public void paymentOnLogin(ActionEvent actionEvent) throws IOException {

        AnchorPane payment = FXMLLoader.load(this.getClass().getResource("/view/payment_form.fxml"));
        CenterNode.getChildren().clear();
        CenterNode.getChildren().add(payment);

    }

    public void ClientLoginOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane customerPane = FXMLLoader.load(this.getClass().getResource("/view/Client_form.fxml"));
        CenterNode.getChildren().clear();
        CenterNode.getChildren().add(customerPane);
    }

    public void BookingOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane root = FXMLLoader.load(this.getClass().getResource("/view/Booking_form.fxml"));

        Scene scene = new Scene(root);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Booking Form");
        new SlideInLeft(root).play();
    }

    public void btnDash2OnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane Dash2 = FXMLLoader.load(this.getClass().getResource("/view/Dash2.fxml"));
        CenterNode.getChildren().clear();
        CenterNode.getChildren().add(Dash2);

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        iniLineChart();
        iniPieChart();
        try {
            GuestCount = getGuestCount();
            vehicleCount = getVehicleCount();
            roomCount = getRoomCount();
            empCount = getEmpCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setCustomerCount(GuestCount);
        setVehicleCount(vehicleCount);
        setRoomCount(roomCount);
        setEmpCount(empCount);


        ScaleTransition scale = new ScaleTransition();
        scale.setNode(userimg);
        scale.setDuration(Duration.millis(2000));
        scale.setCycleCount(TranslateTransition.INDEFINITE);
        scale.setInterpolator(Interpolator.LINEAR);
        scale.setByX(0.25);
        scale.setByY(0.25);
        scale.setAutoReverse(true);
        scale.play();





    }
    private void iniLineChart(){
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data("Mondays",8));
        series.getData().add(new XYChart.Data("Tuesday",12));
        series.getData().add(new XYChart.Data("Wednesday",16));
        series.getData().add(new XYChart.Data("Thursday",4));
        series.getData().add(new XYChart.Data("Friday",12));
        series.getData().add(new XYChart.Data("Saturday",10));
        series.getData().add(new XYChart.Data("Sunday",5));
        lineChart.getData().addAll(series);
        lineChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
        series.getNode().setStyle("-fx-stroke: #000000");
    }

    private void iniPieChart(){
        ObservableList<PieChart.Data> PiechartDate = FXCollections.observableArrayList(
                new PieChart.Data("Sri Lanka" , 15),
                new PieChart.Data("China" , 5),
                new PieChart.Data("Australia" , 35),
                new PieChart.Data("Russia" , 10),
                new PieChart.Data("Finland" , 35)

        );
        pieChartt.setData(PiechartDate);
    }

    public void btncalOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/calculator.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setTitle("Calculator");

        ((main.MainWindowController)loader.getController()).init(stage);
        stage.show();
    }


    public void CalenderOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/Calender.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Calender!");
        stage.setScene(scene);
        stage.show();

    }
}
