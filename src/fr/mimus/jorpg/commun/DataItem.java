package fr.mimus.jorpg.commun;

import java.io.Serializable;

public class DataItem implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public String nom = "";
	public String desc = "";
	public int type = 0;
	public int dur = 0;
	public int pic = 0;
	
	public int dommage = 0;
	public int defense = 0;
	
	public boolean paperdoll = false;
	public int paperdollId = 0;
	public boolean jet = false;
	public int jetId = 0;
	
	public int prixVente = 0;
	
	// Requis
	public int levelReq = 0;
	public int forceReq = 0;
	public int dexteriteReq = 0;
	public int endurenceReq = 0;
	public int energieReq = 0;
	
	// Ajout/Bonus
	public int vie = 0;
	public int magie = 0;
	public int force = 0;
	public int dexterite = 0;
	public int endurence = 0;
	public int energie = 0;
	public int exp = 0;
			
}
