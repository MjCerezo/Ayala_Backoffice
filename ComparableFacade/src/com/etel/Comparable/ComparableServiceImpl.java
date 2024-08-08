package com.etel.Comparable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;


public class ComparableServiceImpl implements ComparableService {
	private DBService dbServiceLOS = new DBServiceImpl();
	
	@Override
	public String addOrupdateComparableAuto(Tbappautocomparablelisting comp) {
		// TODO Auto-generated method stub
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("id", comp.getId());
			Tbappautocomparablelisting com = (Tbappautocomparablelisting) dbServiceLOS
					.executeUniqueHQLQuery("FROM Tbappautocomparablelisting WHERE id=:id", params);

			if (com != null) {
				comp.setId(com.getId());
				if(dbServiceLOS.saveOrUpdate(comp)){
					flag = "success";
				}

			} 
			else {
				if(dbServiceLOS.save(comp)){
					flag = "success";
				}

			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbappautocomparablelisting> getListAutoComparable(String appreportid) {
		// TODO Auto-generated method stub
		Map<String,Object> params = HQLUtil.getMap();
		List<Tbappautocomparablelisting> list = new ArrayList<Tbappautocomparablelisting>();
		try {
			params.put("reportid", appreportid);
			list = (List<Tbappautocomparablelisting>) dbServiceLOS
					.executeListHQLQuery("FROM Tbappautocomparablelisting WHERE appraisalreportid=:reportid", params);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String deleteComparableAuto(int id) {
		// TODO Auto-generated method stub
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("id", id);
			Tbappautocomparablelisting comp = (Tbappautocomparablelisting) dbServiceLOS
					.executeUniqueHQLQuery("FROM Tbappautocomparablelisting WHERE id=:id", params);
			
			if(comp != null){
				if(dbServiceLOS.delete(comp));
				flag = "success";
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbappautomarketsurvey> getAutoMarketSurvey(String appreportid) {
		// TODO Auto-generated method stub
		Map<String, Object> params = HQLUtil.getMap();
		List<Tbappautomarketsurvey> list = new ArrayList<Tbappautomarketsurvey>();
		try {
			params.put("reportid", appreportid);
			list = (List<Tbappautomarketsurvey>) dbServiceLOS
					.executeListHQLQuery("FROM Tbappautomarketsurvey WHERE appraisalreportid=:reportid", params);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String addOrupdateAutoMrktSrvy(Tbappautomarketsurvey market) {
		// TODO Auto-generated method stub
		String flag = "failed";
		Map<String,Object> params = HQLUtil.getMap();
		try {
			params.put("id", market.getId());
			Tbappautomarketsurvey mark = (Tbappautomarketsurvey) dbServiceLOS
					.executeUniqueHQLQuery("FROM Tbappautomarketsurvey WHERE id=:id", params);
			if(mark != null){
				market.setId(mark.getId());
				if(dbServiceLOS.saveOrUpdate(market)){
					flag = "updated";
				}
			}else{
				if(dbServiceLOS.save(market)){
					flag = "created";
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String addOrupdateComparableRel(Tbapprelcomparablelisting rel) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			if(dbServiceLOS.saveOrUpdate(rel)){
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbapprelcomparablelisting> getListComparableRel(String appreportid) {
		// TODO Auto-generated method stub
		Map<String, Object> params = HQLUtil.getMap();
		List<Tbapprelcomparablelisting> list = new ArrayList<Tbapprelcomparablelisting>();
		try {
			params.put("reportid", appreportid);
			list = (List<Tbapprelcomparablelisting>) dbServiceLOS
					.executeListHQLQuery("FROM Tbapprelcomparablelisting WHERE appraisalreportid=:reportid", params);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String deleteComparableRel(int id) {
		// TODO Auto-generated method stub
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("id", id);
			Tbapprelcomparablelisting rel = (Tbapprelcomparablelisting) dbServiceLOS
					.executeUniqueHQLQuery("FROM Tbapprelcomparablelisting WHERE id=:id", params);

			if (rel != null) {
				if (dbServiceLOS.delete(rel)) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbapprelopinionsurvey> getListRelOpinion(String appreportid) {
		// TODO Auto-generated method stub
		List<Tbapprelopinionsurvey> rel = new ArrayList<Tbapprelopinionsurvey>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("reportid", appreportid);
			rel = (List<Tbapprelopinionsurvey>) dbServiceLOS
					.executeListHQLQuery("FROM Tbapprelopinionsurvey WHERE appraisalreportid=:reportid", params);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rel;
	}

	@Override
	public String addOrupdateRelOpinion(Tbapprelopinionsurvey rel) {
		String flag = "failed";
		try {
			if(dbServiceLOS.saveOrUpdate(rel)){
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String deleteRelOpinion(int id) {
		// TODO Auto-generated method stub
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("id", id);
			Tbapprelopinionsurvey rel = (Tbapprelopinionsurvey) dbServiceLOS
					.executeUniqueHQLQuery("FROM Tbapprelopinionsurvey WHERE id=:id", params);
			if(rel != null){
				if(dbServiceLOS.delete(rel)){
					flag = "success";
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbapprelbirzone> getListRelBIRzone(String appreportid) {
		// TODO Auto-generated method stub
		List<Tbapprelbirzone> rel = new ArrayList<Tbapprelbirzone>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("reportid", appreportid);
			rel = (List<Tbapprelbirzone>) dbServiceLOS
					.executeListHQLQuery("FROM Tbapprelbirzone WHERE appraisalreportid=:reportid", params);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rel;
	}

	@Override
	public String addOrupdateBIRZonalVal(Tbapprelbirzone rel) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			if(dbServiceLOS.saveOrUpdate(rel)){
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbappreltcpland> getListTCPLand(String appreportid) {
		// TODO Auto-generated method stub
		List<Tbappreltcpland> rel = new ArrayList<Tbappreltcpland>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("reportid", appreportid);
			rel = (List<Tbappreltcpland>) dbServiceLOS
					.executeListHQLQuery("FROM Tbappreltcpland WHERE appraisalreportid=:reportid", params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rel;
	}

	@Override
	public String addOrupdatTCPLand(Tbappreltcpland rel) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			if(dbServiceLOS.saveOrUpdate(rel)){
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbapprelcompvalueland> getListCompValLand(String appreportid) {
		// TODO Auto-generated method stub
		List<Tbapprelcompvalueland> rel = new ArrayList<Tbapprelcompvalueland>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("reportid", appreportid);
			rel = (List<Tbapprelcompvalueland>) dbServiceLOS
					.executeListHQLQuery("FROM Tbapprelcompvalueland WHERE appraisalreportid=:reportid", params);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rel;
	}

	@Override
	public String addOrupdateCompValLand(Tbapprelcompvalueland rel) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			if(dbServiceLOS.saveOrUpdate(rel)){
				flag ="success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbappreltcpbldgimprovements> getListTCPBldg(String appreportid) {
		// TODO Auto-generated method stub
		List<Tbappreltcpbldgimprovements> rel = new ArrayList<Tbappreltcpbldgimprovements>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("reportid", appreportid);
			rel = (List<Tbappreltcpbldgimprovements>) dbServiceLOS
					.executeListHQLQuery("FROM Tbappreltcpbldgimprovements WHERE appraisalreportid=:reportid", params);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rel;
	}

	@Override
	public String addOrupdateTCPBldg(Tbappreltcpbldgimprovements rel) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			if(dbServiceLOS.saveOrUpdate(rel)){
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbapprelcompvaluebldg> getListCompValBldg(String appreportid) {
		// TODO Auto-generated method stub
		List<Tbapprelcompvaluebldg> rel = new ArrayList<Tbapprelcompvaluebldg>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("reportid", appreportid);
			rel = (List<Tbapprelcompvaluebldg>) dbServiceLOS
					.executeListHQLQuery("FROM Tbapprelcompvaluebldg WHERE appraisalreportid=:reportid", params);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rel;
	}

	@Override
	public String addOrupdateCompValBldg(Tbapprelcompvaluebldg rel) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			if(dbServiceLOS.saveOrUpdate(rel)){
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String addOrupdateLegalDescProp(Tbapprellegaldescproperty rel) {
		String flag = "failed";
		try {
			if(rel != null){
				if (dbServiceLOS.saveOrUpdate(rel)) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbapprellegaldescproperty> getListLegalDescProp(String appreportid) {
		// TODO Auto-generated method stub
		List<Tbapprellegaldescproperty> rel = new ArrayList<Tbapprellegaldescproperty>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("reportid", appreportid);
			rel = (List<Tbapprellegaldescproperty>) dbServiceLOS
					.executeListHQLQuery("FROM Tbapprellegaldescproperty WHERE appraisalreportid=:reportid", params);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rel;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbapprellienencumbrance> getListLienEncumbrance(String appreportid) {
		// TODO Auto-generated method stub
		List<Tbapprellienencumbrance> rel = new ArrayList<Tbapprellienencumbrance>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("reportid", appreportid);
			rel = (List<Tbapprellienencumbrance>) dbServiceLOS
					.executeListHQLQuery("FROM Tbapprellienencumbrance WHERE appraisalreportid=:reportid", params);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rel;
	}

	@Override
	public String addOrupdateLienEncumbrance(Tbapprellienencumbrance rel) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			if(dbServiceLOS.saveOrUpdate(rel)){
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbappreltcpcondo> getListTCPCondo(String appreportid) {
		// TODO Auto-generated method stub
		List<Tbappreltcpcondo> rel = new ArrayList<Tbappreltcpcondo>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("reportid", appreportid);
			rel = (List<Tbappreltcpcondo>) dbServiceLOS
					.executeListHQLQuery("FROM Tbappreltcpcondo WHERE appraisalreportid=:reportid", params);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return rel;
	}

	@Override
	public String addOrupdateTCPCondo(Tbappreltcpcondo rel) {
		String flag ="failed";
		try {
			if(dbServiceLOS.saveOrUpdate(rel)){
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String deleteAutoMarket(Integer id) {
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (id!=null) {
				params.put("id", id);
				Tbappautomarketsurvey row = (Tbappautomarketsurvey) dbServiceLOS.executeUniqueHQLQueryMaxResultOne
						("FROM Tbappautomarketsurvey WHERE id=:id", params);
				if (row!=null) {
					if (dbServiceLOS.delete(row)) {
						flag = "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String deleteTCPCompValueById(Integer id, String type) {
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (id != null && type != null) {
				params.put("id", id);
				String hql = "";
				if(type.equals("bldgimprovements")){
					hql = "DELETE FROM TBAPPRELTCPBLDGIMPROVEMENTS";
				}
				if(type.equals("condo")){
					hql = "DELETE FROM TBAPPRELTCPCONDO";
				}
				if(type.equals("land")){
					hql = "DELETE FROM TBAPPRELTCPLAND";
				}
				if(type.equals("parkingslot")){
					hql = "DELETE FROM TBAPPRELTCPPARKINGSLOT";
				}
				Integer res = dbServiceLOS.executeUpdate(hql + " WHERE id=:id", params);
				if(res > 0){
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String deleteLegalDescProperty(Tbapprellegaldescproperty legaldescprop) {
		String flag = "failed";
		try {
			if (legaldescprop != null) {
				if(dbServiceLOS.delete(legaldescprop)){
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String deleteBasesOfEvaluationRel(Integer id, String type) {
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (id != null && type != null) {
				params.put("id", id);
				String hql = "";
				if(type.equals("birzonal")){
					hql = "DELETE FROM TBAPPRELBIRZONE";
				}
				if(type.equals("comparablelisting")){
					hql = "DELETE FROM TBAPPRELCOMPARABLELISTING";
				}
				if(type.equals("opinionsurvey")){
					hql = "DELETE FROM TBAPPRELOPINIONSURVEY";
				}
				if(type.equals("lienencubrance")){
					hql = "DELETE FROM TBAPPRELLIENENCUMBRANCE";
				}
				Integer res = dbServiceLOS.executeUpdate(hql + " WHERE id=:id", params);
				if(res > 0){
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String deleteRELCompValueById(Integer id, String type) {
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (id != null && type != null) {
				params.put("id", id);
				String hql = "";
				if(type.equals("bldgimprovements")){
					hql = "DELETE FROM TBAPPRELCOMPVALUEBLDG";
				}
				if(type.equals("condo")){
					hql = "DELETE FROM TBAPPRELCOMPVALUECONDO";
				}
				if(type.equals("land")){
					hql = "DELETE FROM TBAPPRELCOMPVALUELAND";
				}
				if(type.equals("parkingslot")){
					hql = "DELETE FROM TBAPPRELTAVPARKINGSLOT";
				}
				Integer res = dbServiceLOS.executeUpdate(hql + " WHERE id=:id", params);
				if(res > 0){
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbapprelcompvaluecondo> getListCompValCondo(String appreportid) {
		// TODO Auto-generated method stub
		List<Tbapprelcompvaluecondo> rel = new ArrayList<Tbapprelcompvaluecondo>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("reportid", appreportid);
			rel = (List<Tbapprelcompvaluecondo>) dbServiceLOS
					.executeListHQLQuery("FROM Tbapprelcompvaluecondo WHERE appraisalreportid=:reportid", params);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rel;
	}

	@Override
	public String addOrUpdateCompValCondo(Tbapprelcompvaluecondo rel) {
		String flag = "failed";
		try {
			if (rel != null) {
				if(dbServiceLOS.delete(rel)){
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbappreltavparkingslot> getListTavParkingSlot(String appreportid) {
		List<Tbappreltavparkingslot> rel = new ArrayList<Tbappreltavparkingslot>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("reportid", appreportid);
			rel = (List<Tbappreltavparkingslot>) dbServiceLOS
					.executeListHQLQuery("FROM Tbappreltavparkingslot WHERE appraisalreportid=:reportid", params);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rel;
	}

	@Override
	public String addOrUpdateTavParkingSlot(Tbappreltavparkingslot rel) {
		String flag = "failed";
		try {
			if (rel != null) {
				if(dbServiceLOS.delete(rel)){
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbappreltcpparkingslot> getListTcpParkingSlot(String appreportid) {
		List<Tbappreltcpparkingslot> rel = new ArrayList<Tbappreltcpparkingslot>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("reportid", appreportid);
			rel = (List<Tbappreltcpparkingslot>) dbServiceLOS
					.executeListHQLQuery("FROM Tbappreltcpparkingslot WHERE appraisalreportid=:reportid", params);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rel;
	}

	@Override
	public String addOrUpdateTcpParkingSlot(Tbappreltcpparkingslot rel) {
		String flag = "failed";
		try {
			if (rel != null) {
				if(dbServiceLOS.delete(rel)){
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}

