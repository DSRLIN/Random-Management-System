package service;

import entities.*;

public interface Loanable {
    //可租借属性 租借相关的3个函数
    public void loanByRecommendResult(RoomNumType roomNum,
                                      int startHour,int startMinute,
                                      int lastHour,int lastMinute,
                                      boolean isMultimedia);
}
