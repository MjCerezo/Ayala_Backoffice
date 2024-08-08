package com.etel.lam;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cifsdb.data.Tbmanagement;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.common.service.DBServiceImplLOS;
import com.etel.lam.forms.CFGroupExposureForm;
import com.etel.lam.forms.FinancialStatementListForm;
import com.etel.lam.forms.LAMAPAForm;
import com.etel.lam.forms.LAMDMSHistoryForm;
import com.etel.lam.forms.OtherIncomeExpenseForm;
import com.etel.utils.HQLUtil;
import com.etel.utils.LoggerUtil;
import com.etel.utils.UserUtil;
import com.loansdb.data.Tbapaotherincomeexpense;
import com.loansdb.data.Tbapprovedlamdms;
import com.loansdb.data.TbapprovedlamdmsId;
import com.loansdb.data.Tbcfcompany;
import com.loansdb.data.Tbcfcoobligor;
import com.loansdb.data.Tbcfcovenants;
import com.loansdb.data.Tbcfdetails;
import com.loansdb.data.Tbcftermconditions;
import com.loansdb.data.Tbcicreditcheck;
import com.loansdb.data.Tbcovenants;
import com.loansdb.data.Tbdocspercf;
import com.loansdb.data.Tbdocspercfapp;
import com.loansdb.data.Tbfinancialstatements;
import com.loansdb.data.Tbfsmain;
import com.loansdb.data.Tblamapa;
import com.loansdb.data.Tblamborrowerprofile;
import com.loansdb.data.Tblamcompany;
import com.loansdb.data.Tblamcoobligor;
import com.loansdb.data.Tblamcorporateprofile;
import com.loansdb.data.Tblamcovenants;
import com.loansdb.data.Tblamdocumentation;
import com.loansdb.data.Tblamloandetails;
import com.loansdb.data.Tblamothertermconditions;
import com.loansdb.data.Tblamrationalerecomm;
import com.loansdb.data.Tblamriskprofile;
import com.loansdb.data.Tblstapp;

public class LAMServiceImpl implements LAMService {

	private DBService dbServiceLOS = new DBServiceImplLOS();
	private DBService dbServiceCIF = new DBServiceImplCIF();
	private Map<String, Object> params = HQLUtil.getMap();

	/**
	 * --Get Financial Statement Details by Appno and ID--
	 * @author Kevin (08.11.2018)
	 * @return form  = {@link Tbfinancialstatements}
	 * */
	@Override
	@Deprecated
	public Tbfinancialstatements getFSDetailsByAppNoAndID(String appno, Integer fsid) {
		// TODO Auto-generated method stub
		Tbfinancialstatements fs = new Tbfinancialstatements();
//		try {
//			if(appno != null && fsid != null){
//				params.put("appno", appno);
//				params.put("fsid", fsid);
//				fs = (Tbfinancialstatements) dbServiceLOS.executeUniqueHQLQuery("FROM Tbfinancialstatements WHERE cfappno=:appno AND fsid=:fsid", params);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return fs;
	}

	/**
	 * -- Save or Update Financial Statement Details--
	 * @author Kevin (08.11.2018)
	 * @return String = success otherwise failed
	 * */
	@Override
	@Deprecated
	public String saveOrUpdateFS(Tbfinancialstatements fs) {
		// TODO Auto-generated method stub
		String flag = "failed";
//		try {
//			Calendar cal = Calendar.getInstance();
//			if(fs != null && fs.getYear() != null){
//				cal.setTime(fs.getYear());
//				cal.set(Calendar.HOUR, 0);
//				cal.set(Calendar.MINUTE, 0);
//				cal.set(Calendar.SECOND, 0);
//				cal.set(Calendar.HOUR_OF_DAY, 0);
//				fs.setYear(cal.getTime());
//			}
//
//			if(fs.getFsid() == null){
//				fs.setCreatedby(UserUtil.securityService.getUserName());
//				fs.setDatecreated(new Date());
//				if(dbServiceLOS.save(fs)){
//					flag = "success";
//				}
//			}else{
//				params.put("appno", fs.getCfappno());
//				params.put("fsid", fs.getFsid());
//				Tbfinancialstatements fsdetails = (Tbfinancialstatements) dbServiceLOS.executeUniqueHQLQuery("FROM Tbfinancialstatements WHERE cfappno=:appno AND fsid=:fsid", params);
//				if(fsdetails != null){
//					fsdetails.setYear(fs.getYear());
//					fsdetails.setAuditoropinion(fs.getAuditoropinion());
//					fsdetails.setBscashequivalents(fs.getBscashequivalents());
//					fsdetails.setBstradereceivable(fs.getBstradereceivable());
//					fsdetails.setBsinventories(fs.getBsinventories());
//					fsdetails.setBscurrentassets(fs.getBscurrentassets());
//					fsdetails.setBstotalassets(fs.getBstotalassets());
//					fsdetails.setBstradepayable(fs.getBstradepayable());
//					fsdetails.setBscurrentportiondebt(fs.getBscurrentportiondebt());
//					fsdetails.setBscurrentliabilities(fs.getBscurrentliabilities());
//					fsdetails.setBspayablerelatedparty(fs.getBspayablerelatedparty());
//					fsdetails.setBstotalliabilities(fs.getBstotalliabilities());
//					fsdetails.setBsnetworth(fs.getBsnetworth());
//					fsdetails.setBsnetworkingcapital(fs.getBsnetworkingcapital());
//					fsdetails.setIssales(fs.getIssales());
//					fsdetails.setIscostgoodssold(fs.getIscostgoodssold());
//					fsdetails.setIsoperatingexpenses(fs.getIsoperatingexpenses());
//					fsdetails.setIsearningsbefore(fs.getIsearningsbefore());
//					fsdetails.setIsfinancecost(fs.getIsfinancecost());
//					fsdetails.setIsnetincome(fs.getIsnetincome());
//					fsdetails.setGrowthrate(fs.getGrowthrate());
//					fsdetails.setProfgrosssmargin(fs.getProfgrosssmargin());
//					fsdetails.setProfoperatingmargin(fs.getProfoperatingmargin());
//					fsdetails.setProfnetmargin(fs.getProfnetmargin());
//					fsdetails.setProfreturnasset(fs.getProfreturnasset());
//					fsdetails.setProfreturnequity(fs.getProfreturnequity());
//					fsdetails.setProfcashflowmargin(fs.getProfcashflowmargin());
//					fsdetails.setLiqcurrentratio(fs.getLiqcurrentratio());
//					fsdetails.setLiqquickratio(fs.getLiqquickratio());
//					fsdetails.setLevdebtequityratio(fs.getLevdebtequityratio());
//					fsdetails.setLevdebtassetratio(fs.getLevdebtassetratio());
//					fsdetails.setLevdebtincomeratio(fs.getLevdebtincomeratio());
//					fsdetails.setLevinterestcoverage(fs.getLevinterestcoverage());
//					fsdetails.setUpdatedby(UserUtil.securityService.getUserName());
//					fsdetails.setLastupdated(new Date());
//					if(dbServiceLOS.saveOrUpdate(fsdetails)){
//						flag = "success";
//					}
//
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return flag;
	}

