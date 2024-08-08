package com.etel.group;

import java.util.List;

import com.etel.group.forms.GroupForm;
import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
import com.wavemaker.runtime.service.annotations.ExposeToClient;

/**
 * This is a client-facing service class. All public methods will be exposed to
 * the client. Their return values and parameters will be passed to the client
 * or taken from the client, respectively. This will be a singleton instance,
 * shared between all requests.
 * 
 * To log, call the superclass method log(LOG_LEVEL, String) or log(LOG_LEVEL,
 * String, Exception). LOG_LEVEL is one of FATAL, ERROR, WARN, INFO and DEBUG to
 * modify your log level. For info on these levels, look for tomcat/log4j
 * documentation
 */
@ExposeToClient
public class GroupFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log
	 * level; recommend changing this to FATAL or ERROR before deploying. For
	 * info on these levels, look for tomcat/log4j documentation
	 */
	public GroupFacade() {
		super(INFO);
	}

	/** Get List of Group */
	public List<GroupForm> getListOfGroup(String groupname, String company, String coop, String branch) {
		GroupService srvc = new GroupServiceImpl();
		return srvc.getListOfGroup(groupname, company, coop, branch);
	}

	/** Get List of Group by companycode and branchcode */
	public List<GroupForm> getListOfGroupByCompanyAndBranch(String companyCode, String branchCode) {
		GroupService srvc = new GroupServiceImpl();
		return srvc.getListOfGroupByCompanyAndBranch(companyCode, branchCode);
	}

	/** GROUP- Save and Update TBGROUP */
	public String AddGroup(GroupForm form) {
		GroupService srvc = new GroupServiceImpl();
		return srvc.AddGroup(form);
	}

	/** Display Group values from TBGROUP */
	public List<GroupForm> displayGroupDetails(String groupname) {
		GroupService srvc = new GroupServiceImpl();
		return srvc.displayGroupDetails(groupname);
	}

	/** Update GROUP to TBGROUP */
	public String updateGroup(GroupForm form) {
		GroupService srvc = new GroupServiceImpl();
		return srvc.updateGroup(form);
	}
	/** Delete Group to TBGROUP */
	public String deleteGroup(GroupForm form) {
		GroupService srvc = new GroupServiceImpl();
		return srvc.deleteGroup(form);
	}
	
	
}
