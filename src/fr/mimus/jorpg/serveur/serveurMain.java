package fr.mimus.jorpg.serveur;

import java.io.File;

import fr.mimus.jorpg.commun.DataClient;
import fr.mimus.jorpg.commun.DataConfigClient;
import fr.mimus.jorpg.commun.DataConfigServeur;
import fr.mimus.jorpg.commun.DataLoader;

public class serveurMain {
	static DataConfigServeur dcs = new DataConfigServeur();
	static serveurThread serveur;
	public static void main(String[] args) {
		if((new File("Data/Serveur.wns")).exists()) {
			DataClient dc = (DataClient) DataLoader.decompileClient("Serveur");
			dcs = (DataConfigServeur) dc.data;
		}
		serveur = new serveurThread();
		serveur.start();
	}
}
