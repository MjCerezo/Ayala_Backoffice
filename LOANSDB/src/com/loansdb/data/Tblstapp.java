
package com.loansdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  LOANSDB.Tblstapp
 *  10/13/2020 10:21:35
 * 
 */
public class Tblstapp {

    private String appno;
    private String cifno;
    private String cifname;
    private String customertype;
    private Integer applicationtype;
    private Date applicationdate;
    private Integer applicationstatus;
    private Date statusdatetime;
    private Integer lastapplicationstatus;
    private String accountofficer;
    private String losoriginatingteam;
    private String ciforiginatingteam;
    private String loanproduct;
    private Date datecreated;
    private String createdby;
    private Date datelastupdated;
    private String lastupdatedby;
    private String companycode;
    private String regioncode;
    private String branchcode;
    private String pnno;
    private String accountno;
    private String existingpnno;
    private String existingaccountno;
    private String existinglinerefno;
    private String mainlinerefno;
    private String ciftin;
    private String typeofline;
    private Boolean isforreconsideration;
    private Boolean isforamendment;
    private Boolean isbicompleted;
    private Boolean iscicompleted;
    private Boolean isappraisalcompleted;
    private Integer createevalreportflag;
    private String recommendedby;
    private String recommendationremarks;
    private String reasonforcancellation;
    private String cancelledby;
    private String rejectedby;
    private String reasonforreject;
    private Boolean isdoneencoding;
    private Boolean isrejected;
    private Boolean iscancelled;
    private Boolean isbooked;
    private Boolean isbookdocpending;
    private BigDecimal initialloanamount;
    private BigDecimal approvedloanamount;
    private BigDecimal proposedlineamount;
    private BigDecimal approvedlineamount;
    private BigDecimal approvedinterestrate;
    private Integer approvedterm;
    private Date dtbook;
    private Date fduedt;
    private String loanpurpose;
    private Integer loanterm;
    private String paymentcycle;
    private String repaymenttype;
    private String interestperiod;
    private String termperiod;
    private Integer ppynum;
    private BigDecimal interestdue;
    private BigDecimal eir;
    private BigDecimal amortamt;
    private BigDecimal eyrate;
    private BigDecimal mir;
    private String typefacility;
    private String cfrefno;
    private String assigneddochead;
    private String assigneddocanalyst;
    private Date docassigndate;
    private Date documentationstatusdate;
    private Integer documentationstatus;
    private String cfrefnoconcat;
    private String reasonreturn;
    private String referraltype;
    private String referrorname;
    private String availedlineapp;
    private String cfrefnoconcatmain;
    private Date cfexpdt;
    private Integer createcireceivableflag;
    private String dmsid;
    private String rollovertype;

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

    public String getCifname() {
        return cifname;
    }

    public void setCifname(String cifname) {
        this.cifname = cifname;
    }

    public String getCustomertype() {
        return customertype;
    }

    public void setCustomertype(String customertype) {
        this.customertype = customertype;
    }

    public Integer getApplicationtype() {
        return applicationtype;
    }

    public void setApplicationtype(Integer applicationtype) {
        this.applicationtype = applicationtype;
    }

    public Date getApplicationdate() {
        return applicationdate;
    }

    public void setApplicationdate(Date applicationdate) {
        this.applicationdate = applicationdate;
    }

    public Integer getApplicationstatus() {
        return applicationstatus;
    }

    public void setApplicationstatus(Integer applicationstatus) {
        this.applicationstatus = applicationstatus;
    }

    public Date getStatusdatetime() {
        return statusdatetime;
    }

    public void setStatusdatetime(Date statusdatetime) {
        this.statusdatetime = statusdatetime;
    }

    public Integer getLastapplicationstatus() {
        return lastapplicationstatus;
    }

    public void setLastapplicationstatus(Integer lastapplicationstatus) {
        this.lastapplicationstatus = lastapplicationstatus;
    }

    public String getAccountofficer() {
        return accountofficer;
    }

    public void setAccountofficer(String accountofficer) {
        this.accountofficer = accountofficer;
    }

