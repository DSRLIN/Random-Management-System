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
    //TODO:在ROOM中整一个根据子类对象类型返回字符串的方法
    //      以方便其他层调用传入String type的多态接口

    //缓存连接、语句及结果集
    private Connection conn;
    private PreparedStatement pStmt;
    private ResultSet rs;

    //将需要的SQL字符串存储为静态变量以便重复调用
    public final static String insertSQL =
            "INSERT INTO room_table values (?, ?, ?, ?); ";
    public final static String updateSQL =
            "UPDATE room_table SET TYPE = ?,SIZE = ?,is_multimedia = ? WHERE RID = ?; ";
    public final static String deleteSQL =
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
        //当前房号已存在
        if(queryRoom(room.getRoomName()) != null) return false;
        try{
            conn = BaseDao.getConnection();
            pStmt = conn.prepareStatement(insertSQL);
            pStmt.setObject(1,room.getRoomName());
            //若可被固定占用则为教室
            if(room.getIsFixedTimeUsed()) pStmt.setObject(2,"教室");
            else pStmt.setObject(2,"会议室");
            //根据枚举获取房间大小
            pStmt.setObject(3,room.getRoomNum().getValue());
            pStmt.setObject(4,room.getIsMultiMedia());
            pStmt.execute();
            int count = pStmt.getUpdateCount();
            return count > 0;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            BaseDao.closeObject(conn,pStmt,rs);
        }
    }

    @Override
    public boolean editRoom(Room room) {
        //当前房间不存在
        if(queryRoom(room.getRoomName()) == null) return false;
        try{
            conn = BaseDao.getConnection();
            pStmt = conn.prepareStatement(updateSQL);
            //更新时房号在末尾
            pStmt.setObject(4,room.getRoomName());
            //与添加房间逻辑一致
            if(room.getIsFixedTimeUsed()) pStmt.setObject(2,"教室");
            else pStmt.setObject(1,"会议室");
            pStmt.setObject(2,room.getRoomNum().getValue());
            pStmt.setObject(3,room.getIsMultiMedia());
            pStmt.execute();
            int count = pStmt.getUpdateCount();
            return count > 0;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            BaseDao.closeObject(conn,pStmt,rs);
        }
    }

    @Override
    public boolean deleteRoom(String RID) {
        if(queryRoom(RID) == null) return false;
        try{
            conn = BaseDao.getConnection();
            pStmt = conn.prepareStatement(deleteSQL);
            pStmt.setObject(1,RID);
            pStmt.execute();
            int count = pStmt.getUpdateCount();
            return count > 0;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            BaseDao.closeObject(conn,pStmt,rs);
        }
    }

    @Override
    public boolean deleteRoom(Room room) {
        return deleteRoom(room.getRoomName());
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
                if(roomType.equals("教室")) targetRoom = new Classroom(roomID,roomSIZE,isMultimedia);
                else targetRoom = new MeetingRoom(roomID,roomSIZE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            BaseDao.closeObject(conn,pStmt,rs);
        }
        return targetRoom;
    }

    @Override
    public ArrayList<Room> queryRoomList() {
        ArrayList<Room> roomList = null;
        try{
            conn = BaseDao.getConnection();
            pStmt = conn.prepareStatement(queryListSQL);
            rs = pStmt.executeQuery();
            if(rs.isBeforeFirst()){
                //工具函数 降低代码冗余
                roomList = generateListByResultSet(rs);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            BaseDao.closeObject(conn,pStmt,rs);
        }
        return roomList;
    }

    @Override
    public ArrayList<Room> queryRoomList(String type) {
        ArrayList<Room> roomList = null;
        try{
            conn = BaseDao.getConnection();
            pStmt = conn.prepareStatement(queryListSQL_Type);
            pStmt.setObject(1,type);
            rs = pStmt.executeQuery();
            if(rs.isBeforeFirst()){
               roomList = generateListByResultSet(rs);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            BaseDao.closeObject(conn,pStmt,rs);
        }
        return roomList;
    }

    @Override
    public ArrayList<Room> queryRoomList(RoomNumType size) {
        ArrayList<Room> roomList = null;
        try{
            conn = BaseDao.getConnection();
            pStmt = conn.prepareStatement(queryListSQL_Size);
            pStmt.setObject(1,size.getValue());
            rs = pStmt.executeQuery();
            if(rs.isBeforeFirst()){
                roomList = generateListByResultSet(rs);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            BaseDao.closeObject(conn,pStmt,rs);
        }
        return roomList;
    }

    @Override
    public ArrayList<Room> queryRoomList(Boolean isMultimedia) {
        ArrayList<Room> roomList = null;
        try{
            conn = BaseDao.getConnection();
            pStmt = conn.prepareStatement(queryListSQL_Multimedia);
            pStmt.setObject(1,isMultimedia);
            rs = pStmt.executeQuery();
            if(rs.isBeforeFirst()){
                roomList = generateListByResultSet(rs);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            BaseDao.closeObject(conn,pStmt,rs);
        }
        return roomList;
    }

    /**
     * 根据查询结果集生成Room对象列表
     * 工具函数 承载冗余代码
     * @param rs 查询结果集
     * @return Room对象列表
     */
    private ArrayList<Room> generateListByResultSet(ResultSet rs) throws SQLException{
        //TODO:检验通用性
        String tempRID, tempType;
        boolean tempIM;
        RoomNumType tempSize;
        Room tempRoom;
        ArrayList<Room> roomList = new ArrayList<>();
        //从结果集中获取信息
        while(rs.next()){
            tempRID = rs.getString("RID");
            tempType = rs.getString("TYPE");
            tempSize = RoomNumType.getEnum(rs.getInt("SIZE"));
            tempIM = rs.getBoolean("is_multimedia");
            //根据类别生成子类对象
            if(tempType.equals("教室")){
                tempRoom = new Classroom(tempRID,tempSize,tempIM);
            }else{
                tempRoom = new MeetingRoom(tempRID,tempSize);
            }
            //存入列表
            roomList.add(tempRoom);
        }
        //返回结果
        return roomList;
    }
}
