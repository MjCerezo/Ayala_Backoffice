/**
 * 
 */
package com.etel.depositproductinterest;

import java.util.List;

import com.coopdb.data.Tbdepositproductinterestrate;

/**
 * @author ETEL-LAPTOP19
 *
 */
public interface DepositProductInterestService {

	String saveOrUpdateProductInterest(Tbdepositproductinterestrate depositproductinterestrate);

	List<Tbdepositproductinterestrate> listDepositProductInterestRates(
			Tbdepositproductinterestrate depositproductinterestrate);

}
