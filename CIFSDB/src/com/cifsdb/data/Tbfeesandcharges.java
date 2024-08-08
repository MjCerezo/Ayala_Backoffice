
package com.cifsdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  CIFSDB.Tbfeesandcharges
 *  09/26/2023 10:13:05
 * 
 */
public class Tbfeesandcharges {

    private String productcode;
    private Boolean dstflag;
    private BigDecimal dstrate;
    private String dstrule;
    private BigDecimal dstformuladiv;
    private BigDecimal dstformulamul;
    private BigDecimal dstfixedamt;
    private BigDecimal dstamount1;
    private BigDecimal dstamount2;
    private String dstcollection;
    private Boolean notarialflag;
    private BigDecimal notarialamt;
    private BigDecimal notarialamtperdoc;
    private String notarialcollection;
    private Boolean vatflag;
    private BigDecimal vatrate;
    private String vatcollection;
    private Boolean grtflag;
    private BigDecimal grtrate;
    private String grtcollection;
    private Boolean appfeeflag;
    private BigDecimal appfeeamt;
    private BigDecimal appfeerate;
    private String appfeerule;
    private String appfeecollection;
    private Boolean servicefeeflag;
    private BigDecimal servicefeeamt;
    private BigDecimal servicefeerate;
    private String servicefeerule;
    private String servicefeecollection;
    private Boolean processingfeeflag;
    private String processingfeerule;
    private BigDecimal processingfeeamt;
    private BigDecimal processingfeerate;
    private String processingfeeoption;
    private BigDecimal processingfeeamt1;
    private BigDecimal processingfeerate1;
    private String processingfeeoption1;
    private String processingfeecollection;
    private Boolean fireinsuranceflag;
    private String fireinsurancerule;
    private BigDecimal fireinsuranceamt;
    private String fireinsurancecollection;
    private Boolean marineinsuranceflag;
    private String marineinsurancerule;
    private BigDecimal marineinsuranceamt;
    private String marineinsurancecollection;
    private Boolean comprehensiveflag;
    private String comprehensiverule;
    private BigDecimal comprehensiveamt;
    private String comprehensivecollection;
    private Boolean creditlifeflag;
    private String creditliferule;
    private BigDecimal creditlifeamt;
    private BigDecimal creditlifediv;
    private BigDecimal creditlifemul;
    private String creditlifecollection;
    private Boolean agentcommflag;
    private String agentcommrule;
    private BigDecimal agentcommamt;
    private String agentcommcollection;
    private Boolean dealercommflag;
    private String dealercommrule;
    private BigDecimal dealercommamt;
    private String dealercommcollection;
    private Boolean advanceamortflag;
    private Boolean interestchargeflag;
    private String interestchargecollection;
    private Boolean remflag;
    private String remrule;
    private BigDecimal remamt;
    private String remcollection;
    private Boolean branchincentivefeeflag;
    private String branchincentivefeerule;
    private BigDecimal branchincentivefeeamt;
    private BigDecimal branchincentivefeerate;
    private String branchincentivefeecollection;
    private String updatedby;
    private Date dateupdated;
    private Boolean atmtransfeeflag;
    private BigDecimal atmtransamount;
    private String atmtransfeecollection;
    private Boolean cashcardfeeflag;
    private BigDecimal cashcardamount;
    private String cashcardfeecollection;
    private Boolean depedincentiveflag;
    private String depedincentiverule;
    private BigDecimal depedincentiveamt;
    private BigDecimal depedincentiverate;
    private String depedincentivecollection;
    private BigDecimal processingfeeamt2;
    private BigDecimal processingfeerate2;
    private String processingfeeoption2;
    private Boolean mrifeeflag;
    private String mrifeerule;
    private BigDecimal mrifeeamt;
    private BigDecimal mrifeerate;
    private String mrifeecollection;
    private Boolean chattelfeeflag;
    private String chattelfeerule;
    private BigDecimal chattelfeeamt;
    private BigDecimal chattelfeerate;
    private String chattelfeecollection;
    private Boolean appraisalfeeflag;
    private String appraisalfeerule;
    private BigDecimal appraisalfeeamt;
    private BigDecimal appraisalfeerate;
    private String appraisalfeecollection;
    private Boolean preterminationflag;
    private String preterminationrule;
    private BigDecimal preterminationamt;
    private BigDecimal preterminationrate;
    private String preterminationcollection;
    private Boolean prepaymentfeeflag;
    private String prepaymentfeerule;
    private BigDecimal prepaymentfeeamt;
    private BigDecimal prepaymentfeerate;
    private String prepaymentcollection;
    private Boolean commitmentfeeflag;
    private String commitmentfeerule;
    private BigDecimal commitmentfeeamt;
    private BigDecimal commitmentfeerate;
    private String commitmentfeecollection;
    private Boolean ppdfeeflag;
    private String ppdfeerule;
    private BigDecimal ppdfeeamt;
    private BigDecimal ppdfeerate;
    private String ppdfeecollection;
    private Boolean variableflag1;
    private String variablename1;
    private String variablerule1;
    private BigDecimal variableamt1;
    private BigDecimal variablerate1;
    private String variablecollection1;
    private Boolean variableflag2;
    private String variablename2;
    private String variablerule2;
    private BigDecimal variableamt2;
    private BigDecimal variablerate2;
    private String variablecollection2;

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public Boolean getDstflag() {
        return dstflag;
    }

