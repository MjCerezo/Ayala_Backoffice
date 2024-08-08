package com.etel.policymodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbaudittrail;
import com.coopdb.data.Tbpolicygroup;
import com.coopdb.data.Tbpolicyitems;
import com.coopdb.data.Tbpolicymodel;
import com.coopdb.data.Tbpolicyoperandsperitem;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.scoremodel.forms.PolicyItemsForm;
import com.etel.util.SequenceGenerator;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;

/**
 * @author Kevin
 *
 */
public class PolicyModelServiceImpl implements PolicyModelService {

	private DBService dbService = new DBServiceImpl();
	private Map<String, Object> params = HQLUtil.getMap();
	
	@Override
	public String addModel(String modelname, String modeldesc) {
		// TODO Auto-generated method stub
		Tbpolicymodel model = new Tbpolicymodel();
		String flag = "failed";
		try {
			String tempModelNo = SequenceGenerator.generateModelNoSequence("POLICYMODEL");
			model.setModelno(tempModelNo);
			model.setModelname(modelname);
			model.setDescription(modeldesc);
			model.setLastitemseqno(0);
			model.setCreatedby(UserUtil.securityService.getUserName());
			model.setDatecreated(new Date());
			if (dbService.saveOrUpdate(model)) {
				flag = "success";
				Tbaudittrail recc = new Tbaudittrail();
				recc.setEventname("Add Policy Model");
				recc.setEventdescription("Add Model " + modelname + " in Policy Model with Model No " + tempModelNo);
				recc.setEventdatetime(new Date());
				recc.setUsername(UserUtil.securityService.getUserName());
				dbService.save(recc);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String addPolicyCharacteristic(String modelno, String fielddesc, String tbcode, String fieldname,
			String datatype, String multi, String groupname, String codename, String dbcode, String apprefno) {
		// TODO Auto-generated method stub
		Tbpolicyitems scoretb = new Tbpolicyitems();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("modelno", modelno);
		String flag = "failed";
		try {
			String policykey = "01";
			Tbpolicymodel p = (Tbpolicymodel) dbService.executeUniqueHQLQuery("FROM Tbpolicymodel WHERE modelno=:modelno", params);
			if(p != null){
				if(p.getLastitemseqno() != null){
					policykey = String.format("%02d", p.getLastitemseqno() + 1);
				}
				p.setLastitemseqno(p.getLastitemseqno() == null? 1 : (p.getLastitemseqno() + 1));
				dbService.saveOrUpdate(p);
			}
			scoretb.setModelno(modelno);
			scoretb.setPolicykey(policykey);
			scoretb.setTbcode(tbcode);
			scoretb.setFielddesc(fielddesc);
			scoretb.setFieldname(fieldname);
			scoretb.setDatatype(datatype);
			scoretb.setPolicykey(modelno + policykey + "01");
			scoretb.setMulti(multi);
			scoretb.setGroupname(groupname);
			scoretb.setCodename(codename);
			scoretb.setDbcode(dbcode);
			scoretb.setApprefno(apprefno);
			scoretb.setItemseqno(policykey);
			scoretb.setCreatedby(UserUtil.securityService.getUserName());
			scoretb.setDatecreated(new Date());
			if (dbService.save(scoretb)) {
				System.out.println("GROUP NAME ");
				flag = "success";
				params.put("gpname", groupname);
				Tbaudittrail recc = new Tbaudittrail();
				recc.setEventname("Add Policy Item");
				recc.setEventdescription("Add " + fielddesc + " in Policy Item with Model No " + modelno);
				recc.setEventdatetime(new Date());
				recc.setUsername(UserUtil.securityService.getUserName());
				dbService.save(recc);
				if (groupname.length() > 0) {
					List<Tbpolicygroup> groupCheck = (List<Tbpolicygroup>) dbService
							.executeListHQLQuery("FROM Tbpolicygroup WHERE groupname=:gpname", params);
					if (groupCheck.isEmpty()) {
						Tbpolicygroup createGroup = new Tbpolicygroup();
						createGroup.setGroupname(groupname);
						createGroup.setModelno(modelno);
						dbService.saveOrUpdate(createGroup);
						System.out.println("Create groupname: "+groupname+" -  successful!");
					} else {
						System.out.println("Groupname: "+groupname+" -  already exist!");
					}
				}
			} else {
				flag = "failed";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	@Override
	public String addPolicyCharacteristicDetail(String policykey, String oper1, String value1, String oper2,
			String value2, String remarks, String approvallevel, String field) {
		// TODO Auto-generated method stub
		Tbpolicyoperandsperitem pol = new Tbpolicyoperandsperitem();
		String flag = "failed";
		try {
			Tbpolicyitems csky = null;
			Map<String, Object> params = HQLUtil.getMap();
			params.put("policykey", policykey);
			csky = (Tbpolicyitems) dbService.executeUniqueHQLQuery("FROM Tbpolicyitems WHERE policykey=:policykey",
					params);
			if (csky == null) {
				flag = "failed";
				System.out.println(">>>>> POLICY KEY DOES NOT EXIST");
			} else {
				pol.setPolicykey(policykey);
				pol.setOperand1(oper1);
				pol.setOperand2(oper2);
				pol.setValue1(value1);
				pol.setValue2(value2);
				pol.setModelno(csky.getModelno());
				pol.setPolicydesc(remarks);
				pol.setApprovallevel(approvallevel);
				pol.setFielddesc(field);
				pol.setCreatedby(UserUtil.securityService.getUserName());
				pol.setDatecreated(new Date());
				if (dbService.saveOrUpdate(pol)) {
					flag = "success";
					Tbaudittrail recc = new Tbaudittrail();
					recc.setEventname("Add Policy Characteristic BIN");
					recc.setEventdescription("Add BIN in Policy Item of " + field + " with Model No "
							+ csky.getModelno());
					recc.setEventdatetime(new Date());
					recc.setUsername(UserUtil.securityService.getUserName());
					dbService.save(recc);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbpolicymodel> getModelList() {
		// TODO Auto-generated method stub
		List<Tbpolicymodel> list = new ArrayList<Tbpolicymodel>();
		try {
			list = (List<Tbpolicymodel>) dbService.executeListHQLQuery("FROM Tbpolicymodel", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PolicyItemsForm> getPolicyCharacteristic(String modelno) {
		// TODO Auto-generated method stub
		List<PolicyItemsForm> list = new ArrayList<PolicyItemsForm>();
		try{
			if(modelno != null){
				params.put("modelno", modelno);
				list = (List<PolicyItemsForm>) dbService.execSQLQueryTransformer("SELECT a.id, a.modelno, a.itemseqno, a.policykey, a.dbcode, a.tbcode, a.fielddesc, a.fieldname, "
						+ "a.datatype, a.multi, a.groupname, a.codename, a.apprefno, b.dbnametodisplay, c.tbnametodisplay, d.fieldnametodisplay "
						+ "FROM TBPOLICYITEMS a LEFT JOIN TBDATABASEPARAMS b ON a.dbcode=b.dbcode "
						+ "LEFT JOIN TBTABLEPARAMS c ON b.dbcode=c.dbcode AND a.tbcode=c.tbcode "
						+ "LEFT JOIN TBFIELDPARAMS d ON c.dbcode=d.dbcode AND c.tbcode=d.tbcode AND a.fieldname=d.fieldcode "
						+ "WHERE a.modelno=:modelno", params, PolicyItemsForm.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbpolicyoperandsperitem> getPolicyCharacteristicDetail(String scKey) {
		// TODO Auto-generated method stub
		List<Tbpolicyoperandsperitem> list = new ArrayList<Tbpolicyoperandsperitem>();
		params.put("ckey", scKey);
		try {
			list = (List<Tbpolicyoperandsperitem>) dbService
					.executeListHQLQuery("FROM Tbpolicyoperandsperitem WHERE policykey=:ckey", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String updatePolicyCharacteristic(Tbpolicyitems pol) {
		// TODO Auto-generated method stub
		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		try {
			if(pol.getId() != null){
				params.put("id", pol.getId());
				Tbpolicyitems i =(Tbpolicyitems)dbService.executeUniqueHQLQuery("FROM Tbpolicyitems WHERE id=:id", params);
				if(i != null){
					pol.setCreatedby(i.getCreatedby());
					pol.setDatecreated(i.getDatecreated());
				}
				pol.setUpdatedby(UserUtil.securityService.getUserName());
				pol.setDateupdated(new Date());
				if (dbService.saveOrUpdate(pol)) {
					flag = "success";
					Tbaudittrail recc = new Tbaudittrail();
					recc.setEventname("Update Policy Items");
					recc.setEventdescription(
							"Update " + pol.getFielddesc() + " in Policy Items with Model No " + pol.getModelno());
					recc.setEventdatetime(new Date());
					recc.setUsername(UserUtil.securityService.getUserName());
					dbService.save(recc);
					if (pol.getGroupname().length() > 0) {
						params.put("gpname", pol.getGroupname());
						List<Tbpolicygroup> groupCheck = (List<Tbpolicygroup>) dbService
								.executeListHQLQuery("FROM Tbpolicygroup WHERE groupname=:gpname", params);
						if (groupCheck == null || groupCheck.isEmpty()) {
							Tbpolicygroup createGroup = new Tbpolicygroup();
							createGroup.setGroupname(pol.getGroupname());
							createGroup.setModelno(pol.getModelno());
							dbService.saveOrUpdate(createGroup);
							System.out.println("Create group: "+pol.getGroupname()+" -  successful!");
						} else {
							System.out.println("Groupname: "+pol.getGroupname()+" -  already exist!");
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
	public String updatePolicyCharacteristicDetail(Tbpolicyoperandsperitem pol) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			if(pol.getId() != null){
				params.put("id", pol.getId());
				Tbpolicyoperandsperitem i =(Tbpolicyoperandsperitem)dbService.executeUniqueHQLQuery("FROM Tbpolicyoperandsperitem WHERE id=:id", params);
				if(i != null){
					pol.setCreatedby(i.getCreatedby());
					pol.setDatecreated(i.getDatecreated());
				}
				pol.setUpdatedby(UserUtil.securityService.getUserName());
				pol.setDateupdated(new Date());
				if (dbService.saveOrUpdate(pol)) {
					flag = "success";
					Tbaudittrail recc = new Tbaudittrail();
					recc.setEventname("Update Policy Items BIN");
					recc.setEventdescription("Update BIN in Policy Operands of " + pol.getFielddesc()
							+ " in Model No " + pol.getModelno());
					recc.setEventdatetime(new Date());
					recc.setUsername(UserUtil.securityService.getUserName());
					dbService.save(recc);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String deleteModel(String modelno) {
		// TODO Auto-generated method stub
		String flag = "failed";
		params.put("modelno", modelno);
		try {
			int res = dbService.executeUpdate("DELETE FROM TBPOLICYMODEL WHERE modelno=:modelno", params);
			if(res > 0){
				flag = "success";
				
				Tbaudittrail recc = new Tbaudittrail();
				recc.setEventname("Delete Score Model");
				recc.setEventdescription("Delete Score Model - with Model No: " + modelno);
				recc.setEventdatetime(new Date());
				dbService.save(recc);
				
				dbService.executeUpdate("DELETE FROM TBPOLICYITEMS WHERE modelno=:modelno", params);
				dbService.executeUpdate("DELETE FROM TBPOLICYOPERANDSPERITEM WHERE modelno=:modelno", params);
				dbService.executeUpdate("DELETE FROM TBPOLICYGROUP WHERE modelno=:modelno", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String deleteCharac(String policykey) {
		// TODO Auto-generated method stub
		String flag = "failed";
		params.put("policykey", policykey);
		Tbpolicyitems charac = new Tbpolicyitems();
		List<Tbpolicyoperandsperitem> characDet = new ArrayList<Tbpolicyoperandsperitem>();
		try {
			charac = (Tbpolicyitems) dbService.executeUniqueHQLQuery("FROM Tbpolicyitems WHERE policykey=:policykey",
					params);
			characDet = (List<Tbpolicyoperandsperitem>) dbService
					.executeListHQLQuery("FROM Tbpolicyoperandsperitem WHERE policykey=:policykey", params);
			String d1 = charac.getFielddesc();
			String d2 = charac.getModelno();
			if (dbService.delete(charac)) {
				System.out.println("Policy Item deleted");
				flag = "success";
				Tbaudittrail recc = new Tbaudittrail();
				recc.setEventname("Delete Policy Item");
				recc.setEventdescription("Delete Policy Item " + d1 + " with Model No " + d2);
				recc.setEventdatetime(new Date());
				recc.setUsername(UserUtil.securityService.getUserName());
				dbService.save(recc);
				for (Tbpolicyoperandsperitem ccd : characDet) {
					if (characDet == null) {
						System.out.println("Policy has no BIN");
						flag = "success";
					} else {
						if (dbService.delete(ccd)) {
							flag = "success";
						} else {
							System.out.println(">>>>>>Error in deletion of Policy Item");
							flag = "failed";
						}
					}
				}
			} else {
				System.out.println("Policy Item deletion failed");
				flag = "failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String deleteCharacDet(int id) {
		// TODO Auto-generated method stub
		String flag = "failed";
		Tbpolicyoperandsperitem characDet = new Tbpolicyoperandsperitem();
		params.put("id", id);
		try {
			characDet = (Tbpolicyoperandsperitem) dbService
					.executeUniqueHQLQuery("FROM Tbpolicyoperandsperitem WHERE id=:id", params);
			String d1 = characDet.getPolicykey();
			String d2 = characDet.getModelno();
			if (dbService.delete(characDet)) {
				flag = "success";
				Tbaudittrail recc = new Tbaudittrail();
				recc.setEventname("Delete Score Item BIN");
				recc.setEventdescription("Delete Policy Operand Item BIN in " + d1 + " with Model No " + d2);
				recc.setEventdatetime(new Date());
				recc.setUsername(UserUtil.securityService.getUserName());
				dbService.save(recc);
				System.out.println("Score Policy per item deleted");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String duplicateModel(String modelname, String modeldesc, String dupmodelno) {
		// TODO Auto-generated method stub
		String flag = "failed";
		params.put("model", dupmodelno);
		String username = UserUtil.securityService.getUserName();
		try {
			Tbpolicymodel pmod = (Tbpolicymodel)dbService
					.executeUniqueHQLQuery("FROM Tbpolicymodel WHERE modelno=:model", params);
			if(pmod != null){
				pmod.setModelname(modelname);
				String tempModelNo = SequenceGenerator.generateModelNoSequence("POLICYMODEL");
				pmod.setModelno(tempModelNo);
				pmod.setDescription(modeldesc);
				pmod.setCreatedby(username);
				pmod.setDatecreated(new Date());
				pmod.setUpdatedby(null);
				pmod.setDateupdated(null);
				if (dbService.save(pmod)) {
					Tbaudittrail recc = new Tbaudittrail();
					recc.setEventname("Duplicate Credit Model");
					recc.setEventdescription("Duplicate Policy Model No " + dupmodelno);
					recc.setEventdatetime(new Date());
					recc.setUsername(username);
					dbService.save(recc);
					
					//Policy Group
					List<Tbpolicygroup> policygrp = (List<Tbpolicygroup>) dbService.executeListHQLQuery("FROM Tbpolicygroup WHERE modelno=:model", params);
					if(policygrp != null){
						for (Tbpolicygroup dupPolicyGrp : policygrp) {
							dupPolicyGrp.setId(null);
							dupPolicyGrp.setModelno(tempModelNo);
							dupPolicyGrp.setCreatedby(username);
							dupPolicyGrp.setDatecreated(new Date());
							dupPolicyGrp.setUpdatedby(null);
							dupPolicyGrp.setDateupdated(null);
							dbService.save(dupPolicyGrp);
						}

					}
					
					List<Tbpolicyitems> policyitems = (List<Tbpolicyitems>) dbService.executeListHQLQuery("FROM Tbpolicyitems WHERE modelno=:model", params);
					if(policyitems != null){
						for (Tbpolicyitems dupPolicyItems : policyitems) {
							params.put("policykey", dupPolicyItems.getPolicykey());
							dupPolicyItems.setModelno(tempModelNo);
							dupPolicyItems.setPolicykey(tempModelNo.concat(dupPolicyItems.getItemseqno()).concat("01"));
							dupPolicyItems.setDatecreated(new Date());
							dupPolicyItems.setUpdatedby(null);
							dupPolicyItems.setDateupdated(null);
							if(dbService.save(dupPolicyItems)){
								
								List<Tbpolicyoperandsperitem> policyitemoperand = (List<Tbpolicyoperandsperitem>) dbService.executeListHQLQuery("FROM Tbpolicyoperandsperitem WHERE modelno=:model AND policykey=:policykey", params);
								if(policyitemoperand != null){
									for (Tbpolicyoperandsperitem dupPolicyItemOperand : policyitemoperand) {
										dupPolicyItemOperand.setId(null);
										dupPolicyItemOperand.setModelno(dupPolicyItems.getModelno());
										dupPolicyItemOperand.setPolicykey(dupPolicyItems.getPolicykey());
										dupPolicyItemOperand.setDatecreated(new Date());
										dupPolicyItemOperand.setUpdatedby(null);
										dupPolicyItemOperand.setDateupdated(null);
										dbService.save(dupPolicyItemOperand);
									}//end of Tbpolicyoperandsperitem forloop
									
								}
								
							}
						}//end of Tbpolicyitems forloop
					}
					
					flag = "success";
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String updatePolicyModel(Tbpolicymodel model) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			if(model.getModelno() != null){
				params.put("modelno", model.getModelno());
				Tbpolicymodel m =(Tbpolicymodel) dbService.executeUniqueHQLQuery("FROM Tbpolicymodel WHERE modelno=:modelno", params);
				if(m != null){
					model.setCreatedby(m.getCreatedby());
					model.setDatecreated(m.getDatecreated());
					model.setLastitemseqno(m.getLastitemseqno());
				}
			}
			model.setUpdatedby(UserUtil.securityService.getUserName());
			model.setDateupdated(new Date());
			if (dbService.saveOrUpdate(model)) {
				flag = "success";
				Tbaudittrail recc = new Tbaudittrail();
				recc.setEventname("Update Score Model");
				recc.setEventdescription("Update Score Model No " + model.getModelno());
				recc.setEventdatetime(new Date());
				recc.setUsername(UserUtil.securityService.getUserName());
				dbService.save(recc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbpolicygroup> getGroupList(String modelno) {
		// TODO Auto-generated method stub
		List<Tbpolicygroup> list = new ArrayList<Tbpolicygroup>();
		try {
			params.put("model", modelno);
			list = (List<Tbpolicygroup>) dbService.executeListHQLQuery("FROM Tbpolicygroup WHERE modelno=:model", params);
			if (list.isEmpty()) {
				System.out.println("Policy Group is Empty");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getGroupListByName(String modelno) {
		// TODO Auto-generated method stub
		params.put("mdl", modelno);
		List<String> list = new ArrayList<String>();
		try {
			list = (List<String>) dbService.execSQLQueryTransformer("SELECT DISTINCT groupname FROM Tbpolicygroup WHERE modelno=:mdl", params, null,1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbpolicyoperandsperitem> getBINByName(String groupname) {
		// TODO Auto-generated method stub
		params.put("grp", groupname);
		List<Tbpolicyitems> characList = new ArrayList<Tbpolicyitems>();
		List<Tbpolicyoperandsperitem> binList = new ArrayList<Tbpolicyoperandsperitem>();
		try {
			characList = (List<Tbpolicyitems>) dbService
					.executeListHQLQuery("FROM Tbpolicyitems WHERE groupname=:grp", params);
			if(characList != null){
				for (Tbpolicyitems charac : characList) {
					params.put("policykey", charac.getPolicykey());
					List<Tbpolicyoperandsperitem> bnlist = (List<Tbpolicyoperandsperitem>) dbService
							.executeListHQLQuery("FROM Tbpolicyoperandsperitem WHERE policykey=:policykey", params);
					binList.addAll(bnlist);
				}
			}

		} catch (Exception e) {

		}
		return binList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String saveCombinationKey(Tbpolicygroup group) {
		// TODO Auto-generated method stub
		params.put("gname", group.getGroupname());
		String flag = "failed";
		List<Tbpolicygroup> groupList = new ArrayList<Tbpolicygroup>();
		try {
			groupList = (List<Tbpolicygroup>) dbService.executeListHQLQuery("FROM Tbpolicygroup WHERE groupname=:gname",
					params);
			if(groupList != null){
				Tbpolicygroup upd = groupList.get(0);
				if (upd.getGroupkey() == null || upd.getGroupkey().equals(null) || upd.getGroupkey() == ""
						|| upd.getGroupkey().equals("") || upd.getGroupkey().equals(" ") || upd.getGroupkey() == " ") {
					upd.setKeydesc(group.getKeydesc());
					upd.setGroupkey(group.getGroupkey());
					upd.setApprovallevel(group.getApprovallevel());
					upd.setPolicydesc(group.getPolicydesc());
					upd.setModelno(group.getModelno());
					upd.setCreatedby(UserUtil.securityService.getUserName());
					upd.setDatecreated(new Date());
					if (dbService.saveOrUpdate(upd)) {
						flag = "success";
						Tbaudittrail recc = new Tbaudittrail();
						recc.setEventname("Save Policy Combination Key");
						recc.setEventdescription("Save Policy Combination Key for group " + group.getGroupname()
								+ " with Model No " + group.getModelno());
						recc.setEventdatetime(new Date());
						recc.setUsername(UserUtil.securityService.getUserName());
						dbService.save(recc);
					}
				} else {
					params.put("grpkey", group.getGroupkey());
					List<Tbpolicygroup> check = (List<Tbpolicygroup>) dbService
							.executeListHQLQuery("FROM Tbpolicygroup WHERE groupkey=:grpkey", params);
					if (!check.isEmpty()) {
						flag = "exist";
					} else {
						if (dbService.save(group)) {
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
	public String updateGroup(Tbpolicygroup group) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			if(group.getId() != null){
				params.put("id", group.getId());
				Tbpolicygroup g =(Tbpolicygroup)dbService.executeUniqueHQLQuery("FROM Tbpolicygroup WHERE id=:id", params);
				if(g != null){
					group.setCreatedby(g.getCreatedby());
					group.setDatecreated(g.getDatecreated());
				}
				group.setUpdatedby(UserUtil.securityService.getUserName());
				group.setDateupdated(new Date());
				if (dbService.saveOrUpdate(group)) {
					flag = "success";
					Tbaudittrail recc = new Tbaudittrail();
					recc.setEventname("Update Policy Combination Key");
					recc.setEventdescription("Update Policy Combination Key for group " + group.getGroupname()
							+ " with Model No " + group.getModelno());
					recc.setEventdatetime(new Date());
					recc.setUsername(UserUtil.securityService.getUserName());
					dbService.save(recc);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String deleteGroupKey(Tbpolicygroup group) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			if (dbService.delete(group)) {
				flag = "success";
				Tbaudittrail recc = new Tbaudittrail();
				recc.setEventname("Delete Policy Combination Key");
				recc.setEventdescription("Delete Policy Combination Key for group " + group.getGroupname()
						+ " with Model No " + group.getModelno());
				recc.setEventdatetime(new Date());
				recc.setUsername(UserUtil.securityService.getUserName());
				dbService.save(recc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}
