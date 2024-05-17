package lk.ijse.controller.Controller;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.Mail;
import lk.ijse.controller.databse.DBConnection;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFields;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import org.controlsfx.control.Notifications;

public class AdminLoginFormController implements Initializable {

    @FXML
    private Button backInLoginn;

    @FXML
    private AnchorPane root;

    @FXML
    private ImageView logo;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    void BackLoginONAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/welcom_form.fxml"));
            Parent loginPanel = loader.load();

            Stage stage = (Stage) root.getScene().getWindow();
            stage.setTitle("Welcome");

            // Animate the exit of the welcome page
            Timeline exitTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(1),
                            new KeyValue(root.translateXProperty(), -root.getWidth(), Interpolator.EASE_BOTH))
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
    void LoginOnActionAdmin(ActionEvent event) {
        String userName = username.getText();
        String pw = password.getText();

        if (isValied()){
            try {
                cheackCredential(userName, pw);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Check your fields correctly !").show();
        }
    }

    private void cheackCredential(String userName, String pw) {
        try {
            if (userName.isEmpty() || pw.isEmpty()) {
                throw new IllegalArgumentException("Username or password is empty");
            }

            String sql = "SELECT Email , Passward FROM Admin WHERE Email = ?";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String user = resultSet.getString("Email");
                String pww = resultSet.getString("Passward");

                if (pw.equals(pww)) {
                    NavigateToDashBord();
                } else {
                    throw new IllegalArgumentException("Sorry! Password is incorrect!");
                }
            } else {
                throw new IllegalArgumentException("Sorry! User ID cannot be found!");
            }
        } catch (SQLException | IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void NavigateToDashBord() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashBord.fxml"));
            Parent loginPanel = loader.load();

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setTitle("Dashboard");

            // Animate the exit of the login page
            Timeline exitTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(1),
                            new KeyValue(loginButton.translateXProperty(), -loginButton.getWidth(), Interpolator.EASE_BOTH))
            );
            exitTimeline.setOnFinished(e -> {
                // Animate the entrance of the dashboard page
                double sceneWidth = stage.getScene().getWidth(); // or any other suitable width
                loginPanel.translateXProperty().set(sceneWidth);
                stage.getScene().setRoot(loginPanel);
                Timeline entranceTimeline = new Timeline(
                        new KeyFrame(Duration.seconds(1),
                                new KeyValue(loginPanel.translateXProperty(), 0, Interpolator.EASE_BOTH))
                );
                entranceTimeline.play();

                // Send email notification
                sendEmailNotification(username.getText());

            });
            exitTimeline.play();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void sendEmailNotification(String email) {
        // Create and configure the Mail object
        Mail mail = new Mail();
        mail.setMsg("Welcome! User: " + email + "\nNew Login Detected at: " + java.time.LocalDateTime.now() + "\nThank You,\nNatures bliss  Holidays  Support Team");
        mail.setTo(email);
        mail.setSubject("Natures bliss  Holidays Login Detection");

        // Start a new thread to send the email
        Thread thread = new Thread(() -> {
            try {
                mail.run();

                showNotification("Email Sent", "New Login Detected ! " + java.time.LocalTime.now()+ " Thanks you ");
            } catch (Exception e) {
                e.printStackTrace();
                // Show notification on email sending failure
                showNotification("Email Sending Failed", "Failed to send email notification!");
            }
        });
        thread.start();
    }

    private void showNotification(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.show();
        });
    }

    private void clearFields() {
        username.setText("");
        password.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RotateTransition rotate = new RotateTransition();
        rotate.setNode(logo);
        rotate.setDuration(Duration.millis(2000));
        rotate.setCycleCount(TranslateTransition.INDEFINITE);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setByAngle(360);
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.play();
    }

    public void txtPwOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.PASSWARD, password);
    }

    public void txtUserIDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.EMAIL, username);
    }

    public boolean isValied() {
        if (!Regex.setTextColor(TextFields.PASSWARD, password)) return false;
        if (!Regex.setTextColor(TextFields.EMAIL, username)) return false;
        return true;
    }
}
