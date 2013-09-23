package fr.mimus.jorpg.commun;

import java.io.Serializable;

public class DataQuestBook implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public int id = -1;
	public int etape = 0;
	public int data[] = new int[5];
	
}
