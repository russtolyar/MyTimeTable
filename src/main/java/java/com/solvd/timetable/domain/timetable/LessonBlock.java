package java.com.solvd.timetable.domain.timetable;

import java.com.solvd.timetable.domain.Grade;
import java.com.solvd.timetable.domain.Room;
import java.com.solvd.timetable.domain.Subject;
import java.com.solvd.timetable.domain.Teacher;

public class LessonBlock {

    private Long id;
    private Day day;
    private LessonNumber lessonNumber;
    private Grade grade;
    private Subject subject;
    private Teacher teacher;
    private Room room;

    public LessonBlock() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public LessonNumber getLessonNumber() {
        return lessonNumber;
    }

    public void setLessonNumber(LessonNumber lessonNumber) {
        this.lessonNumber = lessonNumber;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return

                " || " + lessonNumber.getTime() + " || " +
                        subject + " || " +
                        room + " || " +
                        teacher;
    }
}