    public void setDstflag(Boolean dstflag) {
        this.dstflag = dstflag;
    }

    public BigDecimal getDstrate() {
        return dstrate;
    }

    public void setDstrate(BigDecimal dstrate) {
        this.dstrate = dstrate;
    }

    public String getDstrule() {
        return dstrule;
    }

    public void setDstrule(String dstrule) {
        this.dstrule = dstrule;
    }

    public BigDecimal getDstformuladiv() {
        return dstformuladiv;
    }

    public void setDstformuladiv(BigDecimal dstformuladiv) {
        this.dstformuladiv = dstformuladiv;
    }

    public BigDecimal getDstformulamul() {
        return dstformulamul;
    }

    public void setDstformulamul(BigDecimal dstformulamul) {
        this.dstformulamul = dstformulamul;
    }

    public BigDecimal getDstfixedamt() {
        return dstfixedamt;
    }

    public void setDstfixedamt(BigDecimal dstfixedamt) {
        this.dstfixedamt = dstfixedamt;
    }

    public BigDecimal getDstamount1() {
        return dstamount1;
    }

    public void setDstamount1(BigDecimal dstamount1) {
        this.dstamount1 = dstamount1;
    }

    public BigDecimal getDstamount2() {
        return dstamount2;
    }

    public void setDstamount2(BigDecimal dstamount2) {
        this.dstamount2 = dstamount2;
    }

    public String getDstcollection() {
        return dstcollection;
    }

    public void setDstcollection(String dstcollection) {
        this.dstcollection = dstcollection;
    }

    public Boolean getNotarialflag() {
        return notarialflag;
    }

    public void setNotarialflag(Boolean notarialflag) {
        this.notarialflag = notarialflag;
    }

    public BigDecimal getNotarialamt() {
        return notarialamt;
    }

    public void setNotarialamt(BigDecimal notarialamt) {
        this.notarialamt = notarialamt;
    }

    public BigDecimal getNotarialamtperdoc() {
        return notarialamtperdoc;
    }

    public void setNotarialamtperdoc(BigDecimal notarialamtperdoc) {
        this.notarialamtperdoc = notarialamtperdoc;
    }

    public String getNotarialcollection() {
        return notarialcollection;
    }

    public void setNotarialcollection(String notarialcollection) {
        this.notarialcollection = notarialcollection;
    }

    public Boolean getVatflag() {
        return vatflag;
    }

    public void setVatflag(Boolean vatflag) {
        this.vatflag = vatflag;
    }

    public BigDecimal getVatrate() {
        return vatrate;
    }

    public void setVatrate(BigDecimal vatrate) {
        this.vatrate = vatrate;
    }

    public String getVatcollection() {
        return vatcollection;
    }

    public void setVatcollection(String vatcollection) {
        this.vatcollection = vatcollection;
    }

