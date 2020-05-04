package servlet;

import bean.ChargeInfoBean;
import bean.StudentInfoBean;
import dao.StudentInfoDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class StudentInfoServlet extends HttpServlet{

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //设置编码防止响应乱码
        req.setCharacterEncoding("UTF-8");
        //获取请求类型
        String reqType=req.getParameter("reqType");

        if("FIND_STUDENT_BY_CLASS_ID".equals(reqType)){
            //获取客户端输入的参数
            int cid=Integer.parseInt(req.getParameter("cid"));
            //创建学生数据库操作对象
            StudentInfoDao dao = new StudentInfoDao();
            List<StudentInfoBean> allStudentInfo=dao.findAllStudentByClassId(cid);
            //保存查询到的学生信息
            req.setAttribute("allStudentInfo", allStudentInfo);
            //将查询结果交给jsp处理
            req.getRequestDispatcher("/studentInfo.jsp").forward(req, resp);
        }
        if("ADD_STU_INFO".equals(reqType)){
            //获取客户端输入的参数
            int cid=Integer.parseInt(req.getParameter("classId"));
            String stuname=req.getParameter("stuSname");
            String stusex=req.getParameter("stuSsex");
            int stuage=Integer.parseInt(req.getParameter("stuSage"));
            StudentInfoBean bean =new StudentInfoBean();
            bean.setCid(cid);
            bean.setSbalance(BigDecimal.ZERO);
            bean.setSage(stuage);
            bean.setSname(stuname);
            bean.setSsex(stusex);
            //创建学生数据库操作对象
            StudentInfoDao dao = new StudentInfoDao();
            dao.addStudentInfo(bean);
            List<StudentInfoBean> allStudentInfo=dao.findAllStudentByClassId(cid);
            //保存查询到的学生信息
            req.setAttribute("allStudentInfo", allStudentInfo);
            //将查询结果交给jsp处理
            req.getRequestDispatcher("/studentInfo.jsp?cid="+cid+"").forward(req, resp);
        }

        if("FIND_STUDENT_BY_SNO".equals(reqType)){
            //获取学生学号
            int sno=Integer.parseInt(req.getParameter("sno"));
            //创建学生数据库对象
            StudentInfoDao dao = new StudentInfoDao();
            StudentInfoBean bean=dao.findStudentBySno(sno);
            //保存该学生信息
            req.setAttribute("stuInfo", bean);
            //将查询结果转交给jsp处理
            req.getRequestDispatcher("/chargeInfo.jsp").forward(req, resp);
        }
        if("UPDATE_STU_SBALANCE".equals(reqType)){
            //获取参数
            int cid=Integer.parseInt(req.getParameter("cid"));
            int sno=Integer.parseInt(req.getParameter("sno"));
            String location=req.getParameter("cglocation");
            String sbalanceStr =req.getParameter("amount");
            BigDecimal sbalance = new BigDecimal(sbalanceStr);
            //创建学生充值对象Bean
            ChargeInfoBean bean = new ChargeInfoBean();
            bean.setCglocation(location);
            bean.setCgmoney(sbalance);
            java.util.Date date = new java.util.Date();
            @SuppressWarnings("deprecation")
            int year=date.getYear();
            @SuppressWarnings("deprecation")
            int month=date.getMonth();
            @SuppressWarnings("deprecation")
            int day=date.getDate();
            bean.setCgtime(new Date(year, month, day));
            //生成交易流水号
            SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMddHHmmss");
            String cgno=sdf.format(date)+"_"+sno;
            bean.setCgno(cgno);
            bean.setSno(sno);
            //获取数据库操作对象
            StudentInfoDao dao = new StudentInfoDao();
            dao.updateStudentSbalanceBySno(bean);
            List<ChargeInfoBean> allChargetInfo=dao.findAllChargeInfoBySno(sno);
            //保存查询到的学生信息
            req.setAttribute("allChargeInfo", allChargetInfo);

            StudentInfoBean stubean=dao.findStudentBySno(sno);
            //保存该学生信息
            req.setAttribute("stuInfo", stubean);

            //将查询结果交给jsp处理
            req.getRequestDispatcher("/chargeInfo.jsp?cid="+cid+"").forward(req, resp);
        }
        if("QUERY_ALL_CHARGE_INFO_BY_SNO".equals(reqType)){
            int sno=Integer.parseInt(req.getParameter("sno"));
            int cid=Integer.parseInt(req.getParameter("cid"));
            //获取数据库操作对象
            StudentInfoDao dao = new StudentInfoDao();
            List<ChargeInfoBean> allChargetInfo=dao.findAllChargeInfoBySno(sno);
            //保存查询到的学生信息
            req.setAttribute("allChargeInfo", allChargetInfo);

            StudentInfoBean stubean=dao.findStudentBySno(sno);
            //保存该学生信息
            req.setAttribute("stuInfo", stubean);
            //将查询结果交给jsp处理
            req.getRequestDispatcher("/chargeInfo.jsp?cid="+cid+"").forward(req, resp);
        }
    }
}