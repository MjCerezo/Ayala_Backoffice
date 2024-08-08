
package com.cifsdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  CIFSDB.Tblstcomakers
 *  09/26/2023 10:13:06
 * 
 */
public class Tblstcomakers {

    private TblstcomakersId id;
    private String cifno;
    private String customername;
    private Date dateadded;
    private String participationcode;
    private String membername;
    private String fulladdress;
    private String accountno;
    private BigDecimal monthlyincome;
    private BigDecimal businessincome;
    private BigDecimal otherincome;
    private BigDecimal totalincome;

    public TblstcomakersId getId() {
        return id;
    }

    public void setId(TblstcomakersId id) {
        this.id = id;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public Date getDateadded() {
        return dateadded;
    }

    public void setDateadded(Date dateadded) {
        this.dateadded = dateadded;
    }

    public String getParticipationcode() {
        return participationcode;
    }

    public void setParticipationcode(String participationcode) {
        this.participationcode = participationcode;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }

    public String getFulladdress() {
        return fulladdress;
    }

    public void setFulladdress(String fulladdress) {
        this.fulladdress = fulladdress;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public BigDecimal getMonthlyincome() {
        return monthlyincome;
    }

    public void setMonthlyincome(BigDecimal monthlyincome) {
        this.monthlyincome = monthlyincome;
    }

    public BigDecimal getBusinessincome() {
        return businessincome;
    }

    public void setBusinessincome(BigDecimal businessincome) {
        this.businessincome = businessincome;
    }

    public BigDecimal getOtherincome() {
        return otherincome;
    }

    public void setOtherincome(BigDecimal otherincome) {
        this.otherincome = otherincome;
    }

    public BigDecimal getTotalincome() {
        return totalincome;
    }

    public void setTotalincome(BigDecimal totalincome) {
        this.totalincome = totalincome;
    }

}
