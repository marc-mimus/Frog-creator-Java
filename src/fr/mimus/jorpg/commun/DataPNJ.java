package fr.mimus.jorpg.commun;

import java.io.Serializable;

public class DataPNJ implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public String nom = "";
	public String disc = "";
	public int type = 0;
	public int pic = 0;
	
	public int vie = 0;
	public int force = 0;
	public int dexterite = 0;
	public int endurence = 0;
	public int energie = 0;
	
	public int exp = 0;
	
	public int[] drop1 = new int[3];
	public int[] drop2 = new int[3];
	public int[] drop3 = new int[3];
}
