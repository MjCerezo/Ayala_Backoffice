package com.user.maintenance.form;

import java.math.BigDecimal;
import java.util.Date;

public class UserEditForm {

	private int id;
	private String firstname;
	private String middlename;
	private String lastname;
	private String email;
	private BigDecimal tellerslimit;
	private boolean passneverexp;
	private boolean suspended;
	private boolean active;
	private boolean locked;
	private String updatedby;
	private Date dtupdated;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getMiddlename() {
		return middlename;
	}
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public BigDecimal getTellerslimit() {
		return tellerslimit;
	}
	public void setTellerslimit(BigDecimal tellerslimit) {
		this.tellerslimit = tellerslimit;
	}
	public boolean isPassneverexp() {
		return passneverexp;
	}
	public void setPassneverexp(boolean passneverexp) {
		this.passneverexp = passneverexp;
	}
	public boolean isSuspended() {
		return suspended;
	}
	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	public String getUpdatedby() {
		return updatedby;
	}
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}
	public Date getDtupdated() {
		return dtupdated;
	}
	public void setDtupdated(Date dtupdated) {
		this.dtupdated = dtupdated;
	}
	
}
