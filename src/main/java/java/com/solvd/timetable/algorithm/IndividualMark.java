package java.com.solvd.timetable.algorithm;

import java.com.solvd.timetable.domain.timetable.LessonBlock;
import java.util.List;

public class IndividualMark {

    private List<LessonBlock> individual;
    private int mark;

    public IndividualMark() {
    }

    public List<LessonBlock> getIndividual() {
        return individual;
    }

    public void setIndividual(List<LessonBlock> individual) {
        this.individual = individual;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}
