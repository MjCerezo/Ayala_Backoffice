package com.etel.dbcodeparams;

import java.util.List;

import com.coopdb.data.Tbdatabaseparams;
import com.coopdb.data.Tbfieldparams;
import com.coopdb.data.Tbtableparams;

/**
 * @author Kevin
 *
 */
public interface DBCodeParamsService {

	String saveOrDeleteDatabaseParams(Tbdatabaseparams dbparams, String ident);

	List<Tbdatabaseparams> getListDBParams();

	String saveOrDeleteTableParams(Tbtableparams tableparams, String ident);

	List<Tbtableparams> getListTableParamsByDBCode(String dbcode);

	String saveOrDeleteFieldParams(Tbfieldparams fieldparams, String ident);
	
	List<Tbfieldparams> getListTableField(String dbcode, String tbcode);


}
