package test;

import entities.RentAction;
import entities.Room;
import entities.RoomNumType;
import service.impl.ControlSystemServiceImpl;
import service.impl.UserSystemServiceImpl;

import java.util.List;
import java.util.Scanner;

public class Management {

    public static void startRandomManagement(){
        System.out.println("----------------------学校教室/会议室借用系统 -----------------");
        Scanner input = new Scanner(System.in);
        System.out.println("请选择要进行的操作，输入1为用户登录，输入2为管理员登录，输入3进行用户注册，输入4退出系统");
        boolean type = true;
        String num;
        while (type) {
            num = input.next();
            switch (num){
                case "1":
                    userLogin();
                    type = false;
                    break;
                case "2":
                    managerLogin();
                    type = false;
                    break;
                case "3":
                    registerUser();
                    type=true;
                    break;
                case "4":
                    System.out.println("谢谢使用！");
                    type = false;
                    break;
                default:
                    System.out.println("输入有误，请按照指定规则输入");
                    System.out.println("请选择要进行的操作，输入1为用户登录，输入2为管理员登录,输入3进行用户注册，输入4退出系统");
                    type = true;
                    break;
            }
        }
    }

    //用户登陆
    private static void userLogin(){
        Scanner input=new Scanner(System.in);
        boolean type = false;
        do {
            System.out.println("请输入用户名");
            String name = input.next();
            System.out.println("请输入用户密码");
            String password = input.next();
            UserSystemServiceImpl user = new UserSystemServiceImpl();
            if (user.login(name, password)) {
                type=true;
                System.out.println("您已登录成功，可以进行如下操作");
                UserChoose(user);
            } else {
                System.out.println("输入错误，请重新输入");
            }
        }while (!type);
    }

    //用户注册
    //有问题**可能因为为没有安装数据库？？
    private static void registerUser(){
        Scanner input=new Scanner(System.in);
        System.out.println("请输入用户名");
        String name = input.next();
        System.out.println("请输入用户密码");
        String password = input.next();
        UserSystemServiceImpl user = new UserSystemServiceImpl();
        user.register(name,password);
    }

    //管理员登陆
    private static void managerLogin(){
        Scanner input=new Scanner(System.in);
        boolean type = false;
        do {
            System.out.println("请输入用户名");
            String name = input.next();
            System.out.println("请输入用户密码");
            String password = input.next();
            ControlSystemServiceImpl user = new ControlSystemServiceImpl();
            if (user.login(name, password)) {
                type=true;
                System.out.println("您已登录成功，可以进行如下操作");
                ManagerChoose(user);
            } else {
                System.out.println("输入错误，请重新输入");
            }
        }while (!type);
    }


    //用户界面
    private static void UserChoose(UserSystemServiceImpl user){
        System.out.println("1：借用房间");
        System.out.println("2：借用情况查询");
        System.out.println("3：取消房间借用");
        System.out.println("4：查询推荐结果");
        System.out.println("5：显示全部教室信息");
        System.out.println("请根据需要执行的操作，选择序号输入，退出请输入0");
        Scanner input=new Scanner(System.in);
        boolean type = true;
        while (type) {
            int num = input.nextInt();
            switch (num) {
                case 0:
                    System.out.println("退出成功");
                    type = false;
                    startRandomManagement();
                    break;
                case 1:
                    borrowingRoom(user);//借用房间
                    type = false;
                    break;
                case 2:
                    borrowingQuery(user);//借用情况查询
                    type = false;
                    break;
                case 3:
                    cancelBorrow(user);//取消借用
                    type = false;
                    break;
                case 4:
                    queryRecommend(user);//查询推荐结果
                    type = false;
                    break;
                case 5:
                    showAllRoom();//显示全部教室信息
                    type = false;
                    break;
                default:
                    System.out.println("输入有误,请重新输入");
                    type = true;
                    break;
            }
        }
    }

    //管理员界面
    private static void ManagerChoose(ControlSystemServiceImpl user){
        System.out.println("1：借用房间");
        System.out.println("2：借用情况查询");
        System.out.println("3：取消房间借用");
        System.out.println("4：查询推荐结果");
        System.out.println("5：删除用户");
        System.out.println("6：显示全部教室信息");
        System.out.println("7：显示全部用户信息");
        System.out.println("请根据需要执行的操作，选择序号输入，退出请输入0");
        Scanner input=new Scanner(System.in);
        boolean type = true;
        while (type) {
            int num = input.nextInt();
            switch (num) {
                case 0:
                    System.out.println("退出成功");
                    type = false;
                    startRandomManagement();
                    break;
                case 1:
                    borrowingRoom(user);//借用房间
                    type = false;
                    break;
                case 2:
                    borrowingQuery(user);//借用情况查询
                    type = false;
                    break;
                case 3:
                    cancelBorrow(user);//取消借用
                    type = false;
                    break;
                case 4:
                    queryRecommend(user);//查询推荐结果
                    type = false;
                    break;
                case 5:
                    deleteAccount(user);//删除用户
                    type = false;
                    break;
                case 6:
                    showAllRoom();//显示全部教室信息
                    type = false;
                    break;
                case 7:
                    showAllAccount();//显示全部人员信息
                    type = false;
                    break;
                default:
                    System.out.println("输入有误,请重新输入");
                    type = true;
                    break;
            }
        }
    }

