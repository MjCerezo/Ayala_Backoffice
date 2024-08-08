
package com.loansdb.data;



/**
 *  LOANSDB.Tbloanrepaymentscheme
 *  10/13/2020 10:21:35
 * 
 */
public class Tbloanrepaymentscheme {

    private Integer schemecode;
    private String repaymentscheme;
    private String intpaytype;
    private String description;

    public Integer getSchemecode() {
        return schemecode;
    }

    public void setSchemecode(Integer schemecode) {
        this.schemecode = schemecode;
    }

    public String getRepaymentscheme() {
        return repaymentscheme;
    }

    public void setRepaymentscheme(String repaymentscheme) {
        this.repaymentscheme = repaymentscheme;
    }

    public String getIntpaytype() {
        return intpaytype;
    }

    public void setIntpaytype(String intpaytype) {
        this.intpaytype = intpaytype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
