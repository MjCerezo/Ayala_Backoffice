
package com.coopdb.data;

import java.math.BigDecimal;


/**
 *  COOPDB.Tbapaotherincomeexpense
 *  08/04/2024 12:54:44
 * 
 */
public class Tbapaotherincomeexpense {

    private Integer id;
    private Integer evalreportid;
    private String appno;
    private String fundtype;
    private String incomename;
    private BigDecimal incomeamount;
    private String expensename;
    private BigDecimal expenseamount;

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

    public String getFundtype() {
        return fundtype;
    }

    public void setFundtype(String fundtype) {
        this.fundtype = fundtype;
    }

    public String getIncomename() {
        return incomename;
    }

    public void setIncomename(String incomename) {
        this.incomename = incomename;
    }

    public BigDecimal getIncomeamount() {
        return incomeamount;
    }

    public void setIncomeamount(BigDecimal incomeamount) {
        this.incomeamount = incomeamount;
    }

    public String getExpensename() {
        return expensename;
    }

    public void setExpensename(String expensename) {
        this.expensename = expensename;
    }

    public BigDecimal getExpenseamount() {
        return expenseamount;
    }

    public void setExpenseamount(BigDecimal expenseamount) {
        this.expenseamount = expenseamount;
    }

}
