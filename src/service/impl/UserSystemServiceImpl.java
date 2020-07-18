package service.impl;

import dao.AccountDao;
import dao.impl.AccountDaoImpl;
import entities.RoomNumType;
import service.UserSystemService;

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
    public boolean loanByName(String roomName) {
        return false;
    }

    @Override
    public boolean isUsed(String roomName,int useHour, int useMinute) {
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
    public boolean cancel() {
        return false;
    }
}
