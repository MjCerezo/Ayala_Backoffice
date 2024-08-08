
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbcodetable
 *  08/04/2024 12:54:43
 * 
 */
public class Tbcodetable {

    private TbcodetableId id;
    private String desc1;
    private String desc2;
    private String remarks;
    private String createdby;
    private Date createddate;

    public TbcodetableId getId() {
        return id;
    }

    public void setId(TbcodetableId id) {
        this.id = id;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public String getDesc2() {
        return desc2;
    }

    public void setDesc2(String desc2) {
        this.desc2 = desc2;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

}
