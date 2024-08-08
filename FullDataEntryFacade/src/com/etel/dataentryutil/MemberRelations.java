package com.etel.dataentryutil;

import java.util.Date;
import java.util.Map;

import org.cloudfoundry.org.codehaus.jackson.map.ObjectMapper;

import com.coopdb.data.Tbcodetable;
import com.coopdb.data.Tbmember;
import com.coopdb.data.Tbmemberrelationship;
import com.coopdb.data.Tbmembershipapp;
import com.coopdb.data.Tbuser;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.dataentry.FullDataEntryService;
import com.etel.dataentry.FullDataEntryServiceImpl;
import com.etel.utils.HQLUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class MemberRelations {
	private DBService dbService = new DBServiceImpl();
	private Map<String, Object> param = HQLUtil.getMap();
	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private FullDataEntryService dtntry = new FullDataEntryServiceImpl();

	public String createAssociateRegular(String appid, Tbmembershipapp relappform, String relatedappid) {

		try {
			param.put("user", secservice.getUserName());
			Tbuser user = (Tbuser) dbService.executeUniqueHQLQuery("FROM Tbuser WHERE username=:user", param);

			param.put("regid", appid);
			param.put("relatedappid", relatedappid);

			/* the regular applicant */
			Tbmembershipapp reg = dtntry.getMembershipapp(appid, null);

			/* create membership application for the associate/relative */
			Tbmembershipapp asoc = new Tbmembershipapp();

			/* from parameter form */
			asoc.setAge(relappform.getAge());
			asoc.setDateofbirth(relappform.getDateofbirth());
			asoc.setGender(relappform.getGender());
			asoc.setAreacodephone(relappform.getAreacodephone());
			asoc.setPhoneno(relappform.getPhoneno());
			asoc.setSuffix(relappform.getSuffix());

			/* name */
			asoc.setLastname(relappform.getLastname().toUpperCase());
			asoc.setFirstname(relappform.getFirstname().toUpperCase());
			asoc.setMiddlename(relappform.getMiddlename().toUpperCase());

			/* address */
			asoc.setCountry1(relappform.getCountry1());
			asoc.setStateprovince1(relappform.getStateprovince1());
			asoc.setRegion1(relappform.getRegion1());
			asoc.setCity1(relappform.getCity1());
			asoc.setBarangay1(relappform.getBarangay1());
			asoc.setSubdivision1(relappform.getSubdivision1());
			asoc.setStreetnoname1(relappform.getStreetnoname1());
			asoc.setPostalcode1(relappform.getPostalcode1());

			asoc.setMembershipappid(relatedappid);
			asoc.setMembershipappstatus("1");
			asoc.setMembershipstatus("7");
			asoc.setApplicanttype("1");
			asoc.setMembershipappid(relatedappid);
			asoc.setApplicationdate(new Date());
			asoc.setEncodedby(secservice.getUserName());
			asoc.setCoopcode(user.getCoopcode());
			asoc.setMembershipclass("2");// default assoc-regular
			asoc.setOriginatingbranch(user.getBranchcode());
			asoc.setBranch(reg.getBranch());

			if (dbService.save(asoc)) {

				/* get the (regular - assoc relationship) */
				Tbmemberrelationship rel = (Tbmemberrelationship) dbService.executeUniqueHQLQuery(
						"FROM Tbmemberrelationship WHERE relatedappid=:relatedappid AND applicantid=:regid", param);

				if (rel != null) {
					/* assoc's relationship to the regular applicant(employee) */
					String relationship = rel.getRelationshipcode();

					/* create counter relationship, (assoc - regular relationship) */
					Tbmemberrelationship nr = new Tbmemberrelationship();

					/* relationship */
					nr.setRelatedappid(reg.getMembershipappid());
					nr.setApplicantid(asoc.getMembershipappid());
					nr.setDegreecode(rel.getDegreecode());

					/* personal details */
					nr.setFirstname(reg.getFirstname());
					nr.setMiddlename(reg.getMiddlename());
					nr.setLastname(reg.getLastname());
					nr.setMainmembername(reg.getLastname() + " " + reg.getFirstname() + "" + reg.getMiddlename());
					nr.setDateofbirth(reg.getDateofbirth());
					nr.setGender(reg.getGender());
					nr.setAge(reg.getAge());
					nr.setSuffix(reg.getSuffix());
					nr.setContactno(reg.getAreacodephone() + reg.getPhoneno());

					/* address details */
					nr.setCountry(reg.getCountry1());
					nr.setRegion(reg.getRegion1());
					nr.setStateprovince(reg.getStateprovince1());
					nr.setCity(reg.getCity1());
					nr.setPostalcode(reg.getPostalcode1());
					nr.setBarangay(reg.getBarangay1());
					nr.setStreetnoname(reg.getStreetnoname1());
					nr.setSubdivision(reg.getSubdivision1());

					/* security/audit details */
					nr.setAddedby(user.getUsername());
					nr.setDateadded(new Date());

					/* Others */
					nr.setIsbeneficiary(false);
					nr.setIsdependent(false);

					/* assoc's relationship to the regular employee(applicant) */

					/* note that values being coded here are defaults from db parameters. */

					/* TBCODETABLE(codename=RELATIONSHIP) */

					/*
					 * read the following precious comments to analyze the process and connections
					 */

					/* if assoc is regular's : */
					if (relationship.equals("0") || relationship.equals("1")) {
						/* Father or Mother */

						if (reg.getGender().equals("M")) {
							/* then regular is assoc's : Son */
							nr.setRelationshipcode("2");
						}
						if (reg.getGender().equals("F")) {
							/* then regular is assoc's : Daughter */
							nr.setRelationshipcode("3");
						}
					}

					if (relationship.equals("2") || relationship.equals("3")) {
						/* Son or Daughter */

						if (reg.getGender().equals("M")) {
							/* then regular is assoc's : Father */
							nr.setRelationshipcode("0");
						}
						if (reg.getGender().equals("F")) {
							/* then regular is assoc's : Mother */
							nr.setRelationshipcode("1");
						}
					}

					if (relationship.equals("4") || relationship.equals("5")) {
						/* Husband or Wife */

						if (reg.getGender().equals("M")) {
							/* then regular is assoc's : Husband */
							nr.setRelationshipcode("4");
						}
						if (reg.getGender().equals("F")) {
							/* then regular is assoc's : Wife */
							nr.setRelationshipcode("5");
						}
					}

					if (relationship.equals("6") || relationship.equals("7")) {
						/* Brother or Sister */

						if (reg.getGender().equals("M")) {
							/* then regular is assoc's : Brother */
							nr.setRelationshipcode("6");
						}
						if (reg.getGender().equals("F")) {
							/* then regular is assoc's : Sister */
							nr.setRelationshipcode("7");
						}
					}

					if (relationship.equals("8") || relationship.equals("9")) {
						/* Grandfather or Grandmother */

						if (reg.getGender().equals("M")) {
							/* then regular is assoc's : Grandson */
							nr.setRelationshipcode("10");
						}
						if (reg.getGender().equals("F")) {
							/* then regular is assoc's : Granddaughter */
							nr.setRelationshipcode("11");
						}
					}

					if (relationship.equals("10") || relationship.equals("11")) {
						/* Grandson */

						if (reg.getGender().equals("M")) {
							/* then regular is assoc's : Grandfather */
							nr.setRelationshipcode("8");
						}
						if (reg.getGender().equals("F")) {
							/* then regular is assoc's : Grandmother */
							nr.setRelationshipcode("9");
						}
					}

					if (relationship.equals("12") || relationship.equals("13")) {
						/* Uncle or Aunt */

						if (reg.getGender().equals("M")) {
							/* then regular is assoc's : Nephew */
							nr.setRelationshipcode("14");
						}
						if (reg.getGender().equals("F")) {
							/* then regular is assoc's : Niece */
							nr.setRelationshipcode("15");
						}
					}

					if (relationship.equals("14") || relationship.equals("15")) {
						/* Nephew */

						if (reg.getGender().equals("M")) {
							/* then regular is assoc's : Uncle */
							nr.setRelationshipcode("12");
						}
						if (reg.getGender().equals("F")) {
							/* then regular is assoc's : Aunt */
							nr.setRelationshipcode("13");
						}
					}

					if (relationship.equals("16")) {
						/* Cousin */

						/* then regular is assoc's : Cousin */
						nr.setRelationshipcode("16");
					}

					if (relationship.equals("17")) {
						/* Others */

						/* then regular is assoc's : Others */
						nr.setRelationshipcode("17");
					}

					nr.setIsautogenerated(true);
					param.put("codevalue", nr.getRelationshipcode());
					nr.setRelationshipdesc((String) dbService.executeUniqueSQLQuery(
							"SELECT desc1 FROM TBCODETABLE WHERE codename='RELATIONSHIP' AND codevalue=:codevalue",
							param));
					if (dbService.save(nr)) {
						return asoc.getMembershipappid();
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
			// TODO: handle exception
		}
		return "failed";
	}

	/* values returned were only relationshipcode and relationshipdesc */
	public Tbmemberrelationship getCounterPartRelationship(String mainrelationship, String relativegender) {
		Tbmemberrelationship nr = new Tbmemberrelationship();
		/*
		 * if relative is main's : (Father) then main is relative's : Son(Male) ||
		 * Daughter(Female)
		 */

		/* if relative is main's : */

		if (mainrelationship.equals("0") || mainrelationship.equals("1")) {
			/* Father or Mother */

			if (relativegender.equals("M")) {
				/* then main is relative's : Son */
				nr.setRelationshipcode("2");
			}
			if (relativegender.equals("F")) {
				/* then main is relative's : Daughter */
				nr.setRelationshipcode("3");
			}
		}

		if (mainrelationship.equals("2") || mainrelationship.equals("3")) {
			/* Son or Daughter */

			if (relativegender.equals("M")) {
				/* then main is relative's : Father */
				nr.setRelationshipcode("0");
			}
			if (relativegender.equals("F")) {
				/* then main is relative's : Mother */
				nr.setRelationshipcode("1");
			}
		}

		if (mainrelationship.equals("4") || mainrelationship.equals("5")) {
			/* Husband or Wife */

			if (relativegender.equals("M")) {
				/* then main is relative's : Husband */
				nr.setRelationshipcode("4");
			}
			if (relativegender.equals("F")) {
				/* then main is relative's : Wife */
				nr.setRelationshipcode("5");
			}
		}

		if (mainrelationship.equals("6") || mainrelationship.equals("7")) {
			/* Brother or Sister */

			if (relativegender.equals("M")) {
				/* then main is relative's : Brother */
				nr.setRelationshipcode("6");
			}
			if (relativegender.equals("F")) {
				/* then main is relative's : Sister */
				nr.setRelationshipcode("7");
			}
		}

		if (mainrelationship.equals("8") || mainrelationship.equals("9")) {
			/* Grandfather or Grandmother */

			if (relativegender.equals("M")) {
				/* then main is relative's : Grandson */
				nr.setRelationshipcode("10");
			}
			if (relativegender.equals("F")) {
				/* then main is relative's : Granddaughter */
				nr.setRelationshipcode("11");
			}
		}

		if (mainrelationship.equals("10") || mainrelationship.equals("11")) {
			/* Grandson */

			if (relativegender.equals("M")) {
				/* then main is relative's : Grandfather */
				nr.setRelationshipcode("8");
			}
			if (relativegender.equals("F")) {
				/* then main is relative's : Grandmother */
				nr.setRelationshipcode("9");
			}
		}

		if (mainrelationship.equals("12") || mainrelationship.equals("13")) {
			/* Uncle or Aunt */

			if (relativegender.equals("M")) {
				/* then main is relative's : Nephew */
				nr.setRelationshipcode("14");
			}
			if (relativegender.equals("F")) {
				/* then main is relative's : Niece */
				nr.setRelationshipcode("15");
			}
		}

		if (mainrelationship.equals("14") || mainrelationship.equals("15")) {
			/* Nephew */

			if (relativegender.equals("M")) {
				/* then main is relative's : Uncle */
				nr.setRelationshipcode("12");
			}
			if (relativegender.equals("F")) {
				/* then main is relative's : Aunt */
				nr.setRelationshipcode("13");
			}
		}

		if (mainrelationship.equals("16")) {
			/* Cousin */

			/* then main is relative's : Cousin */
			nr.setRelationshipcode("16");
		}

		if (mainrelationship.equals("17")) {
			/* Others */

			/* then main is relative's : Others */
			nr.setRelationshipcode("17");
		}
		try {
			param.put("codevalue", nr.getRelationshipcode());
			Tbcodetable c = (Tbcodetable)dbService.executeUniqueHQLQuery("FROM Tbcodetable WHERE codename='RELATIONSHIP' AND codevalue=:codevalue", param);
			if (c != null) {
				nr.setRelationshipdesc(c.getDesc1());
				nr.setDegreecode(c.getDesc2() != null ? c.getDesc2() : null);
			}
//			nr.setRelationshipdesc((String) dbService.executeUniqueSQLQuery(
//					"SELECT desc1 FROM TBCODETABLE WHERE codename='RELATIONSHIP' AND codevalue=:codevalue", param));
		} catch (Exception e) {
			e.printStackTrace();
			return nr;
			// TODO: handle exception
		}
		return nr;
	}

	public String createAssocRegularRelationship(Tbmember regular, Tbmembershipapp assocregular, String relationship) {
		Tbmemberrelationship relative = new Tbmemberrelationship();
		Tbmemberrelationship main = new Tbmemberrelationship();
		try {
			ObjectMapper m = new ObjectMapper();
			System.out.println(m.writeValueAsString(regular));
			System.out.println(m.writeValueAsString(assocregular));
			System.out.println(relationship);
			
			main.setApplicantid(assocregular.getMembershipappid());
			main.setRelatedappid(regular.getMembershipappid());
			main.setRelatedmemberid(regular.getMembershipid());
			main.setMainmembername(assocregular.getLastname() + " " + assocregular.getFirstname() + " "
					+ assocregular.getMiddlename());
			main.setDateofbirth(regular.getDateofbirth());
			main.setFirstname(regular.getFirstname());
			main.setMiddlename(regular.getMiddlename());
			main.setLastname(regular.getLastname());
			main.setSuffix(regular.getSuffix());

			main.setBarangay(regular.getBarangay1());
			main.setStreetnoname(regular.getStreetnoname1());
			main.setSubdivision(regular.getSubdivision1());
			main.setStateprovince(regular.getStateprovince1());
			main.setPostalcode(regular.getPostalcode1());
			main.setCity(regular.getCity1());
			main.setRegion(regular.getRegion1());
			main.setCountry(regular.getCountry1());
			main.setIsautogenerated(true);

			relative.setApplicantid(regular.getMembershipappid());
			relative.setRelatedappid(assocregular.getMembershipappid());
			relative.setMainmemberid(regular.getMembershipid());
			relative.setRelatedmemberid(null);
			relative.setMainmembername(
					regular.getLastname() + " " + regular.getFirstname() + " " + regular.getMiddlename());

			relative.setDateofbirth(assocregular.getDateofbirth());
			relative.setFirstname(assocregular.getFirstname());
			relative.setMiddlename(assocregular.getMiddlename());
			relative.setLastname(assocregular.getLastname());

			if (relationship != null) {
				Tbmemberrelationship r = getCounterPartRelationship(relationship, regular.getGender());
				main.setRelationshipcode(r.getRelationshipcode());
				main.setRelationshipdesc(r.getRelationshipdesc());
				main.setDegreecode(r.getDegreecode());

				relative.setRelationshipcode(relationship);
				param.put("codevalue", relationship);
				Tbcodetable c = (Tbcodetable)dbService.executeUniqueHQLQuery("FROM Tbcodetable WHERE codename='RELATIONSHIP' AND codevalue=:codevalue", param);
				if (c != null) {
					relative.setRelationshipdesc(c.getDesc1());
					relative.setDegreecode(c.getDesc2());
				}
//				relative.setRelationshipdesc((String) dbService.executeUniqueSQLQuery(
//						"SELECT desc1 FROM TBCODETABLE WHERE codename='RELATIONSHIP' AND codevalue=:codevalue", param));
			}
			if (dbService.save(main) && dbService.save(relative)) {
				return "success";
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return "failed";
	}

}
