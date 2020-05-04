package servlet;

import util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;encoding=utf-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet resultSet = null;
        try {
            // 获取数据库连接
            conn= DBUtil.getConnection();
            // 整理一条SQL语句
            String sql = "select * from admin where username=?and password=?;";
            // 创建SQL执行对象
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1,username);
            pStmt.setString(2,password);
            // 执行sql语句
            resultSet = pStmt.executeQuery();
            while (resultSet.next()) {
                String ps = resultSet.getString("password");
                String un = resultSet.getString("username");
                if("123".equals(ps)&&"root".equals(un)) {
                    request.getRequestDispatcher("classInfo.jsp").forward(request,response);
                }
            }
            request.getRequestDispatcher("error.jsp").forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            DBUtil.release(conn, pStmt, resultSet);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
