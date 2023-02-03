package it.gruppo6.entities;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import it.gruppo6.entities.TitoloDiViaggio.TipoTitolo;

@Entity
public class Tessera {
	@Id
	private String id;
	
	@Column(name = "emissione_tessera")
	private LocalDate dataInizio;
	
	@Column(name = "scadenza_tessera")
	private LocalDate dataScadenza;
	
	@Column(name = "tipo_abbonamento")
	private TipoTitolo tipologia;
	
	@Column(name = "inizio_abbonamento")
	private LocalDate dataInizioAbb;
	
	@Column(name = "scadenza_abbonamento")
	private LocalDate dataScadenzaAbb;
	
	@OneToOne(mappedBy = "numeroTessera")
	@JoinColumn(name = "numeroTessera")
	private Utente utente;
	
	public LocalDate getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(LocalDate dataInizio) {
		this.dataInizio = dataInizio;
	}

	public String getId() {
		return id;
	}
	
	public void setDataScadenza(LocalDate dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDate getDataScadenza() {
		return dataScadenza;
	}
	
	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public LocalDate getDataScadenzaAbb() {
		return dataScadenzaAbb;
	}

	public void setDataScadenzaAbb(LocalDate dataScadenzaAbb) {
		this.dataScadenzaAbb = dataScadenzaAbb;
	}

	public LocalDate getDataInizioAbb() {
		return dataInizioAbb;
	}

	public void setDataInizioAbb(LocalDate dataInizioAbb) {
		this.dataInizioAbb = dataInizioAbb;
	}

	public TipoTitolo getTipologia() {
		return tipologia;
	}

	public void setTipologia(TipoTitolo tipologia) {
		this.tipologia = tipologia;
	}

	@Override
	public String toString() {
		return "[ Tessera ] \n\nNumero: " + id + "\nData Inizio: " + dataInizio + "\nData Scadenza: " + dataScadenza + "\nTipologia abbonamento: "
				+ (tipologia != null ? tipologia: "Nessun abbonamento") + "\nData InizioAbbonamento: " + (dataInizioAbb != null ? dataInizioAbb: "Nessun abbonamento") + "\nData Scadenza Abbonamento: " + (dataScadenzaAbb != null ? dataScadenzaAbb: "Nessun abbonamento");
	}
}
