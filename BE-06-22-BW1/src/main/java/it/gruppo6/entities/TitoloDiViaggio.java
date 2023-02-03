package it.gruppo6.entities;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class TitoloDiViaggio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String numeroBiglietto;
	
	@Enumerated(EnumType.STRING)
    private TipoTitolo tipoTitolo;
	@OneToOne
	private Biglietteria luogoEmissione;
	@Column (name = "Data_Emissione_Titolo_Viaggio")
	private LocalDate dataEmissione;
	private boolean convalidato;
	
	//enum
    public enum TipoTitolo {
    	BIGLIETTO, SETTIMANALE, MENSILE
    }

    //getter setter
	public String getNumeroBiglietto() {
		return numeroBiglietto;
	}

	public LocalDate getDataEmissione() {
		return dataEmissione;
	}

	public void setDataEmissione(LocalDate dataEmissione) {
		this.dataEmissione = dataEmissione;
	}

	public void setNumeroBiglietto(String numeroBiglietto) {
		this.numeroBiglietto = numeroBiglietto;
	}

	public TipoTitolo getTipoTitolo() {
		return tipoTitolo;
	}

	public void setTipoTitolo(TipoTitolo tipoTitolo) {
		this.tipoTitolo = tipoTitolo;
	}

	public Biglietteria getLuogoEmissione() {
		return luogoEmissione;
	}

	public void setLuogoEmissione(Biglietteria luogoEmissione) {
		this.luogoEmissione = luogoEmissione;
	}

	public boolean isConvalidato() {
		return convalidato;
	}

	public void setConvalidato(boolean validita) {
		this.convalidato = validita;
	}

	@Override
	public String toString() {
		return "TitoloDiViaggio: \n﹉﹉﹉﹉﹉﹉﹉﹉\n" + "Numero biglietto: " + numeroBiglietto + "\nTipo: " + tipoTitolo
				+ "\nLuogo Emissione: " + luogoEmissione.getId() + "\nData Emissione: " + dataEmissione;
	}
}