    public Boolean getGrtflag() {
        return grtflag;
    }

    public void setGrtflag(Boolean grtflag) {
        this.grtflag = grtflag;
    }

    public BigDecimal getGrtrate() {
        return grtrate;
    }

    public void setGrtrate(BigDecimal grtrate) {
        this.grtrate = grtrate;
    }

    public String getGrtcollection() {
        return grtcollection;
    }

    public void setGrtcollection(String grtcollection) {
        this.grtcollection = grtcollection;
    }

    public Boolean getAppfeeflag() {
        return appfeeflag;
    }

    public void setAppfeeflag(Boolean appfeeflag) {
        this.appfeeflag = appfeeflag;
    }

    public BigDecimal getAppfeeamt() {
        return appfeeamt;
    }

    public void setAppfeeamt(BigDecimal appfeeamt) {
        this.appfeeamt = appfeeamt;
    }

    public BigDecimal getAppfeerate() {
        return appfeerate;
    }

    public void setAppfeerate(BigDecimal appfeerate) {
        this.appfeerate = appfeerate;
    }

    public String getAppfeerule() {
        return appfeerule;
    }

    public void setAppfeerule(String appfeerule) {
        this.appfeerule = appfeerule;
    }

    public String getAppfeecollection() {
        return appfeecollection;
    }

    public void setAppfeecollection(String appfeecollection) {
        this.appfeecollection = appfeecollection;
    }

    public Boolean getServicefeeflag() {
        return servicefeeflag;
    }

    public void setServicefeeflag(Boolean servicefeeflag) {
        this.servicefeeflag = servicefeeflag;
    }

    public BigDecimal getServicefeeamt() {
        return servicefeeamt;
    }

    public void setServicefeeamt(BigDecimal servicefeeamt) {
        this.servicefeeamt = servicefeeamt;
    }

    public BigDecimal getServicefeerate() {
        return servicefeerate;
    }

    public void setServicefeerate(BigDecimal servicefeerate) {
        this.servicefeerate = servicefeerate;
    }

    public String getServicefeerule() {
        return servicefeerule;
    }

    public void setServicefeerule(String servicefeerule) {
        this.servicefeerule = servicefeerule;
    }

    public String getServicefeecollection() {
        return servicefeecollection;
    }

    public void setServicefeecollection(String servicefeecollection) {
        this.servicefeecollection = servicefeecollection;
    }

    public Boolean getProcessingfeeflag() {
        return processingfeeflag;
    }

    public void setProcessingfeeflag(Boolean processingfeeflag) {
        this.processingfeeflag = processingfeeflag;
    }

    public String getProcessingfeerule() {
        return processingfeerule;
    }

    public void setProcessingfeerule(String processingfeerule) {
        this.processingfeerule = processingfeerule;
    }

    public BigDecimal getProcessingfeeamt() {
        return processingfeeamt;
    }

    public void setProcessingfeeamt(BigDecimal processingfeeamt) {
        this.processingfeeamt = processingfeeamt;
    }

    public BigDecimal getProcessingfeerate() {
        return processingfeerate;
    }

    public void setProcessingfeerate(BigDecimal processingfeerate) {
        this.processingfeerate = processingfeerate;
    }

    public String getProcessingfeeoption() {
        return processingfeeoption;
    }

    public void setProcessingfeeoption(String processingfeeoption) {
        this.processingfeeoption = processingfeeoption;
    }

    public BigDecimal getProcessingfeeamt1() {
        return processingfeeamt1;
    }

    public void setProcessingfeeamt1(BigDecimal processingfeeamt1) {
        this.processingfeeamt1 = processingfeeamt1;
    }

    public BigDecimal getProcessingfeerate1() {
        return processingfeerate1;
    }

    public void setProcessingfeerate1(BigDecimal processingfeerate1) {
        this.processingfeerate1 = processingfeerate1;
    }

    public String getProcessingfeeoption1() {
        return processingfeeoption1;
    }

    public void setProcessingfeeoption1(String processingfeeoption1) {
        this.processingfeeoption1 = processingfeeoption1;
    }

