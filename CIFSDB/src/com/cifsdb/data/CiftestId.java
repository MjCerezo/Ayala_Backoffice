
package com.cifsdb.data;

import java.io.Serializable;


/**
 *  CIFSDB.CiftestId
 *  12/04/2022 22:33:40
 * 
 */
public class CiftestId
    implements Serializable
{

    private String cifno;
    private String name;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CiftestId)) {
            return false;
        }
        CiftestId other = ((CiftestId) o);
        if (this.cifno == null) {
            if (other.cifno!= null) {
                return false;
            }
        } else {
            if (!this.cifno.equals(other.cifno)) {
                return false;
            }
        }
        if (this.name == null) {
            if (other.name!= null) {
                return false;
            }
        } else {
            if (!this.name.equals(other.name)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.cifno!= null) {
            rtn = (rtn + this.cifno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.name!= null) {
            rtn = (rtn + this.name.hashCode());
        }
        return rtn;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
