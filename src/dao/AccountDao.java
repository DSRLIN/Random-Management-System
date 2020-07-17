package dao;

import java.util.HashMap;

/**
 * 账户表DAO接口——采用将SQL写入到DAO中的设计方式,降低服务层复杂度
 * 插入/更新接口:
 *  注册（插入）——参数：账号（String）、密码（String）
 *      注册成功返回true 账号已存在返回false
 *  修改密码（更新）——参数：账号（String）、新密码（String）
 *      修改成功返回true 账号不存在返回false
 * 查询接口:
 *  查询所有账户（不含密码） —— 返回HashMap<Integer,String> 对应ID与账户 可通过foreach遍历
 *  根据账户获取ID —— 输入账户 若存在则返回ID 否则返回null
 *  根据账号获取密码 —— 输入账号 若存在则返回密码 否则返回null
 */
public interface AccountDao {

    /**
     * 与数据库交互的注册方法
     * @param account 账户
     * @param password 密码
     * @return 是否成功注册
     */
    boolean registerAccount(String account,String password);

    /**
     * 与数据库交互的密码修改方法
     * @param account 账户
     * @param newPassword 新密码
     * @return 是否成功修改密码
     */
    boolean changePassword(String account,String newPassword);


    /**
     * 获取表中全部账户及其ID
     * @return 由表中ID和账户对应组成的哈希图
     */
    HashMap<Integer,String> queryAccountList();

    /**
     * 根据账户获取ID
     * @param account 账户
     * @return ID
     */
    Integer queryID(String account);

    /**
     * 根据账户获取密码（注意权限）
     * @param account 账户
     * @return 密码
     */
    String queryPassword(String account);
}
