package java.com.solvd.timetable.persistence.Impl;

import java.com.solvd.timetable.algorithm.TeacherWish;
import java.com.solvd.timetable.persistence.MybatisSessionHolder;
import java.com.solvd.timetable.persistence.TeacherWishesRepository;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class TeacherWishesRepositoryImpl implements TeacherWishesRepository {

    @Override
    public List<TeacherWish> getTeacherWishes() {
        List<TeacherWish> teacherWishes;
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            TeacherWishesRepository teacherWishesRepository = session.getMapper(TeacherWishesRepository.class);
            teacherWishes = teacherWishesRepository.getTeacherWishes();
        }
        return teacherWishes;
    }

}
