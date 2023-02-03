package it.gruppo6.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import it.gruppo6.entities.Veicoli.StatoManutenzione;

@Entity
public class Manutenzioni {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private StatoManutenzione stato;

	@Column(name = "data_inizio_manutenzione")
	private LocalDateTime dataInizioManutenzione;
	@Column(name = "data_fine_manutenzione")
	private LocalDateTime dataFineManutenzione;
	
	@ManyToOne
	private Veicoli veicolo;

	public StatoManutenzione getStato() {
		return stato;
	}

	public void setStato(StatoManutenzione stato) {
		this.stato = stato;
	}

	public Veicoli getVeicolo() {
		return veicolo;
	}

	public void setVeicolo(Veicoli veicolo) {
		this.veicolo = veicolo;
	}

	public LocalDateTime getDataInizioManutenzione() {
		return dataInizioManutenzione;
	}

	public void setDataInizioManutenzione(LocalDateTime dataInizioManutenzione) {
		this.dataInizioManutenzione = dataInizioManutenzione;
	}

	public LocalDateTime getDataFineManutenzione() {
		return dataFineManutenzione;
	}

	public void setDataFineManutenzione(LocalDateTime dataFineManutenzione) {
		this.dataFineManutenzione = dataFineManutenzione;
	}
}