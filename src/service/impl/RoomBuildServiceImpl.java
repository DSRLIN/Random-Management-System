package service.impl;

import dao.RoomDao;
import dao.impl.RoomDaoImpl;
import entities.Classroom;
import entities.MeetingRoom;
import entities.Room;
import service.RoomBuildService;

public class RoomBuildServiceImpl implements RoomBuildService {
    @Override
    public boolean buildClassroom(Classroom room) {
        RoomDao rd = new RoomDaoImpl();
        return rd.addRoom(room);
    }
    public boolean buildMeetingRoom(MeetingRoom room) {
        RoomDao rd = new RoomDaoImpl();
        return rd.addRoom(room);
    }
    //TODO:调用dao层接口 在数据库中添加新Room内容
}
