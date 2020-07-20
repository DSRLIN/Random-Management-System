package service.impl;

import dao.AccountDao;
import dao.RentDao;
import dao.RoomDao;
import entities.*;
import service.ControlSystemService;
import dao.impl.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ControlSystemServiceImpl implements ControlSystemService {
    private boolean isLogin = false;

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
    public boolean loanByName(String roomName,
                              int startHour,int startMinute,
                              int lastHour,int lastMinute){
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
    public List<RentAction> cancelQuery() {
        RentDao rtd = new RentDaoImpl();
        AccountDao ad = new AccountDaoImpl();
        return rtd.queryRentList();
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
    public boolean cancel(RentAction ra) {
        RentDao rtd = new RentDaoImpl();
        return rtd.deleteRent(ra);
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
