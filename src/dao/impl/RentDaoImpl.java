package dao.impl;

import dao.RentDao;
import entities.RentAction;

import java.sql.*;
import java.util.*;

/**
 * 借用行为表操作类
 * @author Pharsalia
 * @version 0.0.1
 */
public class RentDaoImpl implements RentDao {
    //缓存连接、语句及结果集
    private Connection conn;
    private PreparedStatement pStmt;
    private ResultSet rs;

    //将需要的SQL字符串存储为静态变量以便重复调用
    public final static String insertSQL = "";
    public final static String deleteSQL = "";

    public final static String queryListSQL = "";
    public final static String queryListSQL_Uid = "";
    public final static String queryListSQL_Rid = "";
    public final static String queryListSQL_Fixed = "";

    @Override
    public boolean addRent(Integer userID, String roomID, String startTime, String lastTime, boolean isFixed) {
        return false;
    }

    @Override
    public boolean deleteRent(Integer userID, String roomID, String startTime, String lastTime) {
        return false;
    }

    @Override
    public int addRent(RentAction rentAction) {
        return 0;
    }

    @Override
    public boolean deleteRent(RentAction rentAction) {
        return false;
    }

    @Override
    public boolean deleteRent(int rentNumber) {
        return false;
    }

    @Override
    public RentAction queryRent(int rentNumber) {
        return null;
    }

    @Override
    public int queryRentNumber(RentAction rentAction) {
        return 0;
    }

    @Override
    public ArrayList<RentAction> queryRentList() {
        return null;
    }

    @Override
    public ArrayList<RentAction> queryRentList(Integer UID) {
        return null;
    }

    @Override
    public ArrayList<RentAction> queryRentList(String RID) {
        return null;
    }

    @Override
    public ArrayList<RentAction> queryRentList(boolean isFixed) {
        return null;
    }
}
