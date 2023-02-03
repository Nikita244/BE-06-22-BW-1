package it.gruppo6.app;

import java.util.Scanner;
import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;
import it.gruppo6.dao.BiglietteriaDAO;
import it.gruppo6.dao.TrattaDAO;
import it.gruppo6.util.Methods;

public class Main {
	static Decorazioni c = new Decorazioni();
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		start();
		scan.close();
	}
	

	//----------------------------------------Apertura scanner----------------------------------------//

	public static void start() {

		int scelta;

		while (true) {
			Decorazioni.titolo();
			System.out.println(colorize("	Seleziona tra le seguenti opzioni per continuare: \n", BOLD()));
			System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
			System.out.println("║            ║             ║               ║                        ║"); 
			System.out.println("║ (1) Utente ║ (2) Gestore ║ (3) Logistica ║ (0) Termina esecuzione ║");
			System.out.println("║            ║             ║               ║                        ║"); 
			System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

			if (scan.hasNextInt()) {
				scelta = scan.nextInt();
				if (scelta == 1 || scelta == 2 || scelta == 3 || scelta == 0) {
					break;
				} else {
					System.out.println("Scelta non valida. Riprovare.");
				}
			} else { // se scelgono un decimale
				System.out.println("Inserire un numero intero valido.");
				scan.next(); // per pulire l'input
			}
		}

		switch (scelta) {
		case 1:
			opzioniUtente();
			break;
		case 2:
			opzioniGestore();
			break;
		case 3:
			opzioniParcoMezzi();
			break;
		case 0:
			System.out.println(colorize("Chiusura in corso..", BOLD()));
			Methods.attesa(3);
			System.out.println(colorize("Esecuzione del programma terminata.", BOLD(), BRIGHT_YELLOW_TEXT()));
			break;
		}
	}

	//----------------------------------------Interfaccia Utente----------------------------------------//

	public static void opzioniUtente() {
		int sceltaU;

		while (true) {
			System.out.println("Seleziona tra le seguenti opzioni per continuare: \n");
			System.out.println(colorize("﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉", BRIGHT_CYAN_TEXT(), BOLD()));
			System.out.println("(1) Acquista un biglietto \n(2) Registrati \n(3) Acquista un abbonamento \n(4) Controlla la tua tessera\n(5) Indietro");
			System.out.println(colorize("﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍", BRIGHT_CYAN_TEXT(), BOLD()));

			if (scan.hasNextInt()) {
				sceltaU = scan.nextInt();
				if (sceltaU >= 1 && sceltaU <= 5) {
					break;
				} else {
					System.out.println("Scelta non valida. Riprovare.");
				}
			} else {
				System.out.println("Inserire un numero intero valido.");
				scan.next();
			}
		}
		switch (sceltaU) {
		case 1:
			// acquista biglietto
			Methods.compraBiglietto();
			break;

		case 2:
			// resgistra nuova tessera
			Methods.creaUtente();
			break;

		case 3:
			// acquista abbonamento
			Methods.compraAbbonamento();
			break;

		case 4:
			// controlla la tua tessera
			Methods.controlloTesseraUtente();
			break;

		case 5:
			// indietro
			start();
			break;
		}
	}

	
	//----------------------------------------Interfaccia Gestore----------------------------------------//

	public static void opzioniGestore() {
		int sceltaG;

		while (true) {
			System.out.println("Seleziona tra le seguenti opzioni per continuare: \n ");
			System.out.println(colorize("﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉", YELLOW_TEXT(), BOLD()));
			System.out.println("(1) Aggiungi punto vendita \n(2) Modifica stato punto vendita \n(3) Controlla tessera utente \n(4) Controlla Biglietti/Abbonamenti emessi da tutti i punti vendita nell'ultimo mese \n(5) Controlla titoli di viaggio emessi da un punto vendita \n(6) Indietro");
			System.out.println(colorize("﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍", YELLOW_TEXT(), BOLD()));
			if (scan.hasNextInt()) {
				sceltaG = scan.nextInt();
				if (sceltaG >= 1 && sceltaG <= 6) {
					break;
				} else {
					System.out.println("Scelta non valida. Riprovare.");
				}
			} else { // se scelgono un decimale
				System.out.println("Inserire un numero intero valido.");
				scan.next(); // per pulire l'input
			}
		}

		switch (sceltaG) {
		case 1:
			// aggiungi punto vendita
			Methods.creaPuntoVendita();
			break;
		case 2:
			// cambio stato attivita punto vendita
			Methods.modificaPV();
			break;

		case 3:
			// controlla per tessera utente
			Methods.controlloTessera();
			break;

		case 4:
			// controlla il totale di biglietti e abbonamenti emessi nell'ultimo mese
			BiglietteriaDAO.totaleQtyEmessaPerTipo();
			opzioniGestore();
			break;

		case 5:
			// controllo titoli viaggio emessi da un punto vendita
			Methods.controllaEmissioniPv();
			opzioniGestore();
			break;
		case 6:
			// indietro
			start();
			break;
		}
	}
	
	//----------------------------------------Interfaccia ParcoMezzi----------------------------------------//
	public static void opzioniParcoMezzi() {
		int sceltaPM;
		while (true) {
			System.out.println("Seleziona tra le seguenti opzioni per continuare: \n ");
			System.out.println(colorize("﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉﹉", BRIGHT_GREEN_TEXT(), BOLD()));
			System.out.println("(1) Aggiungi un nuovo veicolo \n(2) Aggiungi una nuova tratta \n(3) Associa veicolo a tratta \n(4) Esegui corsa \n(5) Gestione manutenzione \n(6) Indietro");
			System.out.println(colorize("﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍", BRIGHT_GREEN_TEXT(), BOLD()));
			if (scan.hasNextInt()) {
				sceltaPM = scan.nextInt();
				if (sceltaPM >= 1 && sceltaPM <= 6) {
					break;
				} else {
					System.out.println(colorize("Scelta non valida. Riprovare.", BRIGHT_RED_TEXT()));
				}
			} else { // se scelgono un decimale
				System.out.println(colorize("Inserire un numero intero valido.", BRIGHT_RED_TEXT()));
				scan.next(); // per pulire l'input
			}
		}

		switch (sceltaPM) {
		case 1:
			//aggiunta di veicolo
			Methods.aggiungiVeicoli();
			opzioniParcoMezzi();
			break;
		case 2:
			Methods.aggiungiTratta();
			opzioniParcoMezzi();
			break;
		case 3:
			TrattaDAO.aggiungiVeicoloATratta();
			opzioniParcoMezzi();
			break;
		case 4:
			TrattaDAO.eseguiTratta();
			break;
		case 5:
			Methods.gestioneManutenzione();
			break;
		case 6:
			// indietro
			start();
			break;
		}
	}
}