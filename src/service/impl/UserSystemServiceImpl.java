package service.impl;

import dao.AccountDao;
import dao.RentDao;
import dao.RoomDao;
import dao.impl.AccountDaoImpl;
import dao.impl.RentDaoImpl;
import dao.impl.RoomDaoImpl;
import entities.*;
import service.UserSystemService;

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
        if(isLogin){
            long startTime = startHour*3600+startMinute*60;
            long lastTime = lastHour*3600+lastMinute+60;
            long useTime = startTime+lastTime;
            RoomDao rd = new RoomDaoImpl();
            List<Room> roomNumList = rd.queryRoomList(roomNum);
            List<Room> roomMultimediaList = rd.queryRoomList(isMultimedia);
            //注：最后的返回项是roomNumList
            roomNumList.retainAll(roomMultimediaList);
            for (int i = 0; i < roomNumList.size(); i++) {
                //教室类型的
                if(roomNumList.get(i).getIsFixedTimeUsed()){
                    Classroom tmpCls = (Classroom) roomNumList.get(i);
                    //循环只要找到true立刻鲨掉
                    AtomicBoolean secondKey = new AtomicBoolean(true); //如果第一个循环未找到 则进入第二个循环
                    for (int j = 0; j < tmpCls.getFixedUsingTimeStart().size(); j++) {
                        if((startTime > tmpCls.getFixedUsingTimeStart().get(j)&&
                            startTime < tmpCls.getFixedUsingTimeEnd().get(j))||(
                            useTime > tmpCls.getFixedUsingTimeStart().get(j)&&
                            useTime < tmpCls.getFixedUsingTimeEnd().get(j))||(
                            tmpCls.getFixedUsingTimeStart().get(j) > startTime&&
                            tmpCls.getFixedUsingTimeStart().get(j) < useTime)||(
                            tmpCls.getFixedUsingTimeEnd().get(j) > startTime&&
                            tmpCls.getFixedUsingTimeEnd().get(j) < useTime)) {
                            roomNumList.remove(i);
                            secondKey.set(false);
                            break;
                        }
                        if(secondKey.get()){
                            for (int k = 0; k < tmpCls.getFreeUsingTimeStart().size(); k++) {
                                if((startTime > tmpCls.getFreeUsingTimeStart().get(k)&&
                                    startTime < tmpCls.getFreeUsingTimeEnd().get(k))||(
                                    useTime > tmpCls.getFreeUsingTimeStart().get(k)&&
                                    useTime < tmpCls.getFreeUsingTimeEnd().get(k))||(
                                    tmpCls.getFreeUsingTimeStart().get(k) > startTime&&
                                    tmpCls.getFreeUsingTimeStart().get(k) < useTime)||(
                                    tmpCls.getFreeUsingTimeEnd().get(k) > startTime&&
                                    tmpCls.getFreeUsingTimeEnd().get(k) < useTime)) {
                                    roomNumList.remove(i);
                                    break;
                                }
                            }

                        }
                    }
                }else{
                    MeetingRoom tmpMtr = (MeetingRoom) roomNumList.get(i);
                    //循环只要找到true立刻鲨掉
                    for (int k = 0; k < tmpMtr.getFreeUsingTimeStart().size(); k++) {
                        if((startTime > tmpMtr.getFreeUsingTimeStart().get(k)&&
                            startTime < tmpMtr.getFreeUsingTimeEnd().get(k))||(
                            useTime > tmpMtr.getFreeUsingTimeStart().get(k)&&
                            useTime < tmpMtr.getFreeUsingTimeEnd().get(k))||(
                            tmpMtr.getFreeUsingTimeStart().get(k) > startTime&&
                            tmpMtr.getFreeUsingTimeStart().get(k) < useTime)||(
                            tmpMtr.getFreeUsingTimeEnd().get(k) > startTime&&
                            tmpMtr.getFreeUsingTimeEnd().get(k) < useTime)) {
                            roomNumList.remove(i);
                            break;
                        }
                    }
                }
            }
            return roomNumList;
        }
        else return null;
    }

    @Override
    public boolean loanByName(Integer UID, String roomName,
                              int startHour, int startMinute,
                              int lastHour, int lastMinute) {
        if(isLogin) {
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
    public boolean isUsed(String roomName,int useHour, int useMinute) {
        RoomDao rd = new RoomDaoImpl();
        Room thisRoom = rd.queryRoom(roomName);
        //根据不同类型进行转型并判断返回
        if (thisRoom == null){
            return false;
        }
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
