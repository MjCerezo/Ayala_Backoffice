package com.ete.collateral;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.cloudfoundry.org.codehaus.jackson.map.DeserializationConfig;
import org.cloudfoundry.org.codehaus.jackson.map.ObjectMapper;

import com.coopdb.data.Tbappraisalauto;
import com.coopdb.data.Tbappraisaldeposits;
import com.coopdb.data.Tbappraisallivestock;
import com.coopdb.data.Tbappraisalmachine;
import com.coopdb.data.Tbappraisalrel;
import com.coopdb.data.Tbappraisalreportmain;
import com.coopdb.data.Tbcodetable;
import com.coopdb.data.Tbcolappraisalrequest;
import com.coopdb.data.Tbcollateralauto;
import com.coopdb.data.Tbcollateraldeposits;
import com.coopdb.data.Tbcollaterallivestock;
import com.coopdb.data.Tbcollateralmachineries;
import com.coopdb.data.Tbcollateralmain;
import com.coopdb.data.Tbcollateralrel;
import com.coopdb.data.Tbcollateralsecurities;
import com.coopdb.data.Tbloancollateral;
import com.coopdb.data.Tblstapp;
import com.coopdb.data.Tbsequence;
import com.etel.collateralforms.AppraisalAccessRights;
import com.etel.collateralforms.AppraisalForm;
import com.etel.collateralforms.CAAccessRightsForm;
import com.etel.collateralforms.CollateralTables;
import com.etel.collateralforms.DedupeCollateralForm;
import com.etel.collateralforms.DedupeCollateralFormGroup;
import com.etel.collateralforms.LoanApplicationUsageForm;
import com.etel.collateralforms.TbCollateralMainForm;
import com.etel.collateralforms.TbCollateralMainFormGroup;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.common.service.DBServiceImplLOS;
import com.etel.defaultusers.forms.DefaultUsers;
import com.etel.financial.form.CollateralLoanableForm;
import com.etel.forms.ReturnForm;
import com.etel.utils.ApplicationNoGenerator;
import com.etel.utils.AuditLog;
import com.etel.utils.AuditLogEvents;
import com.etel.utils.DateTimeUtil;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;
import com.loansdb.data.Tbcollateralgroupmain;
import com.loansdb.data.Tbcollateralpergroup;
import com.loansdb.data.Tbloancollateralgroup;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class CollateralServiceImpl implements CollateralService {

	private DBService dbServiceLOS = new DBServiceImpl();
	private Map<String, Object> params = HQLUtil.getMap();
	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	@SuppressWarnings("unused")
	private ObjectMapper mapper = new ObjectMapper();
	private String user = UserUtil.getUserByUsername(secservice.getUserName()).getFullname();
	private String username = secservice.getUserName();

	@Override
	public String saveOrupdateCollateral(String appno, String saveOrupdate, String collateraltype, String referenceno,
			String oldreferenceno, CollateralTables tb) {
		String flag = "failed";
		// String colid = ApplicationNoGenerator.generateCollateralID("COL");
		try {
			System.out.println(saveOrupdate);
			if (saveOrupdate != null && collateraltype != null && referenceno != null) {
				if (saveOrupdate.equalsIgnoreCase("ADD")) {
					// 1 - check if existing
					params.put("refno", referenceno.trim());
					Integer list = (Integer) dbServiceLOS.executeUniqueSQLQuery(
							"SELECT COUNT(*) FROM Tbcollateralmain WHERE referenceno=:refno", params);
					if (list != null && list > 0) {
						return flag = "existing";
					} else {
						// 2 - create collateral record
						Boolean isSavedSuccessfully = false;
						/** START OF ADD or SAVE **/
						if (collateraltype.equals("1")) {// Auto
							if (dbServiceLOS.save(tb.getAuto())) {
								isSavedSuccessfully = true;
								tb.getAuto().setEncodedby(secservice.getUserName());
								tb.getMain().setRegisteredowner(tb.getAuto().getRegisteredowner());
							}
						} else if (collateraltype.equals("2")) {// Real
							if (dbServiceLOS.save(tb.getRel())) {
								isSavedSuccessfully = true;
								tb.getRel().setEncodedby(secservice.getUserName());
								tb.getMain().setRegisteredowner(tb.getRel().getRegisteredowner());
							}
						} else if (collateraltype.equals("3")) {// Deposits
							tb.getDeposits().setEncodedby(secservice.getUserName());
							isSavedSuccessfully = dbServiceLOS.save(tb.getDeposits());
							// if(dbServiceLOS.save(tb.getDeposits())){
							// isSavedSuccessfully = true;
							// }
						} else if (collateraltype.equals("4")) {// Machineries
							tb.getMachineries().setEncodedby(secservice.getUserName());
							isSavedSuccessfully = dbServiceLOS.save(tb.getMachineries());
							// if(dbServiceLOS.save(tb.getMachineries())){
							// isSavedSuccessfully = true;
							// }
						} else if (collateraltype.equals("5")) {// Livestock
							tb.getLivestock().setReferenceno(ApplicationNoGenerator.generateLiveStockReferenceNo("LS"));
							tb.getLivestock().setEncodedby(secservice.getUserName());
							tb.getLivestock().setDateencoded(new Date());
							tb.getMain().setReferenceno(tb.getLivestock().getReferenceno());
							isSavedSuccessfully = dbServiceLOS.save(tb.getLivestock());
							/* COOP BASED | changed to livestock */
							// tb.getSecurities().setEncodedby(user);
							// isSavedSuccessfully =
							// dbServiceLOS.save(tb.getSecurities());
							// if(dbServiceLOS.save(tb.getSecurities())){
							// isSavedSuccessfully = true;
							// }
						}
						if (isSavedSuccessfully) {
							if (appno != null) {
								/**
								 * Creating Collateral Report inside Application
								 */
								tb.getMain().setCollateralstatus("1");
								flag = saveAndUseCollateral(appno, tb.getMain().getReferenceno(),
										tb.getMain().getCollateraltype());
								/* called another method */
							} else {
								/*
								 * Creating Collateral Report outside Application
								 */
								tb.getMain().setCollateralstatus("0");
							}
							tb.getMain().setEncodedby(secservice.getUserName());
							tb.getMain().setDateencoded(new Date());
							if (dbServiceLOS.save(tb.getMain())) {
								AuditLog.addAuditLog(
										AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("ADD NEW COLLATERAL",
												AuditLogEvents.LOAN_APPLICATION_ENCODING)),
										"User " + username + " Added New " + appno + "'s Collateral Type .", username,
										new Date(), AuditLogEvents.LOAN_APPLICATION_ENCODING);
								flag = "success";
							}
						}
					}
				} else if (saveOrupdate.equalsIgnoreCase("UPDATE")) {
					if (oldreferenceno != null) {
						if (!oldreferenceno.equals(referenceno)) {
							// Start of Update - WITH Changes at Reference
							// Number
							// 1 - check if new reference no is existing
							params.put("refno", referenceno);
							Integer list = (Integer) dbServiceLOS.executeUniqueSQLQuery(
									"SELECT COUNT(*) FROM Tbcollateralmain WHERE referenceno=:refno", params);
							if (list != null && list > 0) {
								return flag = "existing";
							} else {
								// 2 - update collateral record using old
								// reference no
								params.put("oldrefno", oldreferenceno);
								Tbcollateralmain colmain = (Tbcollateralmain) dbServiceLOS
										.executeUniqueHQLQueryMaxResultOne(
												"FROM Tbcollateralmain WHERE referenceno=:oldrefno", params);
								if (colmain != null) {
									colmain.setReferenceno(tb.getMain().getReferenceno());
									colmain.setAppraisalreportid(tb.getMain().getAppraisalreportid());
									colmain.setAppraisalstatus(tb.getMain().getAppraisalstatus());
									colmain.setLastappraisedvalue(tb.getMain().getLastappraisedvalue());
									colmain.setDateoflastappraisal(tb.getMain().getDateoflastappraisal());
									colmain.setDateupdated(new Date());
									colmain.setUpdatedby(user);

									String q = "UPDATE Tbloancollateral SET collateralreferenceno ='" + referenceno
											+ "' WHERE collateralreferenceno='" + oldreferenceno + "'";
									Integer res = dbServiceLOS.executeUpdate(q, null);
									if (res != null && res > 0) {
										Boolean isUpdatedSuccessfully = false;
										if (collateraltype.equals("1")) {
											Tbcollateralauto vehicle = (Tbcollateralauto) dbServiceLOS
													.executeUniqueHQLQueryMaxResultOne(
															"FROM Tbcollateralauto WHERE referenceno=:oldrefno",
															params);
											if (vehicle != null) {
												vehicle.setReferenceno(tb.getAuto().getReferenceno());
												vehicle.setNeworused(tb.getAuto().getNeworused());
												vehicle.setVehicletype(tb.getAuto().getVehicletype());
												vehicle.setMake(tb.getAuto().getMake());
												vehicle.setModel(tb.getAuto().getModel());
												vehicle.setModeldetails(tb.getAuto().getModeldetails());
												vehicle.setYear(tb.getAuto().getYear());
												vehicle.setSerialchassisno(tb.getAuto().getSerialchassisno());
												vehicle.setOrnumber(tb.getAuto().getOrnumber());
												vehicle.setEngineno(tb.getAuto().getEngineno());
												vehicle.setCrnumber(tb.getAuto().getCrnumber());
												vehicle.setMvfileno(tb.getAuto().getMvfileno());
												vehicle.setPlateno(tb.getAuto().getPlateno());
												vehicle.setConductionstickerno(tb.getAuto().getConductionstickerno());
												vehicle.setRegisteredowner(tb.getAuto().getRegisteredowner());
												vehicle.setRetailprice(tb.getAuto().getRetailprice());
												vehicle.setDatepurchased(tb.getAuto().getDatepurchased());
												vehicle.setLocation(tb.getAuto().getLocation());
												vehicle.setLtooffice(tb.getAuto().getLtooffice());
												vehicle.setFuel(tb.getAuto().getFuel());
												vehicle.setNoofcylinders(tb.getAuto().getNoofcylinders());
												vehicle.setKmreading(tb.getAuto().getKmreading());
												vehicle.setColor(tb.getAuto().getColor());
												vehicle.setAccessories(tb.getAuto().getAccessories());
												vehicle.setTirecondition(tb.getAuto().getTirecondition());
												vehicle.setRemarks(tb.getAuto().getRemarks());
												if (dbServiceLOS.saveOrUpdate(vehicle)) {
													isUpdatedSuccessfully = true;
													colmain.setRegisteredowner(tb.getAuto().getRegisteredowner());
												} else {
												}
											}
										} else if (collateraltype.equals("2")) {
											Tbcollateralrel rel = (Tbcollateralrel) dbServiceLOS
													.executeUniqueHQLQueryMaxResultOne(
															"FROM Tbcollateralrel WHERE referenceno=:oldrefno", params);
											if (rel != null) {// Condo
												rel.setReferenceno(tb.getRel().getReferenceno());
												rel.setTitleno(tb.getRel().getTitleno());
												rel.setBldgname(tb.getRel().getBldgname());
												rel.setUnitno(tb.getRel().getUnitno());
												rel.setUnittype(tb.getRel().getUnittype());
												rel.setRegisteredowner(tb.getRel().getRegisteredowner());
												rel.setPropertyaddress(tb.getRel().getPropertyaddress());
												rel.setArea(tb.getRel().getArea());
												rel.setPrevtitleno(tb.getRel().getPrevtitleno());
												rel.setPrevowner(tb.getRel().getPrevowner());
												rel.setPropertyvalue(tb.getRel().getPropertyvalue());
												rel.setRemarks(tb.getRel().getRemarks());
												// House and Lot && Vacant Lot
												rel.setHousetype(tb.getRel().getHousetype());
												rel.setLotno(tb.getRel().getLotno());
												rel.setBlockno(tb.getRel().getBlockno());
												if (dbServiceLOS.saveOrUpdate(rel)) {
													isUpdatedSuccessfully = true;
													colmain.setRegisteredowner(tb.getRel().getRegisteredowner());
												}
											}
										} else if (collateraltype.equals("3")) {
											Tbcollateraldeposits deposit = (Tbcollateraldeposits) dbServiceLOS
													.executeUniqueHQLQueryMaxResultOne(
															"FROM Tbcollateraldeposits WHERE referenceno=:oldrefno",
															params);
											if (deposit != null) {
												deposit.setReferenceno(tb.getDeposits().getReferenceno());
												deposit.setRegisteredname(tb.getDeposits().getRegisteredname());
												deposit.setAcctnumber(tb.getDeposits().getAcctnumber());
												deposit.setAmount(tb.getDeposits().getAmount());
												deposit.setIntrate(tb.getDeposits().getIntrate());
												deposit.setDateofplacement(tb.getDeposits().getDateofplacement());
												deposit.setMaturitydate(tb.getDeposits().getMaturitydate());
												deposit.setRemarks(tb.getDeposits().getRemarks());
												isUpdatedSuccessfully = dbServiceLOS.saveOrUpdate(deposit);
											}
										} else if (collateraltype.equals("4")) {
											Tbcollateralmachineries machine = (Tbcollateralmachineries) dbServiceLOS
													.executeUniqueHQLQueryMaxResultOne(
															"FROM Tbcollateralmachineries WHERE referenceno=:oldrefno",
															params);
											if (machine != null) {
												machine.setReferenceno(tb.getMachineries().getReferenceno());
												machine.setMachinerydetails(tb.getMachineries().getMachinerydetails());
												machine.setSerialnumber(tb.getMachineries().getSerialnumber());
												machine.setManufacturer(tb.getMachineries().getManufacturer());
												machine.setManufacturedyear(tb.getMachineries().getManufacturedyear());
												machine.setMachinelocation(tb.getMachineries().getMachinelocation());
												machine.setMachinecondition(tb.getMachineries().getMachinecondition());
												machine.setIntendeduse(tb.getMachineries().getIntendeduse());
												machine.setRemarks(tb.getMachineries().getRemarks());
												isUpdatedSuccessfully = dbServiceLOS.saveOrUpdate(machine);
											}
										} else if (collateraltype.equals("5")) {
											Tbcollaterallivestock livestock = (Tbcollaterallivestock) dbServiceLOS
													.executeUniqueHQLQuery(
															"FROM Tbcollaterallivestock WHERE referenceno=:oldrefno",
															params);
											if (livestock != null) {
												livestock.setReferenceno(tb.getLivestock().getReferenceno());
												livestock.setDescription(tb.getLivestock().getDescription());
												livestock.setAppraisalstatus(tb.getLivestock().getAppraisalstatus());
												livestock.setCollateralstatus(tb.getLivestock().getCollateralstatus());
												livestock.setCollateralid(tb.getLivestock().getCollateralid());
												isUpdatedSuccessfully = dbServiceLOS.saveOrUpdate(livestock);
											}
//											Tbcollateralsecurities securities = (Tbcollateralsecurities) dbServiceLOS
//													.executeUniqueHQLQueryMaxResultOne(
//															"FROM Tbcollateralsecurities WHERE referenceno=:oldrefno",
//															params);
//											if (securities != null) {
//												securities.setReferenceno(tb.getSecurities().getReferenceno());
//												securities.setReferenceno(tb.getSecurities().getReferenceno());
//												securities.setSecuritycode(tb.getSecurities().getSecuritycode());
//												securities.setSecuritydesc(tb.getSecurities().getSecuritydesc());
//												securities.setIsinno(tb.getSecurities().getIsinno());
//												securities.setIssuername(tb.getSecurities().getIssuername());
//												securities.setUnitvalue(tb.getSecurities().getUnitvalue());
//												securities.setRating(tb.getSecurities().getRating());
//												securities.setMaturitydate(tb.getSecurities().getMaturitydate());
//												securities.setFundname(tb.getSecurities().getFundname());
//												securities.setSchemename(tb.getSecurities().getSchemename());
//												securities.setIntpayout(tb.getSecurities().getIntpayout());
//												securities.setBondduration(tb.getSecurities().getBondduration());
//												securities.setSecurityprice(tb.getSecurities().getSecurityprice());
//												securities.setRemarks(tb.getSecurities().getRemarks());
//												isUpdatedSuccessfully = dbServiceLOS.saveOrUpdate(securities);
//											}
										}
										if (isUpdatedSuccessfully) {
											if (dbServiceLOS.saveOrUpdate(colmain)) {
												flag = "success";
											}
										}
									}
								}
							}
						} else {
							// Start of Update - NO Changes at Reference Number
							// - query using > referenceno <
							Tbcollateralmain colmain = (Tbcollateralmain) dbServiceLOS
									.executeUniqueHQLQueryMaxResultOne("FROM Tbcollateralmain WHERE referenceno=:refno",
											params);
							if (colmain != null) {
								// colmain.setReferenceno(tb.getMain().getReferenceno());
								colmain.setAppraisalreportid(tb.getMain().getAppraisalreportid());
								colmain.setAppraisalstatus(tb.getMain().getAppraisalstatus());
								colmain.setLastappraisedvalue(tb.getMain().getLastappraisedvalue());
								colmain.setDateoflastappraisal(tb.getMain().getDateoflastappraisal());
								colmain.setDateupdated(new Date());
								colmain.setUpdatedby(secservice.getUserName());
								Boolean isUpdatedSuccessfully = false;
								if (collateraltype.equals("1")) {
									Tbcollateralauto vehicle = (Tbcollateralauto) dbServiceLOS
											.executeUniqueHQLQueryMaxResultOne(
													"FROM Tbcollateralauto WHERE referenceno=:refno", params);
									if (vehicle != null) {
										// vehicle.setReferenceno(tb.getAuto().getReferenceno());
										vehicle.setNeworused(tb.getAuto().getNeworused());
										vehicle.setVehicletype(tb.getAuto().getVehicletype());
										vehicle.setMake(tb.getAuto().getMake());
										vehicle.setModel(tb.getAuto().getModel());
										vehicle.setModeldetails(tb.getAuto().getModeldetails());
										vehicle.setYear(tb.getAuto().getYear());
										vehicle.setSerialchassisno(tb.getAuto().getSerialchassisno());
										vehicle.setOrnumber(tb.getAuto().getOrnumber());
										vehicle.setEngineno(tb.getAuto().getEngineno());
										vehicle.setCrnumber(tb.getAuto().getCrnumber());
										vehicle.setMvfileno(tb.getAuto().getMvfileno());
										vehicle.setPlateno(tb.getAuto().getPlateno());
										vehicle.setConductionstickerno(tb.getAuto().getConductionstickerno());
										vehicle.setRegisteredowner(tb.getAuto().getRegisteredowner());
										vehicle.setRetailprice(tb.getAuto().getRetailprice());
										vehicle.setDatepurchased(tb.getAuto().getDatepurchased());
										vehicle.setLocation(tb.getAuto().getLocation());
										vehicle.setLtooffice(tb.getAuto().getLtooffice());
										vehicle.setFuel(tb.getAuto().getFuel());
										vehicle.setNoofcylinders(tb.getAuto().getNoofcylinders());
										vehicle.setKmreading(tb.getAuto().getKmreading());
										vehicle.setColor(tb.getAuto().getColor());
										vehicle.setAccessories(tb.getAuto().getAccessories());
										vehicle.setTirecondition(tb.getAuto().getTirecondition());
										vehicle.setRemarks(tb.getAuto().getRemarks());
										isUpdatedSuccessfully = dbServiceLOS.saveOrUpdate(vehicle);
									}
								} else if (collateraltype.equals("2")) {
									Tbcollateralrel rel = (Tbcollateralrel) dbServiceLOS
											.executeUniqueHQLQueryMaxResultOne(
													"FROM Tbcollateralrel WHERE referenceno=:refno", params);
									if (rel != null) {
										// Condo
										// rel.setReferenceno(tb.getRel().getReferenceno());
										rel.setTitleno(tb.getRel().getTitleno());
										rel.setBldgname(tb.getRel().getBldgname());
										rel.setUnitno(tb.getRel().getUnitno());
										rel.setUnittype(tb.getRel().getUnittype());
										rel.setRegisteredowner(tb.getRel().getRegisteredowner());
										rel.setPropertyaddress(tb.getRel().getPropertyaddress());
										rel.setArea(tb.getRel().getArea());
										rel.setPrevtitleno(tb.getRel().getPrevtitleno());
										rel.setPrevowner(tb.getRel().getPrevowner());
										rel.setPropertyvalue(tb.getRel().getPropertyvalue());
										rel.setRemarks(tb.getRel().getRemarks());
										// House and Lot && Vacant Lot
										rel.setHousetype(tb.getRel().getHousetype());
										rel.setLotno(tb.getRel().getLotno());
										rel.setBlockno(tb.getRel().getBlockno());
										isUpdatedSuccessfully = dbServiceLOS.saveOrUpdate(rel);
									}
								} else if (collateraltype.equals("3")) {
									Tbcollateraldeposits deposit = (Tbcollateraldeposits) dbServiceLOS
											.executeUniqueHQLQueryMaxResultOne(
													"FROM Tbcollateraldeposits WHERE referenceno=:refno", params);
									if (deposit != null) {
										// deposit.setReferenceno(tb.getDeposits().getReferenceno());
										deposit.setRegisteredname(tb.getDeposits().getRegisteredname());
										deposit.setAcctnumber(tb.getDeposits().getAcctnumber());
										deposit.setAmount(tb.getDeposits().getAmount());
										deposit.setIntrate(tb.getDeposits().getIntrate());
										deposit.setDateofplacement(tb.getDeposits().getDateofplacement());
										deposit.setMaturitydate(tb.getDeposits().getMaturitydate());
										deposit.setRemarks(tb.getDeposits().getRemarks());
										isUpdatedSuccessfully = dbServiceLOS.saveOrUpdate(deposit);
									}
								} else if (collateraltype.equals("4")) {
									Tbcollateralmachineries machine = (Tbcollateralmachineries) dbServiceLOS
											.executeUniqueHQLQueryMaxResultOne(
													"FROM Tbcollateralmachineries WHERE referenceno=:refno", params);
									if (machine != null) {
										// machine.setReferenceno(tb.getMachineries().getReferenceno());
										machine.setMachinerydetails(tb.getMachineries().getMachinerydetails());
										machine.setSerialnumber(tb.getMachineries().getSerialnumber());
										machine.setManufacturer(tb.getMachineries().getManufacturer());
										machine.setManufacturedyear(tb.getMachineries().getManufacturedyear());
										machine.setMachinelocation(tb.getMachineries().getMachinelocation());
										machine.setMachinecondition(tb.getMachineries().getMachinecondition());
										machine.setIntendeduse(tb.getMachineries().getIntendeduse());
										machine.setRemarks(tb.getMachineries().getRemarks());
										isUpdatedSuccessfully = dbServiceLOS.saveOrUpdate(machine);
									}
								} else if (collateraltype.equals("5")) {
									// This is livestock not securities
									Tbcollaterallivestock livestock = (Tbcollaterallivestock) dbServiceLOS
											.executeUniqueHQLQuery(
													"FROM Tbcollaterallivestock WHERE referenceno=:refno", params);
									if (livestock != null) {
										livestock.setReferenceno(tb.getLivestock().getReferenceno());
										livestock.setDescription(tb.getLivestock().getDescription());
										livestock.setAppraisalstatus(tb.getLivestock().getAppraisalstatus());
										livestock.setCollateralstatus(tb.getLivestock().getCollateralstatus());
										livestock.setCollateralid(tb.getLivestock().getCollateralid());
										isUpdatedSuccessfully = dbServiceLOS.saveOrUpdate(livestock);
									}
									// Tbcollateralsecurities securities =
									// (Tbcollateralsecurities) dbServiceLOS
									// .executeUniqueHQLQueryMaxResultOne(
									// "FROM Tbcollateralsecurities WHERE
									// referenceno=:refno", params);
									// if (securities != null) {
									// //
									// securities.setReferenceno(tb.getSecurities().getReferenceno());
									// //
									// securities.setSecuritytype(tb.getSecurities().getSecuritytype());
									// // remove as per Noreen
									// securities.setReferenceno(tb.getSecurities().getReferenceno());
									// securities.setSecuritycode(tb.getSecurities().getSecuritycode());
									// securities.setSecuritydesc(tb.getSecurities().getSecuritydesc());
									// securities.setIsinno(tb.getSecurities().getIsinno());
									// securities.setIssuername(tb.getSecurities().getIssuername());
									// securities.setUnitvalue(tb.getSecurities().getUnitvalue());
									// //
									// securities.setNoofunits(tb.getSecurities().getNoofunits());
									// securities.setRating(tb.getSecurities().getRating());
									// securities.setMaturitydate(tb.getSecurities().getMaturitydate());
									// securities.setFundname(tb.getSecurities().getFundname());
									// securities.setSchemename(tb.getSecurities().getSchemename());
									// securities.setIntpayout(tb.getSecurities().getIntpayout());
									// //
									// securities.setIntrate(tb.getSecurities().getIntrate());
									// securities.setBondduration(tb.getSecurities().getBondduration());
									// securities.setSecurityprice(tb.getSecurities().getSecurityprice());
									// securities.setRemarks(tb.getSecurities().getRemarks());
									// isUpdatedSuccessfully =
									// dbServiceLOS.saveOrUpdate(securities);
									// //
									// if(dbServiceLOS.saveOrUpdate(securities)){
									// // isUpdatedSuccessfully = true;
									// // }
									// }
								}
								if (isUpdatedSuccessfully) {
									if (dbServiceLOS.saveOrUpdate(colmain)) {
										flag = "success";
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String checkCollateralMain(String referenceno) {
		String flag = "failed";
		try {
			if (referenceno != null) {
				params.put("refno", referenceno);
				Integer colmain = (Integer) dbServiceLOS.executeUniqueSQLQuery(
						"SELECT COUNT(*) FROM Tbcollateralmain WHERE referenceno=:refno", params);
				if (colmain != null && colmain > 0) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Tbcollateralmain geTbcollateralmain(String referenceno) {
		Tbcollateralmain main = new Tbcollateralmain();
		try {
			if (referenceno != null) {
				params.put("refno", referenceno);
				main = (Tbcollateralmain) dbServiceLOS
						.executeUniqueHQLQueryMaxResultOne("FROM Tbcollateralmain WHERE referenceno=:refno", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return main;
	}

	@Override
	public String saveAndUseCollateral(String appno, String collateralrefno, String collateraltype) {
		String flag = "failed";
		System.out.println("SAVE and USE COLLATERAL");
		try {
			String colid = ApplicationNoGenerator.generateCollateralID("COL");
			if (appno != null && collateralrefno != null && collateraltype != null && colid != null) {
				params.put("a", appno);
				params.put("refno", collateralrefno);
				params.put("id", colid);
				// Integer row = (Integer) dbServiceLOS.executeUniqueSQLQuery(
				// "SELECT COUNT(*) FROM Tbloancollateral WHERE
				// collateralreferenceno=:refno AND appno=:a AND colid=:id",
				// params);
				Integer row = (Integer) dbServiceLOS.executeUniqueSQLQuery(
						"SELECT COUNT(*) FROM Tbloancollateral WHERE collateralreferenceno=:refno", params);
				if (row != null && row > 0) {
					return flag = "existing";
				} else {
					
					
					String q = "INSERT INTO Tbloancollateral (collateralreferenceno, appno, collateraltype, colid, appraisedvalue, loanablevalue, marketvalue) VALUES ('"
							+ collateralrefno + "','" + appno + "','" + collateraltype + "','" + colid + "', "
							+ "(SELECT appraisedvalue FROM Tbappraisalreportmain WHERE referenceno='" + collateralrefno
							+ "'), " + "(SELECT loanablevalue FROM Tbappraisalreportmain WHERE referenceno='"
							+ collateralrefno + "'), "
							+ "(SELECT marketvalue FROM Tbappraisalreportmain WHERE referenceno='" + collateralrefno
							+ "'))";
					Integer res = dbServiceLOS.executeUpdate(q, null);
					System.out.println(res);
					if (res != null && res > 0) {
						flag = "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TbCollateralMainForm> getCollateralMainList(String appno, String collateraltype, Boolean audit) {
		List<TbCollateralMainForm> list = new ArrayList<TbCollateralMainForm>();
		try {
			String q = "SELECT a.collateralid, " + "a.referenceno, "
					+ "(SELECT desc1 FROM TBCODETABLE WHERE codename='COLLATERALTYPE' AND codevalue=a.collateraltype) as collateraltype, "
					+ "a.dateoflastappraisal, "
					+ "a.appraisalstatus as appraisalstatus, "
					+ "(SELECT desc1 FROM TBCODETABLE WHERE codename='COLLATERALSTATUS' AND codevalue=a.collateralstatus) as collateralstatus,"
					+ "(SELECT desc1 FROM TBCODETABLE WHERE codename='PROPERTYTYPE' AND codevalue=c.propertytype ) as propertytype, "
					+ "(SELECT desc1 FROM Tbcodetable WHERE codename='NEWUSED' AND codevalue=(SELECT neworused FROM TBCOLLATERALAUTO WHERE referenceno=a.referenceno)) as newused "
					+ "FROM TBCOLLATERALMAIN a " + "LEFT JOIN TBCOLLATERALREL c ON c.referenceno = a.referenceno ";
			if (appno != null && !appno.equals("")) {
				q = q + "LEFT JOIN TBLOANCOLLATERAL b ON a.referenceno = b.collateralreferenceno WHERE b.appno ='"
						+ appno + "'";
				if (collateraltype != null) {
					q = q + " AND a.collateraltype ='" + collateraltype + "'";
				}
			} else {
				if (collateraltype != null) {
					q = q + "WHERE a.collateraltype ='" + collateraltype + "'";
				}
			}
			q = q + " ORDER BY a.collateraltype DESC";
			list = (List<TbCollateralMainForm>) dbServiceLOS.execSQLQueryTransformer(q, null,
					TbCollateralMainForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (audit != null) {
			if (audit) {
				params.put("appno", appno);
				Integer status = (Integer) dbServiceLOS
						.executeUniqueSQLQuery("SELECT applicationstatus FROM Tblstapp WHERE appno=:appno", params);
				if (status.equals(1)) {
					AuditLog.addAuditLog(
							AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("SEARCH COLLATERAL TYPE",
									AuditLogEvents.LOAN_APPLICATION_ENCODING)),
							"User " + username + " Search " + appno + "'s Collateral Details.", username, new Date(),
							AuditLogEvents.LOAN_APPLICATION_ENCODING);
				}
				if (status.equals(3)) {
					AuditLog.addAuditLog(
							AuditLogEvents.getAuditLogEvents(
									AuditLogEvents.getEventID("SEARCH COLLATERAL TYPE FROM EVAL REPORT",
											AuditLogEvents.LOAN_APPLICATION_EVALUATION)),
							"User " + username + " Searched " + appno + "'s Collateral Type from Eval Report.",
							username, new Date(), AuditLogEvents.LOAN_APPLICATION_EVALUATION);
				}
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanApplicationUsageForm> listLoanApplicationUsage(String referenceno, String loanno) {
		List<LoanApplicationUsageForm> list = new ArrayList<LoanApplicationUsageForm>();
		try {
			if (referenceno != null) {
				params.put("refno", referenceno);
				String q = "SELECT a.collateralreferenceno, a.appno, a.loanno, "
						+ "(SELECT productname FROM TBLOANPRODUCT WHERE productcode=(SELECT loanproduct FROM TBLSTAPP WHERE appno=a.appno)) as loanproduct, "
						+ "(SELECT processname FROM Tbworkflowprocess WHERE sequenceno=(SELECT applicationstatus FROM TBLSTAPP WHERE appno=a.appno) "
						+ "AND workflowid=(SELECT applicationtype FROM TBLSTAPP WHERE appno=a.appno)) as applicationstatus, "
						+ "(SELECT desc1 FROM TBCODETABLE WHERE codename='ACCTSTS' AND codevalue=a.loanstatus) as loanstatus, "
						+ "(SELECT cifname FROM TBLSTAPP WHERE appno=a.appno) as borrowername "
						+ "FROM TBLOANCOLLATERAL a " + "WHERE a.collateralreferenceno=:refno";
				if (loanno != null) {
					q = q + " AND a.loanno IS NOT NULL";
				} else {
					q = q + " AND a.loanno IS NULL";
				}
				list = (List<LoanApplicationUsageForm>) dbServiceLOS.execSQLQueryTransformer(q, params,
						LoanApplicationUsageForm.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String removeCollateral(String appno, String referenceno) {
		String flag = "failed";
		try {
			if (referenceno != null) {
				if (appno != null && !appno.equals("")) {
					String q = "DELETE FROM Tbloancollateral WHERE appno='" + appno + "' AND collateralreferenceno='"
							+ referenceno + "'";
					Integer res = dbServiceLOS.executeUpdate(q, null);
					if (res != null && res == 1) {
						flag = "success";
					}
				} else {
					params.put("refno", referenceno);
					Integer row = (Integer) dbServiceLOS.executeUniqueSQLQuery(
							"SELECT COUNT(*) FROM Tbloancollateral WHERE collateralreferenceno=:refno", params);
					if (row != null && row > 0) {
						return flag = "existing";
					} else {
						// Aug30,2018
						Tbcollateralmain main = (Tbcollateralmain) dbServiceLOS.executeUniqueHQLQueryMaxResultOne(
								"FROM Tbcollateralmain WHERE referenceno=:refno", params);
						if (main != null && main.getCollateraltype() != null) {
							Integer res = null;
							if (main.getCollateraltype().equals("1")) {
								res = dbServiceLOS.executeUpdate(
										"DELETE FROM Tbcollateralauto WHERE referenceno='" + referenceno + "'", null);
							} else if (main.getCollateraltype().equals("2")) {
								res = dbServiceLOS.executeUpdate(
										"DELETE FROM Tbcollateralrel WHERE referenceno='" + referenceno + "'", null);
							} else if (main.getCollateraltype().equals("3")) {
								res = dbServiceLOS.executeUpdate(
										"DELETE FROM Tbcollateraldeposits WHERE referenceno='" + referenceno + "'",
										null);
							} else if (main.getCollateraltype().equals("4")) {
								res = dbServiceLOS.executeUpdate(
										"DELETE FROM Tbcollateralmachineries WHERE referenceno='" + referenceno + "'",
										null);
							} else if (main.getCollateraltype().equals("5")) {// Changed
																				// to
																				// Livestock
								res = dbServiceLOS.executeUpdate(
										"DELETE FROM Tbcollaterallivestock WHERE referenceno='" + referenceno + "'",
										null);
								// res = dbServiceLOS.executeUpdate(
								// "DELETE FROM Tbcollateralsecurities WHERE
								// referenceno='" + referenceno + "'",
								// null);
							}
							if (res != null && res == 1) {
								Integer res2 = dbServiceLOS.executeUpdate(
										"DELETE FROM Tbcollateralmain WHERE referenceno='" + referenceno + "'", null);
								if (res2 != null && res2 == 1) {
									flag = "success";
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public int getCArequestTotal(String appNo, String reqId, String refNo, String reqStat, String colType,
			String appType, String colCat, String assgndUser, Boolean viewflag, Boolean isViewRequest) {
		Integer total = 0;
		assgndUser = UserUtil.securityService.getUserName();
		try {
			StringBuilder hql = new StringBuilder();
			if (appNo == null && reqId == null && refNo == null && reqStat == null && colType == null && appType == null
					&& colCat == null) {
				return 0;
			} else {
				hql.append("SELECT count(*) FROM Tbcolappraisalrequest WHERE colappraisalrequestid is not null ");
				if (reqId != null) {
					hql.append(" AND colappraisalrequestid like '%" + reqId + "%'");
				}
				if (refNo != null) {
					hql.append(" AND referenceno = '" + refNo + "'");
				}
				if (reqStat != null) {
					hql.append(" AND status like '%" + reqStat + "%'");
				}
				if (colType != null) {
					hql.append(" AND collateraltype like '%" + colType + "%'");
				}
				if (viewflag == null || viewflag == false) {
					hql.append(" AND appno IS NULL");
				} else {
					if (appNo != null) {
						hql.append(" AND appno like '%" + appNo + "%'");
					}
					if (assgndUser != null && isViewRequest == null) {
						hql.append(" AND ((requestedby='" + assgndUser + "' AND status='0') OR ((assignedappraiser='"
								+ assgndUser + "') AND status IN ('0','1','6')) OR (assignedappsupervisor='"
								+ assgndUser + "' AND status NOT IN ('5')))");// Completed
					}
				}
				if (appType != null) {
					hql.append(" AND typeappraisal like '%" + appType + "%'");

				}
				if (colCat != null) {
					hql.append(" AND collateralcategory like '%" + colCat + "%'");
				}
			}
			total = (Integer) dbServiceLOS.executeUniqueSQLQuery(hql.toString(), params);
			if (total == null) {
				return 0;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	@Override
	public CAAccessRightsForm getCAAccessRights(String requestid, String dlgType, Boolean viewflag) {
		// TODO Auto-generated method stub
		CAAccessRightsForm form = new CAAccessRightsForm();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (requestid != null) {
				params.put("requestid", requestid);
				Tbcolappraisalrequest req = (Tbcolappraisalrequest) dbServiceLOS.executeUniqueHQLQuery(
						"FROM Tbcolappraisalrequest " + "WHERE colappraisalrequestid=:requestid", params);
				if (req != null) {
					String supervisor = new DefaultUsers(req.getCompanycode()).getAppraisalsupervisor();
					// Draft
					// if(req.getStatus().equals("0")){
					if (req.getStatus().equals("dr")) {
						if (req.getRequestedby() != null
								&& req.getRequestedby().equals(UserUtil.securityService.getUserName())) {
							form.setBtnSave(true);
							form.setBtnSubmit(true);
							form.setReadOnly(false);
							// Outside Application
							if (req.getAppno() == null || (viewflag != null && viewflag == false)) {
								form.setSlcCompany(false);
								form.setBtnCancel(true);
							}
						}
						// CA Supervisor
						if (supervisor != null && supervisor.equals(UserUtil.securityService.getUserName())) {
							form.setBtnSubmit(true);
							form.setBtnCancel(true);
							form.setSlcAssignedCA(false);

						}
					}
					// New
					// if(req.getStatus().equals("1")){
					if (req.getStatus().equals("0")) {
						if (req.getAssignedappraiser() != null
								&& req.getAssignedappraiser().equals(UserUtil.securityService.getUserName())) {
							form.setBtnSubmit(false);
							form.setBtnCreateReport(true);
						}
					}
					if (req.getStatus().equals("0") || req.getStatus().equals("1") || req.getStatus().equals("2")
							|| req.getStatus().equals("6")) {
					}

				}

			} else {
				// if (dlgType != null && dlgType.equals("new")) {
				// form.setBtnCreateReport(true);//Daniel 12.14.2018
				form.setBtnSave(true);
				form.setReadOnly(false);
				if (viewflag != null && viewflag == false) {
					form.setSlcCompany(false);
				}
				// }
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public ReturnForm createCAReport(String carequestid, String careportid) {
		ReturnForm form = new ReturnForm();
		Map<String, Object> params = HQLUtil.getMap();
		form.setFlag("failed");
		Tbcolappraisalrequest req = new Tbcolappraisalrequest();
		Tbappraisalreportmain repmain = new Tbappraisalreportmain();
		try {
			params.put("requestid", carequestid);
			req = (Tbcolappraisalrequest) dbServiceLOS
					.executeUniqueHQLQuery("FROM Tbcolappraisalrequest WHERE colappraisalrequestid=:requestid", params);
			if (req != null) {
				if (careportid == null) {
					repmain = new Tbappraisalreportmain();
					repmain.setAppno(req.getAppno());
					String genCaReportid = ApplicationNoGenerator.generateReportID("CA");

					repmain.setAppraisalreportid(genCaReportid);
					repmain.setColappraisalrequestid(carequestid);
					repmain.setTypeappraisal(req.getTypeappraisal());
					repmain.setCollateraltype(req.getCollateraltype());
					repmain.setReferenceno(req.getReferenceno());
					repmain.setReportedby(UserUtil.securityService.getUserName());
					repmain.setReportedby(req.getAssignedappraiser());
//					repmain.setReportdate(new Date());
					repmain.setStatus("0");
					repmain.setStatusdatetime(new Date());
					repmain.setCompanycode(req.getCompanycode());
					repmain.setReasonforappraisal(req.getPurposeofappraisal());
					repmain.setRequestingofficer(req.getRequestedby());
					repmain.setNewused(req.getNewused());
					repmain.setPropertytype(req.getPropertytype());
					repmain.setDatecreated(new Date());
					repmain.setReviewedby(req.getRequestedby());
					// Wel 10.10.18
					params.put("appno", req.getAppno());
					params.put("refno", req.getReferenceno());
					Tbloancollateral row = (Tbloancollateral) dbServiceLOS.executeUniqueHQLQueryMaxResultOne(
							"FROM Tbloancollateral WHERE appno=:appno AND collateralreferenceno=:refno", params);
					String appno = null;
					if (row != null) {
						appno = row.getAppno();
					}
					params.put("appno2", appno);
					Tblstapp app = (Tblstapp) dbServiceLOS
							.executeUniqueHQLQueryMaxResultOne("FROM Tblstapp WHERE appno=:appno2", params);
					if (app != null && app.getCifno() != null) {
						repmain.setCifno(app.getCifno());
					}

					if (dbServiceLOS.save(repmain)) {
						form.setFlag("Success");
						form.setMessage(repmain.getAppraisalreportid());
					}

				} else {
					params.put("reportid", careportid);
					repmain = (Tbappraisalreportmain) dbServiceLOS.executeUniqueHQLQuery(
							"FROM Tbappraisalreportmain " + "WHERE appraisalreportid=:reportid", params);
					if (repmain != null) {
						repmain.setDateupdated(new Date());
						if (dbServiceLOS.saveOrUpdate(repmain)) {
							form.setFlag("Success");
							form.setMessage(repmain.getAppraisalreportid());
						}
					}
				}
				// req.setStatus("2");
				// dbServiceLOS.saveOrUpdate(req);

			}
			if (form.getFlag().equals("Success")) {
				// Set status to On-Process(Tbinvestigationinst) Inside
				// application
				if (repmain.getAppno() != null && !repmain.equals("---")) {
					params.put("appno", repmain.getAppno());
					params.put("cifno", repmain.getCifno());
					params.put("refno", repmain.getReferenceno());
					params.put("invsttype", "CA");

					// On-Process
					dbServiceLOS.executeUpdate(
							"UPDATE Tbcolinvestigationinst SET status='2' WHERE appno=:appno AND cifno=:cifno AND investigationtype=:invsttype AND referenceno=:refno",
							params);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public Tbappraisalrel getRealEstatebyReportId(String appreportid) {
		Tbappraisalrel real = new Tbappraisalrel();
		try {
			if (appreportid != null) {
				params.put("reportid", appreportid);
				real = (Tbappraisalrel) dbServiceLOS
						.executeUniqueHQLQuery("FROM Tbappraisalrel WHERE " + "appraisalreportid=:reportid", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return real;
	}

	@Override
	public Tbappraisalauto getAppAutoByRepId(String appreportid) {
		Tbappraisalauto auto = new Tbappraisalauto();
		try {
			if (appreportid != null) {
				params.put("reportid", appreportid);
				auto = (Tbappraisalauto) dbServiceLOS
						.executeUniqueHQLQuery("FROM Tbappraisalauto WHERE " + "appraisalreportid=:reportid", params);
				if (auto != null) {
					return auto;
				} else {
					Tbappraisalreportmain m = getCaReportMain(appreportid);
					if (m != null) {
						CollateralTables c = getCollateralbyCollateralTypeAndRefNo(m.getCollateraltype(),
								m.getReferenceno());
						if (c.getAuto() != null) {
							Tbappraisalauto n = new Tbappraisalauto();
							n.setChassisno(c.getAuto().getSerialchassisno());
							n.setColor(c.getAuto().getColor());
							n.setVehiclecategory(c.getAuto().getVehicletype());
							n.setFuel(c.getAuto().getFuel());
//							n.setKmreading(c.getAuto().getkm);
							n.setLocation(c.getAuto().getLocation());
							n.setPlateno(c.getAuto().getPlateno());
							n.setEngineno(c.getAuto().getEngineno());
							n.setRetailprice(c.getAuto().getRetailprice());
							n.setYear(c.getAuto().getYear());
							n.setMake(c.getAuto().getMake());
							n.setModeldetails(c.getAuto().getModeldetails());
							n.setModelseries(c.getAuto().getModel());
							n.setMotorno(c.getAuto().getEngineno());
							n.setRemLocation(c.getAuto().getLocation());
							n.setRemMvfileno(c.getAuto().getMvfileno());
							n.setRemLtoagency(c.getAuto().getLtooffice());
							return n;
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return auto;
	}

	@Override
	public String addOrupdateAutoAppraisal(Tbappraisalauto auto) {
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();

		try {
			System.out.println(auto.toString());
			params.put("reportid", auto.getAppraisalreportid());
			Tbappraisalauto appauto = (Tbappraisalauto) dbServiceLOS
					.executeUniqueHQLQuery("FROM Tbappraisalauto WHERE appraisalreportid=:reportid", params);
			if (appauto != null) {
				auto.setAppraisalreportid(appauto.getAppraisalreportid());
				if (dbServiceLOS.saveOrUpdate(auto)) {
					flag = "updated";
				}
			} else {
				if (dbServiceLOS.save(auto)) {
					flag = "created";
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String addOrupdateRealEstate(Tbappraisalrel real) {
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("reportid", real.getAppraisalreportid());
			Tbappraisalrel rel = (Tbappraisalrel) dbServiceLOS
					.executeUniqueHQLQuery("FROM Tbappraisalrel WHERE appraisalreportid=:reportid", params);

			if (rel != null) {
				real.setAppraisalreportid(rel.getAppraisalreportid());
				if (dbServiceLOS.saveOrUpdate(real)) {
					flag = "updated";
				}

			} else {
				if (dbServiceLOS.save(real)) {
					flag = "created";
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String addOrupdateMachineries(Tbappraisalmachine machine, String type) {
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		try {
			System.out.println(machine);
			params.put("reportid", machine.getAppraisalreportid());
			Tbappraisalmachine mach = (Tbappraisalmachine) dbServiceLOS
					.executeUniqueHQLQuery("FROM Tbappraisalmachine WHERE appraisalreportid=:reportid", params);

			if (mach != null) {
				machine.setAppraisalreportid(mach.getAppraisalreportid());
				machine.setMachinetype(type);
				if (dbServiceLOS.saveOrUpdate(machine)) {
					flag = "success";
				}
			} else {
				machine.setMachinetype(type);
				if (dbServiceLOS.save(machine)) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String appraisalChangeStatus(String status, String requestid, String reasonforreturn) {
		String flag = "failed";
		try {
			if (status != null && requestid != null) {
				params.put("reportid", requestid);
				Tbappraisalreportmain row = (Tbappraisalreportmain) dbServiceLOS.executeUniqueHQLQueryMaxResultOne(
						"FROM Tbappraisalreportmain WHERE appraisalreportid=:reportid", params);
				/*
				 * REPORTSTATUS 0 New REPORTSTATUS 1 For Review REPORTSTATUS 2 Reviewed
				 * REPORTSTATUS 3 Returned REPORTSTATUS 4 Cancelled
				 */
				if (row != null) {
					row.setStatus(status);
					row.setDateupdated(new Date());
					row.setStatusdatetime(new Date());

					/** For Review */
					if (status.equals("2")) {
						row.setReviewedby(secservice.getUserName());
						row.setDatereviewed(new Date());

						/*
						 * REQUESTSTATUS 0 Draft REQUESTSTATUS 1 New REQUESTSTATUS 2 On-going
						 * REQUESTSTATUS 3 Completed REQUESTSTATUS 4 Cancelled
						 */

						// Set status to Completed (Tbcolappraisalrequest)
						params.put("carequestid", row.getColappraisalrequestid());
						int res = dbServiceLOS.executeUpdate(
								"UPDATE Tbcolappraisalrequest SET status='3' WHERE colappraisalrequestid=:carequestid",
								params);
						if (res > 0) {
							// Update status (Tbinvestigationinst) Inside
							// application
							if (row.getAppno() != null) {
								params.put("appno", row.getAppno());
								params.put("cifno", row.getCifno());
								params.put("invsttype", "CA");
								params.put("refno", row.getReferenceno());
								Integer a = (Integer) dbServiceLOS.executeUniqueSQLQuery(
										"SELECT COUNT(*) FROM Tbcolappraisalrequest WHERE appno=:appno AND referenceno=:refno AND status IN ('0','1','2')",
										params);
								if (a == null || (a != null && a == 0)) {
									// System.out.println(">>>>>>>>>>>>
									// Instruction Completed <<<<<<<<<<<<");
									dbServiceLOS.executeUpdate(
											"UPDATE Tbcolinvestigationinst SET status='3' WHERE appno=:appno AND cifno=:cifno AND investigationtype=:invsttype AND referenceno=:refno",
											params);
								} else {
									dbServiceLOS.executeUpdate(
											"UPDATE Tbcolinvestigationinst SET status='2' WHERE appno=:appno AND cifno=:cifno AND investigationtype=:invsttype AND referenceno=:refno",
											params);
								}
							}
						}
					}

					/** Return */
					if (status.equals("4")) {
						row.setReasonforreturn(reasonforreturn);

						// Set status to Report Returned (Tbcolappraisalrequest)
						params.put("carequestid", row.getColappraisalrequestid());
						dbServiceLOS.executeUpdate(
								"UPDATE Tbcolappraisalrequest SET status='4' WHERE colappraisalrequestid=:carequestid",
								params);
					}

					/** Cancelled */
					if (status.equals("5")) {
						// Set status to Cancelled (Tbcolappraisalrequest)
						params.put("carequestid", row.getColappraisalrequestid());
						dbServiceLOS.executeUpdate(
								"UPDATE Tbcolappraisalrequest SET status='5' WHERE colappraisalrequestid=:carequestid",
								params);
					}

					if (dbServiceLOS.saveOrUpdate(row)) {
						if (row.getStatus().equals("4")) {
							AuditLog.addAuditLog(
									AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("RETURN TO CA",
											AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL)),
									"User " + username + " Returned to Collateral Appraiser.", username, new Date(),
									AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL);
						}
						flag = "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String saveOrUpdateAppraisalreportmain(String appraisalreportid, Date appraisalDate,
			Boolean startReportFlag) {
		String flag = "failed";
		try {
			if (appraisalreportid != null) {
				params.put("appraisalreportid", appraisalreportid);
				Tbappraisalreportmain row = (Tbappraisalreportmain) dbServiceLOS.executeUniqueHQLQueryMaxResultOne(
						"FROM Tbappraisalreportmain WHERE appraisalreportid=:appraisalreportid", params);
				if (row != null) {
					row.setAppraisaldate(appraisalDate);
					// start report flag
					if (startReportFlag != null && startReportFlag) {
						row.setReportdate(new Date());
					}
					if (dbServiceLOS.saveOrUpdate(row)) {
						AuditLog.addAuditLog(
								AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("SAVE CA REPORT",
										AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL)),
								"User " + username + " Saved CA Report.", username, new Date(),
								AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL);
						flag = "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Tbappraisalmachine getAppMachineriesByRepId(String appreportid) {
		Tbappraisalmachine row = new Tbappraisalmachine();
		try {
			if (appreportid != null) {
				params.put("reportid", appreportid);
				row = (Tbappraisalmachine) dbServiceLOS.executeUniqueHQLQuery(
						"FROM Tbappraisalmachine WHERE " + "appraisalreportid=:reportid", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	@Override
	public String updateColInstStatus(Integer id) {
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (id != null) {
				params.put("id", id);
				Integer res = dbServiceLOS.executeUpdate(
						// "UPDATE Tbcolinvestigationinst SET
						// status='On-Process' WHERE id=:id",
						"UPDATE Tbcolinvestigationinst SET status='2' WHERE id=:id", params);
				if (res == 1) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String generateAppraisalTableAuto(String refno, String apprisaltype, String appraisalreportid,
			String appno) {
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (refno != null && apprisaltype != null && apprisaltype.equals("2") && appraisalreportid != null
					&& appno != null) { // Table Appraisal
				params.put("refno", refno);
				Tbcollateralauto c = (Tbcollateralauto) dbServiceLOS
						.executeUniqueHQLQueryMaxResultOne("FROM Tbcollateralauto WHERE referenceno=:refno", params);
				if (c != null) {
					Tbappraisalauto a = new Tbappraisalauto();
					// a.setReferenceno(c.getReferenceno());
					a.setAppraisalreportid(appraisalreportid);
					a.setAppno(appno);
					a.setCollateralid(c.getCollateralid());
					a.setMake(c.getMake());
					a.setModeldetails(c.getModeldetails());
					a.setMvfileno(c.getMvfileno());
					a.setYear(c.getYear());
					a.setPlateno(c.getPlateno());
					a.setColor(c.getColor());
					a.setLtooffice(c.getLtooffice());
					a.setOrno(c.getOrnumber());
					a.setAccessories(c.getAccessories());
					a.setTirecondition(c.getTirecondition());
					a.setRegisteredto(c.getRegisteredowner());
					a.setFuel(c.getFuel());
					a.setEngineno(c.getEngineno());
					a.setLocation(c.getLocation());
					a.setRemarks(c.getRemarks());
					a.setDatepurchased(c.getDatepurchased());
					if (dbServiceLOS.save(a)) {
						flag = "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String generateAppraisalTableRel(String refno, String apprisaltype, String appraisalreportid, String appno) {
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (refno != null && apprisaltype != null && apprisaltype.equals("2") && appraisalreportid != null
					&& appno != null) {
				params.put("refno", refno);
				Tbcollateralrel c = (Tbcollateralrel) dbServiceLOS
						.executeUniqueHQLQueryMaxResultOne("FROM Tbcollateralrel WHERE referenceno=:refno", params);
				if (c != null) {
					Tbappraisalrel a = new Tbappraisalrel();
					a.setReferenceno(c.getReferenceno());
					a.setAppraisalreportid(appraisalreportid);
					a.setAppno(appno);
					a.setCollateralid(c.getCollateralid());
					a.setTypeappraisal(apprisaltype);
					a.setPropertytype(c.getPropertytype());
					a.setUnitno(c.getUnitno());
					a.setLandlotno(c.getLotno());
					a.setTechblkno(c.getBlockno());
					if (dbServiceLOS.save(a)) {
						flag = "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String saveAndSubmitRequest(Boolean flag, String colappraisalrequestid) {
		String res = "failed";
		try {
			// System.out.println("-------- colappraisalrequestid : " +
			// colappraisalrequestid);
			if ((flag != null && flag == true) && colappraisalrequestid != null) {
				params.put("id", colappraisalrequestid);
				Tbcolappraisalrequest row = (Tbcolappraisalrequest) dbServiceLOS
						.executeUniqueHQLQuery("FROM Tbcolappraisalrequest WHERE colappraisalrequestid=:id", params);
				if (row != null) {
					params.put("refno", row.getReferenceno());
					dbServiceLOS.executeUpdate(
							"UPDATE Tbcolinvestigationinst SET status = '1' WHERE referenceno=:refno", params); // Instruction
																												// Opened
				}
				Integer r = dbServiceLOS
						.executeUpdate("DELETE FROM TBCOLAPPRAISALREQUEST WHERE colappraisalrequestid=:id", params);
				if (r != null && r == 1) {
					String yearCheck = DateTimeUtil.yearCheck();
					params.put("year", Integer.valueOf(yearCheck));
					Tbsequence sequence = (Tbsequence) dbServiceLOS
							.executeUniqueHQLQuery("FROM Tbsequence WHERE year=:year AND product='CA'", params);
					sequence.setSequence(sequence.getSequence() - 1);
					if (dbServiceLOS.saveOrUpdate(sequence)) {
						res = "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public String checkIfExisting(String refno) {
		DBService dbServiceCOOP = new DBServiceImpl();
		String flag = "existing";
		try {
			params.put("refno", refno.trim());
			Integer list = (Integer) dbServiceCOOP
					.executeUniqueSQLQuery("SELECT COUNT(*) FROM Tbcollateralmain WHERE referenceno=:refno", params);
			if (list == null || list == 0) {
				return flag = "new";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public DedupeCollateralForm dedupeTbcollateralmain(String referenceno) {
		DedupeCollateralForm rForm = new DedupeCollateralForm();
		rForm.setFlag("failed");
		Tbcollateralmain form = new Tbcollateralmain();
		try {
			if (referenceno != null) {
				params.put("refno", referenceno);
				Integer collateralmain = (Integer) dbServiceLOS.executeUniqueSQLQuery(
						"SELECT COUNT(*) FROM TBCOLLATERALMAIN WHERE referenceno=:refno", params);
				String q = "";
				if (collateralmain != null && collateralmain == 1) {
					q = "SELECT (SELECT desc1 FROM TBCODETABLE WHERE codename='COLLATERALTYPE' AND codevalue=a.collateraltype) as collateraltype, "
							+ "(SELECT desc1 FROM TBCODETABLE WHERE codename='COLLATERALSTATUS' AND codevalue=a.collateralstatus) as collateralstatus, "
							+ "(SELECT desc1 FROM TBCODETABLE WHERE codename='APPRAISALSTATUS' AND codevalue=a.appraisalstatus) as appraisalstatus, "
							+ "* FROM TBCOLLATERALMAIN a WHERE a.referenceno=:refno";
					form = (Tbcollateralmain) dbServiceLOS.execSQLQueryTransformer(q, params, Tbcollateralmain.class,
							0);
					form.setEncodedby(UserUtil.getUserFullname(form.getEncodedby()));
					form.setUpdatedby(UserUtil.getUserFullname(form.getUpdatedby()));
					rForm.setCollateralmain(form);
					rForm.setFlag("success");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rForm;
	}

	@Override
	public CollateralTables getCollateralbyCollateralTypeAndRefNo(String collateraltype, String referenceno) {
		CollateralTables row = new CollateralTables();
		try {
			if (collateraltype != null && referenceno != null) {
				params.put("refno", referenceno);
				// MAIN
				row.setMain((Tbcollateralmain) dbServiceLOS
						.executeUniqueHQLQueryMaxResultOne("FROM Tbcollateralmain WHERE referenceno=:refno", params));
				if (collateraltype.equals("1")) { // VEHICLE
					row.setAuto((Tbcollateralauto) dbServiceLOS.executeUniqueHQLQueryMaxResultOne(
							"FROM Tbcollateralauto WHERE referenceno=:refno", params));
				} else if (collateraltype.equals("2")) { // REAL ESTATE
					row.setRel((Tbcollateralrel) dbServiceLOS.executeUniqueHQLQueryMaxResultOne(
							"FROM Tbcollateralrel WHERE referenceno=:refno", params));
				} else if (collateraltype.equals("3")) { // HOLD OUT DEPOSIT
					row.setDeposits((Tbcollateraldeposits) dbServiceLOS.executeUniqueHQLQueryMaxResultOne(
							"FROM Tbcollateraldeposits WHERE referenceno=:refno", params));
				} else if (collateraltype.equals("4")) { // MACHINERIES
					row.setMachineries((Tbcollateralmachineries) dbServiceLOS.executeUniqueHQLQueryMaxResultOne(
							"FROM Tbcollateralmachineries WHERE referenceno=:refno", params));
				} else if (collateraltype.equals("5")) { // LIVESTOCK
					row.setLivestock((Tbcollaterallivestock) dbServiceLOS.executeUniqueHQLQueryMaxResultOne(
							"FROM Tbcollaterallivestock WHERE referenceno=:refno", params));
					// row.setSecurities((Tbcollateralsecurities)
					// dbServiceLOS.executeUniqueHQLQueryMaxResultOne(
					// "FROM Tbcollateralsecurities WHERE referenceno=:refno",
					// params));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public CollateralTables getCollateralDetails(String colrefno, String coltype) {
		Map<String, Object> params = HQLUtil.getMap();
		CollateralTables form = new CollateralTables();
		List<Tbcollateralauto> auto = new ArrayList<Tbcollateralauto>();
		List<Tbcollateralrel> rel = new ArrayList<Tbcollateralrel>();
		try {
			params.put("colrefno", colrefno);
			if (coltype.equals("1")) {
				// auto = (List<Tbcollateralauto>) dbServiceLOS
				// .executeListHQLQuery("FROM Tbcollateralauto WHERE
				// referenceno=:colrefno", params);
				form.setColauto((List<Tbcollateralauto>) dbServiceLOS
						.executeListHQLQuery("FROM Tbcollateralauto WHERE referenceno=:colrefno", params));
			}
			if (coltype.equals("2")) {
				form.setColrel((List<Tbcollateralrel>) dbServiceLOS
						.executeListHQLQuery("FROM Tbcollateralrel WHERE referenceno=:colrefno", params));
				// rel = (List<Tbcollateralrel>) dbServiceLOS
				// .executeListHQLQuery("FROM Tbcollateralrel WHERE
				// referenceno=:colrefno", params);
				// form.setColrel(rel);
			}
			// if(coltype == "3"){
			// params.put("colrefno", colrefno);
			// list =
			// (List<CollateralTables>)dbServiceLOS.executeListHQLQuery("FROM
			// Tbcollateraldeposits where referenceno=:colrefno", params);
			// }
			if (coltype == "4") {
				// form.setColrel((List<Tbcollateralrel>) dbServiceLOS
				// .executeListHQLQuery("FROM Tbcollateralrel WHERE
				// referenceno=:colrefno", params));
			}
			// if(coltype == "5"){
			// params.put("colrefno", colrefno);
			// list =
			// (List<CollateralTables>)dbServiceLOS.executeListHQLQuery("FROM
			// Tbcollateralsecurities where referenceno=:colrefno", params);
			// }
			// form.setColauto(auto);
			// form.setColrel(rel);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return form;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbappraisalreportmain> getCAReportByRequestId(String requestid) {
		// TODO Auto-generated method stub
		Map<String, Object> params = HQLUtil.getMap();
		List<Tbappraisalreportmain> list = new ArrayList<Tbappraisalreportmain>();
		StringBuilder hql = new StringBuilder();
		try {
			params.put("requestid", requestid);
			if (requestid != null) {
				hql.append(
						"SELECT a.appraisalreportid,a.colappraisalrequestid,a.appno,(SELECT companyname FROM TBCOMPANY WHERE companycode=a.companycode) as companycode, ");
				hql.append(
						"(SELECT fullname FROM TBUSER WHERE username=a.reportedby) as reportedby, (SELECT desc1 FROM TBCODETABLE WHERE codename='REPORTSTATUS' AND codevalue=a.status )as status, a.reasonforappraisal ");
				hql.append("FROM Tbappraisalreportmain a WHERE a.colappraisalrequestid=:requestid");

				list = (List<Tbappraisalreportmain>) dbServiceLOS.execSQLQueryTransformer(hql.toString(), params,
						Tbappraisalreportmain.class, 1);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Tbappraisalreportmain getCaReportMain(String appreportid) {
		// TODO Auto-generated method stub
		Map<String, Object> params = HQLUtil.getMap();
		Tbappraisalreportmain report = new Tbappraisalreportmain();
		try {
			if (appreportid != null) {
				params.put("reportid", appreportid);
				report = (Tbappraisalreportmain) dbServiceLOS.executeUniqueHQLQuery(
						"FROM Tbappraisalreportmain WHERE " + "appraisalreportid=:reportid", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return report;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcolappraisalrequest> getListCArequest(String appNo, String reqId, String refNo, String reqStat,
			String colType, String appType, Integer page, Integer maxResult, Boolean viewflag, String colCat,
			String assgndUser, Boolean isViewRequest) {
		List<Tbcolappraisalrequest> list = new ArrayList<Tbcolappraisalrequest>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			StringBuilder hql = new StringBuilder();

			if (appNo == null && reqId == null && refNo == null && reqStat == null && colType == null && appType == null
					&& colCat == null) {
			} else {
				hql.append(
						"SELECT a.colappraisalrequestid, a.appno, a.referenceno ,(SELECT desc1 from Tbcodetable where codename='REQUESTSTATUS' "
								+ "AND codevalue=a.status) as status, (SELECT desc1 from Tbcodetable where codename='TYPEAPPRAISAL' "
								+ "and codevalue=a.typeappraisal) as typeappraisal, a.remarks , ");
				hql.append(
						"(SELECT desc1 from Tbcodetable WHERE codename='PROPERTYTYPE' AND codevalue=a.propertytype)as propertytype, ");
				hql.append(
						"(SELECT desc1 from Tbcodetable WHERE codename='COLLATERALTYPE' AND codevalue=a.collateraltype)as collateraltype "
								+ ",(SELECT desc1 FROM Tbcodetable WHERE codename='NEWUSED' AND codevalue=(SELECT neworused FROM TBCOLLATERALAUTO WHERE referenceno=a.referenceno)) as newused, "
								+ "(SELECT fullname FROM Tbuser WHERE username=a.assignedappraiser) as assignedappraiser");
				hql.append(" FROM Tbcolappraisalrequest a WHERE ");
				if ((colCat != null && (colCat.equals("S") || colCat.equals("G")))
						&& (viewflag == null || viewflag == false)) {
					hql.append(" a.collateralcategory like '%" + colCat + "%'");
				} else {
					hql.append(" a.collateralcategory IN ('S','G')");
				}
				if (reqId != null) {
					hql.append(" AND a.colappraisalrequestid like '%" + reqId + "%'");
				}
				if (viewflag == null || viewflag == false) {
					hql.append(" AND a.appno IS NULL");
				} else {
					if (appNo != null) {
						hql.append(" AND appno like '%" + appNo + "%'");
					}
					if (assgndUser != null && isViewRequest == null) {
						hql.append(" AND ((requestedby='" + assgndUser + "' AND status='0') OR ((assignedappraiser='"
								+ assgndUser + "') AND status IN ('0','1','6')) OR (assignedappsupervisor='"
								+ assgndUser + "' AND status NOT IN ('5')))");// Completed
					}
				}
				if (refNo != null) {
					hql.append(" AND a.referenceno = '" + refNo + "'");
				}
				if (reqStat != null) {
					hql.append(" AND a.status like '%" + reqStat + "%'");
				}
				if (colType != null) {
					hql.append(" AND a.collateraltype like '%" + colType + "%'");
				}
				if (appType != null) {
					hql.append(" AND a.typeappraisal like '%" + appType + "%'");
				}
				list = (List<Tbcolappraisalrequest>) dbServiceLOS.execSQLQueryTransformerListPagination(hql.toString(),
						null, Tbcolappraisalrequest.class, page == null ? 1 : page, maxResult);
				if (list != null && !list.isEmpty()) {
					for (Tbcolappraisalrequest col : list) {
						if (col.getStatus() != null) {
							params.put("reqstat", col.getStatus());
							Tbcodetable code = (Tbcodetable) dbServiceLOS.executeUniqueHQLQuery(
									"FROM Tbcodetable WHERE " + "codename='REQUESTSTATUS' AND codevalue=:reqstat",
									params);
							if (code != null) {
								col.setStatus(code.getDesc1());
							}
						}
					}
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		AuditLog.addAuditLog(
				AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("VIEW ALL COLLATERAL REQUEST",
						AuditLogEvents.LOAN_APPLICATION_GENERATE_INSTRUCTION_SHEET)),
				"User " + username + " Viewed All Collateral Request.", username, new Date(),
				AuditLogEvents.LOAN_APPLICATION_GENERATE_INSTRUCTION_SHEET);
		return list;
	}

	@Override
	public Tbcolappraisalrequest getDataByRequestId(String colappid) {
		// TODO Auto-generated method stub
		Map<String, Object> params = HQLUtil.getMap();
		Tbcolappraisalrequest req = new Tbcolappraisalrequest();
		try {
			params.put("reqid", colappid);
			req = (Tbcolappraisalrequest) dbServiceLOS.executeUniqueHQLQuery(
					"FROM Tbcolappraisalrequest WHERE " + "colappraisalrequestid=:reqid", params);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return req;
	}

	@Override
	public String getaddorUpdateCARequest(Tbcolappraisalrequest request, String status) {
		// TODO Auto-generated method stub
		/*
		 * Simplified - Daniel Fesalbon, Dec 17, 2018 (String) Return Values : "success"
		 * = Request saved or updated having Request ID, "redID(dataValue)" = not saved,
		 * just request ID being generated, "failed" = execution failed
		 */
		String dataValue = "failed";
		Map<String, Object> param = HQLUtil.getMap();
		Tbcolappraisalrequest r = null;
		try {
			// if (status == null) {
			// return flagOrRequestId;
			// }
			if (request.getColappraisalrequestid() != null) {
				param.put("colappraisalreqid", request.getColappraisalrequestid());
				r = (Tbcolappraisalrequest) dbServiceLOS.executeUniqueHQLQuery(
						"FROM Tbcolappraisalrequest WHERE " + "colappraisalrequestid=:colappraisalreqid", param);
				if (r != null) {
					r.setRemarks(request.getRemarks());
					r.setPurposeofappraisal(request.getPurposeofappraisal());
					r.setCollateraltype(request.getCollateraltype());
					r.setCollateralcategory("S");// Defaul single only
					r.setReferenceno(request.getReferenceno());
					r.setStatus(status);
					// r.setAppno(request.getAppno()); if existing do not remove
					// app no 10.10.18
					r.setTypeappraisal(request.getTypeappraisal());
					r.setCompanycode(request.getCompanycode());
					r.setAssignedappraiser(request.getAssignedappraiser());
					r.setRequestedby(request.getRequestedby());
					r.setDaterequested(new Date());
					r.setGroupid(request.getGroupid());
					r.setNewused(request.getNewused());
					r.setPropertytype(request.getPropertytype());
					r.setCollateralcategory("S");
					if (r.getCompanycode() != null) {
						DefaultUsers d = new DefaultUsers(request.getCompanycode());
						if (r.getAssignedappraiser() != null
								&& r.getAssignedappraiser().equals(d.getAppraisalsupervisor())) {
							r.setAssignedappraiser(d.getAppraisalsupervisor());
							r.setAssignedappsupervisor(d.getAppraisalsupervisor());
						}
					}
					if (dbServiceLOS.saveOrUpdate(r)) {
						dataValue = "success";
					}
				} else {
					// return flagOrRequestId;
					r = new Tbcolappraisalrequest();
					// Set Colappraisalrequestid -- Generate request ID
					r.setColappraisalrequestid(request.getColappraisalrequestid());
					// Set Other Details
					r.setAppno(request.getAppno());
					r.setCollateralcategory(request.getCollateralcategory());
					r.setReferenceno(request.getReferenceno());
					r.setCollateraltype(request.getCollateraltype());
					// CODETABLE 'REQUEST STATUS' 1 = NEW , 0 = DRAFT
					r.setStatus("0");
					r.setTypeappraisal(request.getTypeappraisal());
					r.setCompanycode(request.getCompanycode());
					r.setRemarks(request.getRemarks());
					r.setPurposeofappraisal(request.getPurposeofappraisal());
					r.setDaterequested(new Date());
					r.setRequestedby(UserUtil.securityService.getUserName());
					r.setGroupid(request.getGroupid());
					r.setNewused(request.getNewused());
					r.setAssignedappraiser(request.getAssignedappraiser());
					r.setPropertytype(request.getPropertytype());
					r.setAssignedappraiser(request.getAssignedappraiser());
					if (request.getCompanycode() != null) {
						DefaultUsers d = new DefaultUsers(request.getCompanycode());
						r.setAssignedappsupervisor(d.getAppraisalsupervisor());
					} else {
						r.setAssignedappsupervisor(request.getAssignedappsupervisor());
					}
					if (dbServiceLOS.save(r)) {
						// flagOrRequestId = r.getColappraisalrequestid();
						dataValue = "success";
					} else {
						return dataValue;
					}
				}
				// if (dbServiceLOS.saveOrUpdate(r)) {
				//// flagOrRequestId = r.getColappraisalrequestid();
				// flagOrRequestId = "success";
				// }
			} else {
				return ApplicationNoGenerator.generateRequestID("CA");
				// Draft
				/*
				 * r = new Tbcolappraisalrequest(); // Set Colappraisalrequestid -- Generate
				 * request ID r.setColappraisalrequestid(ApplicationNoGenerator.
				 * generateRequestID("CA")); // Set Other Details
				 * r.setAppno(request.getAppno());
				 * r.setCollateralcategory(request.getCollateralcategory());
				 * r.setReferenceno(request.getReferenceno());
				 * r.setCollateraltype(request.getCollateraltype()); // CODETABLE 'REQUEST
				 * STATUS' 1 = NEW , 0 = DRAFT r.setStatus(status);
				 * r.setTypeappraisal(request.getTypeappraisal());
				 * r.setCompanycode(request.getCompanycode());
				 * r.setRemarks(request.getRemarks());
				 * r.setPurposeofappraisal(request.getPurposeofappraisal());
				 * r.setDaterequested(new Date());
				 * r.setRequestedby(UserUtil.securityService.getUserName());
				 * r.setGroupid(request.getGroupid()); r.setNewused(request.getNewused());
				 * r.setAssignedappraiser(request.getAssignedappraiser());
				 * r.setPropertytype(request.getPropertytype()); if (request.getCompanycode() !=
				 * null) { DefaultUsers d = new DefaultUsers(request.getCompanycode());
				 * r.setAssignedappraiser(d.getAppraisalsupervisor());
				 * r.setAssignedappsupervisor(d.getAppraisalsupervisor()); } else {
				 * r.setAssignedappsupervisor(request.getAssignedappsupervisor() ); } if
				 * (dbServiceLOS.save(r)) { flagOrRequestId = r.getColappraisalrequestid(); //
				 * flagOrRequestId = "success"; } else { return flagOrRequestId; }
				 */
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataValue;
	}

	@Override
	public Tbcollateralauto getAutoDetailsByReference(String reference) {
		// TODO Auto-generated method stub
		try {
			params.put("referenceno", reference);
			Tbcollateralauto auto = (Tbcollateralauto) dbServiceLOS
					.executeUniqueHQLQuery("FROM Tbcollateralauto WHERE referenceno=:referenceno", params);
			if (auto != null) {
				return auto;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ReturnForm updateReportStatus(String reportid, String status) {
		// TODO Auto-generated method stub
		/* Daniel Fesalbon - December 19, 2018 */
		ReturnForm form = new ReturnForm();
		form.setFlag("failed");
		params.put("status", status);
		params.put("reportid", reportid);
		try {
			if (reportid != null && status != null) {
				Integer i = (Integer) dbServiceLOS
						.executeUpdate("UPDATE Tbappraisalreportmain SET status =:reportid, statusdatetime=GETDATE() "
								+ " WHERE appraisalreportid=:reportid", null);
				if (i != null || i > 0) {
					form.setFlag("success");
					if (status.equals("2")) {
						/* 2 = For Review */
						form.setMessage("<b>" + reportid + "</b> successfully sent for review.");
					}
					if (status.equals("3")) {
						/* 3 = Reviewed */
						form.setMessage("<b>" + reportid + "</b> successfully tagged as reviewed.");

					}
					if (status.equals("4")) {
						/* 4 = Returned */
						form.setMessage("<b>" + reportid + "</b> has been returned.");
					}
				}
			} else {
				form.setMessage("Parameter failure.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public ReturnForm saveOrUpdateAppraisal(AppraisalForm appraisals, String collateraltype) {
		// TODO Auto-generated method stub
		/* Daniel Fesalbon - December 19, 2018 */
		ReturnForm form = new ReturnForm();
		form.setFlag("failed");
		try {
			if (collateraltype != null) {
				if (collateraltype.equals("1")) {
					/* 1 = Vehicle */
					if (dbServiceLOS.saveOrUpdate(appraisals.getAuto())) {
						form.setFlag("success");
						form.setMessage("Vehicle Appraisal details saved.");
					}
				}
				if (collateraltype.equals("2")) {
					/* 2 = Real Estate */
					if (dbServiceLOS.saveOrUpdate(appraisals.getRealestate())) {
						form.setFlag("success");
						form.setMessage("Real Estate Appraisal details saved.");
					}
				}
				if (collateraltype.equals("3")) {
					/* 3 = Hold Out Deposit */
					if (dbServiceLOS.saveOrUpdate(appraisals.getHoldoutdeposits())) {
						form.setFlag("success");
						form.setMessage("Hold Out Deposit Appraisal details saved.");
					}
				}
				if (collateraltype.equals("4")) {
					/* 4 = Machineries */
					if (dbServiceLOS.saveOrUpdate(appraisals.getMachineries())) {
						form.setFlag("success");
						form.setMessage("Machinery Appraisal details saved.");
					}
				}
				if (collateraltype.equals("5")) {
					/* 5 = Livestock */
					if (dbServiceLOS.saveOrUpdate(appraisals.getLivestock())) {
						form.setFlag("success");
						form.setMessage("Livestock Appraisal details saved.");
					}
				}
			} else {
				form.setMessage("Parameter failure.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public AppraisalForm getAppraisalDetailsViaID(String reportid, String requestid) {
		// TODO Auto-generated method stub
		/* Daniel Fesalbon - December 19, 2018 */
		AppraisalForm appr = new AppraisalForm();
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			params.put("reportid", reportid);
			params.put("requestid", requestid);
			if (reportid != null) {
				Tbappraisalreportmain main = (Tbappraisalreportmain) dbServiceLOS
						.executeUniqueHQLQuery("FROM Tbappraisalreportmain WHERE appraisalreportid=:reportid", params);
				if (main != null) {
					params.put("refno", main.getReferenceno());
					if (main.getCollateraltype().equals("1")) {
						/* Vehicle */
						Tbappraisalauto auto = (Tbappraisalauto) dbServiceLOS.executeUniqueHQLQuery(
								"FROM Tbappraisalauto WHERE appraisalreportid=:reportid ", params);
						if (auto != null) {
							appr.setAuto(auto);
						} else {
							Tbcollateralauto a = (Tbcollateralauto) dbServiceLOS
									.executeUniqueHQLQuery("FROM Tbcollateralauto WHERE referenceno=:refno", params);
							String value = mapper.writeValueAsString(a);
							Tbappraisalauto appauto = mapper.readValue(value, Tbappraisalauto.class);
							appauto.setModelseries(a.getModel());
							appauto.setVehiclecategory(a.getVehicletype());
							appauto.setChassisno(a.getReferenceno());
							appr.setAuto(appauto);
//							a.setKmreading(autoc.getKmreading());
						}
					}
					if (main.getCollateraltype().equals("2")) {
						/* Real Estate */
						appr.setRealestate((Tbappraisalrel) dbServiceLOS.executeUniqueHQLQuery(
								"FROM Tbappraisalrel WHERE appraisalreportid=:reportid ", params));
					}
					if (main.getCollateraltype().equals("3")) {
						/* Hold Out Deposit */
						appr.setHoldoutdeposits((Tbappraisaldeposits) dbServiceLOS.executeUniqueHQLQuery(
								"FROM Tbappraisaldeposits WHERE appraisalreportid=:reportid ", params));
					}
					if (main.getCollateraltype().equals("4")) {
						/* Machineries */
						Tbappraisalmachine machine = (Tbappraisalmachine) dbServiceLOS.executeUniqueHQLQuery(
								"FROM Tbappraisalmachine WHERE appraisalreportid=:reportid ", params);
						if (machine != null) {
							appr.setMachineries(machine);
						} else {
							Tbcollateralmachineries m = (Tbcollateralmachineries) dbServiceLOS.executeUniqueHQLQuery(
									"FROM Tbcollateralmachineries WHERE referenceno=:refno", params);
							String value = mapper.writeValueAsString(m);
							appr.setMachineries(mapper.readValue(value, Tbappraisalmachine.class));
						}
					}
					if (main.getCollateraltype().equals("5")) {
						/* Livestock */
						Tbappraisallivestock livestock = (Tbappraisallivestock) dbServiceLOS.executeUniqueHQLQuery(
								"FROM Tbappraisallivestock WHERE appraisalreportid=:reportid ", params);
						if (livestock != null) {
							appr.setLivestock(livestock);
						} else {
							Tbcollaterallivestock l = (Tbcollaterallivestock) dbServiceLOS.executeUniqueHQLQuery(
									"FROM Tbcollaterallivestock WHERE referenceno=:refno", params);
							String value = mapper.writeValueAsString(l);
							appr.setLivestock(mapper.readValue(value, Tbappraisallivestock.class));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appr;
	}

	@Override
	public AppraisalAccessRights AppraisalButtonAccess(String reportid) {
		// TODO Auto-generated method stub
		/* Daniel Fesalbon - December 19, 2018 */
		AppraisalAccessRights access = new AppraisalAccessRights();
		try {
			params.put("reportid", reportid);
			Tbappraisalreportmain report = (Tbappraisalreportmain) dbServiceLOS
					.executeUniqueHQLQuery("FROM Tbappraisalreportmain WHERE appraisalreportid=:reportid", params);
			if (report != null) {
				params.put("requestid", report.getColappraisalrequestid());
				Tbcolappraisalrequest request = (Tbcolappraisalrequest) dbServiceLOS.executeUniqueHQLQuery(
						"FROM Tbcolappraisalrequest WHERE colappraisalrequestid=:requestid", params);
				if (request != null) {
					if (report.getStatus().equals("0")) {
						/* New */
						if (request.getAssignedappraiser().equals(secservice.getUserName())) {
							/* Assigned Appraiser can access start report */
							access.setShowStartReport(true);
						}
					}
					if (report.getStatus().equals("1") || report.getStatus().equals("4")) {
						/* On Going */
						if (request.getAssignedappraiser().equals(secservice.getUserName())) {
							/* Save and Submit button */
							access.setShowSaveReport(true);
							access.setShowSubmitForReview(true);
							access.setReadOnly(false);
						}
					}
					if (report.getStatus().equals("2")) {
						/* For Review */
						if (request.getAssignedappsupervisor().equals(secservice.getUserName())) {
							access.setShowDoneReview(true);
							access.setShowReturn(true);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return access;
	}

	@Override
	public Tbappraisaldeposits getAppDepositsByRepId(String appreportid) {
		Map<String, Object> params = HQLUtil.getMap();
		Tbappraisaldeposits row = new Tbappraisaldeposits();
		try {
			if (appreportid != null) {
				params.put("reportid", appreportid);
				row = (Tbappraisaldeposits) dbServiceLOS.executeUniqueHQLQuery(
						"FROM Tbappraisaldeposits WHERE " + "appraisalreportid=:reportid", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	@Override
	public String addOrupdateAppDeposit(Tbappraisaldeposits dep) {
		// TODO Auto-generated method stub
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("reportid", dep.getAppraisalreportid());
			Tbappraisaldeposits depo = (Tbappraisaldeposits) dbServiceLOS
					.executeUniqueHQLQuery("FROM Tbappraisaldeposits WHERE appraisalreportid=:reportid", params);

			if (depo != null) {
//				System.out.println("Tbappraisaldeposits not null");
				dep.setAppraisalreportid(depo.getAppraisalreportid());
				if (dbServiceLOS.saveOrUpdate(dep)) {
					flag = "success";
//					System.out.println("appraisal deposit updated");
				}
			} else {
//				System.out.println("Tbappraisaldeposits is null");
				if (dbServiceLOS.save(dep)) {
					flag = "success";
//					System.out.println("appraisal deposit created");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String saveOrUpdateApprMainMarketValueDetails(Tbappraisalreportmain values) {
		// TODO Auto-generated method stub
		try {
			params.put("id", values.getAppraisalreportid());
			if (values.getAppraisalreportid() != null) {
				Tbappraisalreportmain a = (Tbappraisalreportmain) dbServiceLOS
						.executeUniqueHQLQuery("FROM Tbappraisalreportmain WHERE appraisalreportid=:id", params);
				if (a != null) {
					a.setMarketvalue(values.getMarketvalue());
					a.setLoanablevalue(values.getLoanablevalue());
					a.setLoanablevalpercent(values.getLoanablevalpercent());
					a.setAppraisedvalue(values.getAppraisedvalue());
					a.setAppvalpercent(values.getAppvalpercent());
					if (dbServiceLOS.saveOrUpdate(a))
						return "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return "failed";
	}

	public Boolean saveAppraisalValues(Tbappraisalreportmain main) {
		try {
			if (main != null) {
				params.put("id", main.getReferenceno());
				Tbloancollateral l = (Tbloancollateral) dbServiceLOS
						.executeUniqueHQLQuery("FROM Tbloancollateral WHERE collateralreferenceno=:id", params);
				if (l != null) {
					l.setMarketvalue(main.getMarketvalue());
					l.setLoanablevalue(main.getLoanablevalue());
					l.setAppraisedvalue(main.getAppraisedvalue());
					l.setLastappraisaldate(main.getAppraisaldate());
					return dbServiceLOS.saveOrUpdate(l);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CollateralLoanableForm> listLoanCollateral(String appno, String acctno){
		List<CollateralLoanableForm> form = new ArrayList<CollateralLoanableForm>();
		params.put("appno", appno);
		params.put("acctno", acctno);
		String q = "SELECT collateralid,collateralreferenceno,collateraltype,appraisedvalue,0.7 as loanablepercentage,loanablevalue,lastappraisaldate "
				+ "FROM Tbloancollateral WHERE ";
		q += appno != null && !appno.equals("")?"appno =:appno ":"";
		q += appno != null && !appno.equals("") && acctno != null && !acctno.equals("")?" AND acctno =:acctno"
				:acctno != null && !acctno.equals("")?"acctno =:acctno":"";
		try {
			form = dbServiceLOS.executeListSQLQuery(q, params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return form;
	}
	//MAR 11-03-2020
	@Override
	public String addSingleCollateralToGrp(Tbcollateralpergroup data) {
		String flag = "failed";
		try {
			if(data.getGroupid()!=null && data.getCollateralreferenceno()!=null){
				params.put("grpID", data.getGroupid().trim());
				params.put("singleRefno", data.getCollateralreferenceno().trim());
				Tbcollateralpergroup row = (Tbcollateralpergroup) dbServiceLOS.executeUniqueHQLQueryMaxResultOne
						("FROM Tbcollateralpergroup WHERE groupid=:grpID AND collateralreferenceno=:singleRefno", params);
				if(row==null){
					Tbcollateralpergroup n = new Tbcollateralpergroup();
					n.setGroupid(data.getGroupid());
					//n.setGroupname(data.getGroupname());
					n.setCollateralreferenceno(data.getCollateralreferenceno());
					n.setCollateraltype(data.getCollateraltype());
					if (dbServiceLOS.save(n)) {
						flag = "success";
					}
				}else{
					flag = "existing";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	@SuppressWarnings("unused")
	@Override
	public String useCollateralGroup(String grpID, String appno, String grpType, String grpName) {
		String flag = "failed";
		try {
			if(appno!=null && grpID!=null && grpType!=null){
				params.put("a", appno);
				params.put("id", grpID);
				String gcolid = ApplicationNoGenerator.generateCollateralID("COL");
				params.put("id", gcolid);
				Integer row = (Integer) dbServiceLOS.executeUniqueSQLQuery("SELECT COUNT(*) FROM Tbloancollateralgroup WHERE groupid=:id AND appno=:a AND colid=:id", params);
				if(row!=null && row > 0){
					return flag = "existing";
				}else{
					String q = "INSERT INTO Tbloancollateralgroup (groupid, appno, grouptype,colid,groupname) VALUES ('"+grpID+"','"+appno+"','"+grpType+"','"+gcolid+"','"+grpName+"')";
					Integer res = dbServiceLOS.executeUpdate(q, null);
					if(res!=null && res > 0){
						String q2 = "UPDATE TBCOLLATERALGROUPMAIN  SET groupstatus='1' WHERE groupid ='"+grpID+"'";
						Integer res2 = dbServiceLOS.executeUpdate(q2, null);
						if(res!=null && res > 0){
							flag = "success";
						}
					}					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	@Override
	public String removeGroupCollateralToLoan(String appno, String grpID) {
		String flag = "failed";
		DBService dbServiceCOOP = new DBServiceImpl();
		try {	
			if(appno!=null&&grpID!=null){
				String q = "DELETE FROM Tbloancollateralgroup WHERE appno='"+appno+"' AND groupid='"+grpID+"'";
				Integer res = dbServiceCOOP.executeUpdate(q, null);
				if(res!=null && res ==1){
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	@Override
	public String saveOrupdateCollateralGrpMain(String saveOrupdate, Tbcollateralgroupmain grpmain) {
		String flag = "failed";
		try {
			if(grpmain.getGroupname()!=null){
				if(saveOrupdate!=null && saveOrupdate.equals("ADD")){
					// check if group name is existing 
					params.put("grpname", grpmain.getGroupname().trim());
					Integer row = (Integer) dbServiceLOS.executeUniqueSQLQuery("SELECT COUNT(*) FROM Tbcollateralgroupmain WHERE groupname=:grpname", params);
					if(row!=null && row > 0){
						return flag = "existing";
					}else{
						// create new record
						String id = generateCollateralGrpID(); // called method generateCollateralGrpID
						grpmain.setGroupid(id);
						if(grpmain.getGrouptype()!=null && grpmain.getGroupid()!=null){
							Tbcollateralgroupmain grp = new Tbcollateralgroupmain();
							grp.setGrouptype(grpmain.getGrouptype());
							grp.setGroupname(grpmain.getGroupname());
							grp.setGroupid(grpmain.getGroupid());
							grp.setGroupstatus("0"); // In-Active
							grp.setDatecreated(new Date());
							grp.setCreatedby(secservice.getUserName());
							if(dbServiceLOS.save(grp)){
								//System.out.println(">>> SUCCESS SAVING COLLATERAL - Group Main");	
								flag = "success";
							}
						}	
					}
				}else if(saveOrupdate!=null && saveOrupdate.equals("UPDATE")){
					if(grpmain.getGroupid()!=null){
						params.put("id", grpmain.getGroupid());
						Tbcollateralgroupmain row = (Tbcollateralgroupmain) dbServiceLOS.executeUniqueHQLQueryMaxResultOne
								("FROM Tbcollateralgroupmain WHERE groupid=:id", params);
						if(row!=null){
							// updating record
							row.setGroupname(grpmain.getGroupname());
							row.setDateupdated(new Date());
							row.setUpdatedby(secservice.getUserName());
							if(dbServiceLOS.saveOrUpdate(row)){
								//System.out.println(">>> SUCCESS UPDATING COLLATERAL - Group Main");
								flag = "success";
							}
						}
					}
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbloancollateralgroup> listGrpLoanApplicationUsage(String grpID, String loanno) {
		List<Tbloancollateralgroup> list = new ArrayList<Tbloancollateralgroup>();
		try {
			if(grpID!=null){
			params.put("id", grpID);
			String q = "SELECT a.groupid, a.groupname, a.appno, a.loanno, "
					+ "(SELECT productname FROM TBLOANPRODUCT WHERE productcode=(SELECT loanproduct FROM TBLSTAPP WHERE appno=a.appno)) as loanproduct, "
					+ "(SELECT processname FROM Tbworkflowprocess WHERE sequenceno=(SELECT applicationstatus FROM TBLSTAPP WHERE appno=a.appno) "
					+ "AND workflowid=(SELECT applicationtype FROM TBLSTAPP WHERE appno=a.appno)) as applicationstatus, "
					+ "(SELECT desc1 FROM TBCODETABLE WHERE codename='ACCTSTS' AND codevalue=a.loanstatus) as loanstatus, "
					+ "(SELECT cifname FROM TBLSTAPP WHERE appno=a.appno) as borrowername "
					+ "FROM TBLOANCOLLATERALGROUP a "
					+ "WHERE a.groupid=:id";
			if(loanno!=null){
				q = q + " AND a.loanno IS NOT NULL";
			}else{
				q = q + " AND a.loanno IS NULL";
			}			
			list = (List<Tbloancollateralgroup>) dbServiceLOS.execSQLQueryTransformer(q, params, Tbloancollateralgroup.class, 1);			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcollateralpergroup> listCollateralperGrp(String grpID) {
		List<Tbcollateralpergroup> list = new ArrayList<Tbcollateralpergroup>();
		try {
			if(grpID!=null){
				params.put("id", grpID);
				String q = "SELECT a.groupid, a.collateralreferenceno, "
						+ "(SELECT desc1 FROM TBCODETABLE WHERE codename='COLLATERALTYPE' AND codevalue=a.collateraltype) as collateraltype "
						+ "FROM TBCOLLATERALPERGROUP a "
						+ "WHERE groupid=:id";
				list = (List<Tbcollateralpergroup>) dbServiceLOS.execSQLQueryTransformer(q, params, Tbcollateralpergroup.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TbCollateralMainFormGroup> getCollateralGrpMainList(String appno) {
		List<TbCollateralMainFormGroup> list = new ArrayList<TbCollateralMainFormGroup>();
		try {
			if(appno!=null){
				String q = "SELECT a.groupname, a.groupid, "
						+ "(SELECT desc1 FROM TBCODETABLE WHERE codename='GROUPTYPE' AND codevalue=a.grouptype) as grouptype, "
						+ "a.datecreated, (SELECT desc1 FROM TBCODETABLE WHERE codename='COLLATERALSTATUS' AND codevalue=a.groupstatus) as collateralstatus "
						+ "FROM TBCOLLATERALGROUPMAIN a "
						+ "LEFT JOIN TBLOANCOLLATERALGROUP b ON a.groupid = b.groupid "
						+ "WHERE b.appno ='"+appno+"'";
						list = (List<TbCollateralMainFormGroup>) dbServiceLOS.execSQLQueryTransformer(q, null, TbCollateralMainFormGroup.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public DedupeCollateralFormGroup dedupeTbcollateralgroupmain(String grpID, String grpName) {
		DedupeCollateralFormGroup rForm = new DedupeCollateralFormGroup();
		rForm.setFlag("failed");
		Tbcollateralgroupmain form = new Tbcollateralgroupmain();
		try {
			if(grpName!=null){
				params.put("name", grpName);
				Integer collateralmain = (Integer) dbServiceLOS.executeUniqueSQLQuery("SELECT COUNT(*) FROM Tbcollateralgroupmain WHERE groupname=:name", params);
				String q = "";
				if(collateralmain != null && collateralmain == 1){
					q = "SELECT (SELECT desc1 FROM TBCODETABLE WHERE codename='COLLATERALTYPE' AND codevalue=a.grouptype) as grouptype, "
							+ "(SELECT desc1 FROM TBCODETABLE WHERE codename='COLLATERALSTATUS' AND codevalue=a.groupstatus) as groupstatus, "
							+ "(SELECT desc1 FROM TBCODETABLE WHERE codename='APPRAISALSTATUS' AND codevalue=a.groupappraisalstatus) as groupstatus, "
							+ "* FROM Tbcollateralgroupmain a WHERE a.groupname=:name";
					form = (Tbcollateralgroupmain) dbServiceLOS.execSQLQueryTransformer(q, params, Tbcollateralgroupmain.class, 0);
					form.setCreatedby(UserUtil.getUserFullname(form.getCreatedby()));
					form.setUpdatedby(UserUtil.getUserFullname(form.getUpdatedby()));
					rForm.setMain(form);
					rForm.setFlag("success");
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rForm;
	}
	
	@Override
	public String generateCollateralGrpID() {
		String id = null;
		try {
			Tbsequence sequence = (Tbsequence) dbServiceLOS.executeUniqueHQLQuery("FROM Tbsequence WHERE product='COLLATERALGROUP'", params);
			if(sequence!=null){
				// update record
				int one = sequence.getSequence(); // value
				String zero = String.format("%06d", one); // 6 seq nos
				id = "CG" + zero; // generated
				sequence.setSequence(sequence.getSequence() + 1); // + 1 to be
																	// use for
																	// the next
																	// sequence
				dbServiceLOS.saveOrUpdate(sequence);
			}else{
				// create record
				Tbsequence sequenceNew = new Tbsequence();
				sequenceNew.setProduct("COLLATERALGROUP");
				sequenceNew.setSequence(1);
				if(dbServiceLOS.save(sequenceNew)){
					int one = sequenceNew.getSequence();
					String zero = String.format("%06d", one);
					id = "CG" + zero; // generated
					sequenceNew.setSequence(sequenceNew.getSequence() + 1);
					dbServiceLOS.saveOrUpdate(sequenceNew);					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}


}
