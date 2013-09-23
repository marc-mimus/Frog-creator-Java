package fr.mimus.jorpg.serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import fr.mimus.jorpg.commun.DataItem;
import fr.mimus.jorpg.commun.DataJoueur;
import fr.mimus.jorpg.commun.DataLoader;

public class serveurClientSock extends Thread {
	public final String SEP = "\u00FE";
	public final String END = "\u00DF";
	public int idClient = -1;
	private Socket socket = null;
	public boolean isOnLine = false;
	public boolean isConnect = false;
	public boolean inGame = false;
	public int selectPersonnage;
	public static long attackTime;
	private PrintWriter out;
	private BufferedReader in;
	public String clientIP;
	DataJoueur joueur;
	
	public serveurClientSock(int id) {
		super("client");
		this.idClient = id;
		joueur = new DataJoueur();
	}
	
	public void connect(Socket s) {
		this.socket = s;
		this.isOnLine = true;
		this.clientIP = socket.getInetAddress().getHostAddress();
	}
	
	public void disconnect() {
		this.isOnLine = false;
		if(inGame) DataLoader.saveJoueur(this.joueur);
		this.isConnect = false;
		this.inGame = false;
		this.joueur = new DataJoueur();
		serveurThread.sendToAllOtherMe(idClient, "unspawn"+SEP+idClient+SEP+END);
	}
	
	public void run(){
		
		System.out.println("("+this.idClient+") "+clientIP+" vient de se connecte.");
			//socket.setTcpNoDelay(true);
			try {
				out = new PrintWriter(socket.getOutputStream(), true);
				in = new BufferedReader (new InputStreamReader(socket.getInputStream()));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			String inputLine;
			String[] t;
			do{
				inputLine = "";
				try {
					inputLine = in.readLine();
				} catch (IOException e) {
					//e.printStackTrace();
					this.isOnLine = false;
				}

				if(inputLine!=null && this.isOnLine) {
					t = inputLine.split(this.SEP);
					System.out.println("Client #"+this.idClient+" > "+t[0]);
					serveurReception Recep = new serveurReception(this, t);
					Recep.start();
				}

			} while (isOnLine && inputLine != null);
			
			this.disconnect();
			try {
				in.close();
				out.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			System.out.println("("+this.idClient+") "+clientIP+" vient de se deconnecte.");
	}
	
	public void send(String message){
		out.println(message);
		System.out.println("Client #"+this.idClient+" < "+message.split(this.SEP)[0]);
	}
	public int getDmg() {
		int force = joueur.perso[selectPersonnage].force;
		int dex = joueur.perso[selectPersonnage].dexterite;
		int d = 0;
		for(int i = 0; i < joueur.perso[selectPersonnage].equipement.length; i++) {
			if(joueur.perso[selectPersonnage].equipement[i] != -1) {
				DataItem di = serveurThread.item.get(joueur.perso[selectPersonnage].inventaire[joueur.perso[selectPersonnage].equipement[i]].id);
				force += di.force;
				dex += di.dexterite;
				d += di.dommage;
			}
		}
		int atk = force + dex/2 + d;
		return atk;
	}

	public int getDef() {
		int dex = joueur.perso[selectPersonnage].dexterite;
		int end = joueur.perso[selectPersonnage].endurence;
		int d = 0;
		for(int i = 0; i < joueur.perso[selectPersonnage].equipement.length; i++) {
			if(joueur.perso[selectPersonnage].equipement[i] != -1) {
				DataItem di = serveurThread.item.get(joueur.perso[selectPersonnage].inventaire[joueur.perso[selectPersonnage].equipement[i]].id);
				end += di.defense;
				dex += di.dexterite;
				d += di.defense;
			}
		}
		int def = dex + end/2 + d;
		return def;
	}
	
	public static long getTime() {
	    return System.currentTimeMillis();
	}
}

