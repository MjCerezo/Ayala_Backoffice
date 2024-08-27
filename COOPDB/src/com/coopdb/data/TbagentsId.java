
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.TbagentsId
 *  08/27/2024 14:22:56
 * 
 */
public class TbagentsId
    implements Serializable
{

    private String companycode;
    private String agentcode;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbagentsId)) {
            return false;
        }
        TbagentsId other = ((TbagentsId) o);
        if (this.companycode == null) {
            if (other.companycode!= null) {
                return false;
            }
        } else {
            if (!this.companycode.equals(other.companycode)) {
                return false;
            }
        }
        if (this.agentcode == null) {
            if (other.agentcode!= null) {
                return false;
            }
        } else {
            if (!this.agentcode.equals(other.agentcode)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.companycode!= null) {
            rtn = (rtn + this.companycode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.agentcode!= null) {
            rtn = (rtn + this.agentcode.hashCode());
        }
        return rtn;
    }

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode;
    }

    public String getAgentcode() {
        return agentcode;
    }

    public void setAgentcode(String agentcode) {
        this.agentcode = agentcode;
    }

}
