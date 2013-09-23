package fr.mimus.jorpg.commun;

import java.io.Serializable;

public class DataMap implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public String nom;
	public int height = 19;
	public int width = 20;
	public DataCase[][] Case;
	public int[] border = {-1,-1,-1,-1};
	
	public DataMap(){
		Case = new DataCase[width][height];
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				Case[x][y] = new DataCase();
			}
		}
	}
	public DataMap(int h, int w){
		height = h;
		width = w;
		Case = new DataCase[w][h];
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				Case[x][y] = new DataCase();
			}
		}
	}
}
