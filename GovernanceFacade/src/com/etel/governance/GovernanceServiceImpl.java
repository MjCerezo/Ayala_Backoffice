package com.etel.governance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.cloudfoundry.org.codehaus.jackson.map.DeserializationConfig;
import org.cloudfoundry.org.codehaus.jackson.map.ObjectMapper;

import com.coopdb.data.Tbevents;
import com.coopdb.data.Tbeventschecklist;
import com.coopdb.data.Tbmember;
import com.coopdb.data.Tbmembereventschecklist;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

/**
 * @author Daniel
 */

public class GovernanceServiceImpl implements GovernanceService {

	private DBService dbService = new DBServiceImpl();
	private Map<String, Object> params = HQLUtil.getMap();
	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");

	@SuppressWarnings("null")
	@Override
	public governancePojo getMember(String memberid) {
		governancePojo gov = new governancePojo();
		try {
			params.put("memberid", memberid);
			Tbmember mem = (Tbmember) dbService.execSQLQueryTransformer(
					"SELECT governancetype, membername, membershipclass, membershipid, membershipstatus, membershipdate, chaptercode FROM Tbmember WHERE membershipid=:memberid",
					params, Tbmember.class, 0);
			if (mem != null) {
				params.put("id", mem.getMembershipid());
				@SuppressWarnings("unchecked")
				List<Tbmembereventschecklist> events = (List<Tbmembereventschecklist>) dbService
						.executeListHQLQuery("FROM Tbmembereventschecklist WHERE membershipid=:id", params);
				if (events != null || events.size() != 0) {
					gov.setMembergovernancechecklist(events);
				} else {
					gov.setMembergovernancechecklist(null);
				}
				gov.setGovernancetype(mem.getGovernancetype());
				gov.setChapter(mem.getChaptercode());
				gov.setMembername(mem.getMembername());
				gov.setMembershipclass(mem.getMembershipclass());
				gov.setMembershipdate(mem.getMembershipdate());
				gov.setMembershipstatus(mem.getMembershipstatus());
				gov.setMembershipid(mem.getMembershipid());
				return gov;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String updateMemberEventsChecklist(List<Tbeventschecklist> list) {
		// TODO Auto-generated method stub
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public List<Tbeventschecklist> getMemberchecklist(String memberid) {
		// TODO Auto-generated method stub
		try {
			params.put("id", memberid);
			List<Tbeventschecklist> events = (List<Tbeventschecklist>) dbService
					.executeListHQLQuery("FROM Tbeventschecklist WHERE membershipid=:id", params);
			if (events != null || events.size() != 0) {
				return events;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Tbeventschecklist> getEvents(String govertype) {
		// TODO Auto-generated method stub
		List<Tbeventschecklist> tb = new ArrayList<Tbeventschecklist>();
		try {
			params.put("govetype", govertype);
			@SuppressWarnings({ "unchecked" })
			List<Tbevents> get = (List<Tbevents>) dbService
					.executeListHQLQuery("FROM Tbevents WHERE govtypeclassification=:govetype", params);
			for (Tbevents f : get) {
				Tbeventschecklist n = new Tbeventschecklist();
				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				String value = mapper.writeValueAsString(f);
				n = mapper.readValue(value, Tbeventschecklist.class);
				tb.add(n);
			}
			return tb;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String saveMemberEvents(String[] eventcode, Boolean[] hasattended, String governancetype, String memberid) {
		try {// parameter eventcode = eventname in the database
			params.put("memberid", memberid);
			params.put("type", governancetype);
			List<Tbmembereventschecklist> list = new ArrayList<Tbmembereventschecklist>();
			@SuppressWarnings("unchecked")
			List<Tbevents> events = (List<Tbevents>) dbService
					.executeListHQLQuery("FROM Tbevents WHERE govtypeclassification=:type", params);
			Tbmember mem = (Tbmember) dbService.executeUniqueHQLQuery("FROM Tbmember WHERE membershipid=:memberid",
					params);
			if (mem != null) {
				mem.setGovernancetype(governancetype);
			}
			if (eventcode.length == hasattended.length) {
				for (int i = 0; i < hasattended.length; i++) {
					Tbmembereventschecklist eve = new Tbmembereventschecklist();
					eve.setMembershipid(memberid);
					eve.setEventname(eventcode[i]);
					for (Tbevents check : events) {
						if (check.getEventname().equals(eventcode[i])) {// get
																		// the
																		// events
																		// details
																		// from
																		// tbevents
							eve.setEventdate(check.getEventdate());
							eve.setEventcode(check.getId().getEventcode());
							eve.setEventtype(check.getEventtype());
							eve.setIsrequired(check.getIsrequired());
							eve.setGovernancetype(check.getGovtypeclassification());
						}
					}
					if (hasattended[i] == null) {
						hasattended[i] = false;
					}
					eve.setHasattended(hasattended[i]);
					list.add(eve);
				}
				for (Tbmembereventschecklist member : list) {
					dbService.saveOrUpdate(member);
				}
				dbService.saveOrUpdate(mem);
				return "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public String updateMemberEvents(String[] eventcode, Boolean[] hasattended, String governancetype,
			String memberid) {
		try {// parameter eventcode = eventname in the database
			params.put("memberid", memberid);
			@SuppressWarnings("unchecked")
			List<Tbmembereventschecklist> events = (List<Tbmembereventschecklist>) dbService
					.executeListHQLQuery("FROM Tbmembereventschecklist WHERE membershipid=:memberid", params);
			if (events != null) {
				if (eventcode.length == hasattended.length) {
					for (int i = 0; i < hasattended.length; i++) {
						for (Tbmembereventschecklist check : events) {
							if (check.getEventname().equals(eventcode[i])) {
								if (hasattended[i] == null || hasattended[i] == false) {
									check.setHasattended(false);
								}
								if (hasattended[i] == true) {
									check.setHasattended(true);
								}
								dbService.saveOrUpdate(check);
							}
						}
					}
					return "success";
				}
			}
			if (events == null) {
				return memberid + " not found";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public List<Tbeventschecklist> getMemberEventsforUpdates(String govertype, String memberid) {

		List<Tbeventschecklist> tb = new ArrayList<Tbeventschecklist>();
		try {
			params.put("memberid", memberid);
			@SuppressWarnings({ "unchecked" })
			List<Tbmembereventschecklist> memberevents = (List<Tbmembereventschecklist>) dbService
					.executeListHQLQuery("FROM Tbmembereventschecklist WHERE membershipid=:memberid", params);
			Tbmember mem = (Tbmember) dbService.execSQLQueryTransformer(
					"SELECT governancetype FROM Tbmember WHERE membershipid=:memberid", params, Tbmember.class, 0);
			params.put("govetype", mem.getGovernancetype());
			@SuppressWarnings("unchecked")
			List<Tbevents> get = (List<Tbevents>) dbService
					.executeListHQLQuery("FROM Tbevents WHERE govtypeclassification=:govetype", params);
			for (Tbevents f : get) {
				Tbeventschecklist n = new Tbeventschecklist();
				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				String value = mapper.writeValueAsString(f);
				n = mapper.readValue(value, Tbeventschecklist.class);
				for (Tbmembereventschecklist c : memberevents) {
					if (f.getEventname().equals(c.getEventname())) {
						n.setHasattended(c.getHasattended());
					}
				}
				tb.add(n);
			}
			return tb;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String updateGovernancePerId(String membershipid, Integer id, Boolean hasattended, Date eventdate,
			Boolean required) {
		// TODO Auto-generated method stub
		try {
			params.put("membershipid", membershipid);
			params.put("id", id);
			Tbmembereventschecklist e = (Tbmembereventschecklist) dbService.executeUniqueHQLQuery(
					"FROM Tbmembereventschecklist WHERE membershipid=:membershipid AND id=:id", params);
			if (e != null) {
				e.setHasattended(hasattended);
				e.setIsrequired(required);
				if (dbService.saveOrUpdate(e))
					return "Member events update.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@SuppressWarnings("unchecked")
	@Override
	public String setUpGovernance(String membershipid, String governancetype) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			params.put("governancetype", governancetype);
			params.put("membershipid", membershipid);
			if (governancetype != null) {
				List<Tbevents> events = (List<Tbevents>) dbService
						.executeListHQLQuery("FROM Tbevents WHERE govtypeclassification=:governancetype", params);
				if (events.size() > 0 || events != null) {
					for (Tbevents e : events) {
						Tbmembereventschecklist m = new Tbmembereventschecklist();
						m.setMembershipid(membershipid);
						m.setEventcode(e.getId().getEventcode());
						m.setEventdate(e.getEventdate());
						m.setEventname(e.getEventname());
						m.setEventtype(e.getEventtype());
						m.setIsrequired(e.getIsrequired());
						m.setGovernancetype(e.getGovtypeclassification());
						dbService.save(m);
					}
					Integer update = (Integer) dbService.executeUpdate("UPDATE Tbmember set governancetype='"
							+ governancetype + "' WHERE membershipid=:membershipid", params);
					if(update > 0){
						flag = "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbmembereventschecklist> getMemberGovernance(String membershipid) {
		// TODO Auto-generated method stub
		try {
			params.put("membershipid", membershipid);
			if (membershipid != null) {
				List<Tbmembereventschecklist> list = (List<Tbmembereventschecklist>) dbService
						.executeListHQLQuery("FROM Tbmembereventschecklist WHERE membershipid=:membershipid", params);
				if (list.size() > 0 || list != null) {
					return list;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String updateMemberGovernanceEvents(Tbmembereventschecklist memberevent) {
		// TODO Auto-generated method stub
		try {
			params.put("membershipid", memberevent.getMembershipid());
			params.put("eventcode", memberevent.getEventcode());
			if (memberevent.getMembershipid() != null && memberevent.getEventcode() != null) {
				Tbmembereventschecklist e = (Tbmembereventschecklist) dbService.executeUniqueHQLQuery(
						"FROM Tbmembereventschecklist WHERE membershipid=:membershipid AND eventcode=:eventcode",
						params);
				if(e!=null){
					e.setHasattended(memberevent.getHasattended());
					e.setHasvoted(memberevent.getHasvoted());
					if(dbService.saveOrUpdate(e)){
						return "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}
}
