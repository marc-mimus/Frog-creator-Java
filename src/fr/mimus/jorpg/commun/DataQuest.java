package fr.mimus.jorpg.commun;

import java.io.Serializable;
import java.util.ArrayList;

public class DataQuest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public String name = "";
	public String desc = "";
	
	public ArrayList<DataVal> mission;
	
	public int exp = 0;
	public ArrayList<DataItemSlot> objet;
	
	
	public DataQuest() {
		mission = new ArrayList<DataVal>();
		objet = new ArrayList<DataItemSlot>();
	}

}
