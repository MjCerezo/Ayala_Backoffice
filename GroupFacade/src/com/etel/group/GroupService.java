package com.etel.group;

import java.util.List;

import com.etel.group.forms.GroupForm;

public interface GroupService {

	List<GroupForm> getListOfGroupByCompanyAndBranch(String companyCode, String branchCode);

	List<GroupForm> getListOfGroup(String groupname, String company, String coop, String branch);
	String AddGroup(GroupForm form);
	List<GroupForm> displayGroupDetails(String groupname);
	String updateGroup(GroupForm form);
	String deleteGroup(GroupForm form);


}
