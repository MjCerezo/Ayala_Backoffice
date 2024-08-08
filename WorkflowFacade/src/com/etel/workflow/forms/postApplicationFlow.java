package com.etel.workflow.forms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbmember;
import com.coopdb.data.Tbmembershipapp;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;


public class postApplicationFlow {
	private DBService dbService = new DBServiceImpl();
	private Map<String, Object> param = HQLUtil.getMap();
	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	public List<String> fromApplicationToMembership(String membershipappid, String stage, String remarks){
		List<String> list = new ArrayList<String>();
		try {
			param.put("membershipappid", membershipappid);
			if(membershipappid!=null){
				Tbmembershipapp app = (Tbmembershipapp)dbService.executeUniqueHQLQuery("FROM Tbmembershipapp WHERE membershipappid=:membershipappid", param);
				Tbmember mem = (Tbmember)dbService.executeUniqueHQLQuery("FROM Tbmember WHERE membershipappid=:membershipappid", param);
				if(mem!=null && app!=null){
					list.add("application found and matched");
					if(stage.equalsIgnoreCase("Payment")){
						list.add("payment stage");
						mem.setCashier(app.getCashier());
						mem.setPaymentapprovaldate(app.getPaymentapprovaldate());
						mem.setMembershipstatusdate(new Date());
						if(dbService.saveOrUpdate(mem)){
							list.add("post application updated");
						} else {
							list.add("problem updating post application");
						}
					}
					else if(stage.equalsIgnoreCase("Recommendation")){
						list.add("recommendation stage");
						mem.setRecommendationdate(app.getRecommendationdate());
						mem.setRecommendedby(app.getRecommendedby());
						mem.setMembershipstatusdate(new Date());
						if(dbService.saveOrUpdate(mem)){
							list.add("post application updated");
						} else {
							list.add("problem updating post application");
						}
					}
					else if(stage.equalsIgnoreCase("Board Approval")){
						list.add("board approval stage");
						mem.setBoardapprover(app.getBoardapprover());
						mem.setBoardappstatusdate(app.getBoardappstatusdate());
						mem.setBoardapproverremarks(app.getBoardapproverremarks());
						mem.setBoardresno(app.getBoardresno());
						mem.setMembershipstatusdate(new Date());
						if(dbService.saveOrUpdate(mem)){
							list.add("post application updated");
						} else {
							list.add("problem updating post application");
						}
					} else {
						list.add("stage invalid");
					}
				} else if (app!=null && mem == null){
					list.add("application found but not yet member");
				} else {
					list.add("no application found");
				}
			}
		} catch (Exception x) {
			x.printStackTrace();
		}
		return list;
	}

}
