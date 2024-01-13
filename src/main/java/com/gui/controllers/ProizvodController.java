package com.gui.controllers;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;


import com.ejb.services.ProizvodService;
import com.jpa.entities.Proizvod;
@ManagedBean
public class ProizvodController {
	
	private Proizvod proizvod = new Proizvod();
	private int idProizvoda = 0;
	private int idProizvodaUpdate = 0;
	private String TipProizvoda = "";
	private String NazivProizvoda = "";
	private int CenaProizvoda = 0;
	private int CenaPoDanuProizvoda = 0;
	private int BonusPoeniProizvoda = 0;
	private String GostKupujeProizvod = "false";
	private int KolicinaProizvoda = 0;
	private int KolicinaProizvodaBuy = 0;
	
	private String TipKupca = "";
	private int idKupca = 0;
	
	private String KoristiPoene = "false";//Sa ovim proveravamo ako korisnik zeli da iskoristi bonus poene tokom kupovine za snizenje
	
	@EJB
	private ProizvodService service;
	
	
	
	public Proizvod getProizvod() {
		return proizvod;
	}
	public void setProizvod(Proizvod proizvod) {
		this.proizvod = proizvod;
	}
	
	public int getIdProizvoda() {
		return idProizvoda;
	}
	
	public void setIdProizvoda(int idProizvoda) {
		this.idProizvoda = idProizvoda;
	}
	
	public int getIdProizvodaUpdate() {
		return idProizvodaUpdate;
	}
	
	public void setIdProizvodaUpdate(int idProizvodaUpdate) {
		this.idProizvodaUpdate = idProizvodaUpdate;
	}
	
	public String getTipProizvoda() {
		return TipProizvoda;
	}
	
	public void setTipProizvoda(String TipProizvoda) {
		this.TipProizvoda = TipProizvoda;
	}
	
	public String getNazivProizvoda() {
		return NazivProizvoda;
	}
	
	public void setNazivProizvoda(String NazivProizvoda) {
		this.NazivProizvoda = NazivProizvoda;
	}
	
	public int getCenaProizvoda() {
		return CenaProizvoda;
	}
	
	public void setCenaProizvoda(int CenaProizvoda) {
		this.CenaProizvoda = CenaProizvoda;
	}
	
	public int getCenaPoDanuProizvoda() {
		return CenaPoDanuProizvoda;
	}
	
	public void setCenaPoDanuProizvoda(int CenaPoDanuProizvoda) {
		this.CenaPoDanuProizvoda = CenaPoDanuProizvoda;
	}
	
	public int getBonusPoeniProzivoda() {
		return BonusPoeniProizvoda;
	}
	
	public void setBonusPoeniProizvoda(int BonusPoeniProizvoda) {
		this.BonusPoeniProizvoda = BonusPoeniProizvoda;
	}
	
	public String getGostKupujeProizvod() {
		return GostKupujeProizvod;
	}
	
	public void setGostKupujeProizvod(String GostKupujeProizvod) {
		this.GostKupujeProizvod = GostKupujeProizvod;
	}
	
	public int getKolicinaProizvoda() {
		return KolicinaProizvoda;
	}
	
	public void setKolicinaProizvoda(int KolicinaProizvoda) {
		this.KolicinaProizvoda = KolicinaProizvoda;
	}
	
	public int getKolicinaProizvodaBuy() {
		return KolicinaProizvodaBuy;
	}
	
	public void setKolicinaProizvodaBuy(int KolicinaProizvodaBuy) {
		this.KolicinaProizvodaBuy = KolicinaProizvodaBuy;
	}
	
	public String getTipKupca() {
		return TipKupca;
	}
	
	public void setTipKupca(String TipKupca) {
		this.TipKupca = TipKupca;
	}
	
	public int getIdKupca() {
		return idKupca;
	}
	
	public void setIdKupca(int idKupca) {
		this.idKupca = idKupca;
	}
	
	public String getKoristiPoene(){
		return KoristiPoene;
	}
	
	public void setKoristiPoene(String KoristiPoene) {
		this.KoristiPoene = KoristiPoene;
	}
	
	
	public void saveProizvod(Proizvod emp) {
		service.addProizvod(emp);
	}
	
	public void removeProizvod(int id) {
	    service.deleteProizvod(id);
	}


	public void updateProizvod(int id, int cena, int cenaPoDanu, String odobrenje, int kolicina) {
		service.updateProizvod(id, cena, cenaPoDanu, odobrenje, kolicina);
	}
	
	public List<Proizvod> listProizvoda() {
	    List<Proizvod> allProizvodi = service.listProizvoda();
	    return allProizvodi;
	}

	public void buyProizvod(String tip, String naziv, int kolicina, String kupac, String iskoristiP, int idClana) {
		service.buyProizvod(tip, naziv, kolicina, kupac, iskoristiP, idClana);
	}
	
}
