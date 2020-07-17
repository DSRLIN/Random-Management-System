package service;

public interface UserSystemService extends Loanable,Queryable {
    //用户控制接口

    //用户实际登录入口
    public void login(String userName,String userPasswd);

    //注册接口
    public void register(String userName,String userPasswd);

    //取消教室占用 只能取消自己申请的内容
    public void cancel();
}
