package service;

public interface UserSystemService extends Loanable,Queryable {
    //用户控制接口

    //用户实际登录入口
    public boolean login(String userName,String userPasswd);

    //注册接口
    public boolean register(String userName,String userPasswd);

    //取消教室占用 只能取消自己申请的内容
    public boolean cancel();
}
