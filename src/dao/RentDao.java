package dao;

import entities.RentAction;

import java.util.List;

/**
 * 租用表DAO接口
 *  +增、删、改相关接口:
 *      新增借用 —— 参数:账户ID,房间号,起始时间,持续时间,是否为固定借用(T/F)
 *          增加成功返回true 未找到用户ID或房间号返回false 不对借用时间段是否冲突进行检测
 *      取消借用 —— 参数:账户ID,房间号,起始时间,持续时间
 *          只有这四项参数完全匹配才会执行取消操作 理论上不用确定是否固定占用
 *      修改借用 —— 请自行删除对应条目并新增条目
 *  +查询接口:
 *      查询所有借用操作 —— 返回List<RentAction> 包含所有借用行为对象
 *      根据用户ID查询借用列表 —— 输入用户ID 返回List<RentAction> 若无该用户借用行为则返回null
 *      根据房间ID查询借用列表 —— 输入房间ID 返回List<RentAction> 若无该房间则返回null
 *      根据是否为固定借用返回借用行为列表 —— 输入是否固定借用 返回List<RentAction> 若列表为空则返回null
 * @author Pharsalia
 * @version 0.1.0
 */
public interface RentDao {
    //TODO：需要根据时间段查询借用行为的接口吗（
    // 需要的话我需要一个时间字符串的标准以便比较
    // 或是 拿着整体表自己从里面取需求时间段？

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
     * 查询所有借用操作
     * @return 借用操作对象列表
     */
    List<RentAction> queryRentList();

    /**
     * 根据借用者查询借用操作(多态)
     * @param UID 借用者ID
     * @return 借用操作对象列表
     */
    List<RentAction> queryRentList(Integer UID);

    /**
     * 根据房间号查询借用操作(多态)
     * @param RID 房间ID
     * @return 借用操作对象列表
     */
    List<RentAction> queryRentList(String RID);

    /**
     * 根据是否固定时段借用查询借用操作(多态)
     * @param isFixed 是否为固定时段借用
     * @return 借用操作对象列表
     */
    List<RentAction> queryRentList(boolean isFixed);
}
