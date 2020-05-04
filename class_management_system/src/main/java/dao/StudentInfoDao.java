package dao;

import bean.ChargeInfoBean;
import bean.StudentInfoBean;
import bean.TradeInfoBean;
import util.DBUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentInfoDao {

    public void addStudentInfo(StudentInfoBean bean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // 获取数据库连接
            conn = DBUtil.getConnection();
            // 整理一条SQL语句
            String sql = "INSERT INTO student_info (sname,ssex,sage,cid,sbalance) VALUES (?,?,?,?,?)";
            // 创建SQL执行对象
            pstmt = conn.prepareStatement(sql);
            // 给预处理对象赋值
            pstmt.setString(1, bean.getSname());
            pstmt.setString(2, bean.getSsex());
            pstmt.setInt(3, bean.getSage());
            pstmt.setInt(4, bean.getCid());
            pstmt.setBigDecimal(5, BigDecimal.ZERO);
            //执行SQL语句
            int row = pstmt.executeUpdate();
            if (row != 1) {
                throw new RuntimeException("新增学生信息失败！");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.release(conn, pstmt, null);
        }
    }

    public void updateStudentSbalanceBySno(ChargeInfoBean bean) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // 获取连接
            conn = DBUtil.getConnection();
            //事物控制机制
            conn.setAutoCommit(false);
            // 整理一条SQL语句
            //String sql = "SELECT sno,sname,ssex,sage,sbalance from student_info where cid=?";
            // 创建执行sql的对象
            pstmt = conn.prepareStatement("UPDATE student_info SET sbalance=sbalance+? WHERE SNO=?");
            //执行sql语句
            pstmt.setBigDecimal(1, bean.getCgmoney());
            pstmt.setInt(2, bean.getSno());
            int row = pstmt.executeUpdate();
            if (row != 1) {
                throw new RuntimeException("更新学生余额失败！");
            } else {
                //更新学生充值数据表
                //创建预编译对象
                pstmt = conn.prepareStatement("INSERT INTO charge_info (cgno,sno,cglocation,cgtime,cgmoney) VALUES(?,?,?,?,?)");
                //给预编译对象赋值
                pstmt.setString(1, bean.getCgno());
                pstmt.setInt(2, bean.getSno());
                pstmt.setString(3, bean.getCglocation());
                pstmt.setDate(4, bean.getCgtime());
                pstmt.setBigDecimal(5, bean.getCgmoney());
                //执行sql语句
                int row1 = pstmt.executeUpdate();
                if (row1 != 1) {
                    throw new RuntimeException("更新交易流水号失败");
                } else {
                    conn.commit();
                }
            }
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                throw new RuntimeException(e1);
            }
        } finally {
            DBUtil.release(conn, pstmt, null);
        }
    }

    public StudentInfoBean findStudentBySno(int sno) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StudentInfoBean bean = null;
        try {
            // 获取连接
            conn = DBUtil.getConnection();
            // 整理一条SQL语句
            //String sql = "SELECT sno,sname,ssex,sage,sbalance from student_info where cid=?";
            // 创建执行sql的对象
            pstmt = conn.prepareStatement("SELECT cid,sno,sname,ssex,sage,sbalance from student_info where sno=?");
            //执行sql语句
            pstmt.setInt(1, sno);
            rs = pstmt.executeQuery();
            //遍历结果集
            while (rs.next()) {
                int scid = rs.getInt("cid");
                int stusno = rs.getInt("sno");
                String name = rs.getString("sname");
                String ssex = rs.getString("ssex");
                int sage = rs.getInt("sage");
                BigDecimal sbalance = rs.getBigDecimal("sbalance");
                //封装成学生Bean对象
                bean = new StudentInfoBean();
                bean.setCid(scid);
                bean.setSno(sno);
                bean.setSname(name);
                bean.setSage(sage);
                bean.setSsex(ssex);
                bean.setSbalance(sbalance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.release(conn, pstmt, rs);
        }
        return bean;
    }

    public List<StudentInfoBean> findAllStudentByClassId(int cid) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<StudentInfoBean> stuList = new ArrayList<StudentInfoBean>();
        try {
            // 获取连接
            conn = DBUtil.getConnection();
            // 整理一条SQL语句
            //String sql = "SELECT sno,sname,ssex,sage,sbalance from student_info where cid=?";
            // 创建执行sql的对象
            pstmt = conn.prepareStatement("SELECT cid,sno,sname,ssex,sage,sbalance from student_info where cid=?");
            //执行sql语句
            pstmt.setInt(1, cid);
            rs = pstmt.executeQuery();
            //遍历结果集
            while (rs.next()) {
                int scid = rs.getInt("cid");
                int sno = rs.getInt("sno");
                String name = rs.getString("sname");
                String ssex = rs.getString("ssex");
                int sage = rs.getInt("sage");
                BigDecimal sbalance = rs.getBigDecimal("sbalance");
                //封装成学生Bean对象
                StudentInfoBean bean = new StudentInfoBean();
                bean.setCid(scid);
                bean.setSno(sno);
                bean.setSname(name);
                bean.setSage(sage);
                bean.setSsex(ssex);
                bean.setSbalance(sbalance);
                //添加到队列中
                stuList.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.release(conn, pstmt, rs);
        }
        return stuList;
    }

    /*
     * 学生交易相关方法
     */
    public void addTradeInfo(TradeInfoBean bean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // 获取数据库连接
            conn = DBUtil.getConnection();
            //事物控制
            conn.setAutoCommit(false);
            // 整理一条SQL语句
            //创建预编译对象
            pstmt = conn.prepareStatement("INSERT INTO trade_info (tno,sno,tlocation,tgoods,tdate,tmoney) VALUES(?,?,?,?,?,?)");
            //给预编译对象赋值
            pstmt.setString(1, bean.getTno());
            pstmt.setInt(2, bean.getSno());
            pstmt.setString(3, bean.getTlocation());
            pstmt.setString(4, bean.getTgoods());
            pstmt.setDate(5, bean.getTdate());
            pstmt.setBigDecimal(6, bean.getTmoney());
            //执行sql语句
            int row = pstmt.executeUpdate();
            if (row != 1) {
                throw new RuntimeException("更新交易流水号失败");
            } else {
                pstmt = conn.prepareStatement("UPDATE student_info SET sbalance=sbalance+? WHERE sno=?");
                //执行sql语句
                pstmt.setBigDecimal(1, bean.getTmoney().negate());
                pstmt.setInt(2, bean.getSno());
                int row1 = pstmt.executeUpdate();
                if (row1 != 1) {
                    throw new RuntimeException("更新学生余额失败！");
                } else {
                    conn.commit();
                }
            }
        } catch (Exception e) {
            try {
                //抛异常，数据回滚
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
                throw new RuntimeException(e);
            }
        } finally {
            DBUtil.release(conn, pstmt, null);
        }
    }

    public List<TradeInfoBean> findAllTradeInfoBySno(int sno) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<TradeInfoBean> tradeInfoList = new ArrayList<TradeInfoBean>();
        try {
            // 获取连接
            conn = DBUtil.getConnection();
            // 创建执行sql的对象
            pstmt = conn.prepareStatement("SELECT tid,tno,sno,tlocation,tgoods,tdate,tmoney FROM trade_info WHERE sno=? ORDER BY tdate DESC");
            //执行sql语句
            pstmt.setInt(1, sno);
            rs = pstmt.executeQuery();
            //遍历结果集
            while (rs.next()) {
                int tid = rs.getInt("tid");
                String tno = rs.getString("tno");
                int sno1 = rs.getInt("sno");
                String tlocation = rs.getString("tlocation");
                String tgoods = rs.getString("tgoods");
                Date tdate = rs.getDate("tdate");
                BigDecimal tmoney = rs.getBigDecimal("tmoney");
                TradeInfoBean bean = new TradeInfoBean();
                bean.setTno(tno);
                bean.setSno(sno1);
                bean.setTdate(tdate);
                bean.setTgoods(tgoods);
                bean.setTid(tid);
                bean.setTlocation(tlocation);
                bean.setTmoney(tmoney);
                tradeInfoList.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.release(conn, pstmt, rs);
        }
        return tradeInfoList;
    }

    public List<ChargeInfoBean> findAllChargeInfoBySno(int sno) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ChargeInfoBean> chargeInfoList = new ArrayList<ChargeInfoBean>();
        try {
            // 获取连接
            conn = DBUtil.getConnection();
            // 创建执行sql的对象
            pstmt = conn.prepareStatement("SELECT sno,cgno,cglocation,cgtime,cgmoney FROM charge_info WHERE sno=? ORDER BY cgtime DESC");
            //执行sql语句
            pstmt.setInt(1, sno);
            rs = pstmt.executeQuery();
            //遍历结果集
            while (rs.next()) {
                int sno1 = rs.getInt("sno");
                String cgno = rs.getString("cgno");
                String location = rs.getString("cglocation");
                Date cgtime = rs.getDate("cgtime");
                BigDecimal cgmoney = rs.getBigDecimal("cgmoney");
                ChargeInfoBean bean = new ChargeInfoBean();
                bean.setSno(sno1);
                bean.setCgno(cgno);
                bean.setCglocation(location);
                bean.setCgtime(cgtime);
                bean.setCgmoney(cgmoney);
                chargeInfoList.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.release(conn, pstmt, rs);
        }
        return chargeInfoList;
    }

}