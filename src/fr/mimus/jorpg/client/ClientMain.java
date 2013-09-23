package fr.mimus.jorpg.client;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import fr.mimus.jorpg.commun.DataClient;
import fr.mimus.jorpg.commun.DataConfigClient;
import fr.mimus.jorpg.commun.DataLoader;
import fr.mimus.jorpg.commun.DataPersonnage;

public class ClientMain extends JFrame {
	private static final long serialVersionUID = 1L;

	static ClientThread client;
	static DataPersonnage[] joueur = new DataPersonnage[500];
	static ThreadGameLoop gameloop;
	static Socket socket;
	static ClientBoard CB;
	static DataConfigClient dcc = new DataConfigClient();
	int menu = 0;
	
	public static void main(String[] zero) {
		/*client = new ClientThread();
		client.start();*/
		for(int i = 0; i < joueur.length; i++) {
			joueur[i] = new DataPersonnage();
		}
		@SuppressWarnings("unused")
		ClientMain cm = new ClientMain();
	}
	
	public ClientMain() {
		if((new File("Data/Client.wns")).exists()) {
			DataClient dc = (DataClient) DataLoader.decompileClient("Client");
			dcc = (DataConfigClient) dc.data;
		}
		try {
			socket = new Socket("drastal-online.fr"/*dcc.ip*/, dcc.port);
			CB = new ClientBoard(this);
			client = new ClientThread(socket, this);
			gameloop = new ThreadGameLoop(this);
			client.start();
			this.setTitle(dcc.gameName);
			this.setSize(640, 638);
			this.setResizable(false);
			this.setLayout(new BorderLayout());
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.add(CB);
			this.setVisible(true);
			gameloop.start();
		}catch (UnknownHostException e) {
			e.printStackTrace();
		}catch (IOException e) {
			//e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Jeu Offline.");
			System.exit(-1);
		}
	}
}
