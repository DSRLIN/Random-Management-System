package dao.impl;

import dao.*;
import entities.*;

import java.sql.*;
import java.util.*;

/**
 * 借用行为表操作类
 * @author Pharsalia
 * @version 0.1.0
 */
public class RentDaoImpl implements RentDao {
    //缓存连接、语句及结果集
    private Connection conn;
    private PreparedStatement pStmt;
    private ResultSet rs;

    //将需要的SQL字符串存储为静态变量以便重复调用
    public final static String insertSQL =
            "INSERT INTO rent_table VALUES (null,?,?,?,?,?);  ";
    public final static String deleteSQL =
            "DELETE FROM rent_table WHERE rent_ID = ?; ";

    public final static String querySQL =
            "SELECT * FROM rent_table WHERE user_ID = ? AND room_ID = ? AND start_time = ? AND last_time = ?; ";
    public final static String querySQL_RentID =
            "SELECT * FROM rent_table WHERE rent_ID = ?; ";

    public final static String queryListSQL =
            "SELECT * FROM rent_table;";
    public final static String queryListSQL_Uid =
            "SELECT * FROM rent_table WHERE user_ID = ?; ";
    public final static String queryListSQL_Rid =
            "SELECT * FROM rent_table WHERE room_ID = ?; ";
    public final static String queryListSQL_Fixed =
            "SELECT * FROM rent_table WHERE is_fixed = ?; ";

    @Override
    public boolean addRent(Integer userID, String roomID, String startTime, String lastTime, boolean isFixed) {
        RentAction r = new RentAction(0,userID,roomID,startTime,lastTime,isFixed);
        return addRent(r) != null;
    }

    @Override
    public boolean deleteRent(Integer userID, String roomID, String startTime, String lastTime) {
        RentAction r = new RentAction(0,userID,roomID,startTime,lastTime,false);
        return deleteRent(r);
    }

