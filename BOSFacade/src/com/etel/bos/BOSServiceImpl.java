/**
 * 
 */
package com.etel.bos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbproductperbos;
import com.coopdb.data.TbproductperbosId;
import com.etel.bos.form.ProductPerBOSForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.AuditLog;
import com.etel.utils.AuditLogEvents;
import com.etel.utils.HQLUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

/**
 * @author ETEL-LAPTOP19
 *
 */
@SuppressWarnings("unchecked")
public class BOSServiceImpl implements BOSService {

	private DBService dbService = new DBServiceImpl();
	private Map<String, Object> param = HQLUtil.getMap();
	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");

	@Override
	public List<ProductPerBOSForm> listProductPerBOS(String boscode, String membershiptype, String servicestatus) {
		List<ProductPerBOSForm> prodList = new ArrayList<ProductPerBOSForm>();
		/*param.put("boscode", boscode);
		param.put("membershiptype", membershiptype);
		param.put("servicestatus", servicestatus);*/
		param.put("boscode", boscode == null ? "%%" : "%" + boscode.trim() + "%");
		param.put("membershiptype", membershiptype == null ? "%%" : "%" + membershiptype.trim() + "%");
		param.put("servicestatus", servicestatus == null ? "%%" : "%" + servicestatus.trim() + "%");
		try {

			prodList = (List<ProductPerBOSForm>) dbService.execSQLQueryTransformer(
					"SELECT boscode,productcode,productname,membershiptype,servicestatus,membershiptypedesc,servicestatusdesc "
					+ "FROM Tbproductperbos "
					+ "WHERE boscode LIKE:boscode AND membershiptype LIKE:membershiptype AND servicestatus LIKE:servicestatus",
					param, ProductPerBOSForm.class, 1);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return prodList;
	}

	@Override
	public String addProduct(ProductPerBOSForm form) {
		Tbproductperbos prod = new Tbproductperbos();
		TbproductperbosId prodid = new TbproductperbosId();
		param.put("boscode", form.getBoscode());
		param.put("productcode", form.getProductcode());
		try {

			prodid.setBoscode(form.getBoscode());
			prodid.setProductcode(form.getProductcode());
			prod.setProductname(form.getProductname());
//			String[] membershipclass = form.getMembershiptype().replaceAll("\\[", "").replaceAll("\\]","").replaceAll("\"", "").split(",");
//			String[] servicestatus = form.getServicestatus().replaceAll("\\[", "").replaceAll("\\]","").replaceAll("\"", "").split(",");
//			for (String mem : membershipclass) {
//				for (String serv : servicestatus) {
			Tbproductperbos origForm = new Tbproductperbos();
			origForm = (Tbproductperbos) dbService.executeUniqueHQLQuery(
					"FROM Tbproductperbos WHERE boscode=:boscode and productcode=:productcode", param);
			if (origForm != null) {
				prod.setAssignedby(origForm.getAssignedby());
				prod.setDateassigned(origForm.getDateassigned());
				prod.setDateupdated(new Date());
				prod.setUpdatedby(secservice.getUserName());
			} else {
				prod.setAssignedby(secservice.getUserName());
				prod.setDateassigned(new Date());
				prod.setDateupdated(null);
				prod.setUpdatedby(null);
			}
			prod.setMembershiptype(form.getMembershiptype().replaceAll("\\[", "").replaceAll("\\]", "")
					.replaceAll("\"", "").replaceAll(" \"", ""));
			prod.setServicestatus(form.getServicestatus().replaceAll("\\[", "").replaceAll("\\]", "")
					.replaceAll("\"", "").replaceAll(" \"", ""));
			prod.setId(prodid);
			prod.setIsactive(false);
			prod.setProductname(form.getProductname());
			prod.setMembershiptypedesc(form.getMembershiptypedesc().replaceAll("\\[", "").replaceAll("\\]", "")
					.replaceAll("\"", "").replaceAll(" \"", ""));
			prod.setServicestatusdesc(form.getServicestatusdesc().replaceAll("\\[", "").replaceAll("\\]", "")
					.replaceAll("\"", "").replaceAll(" \"", ""));
			if (dbService.saveOrUpdate(prod)) {
				System.out.println("Prodcut Saved for : " + prodid.getBoscode() + " " + prodid.getProductcode());
				AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.SAVE_LOAN_PRODUCT_PER_BOS),
						"Saved product per BOS by " + secservice.getUserName() + ": Service status : "
								+ prod.getServicestatus() + " Membership type: " + prod.getMembershiptype(),
						RuntimeAccess.getInstance().getRequest().getUserPrincipal().getName(), new Date(),
						AuditLogEvents.getEventModule(AuditLogEvents.SAVE_LOAN_PRODUCT_PER_BOS));
			} else {
				System.out.println("Error saving product for : " + prodid.getBoscode() + " " + prodid.getProductcode());
//					}
//				}
			}
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public String editProduct(ProductPerBOSForm form) {
		Tbproductperbos prod = new Tbproductperbos();
		TbproductperbosId prodid = new TbproductperbosId();
		try {
			prodid.setBoscode(form.getBoscode());
			prodid.setProductcode(form.getProductcode());
			prod.setId(prodid);
//			prod.setAssignedby(form.getAssignedby());
//			prod.setDateassigned(form.getDateassigned());
			prod.setDateupdated(new Date());
			prod.setIsactive(false);
//			prod.setMembershiptype(form.getMembershiptype());
			prod.setProductname(form.getProductname());
//			prod.setProducttype(form.getProducttype());
//			prod.setServicestatus(form.getServicestatus());
			prod.setUpdatedby(secservice.getUserName());
			dbService.saveOrUpdate(prod);
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public String deleteProduct(ProductPerBOSForm form) {
		param.put("boscode", form.getBoscode());
		param.put("productcode", form.getProductcode());
		param.put("membertype", form.getMembershiptype());
		param.put("servicestatus", form.getServicestatus());
		try {
			dbService.executeUpdate("DELETE FROM Tbproductperbos WHERE boscode =:boscode and productcode =:productcode "
					+ "and membershiptype =:membertype and servicestatus =:servicestatus", param);
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "failed";
	}
}
