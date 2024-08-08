
package com.loansdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  LOANSDB.Tbirregsched
 *  10/13/2020 10:21:35
 * 
 */
public class Tbirregsched {

    private Integer id;
    private String loanappno;
    private Date transdate;
    private BigDecimal transamount;
    private BigDecimal principal;
    private String checkno;
    private String referenceno;
    private String receivabletype;
    private Integer receivableid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoanappno() {
        return loanappno;
    }

    public void setLoanappno(String loanappno) {
        this.loanappno = loanappno;
    }

    public Date getTransdate() {
        return transdate;
    }

    public void setTransdate(Date transdate) {
        this.transdate = transdate;
    }

    public BigDecimal getTransamount() {
        return transamount;
    }

    public void setTransamount(BigDecimal transamount) {
        this.transamount = transamount;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public String getCheckno() {
        return checkno;
    }

    public void setCheckno(String checkno) {
        this.checkno = checkno;
    }

    public String getReferenceno() {
        return referenceno;
    }

    public void setReferenceno(String referenceno) {
        this.referenceno = referenceno;
    }

    public String getReceivabletype() {
        return receivabletype;
    }

    public void setReceivabletype(String receivabletype) {
        this.receivabletype = receivabletype;
    }

    public Integer getReceivableid() {
        return receivableid;
    }

    public void setReceivableid(Integer receivableid) {
        this.receivableid = receivableid;
    }

}
