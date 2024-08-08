package com.etel.history;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbhistory;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.utils.HQLUtil;
import com.etel.utils.LoggerUtil;
import com.etel.utils.UserUtil;

public class HistoryServiceImpl implements HistoryService {

	private DBService dbServiceCOOP = new DBServiceImpl();
	private Map<String, Object> params = HQLUtil.getMap();

	@Override
	public String addHistory(String cifno, String description, String remarks) {
		Tbhistory h = new Tbhistory();
		DBService dbService = new DBServiceImpl();

		try {
			if (cifno != null) {
				h.setCifno(cifno);
				h.setEventdescription(description);
				h.setHistorydatetime(new Date());
				h.setUsername(UserUtil.securityService.getUserName());
				h.setRemarks(remarks);
				dbService.save(h);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbhistory> listHistory(String cifno) {
		List<Tbhistory> list = new ArrayList<Tbhistory>();
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (cifno != null) {
				params.put("cifno", cifno);
				list = (List<Tbhistory>) dbService.executeListHQLQuery("FROM Tbhistory WHERE cifno=:cifno", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// MAR 10-13-2020
	@Override
	public String saveHistory(String appno, Integer eventid, String eventdescription) {
		String flag = "failed";
		Tbhistory h = new Tbhistory();
		try {
			if (appno != null && eventid != null && eventdescription != null) {
				params.put("eventid", eventid);
				String eventname = (String) dbServiceCOOP
						.executeUniqueSQLQuery("SELECT eventname FROM TBAUDITEVENTS WHERE eventid=:eventid", params);

				// h.setAppno(appno);
				h.setUsername(UserUtil.securityService.getUserName());
				h.setHistorydatetime(new Date());
				h.setId(eventid);
				h.setEventname(eventname);
				h.setEventdescription(eventdescription);
				// h.setIpaddress(UserUtil.getUserIp());
				if (dbServiceCOOP.save(h)) {
					flag = "success";
				}
			} else {
				LoggerUtil.error(">>>>>>>>Required parameters cannot be empty !", this.getClass());
			}
		} catch (Exception e) {
			e.printStackTrace();
			LoggerUtil.exceptionError(e, this.getClass());
		}
		return flag;
	}

}
