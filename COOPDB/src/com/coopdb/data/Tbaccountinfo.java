
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbaccountinfo
 *  08/04/2024 12:54:41
 * 
 */
public class Tbaccountinfo {

    private String applno;
    private String clientid;
    private Date txdate;
    private BigDecimal accref;
    private String accrtype;
    private String acctoff;
    private Integer acctsts;
    private BigDecimal addon;
    private Integer adjustedTerm;
    private BigDecimal adjustmentAmortization;
    private BigDecimal air;
    private String amortInWords;
    private BigDecimal amortfee;
    private String amtInWords;
    private String apprbr;
    private BigDecimal bkfxrate;
    private String collAgency;
    private String collCollector;
    private String collZone;
    private Integer colnum;
    private BigDecimal crline1;
    private Date crline1dt;
    private BigDecimal crline2;
    private Date crline2dt;
    private String currency;
    private String dealer;
    private BigDecimal desiredMonthlyAmort;
    private String devcode;
    private String docStampDeductType;
    private Integer docnum;
    private BigDecimal docstamp;
    private Date dtbook;
    private BigDecimal eir;
    private BigDecimal faceamt;
    private Date fduedt;
    private BigDecimal fireInsurance;
    private String fireInsuranceDeductType;
    private String fundtype;
    private Integer insType2addtnlDependents;
    private BigDecimal insuranceOutright;
    private String insuranceOutrightDeductType;
    private String insuranceType;
    private String interestPeriod;
    private String intpycyc;
    private Date intpyfddt;
    private String intpytype;
    private String inttyp;
    private Boolean isAmortFeeRounded;
    private Boolean isWithInsurance;
    private BigDecimal lastInstallment;
    private String legveh;
    private BigDecimal lifeInsurance;
    private String lifeInsuranceDeductType;
    private String loanno;
    private String loanoff;
    private String loanpur;
    private String loantype;
    private BigDecimal lpcrate;
    private Date matdt;
    private BigDecimal mir;
    private BigDecimal monthlyPension;
    private String name;
    private String netaccount;
    private String netdispo;
    private BigDecimal netprcds;
    private BigDecimal netprcdsorig;
    private Integer gracePeriod;
    private Integer noOfInterestPaymentToAdvance;
    private BigDecimal nominal;
    private BigDecimal notarialFee;
    private String notarialFeeDeductType;
    private BigDecimal notfee;
    private Date nxtrprce;
    private String origbr;
    private String origoff;
    private BigDecimal orppd;
    private BigDecimal othcharges;
    private BigDecimal otherAddtnlToAmortFee;
    private BigDecimal otherNonFinancialCharges;
    private String otherNonFinancialChargesDeductType;
    private BigDecimal ouid;
    private BigDecimal outrightPf;
    private String outrightPfdeductType;
    private String payacct;
    private String payinst;
    private Integer paymentDueDay;
    private String pymntPlan;
    private Integer pdcctr;
    private BigDecimal pnamt;
    private String pnno;
    private String ppycyc;
    private Date ppyfddt;
    private Integer ppynum;
    private String ppytype;
    private BigDecimal prime;
    private BigDecimal processingFee;
    private String processingFeeDeductType;
    private String product;
    private String productGroup;
    private String rateInWords;
    private BigDecimal registerOfDeeds;
    private String registerOfDeedsDeductType;
    private String repricingPeriod;
    private Integer repricingTerm;
    private String rprcecyc;
    private String rprceflg;
    private String seccode;
    private BigDecimal sellprice;
    private BigDecimal ser;
    private BigDecimal serviceChargeRate;
    private BigDecimal spread;
    private Date stsdate;
    private String subprd;
    private Integer term;
    private String termcyc;
    private BigDecimal totalNfccollect;
    private BigDecimal totalNfcdeductToLoanProc;
    private Integer txbatch;
    private String txbranch;
    private String txcode;
    private String txmkr;
    private String txoff;
    private String txoper;
    private Integer txseq;
    private String txsrce;
    private String txstat;
    private Date txvaldt;
    private BigDecimal uidAdv;
    private BigDecimal uidBal;
    private Date uidEndDate;
    private Integer intchargedays;
    private BigDecimal intchargeamt;
    private BigDecimal eyrate;
    private Integer notfeedocs;
    private String cfrefno1;
    private String cfrefno2;
    private Integer applicationtype;
    private String cfrefnoconcat;
    private String prinpaycycle;
    private String intpaycycle;
    private BigDecimal maturityvalue;
    private BigDecimal interestdue;
    private BigDecimal totalfeescharges;
    private BigDecimal totalloanoffsetamt;
    private BigDecimal downpay;
    private BigDecimal downpaypcnt;
    private BigDecimal retentionamt;
    private BigDecimal retentionpcnt;
    private BigDecimal amtfinance;
    private String repaytypedesc;
    private String inttypedesc;
    private String intpaytypedesc;
    private String termcycdesc;
    private String intcycdesc;
    private String prinpaycycdesc;
    private String intpaycycdesc;
    private String intcompmethod;
    private String incompmethoddesc;
    private BigDecimal totalfeesandcharges;
    private Integer noofintpay;
    private Integer noofprinpay;
    private Integer noofadvint;
    private Integer noofadvprin;
    private String duedaterule;
    private BigDecimal advintamt;
    private BigDecimal advprinamt;
    private BigDecimal totalfeesamortized;
    private BigDecimal totalpayabletobank;
    private BigDecimal totalfeesdeducttoloan;
    private BigDecimal totalunpaidfees;
    private BigDecimal totalpaidfees;
    private String debitbankacctno;
    private String debitbankaccttype;
    private String debitbankbr;
    private String debitbankname;
    private String debitbankbrstn;
    private Boolean collectpdcflag;
    private Boolean collectcheckflag;
    private Boolean collectcashflag;
    private Boolean collectdebitacctflag;
    private String incomerecognition;
    private Integer baseyear;
    private String excesspaymenthandling;
    private String ddlqtype;

