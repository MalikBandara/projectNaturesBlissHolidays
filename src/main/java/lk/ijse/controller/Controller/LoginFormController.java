/*
package lk.ijse.controller;



import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.controller.databse.DBConnection;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {


    @FXML
    private Button loginButton;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Label welcomBackTxt;

    @FXML
    private Label welcomBackTxt1;


    private Connection connection;
    private PreparedStatement preparedStatement;

    private ResultSet resultSet;



    public void loginOnAction (){

        String userName = username.getText();
        String pw = password.getText();

        try {
            cheackCredential(userName , pw );

        }catch (Exception e ){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }



    }

    private void cheackCredential(String userName , String pw) throws SQLException, IOException {
        String sql = "SELECT User_name , Passward FROM Admin WHERE User_name = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,userName);
        ResultSet resultSet = statement.executeQuery();


        if(userName.isEmpty() && pw.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Username or password is empty").show();
        }else {
            if (resultSet.next()) {
                String user = resultSet.getString("User_Name");
                String pww = resultSet.getString("Passward");

                if (pw.equals(pww)) {
                    NavigateToDashBord();
                } else if (userName.equals(user) || (!pw.equals(pww))) {
                    new Alert(Alert.AlertType.ERROR, "sorry! password is incorrect!").show();
                }

            } else {
                new Alert(Alert.AlertType.INFORMATION, "sorry! user id can't be find!").show();
            }
        }


    }

    private void NavigateToDashBord() throws IOException {
        Stage stage = (Stage)loginButton.getScene().getWindow();
        stage.close();
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/DashBord.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.centerOnScreen();
        stage1.setTitle("Dashboard Form");
        stage1.show();
    }

    public void ExitLogin(){
        System.exit(0);
    }




    public void linkRegistrationOnAction(javafx.event.ActionEvent actionEvent) throws IOException {



    }
}


 */