    @Override
    public Integer addRent(RentAction rentAction) {
        //接口被调用时的隐含条件:UID及RID均存在
        AccountDao accountDao = new AccountDaoImpl();
        if(accountDao.queryAccount(rentAction.UID) == null) return null;
        RoomDao roomDao = new RoomDaoImpl();
        if(roomDao.queryRoom(rentAction.RID) == null) return null;
        Integer rentNumber = null;
        try {
            conn = BaseDao.getConnection();
            pStmt = conn.prepareStatement(insertSQL);
            pStmt.setObject(1,rentAction.UID);
            pStmt.setObject(2,rentAction.RID);
            pStmt.setObject(3,rentAction.start_time);
            pStmt.setObject(4,rentAction.last_time);
            pStmt.setObject(5,rentAction.isFixed);
            pStmt.execute();
            //若插入成功 则再次查表获得操作ID并返回结果
            int count = pStmt.getUpdateCount();
            if(count > 0){
                /* 偶发数据库连接关闭错误的原理:
                 *      当在try块内直接调用自身方法时
                 *      与sql执行相关的函数会在结束后关闭对象
                 *      但关闭的是this的对象 导致返回到调用点时 对象为已关闭状态
                 *      此时再去关闭这些对象就会导致报错
                 */
                RentDao rentDao = new RentDaoImpl();
                rentNumber = rentDao.queryRentNumber(rentAction);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            BaseDao.closeObject(conn,pStmt,rs);
        }
        return rentNumber;
    }

    @Override
    public boolean deleteRent(RentAction rentAction) {
        Integer rentNumber = queryRentNumber(rentAction);
        return deleteRent(rentNumber);
    }

    @Override
    public boolean deleteRent(Integer rentNumber) {
        if(queryRent(rentNumber) == null) return false;
        try {
            conn = BaseDao.getConnection();
            pStmt = conn.prepareStatement(deleteSQL);
            pStmt.setObject(1,rentNumber);
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
    public RentAction queryRent(Integer rentNumber) {
        if(rentNumber == null) return null;
        RentAction rentAction = null;
        try {
            conn = BaseDao.getConnection();
            pStmt = conn.prepareStatement(querySQL_RentID);
            pStmt.setObject(1,rentNumber);
            rs = pStmt.executeQuery();
            if(rs.isBeforeFirst()){
                int tempRentID = rs.getInt("rent_ID");
                int tempUserID = rs.getInt("user_ID");
                String tempRoomID = rs.getString("room_ID");
                String startTime = rs.getString("start_time");
                String lastTime = rs.getString("last_time");
                boolean isFixed = rs.getBoolean("is_fixed");
                rentAction = new RentAction(tempRentID,tempUserID,tempRoomID,startTime,lastTime,isFixed);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            BaseDao.closeObject(conn,pStmt,rs);
        }
        return rentAction;
    }

    @Override
    public Integer queryRentNumber(RentAction rentAction) {
        if(rentAction == null) return null;
        Integer rentNumber = null;
        try {
            conn = BaseDao.getConnection();
            pStmt = conn.prepareStatement(querySQL);
            pStmt.setObject(1,rentAction.UID);
            pStmt.setObject(2,rentAction.RID);
            pStmt.setObject(3,rentAction.start_time);
            pStmt.setObject(4,rentAction.last_time);
            rs = pStmt.executeQuery();
            if(rs.isBeforeFirst()){
                rentNumber = rs.getInt("rent_ID");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            BaseDao.closeObject(conn,pStmt,rs);
        }
        return rentNumber;
    }

    @Override
    public ArrayList<RentAction> queryRentList() {
        ArrayList<RentAction> rentList = null;
        try {
            conn = BaseDao.getConnection();
            pStmt = conn.prepareStatement(queryListSQL);
            rs = pStmt.executeQuery();
            if(rs.isBeforeFirst()){
                rentList = generateListByResultSet(rs);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            BaseDao.closeObject(conn,pStmt,rs);
        }
        return rentList;
    }

    @Override
    public ArrayList<RentAction> queryRentList(Integer UID) {
        ArrayList<RentAction> rentList = null;
        try {
            conn = BaseDao.getConnection();
            pStmt = conn.prepareStatement(queryListSQL_Uid);
            pStmt.setObject(1,UID);
            rs = pStmt.executeQuery();
            if(rs.isBeforeFirst()){
                rentList = generateListByResultSet(rs);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            BaseDao.closeObject(conn,pStmt,rs);
        }
        return rentList;
    }

    @Override
    public ArrayList<RentAction> queryRentList(String RID) {
        ArrayList<RentAction> rentList = null;
        try {
            conn = BaseDao.getConnection();
            pStmt = conn.prepareStatement(queryListSQL_Rid);
            pStmt.setObject(1,RID);
            rs = pStmt.executeQuery();
            if(rs.isBeforeFirst()){
                rentList = generateListByResultSet(rs);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            BaseDao.closeObject(conn,pStmt,rs);
        }
        return rentList;
    }

    @Override
    public ArrayList<RentAction> queryRentList(boolean isFixed) {
        ArrayList<RentAction> rentList = null;
        try {
            conn = BaseDao.getConnection();
            pStmt = conn.prepareStatement(queryListSQL_Fixed);
            pStmt.setObject(1,isFixed);
            rs = pStmt.executeQuery();
            if(rs.isBeforeFirst()){
                rentList = generateListByResultSet(rs);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            BaseDao.closeObject(conn,pStmt,rs);
        }
        return rentList;
    }

    /**
     * 根据查询结果集生成RentAction对象列表
     * 工具函数 承载冗余代码
     * @param rs 查询结果集
     * @return RentAction对象列表
     */
    private ArrayList<RentAction> generateListByResultSet(ResultSet rs) throws SQLException {
        //TODO:检验通用性
        ArrayList<RentAction> rentList = new ArrayList<>();
        int tempRentID,tempUserID;
        String tempRoomID, startTime, lastTime;
        boolean isFixed;
        RentAction tempRent;
        //从结果集中获取信息
        while(rs.next()){
            tempRentID = rs.getInt("rent_ID");
            tempUserID = rs.getInt("user_ID");
            tempRoomID = rs.getString("room_ID");
            startTime = rs.getString("start_time");
            lastTime = rs.getString("last_time");
            isFixed = rs.getBoolean("is_fixed");
            //根据类别生成子类对象
            tempRent = new RentAction(tempRentID,tempUserID,tempRoomID,startTime,lastTime,isFixed);
            //存入列表
            rentList.add(tempRent);
        }
        //返回结果
        return rentList;
    }
}
