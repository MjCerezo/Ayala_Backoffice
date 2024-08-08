/**
 * 
 */
package com.etel.vault;

import java.util.Date;
import java.util.Map;

import javax.xml.registry.infomodel.User;

import org.apache.log4j.Logger;

import com.coopdb.data.Tbuser;
import com.coopdb.data.Tbvault;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.company.CompanyServiceImpl;
import com.etel.util.HQLUtil;
import com.etel.utils.UserUtil;
import com.mysql.jdbc.log.Slf4JLogger;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

/**
 * @author ETEL-LAPTOP19
 *
 */
public class VaultServiceImpl implements VaultService {

	DBService dbSrvc = new DBServiceImpl();
	Map<String, Object> param = HQLUtil.getMap();
	SecurityService serviceS = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	
	@Override
	public Tbvault findVaultbyCoopCodeAndBranchCode(String coopcode, String branchcode) {
		// TODO find vault by userid
		Tbuser user = UserUtil.getUserByUsername(serviceS.getUserName());
		Tbvault vault = new Tbvault();
		try {
			param.put("coopcode", user.getCoopcode());
			param.put("branchcode", user.getBranchcode());
			vault = (Tbvault) dbSrvc.executeUniqueHQLQuery("FROM Tbvault WHERE coopcode =:coopcode AND branchcode =:branchcode", param);
			if(vault == null) {
				System.out.println("Vault not yet created.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vault;
	}
	@Override
	public String createVault(Tbvault vault) {
		// TODO create vault
		param.put("amount", vault.getNetamount());
		param.put("id", vault.getId());
		param.put("username", serviceS.getUserName());
		try {
			Tbuser user = UserUtil.getUserByUsername(serviceS.getUserName());
			vault.setCoopcode(user.getCoopcode());
			vault.setBranchcode(user.getBranchcode());
			vault.setCreatedby(user.getUsername());
			vault.setDatecreated(new Date());
			if(dbSrvc.save(vault) && (Integer)dbSrvc.execStoredProc("UPDATE TBNETAMT SET userbalance =:amount WHERE " + 
					"						userid = (SELECT userid FROM TBUSER WHERE username=:username) AND currency = 'PHP' and transfertype='1'", param, null, 2, null)>0) {
				return "Vault created successfully!";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "Error occured during process.";
	}
	@Override
	public String updateVault(Tbvault vault) {
		// TODO update vault
		param.put("amount", vault.getNetamount());
		param.put("id", vault.getId());
		param.put("username", serviceS.getUserName());
		try {
//			System.out.println(serviceS.getUserName() + " << USERNAME");
			if((Integer)dbSrvc.execStoredProc("UPDATE TBVAULT SET netamount =:amount, updatedby =:username, lastupdated = GETDATE() "
					+ "WHERE id =:id", param, null, 2, null)>0
					&& (Integer)dbSrvc.execStoredProc("UPDATE TBNETAMT SET userbalance =:amount WHERE " + 
							"						userid = (SELECT userid FROM TBUSER WHERE username=:username) AND currency = 'PHP' and transfertype='1'", param, null, 2, null)>0) {
				return "Vault updated successfully!";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "Error occured during process.";
	}
	
	
}
