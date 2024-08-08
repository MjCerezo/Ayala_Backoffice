/**
 * 
 */
package com.etel.agent;

import java.util.List;

import com.coopdb.data.Tbagents;
import com.etel.agent.forms.AgentForm;

/**
 * @author ETEL-LAPTOP19
 *
 */
public interface AgentService {

	String deleteAgent(String agentcode, String companycode);

	String saveAgent(Tbagents agent,String beingUpdated);

	List<Tbagents> listAgent(String agentcode, String name, String companycode);

	List<AgentForm> listAgents(String agentcode, String name, String companycode);

}
