package bean;

import java.math.BigDecimal;
import java.sql.Date;


public class TradeInfoBean {

    private int tid;

    private String tno;

    private int sno;

    private String tlocation;

    private String tgoods;

    private Date tdate;

    private BigDecimal tmoney;

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTno() {
        return tno;
    }

    public void setTno(String tno) {
        this.tno = tno;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getTlocation() {
        return tlocation;
    }

    public void setTlocation(String tlocation) {
        this.tlocation = tlocation;
    }

    public String getTgoods() {
        return tgoods;
    }

    public void setTgoods(String tgoods) {
        this.tgoods = tgoods;
    }

    public Date getTdate() {
        return tdate;
    }

    public void setTdate(Date tdate) {
        this.tdate = tdate;
    }

    public BigDecimal getTmoney() {
        return tmoney;
    }

    public void setTmoney(BigDecimal tmoney) {
        this.tmoney = tmoney;
    }
}