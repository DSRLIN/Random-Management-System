package service;

public interface Queryable {
    //可查询属性 查询相关的1个函数
    public boolean isUsed(String roomName,int useHour,int useMinute);
}
