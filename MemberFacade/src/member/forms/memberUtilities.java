package member.forms;

import java.util.Date;
import java.util.Map;

import com.coopdb.data.Tbchangeprofilehistory;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class memberUtilities {

	private DBService dbService = new DBServiceImpl();
	private Map<String, Object> params = HQLUtil.getMap();
	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");

	public Integer updateCount(String membershipid, String category) {
		Integer count = 1;
		try {
			params.put("membershipid", membershipid);
			params.put("category", category);
			if (membershipid != null && category != null) {
				Tbchangeprofilehistory h  = (Tbchangeprofilehistory) dbService.execSQLQueryTransformer(
						"SELECT TOP 1 updatecount FROM Tbchangeprofilehistory WHERE changecategorytype=:category AND memberid=:membershipid ORDER BY updatecount DESC",
						params, Tbchangeprofilehistory.class, 0);
				if (h != null) {
					count = h.getUpdatecount()+1;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return count;
	}

	public boolean profileChanged(String fieldtype, String membershipid, String oldvalue, String newvalue,
			String source, String remarks, String changecategory, Integer refno, Integer count) {
		Tbchangeprofilehistory h = new Tbchangeprofilehistory();
		try {
			h.setMemberid(membershipid);
			h.setChangefieldtype(fieldtype);
			h.setChangecategorytype(changecategory);
			h.setOldvalue(oldvalue);
			h.setNewvalue(newvalue);
			h.setSource(source);
			h.setUpdateremarks(remarks);
			h.setUpdatedby(secservice.getUserName());
			h.setDateupdated(new Date());
			h.setUpdaterefno(refno);
			h.setUpdatecount(count);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return dbService.save(h);
	}
	
	public String getMemberDetailsQueryReadOnly() {
		return "SELECT  TOP 1 " + 
				"mem.firstname, " + 
				"mem.lastname, " + 
				"mem.middlename," + 
				"mem.shortname," + 
				"mem.dateofbirth," + 
				"mem.suffix," + 
				"mem.age," + 
				"mem.placeofbirth," + 
				"mem.countryofbirth," + 
				"mem.nationality," + 
				"mem.heightfeet," + 
				"mem.heightinches," + 
				"mem.weight," + 
				"mem.socialaffiliation," + 
				"" + 
				"ti.desc1 AS title," + 
				"gen.desc1 AS gender," + 
				"rel.desc1 AS religion," + 
				"bl.desc1 AS bloodtype," + 
				"st.desc1 AS civilstatus," + 
				"id.desc1 AS idtype," + 
				"" + 
				"mem.areacodephone," + 
				"mem.phoneno," + 
				"mem.officephoneareacode," + 
				"mem.officephoneno," + 
				"mem.mobilephoneareacode," + 
				"mem.mobilephoneno," + 
				"mem.emailaddress," + 
				"mem.tin," + 
				"mem.gsis," + 
				"mem.sss," + 
				"mem.idno," + 
				"mem.issuedate," + 
				"mem.expirydate," + 
				"mem.primaryschoolname," + 
				"mem.primaryyrgrad," + 
				"mem.secondschoolname," + 
				"mem.secondyrgrad," + 
				"mem.tertiaryschoolname," + 
				"mem.tertiaryyrgrad," + 
				"mem.sameaspermanentaddress," + 
				"mem.occupiedsince1," + 
				"mem.occupiedsince2," + 
				"mem.ownershiptype1," + 
				"mem.ownershiptype2," + 
				"" + 
				"add1.country AS country1," + 
				"add1.stateprovince AS stateprovince1," + 
				"add1.city AS city1," + 
				"add1.areacode as region1," + 
				"CONCAT(add1.postalcode,' - ',add1.areadesc) as postalcode1," + 
				"" + 
				"add2.country AS country2," + 
				"add2.stateprovince AS stateprovince2," + 
				"add2.city AS city2," + 
				"add2.areacode as region2," + 
				"CONCAT(add2.postalcode,' - ',add2.areadesc) as postalcode2," + 
				"" + 
				"mem.membershipid," + 
				"mem.membershipappid," + 
				"" + 
				"mem.salary," + 
				"mem.businessincome," + 
				"mem.otherincome," + 
				"" + 
				"mem.spousesalary," + 
				"mem.spousebusinesssalary," + 
				"mem.spouseotherincome" + 
				"" + 
				" FROM TBMEMBER mem " + 
				"" + 
				" LEFT JOIN TBCODETABLE ti ON ti.codevalue=mem.title AND ti.codename='TITLE'" + 
				" LEFT JOIN TBCODETABLE gen ON gen.codevalue=mem.gender AND gen.codename='GENDER'" + 
				" LEFT JOIN TBCODETABLE rel ON rel.codevalue=mem.religion AND rel.codename='RELIGION'" + 
				" LEFT JOIN TBCODETABLE bl ON bl.codevalue=mem.bloodtype AND bl.codename='BLOODTYPE'" + 
				" LEFT JOIN TBCODETABLE st ON st.codevalue=mem.civilstatus AND st.codename='CIVILSTATUS'" + 
				" LEFT JOIN TBCODETABLE id ON id.codevalue=mem.idtype AND id.codename='IDTYPESECOND'" + 
				" LEFT JOIN TBCOUNTRY " + 
				"" + 
				" add1 ON " + 
				" add1.code=mem.country1 AND " + 
				" add1.stateprovince=mem.stateprovince1 AND " + 
				" add1.city=mem.city1 AND " + 
				" add1.areacode=mem.region1 AND " + 
				" add1.postalcode=mem.postalcode1" + 
				"" + 
				" LEFT JOIN TBCOUNTRY " + 
				"" + 
				" add2 ON " + 
				" add2.code=mem.country2 AND " + 
				" add2.stateprovince=mem.stateprovince2 AND " + 
				" add2.city=mem.city2 AND " + 
				" add2.areacode=mem.region2 AND " + 
				" add2.postalcode=mem.postalcode2" + 
				" WHERE mem.membershipid=:membershipid ";
	}

}
