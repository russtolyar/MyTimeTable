package java.com.solvd.timetable.algorithm;


import java.com.solvd.timetable.domain.Subject;

public class SubjectComplexity {

    private Long id;
    private Subject subject;
    private int complexity;

    public SubjectComplexity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getComplexity() {
        return complexity;
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }

}

