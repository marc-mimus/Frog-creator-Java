package fr.mimus.jorpg.serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class serveurCommand extends Thread {
	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	String command;
	serveurThread st;
	public serveurCommand(serveurThread pst) {
		super("command");
		st = pst;
	}
	public void run(){
		boolean valid = false;

		try {
			while ((command = in.readLine()) != null){
				valid = false;
				command = command.trim().toUpperCase();
				if (command.compareTo("STOP")==0){
					valid = true;
					st.listening = false;
					System.exit(0);
				}
				else if (command.compareTo("LIST")==0 || command.compareTo("LISTE")==0){
					valid = true;
					st.sockList();
				}
				else if (command.compareTo("HELP")==0 || command.compareTo("AIDE")==0){
					valid = true;
					System.out.println("# Commande pour le Serveur:");
					System.out.println("STOP - ArrÃªte le serveur");
					System.out.println("LIST ou LISTE - donne la liste des joueurs");
					System.out.println("RELOAD - recharge le serveur (Cartes, objets...)");
					System.out.println("# Commande sur le joueur:");
				}
				else if (command.compareTo("RELOAD")==0){
					valid = true;
					st.reloadAll();
					/*st.reloadMap();
					System.out.println("Les cartes sont recharger.");
					st.reloadItem();
					System.out.println("Les objets sont recharger.");*/
				}
				else if (command.startsWith("KICK") || command.startsWith("kick")){
					valid = true;
					String str[] = command.split(" ");
					int id = st.seachPlayerID(str[1]);
					if(id > -1) {
						serveurThread.Clients[id].send("noconnect"+serveurThread.SEP+"Vous avez été kick par le serveur."+serveurThread.SEP+serveurThread.END);
						System.out.println("Le joueur "+str[1]+ " a été kick.");
					} else {
						System.out.println("Erreur commande ou de nom...");
						System.out.println("Commande: kick [nom]");
						System.out.println("Le joueur doit être connecté,");
						System.out.println("Pour voir la liste des joueurs: liste");
					}
				}
				else if (command.startsWith("TIME") || command.startsWith("time")){
					valid = true;
					int heure = (int) (ThreadGameLoop.timeGame.time/60);
					int minute = (int) (ThreadGameLoop.timeGame.time-(heure*60));
					System.out.println(ThreadGameLoop.timeGame.jour+"j, "+heure+":"+minute);
				}
				if (!valid){
					System.out.println("Erreur, commande inexistante.");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}