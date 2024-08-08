package com.etel.Comparable;

import java.util.List;

import com.coopdb.data.Tbappautocomparablelisting;
import com.coopdb.data.Tbappautomarketsurvey;
import com.coopdb.data.Tbapprelbirzone;
import com.coopdb.data.Tbapprelcomparablelisting;
import com.coopdb.data.Tbapprelcompvaluebldg;
import com.coopdb.data.Tbapprelcompvaluecondo;
import com.coopdb.data.Tbapprelcompvalueland;
import com.coopdb.data.Tbapprellegaldescproperty;
import com.coopdb.data.Tbapprellienencumbrance;
import com.coopdb.data.Tbapprelopinionsurvey;
import com.coopdb.data.Tbappreltavparkingslot;
import com.coopdb.data.Tbappreltcpbldgimprovements;
import com.coopdb.data.Tbappreltcpcondo;
import com.coopdb.data.Tbappreltcpland;
import com.coopdb.data.Tbappreltcpparkingslot;
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
public class ComparableFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public ComparableFacade() {
       super(INFO);
    }
    
    ComparableService srvc = new ComparableServiceImpl();
    
    public String sampleJavaOperation() {
       String result  = null;
       try {
          log(INFO, "Starting sample operation");
          result = "Hello World";
          log(INFO, "Returning " + result);
       } catch(Exception e) {
          log(ERROR, "The sample java service operation has failed", e);
       }
       return result;
    }
    public String addOrupdateComparableAuto(Tbappautocomparablelisting comp){
    	return srvc.addOrupdateComparableAuto(comp);
    }
    public List<Tbappautocomparablelisting> getListAutoComparable(String appreportid){
    	return srvc.getListAutoComparable(appreportid);
    }
    public String deleteComparableAuto(int id){
    	return srvc.deleteComparableAuto(id);
    }
    public List<Tbappautomarketsurvey> getAutoMarketSurvey(String appreportid){
    	return srvc.getAutoMarketSurvey(appreportid);
    }
    public String deleteAutoMarket (Integer id){
    	return srvc.deleteAutoMarket(id);
    }
    public String addOrupdateAutoMrktSrvy(Tbappautomarketsurvey market){
    	return srvc.addOrupdateAutoMrktSrvy(market);
    }
    public String addOrupdateComparableRel(Tbapprelcomparablelisting rel){
    	return srvc.addOrupdateComparableRel(rel);
    }
    public List<Tbapprelcomparablelisting> getListComparableRel(String appreportid){
    	return srvc.getListComparableRel(appreportid);
    }
    public String deleteComparableRel(int id){
    	return srvc.deleteComparableRel(id);
    }
    public List<Tbapprelopinionsurvey> getListRelOpinion(String appreportid){
    	return srvc.getListRelOpinion(appreportid);
    }
    public String addOrupdateRelOpinion(Tbapprelopinionsurvey rel){
    	return srvc.addOrupdateRelOpinion(rel);
    }
    public String deleteRelOpinion(int id){
    	return srvc.deleteRelOpinion(id);
    }
    public List<Tbapprelbirzone> getListRelBIRzone(String appreportid){
    	return srvc.getListRelBIRzone(appreportid);
    }
    public String addOrupdateBIRZonalVal(Tbapprelbirzone rel){
    	return srvc.addOrupdateBIRZonalVal(rel);
    }
    public List<Tbappreltcpland> getListTCPLand(String appreportid){
    	return srvc.getListTCPLand(appreportid);
    }
    public String addOrupdateTCPLand(Tbappreltcpland rel){
    	return srvc.addOrupdatTCPLand(rel);

    }
    public List<Tbapprelcompvalueland> getListCompValLand(String appreportid){
    	return srvc.getListCompValLand(appreportid);
    }
    public String addOrupdateCompValLand(Tbapprelcompvalueland rel){
    	return srvc.addOrupdateCompValLand(rel);
    }
    public List<Tbappreltcpbldgimprovements> getListTCPBldg(String appreportid){
    	return srvc.getListTCPBldg(appreportid);
    }
    public String addOrupdateTCPBldg(Tbappreltcpbldgimprovements rel){
    	return srvc.addOrupdateTCPBldg(rel);
    }
    public List<Tbapprelcompvaluebldg> getListCompValBldg(String appreportid){
    	return srvc.getListCompValBldg(appreportid);
    }
    public String addOrupdateCompValBldg(Tbapprelcompvaluebldg rel){
    	return srvc.addOrupdateCompValBldg(rel);
    }
    public String addOrupdateLegalDescProp(Tbapprellegaldescproperty rel){
    	return srvc.addOrupdateLegalDescProp(rel);
    }
    public List<Tbapprellegaldescproperty> getListLegalDescProp(String appreportid){
    	return srvc.getListLegalDescProp(appreportid);
    }
    public List<Tbapprellienencumbrance> getListLienEncumbrance(String appreportid){
    	return srvc.getListLienEncumbrance(appreportid);
    }
    public String addOrupdateLienEncumbrance(Tbapprellienencumbrance rel){
    	return srvc.addOrupdateLienEncumbrance(rel);
    }
    public List<Tbappreltcpcondo> getListTCPCondo(String appreportid){
    	return srvc.getListTCPCondo(appreportid);
    }
    public String addOrupdateTCPCondo(Tbappreltcpcondo rel){
    	return srvc.addOrupdateTCPCondo(rel);
    }
    public String deleteTCPCompValueById(Integer id, String type){
    	return srvc.deleteTCPCompValueById(id, type);
    }
    public String deleteLegalDescProperty(Tbapprellegaldescproperty legaldescprop){
    	return srvc.deleteLegalDescProperty(legaldescprop);
    }
    public String deleteBasesOfEvaluationRel(Integer id, String type){
    	return srvc.deleteBasesOfEvaluationRel(id, type);
    }
    public String deleteRELCompValueById(Integer id, String type){
    	return srvc.deleteRELCompValueById(id, type);
    }
    public List<Tbapprelcompvaluecondo> getListCompValCondo(String appreportid){
    	return srvc.getListCompValCondo(appreportid);
    }
    public String addOrUpdateCompValCondo(Tbapprelcompvaluecondo rel){
    	return srvc.addOrUpdateCompValCondo(rel);
    }
    public List<Tbappreltavparkingslot> getListTavParkingSlot(String appreportid){
    	return srvc.getListTavParkingSlot(appreportid);
    }
    public String addOrUpdateTavParkingSlot(Tbappreltavparkingslot rel){
    	return srvc.addOrUpdateTavParkingSlot(rel);
    }
    public List<Tbappreltcpparkingslot> getListTcpParkingSlot(String appreportid){
    	return srvc.getListTcpParkingSlot(appreportid);
    }
    public String addOrUpdateTcpParkingSlot(Tbappreltcpparkingslot rel){
    	return srvc.addOrUpdateTcpParkingSlot(rel);
    }
}
