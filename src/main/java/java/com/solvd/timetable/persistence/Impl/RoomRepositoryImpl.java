package java.com.solvd.timetable.persistence.Impl;

import java.com.solvd.timetable.domain.Room;
import java.com.solvd.timetable.persistence.MybatisSessionHolder;
import java.com.solvd.timetable.persistence.RoomRepository;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class RoomRepositoryImpl implements RoomRepository {

    @Override
    public List<Room> getRooms() {
        List<Room> rooms;
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            RoomRepository roomRepository = session.getMapper(RoomRepository.class);
            rooms = roomRepository.getRooms();
        }
        return rooms;
    }

}
