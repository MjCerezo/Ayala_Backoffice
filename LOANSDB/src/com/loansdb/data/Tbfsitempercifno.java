
package com.loansdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  LOANSDB.Tbfsitempercifno
 *  10/13/2020 10:21:35
 * 
 */
public class Tbfsitempercifno {

    private Integer id;
    private String cifno;
    private String fstype;
    private Date fsdate;
    private String fssection;
    private String fssubsection;
    private String fsitemtype;
    private String fsitemname;
    private BigDecimal fsitemamt;
    private Integer sequence;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date lastupdated;
    private Boolean itemnameflag;
    private Boolean industryflag;
    private String industryname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getFstype() {
        return fstype;
    }

    public void setFstype(String fstype) {
        this.fstype = fstype;
    }

    public Date getFsdate() {
        return fsdate;
    }

    public void setFsdate(Date fsdate) {
        this.fsdate = fsdate;
    }

    public String getFssection() {
        return fssection;
    }

    public void setFssection(String fssection) {
        this.fssection = fssection;
    }

    public String getFssubsection() {
        return fssubsection;
    }

    public void setFssubsection(String fssubsection) {
        this.fssubsection = fssubsection;
    }

    public String getFsitemtype() {
        return fsitemtype;
    }

    public void setFsitemtype(String fsitemtype) {
        this.fsitemtype = fsitemtype;
    }

    public String getFsitemname() {
        return fsitemname;
    }

    public void setFsitemname(String fsitemname) {
        this.fsitemname = fsitemname;
    }

    public BigDecimal getFsitemamt() {
        return fsitemamt;
    }

    public void setFsitemamt(BigDecimal fsitemamt) {
        this.fsitemamt = fsitemamt;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
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

    public Date getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(Date lastupdated) {
        this.lastupdated = lastupdated;
    }

    public Boolean getItemnameflag() {
        return itemnameflag;
    }

    public void setItemnameflag(Boolean itemnameflag) {
        this.itemnameflag = itemnameflag;
    }

    public Boolean getIndustryflag() {
        return industryflag;
    }

    public void setIndustryflag(Boolean industryflag) {
        this.industryflag = industryflag;
    }

    public String getIndustryname() {
        return industryname;
    }

    public void setIndustryname(String industryname) {
        this.industryname = industryname;
    }

}
