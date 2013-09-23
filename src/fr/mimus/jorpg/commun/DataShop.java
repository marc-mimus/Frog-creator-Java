package fr.mimus.jorpg.commun;

import java.io.Serializable;
import java.util.ArrayList;

public class DataShop implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public String name = "";
	
	public ArrayList<Integer> article;
	public ArrayList<Integer> valeur;
	
	public DataShop() {
		article = new ArrayList<Integer>();
		valeur = new ArrayList<Integer>();
	}

}
