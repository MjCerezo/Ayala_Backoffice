package com.etel.ci.report.desk;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbmemberemployment;
import com.coopdb.data.Tbdeskciactivity;
import com.coopdb.data.Tbdeskcidetails;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;
import com.etel.utils.ObjectUtil;

public class EVRDesk {
	private String employername;
	private String companyaddress;
	private String contactinfo;
	private String natureofbusiness;
	private String businesstype;
	private String employmentstat;
	private String lengthofservice;
	private String position;
	private BigDecimal monthlysalary;
	
	/**Save initial Desk EVR**/
	@SuppressWarnings("unchecked")
	public static void saveEVRDesk(String rptid, String cifno)throws IllegalArgumentException, IllegalAccessException{
		DBService dbservice = new DBServiceImpl();
		List<Tbmemberemployment> emplist = new ArrayList<Tbmemberemployment>();
		try {
			if(cifno == null){
				System.out.println("Saving EVRDesk failed : null cifno");
			}else{
				Map<String, Object> params = HQLUtil.getMap();
				params.put("cifno", cifno);
				String sql = null;
				emplist = (List<Tbmemberemployment>) dbservice
						.executeListHQLQuery("FROM Tbmemberemployment WHERE membershipid=:cifno", params);
				if (emplist != null && !emplist.isEmpty()) {
					for (Tbmemberemployment e : emplist) {
						params.put("empid", e.getId());
						sql = "SELECT " + 
								"e.companyname as employername, " + 
								"CONCAT(e.streetnoname,' ', subdivision, ' ', barangay, ' ', stateprovince, ' ', city, ' ', region, ' ', country, ' ', postalcode) as companyaddress, " + 
								"CONCAT(e.areacodephone,' ',e.phoneno) as contactinfo, " + 
								"(SELECT psicdesc FROM TBPSICCODES WHERE psiccode = e.psiccode) as natureofbusiness, " + 
								"(SELECT desc1 FROM TBCODETABLE WHERE codename = 'BUSINESSTYPE' AND " + 
								"codevalue = businesstype) as businesstype, " + 
								"(SELECT desc1 FROM TBCODETABLE WHERE codename = 'EMPLOYMENTSTATUS' AND codevalue = e.employmentstatus) as employmentstat, " + 
								"CONCAT(e.yearsofemployment,' years') as lengthofservice, " + 
								"CONVERT(DECIMAL(20,4), m.salary) as monthlysalary, " + 
								"(SELECT desc1 FROM TBCODETABLE WHERE codename = 'POSITION' AND codevalue = e.position) as position " + 
								"FROM Tbmemberemployment e LEFT JOIN Tbmember m on e.membershipid = m.membershipid " + 
								"WHERE e.membershipid =:cifno and e.id=:empid" ;
						EVRDesk evr = new EVRDesk();
						evr = (EVRDesk) dbservice.execSQLQueryTransformer(sql, params, EVRDesk.class, 0);
						if (evr != null) {
							Map<String, Object> m = ObjectUtil.getFieldNamesAndValues(evr);
							try {
								for (Map.Entry<String, Object> entry : m.entrySet()) {
									params.put("category", entry.getKey());
									params.put("rptid", rptid);
									Tbdeskcidetails d = (Tbdeskcidetails)dbservice
											.executeUniqueHQLQuery("FROM Tbdeskcidetails WHERE cireportid=:rptid "
													+ "AND ciactivity='EVR' AND category=:category", params);
									if(d != null){
										if (entry.getValue() == null || entry.getValue().equals("")) {
											d.setDeclaredvalue("");
										} else {
											if(entry.getKey().equals("monthlysalary")){
												d.setDeclaredvalue(formatCurrency(entry.getValue()));
											}else{
												d.setDeclaredvalue(entry.getValue().toString());	
											}
										}
										dbservice.saveOrUpdate(d);
									}else{
										Tbdeskcidetails d2 = new Tbdeskcidetails();
										d2.setCategory(entry.getKey());
										d2.setCireportid(rptid);
										d2.setFindings(null);
										d2.setCiactivity("EVR");
										d2.setEmploymentid(e.getId());
										if (entry.getValue() == null || entry.getValue().equals("")) {
											d2.setDeclaredvalue("");
										} else {
											if(entry.getKey().equals("monthlysalary")){
												d2.setDeclaredvalue(formatCurrency(entry.getValue()));
											}else{
												d2.setDeclaredvalue(entry.getValue().toString());	
											}
										}
										dbservice.save(d2);
									}
								}

							} catch (Exception ex) {
								ex.printStackTrace();
							}
							
							params.put("rptid", rptid);
							params.put("eid", e.getId());
							Tbdeskciactivity activity = (Tbdeskciactivity) dbservice
									.executeUniqueHQLQuery("FROM Tbdeskciactivity WHERE cireportid=:rptid AND ciactivity='EVR' AND employmentid=:eid", params);
							if(activity == null){
								// save initial desk CI activity so we can have a
								// list of EVR Activities in EVR Tab
								Tbdeskciactivity a = new Tbdeskciactivity();
								a.setEmploymentid(e.getId());
								a.setCireportid(rptid);
								a.setCiactivity("EVR");
								a.setCifno(e.getMembershipid());
								dbservice.saveOrUpdate(a);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**List EVR desk details manually**/
	public List<Tbdeskcidetails> getEVRDesk(List<Tbdeskcidetails> evrdesklist){
		Tbdeskcidetails [] aDesk;
		List<Tbdeskcidetails> list = new ArrayList<Tbdeskcidetails>();
		try {
			if(evrdesklist == null){
				return list;
			}
			aDesk = new Tbdeskcidetails[9];
			for(Tbdeskcidetails d : evrdesklist){
				if(d.getCategory().equals("employername")){
					d.setCategory("Business / Company Name");
					aDesk[0] = d;
				}
				if(d.getCategory().equals("companyaddress")){
					d.setCategory("Company Address");
					aDesk[1] = d;
				}
				if(d.getCategory().equals("contactinfo")){
					d.setCategory("Contact Information");
					aDesk[2] = d;
				}
				if(d.getCategory().equals("natureofbusiness")){
					d.setCategory("Nature of Business");
					aDesk[3] = d;
				}
				if(d.getCategory().equals("businesstype")){
					d.setCategory("Business Classification");
					aDesk[4] = d;
				}
				if(d.getCategory().equals("employmentstat")){
					d.setCategory("Employment Status");
					aDesk[5] = d;
				}
				if(d.getCategory().equals("lengthofservice")){
					d.setCategory("Length of Service");
					aDesk[6] = d;
				}
				if(d.getCategory().equals("monthlysalary")){
					d.setCategory("Monthly Salary");
					aDesk[7] = d;
				}
				if(d.getCategory().equals("position")){
					d.setCategory("Position");
					aDesk[8] = d;
				}
			}
			if(aDesk != null){
//				return list = Arrays.asList(aDesk);
				for(Tbdeskcidetails d : aDesk){
					if(d != null){
						list.add(d);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public String getEmployername() {
		return employername;
	}
	public void setEmployername(String employername) {
		this.employername = employername;
	}
	public String getCompanyaddress() {
		return companyaddress;
	}
	public void setCompanyaddress(String companyaddress) {
		this.companyaddress = companyaddress;
	}
	public String getContactinfo() {
		return contactinfo;
	}
	public void setContactinfo(String contactinfo) {
		this.contactinfo = contactinfo;
	}
	public String getNatureofbusiness() {
		return natureofbusiness;
	}
	public void setNatureofbusiness(String natureofbusiness) {
		this.natureofbusiness = natureofbusiness;
	}
	public String getBusinesstype() {
		return businesstype;
	}
	public void setBusinesstype(String businesstype) {
		this.businesstype = businesstype;
	}
	public String getEmploymentstat() {
		return employmentstat;
	}
	public void setEmploymentstat(String employmentstat) {
		this.employmentstat = employmentstat;
	}
	public String getLengthofservice() {
		return lengthofservice;
	}
	public void setLengthofservice(String lengthofservice) {
		this.lengthofservice = lengthofservice;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public BigDecimal getMonthlysalary() {
		return monthlysalary;
	}
	public void setMonthlysalary(BigDecimal monthlysalary) {
		this.monthlysalary = monthlysalary;
	}
	
    public static String formatCurrency( Object value ) {
        BigDecimal ret = new BigDecimal(0.0000);
        
        if( value != null ) {
            if( value instanceof BigDecimal ) {
                ret = (BigDecimal) value;
            } else if( value instanceof String ) {
                ret = new BigDecimal( (String) value );
            } else if( value instanceof BigInteger ) {
                ret = new BigDecimal( (BigInteger) value );
            } else if( value instanceof Number ) {
                ret = new BigDecimal( ((Number)value).doubleValue() );
            } else {
                throw new ClassCastException("Not possible to coerce ["+value+"] from class "+value.getClass()+" into a BigDecimal.");
            }
            
    		NumberFormat df = NumberFormat.getCurrencyInstance();
    		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
    		dfs.setCurrencySymbol("Php ");
    		dfs.setGroupingSeparator(',');
    		dfs.setMonetaryDecimalSeparator('.');
    		((DecimalFormat) df).setDecimalFormatSymbols(dfs);
    		return df.format(ret);
        }
        return "";
    }
    
}
