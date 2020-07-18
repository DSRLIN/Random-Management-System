package dao.impl;

import dao.RoomDao;
import entities.Room;

import java.sql.*;
import java.util.*;

public class RoomDaoImpl implements RoomDao {
    //缓存连接、语句及结果集
    private Connection conn;
    private PreparedStatement pStmt;
    private ResultSet rs;

    //将需要的SQL字符串存储为静态变量以便重复调用
    public final static String insertSQL =
            "";
    public final static String updateSQL =
            "";
    public final static String deleteSQL_Rid =
            "";
    public final static String deleteSQL_Room =
            "";

    public final static String querySQL =
            "";
    public final static String queryListSQL =
            "";
    public final static String queryListSQL_type =
            "";
    public final static String queryListSQL_size =
            "";
    public final static String queryListSQL_multimedia =
            "";

    @Override
    public boolean addRoom(Room room) {
        return false;
    }

    @Override
    public boolean editRoom(Room room) {
        return false;
    }

    @Override
    public boolean deleteRoom(String RID) {
        return false;
    }

    @Override
    public boolean deleteRoom(Room room) {
        return false;
    }

    @Override
    public Room queryRoom(String RID) {
        return null;
    }

    @Override
    public List<Room> queryRoomList() {
        return null;
    }

    @Override
    public List<Room> queryRoomList(String type) {
        return null;
    }

    @Override
    public List<Room> queryRoomList(Integer size) {
        return null;
    }

    @Override
    public List<Room> queryRoomList(Boolean isMultimedia) {
        return null;
    }
}
