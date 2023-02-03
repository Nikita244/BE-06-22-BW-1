package it.gruppo6.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import javax.persistence.NoResultException;
import it.gruppo6.entities.Tessera;
import it.gruppo6.util.JpaUtil;
import it.gruppo6.util.Methods;

public class TesseraDAO extends JpaUtil {
	public static void save(Tessera te) {
		try {
			t.begin();
			em.persist(te);
			t.commit();
		} catch (Exception x) {
			System.out.println("Ops! Qualcosa è andato storto...");
		}
	}
	
	public static void getInfoByIdTessera(String numT) {
		try { 					
			String numTessera = (numT).toUpperCase();
	    	Tessera u = em.createQuery("SELECT t FROM Tessera t WHERE t.id = :tes", Tessera.class)
	                .setParameter("tes", numTessera)
	                .getSingleResult();
	        System.out.println("\n " + u + "\n ");
	    	Methods.attesa(1);
		} catch (NoResultException x) {
	        System.out.println("Tessera non trovata!");
	        Methods.attesa(1);
	    }
	}
	
	public static Tessera getInfoByIdTesseraAbbonamento(String numT) {
	 		String numTessera = (numT).toUpperCase();
	    	Tessera u = em.createQuery("SELECT t FROM Tessera t WHERE t.id = :tes", Tessera.class)
	                .setParameter("tes", numTessera)
	                .getSingleResult();
	        return u;
	}
	
	
	
	public static Tessera creaTessera(String numTessera, LocalDate data) {
		Tessera t = new Tessera();
		t.setId(numTessera);
		t.setDataInizio(data);
		t.setDataScadenza(data.plusYears(1));
		
		return t;
	}
	
	
	public static void salvaTessere(ArrayList<Tessera> tList) {
		try {
			for (Tessera t : tList) {
				save(t);
			}
			System.out.println("Tessere salvate con successo!");
		} catch (Exception e) {
			System.out.println("Errore nel salvataggio.");
		}
	}
}
