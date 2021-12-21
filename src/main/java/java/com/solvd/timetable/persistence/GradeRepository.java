package java.com.solvd.timetable.persistence;

import java.com.solvd.timetable.domain.Grade;

import java.util.List;

public interface GradeRepository {

    List<Grade> getGrades();

}
