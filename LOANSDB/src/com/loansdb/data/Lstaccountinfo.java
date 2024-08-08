
package com.loansdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  LOANSDB.Lstaccountinfo
 *  06/29/2018 16:12:55
 * 
 */
public class Lstaccountinfo {

    private String applno;
    private String clientid;
    private Date txdate;
    private Double accref;
    private String accrtype;
    private String acctoff;
    private Integer acctsts;
    private Double addon;
    private Float adjustedTerm;
    private BigDecimal adjustmentAmortization;
    private BigDecimal air;
    private String amortInWords;
    private BigDecimal amortfee;
    private String amtInWords;
    private String apprbr;
    private Double bkfxrate;
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
    private BigDecimal downpay;
    private Date dtbook;
    private Double eir;
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
    private Double lpcrate;
    private Date matdt;
    private Double mir;
    private BigDecimal monthlyPension;
    private String name;
    private String netaccount;
    private String netdispo;
    private BigDecimal netprcds;
    private BigDecimal netprcdsorig;
    private Float gracePeriod;
    private Integer noOfInterestPaymentToAdvance;
    private Double nominal;
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
    private Double prime;
    private BigDecimal processingFee;
    private String processingFeeDeductType;
    private String product;
    private String productGroup;
    private String rateInWords;
    private BigDecimal registerOfDeeds;
    private String registerOfDeedsDeductType;
    private String repricingPeriod;
    private Float repricingTerm;
    private String rprcecyc;
    private String rprceflg;
    private String seccode;
    private BigDecimal sellprice;
    private Double ser;
    private Double serviceChargeRate;
    private Double spread;
    private Date stsdate;
    private Integer subprd;
    private Float term;
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
    private Integer txstat;
    private Date txvaldt;
    private BigDecimal uidAdv;
    private BigDecimal uidBal;
    private Date uidEndDate;

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

    public Double getAccref() {
        return accref;
    }

    public void setAccref(Double accref) {
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

    public Double getAddon() {
        return addon;
    }

    public void setAddon(Double addon) {
        this.addon = addon;
    }

    public Float getAdjustedTerm() {
        return adjustedTerm;
    }

    public void setAdjustedTerm(Float adjustedTerm) {
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

    public Double getBkfxrate() {
        return bkfxrate;
    }

    public void setBkfxrate(Double bkfxrate) {
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

    public BigDecimal getDownpay() {
        return downpay;
    }

    public void setDownpay(BigDecimal downpay) {
        this.downpay = downpay;
    }

    public Date getDtbook() {
        return dtbook;
    }

    public void setDtbook(Date dtbook) {
        this.dtbook = dtbook;
    }

    public Double getEir() {
        return eir;
    }

    public void setEir(Double eir) {
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

    public Double getLpcrate() {
        return lpcrate;
    }

    public void setLpcrate(Double lpcrate) {
        this.lpcrate = lpcrate;
    }

    public Date getMatdt() {
        return matdt;
    }

    public void setMatdt(Date matdt) {
        this.matdt = matdt;
    }

    public Double getMir() {
        return mir;
    }

    public void setMir(Double mir) {
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

    public Float getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(Float gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public Integer getNoOfInterestPaymentToAdvance() {
        return noOfInterestPaymentToAdvance;
    }

    public void setNoOfInterestPaymentToAdvance(Integer noOfInterestPaymentToAdvance) {
        this.noOfInterestPaymentToAdvance = noOfInterestPaymentToAdvance;
    }

    public Double getNominal() {
        return nominal;
    }

    public void setNominal(Double nominal) {
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

    public Double getPrime() {
        return prime;
    }

    public void setPrime(Double prime) {
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

    public Float getRepricingTerm() {
        return repricingTerm;
    }

    public void setRepricingTerm(Float repricingTerm) {
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

    public Double getSer() {
        return ser;
    }

    public void setSer(Double ser) {
        this.ser = ser;
    }

    public Double getServiceChargeRate() {
        return serviceChargeRate;
    }

    public void setServiceChargeRate(Double serviceChargeRate) {
        this.serviceChargeRate = serviceChargeRate;
    }

    public Double getSpread() {
        return spread;
    }

    public void setSpread(Double spread) {
        this.spread = spread;
    }

    public Date getStsdate() {
        return stsdate;
    }

    public void setStsdate(Date stsdate) {
        this.stsdate = stsdate;
    }

    public Integer getSubprd() {
        return subprd;
    }

    public void setSubprd(Integer subprd) {
        this.subprd = subprd;
    }

    public Float getTerm() {
        return term;
    }

    public void setTerm(Float term) {
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

    public Integer getTxstat() {
        return txstat;
    }

    public void setTxstat(Integer txstat) {
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

}
