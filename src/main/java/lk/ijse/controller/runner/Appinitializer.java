//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package lk.ijse.controller.runner;

import java.awt.*;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Appinitializer extends Application {
    public Appinitializer() {
    }

    private double x  = 0;
    private double y = 0;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/welcom_form.fxml"));
        Scene scene = new Scene( root,1900,1000);
       // primaryStage.initStyle(StageStyle.TRANSPARENT);

        primaryStage.centerOnScreen();
        primaryStage.setTitle("Natures_Bliss_Holidays");
        primaryStage.setScene(scene);

        primaryStage.show();




    }
}
