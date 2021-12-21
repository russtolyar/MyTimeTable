package java.com.solvd.timetable.service.impl;



import java.com.solvd.timetable.algorithm.TeacherWish;
import java.com.solvd.timetable.persistence.Impl.TeacherWishesRepositoryImpl;
import java.com.solvd.timetable.persistence.TeacherWishesRepository;
import java.com.solvd.timetable.service.TeacherWishService;
import java.util.List;

public class TeacherWishServiceImpl implements TeacherWishService {

    private final TeacherWishesRepository teacherWishesRepository;

    public TeacherWishServiceImpl() {
        this.teacherWishesRepository = new TeacherWishesRepositoryImpl();
    }

    @Override
    public List<TeacherWish> getAll() {
        return teacherWishesRepository.getTeacherWishes();
    }

}
