package fr.mimus.jorpg.serveur;

import java.awt.Color;
import java.util.ArrayList;

import fr.mimus.jorpg.commun.DataCase;
import fr.mimus.jorpg.commun.DataPNJ;
import fr.mimus.jorpg.commun.DataPNJMap;
import fr.mimus.jorpg.commun.DataPersonnage;
import fr.mimus.jorpg.commun.DataTime;

public class ThreadGameLoop extends Thread {
	long npcTime = 0;
	long gameTime = 0;
	static boolean isNight = false;
	static DataTime timeGame = new DataTime();
	public java.util.Random rand = new java.util.Random();
	public ThreadGameLoop() {
		
	}
	public void run(){
		while(true) {
			if(serveurMain.dcs.isTime) {
				if(getTime() - gameTime > serveurMain.dcs.timeMinute) {
					gameTime = getTime();
					timeGame.time++;
					if(timeGame.time > serveurMain.dcs.timeDay) {
						// 24h écouler
						timeGame.time = 0;
						timeGame.jour++;
						System.out.println("Un jour est passé.");
					}
					if(timeGame.time == serveurMain.dcs.timeSun) {
						// Lever du jour
						serveurThread.sendToAll("journuit"+serveurThread.SEP+"1"+serveurThread.SEP+serveurThread.END);
						isNight = false;
						System.out.println("Le Soleil se lève.");
					}
					if(timeGame.time == serveurMain.dcs.timeNight) {
						// Coucher de soleil
						serveurThread.sendToAll("journuit"+serveurThread.SEP+"0"+serveurThread.SEP+serveurThread.END);
						isNight = true;
						System.out.println("Le Soleil se couche.");
					}
				}
			} else {
				if(isNight) {
					isNight = false;
					serveurThread.sendToAll("journuit"+serveurThread.SEP+"0"+serveurThread.SEP+serveurThread.END);
				}
			}
			if(getTime() - npcTime > 2000) {
				npcTime = getTime();
				// IA NPC
				for(int i = 0; i < serveurThread.pnjMap.size(); i++) {
					if(serveurThread.PlayerInMap(i)) {	
						for(int j = 0; j < serveurThread.pnjMap.get(i).size(); j++) {
							if(serveurThread.pnjMap.get(i).get(j).target == -1 || serveurThread.pnj.get(serveurThread.pnjMap.get(i).get(j).id).type == 0) {
								switch(rand.nextInt(6)-1) {
								case 0:
									serveurThread.pnjMap.get(i).get(j).dir = 0;
									if(!checkCollid(i, serveurThread.pnjMap.get(i).get(j).x, serveurThread.pnjMap.get(i).get(j).y-1)){
										serveurThread.pnjMap.get(i).get(j).y--;
										serveurThread.sendToMap(i, "movenpc" + serveurThread.SEP + j + serveurThread.SEP + 0 + serveurThread.SEP + serveurThread.END);
									} else {
										serveurThread.sendToMap(i, "nomovenpc" + serveurThread.SEP + j + serveurThread.SEP + 0 + serveurThread.SEP + serveurThread.END);
									}
									break;
								case 1:
									serveurThread.pnjMap.get(i).get(j).dir = 1;
									if(!checkCollid(i, serveurThread.pnjMap.get(i).get(j).x+1, serveurThread.pnjMap.get(i).get(j).y)){
										serveurThread.pnjMap.get(i).get(j).x++;
										serveurThread.sendToMap(i, "movenpc" + serveurThread.SEP + j + serveurThread.SEP + 1 + serveurThread.SEP + serveurThread.END);
									} else {
										serveurThread.sendToMap(i, "nomovenpc" + serveurThread.SEP + j + serveurThread.SEP + 1 + serveurThread.SEP + serveurThread.END);
									}
									break;
								case 2:
									serveurThread.pnjMap.get(i).get(j).dir = 2;
									if(!checkCollid(i, serveurThread.pnjMap.get(i).get(j).x, serveurThread.pnjMap.get(i).get(j).y+1)){
										serveurThread.pnjMap.get(i).get(j).y++;
										serveurThread.sendToMap(i, "movenpc" + serveurThread.SEP + j + serveurThread.SEP + 2 + serveurThread.SEP + serveurThread.END);
									} else {
										serveurThread.sendToMap(i, "nomovenpc" + serveurThread.SEP + j + serveurThread.SEP + 2 + serveurThread.SEP + serveurThread.END);
									}
									break;
								case 3:
									serveurThread.pnjMap.get(i).get(j).dir = 3;
									if(!checkCollid(i, serveurThread.pnjMap.get(i).get(j).x-1, serveurThread.pnjMap.get(i).get(j).y)){
										serveurThread.pnjMap.get(i).get(j).x--;
										serveurThread.sendToMap(i, "movenpc" + serveurThread.SEP + j + serveurThread.SEP + 3 + serveurThread.SEP + serveurThread.END);
									} else {
										serveurThread.sendToMap(i, "nomovenpc" + serveurThread.SEP + j + serveurThread.SEP + 3 + serveurThread.SEP + serveurThread.END);
									}
									break;
								}
							} else {
								DataPersonnage perso = serveurThread.Clients[serveurThread.pnjMap.get(i).get(j).target].joueur.perso[serveurThread.Clients[serveurThread.pnjMap.get(i).get(j).target].selectPersonnage];
								if(perso.map == i && serveurThread.Clients[serveurThread.pnjMap.get(i).get(j).target].inGame) {
									DataPNJMap pm = serveurThread.pnjMap.get(i).get(j);
									int diffx = (pm.x > perso.x)?pm.x-perso.x:perso.x-pm.x;
									int diffy = (pm.y > perso.y)?pm.y-perso.y:perso.y-pm.y;
									boolean move = false;
									if(diffx > diffy) {
										if(pm.x > perso.x) {
											if(pm.x-1 == perso.x && pm.y == perso.y){
												// Attack player code
												pnjAttackPlayer(i, j , pm.target);
												serveurThread.pnjMap.get(i).get(j).dir = 3;
												serveurThread.sendToMap(i, "nomovenpc" + serveurThread.SEP + j + serveurThread.SEP + serveurThread.pnjMap.get(i).get(j).dir + serveurThread.SEP + serveurThread.END);
											} else {
												serveurThread.pnjMap.get(i).get(j).dir = 3;
												move = true;
											}
										} else {
											if(pm.x+1 == perso.x && pm.y == perso.y){
												// Attack player code
												pnjAttackPlayer(i, j , pm.target);
												serveurThread.pnjMap.get(i).get(j).dir = 1;
												serveurThread.sendToMap(i, "nomovenpc" + serveurThread.SEP + j + serveurThread.SEP + serveurThread.pnjMap.get(i).get(j).dir + serveurThread.SEP + serveurThread.END);
											} else {
												serveurThread.pnjMap.get(i).get(j).dir = 1;
												move = true;
											}
										}
									} else {
										if(pm.y > perso.y) {
											if(pm.x == perso.x && pm.y-1 == perso.y){
												// Attack player code
												pnjAttackPlayer(i, j , pm.target);
												serveurThread.pnjMap.get(i).get(j).dir = 0;
												serveurThread.sendToMap(i, "nomovenpc" + serveurThread.SEP + j + serveurThread.SEP + serveurThread.pnjMap.get(i).get(j).dir + serveurThread.SEP + serveurThread.END);
											} else {
												serveurThread.pnjMap.get(i).get(j).dir = 0;
												move = true;
											}
										} else {
											if(pm.x == perso.x && pm.y-1 == perso.y){
												// Attack player code
												pnjAttackPlayer(i, j , pm.target);
												serveurThread.pnjMap.get(i).get(j).dir = 2;
												serveurThread.sendToMap(i, "nomovenpc" + serveurThread.SEP + j + serveurThread.SEP + serveurThread.pnjMap.get(i).get(j).dir + serveurThread.SEP + serveurThread.END);
											} else {
												serveurThread.pnjMap.get(i).get(j).dir = 2;
												move = true;
											}
										}
									}
									if(move) {
										switch(serveurThread.pnjMap.get(i).get(j).dir) {
										case 0:
											if(!checkCollid(i, serveurThread.pnjMap.get(i).get(j).x, serveurThread.pnjMap.get(i).get(j).y-1)){
												serveurThread.pnjMap.get(i).get(j).y--;
												serveurThread.sendToMap(i, "movenpc" + serveurThread.SEP + j + serveurThread.SEP + 0 + serveurThread.SEP + serveurThread.END);
											} else {
												serveurThread.sendToMap(i, "nomovenpc" + serveurThread.SEP + j + serveurThread.SEP + 0 + serveurThread.SEP + serveurThread.END);
											}
											break;
										case 1:
											if(!checkCollid(i, serveurThread.pnjMap.get(i).get(j).x+1, serveurThread.pnjMap.get(i).get(j).y)){
												serveurThread.pnjMap.get(i).get(j).x++;
												serveurThread.sendToMap(i, "movenpc" + serveurThread.SEP + j + serveurThread.SEP + 1 + serveurThread.SEP + serveurThread.END);
											} else {
												serveurThread.sendToMap(i, "nomovenpc" + serveurThread.SEP + j + serveurThread.SEP + 1 + serveurThread.SEP + serveurThread.END);
											}
											break;
										case 2:
											if(!checkCollid(i, serveurThread.pnjMap.get(i).get(j).x, serveurThread.pnjMap.get(i).get(j).y+1)){
												serveurThread.pnjMap.get(i).get(j).y++;
												serveurThread.sendToMap(i, "movenpc" + serveurThread.SEP + j + serveurThread.SEP + 2 + serveurThread.SEP + serveurThread.END);
											} else {
												serveurThread.sendToMap(i, "nomovenpc" + serveurThread.SEP + j + serveurThread.SEP + 2 + serveurThread.SEP + serveurThread.END);
											}
											break;
										case 3:
											if(!checkCollid(i, serveurThread.pnjMap.get(i).get(j).x-1, serveurThread.pnjMap.get(i).get(j).y)){
												serveurThread.pnjMap.get(i).get(j).x--;
												serveurThread.sendToMap(i, "movenpc" + serveurThread.SEP + j + serveurThread.SEP + 3 + serveurThread.SEP + serveurThread.END);
											} else {
												serveurThread.sendToMap(i, "nomovenpc" + serveurThread.SEP + j + serveurThread.SEP + 3 + serveurThread.SEP + serveurThread.END);
											}
											break;
										}
									}
								} else {
									serveurThread.pnjMap.get(i).get(j).target = -1;
								}
								
							}
						}
						if(serveurThread.pnjMap.get(i).size() == 0) {
							spawnAllPnjToMap(i);
						}
					}
				}
			}
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void pnjAttackPlayer(int map, int pnj, int player) {
		DataPNJMap pm = serveurThread.pnjMap.get(map).get(pnj);
		DataPNJ p = serveurThread.pnj.get(pm.id);
		int atk = p.force + p.dexterite/2;
		int def = serveurThread.Clients[player].getDef();
		int dmg = def - atk;
		if(dmg <= 0) dmg = 1;
		serveurThread.Clients[player].joueur.perso[serveurThread.Clients[player].selectPersonnage].vie -= dmg;
		String data = "msgmap"+serveurThread.SEP;
		data += Color.red.getRGB()+serveurThread.SEP;
		data += "-"+dmg+serveurThread.SEP;
		data += serveurThread.Clients[player].joueur.perso[serveurThread.Clients[player].selectPersonnage].x+serveurThread.SEP;
		data += serveurThread.Clients[player].joueur.perso[serveurThread.Clients[player].selectPersonnage].y+serveurThread.SEP+serveurThread.END;
		serveurThread.sendToMap(map, data);
		if(serveurThread.Clients[player].joueur.perso[serveurThread.Clients[player].selectPersonnage].vie <= 0) {
			serveurThread.Clients[player].joueur.perso[serveurThread.Clients[player].selectPersonnage].vie = serveurThread.Clients[player].joueur.perso[serveurThread.Clients[player].selectPersonnage].getMaxVie();
			// Code spawn
			serveurThread.Clients[player].joueur.perso[serveurThread.Clients[player].selectPersonnage].map = 0;
			serveurThread.Clients[player].joueur.perso[serveurThread.Clients[player].selectPersonnage].x = 5;
			serveurThread.Clients[player].joueur.perso[serveurThread.Clients[player].selectPersonnage].y = 5;
			DataPersonnage tempdp = serveurThread.Clients[player].joueur.perso[serveurThread.Clients[player].selectPersonnage];
			serveurThread.sendToAll("tp"+serveurThread.SEP+player+serveurThread.SEP+tempdp.map+serveurThread.SEP+tempdp.x+serveurThread.SEP+tempdp.y+serveurThread.SEP+serveurThread.END);
			DataPersonnage dp = serveurThread.Clients[player].joueur.perso[serveurThread.Clients[player].selectPersonnage];
			if(serveurThread.pnjMap.get(dp.map).size() > 0) {
				data = "clearnpc"+serveurThread.SEP+serveurThread.END;
				serveurThread.Clients[player].send(data);
				for(int i = 0; i < serveurThread.pnjMap.get(dp.map).size(); i++) {
					data = "spawnnpc" + serveurThread.SEP + i + serveurThread.SEP;
					data += serveurThread.pnjMap.get(dp.map).get(i).id + serveurThread.SEP + serveurThread.pnjMap.get(dp.map).get(i).x + serveurThread.SEP + serveurThread.pnjMap.get(dp.map).get(i).y + serveurThread.SEP + serveurThread.pnjMap.get(dp.map).get(i).dir + serveurThread.SEP + serveurThread.END;
					serveurThread.Clients[player].send(data);
				}
			}
			serveurThread.pnjMap.get(map).get(pnj).target = -1;
			int Xp = (int) (serveurThread.Clients[player].joueur.perso[serveurThread.Clients[player].selectPersonnage].exp - (serveurThread.Clients[player].joueur.perso[serveurThread.Clients[player].selectPersonnage].exp *.9));
			serveurThread.Clients[player].joueur.perso[serveurThread.Clients[player].selectPersonnage].exp = (int) (serveurThread.Clients[player].joueur.perso[serveurThread.Clients[player].selectPersonnage].exp *.9);
			data = "msgmap"+serveurThread.SEP;
			data += Color.red.getRGB()+serveurThread.SEP;
			data += "-"+Xp+"xp"+serveurThread.SEP;
			data += serveurThread.Clients[player].joueur.perso[serveurThread.Clients[player].selectPersonnage].x+serveurThread.SEP;
			data += serveurThread.Clients[player].joueur.perso[serveurThread.Clients[player].selectPersonnage].y+serveurThread.SEP+serveurThread.END;
			serveurThread.sendToMap(map, data);
		}
		data = "getstat"+serveurThread.SEP;
		data+= serveurThread.Clients[player].joueur.perso[serveurThread.Clients[player].selectPersonnage].vie + serveurThread.SEP;
		data+= serveurThread.Clients[player].joueur.perso[serveurThread.Clients[player].selectPersonnage].magie + serveurThread.SEP;
		data+= serveurThread.Clients[player].joueur.perso[serveurThread.Clients[player].selectPersonnage].level + serveurThread.SEP;
		data+= serveurThread.Clients[player].joueur.perso[serveurThread.Clients[player].selectPersonnage].exp + serveurThread.SEP;
		data+= serveurThread.Clients[player].joueur.perso[serveurThread.Clients[player].selectPersonnage].force + serveurThread.SEP;
		data+= serveurThread.Clients[player].joueur.perso[serveurThread.Clients[player].selectPersonnage].dexterite + serveurThread.SEP;
		data+= serveurThread.Clients[player].joueur.perso[serveurThread.Clients[player].selectPersonnage].endurence + serveurThread.SEP;
		data+= serveurThread.Clients[player].joueur.perso[serveurThread.Clients[player].selectPersonnage].energie + serveurThread.SEP;
		data+= serveurThread.Clients[player].joueur.perso[serveurThread.Clients[player].selectPersonnage].pts + serveurThread.SEP;
		data+= serveurThread.Clients[player].joueur.perso[serveurThread.Clients[player].selectPersonnage].money + serveurThread.SEP + serveurThread.END;
		serveurThread.Clients[player].send(data);
	}
	
	public void spawnAllPnjToMap(int map) {
		ArrayList<Integer[]> mobSpawn = spawner(map);
		for(int i = 0; i < mobSpawn.size(); i++) {
			DataPNJMap dpm = new DataPNJMap();
			dpm.id = mobSpawn.get(i)[2];
			dpm.x = mobSpawn.get(i)[0];
			dpm.y = mobSpawn.get(i)[1];
			dpm.vie = serveurThread.pnj.get(mobSpawn.get(i)[2]).vie;
			serveurThread.pnjMap.get(map).add(dpm);
		}
		if(mobSpawn.size()>0) System.out.println("Map #"+map+": Pnj créer ("+mobSpawn.size()+")");
	}
	
	public static void spawnPnjToMap(int map, int num) {
		ArrayList<Integer[]> mobSpawn = spawner(map);
		DataPNJMap dpm = new DataPNJMap();
		dpm.id = mobSpawn.get(num)[2];
		dpm.x = mobSpawn.get(num)[0];
		dpm.y = mobSpawn.get(num)[1];
		dpm.dir = 2;
		dpm.vie = serveurThread.pnj.get(mobSpawn.get(num)[2]).vie;
		serveurThread.pnjMap.get(map).set(num, dpm);
		String data = "spawnnpcone" + serveurThread.SEP + num + serveurThread.SEP;
		data += dpm.id + serveurThread.SEP + dpm.x + serveurThread.SEP + dpm.y + serveurThread.SEP + dpm.dir + serveurThread.SEP + serveurThread.END;
		serveurThread.sendToMap(map, data);
	}
	
	public static ArrayList<Integer[]> spawner(int map) {
		ArrayList<Integer[]> temp = new ArrayList<Integer[]>();
		for(int y = 0; y < serveurThread.map.get(map).height; y++) {
			for(int x = 0; x < serveurThread.map.get(map).width; x++) {
				DataCase dc = serveurThread.map.get(map).Case[x][y];
				if(dc.attribut == 3) {
					Integer[] t = {x,y,dc.data[0]};
					temp.add(t);
				}
			}
		}
		return temp;
	}
	
	public boolean checkCollid(int map, int x, int y) {
		if(y < 0) return true;
		if(x < 0) return true;
		if(y >= serveurThread.map.get(map).height) return true;
		if(x >= serveurThread.map.get(map).width) return true;
		if(serveurThread.map.get(map).Case[x][y].attribut == 1) return true;
		return false;
	}
	public long getTime() {
	    return System.currentTimeMillis();
	}
}