	/**
	 * --Get List of Financial Statements by Appno--
	 * @author Kevin (08.11.2018)
	 * @return form  = {@link FinancialStatementListForm}
	 * */
	@Override
	@Deprecated
	public FinancialStatementListForm getListOfFSDetailsByAppNo(String appno, Integer evalreportid) {
		// TODO Auto-generated method stub
		FinancialStatementListForm form = new FinancialStatementListForm();
//		try {
//			if(appno != null && evalreportid != null){
//				params.put("appno", appno);
//				params.put("evalreportid", evalreportid);
//				List<Tbfinancialstatements> fslist = (List<Tbfinancialstatements>) dbServiceLOS.executeListHQLQuery("FROM Tbfinancialstatements WHERE cfappno=:appno AND evalreportid=:evalreportid", params);
//				if(fslist != null && !fslist.isEmpty()){
//					int i = 0;
//					for(Tbfinancialstatements fs: fslist){
//						i++;
//						if (i == 1) {
//							form.setFs1(fs);
//						}
//						if (i == 2) {
//							form.setFs2(fs);
//						}
//						if (i == 3) {
//							form.setFs3(fs);
//						}
//						if (i == 4) {
//							form.setFs4(fs);
//						}
//						if (i == 5) {
//							form.setFs5(fs);
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return form;
	}

