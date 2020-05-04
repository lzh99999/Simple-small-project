<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="bean.StudentInfoBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>新增学生信息页面</title>
    <style type="text/css">
        tr {
            text-align: center;
        }
    </style>
</head>
<body>

<div style="margin: 0 auto; margin-top: 40px;">
    <form action="addStuInfoReq?reqType=ADD_STU_INFO" method="POST">
        <center>
            <table width="40%" border="1" bgcolor="#FFFFC6">
                <caption style="margin-bottom: 20px">新增学生信息</caption>
                <tr>
                    <td>学生姓名：</td>
                    <td><input type="text" name="stuSname"></td>
                </tr>
                <tr>
                    <td>学生性别：</td>
                    <td><select name="stuSsex">
                        <option>男</option>
                        <option>女</option>
                    </select></td>
                </tr>
                <tr>
                    <td>学生年龄：</td>
                    <td><input type="text" name="stuSage"> <input
                            type="hidden" name="classId"
                            value="<%=request.getParameter("cid")%>"></td>
                </tr>
                <tr>
                    <td><input type="reset" value="重置"></td>
                    <td><input type="submit" value="确定"></td>
                </tr>
            </table>
        </center>
    </form>
</div>

<div style="margin: 0 auto; margin-top: 40px;">
    <center>
        <table width="80%" border="1" bgcolor="#FFFFC6">
            <caption style="margin-bottom: 20px">学生信息列表</caption>
            <tr>
                <th>所属班级</th>
                <th>学生学号</th>
                <th>学生姓名</th>
                <th>学生性别</th>
                <th>学生年龄</th>
                <th>余额</th>
                <th>  操作类型  </th>
            </tr>
            <%
                List<StudentInfoBean> stuInfo=(List<StudentInfoBean>)request.getAttribute("allStudentInfo");
                for(StudentInfoBean stu: stuInfo){
            %>
            <tr>
                <td><%=stu.getCid()%></td>
                <td><%=stu.getSno()%></td>
                <td><%=stu.getSname()%></td>
                <td><%=stu.getSsex()%></td>
                <td><%=stu.getSage()%></td>
                <td><%=stu.getSbalance()%></td>
                <td><a
                        href="http://localhost:8080/class_system/findStudentBySno?reqType=FIND_STUDENT_BY_SNO&sno=<%=stu.getSno()%>&cid=<%=stu.getCid()%>">充值</a>    
                    <a href="http://localhost:8080/class_system/tradeInfo.jsp?sno=<%=stu.getSno()%>">消费</a></td>
            </tr>
            <%
                }
            %>
        </table>
    </center>
</div>
</body>
</html>