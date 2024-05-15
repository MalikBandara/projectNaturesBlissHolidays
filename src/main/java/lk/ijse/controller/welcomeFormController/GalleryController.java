package lk.ijse.controller.welcomeFormController;

import com.jfoenix.controls.JFXButton;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.image.ImageView; // Correct import for ImageView

public class GalleryController implements Initializable {

    @FXML
    private Button pausebtn;

    @FXML
    private ImageView imageSlide; // Correct import for ImageView

    private File file;
    private MediaPlayer mediaPlayer;
    private Media mediaa;

    @FXML
    private MediaView media;

    @FXML
    private Button playbtn;

    @FXML
    private Button resetbutton;

    @FXML
    private ImageView img;

    @FXML
    private Button BackOnWeclome;

    @FXML
    private AnchorPane galaeryRoot;

    @FXML
    private JFXButton backtowel;

    @FXML
    private ImageView imagelogo;

    int count = 0; // Initialize count variable

    public void slideShow() {
        ArrayList<Image> images = new ArrayList<>();
        images.add(new Image("/slideimage/img1.png"));
        images.add(new Image("/slideimage/img2.png"));
        images.add(new Image("/slideimage/img3.png"));
        images.add(new Image("/slideimage/Screenshot from 2024-05-11 18-30-58.png"));



        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            imageSlide.setImage(images.get(count));
            count = (count + 1) % images.size(); // Increment count and loop back to 0 when reaching the end
            if (count==4)
                count=0;
        }));
        timeline.setCycleCount(Timeline.INDEFINITE); // To make the slideshow repeat indefinitely
        timeline.play();
    }

    @FXML
    void btnGaleryOnAction(ActionEvent event) {
        try {
            Stage stage1 = (Stage) backtowel.getScene().getWindow();
            stage1.close();
            Parent load = FXMLLoader.load(getClass().getResource("/view/services.fxml"));
            Scene scene = new Scene(load);
            Stage stage = new Stage();
            stage.setTitle("Welcome_page");
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        file = new File("/home/malik/Downloads/Hotel Cinematic - Boutique Hotel Port Elizabeth.mp4");
        mediaa = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(mediaa);
        media.setMediaPlayer(mediaPlayer);
        slideShow();

        RotateTransition rotate = new RotateTransition();
        rotate.setNode(img);
        rotate.setDuration(Duration.millis(2000));
        rotate.setCycleCount(TranslateTransition.INDEFINITE);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setByAngle(360);
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.play();
    }

    public void playOnAction(ActionEvent actionEvent) {
        mediaPlayer.play();
    }

    public void pauseOnAction(ActionEvent actionEvent) {
        mediaPlayer.pause();
    }

    public void resetOnAction(ActionEvent actionEvent) {
        if (mediaPlayer.getStatus() != MediaPlayer.Status.READY) {
            mediaPlayer.seek(Duration.seconds(0.0));
        }
    }
}
