package fr.mimus.jorpg.commun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

public class DataLoader {
	public static DataMap load(String id) {
		DataMap map;
		try {
			if((new File("Data/Maps/"+id+".ns")).exists() == false) {
				JOptionPane.showMessageDialog(null, "Chargement echouer 2 !") ;
				return null;
			}
	      FileInputStream fichier = new FileInputStream("Data/Maps/"+id+".ns");
	      ObjectInputStream ois = new ObjectInputStream(fichier);
	      map = (DataMap) ois.readObject();
	      fichier.close();
	      ois.close();
	      return map;
	    } 
	    catch (java.io.IOException e) {
	      e.printStackTrace();
	    }
	    catch (ClassNotFoundException e) {
	      e.printStackTrace();
	    }
		return null;
	}
	
	public static void save(int id, DataMap map) {
		try {
			if((new File("Data/Maps/")).exists() == false) (new File("Data/Maps/")).mkdirs();
		      FileOutputStream fichier = new FileOutputStream("Data/Maps/map"+id+".ns");
		      ObjectOutputStream oos = new ObjectOutputStream(fichier);
		      oos.writeObject(map);
		      oos.flush();
		      oos.close();
	    }
	    catch (java.io.IOException e) {
	      e.printStackTrace();
	    }
	}
	
	public static DataJoueur loadJoueur(String id) {
		DataJoueur map;
		try {
			if((new File("Serveur/Comptes/"+id+".ns")).exists() == false) {
				JOptionPane.showMessageDialog(null, "Chargement echouer 2 !") ;
				return null;
			}
	      FileInputStream fichier = new FileInputStream("Serveur/Comptes/"+id+".ns");
	      ObjectInputStream ois = new ObjectInputStream(fichier);
	      map = (DataJoueur) ois.readObject();
	      fichier.close();
	      ois.close();
	      return map;
	    } 
	    catch (java.io.IOException e) {
	      e.printStackTrace();
	    }
	    catch (ClassNotFoundException e) {
	      e.printStackTrace();
	    }
		return null;
	}
	public static void saveJoueur(DataJoueur map) {
		try {
			if((new File("Serveur/Comptes/")).exists() == false) (new File("Serveur/Comptes/")).mkdirs();
		      FileOutputStream fichier = new FileOutputStream("Serveur/Comptes/"+map.nom+".ns");
		      ObjectOutputStream oos = new ObjectOutputStream(fichier);
		      oos.writeObject(map);
		      oos.flush();
		      oos.close();
	    }
	    catch (java.io.IOException e) {
	      e.printStackTrace();
	    }
	}
	
	public static DataItem loadItem(String id) {
		DataItem map;
		try {
			if((new File("Data/Objets/"+id+".ns")).exists() == false) {
				JOptionPane.showMessageDialog(null, "Chargement echouer 2 !") ;
				return null;
			}
	      FileInputStream fichier = new FileInputStream("Data/Objets/"+id+".ns");
	      ObjectInputStream ois = new ObjectInputStream(fichier);
	      map = (DataItem) ois.readObject();
	      fichier.close();
	      ois.close();
	      return map;
	    } 
	    catch (java.io.IOException e) {
	      e.printStackTrace();
	    }
	    catch (ClassNotFoundException e) {
	      e.printStackTrace();
	    }
		return null;
	}
	
	public static void saveItem(int id, DataItem map) {
		try {
			if((new File("Data/Objets/")).exists() == false) (new File("Data/Objets/")).mkdirs();
		      FileOutputStream fichier = new FileOutputStream("Data/Objets/objet"+id+".ns");
		      ObjectOutputStream oos = new ObjectOutputStream(fichier);
		      oos.writeObject(map);
		      oos.flush();
		      oos.close();
	    }
	    catch (java.io.IOException e) {
	      e.printStackTrace();
	    }
	}
	public static DataPNJ loadPNJ(String id) {
		DataPNJ map;
		try {
			if((new File("Data/PNJs/"+id+".ns")).exists() == false) {
				JOptionPane.showMessageDialog(null, "Chargement echouer 2 !") ;
				return null;
			}
	      FileInputStream fichier = new FileInputStream("Data/PNJs/"+id+".ns");
	      ObjectInputStream ois = new ObjectInputStream(fichier);
	      map = (DataPNJ) ois.readObject();
	      fichier.close();
	      ois.close();
	      return map;
	    } 
	    catch (java.io.IOException e) {
	      e.printStackTrace();
	    }
	    catch (ClassNotFoundException e) {
	      e.printStackTrace();
	    }
		return null;
	}
	
