package java.com.solvd.timetable.service;


import java.com.solvd.timetable.domain.timetable.TimeTable;

public interface TimeTableService {

    void createTimeTable(TimeTable timeTable);

    TimeTable getTimeTable();

}
