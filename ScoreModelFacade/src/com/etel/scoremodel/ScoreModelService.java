/**
 * 
 */
package com.etel.scoremodel;

import java.math.BigDecimal;
import java.util.List;

import com.coopdb.data.Tbdatabaseparams;
import com.coopdb.data.Tbscorecriteria;
import com.coopdb.data.Tbscoreitems;
import com.coopdb.data.Tbscorematrixpermodel;
import com.coopdb.data.Tbscoremodel;
import com.coopdb.data.Tbscoreoperandsperitem;
import com.coopdb.data.Tbscoresubcriteria;
import com.etel.scoremodel.forms.ScoreItemsForm;

/**
 * @author Kevin
 *
 */
public interface ScoreModelService {

	String addModel(String modelname, String modeldesc, BigDecimal passingscore);

	String addScoreCard(String modelno, String criteriaid, String subcriteriaid, String fielddesc, String tbcode, String fieldname, String datatype,
			BigDecimal weights, String codename, String dbcode, String apprefno);

	String addScoreCardVal(String scorekey, String oper1, String value1, String oper2, String value2, BigDecimal score);

	List<Tbscoremodel> getModelList();

	List<ScoreItemsForm> getScorecard(String modelno, String criteriaid, String subcriteriaid);

	List<Tbscoreoperandsperitem> getScorecardScore(String scorekey);

	String updateScoreCardVal(Tbscoreoperandsperitem score);

	String updateScoreCard(Tbscoreitems tbscore);

	String deleteModel(String modelno);

	String deleteCharac(String scorekey);

	String deleteCharacDet(Integer id);

	String duplicateModel(String modelname, String modeldesc, BigDecimal passingscore, String dupmodelno);

	String updateCreditModel(Tbscoremodel model);

	List<String> codetableList();

	List<String> getDBList();

	List<String> getTableListByDB(String dbname);

	List<String> getColumnListByDBAndTable(String dbname, String tablename);

	List<Tbdatabaseparams> getDatabaseParamsList();

	List<String> getCodetableByDBAndTable(String dbname, String codestablename);

	String saveOrDeleteScoreCriteria(Tbscorecriteria criteria, String ident);

	List<Tbscorecriteria> getScoreCriteriaListByModelno(String modelno);

	String saveOrDeleteScoreSubCriteria(Tbscoresubcriteria subcriteria, String ident);

	List<Tbscoresubcriteria> getScoreSubCriteriaList(String modelno, String criteriaid);

	String saveOrDeleteScoreMatrixPerModel(Tbscorematrixpermodel matrix, String ident);

	List<Tbscorematrixpermodel> getScoreMatrixListPerModel(String modelno);
}
