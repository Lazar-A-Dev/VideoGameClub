package com.ejb.services.impl;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import com.ejb.services.KlubService;
import com.jpa.entities.Clan;
import com.jpa.entities.Klub;
import com.jpa.entities.Proizvod;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
@Stateless
public class KlubServiceImpl implements KlubService {
	@PersistenceContext(name = "KlubVideoIgara")
	private EntityManager em;
	
	@Override
	public void rentProizvod(int idClana, String tip, String naziv, Date datumOd, Date datumDo) {
		System.out.println("id clana: " + idClana);
	    System.out.println("tip proizvoda: " + tip);
	    System.out.println("naziv proizvoda: " + naziv);
	    System.out.println("Datum od: " + datumOd);
	    System.out.println("Datum do: " + datumDo);
	    String opis = "";
	    
	    LocalDate localDatumOd = datumOd.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    LocalDate localDatumDo = datumDo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

	    // Broja dana između dva datuma
	    long brojDana = ChronoUnit.DAYS.between(localDatumOd, localDatumDo);
	    
	  //Trazimo proizvod
	    TypedQuery<Proizvod> query = em.createQuery("SELECT p FROM Proizvod p WHERE p.TipProizvoda = :tip AND p.NazivProizvoda = :naziv", Proizvod.class);
	    	query.setParameter("tip", tip);
	    	query.setParameter("naziv", naziv);
	    //Ako ne postoji onda je null
	    	List<Proizvod> results = query.getResultList();
	    	Proizvod proizvod = results.isEmpty() ? null : results.get(0);
	    //Trazimo clan
	    	TypedQuery<Clan> queryClan = em.createQuery("SELECT c FROM Clan c WHERE c.idClana = :idClana", Clan.class);
    	    queryClan.setParameter("idClana", idClana);
    	//Alo ne postoji onda je isto null
    	    List<Clan> resultsClan = queryClan.getResultList();
    	    Clan clan = resultsClan.isEmpty() ? null : resultsClan.get(0);
	    
	    try {
	    	if(clan != null && proizvod != null) {
	    	
	    	if(clan.getOdobrenjeClana() != "false") {
	    		
	    		if(clan.getRacunClana() > proizvod.getCenaPoDanuProizvoda() * (int)brojDana) {
	    			int noviRacun = clan.getRacunClana() - proizvod.getCenaPoDanuProizvoda() * (int)brojDana;
	    			clan.setRacunClana(noviRacun);
	    			em.merge(clan);
	    			
	    			// Dodavanje evidencije u tabeli klub
	                Klub klub = new Klub();
	                klub.setTipRadnje("Iznajmljivanje");
	                klub.setIdClana(idClana);
	                klub.setIdProizvoda(proizvod.getIdProizvoda());
	                
	                // Formatiranje trenutnog datuma u string
	                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	                String danasnjiDatumString = LocalDate.now().format(formatter);

	                // Postavljanje datuma u objekt Klub
	                klub.setDatum(java.sql.Date.valueOf(LocalDate.parse(danasnjiDatumString, formatter)));
	                opis = "Datum od:" + datumOd + " datum do:" + datumDo + " Cena: " + proizvod.getCenaPoDanuProizvoda() * (int)brojDana;
	                klub.setOpis(opis);

	                em.persist(klub);
	    		}
	    		else {
	    			System.out.println("Clan sa id-jem: " + idClana + " nema dovoljno novca na racunu za ovu radnju");
	    		}
	    	}
	    	else {
	    		 System.out.println("Clan sa id-jem: " + idClana + " nema pravo da iznajmi proizvod");
	    	}
	    	}
	    	else {
	    		System.out.println("Član ili proizvod nije pronađen.");
	    	}
	    }
	    catch (NoResultException e) {
            System.out.println("Doslo je do greske prilikom iznajmljivanja.");
        }
	}
	
