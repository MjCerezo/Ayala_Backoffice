package com.etel.team;

import java.util.List;

import com.etel.team.forms.TeamForm;
import com.etel.team.forms.TeamMembersForm;
import com.etel.team.forms.UserrolesForm;
import com.coopdb.data.Tbteams;
import com.coopdb.data.Tbuser;

public interface TeamService {

	List<TeamForm> getListOfTeams();
	List<TeamForm> displayTeamValues(String teamcode);
//	String saveUpdateTeam(TeamForm form);
//	String updateTeam(TeamForm form);
//	String deleteTeam(TeamForm form);
//	List<TeamForm> searchTeam(String search);
	String addTeam(Tbteams team);
	Tbteams getValuesTeam(String teamcode, String teamname);
	List<UserrolesForm> getListofOfficer();
	String saveUpdateTeam(TeamForm form);
	List<TeamMembersForm> getListofEncoder(String teamcode);
	List<Tbuser> getListofEncoder1(String teamcode);
	List<TeamForm> searchAll(String search);
	String deleteTeam(TeamForm form);
	List<TeamForm> getListOfTeamsCIF();

}
