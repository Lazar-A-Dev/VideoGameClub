package com.ejb.services.impl;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ejb.services.ProizvodService;
import com.jpa.entities.Clan;
import com.jpa.entities.Klub;
import com.jpa.entities.Proizvod;
@Stateless
public class ProizvodServiceImpl implements ProizvodService {
	@PersistenceContext(name = "KlubVideoIgara")
	private EntityManager em;
	
	@Override
	public void addProizvod(Proizvod emp) {
		em.persist(emp);
	}
	
	@Override
	public void deleteProizvod(int id) {
	    Proizvod e = em.find(Proizvod.class, id);

	    if (e == null) {
	        throw new IllegalArgumentException("Cannot delete non-existing entity with ID: " + id);
	    }

	    em.remove(e);
	}



	@Override
	public void updateProizvod(int id, int cena, int cenaPoDanu, String odobrenje, int kolicina) {
		//System.out.println("Kolicina proizvoda nakon update:" + kolicina);
		Proizvod e = em.find(Proizvod.class,id);
		e.setCenaProizvoda(cena);
		e.setCenaPoDanuProizvoda(cenaPoDanu);
		e.setGostKupujeProizvod(odobrenje);
		e.setKolicinaProizvoda(kolicina);
		
	}
	
	@Override
	public List<Proizvod> listProizvoda() {
	    List<Proizvod> allProizvodi = em.createQuery("SELECT e FROM Proizvod e", Proizvod.class).getResultList();
	    return allProizvodi;
	}
	
