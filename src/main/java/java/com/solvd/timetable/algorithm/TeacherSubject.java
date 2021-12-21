package java.com.solvd.timetable.algorithm;


import java.com.solvd.timetable.domain.Subject;
import java.com.solvd.timetable.domain.Teacher;

public class TeacherSubject {

    private Teacher teacher;
    private Subject subject;

    public TeacherSubject() {
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

}
