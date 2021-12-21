package java.com.solvd.timetable.persistence;

import java.com.solvd.timetable.algorithm.DayComplexity;

import java.util.List;

public interface DayComplexityRepository {

    List<DayComplexity> getDayComplexities();

}
