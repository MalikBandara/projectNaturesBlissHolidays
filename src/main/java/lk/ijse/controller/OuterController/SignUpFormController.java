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
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.controller.model.Guest;
import lk.ijse.regex;
import lk.ijse.repository.GuestRepo;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpFormController implements Initializable {

    @FXML
    private PasswordField txtRePassword;


    @FXML
    private AnchorPane RegisterPanel;

    @FXML
    private TextField txtEmail;

    @FXML
    private Hyperlink login;

    @FXML
    private Button registerButton;

    @FXML
    private TextField txtId;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private ImageView logo;

    @FXML
    void LoginONAction(ActionEvent event) throws IOException {
        AnchorPane root = FXMLLoader.load(this.getClass().getResource("/view/LoginPart2.fxml"));

        Scene scene = new Scene(root);

        Stage stage = (Stage) this.RegisterPanel.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login Form");
        new SlideInLeft(root).play();


    }

    @FXML
    void RegisterOnAction(ActionEvent event) {
        String userId=txtId.getText();
        String userName=txtUserName.getText();
        String password=txtPassword.getText();
        String rePassword=txtRePassword.getText();
        String email=txtEmail.getText();

        if (isValied()){
            regex regex = new regex();
            boolean isIdNumMatched = regex.isIdNumValid(userId);
            boolean isEmailAddressMathced = regex.isEmailValid(email);
            if(isIdNumMatched){
                if(isEmailAddressMathced){

                    if(userId!=null&&userName!=null&&password!=null&&rePassword!=null&&isEmailAddressMathced) {
                        if (password.equals(rePassword)) {
                            boolean passwordValid = regex.isPasswordValid(txtPassword.getText());
                            System.out.println(passwordValid);
                            if (!passwordValid) {
                                new Alert(Alert.AlertType.ERROR, "Password must be containing at least one uppercase, one lowercase, one digit and one special character").show();
                            }else{
                                Guest touristDto = new Guest(userId, userName, password,email);
                                try {
                                    boolean isSaved = GuestRepo.saveGuest(touristDto);
                                    if (isSaved) {
                                        clearFields();
                                        new Alert(Alert.AlertType.INFORMATION, "Sign Up Successful").showAndWait();

                                    } else {
                                        new Alert(Alert.AlertType.ERROR, "Duplicate ID check your identity Number  ").showAndWait();
                                    }
                                } catch (Exception e) {
                                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                                }
                            }
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Passwords do not match").show();
                            txtRePassword.clear();
                        }
                    }else{
                        new Alert(Alert.AlertType.ERROR, "All fields are required").show();
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR, "Invalid Email Address").show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR, "Invalid ID Number").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Check your fields ").show();
        }



    }
    public void clearFields(){
        txtId.clear();
        txtPassword.clear();
        txtRePassword.clear();
        txtUserName.clear();
        txtEmail.clear();
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

    public void NameOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.NAME,txtUserName);

    }

    public void NicOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.NIC,txtId);

    }

    public void pwOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.PASSWARD,txtPassword);

    }

    public void repwOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.PASSWARD,txtRePassword);

    }

    public void EmailkeyOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.EMAIL,txtEmail);

    }

    public boolean isValied(){
        if (! Regex.setTextColor(TextFields.NAME,txtUserName)) return false;
        if (!Regex.setTextColor(TextFields.NIC,txtId)) return false;
        if (!Regex.setTextColor(TextFields.PASSWARD,txtPassword)) return false;
        if (!Regex.setTextColor(TextFields.PASSWARD,txtRePassword)) return false;
        if (!Regex.setTextColor(TextFields.EMAIL,txtEmail)) return false;

        return true;
    }
}
