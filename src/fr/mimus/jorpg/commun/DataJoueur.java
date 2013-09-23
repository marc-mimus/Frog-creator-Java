package fr.mimus.jorpg.commun;

import java.io.Serializable;

public class DataJoueur implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public String nom;
	public String pwd;
	
	public DataPersonnage perso[] = new DataPersonnage[3];
	
	public DataItemSlot[] banque = new DataItemSlot[32];
	
	public DataJoueur() { 
		perso[0] = new DataPersonnage();
		perso[1] = new DataPersonnage();
		perso[2] = new DataPersonnage();
	}
}