    public String getProcessingfeecollection() {
        return processingfeecollection;
    }

    public void setProcessingfeecollection(String processingfeecollection) {
        this.processingfeecollection = processingfeecollection;
    }

    public Boolean getFireinsuranceflag() {
        return fireinsuranceflag;
    }

    public void setFireinsuranceflag(Boolean fireinsuranceflag) {
        this.fireinsuranceflag = fireinsuranceflag;
    }

    public String getFireinsurancerule() {
        return fireinsurancerule;
    }

    public void setFireinsurancerule(String fireinsurancerule) {
        this.fireinsurancerule = fireinsurancerule;
    }

    public BigDecimal getFireinsuranceamt() {
        return fireinsuranceamt;
    }

    public void setFireinsuranceamt(BigDecimal fireinsuranceamt) {
        this.fireinsuranceamt = fireinsuranceamt;
    }

    public String getFireinsurancecollection() {
        return fireinsurancecollection;
    }

    public void setFireinsurancecollection(String fireinsurancecollection) {
        this.fireinsurancecollection = fireinsurancecollection;
    }

    public Boolean getMarineinsuranceflag() {
        return marineinsuranceflag;
    }

    public void setMarineinsuranceflag(Boolean marineinsuranceflag) {
        this.marineinsuranceflag = marineinsuranceflag;
    }

    public String getMarineinsurancerule() {
        return marineinsurancerule;
    }

    public void setMarineinsurancerule(String marineinsurancerule) {
        this.marineinsurancerule = marineinsurancerule;
    }

    public BigDecimal getMarineinsuranceamt() {
        return marineinsuranceamt;
    }

    public void setMarineinsuranceamt(BigDecimal marineinsuranceamt) {
        this.marineinsuranceamt = marineinsuranceamt;
    }

    public String getMarineinsurancecollection() {
        return marineinsurancecollection;
    }

    public void setMarineinsurancecollection(String marineinsurancecollection) {
        this.marineinsurancecollection = marineinsurancecollection;
    }

    public Boolean getComprehensiveflag() {
        return comprehensiveflag;
    }

    public void setComprehensiveflag(Boolean comprehensiveflag) {
        this.comprehensiveflag = comprehensiveflag;
    }

    public String getComprehensiverule() {
        return comprehensiverule;
    }

    public void setComprehensiverule(String comprehensiverule) {
        this.comprehensiverule = comprehensiverule;
    }

    public BigDecimal getComprehensiveamt() {
        return comprehensiveamt;
    }

    public void setComprehensiveamt(BigDecimal comprehensiveamt) {
        this.comprehensiveamt = comprehensiveamt;
    }

    public String getComprehensivecollection() {
        return comprehensivecollection;
    }

    public void setComprehensivecollection(String comprehensivecollection) {
        this.comprehensivecollection = comprehensivecollection;
    }

    public Boolean getCreditlifeflag() {
        return creditlifeflag;
    }

    public void setCreditlifeflag(Boolean creditlifeflag) {
        this.creditlifeflag = creditlifeflag;
    }

    public String getCreditliferule() {
        return creditliferule;
    }

    public void setCreditliferule(String creditliferule) {
        this.creditliferule = creditliferule;
    }

    public BigDecimal getCreditlifeamt() {
        return creditlifeamt;
    }

    public void setCreditlifeamt(BigDecimal creditlifeamt) {
        this.creditlifeamt = creditlifeamt;
    }

    public BigDecimal getCreditlifediv() {
        return creditlifediv;
    }

    public void setCreditlifediv(BigDecimal creditlifediv) {
        this.creditlifediv = creditlifediv;
    }

    public BigDecimal getCreditlifemul() {
        return creditlifemul;
    }

    public void setCreditlifemul(BigDecimal creditlifemul) {
        this.creditlifemul = creditlifemul;
    }

    public String getCreditlifecollection() {
        return creditlifecollection;
    }

    public void setCreditlifecollection(String creditlifecollection) {
        this.creditlifecollection = creditlifecollection;
    }

    public Boolean getAgentcommflag() {
        return agentcommflag;
    }

