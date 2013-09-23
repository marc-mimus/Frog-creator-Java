package fr.mimus.jorpg.commun;

import java.io.Serializable;

public class DataConfigServeur implements Serializable {
	private static final long serialVersionUID = 1L;
	public DataConfigClient dcc = new DataConfigClient();
	
	public boolean isTime = true;
	public long timeMinute = 60000;
	public long timeDay = 1440;
	public long timeSun = 440;
	public long timeNight = 1235;
	
	
}
