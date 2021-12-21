package java.com.solvd.timetable.service.impl;

import java.com.solvd.timetable.domain.Grade;
import java.com.solvd.timetable.domain.timetable.Day;
import java.com.solvd.timetable.domain.timetable.LessonBlock;
import java.com.solvd.timetable.domain.timetable.LessonNumber;
import java.com.solvd.timetable.domain.timetable.TimeTable;
import java.com.solvd.timetable.persistence.Impl.TimeTableRepositoryImpl;
import java.com.solvd.timetable.persistence.TimeTableRepository;
import java.com.solvd.timetable.service.TimeTableService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimeTableServiceImpl implements TimeTableService {

    private final TimeTableRepository timeTableRepository;

    public TimeTableServiceImpl() {
        this.timeTableRepository = new TimeTableRepositoryImpl();
    }

    @Override
    public void createTimeTable(TimeTable timeTable) {
        timeTableRepository.deleteTimeTable();
        List<LessonBlock> lessonBlocks = timeTable.getLessonBlocks();
        lessonBlocks.stream()
                .filter(lessonBlock -> lessonBlock != null)
                .forEach(timeTableRepository::createTimeTable);
    }

    @Override
    public TimeTable getTimeTable() {
        List<LessonBlock> lessonBlocks = timeTableRepository.getLessonBlocks();
        TimeTable timeTable = formatTimeTable(lessonBlocks);
        timeTable.setLessonBlocks(lessonBlocks);
        return timeTable;
    }

    private TimeTable formatTimeTable(List<LessonBlock> lessonBlocks) {
        List<LessonNumber> lessonNumbersInTimetable = Arrays.asList(LessonNumber.values());
        int maxLessonCount = lessonNumbersInTimetable.size();
        int daysInWeek = daysInWeek(lessonBlocks);
        int gradesCount = gradesInTimetable(lessonBlocks);

        dayLoop:
        for (int i = 0; i < daysInWeek; i++) {
            int day = i * gradesCount * maxLessonCount;

            gradeLoop:
            for (int j = 0; j < gradesCount; j++) {
                int grade = j * maxLessonCount;

                for (int lesson = 0; lesson < maxLessonCount; lesson++) {
                    int index = day + grade + lesson;
                    if (index < lessonBlocks.size() - 1) {
                        if (!lessonBlocks.get(index).getGrade().getName().equals(lessonBlocks.get(index + 1).getGrade().getName())) {
                            for (int x = 1; x < maxLessonCount - lesson; x++) {
                                LessonBlock lessonBlock = null;
                                lessonBlocks.add(index + x, lessonBlock);
                            }
                            continue gradeLoop;
                        }
                    } else {
                        for (int x = 1; x < maxLessonCount - lesson; x++) {
                            LessonBlock lessonBlock = null;
                            lessonBlocks.add(index + x, lessonBlock);
                        }
                        break dayLoop;
                    }
                }
            }
        }

        TimeTable timeTable = new TimeTable();
        timeTable.setLessonBlocks(lessonBlocks);
        return timeTable;
    }

    private int daysInWeek(List<LessonBlock> lessonBlocks) {
        List<Day> daysInTimetable = new ArrayList<>();
        for (LessonBlock lessonBlock : lessonBlocks) {
            if (lessonBlock != null) {
                if (daysInTimetable.isEmpty()) {
                    daysInTimetable.add(lessonBlock.getDay());
                } else {
                    int dayCount = 0;
                    for (Day day : daysInTimetable) {
                        if (!day.name().equals(lessonBlock.getDay().name())) {
                            dayCount++;
                        }
                    }
                    if (dayCount == daysInTimetable.size()) {
                        daysInTimetable.add(lessonBlock.getDay());
                    }
                }
            }
        }
        return daysInTimetable.size();
    }

    private int gradesInTimetable(List<LessonBlock> lessonBlocks) {
        List<Grade> gradesInTimetable = new ArrayList<>();
        for (LessonBlock lessonBlock : lessonBlocks) {
            if (lessonBlock != null) {
                if (gradesInTimetable.isEmpty()) {
                    gradesInTimetable.add(lessonBlock.getGrade());
                } else {
                    int gradeCount = 0;
                    for (Grade grade : gradesInTimetable) {
                        if (!grade.getName().equals(lessonBlock.getGrade().getName())) {
                            gradeCount++;
                        }
                    }
                    if (gradeCount == gradesInTimetable.size()) {
                        gradesInTimetable.add(lessonBlock.getGrade());
                    }
                }
            }
        }
        return gradesInTimetable.size();
    }

}