package com.gui.controllers;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import com.ejb.services.KlubService;
import com.jpa.entities.Klub;
@ManagedBean
public class KlubController {
	
	private Klub klub = new Klub();
	private int idKlub = 0;
	private String TipRadnje = "";
	private int idClana = 0;
	private int idClanaDonate = 0;
	private int idClanaSell = 0;
	private int idProizvoda = 0;
	private Date Datum;
	private String Opis = "";
		
	private Date datumOd;
	private Date datumDo;
	
	private String TipProizvoda = "";
	private String TipProizvodaSell = "";
	private String TipProizvodaRent = "";
	private String NazivProizvoda = "";
	private String NazivProizvodaSell = "";
	private String NazivProizvodaRent = "";
	private int KolicinaProizvoda = 0;
	private int KolicinaProizvodaSell = 0;
	
	@EJB
	private KlubService service;
	public Klub getKlub() {
		return klub;
	}
	
	public void setKlub(Klub klub) {
		this.klub = klub;
	}
	
	public int getIdKlub() {
		return idKlub;
	}
		
	public void setIdKlub(int idKlub) {
		this.idKlub = idKlub;
	}
		
	public String getTipRadnje() {
		return TipRadnje;
	}
		
	public void setTipRadnje(String TipRadnje) {
		this.TipRadnje = TipRadnje;
	}
		
	public int getIdClana() {
		return idClana;
	}
		
	public void setIdClana(int idClana) {
		this.idClana = idClana;
	}
	
	public int getIdClanaDonate() {
		return idClanaDonate;
	}
		
	public void setIdClanaDonate(int idClanaDonate) {
		this.idClanaDonate = idClanaDonate;
	}
	
	public int getIdClanaSell() {
		return idClanaSell;
	}
		
	public void setIdClanaSell(int idClanaSell) {
		this.idClanaSell = idClanaSell;
	}
		
	public int getIdProizvoda() {
		return idProizvoda;
	}
		
	public void setIdProizvoda(int idProizvoda) {
		this.idProizvoda = idProizvoda;
	}
		
	public Date getDatum() {
		return Datum;
	}
		
	public void setDatum(Date Datum) {
		this.Datum = Datum;
	}
		
	public String getOpis() {
		return Opis;
	}
		
	public void setOpis(String Opis) {
		this.Opis = Opis;
	}
	
	public Date getDatumOd() {
		return datumOd;
	}
	
	public void setDatumOd(Date datumOd){
		this.datumOd = datumOd;
	}
	
	public Date getDatumDo() {
		return datumDo;
	}
	
	public void setDatumDo(Date datumDo){
		this.datumDo = datumDo;
	}
	
	public String getTipProizvoda() {
		return TipProizvoda;
	}
	
	public void setTipProizvoda(String TipProizvoda) {
		this.TipProizvoda = TipProizvoda;
	}
	
	
	public String getTipProizvodaRent() {
		return TipProizvodaRent;
	}
	
	public void setTipProizvodaRent(String TipProizvodaRent) {
		this.TipProizvodaRent = TipProizvodaRent;
	}
	
	public String getTipProizvodaSell() {
		return TipProizvodaSell;
	}
	
	public void setTipProizvodaSell(String TipProizvodaSell) {
		this.TipProizvodaSell = TipProizvodaSell;
	}
	
	public String getNazivProizvoda() {
		return NazivProizvoda;
	}
	
	public void setNazivProizvoda(String NazivProizvoda) {
		this.NazivProizvoda = NazivProizvoda;
	}
	
	public String getNazivProizvodaRent() {
		return NazivProizvodaRent;
	}
	
	public void setNazivProizvodaRent(String NazivProizvodaRent) {
		this.NazivProizvodaRent = NazivProizvodaRent;
	}
	
	public String getNazivProizvodaSell() {
		return NazivProizvodaSell;
	}
	
	public void setNazivProizvodaSell(String NazivProizvodaSell) {
		this.NazivProizvodaSell = NazivProizvodaSell;
	}
	
	public int getKolicinaProizvoda() {
		return KolicinaProizvoda;
	}
	
	public void setKolicinaProizvoda(int KolicinaProizvoda) {
		this.KolicinaProizvoda = KolicinaProizvoda;
	}
	
	public int getKolicinaProizvodaSell() {
		return KolicinaProizvodaSell;
	}
	
	public void setKolicinaProizvodaSell(int KolicinaProizvodaSell) {
		this.KolicinaProizvodaSell = KolicinaProizvodaSell;
	}
	
	
	public void rentProizvod(int idClana, String tip, String naziv, Date datumOd, Date datumDo) {
		service.rentProizvod(idClana, tip, naziv, datumOd, datumDo);
	}
	
	public void donateProizvod(int idClana, String tip, String naziv, int kolicina) {
		service.donateProizvod(idClana, tip, naziv, kolicina);
	}
	
	public void sellProizvod(int idClana, String tip, String naziv, int kolicina) {
		service.sellProizvod(idClana, tip, naziv, kolicina);
	}
	
	public List<Klub> listKlub() {
	    List<Klub> allKlub = service.listKlub();
	    return allKlub;
	}
	


}
