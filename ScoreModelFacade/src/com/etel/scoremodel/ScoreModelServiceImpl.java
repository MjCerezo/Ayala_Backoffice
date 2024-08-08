package com.etel.scoremodel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbaudittrail;
import com.coopdb.data.Tbdatabaseparams;
import com.coopdb.data.Tbscorecriteria;
import com.coopdb.data.Tbscoreitems;
import com.coopdb.data.Tbscorematrixpermodel;
import com.coopdb.data.Tbscoremodel;
import com.coopdb.data.Tbscoreoperandsperitem;
import com.coopdb.data.Tbscoresubcriteria;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.scoremodel.forms.ScoreItemsForm;
import com.etel.util.SequenceGenerator;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;

/**
 * @author Kevin
 *
 */
public class ScoreModelServiceImpl implements ScoreModelService {

	private DBService dbService = new DBServiceImpl();
	private Map<String, Object> params = HQLUtil.getMap();

	@Override
	public String addModel(String modelname, String modeldesc, BigDecimal passingscore) {
		// TODO Auto-generated method stub
		Tbscoremodel model = new Tbscoremodel();
		String flag = "failed";
		try {
			String tempModelNo = SequenceGenerator.generateModelNoSequence("SCOREMODEL");
			model.setModelno(tempModelNo);
			model.setModelname(modelname);
			model.setDescription(modeldesc);
			model.setPassingscore(passingscore);
			model.setLastcriteriaseqno(0);
			model.setCreatedby(UserUtil.securityService.getUserName());
			model.setDatecreated(new Date());
			if (dbService.saveOrUpdate(model)) {
				flag = "success";
				Tbaudittrail recc = new Tbaudittrail();
				recc.setEventname("Add Score Model");
				recc.setEventdescription("Add Model " + modelname + " in Score Model with Model No " + tempModelNo);
				recc.setEventdatetime(new Date());
				recc.setUsername(UserUtil.securityService.getUserName());
				dbService.save(recc);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}


	@Override
	public String addScoreCard(String modelno, String criteriaid, String subcriteriaid, String fielddesc, String tbcode, String fieldname, String datatype,
			BigDecimal weights, String codename, String dbcode, String apprefno) {
		// TODO Auto-generated method stub
		Tbscoreitems scoretb = new Tbscoreitems();
		params.put("modelno", modelno);
		String flag = "failed";
		try {
			params.put("modelno", modelno);
			params.put("criteriaid", criteriaid);
			params.put("subcriteriaid", subcriteriaid);
			String scorekey = "01";
			Tbscoresubcriteria s = (Tbscoresubcriteria) dbService.executeUniqueHQLQuery("FROM Tbscoresubcriteria WHERE modelno=:modelno AND criteriaid=:criteriaid AND subcriteriaid=:subcriteriaid", params);
			if(s != null){
				if(s.getLastitemseqno() != null){
					scorekey = String.format("%02d", s.getLastitemseqno() + 1);
				}
				s.setLastitemseqno(s.getLastitemseqno() == null? 1 : (s.getLastitemseqno() + 1));
				dbService.saveOrUpdate(s);
			}
			scoretb.setCriteriaid(criteriaid);
			scoretb.setSubcriteriaid(subcriteriaid);
			scoretb.setItemseqno(scorekey);
			scoretb.setScorekey(subcriteriaid.concat(scorekey));
			scoretb.setModelno(modelno);
			scoretb.setTbcode(tbcode);
			scoretb.setFielddesc(fielddesc);
			scoretb.setDatatype(datatype);
			scoretb.setFieldname(fieldname);
			scoretb.setWeights(weights);
			scoretb.setCodename(codename);
			scoretb.setDbcode(dbcode);
			scoretb.setApprefno(apprefno);
			scoretb.setCreatedby(UserUtil.securityService.getUserName());
			scoretb.setDatecreated(new Date());
			if (dbService.saveOrUpdate(scoretb)) {
				Tbaudittrail recc = new Tbaudittrail();
				recc.setEventname("Add Score Item");
				recc.setEventdescription("Add " + fieldname + " in Score Items with Score Key No " + scoretb.getScorekey());
				recc.setEventdatetime(new Date());
				recc.setUsername(UserUtil.securityService.getUserName());
				dbService.save(recc);
				flag = "success";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}


	@Override
	public String addScoreCardVal(String scorekey, String oper1, String value1, String oper2, String value2,
			BigDecimal score) {
		// TODO Auto-generated method stub
		Tbscoreoperandsperitem scoreVal = new Tbscoreoperandsperitem();
		DBService dbServ = new DBServiceImpl();
		String flag = "failed";
		try {
			Tbscoreitems csky = null;
			Map<String, Object> params = HQLUtil.getMap();
			params.put("scorekey", scorekey);
			System.out.println("Key : " + scorekey);
			csky = (Tbscoreitems) dbServ.executeUniqueHQLQuery("FROM Tbscoreitems WHERE scorekey=:scorekey",
					params);
			if (csky == null) {
				flag = "failed";
				System.out.println(">>>>> SCOREKEY DOES NOT EXIST");
			} else {
				scoreVal.setScorekey(scorekey);
				scoreVal.setOperand1(oper1);
				scoreVal.setOperand2(oper2);
				scoreVal.setValue1(value1);
				scoreVal.setValue2(value2);
				scoreVal.setScore(score);
				scoreVal.setModelno(csky.getModelno());
				scoreVal.setCreatedby(UserUtil.securityService.getUserName());
				scoreVal.setDatecreated(new Date());
				if (dbService.saveOrUpdate(scoreVal)) {
					flag = "success";
					Tbaudittrail recc = new Tbaudittrail();
					recc.setEventname("Add Credit Characteristic BIN");
					recc.setEventdescription("Add BIN in Score items  of " + csky.getFielddesc()
							+ " with Model No " + csky.getModelno());
					recc.setEventdatetime(new Date());
					recc.setUsername(UserUtil.securityService.getUserName());
					dbServ.save(recc);
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = "failed";
		}
		return flag;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Tbscoremodel> getModelList() {
		// TODO Auto-generated method stub
		List<Tbscoremodel> list = new ArrayList<Tbscoremodel>();
		try {
			list = (List<Tbscoremodel>) dbService.executeListHQLQuery("FROM Tbscoremodel", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<ScoreItemsForm> getScorecard(String modelno, String criteriaid, String subcriteriaid) {
		// TODO Auto-generated method stub
		List<ScoreItemsForm> list = new ArrayList<ScoreItemsForm>();
		try{
			if(modelno != null && criteriaid != null && subcriteriaid != null){
				params.put("modelno", modelno);
				params.put("criteriaid", criteriaid);
				params.put("subcriteriaid", subcriteriaid);
				list = (List<ScoreItemsForm>) dbService.execSQLQueryTransformer("SELECT a.id, a.modelno, a.criteriaid, a.subcriteriaid, a.itemseqno, a.scorekey, a.dbcode, a.tbcode, a.fielddesc, a.fieldname, "
						+ "a.datatype, a.weights, a.codename, a.apprefno, b.dbnametodisplay, c.tbnametodisplay, d.fieldnametodisplay "
						+ "FROM TBSCOREITEMS a LEFT JOIN TBDATABASEPARAMS b ON a.dbcode=b.dbcode "
						+ "LEFT JOIN TBTABLEPARAMS c ON b.dbcode=c.dbcode AND a.tbcode=c.tbcode "
						+ "LEFT JOIN TBFIELDPARAMS d ON c.dbcode=d.dbcode AND c.tbcode=d.tbcode AND a.fieldname=d.fieldcode "
						+ "WHERE a.modelno=:modelno AND a.criteriaid=:criteriaid AND a.subcriteriaid=:subcriteriaid", params, ScoreItemsForm.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Tbscoreoperandsperitem> getScorecardScore(String scorekey) {
		// TODO Auto-generated method stub
		List<Tbscoreoperandsperitem> list = new ArrayList<Tbscoreoperandsperitem>();
		try {
			if (scorekey != null) {
				params.put("scorekey", scorekey);
				list = (List<Tbscoreoperandsperitem>) dbService
						.executeListHQLQuery("FROM Tbscoreoperandsperitem WHERE scorekey=:scorekey", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	@Override
	public String updateScoreCardVal(Tbscoreoperandsperitem score) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			if(score.getId() != null){
				params.put("id", score.getId());
				Tbscoreoperandsperitem scoretb =(Tbscoreoperandsperitem)dbService.executeUniqueHQLQuery("FROM Tbscoreoperandsperitem WHERE id=:id", params);
				if(scoretb != null){
					score.setCreatedby(scoretb.getCreatedby());
					score.setDatecreated(scoretb.getDatecreated());
				}
			}
			score.setUpdatedby(UserUtil.securityService.getUserName());
			score.setDateupdated(new Date());
			if (dbService.saveOrUpdate(score)) {
				flag = "success";
				Tbaudittrail recc = new Tbaudittrail();
				recc.setEventname("Update Credit Characteristic BIN");
				recc.setEventdescription("Update BIN in Score operand per item of " + score.getScorekey()
						+ " in Model No " + score.getModelno());
				recc.setEventdatetime(new Date());
				recc.setUsername(UserUtil.securityService.getUserName());
				dbService.save(recc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}


	@Override
	public String updateScoreCard(Tbscoreitems tbscore) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			if(tbscore.getId() != null){
				params.put("id", tbscore.getId());
				Tbscoreitems scoretb =(Tbscoreitems)dbService.executeUniqueHQLQuery("FROM Tbscoreitems WHERE id=:id", params);
				if(scoretb != null){
					scoretb.setTbcode(tbscore.getTbcode());
					scoretb.setFielddesc(tbscore.getFielddesc());
					scoretb.setDatatype(tbscore.getDatatype());
					scoretb.setFieldname(tbscore.getFieldname());
					scoretb.setWeights(tbscore.getWeights());
					scoretb.setCodename(tbscore.getCodename());
					scoretb.setDbcode(tbscore.getDbcode());
					scoretb.setApprefno(tbscore.getApprefno());
					scoretb.setUpdatedby(UserUtil.securityService.getUserName());
					scoretb.setDateupdated(new Date());
					if (dbService.saveOrUpdate(scoretb)) {
						flag = "success";
						Tbaudittrail recc = new Tbaudittrail();
						recc.setEventname("Update Policy Characteristic");
						recc.setEventdescription("Update " + tbscore.getFielddesc() + " in Policy Items with Model No "
								+ tbscore.getModelno());
						recc.setEventdatetime(new Date());
						recc.setUsername(UserUtil.securityService.getUserName());
						dbService.save(recc);
					}
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
			int res = dbService.executeUpdate("DELETE FROM TBSCOREMODEL WHERE modelno=:modelno", params);
			if(res > 0){
				flag = "success";
				Tbaudittrail recc = new Tbaudittrail();
				recc.setEventname("Delete Score Model");
				recc.setEventdescription("Delete Score Model - with Model No: " + modelno);
				recc.setEventdatetime(new Date());
				dbService.save(recc);
				
				dbService.executeUpdate("DELETE FROM TBSCOREMATRIXPERMODEL WHERE modelno=:modelno", params);
				dbService.executeUpdate("DELETE FROM TBSCORECRITERIA WHERE modelno=:modelno", params);
				dbService.executeUpdate("DELETE FROM TBSCORESUBCRITERIA WHERE modelno=:modelno", params);
				dbService.executeUpdate("DELETE FROM TBSCOREITEMS WHERE modelno=:modelno", params);
				dbService.executeUpdate("DELETE FROM TBSCOREOPERANDSPERITEM WHERE modelno=:modelno", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}


	@SuppressWarnings("unchecked")
	@Override
	public String deleteCharac(String scorekey) {
		// TODO Auto-generated method stub
		String flag = "failed";
		params.put("scorekey", scorekey);
		Tbscoreitems charac = new Tbscoreitems();
		List<Tbscoreoperandsperitem> characDet = new ArrayList<Tbscoreoperandsperitem>();
		try {
			charac = (Tbscoreitems) dbService.executeUniqueHQLQuery("FROM Tbscoreitems WHERE scorekey=:scorekey",
					params);
			characDet = (List<Tbscoreoperandsperitem>) dbService
					.executeListHQLQuery("FROM Tbscoreoperandsperitem WHERE scorekey=:scorekey", params);
			String d1 = charac.getFielddesc();
			String d2 = charac.getModelno();
			if (dbService.delete(charac)) {
				System.out.println("Score Item deleted");
				flag = "success";
				Tbaudittrail recc = new Tbaudittrail();
				recc.setEventname("Delete Score Item");
				recc.setEventdescription("Delete Score Item " + d1 + " with Model No " + d2);
				recc.setEventdatetime(new Date());
				recc.setUsername(UserUtil.securityService.getUserName());
				dbService.save(recc);
				for (Tbscoreoperandsperitem ccd : characDet) {
					if (characDet == null) {
						System.out.println("Score has no BIN");
						flag = "success";
					} else {
						if (dbService.delete(ccd)) {
							flag = "success";
						} else {
							System.out.println(">>>>>>Error in deletion of Score Item");
							flag = "failed";
						}
					}
				}
			} else {
				System.out.println("Score Item deletion failed");
				flag = "failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}


	@Override
	public String deleteCharacDet(Integer id) {
		// TODO Auto-generated method stub
		String flag = "failed";
		Tbscoreoperandsperitem characDet = new Tbscoreoperandsperitem();
		params.put("id", id);
		try {
			characDet = (Tbscoreoperandsperitem) dbService
					.executeUniqueHQLQuery("FROM Tbscoreoperandsperitem WHERE id=:id", params);
			String d1 = characDet.getScorekey();
			String d2 = characDet.getModelno();
			if (dbService.delete(characDet)) {
				flag = "success";
				Tbaudittrail recc = new Tbaudittrail();
				recc.setEventname("Delete Score Item BIN");
				recc.setEventdescription("Delete Score Operand Item BIN in " + d1 + " with Model No " + d2);
				recc.setEventdatetime(new Date());
				recc.setUsername(UserUtil.securityService.getUserName());
				dbService.save(recc);
				System.out.println("Score Operand per item deleted");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}


	@SuppressWarnings("unchecked")
	@Override
	public String duplicateModel(String modelname, String modeldesc, BigDecimal passingscore, String dupmodelno) {
		// TODO Auto-generated method stub
		String flag = "failed";
		params.put("model", dupmodelno);
		String username = UserUtil.securityService.getUserName();
		try {
			Tbscoremodel cmod = (Tbscoremodel)dbService
					.executeUniqueHQLQuery("FROM Tbscoremodel WHERE modelno=:model", params);
			if(cmod != null){
				cmod.setModelname(modelname);
				String tempModelNo = SequenceGenerator.generateModelNoSequence("SCOREMODEL");
				cmod.setModelno(tempModelNo);
				cmod.setPassingscore(passingscore);
				cmod.setDescription(modeldesc);
				cmod.setCreatedby(username);
				cmod.setDatecreated(new Date());
				cmod.setUpdatedby(null);
				cmod.setDateupdated(null);
				if (dbService.save(cmod)) {
					Tbaudittrail recc = new Tbaudittrail();
					recc.setEventname("Duplicate Credit Model");
					recc.setEventdescription("Duplicate Score Model No " + dupmodelno);
					recc.setEventdatetime(new Date());
					recc.setUsername(username);
					dbService.save(recc);
					
					//Score Result Matrix
					List<Tbscorematrixpermodel> scorematrix = (List<Tbscorematrixpermodel>) dbService.executeListHQLQuery("FROM Tbscorematrixpermodel WHERE modelno=:model", params);
					if(scorematrix != null){
						for (Tbscorematrixpermodel dupScoreMatrix : scorematrix) {
							dupScoreMatrix.setId(null);
							dupScoreMatrix.setModelno(tempModelNo);
							dupScoreMatrix.setCreatedby(username);
							dupScoreMatrix.setDatecreated(new Date());
							dupScoreMatrix.setUpdatedby(null);
							dupScoreMatrix.setDateupdated(null);
							dbService.save(dupScoreMatrix);
						}

					}
					
					List<Tbscorecriteria> criteria = (List<Tbscorecriteria>) dbService.executeListHQLQuery("FROM Tbscorecriteria WHERE modelno=:model", params);
					if(criteria != null){
						for (Tbscorecriteria dupCriteria : criteria) {
							params.put("criteriaid", dupCriteria.getCriteriaid());
							dupCriteria.setModelno(tempModelNo);
							dupCriteria.setCriteriaid(tempModelNo.concat(dupCriteria.getCriteriaseqno()));
							dupCriteria.setCreatedby(username);
							dupCriteria.setDatecreated(new Date());
							dupCriteria.setUpdatedby(null);
							dupCriteria.setDateupdated(null);
							if(dbService.save(dupCriteria)){
								
								List<Tbscoresubcriteria> subcriteria = (List<Tbscoresubcriteria>) dbService.executeListHQLQuery("FROM Tbscoresubcriteria WHERE modelno=:model AND criteriaid=:criteriaid", params);
								if(subcriteria != null){
									for (Tbscoresubcriteria dupSubCriteria : subcriteria) {
										params.put("subcriteriaid", dupSubCriteria.getSubcriteriaid());
										dupSubCriteria.setModelno(dupCriteria.getModelno());
										dupSubCriteria.setCriteriaid(dupCriteria.getCriteriaid());
										dupSubCriteria.setSubcriteriaid(dupCriteria.getCriteriaid().concat(dupSubCriteria.getSubcriteriaseqno()));
										dupSubCriteria.setDatecreated(new Date());
										dupSubCriteria.setUpdatedby(null);
										dupSubCriteria.setDateupdated(null);
										if(dbService.save(dupSubCriteria)){
											
											
											List<Tbscoreitems> scoreitems = (List<Tbscoreitems>) dbService.executeListHQLQuery("FROM Tbscoreitems WHERE modelno=:model AND criteriaid=:criteriaid AND subcriteriaid=:subcriteriaid", params);
											if(scoreitems != null){
												for (Tbscoreitems dupScoreItems : scoreitems) {
													params.put("scorekey", dupScoreItems.getScorekey());
													dupScoreItems.setModelno(dupSubCriteria.getModelno());
													dupScoreItems.setCriteriaid(dupSubCriteria.getCriteriaid());
													dupScoreItems.setSubcriteriaid(dupSubCriteria.getSubcriteriaid());
													dupScoreItems.setScorekey(dupSubCriteria.getSubcriteriaid().concat(dupScoreItems.getItemseqno()));
													dupScoreItems.setDatecreated(new Date());
													dupScoreItems.setUpdatedby(null);
													dupScoreItems.setDateupdated(null);
													if(dbService.save(dupScoreItems)){
														
														List<Tbscoreoperandsperitem> scoreitemoperand = (List<Tbscoreoperandsperitem>) dbService.executeListHQLQuery("FROM Tbscoreoperandsperitem WHERE modelno=:model AND scorekey=:scorekey", params);
														if(scoreitemoperand != null){
															for (Tbscoreoperandsperitem dupScoreItemOperand : scoreitemoperand) {
																dupScoreItemOperand.setId(null);
																dupScoreItemOperand.setModelno(dupScoreItems.getModelno());
																dupScoreItemOperand.setScorekey(dupScoreItems.getScorekey());
																dupScoreItemOperand.setDatecreated(new Date());
																dupScoreItemOperand.setUpdatedby(null);
																dupScoreItemOperand.setDateupdated(null);
																dbService.save(dupScoreItemOperand);
															}//end of Tbscoreoperandsperitem forloop
															
														}
														
													}
												}//end of Tbscoreitems forloop
											}
											
											
										}
									}//end of Tbscoresubcriteria forloop
									
								}
								
							}
						}//end of Tbscorecriteria forloop
						
						
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
	public String updateCreditModel(Tbscoremodel model) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			if(model.getModelno() != null){
				params.put("modelno", model.getModelno());
				Tbscoremodel m =(Tbscoremodel) dbService.executeUniqueHQLQuery("FROM Tbscoremodel WHERE modelno=:modelno", params);
				if(m != null){
					model.setCreatedby(m.getCreatedby());
					model.setDatecreated(m.getDatecreated());
					model.setLastcriteriaseqno(m.getLastcriteriaseqno());
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
	public List<String> codetableList() {
		// TODO Auto-generated method stub
		List<Object> objlist = new ArrayList<Object>();
		List<String> list = new ArrayList<String>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			objlist = (List<Object>) dbService
					.executeListHQLQuery("SELECT MAX(codename) as cdname FROM Codetable GROUP BY codename", params);
			for (Object obj : objlist) {
				list.add(String.valueOf(obj));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<String> getDBList() {
		// TODO Auto-generated method stub
		List<String> list = null;
		try {
			list = (List<String>) dbService
					.execSQLQueryTransformer("SELECT name FROM master.dbo.sysdatabases WHERE dbid > 6", null, null, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<String> getTableListByDB(String dbname) {
		// TODO Auto-generated method stub
		List<String> list = null;
		try {
			if (dbname != null) {
				list = (List<String>) dbService.executeListSQLQuery("SELECT TABLE_NAME FROM " + dbname
						+ ".INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE'", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<String> getColumnListByDBAndTable(String dbname, String tablename) {
		// TODO Auto-generated method stub
		List<String> list = null;
		try {
			if (dbname != null && tablename != null) {
				list = (List<String>) dbService.executeListSQLQuery("Select COLUMN_NAME From " + dbname
						+ ".INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='" + tablename + "'", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Tbdatabaseparams> getDatabaseParamsList() {
		// TODO Auto-generated method stub
		List<Tbdatabaseparams> list = null;
		try {
			
			list = (List<Tbdatabaseparams>) dbService.executeListHQLQuery("FROM Tbdatabaseparams", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<String> getCodetableByDBAndTable(String dbname, String codestablename) {
		// TODO Auto-generated method stub
		List<String> list = null;
		try {
			if (dbname != null && codestablename != null) {
				list = (List<String>) dbService.executeListSQLQuery(
						"SELECT DISTINCT(codename) FROM " + dbname + ".dbo." + codestablename + "", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	@SuppressWarnings("unchecked")
	@Override
	public String saveOrDeleteScoreCriteria(Tbscorecriteria criteria, String ident) {
		String flag = "failed";
		try {
			if(ident != null && ident.equals("delete")){
				params.put("modelno", criteria.getModelno());
				params.put("criteriaid", criteria.getCriteriaid());
				if(dbService.delete(criteria)){
					flag = "success";
					int res = dbService.executeUpdate("DELETE FROM TBSCORESUBCRITERIA WHERE criteriaid=:criteriaid", params);
					if(res > 0){
						
						List<Tbscoreitems> scoreitems = (List<Tbscoreitems>) dbService.executeListHQLQuery("FROM Tbscoreitems WHERE modelno=:modelno AND criteriaid=:criteriaid", params);
						if(scoreitems != null){
							for (Tbscoreitems dupScoreItems : scoreitems) {
								params.put("scorekey", dupScoreItems.getScorekey());
								if(dbService.delete(dupScoreItems)){
									List<Tbscoreoperandsperitem> scoreitemoperand = (List<Tbscoreoperandsperitem>) dbService.executeListHQLQuery("FROM Tbscoreoperandsperitem WHERE modelno=:modelno AND scorekey=:scorekey", params);
									if(scoreitemoperand != null){
										for (Tbscoreoperandsperitem dupScoreItemOperand : scoreitemoperand) {
											dbService.delete(dupScoreItemOperand);
										}//end of Tbscoreoperandsperitem forloop
									}
								}
							}//end of Tbscoresubcriteria forloop
						}
						
					}
					Tbaudittrail recc = new Tbaudittrail();
					recc.setEventname("Delete Criteria");
					recc.setEventdescription("Delete Criteria " + criteria.getCriterianame() + " with criteriaid : " + criteria.getCriteriaid());
					recc.setEventdatetime(new Date());
					recc.setUsername(UserUtil.securityService.getUserName());
					dbService.save(recc);
				}
				return flag;
			} else {
				if(criteria.getCriteriaid() == null){
					String criteriaid = "01";
					params.put("modelno", criteria.getModelno());
					Tbscoremodel m = (Tbscoremodel) dbService.executeUniqueHQLQuery("FROM Tbscoremodel WHERE modelno=:modelno", params);
					if(m != null){
						if(m.getLastcriteriaseqno() != null){
							criteriaid = String.format("%02d", m.getLastcriteriaseqno() + 1);
						}
						m.setLastcriteriaseqno(m.getLastcriteriaseqno() == null ? 1 : (m.getLastcriteriaseqno() + 1));
						dbService.saveOrUpdate(m);
					}
					
					
					criteria.setCriteriaid(criteria.getModelno().concat(criteriaid));
					criteria.setCriteriaseqno(criteriaid);
					criteria.setLastsubcriteriaseqno(0);
					criteria.setCreatedby(UserUtil.securityService.getUserName());
					criteria.setDatecreated(new Date());
					if(dbService.save(criteria)){
						flag = "success";
						Tbaudittrail recc = new Tbaudittrail();
						recc.setEventname("Add Criteria");
						recc.setEventdescription("Add Criteria " + criteria.getCriterianame() + " with criteriaid : " + criteria.getCriteriaid());
						recc.setEventdatetime(new Date());
						recc.setUsername(UserUtil.securityService.getUserName());
						dbService.save(recc);
					}
				}else{
					params.put("criteriaid", criteria.getCriteriaid());
					Tbscorecriteria c = (Tbscorecriteria) dbService.executeUniqueHQLQuery("FROM Tbscorecriteria WHERE criteriaid=:criteriaid", params);
					if(c != null){
						String crtname = c.getCriterianame();
						c.setCriterianame(criteria.getCriterianame());
						c.setTotalscore(criteria.getTotalscore());
						c.setUpdatedby(UserUtil.securityService.getUserName());
						c.setDateupdated(new Date());
						if(dbService.saveOrUpdate(c)){
							flag = "success";
							Tbaudittrail recc = new Tbaudittrail();
							recc.setEventname("Update Criteria");
							recc.setEventdescription("Update Criteria " +crtname+" to "+ criteria.getCriterianame() + " with criteriaid : " + criteria.getCriteriaid());
							recc.setEventdatetime(new Date());
							recc.setUsername(UserUtil.securityService.getUserName());
							dbService.save(recc);
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
	public List<Tbscorecriteria> getScoreCriteriaListByModelno(String modelno) {
		// TODO Auto-generated method stub
		List<Tbscorecriteria> list = null;
		try {
			if (modelno != null) {
				params.put("modelno", modelno);
				list = (List<Tbscorecriteria>) dbService.executeListHQLQuery("FROM Tbscorecriteria WHERE modelno=:modelno", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	@SuppressWarnings("unchecked")
	@Override
	public String saveOrDeleteScoreSubCriteria(Tbscoresubcriteria subcriteria, String ident) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			if(ident != null && ident.equals("delete")){
				params.put("modelno", subcriteria.getModelno());
				params.put("criteriaid", subcriteria.getCriteriaid());
				params.put("subcriteriaid", subcriteria.getSubcriteriaid());
				if(dbService.delete(subcriteria)){
					flag = "success";
					
					List<Tbscoreitems> scoreitems = (List<Tbscoreitems>) dbService.executeListHQLQuery("FROM Tbscoreitems WHERE modelno=:modelno AND criteriaid=:criteriaid AND subcriteriaid=:subcriteriaid", params);
					if(scoreitems != null){
						for (Tbscoreitems dupScoreItems : scoreitems) {
							params.put("scorekey", dupScoreItems.getScorekey());
							if(dbService.delete(dupScoreItems)){
								List<Tbscoreoperandsperitem> scoreitemoperand = (List<Tbscoreoperandsperitem>) dbService.executeListHQLQuery("FROM Tbscoreoperandsperitem WHERE modelno=:modelno AND scorekey=:scorekey", params);
								if(scoreitemoperand != null){
									for (Tbscoreoperandsperitem dupScoreItemOperand : scoreitemoperand) {
										dbService.delete(dupScoreItemOperand);
									}//end of Tbscoreoperandsperitem forloop
								}
							}
						}//end of Tbscoresubcriteria forloop
					}
					
					Tbaudittrail recc = new Tbaudittrail();
					recc.setEventname("Delete Criteria");
					recc.setEventdescription("Delete Sub-Criteria " + subcriteria.getSubcriterianame() + " with subcriteriaid : " + subcriteria.getSubcriteriaid());
					recc.setEventdatetime(new Date());
					recc.setUsername(UserUtil.securityService.getUserName());
					dbService.save(recc);
				}
				return flag;
			} else {
				params.put("modelno", subcriteria.getModelno());
				params.put("criteriaid", subcriteria.getCriteriaid());
				if(subcriteria.getSubcriteriaid() == null){
					String subcriteriaid = "01";
					Tbscorecriteria s = (Tbscorecriteria) dbService.executeUniqueHQLQuery("FROM Tbscorecriteria WHERE modelno=:modelno AND criteriaid=:criteriaid", params);
					if(s != null){
						if(s.getLastsubcriteriaseqno() != null){
							subcriteriaid = String.format("%02d", s.getLastsubcriteriaseqno() + 1);
						}
						s.setLastsubcriteriaseqno(s.getLastsubcriteriaseqno() == null? 1 : (s.getLastsubcriteriaseqno() + 1));
						dbService.saveOrUpdate(s);
					}
					subcriteria.setSubcriteriaid(subcriteria.getCriteriaid().concat(subcriteriaid));
					subcriteria.setSubcriteriaseqno(subcriteriaid);
					subcriteria.setLastitemseqno(0);
					subcriteria.setCreatedby(UserUtil.securityService.getUserName());
					subcriteria.setDatecreated(new Date());
					if(dbService.save(subcriteria)){
						flag = "success";
						Tbaudittrail recc = new Tbaudittrail();
						recc.setEventname("Add Criteria");
						recc.setEventdescription("Add Sub-Criteria " + subcriteria.getSubcriterianame() + " with subcriteriaid : " + subcriteria.getSubcriteriaid());
						recc.setEventdatetime(new Date());
						recc.setUsername(UserUtil.securityService.getUserName());
						dbService.save(recc);
					}
				}else{
					params.put("subcriteriaid", subcriteria.getSubcriteriaid());
					Tbscoresubcriteria c = (Tbscoresubcriteria) dbService.executeUniqueHQLQuery("FROM Tbscoresubcriteria WHERE modelno=:modelno AND criteriaid=:criteriaid AND subcriteriaid=:subcriteriaid", params);
					if(c != null){
						String subcrtname = c.getSubcriterianame();
						c.setSubcriterianame(subcriteria.getSubcriterianame());
						c.setHighestscore(subcriteria.getHighestscore());
						c.setUpdatedby(UserUtil.securityService.getUserName());
						c.setDateupdated(new Date());
						if(dbService.saveOrUpdate(c)){
							flag = "success";
							Tbaudittrail recc = new Tbaudittrail();
							recc.setEventname("Update Criteria");
							recc.setEventdescription("Update Sub-Criteria " +subcrtname+" to "+ subcriteria.getSubcriterianame() + " with subcriteriaid : " + subcriteria.getSubcriteriaid());
							recc.setEventdatetime(new Date());
							recc.setUsername(UserUtil.securityService.getUserName());
							dbService.save(recc);
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
	public List<Tbscoresubcriteria> getScoreSubCriteriaList(String modelno, String criteriaid) {
		// TODO Auto-generated method stub
		List<Tbscoresubcriteria> list = null;
		try {
			if (modelno != null && criteriaid != null) {
				params.put("modelno", modelno);
				params.put("criteriaid", criteriaid);
				list = (List<Tbscoresubcriteria>) dbService.executeListHQLQuery("FROM Tbscoresubcriteria WHERE modelno=:modelno AND criteriaid=:criteriaid", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	@Override
	public String saveOrDeleteScoreMatrixPerModel(Tbscorematrixpermodel matrix, String ident) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			if(ident != null && ident.equals("delete")){
				if(dbService.delete(matrix)){
					flag = "success";
					Tbaudittrail recc = new Tbaudittrail();
					recc.setEventname("Delete Score Model Matrix");
					recc.setEventdescription("Delete Score Model Matrix  with id : " + matrix.getId());
					recc.setEventdatetime(new Date());
					recc.setUsername(UserUtil.securityService.getUserName());
					dbService.save(recc);
				}
				return flag;
			} else {
				if(matrix.getId() == null){
					matrix.setCreatedby(UserUtil.securityService.getUserName());
					matrix.setDatecreated(new Date());
					if(dbService.save(matrix)){
						flag = "success";
						Tbaudittrail recc = new Tbaudittrail();
						recc.setEventname("Add Score Model Matrix");
						recc.setEventdescription("Add Score Model Matrix  with id : " + matrix.getId());
						recc.setEventdatetime(new Date());
						recc.setUsername(UserUtil.securityService.getUserName());
						dbService.save(recc);
					}
				}else{
					params.put("id", matrix.getId());
					Tbscorematrixpermodel m = (Tbscorematrixpermodel) dbService.executeUniqueHQLQuery("FROM Tbscorematrixpermodel WHERE id=:id", params);
					if(m != null){
						matrix.setCreatedby(m.getCreatedby());
						matrix.setDatecreated(m.getDatecreated());
					}
					matrix.setUpdatedby(UserUtil.securityService.getUserName());
					matrix.setDateupdated(new Date());
					if(dbService.saveOrUpdate(matrix)){
						flag = "success";
						Tbaudittrail recc = new Tbaudittrail();
						recc.setEventname("Update Score Model Matrix");
						recc.setEventdescription("Update Score Model Matrix  with id : " + matrix.getId());
						recc.setEventdatetime(new Date());
						recc.setUsername(UserUtil.securityService.getUserName());
						dbService.save(recc);
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
	public List<Tbscorematrixpermodel> getScoreMatrixListPerModel(String modelno) {
		// TODO Auto-generated method stub
		List<Tbscorematrixpermodel> list = null;
		try {
			if (modelno != null) {
				params.put("modelno", modelno);
				list = (List<Tbscorematrixpermodel>) dbService.executeListHQLQuery("FROM Tbscorematrixpermodel WHERE modelno=:modelno", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}


