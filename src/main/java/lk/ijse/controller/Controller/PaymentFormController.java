package lk.ijse.controller.Controller;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.controller.model.Payment;
import lk.ijse.controller.model.tm.PaymentTm;
import lk.ijse.repository.PaymentRepo;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFields;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PaymentFormController {

    @FXML
    private TextField Amount;

    @FXML
    private Button paysave;

    @FXML
    private Button payupdate;

    @FXML
    private Button payback;

    @FXML
    private Button payclear;

    @FXML
    private Button paydelete;

    @FXML
    private Button btn;

    @FXML
    private TableColumn<?, ?> colMethod;

    @FXML
    private TableColumn<?, ?> colamount;

    @FXML
    private TableColumn<?, ?> colpaidDate;

    @FXML
    private TableColumn<?, ?> colpyment;

    @FXML
    private TableColumn<?, ?> colstatus;

    @FXML
    private TextField method;

    @FXML
    private TextField paiddate;

    @FXML
    private TextField paymentId;

    @FXML
    private TextField status;

    @FXML
    private TableView<PaymentTm> tblPayment;

    @FXML
    void PidOnAction(ActionEvent event) {
        try {
            String paymentIdText = paymentId.getText();

            Payment payment = PaymentRepo.searchPaymentById(paymentIdText);
            if (payment != null) {
                paymentId.setText(payment.getPayId());
                Amount.setText(String.valueOf(payment.getAmount()));
                method.setText(payment.getMethod());
                paiddate.setText(payment.getPaidDate());
                status.setText(payment.getStatus());
            } else {
                new Alert(Alert.AlertType.ERROR, "Payment is not found!").show();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle or log the exception as needed
            new Alert(Alert.AlertType.ERROR, "An error occurred while searching for the payment.").show();
        }
    }


    @FXML
    void backOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashBord.fxml"));
            Parent Loginpanel = loader.load();

            Stage stage = (Stage) payback.getScene().getWindow();
            stage.setTitle("Login");

            // Animate the exit of the welcome page
            Timeline exitTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(1),
                            new KeyValue(payback.translateXProperty(), -payback.getWidth(), Interpolator.EASE_BOTH))
            );
            exitTimeline.setOnFinished(e -> {
                // Animate the entrance of the service page
                double sceneWidth = stage.getScene().getWidth(); // or any other suitable width
                Loginpanel.translateXProperty().set(sceneWidth);
                stage.getScene().setRoot(Loginpanel);
                Timeline entranceTimeline = new Timeline(
                        new KeyFrame(Duration.seconds(1),
                                new KeyValue(Loginpanel.translateXProperty(), 0, Interpolator.EASE_BOTH))
                );
                entranceTimeline.play();
            });
            exitTimeline.play();
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    @FXML
    void clearOnAction(ActionEvent event) {
        clearFields();

    }
    @FXML
    void clearFields() {
        // Clear text fields
        Amount.clear();
        method.clear();
        paiddate.clear();
        paymentId.clear();
        status.clear();

        // Clear selection in table view (if applicable)
       // tblPayment.getSelectionModel().clearSelection();
    }


    @FXML
    void deleteOnAction(ActionEvent event) {

        String paymentIdText = paymentId.getText();

        if (isValied()){
            try {
                boolean isDeleted = PaymentRepo.deletePayment(paymentIdText);
                if(isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Payment deleted!").show();
                    loadAllPayment();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

        }else {
            new Alert(Alert.AlertType.ERROR, "Check your fields correctly !").show();
        }



    }
    public void initialize(){
        setCellValueFactory();
        loadAllPayment();
        paiddate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    private void loadAllPayment() {
        ObservableList<PaymentTm> obList = FXCollections.observableArrayList();

        try {
            List<Payment> PaymentList = PaymentRepo.getAllPayment();
            for(Payment Payment : PaymentList){
                PaymentTm paymentTm = new PaymentTm(
                        Payment.getPayId(),
                        Payment.getAmount(),
                        Payment.getMethod(),
                        Payment.getPaidDate(),
                        Payment.getStatus()
                );
                obList.add(paymentTm);
            }
            tblPayment.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCellValueFactory() {
        colpyment.setCellValueFactory(new PropertyValueFactory<>("payId"));
        colamount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colstatus.setCellValueFactory(new PropertyValueFactory<>("method"));
        colpaidDate.setCellValueFactory(new PropertyValueFactory<>("paidDate"));
        colMethod.setCellValueFactory(new PropertyValueFactory<>("status"));

    }

    @FXML
    void saveOnAction(ActionEvent event) {
        String id = paymentId.getText();
        String amount = Amount.getText();
        String Status = status.getText();
        String paidDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String Method = method.getText();
        if (isValied()){
            Payment payment = new Payment(id, amount, Status, paidDate, Method);

            try {
                if (id.isEmpty() || amount.isEmpty() || Status.isEmpty() || paidDate.isEmpty() || Method.isEmpty()) {
                    new Alert(Alert.AlertType.INFORMATION, "Empty Fields! Try again").show();
                } else {
                    boolean isSaved = PaymentRepo.savePayment(payment);
                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Payment saved successfully!").show();
                        loadAllPayment();
                    }
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

        }else {
            new Alert(Alert.AlertType.ERROR, "Check your fields correctly !").show();
        }





    }

    @FXML
    void updateOnAction(ActionEvent event) {
        String id = paymentId.getText();
        String amount = Amount.getText();
        String status = this.status.getText();
        String paidDate = paiddate.getText();
        String method = this.method.getText();

        if (isValied()){
            Payment payment = new Payment(id, amount, method, paidDate, status);
            try {
                boolean isUpdated = PaymentRepo.updatePayment(payment);
                if(isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Payment updated successfully!").show();
                    loadAllPayment();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

        }else {
            new Alert(Alert.AlertType.ERROR, "Check your fields correctly !").show();
        }




    }

    public void paymentidOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.PayId,paymentId);

    }

    public void amouuntOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.DOUBLE,Amount);

    }

    public void methodOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.PayMethod,status);

    }

    public void dateOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.DATE,paiddate);



    }

    public void statusOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.PayStatus,method);


    }

    public boolean isValied(){
        if (! Regex.setTextColor(TextFields.PayId,paymentId)) return false;
        if (!Regex.setTextColor(TextFields.DOUBLE,Amount)) return false;
        if (!Regex.setTextColor(TextFields.PayMethod,status)) return false;
        if (!Regex.setTextColor(TextFields.DATE,paiddate)) return false;
        if (!Regex.setTextColor(TextFields.PayStatus,method)) return false;

        return true;
    }
}
