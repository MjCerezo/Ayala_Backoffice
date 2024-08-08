package com.etel.qdeforms;

import java.util.Date;
import java.util.List;

import com.cifsdb.data.Tbcifmain;
import com.coopdb.data.Tbemployee;
import com.coopdb.data.Tbmember;

public class QDEParameterForm {
	
	private String appno;
	private String membershipid;
	private String loanproduct;
	private Integer applicationtype;
	private String cifname;
	private String membershipclass;
	private String membershipstatus;
	private String employeeid;
	private String chapter;
	private String branch;
	private String homephoneareacode;
	private String homephoneno;
	private String mobilephoneareacode;
	private String mobilephoneno;
	private String emailaddress;
	private String fulladdress1;
	/**** Added Kyle ****/
	private String cifno;
	private int count;
	

	private String lname;
	private String fname;
	private String mname;
	private String suffix;
	private Date date; // date of birth OR date of incorporation
	private String businessname;
	private String btype; // modified July 24, 2017 for Employment and Business - Quick Setup
	private String tin;
	private String sss;
	private String streetno;
	private String subdivision;
	private String country;
	private String province;
	private String city;
	private String barangay;
	private String postalcode;
	private String custType;
	
	private String bankName;
	private String bankAccountNumber;
	
	
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	// MAR 10-13-2020
	private List<Tbcifmain> cif;
	private String customertype;
	public String getCustomertype() {
		return customertype;
	}

	public void setCustomertype(String customertype) {
		this.customertype = customertype;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getTypefacility() {
		return typefacility;
	}

	public void setTypefacility(String typefacility) {
		this.typefacility = typefacility;
	}

	public List<Tbcifmain> getCif() {
		return cif;
	}

	private String company;
	private String typefacility;

	//END
	
	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getHomephoneareacode() {
		return homephoneareacode;
	}

	public void setHomephoneareacode(String homephoneareacode) {
		this.homephoneareacode = homephoneareacode;
	}

	public String getHomephoneno() {
		return homephoneno;
	}

	public void setHomephoneno(String homephoneno) {
		this.homephoneno = homephoneno;
	}

	public String getMobilephoneareacode() {
		return mobilephoneareacode;
	}

	public void setMobilephoneareacode(String mobilephoneareacode) {
		this.mobilephoneareacode = mobilephoneareacode;
	}

	public String getMobilephoneno() {
		return mobilephoneno;
	}

	public void setMobilephoneno(String mobilephoneno) {
		this.mobilephoneno = mobilephoneno;
	}

	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}
	public String getMembershipclass() {
		return membershipclass;
	}

	public void setMembershipclass(String membershipclass) {
		this.membershipclass = membershipclass;
	}

	public String getMembershipstatus() {
		return membershipstatus;
	}

	public void setMembershipstatus(String membershipstatus) {
		this.membershipstatus = membershipstatus;
	}

	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}

	public String getChapter() {
		return chapter;
	}

	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	public String getCifname() {
		return cifname;
	}

	public void setCifname(String cifname) {
		this.cifname = cifname;
	}

	public Integer getApplicationtype() {
		return applicationtype;
	}

	public void setApplicationtype(Integer applicationtype) {
		this.applicationtype = applicationtype;
	}

	private List<Tbmember> member;
	
	private List<Tbemployee> employee;
	
	public List<Tbmember> getMember() {
		return member;
	}

	public void setMember(List<Tbmember> member) {
		this.member = member;
	}

	public List<Tbemployee> getEmployee() {
		return employee;
	}

	public void setEmployee(List<Tbemployee> employee) {
		this.employee = employee;
	}

	public String getAppno() {
		return appno;
	}

	public void setAppno(String appno) {
		this.appno = appno;
	}

	public String getMembershipid() {
		return membershipid;
	}

	public void setMembershipid(String membershipid) {
		this.membershipid = membershipid;
	}


	public String getLoanproduct() {
		return loanproduct;
	}

	public void setLoanproduct(String loanproduct) {
		this.loanproduct = loanproduct;
	}

	public String getFulladdress1() {
		return fulladdress1;
	}

	public void setFulladdress1(String fulladdress1) {
		this.fulladdress1 = fulladdress1;
	}

	public String getCifno() {
		return cifno;
	}

	public void setCifno(String cifno) {
		this.cifno = cifno;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getBusinessname() {
		return businessname;
	}
	public void setBusinessname(String businessname) {
		this.businessname = businessname;
	}
	public String getBtype() {
		return btype;
	}
	public void setBtype(String btype) {
		this.btype = btype;
	}
	public String getTin() {
		return tin;
	}
	public void setTin(String tin) {
		this.tin = tin;
	}
	public String getSss() {
		return sss;
	}
	public void setSss(String sss) {
		this.sss = sss;
	}
	public String getStreetno() {
		return streetno;
	}
	public void setStreetno(String streetno) {
		this.streetno = streetno;
	}
	public String getSubdivision() {
		return subdivision;
	}
	public void setSubdivision(String subdivision) {
		this.subdivision = subdivision;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getBarangay() {
		return barangay;
	}
	public void setBarangay(String barangay) {
		this.barangay = barangay;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	
	// MAR 10-13-2020
	public void setCif(List<Tbcifmain> cif) {
		this.cif = cif;
	}
	//END
	
	
	//MAR 10-25-2020
		private String lastnameNotExisting;
		private String firstnameNotExisting;
		private String businessnameNotExisting;
		
		public String getLastnameNotExisting() {
			return lastnameNotExisting;
		}

		public void setLastnameNotExisting(String lastnameNotExisting) {
			this.lastnameNotExisting = lastnameNotExisting;
		}

		public String getFirstnameNotExisting() {
			return firstnameNotExisting;
		}

		public void setFirstnameNotExisting(String firstnameNotExisting) {
			this.firstnameNotExisting = firstnameNotExisting;
		}

		public String getBusinessnameNotExisting() {
			return businessnameNotExisting;
		}

		public void setBusinessnameNotExisting(String businessnameNotExisting) {
			this.businessnameNotExisting = businessnameNotExisting;
		}

}
