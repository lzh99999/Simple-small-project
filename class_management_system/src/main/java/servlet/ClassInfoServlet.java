package servlet;

import bean.ClassInfoBean;
import dao.ClassInfoDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ClassInfoServlet extends HttpServlet{

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //设置编码，防止请求乱码
        req.setCharacterEncoding("UTF-8");
        //获取参数
        //String className=req.getParameter("className");
        //获取请求类型
        String reqType=req.getParameter("reqType");

        if("QUERY_ALL_CLASS".equals(reqType)){
            //创建数据库操作对象
            ClassInfoDao dao =new ClassInfoDao();
            //查询所有班级信息
            List<ClassInfoBean> classInfo=dao.findAll();
            //保存查询的班级信息
            req.setAttribute("classInfo", classInfo);
            //转发请求
            req.getRequestDispatcher("/classInfo.jsp").forward(req, resp);
        }

        if("ADD_NEW_CLASS_INFO".equals(reqType)){
            //获取参数
            String className=req.getParameter("className");
            //创建ClassInfoBean对象保存信息
            ClassInfoBean bean =new ClassInfoBean();
            bean.setCname(className);
            //创建数据库操作对象
            ClassInfoDao dao =new ClassInfoDao();
            //新增班级信息到数据库
            dao.addClassInfo(bean);
            //查询所有班级信息
            List<ClassInfoBean> classInfo=dao.findAll();
            //保存查询的班级信息
            req.setAttribute("classInfo", classInfo);
            //转发请求
            req.getRequestDispatcher("/classInfo.jsp").forward(req, resp);
        }
    }
}