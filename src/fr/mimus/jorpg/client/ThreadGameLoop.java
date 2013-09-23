package fr.mimus.jorpg.client;

import java.awt.event.KeyEvent;

public class ThreadGameLoop extends Thread {
	ClientMain cm;
	public java.util.Random rand = new java.util.Random();
	public static boolean botIsRun = false;
	public ThreadGameLoop(ClientMain c) {
		cm=c;
	}
	
	public void run(){
		long attacktime = 0;
		long animetime = 0;
		long moveTime = 0;
		long botTime = getTime();
		while(true) {
			cm.repaint();
			if(getTime() - botTime > 500 && botIsRun) {
				botTime = getTime();
				int rnd = rand.nextInt(6)-1;
				if(rnd >= 0 && rnd <= 3) {
					ClientMain.client.send("move"+ClientMain.client.SEP+rnd+ClientMain.client.SEP+ClientMain.client.END);
					ClientMain.client.isMoved = true;
				}
			}
			if(getTime() - moveTime > 120) {
				moveTime = getTime();
				for(int i = 0; i < ClientMain.joueur.length; i++) {
					if(ClientMain.joueur[i].nom != null) {
						if(ClientMain.joueur[i].nom.length() > 0) {
							if(ClientMain.joueur[i].offsetX < 0) ClientMain.joueur[i].offsetX += 8;
							if(ClientMain.joueur[i].offsetX > 0) ClientMain.joueur[i].offsetX -= 8;
							if(ClientMain.joueur[i].offsetY < 0) ClientMain.joueur[i].offsetY += 8;
							if(ClientMain.joueur[i].offsetY > 0) ClientMain.joueur[i].offsetY -= 8;
							if(ClientMain.joueur[i].offsetX == 0 && ClientMain.joueur[i].offsetY == 0) {
								if(i == ClientMain.client.myid) ClientMain.client.isMoved = false;
								 ClientMain.joueur[i].anim = 0;
							} else {
								ClientMain.joueur[i].anim++;
								if(ClientMain.joueur[i].anim > 3) ClientMain.joueur[i].anim = 0;
							}
						}
					}
				}
				
				for(int i = 0; i < ClientBoard.pnjMap.size(); i++) {
					if(ClientBoard.pnjMap.get(i).id >= 0) {
						if(ClientBoard.pnjMap.get(i).offsetX < 0) ClientBoard.pnjMap.get(i).offsetX += 8;
						if(ClientBoard.pnjMap.get(i).offsetX > 0) ClientBoard.pnjMap.get(i).offsetX -= 8;
						if(ClientBoard.pnjMap.get(i).offsetY < 0) ClientBoard.pnjMap.get(i).offsetY += 8;
						if(ClientBoard.pnjMap.get(i).offsetY > 0) ClientBoard.pnjMap.get(i).offsetY -= 8;
						if(ClientBoard.pnjMap.get(i).offsetX == 0 && ClientBoard.pnjMap.get(i).offsetY == 0) {
							ClientBoard.pnjMap.get(i).anim = 0;
						} else {
							ClientBoard.pnjMap.get(i).anim++;
							if(ClientBoard.pnjMap.get(i).anim > 3) ClientBoard.pnjMap.get(i).anim = 0;
						}
					}
				}
			}
			if(getTime() - animetime > 250) {
				animetime = getTime();
				ClientBoard.animTime++;
				if(ClientBoard.animTime > 1) ClientBoard.animTime = 0;
			}
			
			
			try {
				Thread.sleep(1000/120);
				if(getTime() - attacktime > 750) {
					attacktime = getTime();
					ClientMain.client.isAttack = false;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public static long getTime() {
	    return System.currentTimeMillis();
	}
}
