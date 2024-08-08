
package com.cifsdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  CIFSDB.Tbloans
 *  09/26/2023 10:13:06
 * 
 */
public class Tbloans {

    private Integer id;
    private String legveh;
    private String legbranch;
    private String accountno;
    private String slaidno;
    private String principalNo;
    private String coMaker1;
    private String coMaker2;
    private String productGroup;
    private String loantype;
    private String prodcode;
    private String subprd1;
    private String subprd2;
    private String seccode;
    private String applno;
    private String pnno;
    private BigDecimal pnamt;
    private BigDecimal pnintrate;
    private String pnintmethod;
    private BigDecimal pnterm;
    private String pntermcyc;
    private Integer pnilno;
    private Integer pnilnocyc;
    private BigDecimal faceamt;
    private String pymtplan;
    private BigDecimal effyield;
    private String accrtype;
    private String inttyp;
    private Date dtbook;
    private Date fduedt;
    private Date matdt;
    private BigDecimal uiadv;
    private Integer uiadvno;
    private Date reviewdate;
    private Integer reviewdays;
    private BigDecimal lpcrate;
    private Integer graceprd;
    private Integer pdcctr;
    private String loanoff;
    private String loanpur;
    private Integer tdueilno;
    private Date tdueilduedt;
    private BigDecimal tdueilamt;
    private BigDecimal tdueintamt;
    private BigDecimal tdueprinamt;
    private Integer tpdilno;
    private Date tpdilduedt;
    private BigDecimal tpdilamt;
    private BigDecimal tpdintamt;
    private BigDecimal tpdprinamt;
    private BigDecimal idealprinbal;
    private BigDecimal idealloanbal;
    private BigDecimal prinbal;
    private BigDecimal loanbal;
    private BigDecimal excessbal;
    private BigDecimal lpcbal;
    private BigDecimal uidbal;
    private BigDecimal ienc;
    private BigDecimal air;
    private BigDecimal ar1;
    private BigDecimal ar2;
    private String ar1esc;
    private String ar2esc;
    private BigDecimal chk4clr;
    private Integer iltogo;
    private BigDecimal dlylpc;
    private BigDecimal partialint;
    private BigDecimal partialprin;
    private Date lpdduedt;
    private Integer lpdilno;
    private Date lstaccrdt;
    private BigDecimal lstpyamt;
    private Date lstpydt;
    private String lsttxcod;
    private Date lsttxdt;
    private Integer nxtilno;
    private Date nxtduedt;
    private BigDecimal nxtilamt;
    private BigDecimal nxtintamt;
    private BigDecimal nxtprinamt;
    private Integer remedsts;
    private Integer acctsts;
    private Date acctstsDate;
    private Integer oldsts;
    private Integer ddlq;
    private Integer ddlqBucket;
    private Integer ddlqBucketPrev;
    private String collAgency;
    private String collCollector;
    private String collZone;
    private Integer xpd1;
    private Integer xpd30;
    private Integer xpd60;
    private Integer xpd90;
    private BigDecimal interestAmt;
    private BigDecimal eir;
    private BigDecimal amortizationAmt;
    private Integer paymentCycle;
    private Integer relType;
    private BigDecimal factorRate;
    private BigDecimal ouid;
    private BigDecimal ytdprinpd;
    private BigDecimal ytdintpd;
    private BigDecimal btdprinpd;
    private BigDecimal btdintpd;
    private Integer prodPriorityCode;
    private Integer nxtdueilno;
    private Date nxtdueilduedt;
    private BigDecimal nxtdueilamt;
    private BigDecimal nxtdueilint;
    private BigDecimal nxtdueilprin;
    private BigDecimal unpaidint;
    private BigDecimal unpaidprin;
    private BigDecimal aer;
    private String fullname;
    private String refno;
    private String facilityid;
    private BigDecimal curruidamt;
    private BigDecimal curruidctr;
    private BigDecimal currdailyint;
    private BigDecimal mtdint;
    private Integer oldddlq;
    private String intpycomp;
    private BigDecimal memint;
    private String intpaytype;
    private String cfrefno1;
    private String cfrefno2;
    private String intcycdesc;
    private String termcycdesc;
    private String interestperiod;
    private BigDecimal addtnlint;
    private Boolean wtaxflag;
    private String incomerecognition;
    private Integer baseyear;
    private String excesspaymenthandling;
    private String ddlqtype;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLegveh() {
        return legveh;
    }

