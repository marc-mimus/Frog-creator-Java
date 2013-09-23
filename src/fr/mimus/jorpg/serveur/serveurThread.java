package fr.mimus.jorpg.serveur;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import fr.mimus.jorpg.commun.DataCase;
import fr.mimus.jorpg.commun.DataItem;
import fr.mimus.jorpg.commun.DataItemSlot;
import fr.mimus.jorpg.commun.DataJoueur;
import fr.mimus.jorpg.commun.DataLoader;
import fr.mimus.jorpg.commun.DataMap;
import fr.mimus.jorpg.commun.DataPNJ;
import fr.mimus.jorpg.commun.DataPNJMap;
import fr.mimus.jorpg.commun.DataPersonnage;
import fr.mimus.jorpg.commun.DataQuest;
import fr.mimus.jorpg.commun.DataShop;

public class serveurThread extends Thread{
	public static final String SEP = "\u00FE";
	public static final String END = "\u00DF";
	ServerSocket sock = null;
	boolean listening = true;
	static serveurClientSock[] Clients = new serveurClientSock[500];
	serveurCommand cmd;
	static ArrayList<DataMap> map;
	static ArrayList<DataItemSlot[][]> itemMap;
	static ArrayList<ArrayList<DataPNJMap>> pnjMap;
	public static ArrayList<DataItem> item;
	static ArrayList<DataPNJ> pnj;
	static ArrayList<String> persoName;
	static ArrayList<DataShop> shop;
	static ArrayList<DataQuest> quete;
	static ThreadGameLoop tgl;
	public serveurThread() { 
		map = new ArrayList<DataMap>();
		itemMap = new ArrayList<DataItemSlot[][]>();
		pnjMap = new ArrayList<ArrayList<DataPNJMap>>();
		item = new ArrayList<DataItem>();
		pnj = new ArrayList<DataPNJ>();
		shop = new ArrayList<DataShop>();
		quete = new ArrayList<DataQuest>();
		persoName = new ArrayList<String>();
		tgl = new ThreadGameLoop();
		reloadPersoName();
		
		String [] listefichiers = (new File("Data/Maps/")).list(); 
		/*for(int i=0;i<listefichiers.length;i++){
			if(listefichiers[i].endsWith(".ns")==true){
				map.add(DataLoader.load(listefichiers[i].substring(0,listefichiers[i].length()-3)));
			}
		} */
		for(int i=0;i<listefichiers.length;i++){
			if((new File("Data/Maps/map"+i+".ns")).exists()) {
				map.add(DataLoader.load("map"+i));
				itemMap.add(new DataItemSlot[map.get(map.size()-1).width][map.get(map.size()-1).height]);
				for(int x = 0; x < itemMap.get(itemMap.size()-1).length; x++) {
					for(int y = 0; y < itemMap.get(itemMap.size()-1)[x].length; y++) {
						itemMap.get(itemMap.size()-1)[x][y] = new DataItemSlot();
					}
				}
				pnjMap.add(new ArrayList<DataPNJMap>());
			} else {
				map.add(new DataMap());
				itemMap.add(new DataItemSlot[map.get(map.size()-1).width][map.get(map.size()-1).height]);
				for(int x = 0; x < itemMap.get(itemMap.size()-1).length; x++) {
					for(int y = 0; y < itemMap.get(itemMap.size()-1)[x].length; y++) {
						itemMap.get(itemMap.size()-1)[x][y] = new DataItemSlot();
					}
				}
				pnjMap.add(new ArrayList<DataPNJMap>());
				tgl.spawnAllPnjToMap(map.size()-1);
			}
		} 
		listefichiers = (new File("Data/Objets/")).list(); 
		for(int i=0;i<listefichiers.length;i++){
			if((new File("Data/Objets/objet"+i+".ns")).exists()) {
				item.add(DataLoader.loadItem("objet"+i));
			} else {
				item.add(new DataItem());
			}
		} 
		listefichiers = (new File("Data/PNJs/")).list(); 
		for(int i=0;i<listefichiers.length;i++){
			if((new File("Data/PNJs/pnj"+i+".ns")).exists()) {
				pnj.add(DataLoader.loadPNJ("pnj"+i));
			} else {
				pnj.add(new DataPNJ());
			}
		}
		listefichiers = (new File("Data/Magasins/")).list(); 
		for(int i=0;i<listefichiers.length;i++){
			if((new File("Data/Magasins/Magasin"+i+".ns")).exists()) {
				shop.add(DataLoader.loadShop("Magasin"+i));
			} else {
				shop.add(new DataShop());
			}
		} 
		listefichiers = (new File("Data/Quetes/")).list(); 
		for(int i=0;i<listefichiers.length;i++){
			if((new File("Data/Quetes/Quete"+i+".ns")).exists()) {
				quete.add(DataLoader.loadQuete("Quete"+i));
			} else {
				quete.add(new DataQuest());
			}
		} 
	}
	
