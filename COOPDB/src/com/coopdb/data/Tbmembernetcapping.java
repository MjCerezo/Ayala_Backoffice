
package com.coopdb.data;

import java.math.BigDecimal;


/**
 *  COOPDB.Tbmembernetcapping
 *  08/27/2024 14:22:57
 * 
 */
public class Tbmembernetcapping {

    private String appno;
    private String cifno;
    private BigDecimal basicsalary;
    private BigDecimal withholdingtax;
    private BigDecimal existingamort;
    private BigDecimal nthp;

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public BigDecimal getBasicsalary() {
        return basicsalary;
    }

    public void setBasicsalary(BigDecimal basicsalary) {
        this.basicsalary = basicsalary;
    }

    public BigDecimal getWithholdingtax() {
        return withholdingtax;
    }

    public void setWithholdingtax(BigDecimal withholdingtax) {
        this.withholdingtax = withholdingtax;
    }

    public BigDecimal getExistingamort() {
        return existingamort;
    }

    public void setExistingamort(BigDecimal existingamort) {
        this.existingamort = existingamort;
    }

    public BigDecimal getNthp() {
        return nthp;
    }

    public void setNthp(BigDecimal nthp) {
        this.nthp = nthp;
    }

}
