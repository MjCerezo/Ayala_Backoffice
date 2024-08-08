
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbloanproduct
 *  08/04/2024 12:54:42
 * 
 */
public class Tbloanproduct {

    private String productcode;
    private String companycode;
    private String productname;
    private String producttype1;
    private String producttype2;
    private String minloanamountrule;
    private String maxloanamountrule;
    private BigDecimal minloanamount;
    private BigDecimal maxloanamount;
    private String mintermrule;
    private String maxtermrule;
    private BigDecimal minterm;
    private BigDecimal maxterm;
    private String interestraterule;
    private BigDecimal defaultinterestrate;
    private BigDecimal mininterestrate;
    private BigDecimal maxinterestrate;
    private String repaymenttype;
    private Boolean withrepricing;
    private Boolean withpretermination;
    private Integer baseyear;
    private String securityclassification;
    private String securitytype;
    private String repaymentmode;
    private String repaymentcycle;
    private Boolean hasretention;
    private BigDecimal retentionrate;
    private Boolean hasadvanceinterest;
    private Boolean hasreavailment;
    private BigDecimal reavailementpayraterequired;
    private Integer indefaultcondition;
    private Integer noofcomakerrequired;
    private Boolean haslpc;
    private Integer lpcgraceperiod;
    private BigDecimal lpcrate;
    private String lpcperiod;
    private Boolean haspastduecharges;
    private String excesspaymenthandling;
    private String nonworkingdatehandling;
    private String incomerecognition;
    private BigDecimal upfrontchargesrate;
    private BigDecimal depedmaxloanamount1;
    private BigDecimal depedmaxloanamount2;
    private BigDecimal depedinterestrate1;
    private BigDecimal depedinterestrate2;
    private BigDecimal depedinterestrate3;
    private String interestpaid;
    private String seafarertermrule;
    private BigDecimal mincapcon;
    private BigDecimal firstloaneligibilitymoa;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date dateupdated;
    private Boolean isadepedloan;
    private Boolean isaseafarerloan;
    private String description;
    private Boolean hasDownpayment;
    private BigDecimal downpayment;
    private String interesttype;
    private String interestperiod;
    private String termperiod;
    private Integer defaultterm;
    private Boolean iscomakerrequired;
    private Boolean iscollateralrequired;
    private String calculatortype;
    private Boolean hascoborrower;
    private Boolean hassurety;
    private Boolean hasaif;
    private Boolean collateralflag;
    private String repricingfreq;
    private String subproductcode;
    private String subproductname;
    private String currency;
    private String intpaycomp;
    private String intpaytype;
    private Boolean enableindividual;
    private Boolean enablecorporate;
    private Boolean enablesoleproprietor;
    private String creditmodel;
    private String policymodel;
    private String workflowmodel;
    private String riskmodel;
    private String status;
    private Boolean hasadvanceamort;
    private Integer noofadvamort;
    private Integer maxavailcount;
    private Integer maxexistingavailcount;
    private String loanpurpose;
    private String modepayment;
    private String mlaccondition;
    private BigDecimal maxloanableamt;
    private String roundingscheme;
    private String accrtype;
    private String ddlqtype;

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProducttype1() {
        return producttype1;
    }

    public void setProducttype1(String producttype1) {
        this.producttype1 = producttype1;
    }

    public String getProducttype2() {
        return producttype2;
    }

    public void setProducttype2(String producttype2) {
        this.producttype2 = producttype2;
    }

    public String getMinloanamountrule() {
        return minloanamountrule;
    }

    public void setMinloanamountrule(String minloanamountrule) {
        this.minloanamountrule = minloanamountrule;
    }

    public String getMaxloanamountrule() {
        return maxloanamountrule;
    }

    public void setMaxloanamountrule(String maxloanamountrule) {
        this.maxloanamountrule = maxloanamountrule;
    }

    public BigDecimal getMinloanamount() {
        return minloanamount;
    }

    public void setMinloanamount(BigDecimal minloanamount) {
        this.minloanamount = minloanamount;
    }

    public BigDecimal getMaxloanamount() {
        return maxloanamount;
    }

    public void setMaxloanamount(BigDecimal maxloanamount) {
        this.maxloanamount = maxloanamount;
    }

    public String getMintermrule() {
        return mintermrule;
    }

    public void setMintermrule(String mintermrule) {
        this.mintermrule = mintermrule;
    }

    public String getMaxtermrule() {
        return maxtermrule;
    }

    public void setMaxtermrule(String maxtermrule) {
        this.maxtermrule = maxtermrule;
    }

    public BigDecimal getMinterm() {
        return minterm;
    }

    public void setMinterm(BigDecimal minterm) {
        this.minterm = minterm;
    }

