/**
 * 
 */
package com.etel.agent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbagents;
import com.etel.agent.forms.AgentForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.util.HQLUtil;
import com.etel.utils.UserUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

/**
 * @author ETEL-LAPTOP19
 *
 */
public class AgentServiceImpl implements AgentService {
	

	DBService dbService = new DBServiceImpl();
	Map<String, Object> param = HQLUtil.getMap();
	SecurityService service = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");

	@Override
	public String saveAgent(Tbagents agent,String beingUpdated) {
		try {
			System.out.println(beingUpdated);
			if(beingUpdated.contains("false"))
			{
				agent.setCreatedby(service.getUserName());
				agent.setDatecreated(new Date());
			}
			else{
				agent.setUpdatedby(service.getUserName());
				agent.setDateupdated(new Date());
			}
		
			dbService.saveOrUpdate(agent);
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "failed";
	}
	
	@Override
	public String deleteAgent(String agentcode, String companycode) {
		try {
			param.put("agentcode", agentcode);
			param.put("companycode", companycode);
			dbService.getSQLMaxId("DELETE FROM Tbagent WHERE agentcode =:agentcode and companycode =:companycode", param);
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "failed";
	}
	@Override
	public List<Tbagents> listAgent(String agentcode, String name, String companycode) {
		List<Tbagents> agents = new ArrayList<Tbagents>();
		String qry = "FROM Tbagents where companycode =:companycode ";
		try {
//			param.put("companycode", UserUtil.getUserByUsername(service.getUserName()).getCompanycode());
			param.put("companycode", companycode);
			if(agentcode != null && !agentcode.equals("")) {
				param.put("agentcode", agentcode);	
				qry = "AND agentcode =:agentcode";
			} else if (name != null && !name.equals("")) {
				param.put("name", name);
				qry = "AND (firstname like:name OR middlename like:name OR lastname like:name)";
			}
			agents = (List<Tbagents>) dbService.executeListHQLQuery(qry, param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return agents;
	}

	@Override
	public List<AgentForm> listAgents(String agentcode, String name, String companycode) {
		// TODO Auto-generated method stub
		List<AgentForm> list = new ArrayList<AgentForm>();
		try {
			List<Tbagents> agents = listAgent(agentcode, name, companycode);
			for(Tbagents a : agents) {
				AgentForm f = new AgentForm();
				f.setAgentcode(a.getId().getAgentcode());
				f.setBankacctno(a.getBankacctno());
				f.setBarangay(a.getBarangay());
				f.setCity(a.getCity());
				f.setCompanycode(a.getId().getCompanycode());
				f.setCountry(a.getCountry());
				f.setFirstname(a.getFirstname());
				f.setLastname(a.getLastname());
				f.setMiddlename(a.getMiddlename());
				f.setPostalcode(a.getPostalcode());
				f.setRegion(a.getRegion());
				f.setStateprovince(a.getStateprovince());
				f.setStreetnoname(a.getStreetnoname());
				f.setSubdivision(a.getSubdivision());
				f.setSuffix(a.getSuffix());
				list.add(f);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return list;
	}
}
