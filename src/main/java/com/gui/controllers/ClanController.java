package com.gui.controllers;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import com.ejb.services.ClanService;
import com.jpa.entities.Clan;
@ManagedBean
public class ClanController {
	
	private Clan clan = new Clan();
	private int idClana = 0;
	private int idClanaUpdate = 0;
	private String ImeClana = "";
	private String PrezimeClana = "";
	private String EmailClana = "";
	private String BrojTelClana = "";
	private String OdobrenjeClana = "false";
	private int BrojPoenaClana = 0;
	private int RacunClana = 0;	
	
	@EJB
	private ClanService service;
	public Clan getClan() {
		return clan;
	}
	public void setClan(Clan clan) {
		this.clan = clan;
	}
	
	public int getIdClana() {
	    return idClana;
	}

	public void setIdClana(int idClana) {
	    this.idClana = idClana;
	}
	
	
	public int getIdClanaUpdate() {
	    return idClanaUpdate;
	}

	public void setIdClanaUpdate(int idClanaUpdate) {
	    this.idClanaUpdate = idClanaUpdate;
	}
	
	public String getImeClana() {
		return ImeClana;
	}
	public void setImeClana(String ImeClana) {
		this.ImeClana = ImeClana;
	}
	
	public String getPrezimeClana() {
		return PrezimeClana;
	}
	public void setPrezimeClana(String PrezimeClana) {
		this.PrezimeClana = PrezimeClana;
	}
	
	public String getEmailClana() {
		return EmailClana;
	}
	public void setEmailClana(String EmailClana) {
		this.EmailClana = EmailClana;
	}
	
	public String getBrojTelClana() {
		return BrojTelClana;
	}
	public void setBrojTelClana(String BrojTelClana) {
		this.BrojTelClana = BrojTelClana;
	}
	
	public String getOdobrenjeClana() {
		return OdobrenjeClana;
	}
	public void setOdobrenjeClana(String OdobrenjeClana) {
		this.OdobrenjeClana = OdobrenjeClana;
	}
	
	public int getBrojPoenaClana() {
		return BrojPoenaClana;
	}
	public void setBrojPoenaClana(int BrojPoenaClana) {
		this.BrojPoenaClana = BrojPoenaClana;
	}
	
	public int getRacunClana() {
		return RacunClana;
	}
	public void setRacunClana(int RacunClana) {
		this.RacunClana = RacunClana;
	}
	
	
	
	public void saveClan(Clan emp) {
		service.addClan(emp);
	}
	
	public void removeClan(int id) {
	    System.out.println("ID clana za brisanje: " + id);
	    service.deleteClan(id);
	}


	public void updateClan(int id, String odobrenje, int poeni, int racun) {
		service.updateClan(id, odobrenje, poeni, racun);
	}
	
	public List<Clan> listClanova() {
	    List<Clan> allClanovi = service.listClanova();
	    return allClanovi;
	}
	
	//Ovo je za select funkciju gde se bira odobrenje
		public List<String> selOdobrenjeClanova;
		
		@PostConstruct
		public void selOdobrenjeClanovaFun() {
			selOdobrenjeClanova = new ArrayList<>();
			selOdobrenjeClanova.add("true");
			selOdobrenjeClanova.add("false");
		}

		
		public List<String> getSelOdobrenjeClanova() {
		    return selOdobrenjeClanova;
		}

	
}