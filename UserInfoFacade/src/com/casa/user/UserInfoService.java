package com.casa.user;

import java.math.BigDecimal;

import com.casa.user.forms.UserDetailForm;
import com.coopdb.data.Tbbranch;
import com.coopdb.data.Tbunit;

public interface UserInfoService {

	UserDetailForm getUserinfo(String userid);
	BigDecimal getUnitBalance(String userid, String currency, String transfertype);
	Tbbranch getUnitinfo();
	
}

