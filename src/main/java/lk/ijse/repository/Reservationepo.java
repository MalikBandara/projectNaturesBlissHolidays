package lk.ijse.repository;


import javafx.scene.control.Alert;
import lk.ijse.controller.databse.DBConnection;
import lk.ijse.controller.model.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Reservationepo {



    private static String getRoomIdForReservation(String reservationId) throws SQLException {
        String sql = "SELECT Room_id FROM Reservation WHERE id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, reservationId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("Room_id");
                } else {
                    throw new SQLException("Room ID not found for reservation: " + reservationId);
                }
            }
        }
    }





    public static boolean saveReservation(Reservation reservation) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {

            connection.setAutoCommit(false);


            String sql = "INSERT INTO Reservation VALUES(?,?,?,?,?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, reservation.getId());
            pstm.setString(2, reservation.getGuestName());
            pstm.setString(3, reservation.getCheckInDate());
            pstm.setString(4, reservation.getCheckOutDate());
            pstm.setString(5, reservation.getRoomType());
            pstm.setString(6, reservation.getNumGuests());
            pstm.setString(7, reservation.getRoomId());

            int rowsAffected = pstm.executeUpdate();
            boolean b =  rowsAffected > 0;


            String roomUpdateSql = "UPDATE Room SET Status = 'Reservation Booked' WHERE Room_id = ?";
            PreparedStatement roomUpdatePstm = connection.prepareStatement(roomUpdateSql);
            roomUpdatePstm.setString(1, reservation.getRoomId());
            roomUpdatePstm.executeUpdate();



            if (b) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);

        }
    }
    public static String getLastReservationId() throws SQLException {
        String sql = "SELECT id FROM Reservation ORDER BY id DESC LIMIT 1";
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
