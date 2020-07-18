package dao.impl;

import dao.BaseDao;
import dao.RoomDao;
import entities.Classroom;
import entities.MeetingRoom;
import entities.Room;
import entities.RoomNumType;

import java.sql.*;
import java.util.*;

/**
 * 可借用房间表操作类
 * @author Pharsalia
 * @version 0.1.0
 */
public class RoomDaoImpl implements RoomDao {
    //缓存连接、语句及结果集
    private Connection conn;
    private PreparedStatement pStmt;
    private ResultSet rs;

    //将需要的SQL字符串存储为静态变量以便重复调用
    public final static String insertSQL =
            "INSERT INTO room_table values (?, ?, ?, ?); ";
    public final static String updateSQL =
            "UPDATE room_table SET RID = ?,TYPE = ?,SIZE = ?,is_multimedia = ? WHERE RID = ?; ";
    public final static String deleteSQL_Rid =
            "DELETE FROM room_table where RID = ?; ";

    public final static String querySQL =
            "SELECT * FROM room_table WHERE RID = ?;";
    public final static String queryListSQL =
            "SELECT * FROM room_table; ";
    public final static String queryListSQL_Type =
            "SELECT * FROM room_table WHERE TYPE = ?; ";
    public final static String queryListSQL_Size =
            "SELECT * FROM room_table WHERE SIZE = ?; ";
    public final static String queryListSQL_Multimedia =
            "SELECT * FROM room_table WHERE is_multimedia = ?; ";

    @Override
    public boolean addRoom(Room room) {
        try{

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            BaseDao.closeObject(conn,pStmt,rs);
        }
        return false;
    }

    @Override
    public boolean editRoom(Room room) {
        try{

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            BaseDao.closeObject(conn,pStmt,rs);
        }
        return false;
    }

    @Override
    public boolean deleteRoom(String RID) {
        try{

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            BaseDao.closeObject(conn,pStmt,rs);
        }
        return false;
    }

    @Override
    public boolean deleteRoom(Room room) {
        try{

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            BaseDao.closeObject(conn,pStmt,rs);
        }
        return false;
    }

    @Override
    public Room queryRoom(String RID) {
        Room targetRoom = null;
        try{
            conn = BaseDao.getConnection();
            pStmt = conn.prepareStatement(querySQL);
            pStmt.setObject(1,RID);
            rs = pStmt.executeQuery();
            if(rs.isBeforeFirst()){
                String roomID = rs.getString("RID");
                String roomType = rs.getString("TYPE");
                RoomNumType roomSIZE = RoomNumType.getEnum(rs.getInt("SIZE"));
                boolean isMultimedia = rs.getBoolean("is_multimedia");
                if(roomType.equals("教室")){
                    targetRoom = new Classroom(roomID,roomSIZE,isMultimedia);
                }else if(roomType.equals("会议室")){
                    targetRoom = new MeetingRoom(roomID,roomSIZE);
                }else{
                    System.out.println("Debug:"+roomType);
                    return null;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            BaseDao.closeObject(conn,pStmt,rs);
        }
        return targetRoom;
    }

    @Override
    public List<Room> queryRoomList() {
        try{

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            BaseDao.closeObject(conn,pStmt,rs);
        }
        return null;
    }

    @Override
    public List<Room> queryRoomList(String type) {
        try{

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            BaseDao.closeObject(conn,pStmt,rs);
        }
        return null;
    }

    @Override
    public List<Room> queryRoomList(Integer size) {
        try{

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            BaseDao.closeObject(conn,pStmt,rs);
        }
        return null;
    }

    @Override
    public List<Room> queryRoomList(Boolean isMultimedia) {
        try{

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            BaseDao.closeObject(conn,pStmt,rs);
        }
        return null;
    }
}
