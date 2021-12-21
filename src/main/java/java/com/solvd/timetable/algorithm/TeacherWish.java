package java.com.solvd.timetable.algorithm;


import java.com.solvd.timetable.domain.Teacher;
import java.com.solvd.timetable.domain.timetable.LessonNumber;
import java.util.List;

public class TeacherWish {

    private Long id;
    private Teacher teacher;
    private List<LessonNumber> wishes;

    public TeacherWish() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<LessonNumber> getWishes() {
        return wishes;
    }

    public void setWishes(List<LessonNumber> wishes) {
        this.wishes = wishes;
    }

}