    public void setAgentcommflag(Boolean agentcommflag) {
        this.agentcommflag = agentcommflag;
    }

    public String getAgentcommrule() {
        return agentcommrule;
    }

    public void setAgentcommrule(String agentcommrule) {
        this.agentcommrule = agentcommrule;
    }

    public BigDecimal getAgentcommamt() {
        return agentcommamt;
    }

    public void setAgentcommamt(BigDecimal agentcommamt) {
        this.agentcommamt = agentcommamt;
    }

    public String getAgentcommcollection() {
        return agentcommcollection;
    }

    public void setAgentcommcollection(String agentcommcollection) {
        this.agentcommcollection = agentcommcollection;
    }

    public Boolean getDealercommflag() {
        return dealercommflag;
    }

    public void setDealercommflag(Boolean dealercommflag) {
        this.dealercommflag = dealercommflag;
    }

    public String getDealercommrule() {
        return dealercommrule;
    }

    public void setDealercommrule(String dealercommrule) {
        this.dealercommrule = dealercommrule;
    }

    public BigDecimal getDealercommamt() {
        return dealercommamt;
    }

    public void setDealercommamt(BigDecimal dealercommamt) {
        this.dealercommamt = dealercommamt;
    }

    public String getDealercommcollection() {
        return dealercommcollection;
    }

    public void setDealercommcollection(String dealercommcollection) {
        this.dealercommcollection = dealercommcollection;
    }

    public Boolean getAdvanceamortflag() {
        return advanceamortflag;
    }

    public void setAdvanceamortflag(Boolean advanceamortflag) {
        this.advanceamortflag = advanceamortflag;
    }

    public Boolean getInterestchargeflag() {
        return interestchargeflag;
    }

    public void setInterestchargeflag(Boolean interestchargeflag) {
        this.interestchargeflag = interestchargeflag;
    }

    public String getInterestchargecollection() {
        return interestchargecollection;
    }

    public void setInterestchargecollection(String interestchargecollection) {
        this.interestchargecollection = interestchargecollection;
    }

    public Boolean getRemflag() {
        return remflag;
    }

    public void setRemflag(Boolean remflag) {
        this.remflag = remflag;
    }

    public String getRemrule() {
        return remrule;
    }

    public void setRemrule(String remrule) {
        this.remrule = remrule;
    }

    public BigDecimal getRemamt() {
        return remamt;
    }

    public void setRemamt(BigDecimal remamt) {
        this.remamt = remamt;
    }

    public String getRemcollection() {
        return remcollection;
    }

    public void setRemcollection(String remcollection) {
        this.remcollection = remcollection;
    }

    public Boolean getBranchincentivefeeflag() {
        return branchincentivefeeflag;
    }

    public void setBranchincentivefeeflag(Boolean branchincentivefeeflag) {
        this.branchincentivefeeflag = branchincentivefeeflag;
    }

    public String getBranchincentivefeerule() {
        return branchincentivefeerule;
    }

    public void setBranchincentivefeerule(String branchincentivefeerule) {
        this.branchincentivefeerule = branchincentivefeerule;
    }

    public BigDecimal getBranchincentivefeeamt() {
        return branchincentivefeeamt;
    }

    public void setBranchincentivefeeamt(BigDecimal branchincentivefeeamt) {
        this.branchincentivefeeamt = branchincentivefeeamt;
    }

    public BigDecimal getBranchincentivefeerate() {
        return branchincentivefeerate;
    }

    public void setBranchincentivefeerate(BigDecimal branchincentivefeerate) {
        this.branchincentivefeerate = branchincentivefeerate;
    }

    public String getBranchincentivefeecollection() {
        return branchincentivefeecollection;
    }

