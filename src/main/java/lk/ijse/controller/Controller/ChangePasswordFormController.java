package lk.ijse.controller.Controller;

import animatefx.animation.FadeIn;
import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideInUp;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.controller.OuterController.LoginFormPart2;
import lk.ijse.repository.GuestRepo;


import java.io.IOException;
import java.sql.SQLException;

public class ChangePasswordFormController {

    public Text txtSubText;
    public Button btnBack;
    public Button btnChangePassword;
    public PasswordField txtPassword;
    public PasswordField txtRePassword;
    public Text txtUserId;

    public void initialize() {
       // new SlideInLeft(txtSubText).play();
        txtUserId.setText(LoginFormPart2.attemptingUser);
        new FadeIn(txtUserId).play();
    }
    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Do you want to go back , Are you sure ?");
        alert.setContentText("Choose your option.");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeYes) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/view/LoginPart2.fxml"));
                    Scene scene1 = new Scene(root);
                    Stage stage1 = (Stage) btnBack.getScene().getWindow();
                    stage1.setScene(scene1);
                    stage1.setTitle("Login");
                    stage1.centerOnScreen();

                    new FadeIn(root).play();
                }catch (Exception e){
                    System.out.println(e);
                }
            } else if (response == buttonTypeNo) {
                System.out.println("User clicked No");
            }
        });

    }

    public void btnChangePasswordOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        String password = txtPassword.getText();
        String rePassword = txtRePassword.getText();
        if(password.length()>=8 && password.equals(rePassword)){
            boolean isChanged = GuestRepo.changePassword(LoginFormPart2.attemptingUser, password);
            if(isChanged){
                new Alert(Alert.AlertType.CONFIRMATION, "Password Changed Successfully | Redirecting to Login").showAndWait();
                Parent root = FXMLLoader.load(getClass().getResource("/view/LoginPart2.fxml"));
                Scene scene1 = new Scene(root);
                Stage stage1 = (Stage) btnChangePassword.getScene().getWindow();
                stage1.setScene(scene1);
                stage1.setTitle("Login");
                stage1.centerOnScreen();

                new SlideInUp(root).play();
            }else{
                new Alert(Alert.AlertType.ERROR, "Password Changing Failed | Try Again").showAndWait();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Check Your Password again").showAndWait();
        }
    }
}
