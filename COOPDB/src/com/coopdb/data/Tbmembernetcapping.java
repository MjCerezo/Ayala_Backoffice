
package com.coopdb.data;

import java.math.BigDecimal;


/**
 *  COOPDB.Tbmembernetcapping
 *  08/10/2024 21:24:57
 * 
 */
public class Tbmembernetcapping {

    private String appno;
    private String cifno;
    private BigDecimal basicsalary;
    private BigDecimal withholdingtax;
    private BigDecimal existingamort;

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

}
