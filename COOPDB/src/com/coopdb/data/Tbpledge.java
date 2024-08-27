
package com.coopdb.data;

import java.math.BigDecimal;


/**
 *  COOPDB.Tbpledge
 *  08/27/2024 14:22:57
 * 
 */
public class Tbpledge {

    private Integer id;
    private String membershipid;
    private String membershipappid;
    private String savingsacctno;
    private BigDecimal savingspledgeamt;
    private String savingspycycle;
    private String capconacctno;
    private BigDecimal capconpledgeamt;
    private String capconpycycle;
    private Integer capcontermno;
    private Integer noofshare;
    private BigDecimal shareparvalue;
    private BigDecimal totalcapcon;
    private String capcontermperiod;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMembershipid() {
        return membershipid;
    }

    public void setMembershipid(String membershipid) {
        this.membershipid = membershipid;
    }

    public String getMembershipappid() {
        return membershipappid;
    }

    public void setMembershipappid(String membershipappid) {
        this.membershipappid = membershipappid;
    }

    public String getSavingsacctno() {
        return savingsacctno;
    }

    public void setSavingsacctno(String savingsacctno) {
        this.savingsacctno = savingsacctno;
    }

    public BigDecimal getSavingspledgeamt() {
        return savingspledgeamt;
    }

    public void setSavingspledgeamt(BigDecimal savingspledgeamt) {
        this.savingspledgeamt = savingspledgeamt;
    }

    public String getSavingspycycle() {
        return savingspycycle;
    }

    public void setSavingspycycle(String savingspycycle) {
        this.savingspycycle = savingspycycle;
    }

    public String getCapconacctno() {
        return capconacctno;
    }

    public void setCapconacctno(String capconacctno) {
        this.capconacctno = capconacctno;
    }

    public BigDecimal getCapconpledgeamt() {
        return capconpledgeamt;
    }

    public void setCapconpledgeamt(BigDecimal capconpledgeamt) {
        this.capconpledgeamt = capconpledgeamt;
    }

    public String getCapconpycycle() {
        return capconpycycle;
    }

    public void setCapconpycycle(String capconpycycle) {
        this.capconpycycle = capconpycycle;
    }

    public Integer getCapcontermno() {
        return capcontermno;
    }

    public void setCapcontermno(Integer capcontermno) {
        this.capcontermno = capcontermno;
    }

    public Integer getNoofshare() {
        return noofshare;
    }

    public void setNoofshare(Integer noofshare) {
        this.noofshare = noofshare;
    }

    public BigDecimal getShareparvalue() {
        return shareparvalue;
    }

    public void setShareparvalue(BigDecimal shareparvalue) {
        this.shareparvalue = shareparvalue;
    }

    public BigDecimal getTotalcapcon() {
        return totalcapcon;
    }

    public void setTotalcapcon(BigDecimal totalcapcon) {
        this.totalcapcon = totalcapcon;
    }

    public String getCapcontermperiod() {
        return capcontermperiod;
    }

    public void setCapcontermperiod(String capcontermperiod) {
        this.capcontermperiod = capcontermperiod;
    }

}
