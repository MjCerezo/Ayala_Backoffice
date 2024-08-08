package member.forms;

import java.io.File;
import java.util.Date;
import java.util.Map;

import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;
import com.etel.utils.ImageUtils;
import com.wavemaker.runtime.RuntimeAccess;

public class MemberProfileHeader {
	private String membershipid;
	private String membername;
	private Date membershipdate;
	private String membershiptype;
	private String membershipstatus;
	private String employeeid;
	private String servicestatus;
	private String branchofservice;
	private String branch;
	private String originatingbranch;
	private String picture;
	private String membershipappid;
	
	public static MemberProfileHeader getMemberProfileHeader(String membershipid) {
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			System.out.println("Header : " + membershipid);
			MemberProfileHeader header = new MemberProfileHeader();
			param.put("membershipid", membershipid);
			header = (MemberProfileHeader)dbService.execSQLQueryTransformer("SELECT  " + 
					"mem.membershipid,  " + 
					"CONCAT(mem.lastname,', ',mem.firstname,' ',mem.middlename) as membername, " + 
					"mem.membershipdate, " + 
					"mcl.desc1 as membershiptype, " + 
					"mst.desc1 as membershipstatus, " + 
					"mem.employeeid, " + 
					"mem.servicestatus, " + 
					"CONCAT(bos.companycode,' - ', bos.companyname) as branchofservice, " + 
					"CONCAT(br.branchcode,' - ', br.branchname) as branch, " + 
					"CONCAT(origbr.branchcode,' - ', origbr.branchname) as originatingbranch, " + 
					" " + 
					"mem.membershipappid " + 
					" " + 
					"FROM TBMEMBER mem " + 
					" " + 
					"LEFT JOIN TBCODETABLE mst ON mst.codevalue=mem.membershipstatus " + 
					"LEFT JOIN TBCODETABLE mcl ON mcl.codevalue=mem.membershipclass " + 
					"LEFT JOIN TBMEMBERCOMPANY bos ON bos.companycode=mem.companycode " + 
					"LEFT JOIN TBBRANCH br ON br.branchcode=mem.branch " + 
					"LEFT JOIN TBBRANCH origbr ON origbr.branchcode=mem.originatingbranch " + 
					" " + 
					"WHERE mem.membershipid=:membershipid " + 
					" " + 
					" " + 
					"AND mst.codename='MEMBERSHIPSTATUS' " + 
					"AND mcl.codename='MEMBERSHIPCLASS' " + 
					"AND bos.coopcode=mem.coopcode " + 
					"AND br.coopcode=mem.coopcode " + 
					"AND br.companycode=mem.companycode", param, MemberProfileHeader.class, 0);
			if(header != null) {
				return header;
//				if(header.getPicture() != null && !header.getPicture().equals("")) {
//					String dir = RuntimeAccess.getInstance().getSession().getServletContext()
//							.getRealPath("resources/data/upload/" + header.getMembershipid() + ".jpg");
//					File f = new File(dir);
//					if (f.exists()) {
//						f.delete();
//						String cnvrt = ImageUtils.base64ToImage(header.getPicture(), dir);
//						if (cnvrt != null && cnvrt.equals("success")) {
//							header.setPicture( "resources/data/upload/" + header.getMembershipid() + ".jpg");
//						}
//					} else {
//						String cnvrt = ImageUtils.base64ToImage(header.getPicture(), dir);
//						if (cnvrt != null && cnvrt.equals("success")) {
//							header.setPicture( "resources/data/upload/" + header.getMembershipid() + ".jpg");
//						}
//					}
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}
	
	public String getMembershipid() {
		return membershipid;
	}
	public void setMembershipid(String membershipid) {
		this.membershipid = membershipid;
	}
	public String getMembername() {
		return membername;
	}
	public void setMembername(String membername) {
		this.membername = membername;
	}
	public Date getMembershipdate() {
		return membershipdate;
	}
	public void setMembershipdate(Date membershipdate) {
		this.membershipdate = membershipdate;
	}
	public String getMembershiptype() {
		return membershiptype;
	}
	public void setMembershiptype(String membershiptype) {
		this.membershiptype = membershiptype;
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
	public String getServicestatus() {
		return servicestatus;
	}
	public void setServicestatus(String servicestatus) {
		this.servicestatus = servicestatus;
	}
	public String getBranchofservice() {
		return branchofservice;
	}
	public void setBranchofservice(String branchofservice) {
		this.branchofservice = branchofservice;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getOriginatingbranch() {
		return originatingbranch;
	}
	public void setOriginatingbranch(String originatingbranch) {
		this.originatingbranch = originatingbranch;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getMembershipappid() {
		return membershipappid;
	}

	public void setMembershipappid(String membershipappid) {
		this.membershipappid = membershipappid;
	}
}
