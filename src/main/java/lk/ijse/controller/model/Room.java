package lk.ijse.controller.model;

public class Room {
    private String roomId;
    private String roomNumber;
    private String type;
    private String rate;
    private String status;


    public Room() {
    }


    public Room(String roomId, String roomNumber, String type, String rate, String status) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.type = type;
        this.rate = rate;
        this.status = status;
    }


    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Room{" +
                "roomId='" + roomId + '\'' +
                ", roomNumber=" + roomNumber +
                ", type='" + type + '\'' +
                ", rate=" + rate +
                ", status='" + status + '\'' +
                '}';
    }
}
