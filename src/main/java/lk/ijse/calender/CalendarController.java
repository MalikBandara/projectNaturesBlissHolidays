package lk.ijse.calender;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.*;

public class CalendarController implements Initializable {

    ZonedDateTime dateFocus;
    ZonedDateTime today;

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;

    @FXML
    private TextField dayInput;

    @FXML
    private TextField noteInput;

    @FXML
    private Button saveNoteButton;

    private Map<Integer, String> specialNotes = new HashMap<>();

    private NoteData noteData;
    private static final String NOTE_DATA_FILE = "noteData.ser";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize noteData by loading from file if it exists
        try {
            noteData = NoteData.loadFromFile(NOTE_DATA_FILE);
        } catch (IOException | ClassNotFoundException e) {
            noteData = new NoteData();
        }

        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        drawCalendar();

        saveNoteButton.setOnAction(this::saveNote);

        // Save notes to file when application closes
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                noteData.saveToFile(NOTE_DATA_FILE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    private void drawCalendar() {
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(dateFocus.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH));

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        Map<Integer, List<CalendarActivity>> calendarActivityMap = getCalendarActivitiesMonth(dateFocus);

        int monthMaxDate = dateFocus.getMonth().length(dateFocus.toLocalDate().isLeapYear());
        int dateOffset = dateFocus.withDayOfMonth(1).getDayOfWeek().getValue() % 7;

        int numRows = (int) Math.ceil((monthMaxDate + dateOffset) / 7.0); // Calculate the number of rows needed
        int currentDate = 1;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth = (calendarWidth / 7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight / numRows) - strokeWidth - spacingV; // Adjusted to fit dynamic number of rows
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                if (i == 0 && j < dateOffset) {
                    // Empty cells before the start of the month
                    calendar.getChildren().add(stackPane);
                    continue;
                }

                if (currentDate > monthMaxDate) {
                    // Empty cells after the end of the month
                    calendar.getChildren().add(stackPane);
                    continue;
                }

                Text date = new Text(String.valueOf(currentDate));
                double textTranslationY = -(rectangleHeight / 2) * 0.75;
                date.setTranslateY(textTranslationY);
                stackPane.getChildren().add(date);

                List<CalendarActivity> calendarActivities = calendarActivityMap.get(currentDate);
                if (calendarActivities != null) {
                    createCalendarActivity(calendarActivities, rectangleHeight, rectangleWidth, stackPane);
                }

                if (specialNotes.containsKey(currentDate)) {
                    Text noteText = new Text(specialNotes.get(currentDate));
                    stackPane.getChildren().add(noteText);
                }

                if (today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate) {
                    rectangle.setStroke(Color.BLUE);
                }

                currentDate++;
                calendar.getChildren().add(stackPane);
            }
        }
    }
    private void createCalendarActivity(List<CalendarActivity> calendarActivities, double rectangleHeight, double rectangleWidth, StackPane stackPane) {
        VBox calendarActivityBox = new VBox();
        for (int k = 0; k < calendarActivities.size(); k++) {
            if (k >= 2) {
                Text moreActivities = new Text("...");
                calendarActivityBox.getChildren().add(moreActivities);
                moreActivities.setOnMouseClicked(mouseEvent -> {
                    System.out.println(calendarActivities);
                });
                break;
            }
            Text text = new Text(calendarActivities.get(k).getNotes());
            calendarActivityBox.getChildren().add(text);
            text.setOnMouseClicked(mouseEvent -> {
                System.out.println(text.getText());
            });
        }
        calendarActivityBox.setTranslateY((rectangleHeight / 2) * 0.20);
        calendarActivityBox.setMaxWidth(rectangleWidth * 0.8);
        calendarActivityBox.setMaxHeight(rectangleHeight * 0.65);
        calendarActivityBox.setStyle("-fx-background-color:#0ab9ea");
        stackPane.getChildren().add(calendarActivityBox);
    }

    private Map<Integer, List<CalendarActivity>> createCalendarMap(List<CalendarActivity> calendarActivities) {
        Map<Integer, List<CalendarActivity>> calendarActivityMap = new HashMap<>();

        for (CalendarActivity activity : calendarActivities) {
            int activityDate = activity.getDate().getDayOfMonth();
            if (!calendarActivityMap.containsKey(activityDate)) {
                calendarActivityMap.put(activityDate, new ArrayList<>(List.of(activity)));
            } else {
                List<CalendarActivity> oldListByDate = calendarActivityMap.get(activityDate);
                oldListByDate.add(activity);
            }
        }
        return calendarActivityMap;
    }

    private Map<Integer, List<CalendarActivity>> getCalendarActivitiesMonth(ZonedDateTime dateFocus) {
        List<CalendarActivity> calendarActivities = new ArrayList<>();
        int year = dateFocus.getYear();
        int month = dateFocus.getMonth().getValue();

        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            ZonedDateTime time = ZonedDateTime.of(year, month, random.nextInt(dateFocus.getMonth().length(dateFocus.toLocalDate().isLeapYear())) + 1, 0, 0, 0, 0, dateFocus.getZone());
            calendarActivities.add(new CalendarActivity(time, ""));
        }

        return createCalendarMap(calendarActivities);
    }

    private void saveNote(ActionEvent event) {
        try {
            int day = Integer.parseInt(dayInput.getText());
            String note = noteInput.getText();
            specialNotes.put(day, note);
            calendar.getChildren().clear();
            drawCalendar();
        } catch (NumberFormatException e) {
            System.out.println("Invalid day input. Please enter a valid day number.");
        }
    }
}
