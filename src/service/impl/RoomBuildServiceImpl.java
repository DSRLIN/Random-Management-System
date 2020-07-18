package service.impl;

import dao.RoomDao;
import dao.impl.RoomDaoImpl;
import entities.Room;
import service.RoomBuildService;

public class RoomBuildServiceImpl implements RoomBuildService {
    @Override
    public boolean buildRoom(Room room) {
        RoomDao rd = new RoomDaoImpl();
        return rd.addRoom(room);
    }
    //TODO:调用dao层接口 在数据库中添加新Room内容
}
