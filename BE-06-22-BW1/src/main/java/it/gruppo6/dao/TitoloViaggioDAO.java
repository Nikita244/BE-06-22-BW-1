package it.gruppo6.dao;

import java.time.LocalDate;
import java.util.ArrayList;

import it.gruppo6.app.Main;
import it.gruppo6.entities.Biglietteria;
import it.gruppo6.entities.Tessera;
import it.gruppo6.entities.TitoloDiViaggio;
import it.gruppo6.entities.Biglietteria.StatoServizio;
import it.gruppo6.entities.TitoloDiViaggio.TipoTitolo;
import it.gruppo6.util.JpaUtil;
import it.gruppo6.util.Methods;

public class TitoloViaggioDAO extends JpaUtil {

	public static void save(TitoloDiViaggio tv) {
		try {
			t.begin();
			em.persist(tv);
			t.commit();
		} catch (Exception x) {
			System.out.println("Ops! Qualcosa è andato storto...");
		}
	}

	public static TitoloDiViaggio acquistaTitoloViaggio(String numB, Tessera tessera, TipoTitolo tt,
			Biglietteria luogo) {
			try {
				TipoTitolo tipo = tt;
				LocalDate ora = LocalDate.now();
				TitoloDiViaggio tv = new TitoloDiViaggio();
				tv.setNumeroBiglietto(numB);
				tv.setTipoTitolo(tt);
				tv.setDataEmissione(LocalDate.now());
				tv.setLuogoEmissione(luogo);
				if(tipo.equals(TipoTitolo.BIGLIETTO)) {
					tv.setConvalidato(false);
				} else {
					tv.setConvalidato(true);
				}
				if (tipo == TipoTitolo.BIGLIETTO) {
					luogo.setQtyEmessa(luogo.getQtyEmessa() + 1);
					BiglietteriaDAO.save(luogo);
					return tv;
				}

				if (tessera.getDataScadenza().isBefore(ora)) {
					System.out.println("La tua tessera è scaduta!");
					return tv;
				}

				switch (tipo) {
				case SETTIMANALE: // return settimanale
					tessera.setTipologia(tt);
					tessera.setDataInizioAbb(ora);
					tessera.setDataScadenzaAbb(ora.plusWeeks(1));
					TesseraDAO.save(tessera);
					luogo.setQtyEmessa(luogo.getQtyEmessa() + 1);
					BiglietteriaDAO.save(luogo);
					Methods.attesa(1);
					System.out.println("Abbonamento settimanale ricaricato con successo!");
					Methods.attesa(1);
					break;
				case MENSILE: // return mensile
					tessera.setTipologia(tt);
					tessera.setDataInizioAbb(ora);
					tessera.setDataScadenzaAbb(ora.plusMonths(1));
					TesseraDAO.save(tessera);
					luogo.setQtyEmessa(luogo.getQtyEmessa() + 1);
					BiglietteriaDAO.save(luogo);
					Methods.attesa(1);
					System.out.println("Abbonamento mensile ricaricato con successo!");
					Methods.attesa(1);
					break;
				default:
					System.out.println("Tipo di abbonamento non supportato.");
					return null;
				}
				return tv;
			} catch (Exception e) {
				System.out.println("Si è verificato un errore");
				return null;
			}
	}

	public static void salvaTitoliDiViaggio(ArrayList<TitoloDiViaggio> tvList) {
		try {
			for (TitoloDiViaggio u : tvList) {
				save(u);
			}
			System.out.println("Titoli viaggio salvati con successo!");
		} catch (Exception e) {
			System.out.println("Errore nel salvataggio.");
		}
	}	
	
	public static TitoloDiViaggio getBigliettoById(String tvId) {
	    try {
	        TitoloDiViaggio result = em.createQuery("SELECT e FROM TitoloDiViaggio e WHERE e.numeroBiglietto = :id AND e.tipoTitolo = :tt AND e.convalidato = :boolean", TitoloDiViaggio.class)
	        		.setParameter("id", tvId)
	        		.setParameter("tt", TipoTitolo.BIGLIETTO)
	        		.setParameter("boolean", false)
	                .getSingleResult();
	        return result;
	    } catch (Exception x) {
	        System.out.println("Biglietto non trovato o già convalidato!");
	        TrattaDAO.eseguiTratta();
	        return null;
	}
	}
}
