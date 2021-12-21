package java.com.solvd.timetable.persistence;

import java.com.solvd.timetable.domain.Teacher;

import java.util.List;

public interface TeacherRepository {

    List<Teacher> getTeachers();

}
