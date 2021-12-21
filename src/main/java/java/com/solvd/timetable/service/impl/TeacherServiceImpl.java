package java.com.solvd.timetable.service.impl;


import java.com.solvd.timetable.domain.Teacher;
import java.com.solvd.timetable.persistence.Impl.TeacherRepositoryImpl;
import java.com.solvd.timetable.persistence.TeacherRepository;
import java.com.solvd.timetable.service.TeacherService;
import java.util.List;

public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl() {
        this.teacherRepository = new TeacherRepositoryImpl();
    }

    @Override
    public List<Teacher> getAll() {
        return teacherRepository.getTeachers();
    }

}
