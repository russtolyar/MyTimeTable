package java.com.solvd.timetable.domain.timetable;

public enum LessonNumber {

    FIRST("8:00 - 8:45"), SECOND("8:55 - 9:40"), THIRD("9:50 - 10:35"),
    FOURTH("10:55 - 11:40"), FIFTH("11:50 - 12:35"), SIXTH("12:45 - 13:30"),
    SEVENTH("13:40 - 14:25");

    private final long id;
    private final String time;

    LessonNumber(String time) {
        this.id = this.ordinal() + 1;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

}
