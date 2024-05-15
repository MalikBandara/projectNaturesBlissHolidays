package lk.ijse.controller.model;

public class Reservation {
    private String id;
    private String guestName;
    private String checkInDate;
    private String checkOutDate;
    private String roomType;
    private String numGuests;
    private String roomId;

    // Constructors
    public Reservation() {
    }

    public Reservation(String id, String guestName, String checkInDate, String checkOutDate, String roomType, String numGuests, String roomId) {
        this.id = id;
        this.guestName = guestName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.roomType = roomType;
        this.numGuests = numGuests;
        this.roomId = roomId;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getNumGuests() {
        return numGuests;
    }

    public void setNumGuests(String numGuests) {
        this.numGuests = numGuests;
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
        return "Reservation{" +
                "id='" + id + '\'' +
                ", guestName='" + guestName + '\'' +
                ", checkInDate='" + checkInDate + '\'' +
                ", checkOutDate='" + checkOutDate + '\'' +
                ", roomType='" + roomType + '\'' +
                ", numGuests=" + numGuests +
                ", roomId='" + roomId + '\'' +
                '}';
    }
}