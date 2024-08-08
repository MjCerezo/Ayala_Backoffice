
package com.loansdb.data;



/**
 *  LOANSDB.Tbpolicyerrorresults
 *  10/13/2020 10:21:35
 * 
 */
public class Tbpolicyerrorresults {

    private Integer id;
    private String appno;
    private String characteristics;
    private String value;
    private String remarks;
    private String approvallevel;

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

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getApprovallevel() {
        return approvallevel;
    }

    public void setApprovallevel(String approvallevel) {
        this.approvallevel = approvallevel;
    }

}
