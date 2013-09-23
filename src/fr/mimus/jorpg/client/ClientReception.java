package fr.mimus.jorpg.client;

import javax.swing.JOptionPane;

import fr.mimus.jorpg.commun.DataItemSlot;
import fr.mimus.jorpg.commun.DataPNJMap;
import fr.mimus.jorpg.commun.DataPersonnage;
import fr.mimus.jorpg.commun.MsgMap;

public class ClientReception extends Thread{
	private ClientThread classClient;
	private String packet[];
	
	public ClientReception(ClientThread clientThread, String t[]){
		this.classClient = clientThread;
		this.packet = t;
	}
	public void run() {
		if(this.packet[this.packet.length-1].equals(ClientMain.client.END)) {
			//String osName = System.getProperty("os.name");
			//os.name
			//user.name
			// Creation compte
			if(this.packet[0].equalsIgnoreCase("noconnect")) {
				JOptionPane.showMessageDialog(null, packet[1]) ;
				System.exit(-1);
			} else if(this.packet[0].equalsIgnoreCase("nc")) {
				if(this.packet[1].equalsIgnoreCase("1")) {
					JOptionPane.showMessageDialog(null, "Votre compte a été créé !") ;
				} else {
					JOptionPane.showMessageDialog(null, "Votre compte n'a pas pu être créé...") ;
				}
			}
			// Recupere ID
			else if(this.packet[0].equalsIgnoreCase("youid")) {
				classClient.myid = Integer.parseInt(packet[1]);
			}
			// Connexion
			else if(this.packet[0].equalsIgnoreCase("lc")) {
				if(this.packet[1].equalsIgnoreCase("1")) {
					//JOptionPane.showMessageDialog(null, "Frog Creator Java Bêta 1.0");
					//classClient.cm.menu = 1;
					//classClient.cm.repaint();
				} else {
					JOptionPane.showMessageDialog(null, "Erreur Login ou Mot de Passe..");
				}
			}
			else if(this.packet[0].equalsIgnoreCase("cp")) {
				classClient.cm.menu = 2;
			}
			else if(this.packet[0].equalsIgnoreCase("nocp")) {
				JOptionPane.showMessageDialog(null, "Se nom est déjà prit!");
			}
			else if(this.packet[0].equalsIgnoreCase("pg")) {
				classClient.cm.menu = 5;
			}
			else if(this.packet[0].equalsIgnoreCase("journuit")) {
				if(packet[1].equalsIgnoreCase("1")) {
					ClientBoard.isNight = false;
				} else {
					ClientBoard.isNight = true;
				}
			}
			else if(this.packet[0].equalsIgnoreCase("spawn")) {
				ClientBoard.msgMap.clear();
				int id = Integer.parseInt(packet[1]);
				ClientMain.joueur[id].nom = packet[2];
				ClientMain.joueur[id].map = Integer.parseInt(packet[3]);
				ClientMain.joueur[id].x = Integer.parseInt(packet[4]);
				ClientMain.joueur[id].y = Integer.parseInt(packet[5]);
				ClientMain.joueur[id].sprite = Integer.parseInt(packet[6]);
				if(id == classClient.myid) {
					ClientBoard.createNight();
					ThreadGameLoop.botIsRun = true;
				}
			}
			else if(this.packet[0].equalsIgnoreCase("unspawn")) {
				int id = Integer.parseInt(packet[1]);
				ClientMain.joueur[id] = new DataPersonnage();
			}
			else if(this.packet[0].equalsIgnoreCase("move")) {
				int id = Integer.parseInt(packet[1]);
				int dir = Integer.parseInt(packet[2]);
				ClientMain.joueur[id].dir = dir;
				switch(dir) {
				case 0:
					ClientMain.joueur[id].y--;
					ClientMain.joueur[id].offsetY = 32;
					break;
				case 1:
					ClientMain.joueur[id].x++;
					ClientMain.joueur[id].offsetX = -32;
					break;
				case 2:
					ClientMain.joueur[id].y++;
					ClientMain.joueur[id].offsetY = -32;
					break;
				case 3:
					ClientMain.joueur[id].x--;
					ClientMain.joueur[id].offsetX = 32;
					break;
				}
				
			}
			else if(this.packet[0].equalsIgnoreCase("nomove")) {
				int id = Integer.parseInt(packet[1]);
				int dir = Integer.parseInt(packet[2]);
				ClientMain.joueur[id].dir = dir;
				classClient.isMoved = false;
			}
			else if(this.packet[0].equalsIgnoreCase("tp")) {
				ClientBoard.msgMap.clear();
				int id = Integer.parseInt(packet[1]);
				ClientMain.joueur[id].map = Integer.parseInt(packet[2]);
				ClientMain.joueur[id].x = Integer.parseInt(packet[3]);
				ClientMain.joueur[id].y = Integer.parseInt(packet[4]);
				if(id == classClient.myid) {
					ClientBoard.createNight();
				}
			}
			else if(this.packet[0].equalsIgnoreCase("getstat")) {
				ClientMain.joueur[classClient.myid].vie = Integer.parseInt(packet[1]);
				ClientMain.joueur[classClient.myid].magie = Integer.parseInt(packet[2]);
				ClientMain.joueur[classClient.myid].level = Integer.parseInt(packet[3]);
				ClientMain.joueur[classClient.myid].exp = Integer.parseInt(packet[4]);
				ClientMain.joueur[classClient.myid].force = Integer.parseInt(packet[5]);
				ClientMain.joueur[classClient.myid].dexterite = Integer.parseInt(packet[6]);
				ClientMain.joueur[classClient.myid].endurence = Integer.parseInt(packet[7]);
				ClientMain.joueur[classClient.myid].energie = Integer.parseInt(packet[8]);
				ClientMain.joueur[classClient.myid].pts = Integer.parseInt(packet[9]);
				ClientMain.joueur[classClient.myid].money = Integer.parseInt(packet[10]);
			}
			else if(this.packet[0].equalsIgnoreCase("getvie")) {
				ClientMain.joueur[classClient.myid].vie = Integer.parseInt(packet[1]);
				ClientMain.joueur[classClient.myid].magie = Integer.parseInt(packet[2]);
			}
			else if(this.packet[0].equalsIgnoreCase("chat")) {
				ClientBoard.chatBox.add(this.packet[1]);
				if(ClientBoard.chatBox.size() > 10) ClientBoard.chatBox.remove(0);
				ClientBoard.chatTime = ThreadGameLoop.getTime();
			}
			else if(this.packet[0].equalsIgnoreCase("msgmap")) {
				ClientBoard.msgMap.add(new MsgMap(Integer.parseInt(packet[1]), packet[2], Integer.parseInt(packet[3]),Integer.parseInt(packet[4])));
			}
			else if(this.packet[0].equalsIgnoreCase("oinv")) {
				for(int i = 0; i < ClientMain.joueur[classClient.myid].inventaire.length; i++) {
					ClientMain.joueur[classClient.myid].inventaire[i].id = Integer.parseInt(packet[i*3+1]);
					ClientMain.joueur[classClient.myid].inventaire[i].quantite = Integer.parseInt(packet[i*3+2]);
					ClientMain.joueur[classClient.myid].inventaire[i].durabilite = Integer.parseInt(packet[i*3+3]);
				}
				ClientBoard.inInventaire = true;
			}
			else if(this.packet[0].equalsIgnoreCase("stuff")) {
				for(int i = 0; i < ClientMain.joueur[classClient.myid].equipement.length; i++) {
					ClientMain.joueur[classClient.myid].equipement[i] = Integer.parseInt(packet[i+1]);
				}
			}
			else if(this.packet[0].equalsIgnoreCase("inv")) {
				for(int i = 0; i < ClientMain.joueur[classClient.myid].inventaire.length; i++) {
					ClientMain.joueur[classClient.myid].inventaire[i].id = Integer.parseInt(packet[i*3+1]);
					ClientMain.joueur[classClient.myid].inventaire[i].quantite = Integer.parseInt(packet[i*3+2]);
					ClientMain.joueur[classClient.myid].inventaire[i].durabilite = Integer.parseInt(packet[i*3+3]);
				}
			}
			else if(this.packet[0].equalsIgnoreCase("spawnitemmap")) {
				int n = 1;
				ClientBoard.itemMap = new DataItemSlot[ClientBoard.map.get(ClientMain.joueur[ClientMain.client.myid].map).width][ClientBoard.map.get(ClientMain.joueur[ClientMain.client.myid].map).height];
				for(int x = 0; x < ClientBoard.map.get(ClientMain.joueur[ClientMain.client.myid].map).width; x++) {
					for(int y = 0; y < ClientBoard.map.get(ClientMain.joueur[ClientMain.client.myid].map).height; y++) {
						ClientBoard.itemMap[x][y] = new DataItemSlot();
						ClientBoard.itemMap[x][y].id = Integer.parseInt(packet[n]);
						ClientBoard.itemMap[x][y].quantite = Integer.parseInt(packet[n+1]);
						ClientBoard.itemMap[x][y].durabilite = Integer.parseInt(packet[n+2]);
						n += 3;
					}
				}
			}
			else if(this.packet[0].equalsIgnoreCase("clearnpc")) {
				ClientBoard.pnjMap.clear();
			}
			else if(this.packet[0].equalsIgnoreCase("spawnnpc")) {
				//ClientBoard.pnjMap.add(new DataPNJMap());
				//int id = Integer.parseInt(packet[1]);
				ClientBoard.pnjMap.clear();
				for(int i = 0; i < Integer.parseInt(packet[1]); i++) {
					ClientBoard.pnjMap.add(new DataPNJMap());
					ClientBoard.pnjMap.get(i).id = Integer.parseInt(packet[2 + i*4]);
					ClientBoard.pnjMap.get(i).x = Integer.parseInt(packet[3 + i*4]);
					ClientBoard.pnjMap.get(i).y = Integer.parseInt(packet[4 + i*4]);
					ClientBoard.pnjMap.get(i).dir = Integer.parseInt(packet[5 + i*4]);
				}
			}
			else if(this.packet[0].equalsIgnoreCase("spawnnpcone")) {
				//ClientBoard.pnjMap.add(new DataPNJMap());
				int id = Integer.parseInt(packet[1]);
				ClientBoard.pnjMap.get(id).id = Integer.parseInt(packet[2]);
				ClientBoard.pnjMap.get(id).x = Integer.parseInt(packet[3]);
				ClientBoard.pnjMap.get(id).y = Integer.parseInt(packet[4]);
				ClientBoard.pnjMap.get(id).dir = Integer.parseInt(packet[5]);
				
			}
			else if(this.packet[0].equalsIgnoreCase("removenpc")) {
				int id = Integer.parseInt(packet[1]);
				ClientBoard.pnjMap.remove(id);
			}
			else if(this.packet[0].equalsIgnoreCase("movenpc")) {
				int id = Integer.parseInt(packet[1]);
				int dir = Integer.parseInt(packet[2]);
				if(id < ClientBoard.pnjMap.size()) {
				ClientBoard.pnjMap.get(id).dir = dir;
					switch(dir) {
					case 0:
						ClientBoard.pnjMap.get(id).y--;
						ClientBoard.pnjMap.get(id).offsetY = 32;
						break;
					case 1:
						ClientBoard.pnjMap.get(id).x++;
						ClientBoard.pnjMap.get(id).offsetX = -32;
						break;
					case 2:
						ClientBoard.pnjMap.get(id).y++;
						ClientBoard.pnjMap.get(id).offsetY = -32;
						break;
					case 3:
						ClientBoard.pnjMap.get(id).x--;
						ClientBoard.pnjMap.get(id).offsetX = 32;
						break;
					}
				}
			}
			else if(this.packet[0].equalsIgnoreCase("nomovenpc")) {
				int id = Integer.parseInt(packet[1]);
				if(id < ClientBoard.pnjMap.size()) {
					int dir = Integer.parseInt(packet[2]);
					ClientBoard.pnjMap.get(id).dir = dir;
				}
			}
			else if(this.packet[0].equalsIgnoreCase("openshop")) {
				int id = Integer.parseInt(packet[1]);
				if(id < ClientBoard.shop.size()) {
					ClientBoard.selectShopId = id;
					ClientBoard.selectShop = 0;
					ClientBoard.selectShopPage = 0;
					ClientBoard.inShop = true;
				}
			}
			else if(this.packet[0].equalsIgnoreCase("noshop")) {
				JOptionPane.showMessageDialog(null, packet[1]) ;
			}
			
		} else {
			System.out.println("Packet corrompu.");
			System.out.println("> " + this.packet);
		}	
	}
}
