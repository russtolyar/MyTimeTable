package java.com.solvd.timetable.algorithm;

import java.com.solvd.timetable.domain.Subject;
import java.util.List;

public class SubjectArea {

    private String areaName;
    private List<Subject> subjects;

    public SubjectArea() {
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

}
