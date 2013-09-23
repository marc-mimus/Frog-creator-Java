package fr.mimus.jorpg.commun;

import java.io.Serializable;

public class eventCase implements Serializable {
	private static final long serialVersionUID = 1L;
	public int anim;
	public int frame = 0;
	public long time;
	public int attribut;
	public int data[] = new int[5];
}
