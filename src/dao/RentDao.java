package dao;

/**
 * 租用表DAO接口
 *  +增、删、改相关接口:
 *      新增借用 —— 参数:账户ID,房间号,起始时间,持续时间,是否为固定借用(T/F)
 *          增加成功返回true 未找到用户ID或房间号返回false 不对借用时间段是否冲突进行检测
 *      取消借用 —— 参数:账户ID,房间号,起始时间,持续时间
 *          只有这四项参数完全匹配才会执行取消操作 理论上不用确定是否固定占用
 *  +查询接口:
 *      查询所有借用操作 —— 返回List<RentAction> 包含所有借用行为对象
 * @author Pharsalia
 * @version 0.1.0
 */
public interface RentDao {

}
