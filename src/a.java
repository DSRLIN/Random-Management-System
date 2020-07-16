import dao.BaseDao;

import java.sql.*;

public class a {
    public static void main(String[] args){

        System.out.println("aaa");
        //测试用 不用关注内容
        try{
            ResultSet result = BaseDao.getConnection().prepareStatement("SELECT * FROM test;").executeQuery();
            System.out.println(result.getString(2));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
