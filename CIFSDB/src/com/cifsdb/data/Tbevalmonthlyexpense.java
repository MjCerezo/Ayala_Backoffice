
package com.cifsdb.data;

import java.math.BigDecimal;


/**
 *  CIFSDB.Tbevalmonthlyexpense
 *  09/26/2023 10:13:06
 * 
 */
public class Tbevalmonthlyexpense {

    private Integer id;
    private Integer evalreportid;
    private String appno;
    private String persontype;
    private String expensetype;
    private BigDecimal expenseamount;
    private String remarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEvalreportid() {
        return evalreportid;
    }

    public void setEvalreportid(Integer evalreportid) {
        this.evalreportid = evalreportid;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getPersontype() {
        return persontype;
    }

    public void setPersontype(String persontype) {
        this.persontype = persontype;
    }

    public String getExpensetype() {
        return expensetype;
    }

    public void setExpensetype(String expensetype) {
        this.expensetype = expensetype;
    }

    public BigDecimal getExpenseamount() {
        return expenseamount;
    }

    public void setExpenseamount(BigDecimal expenseamount) {
        this.expenseamount = expenseamount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
