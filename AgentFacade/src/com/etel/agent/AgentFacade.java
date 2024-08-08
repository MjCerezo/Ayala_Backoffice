package com.etel.agent;

import java.util.ArrayList;
import java.util.List;

import com.coopdb.data.Tbagents;
import com.etel.agent.forms.AgentForm;
import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
import com.wavemaker.runtime.service.annotations.ExposeToClient;

/**
 * This is a client-facing service class. All public methods will be exposed to
 * the client. Their return values and parameters will be passed to the client
 * or taken from the client, respectively. This will be a singleton instance,
 * shared between all requests.
 * 
 * To log, call the superclass method log(LOG_LEVEL, String) or log(LOG_LEVEL,
 * String, Exception). LOG_LEVEL is one of FATAL, ERROR, WARN, INFO and DEBUG to
 * modify your log level. For info on these levels, look for tomcat/log4j
 * documentation
 */
@ExposeToClient
public class AgentFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level;
	 * recommend changing this to FATAL or ERROR before deploying. For info on these
	 * levels, look for tomcat/log4j documentation
	 */
	public AgentFacade() {
		super(INFO);
	}

	AgentService srvc = new AgentServiceImpl();

	public String saveAgent(Tbagents agent,String beingUpdated) {
		String result = null;
		try {
			log(INFO, "Starting saveAgent()");
			result = srvc.saveAgent(agent,beingUpdated);
			log(INFO, "Returning " + result);
		} catch (Exception e) {
			log(ERROR, "The saveAgent() java service operation has failed", e);
		}
		return result;
	}

	public String deleteAgent(String agentcode, String companycode) {
		String result = null;
		try {
			log(INFO, "Starting deleteAgent()");
			result = srvc.deleteAgent(agentcode, companycode);
			log(INFO, "Returning " + result);
		} catch (Exception e) {
			log(ERROR, "The deleteAgent() java service operation has failed", e);
		}
		return result;
	}
	public List<Tbagents> listAgent(String agentcode, String name, String companycode) {
		List<Tbagents> agents = new ArrayList<Tbagents>();
		try {
			log(INFO, "Starting listAgent()");
			agents =  srvc.listAgent(agentcode, name, companycode);
			log(INFO, "Returning " + agents.size());
		} catch (Exception e) {
			log(ERROR, "The listAgent() java service operation has failed", e);
		}
		return agents;
	}
	
	public List<AgentForm> listAgents(String agentcode, String name, String companycode){
		return srvc.listAgents(agentcode, name, companycode);
	}
}
