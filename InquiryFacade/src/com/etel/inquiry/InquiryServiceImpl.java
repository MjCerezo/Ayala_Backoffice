package com.etel.inquiry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cifsdb.data.Tbcodetable;
import com.coopdb.data.Tblntxjrnl;
import com.coopdb.data.Tbloans;
import com.coopdb.data.Tblstapp;
import com.coopdb.data.Tbmember;
import com.coopdb.data.Tbmemberbusiness;
import com.coopdb.data.Tbmemberemployment;
import com.coopdb.data.Tbmembershipapp;
import com.coopdb.data.Tbpaysched;
import com.coopdb.data.Tbworkflowprocess;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.dashboard.forms.TMPFrom;
import com.etel.inquiry.forms.CIFInquiryForm;
import com.etel.inquiry.forms.CompanyListPerStagesForm;
import com.etel.inquiry.forms.DedupeCIFForm;
import com.etel.inquiry.forms.InquiryForm;
import com.etel.inquiry.forms.MembershipApplicationForm;
import com.etel.inquiry.forms.MembershipListPerStagesForm;
import com.etel.inquiry.forms.NonMemberInquiryForm;
import com.etel.utils.AuditLog;
import com.etel.utils.AuditLogEvents;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class InquiryServiceImpl implements InquiryService {
	private DBService dbService = new DBServiceImpl();
	private DBService dbServiceCIF = new DBServiceImplCIF();
	private Map<String, Object> param = HQLUtil.getMap();
	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	Map<String, Object> params = new HashMap<String, Object>();

	@Override
	public List<Tbmember> searchMember(String memberid, String membername, Integer page, Integer maxresult) {
		// TODO Auto-generated method stub
		try {
			// get all member
			if (memberid == null && membername == null) {
				@SuppressWarnings("unchecked")
				List<Tbmember> m = (List<Tbmember>) dbService.execSQLQueryTransformer(
						"SELECT membershipclass, dateofbirth, governancetype, membershipappid, membershipstatus, membershipid, CONCAT (lastname, ', ', firstname, ' ', middlename) as firstname,coopcode, employeeid "
								+ "FROM Tbmember ORDER BY membershipid OFFSET (" + page + " - 1)*(" + maxresult
								+ ") ROWS FETCH NEXT " + maxresult + " ROWS ONLY",
						param, Tbmember.class, 1);
				if (m != null) {
					return m;
				}
			}
			// unique result
			if (memberid != null) {
				param.put("id", memberid);
				@SuppressWarnings("unchecked")
				List<Tbmember> m = (List<Tbmember>) dbService.execSQLQueryTransformer(
						"SELECT membershipclass, dateofbirth, governancetype, membershipappid, membershipstatus, membershipid, CONCAT (lastname, ', ', firstname, ' ', middlename) as firstname, employeeid, coopcode "
								+ "FROM Tbmember WHERE membershipid=:id",
						param, Tbmember.class, 1);
				if (m != null) {
					return m;
				}
			}
			// membername
			if (membername != null && memberid == null) {
				int spacenumber = membername.split("\\ ", -1).length - 1;
				int dividedstring = spacenumber + 1;
				StringBuilder query = new StringBuilder("" + "SELECT membershipclass, dateofbirth, governancetype, "
						+ "membershipappid, membershipstatus, membershipid, "
						+ "CONCAT (lastname, ', ', firstname, ' ', middlename) as firstname, "
						+ "coopcode, sss, gsis, tin, employeeid, companycode " + "FROM Tbmember WHERE");
				if (membername.indexOf(" ") != -1) { // if there's a space..multiple parameters<<
					String[] n = membername.split(" ");
					for (int i = 0; i < dividedstring; i++) {
						if (n[i].indexOf(",") != -1) { // if there's a comma..delete comma<<
							n[i] = n[i].replace(",", "");
						}
						String nameque = "name" + i;
						param.put(nameque, n[i] == null ? "'%%'" : "%" + n[i] + "%");
						query.append(" (lastname like:" + nameque + " or firstname like:" + nameque
								+ " or middlename like:" + nameque + " or sss like:" + nameque + " or tin like:"
								+ nameque + " or gsis like:" + nameque + " or employeeid like:" + nameque
								+ " or membershipid like:" + nameque + " or membershipappid like:" + nameque + ") ");
						query.append(" OR ");
					}
				}
				param.put("fullname", membername == null ? "'%%'" : "%" + membername + "%");
				query.append(" (lastname like:fullname or firstname like:fullname or"
						+ " middlename like:fullname or sss like:fullname or "
						+ "tin like:fullname or gsis like:fullname or "
						+ "membershipid like:fullname or membershipappid like:fullname or "
						+ "employeeid like:fullname) ");
				if (page != null && maxresult != null) {
					query.append("ORDER BY membershipid OFFSET (" + page + " - 1)*(" + maxresult + ") ROWS FETCH NEXT "
							+ maxresult + " ROWS ONLY");
				}
				@SuppressWarnings("unchecked")
				List<Tbmember> search = (List<Tbmember>) dbService.execSQLQueryTransformer(query.toString(), param,
						Tbmember.class, 1);
				if (search != null) {
					return search;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public Tbmember getMember(String memberid) {
		// TODO Auto-generated method stub
		try {
			if (memberid != null) {
				param.put("id", memberid);
				Tbmember e = (Tbmember) dbService.executeUniqueHQLQuery("FROM Tbmember WHERE membershipid=:id", param);
				Tbmember m = (Tbmember) dbService.execSQLQueryTransformer("SELECT "
						+ "(SELECT CONCAT (chaptername, +' - '+ chaptercode) FROM TBCHAPTER where chaptercode=(SELECT chaptercode FROM TBMEMBER where membershipid=:id)) as chaptercode, "
						+ "(SELECT CONCAT (coopname, +' - '+ coopcode) FROM TBCOOPERATIVE where coopcode=(SELECT coopcode FROM TBMEMBER where membershipid=:id)) as coopcode, "
						+ "(SELECT CONCAT (branchname, +' - '+ branchcode) FROM TBBRANCH where coopcode=(SELECT coopcode FROM TBMEMBER where membershipid=:id) and branchcode=(SELECT branch FROM TBMEMBER where membershipid=:id)) as branch, "
						+ "(SELECT CONCAT (companyname, +' - '+ companycode) FROM TBMEMBERCOMPANY where coopcode=(SELECT coopcode FROM TBMEMBER where membershipid=:id) and companycode=(SELECT companycode FROM TBMEMBER where membershipid=:id)) as companycode "
						+ "FROM TBMEMBER m where membershipid=:id", param, Tbmember.class, 0);
				if (e != null) {
					if (m != null) {
						if (m.getCoopcode() != null) {
							e.setCoopcode(m.getCoopcode());
						}
						if (m.getCompanycode() != null) {
							e.setCompanycode(m.getCompanycode());
						}
						if (m.getBranch() != null) {
							e.setBranch(m.getBranch());
						}
						if (m.getChaptercode() != null) {
							e.setChaptercode(m.getChaptercode());
						}
						e.setSpousefirstname(e.getSpousefirstname() == null ? "" : e.getSpousefirstname());
						e.setSpousemiddlename(e.getSpousemiddlename() == null ? "" : e.getSpousemiddlename());
						e.setSpouselastname(e.getSpouselastname() == null ? "" : e.getSpouselastname());
					}
					return e;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer countSearchedMember(String membername, String memberid) {
		// TODO Auto-generated method stub
		Integer count = 0;
		try {
			param.put("memberid", memberid);
			param.put("membername", membername);
			if (memberid != null) {
				count = (Integer) dbService.executeUniqueSQLQuery(
						"SELECT COUNT(membershipid) FROM Tbmember WHERE membershipid=:memberid", param);
			} else if (membername != null && memberid == null) {
				int spacenumber = membername.split("\\ ", -1).length - 1;
				int dividedstring = spacenumber + 1;
				StringBuilder query = new StringBuilder("" + "SELECT COUNT(membershipid) FROM Tbmember WHERE");
				if (membername.indexOf(" ") != -1) { // if there's a space..multiple parameters<<
					String[] n = membername.split(" ");
					for (int i = 0; i < dividedstring; i++) {
						if (n[i].indexOf(",") != -1) { // if there's a comma..delete comma<<
							n[i] = n[i].replace(",", "");
						}
						String nameque = "name" + i;
						param.put(nameque, n[i] == null ? "'%%'" : "%" + n[i] + "%");
						query.append(" (lastname like:" + nameque + " or firstname like:" + nameque
								+ " or middlename like:" + nameque + ") ");
						query.append(" OR ");
					}
				}
				param.put("fullname", membername == null ? "'%%'" : "%" + membername + "%");
				query.append(" (lastname like:fullname or firstname like:fullname or middlename like:fullname) ");
				count = (Integer) dbService.executeUniqueSQLQuery(query.toString(), param);
			} else {
				count = (Integer) dbService.executeUniqueSQLQuery("SELECT COUNT(membershipid) FROM Tbmember", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InquiryForm> applicationInquiry(String appno, String memberid, String fname, String lname,
			String applicationstatus, String loanproduct, String corporatename, Integer page, Integer maxresult,
			String customertype, Integer applicationtype) {
		List<InquiryForm> list = new ArrayList<InquiryForm>();
		DBService dbServiceCOOP = new DBServiceImpl();
		try {
			corporatename = UserUtil.getUserByUsername(secservice.getUserName()).getCoopcode();
			params.put("appno", appno == null ? "%" : "%" + appno + "%");
			params.put("lname", lname == null ? "%" : "%" + lname + "%");
			params.put("fname", fname == null ? "%" : "%" + fname + "%");
			// params.put("mname", mname==null?"%":"%"+mname+"%");
			params.put("corporatename", corporatename == null ? "%" : "%" + corporatename + "%");

			// param.put("appno", appno);
			param.put("memberid", memberid);
			// param.put("fname", fname);
			// param.put("lname", lname);
			param.put("applicationstatus", applicationstatus);
			param.put("loanproduct", loanproduct);
			// param.put("corporatename", corporatename);
			param.put("page", page);
			param.put("maxresult", maxresult);
			param.put("customertype", customertype);
			param.put("applicationtype", applicationtype);

			/*
			 * String q =
			 * "EXEC sp_ApplicationInquiry :page , :maxresult, :appno, :memberid, :fname, :lname, :corporatename, :loanproduct, :applicationstatus"
			 * + ", 3, 1, :customertype";
			 */

			String q = "EXEC sp_ApplicationInquiry :page , :maxresult, :appno, :memberid, :fname, :lname, :corporatename, :loanproduct, :customertype";
			list = (List<InquiryForm>) dbServiceCOOP.execSQLQueryTransformer(q, param, InquiryForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int applicationInquiryCount(String appno, String memberid, String fname, String lname,
			String applicationstatus, String loanproduct, String corporatename, Integer page, Integer maxresult,
			String customertype, Integer applicationtype) {
		Integer count = 0;
		DBService dbService = new DBServiceImpl();
		try {
			params.put("appno", appno == null ? "%" : "%" + appno + "%");
			params.put("lname", lname == null ? "%" : "%" + lname + "%");
			params.put("fname", fname == null ? "%" : "%" + fname + "%");
			params.put("corpname", corporatename == null ? "%" : "%" + corporatename + "%");
			params.put("customertype", customertype);
			params.put("page", page);
			params.put("maxresult", maxresult);

			String q = "EXEC sp_ApplicationInquiry @page=" + page + ", @maxresult=" + maxresult + ", @appno=" + appno
					+ ", @lname=:lname, @fname=:fname, @corpname=:corpname, @ispagingon='true', @customertype = "
					+ customertype + " ";
			count = (Integer) dbService.execSQLQueryTransformer(q, params, null, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbmemberemployment> listEmployment(String membershipid) {
		try {
			param.put("memid", membershipid);
			List<Tbmemberemployment> e = (List<Tbmemberemployment>) dbService
					.executeListHQLQuery("FROM Tbmemberemployment WHERE membershipid=:memid", param);
			if (e != null)
				return e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbmemberbusiness> listBusiness(String membershipid) {
		try {
			param.put("memid", membershipid);
			List<Tbmemberbusiness> b = (List<Tbmemberbusiness>) dbService
					.executeListHQLQuery("FROM Tbmemberbusiness WHERE membershipid=:memid", param);
			if (b != null)
				return b;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tblstapp> searchLoanApplications(String appno, String memberid, String appstatus, String firstname,
			String lastname, Integer page, Integer maxresult, String loanproduct) {
		List<Tblstapp> list = new ArrayList<Tblstapp>();
		try {
			StringBuilder query = new StringBuilder();
			query.append(
					"SELECT a.appno, a.cifno, a.cifname, a.applicationtype, a.applicationstatus, a.accountofficer, (SELECT productname FROM TBLOANPRODUCT WHERE productcode=a.loanproduct)as loanproduct, a.datecreated, a.statusdatetime FROM Tblstapp a WHERE ");
			if (appno != null) {
				param.put("appno", appno);
				Tblstapp unique = (Tblstapp) dbService.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", param);
				if (unique != null) {
					list.add(unique);
				}
				return list;
			}
			if (memberid != null) {
				param.put("cifno", memberid);
				query.append("a.cifno=:cifno AND ");
			}
			if (appstatus != null) {
				param.put("appstatus", appstatus);
				query.append("a.applicationstatus=:appstatus AND ");
			}
			if (loanproduct != null) {
				param.put("loanproduct", loanproduct);
				query.append("a.loanproduct=:loanproduct AND ");
			}
			if (firstname != null) {
				param.put("firstname", firstname == null ? "'%%'" : "%" + firstname + "%");
				query.append("a.cifname like:firstname AND ");
			}
			if (lastname != null) {
				param.put("lastname", lastname == null ? "'%%'" : "%" + lastname + "%");
				query.append("a.cifname like:lastname AND ");
			}
//			System.out.println(query.toString().substring(0, query.toString().length() - 4));
			list = (List<Tblstapp>) dbService.execSQLQueryTransformer(
					query.toString().substring(0, query.toString().length() - 5), param, Tblstapp.class, 1);

			System.out.println(">>>>>>>>>>> query : " + query.toString().substring(0, query.toString().length() - 4));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Tbloans> getListofLoanAccounts(String name, String accountno, String refno) {
		// TODO Auto-generated method stub
//		HttpClient http = new HttpClient();
		List<Tbloans> listOfLoanAccounts = new ArrayList<Tbloans>();
		String query = "FROM Tbloans WHERE fullname <> '' ";
		query += name.length() > 0 ? "AND (fullname like:name) " : "";
		query += accountno.length() > 0 ? "AND accountno =:acctno " : "";
		query += refno.length() > 0 ? "AND refno=:refno " : "";
		param.put("name", "%" + name + "%");
		param.put("acctno", accountno);
		param.put("refno", refno);
		System.out.println(query);
		try {
//			 http.main(refno);
//			listOfLoanAccounts = (List<LoanInquiryForm>) dbService.execStoredProc(query, param, LoanInquiryForm.class, 1, null, null);
			listOfLoanAccounts = (List<Tbloans>) dbService.executeListHQLQueryWitMaxResults(query, 20, param);
//			listOfLoanAccounts.stream().map
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listOfLoanAccounts;
	}

	@Override
	public List<Tblntxjrnl> findLedgerByAccountno(String accountno) {
		// TODO Auto-generated method stub
		List<Tblntxjrnl> listOfTx = new ArrayList<Tblntxjrnl>();
		param.put("acctno", accountno);
		try {
			listOfTx = (List<Tblntxjrnl>) dbService.executeListHQLQuery("FROM Tblntxjrnl WHERE accountno =:acctno",
					param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return listOfTx;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbpaysched> findPayschedByAccountno(String accountno) {
		// TODO Auto-generated method stub
		List<Tbpaysched> listOfPaysched = new ArrayList<Tbpaysched>();
		param.put("acctno", accountno);
		try {
			listOfPaysched = (List<Tbpaysched>) dbService
					.executeListHQLQuery("FROM Tbpaysched WHERE accountno =:acctno", param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return listOfPaysched;
	}

	@Override
	public List<Tbmember> searchMemberBills(String memberid, String membername, Integer page, Integer maxresult) {
		// TODO Auto-generated method stub
		try {
			AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_SEARCH_MEMBER_PAYMENT),
					"User " + UserUtil.securityService.getUserName() + " Searched Member Record.",
					UserUtil.securityService.getUserName(), new Date(),
					AuditLogEvents.getEventModule(AuditLogEvents.M_SEARCH_MEMBER_PAYMENT));
			// get all member
			if (memberid == null && membername == null) {
				@SuppressWarnings("unchecked")
				List<Tbmember> m = (List<Tbmember>) dbService.execSQLQueryTransformer(
						"SELECT a.dateofbirth, governancetype, a.membershipappid, membershipstatus, a.membershipid, CONCAT (a.lastname, ', ', a.firstname, ' ', a.middlename) as firstname,a.coopcode, a.employeeid "
								+ "FROM Tbmember a left join TBMEMBERSHIPAPP b on a.membershipappid = b.membershipappid WHERE a.membershipstatus='0' ORDER BY a.membershipid OFFSET ("
								+ page + " - 1)*(" + maxresult + ") ROWS FETCH NEXT " + maxresult + " ROWS ONLY",
						param, Tbmember.class, 1);
				if (m != null) {
					return m;
				}
			}
			// unique result
			if (memberid != null) {
				param.put("id", memberid);
				@SuppressWarnings("unchecked")
				List<Tbmember> m = (List<Tbmember>) dbService.execSQLQueryTransformer(
						"SELECT a.dateofbirth, governancetype, a.membershipappid, membershipstatus, a.membershipid, CONCAT (a.lastname, ', ', a.firstname, ' ', a.middlename) as firstname,a.coopcode, a.employeeid "
								+ "FROM Tbmember a left join TBMEMBERSHIPAPP b on a.membershipappid = b.membershipappid WHERE membershipappstatus='7' AND a.membershipid=:id",
						param, Tbmember.class, 1);
				if (m != null) {
					return m;
				}
			}
			// membername
			if (membername != null && memberid == null) {
				int spacenumber = membername.split("\\ ", -1).length - 1;
				int dividedstring = spacenumber + 1;
				StringBuilder query = new StringBuilder(""
						+ "SELECT a.dateofbirth, governancetype, a.membershipappid, membershipstatus, a.membershipid, CONCAT (a.lastname, ', ', a.firstname, ' ', a.middlename) as firstname,a.coopcode, a.employeeid "
						+ "FROM Tbmember a left join TBMEMBERSHIPAPP b on a.membershipappid = b.membershipappid WHERE membershipappstatus='4' AND ");
				if (membername.indexOf(" ") != -1) { // if there's a space..multiple parameters<<
					String[] n = membername.split(" ");
					for (int i = 0; i < dividedstring; i++) {
						if (n[i].indexOf(",") != -1) { // if there's a comma..delete comma<<
							n[i] = n[i].replace(",", "");
						}
						String nameque = "name" + i;
						param.put(nameque, n[i] == null ? "'%%'" : "%" + n[i] + "%");
						query.append(" (a.lastname like:" + nameque + " or a.firstname like:" + nameque
								+ " or a.middlename like:" + nameque + ") ");
						query.append(" OR ");
					}
				}
				param.put("fullname", membername == null ? "'%%'" : "%" + membername + "%");
				query.append(" (a.lastname like:fullname or a.firstname like:fullname or a.middlename like:fullname) ");
				if (page != null && maxresult != null) {
					query.append("ORDER BY a.membershipid OFFSET (" + page + " - 1)*(" + maxresult
							+ ") ROWS FETCH NEXT " + maxresult + " ROWS ONLY");
				}
				@SuppressWarnings("unchecked")
				List<Tbmember> search = (List<Tbmember>) dbService.execSQLQueryTransformer(query.toString(), param,
						Tbmember.class, 1);
				if (search != null) {

					return search;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer countSearchedMemberBills(String membername, String memberid) {
		// TODO Auto-generated method stub
		Integer count = 0;
		try {
			param.put("memberid", memberid);
			param.put("membername", membername);
			if (memberid != null) {
				count = (Integer) dbService.executeUniqueSQLQuery(
						"SELECT COUNT(a.membershipid) FROM Tbmember a left join TBMEMBERSHIPAPP b on a.membershipappid = b.membershipappid WHERE membershipappstatus='4' AND a.membershipid=:memberid",
						param);
			} else if (membername != null && memberid == null) {
				int spacenumber = membername.split("\\ ", -1).length - 1;
				int dividedstring = spacenumber + 1;
				StringBuilder query = new StringBuilder(""
						+ "SELECT COUNT(a.membershipid) FROM Tbmember a left join TBMEMBERSHIPAPP b on a.membershipappid = b.membershipappid WHERE membershipappstatus='4' AND ");
				if (membername.indexOf(" ") != -1) { // if there's a space..multiple parameters<<
					String[] n = membername.split(" ");
					for (int i = 0; i < dividedstring; i++) {
						if (n[i].indexOf(",") != -1) { // if there's a comma..delete comma<<
							n[i] = n[i].replace(",", "");
						}
						String nameque = "name" + i;
						param.put(nameque, n[i] == null ? "'%%'" : "%" + n[i] + "%");
						query.append(" (a.lastname like:" + nameque + " or a.firstname like:" + nameque
								+ " or a.middlename like:" + nameque + ") ");
						query.append(" OR ");
					}
				}
				param.put("fullname", membername == null ? "'%%'" : "%" + membername + "%");
				query.append(" (a.lastname like:fullname or a.firstname like:fullname or a.middlename like:fullname) ");
				count = (Integer) dbService.executeUniqueSQLQuery(query.toString(), param);
			} else {
				count = (Integer) dbService.executeUniqueSQLQuery(
						"SELECT COUNT(a.membershipid) FROM Tbmember a left join TBMEMBERSHIPAPP b on a.membershipappid = b.membershipappid WHERE membershipappstatus='4'",
						null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NonMemberInquiryForm> listNonMember(String name) {
		List<NonMemberInquiryForm> list = new ArrayList<NonMemberInquiryForm>();
		List<NonMemberInquiryForm> finalList = new ArrayList<NonMemberInquiryForm>();
		// NonMemberInquiryFormList form = new NonMemberInquiryFormList();
		try {
			if (name != null) {
				param.put("name", name == null ? "'%%'" : "%" + name.trim() + "%");

				// agent member
				String q = "SELECT (UPPER(c.lastname) + ', ' + UPPER(c.firstname) + ' ' +  UPPER(c.middlename)) as name,"
						+ "'AGENT' as type," + "c.agentcode as codeno,"
						+ "(b.lastname + ', ' + b.firstname + ' ' +  b.middlename) as refMemberName,"
						+ "'MEMBERSHIP APPLICATION' as referencetype," + "b.membershipid as acctno" + " FROM TBMEMBER b"
						+ " LEFT JOIN TBAGENTS c ON c.agentcode=b.agentcode"
						+ " WHERE b.agentcode IN (SELECT a.agentcode FROM TBAGENTS a WHERE a.lastname LIKE:name OR a.firstname LIKE:name OR a.middlename LIKE:name)";
				list = (List<NonMemberInquiryForm>) dbService.execSQLQueryTransformer(q, param,
						NonMemberInquiryForm.class, 1);
				if (list != null) {
					for (NonMemberInquiryForm f : list) {
						NonMemberInquiryForm n = new NonMemberInquiryForm();
						n.setName(f.getName());
						n.setType(f.getType());
						n.setCodeno(f.getCodeno());
						n.setRefMemberName(f.getRefMemberName());
						n.setReferencetype(f.getReferencetype());
						n.setAcctno(f.getAcctno());
						finalList.add(n);
					}
				}
				// comaker
				String q2 = "SELECT UPPER(a.customername) as name," + "'COMAKER' as type," + "a.cifno as codeno,"
						+ "b.cifname as refMemberName," + "'LOAN APPLICATION' as referencetype," + "b.appno as acctno"
						+ " FROM TBLSTCOMAKERS a" + " LEFT JOIN TBLSTAPP b ON b.appno=a.appno"
						+ " WHERE a.customername LIKE:name";
				list = (List<NonMemberInquiryForm>) dbService.execSQLQueryTransformer(q2, param,
						NonMemberInquiryForm.class, 1);
				if (list != null) {
					for (NonMemberInquiryForm f : list) {
						NonMemberInquiryForm n = new NonMemberInquiryForm();
						n.setName(f.getName());
						n.setType(f.getType());
						n.setCodeno(f.getCodeno());
						n.setRefMemberName(f.getRefMemberName());
						n.setReferencetype(f.getReferencetype());
						n.setAcctno(f.getAcctno());
						finalList.add(n);
					}
				}
				// ao - membership
				String q3 = "SELECT (UPPER(c.lastname) + ', ' + UPPER(c.firstname) + ' ' +  UPPER(c.middlename)) as name,"
						+ "'ACCOUNT OFFICER' as type," + "b.accountofficer as codeno,"
						+ "(b.lastname + ', ' + b.firstname + ' ' +  b.middlename) as refMemberName,"
						+ "'MEMBERSHIP APPLICATION' as referencetype," + "b.membershipid as acctno" + " FROM TBMEMBER b"
						+ " LEFT JOIN TBACCOUNTOFFICER c ON c.aocode=b.accountofficer"
						+ " WHERE b.accountofficer IN(SELECT a.aocode FROM TBACCOUNTOFFICER a WHERE a.lastname LIKE:name OR a.firstname LIKE:name OR a.middlename LIKE:name)";
				list = (List<NonMemberInquiryForm>) dbService.execSQLQueryTransformer(q3, param,
						NonMemberInquiryForm.class, 1);
				if (list != null) {
					for (NonMemberInquiryForm f : list) {
						NonMemberInquiryForm n = new NonMemberInquiryForm();
						n.setName(f.getName());
						n.setType(f.getType());
						n.setCodeno(f.getCodeno());
						n.setRefMemberName(f.getRefMemberName());
						n.setReferencetype(f.getReferencetype());
						n.setAcctno(f.getAcctno());
						finalList.add(n);
					}
				}
				// relative
				String q4 = "SELECT (UPPER(c.lastname) + ', ' + UPPER(c.firstname) + ' ' +  UPPER(c.middlename)) as name,"
						+ "'RELATIVE' as type," + "c.relationshipdesc as codeno,"
						+ "(b.lastname + ', ' + b.firstname + ' ' +  b.middlename) as refMemberName,"
						+ "'MEMBERSHIP APPLICATION' as referencetype," + "b.membershipid as acctno" + " FROM TBMEMBER b"
						+ " LEFT JOIN TBMEMBERRELATIONSHIP c ON c.mainmemberid=b.membershipid"
						+ " WHERE b.membershipid IN (SELECT a.mainmemberid FROM TBMEMBERRELATIONSHIP a WHERE a.lastname LIKE:name OR a.firstname LIKE:name OR a.middlename LIKE:name)";
				list = (List<NonMemberInquiryForm>) dbService.execSQLQueryTransformer(q4, param,
						NonMemberInquiryForm.class, 1);
				if (list != null) {
					for (NonMemberInquiryForm f : list) {
						NonMemberInquiryForm n = new NonMemberInquiryForm();
						n.setName(f.getName());
						n.setType(f.getType());
						n.setCodeno(f.getCodeno());
						n.setRefMemberName(f.getRefMemberName());
						n.setReferencetype(f.getReferencetype());
						n.setAcctno(f.getAcctno());
						finalList.add(n);
					}
				}
				list = finalList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbmembershipapp> searchMembershipApplication(MembershipApplicationForm srch, Integer page,
			Integer maxresult) {
		// TODO Auto-generated method stub
		String query = "SELECT membershipappid, firstname, lastname, middlename, membershipclass, membershipappstatus, encodedby, encodeddate, companycode, originatingbranch, accountofficer FROM Tbmembershipapp WHERE ";
		param.put("membershipappid", srch.getMembershipappid());
		param.put("firstname", srch.getFirstname() != null ? "%" + srch.getFirstname() + "%" : "%%");
		param.put("middlename", srch.getMiddlename() != null ? "%" + srch.getMiddlename() + "%" : "%%");
		param.put("lastname", srch.getLastname() != null ? "%" + srch.getLastname() + "%" : "%%");
		param.put("originatingbranch", srch.getOriginatingbranch());
		param.put("branchofservice", srch.getBranchofservice());
		param.put("accountofficer", srch.getAccountofficer());
		param.put("employeeid", srch.getEmployeeid());
		param.put("membershiptype", srch.getMembershiptype());
		param.put("encoder", srch.getEncoder());
		param.put("dateencoded", srch.getEncodeddate());
		param.put("status", srch.getApplicationstatus());
		List<Tbmembershipapp> list = new ArrayList<Tbmembershipapp>();
		try {
			if (srch.getMembershipappid() != null) {
				query += "membershipappid=:membershipappid AND ";
			}
			if (srch.getFirstname() != null) {
				query += "firstname like:firstname AND ";
			}
			if (srch.getMiddlename() != null) {
				query += "middlename like:middlename AND ";
			}
			if (srch.getLastname() != null) {
				query += "lastname like:lastname AND ";
			}
			if (srch.getOriginatingbranch() != null) {
				query += "originatingbranch =:originatingbranch AND ";
			}
			if (srch.getBranchofservice() != null) {
				query += "companycode =:branchofservice AND ";
			}
			if (srch.getAccountofficer() != null) {
				query += "accountofficer =:accountofficer AND ";
			}
			if (srch.getEmployeeid() != null) {
				query += "employeeid =:employeeid AND ";
			}
			if (srch.getMembershiptype() != null) {
				query += "membershipclass =:membershiptype AND ";
			}
			if (srch.getEncoder() != null) {
				query += "encodedby =:encoder AND ";
			}
			if (srch.getEncodeddate() != null) {
				query += "encodeddate =:dateencoded AND ";
			}
			if (srch.getApplicationstatus() != null) {
				query += "membershipappstatus =:status AND ";
			}
			query = query.substring(0, query.length() - 5);

			if (page != 0 && maxresult != 0) {
				query += " ORDER BY membershipappid OFFSET (" + page + " - 1)*(" + maxresult + ") ROWS FETCH NEXT "
						+ maxresult + " ROWS ONLY";
			}
			list = (List<Tbmembershipapp>) dbService.execSQLQueryTransformer(query, param, Tbmembershipapp.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public Integer searchMembershipApplicationCount(MembershipApplicationForm srch, Integer page, Integer maxresult) {
		// TODO Auto-generated method stub
		String query = "SELECT COUNT(*) FROM Tbmembershipapp WHERE ";
		param.put("membershipappid", srch.getMembershipappid());
		param.put("firstname", srch.getFirstname() != null ? "%" + srch.getFirstname() + "%" : "%%");
		param.put("middlename", srch.getMiddlename() != null ? "%" + srch.getMiddlename() + "%" : "%%");
		param.put("lastname", srch.getLastname() != null ? "%" + srch.getLastname() + "%" : "%%");
		param.put("originatingbranch", srch.getOriginatingbranch());
		param.put("branchofservice", srch.getBranchofservice());
		param.put("accountofficer", srch.getAccountofficer());
		param.put("employeeid", srch.getEmployeeid());
		param.put("membershiptype", srch.getMembershiptype());
		param.put("encoder", srch.getEncoder());
		param.put("dateencoded", srch.getEncodeddate());
		param.put("status", srch.getApplicationstatus());
		List<Tbmembershipapp> list = new ArrayList<Tbmembershipapp>();
		try {
			if (srch.getMembershipappid() != null) {
				query += "membershipappid=:membershipappid AND ";
			}
			if (srch.getFirstname() != null) {
				query += "firstname like:firstname AND ";
			}
			if (srch.getMiddlename() != null) {
				query += "middlename like:middlename AND ";
			}
			if (srch.getLastname() != null) {
				query += "lastname like:lastname AND ";
			}
			if (srch.getOriginatingbranch() != null) {
				query += "originatingbranch =:originatingbranch AND ";
			}
			if (srch.getBranchofservice() != null) {
				query += "companycode =:branchofservice AND ";
			}
			if (srch.getAccountofficer() != null) {
				query += "accountofficer =:accountofficer AND ";
			}
			if (srch.getEmployeeid() != null) {
				query += "employeeid =:employeeid AND ";
			}
			if (srch.getMembershiptype() != null) {
				query += "membershipclass =:membershiptype AND ";
			}
			if (srch.getEncoder() != null) {
				query += "encodedby =:encoder AND ";
			}
			if (srch.getEncodeddate() != null) {
				query += "encodeddate =:dateencoded AND ";
			}
			if (srch.getApplicationstatus() != null) {
				query += "membershipappstatus =:status AND ";
			}
			query = query.substring(0, query.length() - 5);
			Integer i = (Integer) dbService.executeUniqueSQLQuery(query, param);
			return i;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return 0;
	}

	// Renz
	@SuppressWarnings("unchecked")
	@Override
	public List<CIFInquiryForm> searchCIF(String customertype, String cifnumber, String cifstatus, String fullname,
			String ciftype, Date birthdate, Date encodeddate, Date dateofincorporation, Integer page, Integer maxResult,
			String lname, String fname, String mname, String fulladdress) {
		List<CIFInquiryForm> main = new ArrayList<CIFInquiryForm>();
		DBService dbService = new DBServiceImplCIF();
		DBService dbServiceCoop = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		StringBuilder query = new StringBuilder();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dob = null;
		String ecd = null;
		String dtofinc = null;
		params.put("branchcode", UserUtil.getUserByUsername(secservice.getUserName()).getBranchcode());
		if (birthdate != null) {
			dob = formatter.format(birthdate);
		}
		if (encodeddate != null) {
			ecd = formatter.format(encodeddate);
		}
		if (dateofincorporation != null) {
			dtofinc = formatter.format(dateofincorporation);
		}
		boolean name = false;
		if (lname == null && fname == null && mname == null && fullname == null) {
			name = true;
		}
		try {
			if (cifnumber == null && name && cifstatus == null && encodeddate == null && ciftype == null
					&& birthdate == null && dateofincorporation == null && fulladdress == null) {
			} else {
				if (customertype.equals("2")) {
					// INDIVIDUAL
					query.append(
							"SELECT a.cifno, a.fullname, a.dateofbirth, b.tin ,a.encodeddate, a.encodedby, a.cifapproveddate, a.cifapprovedby,");
					query.append(
							"a.ciftype, a.cifstatus, a.borrowerfundertype, a.dateofincorporation, a.customertype, a.fulladdress1, a.fulladdress2,");
					query.append(
							"a.assignedto, a.cifpurpose, a.isencoding FROM Tbcifmain a, Tbcifindividual b WHERE a.originatingbranch =:branchcode AND a.customertype = '2' AND a.cifno=b.cifno AND a.cifstatus != '6' AND ");
					if (cifnumber != null) {
						query.append("a.cifno like '%" + cifnumber + "%' AND ");
					}
					if (cifstatus != null) {
						// For Encoding
						if (cifstatus.equals("For Encoding")) {
							query.append("a.cifstatus = '1' AND ");
						}
						// For Document Submission
						else if (cifstatus.equals("For Document Submission")) {
							query.append("a.cifstatus = '2' AND ");
						}
						// For HR Review
						else if (cifstatus.equals("For HR Review")) {
							query.append("a.cifstatus = '3' AND ");
						}
						// For Approval
						else if (cifstatus.equals("For Approval")) {
							query.append("a.cifstatus = '4' AND ");

						}
						// Approved
						else if (cifstatus.equals("Approved")) {
							query.append("a.cifstatus = '5' AND ");
						}
					}
					if (encodeddate != null) {
						query.append("(a.encodeddate BETWEEN '" + ecd + " 00:00:00' AND '" + ecd + " 23:59:00') AND ");
					}
					if (ciftype != null) {
						query.append("a.ciftype = '" + ciftype + "' AND ");
					}
					if (fulladdress != null) {
						query.append("(a.fulladdress1 LIKE '%" + fulladdress + "%' OR a.fulladdress2 LIKE '%"
								+ fulladdress + "%') AND ");
					}
					if (birthdate != null) {
						query.append("(a.dateofbirth BETWEEN '" + dob + " 00:00:00' AND '" + dob + " 23:59:00') AND ");
					}
					if (lname != null) {
						query.append("b.lastname like '%" + lname + "%' AND ");
					}
					if (fname != null) {
						query.append("b.firstname like '%" + fname + "%' AND ");
					}
					if (mname != null) {
						query.append("b.middlename like '%" + mname + "%' AND ");
					}
					if (fullname != null) {
						query.append("fullname like '%" + fullname + "%' AND ");
					}
					query.append("a.customertype = '" + customertype + "' ORDER BY a.fullname ASC AND ");
					query.append("-----");
					
				} else if (customertype.equals("1")) {
					// CORPORATE
					query.append(
							"SELECT cifno, fullname, dateofbirth,'' as tin, encodeddate, encodedby, cifapproveddate, cifapprovedby, ciftype, cifstatus, borrowerfundertype, dateofincorporation, customertype, fulladdress1, fulladdress2, assignedto, cifpurpose , isencoding FROM Tbcifmain WHERE originatingbranch =:branchcode AND customertype = '1' AND cifstatus != '5' AND ");
					if (cifnumber != null) {
						query.append("cifno like '%" + cifnumber + "%' AND ");
					}
					
					if (cifstatus != null) {
						// For Encoding
						if (cifstatus.equals("For Encoding")) {
							query.append("cifstatus = '1' AND ");
						}
						// For Approval of Cluster Head
						else if (cifstatus.equals("For Approval of Cluster Head")) {
							query.append("cifstatus = '2' AND ");
						}
						// For Approval
						else if (cifstatus.equals("For Approval")) {
							query.append("cifstatus = '3' AND ");
						}
						// Approved
						else if (cifstatus.equals("Approved")) {
							query.append("cifstatus = '4' AND ");
						}
					}
					
					if (encodeddate != null) {
						query.append("(encodeddate BETWEEN '" + ecd + " 00:00:00' AND '" + ecd + " 23:59:00') AND ");
					}
					if (ciftype != null) {
						query.append("ciftype = '" + ciftype + "' AND ");
					}
					if (fulladdress != null) {
						query.append("(fulladdress1 LIKE '%" + fulladdress + "%' OR fulladdress2 LIKE '%" + fulladdress
								+ "%') AND ");
					}
					if (dateofincorporation != null) {
						query.append("(dateofincorporation BETWEEN '" + dtofinc + " 00:00:00' AND '" + dtofinc
								+ " 23:59:00') AND ");
					}
					if (fullname != null) {
						query.append("fullname like '%" + fullname + "%' AND ");
					}
					query.append("customertype = '" + customertype + "' ORDER BY fullname ASC AND ");
					query.append("-----");
				}
				String indivcorpquery = query.substring(0, query.length() - 10);
				System.out.println("<<<<<indivcorpquery>>>>> " + indivcorpquery);
				List<Object> obj = (List<Object>) dbService.executeListSQLQueryWithFirstAndMaxResults(indivcorpquery,
						params, page, maxResult);
				if (obj != null) {
					for (Object obj3 : obj) {
						Object[] obj2 = (Object[]) obj3;
						ArrayList<String> dta = new ArrayList<String>();
						for (Object obj1 : obj2) {
							dta.add(String.valueOf(obj1));
						}
						CIFInquiryForm form = new CIFInquiryForm();
						SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						if (dta.get(0) != null && !dta.get(0).equals("") && !dta.get(0).equalsIgnoreCase("null")) {
							form.setCifno(dta.get(0));
						}
						if (dta.get(1) != null && !dta.get(1).equals("") && !dta.get(1).equalsIgnoreCase("null")) {
							form.setFullname(dta.get(1));
						}
						if (dta.get(2) != null && !dta.get(2).equals("") && !dta.get(2).equalsIgnoreCase("null")) {
							form.setBirthdate(formatter1.parse(dta.get(2)));
						}
						if (dta.get(3) != null && !dta.get(3).equals("") && !dta.get(3).equalsIgnoreCase("null")) {
							form.setTin(dta.get(3));
						}
						if (dta.get(4) != null && !dta.get(4).equals("") && !dta.get(4).equalsIgnoreCase("null")) {
							form.setEncodeddate(formatter1.parse(dta.get(4)));
						}
						if (dta.get(5) != null && !dta.get(5).equals("") && !dta.get(5).equalsIgnoreCase("null")) {
							form.setEncodedby(UserUtil.getUserFullname(dta.get(5)));
						}
						if (dta.get(6) != null && !dta.get(6).equals("") && !dta.get(6).equalsIgnoreCase("null")) {
							form.setCifapproveddate(formatter1.parse(dta.get(6)));
						}
						if (dta.get(7) != null && !dta.get(7).equals("") && !dta.get(7).equalsIgnoreCase("null")) {
							form.setCifapprovedby(dta.get(7));
						}
						if (dta.get(8) != null && !dta.get(8).equals("") && !dta.get(8).equalsIgnoreCase("null")) {
							params.put("ciftype", dta.get(8));
							Tbcodetable cifType = (Tbcodetable) dbService.executeUniqueHQLQuery(
									"FROM Tbcodetable a WHERE a.id.codename ='CIFTYPE' AND a.id.codevalue =:ciftype",
									params);
							if (cifType != null) {
								form.setCiftype(cifType.getDesc1());
							}
						}
						if(customertype.equals("1")) {
							if (dta.get(9) != null && !dta.get(9).equals("") && !dta.get(9).equalsIgnoreCase("null")) {
								params.put("status", dta.get(9));
								Tbworkflowprocess cifStatus = (Tbworkflowprocess) dbServiceCoop.executeUniqueHQLQuery(
										"FROM Tbworkflowprocess where workflowid ='1' AND sequenceno =:status", params);
								form.setCifstatus(cifStatus.getProcessname());
//								if (cifStatus != null) {
//									String isdone = "";
//									if (dta.get(16) != null && !dta.get(16).equals("")
//											&& !dta.get(16).equalsIgnoreCase("null")) {
//										if (dta.get(16).toString().equals("false")) {
//											isdone = "On-going";
//										} else {
//											isdone = "For Review";
//										}
//									}
//									if (cifStatus.getId().getProcessid() == 3) {
//										form.setCifstatus(cifStatus.getProcessname());
//									} else {
//										form.setCifstatus(cifStatus.getProcessname());
//									}
//								}
							}
						}
						
						if(customertype.equals("2")) {
							if (dta.get(9) != null && !dta.get(9).equals("") && !dta.get(9).equalsIgnoreCase("null")) {
								params.put("status", dta.get(9));
								Tbworkflowprocess cifStatus = (Tbworkflowprocess) dbServiceCoop.executeUniqueHQLQuery(
										"FROM Tbworkflowprocess  where workflowid ='2' AND sequenceno =:status", params);
								form.setCifstatus(cifStatus.getProcessname());
//								if (cifStatus != null) {
//									String isdone = "";
//									if (dta.get(16) != null && !dta.get(16).equals("")
//											&& !dta.get(16).equalsIgnoreCase("null")) {
//										if (dta.get(16).toString().equals("false")) {
//											isdone = "On-going";
//										} else {
//											isdone = "For Review";
//										}
//									}
//									if (cifStatus.getId().getProcessid() == 3) {
//										form.setCifstatus(cifStatus.getProcessname());
//									} else {
//										form.setCifstatus(cifStatus.getProcessname());
//									}
//								}
							}
						}

						if (dta.get(10) != null && !dta.get(9).equals("") && !dta.get(10).equalsIgnoreCase("null")) {
							params.put("borFunderType", dta.get(10));
							Tbcodetable borFunderType = (Tbcodetable) dbService.executeUniqueHQLQuery(
									"FROM Tbcodetable a WHERE a.id.codename ='BORROWERFUNDERTYPE' AND a.id.codevalue =:borFunderType",
									params);
							if (borFunderType != null) {
								form.setBorrowerfundertype(borFunderType.getDesc1());
							}
						}
						if (dta.get(11) != null && !dta.get(11).equals("") && !dta.get(11).equalsIgnoreCase("null")) {
							form.setDateofincorporation(formatter1.parse(dta.get(11)));
						}
						if (dta.get(12) != null && !dta.get(12).equals("") && !dta.get(12).equalsIgnoreCase("null")) {
							form.setCustomertype(dta.get(12));
						}
						if (dta.get(13) != null && !dta.get(13).equals("") && !dta.get(13).equalsIgnoreCase("null")) {
							form.setFulladdress1(dta.get(13));
						}
						if (dta.get(14) != null && !dta.get(14).equals("") && !dta.get(14).equalsIgnoreCase("null")) {
							form.setFulladdress2(dta.get(14));
						}
						if (dta.get(15) != null && !dta.get(15).equals("") && !dta.get(15).equalsIgnoreCase("null")) {
							form.setAssignedto(UserUtil.getUserFullname(dta.get(15)));
						}
						if (dta.get(16) != null && !dta.get(16).equals("") && !dta.get(16).equalsIgnoreCase("null")) {
							form.setCifpurpose(dta.get(16));
						}
						main.add(form);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return main;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getCifTotalResult(String customertype, String cifnumber, String cifstatus, String fullname,
			String ciftype, Date birthdate, Date encodeddate, Date dateofincorporation, String lname, String fname,
			String mname, String fulladdress) {
		DBService dbservice = new DBServiceImplCIF();
		StringBuilder query = new StringBuilder();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dob = null;
		String ecd = null;
		String dtofinc = null;
		if (birthdate != null) {
			dob = formatter.format(birthdate);
		}
		if (encodeddate != null) {
			ecd = formatter.format(encodeddate);
		}
		if (dateofincorporation != null) {
			dtofinc = formatter.format(dateofincorporation);
		}
		boolean name = false;
		if (lname == null && fname == null && mname == null && fullname == null) {
			name = true;
		}
		int total = 0;
		try {
			if (cifnumber == null && name && cifstatus == null && encodeddate == null && ciftype == null
					&& birthdate == null && dateofincorporation == null && fulladdress == null) {
			} else {
				if (customertype.equals("2")) {
					// INDIVIDUAL
					query.append(
							"SELECT a.cifno, a.fullname, a.dateofbirth, a.encodeddate, a.encodedby, a.cifapproveddate, a.cifapprovedby,");
					query.append(
							"a.ciftype, a.cifstatus, a.borrowerfundertype, a.dateofincorporation, a.customertype, a.fulladdress1, a.fulladdress2,");
					query.append(
							"a.assignedto, a.cifpurpose FROM Tbcifmain a, Tbcifindividual b WHERE a.cifno=b.cifno AND a.cifstatus != '6'  AND ");
					if (cifnumber != null) {
						query.append("a.cifno like '%" + cifnumber + "%' AND ");
					}
					if (cifstatus != null) {
						query.append("a.cifstatus = '" + cifstatus + "' AND ");
					}
					if (encodeddate != null) {
						query.append("(a.encodeddate BETWEEN '" + ecd + " 00:00:00' AND '" + ecd + " 23:59:00') AND ");
					}
					if (ciftype != null) {
						query.append("a.ciftype = '" + ciftype + "' AND ");
					}
					if (fulladdress != null) {
						query.append("(a.fulladdress1 LIKE '%" + fulladdress + "%' OR a.fulladdress2 LIKE '%"
								+ fulladdress + "%') AND ");
					}
					if (birthdate != null) {
						query.append("(a.dateofbirth BETWEEN '" + dob + " 00:00:00' AND '" + dob + " 23:59:00') AND ");
					}
					if (lname != null) {
						query.append("(b.lastname like '%" + lname + "%' OR b.lastname like '%" + mname
								+ "%' OR b.middlename like " + "'%" + lname + "%') AND ");
					}
					if (fname != null) {
						query.append("b.firstname like '%" + fname + "%' AND ");
					}
					if (mname != null) {
						query.append("(b.middlename like '%" + mname + "%' OR b.lastname like '%" + mname + "%') AND ");
					}
					query.append("a.customertype = '" + customertype + "' AND ");
					query.append("-----");
				} else if (customertype.equals("1")) {
					// CORPORATE
					query.append(
							"SELECT cifno, fullname, dateofbirth, encodeddate, encodedby, cifapproveddate, cifapprovedby, ciftype, cifstatus, borrowerfundertype, dateofincorporation, customertype, fulladdress1, fulladdress2, assignedto, cifpurpose FROM Tbcifmain WHERE a.cifstatus != '5' AND ");
					if (cifnumber != null) {
						query.append("cifno like '%" + cifnumber + "%' AND ");
					}
					if (cifstatus != null) {
						query.append("cifstatus = '" + cifstatus + "' AND ");
					}
					if (encodeddate != null) {
						query.append("(encodeddate BETWEEN '" + ecd + " 00:00:00' AND '" + ecd + " 23:59:00') AND ");
					}
					if (ciftype != null) {
						query.append("ciftype = '" + ciftype + "' AND ");
					}
					if (fulladdress != null) {
						query.append("(fulladdress1 LIKE '%" + fulladdress + "%' OR fulladdress2 LIKE '%" + fulladdress
								+ "%') AND ");
					}
					if (dateofincorporation != null) {
						query.append("(dateofincorporation BETWEEN '" + dtofinc + " 00:00:00' AND '" + dtofinc
								+ " 23:59:00') AND ");
					}
					if (fullname != null) {
						query.append("fullname like '%" + fullname + "%' AND ");
					}
					query.append("customertype = '" + customertype + "' AND ");
					query.append("-----");
				}
				String indivcorpquery = query.substring(0, query.length() - 10);
				List<Object> obj = (List<Object>) dbservice.executeListSQLQuery(indivcorpquery, null);
				if (obj != null) {
					total = obj.size();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	// MAR
	@SuppressWarnings("unchecked")
	@Override
	public List<DedupeCIFForm> dedupeCIF(String cifno, String lname, String fname, String mname, String nameCorpSole,
			Integer page, Integer maxresult, String customertype) {
		List<DedupeCIFForm> list = new ArrayList<DedupeCIFForm>();
		DBService dbServiceCIF = new DBServiceImplCIF();
		params.put("cifno", cifno == null ? "%" : "%" + cifno + "%");
		params.put("lname", lname == null ? "%" : "%" + lname + "%");
		params.put("fname", fname == null ? "%" : "%" + fname + "%");
		params.put("mname", mname == null ? "%" : "%" + mname + "%");
		params.put("nameCorpSole", nameCorpSole == null ? "%" : "%" + nameCorpSole + "%");
		params.put("page", page);
		params.put("maxresult", maxresult);
		params.put("customertype", customertype);
		/*
		 * System.out.println("<><><><><><><><><><><><> cifno : " + cifno);
		 * System.out.println("<><><><><><><><><><><><> lname : " + lname);
		 * System.out.println("<><><><><><><><><><><><> fname : " + fname);
		 * System.out.println("<><><><><><><><><><><><> nameCorpSole : " +
		 * nameCorpSole); System.out.println("<><><><><><><><><><><><> page : " + page);
		 * System.out.println("<><><><><><><><><><><><> maxresult : " + maxresult);
		 * System.out.println("<><><><><><><><><><><><> customertype : " +
		 * customertype);
		 */
		try {
			String q = "EXEC sp_DedupeCIF @page=" + page + ", @maxresult=" + maxresult + ", @cifno=" + cifno
					+ ", @lname=:lname, @fname=:fname,@mname=:mname, @businessname=:nameCorpSole, @ispagingon='true', @customertype = "
					+ customertype + ", @inquirytype='CIF' ";
			// String q = "EXEC sp_DedupeCIF @page=:page, @maxresult=:maxresult,
			// @cifno=:cifno, @lname=:lname, @fname=:fname, @nameCorpSole=:nameCorpSole,
			// @ispagingon='true', @customertype=:customertype";
			list = (List<DedupeCIFForm>) dbServiceCIF.execSQLQueryTransformer(q, params, DedupeCIFForm.class, 1);
			// System.out.println("<><><><><><><><><><><><> q : " + q);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int dedupeCIFCount(String cifno, String lname, String fname, String mname, String nameCorpSole,
			String customertype) {
		Integer count = 0;
		DBService dbServiceCIF = new DBServiceImplCIF();
		params.put("cifno", cifno == null ? "%" : "%" + cifno + "%");
		params.put("lname", lname == null ? "%" : "%" + lname + "%");
		params.put("fname", fname == null ? "%" : "%" + fname + "%");
		params.put("mname", mname == null ? "%" : "%" + mname + "%");
		params.put("nameCorpSole", nameCorpSole == null ? "%" : "%" + nameCorpSole + "%");
		params.put("customertype", customertype);
		/*
		 * System.out.println("------------- cifno : " + cifno);
		 * System.out.println("------------- lname : " + lname);
		 * System.out.println("------------- fname : " + fname);
		 * System.out.println("------------- nameCorpSole : " + nameCorpSole);
		 * System.out.println("------------- customertype : " + customertype);
		 */
		try {
			String q = "EXEC sp_DedupeCIF @page=NULL, @maxresult=NULL, @cifno=" + cifno
					+ ", @lname=:lname, @fname=:fname,@mname=:mname, @businessname=:nameCorpSole, @ispagingon='false', @customertype = "
					+ customertype + ", @inquirytype='CIF' ";
			// String q = "EXEC sp_DedupeCIF @page=NULL, @maxresult=NULL, @cifno=:cifno,
			// @lname=:lname, @fname=:fname, @nameCorpSole=:nameCorpSole,
			// @ispagingon='false', @customertype=:customertype";
			count = (Integer) dbServiceCIF.execSQLQueryTransformer(q, params, null, 0);
			// System.out.println("------------- q : " + q);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	// MAR
	@SuppressWarnings("unchecked")
	@Override
	public List<CIFInquiryForm> searchCIFRB(String customertype, String cifnumber, String cifstatus, String fullname,
			String ciftype, Date birthdate, Date encodeddate, Date dateofincorporation, Integer page, Integer maxResult,
			String lname, String fname, String mname, String fulladdress) {
		List<CIFInquiryForm> main = new ArrayList<CIFInquiryForm>();
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		StringBuilder query = new StringBuilder();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dob = null;
		String ecd = null;
		String dtofinc = null;
		if (birthdate != null) {
			dob = formatter.format(birthdate);
		}
		if (encodeddate != null) {
			ecd = formatter.format(encodeddate);
		}
		if (dateofincorporation != null) {
			dtofinc = formatter.format(dateofincorporation);
		}
		boolean name = false;
		if (lname == null && fname == null && mname == null && fullname == null) {
			name = true;
		}
		try {
			if (cifnumber == null && name && cifstatus == null && encodeddate == null && ciftype == null
					&& birthdate == null && dateofincorporation == null && fulladdress == null) {
			} else {
				if (customertype.equals("1")) {
					// INDIVIDUAL
					query.append(
							"SELECT a.cifno, a.fullname, a.dateofbirth, a.encodeddate, a.encodedby, a.cifapproveddate, a.cifapprovedby,");
					query.append(
							"a.ciftype, a.cifstatus, a.borrowerfundertype, a.dateofincorporation, a.customertype, a.fulladdress1, a.fulladdress2,");
					query.append(
							"a.assignedto, a.cifpurpose FROM Tbcifmain a, Tbcifindividual b WHERE a.cifno=b.cifno AND ");
					if (cifnumber != null) {
						query.append("a.cifno like '%" + cifnumber + "%' AND ");
					}
					if (cifstatus != null) {
						query.append("a.cifstatus = '" + cifstatus + "' AND ");
					}
					if (encodeddate != null) {
						query.append("(a.encodeddate BETWEEN '" + ecd + " 00:00:00' AND '" + ecd + " 23:59:00') AND ");
					}
					if (ciftype != null) {
						query.append("a.ciftype = '" + ciftype + "' AND ");
					}
					if (fulladdress != null) {
						query.append("(a.fulladdress1 LIKE '%" + fulladdress + "%' OR a.fulladdress2 LIKE '%"
								+ fulladdress + "%') AND ");
					}
					if (birthdate != null) {
						query.append("(a.dateofbirth BETWEEN '" + dob + " 00:00:00' AND '" + dob + " 23:59:00') AND ");
					}
					if (lname != null) {
						query.append("(b.lastname like '%" + lname + "%' OR b.lastname like '%" + mname
								+ "%' OR b.middlename like " + "'%" + lname + "%') AND ");
					}
					if (fname != null) {
						query.append("b.firstname like '%" + fname + "%' AND ");
					}
					if (mname != null) {
						query.append("(b.middlename like '%" + mname + "%' OR b.lastname like '%" + mname + "%') AND ");
					}
					query.append("a.customertype = '" + customertype + "' ORDER BY a.fullname ASC AND ");
					query.append("-----");
				} else if (customertype.equals("2") || customertype.equals("3")) {
					// CORPORATE
					query.append(
							"SELECT cifno, fullname, dateofbirth, encodeddate, encodedby, cifapproveddate, cifapprovedby, ciftype, cifstatus, borrowerfundertype, dateofincorporation, customertype, fulladdress1, fulladdress2, assignedto, cifpurpose FROM Tbcifmain WHERE ");
					if (cifnumber != null) {
						query.append("cifno like '%" + cifnumber + "%' AND ");
					}
					if (cifstatus != null) {
						query.append("cifstatus = '" + cifstatus + "' AND ");
					}
					if (encodeddate != null) {
						query.append("(encodeddate BETWEEN '" + ecd + " 00:00:00' AND '" + ecd + " 23:59:00') AND ");
					}
					if (ciftype != null) {
						query.append("ciftype = '" + ciftype + "' AND ");
					}
					if (fulladdress != null) {
						query.append("(fulladdress1 LIKE '%" + fulladdress + "%' OR fulladdress2 LIKE '%" + fulladdress
								+ "%') AND ");
					}
					if (dateofincorporation != null) {
						query.append("(dateofincorporation BETWEEN '" + dtofinc + " 00:00:00' AND '" + dtofinc
								+ " 23:59:00') AND ");
					}
					if (fullname != null) {
						query.append("fullname like '%" + fullname + "%' AND ");
					}
					query.append("customertype = '" + customertype + "' ORDER BY fullname ASC AND ");
					query.append("-----");
				}
				String indivcorpquery = query.substring(0, query.length() - 10);
				System.out.println("<<<<<indivcorpquery>>>>> " + indivcorpquery);
				List<Object> obj = (List<Object>) dbService.executeListSQLQueryWithFirstAndMaxResults(indivcorpquery,
						null, page, maxResult);
				if (obj != null) {
					for (Object obj3 : obj) {
						Object[] obj2 = (Object[]) obj3;
						ArrayList<String> dta = new ArrayList<String>();
						for (Object obj1 : obj2) {
							dta.add(String.valueOf(obj1));
						}
						CIFInquiryForm form = new CIFInquiryForm();
						SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						if (dta.get(0) != null && !dta.get(0).equals("") && !dta.get(0).equalsIgnoreCase("null")) {
							form.setCifno(dta.get(0));
						}
						if (dta.get(1) != null && !dta.get(1).equals("") && !dta.get(1).equalsIgnoreCase("null")) {
							form.setFullname(dta.get(1));
						}
						if (dta.get(2) != null && !dta.get(2).equals("") && !dta.get(2).equalsIgnoreCase("null")) {
							form.setBirthdate(formatter1.parse(dta.get(2)));
						}
						if (dta.get(3) != null && !dta.get(3).equals("") && !dta.get(3).equalsIgnoreCase("null")) {
							form.setEncodeddate(formatter1.parse(dta.get(3)));
						}
						if (dta.get(4) != null && !dta.get(4).equals("") && !dta.get(4).equalsIgnoreCase("null")) {
							form.setEncodedby(UserUtil.getUserFullname(dta.get(4)));
						}
						if (dta.get(5) != null && !dta.get(5).equals("") && !dta.get(5).equalsIgnoreCase("null")) {
							form.setCifapproveddate(formatter1.parse(dta.get(5)));
						}
						if (dta.get(6) != null && !dta.get(6).equals("") && !dta.get(6).equalsIgnoreCase("null")) {
							form.setCifapprovedby(dta.get(6));
						}
						if (dta.get(7) != null && !dta.get(7).equals("") && !dta.get(7).equalsIgnoreCase("null")) {
							params.put("ciftype", dta.get(7));
							Tbcodetable cifType = (Tbcodetable) dbService.executeUniqueHQLQuery(
									"FROM Tbcodetable a WHERE a.id.codename ='CIFTYPE' AND a.id.codevalue =:ciftype",
									params);
							if (cifType != null) {
								form.setCiftype(cifType.getDesc1());
							}
						}
						if (dta.get(8) != null && !dta.get(8).equals("") && !dta.get(8).equalsIgnoreCase("null")) {
							params.put("status", dta.get(8));
							Tbcodetable cifStatus = (Tbcodetable) dbService.executeUniqueHQLQuery(
									"FROM Tbcodetable a WHERE a.id.codename ='CIFSTATUS' AND a.id.codevalue =:status",
									params);
							if (cifStatus != null) {
								form.setCifstatus(cifStatus.getDesc1());
							}
						}
						if (dta.get(9) != null && !dta.get(9).equals("") && !dta.get(9).equalsIgnoreCase("null")) {
							params.put("borFunderType", dta.get(9));
							Tbcodetable borFunderType = (Tbcodetable) dbService.executeUniqueHQLQuery(
									"FROM Tbcodetable a WHERE a.id.codename ='BORROWERFUNDERTYPE' AND a.id.codevalue =:borFunderType",
									params);
							if (borFunderType != null) {
								form.setBorrowerfundertype(borFunderType.getDesc1());
							}
						}
						if (dta.get(10) != null && !dta.get(10).equals("") && !dta.get(10).equalsIgnoreCase("null")) {
							form.setDateofincorporation(formatter1.parse(dta.get(10)));
						}
						if (dta.get(11) != null && !dta.get(11).equals("") && !dta.get(11).equalsIgnoreCase("null")) {
							form.setCustomertype(dta.get(11));
						}
						if (dta.get(12) != null && !dta.get(12).equals("") && !dta.get(12).equalsIgnoreCase("null")) {
							form.setFulladdress1(dta.get(12));
						}
						if (dta.get(13) != null && !dta.get(13).equals("") && !dta.get(13).equalsIgnoreCase("null")) {
							form.setFulladdress2(dta.get(13));
						}
						if (dta.get(14) != null && !dta.get(14).equals("") && !dta.get(14).equalsIgnoreCase("null")) {
							form.setAssignedto(UserUtil.getUserFullname(dta.get(14)));
						}
						if (dta.get(15) != null && !dta.get(15).equals("") && !dta.get(15).equalsIgnoreCase("null")) {
							form.setCifpurpose(dta.get(15));
						}
						main.add(form);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return main;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InquiryForm> applicationInquiry2(String appno, String cifno, String loanproduct, String fname,
			String lname, String corporatename, Integer page, Integer maxresult, String customertype,
			String isExisting) {
		List<InquiryForm> list = new ArrayList<InquiryForm>();
		DBService dbServiceCOOP = new DBServiceImpl();
		try {
			params.put("appno", appno == null ? "%" : "%" + appno + "%");
			params.put("cifno", cifno == null ? "%" : "%" + cifno + "%");
			params.put("lname", lname == null ? "%" : "%" + lname + "%");
			params.put("fname", fname == null ? "%" : "%" + fname + "%");
			params.put("corpname", corporatename == null ? "%" : "%" + corporatename + "%");
			params.put("customertype", customertype);
			params.put("loanproduct", loanproduct);
			params.put("isExisting", isExisting);
			params.put("branchcode", UserUtil.getUserByUsername(secservice.getUserName()).getBranchcode());

			String q = "EXEC sp_ApplicationInquiry @page=" + page + ", @maxresult=" + maxresult + ", @appno=" + appno
					+ ", @cifno=" + cifno + ", @loanproduct = " + loanproduct + " , @lname=" + lname + " , @fname= "
					+ fname + " , @corpname= " + corporatename + " , @ispagingon='true', @customertype = "
					+ customertype + ", @branchcode =:branchcode ";
			list = (List<InquiryForm>) dbServiceCOOP.execSQLQueryTransformer(q, params, InquiryForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int applicationInquiry2Count(String appno, String cifno, String loanproduct, String fname, String lname,
			String corporatename, String customertype, String isExisting) {
		Integer count = 0;
		DBService dbServiceCOOP = new DBServiceImpl();
		try {
			params.put("appno", appno == null ? "%" : "%" + appno + "%");
			params.put("cifno", cifno == null ? "%" : "%" + cifno + "%");
			params.put("lname", lname == null ? "%" : "%" + lname + "%");
			params.put("fname", fname == null ? "%" : "%" + fname + "%");
			params.put("corpname", corporatename == null ? "%" : "%" + corporatename + "%");
			params.put("customertype", customertype);
			params.put("loanproduct", loanproduct);
			params.put("isExisting", isExisting);
			params.put("branchcode", UserUtil.getUserByUsername(secservice.getUserName()).getBranchcode());

			String q = "EXEC sp_ApplicationInquiry @page=NULL, @maxresult=NULL, @appno=" + appno + ", @cifno=" + cifno
					+ ", @loanproduct = " + loanproduct + " , @lname=" + lname + " , @fname= " + fname
					+ " , @corpname= " + corporatename + " , @ispagingon='false', @customertype = " + customertype
					+ ", @branchcode =:branchcode ";
			count = (Integer) dbServiceCOOP.execSQLQueryTransformer(q, params, null, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyListPerStagesForm> companyListPerStages(String branch, String search, String applicationStatus, String daysCount) {
		List<CompanyListPerStagesForm> list = new ArrayList<CompanyListPerStagesForm>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("branch", branch);
			params.put("search", search);
			params.put("applicationStatus", applicationStatus);
			params.put("daysCount", daysCount);
			list = (List<CompanyListPerStagesForm>) dbServiceCIF.execStoredProc(
					"EXEC sp_Dashboard_Company_Application_Stages :branch, :search, :applicationStatus, :daysCount", params,
					CompanyListPerStagesForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MembershipListPerStagesForm> membershipListPerStages(String branch, String search,
			String applicationStatus, String daysCount) {
		List<MembershipListPerStagesForm> list = new ArrayList<MembershipListPerStagesForm>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("branch", branch);
			params.put("search", search);
			params.put("applicationStatus", applicationStatus);
			params.put("daysCount", daysCount);
			list = (List<MembershipListPerStagesForm>) dbServiceCIF.execStoredProc(
					"EXEC sp_Dashboard_Membership_Application_Stages :branch, :search, :applicationStatus, :daysCount", params,
					MembershipListPerStagesForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	

}
