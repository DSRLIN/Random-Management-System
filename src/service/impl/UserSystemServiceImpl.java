package service.impl;

import entities.RoomNumType;
import service.UserSystemService;

public class UserSystemServiceImpl implements UserSystemService {
    private boolean isLogin = false;
    private String curLoginUserName = null;
    private String curLoginUserPasswd = null;
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
    public void login(String userName, String userPasswd) {

    }

    @Override
    public void register(String userName, String userPasswd) {

    }

    @Override
    public void cancel() {

    }
}
