package com.etel.depedcodes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cifsdb.data.Tbcodetable;
import com.cifsdb.data.TbcodetableId;
import com.cifsdb.data.Tbdepedcodes;
import com.cifsdb.data.TbdepedcodesId;
import com.etel.codetable.forms.CodetableForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.depedcodes.form.DepEdCodes;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;

public class DepEdCodesServiceImpl implements DepEdCodesService {
	
	DBService dbservice = new DBServiceImplCIF();

	@SuppressWarnings("unchecked")
	@Override
	public List<DepEdCodes> listDepEdCodes() {
		List<DepEdCodes> dlist= new ArrayList<DepEdCodes>();
		try {
			dlist = (List<DepEdCodes>) dbservice.execSQLQueryTransformer(
					"SELECT DISTINCT regioncode, regionname FROM Tbdepedcodes WHERE divisioncode ='' and stationcode =''", null, DepEdCodes.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dlist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DepEdCodes> listDepEdCodesByRegion(String region) {
		Map<String, Object> params = HQLUtil.getMap();
		List<DepEdCodes> dlist = new ArrayList<DepEdCodes>();
		try {
			if(params != null){
				params.put("region", region);
			
			dlist = (List<DepEdCodes>) dbservice.execSQLQueryTransformer(
					"Select DISTINCT divisioncode, divisionname from Tbdepedcodes WHERE regioncode=:region and divisioncode <>''", params, DepEdCodes.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dlist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DepEdCodes> listDepEdCodesByDivision(String division) {
		Map<String, Object> params = HQLUtil.getMap();
		List<DepEdCodes> list = new ArrayList<DepEdCodes>();
		try {
			if(params != null){
				params.put("division", division);
			list = (List<DepEdCodes>) dbservice.execSQLQueryTransformer(
					"Select DISTINCT stationcode, stationname from Tbdepedcodes WHERE divisioncode=:division and stationcode <>''", params, DepEdCodes.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbdepedcodes> searchDepEdCodes(String region, String division, String station) {
		DBService dbServiceCIF = new DBServiceImplCIF();
		List<Tbdepedcodes> list = new ArrayList<Tbdepedcodes>();
		StringBuilder sb = new StringBuilder();
//		String query = "";
//		System.out.println(">>>>>>>>>>>> R - " + region);
//		System.out.println(">>>>>>>>>>>> D - " + division);
//		System.out.println(">>>>>>>>>>>> S - " + station);
		Map<String, Object> params = HQLUtil.getMap();
		params.put("region", region + "%");
		params.put("division", division + "%");
		params.put("station", station + "%");
		
		try {
//			if(region==null&&division==null&&station==null){
//				System.out.println(">>>>>>>>>>>> Parameter is null !!!");
//				query = "FROM Tbdepedcodes";
//			}else{
//				sb.append("FROM Tbdepedcodes a WHERE ");
//				if(region!=null){
//					sb.append("a.id.regioncode = '" + region + "' AND ");
//				}
//				if(division!=null){
//					sb.append("a.id.divisioncode = '" + division + "' AND ");
//				}
//				if(station!=null){
//					sb.append("a.id.stationcode = '" + station + "' AND ");
//				}
				sb.append("FROM Tbdepedcodes WHERE ");
				if(region!=null){
					sb.append("regionname like :region and divisioncode ='' and stationcode =''");
				}
				else if(division!=null){
					sb.append("divisionname like :division AND stationcode =''");
				}
				else if(station!=null){
					sb.append("stationname like :station");
				}
//				sb.append("12345");
//				query = sb.substring(0, sb.length() - 10);		
//			}
//			System.out.println(">>>>>>>>>>>> QUERY - " + sb.toString());
			list = (List<Tbdepedcodes>)dbServiceCIF.executeListHQLQuery(sb.toString(), params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String saveOrupdateDepedCode(Tbdepedcodes data, String saveOrupdate) {
		String flag = "failed";
		DBService dbServiceCIF = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();	
		Tbdepedcodes row = new Tbdepedcodes(); 
		try {
			if(saveOrupdate.equalsIgnoreCase("AddREGION")){
				if(data.getId().getRegioncode()!=null){
					param.put("r", data.getId().getRegioncode());
					row = (Tbdepedcodes)dbServiceCIF.executeUniqueHQLQueryMaxResultOne
							("FROM Tbdepedcodes a WHERE a.id.regioncode=:r", param);
				}
			}else if(saveOrupdate.equalsIgnoreCase("AddDIVISION")){
				if(data.getId().getRegioncode()!=null&&data.getId().getDivisioncode()!=null){
					param.put("r", data.getId().getRegioncode());
					param.put("d", data.getId().getDivisioncode());
					row = (Tbdepedcodes)dbServiceCIF.executeUniqueHQLQueryMaxResultOne
							("FROM Tbdepedcodes a WHERE a.id.regioncode=:r AND a.id.divisioncode=:d", param);
				}
			}else if(saveOrupdate.equalsIgnoreCase("AddSTATION")){
				if(data.getId().getRegioncode()!=null && data.getId().getDivisioncode()!=null && data.getId().getStationcode()!=null){
					param.put("r", data.getId().getRegioncode());
					param.put("d", data.getId().getDivisioncode());
					param.put("s", data.getId().getStationcode());
					row = (Tbdepedcodes)dbServiceCIF.executeUniqueHQLQueryMaxResultOne
						("FROM Tbdepedcodes a WHERE a.id.regioncode=:r AND a.id.divisioncode=:d AND a.id.stationcode=:s", param);				
					}				
			}else if(saveOrupdate.equalsIgnoreCase("UPDATE")){
				System.out.println(">>>>>>>>>>>> UPDATING");
//				if(data.getId().getRegioncode()!=null && data.getId().getDivisioncode()!=null && data.getId().getStationcode()!=null){
					param.put("r", data.getId().getRegioncode());
					param.put("d", data.getId().getDivisioncode());
					param.put("s", data.getId().getStationcode());
					param.put("rname", data.getRegionname());
					param.put("dname", data.getDivisionname());
					param.put("sname", data.getStationname());
					param.put("user", UserUtil.securityService.getUserName());
					System.out.println(param.get("rname"));
					String query = "";
					if(data.getId().getRegioncode()!=null && data.getId().getDivisioncode()==null &&
							data.getId().getStationcode()==null ) {
						query = "UPDATE Tbdepedcodes a SET a.regionname =:rname, a.updatedby =:user, a.dateupdated = GETDATE() "
								+ "WHERE a.id.regioncode=:r";
					}else if(data.getId().getRegioncode()!=null && data.getId().getDivisioncode()!=null &&
							data.getId().getStationcode()==null ) {
						query = "UPDATE Tbdepedcodes a SET a.id.regioncode =:r, a.regionname =:rname,"
								+ "a.divisionname =:dname, a.updatedby =:user, a.dateupdated = GETDATE() "
								+ "WHERE a.id.divisioncode=:d";
					}else if(data.getId().getRegioncode()!=null && data.getId().getDivisioncode()!=null &&
							data.getId().getStationcode()!=null ) {
						query = "UPDATE Tbdepedcodes a SET a.id.regioncode =:r, a.regionname =:rname,"
								+ "a.id.divisioncode =:d, a.divisionname =:dname,"
								+ "a.stationname =:sname, a.updatedby =:user, a.dateupdated = GETDATE() "
								+ "WHERE a.id.stationcode=:s";
					}
					System.out.println(query);
					if(dbServiceCIF.executeHQLUpdate(query, param)>0) {
						return flag = "success";
					}else {
						return flag = "error";
					}
//					row = (Tbdepedcodes)dbServiceCIF.executeUniqueHQLQueryMaxResultOne
//							("FROM Tbdepedcodes a WHERE a.id.regioncode=:r AND a.id.divisioncode=:d AND a.id.stationcode=:s", param);
//					if(row!=null){
//						row.setRegionname(data.getRegionname());
//						row.setDivisionname(data.getDivisionname());
//						row.setStationname(data.getStationname());
//						row.setDateupdated(new Date());
//						row.setUpdatedby(UserUtil.securityService.getUserName());
//						if(dbservice.saveOrUpdate(row)){
//							return flag = "success";
//						}
//					}					
//				}
			}
//			System.out.println(">>>>>>>>>>>>>>>>>>> region - " + data.getId().getRegioncode());
//			System.out.println(">>>>>>>>>>>>>>>>>>> division - " + data.getId().getDivisioncode());
//			System.out.println(">>>>>>>>>>>>>>>>>>> station - " + data.getId().getStationcode());
//			System.out.println(">>>>>>>>>>>>>>>>>>> row - " + row);
//			System.out.println(">>>>>>>>>>>>>>>>>>> saveOrupdate - " + saveOrupdate);
			if(row!=null){
				System.out.println(">>>>>>>>>>>>>>>>>>> existing");
				return flag = "existing";
			}else{
				System.out.println(">>>>>>>>>>>>>>>>>>> creating new record");
				// create new deped code
				Tbdepedcodes d = new Tbdepedcodes();
				TbdepedcodesId dpId = new TbdepedcodesId();
				dpId.setRegioncode(data.getId().getRegioncode());
				dpId.setDivisioncode(data.getId().getDivisioncode()==null?"":data.getId().getDivisioncode());
				dpId.setStationcode(data.getId().getStationcode()==null?"":data.getId().getStationcode());
				d.setId(dpId);
				d.setRegionname(data.getRegionname());
				d.setDivisionname(data.getDivisionname());
				d.setStationname(data.getStationname());
				d.setDatecreated(new Date());
				d.setCreatedby(UserUtil.securityService.getUserName());
				if(dbServiceCIF.save(d)){
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

    /************************************************************************************* 
     *************************************************************************************START OF DEPED CODES*******/
	/***********REGION***********/
	@Override
	public String saveOrupdateRegion(Tbcodetable data, String addOrupdate) {
		String flag = "failed";
		DBService dbServiceCIF = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();	
		try {
			if(addOrupdate.equalsIgnoreCase("AddREGION")){
				if(data.getId().getCodename().equalsIgnoreCase("DEPEDREGION") && data.getId().getCodevalue()!=null && data.getDesc1()!=null){
//					System.out.println(">>>>>>>>>>>>>>>>>>> AddREGION");
					param.put("regioncode", data.getId().getCodevalue());
					param.put("cdn", data.getId().getCodename());
					Tbcodetable row = (Tbcodetable) dbServiceCIF.executeUniqueHQLQueryMaxResultOne
							("FROM Tbcodetable a WHERE a.id.codename=:cdn AND a.id.codevalue=:regioncode", param);
					if(row!=null){
//						System.out.println(">>>>>>>>>>>>>>>>>>> existing");
						return flag = "existing";
					}else{
						//create new record
//						System.out.println(">>>>>>>>>>>>>>>>>>> creating new record");
						Tbcodetable codetable = new Tbcodetable();
						TbcodetableId ctId = new TbcodetableId();
						ctId.setCodename(data.getId().getCodename());
						ctId.setCodevalue(data.getId().getCodevalue());
						codetable.setId(ctId);
						codetable.setDesc1(data.getDesc1());
						codetable.setDesc2(data.getDesc2());
						codetable.setRemarks(data.getRemarks());
						codetable.setCreatedby(UserUtil.securityService.getUserName());
						codetable.setCreateddate(new Date());			
						if(dbServiceCIF.save(codetable)){
							return flag = "success";
						}
					}
				}
			}else if(addOrupdate.equalsIgnoreCase("UPDATE")){
				if(data.getId().getCodename()!=null && data.getId().getCodevalue()!=null && data.getDesc1()!=null){
					System.out.println(">>>>>>>>>>>>>>>>>>> UPDATE");
					param.put("regioncode", data.getId().getCodevalue());
					param.put("cdn", data.getId().getCodename());
					Tbcodetable row = (Tbcodetable) dbServiceCIF.executeUniqueHQLQueryMaxResultOne
							("FROM Tbcodetable a WHERE a.id.codename=:cdn AND a.id.codevalue=:regioncode", param);
					if(row!=null){
//						System.out.println(">>>>>>>>>>>>>>>>>>> updating");
						row.setDesc1(data.getDesc1());
						if(dbServiceCIF.saveOrUpdate(row)){
							Integer list = (Integer) dbServiceCIF.executeUniqueSQLQuery("SELECT COUNT(*) FROM Tbdepedcodes WHERE regioncode=:regioncode", param);
//							System.out.println(">>>>>>>>>> updating deped table");
//							System.out.println(">>>>>>>>>> list - " + list);
							if(list!=null && list > 0){
//								System.out.println(">>>>>>>>>> LIST !=NULL");
//								System.out.println(">>>>>>>>>> regionname - " +data.getDesc1());
//								System.out.println(">>>>>>>>>> regioncode - " +data.getId().getCodevalue());
								String q = "UPDATE Tbdepedcodes SET regionname ='"+data.getDesc1()+"' WHERE regioncode="+data.getId().getCodevalue()+"";
								Integer res = dbServiceCIF.executeUpdate(q, param);
								if(res!=null && res > 0){
									return flag = "success";
								}								
							}else{
								return flag = "success";
							}
						}
					}					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcodetable> searchRegion(String regionname) {
		DBService dbServiceCIF = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();	
		List<Tbcodetable> list = new ArrayList<Tbcodetable>();
		try {
			if(regionname!=null){
				param.put("rn", regionname);
				list = (List<Tbcodetable>) dbServiceCIF.executeListHQLQuery
						("FROM Tbcodetable a WHERE a.id.codename='DEPEDREGION' AND desc1=:rn", param); 
			}else{
				list = (List<Tbcodetable>) dbServiceCIF.executeListHQLQuery("FROM Tbcodetable a WHERE a.id.codename='DEPEDREGION'", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/***********DIVISION***********/
	@Override
	public String saveOrupdateDivision(Tbcodetable data, String addOrupdate) {
		String flag = "failed";
		DBService dbServiceCIF = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();	
		try {
			if(addOrupdate.equalsIgnoreCase("AddDIVISION")){
				if(data.getId().getCodename().equalsIgnoreCase("DEPEDDIVISION") && data.getId().getCodevalue()!=null 
						&& data.getDesc1()!=null && data.getDesc2()!=null){
					//System.out.println(">>>>>>>>>>>>>>>>>>> AddDIVISION");
					param.put("divisioncode", data.getId().getCodevalue());
					param.put("cdn", data.getId().getCodename());
					param.put("regioncode", data.getDesc2());
					Tbcodetable row = (Tbcodetable) dbServiceCIF.executeUniqueHQLQueryMaxResultOne
							("FROM Tbcodetable a WHERE a.id.codename=:cdn AND a.id.codevalue=:divisioncode AND desc2=:regioncode", param);
					if(row!=null){
						//System.out.println(">>>>>>>>>>>>>>>>>>> existing");
						return flag = "existing";
					}else{
						//create new record
						//System.out.println(">>>>>>>>>>>>>>>>>>> creating new record");
						Tbcodetable codetable = new Tbcodetable();
						TbcodetableId ctId = new TbcodetableId();
						ctId.setCodename(data.getId().getCodename());
						ctId.setCodevalue(data.getId().getCodevalue());
						codetable.setId(ctId);
						codetable.setDesc1(data.getDesc1());
						codetable.setDesc2(data.getDesc2());
						codetable.setRemarks(data.getRemarks());
						codetable.setCreatedby(UserUtil.securityService.getUserName());
						codetable.setCreateddate(new Date());			
						if(dbServiceCIF.save(codetable)){
							return flag = "success";
						}
					}					
				}
			}else if(addOrupdate.equalsIgnoreCase("UPDATE")){
				if(data.getId().getCodename().equalsIgnoreCase("DEPEDDIVISION") && data.getId().getCodevalue()!=null 
						&& data.getDesc1()!=null && data.getDesc2()!=null){
//					System.out.println(">>>>>>>>>>>>>>>>>>> UPDATE");
					param.put("divisioncode", data.getId().getCodevalue());
					param.put("cdn", data.getId().getCodename());
					Tbcodetable row = (Tbcodetable) dbServiceCIF.executeUniqueHQLQueryMaxResultOne
							("FROM Tbcodetable a WHERE a.id.codename=:cdn AND a.id.codevalue=:divisioncode", param);
					if(row!=null){
//						System.out.println(">>>>>>>>>>>>>>>>>>> updating");
						row.setDesc1(data.getDesc1());
						if(dbServiceCIF.saveOrUpdate(row)){
							Integer list = (Integer) dbServiceCIF.executeUniqueSQLQuery("SELECT COUNT(*) FROM Tbdepedcodes WHERE divisioncode=:divisioncode", param);
//							System.out.println(">>>>>>>>>> updating deped table");
//							System.out.println(">>>>>>>>>> list - " + list);
							if(list!=null && list > 0){
//								System.out.println(">>>>>>>>>> LIST !=NULL");
//								System.out.println(">>>>>>>>>> divisionname - " +data.getDesc1());
//								System.out.println(">>>>>>>>>> divisioncode - " +data.getId().getCodevalue());
								String q = "UPDATE Tbdepedcodes SET divisionname ='"+data.getDesc1()+"' WHERE divisioncode="+data.getId().getCodevalue()+"";
								Integer res = dbServiceCIF.executeUpdate(q, param);
								if(res!=null && res > 0){
									return flag = "success";
								}								
							}else{
								return flag = "success";
							}
						}
					}							
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CodetableForm> searchDivisionByRegionCode(String regioncode) {
		DBService dbServiceCIF = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();	
		List<Tbcodetable> list = new ArrayList<Tbcodetable>();	
		List<CodetableForm> cdlist = new ArrayList<CodetableForm>();	
		try {
			//System.out.println(">>>>>>>>>>>>>>>>>>> running searchDivisionByRegionCode, regioncode - " + regioncode);
			if(regioncode!=null){
				param.put("rcode", regioncode);
				list = (List<Tbcodetable>) dbServiceCIF.executeListHQLQuery
						("FROM Tbcodetable a WHERE a.id.codename='DEPEDDIVISION' AND desc2=:rcode", param);
			}
			if(list != null) {
				for(Tbcodetable ct: list) {
					CodetableForm form = new CodetableForm();
					form.setCodename(ct.getId().getCodename());
					form.setCodevalue(ct.getId().getCodevalue());
					form.setDesc1(ct.getDesc1());
					form.setDesc2(ct.getDesc2());
					form.setRemarks(ct.getRemarks());
					form.setCreateddate(ct.getCreateddate());
					form.setCreatedby(ct.getCreatedby());
					cdlist.add(form);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cdlist;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcodetable> searchDivision(String regioncode, String divisioncode) {
		DBService dbServiceCIF = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();	
		List<Tbcodetable> list = new ArrayList<Tbcodetable>();
		try {
			if(regioncode==null && divisioncode==null){
				list = (List<Tbcodetable>) dbServiceCIF.executeListHQLQuery
						("FROM Tbcodetable a WHERE a.id.codename='DEPEDDIVISION'", null);
			}else if(regioncode!=null && divisioncode==null){
				param.put("regioncode", regioncode);
				list = (List<Tbcodetable>) dbServiceCIF.executeListHQLQuery
						("FROM Tbcodetable a WHERE a.id.codename='DEPEDDIVISION' AND desc2=:regioncode", param);
			}else if(regioncode==null && divisioncode!=null){
//				param.put("divisioncode", divisioncode);
//				list = (List<Tbcodetable>) dbServiceCIF.executeListHQLQuery
//						("FROM Tbcodetable a WHERE a.id.codename='DEPEDDIVISION' AND desc1=:divisioncode", param);
			}else if(regioncode!=null && divisioncode!=null){
				param.put("regioncode", regioncode);
				param.put("divisioncode", divisioncode);
				list = (List<Tbcodetable>) dbServiceCIF.executeListHQLQuery
						("FROM Tbcodetable a WHERE a.id.codename='DEPEDDIVISION' AND desc1=:divisioncode AND desc2=:regioncode", param);
			}
			if(list!=null){
				for(Tbcodetable c : list){
					if(c.getDesc2()!=null){
						param.put("regioncode", c.getDesc2());
						Tbcodetable row = (Tbcodetable) dbServiceCIF.executeUniqueHQLQueryMaxResultOne
								("FROM Tbcodetable a WHERE a.id.codename='DEPEDREGION' AND a.id.codevalue=:regioncode", param);
						if(row!=null){
							c.setDesc2(row.getDesc1()); // Region Name
							c.setRemarks(row.getId().getCodevalue()); // Region Code
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
