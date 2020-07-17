package entities;

/**
 * 租用行为类 用于辅助信息传递
 *  关于这个非实体的类在这里的理由:
 *      基于ER图及数据库关系模式
 *      本项目的数据库实体集为用户User和房间Room
 *      对应的 二者间存在的关系集为借用行为Rent
 *      映射基数是 多对多 一个人可以借用多个房间 一个房间可以被多个人借用
 *      同时关系集也是一张表 因此需要一个类来承载表的信息
 *      虽然在entities里确实不是特别贴切 但再为了这个类造个action包 凑合用着吧
 * @author Pharsalia
 * @version 0.1.0
 */
public class RentAction {
    public int UID;
    public String RID;
    public String start_time;
    public String last_time;
    public boolean isFixed;

    /**
     * 构造函数
     * @param userID 用户ID
     * @param roomID 房间ID
     * @param startTime 借用起始时间
     * @param lastTime 借用持续时间
     * @param bFixed 借用方式是否为固定借用
     */
    public RentAction(int userID,String roomID,
                      String startTime,String lastTime,boolean bFixed){
        this.UID = userID;
        this.RID = roomID;
        this.start_time = startTime;
        this.last_time = lastTime;
        this.isFixed = bFixed;
    }
}
