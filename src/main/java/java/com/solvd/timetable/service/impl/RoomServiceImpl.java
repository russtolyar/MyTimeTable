package java.com.solvd.timetable.service.impl;


import java.com.solvd.timetable.domain.Room;
import java.com.solvd.timetable.persistence.Impl.RoomRepositoryImpl;
import java.com.solvd.timetable.persistence.RoomRepository;
import java.com.solvd.timetable.service.RoomService;
import java.util.List;

public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl() {
        this.roomRepository = new RoomRepositoryImpl();
    }

    @Override
    public List<Room> getAll() {
        return roomRepository.getRooms();
    }

}