	@Override
	public void donateProizvod(int idClana, String tip, String naziv, int kolicina) {
	    System.out.println("id clana: " + idClana);
	    System.out.println("Tip: " + tip);
	    System.out.println("Naziv: " + naziv);
	    System.out.println("Kolicina: " + kolicina);
	    String opis = "";
	    String dodatPro = "false";
	    
	    //Trazimo proizvod
	    TypedQuery<Proizvod> query = em.createQuery("SELECT p FROM Proizvod p WHERE p.TipProizvoda = :tip AND p.NazivProizvoda = :naziv", Proizvod.class);
	    	query.setParameter("tip", tip);
	    	query.setParameter("naziv", naziv);
	    //Ako ne postoji onda je null
	    	List<Proizvod> results = query.getResultList();
	    	Proizvod proizvod = results.isEmpty() ? null : results.get(0);
	    //Trazimo clan
	    	TypedQuery<Clan> queryClan = em.createQuery("SELECT c FROM Clan c WHERE c.idClana = :idClana", Clan.class);
    	    queryClan.setParameter("idClana", idClana);
    	//Alo ne postoji onda je isto null
    	    List<Clan> resultsClan = queryClan.getResultList();
    	    Clan clan = resultsClan.isEmpty() ? null : resultsClan.get(0);
	    
	    
	    try {
	    	//Ako proizvod postoji povecavamo mu kolicinu i clanu dodajemo bonus poene
	    	//Ako ne postoji, pravimo proizvod i dodajemo ga u bazi
	    	if(proizvod == null && clan != null) {
	    		Proizvod pro = new Proizvod();
                pro.setNazivProizvoda(naziv);
                pro.setTipProizvoda(tip);
                pro.setGostKupujeProizvod("false");
                pro.setKolicinaProizvoda(kolicina);
                if ("Konzola".equals(tip)) {
                	pro.setBonusPoeniProizvoda(100);
                	pro.setCenaProizvoda(10000);
                	pro.setCenaPoDanuProizvoda(1000);
                }
                else if ("Igrica".equals(tip)) {
                	pro.setBonusPoeniProizvoda(50);
                	pro.setCenaProizvoda(2000);
                	pro.setCenaPoDanuProizvoda(250);
                	
                }
                dodatPro = "true";
                //Dodajemo novi proizvod u bazi
                em.persist(pro);
                //Zatim ga trazimo opet
                query = em.createQuery("SELECT p FROM Proizvod p WHERE p.TipProizvoda = :tip AND p.NazivProizvoda = :naziv", Proizvod.class);

        	    query.setParameter("tip", tip);
        	    query.setParameter("naziv", naziv);
        	    
        	    proizvod = query.getSingleResult();
	    	}
	    	else {
	    		System.out.println("Proizvod ili clan sa ovim id-jem ne postoji.");
	    	}
	    	
            	if(clan != null) {
	    	
	    	    //Ako je proizvod napravljen onda nema potreba da mu se povecava kolicina posto mu je vec dodata. U suprotnom ako proizvod vec postoji onda mu se povecava kolincna
            	if(dodatPro == "false") {
            		int novaKolicina = proizvod.getKolicinaProizvoda() + kolicina;
            		proizvod.setKolicinaProizvoda(novaKolicina);
            		em.merge(proizvod);
            	}
	    	    	
	    	    //Dodajemo bonus poene clanu, 100 za konzolu i 50 za igru
	    	    int noviBonusPoeni = 0 ;
	    	    	
	    	    if ("Konzola".equals(proizvod.getTipProizvoda())) {
	    	    	 noviBonusPoeni = clan.getBrojPoenaClana() + (100 * kolicina);
	    	    } else {
	    	    	 noviBonusPoeni = clan.getBrojPoenaClana() + (50 * kolicina);
	    	    }
	    	    	
	    	    	
	    	    clan.setBrojPoenaClana(noviBonusPoeni);
	    	    em.merge(clan);
	    	    	
	    	    // Dodavanje evidencije u tabeli klub
	            Klub klub = new Klub();
	            klub.setTipRadnje("Donacija");
	            klub.setIdClana(idClana);
	            klub.setIdProizvoda(proizvod.getIdProizvoda());
	                
	            // Formatiranje trenutnog datuma u string
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	            String danasnjiDatumString = LocalDate.now().format(formatter);

	            // Postavljanje datuma u objekt Klub
	            klub.setDatum(java.sql.Date.valueOf(LocalDate.parse(danasnjiDatumString, formatter)));
	            opis = "Ovom donacijom je clan stekao: " + proizvod.getBonusPoeniProizvoda()*kolicina + " bonus poena";
	            klub.setOpis(opis);

	            	em.persist(klub);
	    	    }
	    	    else {
	    	    	System.out.println("Clan sa ovim id-jem ne postoji.");
	    	    }
	    	    
	    	
	    }
	    	catch (NoResultException e) {
	    		System.out.println("Doslo je do greske prilikom donacije proizvoda.");
	    	}
	    	
	}

