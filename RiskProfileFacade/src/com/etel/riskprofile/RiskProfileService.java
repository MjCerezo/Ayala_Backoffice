package com.etel.riskprofile;

import java.util.List;

import com.coopdb.data.Tbriskprofile;


public interface RiskProfileService {

	String addRiskProfile1 (Tbriskprofile risk);
	List<Tbriskprofile> getRiskProfile(Integer id);

}