	public static void savePNJ(int id, DataPNJ map) {
		try {
			if((new File("Data/PNJs/")).exists() == false) (new File("Data/PNJs/")).mkdirs();
		      FileOutputStream fichier = new FileOutputStream("Data/PNJs/pnj"+id+".ns");
		      ObjectOutputStream oos = new ObjectOutputStream(fichier);
		      oos.writeObject(map);
		      oos.flush();
		      oos.close();
	    }
	    catch (java.io.IOException e) {
	      e.printStackTrace();
	    }
	}
	
	public static DataShop loadShop(String id) {
		DataShop map;
		try {
			if((new File("Data/Magasins/"+id+".ns")).exists() == false) {
				JOptionPane.showMessageDialog(null, "Chargement echouer 2 !") ;
				return null;
			}
	      FileInputStream fichier = new FileInputStream("Data/Magasins/"+id+".ns");
	      ObjectInputStream ois = new ObjectInputStream(fichier);
	      map = (DataShop) ois.readObject();
	      fichier.close();
	      ois.close();
	      return map;
	    } 
	    catch (java.io.IOException e) {
	      e.printStackTrace();
	    }
	    catch (ClassNotFoundException e) {
	      e.printStackTrace();
	    }
		return null;
	}
	
	public static void saveShop(int id, DataShop map) {
		try {
			if((new File("Data/Magasins/")).exists() == false) (new File("Data/Magasins/")).mkdirs();
		      FileOutputStream fichier = new FileOutputStream("Data/Magasins/Magasin"+id+".ns");
		      ObjectOutputStream oos = new ObjectOutputStream(fichier);
		      oos.writeObject(map);
		      oos.flush();
		      oos.close();
	    }
	    catch (java.io.IOException e) {
	      e.printStackTrace();
	    }
	}
	public static DataQuest loadQuete(String id) {
		DataQuest map;
		try {
			if((new File("Data/Quetes/"+id+".ns")).exists() == false) {
				JOptionPane.showMessageDialog(null, "Chargement echouer 2 !") ;
				return null;
			}
	      FileInputStream fichier = new FileInputStream("Data/Quetes/"+id+".ns");
	      ObjectInputStream ois = new ObjectInputStream(fichier);
	      map = (DataQuest) ois.readObject();
	      fichier.close();
	      ois.close();
	      return map;
	    } 
	    catch (java.io.IOException e) {
	      e.printStackTrace();
	    }
	    catch (ClassNotFoundException e) {
	      e.printStackTrace();
	    }
		return null;
	}
	
	public static void saveQuete(int id, DataQuest map) {
		try {
			if((new File("Data/Quetes/")).exists() == false) (new File("Data/Quetes/")).mkdirs();
		      FileOutputStream fichier = new FileOutputStream("Data/Quetes/Quete"+id+".ns");
		      ObjectOutputStream oos = new ObjectOutputStream(fichier);
		      oos.writeObject(map);
		      oos.flush();
		      oos.close();
	    }
	    catch (java.io.IOException e) {
	      e.printStackTrace();
	    }
	}
	
	public static void compileClient(String name, Object data) {
		try {
			if((new File("Data/")).exists() == false) (new File("Data/")).mkdirs();
		      FileOutputStream fichier = new FileOutputStream("Data/"+name+".wns");
		      ObjectOutputStream oos = new ObjectOutputStream(fichier);
		      oos.writeObject(data);
		      oos.flush();
		      oos.close();
	    }
	    catch (java.io.IOException e) {
	      e.printStackTrace();
	    }
	}
	public static Object decompileClient(String name) {
		Object map;
		try {
			if((new File("Data/"+name+".wns")).exists() == false) {
				JOptionPane.showMessageDialog(null, "Erreur Chargement: "+name+" non trouvé...") ;
				System.exit(-1);
				return null;
			}
	      FileInputStream fichier = new FileInputStream("Data/"+name+".wns");
	      ObjectInputStream ois = new ObjectInputStream(fichier);
	      map = ois.readObject();
	      fichier.close();
	      ois.close();
	      return map;
	    } 
	    catch (java.io.IOException e) {
	      e.printStackTrace();
	    }
	    catch (ClassNotFoundException e) {
	      e.printStackTrace();
	    }
		return null;
	}
}
