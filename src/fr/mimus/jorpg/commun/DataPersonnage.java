package fr.mimus.jorpg.commun;

import java.io.Serializable;

import fr.mimus.jorpg.serveur.serveurThread;

public class DataPersonnage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// Information personnage
	public String nom;
	public int classe = 0;
	public int sprite = 0;
	public int money = 0;
	
	// Information positionnement
	public int x = 0;
	public int y = 0;
	public int map = 0;
	public int dir = 2;
	public int offsetX = 0;
	public int offsetY = 0;
	public int anim = 0;
	
	// Information Stat
	public int vie = 50;
	public int magie = 25;
	public int level = 1;
	public int exp = 0;
	public int force = 5;
	public int dexterite = 5;
	public int endurence = 5;
	public int energie = 5;
	public int pts = 0;
	
	// Inventaire
	public DataItemSlot[] inventaire = new DataItemSlot[32];
	public int[] equipement = new int[18];
	
	// Livre des Sorts
	public int[] sort = new int[18];
	
	// Livre de Quete
	public DataQuestBook[] quete = new DataQuestBook[10];
	public boolean[] queteList = new boolean[1000];
	
	public DataPersonnage() {
		for(int i = 0; i < inventaire.length; i++) {
			inventaire[i] = new DataItemSlot();
		}
		for(int i = 0; i < quete.length; i++) {
			quete[i] = new DataQuestBook();
		}
		for(int i = 0; i < equipement.length; i++) {
			equipement[i] = -1;
		}
		for(int i = 0; i < queteList.length; i++) {
			queteList[i] = false;
		}
		
		vie = getMaxVie();
		magie = getMaxMagie();
	}
	public int getMaxVie() {
		int add = 0;
		for(int i = 0; i < equipement.length; i++) {
			if(equipement[i] != -1) {
				DataItem di = serveurThread.item.get(inventaire[equipement[i]].id);
				add += di.vie;
			}
		}
		return (endurence*10 + level * 5 + add);
	}
	
	public int getMaxMagie() {
		int add = 0;
		for(int i = 0; i < equipement.length; i++) {
			if(equipement[i] != -1) {
				DataItem di = serveurThread.item.get(inventaire[equipement[i]].id);
				add += di.magie;
			}
		}
		return (energie*4 + level * 2 + add);
	}
	
	public int nextLevel() {
		return (50+level*100+(level-1)*25);
	}

}
