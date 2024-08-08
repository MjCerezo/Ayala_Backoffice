
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbworkflowprocess
 *  10/13/2020 10:21:35
 * 
 */
public class Tbworkflowprocess {

    private TbworkflowprocessId id;
    private String processname;
    private String submitcode;
    private Integer submitoption1;
    private Integer submitoption2;
    private Integer submitoption3;
    private String returncode;
    private Integer returnoption;
    private Boolean isbookprocess;
    private Boolean iscancelprocess;
    private Boolean isrejectprocess;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date dateupdated;
    private Boolean isvisibleindb;
    private String remarks;
    private Boolean tabencoding;
    private Boolean tabinvestigationandappraisal;
    private Boolean tabevaluation;
    private Boolean tabrecommendation;
    private Boolean tabcreditapproval;
    private Boolean tabclientacceptance;
    private Boolean tabdocumentinsurance;
    private Boolean tabreleasingapproval;
    private Boolean tabbookandreleasing;
    private Boolean tabbooked;
    private Boolean tabbookeddocpending;
    private Boolean tabcancelled;
    private Boolean tabrejected;
    private Boolean tabapprovedlines;
    private Boolean tabapprovedlinesdocpending;
    private Boolean tabboardapproval;
    private Boolean tablinecreation;
    private Boolean tablineupdate;
    private Boolean tabnotes;
    private Boolean tabhistory;

    public TbworkflowprocessId getId() {
        return id;
    }

    public void setId(TbworkflowprocessId id) {
        this.id = id;
    }

    public String getProcessname() {
        return processname;
    }

    public void setProcessname(String processname) {
        this.processname = processname;
    }

    public String getSubmitcode() {
        return submitcode;
    }

    public void setSubmitcode(String submitcode) {
        this.submitcode = submitcode;
    }

    public Integer getSubmitoption1() {
        return submitoption1;
    }

    public void setSubmitoption1(Integer submitoption1) {
        this.submitoption1 = submitoption1;
    }

    public Integer getSubmitoption2() {
        return submitoption2;
    }

    public void setSubmitoption2(Integer submitoption2) {
        this.submitoption2 = submitoption2;
    }

    public Integer getSubmitoption3() {
        return submitoption3;
    }

    public void setSubmitoption3(Integer submitoption3) {
        this.submitoption3 = submitoption3;
    }

    public String getReturncode() {
        return returncode;
    }

    public void setReturncode(String returncode) {
        this.returncode = returncode;
    }

    public Integer getReturnoption() {
        return returnoption;
    }

    public void setReturnoption(Integer returnoption) {
        this.returnoption = returnoption;
    }

    public Boolean getIsbookprocess() {
        return isbookprocess;
    }

    public void setIsbookprocess(Boolean isbookprocess) {
        this.isbookprocess = isbookprocess;
    }

    public Boolean getIscancelprocess() {
        return iscancelprocess;
    }

    public void setIscancelprocess(Boolean iscancelprocess) {
        this.iscancelprocess = iscancelprocess;
    }

    public Boolean getIsrejectprocess() {
        return isrejectprocess;
    }

    public void setIsrejectprocess(Boolean isrejectprocess) {
        this.isrejectprocess = isrejectprocess;
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

    public Boolean getIsvisibleindb() {
        return isvisibleindb;
    }

    public void setIsvisibleindb(Boolean isvisibleindb) {
        this.isvisibleindb = isvisibleindb;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getTabencoding() {
        return tabencoding;
    }

    public void setTabencoding(Boolean tabencoding) {
        this.tabencoding = tabencoding;
    }

    public Boolean getTabinvestigationandappraisal() {
        return tabinvestigationandappraisal;
    }

    public void setTabinvestigationandappraisal(Boolean tabinvestigationandappraisal) {
        this.tabinvestigationandappraisal = tabinvestigationandappraisal;
    }

    public Boolean getTabevaluation() {
        return tabevaluation;
    }

    public void setTabevaluation(Boolean tabevaluation) {
        this.tabevaluation = tabevaluation;
    }

    public Boolean getTabrecommendation() {
        return tabrecommendation;
    }

    public void setTabrecommendation(Boolean tabrecommendation) {
        this.tabrecommendation = tabrecommendation;
    }

    public Boolean getTabcreditapproval() {
        return tabcreditapproval;
    }

    public void setTabcreditapproval(Boolean tabcreditapproval) {
        this.tabcreditapproval = tabcreditapproval;
    }

    public Boolean getTabclientacceptance() {
        return tabclientacceptance;
    }

    public void setTabclientacceptance(Boolean tabclientacceptance) {
        this.tabclientacceptance = tabclientacceptance;
    }

    public Boolean getTabdocumentinsurance() {
        return tabdocumentinsurance;
    }

    public void setTabdocumentinsurance(Boolean tabdocumentinsurance) {
        this.tabdocumentinsurance = tabdocumentinsurance;
    }

    public Boolean getTabreleasingapproval() {
        return tabreleasingapproval;
    }

    public void setTabreleasingapproval(Boolean tabreleasingapproval) {
        this.tabreleasingapproval = tabreleasingapproval;
    }

    public Boolean getTabbookandreleasing() {
        return tabbookandreleasing;
    }

    public void setTabbookandreleasing(Boolean tabbookandreleasing) {
        this.tabbookandreleasing = tabbookandreleasing;
    }

    public Boolean getTabbooked() {
        return tabbooked;
    }

    public void setTabbooked(Boolean tabbooked) {
        this.tabbooked = tabbooked;
    }

    public Boolean getTabbookeddocpending() {
        return tabbookeddocpending;
    }

    public void setTabbookeddocpending(Boolean tabbookeddocpending) {
        this.tabbookeddocpending = tabbookeddocpending;
    }

    public Boolean getTabcancelled() {
        return tabcancelled;
    }

    public void setTabcancelled(Boolean tabcancelled) {
        this.tabcancelled = tabcancelled;
    }

    public Boolean getTabrejected() {
        return tabrejected;
    }

    public void setTabrejected(Boolean tabrejected) {
        this.tabrejected = tabrejected;
    }

    public Boolean getTabapprovedlines() {
        return tabapprovedlines;
    }

    public void setTabapprovedlines(Boolean tabapprovedlines) {
        this.tabapprovedlines = tabapprovedlines;
    }

    public Boolean getTabapprovedlinesdocpending() {
        return tabapprovedlinesdocpending;
    }

    public void setTabapprovedlinesdocpending(Boolean tabapprovedlinesdocpending) {
        this.tabapprovedlinesdocpending = tabapprovedlinesdocpending;
    }

    public Boolean getTabboardapproval() {
        return tabboardapproval;
    }

    public void setTabboardapproval(Boolean tabboardapproval) {
        this.tabboardapproval = tabboardapproval;
    }

    public Boolean getTablinecreation() {
        return tablinecreation;
    }

    public void setTablinecreation(Boolean tablinecreation) {
        this.tablinecreation = tablinecreation;
    }

    public Boolean getTablineupdate() {
        return tablineupdate;
    }

    public void setTablineupdate(Boolean tablineupdate) {
        this.tablineupdate = tablineupdate;
    }

    public Boolean getTabnotes() {
        return tabnotes;
    }

    public void setTabnotes(Boolean tabnotes) {
        this.tabnotes = tabnotes;
    }

    public Boolean getTabhistory() {
        return tabhistory;
    }

    public void setTabhistory(Boolean tabhistory) {
        this.tabhistory = tabhistory;
    }

}
