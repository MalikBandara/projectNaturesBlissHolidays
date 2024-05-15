package lk.ijse.controller.model;

public class Booking {
    private String bookingId;
    private String packageId;
    private String identityDetails;
    private String bookingDate;
    private String payId;
    private String roomId;

    // Constructors
    public Booking() {
    }

    public Booking(String bookingId, String packageId, String identityDetails, String bookingDate, String payId, String roomId) {
        this.bookingId = bookingId;
        this.packageId = packageId;
        this.identityDetails = identityDetails;
        this.bookingDate = bookingDate;
        this.payId = payId;
        this.roomId = roomId;
    }

    // Getters and setters
    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getIdentityDetails() {
        return identityDetails;
    }

    public void setIdentityDetails(String identityDetails) {
        this.identityDetails = identityDetails;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    // toString method
    @Override
    public String toString() {
        return "Booking{" +
                "bookingId='" + bookingId + '\'' +
                ", packageId='" + packageId + '\'' +
                ", identityDetails='" + identityDetails + '\'' +
                ", bookingDate=" + bookingDate +
                ", payId='" + payId + '\'' +
                ", roomId='" + roomId + '\'' +
                '}';
    }
}
