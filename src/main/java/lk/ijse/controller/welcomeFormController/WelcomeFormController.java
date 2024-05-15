package lk.ijse.controller.welcomeFormController;

import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideInRight;
import com.jfoenix.controls.JFXButton;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WelcomeFormController {

    @FXML
    private AnchorPane anchorRoot;

    @FXML
    private JFXButton btnAdminLogin;

    @FXML
    private JFXButton btnGuestLogin;

    @FXML
    private JFXButton btnSarvice;

    @FXML
    private ImageView imageroot;

    @FXML
    private ImageView imgLogo;

    @FXML
    private Label lblTime;

    @FXML
    private Label txt1;

    @FXML
    private Label txt2;

    @FXML
    private Label txt3;

    @FXML
    private Label txt4;

    @FXML
    private Label txt5;

    @FXML
    private Label txt6;

    public void initialize() {
        new SlideInLeft(txt1).play();
        new SlideInLeft(txt2).play();
        new SlideInLeft(txt3).play();
        new SlideInLeft(txt4).play();
        new SlideInLeft(txt5).play();
        new SlideInLeft(txt6).play();
        new SlideInLeft(imgLogo).play();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> updateClock())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        updateClock();

    }

    private void updateClock() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedTime = dateFormat.format(now);
        lblTime.setText(formattedTime);
        lblTime.setStyle("-fx-text-fill: black;");
        lblTime.setStyle("-fx-font-size: 20px;");


    }

    @FXML
    void btnGuestLoginOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginPart2.fxml"));
            Parent Loginpanel = loader.load();

            Stage stage = (Stage) btnGuestLogin.getScene().getWindow();
            stage.setTitle("Guest Login");

            // Animate the exit of the welcome page
            Timeline exitTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(1),
                            new KeyValue(btnGuestLogin.translateXProperty(), -btnGuestLogin.getWidth(), Interpolator.EASE_BOTH))
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
    void btnLoginOnAction(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/admin_login_form.fxml"));
            Parent Loginpanel = loader.load();

            Stage stage = (Stage) btnAdminLogin.getScene().getWindow();
            stage.setTitle("Admin Login");

            // Animate the exit of the welcome page
            Timeline exitTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(1),
                            new KeyValue(btnAdminLogin.translateXProperty(), -btnAdminLogin.getWidth(), Interpolator.EASE_BOTH))
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
    void btnServiceOnAction(ActionEvent event) throws IOException {
        AnchorPane root = FXMLLoader.load(this.getClass().getResource("/view/services.fxml"));

        Scene scene = new Scene(root);

        Stage stage = (Stage) this.anchorRoot.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Service Form");
        new SlideInLeft(root).play();
    }

}
