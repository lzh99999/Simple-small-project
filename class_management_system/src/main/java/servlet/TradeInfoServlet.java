package servlet;

import bean.StudentInfoBean;
import bean.TradeInfoBean;
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

public class TradeInfoServlet extends HttpServlet{

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @SuppressWarnings("deprecation")
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //设置请求编码。防止乱码
        req.setCharacterEncoding("UTF-8");
        //获取请求类型
        String reqType=req.getParameter("reqType");
        //如果是新增消费请求
        if("ADD_NEW_TRADE_INFO".equals(reqType)){
            //获取消费参数
            String tradeLocation=req.getParameter("tradeLocation");
            String tradeGoods=req.getParameter("tradeGoods");
            String moneyStr=req.getParameter("tradeMoney");
            BigDecimal tradeMoney=new BigDecimal(moneyStr);
            //获取学号参数
            int sno=Integer.parseInt(req.getParameter("tradeStuSno"));
            //创建学生数据库操作对象
            StudentInfoDao studao = new StudentInfoDao();
            //根据学生获取该学生的余额
            StudentInfoBean stubean=studao.findStudentBySno(sno);
            BigDecimal sbalance =stubean.getSbalance();
            //如果该学生余额不足，则交易失败
            if(sbalance.compareTo(tradeMoney)==-1){
                resp.sendError(404, "余额不足，交易失败！请充值！");
            }else{
                //创建交易bean对象
                TradeInfoBean bean =new TradeInfoBean();
                bean.setSno(sno);
                bean.setTlocation(tradeLocation);
                bean.setTgoods(tradeGoods);
                bean.setTmoney(tradeMoney);
                java.util.Date date = new java.util.Date();
                @SuppressWarnings("deprecation")
                int year=date.getYear();
                @SuppressWarnings("deprecation")
                int month=date.getMonth();
                @SuppressWarnings("deprecation")
                int day=date.getDate();
                bean.setTdate(new Date(year, month, day));
                //生成交易流水号
                SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMddHHmmss");
                String tno=sdf.format(date)+"_"+sno;
                bean.setTno(tno);
                //事务机制控制
                studao.addTradeInfo(bean);
                //查询所有的消费记录
                List<TradeInfoBean> tradeInfo=studao.findAllTradeInfoBySno(sno);
                //保存所有记录
                req.setAttribute("allTradeInfo", tradeInfo);
                //转交给jsp处理
                req.getRequestDispatcher("/tradeInfo.jsp").forward(req, resp);
            }

        }

        //如果是查询消费信息
        if("QUERY_ALL_TRADE_INFO_BY_SNO".equals(reqType)){
            int sno=Integer.parseInt(req.getParameter("sno"));
            //创建数据库操作对象
            StudentInfoDao dao = new StudentInfoDao();
            //查询所有的消费记录
            List<TradeInfoBean> tradeInfo=dao.findAllTradeInfoBySno(sno);
            //保存所有记录
            req.setAttribute("allTradeInfo", tradeInfo);
            //转交给jsp处理
            req.getRequestDispatcher("/tradeInfo.jsp").forward(req, resp);
        }
    }
}