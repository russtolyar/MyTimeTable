package java.com.solvd.timetable.persistence.Impl;

import java.com.solvd.timetable.algorithm.DayComplexity;
import java.com.solvd.timetable.persistence.DayComplexityRepository;
import java.com.solvd.timetable.persistence.MybatisSessionHolder;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class DayComplexityRepositoryImpl implements DayComplexityRepository {

    @Override
    public List<DayComplexity> getDayComplexities() {
        List<DayComplexity> dayComplexities;
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            DayComplexityRepository dayComplexityRepository = session.getMapper(DayComplexityRepository.class);
            dayComplexities = dayComplexityRepository.getDayComplexities();
        }
        return dayComplexities;
    }

}