	@Override
	public void sellProizvod(int idClana, String tip, String naziv, int kolicina) {
		System.out.println("id clana: " + idClana);
	    System.out.println("Tip: " + tip);
	    System.out.println("Naziv: " + naziv);
	    System.out.println("Kolicina: " + kolicina);
	    String dodatPro = "false";
	    String opis = "";
	    //Trazimo proizvod
	    TypedQuery<Proizvod> query = em.createQuery("SELECT p FROM Proizvod p WHERE p.TipProizvoda = :tip AND p.NazivProizvoda = :naziv", Proizvod.class);
	    	query.setParameter("tip", tip);
	    	query.setParameter("naziv", naziv);
	    //Ako ne postoji onda je null
	    	List<Proizvod> results = query.getResultList();
	    	Proizvod proizvod = results.isEmpty() ? null : results.get(0);
	    //Trazimo clan
	    TypedQuery<Clan> queryClan = em.createQuery("SELECT c FROM Clan c WHERE c.idClana = :idClana", Clan.class);
    	    queryClan.setParameter("idClana", idClana);
    	//Ako ne postoji onda je null  
    	    List<Clan> resultsClan = queryClan.getResultList();
    	    Clan clan = resultsClan.isEmpty() ? null : resultsClan.get(0);
	    
	    
	    try {
	    	//Ako proizvod postoji povecavamo mu kolicinu i clanu dodajemo bonus poene
	    	//Ako ne postoji, pravimo proizvod i dodajemo ga u bazi
	    	if(proizvod == null && clan != null) {
	    		Proizvod pro = new Proizvod();
                pro.setNazivProizvoda(naziv);
                pro.setTipProizvoda(tip);
                pro.setGostKupujeProizvod("true");
                pro.setKolicinaProizvoda(kolicina);
                dodatPro = "true";//Ovim potvrdjujem da je kolicina vec dodata proizvodu, kako je nebih povecavao posle
                if ("Konzola".equals(tip)) {
                	pro.setBonusPoeniProizvoda(100);
                	pro.setCenaProizvoda(10000);
                	pro.setCenaPoDanuProizvoda(1000);
                }
                else if ("Igrica".equals(tip)) {
                	pro.setBonusPoeniProizvoda(50);
                	pro.setCenaProizvoda(2000);
                	pro.setCenaPoDanuProizvoda(250);
                	
                }
                
                //Dodajemo novi proizvod u bazi
                em.persist(pro);
                //Zatim ga trazimo opet
                query = em.createQuery("SELECT p FROM Proizvod p WHERE p.TipProizvoda = :tip AND p.NazivProizvoda = :naziv", Proizvod.class);

        	    query.setParameter("tip", tip);
        	    query.setParameter("naziv", naziv);
        	    
        	    proizvod = query.getSingleResult();
                
	    	}
	    	else {
	    		System.out.println("Proizvod ili clan sa ovim id-jem ne postoji.");
	    	}
	    	    
	    	    if(clan != null) {
	    	    	//Ako je proizvod napravljen onda nema potreba da mu se povecava kolicina posto mu je vec dodata. U suprotnom ako proizvod vec postoji onda mu se povecava kolincna
	            	if(dodatPro == "false") {
	            		int novaKolicina = proizvod.getKolicinaProizvoda() + kolicina;
	            		proizvod.setKolicinaProizvoda(novaKolicina);
	            		em.merge(proizvod);
	            	}
	    	    	
	    	    	//Dodajemo bonus poene clanu, 100 za konzolu i 50 za igru
	    	    	int noviRacun = 0 ;
	    	    	
	    	    	if ("Konzola".equals(proizvod.getTipProizvoda())) {
	    	    	    noviRacun = 7000 * kolicina;
	    	    	} else {
	    	    		noviRacun = 1000 * kolicina;
	    	    	}
	    	    	
	    	    	
	    	    	clan.setRacunClana(clan.getRacunClana() + noviRacun);
	    	    	em.merge(clan);
	    	    	
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
	                opis = "Ovom prodajom je clan stekao: " + noviRacun + " novca na racunu";
	                klub.setOpis(opis);

	                em.persist(klub);
	    	    }
	    	    else {
	    	    	System.out.println("Clan sa ovim id-jem ne postoji.");
	    	    }
	    	    
	    	
	    }
	    	catch (NoResultException e) {
	    		System.out.println("Doslo je do greske prilikom prodaje proizvoda.");
	    	}
		    	
	}
	
	@Override
	public List<Klub> listKlub() {
	    List<Klub> allKlub = em.createQuery("SELECT e FROM Klub e", Klub.class).getResultList();
	    return allKlub;
	}

	
}
