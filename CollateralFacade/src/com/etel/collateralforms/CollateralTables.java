package com.etel.collateralforms;

import java.util.List;

import com.coopdb.data.Tbcollateralauto;
import com.coopdb.data.Tbcollateraldeposits;
import com.coopdb.data.Tbcollaterallivestock;
import com.coopdb.data.Tbcollateralmachineries;
import com.coopdb.data.Tbcollateralmain;
import com.coopdb.data.Tbcollateralrel;
import com.coopdb.data.Tbcollateralsecurities;

public class CollateralTables {
	
	Tbcollateralmain main;
	Tbcollateralauto auto;
	Tbcollateralrel rel;
	Tbcollateraldeposits deposits;
	Tbcollateralmachineries machineries;
	Tbcollateralsecurities securities;
	Tbcollaterallivestock livestock;
	List<Tbcollateralauto> colauto;
	List<Tbcollateralrel> colrel;
	
	public Tbcollateralmain getMain() {
		return main;
	}
	public void setMain(Tbcollateralmain main) {
		this.main = main;
	}
	public Tbcollateralauto getAuto() {
		return auto;
	}
	public void setAuto(Tbcollateralauto auto) {
		this.auto = auto;
	}
	public Tbcollateralrel getRel() {
		return rel;
	}
	public void setRel(Tbcollateralrel rel) {
		this.rel = rel;
	}
	public Tbcollateraldeposits getDeposits() {
		return deposits;
	}
	public void setDeposits(Tbcollateraldeposits deposits) {
		this.deposits = deposits;
	}
	public Tbcollateralmachineries getMachineries() {
		return machineries;
	}
	public void setMachineries(Tbcollateralmachineries machineries) {
		this.machineries = machineries;
	}
	public Tbcollateralsecurities getSecurities() {
		return securities;
	}
	public void setSecurities(Tbcollateralsecurities securities) {
		this.securities = securities;
	}
	
	public Tbcollaterallivestock getLivestock(){
		return livestock;
	}
	
	public void setLivestock(Tbcollaterallivestock livestock){
		this.livestock = livestock;
	}
	
	public List<Tbcollateralauto> getColauto() {
		return colauto;
	}
	public void setColauto(List<Tbcollateralauto> colauto) {
		this.colauto = colauto;
	}
	public List<Tbcollateralrel> getColrel() {
		return colrel;
	}
	public void setColrel(List<Tbcollateralrel> colrel) {
		this.colrel = colrel;
	}
	
	
	
}
