package java.com.solvd.timetable.algorithm;

import java.com.solvd.timetable.domain.timetable.Day;

public class DayComplexity {

    private Long id;
    private Day day;
    private int dayComplexity;

    public DayComplexity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public int getDayComplexity() {
        return dayComplexity;
    }

    public void setDayComplexity(int dayComplexity) {
        this.dayComplexity = dayComplexity;
    }

}

