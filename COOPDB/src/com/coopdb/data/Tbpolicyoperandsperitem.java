
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbpolicyoperandsperitem
 *  08/04/2024 12:54:44
 * 
 */
public class Tbpolicyoperandsperitem {

    private Integer id;
    private String modelno;
    private String policykey;
    private String policydesc;
    private String fielddesc;
    private String operand1;
    private String value1;
    private String operand2;
    private String value2;
    private String approvallevel;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date dateupdated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public String getPolicykey() {
        return policykey;
    }

    public void setPolicykey(String policykey) {
        this.policykey = policykey;
    }

    public String getPolicydesc() {
        return policydesc;
    }

    public void setPolicydesc(String policydesc) {
        this.policydesc = policydesc;
    }

    public String getFielddesc() {
        return fielddesc;
    }

    public void setFielddesc(String fielddesc) {
        this.fielddesc = fielddesc;
    }

    public String getOperand1() {
        return operand1;
    }

    public void setOperand1(String operand1) {
        this.operand1 = operand1;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getOperand2() {
        return operand2;
    }

    public void setOperand2(String operand2) {
        this.operand2 = operand2;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getApprovallevel() {
        return approvallevel;
    }

    public void setApprovallevel(String approvallevel) {
        this.approvallevel = approvallevel;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public Date getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }

}
