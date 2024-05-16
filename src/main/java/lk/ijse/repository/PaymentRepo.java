package lk.ijse.repository;

import lk.ijse.controller.databse.DBConnection;
import lk.ijse.controller.model.Booking;
import lk.ijse.controller.model.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepo {
    public static boolean savePayment(Payment payment) throws SQLException {
        String sql = "INSERT INTO payment VALUES (?,?,?,?,?)";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, payment.getPayId());
        pstm.setObject(2, payment.getAmount());
        pstm.setObject(3, payment.getMethod());
        pstm.setObject(4, payment.getPaidDate());
        pstm.setObject(5, payment.getStatus());

        return pstm.executeUpdate() > 0;
    }

    public static List<Payment> getAllPayment() throws SQLException {
        String  sql = "SELECT * FROM payment";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<Payment> puList = new ArrayList<>();
        while (resultSet.next()){
            String PaymentID = resultSet.getString(1);
            String amount = resultSet.getString(2);
            String method = resultSet.getString(3);
            String paidDate = resultSet.getString(4);
            String status = resultSet.getString(5);


            Payment payment = new Payment(PaymentID, amount, method, paidDate, status);
            puList.add(payment);
        }
        return puList;
    }


    public static boolean updatePayment(Payment payment) throws SQLException {
        String sql = "UPDATE payment SET amount = ?, method = ?, paidDate = ?, status = ? WHERE payId = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, payment.getAmount());
        pstm.setObject(2, payment.getMethod());
        pstm.setObject(3, payment.getPaidDate());
        pstm.setObject(4, payment.getStatus());
        pstm.setObject(5, payment.getPayId());

        return pstm.executeUpdate() > 0;

    }

    public static boolean deletePayment(String payId) throws SQLException {
        String sql = "DELETE FROM payment WHERE payId = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, payId);

        return pstm.executeUpdate() > 0;
    }

    public static Payment searchPaymentById(String paymentId) throws SQLException {
        String sql = "SELECT * FROM payment WHERE payId = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, paymentId); // Use setString instead of setObject for String parameters

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String payid = resultSet.getString(1);
            String amount = resultSet.getString(2);
            String method = resultSet.getString(3);
            String paidDate = resultSet.getString(4); // Directly retrieve as double
            String Status = resultSet.getString(5); // Directly retrieve as double



            // Use the retrieved values to create a new Package object
            Payment pkg = new Payment(payid, amount, method, paidDate ,Status);

            return pkg;
        } else {
            return null;
        }
    }

    public static List<String> getId() throws SQLException {

        String sql = "SELECT PayId FROM payment";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<String> mtIDList = new ArrayList<>();
        while (resultSet.next()){
            mtIDList.add(resultSet.getString(1));
        }
        return mtIDList;
    }


    public static String getLastPaymentId() throws SQLException {
        String sql = "SELECT payId FROM payment ORDER BY payId DESC LIMIT 1";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString(1);
        } else {
            return null;
        }
    }
}


