package lk.ijse.controller.welcomeFormController;

import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideInRight;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ServicesFormController implements Initializable {


    @FXML
    private Label s1;

    @FXML
    private Label s10;

    @FXML
    private Label s11;

    @FXML
    private Label s12;

    @FXML
    private Label s13;

    @FXML
    private Label s14;

    @FXML
    private ImageView s15;

    @FXML
    private Label s2;

    @FXML
    private Label s3;

    @FXML
    private Label s4;

    @FXML
    private Label s5;

    @FXML
    private Label s6;

    @FXML
    private Label s7;

    @FXML
    private Label s8;

    @FXML
    private Label s9;
    public Button backService;
    public Button btnGalary;

    public Button btnStart;

    @FXML
    private ImageView img;

    @FXML
    private AnchorPane serviceroot;

    public void initialize(){





    }


    public void btnGalaryOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane root = FXMLLoader.load(this.getClass().getResource("/view/Galery.fxml"));

        Scene scene = new Scene(root);

        Stage stage = (Stage) this.serviceroot.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Galary Form");
        new SlideInLeft(root).play();

    }

    public void btnServiceBackOnAction(ActionEvent actionEvent) {
        try {
            Stage stage1 = (Stage)backService.getScene().getWindow();
            stage1.close();

            Parent load = FXMLLoader.load(getClass().getResource("/view/welcom_form.fxml"));
            Scene scene = new Scene(load);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public void btnGetStartOnAction(ActionEvent actionEvent) throws IOException {

        AnchorPane root = FXMLLoader.load(this.getClass().getResource("/view/SignUpPage.fxml"));

        Scene scene = new Scene(root);

        Stage stage = (Stage) this.serviceroot.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Register Form");
        new SlideInLeft(root).play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RotateTransition rotate = new RotateTransition();
        rotate.setNode(img);
        rotate.setDuration(Duration.millis(2000));
        rotate.setCycleCount(TranslateTransition.INDEFINITE);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setByAngle(360);
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.play();

    }
}
