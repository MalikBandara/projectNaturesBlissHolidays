package lk.ijse.repository;


import javafx.scene.control.Alert;
import lk.ijse.controller.databse.DBConnection;
import lk.ijse.controller.model.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Reservationepo {


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


            String roomUpdateSql = "UPDATE Room SET Status = 'Booked' WHERE Room_id = ?";
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
}
