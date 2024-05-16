package lk.ijse.repository;

import javafx.scene.control.Alert;
import lk.ijse.controller.databse.DBConnection;
import lk.ijse.controller.model.Booking;
import lk.ijse.controller.model.Room;
import lk.ijse.controller.model.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomRepo {
    public static boolean saveRoom(Room room) throws SQLException {
        String sql = "INSERT INTO Room (Room_id, Room_number, Type, Rate, Status) VALUES (?,?,?,?,?)";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, room.getRoomId());
        pstm.setObject(2, room.getRoomNumber());
        pstm.setObject(3, room.getType());
        pstm.setObject(4, room.getRate());
        pstm.setObject(5, room.getStatus());

        return pstm.executeUpdate() > 0;
    }
    public static boolean updateRoomStatus(String roomId, String status) throws SQLException {
        String sql = "UPDATE Room SET status = ? WHERE Room_id = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, status);
        pstm.setString(2, roomId);

        return pstm.executeUpdate() > 0;
    }

    public static boolean updateRoom(Room room) throws SQLException {
        String sql = "UPDATE Room SET Room_number = ?, Type = ?, Rate = ?, Status = ? WHERE Room_id = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, room.getRoomNumber());
        pstm.setObject(2, room.getType());
        pstm.setObject(3, room.getRate());
        pstm.setObject(4, room.getStatus());
        pstm.setObject(5, room.getRoomId());

        return pstm.executeUpdate() > 0;
    }


    public static Room searchRoomById(String roomId) throws SQLException {

        String sql = "SELECT * FROM Room WHERE Room_id = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, roomId); // Use setString instead of setObject for String parameters

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String roomID = resultSet.getString(1);
            String roomNumber = resultSet.getString(2);
            String type = resultSet.getString(3);
            String rate = resultSet.getString(4);
            String status = resultSet.getString(5);

            // Use the retrieved values to create a new Room object
            Room room = new Room(roomID, roomNumber, type, rate, status);

            return room;
        } else {
            return null;
        }
    }
    public static boolean deleteRoom(String roomId) throws SQLException {
        String sql = "DELETE FROM Room WHERE Room_id = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, roomId);

        return pstm.executeUpdate() > 0;
    }


    public static List<Room> getAll() throws SQLException {
        List<Room> list = new ArrayList<>();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM Room";
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rst = pstm.executeQuery();


            while(rst.next()){
                list.add(new Room(rst.getString(1),rst.getString(2), rst.getString(3), rst.getString(4),rst.getString(5)));
            }
            return list;
        }catch (Exception e ){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return list;

    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT Room_id FROM Room";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<String> mtIDList = new ArrayList<>();
        while (resultSet.next()){
            mtIDList.add(resultSet.getString(1));
        }
        return mtIDList;
    }

    public static boolean updateRooms(List<Booking> odList) {

    return  false;
    }
}
