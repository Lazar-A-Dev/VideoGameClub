package com.ejb.services;
import java.util.List;

import com.jpa.entities.Clan;
public interface ClanService {
	public void addClan(Clan emp);
	public void deleteClan(int id);
	public void updateClan(int id, String odobrenje, int poeni, int racun);
	public List<Clan> listClanova();
}

