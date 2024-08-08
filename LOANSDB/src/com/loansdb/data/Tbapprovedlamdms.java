
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbapprovedlamdms
 *  10/13/2020 10:21:35
 * 
 */
public class Tbapprovedlamdms {

    private TbapprovedlamdmsId id;
    private Integer evalreportid;
    private String dmsid;
    private Date dateuploaded;
    private String uploadedby;

    public TbapprovedlamdmsId getId() {
        return id;
    }

    public void setId(TbapprovedlamdmsId id) {
        this.id = id;
    }

    public Integer getEvalreportid() {
        return evalreportid;
    }

    public void setEvalreportid(Integer evalreportid) {
        this.evalreportid = evalreportid;
    }

    public String getDmsid() {
        return dmsid;
    }

    public void setDmsid(String dmsid) {
        this.dmsid = dmsid;
    }

    public Date getDateuploaded() {
        return dateuploaded;
    }

    public void setDateuploaded(Date dateuploaded) {
        this.dateuploaded = dateuploaded;
    }

    public String getUploadedby() {
        return uploadedby;
    }

    public void setUploadedby(String uploadedby) {
        this.uploadedby = uploadedby;
    }

}
