package com.etel.scoremodel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.coopdb.data.Tbdatabaseparams;
import com.coopdb.data.Tbscorecriteria;
import com.coopdb.data.Tbscoreitems;
import com.coopdb.data.Tbscorematrixpermodel;
import com.coopdb.data.Tbscoremodel;
import com.coopdb.data.Tbscoreoperandsperitem;
import com.coopdb.data.Tbscoresubcriteria;
import com.etel.scoremodel.forms.ScoreItemsForm;
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
public class ScoreModelFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
	private ScoreModelService smSrvc = new ScoreModelServiceImpl();
	
	public String addModel(String modelname, String modeldesc, BigDecimal passingscore){
    	return smSrvc.addModel(modelname, modeldesc, passingscore);
    }

	public String addScoreCard(String modelno, String criteriaid, String subcriteriaid, String fielddesc, String tbcode,
			String fieldname, String datatype, BigDecimal weights, String codename, String dbcode, String apprefno) {
		return smSrvc.addScoreCard(modelno, criteriaid, subcriteriaid, fielddesc, tbcode, fieldname, datatype, weights, codename, dbcode, apprefno);
    }
    
    public String addScoreCardVal(String scorekey, String oper1, String value1, String oper2, String value2, BigDecimal score){
    	return smSrvc.addScoreCardVal(scorekey, oper1, value1, oper2, value2, score);
    }
    
    public List<Tbscoremodel> getModelList(){
    	return smSrvc.getModelList();
    }
    
    public List<ScoreItemsForm> getScorecard(String modelno, String criteriaid, String subcriteriaid){
    	List<ScoreItemsForm> list = new ArrayList<ScoreItemsForm>();
    	list = smSrvc.getScorecard(modelno, criteriaid, subcriteriaid);
    	return list;
    }
    
    public List<Tbscoreoperandsperitem> getScorecardScore(String scorekey){
    	return smSrvc.getScorecardScore(scorekey);
    }

    public String updateScoreCardVal(Tbscoreoperandsperitem score){
    	return smSrvc.updateScoreCardVal(score);
    }
    public String updateScoreCard(Tbscoreitems tbscore){
    	return smSrvc.updateScoreCard(tbscore);
    }
    
    public String deleteModel(String modelno){
    	return smSrvc.deleteModel(modelno);
    }
    
    public String deleteCharac(String scorekey){
    	return smSrvc.deleteCharac(scorekey);
    }
    
    public String deleteCharacDet(Integer id){
    	return smSrvc.deleteCharacDet(id);
    }
    public String duplicateModel(String modelname, String modeldesc, BigDecimal passingscore, String dupmodelno){
    	return smSrvc.duplicateModel(modelname, modeldesc, passingscore, dupmodelno);
    }
	
    public String updateCreditModel(Tbscoremodel model){
    	return smSrvc.updateCreditModel(model);
    }

    public List<String> codetableList(){
    	List<String> list = smSrvc.codetableList();
    	return list;
    }
    
    public List<String> getDBList(){
    	return smSrvc.getDBList();
    }
    
    public List<String> getTableListByDB(String dbname){
    	return smSrvc.getTableListByDB(dbname);
    }
    public List<String> getColumnListByDBAndTable(String dbname, String tablename){
    	return smSrvc.getColumnListByDBAndTable(dbname, tablename);
    }
    public List<Tbdatabaseparams> getDatabaseParamsList(){
    	return smSrvc.getDatabaseParamsList();
    }
    public List<String> getCodetableByDBAndTable(String dbname, String codestablename){
    	return smSrvc.getCodetableByDBAndTable(dbname, codestablename);
    }
    public String saveOrDeleteScoreCriteria(Tbscorecriteria criteria, String ident){
    	return smSrvc.saveOrDeleteScoreCriteria(criteria, ident);
    }
    public List<Tbscorecriteria> getScoreCriteriaListByModelno(String modelno){
    	return smSrvc.getScoreCriteriaListByModelno(modelno);
    }
    public String saveOrDeleteScoreSubCriteria(Tbscoresubcriteria subcriteria, String ident){
    	return smSrvc.saveOrDeleteScoreSubCriteria(subcriteria, ident);
    }
    public List<Tbscoresubcriteria> getScoreSubCriteriaList(String modelno, String criteriaid){
    	return smSrvc.getScoreSubCriteriaList(modelno, criteriaid);
    }
    
    public String saveOrDeleteScoreMatrixPerModel(Tbscorematrixpermodel matrix, String ident){
    	return smSrvc.saveOrDeleteScoreMatrixPerModel(matrix, ident);
    }
    public List<Tbscorematrixpermodel> getScoreMatrixListPerModel(String modelno){
    	return smSrvc.getScoreMatrixListPerModel(modelno);
    }
}
