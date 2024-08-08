package com.etel.dbcodeparams;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbdatabaseparams;
import com.coopdb.data.Tbfieldparams;
import com.coopdb.data.Tbtableparams;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.util.SequenceGenerator;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;

/**
 * @author Kevin
 *
 */
public class DBCodeParamsServiceImpl implements DBCodeParamsService {
	
	private DBService dbService = new DBServiceImpl();
	private Map<String, Object> params = HQLUtil.getMap();
	
	@Override
	public String saveOrDeleteDatabaseParams(Tbdatabaseparams dbparams, String ident) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			if(ident != null && ident.equals("delete")){
				params.put("dbcode", dbparams.getDbcode());
				
				if(dbService.delete(dbparams)){
					dbService.executeUpdate("DELETE FROM Tbtableparams WHERE dbcode=:dbcode", params);
					dbService.executeUpdate("DELETE FROM Tbfieldparams WHERE dbcode=:dbcode", params);
					return "success";
				}
			} else {
				if(dbparams.getDbcode() == null){
					String dbcode = SequenceGenerator.generateModelNoSequence("DBCODE");
					dbparams.setDbcode(dbcode);
					dbparams.setLasttableseqno(0);
					dbparams.setCreatedby(UserUtil.securityService.getUserName());
					dbparams.setDatecreated(new Date());
					if(dbService.save(dbparams)){
						flag = "success";
					}
				} else {
					params.put("dbcode", dbparams.getDbcode());
					Tbdatabaseparams d = (Tbdatabaseparams) dbService.executeUniqueHQLQuery("FROM Tbdatabaseparams WHERE dbcode=:dbcode", params);
					if(d != null){
						dbparams.setCreatedby(d.getCreatedby());
						dbparams.setDatecreated(d.getDatecreated());
						dbparams.setLasttableseqno(d.getLasttableseqno());
					}
					dbparams.setUpdatedby(UserUtil.securityService.getUserName());
					dbparams.setDateupdated(new Date());
					if(dbService.saveOrUpdate(dbparams)){
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
	public List<Tbdatabaseparams> getListDBParams() {
		// TODO Auto-generated method stub
		List<Tbdatabaseparams> list = new ArrayList<Tbdatabaseparams>();
		try {
			list = (List<Tbdatabaseparams>) dbService.executeListHQLQuery("FROM Tbdatabaseparams", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String saveOrDeleteTableParams(Tbtableparams tableparams, String ident) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			if(ident != null && ident.equals("delete")){
				params.put("dbcode", tableparams.getDbcode());
				params.put("tbcode", tableparams.getTbcode());
				
				if(dbService.delete(tableparams)){
					dbService.executeUpdate("DELETE FROM Tbfieldparams WHERE dbcode=:dbcode AND tbcode=:tbcode", params);
					return "success";
				}
			} else {
				if(tableparams.getTbcode() == null){
					params.put("dbcode", tableparams.getDbcode());
					params.put("tbname", tableparams.getTbname());
					Integer cnt = (Integer) dbService.executeUniqueSQLQuery("SELECT COUNT(*) FROM Tbtableparams WHERE dbcode=:dbcode AND tbname=:tbname", params);
					if(cnt > 0){
						return "existing";
					}
					
					//Insert New
					String tbcode = "01";
					Tbdatabaseparams d = (Tbdatabaseparams) dbService.executeUniqueHQLQuery("FROM Tbdatabaseparams WHERE dbcode=:dbcode", params);
					if(d != null){
						if(d.getLasttableseqno() != null){
							tbcode = String.format("%02d", d.getLasttableseqno() + 1);
						}
						d.setLasttableseqno(d.getLasttableseqno() == null ? 1 : (d.getLasttableseqno() + 1));
						dbService.saveOrUpdate(d);
					}
					tableparams.setTbcode(tableparams.getDbcode().concat(tbcode));
					tableparams.setDbcode(tableparams.getDbcode());
					tableparams.setLastfieldseqno(0);
					tableparams.setCreatedby(UserUtil.securityService.getUserName());
					tableparams.setDatecreated(new Date());
					if(dbService.save(tableparams)){
						flag = "success";
					}
				} else {
					params.put("dbcode", tableparams.getDbcode());
					params.put("tbcode", tableparams.getTbcode());
					Tbtableparams t = (Tbtableparams) dbService.executeUniqueHQLQuery("FROM Tbtableparams WHERE dbcode=:dbcode AND tbcode=:tbcode", params);
					if(t != null){
						t.setTbnametodisplay(tableparams.getTbnametodisplay());
						t.setUpdatedby(UserUtil.securityService.getUserName());
						t.setDateupdated(new Date());
						if(dbService.saveOrUpdate(t)){
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
	public List<Tbtableparams> getListTableParamsByDBCode(String dbcode) {
		// TODO Auto-generated method stub
		List<Tbtableparams> list = new ArrayList<Tbtableparams>();
		try {
			if(dbcode != null){
				params.put("dbcode", dbcode);
				list = (List<Tbtableparams>) dbService.executeListHQLQuery("FROM Tbtableparams WHERE dbcode=:dbcode", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public String saveOrDeleteFieldParams(Tbfieldparams fieldparams, String ident) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			if(ident != null && ident.equals("delete")){
				if(dbService.delete(fieldparams)){
					return "success";
				}
			} else {
				if(fieldparams.getFieldcode() == null){
					params.put("dbcode", fieldparams.getDbcode());
					params.put("tbcode", fieldparams.getTbcode());
					params.put("fieldname", fieldparams.getFieldname());
					Integer cnt = (Integer) dbService.executeUniqueSQLQuery("SELECT COUNT(*) FROM Tbfieldparams WHERE dbcode=:dbcode AND tbcode=:tbcode AND fieldname=:fieldname ", params);
					if(cnt > 0){
						return "existing";
					}
					
					//Insert New
					String fieldcode = "01";
					Tbtableparams t = (Tbtableparams) dbService.executeUniqueHQLQuery("FROM Tbtableparams WHERE dbcode=:dbcode AND tbcode=:tbcode", params);
					if(t != null){
						if(t.getLastfieldseqno() != null){
							fieldcode = String.format("%02d", t.getLastfieldseqno() + 1);
						}
						t.setLastfieldseqno(t.getLastfieldseqno() == null ? 1 : (t.getLastfieldseqno() + 1));
						dbService.saveOrUpdate(t);
					}
					fieldparams.setFieldcode(fieldparams.getTbcode().concat(fieldcode));
					fieldparams.setCreatedby(UserUtil.securityService.getUserName());
					fieldparams.setDatecreated(new Date());
					if(dbService.save(fieldparams)){
						flag = "success";
					}
				} else {
					params.put("dbcode", fieldparams.getDbcode());
					params.put("tbcode", fieldparams.getTbcode());
					params.put("fieldcode", fieldparams.getFieldcode());
					Tbfieldparams f = (Tbfieldparams) dbService.executeUniqueHQLQuery("FROM Tbfieldparams WHERE dbcode=:dbcode AND tbcode=:tbcode AND fieldcode=:fieldcode", params);
					if(f != null){
						f.setFieldnametodisplay(fieldparams.getFieldnametodisplay());
						f.setUpdatedby(UserUtil.securityService.getUserName());
						f.setDateupdated(new Date());
						if(dbService.saveOrUpdate(f)){
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
	public List<Tbfieldparams> getListTableField(String dbcode, String tbcode) {
		// TODO Auto-generated method stub
		List<Tbfieldparams> list = new ArrayList<Tbfieldparams>();
		try {
			if(dbcode != null){
				params.put("dbcode", dbcode);
				params.put("tbcode", tbcode);
				list = (List<Tbfieldparams>) dbService.executeListHQLQuery("FROM Tbfieldparams WHERE dbcode=:dbcode AND tbcode=:tbcode", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
