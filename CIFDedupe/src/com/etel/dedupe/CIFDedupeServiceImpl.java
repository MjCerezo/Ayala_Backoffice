package com.etel.dedupe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cifsdb.data.AuditTrail;
import com.cifsdb.data.Tbamlacorporate;
import com.cifsdb.data.Tbamlaindividual;
import com.cifsdb.data.Tbamlalistmain;
import com.cifsdb.data.Tbblacklistcorporate;
import com.cifsdb.data.Tbblacklistindividual;
import com.cifsdb.data.Tbblacklistmain;
import com.cifsdb.data.Tbcifcorporate;
import com.cifsdb.data.Tbcifindividual;
import com.cifsdb.data.Tbcifmain;
import com.cifsdb.data.Tbcodetable;
import com.etel.audittrail.AuditTrailFacade;
import com.etel.cifreport.form.CIFTransactionalReportForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.dedupeforms.InquiryCount;
import com.etel.dedupeforms.MembershipDedupeForm;
import com.etel.dedupeforms.amladedupeform;
import com.etel.dedupeforms.blacklistdedupeform;
import com.etel.dedupeforms.cifdedupeform;
import com.etel.dedupeforms.dedupeform;
import com.etel.utils.UserUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class CIFDedupeServiceImpl implements CIFDedupeService {

	public static SecurityService securityService = (SecurityService) RuntimeAccess.getInstance()
			.getServiceBean("securityService");
	/** DEDUPE INDIVIDUAL **/	
	@SuppressWarnings({ "unchecked" })
	@Override
	public dedupeform dedupeIndiv(String lname, String fname, String mname, String suf, Date dob) {
		// TODO Auto-generated method stub
		
		// Individual tb
        List<Tbcifindividual> cif = new ArrayList<Tbcifindividual>();
        List<Tbamlaindividual> aml = new ArrayList<Tbamlaindividual>();        
        List<Tbblacklistindividual> blk = new ArrayList<Tbblacklistindividual>();
        // Main tb
        List<Tbcifmain> cifmain = new ArrayList<Tbcifmain>();
        List<Tbamlalistmain> amlmain = new ArrayList<Tbamlalistmain>();
        List<Tbblacklistmain> blkmain = new ArrayList<Tbblacklistmain>();
		
		dedupeform form = new dedupeform();
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("lname", lname==null?"'%%'":"%"+lname+"%");
		param.put("fname", fname==null?"'%%'":"%"+fname+"%");
		param.put("mname", mname==null?"'%%'":"%"+mname+"%");	
		param.put("suf", suf==null?"'%%'":"%"+suf+"%");	
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String date = null;
		if(dob!=null){
			date = formatter.format(dob);
		}
		
		try {
			if(suf!=null){
				if(dob==null){
					cif = (List<Tbcifindividual>)dbService.executeListHQLQuery
							("FROM Tbcifindividual WHERE ((lastname like :lname  AND firstname like :fname AND middlename like :mname AND suffix like:suf) "
									+ "OR "
									+ "(firstname like :fname AND middlename like :mname AND lastname like :lname) AND suffix like:suf)) ORDER BY lastname ASC", param);
					aml = (List<Tbamlaindividual>)dbService.executeListHQLQuery
							("FROM Tbamlaindividual WHERE (((lastname like :lname or lastname like :mname) AND firstname like :fname AND middlename like :mname AND suffix like:suf AND amlaliststatus='1') "
									+ "OR "
									+ "(firstname like :fname AND (middlename like :mname or lastname like :mname) AND suffix like:suf AND amlaliststatus='1')) ORDER BY lastname ASC", param);
					blk = (List<Tbblacklistindividual>)dbService.executeListHQLQuery
							("FROM Tbblacklistindividual WHERE (((lastname like :lname or lastname like :mname) AND firstname like :fname AND middlename like :mname AND suffix like:suf AND blackliststatus='1') "
									+ "OR "
									+ "(firstname like :fname AND (middlename like :mname or lastname like :mname) AND suffix like:suf AND blackliststatus='1')) ORDER BY lastname ASC", param);
				}else{
					cif = (List<Tbcifindividual>)dbService.executeListHQLQuery("FROM Tbcifindividual WHERE (dateofbirth BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:00') AND ((lastname like :lname  AND firstname like :fname AND middlename like :mname AND suffix like:suf) OR (firstname like :fname AND middlename like :mname AND lastname like :lname AND suffix like:suf)) ORDER BY lastname ASC", param);	
					aml = (List<Tbamlaindividual>)dbService.executeListHQLQuery("FROM Tbamlaindividual WHERE (dateofbirth BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:00') AND"
									+ "(((lastname like :lname or lastname like :mname) AND firstname like :fname AND middlename like :mname AND suffix like:suf AND amlaliststatus='1') "
									+ "OR "
									+ "(firstname like :fname AND (middlename like :mname or lastname like :mname) AND suffix like:suf AND amlaliststatus='1')) ORDER BY lastname ASC", param);		
					blk = (List<Tbblacklistindividual>)dbService.executeListHQLQuery("FROM Tbblacklistindividual WHERE (dateofbirth BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:00') AND"
									+ "(((lastname like :lname or lastname like :mname) AND firstname like :fname AND middlename like :mname AND suffix like:suf) "
									+ "OR "
									+ "(firstname like :fname AND (middlename like :mname or lastname like :mname) AND suffix like:suf AND blackliststatus='1')) ORDER BY lastname ASC", param);				
				}					
			}else{
				if(dob==null){
					cif = (List<Tbcifindividual>)dbService.executeListHQLQuery
							("FROM Tbcifindividual WHERE ((lastname like :lname  AND firstname like :fname AND middlename like :mname) "
									+ "OR "
									+ "(firstname like :fname AND middlename like :mname AND lastname like :lname)) ORDER BY lastname ASC", param);
					aml = (List<Tbamlaindividual>)dbService.executeListHQLQuery
							("FROM Tbamlaindividual WHERE (((lastname like :lname or lastname like :mname) AND firstname like :fname AND middlename like :mname AND amlaliststatus='1') "
									+ "OR "
									+ "(firstname like :fname AND (middlename like :mname or lastname like :mname) AND amlaliststatus='1')) ORDER BY lastname ASC", param);
					blk = (List<Tbblacklistindividual>)dbService.executeListHQLQuery
							("FROM Tbblacklistindividual WHERE (((lastname like :lname or lastname like :mname) AND firstname like :fname AND middlename like :mname AND blackliststatus='1') "
									+ "OR "
									+ "(firstname like :fname AND (middlename like :mname or lastname like :mname) AND blackliststatus='1')) ORDER BY lastname ASC", param);
				}else{
					cif = (List<Tbcifindividual>)dbService.executeListHQLQuery("FROM Tbcifindividual WHERE (dateofbirth BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:00') AND "
									+ "((lastname like :lname AND firstname like :fname AND middlename like :mname) "
									+ "OR "
									+ "(firstname like :fname AND middlename like :mname or lastname like :lname)) ORDER BY lastname ASC", param);	
					aml = (List<Tbamlaindividual>)dbService.executeListHQLQuery("FROM Tbamlaindividual WHERE (dateofbirth BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:00') AND"
									+ "(((lastname like :lname or lastname like :mname) AND firstname like :fname AND middlename like :mname AND amlaliststatus='1') "
									+ "OR "
									+ "(firstname like :fname AND (middlename like :mname or lastname like :mname) AND amlaliststatus='1')) ORDER BY lastname ASC", param);		
					blk = (List<Tbblacklistindividual>)dbService.executeListHQLQuery("FROM Tbblacklistindividual WHERE (dateofbirth BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:00') AND"
									+ "(((lastname like :lname or lastname like :mname) AND firstname like :fname AND middlename like :mname) "
									+ "OR "
									+ "(firstname like :fname AND (middlename like :mname or lastname like :mname) AND blackliststatus='1')) ORDER BY lastname ASC", param);				
				}				
			}
				
				if(cif!=null){
					for(Tbcifindividual cif2 : cif){
						param.put("cifno", cif2.getCifno());
						Tbcifmain c = (Tbcifmain)dbService.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno =:cifno", param);
						if(cifmain.add(c)){
							for(Tbcifmain cifmain2 : cifmain){
								param.put("status", cifmain2.getCifstatus());
								Tbcodetable cifstatus = (Tbcodetable) dbService.executeUniqueHQLQuery("FROM Tbcodetable a WHERE a.id.codename ='CIFSTATUS' AND a.id.codevalue =:status", param);
								if(cifstatus!=null){
									cifmain2.setCifstatus(cifstatus.getDesc1());
								}
								param.put("type", cifmain2.getCiftype());
								Tbcodetable ciftype = (Tbcodetable) dbService.executeUniqueHQLQuery("FROM Tbcodetable a WHERE a.id.codename ='CIFTYPE' AND a.id.codevalue =:type", param);
								if(ciftype!=null){
									cifmain2.setCiftype(ciftype.getDesc1());
								}
							}
							form.setCif(cifmain); /** CIF Bucket **/				
						}
					}
				}
				if(aml!=null){
					for(Tbamlaindividual aml2 : aml){	
						Tbamlalistmain  a2 = new Tbamlalistmain();
						a2.setAmlalistid(aml2.getAmlalistid());
						a2.setCifno(aml2.getCifno());
						a2.setFullname(aml2.getLastname() + ", " + aml2.getFirstname() + (aml2.getSuffix()==null?"":aml2.getSuffix()) + " " + (aml2.getMiddlename()==null?"":aml2.getMiddlename()));
						a2.setAmlalistsource(aml2.getAmlalistsource());
						a2.setStatus(aml2.getAmlaliststatus());
						if(amlmain.add(a2)){
							for(Tbamlalistmain amlmain2 : amlmain){
								param.put("status", amlmain2.getStatus());
								Tbcodetable amlstatus = (Tbcodetable) dbService.executeUniqueHQLQuery("FROM Tbcodetable a WHERE a.id.codename ='AMLASTATUS' AND a.id.codevalue =:status", param);
								if(amlstatus!=null){
									amlmain2.setStatus(amlstatus.getDesc1());
								}	
								param.put("type", amlmain2.getAmlalistsource());
								Tbcodetable source = (Tbcodetable) dbService.executeUniqueHQLQuery("FROM Tbcodetable a WHERE a.id.codename ='AMLALISTSOURCE' AND a.id.codevalue =:type", param);
								if(source!=null){
									amlmain2.setAmlalistsource(source.getDesc1());
								}	
							}
							form.setAmla(amlmain); /** AMLA Bucket **/								
						}
					}
				}				
				if(blk!=null){
					for(Tbblacklistindividual blk2 : blk){
						Tbblacklistmain b2 = new Tbblacklistmain();
						b2.setBlacklistid(blk2.getBlacklistid());
						b2.setCifno(blk2.getCifno());
						b2.setFullname(blk2.getLastname() +  ", " + blk2.getFirstname() + " " + (blk2.getSuffix()==null?"":blk2.getSuffix()) + " " + (blk2.getMiddlename()==null? "": blk2.getMiddlename()));
						b2.setCreateddate(blk2.getDatecreated());
						b2.setBlacklistsource(blk2.getBlacklistsource());
						b2.setStatus(blk2.getBlackliststatus());
						if(blkmain.add(b2)){
							for(Tbblacklistmain blkmain2 : blkmain){
								param.put("status", blkmain2.getStatus());
								Tbcodetable blkstatus = (Tbcodetable) dbService.executeUniqueHQLQuery("FROM Tbcodetable a WHERE a.id.codename ='BLACKLISTSTATUS' AND a.id.codevalue =:status", param);
								if(blkstatus!=null){
									blkmain2.setStatus(blkstatus.getDesc1());
								}
								param.put("type", blkmain2.getBlacklistsource());
								Tbcodetable source = (Tbcodetable) dbService.executeUniqueHQLQuery("FROM Tbcodetable a WHERE a.id.codename ='BLACKLISTSOURCE' AND a.id.codevalue =:type", param);
								if(source!=null){
									blkmain2.setBlacklistsource(source.getDesc1());
								}
							}
							form.setBlacklist(blkmain);	/** BLACKLIST Bucket **/							
						}
					}
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@SuppressWarnings("unchecked")
	@Override
	public dedupeform dedupeIndividual(String lname, String fname, Date dob, String tin, String sss, String streetno,
			String subdivision, String country, String province, String city, String barangay, String postalCode, String losLink) {
		
		AuditTrailFacade auditTrailFacade = new AuditTrailFacade();
		AuditTrail auditTrail = new AuditTrail();
		dedupeform form = new dedupeform();
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> param = new HashMap<String, Object>();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String date = null;
		
		StringBuilder cif = new StringBuilder();
		StringBuilder member = new StringBuilder();
		StringBuilder aml = new StringBuilder();
		StringBuilder blk = new StringBuilder();
		
		try {
			if(lname!=null && fname!=null){
				param.put("lname", lname);
				param.put("fname", fname);
				param.put("losLink", losLink);
				cif.append("SELECT "
						+ "(select branchname from "+ losLink + ".dbo.TBBRANCH WHERE branchcode = b.originatingbranch) AS originatingbranch, "
						+ "b.cifno, b.fullname, b.dateofbirth, "
						+ "(SELECT processname FROM "+ losLink + ".dbo.TBWORKFLOWPROCESS WHERE processid = b.cifstatus) as cifstatus, "
						+ "d.desc1 as ciftype "
						+ "FROM TBCIFINDIVIDUAL a "
						+ "LEFT JOIN TBCIFMAIN b ON a.cifno = b.cifno "
						+ "LEFT JOIN TBCODETABLE c ON b.cifstatus = c.codevalue AND c.codename = 'MEMBERSTATUS' "
						+ "LEFT JOIN TBCODETABLE d ON b.ciftype = d.codevalue AND d.codename = 'CIFTYPE' "
						+ "WHERE a.lastname LIKE '%"+lname.trim()+"%' AND a.firstname LIKE '%"+fname.trim()+"%' ");
						//+ "AND c.codename = 'MEMBERSTATUS' "
						//+ "AND d.codename = 'CIFTYPE' ");
				member.append("SELECT "
						+ "(select branchname from "+ losLink + ".dbo.TBBRANCH WHERE branchcode = b.originatingbranch) AS originatingbranch, "
						+ "b.cifno, b.fullname, b.dateofbirth, a.sss, a.tin, "
						+ "(SELECT processname FROM "+ losLink + ".dbo.TBWORKFLOWPROCESS WHERE workflowid = 2 and sequenceno = b.cifstatus) as cifstatus, "
						+ "d.desc1 as ciftype "
						+ "FROM TBCIFINDIVIDUAL a "
						+ "LEFT JOIN TBCIFMAIN b ON a.cifno = b.cifno "
						+ "LEFT JOIN TBCODETABLE c ON b.cifstatus = c.codevalue AND c.codename = 'MEMBERSTATUS' "
						+ "LEFT JOIN TBCODETABLE d ON b.ciftype = d.codevalue AND d.codename = 'CIFTYPE' "
						+ "WHERE a.lastname LIKE '%"+lname.trim()+"%' AND a.firstname LIKE '%"+fname.trim()+"%' ");
						//+ "AND c.codename = 'MEMBERSTATUS' "
						//+ "AND d.codename = 'CIFTYPE' ");
				/*aml.append("SELECT b.amlalistid, b.cifno, b.fullname, b.createddate, c.desc1 as status, d.desc1 as blacklistsource "
						+ "FROM TBAMLAINDIVIDUAL a "
						+ "LEFT JOIN TBAMLALISTMAIN b ON a.amlalistid = b.amlalistid "
						+ "LEFT JOIN TBCODETABLE c ON b.status = c.codevalue AND c.codename = 'AMLASTATUS' "
						+ "LEFT JOIN TBCODETABLE d ON b.amlalistsource = d.codevalue AND d.codename = 'AMLALISTSOURCE' "
						+ "WHERE a.lastname LIKE '%"+lname.trim()+"%' AND a.firstname LIKE '%"+fname.trim()+"%' ");
						//+ "AND c.codename = 'AMLASTATUS' "
						//+ "AND d.codename = 'AMLALISTSOURCE' ");
				blk.append("SELECT b.blacklistid, b.cifno, b.fullname, b.createddate, c.desc1 as status, d.desc1 as blacklistsource "
						+ "FROM TBBLACKLISTINDIVIDUAL a "
						+ "LEFT JOIN TBBLACKLISTMAIN b ON a.blacklistid = b.blacklistid "
						+ "LEFT JOIN TBCODETABLE c ON b.status = c.codevalue AND c.codename = 'BLACKLISTSTATUS' "
						+ "LEFT JOIN TBCODETABLE d ON b.blacklistsource = d.codevalue AND d.codename = 'BLACKLISTSOURCE' "
						+ "WHERE a.lastname LIKE '%"+lname.trim()+"%' AND a.firstname LIKE '%"+fname.trim()+"%' ");
						//+ "AND c.codename = 'BLACKLISTSTATUS' "
						//+ "AND d.codename = 'BLACKLISTSOURCE' ");*/
				if(dob!=null){
					date = formatter.format(dob);
					cif.append("AND (a.dateofbirth BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:00') ");
					member.append("AND (a.dateofbirth BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:00') "); 
					//aml.append("OR (a.dateofbirth BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:00') ");					
					//blk.append("OR (a.dateofbirth BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:00') ");
				}
				if(tin!=null){
					param.put("tin", tin);
					cif.append("OR a.tin LIKE '%"+tin.trim()+"%' ");
					member.append("OR a.tin LIKE '%"+tin.trim()+"%' ");
				}
				if(sss!=null){
					param.put("sss", sss);
					cif.append("OR a.sss LIKE '%"+sss.trim()+"%' ");
					member.append("OR a.sss LIKE '%"+sss.trim()+"%' ");
				}
				/*if(streetno!=null){
					param.put("streetno", streetno.trim());
					cif.append("AND a.streetno1 LIKE '%"+streetno.trim()+"%' ");
					member.append("AND a.streetno1 LIKE '%"+streetno.trim()+"%' ");
				}
				if(subdivision!=null){
					param.put("subd", subdivision);
					cif.append("AND a.subdivision1 LIKE '%"+subdivision.trim()+"%' ");
					member.append("AND a.subdivision1 LIKE '%"+subdivision.trim()+"%' ");
				}
				if(country!=null){
					param.put("country", country);
					cif.append("AND a.country1=:country ");
					member.append("AND a.country1=:country ");
				}
				if(city!=null){
					param.put("city", city);
					cif.append("AND a.city1=:city ");
					member.append("AND a.city1=:city ");
				}
				if(barangay!=null){
					param.put("barangay", barangay.trim());
					cif.append("AND a.barangay1 LIKE '%"+barangay.trim()+"%' ");
					member.append("AND a.barangay1 LIKE '%"+barangay.trim()+"%' ");
				}	
				if(postalCode!=null){
					param.put("postalCode", postalCode.trim());
					cif.append("AND a.postalcode1=:postalCode ");
					member.append("AND a.postalcode1=:postalCode ");
				}*/					
				List<Tbcifmain> cifmain = (List<Tbcifmain>) dbService.execSQLQueryTransformer(cif.toString(), param, Tbcifmain.class, 1);
				if(cifmain!=null){
					form.setCif(cifmain);
				}
				/*List<Tbamlalistmain> amlmain = (List<Tbamlalistmain>) dbService.execSQLQueryTransformer(aml.toString(), param, Tbamlalistmain.class, 1);
				if(amlmain!=null){
					form.setAmla(amlmain);
				}
				List<Tbblacklistmain> blkmain = (List<Tbblacklistmain>) dbService.execSQLQueryTransformer(blk.toString(), param, Tbblacklistmain.class, 1);
				if(blkmain!=null){
					form.setBlacklist(blkmain);
				}*/
				List<MembershipDedupeForm> membership = (List<MembershipDedupeForm>) dbService.execSQLQueryTransformer(member.toString(), param, MembershipDedupeForm.class, 1);
				if(membership!=null){
					form.setMembershipDedupe(membership);
				}
				
				//Audit Trail TBCODETABLE CODE = AuditTrail Value = 1 Dedupe - Company
				auditTrail.setEventType("3");
				auditTrail.setEventName("0");
				auditTrail.setEventDescription(fname.toUpperCase() + " " + lname.toUpperCase());
				auditTrail.setIpaddress(UserUtil.getUserIp());
				auditTrailFacade.saveAudit(auditTrail);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public dedupeform dedupeCorporate(String businessname, Date incorporationdate, String tin, String sss, String streetno,
			String subdivision, String country, String province, String city, String barangay, String postalCode, String corpOrSoleProp, String losLink) {

		AuditTrailFacade auditTrailFacade = new AuditTrailFacade();
		AuditTrail auditTrail = new AuditTrail();
		dedupeform form = new dedupeform();
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> param = new HashMap<String, Object>();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String date = null;
		
		StringBuilder cif = new StringBuilder();
		StringBuilder aml = new StringBuilder();
		StringBuilder blk = new StringBuilder();
		
		try {
			if(businessname!=null){
				param.put("businessname", businessname);
				param.put("losLink", losLink);
				cif.append("SELECT (select branchname from "+ losLink + ".dbo.TBBRANCH WHERE branchcode = b.originatingbranch) AS originatingbranch, b.cifno, b.fullname, b.dateofincorporation, c.desc1 as cifstatus, d.desc1 as ciftype "
						+ "FROM TBCIFCORPORATE a "
						+ "LEFT JOIN TBCIFMAIN b ON a.cifno = b.cifno "
						+ "LEFT JOIN TBCODETABLE c ON b.cifstatus = c.codevalue "
						+ "LEFT JOIN TBCODETABLE d ON b.ciftype = d.codevalue "
						+ "WHERE a.corporatename LIKE '%"+businessname.trim()+"%' "
						+ "AND c.codename = 'COMPANYSTATUS' "
						+ "AND d.codename = 'CIFTYPE' ");
				aml.append("SELECT b.amlalistid, b.cifno, b.fullname, b.createddate, c.desc1 as status, d.desc1 as blacklistsource "
						+ "FROM TBAMLACORPORATE a "
						+ "LEFT JOIN TBAMLALISTMAIN b ON a.amlalistid = b.amlalistid "
						+ "LEFT JOIN TBCODETABLE c ON b.status = c.codevalue "
						+ "LEFT JOIN TBCODETABLE d ON b.amlalistsource = d.codevalue "
						+ "LEFT JOIN TBCIFMAIN e ON e.fullname LIKE '%"+businessname.trim()+"%' "
						+ "WHERE a.corporatename LIKE '%"+businessname.trim()+"%' "
						+ "AND c.codename = 'AMLASTATUS' "
						+ "AND d.codename = 'AMLALISTSOURCE' ");
				blk.append("SELECT b.blacklistid, b.cifno, b.fullname, b.createddate, c.desc1 as status, d.desc1 as blacklistsource "
						+ "FROM TBBLACKLISTCORPORATE a "
						+ "LEFT JOIN TBBLACKLISTMAIN b ON a.blacklistid = b.blacklistid "
						+ "LEFT JOIN TBCODETABLE c ON b.status = c.codevalue "
						+ "LEFT JOIN TBCODETABLE d ON b.blacklistsource = d.codevalue "
						+ "LEFT JOIN TBCIFMAIN e ON e.fullname LIKE '%"+businessname.trim()+"%' "
						+ "WHERE a.corporatename LIKE '%"+businessname.trim()+"%' "
						+ "AND c.codename = 'BLACKLISTSTATUS' "
						+ "AND d.codename = 'BLACKLISTSOURCE' ");
				if(corpOrSoleProp!=null){
					if(corpOrSoleProp.equalsIgnoreCase("Corp")){
						cif.append("AND b.customertype = '1' ");
						aml.append("AND e.customertype = '1' ");
						blk.append("AND e.customertype = '1' ");
					}else if(corpOrSoleProp.equalsIgnoreCase("Sole")){
						cif.append("AND b.customertype = '3' ");	
						aml.append("AND e.customertype = '3' ");
						blk.append("AND e.customertype = '3' ");
					}
				}				
				if(incorporationdate!=null){
					date = formatter.format(incorporationdate);
					cif.append("AND (a.dateofincorporation BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:00') ");
					aml.append("AND (a.dateofincorporation BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:00') ");					
					blk.append("AND (a.dateofincorporation BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:00') ");
				}
				if(tin!=null){
					param.put("tin", tin);
					cif.append("AND a.tin LIKE '%"+tin.trim()+"%' ");
				}
				if(sss!=null){
					param.put("sss", sss);
					cif.append("AND a.sss LIKE '%"+sss.trim()+"%' ");
				}
				if(streetno!=null){
					param.put("streetno", streetno.trim());
					cif.append("AND a.streetno1 LIKE '%"+streetno.trim()+"%' ");
				}
				if(subdivision!=null){
					param.put("subd", subdivision);
					cif.append("AND a.subdivision1 LIKE '%"+subdivision.trim()+"%' ");
				}
				if(country!=null){
					param.put("country", country);
					cif.append("AND a.country1=:country ");
				}
				if(city!=null){
					param.put("city", city);
					cif.append("AND a.city1=:city ");
				}
				if(barangay!=null){
					param.put("barangay", barangay.trim());
					cif.append("AND a.barangay1 LIKE '%"+barangay.trim()+"%' ");
				}	
				if(postalCode!=null){
					param.put("postalCode", postalCode.trim());
					cif.append("AND a.postalcode1=:postalCode ");
				}	
				
				List<Tbcifmain> cifmain = (List<Tbcifmain>) dbService.execSQLQueryTransformer(cif.toString(), param, Tbcifmain.class, 1);
				if(cifmain!=null){
					form.setCif(cifmain);
				}
				List<Tbamlalistmain> amlmain = (List<Tbamlalistmain>) dbService.execSQLQueryTransformer(aml.toString(), param, Tbamlalistmain.class, 1);
				if(amlmain!=null){
					form.setAmla(amlmain);
				}
				List<Tbblacklistmain> blkmain = (List<Tbblacklistmain>) dbService.execSQLQueryTransformer(blk.toString(), param, Tbblacklistmain.class, 1);
				if(blkmain!=null){
					form.setBlacklist(blkmain);
				}
			}
			
			//Audit Trail TBCODETABLE CODE = AuditTrail Value = 1 Dedupe - Company
			auditTrail.setEventType("2");
			auditTrail.setEventName("0");
			auditTrail.setEventDescription(businessname.toUpperCase());
			auditTrail.setIpaddress(UserUtil.getUserIp());
			auditTrailFacade.saveAudit(auditTrail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}
	
	/** DEDUPE CORPORATE **/	
	@SuppressWarnings({ "unchecked" })
	@Override
	public dedupeform dedupeCorp(String businessname,  Date incorporationdate, String soleProp) {
		// TODO Auto-generated method stub
		System.out.println("---------- soleProp : " + soleProp);
        List<Tbcifcorporate> cif = new ArrayList<Tbcifcorporate>();
        List<Tbamlacorporate> aml = new ArrayList<Tbamlacorporate>();
        List<Tbblacklistcorporate> blk = new ArrayList<Tbblacklistcorporate>();
        List<Tbcifmain> cifmain = new ArrayList<Tbcifmain>();
        List<Tbamlalistmain> amlmain = new ArrayList<Tbamlalistmain>();
        List<Tbblacklistmain> blkmain = new ArrayList<Tbblacklistmain>();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		dedupeform form = new dedupeform();
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("bname", businessname==null?"%":"%"+businessname+"%");
		
		String date = null;
		if(incorporationdate!=null){
			date = formatter.format(incorporationdate);
		}
		
		try {
			
			if(incorporationdate==null){
				if(soleProp!=null){
					if(soleProp.equals("09")){
						cif = (List<Tbcifcorporate>)dbService.executeListHQLQuery("FROM Tbcifcorporate WHERE corporatename like :bname AND businesstype = '09' ORDER BY corporatename ASC", param);
					}else if(soleProp.equals("ALL")){
						cif = (List<Tbcifcorporate>)dbService.executeListHQLQuery("FROM Tbcifcorporate WHERE corporatename like :bname ORDER BY corporatename ASC", param);
					}
				}else{
					cif = (List<Tbcifcorporate>)dbService.executeListHQLQuery("FROM Tbcifcorporate WHERE corporatename like :bname AND businesstype NOT IN('09') ORDER BY corporatename ASC", param);
				}
				if(cif!=null){
					for(Tbcifcorporate cif2 : cif){
						param.put("cifno", cif2.getCifno());
						Tbcifmain c = (Tbcifmain)dbService.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno =:cifno", param);
						cifmain.add(c);
					}
				}
				aml = (List<Tbamlacorporate>)dbService.executeListHQLQuery("FROM Tbamlacorporate WHERE corporatename like :bname AND amlaliststatus='1' ORDER BY corporatename ASC", param);
				if(aml!=null){
					for(Tbamlacorporate aml2 : aml){
						Tbamlalistmain  a2 = new Tbamlalistmain();
						a2.setAmlalistid(aml2.getAmlalistid());
						a2.setCifno(aml2.getCifno());
						a2.setFullname(aml2.getCorporatename());
						a2.setAmlalistsource(aml2.getAmlalistsource());
						a2.setStatus(aml2.getAmlaliststatus());
						amlmain.add(a2);
					}
				}
				blk = (List<Tbblacklistcorporate>)dbService.executeListHQLQuery("FROM Tbblacklistcorporate WHERE corporatename like :bname AND blackliststatus='1' ORDER BY corporatename ASC", param);
				if(blk!=null){
					for(Tbblacklistcorporate blk2 : blk){
						Tbblacklistmain b2 = new Tbblacklistmain();
						b2.setBlacklistid(blk2.getBlacklistid());
						b2.setCifno(blk2.getCifno());
						b2.setFullname(blk2.getCorporatename());
						b2.setCreateddate(blk2.getDatecreated());
						b2.setBlacklistsource(blk2.getBlacklistsource());
						b2.setStatus(blk2.getBlackliststatus());
						blkmain.add(b2);
					}
				}
			}else{
				if(soleProp!=null){
					if(soleProp.equals("09")){
						cif = (List<Tbcifcorporate>)dbService.executeListHQLQuery("FROM Tbcifcorporate WHERE corporatename like :bname AND businesstype = '09' ORDER BY corporatename ASC", param);
					}else if(soleProp.equals("ALL")){
						cif = (List<Tbcifcorporate>)dbService.executeListHQLQuery("FROM Tbcifcorporate WHERE corporatename like :bname ORDER BY corporatename ASC", param);
					}
				}else{
					cif = (List<Tbcifcorporate>)dbService.executeListHQLQuery("FROM Tbcifcorporate WHERE corporatename like :bname AND businesstype NOT IN('09') ORDER BY corporatename ASC", param);
				}
				if(cif!=null){
					for(Tbcifcorporate cif2 : cif){
						param.put("cifno", cif2.getCifno());
						Tbcifmain c = (Tbcifmain)dbService.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno =:cifno", param);
						cifmain.add(c);
					}
				}
				aml = (List<Tbamlacorporate>)dbService.executeListHQLQuery("FROM Tbamlacorporate WHERE (corporatename like :bname AND (dateofincorporation BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:00') AND amlaliststatus='1') ORDER BY corporatename ASC", param);
				if(aml!=null){
					for(Tbamlacorporate aml2 : aml){
						Tbamlalistmain  a2 = new Tbamlalistmain();
						a2.setAmlalistid(aml2.getAmlalistid());
						a2.setCifno(aml2.getCifno());
						a2.setFullname(aml2.getCorporatename());
						a2.setAmlalistsource(aml2.getAmlalistsource());
						a2.setStatus(aml2.getAmlaliststatus());
						amlmain.add(a2);
					}
				}
				blk = (List<Tbblacklistcorporate>)dbService.executeListHQLQuery("FROM Tbblacklistcorporate WHERE (corporatename like :bname AND (dateofincorporation BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:00') AND blackliststatus='1') ORDER BY corporatename ASC", param);
				if(blk!=null){
					for(Tbblacklistcorporate blk2 : blk){
						Tbblacklistmain b2 = new Tbblacklistmain();
						b2.setBlacklistid(blk2.getBlacklistid());
						b2.setCifno(blk2.getCifno());
						b2.setFullname(blk2.getCorporatename());
						b2.setCreateddate(blk2.getDatecreated());
						b2.setBlacklistsource(blk2.getBlacklistsource());
						b2.setStatus(blk2.getBlackliststatus());
						blkmain.add(b2);
					}
				}				
			}
			
			if(cif!=null){
				if(cifmain!=null){
					for(Tbcifmain cifmain2 : cifmain){
						param.put("status", cifmain2.getCifstatus());
						Tbcodetable cifstatus = (Tbcodetable) dbService.executeUniqueHQLQuery("FROM Tbcodetable a WHERE a.id.codename ='CIFSTATUS' AND a.id.codevalue =:status", param);
						if(cifstatus!=null){
							cifmain2.setCifstatus(cifstatus.getDesc1());
						}
						param.put("type", cifmain2.getCiftype());
						Tbcodetable ciftype = (Tbcodetable) dbService.executeUniqueHQLQuery("FROM Tbcodetable a WHERE a.id.codename ='CIFTYPE' AND a.id.codevalue =:type", param);
						if(ciftype!=null){
							cifmain2.setCiftype(ciftype.getDesc1());
						}
					}
					form.setCif(cifmain);
				}
			}
			if(aml!=null){
				if(amlmain!=null){
					for(Tbamlalistmain amlmain2 : amlmain){
						param.put("status", amlmain2.getStatus());
						Tbcodetable amlstatus = (Tbcodetable) dbService.executeUniqueHQLQuery("FROM Tbcodetable a WHERE a.id.codename ='AMLASTATUS' AND a.id.codevalue =:status", param);
						if(amlstatus!=null){
							amlmain2.setStatus(amlstatus.getDesc1());
						}	
						param.put("type", amlmain2.getAmlalistsource());
						Tbcodetable source = (Tbcodetable) dbService.executeUniqueHQLQuery("FROM Tbcodetable a WHERE a.id.codename ='AMLALISTSOURCE' AND a.id.codevalue =:type", param);
						if(source!=null){
							amlmain2.setAmlalistsource(source.getDesc1());
						}	
					}
					form.setAmla(amlmain);
				}
			}
			if(blk!=null){
				if(blkmain!=null){
					for(Tbblacklistmain blkmain2 : blkmain){
						param.put("status", blkmain2.getStatus());
						Tbcodetable blkstatus = (Tbcodetable) dbService.executeUniqueHQLQuery("FROM Tbcodetable a WHERE a.id.codename ='BLACKLISTSTATUS' AND a.id.codevalue =:status", param);
						if(blkstatus!=null){
							blkmain2.setStatus(blkstatus.getDesc1());
						}
						param.put("type", blkmain2.getBlacklistsource());
						Tbcodetable source = (Tbcodetable) dbService.executeUniqueHQLQuery("FROM Tbcodetable a WHERE a.id.codename ='BLACKLISTSOURCE' AND a.id.codevalue =:type", param);
						if(source!=null){
							blkmain2.setBlacklistsource(source.getDesc1());
						}
					}
					form.setBlacklist(blkmain);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public String checkIfExactMatch(String cifno, String lname, String fname, Date dob, String businessname, Date incorporationdate,
			String tin, String sss, String streetno, String subdivision, String country, String province, String city,
			String barangay, String postalCode) {
		String flag = "failed";
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> param = new HashMap<String, Object>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String date = null;
		try {
			if(cifno!=null){
				param.put("cif", cifno);
				if(businessname!=null && incorporationdate!=null){
					date = formatter.format(incorporationdate);
					// Corporate
					if(tin!=null && sss!=null && streetno!=null && subdivision!=null && country!=null && province!=null && city!=null && barangay!=null && postalCode!=null){
						Tbcifcorporate corp = (Tbcifcorporate) dbService.executeUniqueHQLQueryMaxResultOne("FROM Tbcifcorporate WHERE cifno=:cif", param);
						if(corp!=null){
							String date2 = formatter.format(corp.getDateofincorporation());
							if((corp.getCorporatename().trim().equalsIgnoreCase(businessname.trim()))
									&& (date.equals(date2))
									&& (corp.getTin().trim().equals(tin.trim()))
									&& (corp.getSss().trim().equals(sss.trim()))
									&& (corp.getStreetno1().trim().equalsIgnoreCase(streetno.trim()))
									&& (corp.getSubdivision1().trim().equalsIgnoreCase(subdivision.trim()))
									&& (corp.getCountry1().trim().equals(country.trim()))
									&& (corp.getProvince1().trim().equals(province.trim()))
									&& (corp.getCity1().trim().equals(city.trim()))
									&& (corp.getBarangay1().trim().equalsIgnoreCase(barangay.trim()))
									&& (corp.getPostalcode1().trim().equals(postalCode.trim()))){
								flag = "success";
							}
						}
					}
				}else{
					if(lname!=null && fname!=null && dob!=null){
						date = formatter.format(dob);
						// Individual
						if(tin!=null && sss!=null && streetno!=null && subdivision!=null && country!=null && province!=null && city!=null && barangay!=null && postalCode!=null){
							Tbcifindividual indiv = (Tbcifindividual) dbService.executeUniqueHQLQueryMaxResultOne("FROM Tbcifindividual WHERE cifno=:cif", param);
							if(indiv!=null){
								String date2 = formatter.format(indiv.getDateofbirth());
								if(tin!=null && sss!=null && streetno!=null && subdivision!=null && country!=null && province!=null && city!=null && barangay!=null && postalCode!=null){
									if((indiv.getLastname().trim().equalsIgnoreCase(lname.trim()))
											&& (indiv.getFirstname().trim().equalsIgnoreCase(fname.trim()))
											&& (date.equals(date2))
											&& (indiv.getTin().trim().equals(tin.trim()))
											&& (indiv.getSss().trim().equals(sss.trim()))
											&& (indiv.getStreetno1().trim().equalsIgnoreCase(streetno.trim()))
											&& (indiv.getSubdivision1().trim().equalsIgnoreCase(subdivision.trim()))
											&& (indiv.getCountry1().trim().equals(country.trim()))
											&& (indiv.getProvince1().trim().equals(province.trim()))
											&& (indiv.getCity1().trim().equals(city.trim()))
											&& (indiv.getBarangay1().trim().equalsIgnoreCase(barangay.trim()))
											&& (indiv.getPostalcode1().trim().equals(postalCode.trim()))){
										flag = "success";
									}
								}
							}
						}
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
	public dedupeform dedupeIndivFinal(String lname, String fname, String mname, String suf, Date dob, Integer page,
			Integer maxresult, String customertype, String inquirytype, String businessname, Date incorporationdate) {
		dedupeform list = new dedupeform();
		DBService dbService = new DBServiceImplCIF();
		try {
			String dob2 = (dob == null ? "NULL" : ("'"+new SimpleDateFormat("yyyy-MM-dd").format(dob)+"'"));
			lname = (lname == null ? "NULL" : ("'"+lname+"'"));
			fname = (fname == null ? "NULL" : ("'"+fname+"'"));
			mname = (mname == null ? "NULL" : ("'"+mname+"'"));
			suf = (suf == null ? "NULL" : ("'"+suf+"'"));
			
			if(inquirytype!=null){
				if(inquirytype.equals("CIF")){
					//CIF
					String qcif = "EXEC sp_DedupeCIF @page="+page+", @maxresult="+maxresult+", @cifno=NULL, @lname="+lname+", @fname="+fname+", @mname="+mname+", @suf="+suf+", @dob="+dob2+", @businessname=NULL, @incorporationdate=NULL, @ispagingon='true', @customertype='"+customertype+"', @inquirytype='CIF' ";
					List<cifdedupeform> cif = (List<cifdedupeform>) dbService.execSQLQueryTransformer(qcif, null, cifdedupeform.class, 1);
					list.setCifform(cif);
				}
				if(inquirytype.equals("AMLA")){
					//AMLA
					String qamla = "EXEC sp_DedupeCIF @page="+page+", @maxresult="+maxresult+", @cifno=NULL, @lname="+lname+", @fname="+fname+", @mname="+mname+", @suf="+suf+", @dob="+dob2+", @businessname=NULL, @incorporationdate=NULL, @ispagingon='true', @customertype='"+customertype+"', @inquirytype='AMLA' ";
					List<amladedupeform> amla = (List<amladedupeform>) dbService.execSQLQueryTransformer(qamla, null, amladedupeform.class, 1);
					list.setAmlaform(amla);
				}
				if(inquirytype.equals("BLK")){
					//BLACKLIST
					String qblk = "EXEC sp_DedupeCIF @page="+page+", @maxresult="+maxresult+", @cifno=NULL, @lname="+lname+", @fname="+fname+", @mname="+mname+", @suf="+suf+", @dob="+dob2+", @businessname=NULL, @incorporationdate=NULL, @ispagingon='true', @customertype='"+customertype+"', @inquirytype='BLK' ";
					List<blacklistdedupeform> blk = (List<blacklistdedupeform>) dbService.execSQLQueryTransformer(qblk, null, blacklistdedupeform.class, 1);
					list.setBlacklistform(blk);	
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public InquiryCount dedupeIndivFinalCount(String lname, String fname, String mname, String suf, Date dob,
			Integer page, Integer maxresult, String customertype, String inquirytype) {
		InquiryCount count = new InquiryCount();
		Integer cifcount = 0;
		Integer amlacount = 0;
		Integer blkcount = 0;
		DBService dbService = new DBServiceImplCIF();
		try {
			String dob2 = (dob == null ? "NULL" : ("'"+new SimpleDateFormat("yyyy-MM-dd").format(dob)+"'"));
			lname = (lname == null ? "NULL" : ("'"+lname+"'"));
			fname = (fname == null ? "NULL" : ("'"+fname+"'"));
			mname = (mname == null ? "NULL" : ("'"+mname+"'"));
			suf = (suf == null ? "NULL" : ("'"+suf+"'"));
			
			if(inquirytype!=null){
				if(inquirytype.equals("CIF")){
					//CIF
					String qcif = "EXEC sp_DedupeCIF @page=NULL, @maxresult=NULL, @cifno=NULL, @lname="+lname+", @fname="+fname.trim()+", @mname="+mname+", @suf="+suf+", @dob="+dob2+", @businessname=NULL, @incorporationdate=NULL, @ispagingon='false', @customertype='"+customertype+"', @inquirytype='CIF' ";
					cifcount = (Integer) dbService.execSQLQueryTransformer(qcif, null, null, 0);
					count.setCif(cifcount);
				}
				if(inquirytype.equals("AMLA")){
					//AMLA
					String qamla = "EXEC sp_DedupeCIF @page=NULL, @maxresult=NULL, @cifno=NULL, @lname="+lname+", @fname="+fname.trim()+", @mname="+mname+", @suf="+suf+", @dob="+dob2+", @businessname=NULL, @incorporationdate=NULL, @ispagingon='false', @customertype='"+customertype+"', @inquirytype='AMLA' ";
					amlacount = (Integer) dbService.execSQLQueryTransformer(qamla, null, null, 0);
					count.setAmla(amlacount);
				}
				if(inquirytype.equals("BLK")){
					//BLACKLIST
					String qblk = "EXEC sp_DedupeCIF @page=NULL, @maxresult=NULL, @cifno=NULL, @lname="+lname+", @fname="+fname.trim()+", @mname="+mname+", @suf="+suf+", @dob="+dob2+", @businessname=NULL, @incorporationdate=NULL, @ispagingon='false', @customertype='"+customertype+"', @inquirytype='BLK' ";
					blkcount = (Integer) dbService.execSQLQueryTransformer(qblk, null, null, 0);
					count.setBlk(blkcount);	
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<MembershipDedupeForm> membershipDedupe(String lname, String fname, Date dob, String tin,
//			String sss, String streetno, String subdivision, String country, String province, String city,
//			String barangay, String postalCode, String losLink) {
//		
//		List<MembershipDedupeForm> list = new ArrayList<MembershipDedupeForm>();
//		DBService dbService = new DBServiceImplCIF();
//		Map<String, Object> param = new HashMap<String, Object>();
//		
//		try {
//			if(lname!=null && fname!=null){
//				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//				String date = null;
//				StringBuilder myQuery = new StringBuilder();
//				
//				param.put("lname", lname);
//				param.put("fname", fname);
//				param.put("losLink", losLink);
//				
//				myQuery.append("SELECT "
//						+ "(select branchname from "+ losLink + ".dbo.TBBRANCH WHERE branchcode = b.originatingbranch) AS originatingbranch, "
//						+ "b.cifno, b.fullname, b.dateofbirth, b.sss, b.tin, "
//						+ "(SELECT processname FROM "+ losLink + ".dbo.TBWORKFLOWPROCESS WHERE processid = b.cifstatus) as cifstatus, "
//						+ "d.desc1 as ciftype "
//						+ "FROM TBCIFINDIVIDUAL a "
//						+ "LEFT JOIN TBCIFMAIN b ON a.cifno = b.cifno "
//						+ "LEFT JOIN TBCODETABLE c ON b.cifstatus = c.codevalue "
//						+ "LEFT JOIN TBCODETABLE d ON b.ciftype = d.codevalue "
//						+ "WHERE a.lastname LIKE '%"+lname.trim()+"%' AND a.firstname LIKE '%"+fname.trim()+"%' "
//						+ "AND c.codename = 'CIFSTATUS' "
//						+ "AND d.codename = 'CIFTYPE' ");
//				if(dob!=null){
//					date = formatter.format(dob);
//					myQuery.append("AND (a.dateofbirth BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:00') ");
//				}
//				if(tin!=null){
//					param.put("tin", tin);
//				}
//				if(sss!=null){
//					param.put("sss", sss);
//					myQuery.append("AND a.sss LIKE '%"+sss.trim()+"%' ");
//				}
//				if(streetno!=null){
//					param.put("streetno", streetno.trim());
//					myQuery.append("AND a.streetno1 LIKE '%"+streetno.trim()+"%' ");
//				}
//				if(subdivision!=null){
//					param.put("subd", subdivision);
//					myQuery.append("AND a.subdivision1 LIKE '%"+subdivision.trim()+"%' ");
//				}
//				if(country!=null){
//					param.put("country", country);
//					myQuery.append("AND a.country1=:country ");
//				}
//				if(city!=null){
//					param.put("city", city);
//					myQuery.append("AND a.city1=:city ");
//				}
//				if(barangay!=null){
//					param.put("barangay", barangay.trim());
//					myQuery.append("AND a.barangay1 LIKE '%"+barangay.trim()+"%' ");
//				}	
//				if(postalCode!=null){
//					param.put("postalCode", postalCode.trim());
//					myQuery.append("AND a.postalcode1=:postalCode ");
//				}		
//				list = (List<MembershipDedupeForm>) dbService.execSQLQueryTransformer(myQuery.toString(), param,MembershipDedupeForm.class, 1);
//				}
//			
//		}catch(Exception e)
//			{
//				e.printStackTrace();
//			}
//		return list;
//	}

}
