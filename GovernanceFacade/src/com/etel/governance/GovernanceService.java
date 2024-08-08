package com.etel.governance;

import java.util.Date;
import java.util.List;

import com.coopdb.data.Tbeventschecklist;
import com.coopdb.data.Tbmembereventschecklist;

public interface GovernanceService {

	governancePojo getMember(String memberid);

	String updateMemberEventsChecklist(List<Tbeventschecklist> list);

	List<Tbeventschecklist> getMemberchecklist(String memberid);

	List<Tbeventschecklist> getEvents(String govertype);
	
	String saveMemberEvents(String[] eventcode, Boolean[] hasattended, String governancetype, String memberid);

	String updateMemberEvents(String[] eventcode, Boolean[] hasattended, String governancetype, String memberid);

	List<Tbeventschecklist> getMemberEventsforUpdates(String govertype, String memberid);

	String updateGovernancePerId(String membershipid, Integer id, Boolean hasattended, Date eventdate, Boolean required);

	String setUpGovernance(String membershipid, String governancetype);

	List<Tbmembereventschecklist> getMemberGovernance(String membershipid);

	String updateMemberGovernanceEvents(Tbmembereventschecklist memberevent);
}
