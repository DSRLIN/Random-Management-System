package service.impl;

import dao.AccountDao;
import dao.RentDao;
import dao.RoomDao;
import dao.impl.AccountDaoImpl;
import dao.impl.RentDaoImpl;
import dao.impl.RoomDaoImpl;
import entities.*;
import service.UserSystemService;

import java.util.List;

public class UserSystemServiceImpl implements UserSystemService {

    private boolean isLogin = false;
    private String curLoginUserName = null;
    private String curLoginUserPasswd = null;
    @Override
    public boolean loanByRecommendResult(RoomNumType roomNum,
                                      int startHour, int startMinute,
                                      int lastHour, int lastMinute,
                                      boolean isMultimedia) {
        return false;
    }

    @Override
    public boolean loanByName(String roomName,
                              int startHour,int startMinute,
                              int lastHour,int lastMinute) {
        RentDao rtd = new RentDaoImpl();
        RoomDao rd = new RoomDaoImpl();
        AccountDao ad = new AccountDaoImpl();
        Room thisRoom = rd.queryRoom(roomName);
        int startTime = startHour*3600+startMinute*60;
        int lastTime = lastHour*3600+lastMinute*60;
        if(thisRoom.getIsFixedTimeUsed()){
            Classroom classroom = (Classroom)thisRoom;
            Integer userUID = ad.queryUID(curLoginUserName);
            return rtd.addRent(userUID,classroom.getRoomName(),
                    Integer.toString(startTime), Integer.toString(lastTime),true);
        }else{
            MeetingRoom meetingRoom = (MeetingRoom)thisRoom;
            Integer userUID = ad.queryUID(curLoginUserName);
            return rtd.addRent(userUID,meetingRoom.getRoomName(),
                    Integer.toString(startTime), Integer.toString(lastTime),false);
        }
    }

    @Override
    public boolean isUsed(String roomName,int useHour, int useMinute) {
        RoomDao rd = new RoomDaoImpl();
        Room thisRoom = rd.queryRoom(roomName);
        //根据不同类型进行转型并判断返回
        if(thisRoom.getIsFixedTimeUsed()){
            //可固定是教室
            Classroom classroom = (Classroom)thisRoom;
            long useTime = useHour*3600+useMinute*60;
            for (int i = 0; i < classroom.getFixedUsingTimeStart().size(); i++) {
                if((useTime > classroom.getFixedUsingTimeStart().get(i)&&
                        useTime < classroom.getFixedUsingTimeEnd().get(i))){
                    return true;
                }
            }
            for (int k = 0; k < classroom.getFreeUsingTimeStart().size(); k++) {
                if((useTime > classroom.getFreeUsingTimeStart().get(k)&&
                        useTime < classroom.getFreeUsingTimeEnd().get(k))){
                    return true;
                }
            }
        }
        else {
            MeetingRoom meetingRoom = (MeetingRoom)thisRoom;
            long useTime = useHour*3600+useMinute*60;
            for (int i = 0; i < meetingRoom.getFreeUsingTimeStart().size(); i++) {
                if((useTime > meetingRoom.getFreeUsingTimeStart().get(i)&&
                        useTime < meetingRoom.getFreeUsingTimeEnd().get(i))){
                    return true;
                }
            }
        }
        return false;
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
    public List<RentAction> cancel() {
        RentDao rtd = new RentDaoImpl();
        AccountDao ad = new AccountDaoImpl();
        return rtd.queryRentList(ad.queryUID(this.curLoginUserName));
    }
}
