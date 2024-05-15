package lk.ijse.controller.model;

public class Arrange {
    private String tourId;

    private String guestId;

    public Arrange(String tourId, String guestId) {
        this.tourId = tourId;
        this.guestId = guestId;
    }


    public Arrange() {

    }

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public String toString() {
        return "Arrange{" +
                "tourId='" + tourId + '\'' +
                ", guestId='" + guestId + '\'' +
                '}';
    }

}
