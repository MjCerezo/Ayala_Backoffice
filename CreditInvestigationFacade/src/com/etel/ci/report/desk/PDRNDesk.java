package com.etel.ci.report.desk;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;
import com.etel.utils.ObjectUtil;
import com.coopdb.data.Tbdeskciactivity;
import com.coopdb.data.Tbdeskcidetails;

public class PDRNDesk{
	private String name;
	private String presentaddress;
	private String permanentaddress;
	private String age;
	private String civilstatus;
	private String contactdetails;
	private String parentinfo;
	private Integer noofdependents;
	
	//create PDRN
	/**Save initial Desk PDRN**/
	@SuppressWarnings("unchecked")
	public static void savePDRNDesk(String rptid, String cifno) throws IllegalArgumentException, IllegalAccessException {
		DBService dbservice = new DBServiceImpl();
		try {
			if(cifno == null){
				new PDRNDesk();
			}else{
				Map<String, Object> params = HQLUtil.getMap();
				params.put("cifno", cifno);
				String sql = null;
				sql = "SELECT "
						+ "CONCAT((SELECT UPPER (desc1) FROM TBCODETABLE WHERE codename = 'TITLE' AND codevalue = a.title), "
						+ "' ' ,a.firstname , ' ' , a.middlename , ' ' , a.lastname , ' ' , a.suffix )as name, "
						+ " CAST(a.age AS varchar(3)) as age, "
						+ "CONCAT((SELECT desc1 FROM TBCODETABLE WHERE codename = 'CIVILSTATUS' AND codevalue = a.civilstatus), "
						+ "', '  , a.spousefirstname , ' '  , a.spousemiddlename  , ' '  , a.spouselastname) as civilstatus, "
						+ "a.fulladdress1 as presentaddress, " + "a.fulladdress2 as permanentaddress, "
						+ "CONCAT(a.areacodephone,' ',a.homephoneno,', ',a.mobilephoneareacode,' ',a.mobilephoneno)as contactdetails, "
						//+ "CONCAT('Father: ',a.fatherfirstname,' ',a.fathermiddlename,' ',a.fatherlastname,' ',a.fathersuffix,'<br>', "
						//+ "'Mother: ',a.motherfirstname,' ',a.mothermiddlename,' ',a.motherlastname,' ',a.mothersuffix) as parentinfo, "
						+ "(SELECT CONVERT(varchar(8000),(STUFF((SELECT '' + concat (relationshipdesc, + ' : ' + lastname, + ', ' + firstname, + ' ' + middlename) FROM TBMEMBERRELATIONSHIP WHERE mainmemberid=a.membershipid  FOR XML PATH('br')), 1, 4, '')))) as parentinfo, "
						+ "(SELECT COUNT(*) FROM TBMEMBERDEPENDENTS WHERE membershipid = a.membershipid) as noofdependents "
						+ "FROM TBMEMBER a " + "WHERE a.membershipid=:cifno";
				PDRNDesk p = (PDRNDesk)dbservice.execSQLQueryTransformer(sql, params, PDRNDesk.class, 0);
				if(p != null){
					Map<String, Object> m = ObjectUtil.getFieldNamesAndValues(p); 
					if (m != null) {
						for (Map.Entry<String, Object> e : m.entrySet()) {
							params.put("category", e.getKey());
							params.put("rptid", rptid);
							Tbdeskcidetails d = (Tbdeskcidetails)dbservice
									.executeUniqueHQLQuery("FROM Tbdeskcidetails WHERE cireportid=:rptid "
											+ "AND ciactivity='PDRN' AND category=:category", params);
							
							if(d != null){
								if(e.getValue() == null || e.getValue().equals("")){
									d.setDeclaredvalue("");
								}else{
									d.setDeclaredvalue(e.getValue().toString());
								}
								dbservice.saveOrUpdate(d);
							}else{
								Tbdeskcidetails d2 = new Tbdeskcidetails();
								d2.setCategory(e.getKey());
								d2.setCireportid(rptid);
								d2.setCiactivity("PDRN");
								d2.setFindings(null);
								if(e.getValue() == null || e.getValue().equals("")){
									d2.setDeclaredvalue("");
								}else{
									d2.setDeclaredvalue(e.getValue().toString());
								}
								dbservice.save(d2);
							}
						}
					}
					// save initial desk CI activity 
					params.put("rptid", rptid);
					Tbdeskciactivity activity = (Tbdeskciactivity) dbservice
							.executeUniqueHQLQuery("FROM Tbdeskciactivity WHERE cireportid=:rptid AND ciactivity='PDRN'", params);
					if(activity == null){
						Tbdeskciactivity a = new Tbdeskciactivity();
						a.setCireportid(rptid);
						a.setCiactivity("PDRN");
						a.setCifno(cifno);
						dbservice.saveOrUpdate(a);
					}
					dbservice.executeUpdate("UPDATE TBDESKCIDETAILS SET category = 'family information' WHERE category = ''", null);
				}else{
					System.out.println("pdrn is null *****");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public List<Tbdeskcidetails> getPDRNDesk(List<Tbdeskcidetails> pdrndesklist){
		Tbdeskcidetails [] aDesk;
		List<Tbdeskcidetails> list = new ArrayList<Tbdeskcidetails>();
		try {
			aDesk = new Tbdeskcidetails[8];
			
			for(Tbdeskcidetails d : pdrndesklist){
				if(d.getCategory().equals("name")){
					d.setCategory("Name");
					aDesk[0] = d;
				}
				if(d.getCategory().equals("presentaddress")){
					d.setCategory("Present Address");
					aDesk[1] = d;
				}
				if(d.getCategory().equals("permanentaddress")){
					d.setCategory("Permanent Address");
					aDesk[2] = d;
				}
				if(d.getCategory().equals("age")){
					d.setCategory("Age");
					aDesk[3] = d;
				}
				if(d.getCategory().equals("civilstatus")){
					d.setCategory("Civil Status");
					aDesk[4] = d;
				}
				if(d.getCategory().equals("contactdetails")){
					d.setCategory("Contact Details");
					aDesk[5] = d;
				}
				if(d.getCategory().equals("parentinfo")){
					//d.setCategory("Parents Information");
					d.setCategory("Family Information");
					aDesk[6] = d;
				}
				if(d.getCategory().equals("noofdependents")){
					d.setCategory("No. of Dependents");
					aDesk[7] = d;
				}
			}
			if(aDesk != null){
//				return list = Arrays.asList(aDesk);
				for(Tbdeskcidetails d : aDesk){
					if(d != null){
						list.add(d);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPresentaddress() {
		return presentaddress;
	}
	public void setPresentaddress(String presentaddress) {
		this.presentaddress = presentaddress;
	}
	public String getPermanentaddress() {
		return permanentaddress;
	}
	public void setPermanentaddress(String permanentaddress) {
		this.permanentaddress = permanentaddress;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getCivilstatus() {
		return civilstatus;
	}
	public void setCivilstatus(String civilstatus) {
		this.civilstatus = civilstatus;
	}
	public String getContactdetails() {
		return contactdetails;
	}
	public void setContactdetails(String contactdetails) {
		this.contactdetails = contactdetails;
	}
	public String getParentinfo() {
		return parentinfo;
	}
	public void setParentinfo(String parentinfo) {
		this.parentinfo = parentinfo;
	}
	public Integer getNoofdependents() {
		return noofdependents;
	}
	public void setNoofdependents(Integer noofdependents) {
		this.noofdependents = noofdependents;
	}
	
	
}
