package it.gruppo6.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Utente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String username;
	private String dataNascita;
	private String email;
	
	@OneToOne
	private Tessera numeroTessera;
	
	//getters & setters
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Tessera getNumeroTessera() {
		return numeroTessera;
	}
	public void setNumeroTessera(Tessera numeroTessera) {
		this.numeroTessera = numeroTessera;
	}
	
	@Override
	public String toString() {
		return "Utente [id=" + id + ", username=" + username + ", dataNascita=" + dataNascita + ", email="
				+ email + ", numeroTessera=" + numeroTessera + "]";
	}

	
}