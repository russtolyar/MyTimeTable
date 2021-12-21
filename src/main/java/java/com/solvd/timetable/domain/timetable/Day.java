package java.com.solvd.timetable.domain.timetable;

public enum Day {

    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;

    private final long id;

    Day() {
        this.id = this.ordinal() + 1;
    }

    public long getId() {
        return id;
    }

}
