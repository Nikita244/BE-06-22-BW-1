package it.gruppo6.dao;

import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import it.gruppo6.app.Main;
import it.gruppo6.entities.TitoloDiViaggio;
import it.gruppo6.entities.Tratte;
import it.gruppo6.entities.Veicoli;
import it.gruppo6.entities.Veicoli.StatoManutenzione;
import it.gruppo6.util.JpaUtil;
import it.gruppo6.util.Methods;
import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;

public class TrattaDAO extends JpaUtil {
	public static void save(Tratte tr) {
		try {
			t.begin();
			em.persist(tr);
			t.commit();
		} catch (Exception x) {
			System.out.println("Ops! Qualcosa è andato storto nel salvataggio della tratta.");
		}
	}
	
	// AGGIUNGO UN VEICOLO A UNA TRATTA
	public static void aggiungiVeicoloATratta() {
			Scanner myObj = new Scanner(System.in);
			try {
				EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
				em.getTransaction().begin();
				// INSERISCO L'ID DELLA TRATTA CHE VOGLIO
				System.out.println("Inserire l'id della tratta da assocciare al veicolo: ");
				long id_tratta;
				long id_veicolo;
				while (true) {
					try {
						id_tratta = Long.parseLong(myObj.nextLine());
						Tratte tratta = em.find(Tratte.class, id_tratta);
						if (tratta == null) {
							System.err.println("Tratta con id " + id_tratta + " inesistente");
							System.out.println("Inserire l'id della tratta: ");
							continue;
						}
						System.out.println("Inserire id veicolo: ");
						id_veicolo = Long.parseLong(myObj.nextLine());
						Veicoli veicolo = em.find(Veicoli.class, id_veicolo);
						if (veicolo == null) {
							System.err.println("Veicolo con id " + id_veicolo + " inesistente");
							System.out.println("Inserire l'id della tratta: ");
							continue;
						}
						if (veicolo.getTratta() != null) {
							System.err.println("Il veicolo con id " + id_veicolo + " è gia assegnato ad un'altra tratta");
							System.out.println("Inserire l'id della tratta: ");
							continue;
						}
						if (tratta.getVeicoli().contains(veicolo)) {
							System.err.println(
									"Il veicolo con id " + id_veicolo + " è gia assegnato alla tratta " + id_tratta);
							System.out.println("Inserire id tratta: ");
							continue;
						}
						tratta.getVeicoli().add(veicolo);
						veicolo.setTratta(tratta);
						System.out.println("Aggiunta in corso...");
						Methods.attesa(2);
						System.out.println(colorize("Aggiunto correttamente il veicolo con l'id: " + id_veicolo
								+ " alla tratta: " + tratta.getPuntoPartenza() + "-" + tratta.getArrivo(), BRIGHT_CYAN_TEXT()));
						em.merge(tratta);
						em.merge(veicolo);
						em.getTransaction().commit();
						em.close();
						Methods.attesa(1);
						Main.opzioniParcoMezzi();
						break;
					} catch (NumberFormatException e) {
						System.out.println("Devi inserire un numero.");
						aggiungiVeicoloATratta();
					}
				}
				// INSERISCO L'ID DEL VEICOLO
				// AGGIUNGO IL VEICOLO ALLA TRATTA
			} catch (Exception e) {
				t.rollback();
				System.out.println("Errore.");
				aggiungiVeicoloATratta();
				throw e;
			}
	}
	
