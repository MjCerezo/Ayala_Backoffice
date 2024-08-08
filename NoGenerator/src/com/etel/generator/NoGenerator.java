package com.etel.generator;

import com.etel.utils.CIFNoGenerator;
import com.etel.utils.IDNoGenerator;
import com.etel.utils.MngEmpGenerator;
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
public class NoGenerator extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public NoGenerator() {
//       super(INFO);
    }
    
    //Generate CIF No for Indiv
    public String generateCIFNoIndiv(){
 	   String no = CIFNoGenerator.generateCIFNo("INDIVIDUAL");
// 	   System.out.println(">>>>>>>>>>>>>>> GENERATED INDIV CIF No - : " + no);
 	   return no;
   }

    //Generate CIF No for Corp
    public String generateCIFNoCorp(){
 	   String no = CIFNoGenerator.generateCIFNo("CORPORATE");
// 	   System.out.println(">>>>>>>>>>>>>>> GENERATED CORP CIF No - : " + no);
 	   return no;
   }
    
    //Generate CIF No for Sole Prop
    public String generateCIFNoSoleProp(){
 	   String no = CIFNoGenerator.generateCIFNo("SOLEPROPRIETORSHIP");
 	   return no;
   }   

	//Generate BlacklistID
	public String generateAMLAID(){
	   String no = IDNoGenerator.generateIdNo("AMLA");
//	   System.out.println(">>>>>>>>>>>>>>> GENERATED AMLA ID No - : " + no);
	   return no;
	}

	//Generate BlacklistID
	public String generateBlacklistID(){
	   String no = IDNoGenerator.generateIdNo("BLACKLIST");
//	   System.out.println(">>>>>>>>>>>>>>> GENERATED BLACKLIST ID No - : " + no);
	   return no;
	}
    
	//Generate Management
	public String generateMngID(){
	   String no = MngEmpGenerator.generateMngEmpNo("MANAGEMENT");
	   return no;
	}

	//Generate Employment
	public String generateEmpID(){
	   String no = MngEmpGenerator.generateMngEmpNo("EMPLOYMENT");
	   return no;
	}
	
	//Generate BusinessID
	public String generateBusID(){
	   String no = MngEmpGenerator.generateMngEmpNo("BUSINESS");
	   return no;
	}
	
	//Generate Member UserName 
	public String generateMemberId(String productType){
	   String memberId = CIFNoGenerator.generateMemberId(productType);
	   return memberId;
	}
	
	//Generate Company Code 
	public String generateCompanyCode(String companyName){
	   String no = CIFNoGenerator.generateCompanyCode(companyName);
	   return no;
	}
	
	//Generate Member UserName 
	public String generateMemberUserName(String cifNo){
	   String username = CIFNoGenerator.generateMemberUserName(cifNo);
	   return username;
	}

}
