package fr.mimus.jorpg.commun;

import java.awt.Color;

public class MsgMap {
	public Color c;
	public String msg;
	public int x;
	public int y;
	public long time;
	public MsgMap(int rgb, String m, int a, int b) {
		c = new Color(rgb);
		msg = m;
		x = a;
		y = b;
		time = System.currentTimeMillis();
	}
}
