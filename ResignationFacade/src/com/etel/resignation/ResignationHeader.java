package com.etel.resignation;

import java.util.Date;
import java.util.Map;

import org.cloudfoundry.org.codehaus.jackson.map.DeserializationConfig;
import org.cloudfoundry.org.codehaus.jackson.map.ObjectMapper;

import com.coopdb.data.Tbcodetable;
import com.coopdb.data.Tbworkflowprocess;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;
/**
 * @author Daniel
 */
public class ResignationHeader {
	private Date membershipdate;
	private String membershipid;
	private String name;
	private Date effectivitydate;
	private Date txdate;
	private String employeeid;
	private int txrefno;
	private String resignstatus;
	private String stage;
	private static DBService dbService = new DBServiceImpl();
	private static Map<String, Object> param = HQLUtil.getMap();

	public static ResignationHeader getMemberdetails(String memberid, String stage) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			System.out.println(memberid);
			if (memberid != null) {
				param.put("id", memberid);
				ResignationHeader r = (ResignationHeader) dbService.execSQLQueryTransformer(
						"SELECT membershipid, CONCAT (lastname, ', ', firstname, ' ', middlename) as name, "
						+ "txrefno, "
						+ "effectivitydate, "
						+ "txdate, "
						+ "resignstatus, "
						+ "employeeid FROM Tbresign WHERE membershipid=:id",
						param, ResignationHeader.class, 0);
				if(r!=null){
					ResignationHeader details = (ResignationHeader)dbService.execSQLQueryTransformer("SELECT membershipdate FROM Tbmember WHERE membershipid=:id", param, ResignationHeader.class, 0);
					r.setMembershipdate(details.getMembershipdate());
					param.put("stage", r.getResignstatus());
					r.setStage((String)dbService.executeUniqueSQLQuery("SELECT processname FROM Tbworkflowprocess WHERE workflowid = '2' AND sequenceno=:stage", param));
//					Tbworkflowprocess flow = (Tbworkflowprocess)dbService.executeUniqueHQLQuery("FROM Tbworkflowprocess WHERE sequenceno=:stage", param);
//					Tbcodetable s = (Tbcodetable)dbService.executeUniqueHQLQuery("FROM Tbcodetable WHERE codename = 'RESIGNSTATUS' AND codevalue=:stage", param);
//					if(s!=null){
//						r.setStage(s.getDesc1());
//					}
					System.out.println(mapper.writeValueAsString(r));
					return r;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public String getMembershipid() {
		return membershipid;
	}

	public void setMembershipid(String membershipid) {
		this.membershipid = membershipid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getEffectivitydate() {
		return effectivitydate;
	}

	public void setEffectivitydate(Date effectivitydate) {
		this.effectivitydate = effectivitydate;
	}

	public Date getTxdate() {
		return txdate;
	}

	public void setTxdate(Date txdate) {
		this.txdate = txdate;
	}

	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}

	public int getTxrefno() {
		return txrefno;
	}

	public void setTxrefno(int txrefno) {
		this.txrefno = txrefno;
	}

	public String getResignstatus() {
		return resignstatus;
	}

	public void setResignstatus(String resignstatus) {
		this.resignstatus = resignstatus;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public Date getMembershipdate() {
		return membershipdate;
	}

	public void setMembershipdate(Date membershipdate) {
		this.membershipdate = membershipdate;
	}

}
