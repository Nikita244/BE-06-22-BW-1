package it.gruppo6.util;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import javax.persistence.NoResultException;
import it.gruppo6.app.Main;
import it.gruppo6.dao.BiglietteriaDAO;
import it.gruppo6.dao.MezziDAO;
import it.gruppo6.dao.TesseraDAO;
import it.gruppo6.dao.TitoloViaggioDAO;
import it.gruppo6.dao.TrattaDAO;
import it.gruppo6.dao.UtenteDAO;
import it.gruppo6.entities.Biglietteria;
import it.gruppo6.entities.Tessera;
import it.gruppo6.entities.TitoloDiViaggio;
import it.gruppo6.entities.Biglietteria.StatoServizio;
import it.gruppo6.entities.Biglietteria.TipoEnte;
import it.gruppo6.entities.TitoloDiViaggio.TipoTitolo;
import it.gruppo6.entities.Tratte;
import it.gruppo6.entities.Veicoli;
import it.gruppo6.entities.Veicoli.StatoManutenzione;
import it.gruppo6.entities.Veicoli.TipologiaVeicolo;
import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;

public class Methods {
	// ----------------------------------------Interfaccia Utente----------------------------------------//
	public static void compraBiglietto() {
		Scanner s = new Scanner(System.in);
		BiglietteriaDAO.getListaPV();
		System.out.println("Inserisci il codice del punto vendita: ");
		String sceltaP = s.nextLine();
		Biglietteria result = BiglietteriaDAO.infoPuntoVenditaByID(sceltaP, () -> compraBiglietto());
		if (result.getStatoServizio() == StatoServizio.FUORI_SERVIZIO) {
			System.out.println("Il punto vendita è fuori servizio..");
			Methods.attesa(1);
			compraBiglietto();
		}
		TitoloDiViaggio biglietto = TitoloViaggioDAO.acquistaTitoloViaggio(generaNum("B"), null, TipoTitolo.BIGLIETTO,
				result);
		TitoloViaggioDAO.save(biglietto);
		System.out.println(colorize("Dettagli biglietto acquistato: \n", BRIGHT_CYAN_TEXT()));
		System.out.println(biglietto.toString() + "\n");
		Methods.attesa(1);
		Main.opzioniUtente();
	}

	public static void creaUtente() {
		Scanner r = new Scanner(System.in);
		System.out.println("Inserisci il tuo nome e cognome: ");
		String sceltaNome = r.nextLine();
		System.out.println("Inserisci la tua data di nascita in formato YYYY-MM-DD: ");
		String sceltaData = r.nextLine();
		System.out.println("Inserisci la tua email: ");
		String sceltaAdd = r.nextLine();

		Tessera tessera = TesseraDAO.creaTessera(generaNum("UT"), LocalDate.now());
		TesseraDAO.save(tessera);
		UtenteDAO.save(UtenteDAO.creaUtente(sceltaNome, sceltaData, sceltaAdd, tessera));
		System.out.println("Creazione in corso... \n");
		Methods.attesa(2);
		System.out.print("Ecco il tuo nuovo numero di tessera: ");
		System.out.println(colorize(tessera.getId(),BRIGHT_GREEN_TEXT())); 
		Methods.attesa(1);
		System.out.println("Ora puoi acquistare abbonamenti settimanali e mensili!");
		Methods.attesa(2);
		Main.opzioniUtente();

	}

