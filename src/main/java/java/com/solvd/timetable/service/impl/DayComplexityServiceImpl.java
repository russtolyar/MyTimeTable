package java.com.solvd.timetable.service.impl;


import java.com.solvd.timetable.algorithm.DayComplexity;
import java.com.solvd.timetable.persistence.DayComplexityRepository;
import java.com.solvd.timetable.persistence.Impl.DayComplexityRepositoryImpl;
import java.com.solvd.timetable.service.DayComplexityService;
import java.util.List;

public class DayComplexityServiceImpl implements DayComplexityService {

    private final DayComplexityRepository dayComplexityRepository;

    public DayComplexityServiceImpl() {
        this.dayComplexityRepository = new DayComplexityRepositoryImpl();
    }

    @Override
    public List<DayComplexity> getAll() {
        return dayComplexityRepository.getDayComplexities();
    }

}
