package java.com.solvd.timetable.domain.curriculum;

import java.com.solvd.timetable.domain.Grade;
import java.util.List;

public class GradeCurriculum {

    private Long id;
    private Grade grade;
    private List<SubjectCount> countSubjects;

    public GradeCurriculum() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public List<SubjectCount> getCountSubjects() {
        return countSubjects;
    }

    public void setCountSubjects(List<SubjectCount> countSubjects) {
        this.countSubjects = countSubjects;
    }

}
