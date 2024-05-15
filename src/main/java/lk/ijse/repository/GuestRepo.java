package lk.ijse.repository;

import javafx.scene.control.Alert;
import lk.ijse.controller.databse.DBConnection;
import lk.ijse.controller.model.Booking;
import lk.ijse.controller.model.Guest;
import lk.ijse.controller.model.Package;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuestRepo {
    public static boolean saveGuest(Guest guest) throws SQLException {
        try{
            Connection connection = DBConnection.getInstance().getConnection();
            //String sql="INSERT INTO tourist VALUES (?,?, AES_ENCRYPT(?, '43ad-8c7a-603b'),?)";
            String sql = "INSERT INTO Guest VALUES(?,?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, guest.getIdentityDetails());
            pstm.setString(2, guest.getName());
            pstm.setString(3, guest.getPassword());
            pstm.setString(4, guest.getEmail());
            if(pstm.executeUpdate()>0){
                return true;
            }else{
                return false;
            }
        }catch (Exception e ){

        }
        return false;
    }
    public static String getTouristEmailFromId(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT Email FROM Guest WHERE identityDetails=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet rs = pstm.executeQuery();
        if(rs.next()){
            return rs.getString("Email");
        }else{
            return null;
        }
    }
    public static Guest getGuest(String id) throws SQLException {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            //String sql="SELECT identityDetails,name, CONVERT(AES_DECRYPT(password,'43ad-8c7a-603b') USING utf8)AS decrypted_password FROM tourist WHERE identityDetails=?";
            String sql = "SELECT * FROM Guest WHERE identityDetails=?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, id);

            ResultSet rs = pstm.executeQuery();
            Guest dto = new Guest();
            if(rs.next()){
                dto.setIdentityDetails(rs.getString("identityDetails"));
                dto.setName(rs.getString("name"));
                dto.setPassword(rs.getString("password"));
                return dto;
            } else {
                return null;
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return null;
    }

    public static String getGuestEmailFormID(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT Email FROM Guest WHERE identityDetails=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet rs = pstm.executeQuery();
        if(rs.next()){
            return rs.getString("email");
        }else{
            return null;
        }
    }
    public static boolean changePassword(String id, String password) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "UPDATE Guest SET password= ? WHERE identityDetails =?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, password);
        pstm.setString(2, id);
        return pstm.executeUpdate() > 0;
    }

    public static Guest searchGuestById(String identityDetails) {
        String sql = "SELECT identityDetails, name, password, Email FROM Guest WHERE identityDetails = ?";
        Guest guest = null;

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, identityDetails);
            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()) {
                String guestId = resultSet.getString("identityDetails");
                String guestName = resultSet.getString("name");
                String guestPassword = resultSet.getString("password");
                String guestEmail = resultSet.getString("Email");

                guest = new Guest(guestId, guestName, guestPassword, guestEmail);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
            new Alert(Alert.AlertType.ERROR, "An error occurred while searching for the guest.").show();
        }

        return guest;
    }

    public static List<String> getID() throws SQLException {
        String sql = "SELECT identityDetails FROM Guest";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<String> mtIDList = new ArrayList<>();
        while (resultSet.next()){
            mtIDList.add(resultSet.getString(1));
        }
        return mtIDList;

    }

    public static boolean updateGuest(List<Booking> bookingList) {

        return true;
    }

    public static Guest searchGuesttById(String identity) throws SQLException {
        String sql = "SELECT * FROM Guest WHERE identityDetails = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, identity); // Use setString instead of setObject for String parameters

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String identityDetails = resultSet.getString(1);
            String name = resultSet.getString(2);
            String password = resultSet.getString(3);
            String email = resultSet.getString(4);

            // Use the retrieved values to create a new Guest object
            Guest guest = new Guest(identityDetails, name, password, email);

            return guest;
        } else {
            return null;
        }

    }
}