    public BigDecimal getMaxterm() {
        return maxterm;
    }

    public void setMaxterm(BigDecimal maxterm) {
        this.maxterm = maxterm;
    }

    public String getInterestraterule() {
        return interestraterule;
    }

    public void setInterestraterule(String interestraterule) {
        this.interestraterule = interestraterule;
    }

    public BigDecimal getDefaultinterestrate() {
        return defaultinterestrate;
    }

    public void setDefaultinterestrate(BigDecimal defaultinterestrate) {
        this.defaultinterestrate = defaultinterestrate;
    }

    public BigDecimal getMininterestrate() {
        return mininterestrate;
    }

    public void setMininterestrate(BigDecimal mininterestrate) {
        this.mininterestrate = mininterestrate;
    }

    public BigDecimal getMaxinterestrate() {
        return maxinterestrate;
    }

    public void setMaxinterestrate(BigDecimal maxinterestrate) {
        this.maxinterestrate = maxinterestrate;
    }

    public String getRepaymenttype() {
        return repaymenttype;
    }

    public void setRepaymenttype(String repaymenttype) {
        this.repaymenttype = repaymenttype;
    }

    public Boolean getWithrepricing() {
        return withrepricing;
    }

    public void setWithrepricing(Boolean withrepricing) {
        this.withrepricing = withrepricing;
    }

    public Boolean getWithpretermination() {
        return withpretermination;
    }

    public void setWithpretermination(Boolean withpretermination) {
        this.withpretermination = withpretermination;
    }

    public Integer getBaseyear() {
        return baseyear;
    }

    public void setBaseyear(Integer baseyear) {
        this.baseyear = baseyear;
    }

    public String getSecurityclassification() {
        return securityclassification;
    }

    public void setSecurityclassification(String securityclassification) {
        this.securityclassification = securityclassification;
    }

    public String getSecuritytype() {
        return securitytype;
    }

    public void setSecuritytype(String securitytype) {
        this.securitytype = securitytype;
    }

    public String getRepaymentmode() {
        return repaymentmode;
    }

    public void setRepaymentmode(String repaymentmode) {
        this.repaymentmode = repaymentmode;
    }

    public String getRepaymentcycle() {
        return repaymentcycle;
    }

    public void setRepaymentcycle(String repaymentcycle) {
        this.repaymentcycle = repaymentcycle;
    }

    public Boolean getHasretention() {
        return hasretention;
    }

    public void setHasretention(Boolean hasretention) {
        this.hasretention = hasretention;
    }

    public BigDecimal getRetentionrate() {
        return retentionrate;
    }

    public void setRetentionrate(BigDecimal retentionrate) {
        this.retentionrate = retentionrate;
    }

    public Boolean getHasadvanceinterest() {
        return hasadvanceinterest;
    }

    public void setHasadvanceinterest(Boolean hasadvanceinterest) {
        this.hasadvanceinterest = hasadvanceinterest;
    }

    public Boolean getHasreavailment() {
        return hasreavailment;
    }

    public void setHasreavailment(Boolean hasreavailment) {
        this.hasreavailment = hasreavailment;
    }

    public BigDecimal getReavailementpayraterequired() {
        return reavailementpayraterequired;
    }

    public void setReavailementpayraterequired(BigDecimal reavailementpayraterequired) {
        this.reavailementpayraterequired = reavailementpayraterequired;
    }

    public Integer getIndefaultcondition() {
        return indefaultcondition;
    }

    public void setIndefaultcondition(Integer indefaultcondition) {
        this.indefaultcondition = indefaultcondition;
    }

    public Integer getNoofcomakerrequired() {
        return noofcomakerrequired;
    }

    public void setNoofcomakerrequired(Integer noofcomakerrequired) {
        this.noofcomakerrequired = noofcomakerrequired;
    }

    public Boolean getHaslpc() {
        return haslpc;
    }

    public void setHaslpc(Boolean haslpc) {
        this.haslpc = haslpc;
    }

    public Integer getLpcgraceperiod() {
        return lpcgraceperiod;
    }

    public void setLpcgraceperiod(Integer lpcgraceperiod) {
        this.lpcgraceperiod = lpcgraceperiod;
    }

    public BigDecimal getLpcrate() {
        return lpcrate;
    }

    public void setLpcrate(BigDecimal lpcrate) {
        this.lpcrate = lpcrate;
    }

    public String getLpcperiod() {
        return lpcperiod;
    }

    public void setLpcperiod(String lpcperiod) {
        this.lpcperiod = lpcperiod;
    }

    public Boolean getHaspastduecharges() {
        return haspastduecharges;
    }

    public void setHaspastduecharges(Boolean haspastduecharges) {
        this.haspastduecharges = haspastduecharges;
    }

