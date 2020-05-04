<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="bean.TradeInfoBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>学生消费信息界面</title>
    <style type="text/css">
        th {
            text-align: center;
        }

        td {
            text-align: center;
        }
    </style>
</head>
<body>

<div style="margin: 0 auto; margin-top: 40px;">
    <form action="addtradeInfoReq?reqType=ADD_NEW_TRADE_INFO" method="POST">
        <center>
            <table width="40%" ; border="1" ; bgcolor="#FFFFC6">
                <caption style="margin-bottom: 20px">新增消费信息</caption>
                <tr>
                    <td>消费地点：</td>
                    <td><input type="text" name="tradeLocation"></td>
                </tr>
                <tr>
                    <td>消费服务：</td>
                    <td><input type="text" name="tradeGoods"></td>
                </tr>
                <tr>
                    <td>消费金额：</td>
                    <td><input type="text" name="tradeMoney"><input
                            type="hidden" name="tradeStuSno" value="<%=request.getParameter("sno")%>"></td>
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
            <caption style="margin-bottom: 20px">
                学生消费详细信息列表
                <div style="float: right; margin: 0 auto;">
                    <form action="showTradeInfoReq?reqType=QUERY_ALL_TRADE_INFO_BY_SNO&sno=<%=request.getParameter("sno")%>"
                          method="POST">
                        <center>
                            <table width="40%" border="0">
                                <tr>
                                    <td><input type="submit" value="显示所有所有消费记录"></td>
                                </tr>
                            </table>
                        </center>
                    </form>
                </div>
            </caption>
            <tr>
                <th>交易流水号</th>
                <th>消费地点</th>
                <th>消费服务</th>
                <th>消费时间</th>
                <th>消费金额</th>
            </tr>
            <%
                List<TradeInfoBean> tradeInfo=(List<TradeInfoBean>)request.getAttribute("allTradeInfo");
                if(tradeInfo!=null && !tradeInfo.isEmpty()){
                    for(TradeInfoBean tradeInfos : tradeInfo){
            %>
            <tr>
                <td><%=tradeInfos.getTno()%></td>
                <td><%=tradeInfos.getTlocation()%></td>
                <td><%=tradeInfos.getTgoods()%></td>
                <td><%=tradeInfos.getTdate()%></td>
                <td><%=tradeInfos.getTmoney()%></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </center>
</div>
</body>
</html>