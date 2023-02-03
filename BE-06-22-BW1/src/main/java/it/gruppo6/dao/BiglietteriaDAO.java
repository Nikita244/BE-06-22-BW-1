package it.gruppo6.dao;

import java.time.LocalDate;
import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import it.gruppo6.app.Decorazioni;
import it.gruppo6.app.Main;
import it.gruppo6.entities.Biglietteria;
import it.gruppo6.entities.Biglietteria.StatoServizio;
import it.gruppo6.entities.Biglietteria.TipoEnte;
import it.gruppo6.entities.TitoloDiViaggio.TipoTitolo;
import it.gruppo6.util.JpaUtil;
import it.gruppo6.util.Methods;
import it.gruppo6.util.Methods.Function;

public class BiglietteriaDAO extends JpaUtil {
	public static void save(Biglietteria b) {
		try {
			t.begin();
			em.persist(b);
			t.commit();
		} catch (Exception x) {
			System.out.println("Ops! Qualcosa è andato storto...");
		}
	}
	
	public static void savePV(Biglietteria b) {
		try {
			t.begin();
			em.persist(b);
			t.commit();
		} catch (Exception x) {
			savePV(BiglietteriaDAO.creaPuntoVendita(Methods.generaNum("RIV"), TipoEnte.RIVENDITORE_AUTORIZZATO));
		}
	}
	
	public static Biglietteria creaPuntoVendita(String id, TipoEnte te) {
		Biglietteria b = new Biglietteria();
		b.setId(id);
		b.setQtyEmessa(0);
		b.setStatoServizio(StatoServizio.ATTIVO);
		b.setTipoEnte(te);
		return b;
	}
	
	public static void totaleQtyEmessa() {
	    try {
	        Long result = (Long) em.createQuery("SELECT SUM(i.qtyEmessa) FROM Biglietteria i")
	                .getSingleResult();
	        System.out.println("\n Il totale dei titoli di viaggio emessi in tutti i punti vendita è: " + result.intValue() + "\n");
	    } catch (Exception x) {
	        System.out.println("Nessun titolo di viaggio ancora emesso!");
	        Main.opzioniGestore();
	    }
	}
	
	public static void totaleQtyEmessaPerTipo() {
		System.out.println("Controllo in corso...");
		Methods.attesa(1);
	    try {
		    TipoTitolo tB = TipoTitolo.BIGLIETTO;
		    LocalDate d = LocalDate.now().minusMonths(1);
	        Object resultB = em.createQuery("SELECT COUNT(*) FROM TitoloDiViaggio e WHERE e.tipoTitolo = :biglietto and e.dataEmissione >= :data")
	                .setParameter("biglietto", tB)
	                .setParameter("data", d)
	                .getSingleResult();
	        System.out.println("\nSono stati emessi in totale nell'ultimo mese " + resultB + " biglietti.");
	        
	        TipoTitolo tM = TipoTitolo.MENSILE;
	        TipoTitolo tS = TipoTitolo.SETTIMANALE;
	        Object resultAbb = em.createQuery("SELECT COUNT(*) FROM TitoloDiViaggio e WHERE e.tipoTitolo = :mensile OR e.tipoTitolo = :settimanale and e.dataEmissione >= :data")
	                .setParameter("mensile", tM)
	                .setParameter("settimanale", tS)
	                .setParameter("data", d)
	                .getSingleResult();
	        System.out.println("Sono stati emessi in totale nell'ultimo mese " + resultAbb + " abbonamenti. \n");
	        Methods.attesa(1);
	    } catch (Exception x) {
	        System.out.println("Nessun abbonamento ancora emesso!");
	        Methods.attesa(1);
	        Main.opzioniGestore();
	    }
	}

	
	public static void cambiaStato(String pv, StatoServizio ss) {
		try {
			
			Biglietteria b = infoPuntoVenditaByID(pv,() -> Main.opzioniGestore());
			
			if(b.getStatoServizio()== ss) {
				System.out.println(colorize("Errore: lo stato precedente è uguale a quello selezionato!", BRIGHT_RED_TEXT()));
				Methods.attesa(1);
				Methods.modificaPV();
			}else {
				System.out.println(colorize("Modifica avvenuta correttamente!", BRIGHT_GREEN_TEXT()));
				b.setStatoServizio(ss);
				save(b);
			}

		}catch (Exception y) {
	        System.out.println("Errore durante la modifica!");
	        Main.opzioniGestore();
		}

	}
	
	
	public static Biglietteria infoPuntoVenditaByID(String id, Function ritornoSeSbagliato) {
		try { 				
			String Id = (id).toUpperCase();
	    	Biglietteria b = em.createQuery("SELECT i FROM Biglietteria i WHERE i.id = :id", Biglietteria.class)
	                .setParameter("id", Id)
	                .getSingleResult();
	        return b;
		} catch (NoResultException x) {
	        System.out.println(colorize("Punto vendita non trovato!",BRIGHT_RED_TEXT()));
	        ritornoSeSbagliato.call();
	        return null;
	    }
	}
	
	public static void salvaBiglietteria(ArrayList<Biglietteria> bList) {
		try {
			for (Biglietteria b : bList) {
				save(b);
			}
			System.out.println("Punti vendita salvati con successo!");
		} catch (Exception e) {
			System.out.println("Errore nel salvataggio.");
		}
	}
	
	public static void getListaPV() {
		System.out.println(colorize("==============================\nRIV = Rivenditori Autorizzati \nAUT = Distributori Automatici\n==============================", Decorazioni.ORANGE_TEXT));
		Methods.attesa(2);
		System.out.println("Lista di punti vendita attivi:");
		System.out.println("﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉");
		try { 			
			StatoServizio s = StatoServizio.ATTIVO;
	    	List<Biglietteria> big = em.createQuery("SELECT i FROM Biglietteria i WHERE i.statoServizio = :stato", Biglietteria.class)
	                .setParameter("stato", s)
	                .getResultList();
	    	int i = 1;
	        for(Biglietteria b : big) {
	        	System.out.println(i + ") " + "Codice Punto Vendita: [ " + b.getId() + " ]");
	        	i++;
	        }
	    System.out.println(" ");
		} catch (NoResultException x) {
	        System.out.println("Nessun punto vendita attivo trovato!");
	        Main.opzioniUtente();
	    }
	}
	
	public static void getListaPVCompleta() {
		System.out.println(colorize("==============================\nRIV = Rivenditori Autorizzati \nAUT = Distributori Automatici\n==============================", Decorazioni.ORANGE_TEXT));
		Methods.attesa(2);
		System.out.println("Lista di punti vendita attivi:");
		System.out.println("﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉");
		try { 			
			StatoServizio s = StatoServizio.ATTIVO;
	    	List<Biglietteria> big = em.createQuery("SELECT i FROM Biglietteria i", Biglietteria.class)
	                .getResultList();
	        for(Biglietteria b : big) {
	        	System.out.println("Codice Punto Vendita: ( " + b.getId() + " ) || Stato: [ " + (b.getStatoServizio() != StatoServizio.FUORI_SERVIZIO ? b.getStatoServizio() : "FUORI SERVIZIO") + " ]");
	        }
		} catch (NoResultException x) {
	        System.out.println(colorize("Nessun punto vendita attivo trovato!", BRIGHT_RED_TEXT()));
	        Main.opzioniUtente();
	    }
	}
}
