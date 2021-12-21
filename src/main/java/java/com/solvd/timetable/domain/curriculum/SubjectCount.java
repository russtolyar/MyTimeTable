package java.com.solvd.timetable.domain.curriculum;

import java.com.solvd.timetable.domain.Subject;

public class SubjectCount {

    private Subject subject;
    private int count;

    public SubjectCount() {
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}