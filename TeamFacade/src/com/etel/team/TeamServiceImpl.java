package com.etel.team;

/**
 *@author Kevin 
 *
 * */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.email.EmailCode;
import com.etel.email.EmailFacade;
import com.etel.email.forms.EmailForm;
import com.etel.team.forms.TeamForm;
import com.etel.team.forms.TeamMembersForm;
import com.etel.team.forms.UserrolesForm;
import com.etel.utils.HQLUtil;
import com.coopdb.data.Tbteams;
import com.coopdb.data.TbteamsId;
import com.coopdb.data.Tbuser;
import com.coopdb.data.Tbuserroles;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;
public class TeamServiceImpl implements TeamService {

	SecurityService serviceS = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	/** Get List of Teams */
	@SuppressWarnings("unchecked")
	@Override
	public List<TeamForm> getListOfTeams() {
		List<TeamForm> form = new ArrayList<TeamForm>();
		DBService dbService = new DBServiceImpl();
		try {
			List<Tbteams> list = (List<Tbteams>) dbService.executeListHQLQuery("FROM Tbteams", null);
			if (list != null) {
				for (Tbteams team : list) {
					TeamForm teamForm = new TeamForm();
					teamForm.setTeamcode(team.getId().getTeamcode());
					teamForm.setTeamname(team.getId().getTeamname());
					teamForm.setBackupofficer(team.getBackupofficer());
					teamForm.setOfficer(team.getOfficer());
					teamForm.setIsofficeravailable(team.getIsofficeravailable());
					teamForm.setDatecreated(new Date());
					teamForm.setCreatedby(serviceS.getUserName());
					teamForm.setDateupdated(team.getDateupdated());
					teamForm.setUpdatedby(team.getUpdatedby());
					form.add(teamForm);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<TeamForm> getListOfTeamsCIF() {
		List<TeamForm> form = new ArrayList<TeamForm>();
		DBService dbService = new DBServiceImpl();
		try {
			form = (List<TeamForm>) dbService.execSQLQueryTransformer("SELECT teamcode, teamname FROM Tbteams", null, TeamForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}	
		
	/** Display Team Details */
	@SuppressWarnings("unchecked")
	@Override
	public List<TeamForm> displayTeamValues(String teamcode) {
		List<TeamForm> teamval = new ArrayList<TeamForm>();
		DBService dbService = new DBServiceImpl();
		try {
			List<Tbteams> list = (List<Tbteams>) dbService
					.executeListHQLQuery("FROM Tbteams", null);
			if (list != null) {
				for (Tbteams team : list) {
					TeamForm form = new TeamForm();
					form.setOfficer(team.getOfficer());
					form.setTeamcode(team.getId().getTeamcode());
					form.setTeamname(team.getId().getTeamname());
					form.setBackupofficer(team.getBackupofficer());
					form.setIsofficeravailable(team.getIsofficeravailable());
					form.setCreatedby(team.getCreatedby());
					form.setDatecreated(new Date());
					form.setDateupdated(new Date());
					form.setUpdatedby(team.getUpdatedby());
					form.setCompanycode(team.getCompanycode());
					form.setBranchcode(team.getBranchcode());
					form.setGroupcode(team.getGroupcode());
					teamval.add(form);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return teamval;

	}

	@Override
	public String addTeam(Tbteams team) {
		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		Tbteams list = new Tbteams();
		TbteamsId teamId = new TbteamsId();
		try {
			teamId.setTeamcode(team.getId().getTeamcode());
			teamId.setTeamname(team.getId().getTeamname());
			list.setId(teamId);
			list.setBranchcode(team.getBranchcode());
			list.setCompanycode(team.getCompanycode());
			list.setGroupcode(team.getGroupcode());
			list.setCreatedby(team.getCreatedby());
			list.setDatecreated(new Date());

			if (dbService.saveOrUpdate(list)) {
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}


	public Tbteams getValuesTeam(String teamcode, String teamname) {
		Tbteams val = new Tbteams();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("teamcode", teamcode);
		params.put("teamname", teamname);
		try {
			val =  (Tbteams) dbService.executeUniqueHQLQuery("FROM Tbteams a WHERE a.id.teamcode=:teamcode AND a.id.teamname=:teamname", params);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return val;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserrolesForm> getListofOfficer() {
		// TODO Auto-generated method stub
		DBService dbService = new DBServiceImpl();
		List<UserrolesForm> list = new ArrayList<UserrolesForm>();
		try {
			List<Tbuserroles> listofficer = (List<Tbuserroles>) dbService
					.executeListHQLQuery("FROM Tbuserroles WHERE rolename='CIF Officer' ", null);
			if (listofficer.isEmpty()) {
				System.out.println(">> EMPTY >>");
			} else {
				for (Tbuserroles a : listofficer) {
					UserrolesForm form = new UserrolesForm();
					form.setUsername(a.getId().getUsername());
					form.setAssignedby(a.getAssignedby());
					form.setAssigneddate(a.getAssigneddate());
					form.setRoleid(a.getId().getRoleid());
					form.setRolename(a.getRolename());
					list.add(form);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String saveUpdateTeam(TeamForm form) {
		// TODO Auto-generated method stub
		Map<String, Object> params = HQLUtil.getMap();
		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		Tbuser officer = new Tbuser();
		Tbuser backupofficer = new Tbuser();
		try {
			Tbteams team = new Tbteams();
			TbteamsId teamId = new TbteamsId();
			teamId.setTeamcode(form.getTeamcode());
			teamId.setTeamname(form.getTeamname());
			team.setId(teamId);
			team.setOfficer(form.getOfficer());
			team.setBackupofficer(form.getBackupofficer());
			team.setIsofficeravailable(form.getIsofficeravailable());
			team.setCreatedby(serviceS.getUserName());
			team.setDatecreated(new Date());
			team.setCompanycode(form.getCompanycode());
			team.setBranchcode(form.getBranchcode());
			team.setGroupcode(form.getGroupcode());
			team.setUpdatedby(serviceS.getUserName());
			List<Tbuser> userList = (List<Tbuser>)dbService.executeListHQLQuery("FROM Tbuser", null);
			if(form.getOfficer() != null){
				params.put("username", form.getOfficer());
				officer = (Tbuser) dbService.executeUniqueHQLQuery("FROM Tbuser WHERE username=:username", params);
				if(officer != null){
					//check user list if not empty and not null
					if(!userList.isEmpty() && userList != null){
						for(Tbuser u : userList){
							//check officer if already used.
							if(u.getUsername() == form.getOfficer()){
								//check team code
								if(u.getTeamcode().equals(form.getTeamcode())){
									flag = "failed";
								}else{
									officer.setTeamcode(form.getTeamcode());
									dbService.saveOrUpdate(officer);	
								}
							}else{
								//else add officer
								officer.setTeamcode(form.getTeamcode());
								dbService.saveOrUpdate(officer);
									flag = "success";
							}
						}
					}
				}
			}
			if(form.getBackupofficer() != null){
				params.put("username", form.getBackupofficer());
				backupofficer = (Tbuser) dbService.executeUniqueHQLQuery("FROM Tbuser WHERE username=:username", params);
				if(backupofficer!= null){
					if(!userList.isEmpty()&& userList!=null){
						for(Tbuser u: userList){
							if(u.getUsername()==form.getBackupofficer()){
								if(u.getTeamcode().equals(form.getTeamcode())){
									flag = "failed";
								}else{
									backupofficer.setTeamcode(form.getTeamcode());
									dbService.saveOrUpdate(backupofficer);
								}
							}else{
								backupofficer.setTeamcode(form.getTeamcode());
								dbService.saveOrUpdate(backupofficer);
								flag="success";
							}
						}
					}
					
				}
			}
			
			if (dbService.saveOrUpdate(team)) {
				flag = "success";
			}
			
			//Save Email to TBSMTP
			EmailFacade email = new EmailFacade();
			EmailForm emailform = new EmailForm();
			//emailform.setTeamcode(form.getTeamcode());
			//emailform.setEmailcode(EmailCode.OFFICER_OF_THE_DAY);
			//email.saveEmailSMTP(emailform);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	@SuppressWarnings("unchecked")
	public List<TeamMembersForm> getListofEncoder(String teamcode) {
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		List<TeamMembersForm> members = new ArrayList<TeamMembersForm>();
		
		try {

			List<Tbuserroles> usrroles = (List<Tbuserroles>) dbService
					.executeListHQLQuery("FROM Tbuserroles", null);
			for (Tbuserroles u : usrroles) {
				if (u.getId().getRoleid().equals("CIF_ENCODER")) {
					params.put("username", u.getId().getUsername());
					Tbuser usr = (Tbuser) dbService.executeUniqueHQLQuery("FROM Tbuser WHERE username=:username",
							params);
					if (usr != null) {
						if (usr.getTeamcode()!=null
							&& !usr.getTeamcode().equals("") 
							&& usr.getTeamcode().equals(teamcode)) {
							TeamMembersForm form = new TeamMembersForm();
							form.setFullname(usr.getLastname() + " " + usr.getFirstname());
							form.setRole(u.getId().getRoleid());
							form.setUsername(usr.getUsername());
							members.add(form);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return members;
	}
	
	@SuppressWarnings("unchecked")
	public List<Tbuser> getListofEncoder1(String teamcode) {
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("teamcode", teamcode);
		List<Tbuser> us = new ArrayList<Tbuser>();
		try {
			us = (List<Tbuser>) dbService.executeListHQLQuery("Select u.lastname, u.firstname from Tbuser u, Tbuserroles r WHERE r.id.username = u.username AND r.id.roleid='CIF_ENCODER' AND u.teamcode =:teamcode",
					params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return us;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TeamForm> searchAll(String search) {
		// TODO Auto-generated method stub
		List<TeamForm> teamlist = new ArrayList<TeamForm>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("search", "%" + search + "%");
		try {
			List<Tbteams> tlist = (List<Tbteams>) dbService.executeListHQLQuery(
					"FROM Tbteams a where a.id.teamcode LIKE :search or a.id.teamname LIKE :search or a.officer LIKE :search or a.backupofficer LIKE :search",
					params);
			for (Tbteams ct : tlist) {
				TeamForm form = new TeamForm();
				form.setTeamcode(ct.getId().getTeamcode());
				form.setTeamname(ct.getId().getTeamname());
				form.setOfficer(ct.getOfficer());
				form.setBackupofficer(ct.getBackupofficer());
				teamlist.add(form);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return teamlist;
	
	}

	@Override
	public String deleteTeam(TeamForm form) {
		// TODO Auto-generated method stub
		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("teamcode", form.getTeamcode());
		params.put("teamname", form.getTeamname());
		Tbteams tm = new Tbteams();
		try {
			tm = (Tbteams) dbService.executeUniqueHQLQuery("FROM Tbteams a WHERE a.id.teamcode=:teamcode AND a.id.teamname=teamname", params);
			if (dbService.delete(tm)) {
				flag = "successful";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;

	}





	
}
