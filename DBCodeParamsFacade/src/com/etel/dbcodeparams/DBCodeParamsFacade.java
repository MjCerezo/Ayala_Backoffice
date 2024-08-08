package com.etel.dbcodeparams;

import java.util.List;

import com.coopdb.data.Tbdatabaseparams;
import com.coopdb.data.Tbfieldparams;
import com.coopdb.data.Tbtableparams;
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
public class DBCodeParamsFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
	private DBCodeParamsService srvc = new DBCodeParamsServiceImpl();
	
	public String saveOrDeleteDatabaseParams(Tbdatabaseparams dbparams, String ident){
		return srvc.saveOrDeleteDatabaseParams(dbparams, ident);
	}
	public List<Tbdatabaseparams> getListDBParams(){
		return srvc.getListDBParams();
	}
	public String saveOrDeleteTableParams(Tbtableparams tableparams, String ident){
		return srvc.saveOrDeleteTableParams(tableparams, ident);
	}
	public List<Tbtableparams> getListTableParamsByDBCode(String dbcode){
		return srvc.getListTableParamsByDBCode(dbcode);
	}
	public String saveOrDeleteFieldParams(Tbfieldparams fieldparams, String ident){
		return srvc.saveOrDeleteFieldParams(fieldparams, ident);
	}
	public List<Tbfieldparams> getListTableField(String dbcode, String tbcode){
		return srvc.getListTableField(dbcode, tbcode);
	}
}
