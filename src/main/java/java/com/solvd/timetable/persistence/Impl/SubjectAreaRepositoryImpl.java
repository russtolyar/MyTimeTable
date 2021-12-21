package java.com.solvd.timetable.persistence.Impl;

import java.com.solvd.timetable.algorithm.SubjectArea;
import java.com.solvd.timetable.persistence.MybatisSessionHolder;
import java.com.solvd.timetable.persistence.SubjectAreaRepository;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class SubjectAreaRepositoryImpl implements SubjectAreaRepository {

    @Override
    public List<SubjectArea> getSubjectAreas() {
        List<SubjectArea> subjectAreas;
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            SubjectAreaRepository subjectAreaRepository = session.getMapper(SubjectAreaRepository.class);
            subjectAreas = subjectAreaRepository.getSubjectAreas();
        }
        return subjectAreas;
    }

}
