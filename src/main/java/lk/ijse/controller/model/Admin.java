package lk.ijse.controller.model;

public class Admin {
    private String email;
    private String username;
    private String password;
    private String type;


    public Admin(String email, String username, String password, String type) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.type = type;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // toString method
    @Override
    public String toString() {
        return "Admin{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
