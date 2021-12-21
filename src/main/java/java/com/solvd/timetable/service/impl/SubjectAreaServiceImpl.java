package java.com.solvd.timetable.service.impl;


import java.com.solvd.timetable.algorithm.SubjectArea;
import java.com.solvd.timetable.persistence.Impl.SubjectAreaRepositoryImpl;
import java.com.solvd.timetable.persistence.SubjectAreaRepository;
import java.com.solvd.timetable.service.SubjectAreaService;
import java.util.List;

public class SubjectAreaServiceImpl implements SubjectAreaService {

    private final SubjectAreaRepository subjectAreaRepository;

    public SubjectAreaServiceImpl() {
        this.subjectAreaRepository = new SubjectAreaRepositoryImpl();
    }

    @Override
    public List<SubjectArea> getAll() {
        return subjectAreaRepository.getSubjectAreas();
    }

}
