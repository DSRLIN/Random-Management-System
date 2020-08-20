package service;

import entities.RentAction;
import entities.Room;

import java.util.ArrayList;
import java.util.List;

public interface UserSystemService extends Loanable,Queryable {
    //用户控制接口

    //用户实际登录入口
    boolean login(String userName,String userPasswd);

    //注册接口
    boolean register(String userName,String userPasswd);

    //取消教室占用 只能取消自己申请的内容
    boolean cancel(RentAction ra);

    //显示全部教室信息
    ArrayList<Room> queryAllRoom();

    //获得用户UID
}
