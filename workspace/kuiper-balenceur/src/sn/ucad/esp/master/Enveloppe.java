package sn.ucad.esp.master;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Enveloppe implements Serializable {

    private Message message;
    
    private Utilisateur emetteur;
    
    private Utilisateur destinataire;
    
    private List<String> lstUtilisateur=new ArrayList<String>();
    
    private String instruction;
    
    private String ext;
    
    
    private String image;
    
	public Enveloppe() {
		
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Utilisateur getEmetteur() {
		return emetteur;
	}

	public void setEmetteur(Utilisateur emetteur) {
		this.emetteur = emetteur;
	}

	public Utilisateur getDestinataire() {
		return destinataire;
	}

	public void setDestinataire(Utilisateur destinataire) {
		this.destinataire = destinataire;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public List<String> getLstUtilisateur() {
		return lstUtilisateur;
	}

	public void setLstUtilisateur(List<String> lstUtilisateur) {
		this.lstUtilisateur = lstUtilisateur;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

}