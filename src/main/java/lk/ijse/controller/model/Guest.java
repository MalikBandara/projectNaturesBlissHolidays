package lk.ijse.controller.model;

public class Guest {
    private String identityDetails;
    private String name;
    private String password;
    private String email;

    // Constructor
    public Guest(String identityDetails, String name, String password, String email) {
        this.identityDetails = identityDetails;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public Guest() {

    }

    // Getter and setter methods
    public String getIdentityDetails() {
        return identityDetails;
    }

    public void setIdentityDetails(String identityDetails) {
        this.identityDetails = identityDetails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Guest{" +
                "identityDetails='" + identityDetails + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
