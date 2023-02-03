package it.gruppo6.dao;


import java.time.LocalDateTime;
import java.util.Scanner;
import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;
import it.gruppo6.app.Main;
import it.gruppo6.entities.Manutenzioni;
import it.gruppo6.entities.Veicoli;
import it.gruppo6.entities.Veicoli.StatoManutenzione;
import it.gruppo6.entities.Veicoli.TipologiaVeicolo;
import it.gruppo6.util.JpaUtil;
import it.gruppo6.util.Methods;


public class MezziDAO extends JpaUtil {

	public static void save(Veicoli v) {
		try {
			t.begin();
			em.persist(v);
			t.commit();
		} catch (Exception x) {
			System.out.println("Ops! Qualcosa è andato storto nel salvataggio del mezzo.");
			x.printStackTrace();
		}
	}

	public static void deleteObj(Veicoli object) {
		try {
			t.begin();
			em.remove(object);
			t.commit();
		} catch (Exception x) {
			System.out.println("Ops! Qualcosa è andato storto nell'eliminazione del mezzo");
		}
	}

	public static void persist(Object entity) {
		try {
			t.begin();
			em.merge(entity);
			t.commit();
		} catch (Exception x) {
			System.out.println("Ops! Qualcosa è andato storto.");
		}
	}

	public static void deleteById(Long id) {
		Veicoli m = em.find(Veicoli.class, id);
		if (m == null) {
			System.out.println("Il mezzo con l'id " + id + " non è stato trovato!");
			return;
		}
		deleteObj(m);
	}

	public static Veicoli getById(Long id) {
		try {
			return em.find(Veicoli.class, id);
		} finally {
			em.close();
		}
	}
	//----------------------------------------------------------------------------------//	

	public static void manutenzione(long id) {
		Manutenzioni m = em.find(Manutenzioni.class, id);
		Veicoli v = em.find(Veicoli.class, id);
		int ggManutenzione = 0;
		if (v.getTipoVeicolo() == TipologiaVeicolo.TRAM) {
			ggManutenzione = 20;
		} else if (v.getTipoVeicolo() == TipologiaVeicolo.BUS) {
			ggManutenzione = 10;
		}
		if ((v.getStato() == StatoManutenzione.COMPLETATO) || (v.getStato() == StatoManutenzione.IN_SERVIZIO)) {
			v.setStato(StatoManutenzione.MANUTENZIONE);
			System.out.println("Il veicolo " + id + " è in manutenzione");
			m.setDataInizioManutenzione(LocalDateTime.now());
			m.setDataFineManutenzione(LocalDateTime.now().plusDays(ggManutenzione));
		}
		else {
			v.setStato(StatoManutenzione.COMPLETATO);
			System.out.println("Il veicolo " + id + " è attivo");
			m.setDataInizioManutenzione(null);
			m.setDataFineManutenzione(null);
		}
		persist(v);
		ManutenzioneDAO.save(m);
	}
	
	public static void aggiungiAManutenzione() {
		Manutenzioni m = new Manutenzioni();
		Scanner myObj = new Scanner(System.in);
		System.out.println("Inserisci l'Id del veicolo da mettere in manutenzione: ");
		long id_veicolo;
		boolean veicoloTrovato = false;

		while (!veicoloTrovato) {
			try {
				// SELEZIONO L'ID DEL VEICOLO DA MANDARE IN MANUTENZIONE
				id_veicolo = Long.parseLong(myObj.nextLine());
				Veicoli veicolo = em.find(Veicoli.class, id_veicolo);

				if (veicolo != null) {
					if (veicolo.getStato().equals(StatoManutenzione.IN_SERVIZIO)) {
						// IMPOSTO LO STATO DEL VEICOLO IN MANUTENZIONE
						veicolo.setStato(StatoManutenzione.MANUTENZIONE);
						m.setVeicolo(veicolo);
						// IMPOSTO L'INIZIO DELLO STATO DELLA MANUTENZIONE AD OGGI
						m.setDataInizioManutenzione(LocalDateTime.now());
						System.out.println("Inserisci il numero di giorni di manutenzione previsti: ");
						// E IMPOSTO UN IPOTETICA DATA DI FINIE
						int days = Integer.parseInt(myObj.nextLine());
						m.setDataFineManutenzione(LocalDateTime.now().plusDays(days));
						// IMPOSTO LO STATO DEL VEICOLO NELLA TABELLA MANUTENZIONE
						m.setStato(StatoManutenzione.MANUTENZIONE);

						MezziDAO.save(veicolo);
						ManutenzioneDAO.save(m);
						Methods.attesa(1);
						System.out.println(colorize("Il veicolo è stato aggiunto alla manutenzione.", BRIGHT_GREEN_TEXT()));
						Methods.attesa(1);
						Main.opzioniParcoMezzi();
					} else {
						System.out.println("Inserisci l'Id del veicolo da mettere in manutenzione: ");
						System.err.println(colorize("Il veicolo è gia in manutenzione.", BRIGHT_RED_TEXT()));
						aggiungiAManutenzione();
					}
				} else {
					System.err.println("Veicolo non trovato!");
					aggiungiAManutenzione();
				}
			} catch (NumberFormatException e) {
				System.err.println("Deve essere inserito un numero!");
				System.out.println("Inserisci l'Id del veicolo da mettere in manutenzione: ");
			}
		}

		JpaUtil.getEntityManagerFactory().createEntityManager().close();
	}
	
	// RIMUOVO UN VEICOLO DALLA SUA MANUTENZIONE ALLA FINE DELLA DATA
	public static void rimuoviManutenzione() {
		Manutenzioni m = new Manutenzioni();
		Scanner myObj = new Scanner(System.in);
		// INSERISCO L'ID DEL VEICOLO DA RIMUOVERE DALLA MANUTENZIONE
		System.out.println("Inserisci l'Id veicolo da riportare in servizio: ");
		long id_veicolo;
		boolean veicoloTrovato = false;
		while (!veicoloTrovato) {
			try {
				id_veicolo = Long.parseLong(myObj.nextLine());
				Veicoli veicolo = em.find(Veicoli.class, id_veicolo);
				if (veicolo != null) {
					if (veicolo.getStato().equals(StatoManutenzione.MANUTENZIONE)) {
						veicolo.setStato(StatoManutenzione.IN_SERVIZIO);
						m.setVeicolo(veicolo);
						m.setDataFineManutenzione(LocalDateTime.now());
						// IMPOSTO LO STATO DEL VEICOLO NELLA TABELLA MANUTENZIONE
						m.setStato(StatoManutenzione.COMPLETATO);
						MezziDAO.save(veicolo);
						ManutenzioneDAO.save(m);
						veicoloTrovato = true;
						Methods.attesa(1);
						System.out.println(colorize("Il veicolo con Id: " + id_veicolo
								+ " ha terminato la manutenzione in data: " + LocalDateTime.now(), BOLD(), BRIGHT_CYAN_TEXT()));
						Methods.attesa(1);
						Main.opzioniParcoMezzi();
					} else {
						System.err.println(colorize("Il veicolo non è in manutenzione \n", BRIGHT_RED_TEXT(), BOLD()));
						Methods.attesa(1);
						Main.opzioniParcoMezzi();
						continue;
					}
				} else {
					System.err.println("Veicolo con Id: " + id_veicolo + " non trovato!");
					System.out.println("Inserisci Id veicolo: ");
					continue;
				}
			} catch (NumberFormatException e) {
				System.err.println("Devi inserire un numero.");
				rimuoviManutenzione();
			}
		}
		em.close();
	}
}

