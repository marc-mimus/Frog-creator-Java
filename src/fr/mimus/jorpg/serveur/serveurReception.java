package fr.mimus.jorpg.serveur;

import java.awt.Color;
import java.io.File;

import fr.mimus.jorpg.commun.DataCase;
import fr.mimus.jorpg.commun.DataItem;
import fr.mimus.jorpg.commun.DataItemSlot;
import fr.mimus.jorpg.commun.DataJoueur;
import fr.mimus.jorpg.commun.DataLoader;
import fr.mimus.jorpg.commun.DataPNJ;
import fr.mimus.jorpg.commun.DataPNJMap;
import fr.mimus.jorpg.commun.DataPersonnage;

public class serveurReception extends Thread {
	
	private serveurClientSock classClient;
	private String packet[];
	
	public serveurReception(serveurClientSock c, String t[]){
		this.classClient = c;
		this.packet = t;
	}
	public void run() {
		if(this.packet[this.packet.length-1].equals(this.classClient.END)) {
			//String osName = System.getProperty("os.name");
			//os.name
			//user.name
			if(this.packet[0].equalsIgnoreCase("nc")) {
				if((new File("Serveur/Comptes/"+packet[1]+".ns")).exists()) {
					classClient.send("nc"+classClient.SEP+"0"+classClient.SEP+classClient.END);	
				} else {
					classClient.joueur.nom = packet[1];
					classClient.joueur.pwd = packet[2];
					DataLoader.saveJoueur(classClient.joueur);
					classClient.joueur = new DataJoueur();
					classClient.send("nc"+classClient.SEP+"1"+classClient.SEP+classClient.END);	
				}
						
			} else if(this.packet[0].equalsIgnoreCase("lc")) {
				if((new File("Serveur/Comptes/"+packet[1]+".ns")).exists()) {
					DataJoueur tempJ = DataLoader.loadJoueur(packet[1]);
					if(tempJ.pwd.equals(packet[2]) && !serveurThread.isConnectName(packet[1])) {
						classClient.joueur = tempJ;
						classClient.isConnect = true;
						classClient.send("youid"+classClient.SEP+classClient.idClient+classClient.SEP+classClient.END);	
						classClient.send("lc"+classClient.SEP+"1"+classClient.SEP+classClient.END);	
						if(classClient.joueur.perso[classClient.selectPersonnage].nom != null) {
							classClient.inGame = true;
							serveurThread.sendToAll("spawn"+classClient.SEP+classClient.idClient+classClient.SEP+classClient.joueur.perso[classClient.selectPersonnage].nom+classClient.SEP+classClient.joueur.perso[classClient.selectPersonnage].map+classClient.SEP+classClient.joueur.perso[classClient.selectPersonnage].x+classClient.SEP+classClient.joueur.perso[classClient.selectPersonnage].y+classClient.SEP+classClient.joueur.perso[classClient.selectPersonnage].sprite+classClient.SEP+classClient.END);
							serveurThread.sendToMeAll(classClient.idClient);
							classClient.send("pg"+classClient.SEP+classClient.END);
							String data = "getstat"+classClient.SEP;
							data+= classClient.joueur.perso[classClient.selectPersonnage].vie + classClient.SEP;
							data+= classClient.joueur.perso[classClient.selectPersonnage].magie + classClient.SEP;
							data+= classClient.joueur.perso[classClient.selectPersonnage].level + classClient.SEP;
							data+= classClient.joueur.perso[classClient.selectPersonnage].exp + classClient.SEP;
							data+= classClient.joueur.perso[classClient.selectPersonnage].force + classClient.SEP;
							data+= classClient.joueur.perso[classClient.selectPersonnage].dexterite + classClient.SEP;
							data+= classClient.joueur.perso[classClient.selectPersonnage].endurence + classClient.SEP;
							data+= classClient.joueur.perso[classClient.selectPersonnage].energie + classClient.SEP;
							data+= classClient.joueur.perso[classClient.selectPersonnage].pts + classClient.SEP;
							data+= classClient.joueur.perso[classClient.selectPersonnage].money + classClient.SEP + classClient.END;
							classClient.send(data);
							serveurThread.spawnPlayer(classClient.idClient);
						} else {
							classClient.send("cp"+classClient.SEP+classClient.END);
						}
					} else {
						classClient.send("lc"+classClient.SEP+"0"+classClient.SEP+classClient.END);	
					}
				} else {
					classClient.send("lc"+classClient.SEP+"0"+classClient.SEP+classClient.END);	
				}
			} else if(classClient.isConnect && !classClient.inGame) {
				if(this.packet[0].equalsIgnoreCase("cp")) {
					serveurThread.reloadPersoName();
					if(!serveurThread.isPersoExist(packet[1])) {
						classClient.joueur.perso[classClient.selectPersonnage] = new DataPersonnage();
						classClient.joueur.perso[classClient.selectPersonnage].nom = packet[1];
						classClient.inGame = true;
						serveurThread.sendToAll("spawn"+classClient.SEP+classClient.idClient+classClient.SEP+classClient.joueur.perso[classClient.selectPersonnage].nom+classClient.SEP+classClient.joueur.perso[classClient.selectPersonnage].map+classClient.SEP+classClient.joueur.perso[classClient.selectPersonnage].x+classClient.SEP+classClient.joueur.perso[classClient.selectPersonnage].y+classClient.SEP+classClient.joueur.perso[classClient.selectPersonnage].sprite+classClient.SEP+classClient.END);
						serveurThread.sendToMeAll(classClient.idClient);
						classClient.send("pg"+classClient.SEP+classClient.END);
						String data = "getstat"+classClient.SEP;
						data+= classClient.joueur.perso[classClient.selectPersonnage].vie + classClient.SEP;
						data+= classClient.joueur.perso[classClient.selectPersonnage].magie + classClient.SEP;
						data+= classClient.joueur.perso[classClient.selectPersonnage].level + classClient.SEP;
						data+= classClient.joueur.perso[classClient.selectPersonnage].exp + classClient.SEP;
						data+= classClient.joueur.perso[classClient.selectPersonnage].force + classClient.SEP;
						data+= classClient.joueur.perso[classClient.selectPersonnage].dexterite + classClient.SEP;
						data+= classClient.joueur.perso[classClient.selectPersonnage].endurence + classClient.SEP;
						data+= classClient.joueur.perso[classClient.selectPersonnage].energie + classClient.SEP;
						data+= classClient.joueur.perso[classClient.selectPersonnage].pts + classClient.SEP;
						data+= classClient.joueur.perso[classClient.selectPersonnage].money + classClient.SEP + classClient.END;
						classClient.send(data);
						serveurThread.spawnPlayer(classClient.idClient);
					} else {
						classClient.send("nocp"+classClient.SEP+classClient.END);
					}
				} else {
					System.out.println("("+this.classClient.clientIP+") tentative de hack(Packet inconnu).");
					System.out.println("> " + this.packet);
					this.classClient.disconnect();
				}
			} else if(classClient.inGame) {
				if(this.packet[0].equalsIgnoreCase("move")) {
					int dir = Integer.parseInt(packet[1]);
					boolean move = true;
					boolean tpBorder = false;
					classClient.joueur.perso[classClient.selectPersonnage].dir = dir;
					switch(dir) {
					case 0:
						if(classClient.joueur.perso[classClient.selectPersonnage].y-1 < 0) {
							move = false;
							if(serveurThread.map.get(classClient.joueur.perso[classClient.selectPersonnage].map).border[0] > -1) {
								tpBorder = true;
							}
						} else {
							if(serveurThread.map.get(classClient.joueur.perso[classClient.selectPersonnage].map).Case[classClient.joueur.perso[classClient.selectPersonnage].x][classClient.joueur.perso[classClient.selectPersonnage].y-1].attribut == 1) {
								move = false;
							}
							if(collidPNJ(classClient.joueur.perso[classClient.selectPersonnage].map, classClient.joueur.perso[classClient.selectPersonnage].x, classClient.joueur.perso[classClient.selectPersonnage].y-1)) {
								move = false;
							}
						}
						break;
					case 1:
						if(classClient.joueur.perso[classClient.selectPersonnage].x+1 >= serveurThread.map.get(classClient.joueur.perso[classClient.selectPersonnage].map).width) {
							move = false;
							if(serveurThread.map.get(classClient.joueur.perso[classClient.selectPersonnage].map).border[1] > -1) {
								tpBorder = true;
							}
						} else {
							if(serveurThread.map.get(classClient.joueur.perso[classClient.selectPersonnage].map).Case[classClient.joueur.perso[classClient.selectPersonnage].x+1][classClient.joueur.perso[classClient.selectPersonnage].y].attribut == 1) {
								move = false;
							}
							if(collidPNJ(classClient.joueur.perso[classClient.selectPersonnage].map, classClient.joueur.perso[classClient.selectPersonnage].x+1, classClient.joueur.perso[classClient.selectPersonnage].y)) {
								move = false;
							}
						}
						break;
					case 2:
						if(classClient.joueur.perso[classClient.selectPersonnage].y+1 >= serveurThread.map.get(classClient.joueur.perso[classClient.selectPersonnage].map).height) {
							move = false;
							if(serveurThread.map.get(classClient.joueur.perso[classClient.selectPersonnage].map).border[2] > -1) {
								tpBorder = true;
							}
						} else {
							if(serveurThread.map.get(classClient.joueur.perso[classClient.selectPersonnage].map).Case[classClient.joueur.perso[classClient.selectPersonnage].x][classClient.joueur.perso[classClient.selectPersonnage].y+1].attribut == 1) {
								move = false;
							}
							if(collidPNJ(classClient.joueur.perso[classClient.selectPersonnage].map, classClient.joueur.perso[classClient.selectPersonnage].x, classClient.joueur.perso[classClient.selectPersonnage].y+1)) {
								move = false;
							}
						}
						break;
					case 3: 
						if(classClient.joueur.perso[classClient.selectPersonnage].x-1 < 0) {
							move = false;
							if(serveurThread.map.get(classClient.joueur.perso[classClient.selectPersonnage].map).border[3] > -1) {
								tpBorder = true;
							}
						} else {
							if(serveurThread.map.get(classClient.joueur.perso[classClient.selectPersonnage].map).Case[classClient.joueur.perso[classClient.selectPersonnage].x-1][classClient.joueur.perso[classClient.selectPersonnage].y].attribut == 1) {
								move = false;
							}
							if(collidPNJ(classClient.joueur.perso[classClient.selectPersonnage].map, classClient.joueur.perso[classClient.selectPersonnage].x-1, classClient.joueur.perso[classClient.selectPersonnage].y)) {
								move = false;
							}
						}
						break;
					}
					if(move) {
						switch(dir) {
						case 0:
							classClient.joueur.perso[classClient.selectPersonnage].y--;
							break;
						case 1:
							classClient.joueur.perso[classClient.selectPersonnage].x++;
							break;
						case 2:
							classClient.joueur.perso[classClient.selectPersonnage].y++;
							break;
						case 3: 
							classClient.joueur.perso[classClient.selectPersonnage].x--;
							break;
						}
						serveurThread.sendToAll("move"+classClient.SEP+classClient.idClient+classClient.SEP+dir+classClient.SEP+classClient.END);
						
						if(serveurThread.map.get(classClient.joueur.perso[classClient.selectPersonnage].map).Case[classClient.joueur.perso[classClient.selectPersonnage].x][classClient.joueur.perso[classClient.selectPersonnage].y].attribut == 2) {
							if(serveurThread.map.get(classClient.joueur.perso[classClient.selectPersonnage].map).Case[classClient.joueur.perso[classClient.selectPersonnage].x][classClient.joueur.perso[classClient.selectPersonnage].y].data[0] < serveurThread.map.size()) {
								DataCase dc = serveurThread.map.get(classClient.joueur.perso[classClient.selectPersonnage].map).Case[classClient.joueur.perso[classClient.selectPersonnage].x][classClient.joueur.perso[classClient.selectPersonnage].y];
								classClient.joueur.perso[classClient.selectPersonnage].map = dc.data[0];
								classClient.joueur.perso[classClient.selectPersonnage].x = dc.data[1];
								classClient.joueur.perso[classClient.selectPersonnage].y = dc.data[2];
								//System.out.println(dc.data[0]+":"+dc.data[1]+","+dc.data[2]);
								//System.out.println(classClient.joueur.perso[classClient.selectPersonnage].map+":"+classClient.joueur.perso[classClient.selectPersonnage].x+","+classClient.joueur.perso[classClient.selectPersonnage].y);
								serveurThread.sendToAll("spawn"+classClient.SEP+classClient.idClient+classClient.SEP+classClient.joueur.perso[classClient.selectPersonnage].nom+classClient.SEP+classClient.joueur.perso[classClient.selectPersonnage].map+classClient.SEP+classClient.joueur.perso[classClient.selectPersonnage].x+classClient.SEP+classClient.joueur.perso[classClient.selectPersonnage].y+classClient.SEP+classClient.joueur.perso[classClient.selectPersonnage].sprite+classClient.SEP+classClient.END);
								classClient.send("clearnpc"+classClient.SEP+classClient.END);
								serveurThread.spawnPlayer(classClient.idClient);
							}
						}
						if(serveurThread.map.get(classClient.joueur.perso[classClient.selectPersonnage].map).Case[classClient.joueur.perso[classClient.selectPersonnage].x][classClient.joueur.perso[classClient.selectPersonnage].y].attribut == 4) {
							classClient.send("openshop"+classClient.SEP+serveurThread.map.get(classClient.joueur.perso[classClient.selectPersonnage].map).Case[classClient.joueur.perso[classClient.selectPersonnage].x][classClient.joueur.perso[classClient.selectPersonnage].y].data[0]+classClient.SEP+classClient.END);
						}
					
					} else {
						if(!tpBorder) {
							serveurThread.sendToAll("nomove"+classClient.SEP+classClient.idClient+classClient.SEP+dir+classClient.SEP+classClient.END);	
						} else {
							switch(dir) {
							case 0:
								classClient.joueur.perso[classClient.selectPersonnage].map = serveurThread.map.get(classClient.joueur.perso[classClient.selectPersonnage].map).border[0];
								classClient.joueur.perso[classClient.selectPersonnage].y = serveurThread.map.get(classClient.joueur.perso[classClient.selectPersonnage].map).height-1;
								break;
							case 1:
								classClient.joueur.perso[classClient.selectPersonnage].map = serveurThread.map.get(classClient.joueur.perso[classClient.selectPersonnage].map).border[1];
								classClient.joueur.perso[classClient.selectPersonnage].x = 0;
								break;
							case 2:
								classClient.joueur.perso[classClient.selectPersonnage].map = serveurThread.map.get(classClient.joueur.perso[classClient.selectPersonnage].map).border[2];
								classClient.joueur.perso[classClient.selectPersonnage].y = 0;
								break;
							case 3: 
								classClient.joueur.perso[classClient.selectPersonnage].map = serveurThread.map.get(classClient.joueur.perso[classClient.selectPersonnage].map).border[3];
								classClient.joueur.perso[classClient.selectPersonnage].x = serveurThread.map.get(classClient.joueur.perso[classClient.selectPersonnage].map).width-1;
								break;
							}
							serveurThread.sendToAll("spawn"+classClient.SEP+classClient.idClient+classClient.SEP+classClient.joueur.perso[classClient.selectPersonnage].nom+classClient.SEP+classClient.joueur.perso[classClient.selectPersonnage].map+classClient.SEP+classClient.joueur.perso[classClient.selectPersonnage].x+classClient.SEP+classClient.joueur.perso[classClient.selectPersonnage].y+classClient.SEP+classClient.joueur.perso[classClient.selectPersonnage].sprite+classClient.SEP+classClient.END);
							classClient.send("clearnpc"+classClient.SEP+classClient.END);
							serveurThread.spawnPlayer(classClient.idClient);
						}
					}
				} else if(this.packet[0].equalsIgnoreCase("action")) {
					if(serveurThread.itemMap.get(classClient.joueur.perso[classClient.selectPersonnage].map)[classClient.joueur.perso[classClient.selectPersonnage].x][classClient.joueur.perso[classClient.selectPersonnage].y].id >= 0) {
						DataItemSlot dis = serveurThread.itemMap.get(classClient.joueur.perso[classClient.selectPersonnage].map)[classClient.joueur.perso[classClient.selectPersonnage].x][classClient.joueur.perso[classClient.selectPersonnage].y];
						// TODO A modifier
						int slotLibre = idLibreInInv(dis);
						if(slotLibre > -1) {
							DataItem di = serveurThread.item.get(dis.id);
							if(di.type == 17) {
								if(classClient.joueur.perso[classClient.selectPersonnage].inventaire[slotLibre].id > -1) {
									classClient.joueur.perso[classClient.selectPersonnage].inventaire[slotLibre].quantite += dis.quantite;
								} else {
									classClient.joueur.perso[classClient.selectPersonnage].inventaire[slotLibre].id = dis.id;
									classClient.joueur.perso[classClient.selectPersonnage].inventaire[slotLibre].quantite = dis.quantite;
									classClient.joueur.perso[classClient.selectPersonnage].inventaire[slotLibre].durabilite = dis.durabilite;
								}
							} else {
								classClient.joueur.perso[classClient.selectPersonnage].inventaire[slotLibre].id = dis.id;
								classClient.joueur.perso[classClient.selectPersonnage].inventaire[slotLibre].quantite = dis.quantite;
								classClient.joueur.perso[classClient.selectPersonnage].inventaire[slotLibre].durabilite = dis.durabilite;
							}
							DataPersonnage dp = classClient.joueur.perso[classClient.selectPersonnage];
							String data = "inv"+classClient.SEP;
							for(int i = 0; i < dp.inventaire.length; i++) {
								data += dp.inventaire[i].id+classClient.SEP+dp.inventaire[i].quantite+classClient.SEP+dp.inventaire[i].durabilite+classClient.SEP;
							}
							data += classClient.END;
							classClient.send(data);
							serveurThread.itemMap.get(dp.map)[dp.x][dp.y] = new DataItemSlot();
							serveurThread.sendItemMapToInMap(classClient.joueur.perso[classClient.selectPersonnage].map);
						} else {
							String data = "chat"+classClient.SEP;
							data += "(INFO) Pas de place dans l'inventaire"+classClient.SEP;
							data += classClient.END;
							classClient.send(data);
						}
					}
				} else if(this.packet[0].equalsIgnoreCase("attack")) {
					if(serveurClientSock.getTime() - serveurClientSock.attackTime > 750) {
						serveurClientSock.attackTime = serveurClientSock.getTime();
						int ax = 0;
						int ay = 0;
						switch(classClient.joueur.perso[classClient.selectPersonnage].dir ) {
						case 0:
							if(classClient.joueur.perso[classClient.selectPersonnage].y > 0) {
								ax = classClient.joueur.perso[classClient.selectPersonnage].x;
								ay = classClient.joueur.perso[classClient.selectPersonnage].y-1;
							}
							break;
						case 1:
							if(classClient.joueur.perso[classClient.selectPersonnage].x < serveurThread.map.get(classClient.joueur.perso[classClient.selectPersonnage].map).width) {
								ax = classClient.joueur.perso[classClient.selectPersonnage].x+1;
								ay = classClient.joueur.perso[classClient.selectPersonnage].y;
							}
							break;
						case 2:
							if(classClient.joueur.perso[classClient.selectPersonnage].y < serveurThread.map.get(classClient.joueur.perso[classClient.selectPersonnage].map).height) {
								ax = classClient.joueur.perso[classClient.selectPersonnage].x;
								ay = classClient.joueur.perso[classClient.selectPersonnage].y+1;
							}
							break;
						case 3:
							if(classClient.joueur.perso[classClient.selectPersonnage].x > 0) {
								ax = classClient.joueur.perso[classClient.selectPersonnage].x-1;
								ay = classClient.joueur.perso[classClient.selectPersonnage].y;
							}
							break;
						}
						for(int i = 0; i < serveurThread.Clients.length; i++) {
							DataPersonnage dp = serveurThread.Clients[i].joueur.perso[classClient.selectPersonnage];
							if(dp.x == ax && dp.y == ay) {
								int atk = classClient.getDmg();
								int def = serveurThread.Clients[i].getDef();
								int dmg = def - atk;
								if(dmg <= 0) dmg = 1;
								serveurThread.Clients[i].joueur.perso[classClient.selectPersonnage].vie -= dmg;
								if(serveurThread.Clients[i].joueur.perso[classClient.selectPersonnage].vie <= 0) {
									serveurThread.Clients[i].joueur.perso[classClient.selectPersonnage].vie = serveurThread.Clients[i].joueur.perso[classClient.selectPersonnage].getMaxVie();
									// Code spawn
									serveurThread.Clients[i].joueur.perso[classClient.selectPersonnage].map = 0;
									serveurThread.Clients[i].joueur.perso[classClient.selectPersonnage].x = 5;
									serveurThread.Clients[i].joueur.perso[classClient.selectPersonnage].y = 5;
									DataPersonnage tempdp = serveurThread.Clients[i].joueur.perso[classClient.selectPersonnage];
									serveurThread.sendToAll("tp"+classClient.SEP+i+classClient.SEP+tempdp.map+classClient.SEP+tempdp.x+classClient.SEP+tempdp.y+classClient.SEP+classClient.END);
									if(serveurThread.pnjMap.get(dp.map).size() > 0) {
										String data = "clearnpc"+serveurThread.SEP+serveurThread.END;
										serveurThread.Clients[i].send(data);
										data = "spawnnpc" + serveurThread.SEP + serveurThread.pnjMap.get(dp.map).size() + serveurThread.SEP;
										for(int j = 0; j < serveurThread.pnjMap.get(dp.map).size(); j++) {
											data += serveurThread.pnjMap.get(dp.map).get(j).id + serveurThread.SEP + serveurThread.pnjMap.get(dp.map).get(j).x + serveurThread.SEP + serveurThread.pnjMap.get(dp.map).get(j).y + serveurThread.SEP + serveurThread.pnjMap.get(dp.map).get(j).dir + serveurThread.SEP;
										}
										data += serveurThread.END;
										serveurThread.Clients[i].send(data);
									}
								}
								String data = "getstat"+classClient.SEP;
								data+= serveurThread.Clients[i].joueur.perso[classClient.selectPersonnage].vie + classClient.SEP;
								data+= serveurThread.Clients[i].joueur.perso[classClient.selectPersonnage].magie + classClient.SEP;
								data+= serveurThread.Clients[i].joueur.perso[classClient.selectPersonnage].level + classClient.SEP;
								data+= serveurThread.Clients[i].joueur.perso[classClient.selectPersonnage].exp + classClient.SEP;
								data+= serveurThread.Clients[i].joueur.perso[classClient.selectPersonnage].force + classClient.SEP;
								data+= serveurThread.Clients[i].joueur.perso[classClient.selectPersonnage].dexterite + classClient.SEP;
								data+= serveurThread.Clients[i].joueur.perso[classClient.selectPersonnage].endurence + classClient.SEP;
								data+= serveurThread.Clients[i].joueur.perso[classClient.selectPersonnage].energie + classClient.SEP;
								data+= serveurThread.Clients[i].joueur.perso[classClient.selectPersonnage].pts + classClient.SEP;
								data+= serveurThread.Clients[i].joueur.perso[classClient.selectPersonnage].money + classClient.SEP + classClient.END;
								serveurThread.Clients[i].send(data);
								data = "msgmap"+serveurThread.SEP;
								data += Color.red.getRGB()+serveurThread.SEP;
								data += "-"+dmg+serveurThread.SEP;
								data += serveurThread.Clients[i].joueur.perso[classClient.selectPersonnage].x+serveurThread.SEP;
								data += serveurThread.Clients[i].joueur.perso[classClient.selectPersonnage].y+serveurThread.SEP+serveurThread.END;
								serveurThread.sendToMap(serveurThread.Clients[i].joueur.perso[classClient.selectPersonnage].map, data);
							}
						}
						for(int i = 0; i < serveurThread.pnjMap.get(classClient.joueur.perso[classClient.selectPersonnage].map).size(); i++) {
							DataPNJMap pm = serveurThread.pnjMap.get(classClient.joueur.perso[classClient.selectPersonnage].map).get(i);
							DataPNJ p = serveurThread.pnj.get(pm.id);
							if(pm.x == ax && pm.y == ay) {
								if(p.type == 0) {
									String data = "chat"+classClient.SEP;
									data += "(PNJ)"+p.nom+": "+p.disc+classClient.SEP;
									data += classClient.END;
									classClient.send(data);
								} else if(p.type == 1) {
									int atk = classClient.joueur.perso[classClient.selectPersonnage].force + classClient.joueur.perso[classClient.selectPersonnage].dexterite/2;
									int def = p.dexterite + p.endurence/2;
									int dmg = def - atk;
									if(dmg <= 0) dmg = 1;
									serveurThread.pnjMap.get(classClient.joueur.perso[classClient.selectPersonnage].map).get(i).vie -= dmg;
									serveurThread.pnjMap.get(classClient.joueur.perso[classClient.selectPersonnage].map).get(i).target = classClient.idClient;
									String data = "msgmap"+serveurThread.SEP;
									data += Color.red.getRGB()+serveurThread.SEP;
									data += "-"+dmg+serveurThread.SEP;
									data += serveurThread.pnjMap.get(classClient.joueur.perso[classClient.selectPersonnage].map).get(i).x+serveurThread.SEP;
									data += serveurThread.pnjMap.get(classClient.joueur.perso[classClient.selectPersonnage].map).get(i).y+serveurThread.SEP+serveurThread.END;
									serveurThread.sendToMap(classClient.joueur.perso[classClient.selectPersonnage].map, data);
									//System.out.println(serveurThread.pnjMap.get(classClient.joueur.perso[classClient.selectPersonnage].map).get(i).vie);
									if(serveurThread.pnjMap.get(classClient.joueur.perso[classClient.selectPersonnage].map).get(i).vie <= 0) {
										classClient.joueur.perso[classClient.selectPersonnage].exp += p.exp;
										if(classClient.joueur.perso[classClient.selectPersonnage].exp >= classClient.joueur.perso[classClient.selectPersonnage].nextLevel()) {
											classClient.joueur.perso[classClient.selectPersonnage].exp -= classClient.joueur.perso[classClient.selectPersonnage].nextLevel();
											classClient.joueur.perso[classClient.selectPersonnage].pts++;
											classClient.joueur.perso[classClient.selectPersonnage].level++;
										}
										
										data = "msgmap"+serveurThread.SEP;
										data += Color.yellow.getRGB()+serveurThread.SEP;
										data += "+"+p.exp+"xp"+serveurThread.SEP;
										data += serveurThread.pnjMap.get(classClient.joueur.perso[classClient.selectPersonnage].map).get(i).x+serveurThread.SEP;
										data += serveurThread.pnjMap.get(classClient.joueur.perso[classClient.selectPersonnage].map).get(i).y+serveurThread.SEP+serveurThread.END;
										serveurThread.sendToMap(classClient.joueur.perso[classClient.selectPersonnage].map, data);
										
										data = "getstat"+classClient.SEP;
										data+= classClient.joueur.perso[classClient.selectPersonnage].vie + classClient.SEP;
										data+= classClient.joueur.perso[classClient.selectPersonnage].magie + classClient.SEP;
										data+= classClient.joueur.perso[classClient.selectPersonnage].level + classClient.SEP;
										data+= classClient.joueur.perso[classClient.selectPersonnage].exp + classClient.SEP;
										data+= classClient.joueur.perso[classClient.selectPersonnage].force + classClient.SEP;
										data+= classClient.joueur.perso[classClient.selectPersonnage].dexterite + classClient.SEP;
										data+= classClient.joueur.perso[classClient.selectPersonnage].endurence + classClient.SEP;
										data+= classClient.joueur.perso[classClient.selectPersonnage].energie + classClient.SEP;
										data+= classClient.joueur.perso[classClient.selectPersonnage].pts + classClient.SEP;
										data+= classClient.joueur.perso[classClient.selectPersonnage].money + classClient.SEP + classClient.END;
										classClient.send(data);
										
										// NPC
										int de = (new java.util.Random()).nextInt(101);
										if(de <= p.drop1[0]) {
											serveurThread.itemMap.get(classClient.joueur.perso[classClient.selectPersonnage].map)[pm.x][pm.y].id = p.drop1[1];
											serveurThread.itemMap.get(classClient.joueur.perso[classClient.selectPersonnage].map)[pm.x][pm.y].quantite = p.drop1[2];
											serveurThread.itemMap.get(classClient.joueur.perso[classClient.selectPersonnage].map)[pm.x][pm.y].durabilite = serveurThread.item.get(p.drop1[1]).dur;
											serveurThread.sendItemMapToInMap(classClient.joueur.perso[classClient.selectPersonnage].map);
										}
										//serveurThread.sendToMap(classClient.joueur.perso[classClient.selectPersonnage].map, "removenpc"+serveurThread.SEP+i+serveurThread.SEP+serveurThread.END);
										//serveurThread.pnjMap.get(classClient.joueur.perso[classClient.selectPersonnage].map).remove(i);
										ThreadGameLoop.spawnPnjToMap(classClient.joueur.perso[classClient.selectPersonnage].map, i);
									}
								}
							}
						}
					}
				} else if(this.packet[0].equalsIgnoreCase("getstat")) {
					String data = "getstat"+classClient.SEP;
					data+= classClient.joueur.perso[classClient.selectPersonnage].vie + classClient.SEP;
					data+= classClient.joueur.perso[classClient.selectPersonnage].magie + classClient.SEP;
					data+= classClient.joueur.perso[classClient.selectPersonnage].level + classClient.SEP;
					data+= classClient.joueur.perso[classClient.selectPersonnage].exp + classClient.SEP;
					data+= classClient.joueur.perso[classClient.selectPersonnage].force + classClient.SEP;
					data+= classClient.joueur.perso[classClient.selectPersonnage].dexterite + classClient.SEP;
					data+= classClient.joueur.perso[classClient.selectPersonnage].endurence + classClient.SEP;
					data+= classClient.joueur.perso[classClient.selectPersonnage].energie + classClient.SEP;
					data+= classClient.joueur.perso[classClient.selectPersonnage].pts + classClient.SEP;
					data+= classClient.joueur.perso[classClient.selectPersonnage].money + classClient.SEP + classClient.END;
					classClient.send(data);
				} else if(this.packet[0].equalsIgnoreCase("say")) {
					serveurThread.sendToAll("chat" + classClient.SEP + classClient.joueur.perso[classClient.selectPersonnage].nom+": "+this.packet[1] + classClient.SEP + classClient.END);
				} else if(this.packet[0].equalsIgnoreCase("inv")) {
					DataPersonnage dp = classClient.joueur.perso[classClient.selectPersonnage];
					String data = "oinv"+classClient.SEP;
					for(int i = 0; i < dp.inventaire.length; i++) {
						data += dp.inventaire[i].id+classClient.SEP+dp.inventaire[i].quantite+classClient.SEP+dp.inventaire[i].durabilite+classClient.SEP;
					}
					data += classClient.END;
					classClient.send(data);
				} else if(this.packet[0].equalsIgnoreCase("useitem")) {
					DataPersonnage dp = classClient.joueur.perso[classClient.selectPersonnage];
					int id = Integer.parseInt(packet[1]);
					if(dp.inventaire[id].id > -1) {
						DataItem di = serveurThread.item.get(dp.inventaire[id].id);
						String data;
						if(di.type < 17) { // Equipement
							if(di.forceReq <= dp.force && di.dexteriteReq <= dp.dexterite && di.endurenceReq <= dp.endurence && di.energieReq <= dp.energie && di.levelReq <= dp.level) {
								if(dp.equipement[di.type] == id) {
									dp.equipement[di.type] = -1;
								} else {
									dp.equipement[di.type] = id;
								}
								data = "stuff"+classClient.SEP;
								for(int i = 0; i < dp.equipement.length; i++) {
									data += dp.equipement[i]+classClient.SEP;
								}
								data += classClient.END;
								classClient.send(data);
							} else {
								classClient.send("chat"+classClient.SEP+"(INFO) Vous ne remplissez pas les conditions de l'objet."+classClient.SEP+classClient.END);
							}
							
						} else if(di.type == 17) { // Potion
							// Code Potion
							if(di.levelReq <= dp.level) {
								dp.vie += di.vie;
								if(dp.vie > dp.getMaxVie()) dp.vie = dp.getMaxVie();
								dp.magie += di.magie;
								if(dp.magie > dp.getMaxMagie()) dp.magie = dp.getMaxMagie();
								if(dp.inventaire[id].quantite > 1) {
									dp.inventaire[id].quantite--;
								} else {
									dp.inventaire[id].id = -1;
								}
							} else {
								classClient.send("chat"+classClient.SEP+"(INFO) Vous n'avez pas le niveau pour cette objet."+classClient.SEP+classClient.END);
							}
							data = "inv"+classClient.SEP;
							for(int i = 0; i < dp.inventaire.length; i++) {
								data += dp.inventaire[i].id+classClient.SEP+dp.inventaire[i].quantite+classClient.SEP+dp.inventaire[i].durabilite+classClient.SEP;
							}
							data += classClient.END;
							classClient.send(data);
							data = "getstat"+classClient.SEP;
							data+= classClient.joueur.perso[classClient.selectPersonnage].vie + classClient.SEP;
							data+= classClient.joueur.perso[classClient.selectPersonnage].magie + classClient.SEP;
							data+= classClient.joueur.perso[classClient.selectPersonnage].level + classClient.SEP;
							data+= classClient.joueur.perso[classClient.selectPersonnage].exp + classClient.SEP;
							data+= classClient.joueur.perso[classClient.selectPersonnage].force + classClient.SEP;
							data+= classClient.joueur.perso[classClient.selectPersonnage].dexterite + classClient.SEP;
							data+= classClient.joueur.perso[classClient.selectPersonnage].endurence + classClient.SEP;
							data+= classClient.joueur.perso[classClient.selectPersonnage].energie + classClient.SEP;
							data+= classClient.joueur.perso[classClient.selectPersonnage].pts + classClient.SEP;
							data+= classClient.joueur.perso[classClient.selectPersonnage].money + classClient.SEP + classClient.END;
							classClient.send(data);
						}
					}
				} else if(this.packet[0].equalsIgnoreCase("jeteritem")) {
					DataPersonnage dp = classClient.joueur.perso[classClient.selectPersonnage];
					int id = Integer.parseInt(packet[1]);
					if(idStuff(id) == -1){
						if(serveurThread.itemMap.get(dp.map)[dp.x][dp.y].id == -1) {
							DataItemSlot dis = new DataItemSlot();
							dis.id = dp.inventaire[id].id;
							dis.quantite = dp.inventaire[id].quantite;
							dis.durabilite = dp.inventaire[id].durabilite;
							serveurThread.itemMap.get(dp.map)[dp.x][dp.y] = dis;
							dp.inventaire[id] = new DataItemSlot();
							String data = "inv"+classClient.SEP;
							for(int i = 0; i < dp.inventaire.length; i++) {
								data += dp.inventaire[i].id+classClient.SEP+dp.inventaire[i].quantite+classClient.SEP+dp.inventaire[i].durabilite+classClient.SEP;
							}
							data += classClient.END;
							classClient.send(data);
							serveurThread.sendItemMapToInMap(dp.map);
						} else {
							classClient.send("chat"+classClient.SEP+"(INFO) Pas de Place sur la map."+classClient.SEP+classClient.END);
						}
					} else {
						classClient.send("chat"+classClient.SEP+"(INFO) Objet équipé !"+classClient.SEP+classClient.END);
					}
				} else if(this.packet[0].equalsIgnoreCase("usepts")) {
					int id = Integer.parseInt(packet[1]);
					if(classClient.joueur.perso[classClient.selectPersonnage].pts > 0) {
						classClient.joueur.perso[classClient.selectPersonnage].pts--;
						if(id == 0) {
							classClient.joueur.perso[classClient.selectPersonnage].force++;
						} else if(id == 1) {
							classClient.joueur.perso[classClient.selectPersonnage].dexterite++;
						} else if(id == 2) {
							classClient.joueur.perso[classClient.selectPersonnage].endurence++;
						} else if(id == 3) {
							classClient.joueur.perso[classClient.selectPersonnage].energie++;
						}
						String data = "getstat"+classClient.SEP;
						data+= classClient.joueur.perso[classClient.selectPersonnage].vie + classClient.SEP;
						data+= classClient.joueur.perso[classClient.selectPersonnage].magie + classClient.SEP;
						data+= classClient.joueur.perso[classClient.selectPersonnage].level + classClient.SEP;
						data+= classClient.joueur.perso[classClient.selectPersonnage].exp + classClient.SEP;
						data+= classClient.joueur.perso[classClient.selectPersonnage].force + classClient.SEP;
						data+= classClient.joueur.perso[classClient.selectPersonnage].dexterite + classClient.SEP;
						data+= classClient.joueur.perso[classClient.selectPersonnage].endurence + classClient.SEP;
						data+= classClient.joueur.perso[classClient.selectPersonnage].energie + classClient.SEP;
						data+= classClient.joueur.perso[classClient.selectPersonnage].pts + classClient.SEP;
						data+= classClient.joueur.perso[classClient.selectPersonnage].money + classClient.SEP + classClient.END;
						classClient.send(data);
					}
				} else if(this.packet[0].equalsIgnoreCase("buyshop")) {
					int id = Integer.parseInt(packet[1]);
					int itemID = Integer.parseInt(packet[2]);
					
					if(serveurThread.map.get(classClient.joueur.perso[classClient.selectPersonnage].map).Case[classClient.joueur.perso[classClient.selectPersonnage].x][classClient.joueur.perso[classClient.selectPersonnage].y].attribut == 4) {
						if(serveurThread.map.get(classClient.joueur.perso[classClient.selectPersonnage].map).Case[classClient.joueur.perso[classClient.selectPersonnage].x][classClient.joueur.perso[classClient.selectPersonnage].y].data[0] == id){
							if(classClient.joueur.perso[classClient.selectPersonnage].money >= serveurThread.shop.get(id).valeur.get(itemID)) {
								// TODO A modifier
								DataItemSlot dis = new DataItemSlot();
								dis.id = serveurThread.shop.get(id).valeur.get(itemID);
								int idinv = idLibreInInv(dis);
								if(idinv >=0) {
									DataItem di = serveurThread.item.get(dis.id);
									classClient.joueur.perso[classClient.selectPersonnage].money -= serveurThread.shop.get(id).valeur.get(itemID);
									if(di.type == 17) {
										classClient.joueur.perso[classClient.selectPersonnage].inventaire[idinv].id = serveurThread.shop.get(id).article.get(itemID);
										classClient.joueur.perso[classClient.selectPersonnage].inventaire[idinv].quantite++;
										classClient.joueur.perso[classClient.selectPersonnage].inventaire[idinv].durabilite = serveurThread.item.get(serveurThread.shop.get(id).article.get(itemID)).dur;
									} else {
										classClient.joueur.perso[classClient.selectPersonnage].inventaire[idinv].id = serveurThread.shop.get(id).article.get(itemID);
										classClient.joueur.perso[classClient.selectPersonnage].inventaire[idinv].quantite  = 0;
										classClient.joueur.perso[classClient.selectPersonnage].inventaire[idinv].durabilite = serveurThread.item.get(serveurThread.shop.get(id).article.get(itemID)).dur;
									}
									
									
									DataPersonnage dp = classClient.joueur.perso[classClient.selectPersonnage];
									String data = "inv"+classClient.SEP;
									for(int i = 0; i < dp.inventaire.length; i++) {
										data += dp.inventaire[i].id+classClient.SEP+dp.inventaire[i].quantite+classClient.SEP+dp.inventaire[i].durabilite+classClient.SEP;
									}
									data += classClient.END;
									classClient.send(data);
									data = "getstat"+classClient.SEP;
									data+= classClient.joueur.perso[classClient.selectPersonnage].vie + classClient.SEP;
									data+= classClient.joueur.perso[classClient.selectPersonnage].magie + classClient.SEP;
									data+= classClient.joueur.perso[classClient.selectPersonnage].level + classClient.SEP;
									data+= classClient.joueur.perso[classClient.selectPersonnage].exp + classClient.SEP;
									data+= classClient.joueur.perso[classClient.selectPersonnage].force + classClient.SEP;
									data+= classClient.joueur.perso[classClient.selectPersonnage].dexterite + classClient.SEP;
									data+= classClient.joueur.perso[classClient.selectPersonnage].endurence + classClient.SEP;
									data+= classClient.joueur.perso[classClient.selectPersonnage].energie + classClient.SEP;
									data+= classClient.joueur.perso[classClient.selectPersonnage].pts + classClient.SEP;
									data+= classClient.joueur.perso[classClient.selectPersonnage].money + classClient.SEP + classClient.END;
									classClient.send(data);
									classClient.send("noshop"+classClient.SEP+"Vous avez acheter "+serveurThread.item.get(serveurThread.shop.get(id).article.get(itemID)).nom+classClient.SEP+classClient.END);
								} else {
									classClient.send("noshop"+classClient.SEP+"Vous n'avez pas de place !"+classClient.SEP+classClient.END);
								}
							} else {
								classClient.send("noshop"+classClient.SEP+"Vous n'avez pas assez d'argent !"+classClient.SEP+classClient.END);
							}
						} else {
							classClient.send("noshop"+classClient.SEP+"Pas de Magasin ici !"+classClient.SEP+classClient.END);
						}
					} else {
						classClient.send("noshop"+classClient.SEP+"Pas de Magasin ici !"+classClient.SEP+classClient.END);
					}
				} else if(this.packet[0].equalsIgnoreCase("saleshop")) {
					int id = Integer.parseInt(packet[1]);
					int itemID = Integer.parseInt(packet[2]);
					if(serveurThread.map.get(classClient.joueur.perso[classClient.selectPersonnage].map).Case[classClient.joueur.perso[classClient.selectPersonnage].x][classClient.joueur.perso[classClient.selectPersonnage].y].attribut == 4) {
						if(serveurThread.map.get(classClient.joueur.perso[classClient.selectPersonnage].map).Case[classClient.joueur.perso[classClient.selectPersonnage].x][classClient.joueur.perso[classClient.selectPersonnage].y].data[0] == id){
							if(classClient.joueur.perso[classClient.selectPersonnage].inventaire[itemID].id >= -1) {
								int idToShop = checkItemToShop(id, classClient.joueur.perso[classClient.selectPersonnage].inventaire[itemID].id);
								if(idToShop >= 0 && isNotUseItem(itemID)) {
									// TODO A modifier
									DataItem di = serveurThread.item.get(classClient.joueur.perso[classClient.selectPersonnage].inventaire[itemID].id);
									if(di.type == 17) {
										if(classClient.joueur.perso[classClient.selectPersonnage].inventaire[itemID].quantite > 1) {
											classClient.joueur.perso[classClient.selectPersonnage].inventaire[itemID].quantite--;
										} else {
											classClient.joueur.perso[classClient.selectPersonnage].inventaire[itemID].id = -1;
										}
										
									} else {
										classClient.joueur.perso[classClient.selectPersonnage].inventaire[itemID].id = -1;
									}
									int addMoney = serveurThread.shop.get(id).valeur.get(idToShop)/2;
									classClient.joueur.perso[classClient.selectPersonnage].money += addMoney;
									DataPersonnage dp = classClient.joueur.perso[classClient.selectPersonnage];
									String data = "inv"+classClient.SEP;
									for(int i = 0; i < dp.inventaire.length; i++) {
										data += dp.inventaire[i].id+classClient.SEP+dp.inventaire[i].quantite+classClient.SEP+dp.inventaire[i].durabilite+classClient.SEP;
									}
									data += classClient.END;
									classClient.send(data);
									data = "getstat"+classClient.SEP;
									data+= classClient.joueur.perso[classClient.selectPersonnage].vie + classClient.SEP;
									data+= classClient.joueur.perso[classClient.selectPersonnage].magie + classClient.SEP;
									data+= classClient.joueur.perso[classClient.selectPersonnage].level + classClient.SEP;
									data+= classClient.joueur.perso[classClient.selectPersonnage].exp + classClient.SEP;
									data+= classClient.joueur.perso[classClient.selectPersonnage].force + classClient.SEP;
									data+= classClient.joueur.perso[classClient.selectPersonnage].dexterite + classClient.SEP;
									data+= classClient.joueur.perso[classClient.selectPersonnage].endurence + classClient.SEP;
									data+= classClient.joueur.perso[classClient.selectPersonnage].energie + classClient.SEP;
									data+= classClient.joueur.perso[classClient.selectPersonnage].pts + classClient.SEP;
									data+= classClient.joueur.perso[classClient.selectPersonnage].money + classClient.SEP + classClient.END;
									classClient.send(data);
								} else {
									classClient.send("noshop"+classClient.SEP+"Le vendeur ne prend pas cette article."+classClient.SEP+classClient.END);
								}
							} else {
								classClient.send("noshop"+classClient.SEP+"Pas d'objet à vendre !"+classClient.SEP+classClient.END);
							}
						} else {
							classClient.send("noshop"+classClient.SEP+"Pas de Magasin ici !"+classClient.SEP+classClient.END);
						}
					} else {
						classClient.send("noshop"+classClient.SEP+"Pas de Magasin ici !"+classClient.SEP+classClient.END);
					}
				} else {
					System.out.println("("+this.classClient.clientIP+") tentative de hack(Packet inconnu).");
					System.out.println("> " + this.packet);
					this.classClient.disconnect();
				}
			} else {
				System.out.println("("+this.classClient.clientIP+") tentative de hack(Packet inconnu).");
				System.out.println("> " + this.packet);
				this.classClient.disconnect();
			}
		} else {
			System.out.println("("+this.classClient.clientIP+") tentative de hack(Packet corrompu).");
			System.out.println("> " + this.packet);
			this.classClient.disconnect();
		}	
	}
	
