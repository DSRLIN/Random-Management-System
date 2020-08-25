package entities;

public class MeetingRoom extends Room {
    protected boolean isMultimedia = false;    //是否为多媒体教室
    protected boolean isFixedTimeUsed = false; //可否固定占用

    public MeetingRoom(String roomName,RoomNumType roomNum){
        this.roomName = roomName;
        this.roomNum = roomNum;
    }

    @Override
    public boolean getIsMultiMedia() { return this.isMultimedia; }
    @Override
    public boolean getIsFixedTimeUsed() { return this.isFixedTimeUsed; }
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof MeetingRoom)){
            return false;
        }
        return this == obj;
    }
}
