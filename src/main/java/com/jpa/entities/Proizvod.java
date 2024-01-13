package com.jpa.entities;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
@Entity
@Table(name = "proizvod")
public class Proizvod {
	@TableGenerator(name = "proizvod_gen", table = "id_gen", pkColumnName = "gen_name", valueColumnName = "gen_val", allocationSize = 1, pkColumnValue = "proizvod_gen")
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "proizvod_gen")
	
	//@Column(name = "idProizvoda")
	private int idProizvoda;
	
	//@Column(name = "TipProizvoda")
	private String TipProizvoda;
	
	@Column(name = "NazivProizvoda")
	private String NazivProizvoda;
	
	@Column(name = "CenaProizvoda")
	private int CenaProizvoda;
	
	@Column(name = "CenaPoDanuProizvoda")
	private int CenaPoDanuProizvoda;
	
	@Column(name = "BonusPoeniProizvoda")
	private int BonusPoeniProizvoda;
	
	//@Column(name = "GostKupujeProizvod")
	private String GostKupujeProizvod;
	
	//@Column(name = "KolicinaProizvoda")
	private int KolicinaProizvoda;
	
	
	public int getIdProizvoda() {
		return idProizvoda;
	}
	
	public void setIdProizvoda(int idProizvoda) {
		this.idProizvoda = idProizvoda;
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
	
	public int getBonusPoeniProizvoda() {
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
	
}
