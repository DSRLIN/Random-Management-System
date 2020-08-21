package service;

public interface Loanable {
    //可租借属性 租借相关的1个函数

    //输入名字租借
    boolean loanByName(String roomName,
                              int startHour,int startMinute,
                              int lastHour,int lastMinute);
    //注：取消租借由于用户与管理员分层，在分别的接口中进行实现
}
