package lk.ijse.controller.OuterController;

import animatefx.animation.FadeIn;
import animatefx.animation.SlideInLeft;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.Mail;
import lk.ijse.controller.model.Guest;
import lk.ijse.repository.GuestRepo;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFields;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.ResourceBundle;

public class LoginFormPart2 implements Initializable {

    @FXML
    private AnchorPane loginRoot;

    @FXML
    private TextField UserId;

    @FXML
    private Hyperlink txtForgetPassword;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    public static String attemptingUser;


    @FXML
    private ImageView Logo;

    public static int oneTimePassword;

    @FXML
    void loginOnAction() {
        String username = UserId.getText();
        String pass = password.getText();

        try {
            Guest guest = GuestRepo.getGuest(username);

            if (guest != null && guest.getPassword().equals(pass)) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GuestDashBoard.fxml"));
                    Parent Loginpanel = loader.load();

                    Stage stage = (Stage) loginRoot.getScene().getWindow();
                    stage.setTitle("GuestDashboard");

                    // Animate the exit of the welcome page
                    Timeline exitTimeline = new Timeline(
                            new KeyFrame(Duration.seconds(1),
                                    new KeyValue(loginRoot.translateXProperty(), -loginRoot.getWidth(), Interpolator.EASE_BOTH))
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
            } else {
                // Login failed, show an error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid username or password. Please try again.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions
            e.printStackTrace();
            // Optionally, show an error message to the user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while processing your request. Please try again later.");
            alert.showAndWait();
        }
    }

    public void BackLoginONAction(ActionEvent actionEvent) throws IOException {
        AnchorPane root = FXMLLoader.load(this.getClass().getResource("/view/welcom_form.fxml"));

        Scene scene = new Scene(root);

        Stage stage = (Stage) this.loginRoot.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login Form");
        new SlideInLeft(root).play();

    }

    public void linkRegistrationOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane root = FXMLLoader.load(this.getClass().getResource("/view/SignUpPage.fxml"));

        Scene scene = new Scene(root);

        Stage stage = (Stage) this.loginRoot.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Register Form");
        new SlideInLeft(root).play();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RotateTransition rotate = new RotateTransition();
        rotate.setNode(Logo);
        rotate.setDuration(Duration.millis(2000));
        rotate.setCycleCount(TranslateTransition.INDEFINITE);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setByAngle(360);
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.play();
    }

    public void IdentityOnKeyAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.NIC,UserId);
    }

    public void pwOnKeyAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.PASSWARD,password);

    }

    public static int generateOTP(){
        Random random = new Random();
        int password = random.nextInt(9000000) + 1000000;
        System.out.println(password);
        return password;
    }

    public void txtForgetPasswordOnAction(MouseEvent mouseEvent) throws SQLException {
        if(!UserId.getText().isEmpty()){
            if(GuestRepo.getGuest(UserId.getText())!=null) {
                attemptingUser = UserId.getText();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Do you want to Change Your Password ?");
                alert.setContentText("Send OTP To Your Email");

                ButtonType buttonTypeYes = new ButtonType("OK");
                ButtonType buttonTypeNo = new ButtonType("Cancel");

                alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

                alert.showAndWait().ifPresent(response -> {
                    if (response == buttonTypeYes) {
                        try {
                            oneTimePassword = generateOTP();
                            String touristEmail = GuestRepo.getTouristEmailFromId(UserId.getText());
                            Mail mail = new Mail();
                            mail.setMsg("Hello," + "\n\n\tUser : " + touristEmail + " \n\n\tAn OTP Request Detected at :  " + LocalDateTime.now() + " \n\n\tOTP : " + oneTimePassword + " \n\nThank You,\n" +
                                    "Natures bliss  Holidays Support Team");
                            mail.setTo(touristEmail);
                            mail.setSubject("Natures bliss holidays OTP Verification");

                            Thread thread = new Thread(mail);
                            thread.start();

                            Parent root = FXMLLoader.load(getClass().getResource("/view/foreget_password_form.fxml"));
                            Scene scene1 = new Scene(root);
                            Stage stage1 = (Stage) txtForgetPassword.getScene().getWindow();
                            stage1.setScene(scene1);
                            stage1.setTitle("OTP Verification");
                            stage1.centerOnScreen();

                            new FadeIn(root).play();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    } else if (response == buttonTypeNo) {
                        System.out.println("User Canceled the Operation");
                    }
                });
            }else{
                new Alert(Alert.AlertType.ERROR, "Check your User ID again..").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Enter your ID first !").show();
        }
    }

}
