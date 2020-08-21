package service;
import entities.*;
public interface RoomBuildService {
    public boolean buildClassroom(Classroom room);
    public boolean buildMeetingRoom(MeetingRoom room);
    public boolean deleteRoom(String RID);
}
