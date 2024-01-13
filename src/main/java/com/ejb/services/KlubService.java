package com.ejb.services;
import java.util.Date;
import java.util.List;

import com.jpa.entities.Klub;
public interface KlubService {
	
	public void rentProizvod(int idClana, String tip, String naziv, Date datumOd, Date datumDo);
	public void donateProizvod(int idClana, String tip, String naziv, int kolicina);
	public void sellProizvod(int idClana, String tip, String naziv, int kolicina);
	public List<Klub> listKlub();

}
