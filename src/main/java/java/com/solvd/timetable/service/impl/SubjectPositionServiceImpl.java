package java.com.solvd.timetable.service.impl;


import java.com.solvd.timetable.algorithm.SubjectPosition;
import java.com.solvd.timetable.persistence.Impl.SubjectPositionsRepositoryImpl;
import java.com.solvd.timetable.persistence.SubjectPositionRepository;
import java.com.solvd.timetable.service.SubjectPositionService;
import java.util.List;

public class SubjectPositionServiceImpl implements SubjectPositionService {

    private final SubjectPositionRepository subjectPositionRepository;

    public SubjectPositionServiceImpl() {
        this.subjectPositionRepository = new SubjectPositionsRepositoryImpl();
    }

    @Override
    public List<SubjectPosition> getAll() {
        return subjectPositionRepository.getSubjectPositions();
    }

}
