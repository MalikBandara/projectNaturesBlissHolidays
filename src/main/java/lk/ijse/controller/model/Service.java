package lk.ijse.controller.model;

public class Service {
    private String serviceId;
    private String name;
    private String description;
    private String employeeId;


    public Service() {
    }


    public Service(String serviceId, String name, String description, String employeeId) {
        this.serviceId = serviceId;
        this.name = name;
        this.description = description;
        this.employeeId = employeeId;
    }


    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }


    @Override
    public String toString() {
        return "Service{" +
                "serviceId='" + serviceId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", employeeId='" + employeeId + '\'' +
                '}';
    }
}