    public String getExcesspaymenthandling() {
        return excesspaymenthandling;
    }

    public void setExcesspaymenthandling(String excesspaymenthandling) {
        this.excesspaymenthandling = excesspaymenthandling;
    }

    public String getNonworkingdatehandling() {
        return nonworkingdatehandling;
    }

    public void setNonworkingdatehandling(String nonworkingdatehandling) {
        this.nonworkingdatehandling = nonworkingdatehandling;
    }

    public String getIncomerecognition() {
        return incomerecognition;
    }

    public void setIncomerecognition(String incomerecognition) {
        this.incomerecognition = incomerecognition;
    }

    public BigDecimal getUpfrontchargesrate() {
        return upfrontchargesrate;
    }

    public void setUpfrontchargesrate(BigDecimal upfrontchargesrate) {
        this.upfrontchargesrate = upfrontchargesrate;
    }

    public BigDecimal getDepedmaxloanamount1() {
        return depedmaxloanamount1;
    }

    public void setDepedmaxloanamount1(BigDecimal depedmaxloanamount1) {
        this.depedmaxloanamount1 = depedmaxloanamount1;
    }

    public BigDecimal getDepedmaxloanamount2() {
        return depedmaxloanamount2;
    }

    public void setDepedmaxloanamount2(BigDecimal depedmaxloanamount2) {
        this.depedmaxloanamount2 = depedmaxloanamount2;
    }

    public BigDecimal getDepedinterestrate1() {
        return depedinterestrate1;
    }

    public void setDepedinterestrate1(BigDecimal depedinterestrate1) {
        this.depedinterestrate1 = depedinterestrate1;
    }

    public BigDecimal getDepedinterestrate2() {
        return depedinterestrate2;
    }

    public void setDepedinterestrate2(BigDecimal depedinterestrate2) {
        this.depedinterestrate2 = depedinterestrate2;
    }

    public BigDecimal getDepedinterestrate3() {
        return depedinterestrate3;
    }

    public void setDepedinterestrate3(BigDecimal depedinterestrate3) {
        this.depedinterestrate3 = depedinterestrate3;
    }

    public String getInterestpaid() {
        return interestpaid;
    }

    public void setInterestpaid(String interestpaid) {
        this.interestpaid = interestpaid;
    }

    public String getSeafarertermrule() {
        return seafarertermrule;
    }

    public void setSeafarertermrule(String seafarertermrule) {
        this.seafarertermrule = seafarertermrule;
    }

    public BigDecimal getMincapcon() {
        return mincapcon;
    }

    public void setMincapcon(BigDecimal mincapcon) {
        this.mincapcon = mincapcon;
    }

    public BigDecimal getFirstloaneligibilitymoa() {
        return firstloaneligibilitymoa;
    }

