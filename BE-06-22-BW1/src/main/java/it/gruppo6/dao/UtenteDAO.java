package it.gruppo6.dao;

import java.util.ArrayList;
import javax.persistence.NoResultException;

import it.gruppo6.entities.Tessera;
import it.gruppo6.entities.Utente;
import it.gruppo6.util.JpaUtil;


public class UtenteDAO extends JpaUtil {
	public static void save(Utente user) {
		try {
			t.begin();
			em.persist(user);
			t.commit();
		} catch (Exception x) {
			System.out.println("Ops! Qualcosa è andato storto... Riprova!");
			x.printStackTrace();
		}
	}
	
	public static Utente creaUtente(String username, String data, String email, Tessera tessera) {

		Utente u = new Utente();
		u.setUsername(username);
		u.setDataNascita(data);
		u.setEmail(email);
		u.setNumeroTessera(tessera);
		return u;
	}
	

	public static Utente trovaUtenteByTessera(String numT) {
		try { 						  //SELECT * FROM public.tessera WHERE id = 91376784
	    	Utente u = em.createQuery("SELECT t FROM Utente t WHERE t.numeroTessera = :tes", Utente.class)
	                .setParameter("tes", numT)
	                .getSingleResult();
	        return u;
		} catch (NoResultException x) {
	        System.out.println("Tessera non trovata!");
	        return null;
	    }
	}
	
	public static Utente trovaUtenteById(int id) {
		try { 
	    	Utente u = em.createQuery("SELECT i FROM Utente i WHERE i.id = :id", Utente.class)
	                .setParameter("id", id)
	                .getSingleResult();
	        return u;
		} catch (NoResultException x) {
	        System.out.println("Utente non trovato!");
	        return null;
	    }
	}
	
	public static void salvaUtenti(ArrayList<Utente> userList) {
		try {
			for (Utente u : userList) {
				save(u);
			}
			System.out.println("Utenti salvati con successo!");
		} catch (Exception e) {
			System.out.println("Errore nel salvataggio.");
		}
	}
}
