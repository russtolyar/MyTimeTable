package java.com.solvd.timetable.service.impl;


import java.com.solvd.timetable.algorithm.SubjectComplexity;
import java.com.solvd.timetable.persistence.Impl.SubjectComplexityRepositoryImpl;
import java.com.solvd.timetable.persistence.SubjectComplexityRepository;
import java.com.solvd.timetable.service.SubjectComplexityService;
import java.util.List;

public class SubjectComplexityServiceImpl implements SubjectComplexityService {

    private final SubjectComplexityRepository subjectComplexityRepository;

    public SubjectComplexityServiceImpl() {
        this.subjectComplexityRepository = new SubjectComplexityRepositoryImpl();
    }

    @Override
    public List<SubjectComplexity> getAll() {
        return subjectComplexityRepository.getSubjectComplexities();
    }

}
