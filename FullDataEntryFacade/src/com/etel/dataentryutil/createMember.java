package com.etel.dataentryutil;

import java.util.Date;
import java.util.Map;

import org.cloudfoundry.org.codehaus.jackson.map.DeserializationConfig;
import org.cloudfoundry.org.codehaus.jackson.map.ObjectMapper;
import com.coopdb.data.Tbmember;
import com.coopdb.data.Tbmembershipapp;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.dataentry.FullDataEntryServiceImpl;
import com.etel.util.HQLUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class createMember {
	

	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private Map<String, Object> param = HQLUtil.getMap();
	private DBService dbService = new DBServiceImpl();
	public Tbmember saveMember(Tbmembershipapp mapp, String id) {
		try {
			Tbmember mem = new Tbmember();
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String value = mapper.writeValueAsString(mapp);
			mem = mapper.readValue(value, Tbmember.class);
			mem.setMembershipid(id);
			mem.setMembershipappid(mapp.getMembershipappid());
			mem.setMembername(mapp.getLastname().trim().replaceAll("\u00A0", "")+" "+mapp.getFirstname().trim().replaceAll("\u00A0", "")+" "+mapp.getMiddlename().trim().replaceAll("\u00A0", ""));
			if(mapp.getApplicanttype().equals("2")){
				mem.setOldmembershipid(mapp.getMembershipid());
			}
			mem.setMembershipdate(new Date());
			mem.setMembershipstatus("0");
			// fulladdress1
			mem.setFulladdress1(FullDataEntryServiceImpl.fullAddress(mapp.getStreetnoname1(), mapp.getSubdivision1(),
					mapp.getBarangay1(), mapp.getCity1(), mapp.getStateprovince1(),
					mapp.getCountry1(), mapp.getRegion1(), mapp.getPostalcode1()));
			// fulladdress2
			mem.setFulladdress2(FullDataEntryServiceImpl.fullAddress(mapp.getStreetnoname2(), mapp.getSubdivision2(),
					mapp.getBarangay2(), mapp.getCity2(), mapp.getStateprovince2(),
					mapp.getCountry2(), mapp.getRegion2(), mapp.getPostalcode2()));
			mem.setCompanycode(mapp.getCompanycode());
			mem.setCapconpledge(mapp.getCapconpledgeamt());
			mem.setSavingspledge(mapp.getSavingspledgeamt());
			mem.setNoofshares(mapp.getNoofshares());
			param.put("companycode", mapp.getCompanycode());
			param.put("coopcode", mapp.getCoopcode());
			if(mapp.getCompanycode()!=null && !mapp.getCompanycode().equals(null))
			mem.setCompanyname((String) dbService.executeUniqueSQLQuery(
					"SELECT companyname FROM Tbmembercompany WHERE coopcode=:coopcode AND companycode=:companycode",
					param)); // modified by ced 12112018 removed companycode
			return mem;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
