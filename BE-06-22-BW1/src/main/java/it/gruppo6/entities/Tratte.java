package it.gruppo6.entities;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Tratte {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String puntoPartenza;
    private String arrivo;
    private int tempoMedio;
    private int numeroVolteTrattoPercorso;
    @OneToMany
    private List<Veicoli> veicoli;
    
    //getter & setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPuntoPartenza() {
		return puntoPartenza;
	}
	public void setPuntoPartenza(String puntoPartenza) {
		this.puntoPartenza = puntoPartenza;
	}
	public String getArrivo() {
		return arrivo;
	}
	public void setArrivo(String arrivo) {
		this.arrivo = arrivo;
	}
	public int getTempoMedio() {
		return tempoMedio;
	}
	public void setTempoMedio(int tempoMedio) {
		this.tempoMedio = tempoMedio;
	}
	public int getNumeroVolteTrattoPercorso() {
		return numeroVolteTrattoPercorso;
	}
	public void setNumeroVolteTrattoPercorso(int numeroVolteTrattoPercorso) {
		this.numeroVolteTrattoPercorso = numeroVolteTrattoPercorso;
	}
	public List<Veicoli> getVeicoli() {
		return veicoli;
	}
	public void setVeicoli(List<Veicoli> veicoli) {
		this.veicoli = veicoli;
	}
	
	@Override
	public String toString() {
		return "Tratte [id=" + id + ", puntoPartenza=" + puntoPartenza + ", arrivo=" + arrivo + ", tempoMedio="
				+ tempoMedio + ", numeroVolteTrattoPercorso=" + numeroVolteTrattoPercorso + ", veicoli=" + veicoli
				+ "]";
	}
}
