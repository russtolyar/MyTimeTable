package java.com.solvd.timetable.persistence.Impl;

import java.com.solvd.timetable.domain.Teacher;
import java.com.solvd.timetable.persistence.MybatisSessionHolder;
import java.com.solvd.timetable.persistence.TeacherRepository;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class TeacherRepositoryImpl implements TeacherRepository {

    @Override
    public List<Teacher> getTeachers() {
        List<Teacher> teachers;
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            TeacherRepository teacherRepository = session.getMapper(TeacherRepository.class);
            teachers = teacherRepository.getTeachers();
        }
        return teachers;
    }

}
