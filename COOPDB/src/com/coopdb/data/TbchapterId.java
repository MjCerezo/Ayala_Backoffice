
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.TbchapterId
 *  08/04/2024 12:54:43
 * 
 */
public class TbchapterId
    implements Serializable
{

    private String chaptercode;
    private String branchcode;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbchapterId)) {
            return false;
        }
        TbchapterId other = ((TbchapterId) o);
        if (this.chaptercode == null) {
            if (other.chaptercode!= null) {
                return false;
            }
        } else {
            if (!this.chaptercode.equals(other.chaptercode)) {
                return false;
            }
        }
        if (this.branchcode == null) {
            if (other.branchcode!= null) {
                return false;
            }
        } else {
            if (!this.branchcode.equals(other.branchcode)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.chaptercode!= null) {
            rtn = (rtn + this.chaptercode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.branchcode!= null) {
            rtn = (rtn + this.branchcode.hashCode());
        }
        return rtn;
    }

    public String getChaptercode() {
        return chaptercode;
    }

    public void setChaptercode(String chaptercode) {
        this.chaptercode = chaptercode;
    }

    public String getBranchcode() {
        return branchcode;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode;
    }

}
