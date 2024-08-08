package member;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.coopdb.data.Tbchangeprofilehistory;
import com.coopdb.data.Tbcooperative;
import com.coopdb.data.Tbdocchecklist;
import com.coopdb.data.Tbmember;
import com.coopdb.data.Tbpledge;
import com.coopdb.data.Tbupdateprofilerequest;
import com.wavemaker.runtime.server.FileUploadResponse;

import member.forms.CapConDeposits;
import member.forms.ChangeProfileForm;
import member.forms.ReturnForm;
import member.forms.memberProfile;

public interface MemberService {

	ReturnForm updateMemberPersonalProfile(Tbmember details, String category, String source, String remarks);

	memberProfile getMemberProfile(String membershipid);

	memberProfile getMember(String membershipid);

	List<CapConDeposits> getCapCon(String membershipid);

	List<CapConDeposits> getDeposits(String membershipid);

	Tbcooperative getCooperativePerID(String id);

	BigDecimal computerCapconPledge(String monthlypayment, Integer numberofmonths, Integer totalshares);

	ReturnForm updateProfile(Tbmember fields, String changecategory, String remarks, String source, Integer refno);

	ReturnForm updateProfileRequest(Tbupdateprofilerequest details, String membershipid, String changecategory,
			String status);

	Tbupdateprofilerequest getProfileUpdateRequest(Integer txrefno);

	String saveProfileRequestAsDraft(Tbupdateprofilerequest req);

	List<Tbdocchecklist> getProfileUpdateDocuments(Integer txrefno, String membershipid);

	String updateProfileUpdateDocuments(Tbdocchecklist doc);

	Tbpledge getPledgeDetails(String memberid);

	String saveMember2x2Photo(String directory, String appid, String memberid, String tempfile);

	String getUploadedPhoto(String memberid, String appid);

	Tbmember getMemberViaID(String membershipid);

	String deleteMemberRelation(Integer relid);

	List<ChangeProfileForm> searchChangeProfileHistory(String search, String changeCategory,
			String changeFieldType);
	FileUploadResponse upload2x2MemberPicture(MultipartFile file);

	Tbmember getMemberProfileInquiry(String membershipid);

	
}
