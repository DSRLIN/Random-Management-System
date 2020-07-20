package service;

import entities.RentAction;

import java.util.List;

public interface ControlSystemService extends Loanable,Queryable {
    //控制系统接口 管理员使用

    //实际登录函数 外部需要再套一层壳以处理进入用户登录情况
    boolean login(String adminName,String adminPasswd);

    //取消教室借用 任意取消
    boolean cancel(RentAction ra);

    //删除提供账户
    boolean deleteAccount(String accountName);
}
