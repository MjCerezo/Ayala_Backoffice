
package com.cifsdb.data;

import java.io.Serializable;


/**
 *  CIFSDB.TbworkflowprocessId
 *  09/26/2023 10:13:06
 * 
 */
public class TbworkflowprocessId
    implements Serializable
{

    private Integer processid;
    private Integer workflowid;
    private Integer sequenceno;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbworkflowprocessId)) {
            return false;
        }
        TbworkflowprocessId other = ((TbworkflowprocessId) o);
        if (this.processid == null) {
            if (other.processid!= null) {
                return false;
            }
        } else {
            if (!this.processid.equals(other.processid)) {
                return false;
            }
        }
        if (this.workflowid == null) {
            if (other.workflowid!= null) {
                return false;
            }
        } else {
            if (!this.workflowid.equals(other.workflowid)) {
                return false;
            }
        }
        if (this.sequenceno == null) {
            if (other.sequenceno!= null) {
                return false;
            }
        } else {
            if (!this.sequenceno.equals(other.sequenceno)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.processid!= null) {
            rtn = (rtn + this.processid.hashCode());
        }
        rtn = (rtn* 37);
        if (this.workflowid!= null) {
            rtn = (rtn + this.workflowid.hashCode());
        }
        rtn = (rtn* 37);
        if (this.sequenceno!= null) {
            rtn = (rtn + this.sequenceno.hashCode());
        }
        return rtn;
    }

    public Integer getProcessid() {
        return processid;
    }

    public void setProcessid(Integer processid) {
        this.processid = processid;
    }

    public Integer getWorkflowid() {
        return workflowid;
    }

    public void setWorkflowid(Integer workflowid) {
        this.workflowid = workflowid;
    }

    public Integer getSequenceno() {
        return sequenceno;
    }

    public void setSequenceno(Integer sequenceno) {
        this.sequenceno = sequenceno;
    }

}
