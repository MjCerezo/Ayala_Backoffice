package com.etel.loanproduct;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import com.coopdb.data.Tbbillingcutoffperprod;
import com.coopdb.data.Tbcodetable;
import com.coopdb.data.Tbcollateralperprod;
import com.coopdb.data.TbcollateralperprodId;
import com.coopdb.data.Tbcompany;
import com.coopdb.data.Tbdeductions;
import com.coopdb.data.Tbdocsperproduct;
import com.coopdb.data.Tbfeesandcharges;
import com.coopdb.data.Tbintratebyterm;
import com.coopdb.data.Tbloanfeesperapp;
import com.coopdb.data.TbloanfeesperappId;
import com.coopdb.data.Tbloanprodmembertype;
import com.coopdb.data.Tbloanproduct;
import com.coopdb.data.Tbloanproductfees;
import com.coopdb.data.TbloanproductfeesId;
import com.coopdb.data.Tbloanrepaymentscheme;
import com.coopdb.data.Tbloanschemeperprod;
import com.coopdb.data.TbloanschemeperprodId;
import com.coopdb.data.Tblstapp;
import com.coopdb.data.Tbmembershiptypeperbos;
import com.coopdb.data.Tbmlacperproduct;
import com.coopdb.data.TbmlacperproductId;
import com.coopdb.data.Tbproductpercompany;
import com.coopdb.data.TbproductpercompanyId;
import com.coopdb.data.Tbworkflowprocess;
import com.etel.codetable.forms.CodetableForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.loanproduct.forms.BOSForm;
import com.etel.loanproduct.forms.CyclePerLoanSchemeForm;
import com.etel.loanproduct.forms.LoanFeeForm;
import com.etel.loanproduct.forms.LoanFeeInputForm;
import com.etel.loanproduct.forms.LoanIntRateTable;
import com.etel.loanproduct.forms.LoanProductForm;
import com.etel.loanproduct.forms.LoanSchemePerProdForm;
import com.etel.loanproduct.forms.RepaymentSchemeDisplayForm;
import com.etel.loanproduct.forms.Tbproductpercompanyform;
import com.etel.util.HQLUtil;
import com.etel.utils.UserUtil;

public class LoanProductServiceImpl implements LoanProductService {

	// private String username = UserUtil.securityService.getUserName();

	private DBService dbService = new DBServiceImpl();
	Map<String, Object> params = HQLUtil.getMap();
	private Map<String, Object> param = HQLUtil.getMap();

