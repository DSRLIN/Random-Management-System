import dao.BaseDao;
import entities.RoomNumType;

import javax.management.BadAttributeValueExpException;
import java.sql.*;

public class a {
    public static void main(String[] args){
        System.out.println("枚举赋值测试\t"+RoomNumType.forty+":"+RoomNumType.forty.getValue());
        Connection conn = null;
        ResultSet result = null;
        try{
            System.out.println("数据库测试");
            conn = BaseDao.getConnection();
            //测试用户表
            result = conn.prepareStatement("SELECT * FROM account_table;").executeQuery();
            System.out.println(result.getString(1)+
                        "\t"+result.getString(2)+
                        "\t"+result.getString(3));
            result.close();
            //测试房间表 在getString获取Bool时返回1 getBoolean时返回true
            result = conn.prepareStatement("SELECT * FROM room_table;").executeQuery();
            System.out.println(result.getString(1)+
                        "\t"+result.getString(2)+
                        "\t"+result.getString(3)+
                        "\t"+result.getString(4)+
                        "\t"+result.getBoolean(4));
            result.close();
            //测试借用行为表
            result = conn.prepareStatement("SELECT * FROM rent_table;").executeQuery();
            System.out.println(result.getString(1)+
                    "\t"+result.getString(2)+
                    "\t"+result.getString(3)+
                    "\t"+result.getString(4)+
                    "\t"+result.getBoolean(5));
            result.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            BaseDao.closeObject(conn,null,result);
        }
    }
}
