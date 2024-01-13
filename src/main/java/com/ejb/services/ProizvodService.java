package com.ejb.services;
import java.util.List;

import com.jpa.entities.Proizvod;
public interface ProizvodService {
	public void addProizvod(Proizvod emp);
	public void deleteProizvod(int id);
	public void updateProizvod(int id, int cena, int cenaPoDanu, String odobrenje, int kolicina);
	public List<Proizvod> listProizvoda();
	public void buyProizvod(String tip, String naziv, int kolicina, String kupac, String iskoristiP, int idClana);

}