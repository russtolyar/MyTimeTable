package java.com.solvd.timetable.persistence;

import java.com.solvd.timetable.algorithm.SubjectArea;

import java.util.List;

public interface SubjectAreaRepository {

    List<SubjectArea> getSubjectAreas();

}