	public static void compraAbbonamento() {
		Scanner y = new Scanner(System.in);
		boolean flag = true;
		while (flag) {
			System.out.println("Inserisci il numero della tua tessera per continuare: \n ");
			String tesseraId = y.nextLine();

			try {
				Tessera tessera = TesseraDAO.getInfoByIdTesseraAbbonamento(tesseraId);
				System.out.println("\nCodice tessera: " + tessera.getId() + "\nAbbonamento: " + (tessera.getTipologia() != null ? tessera.getTipologia() : "Nessun abbonamento ancora presente sulla tessera \n"));
				Methods.attesa(2);
				BiglietteriaDAO.getListaPV();
				System.out.println("Inserisci il codice del punto vendita: ");
				String pvId = (y.nextLine()).toUpperCase();
				Biglietteria puntoVendita = BiglietteriaDAO.infoPuntoVenditaByID(pvId, () -> compraAbbonamento());
				if (puntoVendita.getStatoServizio() == StatoServizio.FUORI_SERVIZIO) {
					System.out.println(colorize("Il punto vendita è fuori servizio..", BRIGHT_RED_TEXT()));
					compraAbbonamento();
				}
				System.out.println("Digita per acquistare: (1) Abbonamento settimanale - (2) Abbonamento mensile");
				String tipoAbb = y.nextLine();

				if (tipoAbb.equals("1") || tipoAbb.equals("2")) {
					switch (tipoAbb) {
					case "1":
						// acquista settimanale
						if (tessera.getTipologia() == TipoTitolo.SETTIMANALE) {
							while(true) {
								System.out.println(
										"L'abbonamento settimanale è già presente, vuoi estendere la durata? [ Si - No ]");
								String risposta = (y.nextLine()).toUpperCase();
								if (risposta.equals("SI")) {
									tessera.setDataScadenzaAbb(tessera.getDataScadenzaAbb().plusWeeks(1));
									TesseraDAO.save(tessera);
									System.out.println("Ricarica in corso...");
									Methods.attesa(1);
									System.out.println("La durata dell'abbonamento settimanale è stata estesa.");
									Methods.attesa(1);
									Main.opzioniUtente();
									break;
								} else if (risposta.equals("NO")) {
									Main.opzioniUtente();
									break;
								} else {
									System.out.println("Risposta non valida");
								}
							}

						} else {
							TitoloDiViaggio s = TitoloViaggioDAO.acquistaTitoloViaggio(generaNum("S"), tessera,
									TipoTitolo.SETTIMANALE, puntoVendita);
							TitoloViaggioDAO.save(s);
							Methods.attesa(1);
							Main.opzioniUtente();
							break;
						}
						break;
					case "2":
						// acquista mensile
						if (tessera.getTipologia() == TipoTitolo.MENSILE) {
							while(true) {
								System.out.println(
										"L'abbonamento mensile è già presente, vuoi estendere la durata? [ Si - No ]");
								String risposta = (y.nextLine()).toUpperCase();
								if (risposta.equals("SI")) {
									tessera.setDataScadenzaAbb(tessera.getDataScadenzaAbb().plusMonths(1));
									TesseraDAO.save(tessera);
									System.out.println("La durata dell'abbonamento mensile è stata estesa.");
									Main.opzioniUtente();
									break;
								} else if (risposta.equals("NO")) {
									Main.opzioniUtente();
									break;
								} else {
									System.out.println("Risposta non valida");
								}
							}
						} else {
							TitoloDiViaggio m = TitoloViaggioDAO.acquistaTitoloViaggio(generaNum("M"), tessera,
									TipoTitolo.MENSILE, puntoVendita);
							TitoloViaggioDAO.save(m);
							Main.opzioniUtente();
							break;
						}
					}
				} else {
					System.out.println("Scelta non valida. Riprovare.");
					compraAbbonamento();
				}
				flag = false;

			} catch (NoResultException e) {
				System.out.println(colorize("La tessera non è stata trovata!", BRIGHT_RED_TEXT()));
				Methods.attesa(1);
				compraAbbonamento();
			}
		}
	}

	public static void controlloTesseraUtente() {
		Scanner ut = new Scanner(System.in);
		System.out.println("Inserisci il tuo numero di tessera da controllare: ");
		String sceltaT;
		sceltaT = ut.nextLine();
		System.out.println("Controllo in corso...");
		Methods.attesa(1);
		TesseraDAO.getInfoByIdTessera(sceltaT);
		Main.opzioniUtente();

	}

	// ----------------------------------------Interfaccia Gestore----------------------------------------//

	public static void controlloTessera() {
		Scanner in = new Scanner(System.in);
		System.out.println("Inserisci il numero di tessera da controllare: ");
		String sceltaT;
		sceltaT = in.nextLine();

		TesseraDAO.getInfoByIdTessera(sceltaT);
		Main.opzioniGestore();

	}

