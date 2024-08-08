package com.etel.qde;

import java.util.Map;

import com.coopdb.data.Tbappbeneficiary;
import com.coopdb.data.Tbemployee;
import com.coopdb.data.Tbappemployment;
import com.coopdb.data.Tbmembershipapp;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.DateTimeUtil;
import com.etel.utils.HQLUtil;

public class GetterSetter {
	private DBService dbService = new DBServiceImpl();
	@SuppressWarnings("unused")
	private Map<String, Object> param = HQLUtil.getMap();
	//GET FROM TBEMPLOYEE | SAVE TO TBMEMBERSHIPAPP
	public Tbmembershipapp getEmployee(Tbmembershipapp get, Tbemployee emp){
		//if father is beneficiary
		if(emp.getBeneficiaryrelationship1()=="0"){
			if(get.getFatherfirstname()==null || get.getFatherlastname()==null){
//				System.out.println("NAME"+emp.getBeneficiaryfirstname1());
				get.setFatherfirstname(emp.getBeneficiaryfirstname1());
				get.setFathermiddlename(emp.getBeneficiarylmiddlename1());
				get.setFatherlastname(emp.getBeneficiarylastname1());
				get.setFatherfulladdress(emp.getBeneficiaryaddress1());
				dbService.saveOrUpdate(get);
			}
		}
		if(emp.getBeneficiaryrelationship2()=="0"){
			if(get.getFatherfirstname()==null || get.getFatherlastname()==null){
//				System.out.println("NAME"+emp.getBeneficiaryfirstname1());
				get.setFatherfirstname(emp.getBeneficiaryfirstname2());
				get.setFathermiddlename(emp.getBeneficiarylmiddlename2());
				get.setFatherlastname(emp.getBeneficiarylastname2());
				get.setFatherfulladdress(emp.getBeneficiaryaddress2());
				dbService.saveOrUpdate(get);
			}
		}
		if(emp.getBeneficiaryrelationship3()=="0"){
			if(get.getFatherfirstname()==null || get.getFatherlastname()==null){
				get.setFatherfirstname(emp.getBeneficiaryfirstname3());
				get.setFathermiddlename(emp.getBeneficiarylmiddlename3());
				get.setFatherlastname(emp.getBeneficiarylastname3());
				get.setFatherfulladdress(emp.getBeneficiaryaddress3());
				dbService.saveOrUpdate(get);
			}
		}
		if(emp.getBeneficiaryrelationship4()=="0"){
			if(get.getFatherfirstname()==null || get.getFatherlastname()==null){
				get.setFatherfirstname(emp.getBeneficiaryfirstname4());
				get.setFathermiddlename(emp.getBeneficiarylmiddlename4());
				get.setFatherlastname(emp.getBeneficiarylastname4());
				get.setFatherfulladdress(emp.getBeneficiaryaddress4());
				dbService.saveOrUpdate(get);
			}
		}
		if(emp.getBeneficiaryrelationship5()=="0"){
			if(get.getFatherfirstname()==null || get.getFatherlastname()==null){
				get.setFatherfirstname(emp.getBeneficiaryfirstname5());
				get.setFathermiddlename(emp.getBeneficiarylmiddlename5());
				get.setFatherlastname(emp.getBeneficiarylastname5());
				get.setFatherfulladdress(emp.getBeneficiaryaddress5());
				dbService.saveOrUpdate(get);
			}
		}
		//if mother is beneficiary
		if(emp.getBeneficiaryrelationship1()=="1"){
			if(get.getMotherfirstname()==null || get.getMotherlastname()==null){
				get.setMotherfirstname(emp.getBeneficiaryfirstname1());
				get.setMothermiddlename(emp.getBeneficiarylmiddlename1());
				get.setMotherlastname(emp.getBeneficiarylastname1());
				get.setMotherfulladdress(emp.getBeneficiaryaddress1());
				dbService.saveOrUpdate(get);
			}
		}
		if(emp.getBeneficiaryrelationship2()=="1"){
			if(get.getMotherfirstname()==null || get.getMotherlastname()==null){
				get.setMotherfirstname(emp.getBeneficiaryfirstname2());
				get.setMothermiddlename(emp.getBeneficiarylmiddlename2());
				get.setMotherlastname(emp.getBeneficiarylastname2());
				get.setMotherfulladdress(emp.getBeneficiaryaddress2());
				dbService.saveOrUpdate(get);
			}
		}
		if(emp.getBeneficiaryrelationship3()=="1"){
			if(get.getMotherfirstname()==null || get.getMotherlastname()==null){
				get.setMotherfirstname(emp.getBeneficiaryfirstname3());
				get.setMothermiddlename(emp.getBeneficiarylmiddlename3());
				get.setMotherlastname(emp.getBeneficiarylastname3());
				get.setMotherfulladdress(emp.getBeneficiaryaddress3());
				dbService.saveOrUpdate(get);
			}
		}
		if(emp.getBeneficiaryrelationship4()=="1"){
			if(get.getMotherfirstname()==null || get.getMotherlastname()==null){
				get.setMotherfirstname(emp.getBeneficiaryfirstname4());
				get.setMothermiddlename(emp.getBeneficiarylmiddlename4());
				get.setMotherlastname(emp.getBeneficiarylastname4());
				get.setMotherfulladdress(emp.getBeneficiaryaddress4());
				dbService.saveOrUpdate(get);
			}
		}
		if(emp.getBeneficiaryrelationship5()=="1"){
			if(get.getMotherfirstname()==null || get.getMotherlastname()==null){
				get.setMotherfirstname(emp.getBeneficiaryfirstname5());
				get.setMothermiddlename(emp.getBeneficiarylmiddlename5());
				get.setMotherlastname(emp.getBeneficiarylastname5());
				get.setMotherfulladdress(emp.getBeneficiaryaddress5());
				dbService.saveOrUpdate(get);
			}
		}
		get.setSuffix(emp.getSuffix());
		get.setDateofbirth(emp.getDateofbirth());
		get.setAge(DateTimeUtil.getAge(emp.getDateofbirth()));
		get.setPlaceofbirth(emp.getBirthplace());
		get.setGender(emp.getGender());
		get.setCivilstatus(emp.getCivilstatus());
		get.setNationality(emp.getNationality());
		get.setHomephoneareacode(emp.getHomephoneareacode());
		get.setHomephoneno(emp.getHomephoneno());
		get.setOfficephoneareacode(emp.getOfficephoneareacode());
		get.setOfficephoneno(emp.getOfficephoneno());
		get.setMobilephoneareacode(emp.getMobilephoneareacode());
		get.setMobilephoneno(emp.getMobilephoneno());
		get.setEmailaddress(emp.getEmailadd());
		get.setTin(emp.getTin());
		get.setSss(emp.getSss());
		get.setGsis(emp.getGsis());
		get.setCountry1(emp.getCountry1());
		get.setRegion1(emp.getRegion1());
		get.setStateprovince1(emp.getStateprovince1());
		get.setCity1(emp.getCity1());
		get.setBarangay1(emp.getBarangay1());
		get.setSubdivision1(emp.getSubdivision1());
		get.setStreetnoname1(emp.getStreetnoname1());
		get.setPostalcode1(emp.getPostalcode1());
		get.setCountry2(emp.getCountry2());
		get.setRegion2(emp.getRegion2());
		get.setStateprovince2(emp.getStateprovince2());
		get.setCity2(emp.getCity2());
		get.setBarangay2(emp.getBarangay2());
		get.setSubdivision2(emp.getSubdivision2());
		get.setStreetnoname2(emp.getStreetnoname2());
		get.setPostalcode2(emp.getPostalcode2());
		get.setSameaspermanentaddress(emp.getSameaspermanentaddress());
		get.setMiddlename(emp.getMiddlename());
		get.setFirstname(emp.getFirstname());
		get.setLastname(emp.getLastname());
		
		//PSSLAI fields - Daniel  July 15, 2019
		get.setCompanycode(emp.getCompanycode());
		get.setBranch(emp.getBranch());
		get.setServicestatus(emp.getServicestatus());
		return get;
	}
	//GET FROM TBEMPLOYEE | SAVE TO Tbappemployment
	public Tbappemployment setEmployment(Tbappemployment employ, Tbemployee employeed, String memappid){
		employ.setMembershipappid(memappid);
		employ.setEmployeeid(employeed.getEmployeeid());
		employ.setCompanyname(employeed.getCompanyname());
		employ.setEmploymentstatus(employeed.getEmploymentstatus());
		employ.setDatehiredfrom(employeed.getDatehired());
		employ.setDateresign(employeed.getDateresigned());
		employ.setPosition(employeed.getRank());
		employ.setCompanyid(employeed.getCompanycode());
		
		//PSSLAI field - Daniel  July 15, 2019
		employ.setNetpay(employeed.getNetpay());
		employ.setMonthlysalary(employeed.getMonthlysalary());
		employ.setServicestatus(employeed.getServicestatus());
		dbService.saveOrUpdate(employ);
		return employ;
	}
	//GET FROM EMPLOYEE | SAVE TO BENEFICIARY
	public Tbappbeneficiary getEmployeeBeneficiary(Tbappbeneficiary bene, Tbemployee ee, String memappid){
		//BNFCRY1
		if(        ee.getBeneficiaryfirstname1()!=null 
				&& ee.getBeneficiarylmiddlename1()!=null 
				&& ee.getBeneficiarylastname1()!=null
				&& ee.getBeneficiaryrelationship1()!=null){
			//if beneficiary is mother
			//set beneficiary record in Beneficiary tab
			Tbappbeneficiary bene1 = new Tbappbeneficiary();
			bene = bene1;
			bene.setMembershipappid(memappid);
			bene.setFirstname(ee.getBeneficiaryfirstname1());
			bene.setMiddlename(ee.getBeneficiarylmiddlename1());
			bene.setLastname(ee.getBeneficiarylastname1());
			bene.setRelationship(ee.getBeneficiaryrelationship1());
			bene.setFulladdress(ee.getBeneficiaryaddress1());
			dbService.saveOrUpdate(bene);
		}
		//BNFCRY2
		if(        ee.getBeneficiaryfirstname2()!=null 
				&& ee.getBeneficiarylmiddlename2()!=null 
				&& ee.getBeneficiarylastname2()!=null
				&& ee.getBeneficiaryrelationship2()!=null){
			//if beneficiary is mother
			//set beneficiary record in Beneficiary tab
			Tbappbeneficiary bene1 = new Tbappbeneficiary();
			bene = bene1;
			bene.setMembershipappid(memappid);
			bene.setFirstname(ee.getBeneficiaryfirstname2());
			bene.setMiddlename(ee.getBeneficiarylmiddlename2());
			bene.setLastname(ee.getBeneficiarylastname2());
			bene.setRelationship(ee.getBeneficiaryrelationship2());
			bene.setFulladdress(ee.getBeneficiaryaddress2());
			dbService.saveOrUpdate(bene);
		}
		//BNFCRY3
		if(        ee.getBeneficiaryfirstname3()!=null 
				&& ee.getBeneficiarylmiddlename3()!=null 
				&& ee.getBeneficiarylastname3()!=null
				&& ee.getBeneficiaryrelationship3()!=null){
			//if beneficiary is mother
			//set beneficiary record in Beneficiary tab
			Tbappbeneficiary bene1 = new Tbappbeneficiary();
			bene = bene1;
			bene.setMembershipappid(memappid);
			bene.setFirstname(ee.getBeneficiaryfirstname3());
			bene.setMiddlename(ee.getBeneficiarylmiddlename3());
			bene.setLastname(ee.getBeneficiarylastname3());
			bene.setRelationship(ee.getBeneficiaryrelationship3());
			bene.setFulladdress(ee.getBeneficiaryaddress3());
			dbService.saveOrUpdate(bene);
			
		}
		//BNFCRY4
		if(        ee.getBeneficiaryfirstname4()!=null 
				&& ee.getBeneficiarylmiddlename4()!=null 
				&& ee.getBeneficiarylastname4()!=null
				&& ee.getBeneficiaryrelationship4()!=null){
			//if beneficiary is mother
			//set beneficiary record in Beneficiary tab
			Tbappbeneficiary bene1 = new Tbappbeneficiary();
			bene = bene1;
			bene.setMembershipappid(memappid);
			bene.setFirstname(ee.getBeneficiaryfirstname4());
			bene.setMiddlename(ee.getBeneficiarylmiddlename4());
			bene.setLastname(ee.getBeneficiarylastname4());
			bene.setRelationship(ee.getBeneficiaryrelationship4());
			bene.setFulladdress(ee.getBeneficiaryaddress4());
			dbService.saveOrUpdate(bene);
		}
		//BNFCRY5
		if(        ee.getBeneficiaryfirstname5()!=null 
				&& ee.getBeneficiarylmiddlename5()!=null 
				&& ee.getBeneficiarylastname5()!=null
				&& ee.getBeneficiaryrelationship5()!=null){
			//if beneficiary is mother
			//set beneficiary record in Beneficiary tab
			Tbappbeneficiary bene1 = new Tbappbeneficiary();
			bene = bene1;
			bene.setMembershipappid(memappid);
			bene.setFirstname(ee.getBeneficiaryfirstname5());
			bene.setMiddlename(ee.getBeneficiarylmiddlename5());
			bene.setLastname(ee.getBeneficiarylastname5());
			bene.setRelationship(ee.getBeneficiaryrelationship5());
			bene.setFulladdress(ee.getBeneficiaryaddress5());
			dbService.saveOrUpdate(bene);
	}
		return bene;
}
}