    public String getLosoriginatingteam() {
        return losoriginatingteam;
    }

    public void setLosoriginatingteam(String losoriginatingteam) {
        this.losoriginatingteam = losoriginatingteam;
    }

    public String getCiforiginatingteam() {
        return ciforiginatingteam;
    }

    public void setCiforiginatingteam(String ciforiginatingteam) {
        this.ciforiginatingteam = ciforiginatingteam;
    }

    public String getLoanproduct() {
        return loanproduct;
    }

    public void setLoanproduct(String loanproduct) {
        this.loanproduct = loanproduct;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Date getDatelastupdated() {
        return datelastupdated;
    }

    public void setDatelastupdated(Date datelastupdated) {
        this.datelastupdated = datelastupdated;
    }

    public String getLastupdatedby() {
        return lastupdatedby;
    }

    public void setLastupdatedby(String lastupdatedby) {
        this.lastupdatedby = lastupdatedby;
    }

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode;
    }

    public String getRegioncode() {
        return regioncode;
    }

    public void setRegioncode(String regioncode) {
        this.regioncode = regioncode;
    }

    public String getBranchcode() {
        return branchcode;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode;
    }

    public String getPnno() {
        return pnno;
    }

    public void setPnno(String pnno) {
        this.pnno = pnno;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getExistingpnno() {
        return existingpnno;
    }

    public void setExistingpnno(String existingpnno) {
        this.existingpnno = existingpnno;
    }

    public String getExistingaccountno() {
        return existingaccountno;
    }

    public void setExistingaccountno(String existingaccountno) {
        this.existingaccountno = existingaccountno;
    }

    public String getExistinglinerefno() {
        return existinglinerefno;
    }

    public void setExistinglinerefno(String existinglinerefno) {
        this.existinglinerefno = existinglinerefno;
    }

    public String getMainlinerefno() {
        return mainlinerefno;
    }

    public void setMainlinerefno(String mainlinerefno) {
        this.mainlinerefno = mainlinerefno;
    }

    public String getCiftin() {
        return ciftin;
    }

    public void setCiftin(String ciftin) {
        this.ciftin = ciftin;
    }

    public String getTypeofline() {
        return typeofline;
    }

    public void setTypeofline(String typeofline) {
        this.typeofline = typeofline;
    }

    public Boolean getIsforreconsideration() {
        return isforreconsideration;
    }

    public void setIsforreconsideration(Boolean isforreconsideration) {
        this.isforreconsideration = isforreconsideration;
    }

    public Boolean getIsforamendment() {
        return isforamendment;
    }

    public void setIsforamendment(Boolean isforamendment) {
        this.isforamendment = isforamendment;
    }

    public Boolean getIsbicompleted() {
        return isbicompleted;
    }

    public void setIsbicompleted(Boolean isbicompleted) {
        this.isbicompleted = isbicompleted;
    }

    public Boolean getIscicompleted() {
        return iscicompleted;
    }

    public void setIscicompleted(Boolean iscicompleted) {
        this.iscicompleted = iscicompleted;
    }

    public Boolean getIsappraisalcompleted() {
        return isappraisalcompleted;
    }

    public void setIsappraisalcompleted(Boolean isappraisalcompleted) {
        this.isappraisalcompleted = isappraisalcompleted;
    }

    public Integer getCreateevalreportflag() {
        return createevalreportflag;
    }

    public void setCreateevalreportflag(Integer createevalreportflag) {
        this.createevalreportflag = createevalreportflag;
    }

    public String getRecommendedby() {
        return recommendedby;
    }

    public void setRecommendedby(String recommendedby) {
        this.recommendedby = recommendedby;
    }

    public String getRecommendationremarks() {
        return recommendationremarks;
    }

    public void setRecommendationremarks(String recommendationremarks) {
        this.recommendationremarks = recommendationremarks;
    }

    public String getReasonforcancellation() {
        return reasonforcancellation;
    }

    public void setReasonforcancellation(String reasonforcancellation) {
        this.reasonforcancellation = reasonforcancellation;
    }

    public String getCancelledby() {
        return cancelledby;
    }

    public void setCancelledby(String cancelledby) {
        this.cancelledby = cancelledby;
    }

    public String getRejectedby() {
        return rejectedby;
    }

    public void setRejectedby(String rejectedby) {
        this.rejectedby = rejectedby;
    }

    public String getReasonforreject() {
        return reasonforreject;
    }

    public void setReasonforreject(String reasonforreject) {
        this.reasonforreject = reasonforreject;
    }

    public Boolean getIsdoneencoding() {
        return isdoneencoding;
    }

    public void setIsdoneencoding(Boolean isdoneencoding) {
        this.isdoneencoding = isdoneencoding;
    }

    public Boolean getIsrejected() {
        return isrejected;
    }

    public void setIsrejected(Boolean isrejected) {
        this.isrejected = isrejected;
    }

    public Boolean getIscancelled() {
        return iscancelled;
    }

    public void setIscancelled(Boolean iscancelled) {
        this.iscancelled = iscancelled;
    }

    public Boolean getIsbooked() {
        return isbooked;
    }

    public void setIsbooked(Boolean isbooked) {
        this.isbooked = isbooked;
    }

    public Boolean getIsbookdocpending() {
        return isbookdocpending;
    }

    public void setIsbookdocpending(Boolean isbookdocpending) {
        this.isbookdocpending = isbookdocpending;
    }

    public BigDecimal getInitialloanamount() {
        return initialloanamount;
    }

    public void setInitialloanamount(BigDecimal initialloanamount) {
        this.initialloanamount = initialloanamount;
    }

    public BigDecimal getApprovedloanamount() {
        return approvedloanamount;
    }

    public void setApprovedloanamount(BigDecimal approvedloanamount) {
        this.approvedloanamount = approvedloanamount;
    }

    public BigDecimal getProposedlineamount() {
        return proposedlineamount;
    }

    public void setProposedlineamount(BigDecimal proposedlineamount) {
        this.proposedlineamount = proposedlineamount;
    }

    public BigDecimal getApprovedlineamount() {
        return approvedlineamount;
    }

    public void setApprovedlineamount(BigDecimal approvedlineamount) {
        this.approvedlineamount = approvedlineamount;
    }

    public BigDecimal getApprovedinterestrate() {
        return approvedinterestrate;
    }

    public void setApprovedinterestrate(BigDecimal approvedinterestrate) {
        this.approvedinterestrate = approvedinterestrate;
    }

    public Integer getApprovedterm() {
        return approvedterm;
    }

    public void setApprovedterm(Integer approvedterm) {
        this.approvedterm = approvedterm;
    }

    public Date getDtbook() {
        return dtbook;
    }

    public void setDtbook(Date dtbook) {
        this.dtbook = dtbook;
    }

    public Date getFduedt() {
        return fduedt;
    }

    public void setFduedt(Date fduedt) {
        this.fduedt = fduedt;
    }

    public String getLoanpurpose() {
        return loanpurpose;
    }

    public void setLoanpurpose(String loanpurpose) {
        this.loanpurpose = loanpurpose;
    }

    public Integer getLoanterm() {
        return loanterm;
    }

    public void setLoanterm(Integer loanterm) {
        this.loanterm = loanterm;
    }

    public String getPaymentcycle() {
        return paymentcycle;
    }

    public void setPaymentcycle(String paymentcycle) {
        this.paymentcycle = paymentcycle;
    }

    public String getRepaymenttype() {
        return repaymenttype;
    }

    public void setRepaymenttype(String repaymenttype) {
        this.repaymenttype = repaymenttype;
    }

    public String getInterestperiod() {
        return interestperiod;
    }

    public void setInterestperiod(String interestperiod) {
        this.interestperiod = interestperiod;
    }

    public String getTermperiod() {
        return termperiod;
    }

    public void setTermperiod(String termperiod) {
        this.termperiod = termperiod;
    }

    public Integer getPpynum() {
        return ppynum;
    }

    public void setPpynum(Integer ppynum) {
        this.ppynum = ppynum;
    }

    public BigDecimal getInterestdue() {
        return interestdue;
    }

    public void setInterestdue(BigDecimal interestdue) {
        this.interestdue = interestdue;
    }

    public BigDecimal getEir() {
        return eir;
    }

    public void setEir(BigDecimal eir) {
        this.eir = eir;
    }

    public BigDecimal getAmortamt() {
        return amortamt;
    }

    public void setAmortamt(BigDecimal amortamt) {
        this.amortamt = amortamt;
    }

    public BigDecimal getEyrate() {
        return eyrate;
    }

    public void setEyrate(BigDecimal eyrate) {
        this.eyrate = eyrate;
    }

    public BigDecimal getMir() {
        return mir;
    }

    public void setMir(BigDecimal mir) {
        this.mir = mir;
    }

    public String getTypefacility() {
        return typefacility;
    }

    public void setTypefacility(String typefacility) {
        this.typefacility = typefacility;
    }

    public String getCfrefno() {
        return cfrefno;
    }

    public void setCfrefno(String cfrefno) {
        this.cfrefno = cfrefno;
    }

    public String getAssigneddochead() {
        return assigneddochead;
    }

    public void setAssigneddochead(String assigneddochead) {
        this.assigneddochead = assigneddochead;
    }

    public String getAssigneddocanalyst() {
        return assigneddocanalyst;
    }

    public void setAssigneddocanalyst(String assigneddocanalyst) {
        this.assigneddocanalyst = assigneddocanalyst;
    }

    public Date getDocassigndate() {
        return docassigndate;
    }

    public void setDocassigndate(Date docassigndate) {
        this.docassigndate = docassigndate;
    }

    public Date getDocumentationstatusdate() {
        return documentationstatusdate;
    }

    public void setDocumentationstatusdate(Date documentationstatusdate) {
        this.documentationstatusdate = documentationstatusdate;
    }

    public Integer getDocumentationstatus() {
        return documentationstatus;
    }

    public void setDocumentationstatus(Integer documentationstatus) {
        this.documentationstatus = documentationstatus;
    }

    public String getCfrefnoconcat() {
        return cfrefnoconcat;
    }

    public void setCfrefnoconcat(String cfrefnoconcat) {
        this.cfrefnoconcat = cfrefnoconcat;
    }

    public String getReasonreturn() {
        return reasonreturn;
    }

    public void setReasonreturn(String reasonreturn) {
        this.reasonreturn = reasonreturn;
    }

    public String getReferraltype() {
        return referraltype;
    }

    public void setReferraltype(String referraltype) {
        this.referraltype = referraltype;
    }

    public String getReferrorname() {
        return referrorname;
    }

    public void setReferrorname(String referrorname) {
        this.referrorname = referrorname;
    }

    public String getAvailedlineapp() {
        return availedlineapp;
    }

    public void setAvailedlineapp(String availedlineapp) {
        this.availedlineapp = availedlineapp;
    }

    public String getCfrefnoconcatmain() {
        return cfrefnoconcatmain;
    }

    public void setCfrefnoconcatmain(String cfrefnoconcatmain) {
        this.cfrefnoconcatmain = cfrefnoconcatmain;
    }

    public Date getCfexpdt() {
        return cfexpdt;
    }

    public void setCfexpdt(Date cfexpdt) {
        this.cfexpdt = cfexpdt;
    }

    public Integer getCreatecireceivableflag() {
        return createcireceivableflag;
    }

    public void setCreatecireceivableflag(Integer createcireceivableflag) {
        this.createcireceivableflag = createcireceivableflag;
    }

    public String getDmsid() {
        return dmsid;
    }

    public void setDmsid(String dmsid) {
        this.dmsid = dmsid;
    }

    public String getRollovertype() {
        return rollovertype;
    }

    public void setRollovertype(String rollovertype) {
        this.rollovertype = rollovertype;
    }

}
