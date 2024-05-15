package lk.ijse.controller.model;

public class Employee {
    private String employeeId;
    private String name;
    private String address;
    private double salary;
    private String type;
    private String availability;
    private String roomId;
    private String identityDetails;

    // Constructor
    public Employee(String employeeId, String name, String address, double salary, String type, String availability, String roomId) {
        this.employeeId = employeeId;
        this.name = name;
        this.address = address;
        this.salary = salary;
        this.type = type;
        this.availability = availability;
        this.roomId = roomId;

    }

    // Getters
    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getSalary() {
        return salary;
    }

    public String getType() {
        return type;
    }

    public String getAvailability() {
        return availability;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getIdentityDetails() {
        return identityDetails;
    }

    // Setters
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setIdentityDetails(String identityDetails) {
        this.identityDetails = identityDetails;
    }

    // toString method
    @Override
    public String toString() {
        return "Employee{" +
                "employeeId='" + employeeId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", salary=" + salary +
                ", type='" + type + '\'' +
                ", availability='" + availability + '\'' +
                ", roomId='" + roomId + '\'' +
                ", identityDetails='" + identityDetails + '\'' +
                '}';
    }
}
