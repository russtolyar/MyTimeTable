package java.com.solvd.timetable.persistence;

import java.com.solvd.timetable.algorithm.SubjectPosition;

import java.util.List;

public interface SubjectPositionRepository {

    List<SubjectPosition> getSubjectPositions();

}
