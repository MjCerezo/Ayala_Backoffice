package member.forms;

import java.util.Date;

public class ChangeProfileForm {
	
	String memberid;
	String membername;
	String changecategorytype;
	String changefieldtype;
	String updatedby;
	Date dateupdated;
	Integer updatecount;
	String updateremarks;
	String oldvalue;
	String newvalue;
	
	public String getMemberid() {
		return memberid;
	}
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	public String getMembername() {
		return membername;
	}
	public void setMembername(String membername) {
		this.membername = membername;
	}
	public String getChangecategorytype() {
		return changecategorytype;
	}
	public void setChangecategorytype(String changecategorytype) {
		this.changecategorytype = changecategorytype;
	}
	public String getChangefieldtype() {
		return changefieldtype;
	}
	public void setChangefieldtype(String changefieldtype) {
		this.changefieldtype = changefieldtype;
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
	public Integer getUpdatecount() {
		return updatecount;
	}
	public void setUpdatecount(Integer updatecount) {
		this.updatecount = updatecount;
	}
	public String getUpdateremarks() {
		return updateremarks;
	}
	public void setUpdateremarks(String updateremarks) {
		this.updateremarks = updateremarks;
	}
	public String getOldvalue() {
		return oldvalue;
	}
	public void setOldvalue(String oldvalue) {
		this.oldvalue = oldvalue;
	}
	public String getNewvalue() {
		return newvalue;
	}
	public void setNewvalue(String newvalue) {
		this.newvalue = newvalue;
	}
	
	
	
}
