package com.etel.team;

import java.util.List;

import com.etel.team.forms.TeamForm;
import com.etel.team.forms.TeamMembersForm;
import com.etel.team.forms.UserrolesForm;
import com.coopdb.data.Tbteams;
import com.coopdb.data.Tbuser;
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
public class TeamFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log
	 * level; recommend changing this to FATAL or ERROR before deploying. For
	 * info on these levels, look for tomcat/log4j documentation
	 */
	public TeamFacade() {
		super(INFO);
	}

	/***** TEAM NAME - Get List of Team FROM TBTEAMS *****/
	public List<TeamForm> getListOfTeams() {
		TeamService srvc = new TeamServiceImpl();
		return srvc.getListOfTeams();
	}
	public List<TeamForm> getListOfTeamsCIF() {
		TeamService srvc = new TeamServiceImpl();
		return srvc.getListOfTeamsCIF();
	}	

	/***** TEAM NAME - Display details of Team FROM TBTEAMS *****/
	public List<TeamForm> displayTeamValues(String teamcode) {
		TeamService srvc = new TeamServiceImpl();
		return srvc.displayTeamValues(teamcode);
	}

	/** TEAM - Add TEAM to TBTEAMS */
	public String addTeam(Tbteams team) {
		TeamService srvc = new TeamServiceImpl();
		return srvc.addTeam(team);
	}

	/** TEAM - Quick set-up to TBTEAMS */
	public Tbteams getValuesTeam(String teamcode, String teamname) {
		TeamService srvc = new TeamServiceImpl();
		return srvc.getValuesTeam(teamcode, teamname);
	}

	/** TEAM - Get List of CIF OFFICER FROM TBUSERROLES */
	public List<UserrolesForm> getListofOfficer() {
		TeamService srvc = new TeamServiceImpl();
		return srvc.getListofOfficer();
	}

	/** TEAM - Set-up Officer and Back-up Officer to TBTEAMS */
	public String saveUpdateTeam(TeamForm form) {
		TeamService srvc = new TeamServiceImpl();
		return srvc.saveUpdateTeam(form);
	}

	/** TEAM - List of Encoder FROM TBUSERROLES */
	public List<TeamMembersForm> getListofEncoder(String teamcode) {
		TeamService srvc = new TeamServiceImpl();
		return srvc.getListofEncoder(teamcode);
	}
	
	public List<Tbuser> getListofEncoder1(String teamcode) {
		TeamService srvc = new TeamServiceImpl();
		return srvc.getListofEncoder1(teamcode);
	}
	
	 /** TEAMNAME - Search All to TBTEAMS*/
    public List<TeamForm> searchAll(String search){
    	TeamService srvc = new TeamServiceImpl();
    	return srvc.searchAll (search);
    }
    
    /** Delete TEAM FROM TBTEAMS*/
	public String deleteTeam(TeamForm form) {
		TeamService srvc = new TeamServiceImpl();
		return srvc.deleteTeam(form);
	}

}