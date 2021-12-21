package java.com.solvd.timetable.persistence.Impl;

import java.com.solvd.timetable.domain.Grade;
import java.com.solvd.timetable.persistence.GradeRepository;
import java.com.solvd.timetable.persistence.MybatisSessionHolder;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class GradeRepositoryImpl implements GradeRepository {

    @Override
    public List<Grade> getGrades() {
        List<Grade> grades;
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            GradeRepository gradeRepository = session.getMapper(GradeRepository.class);
            grades = gradeRepository.getGrades();
        }
        return grades;
    }

}