    public void setFirstloaneligibilitymoa(BigDecimal firstloaneligibilitymoa) {
        this.firstloaneligibilitymoa = firstloaneligibilitymoa;
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

    public Boolean getIsadepedloan() {
        return isadepedloan;
    }

    public void setIsadepedloan(Boolean isadepedloan) {
        this.isadepedloan = isadepedloan;
    }

    public Boolean getIsaseafarerloan() {
        return isaseafarerloan;
    }

    public void setIsaseafarerloan(Boolean isaseafarerloan) {
        this.isaseafarerloan = isaseafarerloan;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getHasDownpayment() {
        return hasDownpayment;
    }

    public void setHasDownpayment(Boolean hasDownpayment) {
        this.hasDownpayment = hasDownpayment;
    }

    public BigDecimal getDownpayment() {
        return downpayment;
    }

    public void setDownpayment(BigDecimal downpayment) {
        this.downpayment = downpayment;
    }

    public String getInteresttype() {
        return interesttype;
    }

    public void setInteresttype(String interesttype) {
        this.interesttype = interesttype;
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

    public Integer getDefaultterm() {
        return defaultterm;
    }

    public void setDefaultterm(Integer defaultterm) {
        this.defaultterm = defaultterm;
    }

    public Boolean getIscomakerrequired() {
        return iscomakerrequired;
    }

    public void setIscomakerrequired(Boolean iscomakerrequired) {
        this.iscomakerrequired = iscomakerrequired;
    }

    public Boolean getIscollateralrequired() {
        return iscollateralrequired;
    }

    public void setIscollateralrequired(Boolean iscollateralrequired) {
        this.iscollateralrequired = iscollateralrequired;
    }

    public String getCalculatortype() {
        return calculatortype;
    }

    public void setCalculatortype(String calculatortype) {
        this.calculatortype = calculatortype;
    }

    public Boolean getHascoborrower() {
        return hascoborrower;
    }

    public void setHascoborrower(Boolean hascoborrower) {
        this.hascoborrower = hascoborrower;
    }

    public Boolean getHassurety() {
        return hassurety;
    }

    public void setHassurety(Boolean hassurety) {
        this.hassurety = hassurety;
    }

    public Boolean getHasaif() {
        return hasaif;
    }

    public void setHasaif(Boolean hasaif) {
        this.hasaif = hasaif;
    }

    public Boolean getCollateralflag() {
        return collateralflag;
    }

    public void setCollateralflag(Boolean collateralflag) {
        this.collateralflag = collateralflag;
    }

    public String getRepricingfreq() {
        return repricingfreq;
    }

    public void setRepricingfreq(String repricingfreq) {
        this.repricingfreq = repricingfreq;
    }

    public String getSubproductcode() {
        return subproductcode;
    }

    public void setSubproductcode(String subproductcode) {
        this.subproductcode = subproductcode;
    }

    public String getSubproductname() {
        return subproductname;
    }

    public void setSubproductname(String subproductname) {
        this.subproductname = subproductname;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getIntpaycomp() {
        return intpaycomp;
    }

    public void setIntpaycomp(String intpaycomp) {
        this.intpaycomp = intpaycomp;
    }

    public String getIntpaytype() {
        return intpaytype;
    }

    public void setIntpaytype(String intpaytype) {
        this.intpaytype = intpaytype;
    }

    public Boolean getEnableindividual() {
        return enableindividual;
    }

    public void setEnableindividual(Boolean enableindividual) {
        this.enableindividual = enableindividual;
    }

    public Boolean getEnablecorporate() {
        return enablecorporate;
    }

    public void setEnablecorporate(Boolean enablecorporate) {
        this.enablecorporate = enablecorporate;
    }

    public Boolean getEnablesoleproprietor() {
        return enablesoleproprietor;
    }

    public void setEnablesoleproprietor(Boolean enablesoleproprietor) {
        this.enablesoleproprietor = enablesoleproprietor;
    }

    public String getCreditmodel() {
        return creditmodel;
    }

    public void setCreditmodel(String creditmodel) {
        this.creditmodel = creditmodel;
    }

    public String getPolicymodel() {
        return policymodel;
    }

    public void setPolicymodel(String policymodel) {
        this.policymodel = policymodel;
    }

    public String getWorkflowmodel() {
        return workflowmodel;
    }

    public void setWorkflowmodel(String workflowmodel) {
        this.workflowmodel = workflowmodel;
    }

    public String getRiskmodel() {
        return riskmodel;
    }

    public void setRiskmodel(String riskmodel) {
        this.riskmodel = riskmodel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getHasadvanceamort() {
        return hasadvanceamort;
    }

    public void setHasadvanceamort(Boolean hasadvanceamort) {
        this.hasadvanceamort = hasadvanceamort;
    }

    public Integer getNoofadvamort() {
        return noofadvamort;
    }

    public void setNoofadvamort(Integer noofadvamort) {
        this.noofadvamort = noofadvamort;
    }

    public Integer getMaxavailcount() {
        return maxavailcount;
    }

    public void setMaxavailcount(Integer maxavailcount) {
        this.maxavailcount = maxavailcount;
    }

    public Integer getMaxexistingavailcount() {
        return maxexistingavailcount;
    }

    public void setMaxexistingavailcount(Integer maxexistingavailcount) {
        this.maxexistingavailcount = maxexistingavailcount;
    }

    public String getLoanpurpose() {
        return loanpurpose;
    }

    public void setLoanpurpose(String loanpurpose) {
        this.loanpurpose = loanpurpose;
    }

    public String getModepayment() {
        return modepayment;
    }

    public void setModepayment(String modepayment) {
        this.modepayment = modepayment;
    }

    public String getMlaccondition() {
        return mlaccondition;
    }

    public void setMlaccondition(String mlaccondition) {
        this.mlaccondition = mlaccondition;
    }

    public BigDecimal getMaxloanableamt() {
        return maxloanableamt;
    }

    public void setMaxloanableamt(BigDecimal maxloanableamt) {
        this.maxloanableamt = maxloanableamt;
    }

    public String getRoundingscheme() {
        return roundingscheme;
    }

    public void setRoundingscheme(String roundingscheme) {
        this.roundingscheme = roundingscheme;
    }

    public String getAccrtype() {
        return accrtype;
    }

    public void setAccrtype(String accrtype) {
        this.accrtype = accrtype;
    }

    public String getDdlqtype() {
        return ddlqtype;
    }

    public void setDdlqtype(String ddlqtype) {
        this.ddlqtype = ddlqtype;
    }

}