	public static void eseguiTratta() {
		Scanner myObj = new Scanner(System.in);
		try {
			EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
			List<Tratte> tratte = em.createQuery("SELECT t FROM Tratte t", Tratte.class).getResultList();
			if (tratte.isEmpty()) {
				System.out.println("Nessuna tratta trovata");
				return;
			}
			 System.out.println("Caricando la lista delle tratte... \n");
			 Methods.attesa(2);
			 System.out.println(colorize("Lista delle Tratte e degli id dei veicoli", CYAN_TEXT(), BOLD()));
			 System.out.println(colorize("﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉ \n", CYAN_TEXT(), BOLD()));
		      for (int i = 0; i < tratte.size(); i++) {
		         Tratte tratta = tratte.get(i);
		         System.out.println(colorize("[Tratta " + (i + 1) + "]--------------", BOLD()));
		         System.out.println(colorize(tratta.getPuntoPartenza() + " - " + tratta.getArrivo() + "\n", CYAN_TEXT(), BOLD()));
		         System.out.println("I mezzi disponibili: ");
		         if(tratta.getVeicoli().isEmpty()) {
		        	 System.out.println(colorize("[i] Nessun mezzo disponibile per la tratta.", YELLOW_TEXT()));
		         }
		         for (Veicoli vehicle : tratta.getVeicoli()) {
		            System.out.println(colorize("ID: " + vehicle.getId() + " || " + vehicle.getTipoVeicolo() + " (" + (vehicle.getStato() == StatoManutenzione.IN_SERVIZIO ? "Disponibile": "Fuori servizio")+")", BRIGHT_CYAN_TEXT()));
		         }
		         System.out.println();
		         Methods.attesa(1);
		      }
		      System.out.println(colorize("------------------------", BOLD()));
		      Veicoli selectedVehicle = null;
		        while (selectedVehicle == null || !selectedVehicle.getStato().equals(StatoManutenzione.IN_SERVIZIO)) {
		            System.out.print("Inserisci l'id di un veicolo in servizio: ");
		            Long vehicleId = myObj.nextLong();
		            selectedVehicle = em.find(Veicoli.class, vehicleId);
		            if (selectedVehicle == null || !selectedVehicle.getStato().equals(StatoManutenzione.IN_SERVIZIO)) {
		                System.out.println("Veicolo non trovato o non in servizio. Si prega di inserire un altro veicolo.");
		            }
		        }
		      Tratte selectedTratta = selectedVehicle.getTratta();
		      selectedTratta.setNumeroVolteTrattoPercorso(selectedTratta.getNumeroVolteTrattoPercorso() + 1);
		      
		      
		      //INSERIRE CODICE VIDIMAZIONE BIGLIETTI
		      Scanner vid = new Scanner(System.in);
		      String input = "";
		    	try {
		    		System.out.println(colorize("Inserire il numero del biglietto da vidimare oppure scrivi (0) per tornare indietro.", BOLD()));
		    		input = vid.nextLine();
		    		if (input.equals("0")) {
		    			Main.opzioniParcoMezzi();
		    		}
		    		if (input != "0") {
		    			t.begin();
		    			TitoloDiViaggio biglietto = TitoloViaggioDAO.getBigliettoById(input);
		    			biglietto.setConvalidato(true);
		    			selectedVehicle.setBigliettiVidimati(selectedVehicle.getBigliettiVidimati() + 1);
		    			System.out.println(colorize("Biglietto validato correttamente! \n", BOLD(), BRIGHT_GREEN_TEXT()));
			  		      Methods.attesa(1);
		    			System.out.println("Il veicolo ha vidimato in totale: " + selectedVehicle.getBigliettiVidimati() + " biglietti \n");
		    			em.merge(selectedVehicle);
		    			em.merge(biglietto);
		    			t.commit();
		  		      em.getTransaction().begin();
		  		      em.persist(selectedTratta);
		  		      em.getTransaction().commit();
		  		      Methods.attesa(1);
		  		      System.out.println(colorize("Tratta avvenuta con successo!", BRIGHT_GREEN_TEXT()));
		  		      Methods.attesa(1);
		  		      Main.opzioniParcoMezzi();
		    		}
		    	} catch (Exception e) {
		    		System.err.println("Inserire un biglietto valido o non ancora convalidato: ");
		    		eseguiTratta();
		    	}


		} catch (Exception e) {
			System.out.println("Si prega di inserire un id valido.");
			eseguiTratta();
		}
	}
}
