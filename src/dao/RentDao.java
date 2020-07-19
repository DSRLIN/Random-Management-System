package dao;

import entities.RentAction;

import java.util.ArrayList;

/**
 * 租用表DAO接口
 *  +增、删、改相关接口:
 *      添加借用 —— 参数:账户ID,房间号,起始时间,持续时间,是否为固定借用(T/F)
 *          增加成功返回true 未找到用户ID或房间号返回false 不对借用时间段是否冲突进行检测
 *      取消借用 —— 参数:账户ID,房间号,起始时间,持续时间
 *          只有这四项参数完全匹配才会执行取消操作 取消成功返回true 未发现条目返回false
 *      修改借用 —— 请自行删除对应条目并新增条目
 *      0.2.0新增:
 *          添加借用 —— 参数:RentAction RentNumber项随意输入 不会使用
 *              根据RentAction中的信息添加操作 添加成功返回借用操作ID 未找到用户ID或房间号返回null
 *          取消借用 —— 参数:RentAction RentNumber项随意输入 不会使用
 *              根据RentAction中的信息查找条目并执行删除 取消成功返回true
 *          取消借用 —— 参数:借用操作ID
 *              查找表中的借用操作ID对应条目并删除 删除成功返回true 未发现则返回false
 *  +查询接口:
 *      查询所有借用操作 —— 返回List<RentAction> 包含所有借用行为对象
 *      根据用户ID查询借用列表 —— 输入用户ID 返回List<RentAction> 若无该用户借用行为则返回null
 *      根据房间ID查询借用列表 —— 输入房间ID 返回List<RentAction> 若无该房间则返回null
 *      根据是否为固定借用返回借用行为列表 —— 输入是否固定借用 返回List<RentAction> 若列表为空则返回null
 *      0.2.0新增:
 *          根据借用ID查找借用操作 —— 输入借用记录ID 返回对应条目生成的RentAction对象 若无则返回null
 *          根据RentAction查找借用操作 —— 无视借用记录ID 返回剩余四项参数完全匹配的第一条目的ID
 * @author Pharsalia
 * @version 0.2.0
 */
public interface RentDao {
    /**
     * 新增借用操作
     * @param userID 借用者ID
     * @param roomID 借用房间ID
     * @param startTime 起始时间
     * @param lastTime 持续时间
     * @param isFixed 是否为固定时间段借用
     * @return 是否成功借用
     */
    boolean addRent(Integer userID, String roomID,
                    String startTime, String lastTime, boolean isFixed);

    /**
     * 取消借用操作
     * 只有四项参数完全匹配才会执行删除操作
     * 若此前数据库中存在完全相同的条目则会一并删除
     * @param userID 借用者ID
     * @param roomID 借用房间ID
     * @param startTime 起始时间
     * @param lastTime 持续时间
     * @return 是否成功删除
     */
    boolean deleteRent(Integer userID, String roomID,
                       String startTime, String lastTime);

    /**
     * 新增借用操作
     * @param rentAction 借用行为信息
     * @return 借用操作条目ID
     */
    Integer addRent(RentAction rentAction);

    /**
     * 取消借用操作
     * @param rentAction 借用行为信息
     * @return 是否成功取消
     */
    boolean deleteRent(RentAction rentAction);

    /**
     * 取消借用操作
     * @param rentNumber 借用操作条目
     * @return 是否成功取消
     */
    boolean deleteRent(Integer rentNumber);

    /**
     * 根据操作ID查找借用信息
     * @param rentNumber 借用操作ID
     * @return 借用行为信息
     */
    RentAction queryRent(Integer rentNumber);

    /**
     * 根据借用信息查找操作ID
     * @param rentAction 借用行为信息
     * @return 借用操作ID
     */
    Integer queryRentNumber(RentAction rentAction);

    /**
     * 查询所有借用操作
     * @return 借用操作对象列表
     */
    ArrayList<RentAction> queryRentList();

    /**
     * 根据借用者查询借用操作(多态)
     * @param UID 借用者ID
     * @return 借用操作对象列表
     */
    ArrayList<RentAction> queryRentList(Integer UID);

    /**
     * 根据房间号查询借用操作(多态)
     * @param RID 房间ID
     * @return 借用操作对象列表
     */
    ArrayList<RentAction> queryRentList(String RID);

    /**
     * 根据是否固定时段借用查询借用操作(多态)
     * @param isFixed 是否为固定时段借用
     * @return 借用操作对象列表
     */
    ArrayList<RentAction> queryRentList(boolean isFixed);
}
