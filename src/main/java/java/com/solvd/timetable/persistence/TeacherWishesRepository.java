package java.com.solvd.timetable.persistence;

import java.com.solvd.timetable.algorithm.TeacherWish;
import java.util.List;

public interface TeacherWishesRepository {

    List<TeacherWish> getTeacherWishes();

}