	public static void creaPuntoVendita() {
		Scanner pv = new Scanner(System.in);
		System.out.println(
				"Scegli (1) per creare Rivenditore Autorizzato\nScegli (2) per creare Distributore Automatico");
		String sceltaTipo = pv.nextLine();
		while (!sceltaTipo.equals("1") && !sceltaTipo.equals("2")) {
			System.out.println("Scelta non valida. Riprovare.");
			sceltaTipo = pv.nextLine();
		}
		switch (sceltaTipo) {
		case "1":
			System.out.println(colorize("Rivenditore autorizzato aggiunto con successo!", BRIGHT_GREEN_TEXT()));
			BiglietteriaDAO.savePV(BiglietteriaDAO.creaPuntoVendita(generaNum("RIV"), TipoEnte.RIVENDITORE_AUTORIZZATO));
			Methods.attesa(1);
			Main.opzioniGestore();
			break;
		case "2":
			System.out.println(colorize("Distributore automatico creato con successo!", BRIGHT_GREEN_TEXT()));
			BiglietteriaDAO.savePV(BiglietteriaDAO.creaPuntoVendita(generaNum("AUT"), TipoEnte.DISTRIBUTORE_AUTOMATICO));
			Methods.attesa(1);
			Main.opzioniGestore();
			break;
		}
	}

	public static void modificaPV() {

		Scanner mpv = new Scanner(System.in);
		BiglietteriaDAO.getListaPVCompleta();
		System.out.println("Inserisci il codice del punto vendita da modificare: ");
		String sceltaId = mpv.nextLine();
		BiglietteriaDAO.infoPuntoVenditaByID(sceltaId, () -> Methods.modificaPV());
		System.out.println("Digita (1) per attivare il punto vendita\nDigita (2) per disattivare il punto vendita");
		String sceltax = mpv.nextLine();
		while (!sceltax.equals("1") && !sceltax.equals("2")) {
			System.out.println(colorize("Scelta non valida. Riprovare.", BRIGHT_RED_TEXT()));
			sceltax = mpv.nextLine();
		}
		switch (sceltax) {
		case "1":
			BiglietteriaDAO.cambiaStato(sceltaId, StatoServizio.ATTIVO);
			attesa(1);
			Main.opzioniGestore();
			break;
		case "2":
			BiglietteriaDAO.cambiaStato(sceltaId, StatoServizio.FUORI_SERVIZIO);
			attesa(1);
			Main.opzioniGestore();
			break;
		}
	}

	public static void controllaEmissioniPv() {
		Scanner x= new Scanner(System.in);
		BiglietteriaDAO.getListaPVCompleta();
		System.out.println("Inserisci il codice identificativo del punto vendita");
		String sceltaId = x.nextLine();
		System.out.println("Controllo in corso..");
		attesa(1);
		System.out.println(colorize("\nPunto Vendita: "
				+ BiglietteriaDAO.infoPuntoVenditaByID(sceltaId, () -> Main.opzioniGestore()).getId()
				+ "\nQuantità di titolo di viaggio emesse: "
				+ BiglietteriaDAO.infoPuntoVenditaByID(sceltaId, () -> Main.opzioniGestore()).getQtyEmessa() + "\n", BRIGHT_CYAN_TEXT()));
	}
	// ----------------------------------------Interfaccia Parco Mezzi----------------------------------------//
	public static Veicoli aggiungiVeicoli() {
		Scanner myObj = new Scanner(System.in);
		Veicoli veicoli = new Veicoli();
		String sceltaTipo = "";
		int capacita = 0;
		while (!sceltaTipo.equals("1") && !sceltaTipo.equals("2")) {
			System.out.println("Si prega di inserire la tipologia del veicolo da aggiungere: (1) Tram - (2) Autobus");
			// SCELGO IL TIPO DI VEICOLO DA AGGIUNGERE
			sceltaTipo = myObj.nextLine();
		}
		if (sceltaTipo.equals("1")) {
			veicoli.setTipoVeicolo(TipologiaVeicolo.TRAM);
			capacita = 50;
		} else if (sceltaTipo.equals("2")) {
			veicoli.setTipoVeicolo(TipologiaVeicolo.BUS);
			capacita = 100;
		}
		String servizio = "";
		// SCELGO SE IL VEICOLO CHE STO AGGIUNGENDO AL PARCO MEZZI è NUOVO O USATO
		while (!servizio.equals("1") && !servizio.equals("2")) {
			System.out.println("Il mezzo è nuovo o usato? \n (1) Usato - (2) Nuovo");
			servizio = myObj.nextLine();
		}
		// SCELGO SE IL VEICOLO CHE STO AGGIUNGENDO AL PARCO MEZZI è NUOVO O USATO
		if (servizio.equals("1")) {
			// SE IL VEICOLO è USATO DEVO INSERIRE IL NUMERO DI GIORNI DI SERVIZIO GIA EFFETTUATI
			System.out.println("Inserire il numero di giorni di servizio gia effettuati");
			int data = Integer.parseInt(myObj.nextLine());
			LocalDate today = LocalDate.now();
			LocalDate passato = today.minusDays(data);
			veicoli.setAggiunta_al_parco(today);
			veicoli.setDataImmatricolazione(passato);
			// DURATION SI OCCUPA DI CALCOLARE IL NUMERO DI GIORNI CHE IL MEZZO HA GIA ESEGUITO DALL'IMMATRICOLAZIONE
			long passato1 = Duration.between(passato.atStartOfDay(), today.atStartOfDay()).toDays();
			veicoli.setGiorniTotaliServizio(passato1);
		} else if (servizio.equals("2")) {
			LocalDate today = LocalDate.now();
			veicoli.setAggiunta_al_parco(today);
			veicoli.setDataImmatricolazione(today);
		}
		veicoli.setCapacita(capacita);
		// AGGIUNGO LO STATO DEL MEZZO
		veicoli.setStato(StatoManutenzione.IN_SERVIZIO);
		MezziDAO.save(veicoli);
		System.out.println(colorize("Veicolo aggiunto con successo.",BRIGHT_GREEN_TEXT()));
		attesa(1);
		return veicoli;
		}
	
