package sn.ucad.esp.master;

import java.io.Serializable;

public class Utilisateur implements Serializable{
	
	private String nom;
	private String telephone;
	private String cle;

	public Utilisateur() {
		
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCle() {
		return cle;
	}

	public void setCle(String cle) {
		this.cle = cle;
	}

}
