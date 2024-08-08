package com.etel.dashboard.forms;

public class MembershipOtherBucket {
	private Integer approved = 0;
	private Integer declined = 0;
	private Integer deferred = 0;
	
	public Integer getApproved() {
		return approved;
	}
	public Integer getDeclined() {
		return declined;
	}
	public Integer getDeferred() {
		return deferred;
	}
	public void setApproved(Integer approved) {
		this.approved = approved;
	}
	public void setDeclined(Integer declined) {
		this.declined = declined;
	}
	public void setDeferred(Integer deferred) {
		this.deferred = deferred;
	}
	
	
}
