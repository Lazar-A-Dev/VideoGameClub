package com.jpa.entities;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
@Entity
@Table(name = "clan")
public class Clan {
	@TableGenerator(name = "clan_gen", table = "id_gen", pkColumnName = "gen_name", valueColumnName = "gen_val", allocationSize = 1, pkColumnValue = "clan_gen")
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "clan_gen")
	
	//@Column(name = "idClana")
	private int idClana;
	
	//@Column(name = "ImeClana")
	private String ImeClana;
	
	@Column(name = "PrezimeClana")
	private String PrezimeClana;
	
	@Column(name = "EmailClana")
	private String EmailClana;
	
	@Column(name = "BrojTelClana")
	private String BrojTelClana;
	
	@Column(name = "OdobrenjeClana")
	private String OdobrenjeClana;
	
	//@Column(name = "BrojPoenaClana")
	private int BrojPoenaClana;
	
	//@Column(name = "RacunClana")
	private int RacunClana;
	
	
	public int getIdClana() {
		return idClana;
	}
	
	public void setIdClana(int idClana) {
		this.idClana = idClana;
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
	
	
}

