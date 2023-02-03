package it.gruppo6.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "biglietteria")
public class Biglietteria {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private TipoEnte tipoEnte;

    @Enumerated(EnumType.STRING)
    private StatoServizio statoServizio;
    
    private Integer qtyEmessa;
    

    // enums
    public enum TipoEnte {
        DISTRIBUTORE_AUTOMATICO, RIVENDITORE_AUTORIZZATO
    }

    public enum StatoServizio {
        ATTIVO, FUORI_SERVIZIO
    }

    
    //getter setter
    public TipoEnte getTipoEnte() {
		return tipoEnte;
	}

	public void setTipoEnte(TipoEnte tipoEnte) {
		this.tipoEnte = tipoEnte;
	}

	public StatoServizio getStatoServizio() {
		return statoServizio;
	}

	public String getId() {
		return id;
	}

	public void setId(String id2) {
		this.id = id2;
	}

	public void setStatoServizio(StatoServizio statoServizio) {
		this.statoServizio = statoServizio;
	}

	public int getQtyEmessa() {
		return qtyEmessa;
	}

	public void setQtyEmessa(int qtyEmessa) {
		this.qtyEmessa = qtyEmessa;
	}

	@Override
	public String toString() {
		return "Biglietteria [id=" + id + ", tipoEnte=" + tipoEnte + ", statoServizio=" + statoServizio + ", qtyEmessa="
				+ (qtyEmessa != null ? qtyEmessa : "0") + "]";
	}
	
	

    //da fare: bisogna dirgli che settimanali e mensili devono essere collegati al numero tessera senno non possono essere emessi, il biglietto puo essere emesso sempre e bisogna identificare se il distributore automatico o il rivenditore e sopratutto generare un codice univoco del titolo di viaggio

}
