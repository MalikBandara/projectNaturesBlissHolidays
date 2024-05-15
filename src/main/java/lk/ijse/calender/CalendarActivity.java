package lk.ijse.calender;

import java.time.ZonedDateTime;

class CalendarActivity {
    private ZonedDateTime date;
    private String notes;

    public CalendarActivity(ZonedDateTime date, String notes) {
        this.date = date;
        this.notes = notes;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public String getNotes() {
        return notes;
    }
}