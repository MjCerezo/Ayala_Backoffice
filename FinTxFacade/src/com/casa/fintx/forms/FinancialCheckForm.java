package com.casa.fintx.forms;

import java.math.BigDecimal;

/**
 * Created by saoDG on 5/25/2018.
 */
public class FinancialCheckForm {

    private String txrefmain;
    private String checkno;
    private BigDecimal checkamt;
    private String clearingtype;
    private String draweebank;
    private String brstnno;

    public String getTxrefmain() {
        return txrefmain;
    }

    public void setTxrefmain(String txrefmain) {
        this.txrefmain = txrefmain;
    }

    public String getCheckno() {
        return checkno;
    }

    public void setCheckno(String checkno) {
        this.checkno = checkno;
    }

    public BigDecimal getCheckamt() {
        return checkamt;
    }

    public void setCheckamt(BigDecimal checkamt) {
        this.checkamt = checkamt;
    }

    public String getClearingtype() {
        return clearingtype;
    }

    public void setClearingtype(String clearingtype) {
        this.clearingtype = clearingtype;
    }

    public String getDraweebank() {
        return draweebank;
    }

    public void setDraweebank(String draweebank) {
        this.draweebank = draweebank;
    }

    public String getBrstnno() {
        return brstnno;
    }

    public void setBrstnno(String brstnno) {
        this.brstnno = brstnno;
    }

}
