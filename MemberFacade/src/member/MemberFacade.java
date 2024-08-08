package member;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.coopdb.data.Tbchangeprofilehistory;
import com.coopdb.data.Tbcooperative;
import com.coopdb.data.Tbdocchecklist;
import com.coopdb.data.Tbmember;
import com.coopdb.data.Tbpledge;
import com.coopdb.data.Tbupdateprofilerequest;
import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
import com.wavemaker.runtime.server.FileUploadResponse;
import com.wavemaker.runtime.service.annotations.ExposeToClient;

import member.forms.CapConDeposits;
import member.forms.ChangeProfileForm;
import member.forms.MemberProfileHeader;
import member.forms.ReturnForm;
import member.forms.memberProfile;

/**
 * This is a client-facing service class.  All
 * public methods will be exposed to the client.  Their return
 * values and parameters will be passed to the client or taken
 * from the client, respectively.  This will be a singleton
 * instance, shared between all requests. 
 * 
 * To log, call the superclass method log(LOG_LEVEL, String) or log(LOG_LEVEL, String, Exception).
 * LOG_LEVEL is one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level.
 * For info on these levels, look for tomcat/log4j documentation
 */
@ExposeToClient
public class MemberFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
	 private MemberService service = new MemberServiceImpl();
	 
	 
	 public ReturnForm updateMemberPersonalProfile(Tbmember details, String category, String source, String remarks){
		 return service.updateMemberPersonalProfile(details, category, source, remarks);
	 }
	 
	 public memberProfile getMemberProfile(String membershipid){
		 return service.getMemberProfile(membershipid);
	 }
	 
	 public memberProfile getMember(String membershipid){
		 return service.getMember(membershipid);
	 }
	 public List<CapConDeposits> getCapCon(String membershipid){
		 return service.getCapCon(membershipid);
	 }
	 public List<CapConDeposits> getDeposits(String membershipid){
		 return service.getDeposits(membershipid);
	 }
	 
	 public Tbcooperative getCooperativePerID(String id){
		 return service.getCooperativePerID(id);
	 }
	 
	 public BigDecimal computerCapconPledge(String monthlypayment, Integer numberofmonths, Integer totalshares){
		 return service.computerCapconPledge(monthlypayment, numberofmonths, totalshares);
	 }
	 
	 public ReturnForm updateProfile(Tbmember fields, String changecategory, String remarks, String source, Integer refno){
		 return service.updateProfile(fields, changecategory, remarks, source, refno);
	 }
	 
	 public ReturnForm updateProfileRequest(Tbupdateprofilerequest details, String membershipid, String changecategory, String status){
		 return service.updateProfileRequest(details, membershipid, changecategory, status);
	 }
	 
	 public Tbupdateprofilerequest getProfileUpdateRequest(Integer txrefno){
		 return service.getProfileUpdateRequest(txrefno);
	 }
	 
	 public String saveProfileRequestAsDraft(Tbupdateprofilerequest req){
		 return service.saveProfileRequestAsDraft(req);
	 }
	 
	 public List<Tbdocchecklist> getProfileUpdateDocuments(Integer txrefno, String membershipid){
		 return service.getProfileUpdateDocuments(txrefno, membershipid);
	 }
	 
	 public String updateProfileUpdateDocuments(Tbdocchecklist doc){
		 return service.updateProfileUpdateDocuments(doc);
	 }
	 
	 public Tbpledge getPledgeDetails(String memberid){
		 return service.getPledgeDetails(memberid);
	 }
	 
	 public String saveMember2x2Photo(String directory, String appid, String memberid, String tempfile) {
		 return service.saveMember2x2Photo(directory, appid, memberid, tempfile);
	 }
	 
	 public String getUploadedPhoto(String memberid, String appid) {
		 return service.getUploadedPhoto(memberid, appid);
	 }
	 
	 public Tbmember getMemberViaID(String membershipid) {
		 return service.getMemberViaID(membershipid);
	 }
	 
	 public String deleteMemberRelation(Integer relid) {
		 return service.deleteMemberRelation(relid);
	 }
	 //added by fed
	 public List<ChangeProfileForm> searchChangeProfileHistory(String search, String changeCategory,String changeFieldType){
		 return service.searchChangeProfileHistory(search,changeCategory,changeFieldType);
	 }
	public FileUploadResponse upload2x2MemberPicture(MultipartFile file) throws IOException {
			return service.upload2x2MemberPicture(file);
	}
	
	/*
	 * query member details with descriptions - this is for read only/ viewing
	 * purposes
	 */
	public Tbmember getMemberProfileInquiry(String membershipid) {
		return service.getMemberProfileInquiry(membershipid);
	}
	
	public MemberProfileHeader getMemberInquiryHeader(String membershipid) {
		return MemberProfileHeader.getMemberProfileHeader(membershipid);
	}
	 
}
