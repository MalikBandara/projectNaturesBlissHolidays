package lk.ijse.repository;

import lk.ijse.controller.databse.DBConnection;
import lk.ijse.controller.model.Booking;
import lk.ijse.controller.model.Package;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PackageRepo {
    public static boolean savePackage(Package Package) throws SQLException {
        String sql = "INSERT INTO Package VALUES (?,?,?,?)";

        Connection connection = DBConnection.getInstance().getConnection();;
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,Package.getPackageId());
        pstm.setObject(2,Package.getName());
        pstm.setObject(3,Package.getType());
        pstm.setObject(4,Package.getPrice());

        return pstm.executeUpdate() > 0;
    }


    public static boolean updatePackage(Package Package) throws SQLException {

        String sql = "UPDATE Package SET Name = ?,Type = ?,Price = ? WHERE Package_id = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,Package.getName());
        pstm.setObject(2,Package.getType());
        pstm.setObject(3,Package.getPrice());
        pstm.setObject(4,Package.getPackageId());

        return pstm.executeUpdate() > 0;

    }

    public static boolean deletePackage(String packageId) throws SQLException {
        String sql = "DELETE FROM Package WHERE Package_id = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,packageId);

        return pstm.executeUpdate() > 0;
    }

    public static Package searchPackageById(String packageID) throws SQLException {
        String sql = "SELECT * FROM Package WHERE Package_id = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, packageID); // Use setString instead of setObject for String parameters

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String packageId = resultSet.getString(1);
            String packageName = resultSet.getString(2);
            String description = resultSet.getString(3);
            double price = resultSet.getDouble(4); // Directly retrieve as double

            // Use the retrieved values to create a new Package object
            Package pkg = new Package(packageId, packageName, description, price);

            return pkg;
        } else {
            return null;
        }
    }

    public static List<Package> getAllpackages() throws SQLException {

        String  sql = "SELECT * FROM Package";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<Package> puList = new ArrayList<>();
        while (resultSet.next()){
            String PackageID = resultSet.getString(1);
            String PackageName = resultSet.getString(2);
            String Type = resultSet.getString(3);
            double Price = Double.parseDouble(resultSet.getString(4));

            Package Package = new Package(PackageID, PackageName, Type, Price);
            puList.add(Package);
        }
        return puList;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT Package_id FROM Package";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<String> mtIDList = new ArrayList<>();
        while (resultSet.next()){
            mtIDList.add(resultSet.getString(1));
        }
        return mtIDList;
    }

    public static boolean updatePackages(List<Booking> bookingList) {

        return true;
    }


    public static String getLastPackageId() throws SQLException {
        String sql = "SELECT Package_id FROM Package ORDER BY Package_id DESC LIMIT 1";
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
