package java.com.solvd.timetable.persistence;

import java.com.solvd.timetable.domain.curriculum.GradeCurriculum;

import java.util.List;

public interface GradeCurriculumRepository {

    List<GradeCurriculum> getGradeCurricula();

}
