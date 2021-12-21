package java.com.solvd.timetable.persistence;

import java.com.solvd.timetable.domain.Room;

import java.util.List;

public interface RoomRepository {

    List<Room> getRooms();

}
