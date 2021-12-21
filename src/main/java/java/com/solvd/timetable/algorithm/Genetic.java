package java.com.solvd.timetable.algorithm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.com.solvd.timetable.domain.*;
import java.com.solvd.timetable.domain.timetable.Day;
import java.com.solvd.timetable.domain.timetable.LessonBlock;
import java.com.solvd.timetable.domain.timetable.LessonNumber;
import java.com.solvd.timetable.domain.timetable.TimeTable;
import java.com.solvd.timetable.service.*;
import java.com.solvd.timetable.service.impl.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Genetic extends Algorithm {

    private static final Logger LOGGER = LogManager.getLogger(Genetic.class);

    private List<IndividualMark> bestPopulation;
    private List<IndividualMark> currentPopulation;
    private final List<DayComplexity> dayComplexities;
    private final List<SubjectComplexity> subjectComplexities;
    private final List<SubjectArea> subjectAreas;
    private final List<SubjectPosition> subjectPositions;
    private final List<TeacherWish> teacherWishes;

    public Genetic(int daysInWeek) {

        super(daysInWeek);

        DayComplexityService dayComplexityService = new DayComplexityServiceImpl();
        this.dayComplexities = dayComplexityService.getAll();
        formatDayComplexity(this.dayComplexities, daysInWeek);

        SubjectComplexityService subjectComplexityService = new SubjectComplexityServiceImpl();
        this.subjectComplexities = subjectComplexityService.getAll();

        SubjectAreaService subjectAreaService = new SubjectAreaServiceImpl();
        this.subjectAreas = subjectAreaService.getAll();

        SubjectPositionService subjectPositionService = new SubjectPositionServiceImpl();
        this.subjectPositions = subjectPositionService.getAll();

        TeacherWishService teacherWishService = new TeacherWishServiceImpl();
        this.teacherWishes = teacherWishService.getAll();

        createStartPopulation();
    }

    public TimeTable tryGenetic() {
        long iteration = 1;
        while (iteration < 2000000) {
            crossBreeding();
            mutation();
            grading();
            selection();
            if(iteration%100000 == 0) {
                LOGGER.info(iteration + " iterations from 2.000.000.");
            }
            iteration++;
        }
        TimeTable timeTable = new TimeTable();
        timeTable.setLessonBlocks(findMaxInBest().getIndividual());
        return timeTable;
    }

    private void createStartPopulation() {
        this.bestPopulation = new ArrayList<>();
        this.currentPopulation = new ArrayList<>();
        IntStream.range(0, 4).boxed().forEach(index -> {
            IndividualMark individualMark = new IndividualMark();
            individualMark.setIndividual(this.createTimeTable().getLessonBlocks());
            individualMark.setMark(gradeStartPopulation(individualMark.getIndividual()));
            this.bestPopulation.add(individualMark);
        });
    }

    private void crossBreeding() {
        this.currentPopulation = clonePopulation(this.bestPopulation);
        for (int i = 0; i < this.currentPopulation.size(); i += 2) {
            LessonBlock firstIndividualLessonBlock = getRandomLessonBlock(this.currentPopulation.get(i));
            int firstIndex = this.currentPopulation.get(i).getIndividual().indexOf(firstIndividualLessonBlock);

            LessonBlock intermediate = cloneLessonBlock(firstIndividualLessonBlock);

            LessonBlock secondIndividualLessonBlock = getRandomLessonBlock(this.currentPopulation.get(i + 1));
            int secondIndex = this.currentPopulation.get(i + 1).getIndividual().indexOf(secondIndividualLessonBlock);

            this.currentPopulation.get(i).getIndividual().get(firstIndex).setSubject(secondIndividualLessonBlock.getSubject());
            this.currentPopulation.get(i).getIndividual().get(firstIndex).setTeacher(secondIndividualLessonBlock.getTeacher());
            this.currentPopulation.get(i).getIndividual().get(firstIndex).setRoom(secondIndividualLessonBlock.getRoom());

            this.currentPopulation.get(i + 1).getIndividual().get(secondIndex).setSubject(intermediate.getSubject());
            this.currentPopulation.get(i + 1).getIndividual().get(secondIndex).setTeacher(intermediate.getTeacher());
            this.currentPopulation.get(i + 1).getIndividual().get(secondIndex).setRoom(intermediate.getRoom());
        }
    }

    private void mutation() {
        for (IndividualMark individualMark : this.currentPopulation) {
            LessonBlock firstLessonBlock;
            LessonBlock secondLessonBlock;
            while (true) {
                firstLessonBlock = getRandomLessonBlock(individualMark);
                secondLessonBlock = getRandomLessonBlock(individualMark);
                if (firstLessonBlock != secondLessonBlock) {
                    break;
                }
            }

            LessonBlock intermediate = cloneLessonBlock(firstLessonBlock);

            firstLessonBlock.setSubject(secondLessonBlock.getSubject());
            firstLessonBlock.setTeacher(secondLessonBlock.getTeacher());
            firstLessonBlock.setRoom(secondLessonBlock.getRoom());

            secondLessonBlock.setSubject(intermediate.getSubject());
            secondLessonBlock.setTeacher(intermediate.getTeacher());
            secondLessonBlock.setRoom(intermediate.getRoom());
        }
    }

    private void grading() {
        for (IndividualMark individualMark : this.currentPopulation) {
            int mark = 0;
            List<LessonBlock> lessonBlocks = individualMark.getIndividual();
            mark += gradeForOneTeacherPerLesson(lessonBlocks);
            mark += gradeForOneSubjectPerLesson(lessonBlocks);
            mark += gradeForOneSubjectPerDay(lessonBlocks);
            mark += gradeForOneRoomPerLesson(lessonBlocks);
            mark += gradeDayComplexity(lessonBlocks);
            mark += gradeAreaAlternation(lessonBlocks);
            mark += gradeSubjectPositions(lessonBlocks);
            mark += gradeTeacherWishes(lessonBlocks);
            individualMark.setMark(mark);
        }
    }

    private void selection() {
        IntStream.range(0, this.bestPopulation.size()).boxed().forEach(index -> {
            IndividualMark minMarkInBest = findMinInBest();
            IndividualMark maxMarkInCurrent = findMaxInCurrent();
            if(maxMarkInCurrent.getMark() > minMarkInBest.getMark()) {
                int indexOfCurrent = this.currentPopulation.indexOf(maxMarkInCurrent);
                int indexOfBest = this.bestPopulation.indexOf(minMarkInBest);
                this.bestPopulation.set(indexOfBest, this.currentPopulation.get(indexOfCurrent));
            }
        });
    }

    private IndividualMark findMaxInCurrent() {
        int maximal = this.currentPopulation.get(0).getMark();
        int maximalIndex = 0;
        for (int i = 1; i < this.currentPopulation.size(); i++) {
            int current = this.currentPopulation.get(i).getMark();
            if(maximal < current) {
                maximal = current;
                maximalIndex = i;
            }
        }
        return this.currentPopulation.get(maximalIndex);
    }

    private IndividualMark findMinInBest() {
        int minimal = this.bestPopulation.get(0).getMark();
        int minimalIndex = 0;
        for (int i = 1; i < this.bestPopulation.size(); i++) {
            int current = this.bestPopulation.get(i).getMark();
           if(minimal > current) {
               minimal = current;
               minimalIndex = i;
           }
        }
        return this.bestPopulation.get(minimalIndex);
    }

    private IndividualMark findMaxInBest() {
        int maximal = this.bestPopulation.get(0).getMark();
        int maximalIndex = 0;
        for (int i = 1; i < this.bestPopulation.size(); i++) {
            int current = this.bestPopulation.get(i).getMark();
            if(maximal < current) {
                maximal = current;
                maximalIndex = i;
            }
        }
        return this.bestPopulation.get(maximalIndex);
    }

    private int gradeStartPopulation(List<LessonBlock> lessonBlocks) {
        int mark = 0;
        mark += gradeForOneTeacherPerLesson(lessonBlocks);
        mark += gradeForOneSubjectPerLesson(lessonBlocks);
        mark += gradeForOneSubjectPerDay(lessonBlocks);
        mark += gradeForOneRoomPerLesson(lessonBlocks);
        mark += gradeDayComplexity(lessonBlocks);
        mark += gradeAreaAlternation(lessonBlocks);
        mark += gradeSubjectPositions(lessonBlocks);
        mark += gradeTeacherWishes(lessonBlocks);
        return mark;
    }

    private int gradeTeacherWishes(List<LessonBlock> lessonBlocks) {
        int countOfMatches = 0;

        for (int i = 0; i < this.daysInWeek; i++) {
            int day = i * this.gradesCount * this.maxLessonCount;

            for (int j = 0; j < this.gradesCount; j++) {
                int grade = j * this.maxLessonCount;

                for (int lesson = 0; lesson < this.maxLessonCount; lesson++) {
                    int index = day + grade + lesson;
                    LessonBlock lessonBlock = lessonBlocks.get(index);
                    if (lessonBlock != null) {
                        Teacher teacher = lessonBlock.getTeacher();
                        List<LessonNumber> teacherWishes = findTeacherWishes(teacher);
                        int finalLesson = lesson;
                        if (teacherWishes.stream().anyMatch(lessonNumber -> lessonNumber.ordinal() == finalLesson)) {
                            countOfMatches++;
                        }
                    }
                }
            }
        }
        return countOfMatches * 2;
    }

    private int gradeSubjectPositions(List<LessonBlock> lessonBlocks) {
        int countOfMatches = 0;

        for (int i = 0; i < this.daysInWeek; i++) {
            int day = i * this.gradesCount * this.maxLessonCount;

            for (int j = 0; j < this.gradesCount; j++) {
                int grade = j * this.maxLessonCount;

                for (int lesson = 0; lesson < this.maxLessonCount; lesson++) {
                    int index = day + grade + lesson;
                    LessonBlock lessonBlock = lessonBlocks.get(index);
                    if (lessonBlock != null) {
                        Subject subject = lessonBlock.getSubject();
                        List<LessonNumber> subjectPositions = findSubjectPositions(subject);
                        int finalLesson = lesson;
                        if (subjectPositions.stream().anyMatch(lessonNumber -> lessonNumber.ordinal() == finalLesson)) {
                            countOfMatches++;
                        }
                    }
                }
            }
        }
        return countOfMatches * 3;
    }

    private int gradeAreaAlternation(List<LessonBlock> lessonBlocks) {
        int mark = 0;

        for (int j = 0; j < this.gradesCount; j++) {
            int grade = j * this.maxLessonCount;


            for (int i = 0; i < this.daysInWeek; i++) {
                int day = i * this.gradesCount * this.maxLessonCount;

                List<String> subjectAreasPerDay = new ArrayList<>();
                for (int lesson = 0; lesson < this.maxLessonCount; lesson++) {
                    int index = day + grade + lesson;
                    LessonBlock lessonBlock = lessonBlocks.get(index);
                    if (lessonBlock != null) {
                        Subject subject = lessonBlock.getSubject();
                        SubjectArea subjectArea = findSubjectArea(subject);
                        subjectAreasPerDay.add(subjectArea.getAreaName());
                    }
                }
                int alternationCount = countAlternation(subjectAreasPerDay);
                mark += alternationMark(alternationCount);
            }
        }
        mark = mark / this.gradesCount;
        return mark;
    }

    private int gradeDayComplexity(List<LessonBlock> lessonBlocks) {
        int mark = 0;

        for (int j = 0; j < this.gradesCount; j++) {
            int grade = j * this.maxLessonCount;


            for (int i = 0; i < this.daysInWeek; i++) {
                int day = i * this.gradesCount * this.maxLessonCount;

                int complexity = 0;
                for (int lesson = 0; lesson < this.maxLessonCount; lesson++) {
                    int index = day + grade + lesson;
                    LessonBlock lessonBlock = lessonBlocks.get(index);
                    if (lessonBlock != null) {
                        Subject subject = lessonBlock.getSubject();
                        complexity += this.subjectComplexities.stream()
                                .filter(subjectComplexity -> subjectComplexity.getSubject().getName().equals(subject.getName()))
                                .map(subjectComplexity -> subjectComplexity.getComplexity())
                                .findFirst()
                                .get();
                    }
                }
                Day dayInWeek = days.get(i);
                int complexityLimit = dayComplexities.stream()
                        .filter(dayComplexity -> dayComplexity.getDay().name().equals(dayInWeek.name()))
                        .map(dayComplexity -> dayComplexity.getDayComplexity())
                        .findFirst()
                        .get();
                if (complexity < complexityLimit) {
                    mark += 60;
                } else {
                    mark += 0;
                }
            }
        }
        mark = mark / this.gradesCount;
        return mark;
    }

    private int gradeForOneSubjectPerDay(List<LessonBlock> lessonBlocks) {
        int mark = 0;

        dayLoop:
        for (int i = 0; i < this.daysInWeek; i++) {
            int day = i * this.gradesCount * this.maxLessonCount;

            for (int j = 0; j < this.gradesCount; j++) {
                int grade = j * this.maxLessonCount;

                for (int lesson = 0; lesson < this.maxLessonCount; lesson++) {
                    int index = day + grade + lesson;
                    LessonBlock lessonBlock = lessonBlocks.get(index);
                    if (lessonBlock != null) {
                        Subject subject = lessonBlock.getSubject();

                        for (int searchLesson = lesson + 1; searchLesson < this.maxLessonCount; searchLesson++) {
                            int searchIndex = day + grade + searchLesson;
                            LessonBlock searchLessonBlock = lessonBlocks.get(searchIndex);
                            if (searchLessonBlock != null) {
                                Subject searchSubject = searchLessonBlock.getSubject();
                                if (!subject.getId().equals(searchSubject.getId())) {
                                    mark = 1000;
                                } else {
                                    mark = 0;
                                    break dayLoop;
                                }
                            } else {
                                mark = 1000;
                            }
                        }
                    }
                }
            }
        }
        return mark;
    }

    private int gradeForOneRoomPerLesson(List<LessonBlock> lessonBlocks) {
        int mark = 0;

        dayLoop:
        for (int i = 0; i < this.daysInWeek; i++) {
            int day = i * this.gradesCount * this.maxLessonCount;

            for (int j = 0; j < this.gradesCount; j++) {
                int grade = j * this.maxLessonCount;

                for (int lesson = 0; lesson < this.maxLessonCount; lesson++) {
                    int index = day + grade + lesson;
                    LessonBlock lessonBlock = lessonBlocks.get(index);
                    if (lessonBlock != null) {
                        Room room = lessonBlock.getRoom();

                        for (int x = 0; x < this.gradesCount; x++) {
                            int timeTravellerGrade = x * this.maxLessonCount;
                            if (timeTravellerGrade != grade) {
                                int timeTravellerIndex = day + timeTravellerGrade + lesson;
                                LessonBlock timeTravellerLessonBlock = lessonBlocks.get(timeTravellerIndex);
                                if (timeTravellerLessonBlock != null) {
                                    Room timeTraveller = timeTravellerLessonBlock.getRoom();
                                    if (!room.getId().equals(timeTraveller.getId())) {
                                        mark = 1000;
                                    } else {
                                        mark = 0;
                                        break dayLoop;
                                    }
                                } else {
                                    mark = 1000;
                                }
                            }
                        }
                    }
                }
            }
        }
        return mark;
    }

    private int gradeForOneSubjectPerLesson(List<LessonBlock> lessonBlocks) {
        int mark = 0;

        dayLoop:
        for (int i = 0; i < this.daysInWeek; i++) {
            int day = i * this.gradesCount * this.maxLessonCount;

            for (int j = 0; j < this.gradesCount; j++) {
                int grade = j * this.maxLessonCount;

                for (int lesson = 0; lesson < this.maxLessonCount; lesson++) {
                    int index = day + grade + lesson;
                    LessonBlock lessonBlock = lessonBlocks.get(index);
                    if (lessonBlock != null) {
                        Subject subject = lessonBlock.getSubject();

                        for (int x = 0; x < this.gradesCount; x++) {
                            int timeTravellerGrade = x * this.maxLessonCount;
                            if (timeTravellerGrade != grade) {
                                int timeTravellerIndex = day + timeTravellerGrade + lesson;
                                LessonBlock timeTravellerLessonBlock = lessonBlocks.get(timeTravellerIndex);
                                if (timeTravellerLessonBlock != null) {
                                    Subject timeTraveller = timeTravellerLessonBlock.getSubject();
                                    if (!subject.getId().equals(timeTraveller.getId())) {
                                        mark = 1000;
                                    } else {
                                        mark = 0;
                                        break dayLoop;
                                    }
                                } else {
                                    mark = 1000;
                                }
                            }
                        }
                    }
                }
            }
        }
        return mark;
    }

    private int gradeForOneTeacherPerLesson(List<LessonBlock> lessonBlocks) {
        int mark = 0;

        dayLoop:
        for (int i = 0; i < this.daysInWeek; i++) {
            int day = i * this.gradesCount * this.maxLessonCount;

            for (int j = 0; j < this.gradesCount; j++) {
                int grade = j * this.maxLessonCount;

                for (int lesson = 0; lesson < this.maxLessonCount; lesson++) {
                    int index = day + grade + lesson;
                    LessonBlock lessonBlock = lessonBlocks.get(index);
                    if (lessonBlock != null) {
                        Teacher teacher = lessonBlock.getTeacher();

                        for (int x = 0; x < this.gradesCount; x++) {
                            int timeTravellerGrade = x * this.maxLessonCount;
                            if (timeTravellerGrade != grade) {
                                int timeTravellerIndex = day + timeTravellerGrade + lesson;
                                LessonBlock timeTravellerLessonBlock = lessonBlocks.get(timeTravellerIndex);
                                if (timeTravellerLessonBlock != null) {
                                    Teacher timeTraveller = timeTravellerLessonBlock.getTeacher();
                                    if (!teacher.getId().equals(timeTraveller.getId())) {
                                        mark = 1000;
                                    } else {
                                        mark = 0;
                                        break dayLoop;
                                    }
                                } else {
                                    mark = 1000;
                                }
                            }
                        }
                    }
                }
            }
        }
        return mark;
    }

    private LessonBlock getRandomLessonBlock(IndividualMark individualMark) {
        int range = individualMark.getIndividual().size();
        LessonBlock result;
        while (true) {
            int random = (int) (Math.random() * range);
            result = individualMark.getIndividual().get(random);
            if (result != null) {
                break;
            }
        }
        return result;
    }

    private List<IndividualMark> clonePopulation(List<IndividualMark> population) {
        List<IndividualMark> newPopulation = new ArrayList<>();
        for (IndividualMark individualMark : population) {
            List<LessonBlock> newLessonBlocks = cloneLessonBlocks(individualMark.getIndividual());
            IndividualMark newIndividualMark = new IndividualMark();
            newIndividualMark.setIndividual(newLessonBlocks);
            newPopulation.add(newIndividualMark);
        }
        return newPopulation;
    }

    private List<LessonBlock> cloneLessonBlocks(List<LessonBlock> lessonBlocks) {
        List<LessonBlock> newLessonBlocks = new ArrayList<>();
        for (LessonBlock lessonBlock : lessonBlocks) {
            if(lessonBlock != null) {
                LessonBlock newLessonBlock = new LessonBlock();
                newLessonBlock.setId(lessonBlock.getId());
                newLessonBlock.setDay(Day.valueOf(lessonBlock.getDay().name()));
                newLessonBlock.setLessonNumber(LessonNumber.valueOf(lessonBlock.getLessonNumber().name()));

                Grade newGrade = new Grade();
                newGrade.setId(lessonBlock.getGrade().getId());
                newGrade.setName(lessonBlock.getGrade().getName());
                newLessonBlock.setGrade(newGrade);

                Subject newSubject = new Subject();
                newSubject.setId(lessonBlock.getSubject().getId());
                newSubject.setName(lessonBlock.getSubject().getName());
                newSubject.setRoomTypeNeeded(RoomType.valueOf(lessonBlock.getSubject().getRoomTypeNeeded().name()));
                newLessonBlock.setSubject(newSubject);

                Teacher newTeacher = new Teacher();
                newTeacher.setId(lessonBlock.getTeacher().getId());
                newTeacher.setFirstName(lessonBlock.getTeacher().getFirstName());
                newTeacher.setLastName(lessonBlock.getTeacher().getLastName());
                newTeacher.setSubjects(cloneSubjects(lessonBlock.getTeacher().getSubjects()));
                newLessonBlock.setTeacher(newTeacher);

                Room newRoom = new Room();
                newRoom.setId(lessonBlock.getRoom().getId());
                newRoom.setNumber(lessonBlock.getRoom().getNumber());
                newRoom.setRoomType(RoomType.valueOf(lessonBlock.getRoom().getRoomType().name()));
                newLessonBlock.setRoom(newRoom);

                newLessonBlocks.add(newLessonBlock);
            } else {
                newLessonBlocks.add(null);
            }
        }
        return newLessonBlocks;
    }

    private LessonBlock cloneLessonBlock(LessonBlock lessonBlock) {
        LessonBlock newLessonBlock = new LessonBlock();
        newLessonBlock.setId(lessonBlock.getId());
        newLessonBlock.setDay(Day.valueOf(lessonBlock.getDay().name()));
        newLessonBlock.setLessonNumber(LessonNumber.valueOf(lessonBlock.getLessonNumber().name()));

        Grade newGrade = new Grade();
        newGrade.setId(lessonBlock.getGrade().getId());
        newGrade.setName(lessonBlock.getGrade().getName());
        newLessonBlock.setGrade(newGrade);

        Subject newSubject = new Subject();
        newSubject.setId(lessonBlock.getSubject().getId());
        newSubject.setName(lessonBlock.getSubject().getName());
        newSubject.setRoomTypeNeeded(RoomType.valueOf(lessonBlock.getSubject().getRoomTypeNeeded().name()));
        newLessonBlock.setSubject(newSubject);

        Teacher newTeacher = new Teacher();
        newTeacher.setId(lessonBlock.getTeacher().getId());
        newTeacher.setFirstName(lessonBlock.getTeacher().getFirstName());
        newTeacher.setLastName(lessonBlock.getTeacher().getLastName());
        newTeacher.setSubjects(cloneSubjects(lessonBlock.getTeacher().getSubjects()));
        newLessonBlock.setTeacher(newTeacher);

        Room newRoom = new Room();
        newRoom.setId(lessonBlock.getRoom().getId());
        newRoom.setNumber(lessonBlock.getRoom().getNumber());
        newRoom.setRoomType(RoomType.valueOf(lessonBlock.getRoom().getRoomType().name()));
        newLessonBlock.setRoom(newRoom);

        return newLessonBlock;
    }

    private List<Subject> cloneSubjects(List<Subject> subjects) {
        List<Subject> newSubjects = new ArrayList<>();
        for (Subject subject : subjects) {
            Subject newSubject = new Subject();
            newSubject.setId(subject.getId());
            newSubject.setName(subject.getName());
            newSubject.setRoomTypeNeeded(RoomType.valueOf(subject.getRoomTypeNeeded().name()));
            newSubjects.add(newSubject);
        }
        return newSubjects;
    }

    private void formatDayComplexity(List<DayComplexity> dayComplexities, int daysInWeek) {
        if (daysInWeek == 5) {
            this.dayComplexities.remove(this.dayComplexities.size() - 1);
        } else {
            for (DayComplexity dayComplexity : this.dayComplexities) {
                int complexity = (int) (dayComplexity.getDayComplexity() * 0.833);
                dayComplexity.setDayComplexity(complexity);
            }
        }
    }

    private SubjectArea findSubjectArea(Subject subject) {
        SubjectArea subjectArea = null;

        find:
        for (SubjectArea subjectAreaElement : this.subjectAreas) {
            for (Subject subjectElement : subjectAreaElement.getSubjects()) {
                if (subject.getName().equals(subjectElement.getName())) {
                    subjectArea = subjectAreaElement;
                    break find;
                }
            }
        }
        return subjectArea;
    }

    private int countAlternation(List<String> subjectAreas) {
        int count = 0;
        for (int i = 0; i < subjectAreas.size(); i++) {
            if (i < subjectAreas.size() - 1) {
                if (!subjectAreas.get(i).equals(subjectAreas.get(i + 1))) {
                    count++;
                }
            }
        }
        return count;
    }

    private int alternationMark(int alternationCount) {
        int mark = 0;
        if (this.daysInWeek == 5) {
            switch (alternationCount) {
                case 5:
                    mark = 40;
                    break;
                case 4:
                    mark = 20;
                    break;
                case 3:
                    mark = 10;
                    break;
                case 2:
                    break;
            }
        } else {
            switch (alternationCount) {
                case 4:
                    mark = 40;
                    break;
                case 3:
                    mark = 20;
                    break;
                case 2:
                case 1:
                    break;
            }
        }
        return mark;
    }

    private List<LessonNumber> findSubjectPositions(Subject subject) {
        List<LessonNumber> subjectPositions = null;

        for (SubjectPosition subjectPosition : this.subjectPositions) {
            if (subjectPosition.getSubject().getName().equals(subject.getName())) {
                subjectPositions = subjectPosition.getPositions();
            }
        }
        return subjectPositions;
    }

    private List<LessonNumber> findTeacherWishes(Teacher teacher) {
        List<LessonNumber> teacherWishes = null;

        for (TeacherWish teacherWish : this.teacherWishes) {
            if (teacherWish.getTeacher().getId().equals(teacher.getId())) {
                teacherWishes = teacherWish.getWishes();
            }
        }
        return teacherWishes;
    }

}