	/**
	 * --Delete Financial Statement by Appno and fsid--
	 * @author Kevin (08.11.2018)
	 * @return String = success otherwise failed
	 * */
	@Override
	@Deprecated
	public String deleteFSByAppnoAndID(String appno, Integer fsid) {
		// TODO Auto-generated method stub
		String flag = "failed";
//		try {
//			if(appno != null && fsid != null){
//				params.put("appno", appno);
//				params.put("fsid", fsid);
//				int res	 = dbServiceLOS.executeUpdate("DELETE FROM Tbfinancialstatements WHERE cfappno=:appno AND fsid=:fsid", params);
//				if(res > 0){
//					flag = "success";
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String saveOrUpdateLAMLoanDetails(List<Tbcfdetails> cfdetails, Integer evalreportid, String appno, String cifname) {
		String flag = "failed";
		try {
			if(evalreportid != null && appno != null){
				params.put("appno", appno);
				params.put("evalreportid", evalreportid);
				List<Tbcfdetails> cfd = (List<Tbcfdetails>) dbServiceLOS.executeListHQLQuery("FROM Tbcfdetails WHERE cfappno=:appno", params);
				if(cfd != null && !cfd.isEmpty()){
					for(Tbcfdetails cf : cfd) {
						params.put("cfrefnoconcat", cf.getCfrefnoconcat());
						Tblamloandetails loandetail = (Tblamloandetails) dbServiceLOS.executeListHQLQuery("FROM Tblamloandetails WHERE cfappno=:appno AND evalreportid=:evalreportid AND cfrefnoconcat=:cfrefnoconcat", params);
						if(loandetail == null){
							loandetail = new Tblamloandetails();
							loandetail.setCreatedby(UserUtil.securityService.getUserName());
							loandetail.setDatecreated(new Date());
						}
						loandetail.setEvalreportid(evalreportid);
						loandetail.setCfappno(appno);
						loandetail.setCfrefno(cf.getId().getCfrefno());
						loandetail.setCflevel(cf.getId().getCflevel());
						loandetail.setCfseqno(cf.getId().getCfseqno());
						loandetail.setCfsubseqno(cf.getId().getCfsubseqno());
						loandetail.setCfrefnoconcat(cf.getCfrefnoconcat());
						loandetail.setCftype(cf.getCftype());
						loandetail.setCfcode(cf.getCfcode());
						loandetail.setCfcurrency(cf.getCfcurrency());
						loandetail.setCfproposedamt(cf.getCfproposedamt());
						loandetail.setCfapprovedamt(cf.getCfapprovedamt());
						loandetail.setCfamount(cf.getCfamount());
						loandetail.setCfexpdt(cf.getCfexpdt());
						loandetail.setCfdtopen(cf.getCfdtopen());
						loandetail.setCfrevolving(cf.getCfrevolving());
						loandetail.setCfshared(cf.getCfshared());
						loandetail.setCfsharedtype(cf.getCfsharedtype());
						loandetail.setCfstatus(cf.getCfstatus());
						loandetail.setCfavailed(cf.getCfavailed());
						loandetail.setCfearmarked(cf.getCfearmarked());
						loandetail.setCfbalance(cf.getCfbalance());
						loandetail.setCfterm(cf.getCfterm());
						loandetail.setCftermperiod(cf.getCftermperiod());
						loandetail.setCfraterule(cf.getCfraterule());
						loandetail.setCfminrate(cf.getCfminrate());
						loandetail.setCfmaxrate(cf.getCfmaxrate());
						loandetail.setCfintrate(cf.getCfintrate());
						loandetail.setCfintrateperiod(cf.getCfintrateperiod());
						loandetail.setCfrepaymenttype(cf.getCfrepaymenttype());
						loandetail.setRemarks(cf.getRemarks());
						loandetail.setCfmaker(cf.getCfmaker());
						loandetail.setCfupdate(cf.getCfupdate());
						loandetail.setCovenants(cf.getCovenants());
						loandetail.setCifno(cf.getCifno());
						loandetail.setSubfacilityseqno(cf.getSubfacilityseqno());
						loandetail.setCifname(cf.getCifname());
						loandetail.setCfupdatecount(cf.getCfupdatecount());
						loandetail.setCfrequestedvalidity(cf.getCfrequestedvalidity());
						loandetail.setPrevcfappno(cf.getPrevcfappno());
						dbServiceLOS.saveOrUpdate(loandetail);
					}
				}
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	@Override
	public String saveOrUpdateRiskProfile(Tblamriskprofile riskprofile) {
		String flag = "failed";
		try {
			if (dbServiceLOS.saveOrUpdate(riskprofile)) {
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	@Override
	public String saveOrUpdateRationaleRecomm(Tblamrationalerecomm recomm) {
		String flag = "failed";
		try {
			recomm.setYear(Calendar.getInstance().get(Calendar.YEAR));
			if(dbServiceLOS.saveOrUpdate(recomm)){
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	@Override
	public String saveOrUpdateOtherTerms(Tblamothertermconditions otherTerm) {
		String flag = "failed";
		try {
			if(dbServiceLOS.saveOrUpdate(otherTerm)){
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	@Override
	public String saveOrUpdateDocumentation(Tblamdocumentation allfac, Tblamdocumentation finfac, Tblamdocumentation mobfac, Tblamdocumentation stockfac) {
		String flag = "failed";
		try {
			if (dbServiceLOS.saveOrUpdate(allfac) && dbServiceLOS.saveOrUpdate(finfac)
					&& dbServiceLOS.saveOrUpdate(mobfac) && dbServiceLOS.saveOrUpdate(stockfac)) {
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String saveOrUpdateBackground(Tblamcorporateprofile cprofile) {
		String flag = "failed";
		try {
			if(dbServiceLOS.saveOrUpdate(cprofile)){
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
	/**
	 * --Get List Shareholder's Information by CIFNo and relationshipcode--
	 * @author Daniel (08.21.2018)
	 * */
	@SuppressWarnings("unchecked")
	public List<Tbmanagement> getShareholdersInfo(String cifno, String relationcode) {
		try {
			params.put("cifno", cifno);
			params.put("relationship", relationcode);
			List<Tbmanagement> share = (List<Tbmanagement>) dbServiceCIF.executeListHQLQuery("FROM Tbmanagement WHERE cifno=:cifno AND relationshipcode=:relationship", params);
			
			/*
			 * Added Routine  - Kev 09.12.2018
			 * */
			if(share != null){
				for(Tbmanagement mgmt : share){
					params.put("cifno", mgmt.getRelatedcifno());
					String cifname = (String) dbServiceCIF.executeUniqueSQLQuery("Select fullname FROM Tbcifmain WHERE cifno=:cifno", params);
					if(cifname != null){
						mgmt.setFirstname(cifname);
					}
					if(mgmt.getNationality() != null){
						params.put("nationality", mgmt.getNationality());
						String nationality = (String) dbServiceLOS.executeUniqueSQLQuery("SELECT DISTINCT(country) FROM TBCOUNTRY WHERE code=:nationality", params);
						mgmt.setNationality(nationality);
					}
				}
			}
			
			
			return share;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * --Get List Management Team(Not Shareholder) by CIFNo--
	 * @author Daniel (08.21.2018)
	 * */
	@SuppressWarnings("unchecked")
	public List<Tbmanagement> getManagementTeam(String cifno, String notshareholder) {
		try {
			params.put("cifno", cifno);
			params.put("relationship", notshareholder);
			List<Tbmanagement> share = (List<Tbmanagement>) dbServiceCIF.executeListHQLQuery(
					"FROM Tbmanagement WHERE cifno=:cifno AND relationshipcode!=:relationship", params);
			
			/*
			 * Added Routine  - Kev 09.12.2018
			 * */
			if(share != null){
				for(Tbmanagement mgmt : share){
					params.put("cifno", mgmt.getRelatedcifno());
					String cifname = (String) dbServiceCIF.executeUniqueSQLQuery("Select fullname FROM Tbcifmain WHERE cifno=:cifno", params);
					if(cifname != null){
						mgmt.setFirstname(cifname);
					}
				}
			}
			
			return share;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * --Get Credit Dealings by CIFNo--
	 * @author Daniel (08.21.2018)
	 * */
	public List<Tbcicreditcheck> getCreditDealings(String cifno) {
		try {
			params.put("cifno", cifno);
			@SuppressWarnings("unchecked")
			List<Tbcicreditcheck> credit = (List<Tbcicreditcheck>) dbServiceLOS
					.executeListHQLQuery("FROM Tbcicreditcheck WHERE cifno=:cifno", params);
			return credit;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@Override
	public Tblamriskprofile getLAMRiskProfile(String appno, Integer evalreportid) {
		Tblamriskprofile riskProfile = new Tblamriskprofile();
		try {
			if(appno != null && evalreportid != null){
				params.put("appno", appno);
				params.put("evalreportid", evalreportid);
				riskProfile = (Tblamriskprofile) dbServiceLOS.executeUniqueHQLQuery("FROM Tblamriskprofile WHERE appno=:appno AND evalreportid=:evalreportid", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return riskProfile;
	}

	@Override
	public Tblamcorporateprofile getLAMCorporateProfile(String appno, Integer evalreportid) {
		Tblamcorporateprofile corpProfile = new Tblamcorporateprofile();
		try {
			if(appno != null && evalreportid != null){
				params.put("appno", appno);
				params.put("evalreportid", evalreportid);
				corpProfile = (Tblamcorporateprofile) dbServiceLOS.executeUniqueHQLQuery("FROM Tblamcorporateprofile WHERE appno=:appno AND evalreportid=:evalreportid", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return corpProfile;
	}

	@Override
	public Tblamcovenants getLAMCovenants(String appno, Integer evalreportid) {
		Tblamcovenants cvnts = new Tblamcovenants();
		try {
			if(appno != null && evalreportid != null){
				params.put("appno", appno);
				params.put("evalreportid", evalreportid);
				cvnts  = (Tblamcovenants) dbServiceLOS.executeUniqueHQLQuery("FROM Tblamcovenants WHERE appno=:appno AND evalreportid=:evalreportid", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cvnts;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tblamloandetails> getLAMLoanDetails(Integer evalreportid, String appno, String cfrefno, Integer cflevel,
			String cfseqno, String cfsubseqno, String cfrefnoconcat) {
		List<Tblamloandetails> list = new ArrayList<Tblamloandetails>();
		StringBuilder hql = new StringBuilder();
		try {
			if(appno != null){
				params.put("appno", appno);
				hql.append("FROM Tblamloandetails WHERE cfappno=:appno AND ");
				
				if (cfrefno != null) {
					params.put("cfrefno", cfrefno);
					hql.append("cfrefno=:cfrefno AND ");
				}
				if (cflevel != null) {
					params.put("cflevel", cflevel);
					hql.append("cflevel=:cflevel AND ");
				}
				if (cfseqno != null) {
					params.put("cfseqno", cfseqno);
					hql.append("cfseqno=:cfseqno AND ");
				}
				if (cfsubseqno != null) {
					params.put("cfsubseqno", cfsubseqno);
					hql.append("cfsubseqno=:cfsubseqno AND ");
				}
				String prefix = appno.substring(0, 2); 
				if(prefix.equals("AM")){
					if(evalreportid != null && cfrefnoconcat != null){
						hql = new StringBuilder();
						params.put("evalreportid", evalreportid);
						params.put("cfrefnoconcat", cfrefnoconcat);
						hql.append("FROM Tblamloandetails WHERE cfappno=:appno AND evalreportid=:evalreportid AND cfrefnoconcat=:cfrefnoconcat AND ");
					}else{
						Tblstapp app = (Tblstapp) dbServiceLOS.executeUniqueHQLQueryMaxResultOne("FROM Tblstapp WHERE appno=:appno", params);
						if (app != null && app.getCfrefnoconcat() != null) {
							if(cflevel == 0){
								params.put("cfrefnoconcatAM", app.getCfrefnoconcat());
								hql.append("cfrefnoconcat=:cfrefnoconcatAM AND ");
							}
							if(evalreportid != null){
								hql.append("evalreportid=:evalreportid AND ");
							}
						}
					}
					
				}else{
					if(evalreportid != null && cfrefnoconcat != null){
						hql = new StringBuilder();
						params.put("evalreportid", evalreportid);
						params.put("cfrefnoconcat", cfrefnoconcat);
						hql.append("FROM Tblamloandetails WHERE cfappno=:appno AND evalreportid=:evalreportid AND cfrefnoconcat=:cfrefnoconcat AND ");
					}
				}
				list  = (List<Tblamloandetails>) dbServiceLOS.executeListHQLQuery(hql.toString().substring(0, hql.length() - 5), params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Tblamothertermconditions getLAMOtherTermsCondition(String appno, Integer evalreportid) {
		Tblamothertermconditions otc = new Tblamothertermconditions();
		try {
			if(appno != null && evalreportid != null){
				params.put("appno", appno);
				params.put("evalreportid", evalreportid);
				otc  = (Tblamothertermconditions) dbServiceLOS.executeUniqueHQLQuery("FROM Tblamothertermconditions WHERE appno=:appno AND evalreportid=:evalreportid", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return otc;
	}

	@Override
	public String saveOrUpdateCovenants(Tblamcovenants lamcovenants) {
		String flag = "failed";
		try {
			if (dbServiceLOS.saveOrUpdate(lamcovenants)) {
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String saveCFDetailsToLAM(String appno, Integer evalreportid) {
		String flag = "failed";
		try {
			if(evalreportid != null && appno != null){
				params.put("appno", appno);
				params.put("evalreportid", evalreportid);
				//CFDETAILS
				List<Tbcfdetails> cfd = (List<Tbcfdetails>) dbServiceLOS.executeListHQLQuery("FROM Tbcfdetails WHERE id.cfappno=:appno", params);
				if(cfd != null && !cfd.isEmpty()){
					for(Tbcfdetails cf : cfd) {
//						String cifname = "";
//						if(cf.getCifno() != null){
//							params.put("cifno", cf.getCifno());
//							String cifName = (String)dbServiceCIF.execSQLQueryTransformer("SELECT fullname FROM Tbcifmain WHERE cifno=:cifno", params, null, 0);
//							if(cifName != null){
//								cifname = cifName;
//							}
//						}
						params.put("cfrefnoconcat", cf.getCfrefnoconcat());
						Tblamloandetails loandetail = (Tblamloandetails) dbServiceLOS.executeUniqueHQLQuery("FROM Tblamloandetails WHERE cfappno=:appno AND evalreportid=:evalreportid AND cfrefnoconcat=:cfrefnoconcat", params);
						if(loandetail == null){
							loandetail = new Tblamloandetails();
							loandetail.setCreatedby(UserUtil.securityService.getUserName());
							loandetail.setDatecreated(new Date());
						}else{
							loandetail.setUpdatedby(UserUtil.securityService.getUserName());
							loandetail.setLastupdated(new Date());
						}
						loandetail.setEvalreportid(evalreportid);
						loandetail.setCfappno(appno);
						loandetail.setCfrefno(cf.getId().getCfrefno());
						loandetail.setCflevel(cf.getId().getCflevel());
						loandetail.setCfseqno(cf.getId().getCfseqno());
						loandetail.setCfsubseqno(cf.getId().getCfsubseqno());
						loandetail.setCfrefnoconcat(cf.getCfrefnoconcat());
						loandetail.setCftype(cf.getCftype());
						loandetail.setCfcode(cf.getCfcode());
						loandetail.setCfcurrency(cf.getCfcurrency());
						loandetail.setCfproposedamt(cf.getCfproposedamt());
						loandetail.setCfapprovedamt(cf.getCfapprovedamt());
						loandetail.setCfamount(cf.getCfamount());
						loandetail.setCfexpdt(cf.getCfexpdt());
						loandetail.setCfdtopen(cf.getCfdtopen());
						loandetail.setCfrevolving(cf.getCfrevolving());
						loandetail.setCfshared(cf.getCfshared());
						loandetail.setCfsharedtype(cf.getCfsharedtype());
						loandetail.setCfstatus(cf.getCfstatus());
						loandetail.setCfavailed(cf.getCfavailed());
						loandetail.setCfearmarked(cf.getCfearmarked());
						loandetail.setCfbalance(cf.getCfbalance());
						loandetail.setCfterm(cf.getCfterm());
						loandetail.setCftermperiod(cf.getCftermperiod());
						loandetail.setCfraterule(cf.getCfraterule());
						loandetail.setCfminrate(cf.getCfminrate());
						loandetail.setCfmaxrate(cf.getCfmaxrate());
						loandetail.setCfintrate(cf.getCfintrate());
						loandetail.setCfintrateperiod(cf.getCfintrateperiod());
						loandetail.setCfrepaymenttype(cf.getCfrepaymenttype());
						loandetail.setRemarks(cf.getRemarks());
						loandetail.setCfmaker(cf.getCfmaker());
						loandetail.setCfupdate(cf.getCfupdate());
						loandetail.setCovenants(cf.getCovenants());
						loandetail.setCifno(cf.getCifno());
						loandetail.setSubfacilityseqno(cf.getSubfacilityseqno());
						loandetail.setCifname(cf.getCifname());
						loandetail.setCfupdatecount(cf.getCfupdatecount());
						loandetail.setCfrequestedvalidity(cf.getCfrequestedvalidity());
						loandetail.setPrevcfappno(cf.getPrevcfappno());
						
						dbServiceLOS.saveOrUpdate(loandetail);
					}
					//Delete if not in TBCFDETAILS
					dbServiceLOS.executeUpdate("DELETE FROM Tblamloandetails WHERE cfappno=:appno AND evalreportid=:evalreportid AND cfrefnoconcat NOT IN(SELECT cfrefnoconcat FROM Tbcfdetails WHERE cfappno='"+appno+"')", params);
				}else{
					//Delete if not in TBCFDETAILS
					dbServiceLOS.executeUpdate("DELETE FROM Tblamloandetails WHERE cfappno=:appno AND evalreportid=:evalreportid", params);
				}
				
//				//COVENANTS
//				List<Tbcfcovenants> cfcovenants = (List<Tbcfcovenants>) dbServiceLOS.executeListHQLQuery("FROM Tbcfcovenants WHERE cfappno=:appno", params);
//				if(cfcovenants != null && !cfcovenants.isEmpty()){
//					for(Tbcfcovenants cfcovnts : cfcovenants) {
//						params.put("cfrefnoconcat", cfcovnts.getCfrefnoconcat());
//						Tblamcovenants lamCovenants = (Tblamcovenants) dbServiceLOS.executeUniqueHQLQuery("FROM Tblamcovenants WHERE appno=:appno AND evalreportid=:evalreportid AND cfrefnoconcat=:cfrefnoconcat", params);
//						if(lamCovenants == null){
//							lamCovenants = new Tblamcovenants();
//						}
//						lamCovenants.setEvalreportid(evalreportid);
//						lamCovenants.setAppno(appno);
//						lamCovenants.setCfrefno(cfcovnts.getCfrefno());
//						lamCovenants.setCflevel(cfcovnts.getCflevel());
//						lamCovenants.setCfseqno(cfcovnts.getCfseqno());
//						lamCovenants.setCfsubseqno(cfcovnts.getCfsubseqno());
//						if (cfcovnts.getCfrefnoconcat() == null) {
//							cfcovnts.setCfrefnoconcat(cfcovnts.getCfrefno() + "-" + cfcovnts.getCflevel() + "-"
//									+ cfcovnts.getCfseqno() + "-" + cfcovnts.getCfsubseqno());
//						} else {
//							lamCovenants.setCfrefnoconcat(cfcovnts.getCfrefnoconcat());
//						}
//						lamCovenants.setCovenants(cfcovnts.getCfcovenants());
//						dbServiceLOS.saveOrUpdate(lamCovenants);
//					}
//					//Delete if not in TBCFCOVENANTS
//					dbServiceLOS.executeUpdate("DELETE FROM Tblamcovenants WHERE appno=:appno AND evalreportid=:evalreportid AND cfrefnoconcat NOT IN(SELECT cfrefnoconcat FROM Tbcfcovenants WHERE cfappno='"+appno+"')", params);
//				}else{
//					//Delete if not in TBCFCOVENANTS
//					dbServiceLOS.executeUpdate("DELETE FROM Tblamcovenants WHERE appno=:appno AND evalreportid=:evalreportid", params);
//				}
				
				//COOBLIGOR
				List<Tbcfcoobligor> cfcoobligor = (List<Tbcfcoobligor>) dbServiceLOS.executeListHQLQuery("FROM Tbcfcoobligor WHERE cfappno=:appno", params);
				if(cfcoobligor != null && !cfcoobligor.isEmpty()){
					for(Tbcfcoobligor coblg : cfcoobligor) {
						params.put("cfrefnoconcat", coblg.getCfrefnoconcat());
						Tblamcoobligor lamCoobligor = (Tblamcoobligor) dbServiceLOS.executeUniqueHQLQuery("FROM Tblamcoobligor WHERE cfappno=:appno AND evalreportid=:evalreportid AND cfrefnoconcat=:cfrefnoconcat", params);
						if(lamCoobligor == null){
							lamCoobligor = new Tblamcoobligor();
							lamCoobligor.setCfcreateddate(coblg.getCfcreateddate());
							lamCoobligor.setCfaddedby(coblg.getCfaddedby());
						}else{
							lamCoobligor.setCfupdated(new Date());
							lamCoobligor.setCfupdatedby(UserUtil.securityService.getUserName());
						}
						lamCoobligor.setEvalreportid(evalreportid);
						lamCoobligor.setCfappno(appno);
						lamCoobligor.setCfrefno(coblg.getCfrefno());
						lamCoobligor.setCflevel(coblg.getCflevel());
						lamCoobligor.setCfseqno(coblg.getCfseqno());
						lamCoobligor.setCfsubseqno(coblg.getCfsubseqno());
						if (coblg.getCfrefnoconcat() == null) {
							lamCoobligor.setCfrefnoconcat(coblg.getCfrefno() + "-" + coblg.getCflevel() + "-"
									+ coblg.getCfseqno() + "-" + coblg.getCfsubseqno());
						} else {
							lamCoobligor.setCfrefnoconcat(coblg.getCfrefnoconcat());
						}
						lamCoobligor.setCfcifno(coblg.getCfcifno());
						lamCoobligor.setCfshareseq(coblg.getCfshareseq());
						lamCoobligor.setCfcifname(coblg.getCfcifname());
						lamCoobligor.setCfamount(coblg.getCfamount());
						lamCoobligor.setCfcifstatus(coblg.getCfcifstatus());
						lamCoobligor.setRemarks(coblg.getRemarks());
						lamCoobligor.setBusinesstype(coblg.getBusinesstype());
						lamCoobligor.setDateofincorporation(coblg.getDateofincorporation());
						lamCoobligor.setCfproposedamt(coblg.getCfproposedamt());
						lamCoobligor.setCfavailed(coblg.getCfavailed());
						lamCoobligor.setCfearmarked(coblg.getCfearmarked());
						lamCoobligor.setCfbalance(coblg.getCfbalance());
						lamCoobligor.setCfstatus(coblg.getCfstatus());
						lamCoobligor.setCfapprovedamt(coblg.getCfapprovedamt());
						lamCoobligor.setCfexpdt(coblg.getCfexpdt());
						lamCoobligor.setCfrevolving(coblg.getCfrevolving());
						lamCoobligor.setCfterm(coblg.getCfterm());
						lamCoobligor.setCftermperiod(coblg.getCftermperiod());
						lamCoobligor.setCfintrate(coblg.getCfintrate());
						lamCoobligor.setCfintrateperiod(coblg.getCfintrateperiod());
						lamCoobligor.setCftype(coblg.getCftype());
						lamCoobligor.setCfcode(coblg.getCfcode());
						lamCoobligor.setCfcurrency(coblg.getCfcurrency());
						
						dbServiceLOS.saveOrUpdate(lamCoobligor);
					}
					//Delete if not in TBCFCOOBLIGOR
					dbServiceLOS.executeUpdate("DELETE FROM Tblamcoobligor WHERE cfappno=:appno AND evalreportid=:evalreportid AND cfrefnoconcat NOT IN(SELECT cfrefnoconcat FROM Tbcfcoobligor WHERE cfappno='"+appno+"')", params);
				}else{
					//Delete if not in TBCFCOOBLIGOR
					dbServiceLOS.executeUpdate("DELETE FROM Tblamcoobligor WHERE cfappno=:appno AND evalreportid=:evalreportid", params);
				}
				
				/*
				 * LAM Covenants | LAM Terms & Conditions | LAM Documentation
				 * */
				String termcon = "";
				String covenants = "";
				String lamdocs = "";
				String forpreloanrelease = "";
				String foravailment = "";
				String otherdocs = "";
				if(cfd != null && !cfd.isEmpty()){
					for(Tbcfdetails cf : cfd) {
						params.put("cfrefnoconcat", cf.getCfrefnoconcat());
						params.put("cfcode", cf.getCfcode());
						String facilityname = (String) dbServiceLOS.executeUniqueSQLQuery("SELECT facilityname FROM TBCFMAINTENANCE WHERE facilitycode=:cfcode", params);
						
						String facilitylvl = "";
						if (cf.getId().getCflevel() == 0) {
							facilitylvl = "<span>&#8226;</span><b><u> " + "MAIN - ";
						} else if (cf.getId().getCflevel() == 1) {
							facilitylvl = "&nbsp;&nbsp;&nbsp;&nbsp;<b><u> " + "SUB FACILITY - ";
						}
						
						//CFTERMCODITIONS
						List<Tbcftermconditions> cftermcon = (List<Tbcftermconditions>) dbServiceLOS.executeListHQLQuery("FROM Tbcftermconditions WHERE cfappno=:appno AND cfrefnoconcat=:cfrefnoconcat", params);
						if(cftermcon != null && !cftermcon.isEmpty()){
							if(!termcon.equals("")){
								if (cf.getId().getCflevel() == 0) {
									termcon += "<br><br>";
								} else if (cf.getId().getCflevel() == 1) {
									termcon += "<br>";
								}
							}
							termcon +=  facilitylvl + cf.getCfrefnoconcat() + " - " + facilityname + "</b></u><br>";
							termcon += "<ol>";
							for(Tbcftermconditions tc : cftermcon) {
								termcon += "<li>" + tc.getCftermconditions() + "</li>";
							}
							termcon += "</ol>";
						}
						
						
						//COVENANTS
						List<Tbcfcovenants> cfcovenants = (List<Tbcfcovenants>) dbServiceLOS.executeListHQLQuery("FROM Tbcfcovenants WHERE cfappno=:appno AND cfrefnoconcat=:cfrefnoconcat", params);
						if(cfcovenants != null && !cfcovenants.isEmpty()){
							if(!covenants.equals("")){
								if (cf.getId().getCflevel() == 0) {
									covenants += "<br><br>";
								} else if (cf.getId().getCflevel() == 1) {
									covenants += "<br>";
								}
							}
							covenants +=  facilitylvl + cf.getCfrefnoconcat() + " - " + facilityname + "</b></u><br>";
							covenants += "<ol>";
							for(Tbcfcovenants cvnts : cfcovenants) {
								covenants += "<li>" + cvnts.getCfcovenants() + "</li>";
							}
							covenants += "</ol>";
						}
						
						//LAM DOCUMENTATION
						List<Tbdocspercfapp> cfdocs = (List<Tbdocspercfapp>) dbServiceLOS.executeListHQLQuery("FROM Tbdocspercfapp WHERE appno=:appno AND cfrefno=:cfrefnoconcat", params);
						if(cfdocs != null && !cfdocs.isEmpty()){
							if(!lamdocs.equals("")){
								if (cf.getId().getCflevel() == 0) {
									lamdocs += "<br><br>";
								} else if (cf.getId().getCflevel() == 1) {
									lamdocs += "<br>";
								}
							}
							lamdocs += facilitylvl + cf.getCfrefnoconcat() + " - " + facilityname + "</b></u><br><br>";
							
							//For Pre loan release
							for(Tbdocspercfapp docscf : cfdocs) {
								if(forpreloanrelease.equals("") && docscf.getIsforpreloanrelease() != null && docscf.getIsforpreloanrelease()){
									forpreloanrelease +=  "&nbsp;&nbsp;&nbsp;&nbsp;<b><u>Pre-Loan Release Requirement</b></u><br><ol>";
								}
								if (docscf.getIsforpreloanrelease() != null && docscf.getIsforpreloanrelease()) {
									forpreloanrelease += "<li>" + "<b>" + docscf.getDocumentname() + "</b> - "
											+ (docscf.getRemarks() == null ? "" : docscf.getRemarks()) + "</li>";
								}
							}
							if (!forpreloanrelease.equals("")) {
								forpreloanrelease += "</ol>";
							}

							//For Availment
							for(Tbdocspercfapp docscf : cfdocs) {
								if(foravailment.equals("") && docscf.getIsforavailment() != null && docscf.getIsforavailment()){
									foravailment +=  "&nbsp;&nbsp;&nbsp;&nbsp;<b><u>For Availments</b></u><br><ol>";
								}
								if (docscf.getIsforavailment() != null && docscf.getIsforavailment()) {
									foravailment += "<li>" + "<b>" + docscf.getDocumentname() + "</b> - "
											+ (docscf.getRemarks() == null ? "" : docscf.getRemarks()) + "</li>";
								}
							}
							if (!foravailment.equals("")) {
								foravailment += "</ol>";
							}
							
							// Other docs
							for(Tbdocspercfapp docscf : cfdocs) {
								if (otherdocs.equals("")
										&& (docscf.getIsforavailment() == null || docscf.getIsforavailment() == false)
										&& (docscf.getIsforpreloanrelease() == null || docscf.getIsforpreloanrelease() == false)) {
									otherdocs += "&nbsp;&nbsp;&nbsp;&nbsp;<b><u>Other Documents</b></u><br><ol>";
								}
								if ((docscf.getIsforavailment() == null || docscf.getIsforavailment() == false)
										&& (docscf.getIsforpreloanrelease() == null
												|| docscf.getIsforpreloanrelease() == false)) {
									otherdocs += "<li>" + "<b>" + docscf.getDocumentname() + "</b> - "
											+ (docscf.getRemarks() == null ? "" : docscf.getRemarks()) + "</li>";
								}
							}
							if (!otherdocs.equals("")) {
								otherdocs += "</ol>";
							}
						}
					}
					
					Tblamothertermconditions lamTermCon = (Tblamothertermconditions) dbServiceLOS.executeUniqueHQLQuery("FROM Tblamothertermconditions WHERE appno=:appno AND evalreportid=:evalreportid", params);
					if (lamTermCon == null) {
						lamTermCon = new Tblamothertermconditions();
						lamTermCon.setAppno(appno);
						lamTermCon.setEvalreportid(evalreportid);
						lamTermCon.setRemarks(termcon);
						lamTermCon.setRemarkscftermconditions(termcon);
						dbServiceLOS.save(lamTermCon);
					} else {
						if (lamTermCon.getRemarks() == null) {
							lamTermCon.setRemarks(termcon);
						}
						lamTermCon.setRemarkscftermconditions(termcon);
						dbServiceLOS.saveOrUpdate(lamTermCon);
					}
					
					Tblamcovenants lamCovenants = (Tblamcovenants) dbServiceLOS.executeUniqueHQLQuery("FROM Tblamcovenants WHERE appno=:appno AND evalreportid=:evalreportid", params);
					if (lamCovenants == null) {
						lamCovenants = new Tblamcovenants();
						lamCovenants.setAppno(appno);
						lamCovenants.setEvalreportid(evalreportid);
						lamCovenants.setCovenants(covenants);
						lamCovenants.setRemarkscfcovenants(covenants);
						dbServiceLOS.save(lamCovenants);
					} else {
						if (lamCovenants.getCovenants() == null) {
							lamCovenants.setCovenants(covenants);
						}
						lamCovenants.setRemarkscfcovenants(covenants);
						dbServiceLOS.saveOrUpdate(lamCovenants);
					}

					Tblamdocumentation lamDocs = (Tblamdocumentation) dbServiceLOS.executeUniqueHQLQuery("FROM Tblamdocumentation WHERE appno=:appno AND evalreportid=:evalreportid", params);
					if(lamDocs == null){
						lamDocs = new Tblamdocumentation();
						lamDocs.setAppno(appno);
						lamDocs.setEvalreportid(evalreportid);
						lamDocs.setRemarks(lamdocs + forpreloanrelease + foravailment + otherdocs);
						lamDocs.setRemarksdocspercfapp(lamdocs + forpreloanrelease + foravailment + otherdocs);
						dbServiceLOS.save(lamDocs);
						
					} else {
						if (lamDocs.getRemarks() == null) {
							lamDocs.setRemarks(lamdocs + forpreloanrelease + foravailment + otherdocs);
						}
						lamDocs.setRemarksdocspercfapp(lamdocs + forpreloanrelease + foravailment + otherdocs);
						dbServiceLOS.saveOrUpdate(lamDocs);
					}
					
					
					
					//CF COMPANY
					List<Tbcfcompany> cfcompany = (List<Tbcfcompany>) dbServiceLOS.executeListHQLQuery("FROM Tbcfcompany WHERE cfappno=:appno", params);
					if(cfcompany != null && !cfcompany.isEmpty()){
						for(Tbcfcompany comp : cfcompany) {
							params.put("cfrefnoconcat", comp.getCfrefnoconcat());
							Tblamcompany lamCompany = (Tblamcompany) dbServiceLOS.executeUniqueHQLQuery("FROM Tblamcompany WHERE cfappno=:appno AND evalreportid=:evalreportid AND cfrefnoconcat=:cfrefnoconcat", params);
							if(lamCompany == null){
								lamCompany = new Tblamcompany();
							}
							lamCompany.setEvalreportid(evalreportid);
							lamCompany.setCfappno(appno);
							lamCompany.setCfrefno(comp.getCfrefno());
							lamCompany.setCflevel(comp.getCflevel());
							lamCompany.setCfseqno(comp.getCfseqno());
							lamCompany.setCfsubseqno(comp.getCfsubseqno());
							if (comp.getCfrefnoconcat() == null) {
								lamCompany.setCfrefnoconcat(comp.getCfrefno() + "-" + comp.getCflevel() + "-"
										+ comp.getCfseqno() + "-" + comp.getCfsubseqno());
							} else {
								lamCompany.setCfrefnoconcat(comp.getCfrefnoconcat());
							}
							lamCompany.setCompanycode(comp.getCompanycode());
							lamCompany.setCompanyname(comp.getCompanyname());
							lamCompany.setDateassigned(comp.getDateassigned());
							lamCompany.setAssignedby(comp.getAssignedby());
							dbServiceLOS.saveOrUpdate(lamCompany);
						}
						//Delete if not in TBCFCOMPANY
						dbServiceLOS.executeUpdate("DELETE FROM Tblamcompany WHERE cfappno=:appno AND evalreportid=:evalreportid AND cfrefnoconcat NOT IN(SELECT cfrefnoconcat FROM Tbcfcompany WHERE cfappno='"+appno+"')", params);
					}else{
						//Delete if not in TBCFCOMPANY
						dbServiceLOS.executeUpdate("DELETE FROM Tblamcompany WHERE cfappno=:appno AND evalreportid=:evalreportid", params);
					}
					
				}
				
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Tblamrationalerecomm getLAMRationaleRecomm(String appno, Integer evalreportid) {
		Tblamrationalerecomm rr = new Tblamrationalerecomm();
		try {
			if (appno != null && evalreportid != null) {
				params.put("appno", appno);
				params.put("evalreportid", evalreportid);
				rr = (Tblamrationalerecomm) dbServiceLOS.executeUniqueHQLQuery(
						"FROM Tblamrationalerecomm WHERE appno=:appno AND evalreportid=:evalreportid", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rr;
	}

	@Override
	public String saveOrUpdateLAMBorProfile(Tblamborrowerprofile borprofile) {
		String flag = "failed";
		try {
			if (borprofile.getId() == null) {
				borprofile.setCreatedby(UserUtil.securityService.getUserName());
				borprofile.setDatecreated(new Date());
			} else {
				borprofile.setUpdatedby(UserUtil.securityService.getUserName());
				borprofile.setLastupdated(new Date());
			}
			if (dbServiceLOS.saveOrUpdate(borprofile)) {
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Tblamborrowerprofile getLAMBorrowerProfile(String appno, Integer evalreportid) {
		Tblamborrowerprofile bp = new Tblamborrowerprofile();
		try {
			if(appno != null && evalreportid != null){
				params.put("appno", appno);
				params.put("evalreportid", evalreportid);
				bp  = (Tblamborrowerprofile) dbServiceLOS.executeUniqueHQLQuery("FROM Tblamborrowerprofile WHERE appno=:appno AND evalreportid=:evalreportid", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbdocspercf> getListOfDocsPerCF(String facilitycode, String documentcode) {
		List<Tbdocspercf> list = new ArrayList<Tbdocspercf>();
		String hql = "FROM Tbdocspercf WHERE id IS NOT NULL";
		try {
			if (facilitycode != null) {
				params.put("facilitycode", facilitycode);
				hql += " AND facilitycode=:facilitycode";
			}
			if (documentcode != null) {
				params.put("documentcode", documentcode);
				hql += " AND documentcode=:documentcode";
			}
			list = (List<Tbdocspercf>) dbServiceLOS.executeListHQLQuery(hql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	@Deprecated
	public String addLAMDocuments(List<Tbdocspercf> docspercf, Integer evalreportid, String appno, String cfrefno,
			Integer cflevel, String cfseqno, String cfsubseqno, String cfrefnoconcat) {
		String flag = "failed";
//		try {
//			if (docspercf != null && !docspercf.isEmpty() && evalreportid != null && appno != null) {
//				for (Tbdocspercf d : docspercf) {
//					Tblamdocumentation doc = new Tblamdocumentation();
//					doc.setEvalreportid(evalreportid);
//					doc.setAppno(appno);
//					doc.setCfrefno(cfrefno);
//					doc.setCflevel(cflevel);
//					doc.setCfseqno(cfseqno);
//					doc.setCfsubseqno(cfsubseqno);
//					doc.setCfrefnoconcat(cfrefnoconcat);
//					doc.setDocumentname(d.getDocumentname());
//					dbServiceLOS.saveOrUpdate(doc);
//				}
//				flag = "success";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return flag;
	}

	@Override
	public String saveOrUpdateLAMDocumentation(Tblamdocumentation lamdocs) {
		String flag = "failed";
		try {
			if (dbServiceLOS.saveOrUpdate(lamdocs)) {
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String deleteLAMDocuments(Integer id) {
		String flag = "failed";
		try {
			if (id != null) {
				params.put("id", id);
				int res = dbServiceLOS.executeUpdate("DELETE FROM Tblamdocumentation WHERE id=:id", params);
				if (res > 0) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Tblamdocumentation getLAMDocuments(String appno, Integer evalreportid) {
		Tblamdocumentation lamdocs = new Tblamdocumentation();
		try {
			if(appno != null && evalreportid != null){
				params.put("appno", appno);
				params.put("evalreportid", evalreportid);
				lamdocs  = (Tblamdocumentation) dbServiceLOS.executeUniqueHQLQuery("FROM Tblamdocumentation WHERE appno=:appno AND evalreportid=:evalreportid", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lamdocs;
	}

	@Override
	public String saveOrUpdateFSMain(Tbfsmain fsmain) {
		String flag = "failed";
		try {
			if (fsmain.getId() == null) {
				fsmain.setCreatedby(UserUtil.securityService.getUserName());
				fsmain.setDatecreated(new Date());
			}else{
				fsmain.setUpdatedby(UserUtil.securityService.getUserName());
				fsmain.setLastupdated(new Date());
			}
			if (dbServiceLOS.saveOrUpdate(fsmain)) {
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	
	@Override
	@Deprecated
	public Tbfsmain getFSMain(String appno, Integer evalreportid) {
//		Tbfsmain fsmain = new Tbfsmain();
//		try {
//			if (appno != null && evalreportid != null) {
//				params.put("appno", appno);
//				params.put("evalreportid", evalreportid);
//				fsmain = (Tbfsmain) dbServiceLOS.executeUniqueHQLQuery(
//						"FROM Tbfsmain WHERE appno=:appno AND evalreportid=:evalreportid", params);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return fsmain;
		return null;
	}

	@Override
	@Deprecated
	public String addDefaultCovenants(List<Tbcovenants> defaultcovenants, Integer evalreportid, String appno,
			String cfrefno, Integer cflevel, String cfseqno, String cfsubseqno, String cfrefnoconcat) {
		String flag = "failed";
//		try {
//			if (defaultcovenants != null && !defaultcovenants.isEmpty() && evalreportid != null) {
//				for (Tbcovenants c : defaultcovenants) {
//					Tblamcovenants lamcovenants = new Tblamcovenants();
//					lamcovenants.setEvalreportid(evalreportid);
//					lamcovenants.setAppno(appno);
//					lamcovenants.setCfrefno(cfrefno);
//					lamcovenants.setCflevel(cflevel);
//					lamcovenants.setCfseqno(cfseqno);
//					lamcovenants.setCfsubseqno(cfsubseqno);
//					lamcovenants.setCfrefnoconcat(cfrefnoconcat);
//					lamcovenants.setCovenants(c.getCovenants());
//					dbServiceLOS.saveOrUpdate(lamcovenants);
//				}
//				flag = "success";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return flag;
	}

	@Override
	@Deprecated
	public String deleteLAMCovenants(Integer id) {
		String flag = "failed";
//    	try {
//    		if(id != null){
//				params.put("id", id);
//				int res = dbServiceLOS.executeUpdate("DELETE FROM Tblamcovenants WHERE id=:id", params);
//	    		if(res > 0){
//	    			flag = "success";
//	    		}
//	    	}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return flag;
	}
	
	@Override
	public String saveOrUpdateOtherFunds(OtherIncomeExpenseForm form) {
		// TODO SAVE OTHER INCOME/EXPENSE
		String result= "failed";
		try{
		/**	FUNDTYPE = 1 - INCOME
			FUNDTYPE = 2 - EXPENSE
		**/
			if (form.getId() !=null){
				Tbapaotherincomeexpense other = new Tbapaotherincomeexpense();
				params.put("id", form.getId());
				other = (Tbapaotherincomeexpense)dbServiceLOS.executeUniqueHQLQuery("FROM Tbapaotherincomeexpense WHERE id =:id", params);
				if (other != null) {
					other.setExpensename(form.getExpensename());
					other.setExpenseamount(form.getExpenseamount());
					other.setIncomename(form.getIncomename());
					other.setIncomeamount(form.getIncomeamount());
					if (dbServiceLOS.update(other)) {
						result = "success";
					}
				}
			}
			else {
				Tbapaotherincomeexpense other = new Tbapaotherincomeexpense();
				other.setAppno(form.getAppno());
				other.setFundtype(form.getFundtype());
				other.setExpensename(form.getExpensename());
				other.setExpenseamount(form.getExpenseamount());
				other.setIncomename(form.getIncomename());
				other.setIncomeamount(form.getIncomeamount());
				other.setEvalreportid(form.getEvalreportid());
				if (dbServiceLOS.save(other)) {
					result = "success";
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbapaotherincomeexpense> getListOfIncomeExpense(Integer evalreportid, String fundtype) {
		// TODO Get List for Other Income or Expense
		List<Tbapaotherincomeexpense> list = null;
		try {
			/*
			 * FUNDTYPE = 1 - INCOME 
			 * FUNDTYPE = 2 - EXPENSE
			 **/
			if (evalreportid != null && fundtype != null) {
				params.put("evalreportid", evalreportid);
				params.put("fundtype", fundtype);
				list = (List<Tbapaotherincomeexpense>) dbServiceLOS.executeListHQLQuery(
						"FROM Tbapaotherincomeexpense WHERE evalreportid=:evalreportid AND fundtype=:fundtype", params);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	@Override
	public String saveOrUpdateAPA(LAMAPAForm form) {
		// TODO SAVE APA
		String result = "failed";
		try {
			params.put("id", form.getId());
			Tblamapa apa = (Tblamapa) dbServiceLOS.executeUniqueHQLQuery("FROM Tblamapa WHERE id=:id", params);
			// UPDATE APA
			if (apa == null) {
				apa = new Tblamapa();
			}
			apa.setId(form.getId());
			apa.setAppno(form.getAppno());
			apa.setCifno(form.getCifno());
			apa.setStartreviewdate(form.getStartreviewdate());
			apa.setEndreviewdate(form.getEndreviewdate());
			apa.setTotalnodays(form.getTotalnodays());
			apa.setAvefundsupplied(form.getAvefundsupplied());
			apa.setReservereqrate(form.getReservereqrate());
			apa.setReserveproduct(form.getReserveproduct());
			apa.setDepositnetreserve(form.getDepositnetreserve());
			apa.setLoans(form.getLoans());
			apa.setNetfundsuppliedused(form.getNetfundsuppliedused());
			apa.setAveintrateloan(form.getAveintrateloan());
			apa.setAveintratefunds(form.getAveintratefunds());
			apa.setInterestincomeloan(form.getInterestincomeloan());
			apa.setIncomenetfundsupplied(form.getIncomenetfundsupplied());
			apa.setTotalincomefunds(form.getTotalincomefunds());
			apa.setTpr(form.getTpr());
			apa.setInterestexpense(form.getInterestexpense());
			apa.setExpenseinterestrate(form.getExpenseinterestrate());
			apa.setCostnetfundused(form.getCostnetfundused());
			apa.setTotalexpensefunds(form.getTotalexpensefunds());
			apa.setNetincomelossfunds(form.getNetincomelossfunds());
			apa.setRatereturn(form.getRatereturn());
			if (dbServiceLOS.saveOrUpdate(apa)) {
				result = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Tblamapa getAPADetails(Integer id) {
		// TODO GET APA DETAILS
		Tblamapa apadetails = new Tblamapa();
		try {
			if (id != null) {
				params.put("id", id);
				apadetails = (Tblamapa) dbServiceLOS.executeUniqueHQLQuery("FROM Tblamapa WHERE id=:id", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return apadetails;
	}

	@Override
	public String deleteIncomeExpense(Integer id) {
		String flag = "failed";
    	try {
    		if(id != null){
				params.put("id", id);
				int res = dbServiceLOS.executeUpdate("DELETE FROM Tbapaotherincomeexpense WHERE id=:id", params);
	    		if(res > 0){
	    			flag = "success";
	    		}
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CFGroupExposureForm> getCFGroupExposureList(String appno) {
		List<CFGroupExposureForm> list = new ArrayList<CFGroupExposureForm>();
    	try {
    		if(appno != null){
				params.put("appno", appno);
				list = (List<CFGroupExposureForm>) dbServiceLOS.execSQLQueryTransformer("EXEC sp_GetCFGroupExposureList @appno=:appno, @totalflag = '0'", params, CFGroupExposureForm.class, 1);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String saveLAMHistory(String appno) {
		String flag = "failed";
    	try {
    		if(appno != null){
				params.put("appno", appno);
				Tblstapp app = (Tblstapp) dbServiceLOS.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", params);
				if(app != null){
					if(app.getDmsid() == null || (app.getDmsid() != null && app.getDmsid().trim().equals(""))){
						LoggerUtil.error(">>>LAM DMS ID cannot be empty !", this.getClass());
					}else{
						Integer maxEvalRptId = (Integer) dbServiceLOS.executeUniqueSQLQuery("SELECT MAX(evalreportid) FROM Tbevalreport WHERE appno=:appno", params);
						
						//Main cfrefno
						List<Tbcfdetails> cfd = (List<Tbcfdetails>) dbServiceLOS.executeListHQLQuery("FROM Tbcfdetails WHERE id.cfappno=:appno AND id.cflevel='0'", params);
						if(cfd == null){
							LoggerUtil.error(">>>CFDETAILS cannot be empty !", this.getClass());
						}else{
							for(Tbcfdetails c :cfd){
								params.put("cfrefno", c.getId().getCfrefno());
								Tbapprovedlamdms ldms = (Tbapprovedlamdms) dbServiceLOS.executeUniqueHQLQuery("FROM Tbapprovedlamdms WHERE id.appno=:appno AND id.cfrefno=:cfrefno", params);
								if(ldms != null){
									LoggerUtil.info(">>>LAM History Exists for App No: " + app.getAppno()
											+ " / CF Ref No: " + c.getId().getCfrefno() + " / EvalRptID: "
											+ ldms.getEvalreportid() + " with LAM DMS ID: " + ldms.getDmsid(),
											this.getClass());
								} else {
									TbapprovedlamdmsId id = new TbapprovedlamdmsId();
									id.setAppno(appno);
									id.setCfrefno(c.getId().getCfrefno());
									
									Tbapprovedlamdms lamdms = new Tbapprovedlamdms();
									lamdms.setId(id);
									lamdms.setDateuploaded(new Date());
									lamdms.setUploadedby(UserUtil.securityService.getUserName());
									lamdms.setEvalreportid(maxEvalRptId);
									lamdms.setDmsid(app.getDmsid());
									dbServiceLOS.save(lamdms);
								}
							}
							flag = "success";
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
	public List<LAMDMSHistoryForm> getLAMHistoryDMS(String appno) {
		List<LAMDMSHistoryForm> list = new ArrayList<LAMDMSHistoryForm>();
    	try {
    		if(appno == null){
    			LoggerUtil.error(">>>Get LAM History - parameter cannot be empty !", this.getClass());
    		} else {
    			String hql = "SELECT appno, cfrefno, evalreportid, dmsid, dateuploaded, uploadedby "
    					+ "FROM Tbapprovedlamdms WHERE dmsid IS NOT NULL";
    			if(appno != null){
    				params.put("appno", appno);
//    				hql += " AND appno=:appno";
    				
    				String maincfrefno = "''";
	    			//Main cfrefno
					List<Tbcfdetails> cfd = (List<Tbcfdetails>) dbServiceLOS.executeListHQLQuery("FROM Tbcfdetails WHERE id.cfappno=:appno AND id.cflevel='0'", params);
					if(cfd == null){
						LoggerUtil.error(">>>Get LAMDMSHistory - CFDETAILS cannot be empty !", this.getClass());
					}else{
						for(Tbcfdetails c :cfd){
							maincfrefno += ",'" + c.getId().getCfrefno() + "'";
						}
					}
					hql += " AND cfrefno IN (" + maincfrefno + ") ORDER BY dateuploaded DESC";
    			}
				list = (List<LAMDMSHistoryForm>) dbServiceLOS.execSQLQueryTransformer(hql, params, LAMDMSHistoryForm.class, 1);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
