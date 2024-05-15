package lk.ijse.controller.model;

public class Tour {
    private String tourId;
    private String name;
    private String description;
    private String location;


    public Tour() {
    }


    public Tour(String tourId, String name, String description, String location) {
        this.tourId = tourId;
        this.name = name;
        this.description = description;
        this.location = location;
    }


    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    @Override
    public String toString() {
        return "Tour{" +
                "tourId='" + tourId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
