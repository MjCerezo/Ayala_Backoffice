/**
 * 
 */
package com.etel.bureauinvestigation.forms;

import java.util.Date;

import com.coopdb.data.TbevalbiId;

/**
 * @author ETEL-LAPTOP19
 *
 */
public class BIEvalForm {
    private TbevalbiId id;
    private String appno;
    private String subjectname;
    private String bapbireportid;
    private String bapnfis;
    private String cmapbireportid;
    private String cmap;
    private String cicbireportid;
    private String cic;
    private String blacklistreportid;
    private String internalblacklist;
    private String externalblacklist;
    private String amlareportid;
    private String amlawatchlistedinternal;
    private String amlawatchlistedexternal;
    private Date datecreated;
    private String participationcode;
    private String instruction;
    private String overallremarks;
    private String cifno;
	
	public TbevalbiId getId() {
		return id;
	}
	public void setId(TbevalbiId id) {
		this.id = id;
	}
	public String getBlacklistreportid() {
		return blacklistreportid;
	}
	public void setBlacklistreportid(String blacklistreportid) {
		this.blacklistreportid = blacklistreportid;
	}
	public String getInternalblacklist() {
		return internalblacklist;
	}
	public void setInternalblacklist(String internalblacklist) {
		this.internalblacklist = internalblacklist;
	}
	public String getExternalblacklist() {
		return externalblacklist;
	}
	public void setExternalblacklist(String externalblacklist) {
		this.externalblacklist = externalblacklist;
	}
	public String getAmlareportid() {
		return amlareportid;
	}
	public void setAmlareportid(String amlareportid) {
		this.amlareportid = amlareportid;
	}
	public String getAmlawatchlistedinternal() {
		return amlawatchlistedinternal;
	}
	public void setAmlawatchlistedinternal(String amlawatchlistedinternal) {
		this.amlawatchlistedinternal = amlawatchlistedinternal;
	}
	public String getAmlawatchlistedexternal() {
		return amlawatchlistedexternal;
	}
	public void setAmlawatchlistedexternal(String amlawatchlistedexternal) {
		this.amlawatchlistedexternal = amlawatchlistedexternal;
	}
	public String getSubjectname() {
		return subjectname;
	}
	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
	}
	public String getParticipationcode() {
		return participationcode;
	}
	public void setParticipationcode(String participationcode) {
		this.participationcode = participationcode;
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public String getOverallremarks() {
		return overallremarks;
	}
	public void setOverallremarks(String overallremarks) {
		this.overallremarks = overallremarks;
	}
	public String getBapnfis() {
		return bapnfis;
	}
	public void setBapnfis(String bapnfis) {
		this.bapnfis = bapnfis;
	}
	public String getCmap() {
		return cmap;
	}
	public void setCmap(String cmap) {
		this.cmap = cmap;
	}
	public String getCic() {
		return cic;
	}
	public void setCic(String cic) {
		this.cic = cic;
	}
	public String getCifno() {
		return cifno;
	}
	public void setCifno(String cifno) {
		this.cifno = cifno;
	}
	public String getAppno() {
		return appno;
	}
	public void setAppno(String appno) {
		this.appno = appno;
	}
	public String getBapbireportid() {
		return bapbireportid;
	}
	public void setBapbireportid(String bapbireportid) {
		this.bapbireportid = bapbireportid;
	}
	public String getCmapbireportid() {
		return cmapbireportid;
	}
	public void setCmapbireportid(String cmapbireportid) {
		this.cmapbireportid = cmapbireportid;
	}
	public String getCicbireportid() {
		return cicbireportid;
	}
	public void setCicbireportid(String cicbireportid) {
		this.cicbireportid = cicbireportid;
	}
	public Date getDatecreated() {
		return datecreated;
	}
	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}
}
