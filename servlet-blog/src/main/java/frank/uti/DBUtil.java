package frank.uti;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import frank.exception.SystemException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

    private static final String URL = "jdbc:mysql://localhost:3306/blogdemo";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "99999";

 
    private static volatile DataSource DS;

    private static DataSource getDataSource() {
        if(DS == null){
            synchronized (DBUtil.class){
                if(DS == null){
                    DS = new MysqlDataSource();
                    ((MysqlDataSource) DS).setUrl(URL);
                    ((MysqlDataSource) DS).setUser(USERNAME);
                    ((MysqlDataSource) DS).setPassword(PASSWORD);
                }
            }
        }
        return DS;
    }

    // 获取数据库连接
    public static Connection getConnection(){
        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            throw new SystemException("000", "获取数据库连接失败", e);
        }
    }

    //插入/修改/删除不需要释放ResultSet
    public static void close(Connection c, Statement s){
        close(c, s, null);
    }

    // 释放资源操作：数据库服务请求/响应也是基于网络数据传输的，也就是网络IO，
    // 需要在使用完以后释放资源
    public static void close(Connection c, Statement s, ResultSet r){
        try {
            if(c != null)
                c.close();
            if(s != null)
                s.close();
            if(r != null)
                r.close();
        } catch (SQLException e) {
            throw new SystemException("000", "释放数据库资源失败", e);
        }
    }
}
