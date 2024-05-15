package lk.ijse.repository;

import lk.ijse.controller.databse.DBConnection;
import lk.ijse.controller.model.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleRepo {
    public static boolean saveVehicle(Vehicle vehicle) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO Vehicle VALUES(?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, vehicle.getVehicleId());
        pstm.setString(2, vehicle.getNumberOfSeats());
        pstm.setString(3, vehicle.getStatus());
        pstm.setString(4, vehicle.getEmployeeId());

        if(pstm.executeUpdate()>0){
            return true;
        }else{
            return false;
        }


    }

    public static List<Vehicle> getAllVehicles() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Vehicle";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rst = pstm.executeQuery();

        List<Vehicle> list = new ArrayList<>();
        while(rst.next()){
            list.add(new Vehicle(rst.getString(1),rst.getString(2), rst.getString(3), rst.getString(4)));
        }
        return list;



    }
    public static boolean updateVehicle(Vehicle vehicle) throws SQLException {
        String sql = "UPDATE Vehicle SET Status = ?, No_of_seats = ?, Employee_id = ? WHERE Vehicle_id = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, vehicle.getStatus());
        pstm.setString(2, vehicle.getNumberOfSeats());
        pstm.setString(3, vehicle.getEmployeeId());
        pstm.setString(4, vehicle.getVehicleId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean deleteVehicle(String vehicleId) throws SQLException {
        String sql = "DELETE FROM Vehicle WHERE Vehicle_id = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, vehicleId);

        return pstm.executeUpdate() > 0;
    }

    public static Vehicle searchVehicleById(String vehicleId) throws SQLException {
        String sql = "SELECT * FROM Vehicle WHERE Vehicle_id = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, vehicleId); // Use setString instead of setObject for String parameters

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String id = resultSet.getString(1); // Assuming the column index for vehicle ID is 1
            String status = resultSet.getString(2); // Assuming the column index for status is 2
            String numberOfSeats = resultSet.getString(3); // Assuming the column index for number of seats is 3
            String employeeId = resultSet.getString(4); // Assuming the column index for employee ID is 4

            // Use the retrieved values to create a new Vehicle object
            Vehicle vehicle = new Vehicle(id, numberOfSeats, status, employeeId);

            return vehicle;
        } else {
            return null;
        }
    }
}
