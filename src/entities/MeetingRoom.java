package entities;

public class MeetingRoom extends Room {
    protected boolean isMultimedia = false;    //是否为多媒体教室
    protected boolean isFixedTimeUsed = false; //可否固定占用

    MeetingRoom(String roomName,RoomNumType roomNum){
        this.roomName = roomName;
        this.roomNum = roomNum;
    }
}
