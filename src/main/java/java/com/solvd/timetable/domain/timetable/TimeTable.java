package java.com.solvd.timetable.domain.timetable;


import java.com.solvd.timetable.domain.Grade;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TimeTable {

    private List<LessonBlock> lessonBlocks;

    public TimeTable() {
    }

    public TimeTable(int size) {
        this.lessonBlocks = Arrays.asList(new LessonBlock[size]);
    }

    public List<LessonBlock> getLessonBlocks() {
        return lessonBlocks;
    }

    public void setLessonBlocks(List<LessonBlock> lessonBlocks) {
        this.lessonBlocks = lessonBlocks;
    }

    public void printTable() {
        List<Grade> grades = gradesInTimetable(this.lessonBlocks);
        List<Day> days = daysInWeek(this.lessonBlocks);

        for (Grade grade : grades) {
            System.out.println("\n\n\n\t\t\t\t\t\t\t\t" + grade.getName());

            for (Day day : days) {
                System.out.println("\n\t\t\t\t\t\t\t\t" + day.name());

                for (LessonBlock lessonBlock : this.lessonBlocks) {
                    if (lessonBlock != null) {
                        if (lessonBlock.getGrade().getName().equals(grade.getName()) &&
                                lessonBlock.getDay().equals(day)) {
                            System.out.print("\n " + lessonBlock.getLessonNumber().getId());
                            System.out.println(lessonBlock);
                        }
                    }
                }
            }
        }
        System.out.println("\n");
    }

    private List<Day> daysInWeek(List<LessonBlock> lessonBlocks) {
        List<Day> daysInTimetable = new ArrayList<>();
        for (LessonBlock lessonBlock : lessonBlocks) {
            if (lessonBlock != null) {
                if (daysInTimetable.isEmpty()) {
                    daysInTimetable.add(lessonBlock.getDay());
                    continue;
                } else {
                    int dayCount = 0;
                    for (Day day : daysInTimetable) {
                        if (!day.name().equals(lessonBlock.getDay().name())) {
                            dayCount++;
                        }
                    }
                    if (dayCount == daysInTimetable.size()) {
                        daysInTimetable.add(lessonBlock.getDay());
                    }
                }
            }
        }
        return daysInTimetable;
    }

    private List<Grade> gradesInTimetable(List<LessonBlock> lessonBlocks) {
        List<Grade> gradesInTimetable = new ArrayList<>();
        for (LessonBlock lessonBlock : lessonBlocks) {
            if (lessonBlock != null) {
                if (gradesInTimetable.isEmpty()) {
                    gradesInTimetable.add(lessonBlock.getGrade());
                    continue;
                } else {
                    int gradeCount = 0;
                    for (Grade grade : gradesInTimetable) {
                        if (!grade.getName().equals(lessonBlock.getGrade().getName())) {
                            gradeCount++;
                        }
                    }
                    if (gradeCount == gradesInTimetable.size()) {
                        gradesInTimetable.add(lessonBlock.getGrade());
                    }
                }
            }
        }
        return gradesInTimetable;
    }

}
