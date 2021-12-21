package java.com.solvd.timetable;

import java.com.solvd.timetable.algorithm.Algorithm;
import java.com.solvd.timetable.algorithm.Genetic;
import java.com.solvd.timetable.domain.timetable.TimeTable;
import java.com.solvd.timetable.service.TimeTableService;
import java.com.solvd.timetable.service.impl.TimeTableServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class Menu {

    private static final Logger LOGGER = LogManager.getLogger(Menu.class);
    public void show() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nMenu:\n" +
                    "1. Print existing timetable.\n" +
                    "2. Create and print new timetable with our own algorithm.\n" +
                    "3. Create and print new timetable with genetic algorithm.\n" +
                    "4. Exit menu.");
            System.out.println("\nChoose option: ");
            int option = scanner.nextInt();
            TimeTableService timeTableService = new TimeTableServiceImpl();
            switch (option) {
                case 1:
                    TimeTable timeTable = timeTableService.getTimeTable();
                    if (timeTable.getLessonBlocks().size() != 0) {
                        timeTable.printTable();
                    } else {
                        LOGGER.info("There is no existing timetable.");
                    }
                    continue;
                case 2:
                    final int daysInWeek = chooseDaysInWeek();
                    LOGGER.info("\n\nPlease wait. It can takes from 10 seconds to 5 minutes.\n\n");
                    Algorithm algorithm = new Algorithm(daysInWeek);
                    TimeTable newTimeTable = algorithm.createTimeTable();
                    timeTableService.createTimeTable(newTimeTable);
                    newTimeTable.printTable();
                    continue;
                case 3:
                    final int daysInWeekGenetic = chooseDaysInWeek();
                    LOGGER.info("\n\nPlease wait. It will take 20-25 minutes.\n\n");
                    Genetic genetic = new Genetic(daysInWeekGenetic);
                    TimeTable timeTableGenetic = genetic.tryGenetic();
                    timeTableService.createTimeTable(timeTableGenetic);
                    timeTableGenetic.printTable();
                    continue;
                case 4:
                    LOGGER.info("EXIT.\n\n");
                    System.exit(0);
                default:
                    LOGGER.info("You entered wrong value.");
            }
        }
    }

    private static int chooseDaysInWeek() {
        int result;
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the number of days in the school week(5 or 6): ");
            result = scanner.nextInt();
            if (result < 5 && result > 6) {
                LOGGER.info("You entered the wrong number of days(" + result + ").");
                LOGGER.info("Try again...");
            } else {
                break;
            }
        }
        return result;
    }

}
