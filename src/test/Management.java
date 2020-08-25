package test;

import entities.*;
import service.RoomBuildService;
import service.impl.ControlSystemServiceImpl;
import service.impl.RoomBuildServiceImpl;
import service.impl.UserSystemServiceImpl;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Management {

    public static void startRandomManagement(){
        System.out.println("----------------------学校教室/会议室借用系统 -----------------");
        Scanner input = new Scanner(System.in);
        System.out.println("请选择要进行的操作\n1、用户登录\n2、管理员登录\n3、用户注册\n4、退出系统");
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
                    System.out.println("请选择要进行的操作\n1、用户登录\n2、管理员登录\n3、用户注册\n4、退出系统");
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
                UserChoose(user);
            } else {
                System.out.println("输入错误，请重新输入");
            }
        }while (!type);
    }

    //用户注册
    private static void registerUser(){
        Scanner input=new Scanner(System.in);
        int returnNum=1;//返回上一级
        System.out.println("请输入用户名");
        String name = input.next();
        System.out.println("请输入用户密码");
        String password = input.next();
        UserSystemServiceImpl user = new UserSystemServiceImpl();
        user.register(name,password);
        System.out.println("成功创建用户"+name);
        while(returnNum!=0){//返回上一级
            System.out.println("请输入0返回");
            returnNum=input.nextInt();
        }
        startRandomManagement();
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
                ManagerChoose(user);
            } else {
                System.out.println("输入错误，请重新输入");
            }
        }while (!type);
    }


    //用户界面
    private static void UserChoose(UserSystemServiceImpl user){
        System.out.println("您已登录为用户,可以进行如下操作");
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
                    startRandomManagement();//发返回主界面
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
                    showAllRoom(user);//显示全部教室信息
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
        System.out.println("您已登录为管理员，可以进行如下操作");
        System.out.println("1：借用房间");
        System.out.println("2：借用情况查询");
        System.out.println("3：取消房间借用");
        System.out.println("4：查询推荐结果");
        System.out.println("5：删除用户");
        System.out.println("6：显示全部教室信息");
        System.out.println("7：显示全部用户信息");
        System.out.println("8：增加教室");
        System.out.println("9：删除教室");
        System.out.println("请根据需要执行的操作，选择序号输入，退出请输入0");
        Scanner input=new Scanner(System.in);
        boolean type = true;
        while (type) {
            int num = input.nextInt();
            switch (num) {
                case 0:
                    System.out.println("退出成功");
                    type = false;
                    startRandomManagement();//返回主界面
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
                    showAllRoom(user);//显示全部教室信息
                    type = false;
                    break;
                case 7:
                    showAllAccount(user);//显示全部人员信息
                    type = false;
                    break;
                case 8:
                    createRoom(user);//创建新教室
                    type = false;
                    break;
                case 9:
                    deleteRoom(user);//创建新教室
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
    private static void borrowingRoom(UserSystemServiceImpl user){
        Scanner input=new Scanner(System.in);
        int returnNum=1;//返回上一级要用到的数字
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
        state=user.loanByName(roomName, startHour, startmin, endHour, endmin);
        if(state){
            System.out.println("借用成功");
        }else{
            System.out.println("借用失败");
        }
        while(returnNum!=0){//返回上一级
            System.out.println("请输入0返回");
            returnNum=input.nextInt();
        }
        UserChoose(user);
    }

    //用户功能实现：借用情况查询
    private static void borrowingQuery(UserSystemServiceImpl user){
        Scanner input=new Scanner(System.in);
        int returnNum=1;//返回上一级
        System.out.println("欢迎使用教室借用查询功能");
        System.out.println("请输入要查询的教室名称： ");
        String roomName=input.next();
        System.out.println("请输入要查询的起始时间（小时  分钟）");
        int loanHour=input.nextInt();
        int loanmin=input.nextInt();
        boolean state=false;
        state = user.isUsed(roomName,loanHour,loanmin, , );
        if(state){
            System.out.println("该房间在"+loanHour+"小时"+loanmin+"分钟被占用");
        }else{
            System.out.println("该房间在"+loanHour+"小时"+loanmin+"分钟没有被占用");
        }
        while(returnNum!=0){//返回上一级
            System.out.println("请输入0返回");
            returnNum=input.nextInt();
        }
        UserChoose(user);
    }

    //用户功能实现：取消房间借用
    private static void cancelBorrow(UserSystemServiceImpl user){
        Scanner input=new Scanner(System.in);
        int returnNum=1;//返回上一级
        System.out.println("欢迎使用房间借用取消功能");
        List<RentAction> list=user.cancelQuery();
        if (list==null){//表为空的话跳转的返回上一级
            System.out.println("借用记录为空");
        }else {
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
            int id = input.nextInt();

            for (RentAction rentAction : list) {//遍历一遍找到删除
                RentAction ra1 = (RentAction) rentAction;
                if (ra1.RentNumber == id) {
                    user.cancel(ra1);
                }
            }
        }
        while(returnNum!=0){//返回上一级
            System.out.println("请输入0返回");
            returnNum=input.nextInt();
        }
        UserChoose(user);
    }

    //用户功能实现：查询推荐结果
    private static void queryRecommend(UserSystemServiceImpl user){
        Scanner input=new Scanner(System.in);
        int returnNum=1;//返回上一级
        int sitnum=0;//记录座位数量
        boolean isMultimedia=false;//是否多媒体
        RoomNumType roomNum =null;//记录类型
        System.out.println("欢迎使用查询推荐功能");
        System.out.println("请输入座位数量：40/60/200");
        boolean type = true;//循环保证座位输入正确的判别
        boolean type1 = true;//循环保证判断多媒体输入正确
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
        while (type1) {//循环保证输入正确
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
        if(lr==null){//空表返回上一级
            System.out.println("查询结果为空");
        }else {
            System.out.println("房间列表如下：");
            System.out.println("房间号码\t\t房间可容纳人数\t\t房间是否为多媒体教室\t房间类别（教室/会议室）");
            for (Room room : lr) {
                Room rm = (Room) room;
                String pattern = null;
                String isMul = null;
                if (rm.getIsFixedTimeUsed())
                    pattern = "教室";
                else pattern = "会议室";
                if (rm.getIsMultiMedia()) {
                    isMul = "是";
                } else isMul = "不是";
                System.out.println(rm.getRoomName() + "        " + rm.getRoomNum().getValue() + "\t\t\t" + isMul + "\t\t\t\t\t" + pattern + "\t" );
            }
        }
        while(returnNum!=0){//返回上一级
            System.out.println("请输入0返回");
            returnNum=input.nextInt();
        }
        UserChoose(user);
    }

    //用户功能实现：显示全部教室信息
    private static void showAllRoom(UserSystemServiceImpl user){
        int returnNum=1;//返回上一级
        Scanner input=new Scanner(System.in);
        System.out.println("全部教室信息显示如下：");
        List<Room> lr=user.queryAllRoom();
        if(lr==null){
            System.out.println("教室信息为空");
        }else {
            System.out.println("房间号码\t\t房间可容纳人数\t\t房间是否为多媒体教室\t房间类别（教室/会议室）");
            for (Room room : lr) {
                Room rm = (Room) room;
                String pattern = null;
                String isMul = null;
                if (rm.getIsFixedTimeUsed())
                    pattern = "教室";
                else pattern = "会议室";
                if (rm.getIsMultiMedia()) {
                    isMul = "是";
                } else isMul = "不是";
                System.out.println(rm.getRoomName() + "        " + rm.getRoomNum().getValue()+ "\t\t\t" + isMul + "\t\t\t\t\t" + pattern + "\t");
            }
        }
        while(returnNum!=0){//返回上一级
            System.out.println("请输入0返回");
            returnNum=input.nextInt();
        }
        UserChoose(user);
    }

    //管理员功能实现：显示全部教室信息
    private static void showAllRoom(ControlSystemServiceImpl user){
        int returnNum=1;
        Scanner input=new Scanner(System.in);
        System.out.println("全部教室信息显示如下：");
        List<Room> lr=user.queryAllRoom();
        if(lr==null){
            System.out.println("教室信息为空");
        }else {
            System.out.println("房间号码\t\t房间可容纳人数\t\t房间是否为多媒体教室\t房间类别（教室/会议室）");
            for (Room room : lr) {
                Room rm = (Room) room;
                String pattern = null;
                String isMul = null;
                if (rm.getIsFixedTimeUsed())
                    pattern = "教室";
                else pattern = "会议室";
                if (rm.getIsMultiMedia()) {
                    isMul = "是";
                } else isMul = "不是";
                System.out.printf("%-16s",rm.getRoomName());
                System.out.printf("%-16s",rm.getRoomNum().getValue());
                System.out.printf("%-20s",isMul);
                System.out.printf("%-16s",pattern);
                System.out.println();
            }
        }
        while(returnNum!=0){//返回上一级
            System.out.println("请输入0返回");
            returnNum=input.nextInt();
        }
        ManagerChoose(user);
    }

    //管理员功能实现：借用房间
    private static void borrowingRoom(ControlSystemServiceImpl user){
        Scanner input=new Scanner(System.in);
        int returnNum=1;//返回上一级
        System.out.println("欢迎使用教室借用功能");
        System.out.println("请输入要借用的教室名称：");
        String roomName=input.next();
        System.out.println("请输入要借用起始时间（小时  分钟）");
        int startHour=input.nextInt();
        int startmin=input.nextInt();
        System.out.println("请输入要借用持续时间（小时  分钟）");
        int endHour=input.nextInt();
        int endmin=input.nextInt();
        boolean state=false;//判断借用是否成功
        state=user.loanByName( roomName, startHour, startmin, endHour, endmin);
        if(state){
            System.out.println("借用成功");
        }else{
            System.out.println("借用失败");
        }
        while(returnNum!=0){//返回上一级
            System.out.println("请输入0返回");
            returnNum=input.nextInt();
        }
        ManagerChoose(user);
    }

    //管理员功能实现：借用情况查询
    private static void borrowingQuery(ControlSystemServiceImpl user){
        Scanner input=new Scanner(System.in);
        int returnNum=1;//返回上一级
        System.out.println("欢迎使用教室借用查询功能");
        System.out.println("请输入要查询的教室名称： ");
        String roomName=input.next();
        System.out.println("请输入要查询的起始时间（小时  分钟）");
        int loanHour=input.nextInt();
        int loanmin=input.nextInt();
        boolean state=false;//判断是否被占用
        state = user.isUsed(roomName,loanHour,loanmin, , );
        if(state){
            System.out.println("该房间在"+loanHour+"小时"+loanmin+"分钟被占用");
        }else{
            System.out.println("该房间在"+loanHour+"小时"+loanmin+"分钟没有被占用");
        }
        while(returnNum!=0){//返回上一级
            System.out.println("请输入0返回");
            returnNum=input.nextInt();
        }
        ManagerChoose(user);
    }

    //管理员功能实现：取消房间借用
    private static void cancelBorrow(ControlSystemServiceImpl user){
        Scanner input=new Scanner(System.in);
        int returnNum=1;//返回上一级
        System.out.println("欢迎使用房间借用取消功能");
        List<RentAction> list=user.cancelQuery();
        if (list==null) {
            System.out.println("借用记录为空");
        }else {
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
            int id = input.nextInt();

            for (RentAction rentAction : list) {
                RentAction ra1 = (RentAction) rentAction;
                if (ra1.RentNumber == id) {
                    user.cancel(ra1);
                }
            }
        }
        while(returnNum!=0){
            System.out.println("请输入0返回");
            returnNum=input.nextInt();
        }
        ManagerChoose(user);
    }

    //管理员功能实现：查询推荐结果
    private static void queryRecommend(ControlSystemServiceImpl user){
        Scanner input=new Scanner(System.in);
        int returnNum=1;//返回上一级
        int sitnum=0;//座位数量
        boolean isMultimedia=false;
        RoomNumType roomNum =null;//记录类型
        System.out.println("欢迎使用查询推荐功能");
        System.out.println("请输入座位数量：40/60/200");
        boolean type = true;//循环保证座位输入正确的判别
        boolean type1 = true;////循环保证判断多媒体输入正确
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
        if(lr==null){
            System.out.println("查询结果为空");
        }else {
            System.out.println("房间列表如下：");
            System.out.println("房间号码\t\t房间可容纳人数\t\t房间是否为多媒体教室\t房间类别（教室/会议室）");
            for (Room room : lr) {
                Room rm = (Room) room;
                String pattern = null;
                String isMul = null;
                if (rm.getIsFixedTimeUsed())
                    pattern = "教室";
                else pattern = "会议室";
                if (rm.getIsMultiMedia()) {
                    isMul = "是";
                } else isMul = "不是";
                System.out.printf("%-16s",rm.getRoomName());
                System.out.printf("%-16s",rm.getRoomNum().getValue());
                System.out.printf("%-20s",isMul);
                System.out.printf("%-16s",pattern);
                System.out.println();
            }
        }
        while(returnNum!=0){
            System.out.println("请输入0返回");
            returnNum=input.nextInt();
        }
        ManagerChoose(user);
    }

    //管理员功能实现：删除用户
    private static void deleteAccount(ControlSystemServiceImpl user){
        Scanner input=new Scanner(System.in);
        int returnNum=1;//返回上一级
        String nameDel;//要删除的用户名字
        System.out.println("欢迎使用用户删除功能");
        System.out.println("请输入要删除的用户");
        nameDel=input.next();
        if(user.deleteAccount(nameDel)){
            System.out.println("删除成功");
        }else System.out.println("删除失败");
        while(returnNum!=0){//返回上一级
            System.out.println("请输入0返回");
            returnNum=input.nextInt();
        }
        ManagerChoose(user);
    }

    //管理员功能实现：显示全部用户信息
    private static void showAllAccount(ControlSystemServiceImpl user){
        Scanner input=new Scanner(System.in);
        int returnNum=1;//返回上一级
        System.out.println("全部用户信息");
        Set keys=user.queryAllUsers().keySet();
        System.out.println("用户ID\t用户名称\t用户密码");
        for(Object key:keys){
            System.out.println(key+"\t"+user.queryAllUsers().get(key)+"\t"+user.queryAllUsers().get(key));
        }
        while(returnNum!=0){//返回上一级
            System.out.println("请输入0返回");
            returnNum=input.nextInt();
        }
        ManagerChoose(user);
    }

    //管理员功能实现：创建新的房间
    private static void createRoom(ControlSystemServiceImpl user){
        System.out.println("欢迎使用创建房间功能");
        System.out.println("");
        Scanner input=new Scanner(System.in);
        int returnNum=1;
        int sitnum=0;//座位数量
        RoomNumType roomNum =null;//记录类型（座位数量的三种类型）
        String roomName;
        boolean isMultimedia = false;//记录是否为多媒体
        boolean isFixedTimeUsed=false;//记录是否为固定
        int startHour=0;//可固定的起始时间hour
        int startMinute=0;//可固定的起始时间min
        int lastHour=0;//可固定占用的持续时间hour
        int lastMinute=0;//可固定占用的持续时间min
        System.out.println("请输入房间号");
        roomName=input.next();
        System.out.println("请输入座位数量：40/60/200");
        boolean type = true;//座位数量正确输入判定
        boolean judge1 = true;//是否为多媒体正确输入
        boolean judge2 = true;//是否为固定正确输入
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
        System.out.println("请输入是否为多媒体y/n");
        while (judge1) {
            String isMul=input.next();
            if (isMul.equals("y")) {
                judge1 = false;
                isMultimedia=true;
            }else if(isMul.equals("n")){
                judge1 = false;
                isMultimedia=false;
            } else {
                System.out.println("输入有误,请重新输入");
                judge1 = true;
            }
        }
        System.out.println("请输入是否为教室y/n");
        System.out.println("y表示为教室，n表示为会议室");
        while (judge2) {
            String isFix=input.next();
            if (isFix.equals("y")) {
                judge2 = false;
                isFixedTimeUsed=true;
                System.out.println("请输入固定占用的起始时间：小时    分钟");
                startHour=input.nextInt();
                startMinute=input.nextInt();
                System.out.println("请输入固定占用的持续时间：小时    分钟");
                lastHour=input.nextInt();
                lastMinute=input.nextInt();
            }else if(isFix.equals("n")){
                judge2 = false;
                isFixedTimeUsed=false;
            } else {
                System.out.println("输入有误,请重新输入");
                judge2 = true;
            }
        }
        RoomBuildService roomBuild=new RoomBuildServiceImpl();
        if(isFixedTimeUsed){
            Classroom newClassRoom = new Classroom(roomName, roomNum, isMultimedia);
            newClassRoom.addNewFixedUsingTime(startHour,startMinute,lastHour,lastMinute);
            roomBuild.buildClassroom(newClassRoom);
            System.out.println("成功创建房间号码为"+roomName+"。房间容纳人数为"+roomNum+"的教室");
        }else{
            MeetingRoom newMetingRoom;
            newMetingRoom = new MeetingRoom(roomName, roomNum);
            roomBuild.buildMeetingRoom(newMetingRoom);
        }
        while(returnNum!=0){//返回上一级
            System.out.println("请输入0返回");
            returnNum=input.nextInt();
        }
        ManagerChoose(user);
    }

    //管理员功能实现：删除房间功能
    private static void deleteRoom(ControlSystemServiceImpl user){
        Scanner input=new Scanner(System.in);
        int returnNum=1;
        System.out.println("欢迎使用删除房间功能");
        //showAllRoom(user);
        System.out.println("全部教室信息显示如下：");
        List<Room> lr=user.queryAllRoom();
        if(lr==null){
            System.out.println("教室信息为空");
        }else {
            System.out.println("房间号码\t\t房间可容纳人数\t\t房间是否为多媒体教室\t房间类别（教室/会议室）");
            for (Room room : lr) {
                Room rm = (Room) room;
                String pattern = null;
                String isMul = null;
                if (rm.getIsFixedTimeUsed())
                    pattern = "教室";
                else pattern = "会议室";
                if (rm.getIsMultiMedia()) {
                    isMul = "是";
                } else isMul = "不是";
                System.out.printf("%-16s",rm.getRoomName());
                System.out.printf("%-16s",rm.getRoomNum().getValue());
                System.out.printf("%-20s",isMul);
                System.out.printf("%-16s",pattern);
                System.out.println();
            }
        }
        RoomBuildService roomBuild=new RoomBuildServiceImpl();
        System.out.println("请输入要删除的房间的编号");
        String delID=input.next();
        boolean judgeSuccess=false;
        judgeSuccess=roomBuild.deleteRoom(delID);
        if(judgeSuccess){
            System.out.println("删除成功");
        }else{
            System.out.println("删除失败");
        }
        while(returnNum!=0){//返回上一级
            System.out.println("请输入0返回");
            returnNum=input.nextInt();
        }
        ManagerChoose(user);
    }
}
