package java.com.solvd.timetable.algorithm;

import java.com.solvd.timetable.domain.Grade;
import java.com.solvd.timetable.domain.Room;
import java.com.solvd.timetable.domain.Subject;
import java.com.solvd.timetable.domain.Teacher;
import java.com.solvd.timetable.domain.curriculum.GradeCurriculum;
import java.com.solvd.timetable.domain.curriculum.SubjectCount;
import java.com.solvd.timetable.domain.timetable.Day;
import java.com.solvd.timetable.domain.timetable.LessonBlock;
import java.com.solvd.timetable.domain.timetable.LessonNumber;
import java.com.solvd.timetable.domain.timetable.TimeTable;
import java.com.solvd.timetable.service.GradeCurriculumService;
import java.com.solvd.timetable.service.GradeService;
import java.com.solvd.timetable.service.RoomService;
import java.com.solvd.timetable.service.TeacherService;
import java.com.solvd.timetable.service.impl.GradeCurriculumServiceImpl;
import java.com.solvd.timetable.service.impl.GradeServiceImpl;
import java.com.solvd.timetable.service.impl.RoomServiceImpl;
import java.com.solvd.timetable.service.impl.TeacherServiceImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Algorithm {

    protected final int daysInWeek;
    protected final int maxLessonCount;
    protected final int gradesCount;
    protected final List<Day> days;
    protected final List<LessonNumber> lessonNumbers;
    private final List<Grade> grades;
    private final List<Teacher> teachers;
    private final List<Room> rooms;
    private final List<GradeCurriculum> gradeCurricula;

    public Algorithm(int daysInWeek) {
        this.days = Arrays.asList(Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY, Day.FRIDAY, Day.SATURDAY);
        this.lessonNumbers = Arrays.asList(LessonNumber.FIRST, LessonNumber.SECOND, LessonNumber.THIRD,
                LessonNumber.FOURTH, LessonNumber.FIFTH, LessonNumber.SIXTH, LessonNumber.SEVENTH);
        this.daysInWeek = daysInWeek;
        GradeService gradeService = new GradeServiceImpl();
        this.grades = gradeService.getAll();
        TeacherService teacherService = new TeacherServiceImpl();
        this.teachers = teacherService.getAll();
        RoomService roomService = new RoomServiceImpl();
        this.rooms = roomService.getAll();
        GradeCurriculumService gradeCurriculumService = new GradeCurriculumServiceImpl();
        this.gradeCurricula = gradeCurriculumService.getAll();

        this.maxLessonCount = lessonNumbers.size();
        this.gradesCount = grades.size();
    }

    public TimeTable createTimeTable() {
        List<List<Integer>> lessonsPerDay = setAmountOfLessonsPerDay(this.daysInWeek, this.gradeCurricula);
        TimeTable timeTable = new TimeTable(this.daysInWeek * this.maxLessonCount * this.gradesCount);
        again:
        while (true) {
            List<GradeCurriculum> gradeCurricula = cloneGradeCurricula(this.gradeCurricula);
            setAllTeachersFlagTrue(this.teachers);
            setAllRoomsFlagTrue(this.rooms);
            timeTable.getLessonBlocks().forEach(lessonBlock -> lessonBlock = null);
            createLessonBlocksInTimeTable(this.gradesCount, this.maxLessonCount, timeTable, lessonsPerDay);
            List<LessonBlock> table = timeTable.getLessonBlocks();

            for (int i = 0; i < this.daysInWeek; i++) {
                int day = i * this.gradesCount * this.maxLessonCount;
                for (int j = 0; j < this.gradesCount; j++) {
                    int grade = j * this.maxLessonCount;

                    List<SubjectCount> gradeCurriculum = gradeCurricula.get(j).getCountSubjects();

                    for (int lesson = 0; lesson < this.maxLessonCount; lesson++) {
                        if (table.get(day + grade + lesson) != null) {
                            TeacherSubject teacherSubject = randomSubjectTeacher(lesson, gradeCurriculum, this.teachers);
                            if (teacherSubject == null) {
                                continue again;
                            }
                            Room room = findRoom(lesson, teacherSubject.getSubject(), this.rooms);
                            LessonBlock lessonBlock = table.get(day + grade + lesson);
                            lessonBlock.setDay(this.days.get(i));
                            lessonBlock.setGrade(this.grades.get(j));
                            lessonBlock.setLessonNumber(this.lessonNumbers.get(lesson));
                            lessonBlock.setSubject(teacherSubject.getSubject());
                            lessonBlock.setTeacher(teacherSubject.getTeacher());
                            lessonBlock.setRoom(room);
                        }
                    }

                    setAllGradeFlagsTrue(gradeCurriculum);
                }

                setAllRoomsFlagTrue(this.rooms);
                setAllSubjectsFlagsTrue(gradeCurricula);
                setAllTeachersFlagTrue(this.teachers);
            }

            int lessonsInCurriculum = gradeCurricula.stream().
                    mapToInt(gradeCurriculum -> gradeCurriculum.getCountSubjects().stream().
                            mapToInt(count -> count.getCount())
                            .sum())
                    .sum();
            if (lessonsInCurriculum == 0) {
                break;
            }
        }
        return timeTable;
    }

    private void setAllGradeFlagsTrue(List<SubjectCount> gradeCurriculum) {
        gradeCurriculum.forEach(subjectCount -> subjectCount.getSubject().setGradeFlag(true));
    }

    private void setAllSubjectsFlagsTrue(List<GradeCurriculum> gradeCurricula) {
        gradeCurricula.forEach(gradeCurriculum -> gradeCurriculum.getCountSubjects().forEach(subjectCount -> {
            subjectCount.getSubject().setFirstLessonFlag(true);
            subjectCount.getSubject().setSecondLessonFlag(true);
            subjectCount.getSubject().setThirdLessonFlag(true);
            subjectCount.getSubject().setFourthLessonFlag(true);
            subjectCount.getSubject().setFifthLessonFlag(true);
            subjectCount.getSubject().setSixthLessonFlag(true);
            subjectCount.getSubject().setSeventhLessonFlag(true);
        }));
    }

    private void setAllTeachersFlagTrue(List<Teacher> teachers) {
        teachers.forEach(teacher -> {
            teacher.setFirstLessonFlag(true);
            teacher.setSecondLessonFlag(true);
            teacher.setThirdLessonFlag(true);
            teacher.setFourthLessonFlag(true);
            teacher.setFifthLessonFlag(true);
            teacher.setSixthLessonFlag(true);
            teacher.setSeventhLessonFlag(true);
        });
    }

    private void setAllRoomsFlagTrue(List<Room> rooms) {
        rooms.forEach(room -> {
            room.setFirstLessonFlag(true);
            room.setSecondLessonFlag(true);
            room.setThirdLessonFlag(true);
            room.setFourthLessonFlag(true);
            room.setFifthLessonFlag(true);
            room.setSixthLessonFlag(true);
            room.setSeventhLessonFlag(true);
        });
    }

    private Room findRoom(int lesson, Subject subject, List<Room> rooms) {
        Room room = null;
        switch (lesson) {
            case 0:
                for (Room roomElement : rooms) {
                    if (roomElement.getRoomType().name().equals(subject.getRoomTypeNeeded().name())
                            && roomElement.isFirstLessonFlag()) {
                        room = roomElement;
                        room.setFirstLessonFlag(false);
                        break;
                    }
                }
                break;
            case 1:
                for (Room roomElement : rooms) {
                    if (roomElement.getRoomType().name().equals(subject.getRoomTypeNeeded().name())
                            && roomElement.isSecondLessonFlag()) {
                        room = roomElement;
                        room.setSecondLessonFlag(false);
                        break;
                    }
                }
                break;
            case 2:
                for (Room roomElement : rooms) {
                    if (roomElement.getRoomType().name().equals(subject.getRoomTypeNeeded().name())
                            && roomElement.isThirdLessonFlag()) {
                        room = roomElement;
                        room.setThirdLessonFlag(false);
                        break;
                    }
                }
                break;
            case 3:
                for (Room roomElement : rooms) {
                    if (roomElement.getRoomType().name().equals(subject.getRoomTypeNeeded().name())
                            && roomElement.isFourthLessonFlag()) {
                        room = roomElement;
                        room.setFourthLessonFlag(false);
                        break;
                    }
                }
                break;
            case 4:
                for (Room roomElement : rooms) {
                    if (roomElement.getRoomType().name().equals(subject.getRoomTypeNeeded().name())
                            && roomElement.isFifthLessonFlag()) {
                        room = roomElement;
                        room.setFifthLessonFlag(false);
                        break;
                    }
                }
                break;
            case 5:
                for (Room roomElement : rooms) {
                    if (roomElement.getRoomType().name().equals(subject.getRoomTypeNeeded().name())
                            && roomElement.isSixthLessonFlag()) {
                        room = roomElement;
                        room.setSixthLessonFlag(false);
                        break;
                    }
                }
                break;
            case 6:
                for (Room roomElement : rooms) {
                    if (roomElement.getRoomType().name().equals(subject.getRoomTypeNeeded().name())
                            && roomElement.isSeventhLessonFlag()) {
                        room = roomElement;
                        room.setSeventhLessonFlag(false);
                        break;
                    }
                }
                break;
        }
        return room;
    }

    private TeacherSubject randomSubjectTeacher(int lesson, List<SubjectCount> subjectCounts, List<Teacher> teachers) {
        int subjectCountsSize = subjectCounts.size();
        TeacherSubject teacherSubject = null;
        int index;
        switch (lesson) {
            case 0:
                index = 0;
                while (index < 100000) {
                    int randomIntSubject = (int) (Math.random() * subjectCountsSize);
                    SubjectCount subjectCount = subjectCounts.get(randomIntSubject);
                    Teacher teacher = null;

                    end:
                    for (Teacher teacherElement : teachers) {
                        for (Subject subject : teacherElement.getSubjects()) {
                            if (subject.getName().equals(subjectCount.getSubject().getName())) {
                                teacher = teacherElement;
                                break end;
                            }
                        }
                    }

                    if (subjectCount.getCount() != 0 && subjectCount.getSubject().isGradeFlag() && subjectCount.getSubject().isFirstLessonFlag()
                            && teacher.isFirstLessonFlag()) {
                        teacherSubject = new TeacherSubject();
                        teacherSubject.setSubject(subjectCount.getSubject());
                        teacherSubject.setTeacher(teacher);
                        subjectCount.setCount(subjectCount.getCount() - 1);
                        subjectCount.getSubject().setGradeFlag(false);
                        subjectCount.getSubject().setFirstLessonFlag(false);
                        teacher.setFirstLessonFlag(false);
                        break;
                    }
                    index++;
                }
                break;
            case 1:
                index = 0;
                while (index < 100000) {
                    int randomIntSubject = (int) (Math.random() * subjectCountsSize);
                    SubjectCount subjectCount = subjectCounts.get(randomIntSubject);
                    Teacher teacher = null;

                    end:
                    for (Teacher teacherElement : teachers) {
                        for (Subject subject : teacherElement.getSubjects()) {
                            if (subject.getName().equals(subjectCount.getSubject().getName())) {
                                teacher = teacherElement;
                                break end;
                            }
                        }
                    }

                    if (subjectCount.getCount() != 0 && subjectCount.getSubject().isGradeFlag() && subjectCount.getSubject().isSecondLessonFlag()
                            && teacher.isSecondLessonFlag()) {
                        teacherSubject = new TeacherSubject();
                        teacherSubject.setSubject(subjectCount.getSubject());
                        teacherSubject.setTeacher(teacher);
                        subjectCount.setCount(subjectCount.getCount() - 1);
                        subjectCount.getSubject().setGradeFlag(false);
                        subjectCount.getSubject().setSecondLessonFlag(false);
                        teacher.setSecondLessonFlag(false);
                        break;
                    }
                    index++;
                }
                break;
            case 2:
                index = 0;
                while (index < 100000) {
                    int randomIntSubject = (int) (Math.random() * subjectCountsSize);
                    SubjectCount subjectCount = subjectCounts.get(randomIntSubject);
                    Teacher teacher = null;

                    end:
                    for (Teacher teacherElement : teachers) {
                        for (Subject subject : teacherElement.getSubjects()) {
                            if (subject.getName().equals(subjectCount.getSubject().getName())) {
                                teacher = teacherElement;
                                break end;
                            }
                        }
                    }

                    if (subjectCount.getCount() != 0 && subjectCount.getSubject().isGradeFlag() && subjectCount.getSubject().isThirdLessonFlag()
                            && teacher.isThirdLessonFlag()) {
                        teacherSubject = new TeacherSubject();
                        teacherSubject.setSubject(subjectCount.getSubject());
                        teacherSubject.setTeacher(teacher);
                        subjectCount.setCount(subjectCount.getCount() - 1);
                        subjectCount.getSubject().setGradeFlag(false);
                        subjectCount.getSubject().setThirdLessonFlag(false);
                        teacher.setThirdLessonFlag(false);
                        break;
                    }
                    index++;
                }
                break;
            case 3:
                index = 0;
                while (index < 100000) {
                    int randomIntSubject = (int) (Math.random() * subjectCountsSize);
                    SubjectCount subjectCount = subjectCounts.get(randomIntSubject);
                    Teacher teacher = null;

                    end:
                    for (Teacher teacherElement : teachers) {
                        for (Subject subject : teacherElement.getSubjects()) {
                            if (subject.getName().equals(subjectCount.getSubject().getName())) {
                                teacher = teacherElement;
                                break end;
                            }
                        }
                    }

                    if (subjectCount.getCount() != 0 && subjectCount.getSubject().isGradeFlag() && subjectCount.getSubject().isFourthLessonFlag()
                            && teacher.isFourthLessonFlag()) {
                        teacherSubject = new TeacherSubject();
                        teacherSubject.setSubject(subjectCount.getSubject());
                        teacherSubject.setTeacher(teacher);
                        subjectCount.setCount(subjectCount.getCount() - 1);
                        subjectCount.getSubject().setGradeFlag(false);
                        subjectCount.getSubject().setFourthLessonFlag(false);
                        teacher.setFourthLessonFlag(false);
                        break;
                    }
                    index++;
                }
                break;
            case 4:
                index = 0;
                while (index < 100000) {
                    int randomIntSubject = (int) (Math.random() * subjectCountsSize);
                    SubjectCount subjectCount = subjectCounts.get(randomIntSubject);
                    Teacher teacher = null;

                    end:
                    for (Teacher teacherElement : teachers) {
                        for (Subject subject : teacherElement.getSubjects()) {
                            if (subject.getName().equals(subjectCount.getSubject().getName())) {
                                teacher = teacherElement;
                                break end;
                            }
                        }
                    }

                    if (subjectCount.getCount() != 0 && subjectCount.getSubject().isGradeFlag() && subjectCount.getSubject().isFifthLessonFlag()
                            && teacher.isFifthLessonFlag()) {
                        teacherSubject = new TeacherSubject();
                        teacherSubject.setSubject(subjectCount.getSubject());
                        teacherSubject.setTeacher(teacher);
                        subjectCount.setCount(subjectCount.getCount() - 1);
                        subjectCount.getSubject().setGradeFlag(false);
                        subjectCount.getSubject().setFifthLessonFlag(false);
                        teacher.setFifthLessonFlag(false);
                        break;
                    }
                    index++;
                }
                break;
            case 5:
                index = 0;
                while (index < 100000) {
                    int randomIntSubject = (int) (Math.random() * subjectCountsSize);
                    SubjectCount subjectCount = subjectCounts.get(randomIntSubject);
                    Teacher teacher = null;
                    end:

                    for (Teacher teacherElement : teachers) {
                        for (Subject subject : teacherElement.getSubjects()) {
                            if (subject.getName().equals(subjectCount.getSubject().getName())) {
                                teacher = teacherElement;
                                break end;
                            }
                        }
                    }

                    if (subjectCount.getCount() != 0 && subjectCount.getSubject().isGradeFlag() && subjectCount.getSubject().isSixthLessonFlag()
                            && teacher.isSixthLessonFlag()) {
                        teacherSubject = new TeacherSubject();
                        teacherSubject.setSubject(subjectCount.getSubject());
                        teacherSubject.setTeacher(teacher);
                        subjectCount.setCount(subjectCount.getCount() - 1);
                        subjectCount.getSubject().setGradeFlag(false);
                        subjectCount.getSubject().setSixthLessonFlag(false);
                        teacher.setSixthLessonFlag(false);
                        break;
                    }
                    index++;
                }
                break;
            case 6:
                index = 0;
                while (index < 100000) {
                    int randomIntSubject = (int) (Math.random() * subjectCountsSize);
                    SubjectCount subjectCount = subjectCounts.get(randomIntSubject);
                    Teacher teacher = null;

                    end:
                    for (Teacher teacherElement : teachers) {
                        for (Subject subject : teacherElement.getSubjects()) {
                            if (subject.getName().equals(subjectCount.getSubject().getName())) {
                                teacher = teacherElement;
                                break end;
                            }
                        }
                    }

                    if (subjectCount.getCount() != 0 && subjectCount.getSubject().isGradeFlag() && subjectCount.getSubject().isSeventhLessonFlag()
                            && teacher.isSeventhLessonFlag()) {
                        teacherSubject = new TeacherSubject();
                        teacherSubject.setSubject(subjectCount.getSubject());
                        teacherSubject.setTeacher(teacher);
                        subjectCount.setCount(subjectCount.getCount() - 1);
                        subjectCount.getSubject().setGradeFlag(false);
                        subjectCount.getSubject().setSeventhLessonFlag(false);
                        teacher.setSeventhLessonFlag(false);
                        break;
                    }
                    index++;
                }
                break;
        }
        return teacherSubject;
    }

    private List<Integer> randomLessonsPerDay(int daysInWeek, int lessonsPerWeek) {
        int avg = lessonsPerWeek / daysInWeek;
        List<Integer> lessonsPerDay = new ArrayList<>();
        for (int i = 0; i < daysInWeek; i++) {
            lessonsPerDay.add((int) ((Math.random() * ((avg + 1) - (avg - 1) + 1)) + (avg - 1)));
        }
        int countOfLessonsInWeek = lessonsPerDay.stream().reduce((result, element) -> result += element).get();
        if (countOfLessonsInWeek > lessonsPerWeek) {
            int difference = countOfLessonsInWeek - lessonsPerWeek;
            for (int i = 0; i < difference; i++) {
                int max = lessonsPerDay.stream().max((x, y) -> Integer.compare(x, y)).get();
                int index = lessonsPerDay.indexOf(max);
                lessonsPerDay.set(index, lessonsPerDay.get(index) - 1);
            }
        } else if (countOfLessonsInWeek < lessonsPerWeek) {
            int difference = lessonsPerWeek - countOfLessonsInWeek;
            for (int i = 0; i < difference; i++) {
                int min = lessonsPerDay.stream().min((x, y) -> Integer.compare(x, y)).get();
                int index = lessonsPerDay.indexOf(min);
                lessonsPerDay.set(index, lessonsPerDay.get(index) + 1);
            }
        }
        return lessonsPerDay;
    }

    private void createLessonBlocksInTimeTable(int gradesCount, int maxLessonCount, TimeTable timeTable, List<List<Integer>> lessonsPerDay) {
        int grade = 0;
        for (List<Integer> lessonsPerDayInClass : lessonsPerDay) {
            int day = 0;
            for (int lessonInThatDay : lessonsPerDayInClass) {
                for (int lesson = 0; lesson < lessonInThatDay; lesson++) {
                    timeTable.getLessonBlocks().set(day * gradesCount * maxLessonCount + grade * maxLessonCount + lesson, new LessonBlock());
                }
                day++;
            }
            grade++;
        }
    }

    private List<List<Integer>> setAmountOfLessonsPerDay(int daysInWeek, List<GradeCurriculum> gradeCurricula) {
        List<List<Integer>> lessonsPerDay = new ArrayList<>();
        for (GradeCurriculum gradeCurriculum : gradeCurricula) {
            lessonsPerDay.add(randomLessonsPerDay(daysInWeek, gradeCurriculum.getCountSubjects().stream().
                    mapToInt(subjectCount -> subjectCount.getCount()).sum()));
        }
        return lessonsPerDay;
    }

    private List<GradeCurriculum> cloneGradeCurricula(List<GradeCurriculum> gradeCurricula) {
        List<GradeCurriculum> newGradeCurricula = new ArrayList<>();
        for (GradeCurriculum gradeCurriculum : gradeCurricula) {
            Grade grade = new Grade();
            grade.setName(gradeCurriculum.getGrade().getName());
            grade.setId(gradeCurriculum.getGrade().getId());
            List<SubjectCount> subjectCounts = cloneSubjectCounts(gradeCurriculum.getCountSubjects());
            GradeCurriculum newGradeCurriculum = new GradeCurriculum();
            newGradeCurriculum.setGrade(grade);
            newGradeCurriculum.setCountSubjects(subjectCounts);

            newGradeCurricula.add(newGradeCurriculum);
        }
        return newGradeCurricula;
    }

    private List<SubjectCount> cloneSubjectCounts(List<SubjectCount> subjectCounts) {
        List<SubjectCount> newSubjectCounts = new ArrayList<>();
        for (SubjectCount subjectCount : subjectCounts) {
            Subject subject = new Subject();
            subject.setId(subjectCount.getSubject().getId());
            subject.setName(subjectCount.getSubject().getName());
            subject.setRoomTypeNeeded(subjectCount.getSubject().getRoomTypeNeeded());
            subject.setFirstLessonFlag(subjectCount.getSubject().isFirstLessonFlag());
            subject.setSecondLessonFlag(subjectCount.getSubject().isSecondLessonFlag());
            subject.setThirdLessonFlag(subjectCount.getSubject().isThirdLessonFlag());
            subject.setFourthLessonFlag(subjectCount.getSubject().isFourthLessonFlag());
            subject.setFifthLessonFlag(subjectCount.getSubject().isFifthLessonFlag());
            subject.setSixthLessonFlag(subjectCount.getSubject().isSixthLessonFlag());
            subject.setSeventhLessonFlag(subjectCount.getSubject().isSeventhLessonFlag());
            subject.setGradeFlag(subjectCount.getSubject().isGradeFlag());
            SubjectCount newSubjectCount = new SubjectCount();
            newSubjectCount.setSubject(subject);
            newSubjectCount.setCount(subjectCount.getCount());

            newSubjectCounts.add(newSubjectCount);
        }
        return newSubjectCounts;
    }

    public int getDaysInWeek() {
        return daysInWeek;
    }

    public int getMaxLessonCount() {
        return maxLessonCount;
    }

    public int getGradesCount() {
        return gradesCount;
    }
}
