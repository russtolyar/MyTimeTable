package java.com.solvd.timetable.persistence.Impl;

import java.com.solvd.timetable.domain.curriculum.GradeCurriculum;
import java.com.solvd.timetable.persistence.GradeCurriculumRepository;
import java.com.solvd.timetable.persistence.MybatisSessionHolder;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class GradeCurriculumRepositoryImpl implements GradeCurriculumRepository {

    @Override
    public List<GradeCurriculum> getGradeCurricula() {
        List<GradeCurriculum> gradeCurricula;
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            GradeCurriculumRepository gradeCurriculumRepository = session.getMapper(GradeCurriculumRepository.class);
            gradeCurricula = gradeCurriculumRepository.getGradeCurricula();
        }
        return gradeCurricula;
    }

}
