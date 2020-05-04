package bean;

import java.math.BigDecimal;
import java.sql.Date;

public class ChargeInfoBean {

    private int cgid;

    private String cgno;

    private String cglocation;

    private BigDecimal cgmoney;

    private Date cgtime;

    private int sno;

    public int getCgid() {
        return cgid;
    }

    public void setCgid(int cgid) {
        this.cgid = cgid;
    }

    public String getCgno() {
        return cgno;
    }

    public void setCgno(String cgno) {
        this.cgno = cgno;
    }

    public String getCglocation() {
        return cglocation;
    }

    public void setCglocation(String cglocation) {
        this.cglocation = cglocation;
    }

    public BigDecimal getCgmoney() {
        return cgmoney;
    }

    public void setCgmoney(BigDecimal cgmoney) {
        this.cgmoney = cgmoney;
    }

    public Date getCgtime() {
        return cgtime;
    }

    public void setCgtime(Date cgtime) {
        this.cgtime = cgtime;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

}