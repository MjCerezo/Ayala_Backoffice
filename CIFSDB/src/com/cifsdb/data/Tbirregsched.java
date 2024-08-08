
package com.cifsdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  CIFSDB.Tbirregsched
 *  09/26/2023 10:13:06
 * 
 */
public class Tbirregsched {

    private Integer id;
    private String loanappno;
    private Date transdate;
    private BigDecimal transamount;
    private BigDecimal principal;

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

}