	private int checkItemToShop(int shop, int item) {
		for(int i =0; i < serveurThread.shop.get(shop).article.size(); i++) {
			if(item == serveurThread.shop.get(shop).article.get(i)) {
				return i;
			}
		}
		return -1;
	}
	
	private boolean isNotUseItem(int item) {
		DataPersonnage dp = classClient.joueur.perso[classClient.selectPersonnage];
		boolean used = true;
		for(int i = 0; i < dp.equipement.length; i++) {
			if(dp.equipement[i] == item) {
				used = false;
			}
		}
		return used;
	}
	
	private int idLibreInInv(DataItemSlot dis) {
		// TODO A modifier
		DataPersonnage dp = classClient.joueur.perso[classClient.selectPersonnage];
		for(int i = 0; i < dp.inventaire.length; i++) {
			if(dis != null) {
				DataItem di = serveurThread.item.get(dis.id);
				if(di.type == 17) { // Potion
					if(dp.inventaire[i].id == dis.id) { // Potion identique
						return i;
					} else {
						if(dp.inventaire[i].id == -1) {
							return i;
						}
					}
				} else {
					if(dp.inventaire[i].id == -1) {
						return i;
					}
				}
			} else {
				if(dp.inventaire[i].id == -1) {
					return i;
				}
			}
		}
		return -1;
	}
	
	private int idStuff(int id) {
		DataPersonnage dp = classClient.joueur.perso[classClient.selectPersonnage];
		for(int i = 0; i < dp.equipement.length; i++) {
			if(dp.equipement[i]-1 == id) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean collidPNJ(int map, int x, int y) {
		for(int j = 0; j < serveurThread.pnjMap.get(map).size(); j++) {
			if(serveurThread.pnjMap.get(map).get(j).x == x && serveurThread.pnjMap.get(map).get(j).y == y) return true;
		}
		return false;
	}
}
