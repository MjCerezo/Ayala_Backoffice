package com.etel.defaultusers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.defaultusers.forms.ApprovalMatrixForm;
import com.etel.defaultusers.forms.CompanyName;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;
import com.coopdb.data.Tbapprovalmatrix;
import com.coopdb.data.Tbdefaultusers;
import com.coopdb.data.Tbuser;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class DefaultUsersServiceImpl implements DefaultUsersService {
	SecurityService serviceS = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private DBService dbServiceLOS = new DBServiceImpl();
	private Map<String, Object> params = HQLUtil.getMap();
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbdefaultusers> displayDefaultUsers() {
		List<Tbdefaultusers> list = new ArrayList<Tbdefaultusers>();
		try {
			list = (List<Tbdefaultusers>) dbServiceLOS.executeListHQLQuery("FROM Tbdefaultusers", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}
	/**
	 * Get List of Username per Role, company<br>
	 * Last Modified Dec 12, 2017 -Kevin
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbuser> listUsernamePerRole(String role, String companycode) {
		List<Tbuser> list = new ArrayList<Tbuser>();
		try {
			if (role != null) {
				params.put("role", role);
				String hql = "SELECT a.username, b.fullname FROM Tbuserroles a INNER JOIN Tbuser b ON a.username=b.username AND a.roleid=:role";
				
//				if(companycode != null){
//					params.put("companycode", companycode);
//					hql = hql + " AND b.companycode=:companycode";
//				}
				list = (List<Tbuser>) dbServiceLOS.execSQLQueryTransformer(hql, params,Tbuser.class, 1);
				if (list != null) {
					return list;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String saveUpdateDefaultUsers(Tbdefaultusers defaultusers) {
		System.out.println(defaultusers.getBisupervisor());
		String result = "failed";
		Tbdefaultusers users = new Tbdefaultusers();
		params.put("companycode", defaultusers.getCompanycode());
		try {
			users = (Tbdefaultusers) dbServiceLOS.executeUniqueHQLQuery("FROM Tbdefaultusers WHERE companycode=:companycode", params);
			if (users != null) {
				users.setDateupdated(new Date());
				users.setUpdatedby(serviceS.getUserName());
				params.put("companycode", users.getCompanycode());
				
				users.setCompanycode(defaultusers.getCompanycode());
				
				//Update Appraisal Head
				if(defaultusers.getAppraisalsupervisor() != null){
					users.setAppraisalsupervisor(defaultusers.getAppraisalsupervisor());
					dbServiceLOS.executeUpdate("UPDATE Tbappraisalreportmain SET reviewedby='"+defaultusers.getAppraisalsupervisor()+"', dateupdated=GETDATE() WHERE status='2' AND companycode=:companycode", params);
					dbServiceLOS.executeUpdate("UPDATE Tbcolappraisalrequest SET assignedappsupervisor='"+defaultusers.getAppraisalsupervisor()+"' WHERE status IN ('0','1','2','6') AND companycode=:companycode", params);
				}
				
				//Update BIS
				if(defaultusers.getBisupervisor() != null){
					users.setBisupervisor(defaultusers.getBisupervisor());
					dbServiceLOS.executeUpdate("UPDATE Tbbireportmain SET reviewedby='"+defaultusers.getBisupervisor()+"', dateupdated=GETDATE() WHERE status='2' AND companycode=:companycode", params);
					dbServiceLOS.executeUpdate("UPDATE Tbbirequest SET assignedbisupervisor='"+defaultusers.getBisupervisor()+"' WHERE status IN ('0','1','2','6') AND companycode=:companycode", params);
				}
				//Update CIS
				if(defaultusers.getCisupervisor() != null){
					users.setCisupervisor(defaultusers.getCisupervisor());
					dbServiceLOS.executeUpdate("UPDATE Tbcireportmain SET reviewedby='"+defaultusers.getCisupervisor()+"', dateupdated=GETDATE() WHERE status='2' AND companycode=:companycode", params);
					dbServiceLOS.executeUpdate("UPDATE Tbcirequest SET assignedcisupervisor='"+defaultusers.getCisupervisor()+"' WHERE status IN ('0','1','2','6') AND companycode=:companycode", params);
				}
				
				//Update Eval Head Commercial
				if(defaultusers.getEvaluatorheadc() != null){
					users.setEvaluatorheadc(defaultusers.getEvaluatorheadc());
					dbServiceLOS.executeUpdate("UPDATE Tbevalreport SET assignedevaluationhead='"+defaultusers.getEvaluatorheadc()+"' WHERE status IN('0','1','3') AND customertype IN('2','3') AND companycode=:companycode", params);
				}
				//Update Eval Head Retail
				if(defaultusers.getEvaluatorheadr() != null){
					users.setEvaluatorheadr(defaultusers.getEvaluatorheadr());
					dbServiceLOS.executeUpdate("UPDATE Tbevalreport SET assignedevaluationhead='"+defaultusers.getEvaluatorheadr()+"' WHERE status IN('0','1','3') AND customertype='1' AND companycode=:companycode", params);
				}
				
				//Update Doc Head
				if(defaultusers.getDocumentationhead() != null){
					users.setDocumentationhead(defaultusers.getDocumentationhead());
					dbServiceLOS.executeUpdate("UPDATE Tblstapp SET assigneddochead='"+defaultusers.getDocumentationhead()+"' WHERE documentationstatus IN('0','1','3') AND companycode=:companycode", params);
				}
				users.setBisupervisor(defaultusers.getBisupervisor());
				users.setAppraisalsupervisor(defaultusers.getAppraisalsupervisor());
				users.setCisupervisor(defaultusers.getCisupervisor());
				users.setEvaluatorheadr(defaultusers.getEvaluatorheadr());
				users.setDocumentationhead(defaultusers.getDocumentationhead());
				users.setReleasingapprover(defaultusers.getReleasingapprover());
				users.setBookingofficer(defaultusers.getBookingofficer());
				users.setSecadmin(defaultusers.getSecadmin());
				users.setSystemadmin(defaultusers.getSystemadmin());
				users.setDateupdated(new Date());
				users.setUpdatedby(UserUtil.securityService.getUserName());
				if (dbServiceLOS.saveOrUpdate(users)) {
					result = "success";
				}
			}else{
				if (dbServiceLOS.save(defaultusers)) {
					result = "success";
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<CompanyName> listCompany() {
		List<CompanyName> list = new ArrayList<CompanyName>();
		try {
			list = (List<CompanyName>) dbServiceLOS.execSQLQueryTransformer("SELECT c.coopcode as companycode, c.coopname as companyname "
					+ "FROM TBCOOPERATIVE c LEFT JOIN TBDEFAULTUSERS d on c.coopcode = d.companycode", null,
					CompanyName.class, 1);
			if (list != null) {
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String deleteDefaultUsers(String companycode) {
		String flag = "failed";
		params.put("companycode", companycode);
		try {
			int res = dbServiceLOS.executeUpdate("DELETE FROM Tbdefaultusers WHERE companycode=:companycode", params);
			if (res > 0) {
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String saveOrUpdateApprovalMatrix(ApprovalMatrixForm form) {
		// TODO SAVE OR UPDATE APPROVALMATRIX
		String result = "failed";
		Tbapprovalmatrix record = new Tbapprovalmatrix();
		try {
			if (form.getId() != null) {
				params.put("id", form.getId());
				record = (Tbapprovalmatrix) dbServiceLOS.executeUniqueHQLQuery("FROM Tbapprovalmatrix WHERE id=:id",
						params);
				if (record != null) {
					// UPDATE
					record.setLevel1approver(form.getLevel1approver());
					record.setLevel2approver(form.getLevel2approver());
					record.setLevel3approver(form.getLevel3approver());
					record.setLevel1limit(form.getLevel1limit());
					record.setLevel2limit(form.getLevel2limit());
					record.setLevel3limit(form.getLevel3limit());
					record.setLevel1rule(form.getLevel1rule());
					record.setLevel2rule(form.getLevel2rule());
					record.setLevel3rule(form.getLevel3rule());
					record.setLevel1requiredapproval(form.getLevel1requiredapproval());
					record.setLevel2requiredapproval(form.getLevel2requiredapproval());
					record.setLevel3requiredapproval(form.getLevel3requiredapproval());
					record.setLevel1requiredrejected(form.getLevel1requiredrejected());
					record.setLevel2requiredrejected(form.getLevel2requiredrejected());
					record.setLevel3requiredrejected(form.getLevel3requiredrejected());
					record.setUpdatedby(serviceS.getUserName());
					record.setLastupdated(new Date());

					record.setLevel4approver(form.getLevel4approver());
					record.setLevel4limit(form.getLevel4limit());
					record.setLevel4rule(form.getLevel4rule());
					record.setLevel4requiredapproval(form.getLevel4requiredapproval());
					record.setLevel4requiredrejected(form.getLevel4requiredrejected());

					if (dbServiceLOS.update(record)) {
						result = "success";
					}
				}
			} else {
				params.put("txtype", form.getTransactiontype());
				record = (Tbapprovalmatrix) dbServiceLOS
						.executeUniqueHQLQueryMaxResultOne("FROM Tbapprovalmatrix WHERE transactiontype=:txtype", params);
				if (record != null) {
					result = "existing";
				} else {
					// CREATE NEW RECORD
					Tbapprovalmatrix newrecord = new Tbapprovalmatrix();
					newrecord.setCreatedby(serviceS.getUserName());
					newrecord.setDatecreated(new Date());
					newrecord.setTransactiontype(form.getTransactiontype());
					newrecord.setLoanproduct(form.getLoanproduct());
					newrecord.setLevel1approver(form.getLevel1approver());
					newrecord.setLevel2approver(form.getLevel2approver());
					newrecord.setLevel3approver(form.getLevel3approver());
					newrecord.setLevel1limit(form.getLevel1limit());
					newrecord.setLevel2limit(form.getLevel2limit());
					newrecord.setLevel3limit(form.getLevel3limit());
					newrecord.setLevel1rule(form.getLevel1rule());
					newrecord.setLevel2rule(form.getLevel2rule());
					newrecord.setLevel3rule(form.getLevel3rule());
					newrecord.setLevel1requiredapproval(form.getLevel1requiredapproval());
					newrecord.setLevel2requiredapproval(form.getLevel2requiredapproval());
					newrecord.setLevel3requiredapproval(form.getLevel3requiredapproval());
					newrecord.setLevel1requiredrejected(form.getLevel1requiredrejected());
					newrecord.setLevel2requiredrejected(form.getLevel2requiredrejected());
					newrecord.setLevel3requiredrejected(form.getLevel3requiredrejected());

					newrecord.setLevel4approver(form.getLevel4approver());
					newrecord.setLevel4limit(form.getLevel4limit());
					newrecord.setLevel4rule(form.getLevel4rule());
					newrecord.setLevel4requiredapproval(form.getLevel4requiredapproval());
					newrecord.setLevel4requiredrejected(form.getLevel4requiredrejected());
					if (dbServiceLOS.save(newrecord)) {
						result = "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public Tbapprovalmatrix getApprovalMatrixRecord(Integer id) {
		// TODO GET APPROVAL RECORD
		Tbapprovalmatrix details = new Tbapprovalmatrix();
		try{
			if(id != null){
				details = (Tbapprovalmatrix) dbServiceLOS.executeUniqueHQLQuery("FROM Tbapprovalmatrix WHERE id=:id", params);
			}
			else{
				System.out.println("ID IS NULL");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return details;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbapprovalmatrix> getListOfApprovalMatrix() {
		// TODO GET LIST OF APPROVAL MATRIX
		List<Tbapprovalmatrix> list = new ArrayList<Tbapprovalmatrix>();
		try {
			list = (List<Tbapprovalmatrix>) dbServiceLOS.executeListHQLQuery("FROM Tbapprovalmatrix",params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String deleteApprovalMatrix(Integer id) {
		String result = "failed";
		try {
			if (id != null) {
				params.put("id", id);
				Tbapprovalmatrix record = new Tbapprovalmatrix();
				record = (Tbapprovalmatrix) dbServiceLOS.executeUniqueHQLQuery("FROM Tbapprovalmatrix  WHERE id=:id",
						params);
				if (record != null) {
					if (dbServiceLOS.delete(record)) {
						result = "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
