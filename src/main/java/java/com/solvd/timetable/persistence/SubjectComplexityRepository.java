package java.com.solvd.timetable.persistence;

import java.com.solvd.timetable.algorithm.SubjectComplexity;

import java.util.List;

public interface SubjectComplexityRepository {

    List<SubjectComplexity> getSubjectComplexities();

}
