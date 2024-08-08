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



public interface ComparableService {

	String addOrupdateComparableAuto(Tbappautocomparablelisting comp);

	List<Tbappautocomparablelisting> getListAutoComparable(String appreportid);

	String deleteComparableAuto(int id);

	List<Tbappautomarketsurvey> getAutoMarketSurvey(String appreportid);

	String addOrupdateAutoMrktSrvy(Tbappautomarketsurvey market);

	String addOrupdateComparableRel(Tbapprelcomparablelisting rel);

	List<Tbapprelcomparablelisting> getListComparableRel(String appreportid);

	String deleteComparableRel(int id);

	List<Tbapprelopinionsurvey> getListRelOpinion(String appreportid);

	String addOrupdateRelOpinion(Tbapprelopinionsurvey rel);

	String deleteRelOpinion(int id);

	List<Tbapprelbirzone> getListRelBIRzone(String appreportid);

	String addOrupdateBIRZonalVal(Tbapprelbirzone rel);

	List<Tbappreltcpland> getListTCPLand(String appreportid);

	String addOrupdatTCPLand(Tbappreltcpland rel);

	List<Tbapprelcompvalueland> getListCompValLand(String appreportid);

	String addOrupdateCompValLand(Tbapprelcompvalueland rel);

	List<Tbappreltcpbldgimprovements> getListTCPBldg(String appreportid);

	String addOrupdateTCPBldg(Tbappreltcpbldgimprovements rel);

	List<Tbapprelcompvaluebldg> getListCompValBldg(String appreportid);

	String addOrupdateCompValBldg(Tbapprelcompvaluebldg rel);

	String addOrupdateLegalDescProp(Tbapprellegaldescproperty rel);

	List<Tbapprellegaldescproperty> getListLegalDescProp(String appreportid);

	List<Tbapprellienencumbrance> getListLienEncumbrance(String appreportid);

	String addOrupdateLienEncumbrance(Tbapprellienencumbrance rel);

	List<Tbappreltcpcondo> getListTCPCondo(String appreportid);

	String addOrupdateTCPCondo(Tbappreltcpcondo rel);

	String deleteAutoMarket(Integer id);

	String deleteTCPCompValueById(Integer id, String type);

	String deleteLegalDescProperty(Tbapprellegaldescproperty legaldescprop);

	String deleteBasesOfEvaluationRel(Integer id, String type);

	String deleteRELCompValueById(Integer id, String type);

	List<Tbapprelcompvaluecondo> getListCompValCondo(String appreportid);

	String addOrUpdateCompValCondo(Tbapprelcompvaluecondo rel);

	List<Tbappreltavparkingslot> getListTavParkingSlot(String appreportid);

	String addOrUpdateTavParkingSlot(Tbappreltavparkingslot rel);

	List<Tbappreltcpparkingslot> getListTcpParkingSlot(String appreportid);

	String addOrUpdateTcpParkingSlot(Tbappreltcpparkingslot rel);

}
