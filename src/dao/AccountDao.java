package dao;

import java.util.HashMap;

/**
 * 账户表DAO接口
 *  +增、删、改相关接口:
 *      注册账户    ——参数：账号（String）、密码（String）
 *          注册成功返回true 账号已存在返回false
 *      修改密码    ——参数：账号（String）、新密码（String）
 *          修改成功返回true 账号不存在返回false
 *      删除账户    ——参数：账号（String）
 *          删除成功返回true 账号不存在返回false
 *  +查询接口:
 *      查询所有账户（不含密码） —— 返回HashMap<Integer,String> 对应ID与账号 可通过foreach遍历
 *      根据账号获取ID —— 输入账号 若存在则返回ID 否则返回null
 *      根据ID获取账号 —— 输入ID 若存在则返回账号 否则返回null
 *      根据ID获取密码 —— 输入ID 若存在则返回密码 否则返回null
 *      根据账号获取密码 —— 输入账号 若存在则返回密码 否则返回null
 * @author Pharsalia
 * @version 0.2.0
 */
public interface AccountDao {
    /**
     * 注册账号
     * @param account 账号
     * @param password 密码
     * @return 是否成功注册
     */
    boolean registerAccount(String account,String password);

    /**
     * 删除账号
     * PS:"小加加：你号没了"
     * @param account 账号
     * @return 是否成功删号
     */
    boolean deleteAccount(String account);

    /**
     * 修改密码
     * @param account 账号
     * @param newPassword 新密码
     * @return 是否成功修改密码
     */
    boolean changePassword(String account,String newPassword);


    /**
     * 获取全部账号及ID
     * @return 由表中ID和账号对应组成的哈希图
     */
    HashMap<Integer,String> queryAccountList();

    /**
     * 根据账号查询ID
     * @param account 账号
     * @return ID
     */
    Integer queryUID(String account);

    /**
     * 根据ID查询账号
     * @param UID 用户ID
     * @return 账号
     */
    String queryAccount(Integer UID);

    /**
     * 根据账号查询密码（注意权限）
     * @param account 账号
     * @return 密码
     */
    String queryPassword(String account);
    /**
     * 根据ID查询密码（多态）
     * @param UID 用户ID
     * @return 密码
     */
    String queryPassword(Integer UID);
}
