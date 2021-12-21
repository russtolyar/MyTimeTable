package java.com.solvd.timetable.persistence.Impl;

import java.com.solvd.timetable.domain.timetable.LessonBlock;
import java.com.solvd.timetable.persistence.MybatisSessionHolder;
import java.com.solvd.timetable.persistence.TimeTableRepository;
import org.apache.ibatis.session.SqlSession;

import java.util.List;


public class TimeTableRepositoryImpl implements TimeTableRepository {

    @Override
    public void createTimeTable(LessonBlock lessonBlock) {
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            TimeTableRepository timeTableRepository = session.getMapper(TimeTableRepository.class);
            timeTableRepository.createTimeTable(lessonBlock);
        }
    }

    @Override
    public List<LessonBlock> getLessonBlocks() {
        List<LessonBlock> lessonBlocks;
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            TimeTableRepository timeTableRepository = session.getMapper(TimeTableRepository.class);
            lessonBlocks = timeTableRepository.getLessonBlocks();
        }
        return lessonBlocks;
    }

    @Override
    public void deleteTimeTable() {
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            TimeTableRepository timeTableRepository = session.getMapper(TimeTableRepository.class);
            timeTableRepository.deleteTimeTable();
        }
    }

}