	/**
	 * Get List of LoanProduct
	 * 
	 * @author Kevin (12.07.2017)
	 * @return List<{@link Tbloanproduct}>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbloanproduct> getListOfLoanProduct(String product, String prodtype) {
		List<Tbloanproduct> form = new ArrayList<Tbloanproduct>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (prodtype != null) {
				params.put("prodtype", prodtype.trim());
				if (product == null) {
					form = (List<Tbloanproduct>) dbService
							.executeListHQLQuery("FROM Tbloanproduct WHERE producttype1=:prodtype", params);
				} else {
					params.put("product", "%" + product + "%");
					form = (List<Tbloanproduct>) dbService.executeListHQLQuery(
							"FROM Tbloanproduct WHERE productname like :product AND producttype1=:prodtype", params);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	/**
	 * Get Loan Product by productcode
	 * 
	 * @author Kevin (12.07.2017)
	 * @param productcode
	 * @return {@link Tbloanproduct}
	 */
	public Tbloanproduct getLoanProductByProductcode(String productcode) {
		Tbloanproduct prod = new Tbloanproduct();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (productcode != null) {
				params.put("productcode", productcode);
				prod = (Tbloanproduct) dbService
						.executeUniqueHQLQuery("FROM Tbloanproduct WHERE productcode=:productcode", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prod;
	}

	/**
	 * Check product code
	 * 
	 * @author Mheanne (04.04.2018)
	 * @param productcode
	 * @return {String}
	 */

	@Override
	public String checkProductCode(String productcode) {
		// TODO Auto-generated method stub
		String result = "Available";
		Tbloanproduct prod = new Tbloanproduct();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (productcode != null) {
				params.put("productcode", productcode);
				prod = (Tbloanproduct) dbService
						.executeUniqueHQLQuery("FROM Tbloanproduct WHERE productcode=:productcode", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (prod != null) {
				result = "Not Available";
			}
		}
		return result;
	}

	/**
	 * Save product code
	 * 
	 * @author Mheanne (04.04.2018)
	 * @param productcode
	 * @return {String}
	 */

	@SuppressWarnings("unchecked")
	@Override
	public String saveLoanProduct(Tbloanproduct product) {
		// TODO Auto-generated method stub
		String result = "Failed";
		Map<String, Object> params = HQLUtil.getMap();
		try {
			product.setDatecreated(new Date());
			product.setCreatedby(UserUtil.securityService.getUserName());
			if (dbService.saveOrUpdate(product)) {
				result = "Success";

				// Added (05.22.2019 - Kev)
				List<Tbcompany> company = (List<Tbcompany>) dbService.executeListHQLQuery("FROM Tbcompany", null);
				if (company != null) {
					for (Tbcompany c : company) {
						params.put("companycode", c.getId().getCompanycode());
						params.put("prodcode", product.getProductcode());
						Tbproductpercompany p = (Tbproductpercompany) dbService.executeUniqueHQLQuery(
								"FROM Tbproductpercompany WHERE id.companycode=:companycode AND id.productcode=:prodcode",
								params);
						if (p == null) {
							TbproductpercompanyId id = new TbproductpercompanyId();
							id.setCompanycode(c.getId().getCompanycode());
							id.setProductcode(product.getProductcode());

							Tbproductpercompany prodc = new Tbproductpercompany();
							prodc.setId(id);
							prodc.setProductname(product.getProductname());
							prodc.setAssignedby(UserUtil.securityService.getUserName());
							prodc.setDateassigned(new Date());
							prodc.setIsactive(false);
							prodc.setProducttype(product.getProducttype1());
							dbService.save(prodc);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbintratebyterm> getintratebyproduct(String productcode) {
		// TODO Auto-generated method stub
		List<Tbintratebyterm> rates = new ArrayList<Tbintratebyterm>();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("prodcode", productcode);
		try {
			rates = (List<Tbintratebyterm>) dbService
					.executeListHQLQuery("FROM Tbintratebyterm WHERE productcode=:prodcode", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rates;
	}

	public Tbfeesandcharges getFeesandChargesPerProduct(String productcode) {
		Tbfeesandcharges fees = new Tbfeesandcharges();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (productcode != null) {
				params.put("prodcode", productcode);
				fees = (Tbfeesandcharges) dbService
						.executeUniqueHQLQuery("FROM Tbfeesandcharges WHERE productcode=:prodcode", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fees;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbdocsperproduct> getDocumentsPerProduct(String productcode) {
		// TODO Auto-generated method stub
		List<Tbdocsperproduct> docs = new ArrayList<Tbdocsperproduct>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (productcode != null) {
				params.put("prodcode", productcode);
				docs = (List<Tbdocsperproduct>) dbService
						.executeListHQLQuery("FROM Tbdocsperproduct WHERE productcode=:prodcode", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return docs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String saveLoanProductFull(Tbloanproduct product, Tbfeesandcharges fees) {
		// TODO Auto-generated method stub
		String result = "Failed";
		Map<String, Object> params = HQLUtil.getMap();
		try {
			product.setDateupdated(new Date());
			product.setUpdatedby(UserUtil.securityService.getUserName());

//			if(dbService.saveOrUpdate(product) && dbService.saveOrUpdate(fees)){
//				result = "Success";
//			}
			// Modified 09.23.2018

			if (dbService.saveOrUpdate(product)) {
				result = "Success";

				// Added (05.22.2019 - Kev)
				List<Tbcompany> company = (List<Tbcompany>) dbService.executeListHQLQuery("FROM Tbcompany", null);
				if (company != null) {
					for (Tbcompany c : company) {
						params.put("companycode", c.getId().getCompanycode());
						params.put("prodcode", product.getProductcode());
						Tbproductpercompany p = (Tbproductpercompany) dbService.executeUniqueHQLQuery(
								"FROM Tbproductpercompany WHERE id.companycode=:companycode AND id.productcode=:prodcode",
								params);
						if (p == null) {
							TbproductpercompanyId id = new TbproductpercompanyId();
							id.setCompanycode(c.getId().getCompanycode());
							id.setProductcode(product.getProductcode());

							Tbproductpercompany prodc = new Tbproductpercompany();
							prodc.setId(id);
							prodc.setProductname(product.getProductname());
							prodc.setAssignedby(UserUtil.securityService.getUserName());
							prodc.setDateassigned(new Date());
							prodc.setIsactive(false);
							prodc.setProducttype(product.getProducttype1());
							dbService.save(prodc);
						} else {
							p.setProductname(product.getProductname());
							p.setProducttype(product.getProducttype1());
							dbService.saveOrUpdate(p);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * --Save Or Update Required Document Per Product--
	 * 
	 * @author Wel (Sept.06.2018)
	 * @return String = success, otherwise failed
	 */
	@Override
	public String saveDocumentPerProduct(Tbdocsperproduct doc) {
		String result = "failed";
		try {
			if (doc.getProductcode() != null) {
				Tbdocsperproduct row = null;
				if (doc.getId() != null) {
					params.put("id", doc.getId());
					params.put("prod", doc.getProductcode());
					row = (Tbdocsperproduct) dbService.executeUniqueHQLQueryMaxResultOne(
							"FROM Tbdocsperproduct WHERE id=:id AND productcode=:prod", params);
					if (row != null) {
						row.setProductcode(doc.getProductcode());
						row.setDocumentcode(doc.getDocumentcode());
						row.setDocumentname(doc.getDocumentname());
						row.setRemarks(doc.getRemarks());
						row.setApplicationstatus(doc.getApplicationstatus());
						row.setUpdatedby(UserUtil.securityService.getUserName());
						row.setDateupdated(new Date());
						row.setIsindivrequired(doc.getIsindivrequired());
						row.setIscorprequired(doc.getIscorprequired());
						row.setIspartnerrequired(doc.getIspartnerrequired());
						row.setIssoleproprequired(doc.getIssoleproprequired());
						row.setEnablenotarialfee(doc.getEnablenotarialfee());
						if (dbService.saveOrUpdate(row)) {
							result = "success";
						}
					} else {
						Tbdocsperproduct newDoc = new Tbdocsperproduct();
						newDoc.setProductcode(doc.getProductcode());
						newDoc.setDocumentcode(doc.getDocumentcode());
						newDoc.setDocumentname(doc.getDocumentname());
						newDoc.setRemarks(doc.getRemarks());
						newDoc.setApplicationstatus(doc.getApplicationstatus());
						newDoc.setCreatedby(UserUtil.securityService.getUserName());
						newDoc.setDatecreated(new Date());
						newDoc.setDoccategory(doc.getDoccategory()); // for clarification
						newDoc.setIsindivrequired(doc.getIsindivrequired());
						newDoc.setIscorprequired(doc.getIscorprequired());
						newDoc.setIspartnerrequired(doc.getIspartnerrequired());
						newDoc.setIssoleproprequired(doc.getIssoleproprequired());
						newDoc.setEnablenotarialfee(doc.getEnablenotarialfee());
						newDoc.setEnablenotarialfee(doc.getEnablenotarialfee());
						if (dbService.save(newDoc)) {
							result = "success";
						}
					}
				} else {
					if (row == null) {
						Tbdocsperproduct newDoc = new Tbdocsperproduct();
						newDoc.setProductcode(doc.getProductcode());
						newDoc.setDocumentcode(doc.getDocumentcode());
						newDoc.setDocumentname(doc.getDocumentname());
						newDoc.setRemarks(doc.getRemarks());
						newDoc.setApplicationstatus(doc.getApplicationstatus());
						newDoc.setCreatedby(UserUtil.securityService.getUserName());
						newDoc.setDatecreated(new Date());
						newDoc.setDoccategory(doc.getDoccategory()); // for clarification
						newDoc.setIsindivrequired(doc.getIsindivrequired());
						newDoc.setIscorprequired(doc.getIscorprequired());
						newDoc.setIspartnerrequired(doc.getIspartnerrequired());
						newDoc.setIssoleproprequired(doc.getIssoleproprequired());
						newDoc.setEnablenotarialfee(doc.getEnablenotarialfee());
						newDoc.setEnablenotarialfee(doc.getEnablenotarialfee());
						if (dbService.save(newDoc)) {
							result = "success";
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Tblstapp getApplicationDetails(String appno) {
		// TODO Auto-generated method stub
		Tblstapp app = new Tblstapp();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("appno", appno);
		try {
			app = (Tblstapp) dbService.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return app;
	}

	@Override
	public String deleteDocumentPerProduct(Tbdocsperproduct doc) {
		// TODO Auto-generated method stub
		String result = "Failed";
		try {
			if (dbService.delete(doc)) {
				result = "Success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String saveIntRatePerProduct(Tbintratebyterm intrecord) {
		// TODO Auto-generated method stub
		String result = "Failed";
		try {
			if (dbService.saveOrUpdate(intrecord)) {
				result = "Success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<CodetableForm> getListofCreditFacilities() {
//		// TODO Auto-generated method stub
//		List<CodetableForm> codelist = new ArrayList<CodetableForm>();
//		DBService dbService = new DBServiceImpl();
//		Map<String, Object> params = HQLUtil.getMap();
//		try {
//			codelist = (List<CodetableForm>) dbService.execSQLQueryTransformer(
//					"SELECT codename,codevalue,desc1,desc2, remarks,createdby,createddate,updatedby,lastupdated FROM Tbcodetable WHERE codename IN ('FACILITYTYPE','FACILITYNAME') ORDER BY desc1 ASC",
//					params, CodetableForm.class, 1);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return codelist;
//	}

	/**
	 * --Save Or Update Credit Facility--
	 * 
	 * @author Wel (Sept.06.2018)
	 * @return String = success, otherwise failed
	 */
//	@Override
//	public String saveCreditFacility(String facilitycode, String facilityname, String prodcode, String productname,
//			String repaymenttype, String repaymentcode, String selectedfacilitycode, String selectedrepaymentcode) {
//		String flag = "failed";
//		try {
//			if (prodcode != null && facilitycode != null && facilityname != null) { // for parameters
//				params.put("prod", prodcode);
//				params.put("selfac", selectedfacilitycode);
//				Tbloanprodpercf row = (Tbloanprodpercf) dbService.executeUniqueHQLQueryMaxResultOne(
//						"FROM Tbloanprodpercf a WHERE a.id.prodcode=:prod AND a.id.facilitycode=:selfac",
//						params);
//				if (row != null) {
////					Integer res = (Integer) dbService.executeUpdate(
////							"UPDATE Tbloanprodpercf SET facilitycode = '" + facilitycode + "'," + "productname = '" + productname + "'," + "facilityname = '"
////									+ facilityname + "'," + "prodcode = '"+ prodcode + "'"
////									+ " WHERE prodcode=:prod AND facilitycode=:selfac",
////							params);
////					if (res != null && res > 0) {
////						flag = "success";
////					}
////					row.setFacilityname(facilityname);
////					row.setProductname(productname);
////					row.getId().setProdcode(prodcode);
////					row.getId().setFacilitycode(facilitycode);
////					if (dbService.saveOrUpdate(row)) {
////						flag = "success";
////					}
//					flag = "existing";
//				} else {
//					TbloanprodpercfId cfid = new TbloanprodpercfId();
//					cfid.setFacilitycode(facilitycode);
//					cfid.setProdcode(prodcode);
//
//					Tbloanprodpercf cf = new Tbloanprodpercf();
//					cf.setId(cfid);
//					cf.setFacilityname(facilityname);
//					cf.setProductname(productname);
//					cf.setRepaymenttype(repaymenttype);
//					cf.setRepaymentcode(repaymentcode);
//
//					if (dbService.save(cf)) {
//						flag = "success";
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return flag;
//	}

	/**
	 * --Delete Credit Facility--
	 * 
	 * @author Wel (Sept.06.2018)
	 * @return String = success, otherwise failed
	 */
//	@Override
//	public String deleteCreditFacility(String prodcode, String facilitycode, String repaymentcode) {
//		String flag = "failed";
//		try {
//			if (prodcode != null && facilitycode != null) {
//				params.put("prodcode", prodcode);
//				params.put("fac", facilitycode);
////				params.put("rep", repaymentcode);
//				Integer res = dbService.executeUpdate(
//						"DELETE FROM Tbloanprodpercf WHERE prodcode=:prodcode AND facilitycode=:fac",
//						params);
//				if (res != null && res > 0) {
//					flag = "success";
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return flag;
//	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Tbloanprodpercf> getListofCFPerProduct(String prodcode) {
//		// TODO Auto-generated method stub
//		List<Tbloanprodpercf> cflist = new ArrayList<Tbloanprodpercf>();
//		DBService dbService = new DBServiceImplLOS();
//		Map<String, Object> params = HQLUtil.getMap();
//		params.put("prodcode", prodcode);
//		try {
//			cflist = (List<Tbloanprodpercf>) dbService
//					.executeListHQLQuery("From Tbloanprodpercf Where prodcode=:prodcode", params);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return cflist;
//
//	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbloanproductfees> getLoanFees(String prodcode) {
		// TODO Auto-generated method stub
		List<Tbloanproductfees> loanfees = new ArrayList<Tbloanproductfees>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("prodcode", prodcode);
		try {
			loanfees = (List<Tbloanproductfees>) dbService
					.executeListHQLQuery("From Tbloanproductfees Where prodcode=:prodcode", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loanfees;

	}

	/**
	 * --Save Or Update Loan Fee--
	 * 
	 * @author Wel (Sept.05.2018)
	 * @return String = success, otherwise failed
	 */
	@Override
	public String saveLoanFee(LoanFeeForm feeform, String selectedloanfeecode) {
		String flag = "failed";
		try {
			if (feeform.getLoanfeecode() != null && feeform.getProdcode() != null && selectedloanfeecode != null) {
				params.put("prod", feeform.getProdcode());
				params.put("selloanfee", selectedloanfeecode);
				Tbloanproductfees row = (Tbloanproductfees) dbService.executeUniqueHQLQueryMaxResultOne(
						"FROM Tbloanproductfees a WHERE a.id.prodcode=:prod AND a.id.loanfeecode=:selloanfee", params);
				if (row != null) {
//					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//					Integer res = (Integer) dbService.executeUpdate
//							("UPDATE Tbloanproductfees SET prodcode = '"+feeform.getProdcode()+"',"
//									+ "loanfeecode = '"+feeform.getLoanfeecode()+"',"
//									+ "dateupdated = GETDATE(),"
//									+ "updatedby = '"+username+"',"
//									+ "dstamt1 = '"+feeform.getDstamt1()+"',"
//									+ "dstamt2 = '"+feeform.getDstamt2()+"',"
//									+ "dstformuladiv = '"+feeform.getDstdiv()+"',"
//									+ "dstformulamul = '"+feeform.getDstmul()+"',"
//									+ "effectivedate = '"+formatter.format(feeform.getEffectivity())+"',"
//									+ "loancollection = '"+feeform.getCollectiontype()+"',"
//									+ "loanfeeamt = '"+feeform.getLoanfeeamt()+"',"
//									+ "loanfeename = '"+feeform.getLoanfeename()+"',"
//									+ "loanfeereate = '"+feeform.getLoanfeerate()+"',"
//									+ "loanfeetype = '"+feeform.getLoanfeerule()+"',"
//									+ "notdocamt = '"+feeform.getDocumentrate()+"',"
//									+ "rateappliedto = '"+feeform.getRateappliedto()+"'"
//									+ " WHERE prodcode=:prod AND loanfeecode=:selloanfee", params);
//					if (res != null && res > 0) {
//						flag = "success";
//					}

					// Modified 10.19.2018 - Kev
					row.setDateupdated(new Date());
					row.setUpdatedby(UserUtil.securityService.getUserName());
					row.setDstamt1(feeform.getDstamt1());
					row.setDstamt2(feeform.getDstamt2());
					row.setDstformuladiv(feeform.getDstdiv());
					row.setDstformulamul(feeform.getDstmul());
					row.setEffectivedate(feeform.getEffectivity());
					row.setLoancollection(feeform.getCollectiontype());
					row.setLoanfeeamt(feeform.getLoanfeeamt());
					row.setLoanfeename(feeform.getLoanfeename());
					row.setLoanfeerate(feeform.getLoanfeerate());
					row.setLoanfeetype(feeform.getLoanfeerule());
					row.setNotdocamt(feeform.getDocumentrate());
					row.setRateappliedto(feeform.getRateappliedto());

					// Added 10.19.2018 - Kev
					row.setInhouserule(feeform.getInhouserule());
					row.setInhouserateapplied(feeform.getInhouserateapplied());
					row.setInhouserate(feeform.getInhouserate());
					row.setInhouseamt(feeform.getInhouseamt());
					row.setAgentrule(feeform.getAgentrule());
					row.setAgentrateapplied(feeform.getAgentrateapplied());
					row.setAgentrate(feeform.getAgentrate());
					row.setAgentamt(feeform.getAgentamt());

					// Added 11.23.2018 - MMM
					row.setDstformulamul1(feeform.getDstmul1());
					row.setDstformulamul1opt(feeform.getDstmul1opt());
					row.setGlcode(feeform.getGlcode());
					row.setGldescription(feeform.getGldescription());
					if (dbService.saveOrUpdate(row)) {
						flag = "success";
					}
				} else {
					TbloanproductfeesId feeid = new TbloanproductfeesId();
					feeid.setLoanfeecode(feeform.getLoanfeecode());
					feeid.setProdcode(feeform.getProdcode());

					Tbloanproductfees fee = new Tbloanproductfees();
					fee.setId(feeid);
					fee.setCreatedby(UserUtil.securityService.getUserName());
					fee.setDatecreated(feeform.getDatecreated());
					fee.setDateupdated(new Date());
					fee.setDstamt1(feeform.getDstamt1());
					fee.setDstamt2(feeform.getDstamt2());
					fee.setDstformuladiv(feeform.getDstdiv());
					fee.setDstformulamul(feeform.getDstmul());
					fee.setEffectivedate(feeform.getEffectivity());
					fee.setLoancollection(feeform.getCollectiontype());
					fee.setLoanfeeamt(feeform.getLoanfeeamt());
					fee.setLoanfeename(feeform.getLoanfeename());
					fee.setLoanfeerate(feeform.getLoanfeerate());
					fee.setLoanfeetype(feeform.getLoanfeerule());
					fee.setNotdocamt(feeform.getDocumentrate());
					fee.setRateappliedto(feeform.getRateappliedto());

					// Added 10/19/2018 - Kev
					fee.setInhouserule(feeform.getInhouserule());
					fee.setInhouserateapplied(feeform.getInhouserateapplied());
					fee.setInhouserate(feeform.getInhouserate());
					fee.setInhouseamt(feeform.getInhouseamt());
					fee.setAgentrule(feeform.getAgentrule());
					fee.setAgentrateapplied(feeform.getAgentrateapplied());
					fee.setAgentrate(feeform.getAgentrate());
					fee.setAgentamt(feeform.getAgentamt());
					fee.setDstformulamul1(feeform.getDstmul1());
					fee.setDstformulamul1opt(feeform.getDstmul1opt());
					fee.setGlcode(feeform.getGlcode());
					fee.setGldescription(feeform.getGldescription());
					if (dbService.save(fee)) {
						flag = "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * --Delete Loan Fee--
	 * 
	 * @author Wel (Sept.05.2018)
	 * @return String = success, otherwise failed
	 */
	@Override
	public String deleteLoanFee(String prodcode, String loanfeecode) {
		String flag = "failed";
		try {
			if (prodcode != null && loanfeecode != null) {
				params.put("prod", prodcode);
				params.put("loanfee", loanfeecode);
				Integer res = dbService.executeUpdate(
						"DELETE FROM Tbloanproductfees WHERE prodcode=:prod AND loanfeecode=:loanfee", params);
				if (res != null && res > 0) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * --Save Or Update Repayment--
	 * 
	 * @author Wel (Sept.05.2018)
	 * @return String = success, otherwise failed
	 */
	@Override
	public String saveRepayment(String prodcode, String schemecode, String repayment, String selectedschemecode) {
		String flag = "failed";
		try {
			if (prodcode != null && schemecode != null && selectedschemecode != null) {
				params.put("prod", prodcode);
				params.put("selscheme", selectedschemecode);
				Tbloanschemeperprod row = (Tbloanschemeperprod) dbService.executeUniqueHQLQueryMaxResultOne(
						"FROM Tbloanschemeperprod a WHERE a.id.prodcode=:prod AND a.id.schemecode=:selscheme", params);
				if (row != null) {
					Integer res = (Integer) dbService.executeUpdate(
							"UPDATE Tbloanschemeperprod SET prodcode = '" + prodcode + "', schemecode = '" + schemecode
									+ "' WHERE prodcode=:prod AND schemecode=:selscheme",
							params);
					if (res != null && res > 0) {
						flag = "success";
					}
				} else {
					TbloanschemeperprodId schemeid = new TbloanschemeperprodId();
					schemeid.setProdcode(prodcode);
					schemeid.setSchemecode(schemecode);
					Tbloanschemeperprod scheme = new Tbloanschemeperprod();
					scheme.setId(schemeid);
					scheme.setRepaymentscheme(repayment);
					if (dbService.save(scheme)) {
						flag = "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * --Delete Repayment--
	 * 
	 * @author Wel (Sept.05.2018)
	 * @return String = success, otherwise failed
	 */
	@Override
	public String deleteRepayment(String prodcode, String schemecode) {
		String flag = "failed";
		try {
			if (prodcode != null && schemecode != null) {
				params.put("product", prodcode);
				params.put("scheme", schemecode);
				Integer res = dbService.executeUpdate(
						"DELETE FROM Tbloanschemeperprod WHERE prodcode=:product AND schemecode=:scheme", params);
				if (res != null && res > 0) {
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
	public List<Tbloanschemeperprod> getRepayments(String prodcode) {
		List<Tbloanschemeperprod> loanschemes = new ArrayList<Tbloanschemeperprod>();
		params.put("prodcode", prodcode);
		try {
			loanschemes = (List<Tbloanschemeperprod>) dbService
					.executeListHQLQuery("From Tbloanschemeperprod Where prodcode=:prodcode", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loanschemes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbloanrepaymentscheme> getListRepayments() {
		List<Tbloanrepaymentscheme> repayments = new ArrayList<Tbloanrepaymentscheme>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			repayments = (List<Tbloanrepaymentscheme>) dbService.executeListHQLQuery("From Tbloanrepaymentscheme",
					params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return repayments;
	}

	/**
	 * --Get App Status/Process Name by workflowid, sequenceno--
	 * 
	 * @author Wel (Sept.07.2018)
	 * @return form = {@link Tbworkflowprocess}
	 */
	@Override
	public Tbworkflowprocess getAppStatusProcessName(Integer workflowid, Integer sequenceno) {
		Tbworkflowprocess row = new Tbworkflowprocess();
		try {
			if (workflowid != null && sequenceno != null) {
				params.put("workflowid", workflowid);
				params.put("sequenceno", sequenceno);
				row = (Tbworkflowprocess) dbService.executeUniqueHQLQueryMaxResultOne(
						"FROM Tbworkflowprocess a WHERE a.id.workflowid=:workflowid AND a.id.sequenceno=:sequenceno",
						params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	/**
	 * --Get Cycle per Loan Scheme(Repaymenttype)--
	 * 
	 * @author Kevin (09.13.2018)
	 * @return List<{@link CyclePerLoanSchemeForm}>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CyclePerLoanSchemeForm> getPayCyclePerLoanScheme(Integer schemecode, String pi) {
		List<CyclePerLoanSchemeForm> list = new ArrayList<CyclePerLoanSchemeForm>();
		try {
			if (schemecode != null && pi != null) {
				params.put("schemecode", schemecode);
				params.put("pi", pi);
				list = (List<CyclePerLoanSchemeForm>) dbService.execSQLQueryTransformer(
						"SELECT schemecode, cyclecode, cycle, pi FROM Tbcycleperloanscheme WHERE schemecode=:schemecode AND pi=:pi",
						params, CyclePerLoanSchemeForm.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * --Get Loan Scheme per Product--
	 * 
	 * @author Kevin (09.13.2018)
	 * @return List<{@link LoanSchemePerProdForm}>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoanSchemePerProdForm> getLoanSchemePerProd(String prodcode) {
		List<LoanSchemePerProdForm> list = new ArrayList<LoanSchemePerProdForm>();
		try {
			if (prodcode != null) {
				params.put("prodcode", prodcode);
				list = (List<LoanSchemePerProdForm>) dbService.execSQLQueryTransformer(
						"SELECT prodcode, schemecode, repaymentscheme FROM Tbloanschemeperprod WHERE prodcode=:prodcode",
						params, LoanSchemePerProdForm.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * --Get Loan Fess per appno--
	 * 
	 * @author Kevin (09.13.2018)
	 * @return List<{@link Tbloanfeesperapp}>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbloanfeesperapp> getLoanFeesPerApp(String appno) {
		List<Tbloanfeesperapp> list = new ArrayList<Tbloanfeesperapp>();
		try {
			if (appno != null) {
				params.put("appno", appno);
				list = (List<Tbloanfeesperapp>) dbService
						.executeListHQLQuery("FROM Tbloanfeesperapp WHERE appno=:appno", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * --Save Or Update Loan Scheme per appno--
	 * 
	 * @author Kevin (09.13.2018)
	 * @return String = success, otherwise failed
	 */
	@Override
	public String saveOrUpdateLoanFeesPerApp(Tbloanfeesperapp loanfees, String prevloanfeecode) {
		String flag = "failed";
		boolean isNewData = false;
		try {
			if (loanfees.getId().getAppno() != null && loanfees.getId().getLoanfeecode() != null) {
				params.put("appno", loanfees.getId().getAppno());

				if (prevloanfeecode != null) {
					params.put("prevloanfeecode", prevloanfeecode);
					Integer fee = (Integer) dbService.executeUniqueSQLQuery(
							"SELECT COUNT(*) FROM Tbloanfeesperapp WHERE appno=:appno AND loanfeecode=:prevloanfeecode",
							params);
					if (fee > 0) {

						// If existing
						params.put("chkloanfeecode", loanfees.getId().getLoanfeecode());
						Integer chk = (Integer) dbService.executeUniqueSQLQuery(
								"SELECT COUNT(*) FROM Tbloanfeesperapp WHERE appno=:appno AND loanfeecode=:chkloanfeecode",
								params);
						if (!prevloanfeecode.equalsIgnoreCase(loanfees.getId().getLoanfeecode()) && chk > 0) {
							return "existing";
						} else {
//							int res = dbService.executeUpdate("UPDATE Tbloanfeesperapp SET loanfeecode='"
//									+ loanfees.getId().getLoanfeecode() + "', feeamount=" + loanfees.getFeeamount()
//									+ ", doccount=" + loanfees.getDoccount()
//									+ " WHERE appno=:appno AND loanfeecode=:loanfeecode", params);
//							if (res > 0) {
//								flag = "success";
//							}

							Tbloanfeesperapp lfpa = (Tbloanfeesperapp) dbService.executeUniqueHQLQuery(
									"FROM Tbloanfeesperapp WHERE id.appno=:appno AND id.loanfeecode=:loanfeecode",
									params);
							if (lfpa != null) {
								lfpa.setFeeamount(loanfees.getFeeamount());
								lfpa.setDoccount(loanfees.getDoccount());
								lfpa.setCollectionrule(loanfees.getCollectionrule());
								if (dbService.saveOrUpdate(lfpa)) {
									flag = "success";
								}
							}

						}

					} else {
						isNewData = true;
					}
				} else {
					params.put("loanfeecode", loanfees.getId().getLoanfeecode());
					Integer fee = (Integer) dbService.executeUniqueSQLQuery(
							"SELECT COUNT(*) FROM Tbloanfeesperapp WHERE appno=:appno AND loanfeecode=:loanfeecode",
							params);
					if (fee > 0) {
						return "existing";
					} else {
						isNewData = true;
					}
				}

				// If new data
				if (isNewData) {
					loanfees.setStatus("Apply");
					loanfees.setDateupdate(new Date());
					loanfees.setUpdatedby(UserUtil.securityService.getUserName());
					if (dbService.saveOrUpdate(loanfees)) {
						flag = "success";
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * --Delete Loan Scheme per appno--
	 * 
	 * @author Kevin (09.13.2018)
	 * @return String = success, otherwise failed
	 */
	@Override
	public String deleteLoanFeesPerApp(Tbloanfeesperapp loanfees) {
		String flag = "failed";
		try {
			if (dbService.delete(loanfees)) {
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * --Generate Loan Fees per appno--
	 * 
	 * @author Kevin (09.14.2018)
	 * @return String = success, otherwise failed
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String generateLoanFeesPerApp(LoanFeeInputForm fee) {
		String flag = "failed";
		try {
			if (fee.getAppno() != null) {
//				params.put("appno", fee.getAppno());
//				Tblstapp lstapp = (Tblstapp) dbService.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno",
//						params);
				if (fee.getProdcode() != null) {
//					String product = lstapp.getLoanproduct();
					String product = fee.getProdcode();
					if (product != null) {
						params.put("product", product);
						List<Tbloanproductfees> prodfees = (List<Tbloanproductfees>) dbService
								.executeListHQLQuery("FROM Tbloanproductfees WHERE prodcode=:product", params);
						if (prodfees != null) {
							for (Tbloanproductfees f : prodfees) {
								Tbloanfeesperapp feesperapp = new Tbloanfeesperapp();
								TbloanfeesperappId feesperappid = new TbloanfeesperappId();
								feesperappid.setAppno(fee.getAppno());
								feesperappid.setLoanfeecode(f.getId().getLoanfeecode());
								feesperapp.setId(feesperappid);

								BigDecimal loanfee = f.getLoanfeeamt();

								// Using Formula
								if (f.getLoanfeetype().equals("1")) {

									if (f.getDstformulamul1opt().equals("Term in Months")) {
										if (fee.getTermperiod().equals("days")) {
											f.setDstformulamul1(fee.getTermval().divide(BigDecimal.valueOf(30D)));
										} else if (fee.getTermperiod().equals("months")) {
											f.setDstformulamul1(fee.getTermval());
										} else if (fee.getTermperiod().equals("years")) {
											f.setDstformulamul1(fee.getTermval().multiply(BigDecimal.valueOf(12D)));
										}

									} else {
										if (f.getDstformulamul1().compareTo(BigDecimal.valueOf(1D)) < -1)
											f.setDstformulamul1(BigDecimal.valueOf(1D));
									}
									// Loan Amount
									if (f.getRateappliedto().equals("0") && fee.getLoanamount() != null) {
										loanfee = fee.getLoanamount()
												.divide(f.getDstformuladiv(), 2, RoundingMode.HALF_UP)
												.multiply(f.getDstformulamul()).multiply(f.getDstformulamul1());
									}
									// Principal Value
									if (f.getRateappliedto().equals("1") && fee.getPrincipalvalue() != null) {
										loanfee = fee.getPrincipalvalue()
												.divide(f.getDstformuladiv(), 2, RoundingMode.HALF_UP)
												.multiply(f.getDstformulamul()).multiply(f.getDstformulamul1());
									}
									// Interest Value
									if (f.getRateappliedto().equals("2") && fee.getInterestvalue() != null) {
										loanfee = fee.getInterestvalue()
												.divide(f.getDstformuladiv(), 2, RoundingMode.HALF_UP)
												.multiply(f.getDstformulamul()).multiply(f.getDstformulamul1());
									}
									// Past Due Amount
									if (f.getRateappliedto().equals("3") && fee.getPastdueamount() != null) {
										loanfee = fee.getPastdueamount()
												.divide(f.getDstformuladiv(), 2, RoundingMode.HALF_UP)
												.multiply(f.getDstformulamul()).multiply(f.getDstformulamul1());
									}
									// Maturity Value
									if (f.getRateappliedto().equals("4") && fee.getMaturityval() != null) {
										loanfee = fee.getMaturityval()
												.divide(f.getDstformuladiv(), 2, RoundingMode.HALF_UP)
												.multiply(f.getDstformulamul()).multiply(f.getDstformulamul1());
									}
								}

								// Using Fixed Rate
								if (f.getLoanfeetype().equals("2")) {
									BigDecimal d100 = new BigDecimal(100);
									// Loan Amount
									if (f.getRateappliedto().equals("0") && fee.getLoanamount() != null) {
										loanfee = fee.getLoanamount().multiply(f.getLoanfeerate()).divide(d100);
									}
									// Principal Value
									if (f.getRateappliedto().equals("1") && fee.getPrincipalvalue() != null) {
										loanfee = fee.getPrincipalvalue().multiply(f.getLoanfeerate()).divide(d100);
									}
									// Interest Value
									if (f.getRateappliedto().equals("2") && fee.getInterestvalue() != null) {
										loanfee = fee.getInterestvalue().multiply(f.getLoanfeerate()).divide(d100);
									}
									// Past Due Amount
									if (f.getRateappliedto().equals("3") && fee.getPastdueamount() != null) {
										loanfee = fee.getPastdueamount().multiply(f.getLoanfeerate()).divide(d100);
									}
									// Maturity Value
									if (f.getRateappliedto().equals("4") && fee.getMaturityval() != null) {
										loanfee = fee.getMaturityval().multiply(f.getLoanfeerate()).divide(d100);
									}

									if (fee.getMatdate() != null) {
										int noofdays = Days.daysBetween(LocalDate.fromDateFields(fee.getBookingdate()),
												LocalDate.fromDateFields(fee.getMatdate())).getDays();
										if (f.getId().getLoanfeecode().equals("DST")) {

											if (noofdays < 365) {
												BigDecimal term = BigDecimal.valueOf(noofdays)
														.divide(BigDecimal.valueOf(365), 10, RoundingMode.HALF_UP);
												loanfee = loanfee.multiply(term).setScale(2, RoundingMode.HALF_UP);
											}
										}
									}
								}
								// if Notarial Fee and Loan Fee Type = (Using Per Document Rate)
								if (f.getLoanfeetype().equals("4")) {
//									params.put("loanfeecode", f.getId().getLoanfeecode());
//									Tbloanfeesperapp docrate = (Tbloanfeesperapp) dbService
//											.executeUniqueHQLQueryMaxResultOne(
//													"FROM Tbloanfeesperapp WHERE id.appno=:appno AND id.loanfeecode=:loanfeecode",
//													params);
									// get no. of documents with notarial fee
									List<Tbdocsperproduct> docs = new ArrayList<Tbdocsperproduct>();
									params.put("prodcode", fee.getProdcode());

									System.out.println("Product Code : " + fee.getProdcode());
									System.out.println(fee.getCiftype());

									if (fee.getCiftype().equals("1")) {
										docs = (List<Tbdocsperproduct>) dbService.executeListHQLQuery(
												"FROM Tbdocsperproduct WHERE productcode=:prodcode AND enablenotarialfee=true AND isindivrequired=true",
												params);
										System.out.println(docs.size());
										System.out.println(f.getNotdocamt());
									}
									if (fee.getCiftype().equals("2") || fee.getCiftype().equals("3")) {
										// get businesstype
										params.put("businesstype", fee.getBusinesstype());
										Tbcodetable business = (Tbcodetable) dbService.executeUniqueHQLQuery(
												"FROM Tbcodetable where codename='BUSINESSTYPE' AND codevalue=:businesstype",
												params);
										if (business.getDesc2().equals("Corporation"))
											docs = (List<Tbdocsperproduct>) dbService.executeListHQLQuery(
													"FROM Tbdocsperproduct WHERE productcode=:prodcode AND enablenotarialfee=true AND iscorprequired=true",
													params);

										else if (business.getDesc2().equals("Sole Proprietorship"))
											docs = (List<Tbdocsperproduct>) dbService.executeListHQLQuery(
													"FROM Tbdocsperproduct WHERE productcode=:prodcode AND enablenotarialfee=true AND issoleproprequired=true",
													params);

										else if (business.getDesc2().equals("Partnership"))
											docs = (List<Tbdocsperproduct>) dbService.executeListHQLQuery(
													"FROM Tbdocsperproduct WHERE productcode=:prodcode AND enablenotarialfee=true AND ispartnerrequired=true",
													params);

									}
									if (docs != null) {
										loanfee = BigDecimal.valueOf(docs.size()).multiply(f.getNotdocamt());
										feesperapp.setDoccount(docs.size());
									} else {
										feesperapp.setId(feesperappid);
									}
								}

								// Based on Source of Application
								if (f.getLoanfeetype().equals("7")) {

									/*
									 * IN-HOUSE
									 */

									if (fee.getRefferaltype().equals("1") || fee.getRefferaltype().equals("3")) {
										// Fixed Rate
										if (f.getInhouserule().equals("2")) {
											BigDecimal d100 = new BigDecimal(100);
											// Loan Amount
											if (f.getInhouserateapplied().equals("0") && fee.getLoanamount() != null) {
												loanfee = fee.getLoanamount().multiply(f.getInhouserate()).divide(d100);
											}
											// Principal Value
											if (f.getInhouserateapplied().equals("1")
													&& fee.getPrincipalvalue() != null) {
												loanfee = fee.getPrincipalvalue().multiply(f.getInhouserate())
														.divide(d100);
											}
											// Interest Value
											if (f.getInhouserateapplied().equals("2")
													&& fee.getInterestvalue() != null) {
												loanfee = fee.getInterestvalue().multiply(f.getInhouserate())
														.divide(d100);
											}
											// Past Due Amount
											if (f.getInhouserateapplied().equals("3")
													&& fee.getPastdueamount() != null) {
												loanfee = fee.getPastdueamount().multiply(f.getInhouserate())
														.divide(d100);
											}
											// Maturity Value
											if (f.getInhouserateapplied().equals("4") && fee.getMaturityval() != null) {
												loanfee = fee.getMaturityval().multiply(f.getInhouserate())
														.divide(d100);
											}
										}
										// Fixed Amount
										if (f.getInhouserule().equals("3")) {
											loanfee = f.getInhouseamt();
										}

									}
									/*
									 * AGENT
									 */
									// Fixed Rate
									if (fee.getRefferaltype().equals("2")) {
										if (f.getAgentrule().equals("2")) {
											BigDecimal d100 = new BigDecimal(100);
											// Loan Amount
											if (f.getAgentrateapplied().equals("0") && fee.getLoanamount() != null) {
												loanfee = fee.getLoanamount().multiply(f.getAgentrate()).divide(d100);
											}
											// Principal Value
											if (f.getAgentrateapplied().equals("1")
													&& fee.getPrincipalvalue() != null) {
												loanfee = fee.getPrincipalvalue().multiply(f.getAgentrate())
														.divide(d100);
											}
											// Interest Value
											if (f.getAgentrateapplied().equals("2") && fee.getInterestvalue() != null) {
												loanfee = fee.getInterestvalue().multiply(f.getAgentrate())
														.divide(d100);
											}
											// Past Due Amount
											if (f.getAgentrateapplied().equals("3") && fee.getPastdueamount() != null) {
												loanfee = fee.getPastdueamount().multiply(f.getAgentrate())
														.divide(d100);
											}
											// Maturity Value
											if (f.getAgentrateapplied().equals("4") && fee.getMaturityval() != null) {
												loanfee = fee.getMaturityval().multiply(f.getAgentrate()).divide(d100);
											}
										}
										// Fixed Amount
										if (f.getAgentrule().equals("3")) {
											loanfee = f.getAgentamt();
										}
									}
								}

								if (loanfee == null) {
									loanfee = BigDecimal.ZERO;
								}
								if (feesperapp.getDoccount() == null) {
									feesperapp.setDoccount(0);
								}

								feesperapp.setDocrate(f.getNotdocamt());
								feesperapp.setFeeamount(loanfee.setScale(2, RoundingMode.HALF_UP));
								feesperapp.setCollectionrule(f.getLoancollection());
								feesperapp.setStatus("Apply");
								feesperapp.setDateupdate(new Date());
								feesperapp.setUpdatedby(UserUtil.securityService.getUserName());
								feesperapp.setAcctno(null);
								dbService.saveOrUpdate(feesperapp);

							}
						}
						flag = "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * --Get List of Repayment scheme--
	 * 
	 * @author Kevin (09.14.2018)
	 * @return List<{@link Tbloanrepaymentscheme}>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbloanrepaymentscheme> getLoanRepayScheme(Integer schemecode) {
		List<Tbloanrepaymentscheme> list = new ArrayList<Tbloanrepaymentscheme>();
		try {
			String hql = "FROM Tbloanrepaymentscheme";
			if (schemecode != null) {
				params.put("schemecode", schemecode);
				hql += " WHERE schemecode=:schemecode";
			}
			list = (List<Tbloanrepaymentscheme>) dbService.executeListHQLQuery(hql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * --Check default loan fee per prod--
	 * 
	 * @author Kevin (09.15.2018)
	 * @return true otherwise false
	 */
	@Override
	public Boolean isDefaultLoanFee(String prodcode, String loanfeecode) {
		boolean flag = false;
		try {
			if (prodcode != null && loanfeecode != null) {
				params.put("prodcode", prodcode);
				params.put("loanfeecode", loanfeecode);
				Integer cnt = (Integer) dbService.executeUniqueSQLQuery(
						"SELECT COUNT(*) FROM Tbloanproductfees WHERE prodcode=:prodcode AND loanfeecode=:loanfeecode",
						params);
				if (cnt != null && cnt > 0) {
					flag = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * --Submit Loan Product (Change Status)--
	 * 
	 * @author Kevin (10.17.2018)
	 * @return String "success" otherwise "failed"
	 */
	@Override
	public String submitLoanProduct(String productcode, String status) {
		String flag = "failed";
		try {
			if (productcode != null && status != null) {
				params.put("productcode", productcode);
				Tbloanproduct prod = (Tbloanproduct) dbService
						.executeUniqueHQLQuery("FROM Tbloanproduct WHERE productcode=:productcode", params);
				if (prod != null) {
					String prevStatus = prod.getStatus();

					prod.setStatus(status);
					prod.setUpdatedby(UserUtil.securityService.getUserName());
					prod.setDateupdated(new Date());
					if (dbService.saveOrUpdate(prod)) {
						flag = "success";

						if (prevStatus != null && !prevStatus.equals(status)) {
							// Email Notif Routine
//							EmailService emailSrvc = new EmailServiceImpl();
//							emailSrvc.sendEmail(EmailCode.LOAN_PRODUCT_STATUS, productcode, null);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * --Get List of Collateral per Prod--
	 * 
	 * @author Kevin (10.18.2018)
	 * @return List<{@link Tbcollateralperprod}>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcollateralperprod> getListOfCollateralPerProd(String prodcode) {
		List<Tbcollateralperprod> list = new ArrayList<Tbcollateralperprod>();
		try {
			if (prodcode != null) {
				params.put("prodcode", prodcode);
				list = (List<Tbcollateralperprod>) dbService
						.executeListHQLQuery("FROM Tbcollateralperprod WHERE id.prodcode=:prodcode", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * --Save Collateral Per Prod--
	 * 
	 * @author Kevin (10.18.2018)
	 * @return String "success" otherwise "failed"
	 */
	@Override
	public String saveCollateralPerProd(String prodcode, Boolean iscollateralrequired, List<CodetableForm> collateral) {
		String flag = "failed";
		try {
			if (prodcode != null) {
				params.put("prodcode", prodcode);
				if (iscollateralrequired != null && iscollateralrequired && collateral != null) {
					String selectedCol = "''";
					for (CodetableForm c : collateral) {
						selectedCol += ",'" + c.getCodevalue() + "'";
						params.put("collateraltype", c.getCodevalue());
						Integer checker = (Integer) dbService.executeUniqueSQLQuery(
								"SELECT COUNT(*) FROM Tbcollateralperprod WHERE prodcode=:prodcode AND collateraltype=:collateraltype",
								params);
						if (checker != null && checker == 0) {
							TbcollateralperprodId id = new TbcollateralperprodId();
							id.setProdcode(prodcode);
							id.setCollateraltype(c.getCodevalue());

							Tbcollateralperprod p = new Tbcollateralperprod();
							p.setId(id);
							p.setAddedby(UserUtil.securityService.getUserName());
							p.setDateadded(new Date());
							dbService.saveOrUpdate(p);
						}
					}

					// Delete if not in selected item
					dbService.executeUpdate(
							"DELETE FROM Tbcollateralperprod WHERE prodcode=:prodcode AND collateraltype NOT IN("
									+ selectedCol + ")",
							params);
				} else {
					dbService.executeUpdate("DELETE FROM Tbcollateralperprod WHERE prodcode=:prodcode", params);
				}
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * --Get Saved Collateral per Prod--
	 * 
	 * @author Kevin (10.18.2018)
	 * @return List<{@link CodetableForm}>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CodetableForm> getSavedCollateralPerProd(String prodcode) {
		List<CodetableForm> list = new ArrayList<CodetableForm>();
		try {
			if (prodcode != null && !prodcode.equalsIgnoreCase("NULL") && !prodcode.equals("")) {
				params.put("prodcode", prodcode);
				list = (List<CodetableForm>) dbService.execSQLQueryTransformer(
						"SELECT a.codename, a.codevalue, a.desc1, a.desc2 FROM TBCODETABLE a "
								+ "INNER JOIN TBCOLLATERALPERPROD b ON a.codename='COLLATERALTYPE' AND a.codevalue=b.collateraltype "
								+ "WHERE b.prodcode=:prodcode ORDER BY b.collateraltype",
						params, CodetableForm.class, 1);
				if (list == null || list.isEmpty()) {
					list = (List<CodetableForm>) dbService.execSQLQueryTransformer(
							"SELECT c.codename, c.codevalue, c.desc1, c.desc2 FROM Tbloanproduct a "
									+ "LEFT JOIN Tbcollateralperprod b ON a.productcode=b.prodcode "
									+ "LEFT JOIN Tbcodetable c ON b.collateraltype = c.codevalue "
									+ "WHERE a.productname=:prodcode and c.codename='COLLATERALTYPE' ORDER BY b.collateraltype",
							params, CodetableForm.class, 1);
				}
				System.out.println("ezzzzzzzzz " + list.size());
			} else {
				list = (List<CodetableForm>) dbService.execSQLQueryTransformer(
						"SELECT a.codename, a.codevalue, a.desc1, a.desc2 FROM TBCODETABLE a WHERE a.codename='COLLATERALTYPE'",
						params, CodetableForm.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * --Get Repayment Scheme per Prod w/ Payment and Interest Cycle--
	 * 
	 * @author Kevin (10.18.2018)
	 * @return List<{@link RepaymentSchemeDisplayForm}>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RepaymentSchemeDisplayForm> getRepaymentScheme(String prodcode) {
		List<RepaymentSchemeDisplayForm> list = new ArrayList<RepaymentSchemeDisplayForm>();
		try {
			if (prodcode != null) {
				params.put("prodcode", prodcode);
				List<Tbloanschemeperprod> sp = (List<Tbloanschemeperprod>) dbService
						.executeListHQLQuery("FROM Tbloanschemeperprod WHERE id.prodcode=:prodcode", params);
				if (sp != null && !sp.isEmpty()) {
					for (Tbloanschemeperprod s : sp) {
						params.put("schemecode", s.getId().getSchemecode());
						RepaymentSchemeDisplayForm form = new RepaymentSchemeDisplayForm();
						form.setProdcode(s.getId().getProdcode());
						form.setSchemecode(s.getId().getSchemecode());
						form.setRepaymentscheme(s.getRepaymentscheme());

						String paycycle = (String) dbService
								.execSQLQueryTransformer("DECLARE @PayCycle varchar (8000); "
										+ "SELECT @PayCycle = COALESCE(@PayCycle + ', ', '') + cycle "
										+ "FROM TBCYCLEPERLOANSCHEME WHERE schemecode =:schemecode AND pi = 'P' "
										+ "SELECT @PayCycle", params, null, 0);

						String intcycle = (String) dbService
								.execSQLQueryTransformer("DECLARE @IntCycle varchar (8000); "
										+ "SELECT @IntCycle = COALESCE(@IntCycle + ', ', '') + cycle "
										+ "FROM TBCYCLEPERLOANSCHEME WHERE schemecode =:schemecode AND pi = 'I' "
										+ "SELECT @IntCycle", params, null, 0);
						form.setPaycycle(paycycle);
						form.setIntcycle(intcycle);
						list.add(form);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbproductpercompanyform> getListOfLoanProductPerCompany(String company) {
		List<Tbproductpercompanyform> form = new ArrayList<Tbproductpercompanyform>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (company != null) {
				params.put("comp", company.trim());
				form = (List<Tbproductpercompanyform>) dbService.execSQLQueryTransformer(
						"SELECT companycode, productcode, productname, producttype FROM Tbproductpercompany WHERE companycode=:comp AND isactive='true' AND producttype='200'",
						params, Tbproductpercompanyform.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public String checkProductNo(String productno) {
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (productno != null) {
				params.put("producttype2", productno);
				Integer p = (Integer) dbService.executeUniqueSQLQuery(
						"SELECT COUNT(*) FROM Tbloanproduct WHERE producttype2=:producttype2", params);
				if (p > 0) {
					return "Not Available";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Available";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbproductpercompanyform> getListOfLoanProductPerCompanyPerGroup(String company, String prodgrp) {
		// TODO Auto-generated method stub
		List<Tbproductpercompanyform> form = new ArrayList<Tbproductpercompanyform>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (company != null) {
				params.put("comp", company);
				params.put("grp", prodgrp);
				System.out.println(prodgrp);
				System.out.println(company);

				form = (List<Tbproductpercompanyform>) dbService.execSQLQueryTransformer(
						"SELECT companycode, productcode, productname, producttype FROM Tbproductpercompany WHERE companycode=:comp AND isactive='true' AND producttype=:grp",
						params, Tbproductpercompanyform.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbdeductions> getListOfDeductionsPerProductCode(String productcode) {
		List<Tbdeductions> deductions = new ArrayList<Tbdeductions>();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("productcode", productcode);
		try {

			// params.put("txcode", txcode);
			deductions = (List<Tbdeductions>) dbService
					.executeListHQLQuery("FROM Tbdeductions where productcode=:productcode", params);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return deductions;
	}

	@Override
	public String saveorUpdateDeduction(Tbdeductions deduction, String beingUpdated) {
		String flag = "failed";
		String user = UserUtil.securityService.getUserName();
		System.out.println(beingUpdated);
		if (deduction != null) {
			if (beingUpdated.equals("true")) {
				System.out.println("PASSED");
				deduction.setDateupdated(new Date());
				deduction.setUpdatedby(user);
			} else {
				System.out.println("failed");
				deduction.setDatecreated(new Date());
				deduction.setCreatedby(user);
			}
			dbService.saveOrUpdate(deduction);
			flag = "success";
			return flag;
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbbillingcutoffperprod> getListOfBillingCutOff(String productcode) {
		List<Tbbillingcutoffperprod> deductions = new ArrayList<Tbbillingcutoffperprod>();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("productcode", productcode);
		try {

			// params.put("txcode", txcode);
			deductions = (List<Tbbillingcutoffperprod>) dbService
					.executeListHQLQuery("FROM Tbbillingcutoffperprod where productcode=:productcode", params);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return deductions;
	}

	@Override
	public String saveOrUpdateBillingCutOff(Tbbillingcutoffperprod billingCutOff, String beingUpdated) {
		String flag = "failed";
		String user = UserUtil.securityService.getUserName();

		if (billingCutOff != null) {
			if (beingUpdated.contains("true")) {
				billingCutOff.setDateupdated(new Date());
				billingCutOff.setUpdatedby(user);
			} else {

				billingCutOff.setCreatedby(user);
				billingCutOff.setDatecreated(new Date());
			}
			dbService.saveOrUpdate(billingCutOff);

			flag = "success";
			return flag;
		}
		return flag;

	}

	@Override
	public String deleteBillingCutOFf(Tbbillingcutoffperprod billingCutOff) {
		String result = "Failed";
		try {
			if (dbService.delete(billingCutOff)) {
				result = "Success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String deleteDeductions(Tbdeductions deductions) {
		String result = "Failed";
		try {
			if (dbService.delete(deductions)) {
				result = "Success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbmembershiptypeperbos> getMembershipTypePerBos(String boscode) {
		List<Tbmembershiptypeperbos> membershiptype = new ArrayList<Tbmembershiptypeperbos>();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("boscode", boscode);
		System.out.println(boscode);
		try {
			if (boscode != null) {
				System.out.println("PASSED");

				membershiptype = (List<Tbmembershiptypeperbos>) dbService
						.executeListHQLQuery("FROM Tbmembershiptypeperbos where boscode=:boscode", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return membershiptype;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BOSForm> getBOSPerProd(String productcode) {
		params.put("productcode", productcode);
		if (productcode != null) {
			System.out.println("PASSED");
			List<BOSForm> bos = (List<BOSForm>) dbService.execSQLQueryTransformer(
					"SELECT A.boscode,B.companyname,B.maxloanableamount as maxloanableamt FROM TBPRODUCTPERBOS AS A "
							+ "INNER JOIN TBMEMBERCOMPANY AS B ON A.boscode = B.companycode"
							+ " WHERE A.productcode =:productcode GROUP BY A.boscode,B.companyname,B.maxloanableamount",
					params, BOSForm.class, 1);
			return bos;
		}
		return null;
	}

	@Override
	public String saveBOS(Tbloanprodmembertype loanprod, List<CodetableForm> servicestatus) {
		String flag = "failed";
		try {
			if (loanprod != null) {
//					Tbloanprodmembertype loan = new Tbloanprodmembertype();
				// TbmembershiptypeperbosId id = new TbmembershiptypeperbosId();
				for (CodetableForm c : servicestatus) {

					if (c.getCodevalue().equals("1")) {
						loanprod.setIsactive(true);

					} else {
						loanprod.setIsretired(true);
					}

				}
				loanprod.setDateadded(new Date());
				loanprod.setAddedby(UserUtil.securityService.getUserName());
				dbService.saveOrUpdate(loanprod);
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked") // DANI 08032019
	@Override
	public List<CodetableForm> getSavedServiceStatus(String productcode, String membertype) {
		List<CodetableForm> list = new ArrayList<CodetableForm>();
		try {
			if (productcode != null && !productcode.equalsIgnoreCase("NULL") && !productcode.equals("")) {
				System.out.println(productcode);
				params.put("productcode", productcode);
				params.put("membertype", membertype);
				list = (List<CodetableForm>) dbService.execSQLQueryTransformer(
						"SELECT a.codename, a.codevalue, a.desc1, a.desc2 FROM TBCODETABLE a "
								+ "INNER JOIN TBLOANPRODMEMBERTYPE b ON a.codename='SERVICESTATUS' AND a.codevalue= CASE WHEN b.isactive=1 THEN '1' WHEN b.isretired=1 THEN '2' END "
								+ "WHERE b.loanproduct=:productcode AND b.membertype =:membertype ORDER BY b.membertype",
						params, CodetableForm.class, 1);
			} else {
				list = (List<CodetableForm>) dbService.execSQLQueryTransformer(
						"SELECT a.codename, a.codevalue, a.desc1, a.desc2 FROM TBCODETABLE a WHERE a.codename='SERVICESTATUS'",
						params, CodetableForm.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String saveMLAC(List<CodetableForm> mlaparticulars, String lowest, String highest, String prodcode) {
		String flag = "failed";
		String condicode = "";
		try {
			System.out.println(highest);
			System.out.println(lowest);
			if (prodcode != null) {
				params.put("productcode", prodcode);
				String selectedCol = "''";
				Tbmlacperproduct mlac = new Tbmlacperproduct();
				TbmlacperproductId id = new TbmlacperproductId();
				if (highest != null && highest.equals("true")) {
					condicode = "2";

				} else if (lowest != null && lowest.equals("true")) {
					condicode = "1";
				}
				params.put("conditioncode", condicode);
				dbService.executeUpdate("DELETE FROM Tbmlacperproduct WHERE productcode=:productcode", params);
				for (CodetableForm c : mlaparticulars) {
					selectedCol += ",'" + c.getCodevalue() + "'";
//						Tbmembercompany m = (Tbmembercompany) dbService
//								.executeUniqueHQLQuery("FROM Tbmembershiptypeperbos WHERE b=:companycode", params);
					System.out.println(c.getCodevalue());
					System.out.println(condicode);
					id.setProductcode(prodcode);
					id.setConditioncode(condicode);
					id.setParticulars(c.getCodevalue());
					mlac.setId(id);
					mlac.setAddedby(UserUtil.securityService.getUserName());
					mlac.setDateadded(new Date());

					dbService.saveOrUpdate(mlac);

					// Delete if not in selected item
//					dbService.executeUpdate(
//							"DELETE FROM Tbmlacperproduct WHERE productcode=:productcode AND conditioncode=:conditioncode AND particulars NOT IN("
//									+ selectedCol + ")",
//							params);
				}
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CodetableForm> getSavedParticulars(String productcode, String lowest, String highest) {
		List<CodetableForm> list = new ArrayList<CodetableForm>();
//		String conditioncode = null;
		try {
			if (productcode != null && !productcode.equalsIgnoreCase("NULL") && !productcode.equals("")) {
				params.put("productcode", productcode);
//				if(highest.equals("true"))
//				{
//					conditioncode = "2";
//					
//				}
//				if(lowest.equals("true"))
//				{
//					conditioncode = "1";
//				}
//				params.put("conditioncode", conditioncode);
				list = (List<CodetableForm>) dbService.execSQLQueryTransformer(
						"SELECT a.codename, a.codevalue, a.desc1, b.conditioncode as desc2 FROM TBCODETABLE a "
								+ "INNER JOIN TBMLACPERPRODUCT b ON a.codename='MLACPARTICULARS' AND a.codevalue=b.particulars"
								+ " WHERE b.productcode=:productcode ORDER BY b.particulars",
						params, CodetableForm.class, 1);
			} else {
				list = (List<CodetableForm>) dbService.execSQLQueryTransformer(
						"SELECT a.codename, a.codevalue, a.desc1, '' as desc2 FROM TBCODETABLE a WHERE a.codename='MLACPARTICULARS'",
						params, CodetableForm.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanProductForm> listLoanProduct() {
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		List<LoanProductForm> list = new ArrayList<LoanProductForm>();
		try {
			list = (List<LoanProductForm>) dbService.execSQLQueryTransformer(
					"SELECT productcode, productname FROM Tbloanproduct ORDER BY productname ASC", params,
					LoanProductForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbdocsperproduct> getDocumentsPerProductDocCat(String productcode, String docCat) {
		// TODO Auto-generated method stub
		List<Tbdocsperproduct> docs = new ArrayList<Tbdocsperproduct>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (productcode != null) {
				params.put("prodcode", productcode);
				params.put("docCat", docCat);
				docs = (List<Tbdocsperproduct>) dbService.executeListHQLQuery(
						"FROM Tbdocsperproduct WHERE productcode=:prodcode AND doccategory=:docCat", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return docs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanIntRateTable> getLoanIntRateTable(String productcode) {
		List<LoanIntRateTable> list = new ArrayList<LoanIntRateTable>();

		try {
			param.put("productcode", productcode);
			String myQuery = "select productcode, loanterm, rate FROM Tbintratebyterm WHERE productcode = :productcode";
			System.out.print("MAR " + myQuery);
			list = (List<LoanIntRateTable>) dbService.execSQLQueryTransformer(myQuery, param, LoanIntRateTable.class,
					1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