	public static void reloadPersoName(){
		persoName.clear();
		String [] listefichiers = (new File("Serveur/Comptes/")).list(); 
		for(int i=0;i<listefichiers.length;i++){
			if(listefichiers[i].endsWith(".ns")==true){
				DataJoueur temp = DataLoader.loadJoueur(listefichiers[i].substring(0,listefichiers[i].length()-3));
				for(int j = 0; j < temp.perso.length; j++) {
					if(temp.perso[j].nom != null)
						persoName.add(temp.perso[j].nom);
				}
			}
		} 
	}
	
	public static void spawnPlayer(int id) {
		if(Clients[id].joueur.perso[Clients[id].selectPersonnage].map >= map.size()) {
			Clients[id].joueur.perso[Clients[id].selectPersonnage].map = 0;
		}
		DataPersonnage dp = Clients[id].joueur.perso[Clients[id].selectPersonnage];
		String data = "inv"+Clients[id].SEP;
		for(int i = 0; i < dp.inventaire.length; i++) {
			data += dp.inventaire[i].id+Clients[id].SEP+dp.inventaire[i].quantite+Clients[id].SEP+dp.inventaire[i].durabilite+Clients[id].SEP;
		}
		data += Clients[id].END;
		Clients[id].send(data);
		
		data = "stuff"+Clients[id].SEP;
		for(int i = 0; i < dp.equipement.length; i++) {
			data += dp.equipement[i]+Clients[id].SEP;
		}
		data += Clients[id].END;
		Clients[id].send(data);
		
		if(pnjMap.get(dp.map).size() > 0) {
			data = "spawnnpc" + Clients[id].SEP + pnjMap.get(dp.map).size() + Clients[id].SEP;
			for(int i = 0; i < pnjMap.get(dp.map).size(); i++) {
				data += pnjMap.get(dp.map).get(i).id + Clients[id].SEP + pnjMap.get(dp.map).get(i).x + Clients[id].SEP + pnjMap.get(dp.map).get(i).y + Clients[id].SEP + pnjMap.get(dp.map).get(i).dir + Clients[id].SEP;
			}
			data += Clients[id].END;
			Clients[id].send(data);
		}
		sendItemMap(id);
		
		if(ThreadGameLoop.isNight) Clients[id].send("journuit"+serveurThread.SEP+"1"+serveurThread.SEP+serveurThread.END);
	}
	
	public static void sendItemMap(int id) {
		int idMap = Clients[id].joueur.perso[Clients[id].selectPersonnage].map;
		String data = "spawnitemmap" + Clients[id].SEP;
		System.out.println(idMap);
		for(int x = 0; x < itemMap.get(idMap).length; x++) {
			for(int y = 0; y < itemMap.get(idMap)[x].length; y++) {
				data += itemMap.get(idMap)[x][y].id + Clients[id].SEP;
				data += itemMap.get(idMap)[x][y].quantite + Clients[id].SEP;
				data += itemMap.get(idMap)[x][y].durabilite + Clients[id].SEP;
			}
		}
		data += Clients[id].END;
		Clients[id].send(data);
	}
	
	public static void sendItemMapToInMap(int id) {
		int idMap = Clients[id].joueur.perso[Clients[id].selectPersonnage].map;
		String data = "spawnitemmap" + Clients[id].SEP;
		for(int x = 0; x < itemMap.get(idMap).length; x++) {
			for(int y = 0; y < itemMap.get(idMap)[x].length; y++) {
				data += itemMap.get(idMap)[x][y].id + Clients[id].SEP;
				data += itemMap.get(idMap)[x][y].quantite + Clients[id].SEP;
				data += itemMap.get(idMap)[x][y].durabilite + Clients[id].SEP;
			}
		}
		data += Clients[id].END;
		for (int i=0; i<Clients.length; i++){
			if (Clients[i].isOnLine == true && Clients[i].inGame && idMap == Clients[i].joueur.perso[Clients[id].selectPersonnage].map)
			{
				Clients[i].send(data);
			}
		}
	}
	
	public static boolean isPersoExist(String name){
		for(int i = 0; i < persoName.size(); i++) {
			if(persoName.get(i) != null) if(persoName.get(i).equalsIgnoreCase(name)) return true;
		}
		return false;
	}
	
