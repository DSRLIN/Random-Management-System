package service.impl;

import dao.AccountDao;
import entities.RoomNumType;
import service.ControlSystemService;
import dao.impl.*;

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
        return false;
    }

    @Override
    public boolean isUsed(int useHour, int useMinute) {
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
        AccountDaoImpl ad = new AccountDaoImpl();
        return ad.deleteAccount(accountName);
    }
}