	// AGGIUNGO UNA TRATTA AL DATABASE
		public static Tratte aggiungiTratta() {
			Scanner myObj = new Scanner(System.in);
			Tratte tratta = new Tratte();
			// INSERISCO IL PUNTO DI PARTENZA
			System.out.println("Inserire il punto partenza: ");
			String puntopartenza = myObj.nextLine();
			tratta.setPuntoPartenza(puntopartenza);
			// INSERISCO IL PUNTO D'ARRIVO
			System.out.println("Inserire il punto di arrivo: ");
			String arrivo = myObj.nextLine();
			tratta.setArrivo(arrivo);
			// INSERISCO IL TEMPO PREVISTO DELLA TRATTA

			while(true) {
				System.out.println("Inserire il tempo di percorrenza medio della tratta: ");
				try {
					int tempoPercorrenzaPrevisto = Integer.parseInt(myObj.nextLine());
					tratta.setTempoMedio(tempoPercorrenzaPrevisto);
					// QUANDO AGGIUNGO UNA TRATTA PER LA PRIMA VOLTA IL NUMERO DI VOLTE IN CUI è STATA PERCORSA è 0
					tratta.setNumeroVolteTrattoPercorso(0);
					// IMPOSTO I VEICOLI A NULL
					TrattaDAO.save(tratta);
					System.out.println(colorize("Tratta aggiunta con successo!", BRIGHT_GREEN_TEXT()));
					attesa(1);
					break;
				} catch (Exception e) {
					System.out.println(colorize("Si prega di inserire un numero.", BRIGHT_RED_TEXT()));
				}
			}
			return tratta;
		}
		
		public static void gestioneManutenzione() {
			Scanner scan = new Scanner(System.in);
			int sceltaM;
			while (true) {
				System.out.println("Seleziona tra le seguenti opzioni: \n ");
				System.out.println(
						"(1) Aggiungi manutenzione a un veicolo \n(2) Rimuovi manutenzione a un veicolo");

				if (scan.hasNextInt()) {
					sceltaM = scan.nextInt();
					if (sceltaM >= 1 && sceltaM <= 2) {
						break;
					} else {
						System.out.println("Scelta non valida. Riprovare.");
					}
				} else { // se scelgono un decimale
					System.out.println("Inserire un numero intero valido.");
					scan.next(); // per pulire l'input
				}
			}

			switch (sceltaM) {
			case 1:
				MezziDAO.aggiungiAManutenzione();
				break;
			case 2:
				MezziDAO.rimuoviManutenzione();
				break;
		}
		}
	
	
		
	// ----------------------------------------Generatore di Numero----------------------------------------//
	public static String generaNum(String code) {
		HashSet<Integer> init = new HashSet<>();
		Random r = new Random();
		int min = 100000;
		int max = 999999;
		int n = 0;

		do {
			n = min + (int) (r.nextDouble() * (max - min));
		} while (init.contains(n));

		init.add(n);
		String result = code + n;

		return result;
	}

	// ------------------------------Richiamo di metodi nei parametri con lambda------------------------------//
	public interface Function {
		void call();
	}
	
	// ------------------------------Attesa------------------------------//
	public static void attesa(int sec) {
		int millisec = sec * 1000;
		try {
			Thread.sleep(millisec);
		} catch (InterruptedException e) {
			System.out.println("Errore Attesa");
		}
	}
}