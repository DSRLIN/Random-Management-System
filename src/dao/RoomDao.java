package dao;

import entities.Room;
import entities.RoomNumType;

import java.util.ArrayList;

/**
 * 房间表DAO接口
 *  +增、删、改相关接口:
 *      添加房间 —— 参数:Room对象
 *          添加成功返回true 房号已存在返回false
 *      修改房间信息 —— 参数:Room对象
 *          返回是否完成修改 修改的目标为传入对象房号对应的条目 将对象所有属性赋值给该行 请确认输入的是包含完整信息的对象
 *      删除房间 —— 参数:房号String/Room对象
 *          删除成功返回true 房号或Room对象中房号对应的条目不存在返回false
 *  +查询接口:
 *      查询所有房间 —— 返回List<Room> 包含对象对应表中全部的房间信息
 *      根据房间号获取对应房间 —— 输入房间号 若房间存在返回Room 否则返回null
 *      根据房间类别获取房间列表 —— 输入类型 返回List<Room> 若无对应类型房间则返回null
 *      根据房间大小获取房间列表 —— 输入大小 返回List<Room> 若无对应大小房间则返回null
 *      根据房间是否支持多媒体获取房间列表 —— 输入是否为多媒体房间 返回List<Room> 若无对应房间则返回null
 * @author Pharsalia
 * @version 0.1.1
 */
public interface RoomDao {
    /**
     * 添加可借用房间
     * @param room 房间对象
     * @return 是否成功添加
     */
    boolean addRoom(Room room);

    /**
     * 修改房间信息
     * 目标为传入对象房号的对应条目
     * 请确保传入了包含完整信息的对象
     * @param room 房间对象
     * @return 是否完成修改
     */
    boolean editRoom(Room room);

    /**
     * 删除指定房间(多态)
     * @param RID 房间号
     * @return 是否成功删除
     */
    boolean deleteRoom(String RID);
    /**
     * 删除指定房间(多态)
     * @param room 房间对象
     * @return 是否成功删除
     */
    boolean deleteRoom(Room room);

    /**
     * 根据房间号获取房间对象
     * @return 房间对象
     */
    Room queryRoom(String RID);

    /**
     * 查询所有房间
     * @return 房间对象列表
     */
    ArrayList<Room> queryRoomList();
    /**
     * 根据房间类别查询房间（多态）
     *  有关为何这里没有采用传入是否可固定占用的布尔值
     *      这四个查询函数采用了多态的形式 如果这个参数是布尔值的话会和后面的是否多媒体冲突
     *      而是否多媒体没有取代的方式 只能采取输入字符串的方法规避一下
     * @param type 房间类型（教室/会议室）
     * @return 房间对象列表
     */
    ArrayList<Room> queryRoomList(String type);
    /**
     * 根据房间大小查询房间（多态）
     * @param size 房间大小（40/60/200）
     * @return 房间对象列表
     */
    ArrayList<Room> queryRoomList(RoomNumType size);
    /**
     * 根据房间是否支持多媒体查询房间（多态）
     * @param isMultimedia 是否支持多媒体（T/F）
     * @return 房间对象列表
     */
    ArrayList<Room> queryRoomList(Boolean isMultimedia);
}
