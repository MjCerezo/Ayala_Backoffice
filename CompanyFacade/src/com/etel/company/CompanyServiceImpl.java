package com.etel.company;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.coopdb.data.AyalaCompany;
import com.coopdb.data.Tbcomaker;
import com.coopdb.data.Tbcompany;
import com.coopdb.data.TbcompanyId;
import com.coopdb.data.Tbcooperative;
import com.coopdb.data.TbcooperativeId;
import com.coopdb.data.Tbgaaperbos;
import com.coopdb.data.Tbloanproduct;
import com.coopdb.data.Tbmembercompany;
import com.coopdb.data.Tbmembershiptypeperbos;
import com.coopdb.data.TbmembershiptypeperbosId;
import com.coopdb.data.Tbproductpercoop;
import com.coopdb.data.TbproductpercoopId;
import com.etel.codetable.CodetableService;
import com.etel.codetable.CodetableServiceImpl;
import com.etel.codetable.forms.CodetableForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.company.forms.CompanyBranchForm;
import com.etel.company.forms.CompanyForm;
import com.etel.company.forms.CooperativeForm;
import com.etel.utils.HQLUtil;
import com.etel.utils.ImageUtils;
import com.etel.utils.UserUtil;
import com.wavemaker.common.util.IOUtils;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;
import com.wavemaker.runtime.server.FileUploadResponse;

import org.cloudfoundry.org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("unchecked")
public class CompanyServiceImpl implements CompanyService {
	// private static final Logger logger =
	// Logger.getLogger(CompanyServiceImpl.class);
	SecurityService serviceS = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	DBService dbService = new DBServiceImpl();
	Map<String, Object> params = HQLUtil.getMap();

