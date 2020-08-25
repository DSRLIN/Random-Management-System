package service.impl;

import dao.AccountDao;
import dao.RentDao;
import dao.RoomDao;
import dao.impl.AccountDaoImpl;
import dao.impl.RentDaoImpl;
import dao.impl.RoomDaoImpl;
import entities.*;
import service.UserSystemService;
import tools.comparator;
import tools.jointMachine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserSystemServiceImpl implements UserSystemService {

    private boolean isLogin = false;
    private String curLoginUserName = null;
    private String curLoginUserPasswd = null;
    @Override
    public List<Room>  queryRecommendResult(RoomNumType roomNum,
                                      int startHour, int startMinute,
                                      int lastHour, int lastMinute,
                                      boolean isMultimedia) {
        if(isLogin) {
            int endHour = startHour + lastHour;
            int endMinute = startMinute + lastMinute;
            if(endMinute>=60){
                endMinute -= 60;
                endHour += 1;
            }
            RoomDao rd = new RoomDaoImpl();
            RentDao rtd = new RentDaoImpl();
            List<Room> roomNumList = rd.queryRoomList(roomNum);
            List<Room> roomMultimediaList = rd.queryRoomList(isMultimedia);
            //注：最后的返回项是roomNumList
            roomNumList.retainAll(roomMultimediaList);
            for (Room rm:roomNumList) {
                String roomName = rm.getRoomName();
                if(isUsed(roomName,startHour,startMinute,endHour,endMinute)){
                    roomNumList.remove(rm);
                }
            }
            return roomNumList;
        }
        else return null;
    }

    @Override
    public boolean loanByName(String roomName,
                              int startHour, int startMinute,
                              int lastHour, int lastMinute) {
        if(isLogin) {
            if(isUsed(roomName,startHour,startMinute,startHour+lastHour,startMinute+lastMinute)){
                return false;
            }
            RentDao rtd = new RentDaoImpl();
            RoomDao rd = new RoomDaoImpl();
            AccountDao ad = new AccountDaoImpl();
            Room thisRoom = rd.queryRoom(roomName);
            int startTime = startHour * 3600 + startMinute * 60;
            int lastTime = lastHour * 3600 + lastMinute * 60;
            if(thisRoom == null){
                return false;
            }
            if (thisRoom.getIsFixedTimeUsed()) {
                Classroom classroom = (Classroom) thisRoom;
                Integer userUID = ad.queryUID(curLoginUserName);
                return rtd.addRent(userUID, classroom.getRoomName(),
                        Integer.toString(startTime), Integer.toString(lastTime), true);
            } else {
                MeetingRoom meetingRoom = (MeetingRoom) thisRoom;
                Integer userUID = ad.queryUID(curLoginUserName);
                return rtd.addRent(userUID, meetingRoom.getRoomName(),
                        Integer.toString(startTime), Integer.toString(lastTime), false);
            }
        }else return false;
    }


    @Override
    public boolean isUsed(String roomName, int startHour, int startMinute, int endHour, int endMinute) {
        RoomDao rd = new RoomDaoImpl();
        Room thisRoom = rd.queryRoom(roomName);
        RentDao rtd = new RentDaoImpl();
        ArrayList<RentAction> rta = rtd.queryRentList(roomName);
        if(rta==null){
            return false;
        }
        jointMachine j = new jointMachine();
        comparator c = new comparator();
        if (thisRoom == null){
            return false;
        }
        //根据不同类型进行转型并判断返回
        if(thisRoom.getIsFixedTimeUsed()){
            //可固定是教室
            Classroom classroom = (Classroom)thisRoom;
            classroom = j.joint(classroom,rta);
            long startTime = (startHour*3600) + (startMinute*60);
            long endTime = (endHour*3600) + (endMinute*60);
            if(classroom.getFixedUsingTimeStart()!=null){
                for (int i = 0; i < classroom.getFixedUsingTimeStart().size(); i++) {
                    long cFStartTime = classroom.getFixedUsingTimeStart().indexOf(i);
                    long cFEndTime = classroom.getFixedUsingTimeEnd().indexOf(i);
                    if(c.compare(startTime,endTime,cFStartTime,cFEndTime)){
                        return true;
                    }
                }
            }
            if(classroom.getFreeUsingTimeStart()!=null){
                for (int k = 0; k < classroom.getFreeUsingTimeStart().size(); k++) {
                    long cXStartTime = classroom.getFreeUsingTimeStart().indexOf(k);
                    long cXEndTime = classroom.getFreeUsingTimeEnd().indexOf(k);
                    if(c.compare(startTime,endTime,cXStartTime,cXEndTime)){
                        return true;
                    }
                }
            }
        }
        else {
            MeetingRoom meetingRoom = (MeetingRoom)thisRoom;
            meetingRoom = j.joint(meetingRoom,rta);
            long startTime = (startHour*3600) + (startMinute*60);
            long endTime = (endHour*3600) + (endMinute*60);
            if(meetingRoom.getFreeUsingTimeStart() != null){
                for (int i = 0; i < meetingRoom.getFreeUsingTimeStart().size(); i++) {
                    long mFStartTime = meetingRoom.getFreeUsingTimeStart().indexOf(i);
                    long mFEndTime = meetingRoom.getFreeUsingTimeEnd().indexOf(i);
                    if(c.compare(startTime,endTime,mFStartTime,mFEndTime)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<RentAction> cancelQuery() {
        RentDao rtd = new RentDaoImpl();
        AccountDao ad = new AccountDaoImpl();
        return rtd.queryRentList(ad.queryUID(this.curLoginUserName));
    }

    @Override
    public boolean login(String userName, String userPasswd) {
        AccountDao ad = new AccountDaoImpl();
        Integer userUID = ad.queryUID(userName);
        if (userUID == null){
            return false;
        }
        if(!userPasswd.equals(ad.queryPassword(userName))){
            return false;
        }
        this.curLoginUserName = userName;
        this.curLoginUserPasswd = userPasswd;
        this.isLogin = true;
        return true;
    }

    @Override
    public boolean register(String userName, String userPasswd) {
        AccountDao ad = new AccountDaoImpl();
        return ad.registerAccount(userName,userPasswd);
    }

    @Override
    public boolean cancel(RentAction ra) {
        RentDao rtd = new RentDaoImpl();
        return rtd.deleteRent(ra);
    }

    @Override
    public ArrayList<Room> queryAllRoom() {
        RoomDao rd = new RoomDaoImpl();
        return rd.queryRoomList();
    }
}