	public void run() {
		Socket tempSocket ;
		Thread tempThread;
		PrintWriter out;
		int tempID = -1;
		try{
			sock = new ServerSocket(serveurMain.dcs.dcc.port);
		}catch(IOException e){
			System.err.println("Impossible d'ecouter le port: "+serveurMain.dcs.dcc.port);
			System.exit(-1);
		}
		System.out.println("Le serveur est a l'ecoute du port "+sock.getLocalPort());
		try {
			for (int i=0; i<Clients.length; i++){
				Clients[i] = new serveurClientSock(i);
			}
			cmd = new serveurCommand(this);
			cmd.start();
			tgl.start();
			while (listening)
	        {
	        	tempID = sockLibre();
				if(tempID>-1) {
					Clients[tempID].connect(sock.accept());
					tempThread  = new Thread(Clients[tempID]);
						tempThread.start();	
					tempID = -1;
				} else {
					tempSocket  = sock.accept(); 
					out = new PrintWriter(tempSocket.getOutputStream(), true);
						out.println("noconnect"+SEP+"Il n'y a plus de place sur le serveur."+SEP+END);
				        out.flush();
					tempSocket.close();
				}
			}
			for (int i=0; i<Clients.length; i++){
				if (Clients[i].isOnLine && Clients[i].inGame)
				{
					DataLoader.saveJoueur(Clients[i].joueur);
				}
			}
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public int sockLibre() {
		
		int i;
		for (i=0; i<Clients.length; i++){
			if (Clients[i].isOnLine == false)
			{
				return i;
			}
		}
		return -1;
	}
	public void sockList() {
		boolean isConnect = false;
		for (int i=0; i<Clients.length; i++){
			if (Clients[i].isOnLine && Clients[i].inGame) {
				System.out.println(i+": "+Clients[i].clientIP);
				System.out.println("- "+Clients[i].joueur.nom+" - ("+Clients[i].joueur.perso[Clients[i].selectPersonnage].level+")"+Clients[i].joueur.perso[Clients[i].selectPersonnage].nom);
				isConnect = true;
			} 
			else if (Clients[i].isOnLine && Clients[i].isConnect) {
				System.out.println(i+": "+Clients[i].clientIP);
				System.out.println("- "+Clients[i].joueur.nom);
				isConnect = true;
			} 
			else if (Clients[i].isOnLine == true) {
				System.out.println(i+": "+Clients[i].clientIP);
				isConnect = true;
			}
		}
		if(!isConnect){
			System.out.println("Pas de joueur connecté.");
		}
	}
	
	public int seachPlayerID(String name) {
		for (int i=0; i<Clients.length; i++){
			if (Clients[i].isOnLine && Clients[i].inGame) {
				if(name.equalsIgnoreCase(Clients[i].joueur.perso[Clients[i].selectPersonnage].nom)){
					return i;
				}
			}
		}
		return -1;
	}
	
	public static boolean PlayerInMap(int map) {
		for (int i=0; i<Clients.length; i++){
			if (Clients[i].isOnLine && Clients[i].inGame)
			{
				if(Clients[i].joueur.perso[Clients[i].selectPersonnage].map == map) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static void sendToAll(String packet) {
		for (int i=0; i<Clients.length; i++){
			if (Clients[i].isOnLine && Clients[i].inGame)
			{
				Clients[i].send(packet);
			}
		}
	}
	public static void sendToMap(int map, String packet) {
		for (int i=0; i<Clients.length; i++){
			if (Clients[i].isOnLine == true && Clients[i].inGame && Clients[i].joueur.perso[Clients[i].selectPersonnage].map == map)
			{
				Clients[i].send(packet);
			}
		}
	}
	public static void sendToMeAll(int id) {
		for (int i=0; i<Clients.length; i++){
			if (Clients[i].isOnLine == true && Clients[i].inGame)
			{
				Clients[id].send("spawn"+Clients[i].SEP+i+Clients[i].SEP+Clients[i].joueur.nom+Clients[i].SEP+Clients[i].joueur.perso[Clients[i].selectPersonnage].map+Clients[i].SEP+Clients[i].joueur.perso[Clients[i].selectPersonnage].x+Clients[i].SEP+Clients[i].joueur.perso[Clients[i].selectPersonnage].y+Clients[i].SEP+Clients[i].joueur.perso[Clients[i].selectPersonnage].sprite+Clients[i].SEP+Clients[i].END);
			}
		}
	}
	public static void sendToAllOtherMe(int id, String packet) {
		for (int i=0; i<Clients.length; i++){
			if(i != id) {
				if (Clients[i].isOnLine == true && Clients[i].inGame)
				{
					Clients[i].send(packet);
				}
			}
		}
	}
	
	public static boolean isConnectName(String name) {
		for (int i=0; i<Clients.length; i++){
			if (Clients[i].isOnLine == true)
			{
				if(Clients[i].joueur.nom != null) {
					System.out.println(Clients[i].joueur.nom);
					if(Clients[i].joueur.nom.equalsIgnoreCase(name)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void reloadMap() {
		map.clear();
		String [] listefichiers = (new File("Data/Maps/")).list(); 
		/*for(int i=0;i<listefichiers.length;i++){
			if(listefichiers[i].endsWith(".ns")==true){
				map.add(DataLoader.load(listefichiers[i].substring(0,listefichiers[i].length()-3)));
			}
		} */
		for(int i=0;i<listefichiers.length;i++){
			if((new File("Data/Maps/map"+i+".ns")).exists()) {
				map.add(DataLoader.load("map"+i));
			} else {
				map.add(new DataMap());
			}
		} 
	}
	public void reloadItem() {
		item.clear();
		String [] listefichiers = (new File("Data/Objets/")).list(); 
		for(int i=0;i<listefichiers.length;i++){
			if((new File("Data/Objets/objet"+i+".ns")).exists()) {
				item.add(DataLoader.loadItem("objet"+i));
			} else {
				item.add(new DataItem());
			}
		} 
	}
	public void reloadAll() {
		map.clear();
		itemMap.clear();
		pnjMap.clear();
		item.clear();
		pnj.clear();
		shop.clear();
		quete.clear();
		
		String [] listefichiers = (new File("Data/Maps/")).list(); 
		for(int i=0;i<listefichiers.length;i++){
			if((new File("Data/Maps/map"+i+".ns")).exists()) {
				map.add(DataLoader.load("map"+i));
				itemMap.add(new DataItemSlot[map.get(map.size()-1).width][map.get(map.size()-1).height]);
				for(int x = 0; x < itemMap.get(itemMap.size()-1).length; x++) {
					for(int y = 0; y < itemMap.get(itemMap.size()-1)[x].length; y++) {
						itemMap.get(itemMap.size()-1)[x][y] = new DataItemSlot();
					}
				}
				pnjMap.add(new ArrayList<DataPNJMap>());
			} else {
				map.add(new DataMap());
				itemMap.add(new DataItemSlot[map.get(map.size()-1).width][map.get(map.size()-1).height]);
				for(int x = 0; x < itemMap.get(itemMap.size()-1).length; x++) {
					for(int y = 0; y < itemMap.get(itemMap.size()-1)[x].length; y++) {
						itemMap.get(itemMap.size()-1)[x][y] = new DataItemSlot();
					}
				}
				pnjMap.add(new ArrayList<DataPNJMap>());
				tgl.spawnAllPnjToMap(map.size()-1);
			}
		} 
		System.out.println("Les cartes sont rechargé.");
		listefichiers = (new File("Data/Objets/")).list(); 
		for(int i=0;i<listefichiers.length;i++){
			if((new File("Data/Objets/objet"+i+".ns")).exists()) {
				item.add(DataLoader.loadItem("objet"+i));
			} else {
				item.add(new DataItem());
			}
		} 
		System.out.println("Les objets sont rechargé.");
		listefichiers = (new File("Data/PNJs/")).list(); 
		for(int i=0;i<listefichiers.length;i++){
			if((new File("Data/PNJs/pnj"+i+".ns")).exists()) {
				pnj.add(DataLoader.loadPNJ("pnj"+i));
			} else {
				pnj.add(new DataPNJ());
			}
		}
		listefichiers = (new File("Data/Magasins/")).list(); 
		for(int i=0;i<listefichiers.length;i++){
			if((new File("Data/Magasins/Magasin"+i+".ns")).exists()) {
				shop.add(DataLoader.loadShop("Magasin"+i));
			} else {
				shop.add(new DataShop());
			}
		} 
		System.out.println("Les magasins sont rechargé.");
		listefichiers = (new File("Data/Quetes/")).list(); 
		for(int i=0;i<listefichiers.length;i++){
			if((new File("Data/Quetes/Quete"+i+".ns")).exists()) {
				quete.add(DataLoader.loadQuete("Quete"+i));
			} else {
				quete.add(new DataQuest());
			}
		}
		System.out.println("Les quetes sont rechargé.");
	}
}
