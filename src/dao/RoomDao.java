package dao;

import entities.Room;

import java.util.List;

/**
 * 房间表DAO接口
 *  +增、删、改相关接口:
 *      添加房间 —— 参数:Room对象
 *          添加成功返回true 房号已存在返回false
 *      修改房间信息 —— 参数:Room对象
 *          返回是否完成修改 修改的目标为传入对象房号对应的条目 将对象所有属性赋值给该行 请确认输入的是包含完整信息的对象
 *      删除房间 —— 参数:房号/Room对象
 *          删除成功返回true 房号或Room对象中房号对应的条目不存在返回false
 *  +查询接口:
 *      查询所有房间 —— 返回List<Room> 包含对象对应表中全部的房间信息
 *      根据房间号获取对应房间 —— 输入房间号 若房间存在返回Room 否则返回null
 *      根据房间类别获取房间列表 —— 输入类型 返回List<Room> 若无对应类型房间则返回null
 *      根据房间大小获取房间列表 —— 输入大小 返回List<Room> 若无对应大小房间则返回null
 *      根据房间是否支持多媒体获取房间列表 —— 输入是否为多媒体房间 返回List<Room> 若无对应房间则返回null
 * @author Pharsalia
 * @version 0.1.0
 */
public interface RoomDao {
    //TODO：需要增删改吗 虽然房间列表可以固化 但是这些功能作为管理员功能 做出来指不定分高
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
    List<Room> queryRoomList();
    /**
     * 根据房间类别查询房间（多态）
     * @param type 房间类型（教室/会议室）
     * @return 房间对象列表
     */
    List<Room> queryRoomList(String type);
    /**
     * 根据房间大小查询房间（多态）
     * @param size 房间大小（40/60/200）
     * @return 房间对象列表
     */
    List<Room> queryRoomList(Integer size);
    /**
     * 根据房间是否支持多媒体查询房间（多态）
     * @param isMultimedia 是否支持多媒体（T/F）
     * @return 房间对象列表
     */
    List<Room> queryRoomList(Boolean isMultimedia);
}
