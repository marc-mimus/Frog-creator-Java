package fr.mimus.jorpg.commun;

import java.io.Serializable;

public class DataCase implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public int sol[] = new int[2];
	public int masque[] = new int[4];
	public int frange[] = new int[4];
	public int solTile[] = new int[2];
	public int masqueTile[] = new int[4];
	public int frangeTile[] = new int[4];
	public int attribut;
	public boolean light;
	public int data[] = new int[5];
	public eventCase[] ev = new eventCase[2];
}
