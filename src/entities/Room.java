package entities;
import java.util.ArrayList;
public abstract class Room {
    protected String roomName;        //房间号
    protected RoomNumType roomNum;    //题目要求的三种类型
    protected ArrayList<Long> freeUsingTimeStart;  //使用两个数组对占用时间段进行存储 以秒为单位
    protected ArrayList<Long> freeUsingTimeEnd;    //校验时则只需按照索引上下进行比较查错即可
    //至于数量直接调用数组本身的长度查询吧（
    protected boolean isMultimedia;    //是否为多媒体教室
    protected boolean isFixedTimeUsed; //可否固定占用

    public String getRoomName() { return this.roomName; }
    public RoomNumType getRoomNum() { return this.roomNum; }
    public ArrayList<Long> getFreeUsingTimeStart() { return this.freeUsingTimeStart; }
    public ArrayList<Long> getFreeUsingTimeEnd() { return this.freeUsingTimeEnd; }
    public boolean getIsMultiMedia() { return this.isMultimedia; }
    public boolean getIsFixedTimeUsed() { return this.isFixedTimeUsed; }

    //注：多媒体属性及是否可固定占用属性用户不得修改 在初始化时直接决定
    // usingTime的修改单独使用函数 不得直接更改整个数组
    public void setRoomName(String newRoomName) { this.roomName = newRoomName; }

    public void setRoomNum(RoomNumType newRoomNum) { this.roomNum = newRoomNum; }

    public void addNewFreeUsingTime(int startHour,int startMinute,
                                    int lastHour,int lastMinute){
        //起始时间的小时与分钟
        //所需要占用的小时与分钟
        long startLong = (startHour * 3600) + (startMinute * 60);
        long endLong = startLong + ((lastHour * 3600)+(lastMinute * 60));
        if(this.freeUsingTimeStart == null){
            this.freeUsingTimeStart = new ArrayList<Long>();
            this.freeUsingTimeStart.add(startLong);
            this.freeUsingTimeEnd = new ArrayList<Long>();
            this.freeUsingTimeEnd.add(startLong);
        }else{
            this.freeUsingTimeStart.add(startLong);
            this.freeUsingTimeEnd.add(endLong);
        }
    }

}
