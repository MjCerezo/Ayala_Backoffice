/**
 * 
 */
package com.etel.vault;

import com.coopdb.data.Tbvault;

/**
 * @author ETEL-LAPTOP19
 *
 */
public interface VaultService {
	public Tbvault findVaultbyCoopCodeAndBranchCode(String coopcode, String branchcode);
	public String createVault(Tbvault vault);
	public String updateVault(Tbvault vault);
}