    //用户功能实现：借用房间
    //没有判断是否被已经被借用
    private static void borrowingRoom(UserSystemServiceImpl user){
        Scanner input=new Scanner(System.in);
        int returnNum=1;
        System.out.println("欢迎使用教室借用功能");
        System.out.println("请输入要借用的教室名称：");
        String roomName=input.next();
        System.out.println("请输入要借用起始时间（小时  分钟）");
        int startHour=input.nextInt();
        int startmin=input.nextInt();
        System.out.println("请输入要借用中止时间（小时  分钟）");
        int endHour=input.nextInt();
        int endmin=input.nextInt();
        boolean state=false;
        state=user.loanByName(roomName,startHour,startmin,endHour,endmin);
        if(state){
            System.out.println("借用成功");
        }else{
            System.out.println("借用失败");
        }
        while(returnNum!=0){
            System.out.println("请输入0返回");
            returnNum=input.nextInt();
        }
        UserChoose(user);
    }

    //用户功能实现：借用情况查询
    private static void borrowingQuery(UserSystemServiceImpl user){
        Scanner input=new Scanner(System.in);
        int returnNum=1;
        System.out.println("欢迎使用教室借用查询功能");
        System.out.println("请输入要借用的教室名称： ");
        String roomName=input.next();
        System.out.println("请输入要借用起始时间（小时  分钟）");
        int loanHour=input.nextInt();
        int loanmin=input.nextInt();
        boolean state=false;
        state = user.isUsed(roomName,loanHour,loanmin);
        if(state){
            System.out.println("该房间在"+loanHour+"小时"+loanmin+"分钟被占用");
        }else{
            System.out.println("该房间在"+loanHour+"小时"+loanmin+"分钟没有被占用");
        }
        while(returnNum!=0){
            System.out.println("请输入0返回");
            returnNum=input.nextInt();
        }
        UserChoose(user);
    }

    //用户功能实现：取消房间借用
    private static void cancelBorrow(UserSystemServiceImpl user){
        Scanner input=new Scanner(System.in);
        int returnNum=1;
        System.out.println("欢迎使用房间借用取消功能");
        List<RentAction> list=user.cancelQuery();
        System.out.println("借用列表如下");
        System.out.println("借用记录id\t用户id\t房间id\t借用起始时间\t借用持续时间\t借用方式");
        for (RentAction rentAction : list) {
            RentAction ra = (RentAction) rentAction;
            String pattern = null;
            if (ra.isFixed)
                pattern = "固定";
            else pattern = "非固定";
            System.out.println(ra.RentNumber + "\t\t" + ra.UID + "\t" + ra.RID + "\t" + ra.start_time + "\t" + ra.last_time + "\t" + pattern);
        }
        System.out.println("请输入借用教室取消的借用记录id");
        int id=input.nextInt();

        for (RentAction rentAction : list) {
            RentAction ra1 = (RentAction) rentAction;
            if(ra1.RentNumber==id){
                user.cancel(ra1);
            }
        }
        while(returnNum!=0){
            System.out.println("请输入0返回");
            returnNum=input.nextInt();
        }
        UserChoose(user);
    }

    //用户功能实现：查询推荐结果
    private static void queryRecommend(UserSystemServiceImpl user){
        Scanner input=new Scanner(System.in);
        int returnNum=1;
        int sitnum=0;
        boolean isMultimedia=false;
        RoomNumType roomNum =null;//记录类型
        System.out.println("欢迎使用查询推荐功能");
        System.out.println("请输入座位数量：40/60/200");
        boolean type = true;
        boolean type1 = true;
        while (type) {
            sitnum = input.nextInt();
            switch (sitnum) {
                case 40:
                    roomNum=RoomNumType.getEnum(40);
                    type = false;
                    break;
                case 60:
                    roomNum=RoomNumType.getEnum(60);
                    type = false;
                    break;
                case 200:
                    roomNum=RoomNumType.getEnum(200);
                    type = false;
                    break;
                default:
                    System.out.println("输入有误,请重新输入");
                    type = true;
                    break;
            }
        }
        System.out.println("请输入要借用起始时间（小时  分钟）");
        int startHour=input.nextInt();
        int startmin=input.nextInt();
        System.out.println("请输入要借用中止时间（小时  分钟）");
        int endHour=input.nextInt();
        int endmin=input.nextInt();
        System.out.println("请输入是否为多媒体y/n");
        while (type1) {
            String isMul=input.next();
            if (isMul.equals("y")) {
                type1 = false;
                isMultimedia=true;
            }else if(isMul.equals("n")){
                type1 = false;
                isMultimedia=false;
            } else {
            System.out.println("输入有误,请重新输入");
            type1 = true;
            }
        }
        List<Room> lr=user.queryRecommendResult(roomNum,startHour,startmin,endHour,endmin,isMultimedia);
        System.out.println("房间列表如下：");
        while(returnNum!=0){
            System.out.println("请输入0返回");
            returnNum=input.nextInt();
        }
        UserChoose(user);
    }

    //显示全部教室信息
    private static void showAllRoom(){

    }

    //管理员功能实现：借用房间
    private static void borrowingRoom(ControlSystemServiceImpl user){

    }

    //管理员功能实现：借用情况查询
    private static void borrowingQuery(ControlSystemServiceImpl user){

    }

    //管理员功能实现：取消房间借用
    private static void cancelBorrow(ControlSystemServiceImpl user){

    }

    //管理员功能实现：查询推荐结果
    private static void queryRecommend(ControlSystemServiceImpl user){

    }

    //管理员功能实现：删除用户
    private static void deleteAccount(ControlSystemServiceImpl user){

    }

    //管理员功能实现：显示全部用户信息
    private static void showAllAccount(){

    }
}
