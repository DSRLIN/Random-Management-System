package entities;

import java.util.ArrayList;

public class Classroom extends Room {
    protected boolean isFixedTimeUsed = true; //可否固定占用
    protected ArrayList<Long> fixedUsingTimeStart;  //同自由占用时间 以秒为单位
    protected ArrayList<Long> fixedUsingTimeEnd;    //结束时间

    public Classroom(String roomName,RoomNumType roomNum,boolean isMultimedia){
        this.roomName = roomName;
        this.roomNum = roomNum;
        this.isMultimedia = isMultimedia;
    }

    public ArrayList<Long> getFixedUsingTimeStart() { return this.fixedUsingTimeStart; }
    public ArrayList<Long> getFixedUsingTimeEnd() { return this.fixedUsingTimeEnd; }

    public void addNewFixedUsingTime(int startHour,int startMinute,
                                    int lastHour,int lastMinute){
        //起始时间的小时与分钟
        //所需要占用的小时与分钟
        long startLong = (startHour * 3600) + (startMinute * 60);
        long endLong = startLong + ((lastHour * 3600)+(lastMinute * 60));
        this.fixedUsingTimeStart.add(startLong);
        this.fixedUsingTimeEnd.add(endLong);
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Classroom)){
            return false;
        }
        if(this == obj){
            return true;
        }
        Classroom cls = (Classroom)obj;
        return roomNum.equals(cls.roomNum)&&
               roomName.equals(cls.roomName)&&
               fixedUsingTimeStart.equals(cls.fixedUsingTimeStart)&&
               fixedUsingTimeEnd.equals(cls.fixedUsingTimeEnd)&&
               freeUsingTimeStart.equals(cls.freeUsingTimeStart)&&
               freeUsingTimeEnd.equals(cls.freeUsingTimeEnd)&&
               isFixedTimeUsed == cls.isFixedTimeUsed&&
               isMultimedia == cls.isMultimedia;

    }
}
