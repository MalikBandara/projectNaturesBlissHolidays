package lk.ijse.calender;

import java.io.*;
import java.util.*;

public class NoteData implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<Integer, String> specialNotes;

    public NoteData() {
        specialNotes = new HashMap<>();
    }

    public Map<Integer, String> getSpecialNotes() {
        return specialNotes;
    }

    public void setSpecialNotes(Map<Integer, String> specialNotes) {
        this.specialNotes = specialNotes;
    }

    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(specialNotes);
        }
    }

    public static NoteData loadFromFile(String filename) throws IOException, ClassNotFoundException {
        NoteData noteData;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            noteData = new NoteData();
            noteData.setSpecialNotes((Map<Integer, String>) in.readObject());
        }
        return noteData;
    }
}
