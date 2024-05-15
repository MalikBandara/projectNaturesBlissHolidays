package lk.ijse.controller.Controller;

import animatefx.animation.FadeIn;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.controller.OuterController.LoginFormPart2;

import java.io.IOException;

public class ForgetPasswordFormController {

    public TextField txtOTP;
    public Text txtEnterNotice;
    public Button btnVerify;
    public Button btnBack;

    public int count=3;
    public Text txtAttempts;

    public void initialize() {
        txtAttempts.setText("3");
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginPart2.fxml"));
        Scene scene1 = new Scene(root);
        Stage stage1 = (Stage) btnBack.getScene().getWindow();
        stage1.setScene(scene1);
        stage1.setTitle("Login");
        stage1.centerOnScreen();

        new FadeIn(root).play();
    }

    public void btnVerifyOnAction(ActionEvent actionEvent) throws IOException {
        String enteredOTP = txtOTP.getText();
        if (enteredOTP.equals(String.valueOf(LoginFormPart2.oneTimePassword))) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/change_password .fxml"));
            Scene scene1 = new Scene(root);
            Stage stage1 = (Stage) btnBack.getScene().getWindow();
            stage1.setScene(scene1);
            stage1.setTitle("Change Your Password");
            stage1.centerOnScreen();

            new FadeIn(root).play();
        }else{
            if(count!=0){
                new Alert(Alert.AlertType.ERROR, "Invalid OTP").show();
                --count;
                txtAttempts.setText(String.valueOf(count));
            }else{
                new Alert(Alert.AlertType.ERROR, "You have entered wrong OTP too many times : Redirected to Login").show();

                Parent root = FXMLLoader.load(getClass().getResource("/view/LoginPart2.fxml"));
                Scene scene1 = new Scene(root);
                Stage stage1 = (Stage) btnBack.getScene().getWindow();
                stage1.setWidth(1900);
                stage1.setHeight(1000);
                stage1.setScene(scene1);
                stage1.setTitle("Login");
                stage1.centerOnScreen();

                new FadeIn(root).play();
            }
        }
    }
}
