package com.etel.group;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.company.CompanyServiceImpl;
import com.etel.group.forms.GroupForm;
import com.etel.utils.HQLUtil;
import com.coopdb.data.Tbgroup;
import com.coopdb.data.TbgroupId;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

/**
 * @author Kevin
 *
 */
public class GroupServiceImpl implements GroupService {
	SecurityService serviceS = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private static final Logger logger = Logger.getLogger(CompanyServiceImpl.class);

	/** Get List of Group */
	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public List<GroupForm> getListOfGroup(String groupname, String company, String coop, String branch) {
		Map<String, Object> params = HQLUtil.getMap();
		List<GroupForm> form = new ArrayList<GroupForm>();
		List<Tbgroup> list = new ArrayList<Tbgroup>();
		DBService dbService = new DBServiceImpl();
		StringBuilder str = new StringBuilder("FROM Tbgroup WHERE");
		params.put("branch", branch);
		params.put("coop", coop);
		params.put("company", company);
		try {
			if (groupname != null) {
				params.put("groupname", groupname == null ? "'%%'" : "%" + groupname + "%");
				list = (List<Tbgroup>) dbService.execSQLQueryTransformer(
						"SELECT groupcode, " + "branchcode, groupname, isapprovingcommittee, datecreated, createdby, "
								+ "dateupdated, updatedby, coopcode FROM Tbgroup WHERE groupname like :groupname",
						params, Tbgroup.class, 1);
			} else {
				if (company != null) {
					str.append(" companycode=:company AND");
				}
				if (coop != null) {
					str.append(" coopcode=:coop AND");
				}
				if (branch != null) {
					str.append(" branchcode=:branch AND");
				}
				list = (List<Tbgroup>) dbService
						.executeListHQLQuery(str.toString().substring(0, str.toString().length() - 4), params);
			}
			if (list != null) {
				for (Tbgroup group : list) {
					GroupForm grpForm = new GroupForm();
					
					grpForm.setCompanycode(group.getId().getCompanycode());
					grpForm.setBranchcode(group.getId().getBranchcode());
					grpForm.setGroupcode(group.getId().getGroupcode());
					grpForm.setGroupname(group.getGroupname());
					grpForm.setIsapprovingcommittee(group.getIsapprovingcommittee());
					grpForm.setDatecreated(group.getDatecreated());
					grpForm.setCreatedby(group.getCreatedby());
					grpForm.setDateupdated(group.getDateupdated());
					grpForm.setUpdatedby(group.getUpdatedby());
					grpForm.setCoopcode(group.getCoopcode());
					form.add(grpForm);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	/** Get List of Group by companycode and branchcode */
	@SuppressWarnings("unchecked")
	@Override
	public List<GroupForm> getListOfGroupByCompanyAndBranch(String companyCode, String branchCode) {
		// CHANGEDCOMPANYCODETOCOOPERATIVECODE
		List<GroupForm> form = new ArrayList<GroupForm>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("companyCode", companyCode == null ? "" : companyCode);
		params.put("branchCode", branchCode == null ? "" : branchCode);
		try {
//			List<Tbgroup> list = (List<Tbgroup>) dbService.executeListHQLQuery(
//					"FROM Tbgroup a WHERE a.id.coopcode=:companyCode AND a.id.branchcode=:branchCode", params);
			List<Tbgroup> list = (List<Tbgroup>) dbService.executeListHQLQuery(
					"FROM Tbgroup a WHERE a.coopcode=:companyCode AND a.id.branchcode=:branchCode", params);
			if (list != null) {
				for (Tbgroup group : list) {
					GroupForm grpForm = new GroupForm();
					grpForm.setCoopcode(group.getCoopcode());
					grpForm.setCompanycode(group.getId().getCompanycode());
					grpForm.setBranchcode(group.getId().getBranchcode());
					grpForm.setGroupcode(group.getId().getGroupcode());
					grpForm.setGroupname(group.getGroupname());
					grpForm.setIsapprovingcommittee(group.getIsapprovingcommittee());
					grpForm.setDatecreated(group.getDatecreated());
					grpForm.setCreatedby(group.getCreatedby());
					grpForm.setDateupdated(group.getDateupdated());
					grpForm.setUpdatedby(group.getUpdatedby());
					form.add(grpForm);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public String AddGroup(GroupForm form) {
		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		try {
			Tbgroup group = new Tbgroup();
			TbgroupId groupId = new TbgroupId();
			groupId.setCompanycode(form.getCompanycode());
			groupId.setBranchcode(form.getBranchcode());
			groupId.setGroupcode(form.getGroupcode());
			group.setId(groupId);
			group.setGroupname(form.getGroupname());
			group.setIsapprovingcommittee(form.getIsapprovingcommittee());
			group.setCreatedby(serviceS.getUserName());
			group.setDatecreated(new Date());
			group.setCoopcode(form.getCoopcode());
			if (dbService.save(group)) {
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR" + e.toString());
		}

		return flag;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GroupForm> displayGroupDetails(String groupname) {
		// TODO Auto-generated method stub
		List<GroupForm> group = new ArrayList<GroupForm>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();

		try {
			if (groupname != null) {
				params.put("groupname", groupname);
				List<Tbgroup> list = (List<Tbgroup>) dbService
						.executeListHQLQuery("FROM Tbgroup WHERE groupname=:groupname", params);
				if (list != null) {
					for (Tbgroup groupval : list) {
						GroupForm form = new GroupForm();
						form.setCompanycode(groupval.getId().getCompanycode());
						form.setBranchcode(groupval.getId().getBranchcode());
						form.setGroupcode(groupval.getId().getGroupcode());
						form.setGroupname(groupval.getGroupname());
						form.setIsapprovingcommittee(groupval.getIsapprovingcommittee());
						form.setCreatedby(groupval.getCreatedby());
						form.setDatecreated(groupval.getDatecreated());
						form.setCoopcode(groupval.getCoopcode());
						group.add(form);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR" + e.toString());
		}

		return group;
	}

	@Override
	public String updateGroup(GroupForm form) {
		// TODO Auto-generated method stub

		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		System.out.println(form.getGroupcode());
		params.put("groupcode", form.getGroupcode());
		try {
			Tbgroup group = (Tbgroup) dbService.executeUniqueHQLQuery("FROM Tbgroup WHERE groupcode=:groupcode",
					params);
			if (group != null) {
				group.setCoopcode(form.getCoopcode());
				TbgroupId id = new TbgroupId();
				id.setCompanycode(form.getCompanycode());
				id.setBranchcode(form.getBranchcode());
				id.setGroupcode(form.getGroupcode());
				group.setId(id);
				group.setIsapprovingcommittee(form.getIsapprovingcommittee());
				group.setCreatedby(form.getCreatedby());
				group.setDatecreated(form.getDatecreated());
				group.setDateupdated(new Date());
				group.setUpdatedby(serviceS.getUserName());
				group.setGroupname(form.getGroupname());
				if (dbService.saveOrUpdate(group)) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR" + e.toString());
		}
		return flag;
	}

	@Override
	public String deleteGroup(GroupForm form) {
		// TODO Auto-generated method stub
		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("groupcode", form.getGroupcode());
		params.put("companycode", form.getCoopcode());
		params.put("branchcode", form.getBranchcode());
		Tbgroup group = new Tbgroup();
		try {
			group = (Tbgroup) dbService.executeUniqueHQLQuery("FROM Tbgroup WHERE groupcode=:groupcode AND companycode=:companycode AND branchcode=:branchcode ", params);
			if (dbService.delete(group)) {
				flag = "successful";
			}
			System.out.print("MAR : " + group);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;

	}

}
