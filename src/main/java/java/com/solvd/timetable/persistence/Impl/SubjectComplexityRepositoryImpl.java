package java.com.solvd.timetable.persistence.Impl;

import java.com.solvd.timetable.algorithm.SubjectComplexity;
import java.com.solvd.timetable.persistence.MybatisSessionHolder;
import java.com.solvd.timetable.persistence.SubjectComplexityRepository;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class SubjectComplexityRepositoryImpl implements SubjectComplexityRepository {

    @Override
    public List<SubjectComplexity> getSubjectComplexities() {
        List<SubjectComplexity> subjectComplexities;
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            SubjectComplexityRepository subjectComplexityRepository = session.getMapper(SubjectComplexityRepository.class);
            subjectComplexities = subjectComplexityRepository.getSubjectComplexities();
        }
        return subjectComplexities;
    }

}
