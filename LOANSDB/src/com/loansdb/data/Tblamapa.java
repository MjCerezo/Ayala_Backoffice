
package com.loansdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  LOANSDB.Tblamapa
 *  10/13/2020 10:21:35
 * 
 */
public class Tblamapa {

    private Integer id;
    private String appno;
    private String acctno;
    private String cifno;
    private Date startreviewdate;
    private Date endreviewdate;
    private Integer totalnodays;
    private BigDecimal avefundsupplied;
    private BigDecimal reservereqrate;
    private BigDecimal reserveproduct;
    private BigDecimal depositnetreserve;
    private BigDecimal loans;
    private BigDecimal netfundsuppliedused;
    private BigDecimal aveintratefunds;
    private BigDecimal aveintrateloan;
    private BigDecimal interestincomeloan;
    private BigDecimal incomenetfundsupplied;
    private BigDecimal totalincomefunds;
    private BigDecimal tpr;
    private BigDecimal interestexpense;
    private BigDecimal expenseinterestrate;
    private BigDecimal costnetfundused;
    private BigDecimal totalexpensefunds;
    private BigDecimal netincomelossfunds;
    private BigDecimal ratereturn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getAcctno() {
        return acctno;
    }

    public void setAcctno(String acctno) {
        this.acctno = acctno;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public Date getStartreviewdate() {
        return startreviewdate;
    }

    public void setStartreviewdate(Date startreviewdate) {
        this.startreviewdate = startreviewdate;
    }

    public Date getEndreviewdate() {
        return endreviewdate;
    }

    public void setEndreviewdate(Date endreviewdate) {
        this.endreviewdate = endreviewdate;
    }

    public Integer getTotalnodays() {
        return totalnodays;
    }

    public void setTotalnodays(Integer totalnodays) {
        this.totalnodays = totalnodays;
    }

    public BigDecimal getAvefundsupplied() {
        return avefundsupplied;
    }

    public void setAvefundsupplied(BigDecimal avefundsupplied) {
        this.avefundsupplied = avefundsupplied;
    }

    public BigDecimal getReservereqrate() {
        return reservereqrate;
    }

    public void setReservereqrate(BigDecimal reservereqrate) {
        this.reservereqrate = reservereqrate;
    }

    public BigDecimal getReserveproduct() {
        return reserveproduct;
    }

    public void setReserveproduct(BigDecimal reserveproduct) {
        this.reserveproduct = reserveproduct;
    }

    public BigDecimal getDepositnetreserve() {
        return depositnetreserve;
    }

    public void setDepositnetreserve(BigDecimal depositnetreserve) {
        this.depositnetreserve = depositnetreserve;
    }

    public BigDecimal getLoans() {
        return loans;
    }

    public void setLoans(BigDecimal loans) {
        this.loans = loans;
    }

    public BigDecimal getNetfundsuppliedused() {
        return netfundsuppliedused;
    }

    public void setNetfundsuppliedused(BigDecimal netfundsuppliedused) {
        this.netfundsuppliedused = netfundsuppliedused;
    }

    public BigDecimal getAveintratefunds() {
        return aveintratefunds;
    }

    public void setAveintratefunds(BigDecimal aveintratefunds) {
        this.aveintratefunds = aveintratefunds;
    }

    public BigDecimal getAveintrateloan() {
        return aveintrateloan;
    }

    public void setAveintrateloan(BigDecimal aveintrateloan) {
        this.aveintrateloan = aveintrateloan;
    }

    public BigDecimal getInterestincomeloan() {
        return interestincomeloan;
    }

    public void setInterestincomeloan(BigDecimal interestincomeloan) {
        this.interestincomeloan = interestincomeloan;
    }

    public BigDecimal getIncomenetfundsupplied() {
        return incomenetfundsupplied;
    }

    public void setIncomenetfundsupplied(BigDecimal incomenetfundsupplied) {
        this.incomenetfundsupplied = incomenetfundsupplied;
    }

    public BigDecimal getTotalincomefunds() {
        return totalincomefunds;
    }

    public void setTotalincomefunds(BigDecimal totalincomefunds) {
        this.totalincomefunds = totalincomefunds;
    }

    public BigDecimal getTpr() {
        return tpr;
    }

    public void setTpr(BigDecimal tpr) {
        this.tpr = tpr;
    }

    public BigDecimal getInterestexpense() {
        return interestexpense;
    }

    public void setInterestexpense(BigDecimal interestexpense) {
        this.interestexpense = interestexpense;
    }

    public BigDecimal getExpenseinterestrate() {
        return expenseinterestrate;
    }

    public void setExpenseinterestrate(BigDecimal expenseinterestrate) {
        this.expenseinterestrate = expenseinterestrate;
    }

    public BigDecimal getCostnetfundused() {
        return costnetfundused;
    }

    public void setCostnetfundused(BigDecimal costnetfundused) {
        this.costnetfundused = costnetfundused;
    }

    public BigDecimal getTotalexpensefunds() {
        return totalexpensefunds;
    }

    public void setTotalexpensefunds(BigDecimal totalexpensefunds) {
        this.totalexpensefunds = totalexpensefunds;
    }

    public BigDecimal getNetincomelossfunds() {
        return netincomelossfunds;
    }

    public void setNetincomelossfunds(BigDecimal netincomelossfunds) {
        this.netincomelossfunds = netincomelossfunds;
    }

    public BigDecimal getRatereturn() {
        return ratereturn;
    }

    public void setRatereturn(BigDecimal ratereturn) {
        this.ratereturn = ratereturn;
    }

}
