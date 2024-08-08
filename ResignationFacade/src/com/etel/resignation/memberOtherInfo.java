package com.etel.resignation;

import java.util.Map;

import com.coopdb.data.Tbbranch;
import com.coopdb.data.TbbranchId;
import com.coopdb.data.Tbcodetable;
import com.coopdb.data.Tbmember;
import com.coopdb.data.Tbresign;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;

public class memberOtherInfo {
	private String resigningfrom;
	private String branch;
	
	private static DBService dbService = new DBServiceImpl();
	private static Map<String, Object> params = HQLUtil.getMap();
	
	public static memberOtherInfo getBranchandResigningFrom(String memberid) {
		memberOtherInfo member = new memberOtherInfo();
		try {
			if(memberid!=null){
				params.put("id", memberid);
				Tbresign s = (Tbresign)dbService.executeUniqueHQLQuery("FROM Tbresign WHERE membershipid=:id", params);
				if(s!=null){
					if(s.getBothcoopandcompany() && !s.getCooponly()){
						member.setResigningfrom("Co-op and Company");
					}
					if(!s.getBothcoopandcompany() && s.getCooponly()){
						member.setResigningfrom("Co-op");
					}
					Tbmember mem = (Tbmember)dbService.executeUniqueHQLQuery("FROM Tbmember WHERE membershipid=:id", params);
					if(mem!=null){
						System.out.println();
						System.out.println(mem.getBranch());
						params.put("br", mem.getBranch());
						TbbranchId branch = (TbbranchId)dbService.execSQLQueryTransformer("SELECT branchname FROM Tbbranch WHERE branchcode=:br", params, TbbranchId.class, 0);
						if(branch!=null){
							member.setBranch(branch.getBranchname());
						}
						return member;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public String getResigningfrom() {
		return resigningfrom;
	}
	public void setResigningfrom(String resigningfrom) {
		this.resigningfrom = resigningfrom;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
}
