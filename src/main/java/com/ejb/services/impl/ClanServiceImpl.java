package com.ejb.services.impl;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.ejb.services.ClanService;
import com.jpa.entities.Clan;
@Stateless
public class ClanServiceImpl implements ClanService {
	@PersistenceContext(name = "KlubVideoIgara")
	private EntityManager em;
	@Override
	public void addClan(Clan emp) {
		em.persist(emp);
	}
	
	@Override
	public void deleteClan(int id) {
	    Clan e = em.find(Clan.class, id);

	    if (e == null) {
	        throw new IllegalArgumentException("Cannot delete non-existing entity with ID: " + id);
	    }

	    em.remove(e);
	}



	@Override
	public void updateClan(int id, String odobrenje, int poeni, int racun) {
		Clan e=em.find(Clan.class,id);
		e.setOdobrenjeClana(odobrenje);
		e.setBrojPoenaClana(poeni);
		e.setRacunClana(racun);
	}
	
	@Override
	public List<Clan> listClanova() {
	    List<Clan> allClanovi = em.createQuery("SELECT e FROM Clan e", Clan.class).getResultList();
	    return allClanovi;
	}

	
}

