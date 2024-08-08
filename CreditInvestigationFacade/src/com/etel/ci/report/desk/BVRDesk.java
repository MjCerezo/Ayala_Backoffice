package com.etel.ci.report.desk;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbmemberbusiness;
import com.coopdb.data.Tbdeskciactivity;
import com.coopdb.data.Tbdeskcidetails;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;
import com.etel.utils.ObjectUtil;

public class BVRDesk {
	private String businessname;
	private String businessaddress;
	private String contactinfo;
	private String businessclassification;
	private Integer yearsofservice;
	private String natureofbusiness;
	private BigDecimal grossincome;
	
	/**Save initial Desk BVR**/
	@SuppressWarnings("unchecked")
	public static void saveBVRDesk(String rptid, String cifno, String customertype)throws IllegalArgumentException, IllegalAccessException{
		DBService dbservice = new DBServiceImpl();
		String sql = null;
		try {
			if(customertype.equals("1")){
				//individual
				Map<String, Object> params = HQLUtil.getMap();
				params.put("cifno", cifno);
				List<Tbmemberbusiness> buslist = (List<Tbmemberbusiness>) dbservice
						.executeListHQLQuery("FROM Tbmemberbusiness WHERE membershipid=:cifno", params);
				if (buslist != null && !buslist.isEmpty()) {
					for(Tbmemberbusiness b : buslist){
						params.put("busid", b.getId());
						sql = "SELECT " + 
								"b.businessname, " + 
								"b.fulladdress as businessaddress, " + 
								"CONCAT(b.areacodephone,' ',b.phoneno) as contactinfo, " + 
								"(SELECT desc1 FROM TBCODETABLE WHERE codename = 'NATUREBUSINESS' " + 
								"AND codevalue = b.natureofbusiness) as natureofbusiness, " + 
								"(SELECT desc1 FROM TBCODETABLE WHERE codename = 'BUSINESSTYPE' " + 
								"AND codevalue = b.businessclass) as businessclassification, " + 
								"CAST(b.lengthofoperation as int) as yearsofservice, " + 
								"m.businessincome grossincome " + 
								"FROM Tbmemberbusiness b LEFT JOIN Tbmember m on b.membershipid = m.membershipid " + 
								"WHERE b.membershipid=:cifno AND b.id=:busid";
						BVRDesk bvr = new BVRDesk();
						bvr = (BVRDesk) dbservice.execSQLQueryTransformer(sql, params, BVRDesk.class, 0);
						if (bvr != null) {
							Map<String, Object> m = ObjectUtil.getFieldNamesAndValues(bvr);
							try {
								for (Map.Entry<String, Object> e : m.entrySet()) {
									params.put("category", e.getKey());
									params.put("rptid", rptid);
									Tbdeskcidetails d = (Tbdeskcidetails)dbservice
											.executeUniqueHQLQuery("FROM Tbdeskcidetails WHERE cireportid=:rptid "
													+ "AND ciactivity='BVR' AND category=:category", params);
									if(d != null){
										if (e.getValue() == null || e.getValue().equals("")) {
											d.setDeclaredvalue("");
										} else {
											if(e.getKey().equals("grossincome")){
												d.setDeclaredvalue(formatCurrency(e.getValue()));
											}else{
												d.setDeclaredvalue(e.getValue().toString());	
											}
										}	
										dbservice.saveOrUpdate(d);
									}else{
										Tbdeskcidetails d2 = new Tbdeskcidetails();
										d2.setCategory(e.getKey());
										d2.setCireportid(rptid);
										d2.setFindings(null);
										d2.setCiactivity("BVR");
										d2.setBusinessid(b.getId());
										if (e.getValue() == null || e.getValue().equals("")) {
											d2.setDeclaredvalue("");
										} else {
											if(e.getKey().equals("grossincome")){
												d2.setDeclaredvalue(formatCurrency(e.getValue()));
											}else{
												d2.setDeclaredvalue(e.getValue().toString());	
											}
										}
										dbservice.save(d2);
									}
								}
							} catch (Exception ex) {
								ex.printStackTrace();
							}
							
							params.put("rptid", rptid);
							params.put("bid", b.getId());
							Tbdeskciactivity activity = (Tbdeskciactivity) dbservice
									.executeUniqueHQLQuery("FROM Tbdeskciactivity WHERE cireportid=:rptid AND ciactivity='BVR' AND businessid=:bid", params);
							if(activity == null){
								// save initial desk CI activity so we can have a
								// list of BVR Activities in BVR Tab
								Tbdeskciactivity a = new Tbdeskciactivity();
								a.setBusinessid(b.getId());
								a.setCireportid(rptid);
								a.setCiactivity("BVR");
								a.setCifno(b.getMembershipid());
								dbservice.saveOrUpdate(a);	
							}
						}
					}
				}
			}else{
				//corporate
				//save all except nature of business
				Map<String, Object> params = HQLUtil.getMap();
				params.put("cifno", cifno);
				sql = "SELECT " + 
						"m.businessname as businessname, " + 
						"m.fulladdress as businessaddress, " + 
						"CONCAT(m.areacodephone,' ',m.phoneno) as contactinfo, " + 
						"(SELECT desc1 FROM TBCODETABLE WHERE codename = 'BUSINESSTYPE' " + 
						"AND codevalue = m.businessclass) as businessclassification, " + 
						"CAST(m.lengthofoperation as int) as yearsofservice, " + 
						"a.businessincome as grossincome " + 
						"FROM Tbmemberbusiness m left join tbmember a on m.membershipid = a.membershipid " + 
						"WHERE m.id=:cifno";
				BVRDesk bvr = new BVRDesk();
				bvr = (BVRDesk) dbservice.execSQLQueryTransformer(sql, params, BVRDesk.class, 0);
				if (bvr != null) {
					Map<String, Object> m = ObjectUtil.getFieldNamesAndValues(bvr);
					try {
						for (Map.Entry<String, Object> e : m.entrySet()) {
							params.put("category", e.getKey());
							params.put("rptid", rptid);
							Tbdeskcidetails d = (Tbdeskcidetails)dbservice
									.executeUniqueHQLQuery("FROM Tbdeskcidetails WHERE cireportid=:rptid "
											+ "AND ciactivity='BVR' AND category=:category", params);
							if(d != null){
								if (!e.getKey().equals("natureofbusiness")) {
									if (e.getValue() == null || e.getValue().equals("")) {
										d.setDeclaredvalue("");
									} else {
										if (e.getKey().equals("grossincome")) {
											d.setDeclaredvalue(formatCurrency(e.getValue()));
										} else {
											d.setDeclaredvalue(e.getValue().toString());
										}
									}
									dbservice.saveOrUpdate(d);
								}	
							}else{
								Tbdeskcidetails d2 = new Tbdeskcidetails();
								if (!e.getKey().equals("natureofbusiness")) {
									d2.setCategory(e.getKey());
									d2.setCireportid(rptid);
									d2.setFindings(null);
									d2.setCiactivity("BVR");
									if (e.getValue() == null || e.getValue().equals("")) {
										d2.setDeclaredvalue("");
									} else {
										if (e.getKey().equals("grossincome")) {
											d2.setDeclaredvalue(formatCurrency(e.getValue()));
										} else {
											d2.setDeclaredvalue(e.getValue().toString());
										}
									}
									dbservice.save(d2);
								}
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				params.put("rptid", rptid);
				Tbdeskciactivity activity = (Tbdeskciactivity) dbservice
						.executeUniqueHQLQuery("FROM Tbdeskciactivity WHERE cireportid=:rptid AND ciactivity='BVR' AND businessid IS NULL", params);
				if(activity == null){
					// save initial desk CI activity 
					Tbdeskciactivity a = new Tbdeskciactivity();
					a.setCireportid(rptid);
					a.setCiactivity("BVR");
					a.setCifno(cifno);
					a.setBusinessid(Integer.valueOf(cifno));
					dbservice.save(a);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**List BVR desk details manually**/
	public List<Tbdeskcidetails> getBVRDesk(List<Tbdeskcidetails> bvrdesklist, Integer emporbusid){
		Tbdeskcidetails [] aDesk;
		List<Tbdeskcidetails> list = new ArrayList<Tbdeskcidetails>();
		try {
			if(bvrdesklist == null){
				return list;
			}
			aDesk = new Tbdeskcidetails[7];
			if (emporbusid == null) {
				// corporate, since corporate data has no business or employment
				// id.
				for (Tbdeskcidetails d : bvrdesklist) {
					if (d.getCategory().equals("businessname")) {
						d.setCategory("Business / Company Name");
						aDesk[0] = d;
					}
					if (d.getCategory().equals("businessaddress")) {
						d.setCategory("Business Address");
						aDesk[1] = d;
					}
					if (d.getCategory().equals("contactinfo")) {
						d.setCategory("Contact Information");
						aDesk[2] = d;
					}
					if (d.getCategory().equals("businessclassification")) {
						d.setCategory("Business Classification");
						aDesk[3] = d;
					}
					if (d.getCategory().equals("yearsofservice")) {
						d.setCategory("Years in Operation");
						aDesk[4] = d;
					}
					if (d.getCategory().equals("grossincome")) {
						d.setCategory("Gross Income/Annual Turnover");
						aDesk[5] = d;
					}
				}
			} else {
				for (Tbdeskcidetails d : bvrdesklist) {
					if (d.getCategory().equals("businessname")) {
						d.setCategory("Business / Company Name");
						aDesk[0] = d;
					}
					if (d.getCategory().equals("businessaddress")) {
						d.setCategory("Business Address");
						aDesk[1] = d;
					}
					if (d.getCategory().equals("contactinfo")) {
						d.setCategory("Contact Information");
						aDesk[2] = d;
					}
					if (d.getCategory().equals("natureofbusiness")) {
						d.setCategory("Nature of Business");
						aDesk[3] = d;
					}
					if (d.getCategory().equals("businessclassification")) {
						d.setCategory("Business Classification");
						aDesk[4] = d;
					}
					if (d.getCategory().equals("yearsofservice")) {
						d.setCategory("Years in Operation");
						aDesk[5] = d;
					}
					if (d.getCategory().equals("grossincome")) {
						d.setCategory("Gross Income");
						aDesk[6] = d;
					}
				}
			}
			if (aDesk != null) {
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
	
	public String getBusinessname() {
		return businessname;
	}
	public void setBusinessname(String businessname) {
		this.businessname = businessname;
	}
	public String getBusinessaddress() {
		return businessaddress;
	}
	public void setBusinessaddress(String businessaddress) {
		this.businessaddress = businessaddress;
	}
	public String getBusinessclassification() {
		return businessclassification;
	}
	public void setBusinessclassification(String businessclassification) {
		this.businessclassification = businessclassification;
	}
	public Integer getYearsofservice() {
		return yearsofservice;
	}
	public void setYearsofservice(Integer yearsofservice) {
		this.yearsofservice = yearsofservice;
	}
	public String getNatureofbusiness() {
		return natureofbusiness;
	}
	public void setNatureofbusiness(String natureofbusiness) {
		this.natureofbusiness = natureofbusiness;
	}
	public BigDecimal getGrossincome() {
		return grossincome;
	}
	public void setGrossincome(BigDecimal grossincome) {
		this.grossincome = grossincome;
	}

	public String getContactinfo() {
		return contactinfo;
	}

	public void setContactinfo(String contactinfo) {
		this.contactinfo = contactinfo;
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
