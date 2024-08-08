
package com.loansdb.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 *  LOANSDB.TbfeesandchargesId
 *  08/31/2018 10:16:39
 * 
 */
public class TbfeesandchargesId
    implements Serializable
{

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

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbfeesandchargesId)) {
            return false;
        }
        TbfeesandchargesId other = ((TbfeesandchargesId) o);
        if (this.productcode == null) {
            if (other.productcode!= null) {
                return false;
            }
        } else {
            if (!this.productcode.equals(other.productcode)) {
                return false;
            }
        }
        if (this.dstflag == null) {
            if (other.dstflag!= null) {
                return false;
            }
        } else {
            if (!this.dstflag.equals(other.dstflag)) {
                return false;
            }
        }
        if (this.dstrate == null) {
            if (other.dstrate!= null) {
                return false;
            }
        } else {
            if (!this.dstrate.equals(other.dstrate)) {
                return false;
            }
        }
        if (this.dstrule == null) {
            if (other.dstrule!= null) {
                return false;
            }
        } else {
            if (!this.dstrule.equals(other.dstrule)) {
                return false;
            }
        }
        if (this.dstformuladiv == null) {
            if (other.dstformuladiv!= null) {
                return false;
            }
        } else {
            if (!this.dstformuladiv.equals(other.dstformuladiv)) {
                return false;
            }
        }
        if (this.dstformulamul == null) {
            if (other.dstformulamul!= null) {
                return false;
            }
        } else {
            if (!this.dstformulamul.equals(other.dstformulamul)) {
                return false;
            }
        }
        if (this.dstfixedamt == null) {
            if (other.dstfixedamt!= null) {
                return false;
            }
        } else {
            if (!this.dstfixedamt.equals(other.dstfixedamt)) {
                return false;
            }
        }
        if (this.dstamount1 == null) {
            if (other.dstamount1 != null) {
                return false;
            }
        } else {
            if (!this.dstamount1 .equals(other.dstamount1)) {
                return false;
            }
        }
        if (this.dstamount2 == null) {
            if (other.dstamount2 != null) {
                return false;
            }
        } else {
            if (!this.dstamount2 .equals(other.dstamount2)) {
                return false;
            }
        }
        if (this.dstcollection == null) {
            if (other.dstcollection!= null) {
                return false;
            }
        } else {
            if (!this.dstcollection.equals(other.dstcollection)) {
                return false;
            }
        }
        if (this.notarialflag == null) {
            if (other.notarialflag!= null) {
                return false;
            }
        } else {
            if (!this.notarialflag.equals(other.notarialflag)) {
                return false;
            }
        }
        if (this.notarialamt == null) {
            if (other.notarialamt!= null) {
                return false;
            }
        } else {
            if (!this.notarialamt.equals(other.notarialamt)) {
                return false;
            }
        }
        if (this.notarialamtperdoc == null) {
            if (other.notarialamtperdoc!= null) {
                return false;
            }
        } else {
            if (!this.notarialamtperdoc.equals(other.notarialamtperdoc)) {
                return false;
            }
        }
        if (this.notarialcollection == null) {
            if (other.notarialcollection!= null) {
                return false;
            }
        } else {
            if (!this.notarialcollection.equals(other.notarialcollection)) {
                return false;
            }
        }
        if (this.vatflag == null) {
            if (other.vatflag!= null) {
                return false;
            }
        } else {
            if (!this.vatflag.equals(other.vatflag)) {
                return false;
            }
        }
        if (this.vatrate == null) {
            if (other.vatrate!= null) {
                return false;
            }
        } else {
            if (!this.vatrate.equals(other.vatrate)) {
                return false;
            }
        }
        if (this.vatcollection == null) {
            if (other.vatcollection!= null) {
                return false;
            }
        } else {
            if (!this.vatcollection.equals(other.vatcollection)) {
                return false;
            }
        }
        if (this.grtflag == null) {
            if (other.grtflag!= null) {
                return false;
            }
        } else {
            if (!this.grtflag.equals(other.grtflag)) {
                return false;
            }
        }
        if (this.grtrate == null) {
            if (other.grtrate!= null) {
                return false;
            }
        } else {
            if (!this.grtrate.equals(other.grtrate)) {
                return false;
            }
        }
        if (this.grtcollection == null) {
            if (other.grtcollection!= null) {
                return false;
            }
        } else {
            if (!this.grtcollection.equals(other.grtcollection)) {
                return false;
            }
        }
        if (this.appfeeflag == null) {
            if (other.appfeeflag!= null) {
                return false;
            }
        } else {
            if (!this.appfeeflag.equals(other.appfeeflag)) {
                return false;
            }
        }
        if (this.appfeeamt == null) {
            if (other.appfeeamt!= null) {
                return false;
            }
        } else {
            if (!this.appfeeamt.equals(other.appfeeamt)) {
                return false;
            }
        }
        if (this.appfeerate == null) {
            if (other.appfeerate!= null) {
                return false;
            }
        } else {
            if (!this.appfeerate.equals(other.appfeerate)) {
                return false;
            }
        }
        if (this.appfeerule == null) {
            if (other.appfeerule!= null) {
                return false;
            }
        } else {
            if (!this.appfeerule.equals(other.appfeerule)) {
                return false;
            }
        }
        if (this.appfeecollection == null) {
            if (other.appfeecollection!= null) {
                return false;
            }
        } else {
            if (!this.appfeecollection.equals(other.appfeecollection)) {
                return false;
            }
        }
        if (this.servicefeeflag == null) {
            if (other.servicefeeflag!= null) {
                return false;
            }
        } else {
            if (!this.servicefeeflag.equals(other.servicefeeflag)) {
                return false;
            }
        }
        if (this.servicefeeamt == null) {
            if (other.servicefeeamt!= null) {
                return false;
            }
        } else {
            if (!this.servicefeeamt.equals(other.servicefeeamt)) {
                return false;
            }
        }
        if (this.servicefeerate == null) {
            if (other.servicefeerate!= null) {
                return false;
            }
        } else {
            if (!this.servicefeerate.equals(other.servicefeerate)) {
                return false;
            }
        }
        if (this.servicefeerule == null) {
            if (other.servicefeerule!= null) {
                return false;
            }
        } else {
            if (!this.servicefeerule.equals(other.servicefeerule)) {
                return false;
            }
        }
        if (this.servicefeecollection == null) {
            if (other.servicefeecollection!= null) {
                return false;
            }
        } else {
            if (!this.servicefeecollection.equals(other.servicefeecollection)) {
                return false;
            }
        }
        if (this.processingfeeflag == null) {
            if (other.processingfeeflag!= null) {
                return false;
            }
        } else {
            if (!this.processingfeeflag.equals(other.processingfeeflag)) {
                return false;
            }
        }
        if (this.processingfeerule == null) {
            if (other.processingfeerule!= null) {
                return false;
            }
        } else {
            if (!this.processingfeerule.equals(other.processingfeerule)) {
                return false;
            }
        }
        if (this.processingfeeamt == null) {
            if (other.processingfeeamt!= null) {
                return false;
            }
        } else {
            if (!this.processingfeeamt.equals(other.processingfeeamt)) {
                return false;
            }
        }
        if (this.processingfeerate == null) {
            if (other.processingfeerate!= null) {
                return false;
            }
        } else {
            if (!this.processingfeerate.equals(other.processingfeerate)) {
                return false;
            }
        }
        if (this.processingfeeoption == null) {
            if (other.processingfeeoption!= null) {
                return false;
            }
        } else {
            if (!this.processingfeeoption.equals(other.processingfeeoption)) {
                return false;
            }
        }
        if (this.processingfeeamt1 == null) {
            if (other.processingfeeamt1 != null) {
                return false;
            }
        } else {
            if (!this.processingfeeamt1 .equals(other.processingfeeamt1)) {
                return false;
            }
        }
        if (this.processingfeerate1 == null) {
            if (other.processingfeerate1 != null) {
                return false;
            }
        } else {
            if (!this.processingfeerate1 .equals(other.processingfeerate1)) {
                return false;
            }
        }
        if (this.processingfeeoption1 == null) {
            if (other.processingfeeoption1 != null) {
                return false;
            }
        } else {
            if (!this.processingfeeoption1 .equals(other.processingfeeoption1)) {
                return false;
            }
        }
        if (this.processingfeecollection == null) {
            if (other.processingfeecollection!= null) {
                return false;
            }
        } else {
            if (!this.processingfeecollection.equals(other.processingfeecollection)) {
                return false;
            }
        }
        if (this.fireinsuranceflag == null) {
            if (other.fireinsuranceflag!= null) {
                return false;
            }
        } else {
            if (!this.fireinsuranceflag.equals(other.fireinsuranceflag)) {
                return false;
            }
        }
        if (this.fireinsurancerule == null) {
            if (other.fireinsurancerule!= null) {
                return false;
            }
        } else {
            if (!this.fireinsurancerule.equals(other.fireinsurancerule)) {
                return false;
            }
        }
        if (this.fireinsuranceamt == null) {
            if (other.fireinsuranceamt!= null) {
                return false;
            }
        } else {
            if (!this.fireinsuranceamt.equals(other.fireinsuranceamt)) {
                return false;
            }
        }
        if (this.fireinsurancecollection == null) {
            if (other.fireinsurancecollection!= null) {
                return false;
            }
        } else {
            if (!this.fireinsurancecollection.equals(other.fireinsurancecollection)) {
                return false;
            }
        }
        if (this.marineinsuranceflag == null) {
            if (other.marineinsuranceflag!= null) {
                return false;
            }
        } else {
            if (!this.marineinsuranceflag.equals(other.marineinsuranceflag)) {
                return false;
            }
        }
        if (this.marineinsurancerule == null) {
            if (other.marineinsurancerule!= null) {
                return false;
            }
        } else {
            if (!this.marineinsurancerule.equals(other.marineinsurancerule)) {
                return false;
            }
        }
        if (this.marineinsuranceamt == null) {
            if (other.marineinsuranceamt!= null) {
                return false;
            }
        } else {
            if (!this.marineinsuranceamt.equals(other.marineinsuranceamt)) {
                return false;
            }
        }
        if (this.marineinsurancecollection == null) {
            if (other.marineinsurancecollection!= null) {
                return false;
            }
        } else {
            if (!this.marineinsurancecollection.equals(other.marineinsurancecollection)) {
                return false;
            }
        }
        if (this.comprehensiveflag == null) {
            if (other.comprehensiveflag!= null) {
                return false;
            }
        } else {
            if (!this.comprehensiveflag.equals(other.comprehensiveflag)) {
                return false;
            }
        }
        if (this.comprehensiverule == null) {
            if (other.comprehensiverule!= null) {
                return false;
            }
        } else {
            if (!this.comprehensiverule.equals(other.comprehensiverule)) {
                return false;
            }
        }
        if (this.comprehensiveamt == null) {
            if (other.comprehensiveamt!= null) {
                return false;
            }
        } else {
            if (!this.comprehensiveamt.equals(other.comprehensiveamt)) {
                return false;
            }
        }
        if (this.comprehensivecollection == null) {
            if (other.comprehensivecollection!= null) {
                return false;
            }
        } else {
            if (!this.comprehensivecollection.equals(other.comprehensivecollection)) {
                return false;
            }
        }
        if (this.creditlifeflag == null) {
            if (other.creditlifeflag!= null) {
                return false;
            }
        } else {
            if (!this.creditlifeflag.equals(other.creditlifeflag)) {
                return false;
            }
        }
        if (this.creditliferule == null) {
            if (other.creditliferule!= null) {
                return false;
            }
        } else {
            if (!this.creditliferule.equals(other.creditliferule)) {
                return false;
            }
        }
        if (this.creditlifeamt == null) {
            if (other.creditlifeamt!= null) {
                return false;
            }
        } else {
            if (!this.creditlifeamt.equals(other.creditlifeamt)) {
                return false;
            }
        }
        if (this.creditlifediv == null) {
            if (other.creditlifediv!= null) {
                return false;
            }
        } else {
            if (!this.creditlifediv.equals(other.creditlifediv)) {
                return false;
            }
        }
        if (this.creditlifemul == null) {
            if (other.creditlifemul!= null) {
                return false;
            }
        } else {
            if (!this.creditlifemul.equals(other.creditlifemul)) {
                return false;
            }
        }
        if (this.creditlifecollection == null) {
            if (other.creditlifecollection!= null) {
                return false;
            }
        } else {
            if (!this.creditlifecollection.equals(other.creditlifecollection)) {
                return false;
            }
        }
        if (this.agentcommflag == null) {
            if (other.agentcommflag!= null) {
                return false;
            }
        } else {
            if (!this.agentcommflag.equals(other.agentcommflag)) {
                return false;
            }
        }
        if (this.agentcommrule == null) {
            if (other.agentcommrule!= null) {
                return false;
            }
        } else {
            if (!this.agentcommrule.equals(other.agentcommrule)) {
                return false;
            }
        }
        if (this.agentcommamt == null) {
            if (other.agentcommamt!= null) {
                return false;
            }
        } else {
            if (!this.agentcommamt.equals(other.agentcommamt)) {
                return false;
            }
        }
        if (this.agentcommcollection == null) {
            if (other.agentcommcollection!= null) {
                return false;
            }
        } else {
            if (!this.agentcommcollection.equals(other.agentcommcollection)) {
                return false;
            }
        }
        if (this.dealercommflag == null) {
            if (other.dealercommflag!= null) {
                return false;
            }
        } else {
            if (!this.dealercommflag.equals(other.dealercommflag)) {
                return false;
            }
        }
        if (this.dealercommrule == null) {
            if (other.dealercommrule!= null) {
                return false;
            }
        } else {
            if (!this.dealercommrule.equals(other.dealercommrule)) {
                return false;
            }
        }
        if (this.dealercommamt == null) {
            if (other.dealercommamt!= null) {
                return false;
            }
        } else {
            if (!this.dealercommamt.equals(other.dealercommamt)) {
                return false;
            }
        }
        if (this.dealercommcollection == null) {
            if (other.dealercommcollection!= null) {
                return false;
            }
        } else {
            if (!this.dealercommcollection.equals(other.dealercommcollection)) {
                return false;
            }
        }
        if (this.advanceamortflag == null) {
            if (other.advanceamortflag!= null) {
                return false;
            }
        } else {
            if (!this.advanceamortflag.equals(other.advanceamortflag)) {
                return false;
            }
        }
        if (this.interestchargeflag == null) {
            if (other.interestchargeflag!= null) {
                return false;
            }
        } else {
            if (!this.interestchargeflag.equals(other.interestchargeflag)) {
                return false;
            }
        }
        if (this.interestchargecollection == null) {
            if (other.interestchargecollection!= null) {
                return false;
            }
        } else {
            if (!this.interestchargecollection.equals(other.interestchargecollection)) {
                return false;
            }
        }
        if (this.remflag == null) {
            if (other.remflag!= null) {
                return false;
            }
        } else {
            if (!this.remflag.equals(other.remflag)) {
                return false;
            }
        }
        if (this.remrule == null) {
            if (other.remrule!= null) {
                return false;
            }
        } else {
            if (!this.remrule.equals(other.remrule)) {
                return false;
            }
        }
        if (this.remamt == null) {
            if (other.remamt!= null) {
                return false;
            }
        } else {
            if (!this.remamt.equals(other.remamt)) {
                return false;
            }
        }
        if (this.remcollection == null) {
            if (other.remcollection!= null) {
                return false;
            }
        } else {
            if (!this.remcollection.equals(other.remcollection)) {
                return false;
            }
        }
        if (this.branchincentivefeeflag == null) {
            if (other.branchincentivefeeflag!= null) {
                return false;
            }
        } else {
            if (!this.branchincentivefeeflag.equals(other.branchincentivefeeflag)) {
                return false;
            }
        }
        if (this.branchincentivefeerule == null) {
            if (other.branchincentivefeerule!= null) {
                return false;
            }
        } else {
            if (!this.branchincentivefeerule.equals(other.branchincentivefeerule)) {
                return false;
            }
        }
        if (this.branchincentivefeeamt == null) {
            if (other.branchincentivefeeamt!= null) {
                return false;
            }
        } else {
            if (!this.branchincentivefeeamt.equals(other.branchincentivefeeamt)) {
                return false;
            }
        }
        if (this.branchincentivefeerate == null) {
            if (other.branchincentivefeerate!= null) {
                return false;
            }
        } else {
            if (!this.branchincentivefeerate.equals(other.branchincentivefeerate)) {
                return false;
            }
        }
        if (this.branchincentivefeecollection == null) {
            if (other.branchincentivefeecollection!= null) {
                return false;
            }
        } else {
            if (!this.branchincentivefeecollection.equals(other.branchincentivefeecollection)) {
                return false;
            }
        }
        if (this.updatedby == null) {
            if (other.updatedby!= null) {
                return false;
            }
        } else {
            if (!this.updatedby.equals(other.updatedby)) {
                return false;
            }
        }
        if (this.dateupdated == null) {
            if (other.dateupdated!= null) {
                return false;
            }
        } else {
            if (!this.dateupdated.equals(other.dateupdated)) {
                return false;
            }
        }
        if (this.atmtransfeeflag == null) {
            if (other.atmtransfeeflag!= null) {
                return false;
            }
        } else {
            if (!this.atmtransfeeflag.equals(other.atmtransfeeflag)) {
                return false;
            }
        }
        if (this.atmtransamount == null) {
            if (other.atmtransamount!= null) {
                return false;
            }
        } else {
            if (!this.atmtransamount.equals(other.atmtransamount)) {
                return false;
            }
        }
        if (this.atmtransfeecollection == null) {
            if (other.atmtransfeecollection!= null) {
                return false;
            }
        } else {
            if (!this.atmtransfeecollection.equals(other.atmtransfeecollection)) {
                return false;
            }
        }
        if (this.cashcardfeeflag == null) {
            if (other.cashcardfeeflag!= null) {
                return false;
            }
        } else {
            if (!this.cashcardfeeflag.equals(other.cashcardfeeflag)) {
                return false;
            }
        }
        if (this.cashcardamount == null) {
            if (other.cashcardamount!= null) {
                return false;
            }
        } else {
            if (!this.cashcardamount.equals(other.cashcardamount)) {
                return false;
            }
        }
        if (this.cashcardfeecollection == null) {
            if (other.cashcardfeecollection!= null) {
                return false;
            }
        } else {
            if (!this.cashcardfeecollection.equals(other.cashcardfeecollection)) {
                return false;
            }
        }
        if (this.depedincentiveflag == null) {
            if (other.depedincentiveflag!= null) {
                return false;
            }
        } else {
            if (!this.depedincentiveflag.equals(other.depedincentiveflag)) {
                return false;
            }
        }
        if (this.depedincentiverule == null) {
            if (other.depedincentiverule!= null) {
                return false;
            }
        } else {
            if (!this.depedincentiverule.equals(other.depedincentiverule)) {
                return false;
            }
        }
        if (this.depedincentiveamt == null) {
            if (other.depedincentiveamt!= null) {
                return false;
            }
        } else {
            if (!this.depedincentiveamt.equals(other.depedincentiveamt)) {
                return false;
            }
        }
        if (this.depedincentiverate == null) {
            if (other.depedincentiverate!= null) {
                return false;
            }
        } else {
            if (!this.depedincentiverate.equals(other.depedincentiverate)) {
                return false;
            }
        }
        if (this.depedincentivecollection == null) {
            if (other.depedincentivecollection!= null) {
                return false;
            }
        } else {
            if (!this.depedincentivecollection.equals(other.depedincentivecollection)) {
                return false;
            }
        }
        if (this.processingfeeamt2 == null) {
            if (other.processingfeeamt2 != null) {
                return false;
            }
        } else {
            if (!this.processingfeeamt2 .equals(other.processingfeeamt2)) {
                return false;
            }
        }
        if (this.processingfeerate2 == null) {
            if (other.processingfeerate2 != null) {
                return false;
            }
        } else {
            if (!this.processingfeerate2 .equals(other.processingfeerate2)) {
                return false;
            }
        }
        if (this.processingfeeoption2 == null) {
            if (other.processingfeeoption2 != null) {
                return false;
            }
        } else {
            if (!this.processingfeeoption2 .equals(other.processingfeeoption2)) {
                return false;
            }
        }
        if (this.mrifeeflag == null) {
            if (other.mrifeeflag!= null) {
                return false;
            }
        } else {
            if (!this.mrifeeflag.equals(other.mrifeeflag)) {
                return false;
            }
        }
        if (this.mrifeerule == null) {
            if (other.mrifeerule!= null) {
                return false;
            }
        } else {
            if (!this.mrifeerule.equals(other.mrifeerule)) {
                return false;
            }
        }
        if (this.mrifeeamt == null) {
            if (other.mrifeeamt!= null) {
                return false;
            }
        } else {
            if (!this.mrifeeamt.equals(other.mrifeeamt)) {
                return false;
            }
        }
        if (this.mrifeerate == null) {
            if (other.mrifeerate!= null) {
                return false;
            }
        } else {
            if (!this.mrifeerate.equals(other.mrifeerate)) {
                return false;
            }
        }
        if (this.mrifeecollection == null) {
            if (other.mrifeecollection!= null) {
                return false;
            }
        } else {
            if (!this.mrifeecollection.equals(other.mrifeecollection)) {
                return false;
            }
        }
        if (this.chattelfeeflag == null) {
            if (other.chattelfeeflag!= null) {
                return false;
            }
        } else {
            if (!this.chattelfeeflag.equals(other.chattelfeeflag)) {
                return false;
            }
        }
        if (this.chattelfeerule == null) {
            if (other.chattelfeerule!= null) {
                return false;
            }
        } else {
            if (!this.chattelfeerule.equals(other.chattelfeerule)) {
                return false;
            }
        }
        if (this.chattelfeeamt == null) {
            if (other.chattelfeeamt!= null) {
                return false;
            }
        } else {
            if (!this.chattelfeeamt.equals(other.chattelfeeamt)) {
                return false;
            }
        }
        if (this.chattelfeerate == null) {
            if (other.chattelfeerate!= null) {
                return false;
            }
        } else {
            if (!this.chattelfeerate.equals(other.chattelfeerate)) {
                return false;
            }
        }
        if (this.chattelfeecollection == null) {
            if (other.chattelfeecollection!= null) {
                return false;
            }
        } else {
            if (!this.chattelfeecollection.equals(other.chattelfeecollection)) {
                return false;
            }
        }
        if (this.appraisalfeeflag == null) {
            if (other.appraisalfeeflag!= null) {
                return false;
            }
        } else {
            if (!this.appraisalfeeflag.equals(other.appraisalfeeflag)) {
                return false;
            }
        }
        if (this.appraisalfeerule == null) {
            if (other.appraisalfeerule!= null) {
                return false;
            }
        } else {
            if (!this.appraisalfeerule.equals(other.appraisalfeerule)) {
                return false;
            }
        }
        if (this.appraisalfeeamt == null) {
            if (other.appraisalfeeamt!= null) {
                return false;
            }
        } else {
            if (!this.appraisalfeeamt.equals(other.appraisalfeeamt)) {
                return false;
            }
        }
        if (this.appraisalfeerate == null) {
            if (other.appraisalfeerate!= null) {
                return false;
            }
        } else {
            if (!this.appraisalfeerate.equals(other.appraisalfeerate)) {
                return false;
            }
        }
        if (this.appraisalfeecollection == null) {
            if (other.appraisalfeecollection!= null) {
                return false;
            }
        } else {
            if (!this.appraisalfeecollection.equals(other.appraisalfeecollection)) {
                return false;
            }
        }
        if (this.preterminationflag == null) {
            if (other.preterminationflag!= null) {
                return false;
            }
        } else {
            if (!this.preterminationflag.equals(other.preterminationflag)) {
                return false;
            }
        }
        if (this.preterminationrule == null) {
            if (other.preterminationrule!= null) {
                return false;
            }
        } else {
            if (!this.preterminationrule.equals(other.preterminationrule)) {
                return false;
            }
        }
        if (this.preterminationamt == null) {
            if (other.preterminationamt!= null) {
                return false;
            }
        } else {
            if (!this.preterminationamt.equals(other.preterminationamt)) {
                return false;
            }
        }
        if (this.preterminationrate == null) {
            if (other.preterminationrate!= null) {
                return false;
            }
        } else {
            if (!this.preterminationrate.equals(other.preterminationrate)) {
                return false;
            }
        }
        if (this.preterminationcollection == null) {
            if (other.preterminationcollection!= null) {
                return false;
            }
        } else {
            if (!this.preterminationcollection.equals(other.preterminationcollection)) {
                return false;
            }
        }
        if (this.prepaymentfeeflag == null) {
            if (other.prepaymentfeeflag!= null) {
                return false;
            }
        } else {
            if (!this.prepaymentfeeflag.equals(other.prepaymentfeeflag)) {
                return false;
            }
        }
        if (this.prepaymentfeerule == null) {
            if (other.prepaymentfeerule!= null) {
                return false;
            }
        } else {
            if (!this.prepaymentfeerule.equals(other.prepaymentfeerule)) {
                return false;
            }
        }
        if (this.prepaymentfeeamt == null) {
            if (other.prepaymentfeeamt!= null) {
                return false;
            }
        } else {
            if (!this.prepaymentfeeamt.equals(other.prepaymentfeeamt)) {
                return false;
            }
        }
        if (this.prepaymentfeerate == null) {
            if (other.prepaymentfeerate!= null) {
                return false;
            }
        } else {
            if (!this.prepaymentfeerate.equals(other.prepaymentfeerate)) {
                return false;
            }
        }
        if (this.prepaymentcollection == null) {
            if (other.prepaymentcollection!= null) {
                return false;
            }
        } else {
            if (!this.prepaymentcollection.equals(other.prepaymentcollection)) {
                return false;
            }
        }
        if (this.commitmentfeeflag == null) {
            if (other.commitmentfeeflag!= null) {
                return false;
            }
        } else {
            if (!this.commitmentfeeflag.equals(other.commitmentfeeflag)) {
                return false;
            }
        }
        if (this.commitmentfeerule == null) {
            if (other.commitmentfeerule!= null) {
                return false;
            }
        } else {
            if (!this.commitmentfeerule.equals(other.commitmentfeerule)) {
                return false;
            }
        }
        if (this.commitmentfeeamt == null) {
            if (other.commitmentfeeamt!= null) {
                return false;
            }
        } else {
            if (!this.commitmentfeeamt.equals(other.commitmentfeeamt)) {
                return false;
            }
        }
        if (this.commitmentfeerate == null) {
            if (other.commitmentfeerate!= null) {
                return false;
            }
        } else {
            if (!this.commitmentfeerate.equals(other.commitmentfeerate)) {
                return false;
            }
        }
        if (this.commitmentfeecollection == null) {
            if (other.commitmentfeecollection!= null) {
                return false;
            }
        } else {
            if (!this.commitmentfeecollection.equals(other.commitmentfeecollection)) {
                return false;
            }
        }
        if (this.ppdfeeflag == null) {
            if (other.ppdfeeflag!= null) {
                return false;
            }
        } else {
            if (!this.ppdfeeflag.equals(other.ppdfeeflag)) {
                return false;
            }
        }
        if (this.ppdfeerule == null) {
            if (other.ppdfeerule!= null) {
                return false;
            }
        } else {
            if (!this.ppdfeerule.equals(other.ppdfeerule)) {
                return false;
            }
        }
        if (this.ppdfeeamt == null) {
            if (other.ppdfeeamt!= null) {
                return false;
            }
        } else {
            if (!this.ppdfeeamt.equals(other.ppdfeeamt)) {
                return false;
            }
        }
        if (this.ppdfeerate == null) {
            if (other.ppdfeerate!= null) {
                return false;
            }
        } else {
            if (!this.ppdfeerate.equals(other.ppdfeerate)) {
                return false;
            }
        }
        if (this.ppdfeecollection == null) {
            if (other.ppdfeecollection!= null) {
                return false;
            }
        } else {
            if (!this.ppdfeecollection.equals(other.ppdfeecollection)) {
                return false;
            }
        }
        if (this.variableflag1 == null) {
            if (other.variableflag1 != null) {
                return false;
            }
        } else {
            if (!this.variableflag1 .equals(other.variableflag1)) {
                return false;
            }
        }
        if (this.variablename1 == null) {
            if (other.variablename1 != null) {
                return false;
            }
        } else {
            if (!this.variablename1 .equals(other.variablename1)) {
                return false;
            }
        }
        if (this.variablerule1 == null) {
            if (other.variablerule1 != null) {
                return false;
            }
        } else {
            if (!this.variablerule1 .equals(other.variablerule1)) {
                return false;
            }
        }
        if (this.variableamt1 == null) {
            if (other.variableamt1 != null) {
                return false;
            }
        } else {
            if (!this.variableamt1 .equals(other.variableamt1)) {
                return false;
            }
        }
        if (this.variablerate1 == null) {
            if (other.variablerate1 != null) {
                return false;
            }
        } else {
            if (!this.variablerate1 .equals(other.variablerate1)) {
                return false;
            }
        }
        if (this.variablecollection1 == null) {
            if (other.variablecollection1 != null) {
                return false;
            }
        } else {
            if (!this.variablecollection1 .equals(other.variablecollection1)) {
                return false;
            }
        }
        if (this.variableflag2 == null) {
            if (other.variableflag2 != null) {
                return false;
            }
        } else {
            if (!this.variableflag2 .equals(other.variableflag2)) {
                return false;
            }
        }
        if (this.variablename2 == null) {
            if (other.variablename2 != null) {
                return false;
            }
        } else {
            if (!this.variablename2 .equals(other.variablename2)) {
                return false;
            }
        }
        if (this.variablerule2 == null) {
            if (other.variablerule2 != null) {
                return false;
            }
        } else {
            if (!this.variablerule2 .equals(other.variablerule2)) {
                return false;
            }
        }
        if (this.variableamt2 == null) {
            if (other.variableamt2 != null) {
                return false;
            }
        } else {
            if (!this.variableamt2 .equals(other.variableamt2)) {
                return false;
            }
        }
        if (this.variablerate2 == null) {
            if (other.variablerate2 != null) {
                return false;
            }
        } else {
            if (!this.variablerate2 .equals(other.variablerate2)) {
                return false;
            }
        }
        if (this.variablecollection2 == null) {
            if (other.variablecollection2 != null) {
                return false;
            }
        } else {
            if (!this.variablecollection2 .equals(other.variablecollection2)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.productcode!= null) {
            rtn = (rtn + this.productcode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.dstflag!= null) {
            rtn = (rtn + this.dstflag.hashCode());
        }
        rtn = (rtn* 37);
        if (this.dstrate!= null) {
            rtn = (rtn + this.dstrate.hashCode());
        }
        rtn = (rtn* 37);
        if (this.dstrule!= null) {
            rtn = (rtn + this.dstrule.hashCode());
        }
        rtn = (rtn* 37);
        if (this.dstformuladiv!= null) {
            rtn = (rtn + this.dstformuladiv.hashCode());
        }
        rtn = (rtn* 37);
        if (this.dstformulamul!= null) {
            rtn = (rtn + this.dstformulamul.hashCode());
        }
        rtn = (rtn* 37);
        if (this.dstfixedamt!= null) {
            rtn = (rtn + this.dstfixedamt.hashCode());
        }
        rtn = (rtn* 37);
        if (this.dstamount1 != null) {
            rtn = (rtn + this.dstamount1 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.dstamount2 != null) {
            rtn = (rtn + this.dstamount2 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.dstcollection!= null) {
            rtn = (rtn + this.dstcollection.hashCode());
        }
        rtn = (rtn* 37);
        if (this.notarialflag!= null) {
            rtn = (rtn + this.notarialflag.hashCode());
        }
        rtn = (rtn* 37);
        if (this.notarialamt!= null) {
            rtn = (rtn + this.notarialamt.hashCode());
        }
        rtn = (rtn* 37);
        if (this.notarialamtperdoc!= null) {
            rtn = (rtn + this.notarialamtperdoc.hashCode());
        }
        rtn = (rtn* 37);
        if (this.notarialcollection!= null) {
            rtn = (rtn + this.notarialcollection.hashCode());
        }
        rtn = (rtn* 37);
        if (this.vatflag!= null) {
            rtn = (rtn + this.vatflag.hashCode());
        }
        rtn = (rtn* 37);
        if (this.vatrate!= null) {
            rtn = (rtn + this.vatrate.hashCode());
        }
        rtn = (rtn* 37);
        if (this.vatcollection!= null) {
            rtn = (rtn + this.vatcollection.hashCode());
        }
        rtn = (rtn* 37);
        if (this.grtflag!= null) {
            rtn = (rtn + this.grtflag.hashCode());
        }
        rtn = (rtn* 37);
        if (this.grtrate!= null) {
            rtn = (rtn + this.grtrate.hashCode());
        }
        rtn = (rtn* 37);
        if (this.grtcollection!= null) {
            rtn = (rtn + this.grtcollection.hashCode());
        }
        rtn = (rtn* 37);
        if (this.appfeeflag!= null) {
            rtn = (rtn + this.appfeeflag.hashCode());
        }
        rtn = (rtn* 37);
        if (this.appfeeamt!= null) {
            rtn = (rtn + this.appfeeamt.hashCode());
        }
        rtn = (rtn* 37);
        if (this.appfeerate!= null) {
            rtn = (rtn + this.appfeerate.hashCode());
        }
        rtn = (rtn* 37);
        if (this.appfeerule!= null) {
            rtn = (rtn + this.appfeerule.hashCode());
        }
        rtn = (rtn* 37);
        if (this.appfeecollection!= null) {
            rtn = (rtn + this.appfeecollection.hashCode());
        }
        rtn = (rtn* 37);
        if (this.servicefeeflag!= null) {
            rtn = (rtn + this.servicefeeflag.hashCode());
        }
        rtn = (rtn* 37);
        if (this.servicefeeamt!= null) {
            rtn = (rtn + this.servicefeeamt.hashCode());
        }
        rtn = (rtn* 37);
        if (this.servicefeerate!= null) {
            rtn = (rtn + this.servicefeerate.hashCode());
        }
        rtn = (rtn* 37);
        if (this.servicefeerule!= null) {
            rtn = (rtn + this.servicefeerule.hashCode());
        }
        rtn = (rtn* 37);
        if (this.servicefeecollection!= null) {
            rtn = (rtn + this.servicefeecollection.hashCode());
        }
        rtn = (rtn* 37);
        if (this.processingfeeflag!= null) {
            rtn = (rtn + this.processingfeeflag.hashCode());
        }
        rtn = (rtn* 37);
        if (this.processingfeerule!= null) {
            rtn = (rtn + this.processingfeerule.hashCode());
        }
        rtn = (rtn* 37);
        if (this.processingfeeamt!= null) {
            rtn = (rtn + this.processingfeeamt.hashCode());
        }
        rtn = (rtn* 37);
        if (this.processingfeerate!= null) {
            rtn = (rtn + this.processingfeerate.hashCode());
        }
        rtn = (rtn* 37);
        if (this.processingfeeoption!= null) {
            rtn = (rtn + this.processingfeeoption.hashCode());
        }
        rtn = (rtn* 37);
        if (this.processingfeeamt1 != null) {
            rtn = (rtn + this.processingfeeamt1 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.processingfeerate1 != null) {
            rtn = (rtn + this.processingfeerate1 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.processingfeeoption1 != null) {
            rtn = (rtn + this.processingfeeoption1 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.processingfeecollection!= null) {
            rtn = (rtn + this.processingfeecollection.hashCode());
        }
        rtn = (rtn* 37);
        if (this.fireinsuranceflag!= null) {
            rtn = (rtn + this.fireinsuranceflag.hashCode());
        }
        rtn = (rtn* 37);
        if (this.fireinsurancerule!= null) {
            rtn = (rtn + this.fireinsurancerule.hashCode());
        }
        rtn = (rtn* 37);
        if (this.fireinsuranceamt!= null) {
            rtn = (rtn + this.fireinsuranceamt.hashCode());
        }
        rtn = (rtn* 37);
        if (this.fireinsurancecollection!= null) {
            rtn = (rtn + this.fireinsurancecollection.hashCode());
        }
        rtn = (rtn* 37);
        if (this.marineinsuranceflag!= null) {
            rtn = (rtn + this.marineinsuranceflag.hashCode());
        }
        rtn = (rtn* 37);
        if (this.marineinsurancerule!= null) {
            rtn = (rtn + this.marineinsurancerule.hashCode());
        }
        rtn = (rtn* 37);
        if (this.marineinsuranceamt!= null) {
            rtn = (rtn + this.marineinsuranceamt.hashCode());
        }
        rtn = (rtn* 37);
        if (this.marineinsurancecollection!= null) {
            rtn = (rtn + this.marineinsurancecollection.hashCode());
        }
        rtn = (rtn* 37);
        if (this.comprehensiveflag!= null) {
            rtn = (rtn + this.comprehensiveflag.hashCode());
        }
        rtn = (rtn* 37);
        if (this.comprehensiverule!= null) {
            rtn = (rtn + this.comprehensiverule.hashCode());
        }
        rtn = (rtn* 37);
        if (this.comprehensiveamt!= null) {
            rtn = (rtn + this.comprehensiveamt.hashCode());
        }
        rtn = (rtn* 37);
        if (this.comprehensivecollection!= null) {
            rtn = (rtn + this.comprehensivecollection.hashCode());
        }
        rtn = (rtn* 37);
        if (this.creditlifeflag!= null) {
            rtn = (rtn + this.creditlifeflag.hashCode());
        }
        rtn = (rtn* 37);
        if (this.creditliferule!= null) {
            rtn = (rtn + this.creditliferule.hashCode());
        }
        rtn = (rtn* 37);
        if (this.creditlifeamt!= null) {
            rtn = (rtn + this.creditlifeamt.hashCode());
        }
        rtn = (rtn* 37);
        if (this.creditlifediv!= null) {
            rtn = (rtn + this.creditlifediv.hashCode());
        }
        rtn = (rtn* 37);
        if (this.creditlifemul!= null) {
            rtn = (rtn + this.creditlifemul.hashCode());
        }
        rtn = (rtn* 37);
        if (this.creditlifecollection!= null) {
            rtn = (rtn + this.creditlifecollection.hashCode());
        }
        rtn = (rtn* 37);
        if (this.agentcommflag!= null) {
            rtn = (rtn + this.agentcommflag.hashCode());
        }
        rtn = (rtn* 37);
        if (this.agentcommrule!= null) {
            rtn = (rtn + this.agentcommrule.hashCode());
        }
        rtn = (rtn* 37);
        if (this.agentcommamt!= null) {
            rtn = (rtn + this.agentcommamt.hashCode());
        }
        rtn = (rtn* 37);
        if (this.agentcommcollection!= null) {
            rtn = (rtn + this.agentcommcollection.hashCode());
        }
        rtn = (rtn* 37);
        if (this.dealercommflag!= null) {
            rtn = (rtn + this.dealercommflag.hashCode());
        }
        rtn = (rtn* 37);
        if (this.dealercommrule!= null) {
            rtn = (rtn + this.dealercommrule.hashCode());
        }
        rtn = (rtn* 37);
        if (this.dealercommamt!= null) {
            rtn = (rtn + this.dealercommamt.hashCode());
        }
        rtn = (rtn* 37);
        if (this.dealercommcollection!= null) {
            rtn = (rtn + this.dealercommcollection.hashCode());
        }
        rtn = (rtn* 37);
        if (this.advanceamortflag!= null) {
            rtn = (rtn + this.advanceamortflag.hashCode());
        }
        rtn = (rtn* 37);
        if (this.interestchargeflag!= null) {
            rtn = (rtn + this.interestchargeflag.hashCode());
        }
        rtn = (rtn* 37);
        if (this.interestchargecollection!= null) {
            rtn = (rtn + this.interestchargecollection.hashCode());
        }
        rtn = (rtn* 37);
        if (this.remflag!= null) {
            rtn = (rtn + this.remflag.hashCode());
        }
        rtn = (rtn* 37);
        if (this.remrule!= null) {
            rtn = (rtn + this.remrule.hashCode());
        }
        rtn = (rtn* 37);
        if (this.remamt!= null) {
            rtn = (rtn + this.remamt.hashCode());
        }
        rtn = (rtn* 37);
        if (this.remcollection!= null) {
            rtn = (rtn + this.remcollection.hashCode());
        }
        rtn = (rtn* 37);
        if (this.branchincentivefeeflag!= null) {
            rtn = (rtn + this.branchincentivefeeflag.hashCode());
        }
        rtn = (rtn* 37);
        if (this.branchincentivefeerule!= null) {
            rtn = (rtn + this.branchincentivefeerule.hashCode());
        }
        rtn = (rtn* 37);
        if (this.branchincentivefeeamt!= null) {
            rtn = (rtn + this.branchincentivefeeamt.hashCode());
        }
        rtn = (rtn* 37);
        if (this.branchincentivefeerate!= null) {
            rtn = (rtn + this.branchincentivefeerate.hashCode());
        }
        rtn = (rtn* 37);
        if (this.branchincentivefeecollection!= null) {
            rtn = (rtn + this.branchincentivefeecollection.hashCode());
        }
        rtn = (rtn* 37);
        if (this.updatedby!= null) {
            rtn = (rtn + this.updatedby.hashCode());
        }
        rtn = (rtn* 37);
        if (this.dateupdated!= null) {
            rtn = (rtn + this.dateupdated.hashCode());
        }
        rtn = (rtn* 37);
        if (this.atmtransfeeflag!= null) {
            rtn = (rtn + this.atmtransfeeflag.hashCode());
        }
        rtn = (rtn* 37);
        if (this.atmtransamount!= null) {
            rtn = (rtn + this.atmtransamount.hashCode());
        }
        rtn = (rtn* 37);
        if (this.atmtransfeecollection!= null) {
            rtn = (rtn + this.atmtransfeecollection.hashCode());
        }
        rtn = (rtn* 37);
        if (this.cashcardfeeflag!= null) {
            rtn = (rtn + this.cashcardfeeflag.hashCode());
        }
        rtn = (rtn* 37);
        if (this.cashcardamount!= null) {
            rtn = (rtn + this.cashcardamount.hashCode());
        }
        rtn = (rtn* 37);
        if (this.cashcardfeecollection!= null) {
            rtn = (rtn + this.cashcardfeecollection.hashCode());
        }
        rtn = (rtn* 37);
        if (this.depedincentiveflag!= null) {
            rtn = (rtn + this.depedincentiveflag.hashCode());
        }
        rtn = (rtn* 37);
        if (this.depedincentiverule!= null) {
            rtn = (rtn + this.depedincentiverule.hashCode());
        }
        rtn = (rtn* 37);
        if (this.depedincentiveamt!= null) {
            rtn = (rtn + this.depedincentiveamt.hashCode());
        }
        rtn = (rtn* 37);
        if (this.depedincentiverate!= null) {
            rtn = (rtn + this.depedincentiverate.hashCode());
        }
        rtn = (rtn* 37);
        if (this.depedincentivecollection!= null) {
            rtn = (rtn + this.depedincentivecollection.hashCode());
        }
        rtn = (rtn* 37);
        if (this.processingfeeamt2 != null) {
            rtn = (rtn + this.processingfeeamt2 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.processingfeerate2 != null) {
            rtn = (rtn + this.processingfeerate2 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.processingfeeoption2 != null) {
            rtn = (rtn + this.processingfeeoption2 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.mrifeeflag!= null) {
            rtn = (rtn + this.mrifeeflag.hashCode());
        }
        rtn = (rtn* 37);
        if (this.mrifeerule!= null) {
            rtn = (rtn + this.mrifeerule.hashCode());
        }
        rtn = (rtn* 37);
        if (this.mrifeeamt!= null) {
            rtn = (rtn + this.mrifeeamt.hashCode());
        }
        rtn = (rtn* 37);
        if (this.mrifeerate!= null) {
            rtn = (rtn + this.mrifeerate.hashCode());
        }
        rtn = (rtn* 37);
        if (this.mrifeecollection!= null) {
            rtn = (rtn + this.mrifeecollection.hashCode());
        }
        rtn = (rtn* 37);
        if (this.chattelfeeflag!= null) {
            rtn = (rtn + this.chattelfeeflag.hashCode());
        }
        rtn = (rtn* 37);
        if (this.chattelfeerule!= null) {
            rtn = (rtn + this.chattelfeerule.hashCode());
        }
        rtn = (rtn* 37);
        if (this.chattelfeeamt!= null) {
            rtn = (rtn + this.chattelfeeamt.hashCode());
        }
        rtn = (rtn* 37);
        if (this.chattelfeerate!= null) {
            rtn = (rtn + this.chattelfeerate.hashCode());
        }
        rtn = (rtn* 37);
        if (this.chattelfeecollection!= null) {
            rtn = (rtn + this.chattelfeecollection.hashCode());
        }
        rtn = (rtn* 37);
        if (this.appraisalfeeflag!= null) {
            rtn = (rtn + this.appraisalfeeflag.hashCode());
        }
        rtn = (rtn* 37);
        if (this.appraisalfeerule!= null) {
            rtn = (rtn + this.appraisalfeerule.hashCode());
        }
        rtn = (rtn* 37);
        if (this.appraisalfeeamt!= null) {
            rtn = (rtn + this.appraisalfeeamt.hashCode());
        }
        rtn = (rtn* 37);
        if (this.appraisalfeerate!= null) {
            rtn = (rtn + this.appraisalfeerate.hashCode());
        }
        rtn = (rtn* 37);
        if (this.appraisalfeecollection!= null) {
            rtn = (rtn + this.appraisalfeecollection.hashCode());
        }
        rtn = (rtn* 37);
        if (this.preterminationflag!= null) {
            rtn = (rtn + this.preterminationflag.hashCode());
        }
        rtn = (rtn* 37);
        if (this.preterminationrule!= null) {
            rtn = (rtn + this.preterminationrule.hashCode());
        }
        rtn = (rtn* 37);
        if (this.preterminationamt!= null) {
            rtn = (rtn + this.preterminationamt.hashCode());
        }
        rtn = (rtn* 37);
        if (this.preterminationrate!= null) {
            rtn = (rtn + this.preterminationrate.hashCode());
        }
        rtn = (rtn* 37);
        if (this.preterminationcollection!= null) {
            rtn = (rtn + this.preterminationcollection.hashCode());
        }
        rtn = (rtn* 37);
        if (this.prepaymentfeeflag!= null) {
            rtn = (rtn + this.prepaymentfeeflag.hashCode());
        }
        rtn = (rtn* 37);
        if (this.prepaymentfeerule!= null) {
            rtn = (rtn + this.prepaymentfeerule.hashCode());
        }
        rtn = (rtn* 37);
        if (this.prepaymentfeeamt!= null) {
            rtn = (rtn + this.prepaymentfeeamt.hashCode());
        }
        rtn = (rtn* 37);
        if (this.prepaymentfeerate!= null) {
            rtn = (rtn + this.prepaymentfeerate.hashCode());
        }
        rtn = (rtn* 37);
        if (this.prepaymentcollection!= null) {
            rtn = (rtn + this.prepaymentcollection.hashCode());
        }
        rtn = (rtn* 37);
        if (this.commitmentfeeflag!= null) {
            rtn = (rtn + this.commitmentfeeflag.hashCode());
        }
        rtn = (rtn* 37);
        if (this.commitmentfeerule!= null) {
            rtn = (rtn + this.commitmentfeerule.hashCode());
        }
        rtn = (rtn* 37);
        if (this.commitmentfeeamt!= null) {
            rtn = (rtn + this.commitmentfeeamt.hashCode());
        }
        rtn = (rtn* 37);
        if (this.commitmentfeerate!= null) {
            rtn = (rtn + this.commitmentfeerate.hashCode());
        }
        rtn = (rtn* 37);
        if (this.commitmentfeecollection!= null) {
            rtn = (rtn + this.commitmentfeecollection.hashCode());
        }
        rtn = (rtn* 37);
        if (this.ppdfeeflag!= null) {
            rtn = (rtn + this.ppdfeeflag.hashCode());
        }
        rtn = (rtn* 37);
        if (this.ppdfeerule!= null) {
            rtn = (rtn + this.ppdfeerule.hashCode());
        }
        rtn = (rtn* 37);
        if (this.ppdfeeamt!= null) {
            rtn = (rtn + this.ppdfeeamt.hashCode());
        }
        rtn = (rtn* 37);
        if (this.ppdfeerate!= null) {
            rtn = (rtn + this.ppdfeerate.hashCode());
        }
        rtn = (rtn* 37);
        if (this.ppdfeecollection!= null) {
            rtn = (rtn + this.ppdfeecollection.hashCode());
        }
        rtn = (rtn* 37);
        if (this.variableflag1 != null) {
            rtn = (rtn + this.variableflag1 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.variablename1 != null) {
            rtn = (rtn + this.variablename1 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.variablerule1 != null) {
            rtn = (rtn + this.variablerule1 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.variableamt1 != null) {
            rtn = (rtn + this.variableamt1 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.variablerate1 != null) {
            rtn = (rtn + this.variablerate1 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.variablecollection1 != null) {
            rtn = (rtn + this.variablecollection1 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.variableflag2 != null) {
            rtn = (rtn + this.variableflag2 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.variablename2 != null) {
            rtn = (rtn + this.variablename2 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.variablerule2 != null) {
            rtn = (rtn + this.variablerule2 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.variableamt2 != null) {
            rtn = (rtn + this.variableamt2 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.variablerate2 != null) {
            rtn = (rtn + this.variablerate2 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.variablecollection2 != null) {
            rtn = (rtn + this.variablecollection2 .hashCode());
        }
        return rtn;
    }

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
