package com.etel.branch;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

import com.coopdb.data.Tbbranch;
import com.coopdb.data.TbbranchId;
import com.coopdb.data.Tbcompany;
import com.etel.branch.forms.BranchForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.company.forms.CompanyForm;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

/**
 * @author Kevin
 */

public class BranchServiceImpl implements BranchService {
	SecurityService serviceS = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private static final Logger logger = Logger.getLogger(BranchServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override

	/** Get List of Branch by company */

	public List<BranchForm> getListOfBranchByCompany(String companyCode) {
		List<BranchForm> form = new ArrayList<BranchForm>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (companyCode != null) {
				params.put("companyCode", companyCode);
				List<Tbbranch> list = (List<Tbbranch>) dbService
						.executeListHQLQuery("FROM Tbbranch WHERE coopcode=:companyCode", params);
				if (list == null) {
					return null;
				} else {
					for (Tbbranch branch : list) {
						BranchForm branchForm = new BranchForm();
						// branchForm.setBranchid(branch.getId().getBranchid());
						branchForm.setBranchcode(branch.getBranchcode());
						branchForm.setCompanycode(branch.getCompanycode());
						branchForm.setBranchname(branch.getBranchname());
						branchForm.setBranchaddress(branch.getBranchaddress());
						branchForm.setPhoneno(branch.getPhoneno());
						branchForm.setFaxno(branch.getFaxno());
						branchForm.setBranchstatus(branch.getBranchstatus());
						branchForm.setIsopen(branch.getIsopen());
						branchForm.setBranchclassification(branch.getBranchclassification());
						branchForm.setCurrentbusinessdate(branch.getCurrentbusinessdate());
						branchForm.setNextbusinessdate(branch.getNextbusinessdate());
						branchForm.setDatecreated(branch.getDatecreated());
						branchForm.setCreatedby(branch.getCreatedby());
						branchForm.setDateupdated(branch.getDateupdated());
						branchForm.setUpdatedby(branch.getUpdatedby());
						branchForm.setCoopcode(branch.getCoopcode());
						form.add(branchForm);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	/** Get List of Branch */
	@SuppressWarnings("unchecked")
	public List<BranchForm> getListOfBranchbyCompany(String companycode) {
		// TODO Auto-generated method stub

		List<BranchForm> form = new ArrayList<BranchForm>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();

		try {
			params.put("code", companycode);
			List<Tbbranch> list = (List<Tbbranch>) dbService.executeListHQLQuery("FROM Tbbranch WHERE coopcode=:code",
					params);
			if (list != null) {
				for (Tbbranch branch : list) {
					BranchForm brnch = new BranchForm();
					brnch.setCoopcode(branch.getCoopcode());
					brnch.setBranchcode(branch.getBranchcode());
					brnch.setBranchname(branch.getBranchname());
					brnch.setBranchaddress(branch.getBranchaddress());
					brnch.setBranchclassification(branch.getBranchclassification());
					brnch.setBranchstatus(branch.getBranchstatus());
					brnch.setCompanycode(branch.getCompanycode());
					brnch.setCreatedby(branch.getCreatedby());
					brnch.setCurrentbusinessdate(new Date());
					brnch.setDatecreated(new Date());
					brnch.setDateupdated(branch.getDateupdated());
					brnch.setFaxno(branch.getFaxno());
					brnch.setIsopen(branch.getIsopen());
					brnch.setNextbusinessdate(branch.getNextbusinessdate());
					brnch.setPhoneno(branch.getPhoneno());
					brnch.setUpdatedby(branch.getUpdatedby());

					form.add(brnch);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	/** Display Branch Details */
	@SuppressWarnings("unchecked")
	@Override
	public List<BranchForm> displayBranchDetails(String branchname) {
		// TODO Auto-generated method stub

		List<BranchForm> branch = new ArrayList<BranchForm>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("branchname", branchname);
		try {
			List<Tbbranch> list = (List<Tbbranch>) dbService
					.executeListHQLQuery("FROM Tbbranch WHERE branchname=:branchname", params);
			if (list != null) {
				for (Tbbranch branchval : list) {
					BranchForm form = new BranchForm();
					form.setBranchname(branchval.getBranchname());
					form.setBranchcode(branchval.getBranchcode());
					form.setBranchaddress(branchval.getBranchaddress());
					form.setBranchclassification(branchval.getBranchclassification());
					form.setBranchstatus(branchval.getBranchstatus());
					form.setCompanycode(branchval.getCompanycode());
					form.setCreatedby(serviceS.getUserName());
					form.setCurrentbusinessdate(new Date());
					form.setDatecreated(new Date());
					form.setDateupdated(branchval.getDateupdated());
					form.setFaxno(branchval.getFaxno());
					form.setIsopen(branchval.getIsopen());
					form.setNextbusinessdate(branchval.getNextbusinessdate());
					form.setPhoneno(branchval.getPhoneno());
					form.setUpdatedby(branchval.getUpdatedby());

					branch.add(form);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR" + e.toString());
		}

		return branch;
	}

	/** Add Branch */
	@Override
	public String addBranch(BranchForm form) {
		// TODO Auto-generated method stub

		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("branchcode", form.getBranchcode());
			params.put("branchname", form.getBranchname());
			@SuppressWarnings("unchecked")
			List<Tbbranch> list = (List<Tbbranch>) dbService
					.executeListHQLQuery("FROM Tbbranch WHERE branchcode=:branchcode", params);
			if (list.size() > 0 || !list.isEmpty()) {
				return "existing";
			} else {
				Tbbranch branch = new Tbbranch();
				branch.setBranchcode(form.getBranchcode());
				branch.setBranchname(form.getBranchname());
				branch.setBranchaddress(form.getBranchaddress());
				branch.setBranchclassification(form.getBranchclassification());
				branch.setBranchstatus(form.getBranchstatus());
				branch.setCompanycode(form.getCompanycode());
				branch.setCreatedby(serviceS.getUserName());
				branch.setCurrentbusinessdate(form.getCurrentbusinessdate());
				branch.setDatecreated(new Date());
				branch.setDateupdated(form.getDateupdated());
				branch.setFaxno(form.getFaxno());
				branch.setIsopen(form.getIsopen());
				branch.setNextbusinessdate(form.getNextbusinessdate());
				branch.setPhoneno(form.getPhoneno());
				branch.setUpdatedby(form.getUpdatedby());
				branch.setCoopcode(form.getCoopcode());
				branch.setMcacctno(form.getMcaccount());
				branch.setGcacctno(form.getGcaccount());
				// Modified Oct.23,2018 - Added CASA - Ced>>
				branch.setSeqmerch(0);
				branch.setSeqno(0);
				branch.setSeqoverride(0);
				branch.setSeqtdc(0);
				branch.setSequser(0);
				branch.setSeqyy(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).substring(2, 4));
				branch.setCoopcode(form.getCoopcode());
				// Modified Oct.23,2018 - Added CASA - Ced <<
				if (dbService.save(branch)) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR" + e.toString());
		}

		return flag;

	}

	/** Update Branch Details */
	@Override
	public String updateBranch(BranchForm form) {
		// TODO Auto-generated method stub

		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("branchcode", form.getBranchcode());
		params.put("branchname", form.getBranchname());
		System.out.println(form.getCompanycode());
		try {
			Tbbranch branch = (Tbbranch) dbService.executeUniqueHQLQuery("FROM Tbbranch a WHERE branchcode=:branchcode",
					params);
			if (branch != null) {
				branch.setBranchcode(form.getBranchcode());
				branch.setBranchname(form.getBranchname());
				branch.setBranchaddress(form.getBranchaddress());
				branch.setBranchclassification(form.getBranchclassification());
				branch.setBranchstatus(form.getBranchstatus());
				branch.setCompanycode(form.getCompanycode());
				branch.setCurrentbusinessdate(form.getCurrentbusinessdate());
				branch.setDatecreated(form.getDatecreated());
				branch.setCreatedby(form.getCreatedby());
				branch.setDateupdated(new Date());
				branch.setFaxno(form.getFaxno());
				branch.setIsopen(form.getIsopen());
				branch.setNextbusinessdate(form.getNextbusinessdate());
				branch.setPhoneno(form.getPhoneno());
				branch.setUpdatedby(serviceS.getUserName());
				branch.setCoopcode(form.getCoopcode());
				branch.setMcacctno(form.getMcaccount());
				branch.setGcacctno(form.getGcaccount());

				if (dbService.saveOrUpdate(branch)) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR" + e.toString());
		}

		return flag;

	}

	/** Delete Branch Details */
	@Override
	public String deleteBranch(BranchForm form) {
		// TODO Auto-generated method stub

		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("branchcode", form.getBranchcode());
		params.put("branchname", form.getBranchname());
		Tbbranch branch = new Tbbranch();
		try {
			branch = (Tbbranch) dbService.executeUniqueHQLQuery(
					"FROM Tbbranch a WHERE a.id.branchcode=:branchcode AND a.id.branchname=:branchname", params);
			if (dbService.delete(branch)) {
				flag = "successful";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;

	}

	/** Search Branch Details */
	@SuppressWarnings("unchecked")
	@Override
	public List<BranchForm> searchBranch(String search) {
		// TODO Auto-generated method stub

		List<BranchForm> branchlist = new ArrayList<BranchForm>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("search", "%" + search + "%");
		try {
			List<Tbbranch> branch = (List<Tbbranch>) dbService.executeListHQLQuery(
					"FROM Tbbranch a where a.id.branchcode LIKE :search or a.id.branchname LIKE :search or a.branchclassification LIKE :search or a.branchaddress LIKE :search",
					params);
			for (Tbbranch br : branch) {
				BranchForm form = new BranchForm();
				form.setBranchcode(br.getBranchcode());
				form.setBranchname(br.getBranchname());
				form.setBranchaddress(br.getBranchaddress());
				form.setBranchclassification(br.getBranchclassification());
				form.setBranchstatus(br.getBranchstatus());
				form.setCompanycode(br.getCompanycode());
				form.setCreatedby(br.getCreatedby());
				form.setCurrentbusinessdate(br.getCurrentbusinessdate());
				form.setDatecreated(br.getDatecreated());
				form.setDateupdated(br.getDateupdated());
				form.setFaxno(br.getFaxno());
				form.setIsopen(br.getIsopen());
				form.setNextbusinessdate(br.getNextbusinessdate());
				form.setPhoneno(br.getPhoneno());
				form.setUpdatedby(br.getUpdatedby());

				branchlist.add(form);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return branchlist;
	}

	// Get all Company
	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyForm> getAllCompanyList() {
		// TODO Auto-generated method stub

		List<CompanyForm> companylistForm = new ArrayList<CompanyForm>();
		List<Tbcompany> companylist = new ArrayList<Tbcompany>();
		DBService dbService = new DBServiceImpl();

		try {
			companylist = (List<Tbcompany>) dbService.executeListHQLQuery("FROM Tbcompany", null);
			if (companylist != null) {
				CompanyForm co = new CompanyForm();
				System.out.println("com size: " + companylist.size());
				for (Tbcompany c : companylist) {
					co.setCompanyname(c.getId().getCompanyname());
					System.out.println(c.getId().getCompanyname());
					companylistForm.add(co);
				}
			}
			logger.info("Get list of Companyname.");
		} catch (Exception e) {
			logger.error("ERROR" + e.toString());
		}
		return companylistForm;
	}

	// Get All Branch
	@SuppressWarnings("unchecked")
	@Override
	public List<BranchForm> getAllBranchList() {
		// TODO Auto-generated method stub

		List<BranchForm> branchForm = new ArrayList<BranchForm>();
		List<Tbbranch> branchList = new ArrayList<Tbbranch>();
		DBService dbService = new DBServiceImpl();

		try {
			branchList = (List<Tbbranch>) dbService.executeListHQLQuery("FROM Tbbranch", null);
			if (branchList != null) {
				// BranchForm br = new BranchForm();
				for (Tbbranch b : branchList) {
					BranchForm br = new BranchForm();
					br.setBranchname(b.getBranchname());
					branchForm.add(br);
				}
			}
			logger.info("Get List of Branch");
		} catch (Exception e) {
			logger.error("ERROR" + e.toString());
		}
		return branchForm;
	}

	@SuppressWarnings("unchecked")
	@Override

	public List<BranchForm> getListOfBranch() {
		List<BranchForm> form = new ArrayList<BranchForm>();
		DBService dbService = new DBServiceImpl();
		try {
			List<Tbbranch> list = (List<Tbbranch>) dbService.executeListHQLQuery("FROM Tbbranch", null);
			if (list != null) {
				for (Tbbranch branch : list) {
					BranchForm bf = new BranchForm();
					bf.setBranchcode(branch.getBranchcode());
					bf.setBranchname(branch.getBranchname());
					bf.setBranchaddress(branch.getBranchaddress());
					bf.setPhoneno(branch.getPhoneno());
					bf.setFaxno(branch.getFaxno());
					bf.setIsopen(branch.getIsopen());
					bf.setBranchclassification(branch.getBranchclassification());
					bf.setCurrentbusinessdate(branch.getCurrentbusinessdate());
					bf.setNextbusinessdate(branch.getNextbusinessdate());
					bf.setBranchstatus(branch.getBranchstatus());
					bf.setDatecreated(new Date());
					bf.setCreatedby(serviceS.getUserName());
					bf.setDateupdated(branch.getDateupdated());
					bf.setUpdatedby(branch.getUpdatedby());
					bf.setMcaccount(branch.getMcacctno());
					bf.setGcaccount(branch.getGcacctno());
					form.add(bf);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	// DANIELFESALBON AUGUST|13|2018
	public TbbranchId getBranch(String branchcode) {
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			System.out.println("GET BRANCH");
			params.put("code", branchcode);
			TbbranchId b = (TbbranchId) dbService.execSQLQueryTransformer(
					"SELECT branchcode, branchname FROM Tbbranch WHERE branchcode=:code", params, TbbranchId.class, 0);
			System.out.println(b.getBranchname());
			return b;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<BranchForm> getCoopBranches(String coopcode, String companycode) {
		// TODO Auto-generated method stub
		List<BranchForm> list = new ArrayList<BranchForm>();
		try {
			DBService dbService = new DBServiceImpl();
			Map<String, Object> params = HQLUtil.getMap();
			params.put("coopcode", coopcode);
			params.put("companycode", companycode);
			@SuppressWarnings("unchecked")
			List<Tbbranch> br = (List<Tbbranch>) dbService
					.executeListHQLQuery("FROM Tbbranch WHERE coopcode=:coopcode AND companycode=:companycode", params);
			if (br != null) {
				for (Tbbranch b : br) {
					BranchForm n = new BranchForm();
					n.setBranchaddress(b.getBranchaddress());
					n.setBranchcode(b.getBranchcode());
					n.setBranchname(b.getBranchname());
					n.setCoopcode(b.getCoopcode());
					n.setBranchstatus(b.getBranchstatus());
					n.setIsopen(b.getIsopen());
					n.setBranchclassification(b.getBranchclassification());
					n.setNextbusinessdate(b.getNextbusinessdate());
					n.setCurrentbusinessdate(b.getCurrentbusinessdate());
					n.setMcaccount(b.getMcacctno());
					n.setGcaccount(b.getGcacctno());
					n.setPhoneno(b.getPhoneno());
					n.setFaxno(b.getFaxno());
					n.setCompanycode(b.getCompanycode());
					list.add(n);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// Modified Nov.05,2018 - Added CASA - Ced>>
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getListofBranchCodes() {
		DBService dbService = new DBServiceImpl();
		List<String> branchcodes = new ArrayList<String>();
		try {
			branchcodes = dbService.executeListSQLQuery("SELECT branchcode FROM TBBRANCH", null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return branchcodes;
	}

	@Override
	public String updateClearingCutOffTime(String hour, String minutes) {
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("branchcode", UserUtil.getUserByUsername(serviceS.getUserName()).getBranchcode());
		params.put("time", hour.concat(minutes));
		try {
			if (dbService.executeUpdate("UPDATE Tbbranch SET clearingcutofftime=:time WHERE branchcode =:branchcode ",
					params) == 1)
				return "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "failed";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BranchForm> getListOfBranch(String deactivated) {
		List<BranchForm> branchList = new ArrayList<BranchForm>();
		List<Tbbranch> list = new ArrayList<Tbbranch>();
		DBService dbService = new DBServiceImpl();
		try {
			if (deactivated == null)
				list = (List<Tbbranch>) dbService.executeListHQLQuery("FROM Tbbranch", null);
			else
				list = (List<Tbbranch>) dbService.executeListHQLQuery("FROM Tbbranch WHERE isOpen = 0", null);
			if (list != null) {
				for (Tbbranch branch : list) {
					BranchForm form = new BranchForm();
					form.setBranchcode(branch.getBranchcode());
					form.setBranchname(branch.getBranchname());
					form.setBranchaddress(branch.getBranchaddress());
					form.setBranchclassification(branch.getBranchclassification());
					form.setBranchstatus(branch.getBranchstatus());
					form.setCompanycode(branch.getCompanycode());
					form.setCreatedby(branch.getCreatedby());
					form.setCurrentbusinessdate(branch.getCurrentbusinessdate());
					form.setDatecreated(branch.getDatecreated());
					form.setDateupdated(branch.getDateupdated());
					form.setFaxno(branch.getFaxno());
					form.setIsopen(branch.getIsopen());
					form.setNextbusinessdate(branch.getNextbusinessdate());
					form.setPhoneno(branch.getPhoneno());
					form.setUpdatedby(branch.getUpdatedby());
					branchList.add(form);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return branchList;
	}

	@Override
	public Tbbranch getBranchDetails(String branchcode) {
		Tbbranch row = new Tbbranch();
		DBService dbServiceCoop = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(branchcode!=null) {
				params.put("branchcode", branchcode);
				row = (Tbbranch) dbServiceCoop.executeUniqueHQLQuery("FROM Tbbranch WHERE branchcode=:branchcode", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

}
