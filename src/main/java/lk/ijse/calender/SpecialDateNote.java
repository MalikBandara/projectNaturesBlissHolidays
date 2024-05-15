package lk.ijse.calender;

import java.time.ZonedDateTime;

public class SpecialDateNote {
    private ZonedDateTime date;
    private String note;

    public SpecialDateNote(ZonedDateTime date, String note) {
        this.date = date;
        this.note = note;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public String getNote() {
        return note;
    }

    @Override
    public String toString() {
        return "SpecialDateNote{" +
                "date=" + date +
                ", note='" + note + '\'' +
                '}';
    }
}
