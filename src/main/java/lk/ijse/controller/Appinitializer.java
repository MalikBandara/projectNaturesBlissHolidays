//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package lk.ijse.controller;

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

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/LoginPage.fxml"));
        Parent load = (Parent)loader.load();
        primaryStage.initStyle(StageStyle.DECORATED);
        Scene scene = new Scene(load, 1080.0, 707.0);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