    public String getApplno() {
        return applno;
    }

    public void setApplno(String applno) {
        this.applno = applno;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public Date getTxdate() {
        return txdate;
    }

    public void setTxdate(Date txdate) {
        this.txdate = txdate;
    }

    public BigDecimal getAccref() {
        return accref;
    }

    public void setAccref(BigDecimal accref) {
        this.accref = accref;
    }

    public String getAccrtype() {
        return accrtype;
    }

    public void setAccrtype(String accrtype) {
        this.accrtype = accrtype;
    }

    public String getAcctoff() {
        return acctoff;
    }

    public void setAcctoff(String acctoff) {
        this.acctoff = acctoff;
    }

    public Integer getAcctsts() {
        return acctsts;
    }

    public void setAcctsts(Integer acctsts) {
        this.acctsts = acctsts;
    }

    public BigDecimal getAddon() {
        return addon;
    }

    public void setAddon(BigDecimal addon) {
        this.addon = addon;
    }

    public Integer getAdjustedTerm() {
        return adjustedTerm;
    }

    public void setAdjustedTerm(Integer adjustedTerm) {
        this.adjustedTerm = adjustedTerm;
    }

    public BigDecimal getAdjustmentAmortization() {
        return adjustmentAmortization;
    }

    public void setAdjustmentAmortization(BigDecimal adjustmentAmortization) {
        this.adjustmentAmortization = adjustmentAmortization;
    }

    public BigDecimal getAir() {
        return air;
    }

    public void setAir(BigDecimal air) {
        this.air = air;
    }

    public String getAmortInWords() {
        return amortInWords;
    }

    public void setAmortInWords(String amortInWords) {
        this.amortInWords = amortInWords;
    }

    public BigDecimal getAmortfee() {
        return amortfee;
    }

    public void setAmortfee(BigDecimal amortfee) {
        this.amortfee = amortfee;
    }

    public String getAmtInWords() {
        return amtInWords;
    }

    public void setAmtInWords(String amtInWords) {
        this.amtInWords = amtInWords;
    }

    public String getApprbr() {
        return apprbr;
    }

    public void setApprbr(String apprbr) {
        this.apprbr = apprbr;
    }

    public BigDecimal getBkfxrate() {
        return bkfxrate;
    }

    public void setBkfxrate(BigDecimal bkfxrate) {
        this.bkfxrate = bkfxrate;
    }

    public String getCollAgency() {
        return collAgency;
    }

    public void setCollAgency(String collAgency) {
        this.collAgency = collAgency;
    }

    public String getCollCollector() {
        return collCollector;
    }

    public void setCollCollector(String collCollector) {
        this.collCollector = collCollector;
    }

    public String getCollZone() {
        return collZone;
    }

    public void setCollZone(String collZone) {
        this.collZone = collZone;
    }

    public Integer getColnum() {
        return colnum;
    }

    public void setColnum(Integer colnum) {
        this.colnum = colnum;
    }

    public BigDecimal getCrline1() {
        return crline1;
    }

    public void setCrline1(BigDecimal crline1) {
        this.crline1 = crline1;
    }

    public Date getCrline1dt() {
        return crline1dt;
    }

    public void setCrline1dt(Date crline1dt) {
        this.crline1dt = crline1dt;
    }

    public BigDecimal getCrline2() {
        return crline2;
    }

    public void setCrline2(BigDecimal crline2) {
        this.crline2 = crline2;
    }

    public Date getCrline2dt() {
        return crline2dt;
    }

    public void setCrline2dt(Date crline2dt) {
        this.crline2dt = crline2dt;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public BigDecimal getDesiredMonthlyAmort() {
        return desiredMonthlyAmort;
    }

    public void setDesiredMonthlyAmort(BigDecimal desiredMonthlyAmort) {
        this.desiredMonthlyAmort = desiredMonthlyAmort;
    }

    public String getDevcode() {
        return devcode;
    }

    public void setDevcode(String devcode) {
        this.devcode = devcode;
    }

    public String getDocStampDeductType() {
        return docStampDeductType;
    }

    public void setDocStampDeductType(String docStampDeductType) {
        this.docStampDeductType = docStampDeductType;
    }

    public Integer getDocnum() {
        return docnum;
    }

    public void setDocnum(Integer docnum) {
        this.docnum = docnum;
    }

    public BigDecimal getDocstamp() {
        return docstamp;
    }

    public void setDocstamp(BigDecimal docstamp) {
        this.docstamp = docstamp;
    }

    public Date getDtbook() {
        return dtbook;
    }

    public void setDtbook(Date dtbook) {
        this.dtbook = dtbook;
    }

    public BigDecimal getEir() {
        return eir;
    }

    public void setEir(BigDecimal eir) {
        this.eir = eir;
    }

    public BigDecimal getFaceamt() {
        return faceamt;
    }

    public void setFaceamt(BigDecimal faceamt) {
        this.faceamt = faceamt;
    }

    public Date getFduedt() {
        return fduedt;
    }

    public void setFduedt(Date fduedt) {
        this.fduedt = fduedt;
    }

    public BigDecimal getFireInsurance() {
        return fireInsurance;
    }

    public void setFireInsurance(BigDecimal fireInsurance) {
        this.fireInsurance = fireInsurance;
    }

    public String getFireInsuranceDeductType() {
        return fireInsuranceDeductType;
    }

    public void setFireInsuranceDeductType(String fireInsuranceDeductType) {
        this.fireInsuranceDeductType = fireInsuranceDeductType;
    }

    public String getFundtype() {
        return fundtype;
    }

    public void setFundtype(String fundtype) {
        this.fundtype = fundtype;
    }

    public Integer getInsType2addtnlDependents() {
        return insType2addtnlDependents;
    }

    public void setInsType2addtnlDependents(Integer insType2addtnlDependents) {
        this.insType2addtnlDependents = insType2addtnlDependents;
    }

    public BigDecimal getInsuranceOutright() {
        return insuranceOutright;
    }

    public void setInsuranceOutright(BigDecimal insuranceOutright) {
        this.insuranceOutright = insuranceOutright;
    }

    public String getInsuranceOutrightDeductType() {
        return insuranceOutrightDeductType;
    }

    public void setInsuranceOutrightDeductType(String insuranceOutrightDeductType) {
        this.insuranceOutrightDeductType = insuranceOutrightDeductType;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    public String getInterestPeriod() {
        return interestPeriod;
    }

    public void setInterestPeriod(String interestPeriod) {
        this.interestPeriod = interestPeriod;
    }

    public String getIntpycyc() {
        return intpycyc;
    }

    public void setIntpycyc(String intpycyc) {
        this.intpycyc = intpycyc;
    }

    public Date getIntpyfddt() {
        return intpyfddt;
    }

    public void setIntpyfddt(Date intpyfddt) {
        this.intpyfddt = intpyfddt;
    }

    public String getIntpytype() {
        return intpytype;
    }

    public void setIntpytype(String intpytype) {
        this.intpytype = intpytype;
    }

    public String getInttyp() {
        return inttyp;
    }

    public void setInttyp(String inttyp) {
        this.inttyp = inttyp;
    }

    public Boolean getIsAmortFeeRounded() {
        return isAmortFeeRounded;
    }

    public void setIsAmortFeeRounded(Boolean isAmortFeeRounded) {
        this.isAmortFeeRounded = isAmortFeeRounded;
    }

    public Boolean getIsWithInsurance() {
        return isWithInsurance;
    }

    public void setIsWithInsurance(Boolean isWithInsurance) {
        this.isWithInsurance = isWithInsurance;
    }

    public BigDecimal getLastInstallment() {
        return lastInstallment;
    }

    public void setLastInstallment(BigDecimal lastInstallment) {
        this.lastInstallment = lastInstallment;
    }

    public String getLegveh() {
        return legveh;
    }

    public void setLegveh(String legveh) {
        this.legveh = legveh;
    }

    public BigDecimal getLifeInsurance() {
        return lifeInsurance;
    }

    public void setLifeInsurance(BigDecimal lifeInsurance) {
        this.lifeInsurance = lifeInsurance;
    }

    public String getLifeInsuranceDeductType() {
        return lifeInsuranceDeductType;
    }

    public void setLifeInsuranceDeductType(String lifeInsuranceDeductType) {
        this.lifeInsuranceDeductType = lifeInsuranceDeductType;
    }

    public String getLoanno() {
        return loanno;
    }

    public void setLoanno(String loanno) {
        this.loanno = loanno;
    }

    public String getLoanoff() {
        return loanoff;
    }

    public void setLoanoff(String loanoff) {
        this.loanoff = loanoff;
    }

    public String getLoanpur() {
        return loanpur;
    }

    public void setLoanpur(String loanpur) {
        this.loanpur = loanpur;
    }

    public String getLoantype() {
        return loantype;
    }

    public void setLoantype(String loantype) {
        this.loantype = loantype;
    }

    public BigDecimal getLpcrate() {
        return lpcrate;
    }

    public void setLpcrate(BigDecimal lpcrate) {
        this.lpcrate = lpcrate;
    }

    public Date getMatdt() {
        return matdt;
    }

    public void setMatdt(Date matdt) {
        this.matdt = matdt;
    }

    public BigDecimal getMir() {
        return mir;
    }

    public void setMir(BigDecimal mir) {
        this.mir = mir;
    }

    public BigDecimal getMonthlyPension() {
        return monthlyPension;
    }

    public void setMonthlyPension(BigDecimal monthlyPension) {
        this.monthlyPension = monthlyPension;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNetaccount() {
        return netaccount;
    }

    public void setNetaccount(String netaccount) {
        this.netaccount = netaccount;
    }

    public String getNetdispo() {
        return netdispo;
    }

    public void setNetdispo(String netdispo) {
        this.netdispo = netdispo;
    }

    public BigDecimal getNetprcds() {
        return netprcds;
    }

    public void setNetprcds(BigDecimal netprcds) {
        this.netprcds = netprcds;
    }

    public BigDecimal getNetprcdsorig() {
        return netprcdsorig;
    }

    public void setNetprcdsorig(BigDecimal netprcdsorig) {
        this.netprcdsorig = netprcdsorig;
    }

    public Integer getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(Integer gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public Integer getNoOfInterestPaymentToAdvance() {
        return noOfInterestPaymentToAdvance;
    }

    public void setNoOfInterestPaymentToAdvance(Integer noOfInterestPaymentToAdvance) {
        this.noOfInterestPaymentToAdvance = noOfInterestPaymentToAdvance;
    }

    public BigDecimal getNominal() {
        return nominal;
    }

    public void setNominal(BigDecimal nominal) {
        this.nominal = nominal;
    }

    public BigDecimal getNotarialFee() {
        return notarialFee;
    }

    public void setNotarialFee(BigDecimal notarialFee) {
        this.notarialFee = notarialFee;
    }

    public String getNotarialFeeDeductType() {
        return notarialFeeDeductType;
    }

    public void setNotarialFeeDeductType(String notarialFeeDeductType) {
        this.notarialFeeDeductType = notarialFeeDeductType;
    }

    public BigDecimal getNotfee() {
        return notfee;
    }

    public void setNotfee(BigDecimal notfee) {
        this.notfee = notfee;
    }

    public Date getNxtrprce() {
        return nxtrprce;
    }

    public void setNxtrprce(Date nxtrprce) {
        this.nxtrprce = nxtrprce;
    }

    public String getOrigbr() {
        return origbr;
    }

    public void setOrigbr(String origbr) {
        this.origbr = origbr;
    }

    public String getOrigoff() {
        return origoff;
    }

    public void setOrigoff(String origoff) {
        this.origoff = origoff;
    }

    public BigDecimal getOrppd() {
        return orppd;
    }

    public void setOrppd(BigDecimal orppd) {
        this.orppd = orppd;
    }

    public BigDecimal getOthcharges() {
        return othcharges;
    }

    public void setOthcharges(BigDecimal othcharges) {
        this.othcharges = othcharges;
    }

    public BigDecimal getOtherAddtnlToAmortFee() {
        return otherAddtnlToAmortFee;
    }

    public void setOtherAddtnlToAmortFee(BigDecimal otherAddtnlToAmortFee) {
        this.otherAddtnlToAmortFee = otherAddtnlToAmortFee;
    }

    public BigDecimal getOtherNonFinancialCharges() {
        return otherNonFinancialCharges;
    }

    public void setOtherNonFinancialCharges(BigDecimal otherNonFinancialCharges) {
        this.otherNonFinancialCharges = otherNonFinancialCharges;
    }

    public String getOtherNonFinancialChargesDeductType() {
        return otherNonFinancialChargesDeductType;
    }

    public void setOtherNonFinancialChargesDeductType(String otherNonFinancialChargesDeductType) {
        this.otherNonFinancialChargesDeductType = otherNonFinancialChargesDeductType;
    }

    public BigDecimal getOuid() {
        return ouid;
    }

    public void setOuid(BigDecimal ouid) {
        this.ouid = ouid;
    }

    public BigDecimal getOutrightPf() {
        return outrightPf;
    }

    public void setOutrightPf(BigDecimal outrightPf) {
        this.outrightPf = outrightPf;
    }

    public String getOutrightPfdeductType() {
        return outrightPfdeductType;
    }

    public void setOutrightPfdeductType(String outrightPfdeductType) {
        this.outrightPfdeductType = outrightPfdeductType;
    }

    public String getPayacct() {
        return payacct;
    }

    public void setPayacct(String payacct) {
        this.payacct = payacct;
    }

    public String getPayinst() {
        return payinst;
    }

    public void setPayinst(String payinst) {
        this.payinst = payinst;
    }

    public Integer getPaymentDueDay() {
        return paymentDueDay;
    }

    public void setPaymentDueDay(Integer paymentDueDay) {
        this.paymentDueDay = paymentDueDay;
    }

    public String getPymntPlan() {
        return pymntPlan;
    }

    public void setPymntPlan(String pymntPlan) {
        this.pymntPlan = pymntPlan;
    }

    public Integer getPdcctr() {
        return pdcctr;
    }

    public void setPdcctr(Integer pdcctr) {
        this.pdcctr = pdcctr;
    }

    public BigDecimal getPnamt() {
        return pnamt;
    }

    public void setPnamt(BigDecimal pnamt) {
        this.pnamt = pnamt;
    }

    public String getPnno() {
        return pnno;
    }

    public void setPnno(String pnno) {
        this.pnno = pnno;
    }

    public String getPpycyc() {
        return ppycyc;
    }

    public void setPpycyc(String ppycyc) {
        this.ppycyc = ppycyc;
    }

    public Date getPpyfddt() {
        return ppyfddt;
    }

    public void setPpyfddt(Date ppyfddt) {
        this.ppyfddt = ppyfddt;
    }

    public Integer getPpynum() {
        return ppynum;
    }

    public void setPpynum(Integer ppynum) {
        this.ppynum = ppynum;
    }

    public String getPpytype() {
        return ppytype;
    }

    public void setPpytype(String ppytype) {
        this.ppytype = ppytype;
    }

    public BigDecimal getPrime() {
        return prime;
    }

    public void setPrime(BigDecimal prime) {
        this.prime = prime;
    }

    public BigDecimal getProcessingFee() {
        return processingFee;
    }

    public void setProcessingFee(BigDecimal processingFee) {
        this.processingFee = processingFee;
    }

    public String getProcessingFeeDeductType() {
        return processingFeeDeductType;
    }

    public void setProcessingFeeDeductType(String processingFeeDeductType) {
        this.processingFeeDeductType = processingFeeDeductType;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public String getRateInWords() {
        return rateInWords;
    }

    public void setRateInWords(String rateInWords) {
        this.rateInWords = rateInWords;
    }

    public BigDecimal getRegisterOfDeeds() {
        return registerOfDeeds;
    }

    public void setRegisterOfDeeds(BigDecimal registerOfDeeds) {
        this.registerOfDeeds = registerOfDeeds;
    }

    public String getRegisterOfDeedsDeductType() {
        return registerOfDeedsDeductType;
    }

    public void setRegisterOfDeedsDeductType(String registerOfDeedsDeductType) {
        this.registerOfDeedsDeductType = registerOfDeedsDeductType;
    }

    public String getRepricingPeriod() {
        return repricingPeriod;
    }

    public void setRepricingPeriod(String repricingPeriod) {
        this.repricingPeriod = repricingPeriod;
    }

    public Integer getRepricingTerm() {
        return repricingTerm;
    }

    public void setRepricingTerm(Integer repricingTerm) {
        this.repricingTerm = repricingTerm;
    }

    public String getRprcecyc() {
        return rprcecyc;
    }

    public void setRprcecyc(String rprcecyc) {
        this.rprcecyc = rprcecyc;
    }

    public String getRprceflg() {
        return rprceflg;
    }

    public void setRprceflg(String rprceflg) {
        this.rprceflg = rprceflg;
    }

    public String getSeccode() {
        return seccode;
    }

    public void setSeccode(String seccode) {
        this.seccode = seccode;
    }

    public BigDecimal getSellprice() {
        return sellprice;
    }

    public void setSellprice(BigDecimal sellprice) {
        this.sellprice = sellprice;
    }

    public BigDecimal getSer() {
        return ser;
    }

    public void setSer(BigDecimal ser) {
        this.ser = ser;
    }

    public BigDecimal getServiceChargeRate() {
        return serviceChargeRate;
    }

    public void setServiceChargeRate(BigDecimal serviceChargeRate) {
        this.serviceChargeRate = serviceChargeRate;
    }

    public BigDecimal getSpread() {
        return spread;
    }

    public void setSpread(BigDecimal spread) {
        this.spread = spread;
    }

    public Date getStsdate() {
        return stsdate;
    }

    public void setStsdate(Date stsdate) {
        this.stsdate = stsdate;
    }

    public String getSubprd() {
        return subprd;
    }

    public void setSubprd(String subprd) {
        this.subprd = subprd;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public String getTermcyc() {
        return termcyc;
    }

    public void setTermcyc(String termcyc) {
        this.termcyc = termcyc;
    }

    public BigDecimal getTotalNfccollect() {
        return totalNfccollect;
    }

    public void setTotalNfccollect(BigDecimal totalNfccollect) {
        this.totalNfccollect = totalNfccollect;
    }

    public BigDecimal getTotalNfcdeductToLoanProc() {
        return totalNfcdeductToLoanProc;
    }

    public void setTotalNfcdeductToLoanProc(BigDecimal totalNfcdeductToLoanProc) {
        this.totalNfcdeductToLoanProc = totalNfcdeductToLoanProc;
    }

    public Integer getTxbatch() {
        return txbatch;
    }

    public void setTxbatch(Integer txbatch) {
        this.txbatch = txbatch;
    }

    public String getTxbranch() {
        return txbranch;
    }

    public void setTxbranch(String txbranch) {
        this.txbranch = txbranch;
    }

    public String getTxcode() {
        return txcode;
    }

    public void setTxcode(String txcode) {
        this.txcode = txcode;
    }

    public String getTxmkr() {
        return txmkr;
    }

    public void setTxmkr(String txmkr) {
        this.txmkr = txmkr;
    }

    public String getTxoff() {
        return txoff;
    }

    public void setTxoff(String txoff) {
        this.txoff = txoff;
    }

    public String getTxoper() {
        return txoper;
    }

    public void setTxoper(String txoper) {
        this.txoper = txoper;
    }

    public Integer getTxseq() {
        return txseq;
    }

    public void setTxseq(Integer txseq) {
        this.txseq = txseq;
    }

    public String getTxsrce() {
        return txsrce;
    }

    public void setTxsrce(String txsrce) {
        this.txsrce = txsrce;
    }

    public String getTxstat() {
        return txstat;
    }

    public void setTxstat(String txstat) {
        this.txstat = txstat;
    }

    public Date getTxvaldt() {
        return txvaldt;
    }

    public void setTxvaldt(Date txvaldt) {
        this.txvaldt = txvaldt;
    }

    public BigDecimal getUidAdv() {
        return uidAdv;
    }

    public void setUidAdv(BigDecimal uidAdv) {
        this.uidAdv = uidAdv;
    }

    public BigDecimal getUidBal() {
        return uidBal;
    }

    public void setUidBal(BigDecimal uidBal) {
        this.uidBal = uidBal;
    }

    public Date getUidEndDate() {
        return uidEndDate;
    }

    public void setUidEndDate(Date uidEndDate) {
        this.uidEndDate = uidEndDate;
    }

    public Integer getIntchargedays() {
        return intchargedays;
    }

    public void setIntchargedays(Integer intchargedays) {
        this.intchargedays = intchargedays;
    }

    public BigDecimal getIntchargeamt() {
        return intchargeamt;
    }

    public void setIntchargeamt(BigDecimal intchargeamt) {
        this.intchargeamt = intchargeamt;
    }

    public BigDecimal getEyrate() {
        return eyrate;
    }

    public void setEyrate(BigDecimal eyrate) {
        this.eyrate = eyrate;
    }

    public Integer getNotfeedocs() {
        return notfeedocs;
    }

    public void setNotfeedocs(Integer notfeedocs) {
        this.notfeedocs = notfeedocs;
    }

    public String getCfrefno1() {
        return cfrefno1;
    }

    public void setCfrefno1(String cfrefno1) {
        this.cfrefno1 = cfrefno1;
    }

    public String getCfrefno2() {
        return cfrefno2;
    }

    public void setCfrefno2(String cfrefno2) {
        this.cfrefno2 = cfrefno2;
    }

    public Integer getApplicationtype() {
        return applicationtype;
    }

    public void setApplicationtype(Integer applicationtype) {
        this.applicationtype = applicationtype;
    }

    public String getCfrefnoconcat() {
        return cfrefnoconcat;
    }

    public void setCfrefnoconcat(String cfrefnoconcat) {
        this.cfrefnoconcat = cfrefnoconcat;
    }

    public String getPrinpaycycle() {
        return prinpaycycle;
    }

    public void setPrinpaycycle(String prinpaycycle) {
        this.prinpaycycle = prinpaycycle;
    }

    public String getIntpaycycle() {
        return intpaycycle;
    }

    public void setIntpaycycle(String intpaycycle) {
        this.intpaycycle = intpaycycle;
    }

    public BigDecimal getMaturityvalue() {
        return maturityvalue;
    }

    public void setMaturityvalue(BigDecimal maturityvalue) {
        this.maturityvalue = maturityvalue;
    }

    public BigDecimal getInterestdue() {
        return interestdue;
    }

    public void setInterestdue(BigDecimal interestdue) {
        this.interestdue = interestdue;
    }

    public BigDecimal getTotalfeescharges() {
        return totalfeescharges;
    }

    public void setTotalfeescharges(BigDecimal totalfeescharges) {
        this.totalfeescharges = totalfeescharges;
    }

    public BigDecimal getTotalloanoffsetamt() {
        return totalloanoffsetamt;
    }

    public void setTotalloanoffsetamt(BigDecimal totalloanoffsetamt) {
        this.totalloanoffsetamt = totalloanoffsetamt;
    }

    public BigDecimal getDownpay() {
        return downpay;
    }

    public void setDownpay(BigDecimal downpay) {
        this.downpay = downpay;
    }

    public BigDecimal getDownpaypcnt() {
        return downpaypcnt;
    }

    public void setDownpaypcnt(BigDecimal downpaypcnt) {
        this.downpaypcnt = downpaypcnt;
    }

    public BigDecimal getRetentionamt() {
        return retentionamt;
    }

    public void setRetentionamt(BigDecimal retentionamt) {
        this.retentionamt = retentionamt;
    }

    public BigDecimal getRetentionpcnt() {
        return retentionpcnt;
    }

    public void setRetentionpcnt(BigDecimal retentionpcnt) {
        this.retentionpcnt = retentionpcnt;
    }

    public BigDecimal getAmtfinance() {
        return amtfinance;
    }

    public void setAmtfinance(BigDecimal amtfinance) {
        this.amtfinance = amtfinance;
    }

    public String getRepaytypedesc() {
        return repaytypedesc;
    }

    public void setRepaytypedesc(String repaytypedesc) {
        this.repaytypedesc = repaytypedesc;
    }

    public String getInttypedesc() {
        return inttypedesc;
    }

    public void setInttypedesc(String inttypedesc) {
        this.inttypedesc = inttypedesc;
    }

    public String getIntpaytypedesc() {
        return intpaytypedesc;
    }

    public void setIntpaytypedesc(String intpaytypedesc) {
        this.intpaytypedesc = intpaytypedesc;
    }

    public String getTermcycdesc() {
        return termcycdesc;
    }

    public void setTermcycdesc(String termcycdesc) {
        this.termcycdesc = termcycdesc;
    }

    public String getIntcycdesc() {
        return intcycdesc;
    }

    public void setIntcycdesc(String intcycdesc) {
        this.intcycdesc = intcycdesc;
    }

    public String getPrinpaycycdesc() {
        return prinpaycycdesc;
    }

    public void setPrinpaycycdesc(String prinpaycycdesc) {
        this.prinpaycycdesc = prinpaycycdesc;
    }

    public String getIntpaycycdesc() {
        return intpaycycdesc;
    }

    public void setIntpaycycdesc(String intpaycycdesc) {
        this.intpaycycdesc = intpaycycdesc;
    }

    public String getIntcompmethod() {
        return intcompmethod;
    }

    public void setIntcompmethod(String intcompmethod) {
        this.intcompmethod = intcompmethod;
    }

    public String getIncompmethoddesc() {
        return incompmethoddesc;
    }

    public void setIncompmethoddesc(String incompmethoddesc) {
        this.incompmethoddesc = incompmethoddesc;
    }

    public BigDecimal getTotalfeesandcharges() {
        return totalfeesandcharges;
    }

    public void setTotalfeesandcharges(BigDecimal totalfeesandcharges) {
        this.totalfeesandcharges = totalfeesandcharges;
    }

    public Integer getNoofintpay() {
        return noofintpay;
    }

    public void setNoofintpay(Integer noofintpay) {
        this.noofintpay = noofintpay;
    }

    public Integer getNoofprinpay() {
        return noofprinpay;
    }

    public void setNoofprinpay(Integer noofprinpay) {
        this.noofprinpay = noofprinpay;
    }

    public Integer getNoofadvint() {
        return noofadvint;
    }

    public void setNoofadvint(Integer noofadvint) {
        this.noofadvint = noofadvint;
    }

    public Integer getNoofadvprin() {
        return noofadvprin;
    }

    public void setNoofadvprin(Integer noofadvprin) {
        this.noofadvprin = noofadvprin;
    }

    public String getDuedaterule() {
        return duedaterule;
    }

    public void setDuedaterule(String duedaterule) {
        this.duedaterule = duedaterule;
    }

    public BigDecimal getAdvintamt() {
        return advintamt;
    }

    public void setAdvintamt(BigDecimal advintamt) {
        this.advintamt = advintamt;
    }

    public BigDecimal getAdvprinamt() {
        return advprinamt;
    }

    public void setAdvprinamt(BigDecimal advprinamt) {
        this.advprinamt = advprinamt;
    }

    public BigDecimal getTotalfeesamortized() {
        return totalfeesamortized;
    }

    public void setTotalfeesamortized(BigDecimal totalfeesamortized) {
        this.totalfeesamortized = totalfeesamortized;
    }

    public BigDecimal getTotalpayabletobank() {
        return totalpayabletobank;
    }

    public void setTotalpayabletobank(BigDecimal totalpayabletobank) {
        this.totalpayabletobank = totalpayabletobank;
    }

    public BigDecimal getTotalfeesdeducttoloan() {
        return totalfeesdeducttoloan;
    }

    public void setTotalfeesdeducttoloan(BigDecimal totalfeesdeducttoloan) {
        this.totalfeesdeducttoloan = totalfeesdeducttoloan;
    }

    public BigDecimal getTotalunpaidfees() {
        return totalunpaidfees;
    }

    public void setTotalunpaidfees(BigDecimal totalunpaidfees) {
        this.totalunpaidfees = totalunpaidfees;
    }

    public BigDecimal getTotalpaidfees() {
        return totalpaidfees;
    }

    public void setTotalpaidfees(BigDecimal totalpaidfees) {
        this.totalpaidfees = totalpaidfees;
    }

    public String getDebitbankacctno() {
        return debitbankacctno;
    }

    public void setDebitbankacctno(String debitbankacctno) {
        this.debitbankacctno = debitbankacctno;
    }

    public String getDebitbankaccttype() {
        return debitbankaccttype;
    }

    public void setDebitbankaccttype(String debitbankaccttype) {
        this.debitbankaccttype = debitbankaccttype;
    }

    public String getDebitbankbr() {
        return debitbankbr;
    }

    public void setDebitbankbr(String debitbankbr) {
        this.debitbankbr = debitbankbr;
    }

    public String getDebitbankname() {
        return debitbankname;
    }

    public void setDebitbankname(String debitbankname) {
        this.debitbankname = debitbankname;
    }

    public String getDebitbankbrstn() {
        return debitbankbrstn;
    }

    public void setDebitbankbrstn(String debitbankbrstn) {
        this.debitbankbrstn = debitbankbrstn;
    }

    public Boolean getCollectpdcflag() {
        return collectpdcflag;
    }

    public void setCollectpdcflag(Boolean collectpdcflag) {
        this.collectpdcflag = collectpdcflag;
    }

    public Boolean getCollectcheckflag() {
        return collectcheckflag;
    }

    public void setCollectcheckflag(Boolean collectcheckflag) {
        this.collectcheckflag = collectcheckflag;
    }

    public Boolean getCollectcashflag() {
        return collectcashflag;
    }

    public void setCollectcashflag(Boolean collectcashflag) {
        this.collectcashflag = collectcashflag;
    }

    public Boolean getCollectdebitacctflag() {
        return collectdebitacctflag;
    }

    public void setCollectdebitacctflag(Boolean collectdebitacctflag) {
        this.collectdebitacctflag = collectdebitacctflag;
    }

    public String getIncomerecognition() {
        return incomerecognition;
    }

    public void setIncomerecognition(String incomerecognition) {
        this.incomerecognition = incomerecognition;
    }

    public Integer getBaseyear() {
        return baseyear;
    }

    public void setBaseyear(Integer baseyear) {
        this.baseyear = baseyear;
    }

    public String getExcesspaymenthandling() {
        return excesspaymenthandling;
    }

    public void setExcesspaymenthandling(String excesspaymenthandling) {
        this.excesspaymenthandling = excesspaymenthandling;
    }

    public String getDdlqtype() {
        return ddlqtype;
    }

    public void setDdlqtype(String ddlqtype) {
        this.ddlqtype = ddlqtype;
    }

}
