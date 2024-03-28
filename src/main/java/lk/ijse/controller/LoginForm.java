//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginForm {
    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessageLable;
    @FXML
    private Button loginbutton;
    @FXML
    private PasswordField passwardTextField;
    @FXML
    private TextField userNameTextField;

    public LoginForm() {
    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage)this.cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void loginButtonOnAction(ActionEvent event) {
        if (!this.userNameTextField.getText().isBlank() && !this.passwardTextField.getText().isBlank()) {
            this.loginMessageLable.setText("You try to Login!");
        } else {
            this.loginMessageLable.setText("Please enter username and passward.");
        }

    }
}
