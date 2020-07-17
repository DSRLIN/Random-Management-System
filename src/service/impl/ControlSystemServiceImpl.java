package service.impl;

import entities.RoomNumType;
import service.ControlSystemService;

public class ControlSystemServiceImpl implements ControlSystemService {
    private boolean isLogin = false;
    @Override
    public void loanByRecommendResult(RoomNumType roomNum,
                                      int startHour, int startMinute,
                                      int lastHour, int lastMinute,
                                      boolean isMultimedia) {

    }

    @Override
    public void loanByName(String roomName) {

    }

    @Override
    public boolean isUsed(int useHour, int useMinute) {
        return false;
    }

    @Override
    public void login(String adminName, String adminPasswd) {

    }

    @Override
    public void cancel() {

    }

    @Override
    public void deleteAccount(String accountName) {
        //TODO：调接口去数据库执行删除操作
        //什么？你问我用户怎么删？
        //滚去联系客服啊（
    }
}