    public void setBranchincentivefeecollection(String branchincentivefeecollection) {
        this.branchincentivefeecollection = branchincentivefeecollection;
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

    public Boolean getAtmtransfeeflag() {
        return atmtransfeeflag;
    }

    public void setAtmtransfeeflag(Boolean atmtransfeeflag) {
        this.atmtransfeeflag = atmtransfeeflag;
    }

    public BigDecimal getAtmtransamount() {
        return atmtransamount;
    }

    public void setAtmtransamount(BigDecimal atmtransamount) {
        this.atmtransamount = atmtransamount;
    }

    public String getAtmtransfeecollection() {
        return atmtransfeecollection;
    }

    public void setAtmtransfeecollection(String atmtransfeecollection) {
        this.atmtransfeecollection = atmtransfeecollection;
    }

    public Boolean getCashcardfeeflag() {
        return cashcardfeeflag;
    }

    public void setCashcardfeeflag(Boolean cashcardfeeflag) {
        this.cashcardfeeflag = cashcardfeeflag;
    }

    public BigDecimal getCashcardamount() {
        return cashcardamount;
    }

    public void setCashcardamount(BigDecimal cashcardamount) {
        this.cashcardamount = cashcardamount;
    }

    public String getCashcardfeecollection() {
        return cashcardfeecollection;
    }

    public void setCashcardfeecollection(String cashcardfeecollection) {
        this.cashcardfeecollection = cashcardfeecollection;
    }

    public Boolean getDepedincentiveflag() {
        return depedincentiveflag;
    }

    public void setDepedincentiveflag(Boolean depedincentiveflag) {
        this.depedincentiveflag = depedincentiveflag;
    }

    public String getDepedincentiverule() {
        return depedincentiverule;
    }

    public void setDepedincentiverule(String depedincentiverule) {
        this.depedincentiverule = depedincentiverule;
    }

    public BigDecimal getDepedincentiveamt() {
        return depedincentiveamt;
    }

    public void setDepedincentiveamt(BigDecimal depedincentiveamt) {
        this.depedincentiveamt = depedincentiveamt;
    }

    public BigDecimal getDepedincentiverate() {
        return depedincentiverate;
    }

    public void setDepedincentiverate(BigDecimal depedincentiverate) {
        this.depedincentiverate = depedincentiverate;
    }

    public String getDepedincentivecollection() {
        return depedincentivecollection;
    }

    public void setDepedincentivecollection(String depedincentivecollection) {
        this.depedincentivecollection = depedincentivecollection;
    }

    public BigDecimal getProcessingfeeamt2() {
        return processingfeeamt2;
    }

    public void setProcessingfeeamt2(BigDecimal processingfeeamt2) {
        this.processingfeeamt2 = processingfeeamt2;
    }

    public BigDecimal getProcessingfeerate2() {
        return processingfeerate2;
    }

    public void setProcessingfeerate2(BigDecimal processingfeerate2) {
        this.processingfeerate2 = processingfeerate2;
    }

    public String getProcessingfeeoption2() {
        return processingfeeoption2;
    }

    public void setProcessingfeeoption2(String processingfeeoption2) {
        this.processingfeeoption2 = processingfeeoption2;
    }

    public Boolean getMrifeeflag() {
        return mrifeeflag;
    }

    public void setMrifeeflag(Boolean mrifeeflag) {
        this.mrifeeflag = mrifeeflag;
    }

    public String getMrifeerule() {
        return mrifeerule;
    }

    public void setMrifeerule(String mrifeerule) {
        this.mrifeerule = mrifeerule;
    }

    public BigDecimal getMrifeeamt() {
        return mrifeeamt;
    }

    public void setMrifeeamt(BigDecimal mrifeeamt) {
        this.mrifeeamt = mrifeeamt;
    }

    public BigDecimal getMrifeerate() {
        return mrifeerate;
    }

    public void setMrifeerate(BigDecimal mrifeerate) {
        this.mrifeerate = mrifeerate;
    }

    public String getMrifeecollection() {
        return mrifeecollection;
    }

    public void setMrifeecollection(String mrifeecollection) {
        this.mrifeecollection = mrifeecollection;
    }

    public Boolean getChattelfeeflag() {
        return chattelfeeflag;
    }

    public void setChattelfeeflag(Boolean chattelfeeflag) {
        this.chattelfeeflag = chattelfeeflag;
    }

    public String getChattelfeerule() {
        return chattelfeerule;
    }

    public void setChattelfeerule(String chattelfeerule) {
        this.chattelfeerule = chattelfeerule;
    }

    public BigDecimal getChattelfeeamt() {
        return chattelfeeamt;
    }

    public void setChattelfeeamt(BigDecimal chattelfeeamt) {
        this.chattelfeeamt = chattelfeeamt;
    }

    public BigDecimal getChattelfeerate() {
        return chattelfeerate;
    }

    public void setChattelfeerate(BigDecimal chattelfeerate) {
        this.chattelfeerate = chattelfeerate;
    }

    public String getChattelfeecollection() {
        return chattelfeecollection;
    }

    public void setChattelfeecollection(String chattelfeecollection) {
        this.chattelfeecollection = chattelfeecollection;
    }

    public Boolean getAppraisalfeeflag() {
        return appraisalfeeflag;
    }

    public void setAppraisalfeeflag(Boolean appraisalfeeflag) {
        this.appraisalfeeflag = appraisalfeeflag;
    }

    public String getAppraisalfeerule() {
        return appraisalfeerule;
    }

    public void setAppraisalfeerule(String appraisalfeerule) {
        this.appraisalfeerule = appraisalfeerule;
    }

    public BigDecimal getAppraisalfeeamt() {
        return appraisalfeeamt;
    }

    public void setAppraisalfeeamt(BigDecimal appraisalfeeamt) {
        this.appraisalfeeamt = appraisalfeeamt;
    }

    public BigDecimal getAppraisalfeerate() {
        return appraisalfeerate;
    }

    public void setAppraisalfeerate(BigDecimal appraisalfeerate) {
        this.appraisalfeerate = appraisalfeerate;
    }

    public String getAppraisalfeecollection() {
        return appraisalfeecollection;
    }

    public void setAppraisalfeecollection(String appraisalfeecollection) {
        this.appraisalfeecollection = appraisalfeecollection;
    }

    public Boolean getPreterminationflag() {
        return preterminationflag;
    }

    public void setPreterminationflag(Boolean preterminationflag) {
        this.preterminationflag = preterminationflag;
    }

    public String getPreterminationrule() {
        return preterminationrule;
    }

    public void setPreterminationrule(String preterminationrule) {
        this.preterminationrule = preterminationrule;
    }

    public BigDecimal getPreterminationamt() {
        return preterminationamt;
    }

    public void setPreterminationamt(BigDecimal preterminationamt) {
        this.preterminationamt = preterminationamt;
    }

    public BigDecimal getPreterminationrate() {
        return preterminationrate;
    }

    public void setPreterminationrate(BigDecimal preterminationrate) {
        this.preterminationrate = preterminationrate;
    }

    public String getPreterminationcollection() {
        return preterminationcollection;
    }

    public void setPreterminationcollection(String preterminationcollection) {
        this.preterminationcollection = preterminationcollection;
    }

    public Boolean getPrepaymentfeeflag() {
        return prepaymentfeeflag;
    }

    public void setPrepaymentfeeflag(Boolean prepaymentfeeflag) {
        this.prepaymentfeeflag = prepaymentfeeflag;
    }

    public String getPrepaymentfeerule() {
        return prepaymentfeerule;
    }

    public void setPrepaymentfeerule(String prepaymentfeerule) {
        this.prepaymentfeerule = prepaymentfeerule;
    }

    public BigDecimal getPrepaymentfeeamt() {
        return prepaymentfeeamt;
    }

    public void setPrepaymentfeeamt(BigDecimal prepaymentfeeamt) {
        this.prepaymentfeeamt = prepaymentfeeamt;
    }

    public BigDecimal getPrepaymentfeerate() {
        return prepaymentfeerate;
    }

    public void setPrepaymentfeerate(BigDecimal prepaymentfeerate) {
        this.prepaymentfeerate = prepaymentfeerate;
    }

    public String getPrepaymentcollection() {
        return prepaymentcollection;
    }

    public void setPrepaymentcollection(String prepaymentcollection) {
        this.prepaymentcollection = prepaymentcollection;
    }

    public Boolean getCommitmentfeeflag() {
        return commitmentfeeflag;
    }

    public void setCommitmentfeeflag(Boolean commitmentfeeflag) {
        this.commitmentfeeflag = commitmentfeeflag;
    }

    public String getCommitmentfeerule() {
        return commitmentfeerule;
    }

    public void setCommitmentfeerule(String commitmentfeerule) {
        this.commitmentfeerule = commitmentfeerule;
    }

    public BigDecimal getCommitmentfeeamt() {
        return commitmentfeeamt;
    }

    public void setCommitmentfeeamt(BigDecimal commitmentfeeamt) {
        this.commitmentfeeamt = commitmentfeeamt;
    }

    public BigDecimal getCommitmentfeerate() {
        return commitmentfeerate;
    }

    public void setCommitmentfeerate(BigDecimal commitmentfeerate) {
        this.commitmentfeerate = commitmentfeerate;
    }

    public String getCommitmentfeecollection() {
        return commitmentfeecollection;
    }

    public void setCommitmentfeecollection(String commitmentfeecollection) {
        this.commitmentfeecollection = commitmentfeecollection;
    }

    public Boolean getPpdfeeflag() {
        return ppdfeeflag;
    }

    public void setPpdfeeflag(Boolean ppdfeeflag) {
        this.ppdfeeflag = ppdfeeflag;
    }

    public String getPpdfeerule() {
        return ppdfeerule;
    }

    public void setPpdfeerule(String ppdfeerule) {
        this.ppdfeerule = ppdfeerule;
    }

    public BigDecimal getPpdfeeamt() {
        return ppdfeeamt;
    }

    public void setPpdfeeamt(BigDecimal ppdfeeamt) {
        this.ppdfeeamt = ppdfeeamt;
    }

    public BigDecimal getPpdfeerate() {
        return ppdfeerate;
    }

    public void setPpdfeerate(BigDecimal ppdfeerate) {
        this.ppdfeerate = ppdfeerate;
    }

    public String getPpdfeecollection() {
        return ppdfeecollection;
    }

    public void setPpdfeecollection(String ppdfeecollection) {
        this.ppdfeecollection = ppdfeecollection;
    }

    public Boolean getVariableflag1() {
        return variableflag1;
    }

    public void setVariableflag1(Boolean variableflag1) {
        this.variableflag1 = variableflag1;
    }

    public String getVariablename1() {
        return variablename1;
    }

    public void setVariablename1(String variablename1) {
        this.variablename1 = variablename1;
    }

    public String getVariablerule1() {
        return variablerule1;
    }

    public void setVariablerule1(String variablerule1) {
        this.variablerule1 = variablerule1;
    }

    public BigDecimal getVariableamt1() {
        return variableamt1;
    }

    public void setVariableamt1(BigDecimal variableamt1) {
        this.variableamt1 = variableamt1;
    }

    public BigDecimal getVariablerate1() {
        return variablerate1;
    }

    public void setVariablerate1(BigDecimal variablerate1) {
        this.variablerate1 = variablerate1;
    }

    public String getVariablecollection1() {
        return variablecollection1;
    }

    public void setVariablecollection1(String variablecollection1) {
        this.variablecollection1 = variablecollection1;
    }

    public Boolean getVariableflag2() {
        return variableflag2;
    }

    public void setVariableflag2(Boolean variableflag2) {
        this.variableflag2 = variableflag2;
    }

    public String getVariablename2() {
        return variablename2;
    }

    public void setVariablename2(String variablename2) {
        this.variablename2 = variablename2;
    }

    public String getVariablerule2() {
        return variablerule2;
    }

    public void setVariablerule2(String variablerule2) {
        this.variablerule2 = variablerule2;
    }

    public BigDecimal getVariableamt2() {
        return variableamt2;
    }

    public void setVariableamt2(BigDecimal variableamt2) {
        this.variableamt2 = variableamt2;
    }

    public BigDecimal getVariablerate2() {
        return variablerate2;
    }

    public void setVariablerate2(BigDecimal variablerate2) {
        this.variablerate2 = variablerate2;
    }

    public String getVariablecollection2() {
        return variablecollection2;
    }

    public void setVariablecollection2(String variablecollection2) {
        this.variablecollection2 = variablecollection2;
    }

}
