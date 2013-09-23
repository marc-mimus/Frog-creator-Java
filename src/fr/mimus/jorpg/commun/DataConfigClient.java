package fr.mimus.jorpg.commun;

import java.io.Serializable;

public class DataConfigClient implements Serializable {
	private static final long serialVersionUID = 1L;
	public String ip = "127.0.0.1";
	public int port = 2500;
	
	public String gameName = "Frog Creator Bêta";
	public String gameURL = "www.frogcreator.fr";
	
	public boolean httpMAJ = false;
	public String httpURL = "";
}
