package com.etel.FormulasAndValues;

import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbapa;
import com.coopdb.data.Tbsbl;
import com.etel.formulaandvalues.form.FormulaAndValuesForm;

public interface FormulasAndValuesService {

	List<FormulaAndValuesForm> getAPA();

	String saveOrUpdateAPA(Tbapa apa);

	List<FormulaAndValuesForm> getSBL();

	String saveOrUpdateAPA(Tbsbl sbl);
}
