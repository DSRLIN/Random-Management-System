package service;

import entities.RentAction;
import entities.Room;
import entities.RoomNumType;

import java.util.List;

public interface Queryable {
    //可查询属性 查询相关的1个函数
    //我决定把返回列表类型的两个玩意扔这来
    //租借操作仅通过房间号
    List<Room> queryRecommendResult(RoomNumType roomNum,
                                           int startHour, int startMinute,
                                           int lastHour, int lastMinute,
                                           boolean isMultimedia);

    boolean isUsed(String roomName,int startHour,int startMinute,int endHour,int endMinute);

    List<RentAction> cancelQuery();
}
