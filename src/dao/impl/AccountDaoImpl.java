package dao.impl;

import dao.AccountDao;
import dao.BaseDao;

import java.sql.*;
import java.util.*;

/**
 * 账户表操作类
 * 未继承BaseDao的原因:
 *  BaseDao被视为工具类 单例 且全部方法均为静态方法 这种状态下再进行继承是没有意义的
 * @author Pharsalia
 * @version 0.1.0
 */
public class AccountDaoImpl implements AccountDao {
    //缓存连接、语句及结果集
    private Connection conn;
    private PreparedStatement pStmt;
    private ResultSet rs;

    //将需要的SQL字符串存储为静态变量以便重复调用
    public final static String insertSQL =
            "INSERT INTO account_table values (null,?,?); ";
    public final static String deleteSQL =
            "DELETE FROM account_table where ACCOUNT = ?; ";
    public final static String updateSQL =
            "UPDATE account_table SET PASSWORD = ? WHERE ACCOUNT = ?; ";

    public final static String queryUidSQL =
            "SELECT UID FROM account_table WHERE ACCOUNT = ?; ";
    public final static String queryAcctSQL =
            "SELECT ACCOUNT FROM account_table WHERE UID = ?; ";
    public final static String queryPwdSQL_Uid =
            "SELECT PASSWORD FROM account_table WHERE UID = ?; ";
    public final static String queryPwdSQL_Acct =
            "SELECT PASSWORD FROM account_table WHERE ACCOUNT = ?; ";
    public final static String queryListSQL =
            "SELECT UID, ACCOUNT FROM account_table; ";


    @Override
    public boolean registerAccount(String account, String password) {
        //当前账号已存在 直接返回false
        if(queryUID(account) != null) return false;
        try {
            conn = BaseDao.getConnection();
            pStmt = conn.prepareStatement(insertSQL);
            pStmt.setObject(1,account);
            pStmt.setObject(2,password);
            pStmt.execute();
            //检测更新行数
            int count = pStmt.getUpdateCount();
            return count > 0;
        }catch (Exception e){
            //出现任何错误时返回false
            e.printStackTrace();
            return false;
        }finally {
            BaseDao.closeObject(conn,pStmt,null);
        }
    }

    @Override
    public boolean deleteAccount(String account) {
        //大致过程与注册一致
        if(queryUID(account) != null) return false;
        try {
            conn = BaseDao.getConnection();
            pStmt = conn.prepareStatement(deleteSQL);
            pStmt.setObject(1,account);
            pStmt.execute();
            int count = pStmt.getUpdateCount();
            return count > 0;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            BaseDao.closeObject(conn,pStmt,null);
        }
    }

    @Override
    public boolean changePassword(String account, String newPassword) {
        if(queryUID(account) == null) return false;
        try {
            conn = BaseDao.getConnection();
            pStmt = conn.prepareStatement(updateSQL);
            //更新时sql先传入密码后传入账户
            pStmt.setObject(2,account);
            pStmt.setObject(1,newPassword);
            pStmt.execute();
            int count = pStmt.getUpdateCount();
            return count > 0;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            BaseDao.closeObject(conn,pStmt,null);
        }
    }

    @Override
    public HashMap<Integer, String> queryAccountList() {
        HashMap<Integer,String> accountList = null;
        try{
            conn = BaseDao.getConnection();
            pStmt = conn.prepareStatement(queryListSQL);
            rs = pStmt.executeQuery();
            if(rs.isBeforeFirst()){
                int tempUID;
                String tempAccount;
                accountList = new HashMap<>();
                while(rs.next()){
                    //从结果集中获取信息并存入哈希图
                    tempUID = rs.getInt("UID");
                    tempAccount = rs.getString("ACCOUNT");
                    accountList.put(tempUID,tempAccount);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            BaseDao.closeObject(conn,pStmt,rs);
        }
        return accountList;
    }

    @Override
    public Integer queryUID(String account) {
        Integer UID = null;
        try{
            //获取连接
            conn = BaseDao.getConnection();
            //生成预编译语句
            pStmt = conn.prepareStatement(queryUidSQL);
            pStmt.setObject(1,account);
            //执行 判断结果
            rs = pStmt.executeQuery();
            //若游标在第一条前 证明存在第一条
            if(rs.isBeforeFirst()) {
                UID = rs.getInt("UID");
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            BaseDao.closeObject(conn,pStmt,rs);
        }
        return UID;
    }

    @Override
    public String queryAccount(Integer UID) {
        //大致过程与查询ID一致
        String account = null;
        try{
            conn = BaseDao.getConnection();
            pStmt = conn.prepareStatement(queryAcctSQL);
            pStmt.setObject(1,UID);
            rs = pStmt.executeQuery();
            if(rs.isBeforeFirst()) {
                account = rs.getString("ACCOUNT");
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            BaseDao.closeObject(conn,pStmt,rs);
        }
        return account;
    }

    @Override
    public String queryPassword(String account) {
        String password = null;
        try{
            conn = BaseDao.getConnection();
            pStmt = conn.prepareStatement(queryPwdSQL_Acct);
            pStmt.setObject(1,account);
            rs = pStmt.executeQuery();
            if(rs.isBeforeFirst()) {
                password = rs.getString("PASSWORD");
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            BaseDao.closeObject(conn,pStmt,rs);
        }
        return password;
    }

    @Override
    public String queryPassword(Integer UID) {
        String password = null;
        try{
            conn = BaseDao.getConnection();
            pStmt = conn.prepareStatement(queryPwdSQL_Uid);
            pStmt.setObject(1,UID);
            rs = pStmt.executeQuery();
            if(rs.isBeforeFirst()) {
                password = rs.getString("PASSWORD");
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            BaseDao.closeObject(conn,pStmt,rs);
        }
        return password;
    }
}
