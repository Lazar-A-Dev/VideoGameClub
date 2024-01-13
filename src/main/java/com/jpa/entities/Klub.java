package com.jpa.entities;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
@Entity
@Table(name = "klub")
public class Klub {
	@TableGenerator(name = "klub_gen", table = "id_gen", pkColumnName = "gen_name", valueColumnName = "gen_val", allocationSize = 1, pkColumnValue = "klub_gen")
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "klub_gen")
	
	//@Column(name = "idKlub")
	private int idKlub;
	
	//@Column(name = "TipRadnje")
	private String TipRadnje;
	
	@Column(name = "idClana")
	private int idClana;
	
	@Column(name = "idProizvoda")
	private int idProizvoda;
	
	@Column(name = "Datum")
	private Date Datum;
	
	@Column(name = "Opis")
	private String Opis;
	
	
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
	
	
}
