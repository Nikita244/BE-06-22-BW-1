package it.gruppo6.entities;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Veicoli {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int capacita;
	private LocalDate aggiunta_al_parco;
	private LocalDate dataImmatricolazione;
	private long giorniTotaliServizio;
	private int bigliettiVidimati;
	
	@Enumerated(EnumType.STRING)
	private TipologiaVeicolo tipoVeicolo;

	@Enumerated(EnumType.STRING)
	private StatoManutenzione stato;
	
	@ManyToOne
	private Tratte tratta;
	
	//enums
	public enum TipologiaVeicolo {
		TRAM, BUS
	}
	public enum StatoManutenzione {
		COMPLETATO, MANUTENZIONE, IN_SERVIZIO
	}
	
	//getter & setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getCapacita() {
		return capacita;
	}
	public void setCapacita(int capacita) {
		this.capacita = capacita;
	}
	public LocalDate getAggiunta_al_parco() {
		return aggiunta_al_parco;
	}
	public void setAggiunta_al_parco(LocalDate aggiunta_al_parco) {
		this.aggiunta_al_parco = aggiunta_al_parco;
	}
	public LocalDate getDataImmatricolazione() {
		return dataImmatricolazione;
	}
	public void setDataImmatricolazione(LocalDate dataImmatricolazione) {
		this.dataImmatricolazione = dataImmatricolazione;
	}
	public long getGiorniTotaliServizio() {
		return giorniTotaliServizio;
	}
	public void setGiorniTotaliServizio(long giorniTotaliServizio) {
		this.giorniTotaliServizio = giorniTotaliServizio;
	}
	public int getBigliettiVidimati() {
		return bigliettiVidimati;
	}
	public void setBigliettiVidimati(int bigliettiVidimati) {
		this.bigliettiVidimati = bigliettiVidimati;
	}
	public TipologiaVeicolo getTipoVeicolo() {
		return tipoVeicolo;
	}
	public void setTipoVeicolo(TipologiaVeicolo tipoVeicolo) {
		this.tipoVeicolo = tipoVeicolo;
	}
	public StatoManutenzione getStato() {
		return stato;
	}
	public void setStato(StatoManutenzione stato) {
		this.stato = stato;
	}
	public Tratte getTratta() {
		return tratta;
	}
	public void setTratta(Tratte tratta) {
		this.tratta = tratta;
	}
	@Override
	public String toString() {
		return "Veicoli [id=" + id + ", capacita=" + capacita + ", aggiunta_al_parco=" + aggiunta_al_parco
				+ ", dataImmatricolazione=" + dataImmatricolazione + ", giorniTotaliServizio=" + giorniTotaliServizio
				+ ", bigliettiVidimati=" + bigliettiVidimati + ", tipoVeicolo=" + tipoVeicolo + ", stato=" + stato
				+ ", tratta=" + tratta + "]";
	}
}