    public void setLegveh(String legveh) {
        this.legveh = legveh;
    }

    public String getLegbranch() {
        return legbranch;
    }

    public void setLegbranch(String legbranch) {
        this.legbranch = legbranch;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getSlaidno() {
        return slaidno;
    }

    public void setSlaidno(String slaidno) {
        this.slaidno = slaidno;
    }

    public String getPrincipalNo() {
        return principalNo;
    }

    public void setPrincipalNo(String principalNo) {
        this.principalNo = principalNo;
    }

    public String getCoMaker1() {
        return coMaker1;
    }

    public void setCoMaker1(String coMaker1) {
        this.coMaker1 = coMaker1;
    }

    public String getCoMaker2() {
        return coMaker2;
    }

    public void setCoMaker2(String coMaker2) {
        this.coMaker2 = coMaker2;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public String getLoantype() {
        return loantype;
    }

    public void setLoantype(String loantype) {
        this.loantype = loantype;
    }

    public String getProdcode() {
        return prodcode;
    }

    public void setProdcode(String prodcode) {
        this.prodcode = prodcode;
    }

    public String getSubprd1() {
        return subprd1;
    }

    public void setSubprd1(String subprd1) {
        this.subprd1 = subprd1;
    }

    public String getSubprd2() {
        return subprd2;
    }

    public void setSubprd2(String subprd2) {
        this.subprd2 = subprd2;
    }

    public String getSeccode() {
        return seccode;
    }

    public void setSeccode(String seccode) {
        this.seccode = seccode;
    }

    public String getApplno() {
        return applno;
    }

    public void setApplno(String applno) {
        this.applno = applno;
    }

    public String getPnno() {
        return pnno;
    }

    public void setPnno(String pnno) {
        this.pnno = pnno;
    }

    public BigDecimal getPnamt() {
        return pnamt;
    }

    public void setPnamt(BigDecimal pnamt) {
        this.pnamt = pnamt;
    }

    public BigDecimal getPnintrate() {
        return pnintrate;
    }

    public void setPnintrate(BigDecimal pnintrate) {
        this.pnintrate = pnintrate;
    }

    public String getPnintmethod() {
        return pnintmethod;
    }

    public void setPnintmethod(String pnintmethod) {
        this.pnintmethod = pnintmethod;
    }

    public BigDecimal getPnterm() {
        return pnterm;
    }

    public void setPnterm(BigDecimal pnterm) {
        this.pnterm = pnterm;
    }

    public String getPntermcyc() {
        return pntermcyc;
    }

    public void setPntermcyc(String pntermcyc) {
        this.pntermcyc = pntermcyc;
    }

    public Integer getPnilno() {
        return pnilno;
    }

    public void setPnilno(Integer pnilno) {
        this.pnilno = pnilno;
    }

    public Integer getPnilnocyc() {
        return pnilnocyc;
    }

    public void setPnilnocyc(Integer pnilnocyc) {
        this.pnilnocyc = pnilnocyc;
    }

    public BigDecimal getFaceamt() {
        return faceamt;
    }

    public void setFaceamt(BigDecimal faceamt) {
        this.faceamt = faceamt;
    }

    public String getPymtplan() {
        return pymtplan;
    }

    public void setPymtplan(String pymtplan) {
        this.pymtplan = pymtplan;
    }

    public BigDecimal getEffyield() {
        return effyield;
    }

    public void setEffyield(BigDecimal effyield) {
        this.effyield = effyield;
    }

    public String getAccrtype() {
        return accrtype;
    }

    public void setAccrtype(String accrtype) {
        this.accrtype = accrtype;
    }

    public String getInttyp() {
        return inttyp;
    }

    public void setInttyp(String inttyp) {
        this.inttyp = inttyp;
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

    public Date getMatdt() {
        return matdt;
    }

    public void setMatdt(Date matdt) {
        this.matdt = matdt;
    }

    public BigDecimal getUiadv() {
        return uiadv;
    }

    public void setUiadv(BigDecimal uiadv) {
        this.uiadv = uiadv;
    }

    public Integer getUiadvno() {
        return uiadvno;
    }

    public void setUiadvno(Integer uiadvno) {
        this.uiadvno = uiadvno;
    }

    public Date getReviewdate() {
        return reviewdate;
    }

    public void setReviewdate(Date reviewdate) {
        this.reviewdate = reviewdate;
    }

    public Integer getReviewdays() {
        return reviewdays;
    }

    public void setReviewdays(Integer reviewdays) {
        this.reviewdays = reviewdays;
    }

    public BigDecimal getLpcrate() {
        return lpcrate;
    }

    public void setLpcrate(BigDecimal lpcrate) {
        this.lpcrate = lpcrate;
    }

    public Integer getGraceprd() {
        return graceprd;
    }

    public void setGraceprd(Integer graceprd) {
        this.graceprd = graceprd;
    }

    public Integer getPdcctr() {
        return pdcctr;
    }

    public void setPdcctr(Integer pdcctr) {
        this.pdcctr = pdcctr;
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

    public Integer getTdueilno() {
        return tdueilno;
    }

    public void setTdueilno(Integer tdueilno) {
        this.tdueilno = tdueilno;
    }

    public Date getTdueilduedt() {
        return tdueilduedt;
    }

    public void setTdueilduedt(Date tdueilduedt) {
        this.tdueilduedt = tdueilduedt;
    }

    public BigDecimal getTdueilamt() {
        return tdueilamt;
    }

    public void setTdueilamt(BigDecimal tdueilamt) {
        this.tdueilamt = tdueilamt;
    }

    public BigDecimal getTdueintamt() {
        return tdueintamt;
    }

    public void setTdueintamt(BigDecimal tdueintamt) {
        this.tdueintamt = tdueintamt;
    }

    public BigDecimal getTdueprinamt() {
        return tdueprinamt;
    }

    public void setTdueprinamt(BigDecimal tdueprinamt) {
        this.tdueprinamt = tdueprinamt;
    }

    public Integer getTpdilno() {
        return tpdilno;
    }

    public void setTpdilno(Integer tpdilno) {
        this.tpdilno = tpdilno;
    }

    public Date getTpdilduedt() {
        return tpdilduedt;
    }

    public void setTpdilduedt(Date tpdilduedt) {
        this.tpdilduedt = tpdilduedt;
    }

    public BigDecimal getTpdilamt() {
        return tpdilamt;
    }

    public void setTpdilamt(BigDecimal tpdilamt) {
        this.tpdilamt = tpdilamt;
    }

    public BigDecimal getTpdintamt() {
        return tpdintamt;
    }

    public void setTpdintamt(BigDecimal tpdintamt) {
        this.tpdintamt = tpdintamt;
    }

    public BigDecimal getTpdprinamt() {
        return tpdprinamt;
    }

    public void setTpdprinamt(BigDecimal tpdprinamt) {
        this.tpdprinamt = tpdprinamt;
    }

    public BigDecimal getIdealprinbal() {
        return idealprinbal;
    }

    public void setIdealprinbal(BigDecimal idealprinbal) {
        this.idealprinbal = idealprinbal;
    }

    public BigDecimal getIdealloanbal() {
        return idealloanbal;
    }

    public void setIdealloanbal(BigDecimal idealloanbal) {
        this.idealloanbal = idealloanbal;
    }

    public BigDecimal getPrinbal() {
        return prinbal;
    }

    public void setPrinbal(BigDecimal prinbal) {
        this.prinbal = prinbal;
    }

    public BigDecimal getLoanbal() {
        return loanbal;
    }

    public void setLoanbal(BigDecimal loanbal) {
        this.loanbal = loanbal;
    }

    public BigDecimal getExcessbal() {
        return excessbal;
    }

    public void setExcessbal(BigDecimal excessbal) {
        this.excessbal = excessbal;
    }

    public BigDecimal getLpcbal() {
        return lpcbal;
    }

    public void setLpcbal(BigDecimal lpcbal) {
        this.lpcbal = lpcbal;
    }

    public BigDecimal getUidbal() {
        return uidbal;
    }

    public void setUidbal(BigDecimal uidbal) {
        this.uidbal = uidbal;
    }

    public BigDecimal getIenc() {
        return ienc;
    }

    public void setIenc(BigDecimal ienc) {
        this.ienc = ienc;
    }

    public BigDecimal getAir() {
        return air;
    }

    public void setAir(BigDecimal air) {
        this.air = air;
    }

    public BigDecimal getAr1() {
        return ar1;
    }

    public void setAr1(BigDecimal ar1) {
        this.ar1 = ar1;
    }

    public BigDecimal getAr2() {
        return ar2;
    }

    public void setAr2(BigDecimal ar2) {
        this.ar2 = ar2;
    }

    public String getAr1esc() {
        return ar1esc;
    }

    public void setAr1esc(String ar1esc) {
        this.ar1esc = ar1esc;
    }

    public String getAr2esc() {
        return ar2esc;
    }

    public void setAr2esc(String ar2esc) {
        this.ar2esc = ar2esc;
    }

    public BigDecimal getChk4clr() {
        return chk4clr;
    }

    public void setChk4clr(BigDecimal chk4clr) {
        this.chk4clr = chk4clr;
    }

    public Integer getIltogo() {
        return iltogo;
    }

    public void setIltogo(Integer iltogo) {
        this.iltogo = iltogo;
    }

    public BigDecimal getDlylpc() {
        return dlylpc;
    }

    public void setDlylpc(BigDecimal dlylpc) {
        this.dlylpc = dlylpc;
    }

    public BigDecimal getPartialint() {
        return partialint;
    }

    public void setPartialint(BigDecimal partialint) {
        this.partialint = partialint;
    }

    public BigDecimal getPartialprin() {
        return partialprin;
    }

    public void setPartialprin(BigDecimal partialprin) {
        this.partialprin = partialprin;
    }

    public Date getLpdduedt() {
        return lpdduedt;
    }

    public void setLpdduedt(Date lpdduedt) {
        this.lpdduedt = lpdduedt;
    }

    public Integer getLpdilno() {
        return lpdilno;
    }

    public void setLpdilno(Integer lpdilno) {
        this.lpdilno = lpdilno;
    }

    public Date getLstaccrdt() {
        return lstaccrdt;
    }

    public void setLstaccrdt(Date lstaccrdt) {
        this.lstaccrdt = lstaccrdt;
    }

    public BigDecimal getLstpyamt() {
        return lstpyamt;
    }

    public void setLstpyamt(BigDecimal lstpyamt) {
        this.lstpyamt = lstpyamt;
    }

    public Date getLstpydt() {
        return lstpydt;
    }

    public void setLstpydt(Date lstpydt) {
        this.lstpydt = lstpydt;
    }

    public String getLsttxcod() {
        return lsttxcod;
    }

    public void setLsttxcod(String lsttxcod) {
        this.lsttxcod = lsttxcod;
    }

    public Date getLsttxdt() {
        return lsttxdt;
    }

    public void setLsttxdt(Date lsttxdt) {
        this.lsttxdt = lsttxdt;
    }

    public Integer getNxtilno() {
        return nxtilno;
    }

    public void setNxtilno(Integer nxtilno) {
        this.nxtilno = nxtilno;
    }

    public Date getNxtduedt() {
        return nxtduedt;
    }

    public void setNxtduedt(Date nxtduedt) {
        this.nxtduedt = nxtduedt;
    }

    public BigDecimal getNxtilamt() {
        return nxtilamt;
    }

    public void setNxtilamt(BigDecimal nxtilamt) {
        this.nxtilamt = nxtilamt;
    }

    public BigDecimal getNxtintamt() {
        return nxtintamt;
    }

    public void setNxtintamt(BigDecimal nxtintamt) {
        this.nxtintamt = nxtintamt;
    }

    public BigDecimal getNxtprinamt() {
        return nxtprinamt;
    }

    public void setNxtprinamt(BigDecimal nxtprinamt) {
        this.nxtprinamt = nxtprinamt;
    }

    public Integer getRemedsts() {
        return remedsts;
    }

    public void setRemedsts(Integer remedsts) {
        this.remedsts = remedsts;
    }

    public Integer getAcctsts() {
        return acctsts;
    }

    public void setAcctsts(Integer acctsts) {
        this.acctsts = acctsts;
    }

    public Date getAcctstsDate() {
        return acctstsDate;
    }

    public void setAcctstsDate(Date acctstsDate) {
        this.acctstsDate = acctstsDate;
    }

    public Integer getOldsts() {
        return oldsts;
    }

    public void setOldsts(Integer oldsts) {
        this.oldsts = oldsts;
    }

    public Integer getDdlq() {
        return ddlq;
    }

    public void setDdlq(Integer ddlq) {
        this.ddlq = ddlq;
    }

    public Integer getDdlqBucket() {
        return ddlqBucket;
    }

    public void setDdlqBucket(Integer ddlqBucket) {
        this.ddlqBucket = ddlqBucket;
    }

    public Integer getDdlqBucketPrev() {
        return ddlqBucketPrev;
    }

    public void setDdlqBucketPrev(Integer ddlqBucketPrev) {
        this.ddlqBucketPrev = ddlqBucketPrev;
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

    public Integer getXpd1() {
        return xpd1;
    }

    public void setXpd1(Integer xpd1) {
        this.xpd1 = xpd1;
    }

    public Integer getXpd30() {
        return xpd30;
    }

    public void setXpd30(Integer xpd30) {
        this.xpd30 = xpd30;
    }

    public Integer getXpd60() {
        return xpd60;
    }

    public void setXpd60(Integer xpd60) {
        this.xpd60 = xpd60;
    }

    public Integer getXpd90() {
        return xpd90;
    }

    public void setXpd90(Integer xpd90) {
        this.xpd90 = xpd90;
    }

    public BigDecimal getInterestAmt() {
        return interestAmt;
    }

    public void setInterestAmt(BigDecimal interestAmt) {
        this.interestAmt = interestAmt;
    }

    public BigDecimal getEir() {
        return eir;
    }

    public void setEir(BigDecimal eir) {
        this.eir = eir;
    }

    public BigDecimal getAmortizationAmt() {
        return amortizationAmt;
    }

    public void setAmortizationAmt(BigDecimal amortizationAmt) {
        this.amortizationAmt = amortizationAmt;
    }

    public Integer getPaymentCycle() {
        return paymentCycle;
    }

    public void setPaymentCycle(Integer paymentCycle) {
        this.paymentCycle = paymentCycle;
    }

    public Integer getRelType() {
        return relType;
    }

    public void setRelType(Integer relType) {
        this.relType = relType;
    }

    public BigDecimal getFactorRate() {
        return factorRate;
    }

    public void setFactorRate(BigDecimal factorRate) {
        this.factorRate = factorRate;
    }

    public BigDecimal getOuid() {
        return ouid;
    }

    public void setOuid(BigDecimal ouid) {
        this.ouid = ouid;
    }

    public BigDecimal getYtdprinpd() {
        return ytdprinpd;
    }

    public void setYtdprinpd(BigDecimal ytdprinpd) {
        this.ytdprinpd = ytdprinpd;
    }

    public BigDecimal getYtdintpd() {
        return ytdintpd;
    }

    public void setYtdintpd(BigDecimal ytdintpd) {
        this.ytdintpd = ytdintpd;
    }

    public BigDecimal getBtdprinpd() {
        return btdprinpd;
    }

    public void setBtdprinpd(BigDecimal btdprinpd) {
        this.btdprinpd = btdprinpd;
    }

    public BigDecimal getBtdintpd() {
        return btdintpd;
    }

    public void setBtdintpd(BigDecimal btdintpd) {
        this.btdintpd = btdintpd;
    }

    public Integer getProdPriorityCode() {
        return prodPriorityCode;
    }

    public void setProdPriorityCode(Integer prodPriorityCode) {
        this.prodPriorityCode = prodPriorityCode;
    }

    public Integer getNxtdueilno() {
        return nxtdueilno;
    }

    public void setNxtdueilno(Integer nxtdueilno) {
        this.nxtdueilno = nxtdueilno;
    }

    public Date getNxtdueilduedt() {
        return nxtdueilduedt;
    }

    public void setNxtdueilduedt(Date nxtdueilduedt) {
        this.nxtdueilduedt = nxtdueilduedt;
    }

    public BigDecimal getNxtdueilamt() {
        return nxtdueilamt;
    }

    public void setNxtdueilamt(BigDecimal nxtdueilamt) {
        this.nxtdueilamt = nxtdueilamt;
    }

    public BigDecimal getNxtdueilint() {
        return nxtdueilint;
    }

    public void setNxtdueilint(BigDecimal nxtdueilint) {
        this.nxtdueilint = nxtdueilint;
    }

    public BigDecimal getNxtdueilprin() {
        return nxtdueilprin;
    }

    public void setNxtdueilprin(BigDecimal nxtdueilprin) {
        this.nxtdueilprin = nxtdueilprin;
    }

    public BigDecimal getUnpaidint() {
        return unpaidint;
    }

    public void setUnpaidint(BigDecimal unpaidint) {
        this.unpaidint = unpaidint;
    }

    public BigDecimal getUnpaidprin() {
        return unpaidprin;
    }

    public void setUnpaidprin(BigDecimal unpaidprin) {
        this.unpaidprin = unpaidprin;
    }

    public BigDecimal getAer() {
        return aer;
    }

    public void setAer(BigDecimal aer) {
        this.aer = aer;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRefno() {
        return refno;
    }

    public void setRefno(String refno) {
        this.refno = refno;
    }

    public String getFacilityid() {
        return facilityid;
    }

    public void setFacilityid(String facilityid) {
        this.facilityid = facilityid;
    }

    public BigDecimal getCurruidamt() {
        return curruidamt;
    }

    public void setCurruidamt(BigDecimal curruidamt) {
        this.curruidamt = curruidamt;
    }

    public BigDecimal getCurruidctr() {
        return curruidctr;
    }

    public void setCurruidctr(BigDecimal curruidctr) {
        this.curruidctr = curruidctr;
    }

    public BigDecimal getCurrdailyint() {
        return currdailyint;
    }

    public void setCurrdailyint(BigDecimal currdailyint) {
        this.currdailyint = currdailyint;
    }

    public BigDecimal getMtdint() {
        return mtdint;
    }

    public void setMtdint(BigDecimal mtdint) {
        this.mtdint = mtdint;
    }

    public Integer getOldddlq() {
        return oldddlq;
    }

    public void setOldddlq(Integer oldddlq) {
        this.oldddlq = oldddlq;
    }

    public String getIntpycomp() {
        return intpycomp;
    }

    public void setIntpycomp(String intpycomp) {
        this.intpycomp = intpycomp;
    }

    public BigDecimal getMemint() {
        return memint;
    }

    public void setMemint(BigDecimal memint) {
        this.memint = memint;
    }

    public String getIntpaytype() {
        return intpaytype;
    }

    public void setIntpaytype(String intpaytype) {
        this.intpaytype = intpaytype;
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

    public String getIntcycdesc() {
        return intcycdesc;
    }

    public void setIntcycdesc(String intcycdesc) {
        this.intcycdesc = intcycdesc;
    }

    public String getTermcycdesc() {
        return termcycdesc;
    }

    public void setTermcycdesc(String termcycdesc) {
        this.termcycdesc = termcycdesc;
    }

    public String getInterestperiod() {
        return interestperiod;
    }

    public void setInterestperiod(String interestperiod) {
        this.interestperiod = interestperiod;
    }

    public BigDecimal getAddtnlint() {
        return addtnlint;
    }

    public void setAddtnlint(BigDecimal addtnlint) {
        this.addtnlint = addtnlint;
    }

    public Boolean getWtaxflag() {
        return wtaxflag;
    }

    public void setWtaxflag(Boolean wtaxflag) {
        this.wtaxflag = wtaxflag;
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
