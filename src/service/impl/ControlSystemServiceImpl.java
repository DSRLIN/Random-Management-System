package service.impl;

import dao.AccountDao;
import dao.RoomDao;
import entities.*;
import service.ControlSystemService;
import dao.impl.*;

import java.util.List;

public class ControlSystemServiceImpl implements ControlSystemService {
    private boolean isLogin = false;

    @Override
    public boolean loanByRecommendResult(RoomNumType roomNum,
                                      int startHour, int startMinute,
                                      int lastHour, int lastMinute,
                                      boolean isMultimedia) {
        return false;
    }

    @Override
    public boolean loanByName(String roomName) {
        RoomDao rd = new RoomDaoImpl();
        rd.queryRoom(roomName);
        return false;
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
    public boolean login(String adminName, String adminPasswd) {
        if((adminName.equals("admin"))&&(adminPasswd.equals("admin"))){
            this.isLogin = true;
            return true;
        }
        else{return false;}
    }

    @Override
    public boolean cancel() {
        return false;
    }

    @Override
    public boolean deleteAccount(String accountName) {
        //TODO：调接口去数据库执行删除操作
        //什么？你问我用户怎么删？
        //滚去联系客服啊（
        AccountDao ad = new AccountDaoImpl();
        return ad.deleteAccount(accountName);
    }
}