	/** Get List of Company **/
	@Override
	public List<CompanyForm> getListOfCompany(String tableparameter) {
		List<CompanyForm> form = new ArrayList<CompanyForm>();
		try {
			if (tableparameter.equalsIgnoreCase("TBCOOPERATIVE")) {
				List<Tbcooperative> list = (List<Tbcooperative>) dbService.executeListHQLQuery("FROM Tbcooperative",
						null);
				if (list != null) {
					for (Tbcooperative company : list) {
						CompanyForm companyForm = new CompanyForm();
						// companyForm.setCompanycode(company.getId().getCoopcode());
						// companyForm.setCompanyname(company.getId().getCoopname());
						companyForm.setCoopcode(company.getId().getCoopcode());
						companyForm.setCoopname(company.getId().getCoopname());
						companyForm.setAddress(company.getAddress());
						companyForm.setPhoneno(company.getPhoneno());
						companyForm.setFaxno(company.getFaxno());
						companyForm.setEmailaddress(company.getEmailaddress());
						companyForm.setWebsite(company.getWebsite());
						companyForm.setCoopstatus(company.getCoopstatus());
						companyForm.setDatecreated(company.getDatecreated());
						companyForm.setCreatedby(company.getCreatedby());
						companyForm.setDateupdated(company.getDateupdated());
						companyForm.setUpdatedby(company.getUpdatedby());
						companyForm.setCoopsize(company.getCoopsize());
						companyForm.setSharecapitalparval(company.getSharecapitalparval());
						companyForm.setMembershipfee(company.getMembershipfee());
						companyForm.setFinefee(company.getFinefee());
						companyForm.setLogofilename(company.getLogofilename());
						form.add(companyForm);
					}
				}
			}
			if (tableparameter.equalsIgnoreCase("TBCOMPANY")) {
				List<Tbcompany> listc = (List<Tbcompany>) dbService.executeListHQLQuery("FROM Tbcompany", null);
				if (listc != null) {
					for (Tbcompany company : listc) {
						CompanyForm companyForm = new CompanyForm();
						companyForm.setCompanycode(company.getId().getCompanycode());
						companyForm.setCompanyname(company.getId().getCompanyname());
						companyForm.setAddress(company.getAddress());
						companyForm.setPhoneno(company.getPhoneno());
						companyForm.setFaxno(company.getFaxno());
						companyForm.setEmailaddress(company.getEmailaddress());
						companyForm.setWebsite(company.getWebsite());
						companyForm.setCompanystatus(company.getCompanystatus());
						companyForm.setDatecreated(company.getDatecreated());
						companyForm.setCreatedby(company.getCreatedby());
						companyForm.setDateupdated(company.getDateupdated());
						companyForm.setUpdatedby(company.getUpdatedby());
						companyForm.setLogofilename(company.getLogofilename());
						form.add(companyForm);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	// edited by renz... added filepath string
	@SuppressWarnings("unchecked")
	@Override
	public String saveOrUpdateCompany(CompanyForm form, String tableparameter, String flag, String filepath) {
		File file = null;
		if (filepath != null) {
			file = new File(filepath);
		}
		List<Tbloanproduct> list = new ArrayList<Tbloanproduct>();
		try {
			params.put("companycode", form.getCompanycode());
			params.put("companyname", form.getCompanyname());
			if (tableparameter.equalsIgnoreCase("TBCOOPERATIVE")) {
				Tbcooperative company = (Tbcooperative) dbService.executeUniqueHQLQuery("FROM Tbcooperative", params);
				if (company != null) {
					if (flag.equalsIgnoreCase("NEW")) {
						return "existing";
					} else {
						TbcooperativeId comId = new TbcooperativeId();
						comId.setCoopcode(form.getCompanycode());
						comId.setCoopname(form.getCompanyname());
						company.setId(comId);
						company.setAddress(form.getAddress());
						company.setEmailaddress(form.getEmailaddress());
						company.setPhoneno(form.getPhoneno());
						company.setWebsite(form.getWebsite());
						company.setFaxno(form.getFaxno());
						company.setCoopsize(form.getCoopsize());
						company.setFinefee(form.getFinefee());
						company.setMembershipfee(form.getMembershipfee());
						company.setSharecapitalparval(form.getSharecapitalparval());
						System.out.print("");

						// renz
						if (filepath != null) {
							company.setLogofilename(file.getName());
							company.setLogobasecode(ImageUtils.pdfToBase64(filepath));
						} else {
							company.setLogofilename(company.getLogofilename());
							company.setLogobasecode(company.getLogobasecode());
						}

						if (dbService.saveOrUpdate(company))
							return "success";

					}
				} else {
					if (flag.equalsIgnoreCase("UPDATE")) {
						return "Cooperative not found.";
					} else {
						Tbcooperative c = new Tbcooperative();
						TbcooperativeId comId = new TbcooperativeId();
						comId.setCoopcode(form.getCompanycode());
						comId.setCoopname(form.getCompanyname());
						c.setId(comId);
						c.setAddress(form.getAddress());
						c.setEmailaddress(form.getEmailaddress());
						c.setPhoneno(form.getPhoneno());
						c.setWebsite(form.getWebsite());
						c.setCreatedby(serviceS.getUserName());
						c.setDatecreated(new Date());
						c.setFaxno(form.getFaxno());
						c.setCoopsize(form.getCoopsize());
						c.setMembershipfee(form.getMembershipfee());
						c.setFinefee(form.getFinefee());
						c.setSharecapitalparval(form.getSharecapitalparval());

						// renz
						if (filepath != null) {
							c.setLogofilename(file.getName());
							c.setLogobasecode(ImageUtils.pdfToBase64(filepath));
						} else {
							c.setLogofilename(c.getLogofilename());
							c.setLogobasecode(c.getLogobasecode());
						}

						if (dbService.save(c))
							list = (List<Tbloanproduct>) dbService.executeListHQLQuery("FROM Tbloanproduct", params);
						for (Tbloanproduct prod : list) {
							Tbproductpercoop coop = new Tbproductpercoop();
							TbproductpercoopId prodid = new TbproductpercoopId();
							prodid.setCoopcode(form.getCompanycode());
							prodid.setProductcode(prod.getProductcode());
							coop.setId(prodid);
							coop.setProductname(prod.getProductname());
							coop.setProducttype(prod.getProducttype1());
							coop.setAssignedby(serviceS.getUserName());
							coop.setDateassigned(new Date());
							coop.setIsactive(true);
							dbService.save(coop);

						}
						return "success";
					}
				}
			}
			if (tableparameter.equalsIgnoreCase("TBCOMPANY")) {
				Tbcompany company = (Tbcompany) dbService.executeUniqueHQLQuery(
						"FROM Tbcompany a WHERE a.id.companycode=:companycode AND a.id.companyname=:companyname",
						params);
				Tbcooperative coop = (Tbcooperative) dbService.executeUniqueHQLQueryMaxResultOne("FROM Tbcooperative",
						params); // update
				if (coop == null) {
					return "You must set up the institution first.";
				}
				if (company != null) {
					TbcompanyId comId = new TbcompanyId();
					comId.setCompanycode(form.getCompanycode());
					comId.setCompanyname(form.getCompanyname());
					company.setId(comId);
					company.setAddress(form.getAddress());
					company.setEmailaddress(form.getEmailaddress());
					company.setPhoneno(form.getPhoneno());
					company.setWebsite(form.getWebsite());
					company.setFaxno(form.getFaxno());
					// renz
					if (filepath != null) {
						company.setLogofilename(file.getName());
						company.setLogobasecode(ImageUtils.pdfToBase64(filepath));
					} else {
						company.setLogofilename(company.getLogofilename());
						company.setLogobasecode(company.getLogobasecode());
					}

					if (dbService.saveOrUpdate(company))
						return "success";
				}
				// save
				else {
					Tbcompany c = new Tbcompany();
					TbcompanyId comId = new TbcompanyId();
					comId.setCompanycode(form.getCompanycode());
					comId.setCompanyname(form.getCompanyname());
					c.setId(comId);
					c.setAddress(form.getAddress());
					c.setEmailaddress(form.getEmailaddress());
					c.setPhoneno(form.getPhoneno());
					c.setWebsite(form.getWebsite());
					c.setCreatedby(serviceS.getUserName());
					c.setDatecreated(new Date());
					c.setFaxno(form.getFaxno());
					// renz
					if (filepath != null) {
						c.setLogofilename(file.getName());
						c.setLogobasecode(ImageUtils.pdfToBase64(filepath));
					} else {
						c.setLogofilename(c.getLogofilename());
						c.setLogobasecode(c.getLogobasecode());
					}

					if (dbService.save(c))
						return "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public String deleteCompany(CompanyForm form, String tableparameter) {
		// Delete Company
		String flag = "failed";
		try {
			params.put("companycode", form.getCompanycode());
			params.put("companyname", form.getCompanyname());
			if (tableparameter.equalsIgnoreCase("TBCOOPERATIVE")) {
				Tbcooperative company = (Tbcooperative) dbService.executeUniqueHQLQuery(
						"FROM Tbcooperative a WHERE a.id.coopcode=:companycode AND a.id.coopname=:companyname", params);
				if (dbService.delete(company)) {
					flag = "success";
				}
			}
			if (tableparameter.equalsIgnoreCase("TBCOMPANY")) {
				Tbcompany company = (Tbcompany) dbService.executeUniqueHQLQuery(
						"FROM Tbcompany a WHERE a.id.companycode=:companycode AND a.id.companyname=:companyname",
						params);
				if (dbService.delete(company)) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// logger.error("ERROR" + e.toString());
		}
		return flag;
	}

	@Override
	public CompanyForm getCompany(String companycode, String companyname, String tableparameter) {
		try {
			params.put("companycode", companycode);
			params.put("companyname", companyname);
			String q = " ";
			if (tableparameter.equalsIgnoreCase("TBCOOPERATIVE")) {
				q = "FROM Tbcooperative a WHERE a.id.coopcode=:companycode AND a.id.coopname=:companyname";
			}
			if (tableparameter.equalsIgnoreCase("TBCOMPANY")) {
				q = "FROM Tbcompany a WHERE a.id.companycode=:companycode AND a.id.companyname=:companyname";
			}
			CompanyForm company = (CompanyForm) dbService.execSQLQueryTransformer(q, params, CompanyForm.class, 0);
			if (company != null)
				return company;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<CooperativeForm> getAllCooperativeCompanies() {
		List<CooperativeForm> coops = new ArrayList<CooperativeForm>();
		try {
			@SuppressWarnings("unchecked")
			List<Tbcooperative> c = (List<Tbcooperative>) dbService.executeListHQLQuery("FROM Tbcooperative", null);
			if (c != null) {
				for (Tbcooperative coop : c) {
					CooperativeForm f = new CooperativeForm();
					f.setAddress(coop.getAddress());
					f.setCoopcode(coop.getId().getCoopcode());
					f.setCoopname(coop.getId().getCoopname());
					f.setCoopstatus(coop.getCoopstatus());
					f.setCreatedby(coop.getCreatedby());
					f.setDatecreated(coop.getDatecreated());
					f.setDateupdated(coop.getDateupdated());
					f.setEmailaddress(coop.getEmailaddress());
					f.setFaxno(coop.getFaxno());
					// f.setLogo(coop.getLogo()); commented by renz
					f.setPhoneno(coop.getPhoneno());
					f.setUpdatedby(coop.getUpdatedby());
					f.setWebsite(coop.getWebsite());
					f.setCoopsize(coop.getCoopsize());
					f.setMembershipfee(coop.getMembershipfee());
					f.setFinefee(coop.getFinefee());
					f.setSharecapitalparval(coop.getSharecapitalparval());
					coops.add(f);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(coops.size());
		return coops;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyForm> getMemberCompanyPerCooperative(String coopcode) {
		// TODO Auto-generated method stub
		try {
			params.put("coopcode", coopcode);
			List<CompanyForm> com = (List<CompanyForm>) dbService.execSQLQueryTransformer(
					"SELECT " + "companycode, companyname, " + "coopcode, address, phoneno, "
							+ "faxno, emailaddress, website, " + "companystatus, datecreated, "
							+ "createdby, dateupdated, " + "updatedby, businesstype, " + "faxareacode, faxphoneno, "
							+ "areacode, streetnoname, " + "subdivision, barangay, " + "stateprovince, city, region, "
							+ "country, postalcode," + "psiccode, psiclevel1, " + "psiclevel2, psiclevel3, "
							+ "psoccode, psoclevel1, " + "psoclevel2, psoclevel3," + "psoclevel4, "
							+ "maxloanableamount as maxloanamount " + "FROM Tbmembercompany",
					params, CompanyForm.class, 1);
			// removed " WHERE coopcode=:coopcode" by renz
			if (com != null) {
				return com;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String saveOrUpdateMemberCompanyPerCooperative(Tbmembercompany company) {

		try {
			company.setDatecreated(new Date());
			company.setCreatedby(serviceS.getUserName());
			company.setUpdatedby(serviceS.getUserName());
			company.setDateupdated(new Date());

			if (dbService.saveOrUpdate(company)) {
				params.put("boscode", company.getId().getCompanycode());
				if ((Integer) dbService.executeUniqueSQLQuery(
						"SELECT ISNULL((SELECT COUNT(*) FROM Tbgaaperbos WHERE boscode=:boscode),0)", params) == 0) {
					List<CodetableForm> servicestatuslist = new ArrayList<CodetableForm>();
					CodetableService csrvc = new CodetableServiceImpl();
					servicestatuslist = csrvc.getListofCodesPerCodename("SERVICESTATUS");
					for (CodetableForm servicestatus : servicestatuslist) {
						Tbgaaperbos gaaperbos = new Tbgaaperbos();
						gaaperbos.setAmount(BigDecimal.ZERO);
						gaaperbos.setBoscode(company.getId().getCompanycode());
						gaaperbos.setCreatedby(serviceS.getUserName());
						gaaperbos.setDatecreated(new Date());
						gaaperbos.setServicestatus(servicestatus.getCodevalue());
						dbService.save(gaaperbos);
					}
				}
				return "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public String deleteMemberCompany(String coopcode, String companycode) {
		// TODO Auto-generated method stub
		try {
			params.put("coopcode", coopcode);
			params.put("companycode", companycode);
			Tbmembercompany c = (Tbmembercompany) dbService.executeUniqueHQLQuery(
					"FROM Tbmembercompany WHERE companycode=:companycode AND coopcode=:coopcode", params);
			if (c != null) {
				if (dbService.delete(c))
					return "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public List<Tbproductpercoop> getListProdperCoop(String prodcode, String coopcode) {
		// TODO Auto-generated method stub
		List<Tbproductpercoop> list = new ArrayList<Tbproductpercoop>();
		try {
			params.put("prodcode", prodcode);
			params.put("coopcode", coopcode);
			list = (List<Tbproductpercoop>) dbService
					.executeListHQLQuery("FROM Tbproductpercoop WHERE coopcode=:coopcode", params);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String updateProdlist(List<Tbproductpercoop> prod) {
		// TODO Auto-generated method stub
		String flag = "Failed";

		try {

			for (Tbproductpercoop prodcoop : prod) {
				Tbproductpercoop coop = new Tbproductpercoop();
				params.put("coopcode", prodcoop.getId().getCoopcode());
				params.put("prodcode", prodcoop.getId().getProductcode());
				System.out.println("prodcoop: " + prodcoop.getId().getProductcode() + " coopcode: "
						+ prodcoop.getId().getCoopcode());

				coop = (Tbproductpercoop) dbService.executeUniqueHQLQuery(
						"FROM Tbproductpercoop WHERE coopcode=:coopcode AND productcode=:prodcode", params);

				coop.setIsactive(prodcoop.getIsactive());
				if (dbService.saveOrUpdate(coop)) {
					flag = "success";
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public CompanyForm getMemberCompanyPerCode(String companycode) {
		// TODO Auto-generated method stub
		try {
			params.put("companycode", companycode);
			Tbmembercompany m = (Tbmembercompany) dbService
					.executeUniqueHQLQuery("FROM Tbmembercompany WHERE companycode=:companycode", params);
			if (m != null) {
				CompanyForm c = new CompanyForm();
				ObjectMapper mapper = new ObjectMapper();
				c = mapper.readValue(mapper.writeValueAsString(m), CompanyForm.class);
				return c;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public List<CompanyForm> getMemberCompanyPerCooperativePerMemberType(String coopcode, String membertype) {
		// TODO Auto-generated method stub
		String qry = "SELECT " + "companycode, companyname, " + "coopcode, address, phoneno, "
				+ "faxno, emailaddress, website, " + "companystatus, datecreated, " + "createdby, dateupdated, "
				+ "updatedby, businesstype, " + "faxareacode, faxphoneno, " + "areacode, streetnoname, "
				+ "subdivision, barangay, " + "stateprovince, city, region, " + "country, postalcode,"
				+ "psiccode, psiclevel1, " + "psiclevel2, psiclevel3, " + "psoccode, psoclevel1, "
				+ "psoclevel2, psoclevel3," + "psoclevel4, " + "maxloanableamount as maxloanamount "
				+ "FROM Tbmembercompany WHERE coopcode=:coopcode";
		try {
			qry = qry + (membertype.equals("3") ? " AND companycode NOT IN ('PNP','BJMP','BFP')"
					: " AND companycode IN ('PNP','BJMP','BFP')");
			params.put("coopcode", coopcode);
			List<CompanyForm> com = (List<CompanyForm>) dbService.execSQLQueryTransformer(qry, params,
					CompanyForm.class, 1);
			if (com != null) {
				return com;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String saveMemberType(String boscode, List<CodetableForm> membertype) {
		String flag = "failed";
		try {
			if (boscode != null) {
				params.put("boscode", boscode);
				String selectedCol = "''";
				Tbmembershiptypeperbos mem = new Tbmembershiptypeperbos();
				TbmembershiptypeperbosId id = new TbmembershiptypeperbosId();
				for (CodetableForm c : membertype) {
					selectedCol += ",'" + c.getCodevalue() + "'";
					// Tbmembercompany m = (Tbmembercompany) dbService
					// .executeUniqueHQLQuery("FROM Tbmembershiptypeperbos WHERE b=:companycode",
					// params);
					System.out.println(c.getCodevalue());

					id.setBoscode(boscode);
					id.setMembertypecode(c.getCodevalue());
					mem.setId(id);
					mem.setAddedby(UserUtil.securityService.getUserName());
					mem.setDateadded(new Date());

					dbService.saveOrUpdate(mem);

					// Delete if not in selected item
					dbService.executeUpdate(
							"DELETE FROM Tbmembershiptypeperbos WHERE boscode=:boscode AND membertypecode NOT IN("
									+ selectedCol + ")",
							params);
				}
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<CodetableForm> getSavedMemberType(String boscode) {
		List<CodetableForm> list = new ArrayList<CodetableForm>();
		try {
			if (boscode != null && !boscode.equalsIgnoreCase("NULL") && !boscode.equals("")) {
				params.put("boscode", boscode);
				list = (List<CodetableForm>) dbService
						.execSQLQueryTransformer("SELECT a.codename, a.codevalue, a.desc1, a.desc2 FROM TBCODETABLE a"
								+ " INNER JOIN Tbmembershiptypeperbos b ON a.codename='MEMBERSHIPCLASS' AND a.codevalue=b.membertypecode "
								+ "WHERE b.boscode=:boscode ORDER BY b.membertypecode", params, CodetableForm.class, 1);
			} else {
				list = (List<CodetableForm>) dbService.execSQLQueryTransformer(
						"SELECT a.codename, a.codevalue, a.desc1, a.desc2 FROM TBCODETABLE a WHERE a.codename='MEMBERSHIPCLASS'",
						params, CodetableForm.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// added by renz
	@Override
	public FileUploadResponse uploadFile(MultipartFile file) {
		FileUploadResponse ret = new FileUploadResponse();
		// Integer maxFileSize = documentProperties().getMaxfilesize();
		try {

			// if(file.getSize() >= maxFileSize) {
			// ret.setError("Invalid File Size");
			// }
			// else {
			// System.out.println(file.getSize());
			String filename = file.getOriginalFilename(); /* .replaceAll("[^a-zA-Z0-9 ._-]",""); */
			boolean hasExtension = filename.indexOf(".") != -1;
			// String name = (hasExtension) ? filename.substring(0,
			// filename.lastIndexOf(".")) : filename;
			String ext = (hasExtension) ? filename.substring(filename.lastIndexOf(".")) : "";
			if (ext.equalsIgnoreCase(".pdf") || ext.equalsIgnoreCase(".jpg") || ext.equalsIgnoreCase(".png")
					|| ext.equalsIgnoreCase(".jpeg")) {
				File dir = new File(
						RuntimeAccess.getInstance().getSession().getServletContext().getRealPath("resources/images/"));
				if (!dir.exists())
					dir.mkdirs();

				/*
				 * Create a file object that does not point to an existing file. Loop through
				 * names until we find a filename not already in use
				 */
				File outputFile = new File(dir, filename);

				// deleteFileOlderThanXdays(1, dir.toString());

				FileOutputStream fos = new FileOutputStream(outputFile);
				IOUtils.copy(file.getInputStream(), fos);
				file.getInputStream().close();
				fos.close();

				ret.setPath(outputFile.getPath());
				ret.setError("");
			}

			else {
				ret.setError("Invalid File Format");
			}
			// }
		} catch (Exception e) {
			System.out.println("ERROR11:" + e.getMessage() + " | " + e.toString());

			ret.setError(e.getMessage());
		}
		return ret;
	}

	@Override
	public String viewImage(String coopcode) {
		String filepath = null;
		String ext = null;
		DBService dbService = new DBServiceImpl();
		Tbcooperative coop = new Tbcooperative();
		File dir = new File(
				RuntimeAccess.getInstance().getSession().getServletContext().getRealPath("resources/images"));
		Map<String, Object> params = HQLUtil.getMap();
		params.put("coopcode", coopcode);
		coop = (Tbcooperative) dbService.executeUniqueHQLQuery("FROM Tbcooperative WHERE coopcode=:coopcode", params);
		if (coop != null) {
			try {

				if (coop.getLogobasecode() != null) {
					ImageUtils.base64ToPDF(coop.getLogobasecode(), dir.toString() + "\\", coop.getLogofilename());
					ext = coop.getLogofilename().substring(coop.getLogofilename().lastIndexOf("."));
					if (ext.equals(".png")) {
						filepath = "resources/images/" + coop.getLogofilename();
					} else {
						filepath = ImageUtils.ImageToPDF(dir.toString() + "\\", coop.getLogofilename());
					}
				} else {
					return "failed";
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
	public String checkPicOrPDF(String coopcode) {
		String filepath = null;
		String ext = null;
		Tbcooperative coop = new Tbcooperative();
		DBService dbService = new DBServiceImpl();
		File dir = new File(
				RuntimeAccess.getInstance().getSession().getServletContext().getRealPath("resources/images"));
		Map<String, Object> params = HQLUtil.getMap();
		params.put("coopcode", coopcode);

		try {
			coop = (Tbcooperative) dbService
					.executeUniqueHQLQueryMaxResultOne("FROM Tbcooperative WHERE coopcode=:coopcode", params);
			if (coop != null) {
				try {
					if (coop.getLogobasecode() != null) {
						ImageUtils.base64ToPDF(coop.getLogobasecode(), dir.toString() + "\\", coop.getLogofilename());
						filepath = "resources\\images\\" + coop.getLogofilename();

						ext = filepath.substring(filepath.lastIndexOf("."));
					} else {
						return "failed";
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
	public List<CompanyBranchForm> getBranchDropdown() {
		List<CompanyBranchForm> codelist = new ArrayList<CompanyBranchForm>();
		DBService dbService = new DBServiceImpl();
		try {
			codelist = (List<CompanyBranchForm>) dbService.execSQLQueryTransformer(
					"SELECT branchname,branchcode from TBBRANCH", null, CompanyBranchForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codelist;
	}
	
	@Override
	public FileUploadResponse uploadFile2(MultipartFile file) {
		FileUploadResponse ret = new FileUploadResponse();
		// Integer maxFileSize = documentProperties().getMaxfilesize();
		try {

			// if(file.getSize() >= maxFileSize) {
			// ret.setError("Invalid File Size");
			// }
			// else {
			// System.out.println(file.getSize());
			String filename = file.getOriginalFilename(); /* .replaceAll("[^a-zA-Z0-9 ._-]",""); */
			boolean hasExtension = filename.indexOf(".") != -1;
			// String name = (hasExtension) ? filename.substring(0,
			// filename.lastIndexOf(".")) : filename;
			String ext = (hasExtension) ? filename.substring(filename.lastIndexOf(".")) : "";
			if (ext.equalsIgnoreCase(".pdf") || ext.equalsIgnoreCase(".jpg") || ext.equalsIgnoreCase(".png")
					|| ext.equalsIgnoreCase(".jpeg")) {
				File dir = new File(
						RuntimeAccess.getInstance().getSession().getServletContext().getRealPath("resources/images/"));
				if (!dir.exists())
					dir.mkdirs();

				/*
				 * Create a file object that does not point to an existing file. Loop through
				 * names until we find a filename not already in use
				 */
				File outputFile = new File(dir, filename);

				// deleteFileOlderThanXdays(1, dir.toString());

				FileOutputStream fos = new FileOutputStream(outputFile);
				IOUtils.copy(file.getInputStream(), fos);
				file.getInputStream().close();
				fos.close();

				ret.setPath(outputFile.getPath());
				ret.setError("");
			}

			else {
				ret.setError("Invalid File Format");
			}
			// }
		} catch (Exception e) {
			System.out.println("ERROR11:" + e.getMessage() + " | " + e.toString());

			ret.setError(e.getMessage());
		}
		return ret;
	}
}
