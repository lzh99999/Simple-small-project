package dao;

import bean.ClassInfoBean;
import util.DBUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClassInfoDao {

    public void addClassInfo(ClassInfoBean bean) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // 获取数据库连接
            conn=DBUtil.getConnection();
            // 整理一条SQL语句
            String sql = "INSERT INTO class_info (cname) VALUES ('"
                    + bean.getCname() + "')";
            // 创建SQL执行对象
            stmt = conn.createStatement();
            // 执行sql语句
            int row = stmt.executeUpdate(sql);
            if (row != 1) {
                throw new RuntimeException("新增班级失败！");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            DBUtil.release(conn, stmt, null);
        }
    }

    public List<ClassInfoBean> findAll() {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs =null;
        List<ClassInfoBean> classList= new ArrayList<ClassInfoBean>();
        try {
            // 获取连接
            conn = DBUtil.getConnection();
            // 整理一条SQL语句
            String sql = "select cid,cname from class_info";
            // 创建执行sql的对象
            stmt = conn.createStatement();
            //执行sql语句
            rs =stmt.executeQuery(sql);
            //遍历结果集
            while(rs.next()){
                int cid =rs.getInt("cid");
                String cname=rs.getString("cname");
                ClassInfoBean bean = new ClassInfoBean();
                bean.setCid(cid);
                bean.setCname(cname);
                classList.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            DBUtil.release(conn, stmt, rs);
        }
        return classList;
    }
}