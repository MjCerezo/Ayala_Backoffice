package com.etel.FormulasAndValues;

import com.coopdb.data.Tbapa;
import com.coopdb.data.Tbsbl;
import com.etel.formulaandvalues.form.FormulaAndValuesForm;
import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
import com.wavemaker.runtime.service.annotations.ExposeToClient;
import java.util.List;
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
public class FormulasAndValuesFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public FormulasAndValuesFacade() {
       super(INFO);
    }
    private FormulasAndValuesService srvc = new FormulasAndValuesServiceImpl();
    
    public List<FormulaAndValuesForm> getAPA() {
    	return srvc.getAPA();
    }
    
    public String saveOrUpdateAPA(Tbapa apa) {
    	return srvc.saveOrUpdateAPA(apa);
    }
    
    public List<FormulaAndValuesForm> getSBL() {
    	return srvc.getSBL();
    }
    
    public String saveOrUpdateSBL(Tbsbl sbl) {
    	return srvc.saveOrUpdateAPA(sbl);
    }
    
}
