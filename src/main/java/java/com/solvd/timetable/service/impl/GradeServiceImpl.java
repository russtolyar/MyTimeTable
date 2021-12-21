package java.com.solvd.timetable.service.impl;


import java.com.solvd.timetable.domain.Grade;
import java.com.solvd.timetable.persistence.GradeRepository;
import java.com.solvd.timetable.persistence.Impl.GradeRepositoryImpl;
import java.com.solvd.timetable.service.GradeService;
import java.util.List;

public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;

    public GradeServiceImpl() {
        this.gradeRepository = new GradeRepositoryImpl();
    }

    @Override
    public List<Grade> getAll() {
        return gradeRepository.getGrades();
    }

}