	@Override
	public void buyProizvod(String tip, String naziv, int kolicina, String kupac, String iskoristiP, int idClana) {
	    System.out.println("Pronađen proizvod:");
	    System.out.println("Tip: " + tip);
	    System.out.println("Naziv: " + naziv);
	    System.out.println("Količina: " + kolicina);
	    System.out.println("Kupac: " + kupac);
	    System.out.println("idClana: " + idClana);

	    TypedQuery<Proizvod> query = em.createQuery("SELECT p FROM Proizvod p WHERE p.TipProizvoda = :tip AND p.NazivProizvoda = :naziv", Proizvod.class);

	    query.setParameter("tip", tip);
	    query.setParameter("naziv", naziv);

	    try {
	        Proizvod proizvod = query.getSingleResult();

	        if (("gost".equals(kupac) && "true".equals(proizvod.getGostKupujeProizvod())) || "clan".equals(kupac)) {
	            // Ažuriranje količine
	        if(proizvod.getKolicinaProizvoda() >= kolicina) {//Ako neko zahteva veci broj proizvoda od moguceg prekida se proces
	            int novaKolicina = proizvod.getKolicinaProizvoda() - kolicina;
	            proizvod.setKolicinaProizvoda(novaKolicina);

	            // Izmena kolicina u proizvod bazi
	            em.merge(proizvod);
	            
	         // Ako je kupac clan, obradi plaćanje
	            if ("clan".equals(kupac)) {
	                TypedQuery<Clan> queryClan = em.createQuery("SELECT c FROM Clan c WHERE c.idClana = :idClana", Clan.class);
	                queryClan.setParameter("idClana", idClana);

	                try {
	                    Clan clan = queryClan.getSingleResult();
	                    int snizenje = 1;
	                    
	                    // Provera da li je račun veci od ceneProizvoda*kolicina
	                    int ukupnaCena = proizvod.getCenaProizvoda() * kolicina;
	                    
	                    //Ako clan zeli da iskoristi bonus poene za snizenje
	                    if ("true".equals(iskoristiP)) {
	                    	//Clan treba minimalno da ima 100 poena kako bi ostvario namanje snizenje od 5%
	                    	if(clan.getBrojPoenaClana() >= 100) {
	
	                    	int oduzetiPoeni = 0;
	                    		if(clan.getBrojPoenaClana() >= 100 && clan.getBrojPoenaClana() < 250) {
	                    			snizenje = 5;
	                    			oduzetiPoeni = 100;
	                    		}
	                    		else if(clan.getBrojPoenaClana() >= 250 && clan.getBrojPoenaClana() < 500) {
	                    			snizenje = 10;
	                    			oduzetiPoeni = 250;
	                    		}
	                    		else if(clan.getBrojPoenaClana() >= 500 && clan.getBrojPoenaClana() < 1000) {
	                    			snizenje = 25;
	                    			oduzetiPoeni = 500;
	                    		}
	                    		else if(clan.getBrojPoenaClana() >= 1000) {
	                    			snizenje = 50;
	                    			oduzetiPoeni = 1000;
	                    		}
	                    		
	                    		//Nova cena sa snizenjem
	                    		int temp = (int) (ukupnaCena * (snizenje / 100.0));
	                    		ukupnaCena = ukupnaCena - temp;
	                    		
	                    		//Oduzimamo clanu te poene sto je potrosio
	                    		int temp2 = clan.getBrojPoenaClana() - oduzetiPoeni;
	                    		clan.setBrojPoenaClana(temp2);
	                    	}
	                    	else {
	                    		System.out.println("Clan sa id-jem: " + clan.getIdClana() + " nema dovoljan broj poena za bilokoje snizenje");
	                    	}
	                    		
	                    }
	                    
	                    if (clan.getRacunClana() >= ukupnaCena) {
	                        int noviRacun = clan.getRacunClana() - ukupnaCena;
	                        clan.setRacunClana(noviRacun);
	                        int noviPoeni = clan.getBrojPoenaClana() + proizvod.getBonusPoeniProizvoda();
	                        clan.setBrojPoenaClana(noviPoeni);
	                        // Ažuriraj člana u bazi
	                        em.merge(clan);

	                        System.out.println("Transakcija uspešna. Novi račun člana: " + clan.getRacunClana());
	                        System.out.println("Bonus poena zaradjenih: " + proizvod.getBonusPoeniProizvoda());
	                        
	                     // Dodavanje evidencije u tabeli klub
	    	                Klub klub = new Klub();
	    	                klub.setTipRadnje("Prodaja");
	    	                klub.setIdClana(idClana);
	    	                klub.setIdProizvoda(proizvod.getIdProizvoda());
	    	                
	    	                // Formatiranje trenutnog datuma u string
	    	                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    	                String danasnjiDatumString = LocalDate.now().format(formatter);

	    	                // Postavljanje datuma u objekt Klub
	    	                klub.setDatum(java.sql.Date.valueOf(LocalDate.parse(danasnjiDatumString, formatter)));
	    	                String opis = "Proizvod je prodat u kolicini od:" + kolicina + " sa cenom od: " + ukupnaCena;
	    	                if ("true".equals(iskoristiP))
	    	                	opis = "Proizvod je prodat u kolicini od: " + kolicina + " sa cenom od:" + ukupnaCena + " i snizenjem od: " + snizenje + "%";
	    	                
	    	                klub.setOpis(opis);
	    	                em.persist(klub);
	    	                
	                    } else {
	                        System.out.println("Nemate dovoljno sredstava na računu.");
	                        novaKolicina = proizvod.getKolicinaProizvoda() + kolicina;
	        	            proizvod.setKolicinaProizvoda(novaKolicina);

	        	            // Izmena kolicina u proizvod bazi
	        	            em.merge(proizvod);
	                    }
	                } catch (NoResultException e) {
	                    System.out.println("Član nije pronađen.");
	    	            proizvod.setKolicinaProizvoda(proizvod.getKolicinaProizvoda() + kolicina);

	    	            // Vracamo kolicinu na staru ako ne postoji clan
	    	            em.merge(proizvod);
	                }
	            }
	            

	        } 
	        else {
	        	System.out.println("Nije moguce kupiti: " + kolicina + " proizovda, ako ima samo: " + proizvod.getKolicinaProizvoda());
	        	}
	        }
	        else {
	        	System.out.println("Gost nema pravo da poruci ovaj proizvod");
	        }
	        
	    } catch (NoResultException e) {
	        System.out.println("Proizvod nije pronađen.");
	    }
	}




	
}
