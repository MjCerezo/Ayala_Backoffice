package com.etel.collateralforms;

import java.util.Date;

public class SingleAndGroupCollateralForm {
	private Integer collateralid; 
	private String referenceno; 
	private String collateraltype; 
	private Date dateoflastappraisal;
	private String appraisalstatus; 
	private String collateralstatus;
	private String neworused;
	private String propertytype;
	public Integer getCollateralid() {
		return collateralid;
	}
	public String getReferenceno() {
		return referenceno;
	}
	public String getCollateraltype() {
		return collateraltype;
	}
	public Date getDateoflastappraisal() {
		return dateoflastappraisal;
	}
	public String getAppraisalstatus() {
		return appraisalstatus;
	}
	public String getCollateralstatus() {
		return collateralstatus;
	}
	public String getNeworused() {
		return neworused;
	}
	public String getPropertytype() {
		return propertytype;
	}
	public void setCollateralid(Integer collateralid) {
		this.collateralid = collateralid;
	}
	public void setReferenceno(String referenceno) {
		this.referenceno = referenceno;
	}
	public void setCollateraltype(String collateraltype) {
		this.collateraltype = collateraltype;
	}
	public void setDateoflastappraisal(Date dateoflastappraisal) {
		this.dateoflastappraisal = dateoflastappraisal;
	}
	public void setAppraisalstatus(String appraisalstatus) {
		this.appraisalstatus = appraisalstatus;
	}
	public void setCollateralstatus(String collateralstatus) {
		this.collateralstatus = collateralstatus;
	}
	public void setNeworused(String neworused) {
		this.neworused = neworused;
	}
	public void setPropertytype(String propertytype) {
		this.propertytype = propertytype;
	}
	

}
