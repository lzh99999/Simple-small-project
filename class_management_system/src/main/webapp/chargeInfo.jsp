<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="bean.StudentInfoBean"%>
<%@ page import="bean.ChargeInfoBean"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>学生充值界面</title>
    <style type="text/css">
        tr {
            text-align: center;
        }
    </style>
</head>
<body>

<div>
    <center>
        <table width="40%" ; border="1" ; bgcolor="#FFFFC6">
            <caption style="margin-bottom: 20px">学生信息</caption>
            <%
                StudentInfoBean bean = (StudentInfoBean) request
                        .getAttribute("stuInfo");
            %>
            <tr>
                <td>学生班级：</td>
                <td><%=bean.getCid()%></td>
            </tr>
            <tr>
                <td>学生姓名：</td>
                <td><%=bean.getSname()%></td>
            </tr>
            <tr>
                <td>学生学号：</td>
                <td><%=bean.getSno()%></td>
            </tr>
            <tr>
                <td>学生性别：</td>
                <td><%=bean.getSsex()%></td>
            </tr>
            <tr>
                <td>学生年龄：</td>
                <td><%=bean.getSage()%></td>
            </tr>
            <tr>
                <td>剩余金额：</td>
                <td><%=bean.getSbalance()%></td>
            </tr>
        </table>
    </center>
</div>
<div style="margin: 0 auto; margin-top: 40px;">
    <form action="chargeReq?reqType=UPDATE_STU_SBALANCE" method="POST">
        <center>
            <table width="40%" ; border="1" ; bgcolor="#FFFFC6">
                <caption style="margin-bottom: 20px">充值信息</caption>
                <tr>
                    <td>充值地点：</td><td>
                    <select name="cglocation">
                        <option>南校充值点</option>
                        <option>北校充值点</option>
                    </select>
                </tr>
                <tr>
                    </td>
                    <td>充值金额：</td>
                    <td><input type="text" name="amount"> <input
                            type="hidden" name="sno" value="<%=bean.getSno()%>">
                        <input
                                type="hidden" name="cid" value="<%=bean.getCid()%>"></td>
                </tr>
                <tr>
                    <td><input type="reset" value="重置"></td>
                    <td><input type="submit" value="确定"></td>
                </tr>
            </table>
        </center>
    </form>
</div>

<div>
    <div style="margin: 0 auto; margin-top: 40px;">
        <center>
            <table width="70%" border="1" bgcolor="#FFFFC6">
                <caption style="margin-bottom: 20px">
                    学生充值详细信息列表
                    <div style="float: right; margin: 0 auto;">
                        <form action="showChargeInfoReq?reqType=QUERY_ALL_CHARGE_INFO_BY_SNO&sno=<%=request.getParameter("sno")%>&cid=<%=bean.getSno()%>"
                              method="POST">
                            <center>
                                <table width="40%" border="0">
                                    <tr>
                                        <td><input type="submit" value="显示所有充值记录"></td>
                                    </tr>
                                </table>
                            </center>
                        </form>
                    </div>
                </caption>
                <tr>
                    <th>充值流水号</th>
                    <th>充值地点</th>
                    <th>充值时间</th>
                    <th>充值金额</th>
                </tr>
                <%
                    List<ChargeInfoBean> chargeInfo=(List<ChargeInfoBean>)request.getAttribute("allChargeInfo");
                    if(chargeInfo!=null && !chargeInfo.isEmpty()){
                        for(ChargeInfoBean chargeInfos : chargeInfo){
                %>
                <tr>
                    <td><%=chargeInfos.getCgno()%></td>
                    <td><%=chargeInfos.getCglocation()%></td>
                    <td><%=chargeInfos.getCgtime()%></td>
                    <td><%=chargeInfos.getCgmoney()%></td>
                </tr>
                <%
                        }
                    }
                %>
            </table>
        </center>
    </div>
</div>
</body>
</html>