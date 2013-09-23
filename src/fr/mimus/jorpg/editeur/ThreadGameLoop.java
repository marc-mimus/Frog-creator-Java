package fr.mimus.jorpg.editeur;

import fr.mimus.jorpg.client.ClientBoard;

public class ThreadGameLoop extends Thread {
	EditeurMain cm;
	
	public ThreadGameLoop(EditeurMain c) {
		cm=c;
	}
	
	public void run(){
		long animetime = 0;
		while(true) {
			cm.repaint();
			cm.editeurItem.repaint();
			cm.editeurPnj.repaint();
			cm.editeurShop.repaint();
			/*EditeurMain.animTime++;
			if(EditeurMain.animTime > 1) {
				EditeurMain.animTime = 0;
			}*/
			if(getTime() - animetime > 250) {
				animetime = getTime();
				EditeurMain.animTime++;
				if(EditeurMain.animTime > 1) EditeurMain.animTime = 0;
			}
			//System.out.println(EditeurMain.animTime);
			try {
				Thread.sleep(1000/30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public static long getTime() {
	    return System.currentTimeMillis();
	}
}
