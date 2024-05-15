package lk.ijse.repository;

import lk.ijse.controller.databse.DBConnection;
import lk.ijse.controller.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepo {

    public static boolean saveEmployee(Employee employee) throws SQLException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO Employee VALUES(?,?,?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, employee.getEmployeeId());
        pstm.setString(2, employee.getName());
        pstm.setString(3, employee.getAddress());
        pstm.setDouble(4, employee.getSalary());
        pstm.setString(5, employee.getType());
        pstm.setString(6, employee.getAvailability());
        pstm.setString(7, employee.getRoomId());
        pstm.setString(8, "1234567890");
        int rowsAffected = pstm.executeUpdate();
        return rowsAffected > 0;
    }


    public static boolean updateEmployee(Employee employee) throws SQLException {

        String sql = "UPDATE Employee SET Name = ?, Address = ?, Salary = ?, type = ?, availability = ?, Room_id = ? WHERE Employee_id = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, employee.getName());
        pstm.setObject(2, employee.getAddress());
        pstm.setObject(3, employee.getSalary());
        pstm.setObject(4, employee.getType());
        pstm.setObject(5, employee.getAvailability());
        pstm.setObject(6, employee.getRoomId());
        pstm.setObject(7, employee.getEmployeeId());

        return pstm.executeUpdate() > 0;

    }

    public static boolean deleteEmployee(String employeeId) throws SQLException {

        String sql = "DELETE FROM Employee WHERE Employee_id = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, employeeId);
        int rowsAffected = pstm.executeUpdate();
        return rowsAffected > 0;
    }

    public static Employee searchEmployeeById(String employeeId) throws SQLException {
        String sql = "SELECT * FROM Employee WHERE Employee_id = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, employeeId); // Use setString instead of setObject for String parameters

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String empId = resultSet.getString(1);
            String empName = resultSet.getString(2);
            String empAddress = resultSet.getString(3);
            double empSalary = resultSet.getDouble(4);
            String empType = resultSet.getString(5);
            String empAvailability = resultSet.getString(6);
            String rooID = resultSet.getString(7);

            // Create a new Employee object with retrieved values
            Employee employee = new Employee(empId, empName, empAddress, empSalary, empType, empAvailability, rooID);

            return employee;
        } else {
            return null;
        }
    }
    public static List<Employee> getAllEmployees() throws SQLException {
        String sql = "SELECT * FROM Employee";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<Employee> employeeList = new ArrayList<>();
        while (resultSet.next()) {
            String employeeId = resultSet.getString(1);
            String employeeName = resultSet.getString(2);
            String employeeAddress = resultSet.getString(3);
            double employeeSalary = resultSet.getDouble(4);
            String employeeType = resultSet.getString(5);
            String employeeAvailability = resultSet.getString(6);
            String rooID = resultSet.getString(7);


            Employee employee = new Employee(employeeId, employeeName, employeeAddress, employeeSalary, employeeType, employeeAvailability, rooID);
            employeeList.add(employee);
        }
        return employeeList;
    }

    public static List<String> getAllEmployeeIds() throws SQLException {
        String sql = "SELECT Employee_id FROM Employee";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<String> employeeIdList = new ArrayList<>();
        while (resultSet.next()){
            employeeIdList.add(resultSet.getString(1));
        }
        return employeeIdList;
    }



}
