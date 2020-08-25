package dao;

import java.sql.*;

/**
 * 基础DAO类,单例,具体操作类无需继承此类,方法均为静态方法,可直接调用
 * @author Pharsalia
 * @version 0.1.1
 */
public class BaseDao {
    //当前项目无需提供配置文件用于修改参数
    private static String DIVER;
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    //初始化
    private static void init(){
        DIVER = "org.sqlite.JDBC";
        URL = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\DataBase\\RMS-room.sqlite";
        USER = "root";
        PASSWORD = "root";
        //System.out.println("Debug::URL\t" + URL);
    }

    /**
     * 使用默认存储的信息加载数据库并建立连接
     * @return 数据库连接
     */
    public static Connection getConnection() throws ClassNotFoundException{
        Connection conn = null;
        try{
            //forName告知JVM查找并加载对应类,同时运行对应类的静态代码块,进而完成驱动装载
            Class.forName(DIVER);
            conn = DriverManager.getConnection(URL,USER,PASSWORD);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 关闭对应对象,可选择性输入null
     * @param conn 连接对象
     * @param pStmt 预编译语句对象
     * @param rs 结果集对象
     */
    public static synchronized void closeObject(Connection conn, PreparedStatement pStmt, ResultSet rs){
        try{
            if(rs != null) rs.close();
            if(pStmt != null) pStmt.close();
            if(conn != null) conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * 执行预编译语句的通用方法(查询结果无法返回)
     * @param pSql 要执行的预编译语句
     * @param params 语句中对应的参数组成的对象数组
     * @return 影响的行数
     */
    public static int executeSQL(String pSql, Object[] params){
        Connection conn = null;
        PreparedStatement pStmt = null;
        int count = 0;
        try{
            //获取连接及对应预编译语句
            conn = getConnection();
            pStmt = conn.prepareStatement(pSql);
            //输入参数
            if(params != null){
                for(int i = 0;i<params.length;i++){
                    pStmt.setObject(i+1,params[i]);
                }
            }
            //执行
            pStmt.execute();
            count = pStmt.getUpdateCount();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeObject(conn,pStmt,null);
        }
        return count;
    }

    //单例及初始化
    private static BaseDao INSTANCE;
    private BaseDao(){ }
    public static BaseDao getBaseDao(){
        return INSTANCE;
    }
    static {
        INSTANCE = new BaseDao();
        init();
    }
}
