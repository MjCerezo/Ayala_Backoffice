package com.etel.systemparameter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.IOException;

import com.etel.utils.ImageUtils;
import org.apache.log4j.Logger;

import com.cifsdb.data.Tbchangecifdetailsrequest;
import com.cifsdb.data.Tbcifmain;
import com.cifsdb.data.Tbreferrors;
import com.coopdb.data.Tbaccessrights;
import com.coopdb.data.TbaccessrightsId;
import com.coopdb.data.Tbcasafeesandcharges;
import com.coopdb.data.Tbcoa;
import com.coopdb.data.Tbcoamaintenance;
import com.coopdb.data.Tbcolareamaintenance;
import com.coopdb.data.Tbcollectionarea;
import com.coopdb.data.Tbcollector;
import com.coopdb.data.Tbcollectormaintenance;
import com.coopdb.data.Tbcollectorpersubarea;
import com.coopdb.data.Tbcolsubareamaintenance;
import com.coopdb.data.Tbgovernmentcontribution;
import com.coopdb.data.Tbholiday;
import com.coopdb.data.Tbproperties;
import com.etel.branch.forms.BranchForm;
import com.etel.changecifdetails.ChangeCIFDetailsFacade;
import com.etel.changecifdetails.ChangeCIFDetailsService;
import com.etel.changecifdetails.ChangeCIFDetailsServiceImpl;
import com.etel.codetable.forms.CodetableForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.systemparameter.forms.AccessRightsForm;
import com.etel.systemparameter.forms.BranchSysParamsForm;
import com.etel.systemparameter.forms.CollectionAreaForm;
import com.etel.systemparameter.forms.CoopForm;
import com.etel.systemparameter.forms.UserSysParamsForm;
import com.etel.utils.EncryptDecryptUtil;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;
import com.loansdb.data.Tbapprovedcoobligor;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class SystemParamsServiceImpl implements SystemParamsService {
	SecurityService serviceS = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private static final Logger logger = Logger.getLogger(SystemParamsServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbaccessrights> getAccessRightListperModuleName(String modulename) {
		// TODO Auto-generated method stub
		// Get Codevalue per Codename

		List<Tbaccessrights> codevaluelist = new ArrayList<Tbaccessrights>();
		DBService dbservice = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("modulename", modulename);

		try {
			codevaluelist = (List<Tbaccessrights>) dbservice.executeListHQLQuery(
					"FROM Tbaccessrights WHERE modulename=:modulename order by submodulename ASC", params);
			logger.info("Get list of codevalue.");
		} catch (Exception e) {
			logger.error("ERROR" + e.toString());
		}
		return codevaluelist;
	}

	@Override
	public String addAccessRights(AccessRightsForm form) {
		// TODO Auto-generated method stub
		// Add Access Rights per Module Name
		String flag = "failed";
		DBService dbservice = new DBServiceImpl();

		try {
			Tbaccessrights accessrights = new Tbaccessrights();
			TbaccessrightsId moduleName = new TbaccessrightsId();
			moduleName.setModulename(form.getModulename());
			if (form.getSubmodulename() == null) {
				moduleName.setAccessname(form.getModulename() + "_" + form.getAccesstype());
			} else {
				moduleName.setAccessname(
						form.getModulename() + "_" + form.getSubmodulename() + "_" + form.getAccesstype());
			}
			accessrights.setId(moduleName);
			accessrights.setSubmodulename(form.getSubmodulename());
			accessrights.setAccesstype(form.getAccesstype());
			accessrights.setDescription(form.getDescription());
			accessrights.setCreatedby(serviceS.getUserName());
			accessrights.setCreateddate(new Date());

			if (dbservice.save(accessrights)) {
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return flag;
	}

	@Override
	public String deleteAccessRights(String modulename) {
		// TODO Auto-generated method stub
		// Delete Access Rights

		String flag = "failed";
		DBService dbService = new DBServiceImpl();

		Map<String, Object> params = HQLUtil.getMap();
		params.put("modulename", modulename);
		Tbaccessrights tbaccessrights = new Tbaccessrights();
		try {
			tbaccessrights = (Tbaccessrights) dbService
					.executeUniqueHQLQuery("FROM Tbaccessrights a WHERE a.id.accessname=:accessname", params);
			if (dbService.delete(tbaccessrights)) {
				flag = "successful";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * Save System Properties
	 * 
	 * @author Kevin
	 * @param config - TBPROPERTIES TABLE
	 */
	@SuppressWarnings("static-access")
	@Override
	public String savePropertiesConfig(Tbproperties config) {
		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		File file = null;
		if (config.getCompanyheaderlogo().equals("")) {
			
		}else {
			file = new File(config.getCompanyheaderlogo());
		}
		File file1 = null;
		if (config.getCompanywelcomelogo().equals("")) {
			
		}else {
			file1 = new File(config.getCompanywelcomelogo());
		}
		try {
			EncryptDecryptUtil en = new EncryptDecryptUtil();
			Tbproperties c = (Tbproperties) dbService.executeUniqueHQLQueryMaxResultOne("FROM Tbproperties", null);
			if (c != null) {
				c.setSmtpIsdisabled(config.getSmtpIsdisabled());
				c.setSmtpTimeinterval(config.getSmtpTimeinterval());
				c.setSmtpEmailaddress(config.getSmtpEmailaddress());
				// Encrypt Smtp Password
				c.setSmtpPassword(en.encrypt(config.getSmtpPassword()));
				//c.setSmtpPassword(config.getSmtpPassword());

				c.setSmtpEmailaddalias(config.getSmtpEmailaddalias());
				c.setSmtpHost(config.getSmtpHost());
				c.setSmtpPort(config.getSmtpPort());
				c.setRptUrl(config.getRptUrl());
				c.setRptUsername(config.getRptUsername());

				// Encrypt Report DB Password
				c.setRptPassword(en.encrypt(config.getRptPassword()));

				// DMS URL
				c.setDmsUrl(config.getDmsUrl());

				// LINKS
				c.setCifLink(config.getCifLink());
				c.setLosLink(config.getLosLink());
				c.setCasaLink(config.getCasaLink());
				c.setApitoken(config.getApitoken());
				c.setApiurl(config.getApiurl());
				
				//img
				if (config.getCompanyheaderlogo().equals("")) {
					c.setCompanyheaderlogofilename(config.getCompanyheaderlogofilename());
					c.setCompanyheaderlogo(config.getCompanyheaderlogo());
				} else {
					c.setCompanyheaderlogofilename(file.getName());
					c.setCompanyheaderlogo(ImageUtils.pdfToBase64(config.getCompanyheaderlogo()));
				}
				if (config.getCompanywelcomelogo().equals("")) {
					c.setCompanywelcomelogo(config.getCompanywelcomelogo());
					c.setCompanywelcomelogofilename(config.getCompanywelcomelogofilename());
				} else {
					c.setCompanywelcomelogo(ImageUtils.pdfToBase64(config.getCompanywelcomelogo()));
					c.setCompanywelcomelogofilename(file1.getName());
				}
				c.setBuildno(config.getBuildno());
				
				if (dbService.saveOrUpdate(c)) {
					flag = "success";
				}
			} else {
				config.setSmtpPassword(en.encrypt(config.getSmtpPassword()));
				if (dbService.save(config)) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * Get System Properties
	 * 
	 * @author Kevin
	 */
	@SuppressWarnings("static-access")
	@Override
	public Tbproperties getProperties() {
		DBService dbService = new DBServiceImpl();
		Tbproperties config = new Tbproperties();
		try {
			EncryptDecryptUtil en = new EncryptDecryptUtil();
			config = (Tbproperties) dbService.executeUniqueHQLQueryMaxResultOne("FROM Tbproperties", null);
			// Decrypt Smtp Password
			if (config != null && config.getSmtpPassword() != null) {
				config.setSmtpPassword(en.decrypt(config.getSmtpPassword()));
				//config.setSmtpPassword(config.getSmtpPassword());
			}
			// Decrypt Report DB Password
			if (config != null && config.getRptPassword() != null) {
				config.setRptPassword(en.decrypt(config.getRptPassword()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return config;
	}

	// added by fed
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcasafeesandcharges> getRecordsCasaFeesAndCharges() {
		DBService dbService = new DBServiceImpl();
		List<Tbcasafeesandcharges> list = new ArrayList<Tbcasafeesandcharges>();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("coopcode", UserUtil.getUserByUsername(serviceS.getUserName()).getCoopcode());
		list = (List<Tbcasafeesandcharges>) dbService
				.executeListHQLQuery("FROM Tbcasafeesandcharges", param);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CoopForm> getListofCoopcode() {

		List<CoopForm> list = new ArrayList<CoopForm>();
		DBService dbService = new DBServiceImpl();
		try {
			list = (List<CoopForm>) dbService.execSQLQueryTransformer("SELECT coopcode,coopname FROM Tbcooperative",
					null, CoopForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String addRecordsCasaFeesAndCharges(Tbcasafeesandcharges casaFees, String beingUpdated) {
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("coopcode", UserUtil.getUserByUsername(serviceS.getUserName()).getCoopcode());
		param.put("subcode", casaFees.getSubcode());
		try {
			if (casaFees != null) {
				if (beingUpdated.equals("new")) {
					Tbcasafeesandcharges check = (Tbcasafeesandcharges) dbService.executeUniqueHQLQuery(
							"FROM Tbcasafeesandcharges WHERE subcode=:subcode and coopcode =:coopcode", param);
					if (check != null) {
						return "existing";
					}
					casaFees.setCreatedby(serviceS.getUserName());
					casaFees.setDatecreated(new Date());
					dbService.saveOrUpdate(casaFees);
				} else if (beingUpdated.equals("update")) {
					casaFees.setDateupdated(new Date());
					casaFees.setUpdatedby(serviceS.getUserName());
					dbService.saveOrUpdate(casaFees);
				} else {
					param.put("id", casaFees.getId());
					dbService.executeUpdate("DELETE FROM Tbcasafeesandcharges WHERE id=:id", param);
				}
				return "success";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "error";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbreferrors> getRefNameByReferrorType(String reftype) {
		List<Tbreferrors> list = new ArrayList<Tbreferrors>();
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("type", reftype);
			list = (List<Tbreferrors>) dbService.execSQLQueryTransformer(
					"SELECT id, referraltype, referrorname FROM Tbreferrors WHERE referraltype=:type ORDER BY referrorname ASC",
					params, Tbreferrors.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String viewImage(int id, String imgtype) {
		String filepath = null;
		String ext = null;
		DBService dbService = new DBServiceImpl();
		Tbproperties pro = new Tbproperties();
		File dir = new File(
				RuntimeAccess.getInstance().getSession().getServletContext().getRealPath("resources/images"));
		Map<String, Object> params = HQLUtil.getMap();
		params.put("id", id);
		pro = (Tbproperties) dbService.executeUniqueHQLQuery("FROM Tbproperties where id=:id", params);
		if (pro != null) {
			try {
				if(imgtype.equals("H")) {
					System.out.print("marrrrrrr H : " + imgtype);
					if (pro.getCompanyheaderlogo() != null) {
						ImageUtils.base64ToPDF(pro.getCompanyheaderlogo(), dir.toString() + "\\", pro.getCompanyheaderlogofilename());
						ext = pro.getCompanyheaderlogofilename().substring(pro.getCompanyheaderlogofilename().lastIndexOf("."));
						if (ext.equals(".png")) {
							filepath = "resources/images/" + pro.getCompanyheaderlogofilename();
						} else {
							filepath = ImageUtils.ImageToPDF(dir.toString() + "\\", pro.getCompanyheaderlogofilename());
						}
					} else {
						return "failed";
					}
				}else {
					System.out.print("marrrrrrr W : " + imgtype);
					if (pro.getCompanywelcomelogo() != null) {
						ImageUtils.base64ToPDF(pro.getCompanywelcomelogo(), dir.toString() + "\\", pro.getCompanywelcomelogofilename());
						ext = pro.getCompanywelcomelogofilename().substring(pro.getCompanywelcomelogofilename().lastIndexOf("."));
						if (ext.equals(".png")) {
							filepath = "resources/images/" + pro.getCompanywelcomelogofilename();
						} else {
							filepath = ImageUtils.ImageToPDF(dir.toString() + "\\", pro.getCompanywelcomelogofilename());
						}
					} else {
						return "failed";
					}
				}
				
				// System.out.println(filepath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			return "failed";
		}
		return filepath;
	}

	@Override
	public String checkPicOrPDF(int id, String imgtype) {
		String filepath = null;
		String ext = null;
		Tbproperties pro = new Tbproperties();
		DBService dbService = new DBServiceImpl();
		File dir = new File(
				RuntimeAccess.getInstance().getSession().getServletContext().getRealPath("resources/images"));
		Map<String, Object> params = HQLUtil.getMap();
		params.put("id", id);

		try {
			pro = (Tbproperties) dbService
					.executeUniqueHQLQueryMaxResultOne("FROM Tbproperties WHERE id=:id", params);
			if (pro != null) {
				try {
					if(imgtype.equals("H")) {
						if (pro.getCompanyheaderlogo() != null) {
							ImageUtils.base64ToPDF(pro.getCompanyheaderlogo(), dir.toString() + "\\", pro.getCompanyheaderlogofilename());
							filepath = "resources\\images\\" + pro.getCompanyheaderlogofilename();

							ext = filepath.substring(filepath.lastIndexOf("."));
							
						} else {
							
							return "failed";
						}
					}else {
						if (pro.getCompanywelcomelogo() != null) {
							ImageUtils.base64ToPDF(pro.getCompanywelcomelogo(), dir.toString() + "\\", pro.getCompanywelcomelogofilename());
							filepath = "resources\\images\\" + pro.getCompanywelcomelogofilename();

							ext = filepath.substring(filepath.lastIndexOf("."));
							
						} else {
							
							return "failed";
						}
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				
				return "failed";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ext;
	}

	@Override
	public String saveOrUpdateCollectionArea(Tbcollectionarea d, Boolean isChangeOrUpdated) {
		String flag = "failed";
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(d.getId()!=null) {
				
				if(isChangeOrUpdated!=null && isChangeOrUpdated) { // areacode and sub-areacode
					// check if existing
					if(d.getAreacode()!=null && d.getSubareacode()!=null) {
						params.put("areacode", d.getAreacode().trim());
						params.put("subareacode", d.getSubareacode().trim());
						Tbcollectionarea dup = (Tbcollectionarea)dbServiceCOOP.executeUniqueHQLQuery
								("FROM Tbcollectionarea WHERE areacode=:areacode AND subareacode=:subareacode", params);
						if(dup!=null) {
							return "existing";
						}else {
							// update record
							params.put("id", d.getId());
							Tbcollectionarea row = (Tbcollectionarea)dbServiceCOOP.executeUniqueHQLQuery
									("FROM Tbcollectionarea WHERE id=:id", params);
							if(row!=null) {
								row.setAreacode(d.getAreacode().trim());
								row.setAreaname(d.getAreaname().trim());
								row.setSubareacode(d.getSubareacode().trim());
								row.setSubareaname(d.getSubareaname().trim());
								if(dbServiceCOOP.saveOrUpdate(row)) {
									flag = "success";
								}
							}
						}
					}
				}else {
					// no changes areacode and sub-areacode
					// update record
					params.put("id", d.getId());
					Tbcollectionarea row = (Tbcollectionarea)dbServiceCOOP.executeUniqueHQLQuery
							("FROM Tbcollectionarea WHERE id=:id", params);
					if(row!=null) {
						row.setAreacode(d.getAreacode().trim());
						row.setAreaname(d.getAreaname().trim());
						row.setSubareacode(d.getSubareacode().trim());
						row.setSubareaname(d.getSubareaname().trim());
						if(dbServiceCOOP.saveOrUpdate(row)) {
							flag = "success";
						}
					}
				}
			}else {
				// check if existing
				if(d.getAreacode()!=null && d.getSubareacode()!=null) {
					params.put("areacode", d.getAreacode().trim());
					params.put("subareacode", d.getSubareacode().trim());
					Tbcollectionarea dup = (Tbcollectionarea)dbServiceCOOP.executeUniqueHQLQuery
							("FROM Tbcollectionarea WHERE areacode=:areacode AND subareacode=:subareacode", params);
					if(dup!=null) {
						return "existing";
					}else {
						// new record
						Tbcollectionarea n = new Tbcollectionarea();
						n.setAreacode(d.getAreacode().trim());
						n.setAreaname(d.getAreaname().trim());
						n.setSubareacode(d.getSubareacode().trim());
						n.setSubareaname(d.getSubareaname().trim());
						n.setStatus("1"); // Active
						n.setDatecreated(new Date());
						n.setCreatedby(serviceS.getUserName());
						if(dbServiceCOOP.save(n)) {
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
	public List<Tbcollectionarea> listTbcollectionarea(String status, String areacode) {
		List<Tbcollectionarea> list = new ArrayList<Tbcollectionarea>();
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(status!=null) {
				params.put("status", status);
				list = (List<Tbcollectionarea>)dbServiceCOOP.executeListHQLQuery
						("FROM Tbcollectionarea WHERE status=:status", params);
			}else {
				StringBuilder sb = new StringBuilder();
				
					sb.append("SELECT * FROM Tbcollectionarea WHERE ID > 0"); // List All
					if(areacode!=null) {
						params.put("areacode", areacode);
						sb.append(" AND areacode=:areacode"); 
					}
					list = (List<Tbcollectionarea>) dbServiceCOOP.execSQLQueryTransformer
							(sb.toString(), params, Tbcollectionarea.class, 1);				
				
				/*list = (List<Tbcollectionarea>)dbServiceCOOP.executeListHQLQuery
						("FROM Tbcollectionarea", null);*/
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String activateOrDeactivateCollectionArea(Tbcollectionarea d) {
		String flag = "failed";
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(d.getId()!=null) {
				
				// update record
				params.put("id", d.getId());
				Tbcollectionarea row = (Tbcollectionarea)dbServiceCOOP.executeUniqueHQLQuery
						("FROM Tbcollectionarea WHERE id=:id", params);
				if(row!=null) {
					row.setStatus(d.getStatus());
					if(dbServiceCOOP.saveOrUpdate(row)) {
						flag = "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String saveOrUpdateCollector(Tbcollector d, Boolean isChangeOrUpdated) {
		String flag = "failed";
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(d.getId()!=null) {
				
				if(isChangeOrUpdated!=null && isChangeOrUpdated) { // collector id
					// check if existing 
					if(d.getCollectorid()!=null){
						params.put("colid", d.getCollectorid().trim());
						Tbcollector dup = (Tbcollector)dbServiceCOOP.executeUniqueHQLQuery
								("FROM Tbcollector WHERE collectorid=:colid", params);
						if(dup!=null) {
							return "existing";
						}else {
							// update record
							params.put("id", d.getId());
							Tbcollector row = (Tbcollector)dbServiceCOOP.executeUniqueHQLQuery
									("FROM Tbcollector WHERE id=:id", params);
							if(row!=null) {
								row.setCollectorid(d.getCollectorid().trim());
								row.setCollectorname(d.getCollectorname().trim());
								
								row.setAreacode(d.getAreacode().trim());
								row.setSubareacode(d.getSubareacode().trim());
								if(dbServiceCOOP.saveOrUpdate(row)) {
									flag = "success";
								}
							}
						}
					}
				}else {
					// no changes in collector id
					// update record
					params.put("id", d.getId());
					Tbcollector row = (Tbcollector)dbServiceCOOP.executeUniqueHQLQuery
							("FROM Tbcollector WHERE id=:id", params);
					if(row!=null) {
						row.setCollectorid(d.getCollectorid().trim());
						row.setCollectorname(d.getCollectorname().trim());
						
						row.setAreacode(d.getAreacode().trim());
						row.setSubareacode(d.getSubareacode().trim());
						if(dbServiceCOOP.saveOrUpdate(row)) {
							flag = "success";
						}
					}
				}
			}else {
				// check if existing
				if(d.getCollectorid()!=null){
					params.put("colid", d.getCollectorid().trim());
					Tbcollector dup = (Tbcollector)dbServiceCOOP.executeUniqueHQLQuery
							("FROM Tbcollector WHERE collectorid=:colid", params);
					if(dup!=null) {
						return "existing";
					}else {
						// new record
						Tbcollector n = new Tbcollector();
						n.setCollectorid(d.getCollectorid().trim());
						n.setCollectorname(d.getCollectorname().trim());
						
						n.setAreacode(d.getAreacode().trim());
						n.setSubareacode(d.getSubareacode().trim());
						
						n.setStatus("1"); // Active
						n.setDatecreated(new Date());
						n.setCreatedby(serviceS.getUserName());
						if(dbServiceCOOP.save(n)) {
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
	public List<Tbcollector> listTbcollector(String status, String areacode, String subareacode) {
		List<Tbcollector> list = new ArrayList<Tbcollector>();
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("SELECT * FROM Tbcollector WHERE status IN ('0','1')"); // List All
			if(areacode!=null) {
				params.put("areacode", areacode);
				sb.append(" AND areacode=:areacode"); 
			}
			if(subareacode!=null) {
				params.put("subareacode", subareacode);
				sb.append(" AND subareacode=:subareacode"); 
			}
			list = (List<Tbcollector>) dbServiceCOOP.execSQLQueryTransformer
					(sb.toString(), params, Tbcollector.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String activateOrDeactivateCollector(Tbcollector d) {
		String flag = "failed";
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(d.getId()!=null) {
				
				// update record
				params.put("id", d.getId());
				Tbcollector row = (Tbcollector)dbServiceCOOP.executeUniqueHQLQuery
						("FROM Tbcollector WHERE id=:id", params);
				if(row!=null) {
					row.setStatus(d.getStatus());
					if(dbServiceCOOP.saveOrUpdate(row)) {
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
	public List<Tbcollectionarea> listAreacode() {
		List<Tbcollectionarea> list = new ArrayList<Tbcollectionarea>();
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			list = (List<Tbcollectionarea>) dbServiceCOOP.execSQLQueryTransformer
					("SELECT DISTINCT areacode, areaname FROM TBCOLLECTIONAREA", params, Tbcollectionarea.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcollectionarea> listSubAreacode(String areacode) {
		List<Tbcollectionarea> list = new ArrayList<Tbcollectionarea>();
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(areacode!=null) {
				params.put("areacode", areacode);
				list = (List<Tbcollectionarea>) dbServiceCOOP.execSQLQueryTransformer
						("SELECT subareacode, subareaname FROM TBCOLLECTIONAREA WHERE areacode=:areacode", params, Tbcollectionarea.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BranchSysParamsForm> listBranch() {
		List<BranchSysParamsForm> list = new ArrayList<BranchSysParamsForm>();
		DBService dbServiceCOOP = new DBServiceImpl();
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("SELECT branchcode, branchname FROM Tbbranch"); // List All
			list = (List<BranchSysParamsForm>) dbServiceCOOP.execSQLQueryTransformer
					(sb.toString(), null, BranchSysParamsForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CollectionAreaForm> listArea(String branchcode) {
		List<CollectionAreaForm> list = new ArrayList<CollectionAreaForm>();
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		StringBuilder sb = new StringBuilder();
		try {
			if(branchcode!=null) {
				params.put("branchcode", branchcode);
				sb.append("SELECT id, branchcode, areacode, areaname FROM Tbcolareamaintenance WHERE branchcode=:branchcode"); 
				list = (List<CollectionAreaForm>) dbServiceCOOP.execSQLQueryTransformer
						(sb.toString(), params, CollectionAreaForm.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CollectionAreaForm> listSubArea(String branchcode, String areacode) {
		List<CollectionAreaForm> list = new ArrayList<CollectionAreaForm>();
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		StringBuilder sb = new StringBuilder();
		try {
			if(branchcode!=null && areacode!=null) {
				params.put("branchcode", branchcode);
				params.put("areacode", areacode);
				sb.append("SELECT id, branchcode, areacode, subareacode, subareaname FROM Tbcolsubareamaintenance WHERE branchcode=:branchcode AND areacode=:areacode"); 
				list = (List<CollectionAreaForm>) dbServiceCOOP.execSQLQueryTransformer
						(sb.toString(), params, CollectionAreaForm.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CollectionAreaForm> listCollector() {
		List<CollectionAreaForm> list = new ArrayList<CollectionAreaForm>();
		DBService dbServiceCOOP = new DBServiceImpl();
		StringBuilder sb = new StringBuilder();
		try {
				//sb.append("SELECT id, collectorid, branchcode, (collectouserrname) AS collectoridusername, (Select fullname FROM Tbuser WHERE username=collectouserrname) AS collectorname FROM Tbcollectormaintenance"); 
			sb.append("SELECT a.id, a.collectorid, a.branchcode, b.branchname,"
					+ " (a.collectouserrname) AS collectoridusername,"
					+ " (Select fullname FROM Tbuser WHERE username=a.collectouserrname) AS collectorname"
					+ " FROM Tbcollectormaintenance a"
					+ " LEFT JOIN TBBRANCH b ON b.branchcode=a.branchcode");	
			list = (List<CollectionAreaForm>) dbServiceCOOP.execSQLQueryTransformer
						(sb.toString(), null, CollectionAreaForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CollectionAreaForm> listCollectorPerSubArea(String collectorid) {
		List<CollectionAreaForm> list = new ArrayList<CollectionAreaForm>();
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		StringBuilder sb = new StringBuilder();
		try {
			if(collectorid!=null) {
				params.put("collectorid", collectorid);
				//sb.append("SELECT id, branchcode, areacode, subareacode, subareaname, collectorid FROM Tbcollectorpersubarea WHERE collectorid=:collectorid"); 
				sb.append("SELECT a.id, b.branchname, c.areaname, d.subareaname,"
						+ " a.branchcode, a.areacode, a.subareacode, a.collectorid FROM Tbcollectorpersubarea a"
						+ " LEFT JOIN TBBRANCH b ON a.branchcode=b.branchcode"
						+ " LEFT JOIN TBCOLAREAMAINTENANCE c ON c.branchcode=a.branchcode AND c.areacode=a.areacode"
						+ " LEFT JOIN TBCOLSUBAREAMAINTENANCE d ON d.branchcode=a.branchcode AND d.areacode=a.areacode AND d.subareacode=a.subareacode"
						+ " WHERE a.collectorid=:collectorid");
				list = (List<CollectionAreaForm>) dbServiceCOOP.execSQLQueryTransformer
						(sb.toString(), params, CollectionAreaForm.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserSysParamsForm> listUser(String branchcode) {
		List<UserSysParamsForm> list = new ArrayList<UserSysParamsForm>();
		DBService dbServiceCOOP = new DBServiceImpl();
		StringBuilder sb = new StringBuilder();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(branchcode!=null) {
				params.put("branchcode", branchcode);
				sb.append("SELECT username, fullname FROM Tbuser WHERE branchcode=:branchcode"); // List All
				list = (List<UserSysParamsForm>) dbServiceCOOP.execSQLQueryTransformer
						(sb.toString(), params, UserSysParamsForm.class, 1);
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}	

	@Override
	public String deleteItem(Integer id, String type) {
		String flag = "failed";
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(id!=null && type!=null) {
				
				params.put("id", id);
				Integer res = null;
				
				if(type.equalsIgnoreCase("AREA")) {
					res = dbServiceCOOP.executeUpdate("DELETE FROM Tbcolareamaintenance WHERE id=:id", params);
				}else if(type.equalsIgnoreCase("SUBAREA")) {
					res = dbServiceCOOP.executeUpdate("DELETE FROM Tbcolsubareamaintenance WHERE id=:id", params);
				}else if(type.equalsIgnoreCase("COLLECTOR")) {
					res = dbServiceCOOP.executeUpdate("DELETE FROM Tbcollectormaintenance WHERE id=:id", params);
				}else if(type.equalsIgnoreCase("COLLECTORSA")) {
					res = dbServiceCOOP.executeUpdate("DELETE FROM Tbcollectorpersubarea WHERE id=:id", params);
				}
				if(res != null && res == 1){
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String saveOrUpdateArea(Tbcolareamaintenance d, Boolean isChangeOrUpdated) {
		String flag = "failed";
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(d.getId()!=null) {
				
//				if(isChangeOrUpdated!=null && isChangeOrUpdated) { // AREA CODE
//					// check if existing
//					if(d.getBranchcode()!=null && d.getAreacode()!=null) {
//						params.put("branchcode", d.getBranchcode().trim());
//						params.put("areacode", d.getAreacode().trim());
//						Tbcolareamaintenance dup = (Tbcolareamaintenance)dbServiceCOOP.executeUniqueHQLQuery
//								("FROM Tbcolareamaintenance WHERE branchcode=:branchcode AND areacode=:areacode", params);
//						if(dup!=null) {
//							return "existing";
//						}else {
//							// update record
//							params.put("id", d.getId());
//							Tbcolareamaintenance row = (Tbcolareamaintenance)dbServiceCOOP.executeUniqueHQLQuery
//									("FROM Tbcolareamaintenance WHERE id=:id", params);
//							if(row!=null) {
//								row.setAreaname(d.getAreaname().trim());
//								if(dbServiceCOOP.saveOrUpdate(row)) {
//									flag = "success";
//								}
//							}
//						}
//					}
//				}else {
					// No changes - AREA CODE
					// Update Record
					params.put("id", d.getId());
					Tbcolareamaintenance row = (Tbcolareamaintenance)dbServiceCOOP.executeUniqueHQLQuery
							("FROM Tbcolareamaintenance WHERE id=:id", params);
					if(row!=null) {
						row.setAreaname(d.getAreaname().trim());
						if(dbServiceCOOP.saveOrUpdate(row)) {
							flag = "success";
						}
//					}
				}
			}else {
				// check if existing
				if(d.getBranchcode()!=null && d.getAreacode()!=null) {
					params.put("branchcode", d.getBranchcode().trim());
					params.put("areacode", d.getAreacode().trim());
					Tbcolareamaintenance dup = (Tbcolareamaintenance)dbServiceCOOP.executeUniqueHQLQuery
							("FROM Tbcolareamaintenance WHERE branchcode=:branchcode AND areacode=:areacode", params);
					if(dup!=null) {
						return "existing";
					}else {
						// new record
						Tbcolareamaintenance n = new Tbcolareamaintenance();
						n.setBranchcode(d.getBranchcode().trim());
						n.setAreacode(d.getAreacode().trim());
						n.setAreaname(d.getAreaname().trim());
						n.setDatecreated(new Date());
						n.setCreatedby(serviceS.getUserName());
						if(dbServiceCOOP.save(n)) {
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

	@Override
	public String saveOrUpdateSubArea(Tbcolsubareamaintenance d, Boolean isChangeOrUpdated) {
		String flag = "failed";
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(d.getId()!=null) {
				
//				if(isChangeOrUpdated!=null && isChangeOrUpdated) { // SUB-AREA CODE
//					// check if existing
//					if(d.getBranchcode()!=null && d.getAreacode()!=null && d.getSubareacode()!=null) {
//						params.put("branchcode", d.getBranchcode().trim());
//						params.put("areacode", d.getAreacode().trim());
//						params.put("subareacode", d.getSubareacode().trim());
//						Tbcolsubareamaintenance dup = (Tbcolsubareamaintenance)dbServiceCOOP.executeUniqueHQLQuery
//								("FROM Tbcolsubareamaintenance WHERE branchcode=:branchcode AND areacode=:areacode AND subareacode=:subareacode", params);
//						if(dup!=null) {
//							return "existing";
//						}else {
//							// update record
//							params.put("id", d.getId());
//							Tbcolsubareamaintenance row = (Tbcolsubareamaintenance)dbServiceCOOP.executeUniqueHQLQuery
//									("FROM Tbcolsubareamaintenance WHERE id=:id", params);
//							if(row!=null) {
//								row.setSubareaname(d.getSubareaname().trim());
//								if(dbServiceCOOP.saveOrUpdate(row)) {
//									flag = "success";
//								}
//							}
//						}
//					}
//				}else {
					// No changes - SUB-AREA CODE
					// Update Record
					params.put("id", d.getId());
					Tbcolsubareamaintenance row = (Tbcolsubareamaintenance)dbServiceCOOP.executeUniqueHQLQuery
							("FROM Tbcolsubareamaintenance WHERE id=:id", params);
					if(row!=null) {
						row.setSubareaname(d.getSubareaname().trim());
						if(dbServiceCOOP.saveOrUpdate(row)) {
							flag = "success";
						}
					}
//				}
			}else {
				// check if existing
				/*System.out.println("branchcode" + d.getBranchcode().trim());
				System.out.println("areacode" + d.getAreacode().trim());
				System.out.println("subareacode" + d.getSubareacode().trim());*/
				if(d.getBranchcode()!=null && d.getAreacode()!=null && d.getSubareacode()!=null) {
					params.put("branchcode", d.getBranchcode().trim());
					params.put("areacode", d.getAreacode().trim());
					params.put("subareacode", d.getSubareacode().trim());
					Tbcolsubareamaintenance dup = (Tbcolsubareamaintenance)dbServiceCOOP.executeUniqueHQLQuery
							("FROM Tbcolsubareamaintenance WHERE branchcode=:branchcode AND areacode=:areacode AND subareacode=:subareacode", params);
					if(dup!=null) {
						return "existing";
					}else {
						// new record
						Tbcolsubareamaintenance n = new Tbcolsubareamaintenance();
						n.setBranchcode(d.getBranchcode().trim());
						n.setAreacode(d.getAreacode().trim());
						n.setSubareacode(d.getSubareacode().trim());
						n.setSubareaname(d.getSubareaname().trim());
						n.setDatecreated(new Date());
						n.setCreatedby(serviceS.getUserName());
						if(dbServiceCOOP.save(n)) {
							flag = "success";
						}
					}
					//System.out.println("dup" + dup);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unused")
	@Override
	public String saveOrUpdateCollector2(Tbcollectormaintenance d, Boolean isChangeOrUpdated) {
		String flag = "failed";
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(d.getId()!=null) {
				
				// do nothing - no update facility
				
			}else {
				// check if existing username
				
				//System.out.println("collectorusername - " + d.getCollectouserrname().trim());
				//System.out.println("collectorid - " + d.getCollectorid().trim());
				if(d.getCollectouserrname()!=null) {
					
					params.put("collectorusername", d.getCollectouserrname().trim());
					Tbcollectormaintenance dup = (Tbcollectormaintenance)dbServiceCOOP.executeUniqueHQLQuery
							("FROM Tbcollectormaintenance WHERE collectouserrname=:collectorusername", params);
					if(dup!=null) {
						return "existing username";
					}else {
						
						// check if existing collectorid
						if(d.getCollectorid()!=null) {
							params.put("collectorid", d.getCollectorid().trim());
							Tbcollectormaintenance dup2 = (Tbcollectormaintenance)dbServiceCOOP.executeUniqueHQLQuery
									("FROM Tbcollectormaintenance WHERE collectorid=:collectorid", params);
							//System.out.println("dup2 - " + dup2);
							if(dup2!=null) {
								return "existing collectorid";
							}else {
								// new record
								Tbcollectormaintenance n = new Tbcollectormaintenance();
								n.setCollectorid(d.getCollectorid());
								n.setCollectouserrname(d.getCollectouserrname());
								// collector name - query to tbuser.fullname
								n.setBranchcode(d.getBranchcode());
								n.setDatecreated(new Date());
								n.setCreatedby(serviceS.getUserName());
								if(dbServiceCOOP.save(n)) {
									flag = "success";
								}
							}
						}
					}
					//System.out.println("dup - " + dup);
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String saveOrUpdateCollectorPerSubArea(Tbcollectorpersubarea d, Boolean isChangeOrUpdated) {
		String flag = "failed";
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(d.getId()!=null) {
				
				// do nothing - no update facility
				
			}else {
				// check if existing
				if(d.getBranchcode()!=null && d.getAreacode()!=null) {
					params.put("branchcode", d.getBranchcode().trim());
					params.put("areacode", d.getAreacode().trim());
					params.put("subareacode", d.getSubareacode().trim());
					params.put("collectorid", d.getCollectorid().trim());
					Tbcollectorpersubarea dup = (Tbcollectorpersubarea)dbServiceCOOP.executeUniqueHQLQuery
							("FROM Tbcollectorpersubarea WHERE branchcode=:branchcode AND areacode=:areacode AND subareacode=:subareacode AND collectorid=:collectorid", params);
					if(dup!=null) {
						return "existing";
					}else {
						Tbcollectorpersubarea dup2 = (Tbcollectorpersubarea)dbServiceCOOP.executeUniqueHQLQuery
								("FROM Tbcollectorpersubarea WHERE branchcode=:branchcode AND areacode=:areacode AND subareacode=:subareacode", params);
						if(dup2!=null) {
							// record found
							// already assigned a collector
							return "already assigned";
						}else {
							// new record
							Tbcollectorpersubarea n = new Tbcollectorpersubarea();
							n.setBranchcode(d.getBranchcode().trim());
							n.setAreacode(d.getAreacode().trim());
							n.setSubareacode(d.getSubareacode().trim());
							//n.setSubareaname(d.getSubareaname().trim());
							n.setCollectorid(d.getCollectorid());
							n.setUsername(d.getUsername());
							n.setDatecreated(new Date());
							n.setCreatedby(serviceS.getUserName());
							if(dbServiceCOOP.save(n)) {
								flag = "success";
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

	@Override
	public CollectionAreaForm getCollectorName(String branchcode, String areacode, String subareacode) {
		CollectionAreaForm row = new CollectionAreaForm();
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		StringBuilder sb = new StringBuilder();
		try {
			if(branchcode!=null && areacode!=null && subareacode!=null) {
				params.put("branchcode", branchcode);
				params.put("areacode", areacode);
				params.put("subareacode", subareacode);
				
				sb.append("SELECT b.fullname AS collectorname"
						+ " FROM TBCOLLECTORPERSUBAREA a"
						+ " LEFT JOIN TBUSER b ON b.username=a.username"
						+ " WHERE a.branchcode=:branchcode AND a.areacode=:areacode AND a.subareacode=:subareacode"); 
				row = (CollectionAreaForm) dbServiceCOOP.execSQLQueryTransformer
						(sb.toString(), params, CollectionAreaForm.class, 0);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	@Override
	public String saveOrUpdateAreaSubArea(Tbcifmain d, String changetype, String remarks) {
		String flag = "failed";
		//DBService dbServiceCOOP = new DBServiceImpl();
		DBService dbServiceCIF = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(changetype!=null && d.getCifno()!=null) {
				params.put("cifno", d.getCifno());
				Tbcifmain main  = (Tbcifmain) dbServiceCIF.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno=:cifno", params);
				if(main!=null) {
					Tbchangecifdetailsrequest req = new Tbchangecifdetailsrequest();
					
					if(changetype.equals("38")) {
						// Area
						req.setChangefrom(main.getCollectorareacode()); // existing data
						req.setChangeto(d.getCollectorareacode()); // new data
						
						main.setCollectorareacode(d.getCollectorareacode()); 
					}else if(changetype.equals("39")) {
						// Sub-Area
						req.setChangefrom(main.getCollectorsubareacode()); // existing data
						req.setChangeto(d.getCollectorsubareacode()); // new data
						
						main.setCollectorsubareacode(d.getCollectorsubareacode());
					}
					if(dbServiceCIF.saveOrUpdate(main)) {
						// call change cif
						ChangeCIFDetailsService changecif =  new ChangeCIFDetailsServiceImpl();
						req.setRequestid(changecif.generateRequestID(changetype));
						
						req.setRequestedby(UserUtil.securityService.getUserName());
						req.setDaterequested(new Date());
						req.setRequeststatus("1");
						req.setCifno(d.getCifno());
						req.setFullname(main.getFullname());
						req.setChangetype(changetype);
						req.setRemarks(remarks);
						
						if (dbServiceCIF.save(req)) {
							flag = "update";
							return flag;
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
	public List<CollectionAreaForm> listCollectorPerBranchcode(String branchcode) {
		List<CollectionAreaForm> list = new ArrayList<CollectionAreaForm>();
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		StringBuilder sb = new StringBuilder();
		try {
			if(branchcode!=null) {
				params.put("branchcode", branchcode);
				sb.append("SELECT a.collectorid, (a.collectouserrname) AS collectoridusername, (b.fullname) AS collectorname"
						+ " FROM TBCOLLECTORMAINTENANCE a"
						+ " LEFT JOIN TBUSER b ON b.username=a.collectouserrname"
						+ " WHERE a.branchcode=:branchcode"); 
				list = (List<CollectionAreaForm>) dbServiceCOOP.execSQLQueryTransformer
						(sb.toString(), params, CollectionAreaForm.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CollectionAreaForm> listCollectorForReports(String branchcode, String areacode, String subareacode) {
		List<CollectionAreaForm> list = new ArrayList<CollectionAreaForm>();
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		StringBuilder sb = new StringBuilder();
		try {
			if(branchcode!=null) {
				params.put("branchcode", branchcode);
				
				sb.append("SELECT DISTINCT(a.username) AS collectoridusername, (b.fullname) AS collectorname"
						+ " FROM TBCOLLECTORPERSUBAREA a"
						+ " LEFT JOIN TBUSER b ON b.username=a.username"
						+ " WHERE a.branchcode=:branchcode"); 
				if(areacode!=null) {
					params.put("areacode", areacode);
					sb.append(" AND a.areacode=:areacode");
				}
				if(subareacode!=null) {
					params.put("subareacode", subareacode);
					sb.append(" AND a.subareacode=:subareacode");
				}
				list = (List<CollectionAreaForm>) dbServiceCOOP.execSQLQueryTransformer
						(sb.toString(), params, CollectionAreaForm.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcoamaintenance> getAndListTbcoa() {
		List<Tbcoamaintenance> list = new ArrayList<Tbcoamaintenance>();
		DBService dbServiceCoop = new DBServiceImpl();
		//Map<String, Object> params = HQLUtil.getMap();
		try {
				list = (List<Tbcoamaintenance>) dbServiceCoop.executeListHQLQuery("FROM Tbcoamaintenance", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String saveOrDeleteGlCode(String accountno, String desc, String type) {
		String flag = "failed";
		DBService dbServiceCoop = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();

		try {
			if(accountno!=null && desc!=null && type!=null) {
				params.put("accountno", accountno);
				if(type.equalsIgnoreCase("ADD")) {
					Tbcoamaintenance row = (Tbcoamaintenance) dbServiceCoop.executeUniqueHQLQuery
							("FROM Tbcoamaintenance WHERE accountno=:accountno", params);
					if(row!=null) {
						flag = "existing";
					}else {
						Tbcoamaintenance n = new Tbcoamaintenance();
						n.setAccountno(accountno);
						
						Tbcoa coa = (Tbcoa) dbServiceCoop.executeUniqueHQLQuery("FROM Tbcoa WHERE accountno=:accountno", params);
						if(coa!=null) {
							n.setAcctdesc(coa.getAcctdesc());
						}
						
						n.setDatecreated(new Date());
						n.setCreatedby(username);
						if(dbServiceCoop.save(n)) {
							flag = "success";
						}
					}
				}else if(type.equalsIgnoreCase("DEL")) {
					Tbcoamaintenance row = (Tbcoamaintenance) dbServiceCoop.executeUniqueHQLQuery
							("FROM Tbcoamaintenance WHERE accountno=:accountno", params);
					if(row!=null) {
						if(dbServiceCoop.delete(row)) {
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
	public List<Tbcoa> getAndListTbcoaByAcctnoAndDesc(String acctno, String desc) {
		List<Tbcoa> list = new ArrayList<Tbcoa>();
		DBService dbServiceCoop = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			
			if(acctno!=null && desc!=null) {
				params.put("acctno", acctno == null ? "%" : "%" + acctno + "%");
				params.put("desc", desc == null ? "%" : "%" + desc + "%");
				list = (List<Tbcoa>) dbServiceCoop.execSQLQueryTransformer
						("SELECT accountno, acctdesc FROM Tbcoa WHERE accountno LIKE :acctno AND acctdesc LIKE :desc", params, Tbcoa.class, 1);
			}else {
				list = (List<Tbcoa>) dbServiceCoop.execSQLQueryTransformer
						("SELECT accountno, acctdesc FROM Tbcoa", null, Tbcoa.class, 1);
				
				if(acctno!=null) {
					params.put("acctno", acctno == null ? "%" : "%" + acctno + "%");
					list = (List<Tbcoa>) dbServiceCoop.execSQLQueryTransformer
							("SELECT accountno, acctdesc FROM Tbcoa WHERE accountno LIKE :acctno", params, Tbcoa.class, 1);
				}
				if(desc!=null) {
					params.put("desc", desc == null ? "%" : "%" + desc + "%");
					list = (List<Tbcoa>) dbServiceCoop.execSQLQueryTransformer
							("SELECT accountno, acctdesc FROM Tbcoa WHERE acctdesc LIKE :desc", params, Tbcoa.class, 1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String saveOrUpdateHoliday(Tbholiday d, String type) {
		String flag = "failed";
		DBService dbServiceCoop = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(type!=null) {
				if(type.equalsIgnoreCase("DELETE")) {
					if(d.getId()!=null) {
						params.put("id", d.getId());
						Tbholiday row = (Tbholiday) dbServiceCoop.executeUniqueHQLQuery
								("FROM Tbholiday WHERE id=:id", params);
						if(row!=null) {
							if(dbServiceCoop.delete(row)) {
								flag = "success";
							}
						}
					}
				}else if(type.equalsIgnoreCase("SAVEORUPDATE")) {
					if(d.getId()!=null) {
						params.put("id", d.getId());
						Tbholiday row = (Tbholiday) dbServiceCoop.executeUniqueHQLQuery
								("FROM Tbholiday WHERE id=:id", params);
						if(row!=null) {
							row.setHolDate(d.getHolDate());
							row.setHolName(d.getHolName());
							row.setFrequency(d.getFrequency());
							row.setArea(d.getArea());
							row.setHolidaytype(d.getHolidaytype());
							row.setRemarks(d.getRemarks());
							row.setUpdatedby(serviceS.getUserName());
							row.setDateupdated(new Date());
							
							if(dbServiceCoop.saveOrUpdate(row)) {
								flag = "success";
							}
						}
					}else {
						// New record
						Tbholiday n = new Tbholiday();
						n.setHolDate(d.getHolDate());
						n.setHolName(d.getHolName());
						n.setFrequency(d.getFrequency());
						n.setArea(d.getArea());
						n.setHolidaytype(d.getHolidaytype());
						n.setRemarks(d.getRemarks());
						n.setCreatedby(serviceS.getUserName());
						n.setDatecreated(new Date());
						
						if(dbServiceCoop.save(n)) {
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
	public List<Tbholiday> listHoliday(String nationalorlocal, String holidayname, String branchcode) {
		List<Tbholiday> list = new ArrayList<Tbholiday>();
		DBService dbServiceCoop = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("holidayname", holidayname == null ? "%" : "%" + holidayname + "%");
		try {
			if(nationalorlocal!=null) {
				if(nationalorlocal.equalsIgnoreCase("NATIONAL")) {
					// 000
					list = (List<Tbholiday>) dbServiceCoop.executeListHQLQuery("FROM Tbholiday WHERE AREA IN ('000') AND holName LIKE :holidayname", params);
					/*list = (List<Tbholiday>) dbServiceCoop.execSQLQueryTransformer
							("SELECT * FROM TBHOLIDAY WHERE AREA IN ('000')", null, Tbholiday.class, 1);*/
				}else if(nationalorlocal.equalsIgnoreCase("LOCAL")) {
					// branch code
					/*list = (List<Tbholiday>) dbServiceCoop.execSQLQueryTransformer
							("SELECT * FROM TBHOLIDAY WHERE AREA NOT IN ('000')", params, Tbholiday.class, 1);*/
					
					if(branchcode!=null) {
						params.put("branchcode", branchcode);
						list = (List<Tbholiday>) dbServiceCoop.executeListHQLQuery("FROM Tbholiday WHERE AREA NOT IN ('000') AND holName LIKE :holidayname AND area=:branchcode", params);
					}else {
						list = (List<Tbholiday>) dbServiceCoop.executeListHQLQuery("FROM Tbholiday WHERE AREA NOT IN ('000') AND holName LIKE :holidayname", params);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return list;
	}

	@Override
	public String saveOrUpdateDeleteGovernmentContribution(Tbgovernmentcontribution d, String saveOrDel) {
		String flag = "failed";
		DBService dbServiceCoop = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(saveOrDel!=null) {
				if(saveOrDel.equalsIgnoreCase("SAVEORUPDATE")) {
					if(d.getId()!=null) {
						params.put("id", d.getId());
						Tbgovernmentcontribution row = (Tbgovernmentcontribution)dbServiceCoop.executeUniqueHQLQuery
								("FROM Tbgovernmentcontribution WHERE id=:id", params);
						row.setContributiontype(d.getContributiontype());
						row.setSalaryrangefrom(d.getSalaryrangefrom());
						row.setSalaryrangeto(d.getSalaryrangeto());
						row.setSharetype(d.getSharetype());
						row.setSharepercentage(d.getSharepercentage());
						row.setAmountcontribution(d.getAmountcontribution());
						row.setUpdatedby(serviceS.getUserName());
						row.setDateupdated(new Date());
						if(dbServiceCoop.saveOrUpdate(row)) {
							flag = "success";
						}
					}else {
						Tbgovernmentcontribution n = new Tbgovernmentcontribution();
						n.setContributiontype(d.getContributiontype());
						n.setSalaryrangefrom(d.getSalaryrangefrom());
						n.setSalaryrangeto(d.getSalaryrangeto());
						n.setSharetype(d.getSharetype());
						n.setSharepercentage(d.getSharepercentage());
						n.setAmountcontribution(d.getAmountcontribution());
						n.setCreatedby(serviceS.getUserName());
						n.setDatecreated(new Date());
						if(dbServiceCoop.save(n)) {
							flag = "success";
						}
					}
				}else if(saveOrDel.equalsIgnoreCase("DELETE")) {
					if(d.getId()!=null) {
						params.put("id", d.getId());
						Tbgovernmentcontribution row = (Tbgovernmentcontribution)dbServiceCoop.executeUniqueHQLQuery
								("FROM Tbgovernmentcontribution WHERE id=:id", params);
						if(dbServiceCoop.delete(row)) {
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
	public List<Tbgovernmentcontribution> getTbgovernmentcontribution(String contributionType) {
		List<Tbgovernmentcontribution> list = new ArrayList<Tbgovernmentcontribution>();
		DBService dbServiceCoop = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(contributionType!=null) {
				params.put("contributionType", contributionType);
				list = (List<Tbgovernmentcontribution>)dbServiceCoop.executeListHQLQuery
						("FROM Tbgovernmentcontribution WHERE contributiontype=:contributionType", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
