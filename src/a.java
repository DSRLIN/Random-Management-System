import dao.AccountDao;
import dao.impl.AccountDaoImpl;
import entities.RoomNumType;

import java.util.HashMap;

public class a {
    public static void main(String[] args){
        System.out.println("枚举赋值测试\t"+RoomNumType.forty+":"+RoomNumType.forty.getValue());
        //测试账户表接口
        AccountDao accountDao = new AccountDaoImpl();
        System.out.println(accountDao.registerAccount("abc","111"));
        System.out.println(accountDao.queryAccount(accountDao.queryUID("abc")));
        System.out.println(accountDao.changePassword("abc","222"));
        System.out.println(accountDao.queryPassword("abc"));
        //遍历哈希图输出表
        HashMap<Integer,String> temp = accountDao.queryAccountList();
        System.out.println("\nUID\tACCOUNT");
        for (Integer i : temp.keySet()) {
            System.out.println(i + "\t" + temp.get(i));
        }
    }
}
