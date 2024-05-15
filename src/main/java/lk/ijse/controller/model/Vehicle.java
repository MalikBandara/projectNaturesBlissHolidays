package lk.ijse.controller.model;

public class Vehicle {
    private String vehicleId;
    private String numberOfSeats;
    private String status;
    private String employeeId;


    public Vehicle() {
    }


    public Vehicle(String vehicleId, String numberOfSeats, String status, String employeeId) {
        this.vehicleId = vehicleId;
        this.numberOfSeats = numberOfSeats;
        this.status = status;
        this.employeeId = employeeId;
    }


    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(String numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }


    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId='" + vehicleId + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                ", status='" + status + '\'' +
                ", employeeId='" + employeeId + '\'' +
                '}';
    }
}
