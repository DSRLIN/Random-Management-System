package service;

import entities.*;

public interface Loanable {
    //可租借属性 租借相关的2个函数
    //推荐属性
    public boolean loanByRecommendResult(RoomNumType roomNum,
                                      int startHour,int startMinute,
                                      int lastHour,int lastMinute,
                                      boolean isMultimedia);
    //输入名字租借
    public boolean loanByName(String roomName);
    //注：取消租借由于用户与管理员分层，在分别的接口中进行实现
}
