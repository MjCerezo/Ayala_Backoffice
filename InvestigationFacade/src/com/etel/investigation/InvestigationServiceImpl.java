package com.etel.investigation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbcollateralmain;
import com.coopdb.data.Tbinvestigationresults;
import com.coopdb.data.Tblstapp;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;

public class InvestigationServiceImpl implements InvestigationService {

	private DBService dbServiceCOOP = new DBServiceImpl();
	private Map<String, Object> params = HQLUtil.getMap();

	@Override
	public String saveOrUpdateInvestigation(Tbinvestigationresults inv, String invtype) {
		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		Tbcollateralmain colmain = new Tbcollateralmain();

		param.put("appno", inv.getAppno());
		System.out.print("MAR : " + inv.getCollateralrefno() + " END");
		try {
			Tblstapp app = (Tblstapp) dbService.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", param);
			if (inv.getId() == null) {
				if (dbServiceCOOP.saveOrUpdate(inv)) {
					flag = "success";
				}
			} else {
				if (invtype != null) {
					params.put("id", inv.getId());
					Tbinvestigationresults invres = (Tbinvestigationresults) dbServiceCOOP
							.executeUniqueHQLQuery("FROM Tbinvestigationresults WHERE id=:id", params);

					if (invres != null) {
						if (invtype.equals("BI")) {
							invres.setBibapnfis(inv.getBibapnfis());
							invres.setBibapnfisremarks(inv.getBibapnfisremarks());
							invres.setBicmap(inv.getBicmap());
							invres.setBicmapremarks(inv.getBicmapremarks());
							invres.setBicic(inv.getBicic());
							invres.setBicicremarks(inv.getBicicremarks());
							invres.setBiblacklistinternal(inv.getBiblacklistinternal());
							invres.setBiblacklistinternalremarks(inv.getBiblacklistinternalremarks());
							invres.setBiblacklistexternal(inv.getBiblacklistexternal());
							invres.setBiblacklistexternalremarks(inv.getBiblacklistexternalremarks());
							invres.setBiamla(inv.getBiamla());
							invres.setBiamlaremarks(inv.getBiamlaremarks());
							// ADDED 05.05.2021
							invres.setBibapnfisfacilitatedby(inv.getBibapnfisfacilitatedby());
							invres.setBibapnfisdatefacilitated(inv.getBibapnfisdatefacilitated());
							invres.setBicmapfacilitatedby(inv.getBicmapfacilitatedby());
							invres.setBicmapdatefacilitated(inv.getBicmapdatefacilitated());
							invres.setBicicfacilitatedby(inv.getBicicfacilitatedby());
							invres.setBicicdatefacilitated(inv.getBicicdatefacilitated());
							invres.setBiblacklistexternalfacilitatedby(inv.getBiblacklistexternalfacilitatedby());
							invres.setBiblacklistexternaldatefacilitated(inv.getBiblacklistexternaldatefacilitated());
							invres.setBiblacklistinternalfacilitatedby(inv.getBiblacklistinternalfacilitatedby());
							invres.setBiblacklistinternaldatefacilitated(inv.getBiblacklistinternaldatefacilitated());
							invres.setBiamlafacilitatedby(inv.getBiamlafacilitatedby());
							invres.setBiamladatefacilitated(inv.getBiamladatefacilitated());

							Integer bicompleted = (Integer) dbService.executeUniqueSQLQuery("select IIF(count(*)-1> "
									+ "(select count(*) from tbinvestigationresults where appno=:appno "
									+ "and reporttype='BI' and updatedby is not null),1,0) "
									+ "from tbinvestigationresults where appno=:appno "
									+ "and reporttype='BI' and updatedby is null", params);
							app.setIsbicompleted(bicompleted == 1 ? false : true);
							dbService.saveOrUpdate(app);
						}
						if (invtype.equals("CI")) {
							invres.setCipdrn(inv.getCipdrn());
							invres.setCipdrnremarks(inv.getCipdrnremarks());
							invres.setCievr(inv.getCievr());
							invres.setCievrremarks(inv.getCievrremarks());
							invres.setCibvr(inv.getCibvr());
							invres.setCibvrremarks(inv.getCibvrremarks());
							invres.setCibankcheck(inv.getCibankcheck());
							invres.setCibankcheckremarks(inv.getCibankcheckremarks());
							invres.setCicreditcheck(inv.getCicreditcheck());
							invres.setCicreditcheckremarks(inv.getCicreditcheckremarks());
							invres.setCitradecheck(inv.getCitradecheck());
							invres.setCitradecheckremarks(inv.getCitradecheckremarks());
							// ADDED 05.05.2021
							invres.setCipdrnfacilitatedby(inv.getCipdrnfacilitatedby());
							invres.setCipdrndatefacilitated(inv.getCipdrndatefacilitated());
							invres.setCievrfacilitatedby(inv.getCievrfacilitatedby());
							invres.setCievrdatefacilitated(inv.getCievrdatefacilitated());
							invres.setCibvrfacilitatedby(inv.getCibvrfacilitatedby());
							invres.setCibvrdatefacilitated(inv.getCibvrdatefacilitated());
							invres.setCibankcheckfacilitatedby(inv.getCibankcheckfacilitatedby());
							invres.setCibankcheckdatefacilitated(inv.getCibankcheckdatefacilitated());
							invres.setCicreditcheckfacilitatedby(inv.getCicreditcheckfacilitatedby());
							invres.setCicreditcheckdatefacilitated(inv.getCicreditcheckdatefacilitated());
							invres.setCitradecheckfacilitatedby(inv.getCitradecheckfacilitatedby());
							invres.setCitradecheckdatefacilitated(inv.getCitradecheckdatefacilitated());
							Integer cicompleted = (Integer) dbService.executeUniqueSQLQuery("select IIF(count(*)-1> "
									+ "(select count(*) from tbinvestigationresults where appno=:appno "
									+ "and reporttype='CI' and updatedby is not null),1,0) "
									+ "from tbinvestigationresults where appno=:appno "
									+ "and reporttype='CI' and updatedby is null", params);
							app.setIscicompleted(cicompleted == 1 ? false : true);
							dbService.saveOrUpdate(app);
						}
						if (invtype.equals("APPR")) {

							param.put("referenceno", inv.getCollateralrefno());
							colmain = (Tbcollateralmain) dbServiceCOOP.executeUniqueHQLQuery(
									"FROM Tbcollateralmain where referenceno =:referenceno ", param);
							colmain.setAppraisalstatus("Completed");
							colmain.setDateoflastappraisal(inv.getLastappraisaldate());

//							invres.setCollateralrefno(inv.getCollateralrefno());
//							invres.setCollateraltype(inv.getCollateraltype());
							invres.setLastappraisaldate(inv.getLastappraisaldate());
							invres.setAppraisedby(inv.getAppraisedby());
							invres.setAppraisalstatus(inv.getAppraisalstatus());
							invres.setAppraisedvalue(inv.getAppraisedvalue());
							invres.setLoanablevalue(inv.getLoanablevalue());
							invres.setSellingprice(inv.getSellingprice());
							invres.setAvrate(inv.getAvrate());
							invres.setLvrate(inv.getLvrate());
							Integer cacompleted = (Integer) dbService.executeUniqueSQLQuery("select IIF(count(*)-1> "
									+ "(select count(*) from tbinvestigationresults where appno=:appno "
									+ "and reporttype='APPR' and updatedby is not null),1,0) "
									+ "from tbinvestigationresults where appno=:appno "
									+ "and reporttype='APPR' and updatedby is null", params);
							app.setIsappraisalcompleted(cacompleted == 1 ? false : true);
							dbService.saveOrUpdate(app);
							dbServiceCOOP.saveOrUpdate(colmain);
						}

						invres.setDateupdated(new Date());
						invres.setUpdatedby(UserUtil.securityService.getUserName());
						if (dbServiceCOOP.saveOrUpdate(invres)) {
							flag = "success";
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
	public List<Tbinvestigationresults> getInvestigationResList(String appno, String cifno, String invtype) {
		List<Tbinvestigationresults> list = new ArrayList<Tbinvestigationresults>();
		try {
			String strQuery = "FROM Tbinvestigationresults WHERE id IS NOT NULL";
			if (appno != null) {
				params.put("appno", appno);
				strQuery += " AND appno=:appno";
			}
			if (cifno != null) {
				params.put("cifno", cifno);
				strQuery += " AND cifno=:cifno";
			}
			if (invtype != null) {
				params.put("invtype", invtype);
				strQuery += " AND reporttype=:invtype";
			}
			list = (List<Tbinvestigationresults>) dbServiceCOOP.executeListHQLQuery(strQuery, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String generateInvestigationReport(String appno) {
		String flag = "failed";
		try {
			if (appno != null) {
				params.put("appno", appno);
				params.put("username", UserUtil.securityService.getUserName());
				String res = (String) dbServiceCOOP.execSQLQueryTransformer(
						"EXEC sp_GenerateInvestigationRpt @appno=:appno, @username=:username", params, null, 0);
				if (res.equals("success")) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Tbinvestigationresults getInvestigationResPerType(String appno, String cifno, String invtype,
			String participationcode) {
		Tbinvestigationresults inv = new Tbinvestigationresults();
		try {
			String strQuery = "FROM Tbinvestigationresults WHERE id IS NOT NULL";
			if (appno != null) {
				params.put("appno", appno);
				strQuery += " AND appno=:appno";
			}
			if (cifno != null) {
				params.put("cifno", cifno);
				strQuery += " AND cifno=:cifno";
			}
			if (invtype != null) {
				params.put("invtype", invtype);
				strQuery += " AND reporttype=:invtype";
			}
			if (participationcode != null) {
				params.put("participationcode", participationcode);
				strQuery += " AND participationcode=:participationcode";
			}
			inv = (Tbinvestigationresults) dbServiceCOOP.executeUniqueHQLQueryMaxResultOne(strQuery, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inv;
	}
}
