package com.etel.indocc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbpsiccodes;
import com.coopdb.data.Tbpsoccodes;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;
import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
import com.wavemaker.runtime.service.annotations.ExposeToClient;
/**
 * This is a client-facing service class.  All
 * public methods will be exposed to the client.  Their return
 * values and parameters will be passed to the client or taken
 * from the client, respectively.  This will be a singleton
 * instance, shared between all requests. 
 * 
 * To log, call the superclass method log(LOG_LEVEL, String) or log(LOG_LEVEL, String, Exception).
 * LOG_LEVEL is one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level.
 * For info on these levels, look for tomcat/log4j documentation
 */
@ExposeToClient
public class IndoccFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public IndoccFacade() {
       //super(INFO);
    }

    
    
    //PSIC
    @SuppressWarnings("unchecked")
	public List<Tbpsiccodes> psiclevel1Code(){
		List<Tbpsiccodes> psiclist = new ArrayList<Tbpsiccodes>();
		DBService dbservice = new DBServiceImpl();
		try {
			psiclist = (List<Tbpsiccodes>) dbservice.execSQLQueryTransformer("SELECT * FROM Tbpsiccodes ORDER BY level1desc ASC", null, Tbpsiccodes.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return psiclist;
    }
    
    @SuppressWarnings("unchecked")
	public List<Tbpsiccodes> psiclevel2CodeByLevel1Code(String psiclevel1Code){
		List<Tbpsiccodes> psiclist = new ArrayList<Tbpsiccodes>();
		DBService dbservice = new DBServiceImpl();
		Map <String, Object> params = HQLUtil.getMap();
		try {
			if(psiclevel1Code != null){
				params.put("code", psiclevel1Code);
				psiclist = (List<Tbpsiccodes>) dbservice.execSQLQueryTransformer("SELECT * FROM Tbpsiccodes WHERE level1code=:code ORDER BY level2desc ASC", params, Tbpsiccodes.class, 1);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return psiclist;
    }

    @SuppressWarnings("unchecked")
	public List<Tbpsiccodes> psicDescByLevel2Code(String psiclevel2Code){
		List<Tbpsiccodes> psiclist = new ArrayList<Tbpsiccodes>();
		DBService dbservice = new DBServiceImpl();
		Map <String, Object> params = HQLUtil.getMap();
		try {
			if(psiclevel2Code != null){
				params.put("code", psiclevel2Code);
				psiclist = (List<Tbpsiccodes>) dbservice.executeListHQLQuery("FROM Tbpsiccodes WHERE level2code=:code", params);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return psiclist;
    }
    
    
    //PSOC
    @SuppressWarnings("unchecked")
	public List<Tbpsoccodes> psoclevel1Code(){
		List<Tbpsoccodes> psoclist = new ArrayList<Tbpsoccodes>();
		List<Object> obj = new ArrayList<Object>();
		DBService dbservice = new DBServiceImpl();
		try {
			obj = (List<Object>) dbservice.executeListHQLQuery("SELECT DISTINCT level1code, level1desc FROM Tbpsoccodes", null);
			if (obj != null) {
				for (Object obj1 : obj) {
					Object obj2[] = (Object[]) obj1;
					ArrayList<String> data = new ArrayList<String>();
					for (Object obj3 : obj2) {
						data.add(String.valueOf(obj3));
					}
					Tbpsoccodes c = new Tbpsoccodes();
					c.setLevel1code(data.get(0));
					c.setLevel1desc(data.get(1));
					psoclist.add(c);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return psoclist;
    }   
    @SuppressWarnings("unchecked")
	public List<Tbpsoccodes> psoclevel2CodeByLevel1Code(String psoclevel1code){
		List<Tbpsoccodes> psoclist = new ArrayList<Tbpsoccodes>();
		List<Object> obj = new ArrayList<Object>();
		DBService dbservice = new DBServiceImpl();
		Map <String, Object> params = HQLUtil.getMap();
		try {
			if(psoclevel1code != null){
				params.put("code", psoclevel1code);
				obj = (List<Object>) dbservice.executeListHQLQuery("SELECT DISTINCT level2code, level2desc FROM Tbpsoccodes WHERE level1code=:code", params);
				if (obj != null) {
					for (Object obj1 : obj) {
						Object obj2[] = (Object[]) obj1;
						ArrayList<String> data = new ArrayList<String>();
						for (Object obj3 : obj2) {
							data.add(String.valueOf(obj3));
						}
						Tbpsoccodes c = new Tbpsoccodes();
						c.setLevel2code(data.get(0));
						c.setLevel2desc(data.get(1));
						psoclist.add(c);
					}
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return psoclist;
    }  
    
    @SuppressWarnings("unchecked")
	public List<Tbpsoccodes> psoclevel3CodeByLevel2Code(String psoclevel2code){
		List<Tbpsoccodes> psoclist = new ArrayList<Tbpsoccodes>();
		List<Object> obj = new ArrayList<Object>();
		DBService dbservice = new DBServiceImpl();
		Map <String, Object> params = HQLUtil.getMap();
		try {
			if(psoclevel2code != null){
				params.put("code", psoclevel2code);
				obj = (List<Object>) dbservice.executeListHQLQuery("SELECT DISTINCT level3code, level3desc FROM Tbpsoccodes WHERE level2code=:code", params);
				if (obj != null) {
					for (Object obj1 : obj) {
						Object obj2[] = (Object[]) obj1;
						ArrayList<String> data = new ArrayList<String>();
						for (Object obj3 : obj2) {
							data.add(String.valueOf(obj3));
						}
						Tbpsoccodes c = new Tbpsoccodes();
						c.setLevel3code(data.get(0));
						c.setLevel3desc(data.get(1));
						psoclist.add(c);
					}
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return psoclist;
    }  
    
    @SuppressWarnings("unchecked")
	public List<Tbpsoccodes> psoclevel4DescByLevel3Code(String psoclevel3Code){
		List<Tbpsoccodes> psoclist = new ArrayList<Tbpsoccodes>();
		DBService dbservice = new DBServiceImpl();
		Map <String, Object> params = HQLUtil.getMap();
		try {
			if(psoclevel3Code != null){
				params.put("code", psoclevel3Code);
				psoclist = (List<Tbpsoccodes>) dbservice.executeListHQLQuery("FROM Tbpsoccodes WHERE level3code=:code", params);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return psoclist;
    }
    
    @SuppressWarnings("unchecked")
	public List<Tbpsiccodes> searchPSIC(String str){
		DBService dbservice = new DBServiceImpl();
		List<Tbpsiccodes> list = new ArrayList<Tbpsiccodes>();
		try {
			if(str != null){
				list = (List<Tbpsiccodes>) dbservice.executeListHQLQuery("FROM Tbpsiccodes "
						+ "WHERE level1desc like '%"+str+"%' OR level2desc like '%"+str+"%' OR psicdesc like '%"+str+"%'", null);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
    }
    
    @SuppressWarnings("unchecked")
	public List<Tbpsoccodes> searchPSOC(String str){
		DBService dbservice = new DBServiceImpl();
		List<Tbpsoccodes> list = new ArrayList<Tbpsoccodes>();
		try {
			if(str != null){
				list = (List<Tbpsoccodes>) dbservice.executeListHQLQuery("FROM Tbpsoccodes "
						+ "WHERE level1desc like '%"+str+"%' OR level2desc like '%"+str+"%' OR level3desc like '%"+str+"%' OR level4desc like '%"+str+"%'", null);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
    }
    
	public Tbpsiccodes getByPSICCode(String psiccode){
		DBService dbservice = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		Tbpsiccodes psic = new Tbpsiccodes();
		try {
			if(psiccode != null){
				params.put("psiccode", psiccode);
				psic = (Tbpsiccodes) dbservice.executeUniqueHQLQueryMaxResultOne("FROM Tbpsiccodes "
						+ "WHERE psiccode=:psiccode", params);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return psic;
    }

	public Tbpsoccodes getByPSOCCode(String psoccode){
		DBService dbservice = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		Tbpsoccodes psoc = new Tbpsoccodes();
		try {
			if(psoccode != null){
				params.put("psoccode", psoccode);
				psoc = (Tbpsoccodes) dbservice.executeUniqueHQLQueryMaxResultOne("FROM Tbpsoccodes "
						+ "WHERE psoccode=:psoccode", params);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return psoc;
    }	
}
