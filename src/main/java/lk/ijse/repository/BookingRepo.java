package lk.ijse.repository;

import javafx.scene.control.Alert;
import lk.ijse.controller.databse.DBConnection;
import lk.ijse.controller.model.Booking;
import lk.ijse.controller.model.Package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BookingRepo {
    public static boolean saveBookingAndUpdate(String bookingId, String packageId, String identitydetails, String payid, String date,String roomID) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        connection.setAutoCommit(false);
        try {


            // Insert into booking table
            String bookingSql = "INSERT INTO Booking (Booking_id, Package_id, id,bookingDate,payId,Room_id) VALUES (?, ?, ?, ?,?,?)";
            PreparedStatement bookingPstm = connection.prepareStatement(bookingSql);
            bookingPstm.setString(1, bookingId);
            bookingPstm.setString(2, packageId);
            bookingPstm.setString(3, identitydetails);
            bookingPstm.setString(4, date);
            bookingPstm.setString(5, payid);
            bookingPstm.setString(6, roomID);


            int bookingResult = bookingPstm.executeUpdate();

            // Update Rooms table to set booked = true for the specified Room_id
            String roomUpdateSql = "UPDATE Room SET Status = 'Booked' WHERE Room_id = ?";
            PreparedStatement roomUpdatePstm = connection.prepareStatement(roomUpdateSql);
            roomUpdatePstm.setString(1, roomID);
            int roomUpdateResult = roomUpdatePstm.executeUpdate();



            String paymentUpdateSql = "UPDATE payment SET method = 'Paid' WHERE payId = ?";
            PreparedStatement paymentUpdatePstm = connection.prepareStatement(paymentUpdateSql);
            paymentUpdatePstm.setString(1, payid);
            int paymentUpdateResult = paymentUpdatePstm.executeUpdate();


            if (bookingResult > 0 && roomUpdateResult > 0 && paymentUpdateResult > 0) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);

        }
    }


    public static List<Booking> getAllBooking() throws SQLException {
        String  sql = "SELECT * FROM Booking";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<Booking> puList = new ArrayList<>();
        while (resultSet.next()){
            String Bookingid = resultSet.getString(1);
            String package_id = resultSet.getString(2);
            String identity = resultSet.getString(3);
            String payid = resultSet.getString(4);
            String roomId = resultSet.getString(5);
            String date = resultSet.getString(6);


            Booking Booking = new Booking(Bookingid, package_id, identity, payid,roomId,date);
            puList.add(Booking);
        }
        return puList;
    }
    public static boolean deleteBooking(String bookingId) throws SQLException {
        String sql = "DELETE FROM Booking WHERE Booking_id = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, bookingId);

        return pstm.executeUpdate() > 0;
    }

    public static String getRoomIdByBookingId(String bookingId) throws SQLException {
        String sql = "SELECT Room_id FROM Booking WHERE Booking_id = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, bookingId);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString("Room_id");
        } else {
            return null;
        }
    }

    public static String getLastBookingId() throws SQLException {

        String sql = "SELECT Booking_id FROM Booking ORDER BY id DESC LIMIT 1";
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

