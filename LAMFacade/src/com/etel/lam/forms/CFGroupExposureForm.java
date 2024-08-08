package com.etel.lam.forms;

import java.math.BigDecimal;
import java.util.Date;

public class CFGroupExposureForm {

	private String maincifno;
	private String maincifname;
	private String relatedcifname;
	private String relatedcifno;
	private String facilityname;
	private BigDecimal creditline;
	private BigDecimal outstandingbalance;
	private String interestrate;
	private String term;
	private Date validity;
	private String status;

	public String getMaincifno() {
		return maincifno;
	}

	public void setMaincifno(String maincifno) {
		this.maincifno = maincifno;
	}

	public String getMaincifname() {
		return maincifname;
	}

	public void setMaincifname(String maincifname) {
		this.maincifname = maincifname;
	}

	public String getRelatedcifname() {
		return relatedcifname;
	}

	public void setRelatedcifname(String relatedcifname) {
		this.relatedcifname = relatedcifname;
	}

	public String getRelatedcifno() {
		return relatedcifno;
	}

	public void setRelatedcifno(String relatedcifno) {
		this.relatedcifno = relatedcifno;
	}

	public String getFacilityname() {
		return facilityname;
	}

	public void setFacilityname(String facilityname) {
		this.facilityname = facilityname;
	}

	public BigDecimal getCreditline() {
		return creditline;
	}

	public void setCreditline(BigDecimal creditline) {
		this.creditline = creditline;
	}

	public BigDecimal getOutstandingbalance() {
		return outstandingbalance;
	}

	public void setOutstandingbalance(BigDecimal outstandingbalance) {
		this.outstandingbalance = outstandingbalance;
	}

	public String getInterestrate() {
		return interestrate;
	}

	public void setInterestrate(String interestrate) {
		this.interestrate = interestrate;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Date getValidity() {
		return validity;
	}

	public void setValidity(Date validity) {
		this.validity = validity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
