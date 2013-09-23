package fr.mimus.jorpg.editeur;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

import fr.mimus.jorpg.commun.DataClient;
import fr.mimus.jorpg.commun.DataLoader;
import fr.mimus.jorpg.commun.DataMap;

public class EditeurMain extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	//composent
	JMenuBar menuBar = new JMenuBar();
	JMenu mFichier = new JMenu("Fichier");
	JMenuItem mFichierQuitter = new JMenuItem("Quitter");
	JMenuItem mFichierNouv = new JMenuItem("Nouveau");
	JMenuItem mFichierSave = new JMenuItem("Sauvegarder");
	JMenuItem mFichierCompile = new JMenuItem("Compiler Data");
	JMenuItem mFichierCharger = new JMenuItem("Charger");
	JMenu mCouche = new JMenu("Couche");
	JRadioButtonMenuItem mCoucheSol = new JRadioButtonMenuItem("Sol");
	JRadioButtonMenuItem mCoucheSolAnim = new JRadioButtonMenuItem("Animation Sol");
	JRadioButtonMenuItem mCoucheMasque1 = new JRadioButtonMenuItem("Masque 1");
	JRadioButtonMenuItem mCoucheMasque1Anim = new JRadioButtonMenuItem("Animation Masque 1");
	JRadioButtonMenuItem mCoucheMasque2 = new JRadioButtonMenuItem("Masque 2");
	JRadioButtonMenuItem mCoucheMasque2Anim = new JRadioButtonMenuItem("Animation Masque 2");
	JRadioButtonMenuItem mCoucheFrange1 = new JRadioButtonMenuItem("Frange 1");
	JRadioButtonMenuItem mCoucheFrange1Anim = new JRadioButtonMenuItem("Animation Frange 1");
	JRadioButtonMenuItem mCoucheFrange2 = new JRadioButtonMenuItem("Frange 2");
	JRadioButtonMenuItem mCoucheFrange2Anim = new JRadioButtonMenuItem("Animation Frange 2");
	JRadioButtonMenuItem mCoucheAttributNone = new JRadioButtonMenuItem("Aucun");
	JRadioButtonMenuItem mCoucheAttributBloque = new JRadioButtonMenuItem("Bloquer");
	JRadioButtonMenuItem mCoucheAttributTP = new JRadioButtonMenuItem("Téléporter");
	JRadioButtonMenuItem mCoucheAttributSpawnPNJ = new JRadioButtonMenuItem("Spawn PNJ");
	JRadioButtonMenuItem mCoucheAttributVendeur = new JRadioButtonMenuItem("Vendeur");
	JRadioButtonMenuItem mCoucheAttributLumiere = new JRadioButtonMenuItem("Lumière");
	ButtonGroup bg = new ButtonGroup();
	JMenu mOutil = new JMenu("Outil");
	JMenuItem mOutilRemplir = new JMenuItem("Remplir");
	JRadioButtonMenuItem mOutilPinceau = new JRadioButtonMenuItem("Pinceau");
	JRadioButtonMenuItem mOutilPot = new JRadioButtonMenuItem("Pot de Peintre");
	JMenuItem mOutilNight = new JMenuItem("Nuit");
	JMenuItem mOutilGrille = new JMenuItem("Grille");
	
	ButtonGroup og = new ButtonGroup();
	JMenu mEditeurs = new JMenu("Editeurs");
	JMenuItem mEditeursConfig = new JMenuItem("... de Configuration");
	JMenuItem mEditeursObjets = new JMenuItem("... d'Objet");
	JMenuItem mEditeursPnjs = new JMenuItem("... de Pnjs");
	JMenuItem mEditeursShop = new JMenuItem("... de Magasin");
	JMenuItem mEditeursQuete = new JMenuItem("... de Quete");
	
	JMenu mProp = new JMenu("Propriété");
	JMenuItem mPropNom = new JMenuItem("Nom de la map");
	JMenu mPropTP = new JMenu("Bord de map");
	JMenuItem mPropTPHaut = new JMenuItem("TP Haut");
	JMenuItem mPropTPDroite = new JMenuItem("TP Droite");
	JMenuItem mPropTPBas = new JMenuItem("TP Bas");
	JMenuItem mPropTPGauche = new JMenuItem("TP Gauche");
	
	EditeurBoard outil = new EditeurBoard();
	EditeurItemMain editeurItem = new EditeurItemMain();
	EditeurPNJMain editeurPnj = new EditeurPNJMain();
	EditeurShopMain editeurShop = new EditeurShopMain();
	EditeurConfigMain editeurConfig = new EditeurConfigMain();
	
	static ThreadGameLoop tgl;
	
	// anim Map
	static int animTime = 0;
	
	public EditeurMain() {
		tgl = new ThreadGameLoop(this);
		this.setTitle("Editeur FCL - Map");
		this.setSize(840, 660);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initMenuBar();
		this.setJMenuBar(menuBar);
		outil.setSize(200, 600);
		this.add(outil);
		this.setVisible(true);
		tgl.start();
	}
	
	public static void main(String[] zero) {
		@SuppressWarnings("unused")
		EditeurMain em = new EditeurMain();
	}
	
	public void initMenuBar() {	
		// Action Menu Fichier
		mFichierNouv.addActionListener(this);
		mFichierSave.addActionListener(this);
		mFichierCompile.addActionListener(this);
		mFichierCharger.addActionListener(this);
		mFichierQuitter.addActionListener(this);
		
		// Menu Fichier
		mFichier.add(mFichierNouv);
		mFichier.add(mFichierSave);
		mFichier.add(mFichierCompile);
		mFichier.add(mFichierCharger);
		mFichier.add(mFichierQuitter);
				
		// Action Menu Couche
		mCoucheSol.addActionListener(this);
		mCoucheSolAnim.addActionListener(this);
		mCoucheMasque1.addActionListener(this);
		mCoucheMasque1Anim.addActionListener(this);
		mCoucheMasque2.addActionListener(this);
		mCoucheMasque2Anim.addActionListener(this);
		mCoucheFrange1.addActionListener(this);
		mCoucheFrange1Anim.addActionListener(this);
		mCoucheFrange2.addActionListener(this);
		mCoucheFrange2Anim.addActionListener(this);
		mCoucheAttributNone.addActionListener(this);
		mCoucheAttributBloque.addActionListener(this);
		mCoucheAttributTP.addActionListener(this);
		mCoucheAttributSpawnPNJ.addActionListener(this);
		mCoucheAttributVendeur.addActionListener(this);
		mCoucheAttributLumiere.addActionListener(this);
		
		// Action Menu Outil
		mOutilRemplir.addActionListener(this);
		mOutilPinceau.addActionListener(this);
		mOutilPot.addActionListener(this);
		mOutilNight.addActionListener(this);
		mOutilGrille.addActionListener(this);
		
		// Action Menu Editeur
		mEditeursObjets.addActionListener(this);
		mEditeursPnjs.addActionListener(this);
		mEditeursShop.addActionListener(this);
		mEditeursConfig.addActionListener(this);
		//mEditeursConfig.setEnabled(false);
		mEditeursQuete.addActionListener(this);
		mEditeursQuete.setEnabled(false);
		
		// Action Menu Propriété
		mPropNom.addActionListener(this);
		mPropTPGauche.addActionListener(this);
		mPropTPDroite.addActionListener(this);
		mPropTPHaut.addActionListener(this);
		mPropTPBas.addActionListener(this);
		
		// Menu Couche Groupe
		bg.add(mCoucheSol);
		bg.add(mCoucheSolAnim);
		bg.add(mCoucheMasque1);
		bg.add(mCoucheMasque1Anim);
		bg.add(mCoucheMasque2);
		bg.add(mCoucheMasque2Anim);
		bg.add(mCoucheFrange1);
		bg.add(mCoucheFrange1Anim);
		bg.add(mCoucheFrange2);
		bg.add(mCoucheFrange2Anim);
		bg.add(mCoucheAttributNone);
		bg.add(mCoucheAttributBloque);
		bg.add(mCoucheAttributTP);
		bg.add(mCoucheAttributSpawnPNJ);
		bg.add(mCoucheAttributVendeur);
		bg.add(mCoucheAttributLumiere);
		mCoucheSol.setSelected(true);
		
		// Menu Couche
		mCouche.add(mCoucheSol);
		//mCouche.add(mCoucheSolAnim);
		mCouche.add(mCoucheMasque1);
		mCouche.add(mCoucheMasque1Anim);
		mCouche.add(mCoucheMasque2);
		mCouche.add(mCoucheMasque2Anim);
		mCouche.add(mCoucheFrange1);
		mCouche.add(mCoucheFrange1Anim);
		mCouche.add(mCoucheFrange2);
		mCouche.add(mCoucheFrange2Anim);
		mCouche.addSeparator();
		mCouche.add(mCoucheAttributNone);
		mCouche.add(mCoucheAttributBloque);
		mCouche.add(mCoucheAttributTP);
		mCouche.add(mCoucheAttributSpawnPNJ);
		mCouche.add(mCoucheAttributVendeur);
		mCouche.addSeparator();
		mCouche.add(mCoucheAttributLumiere);
		
		// groupe outil
		bg.add(mOutilPinceau);
		bg.add(mOutilPot);
		mOutilPinceau.setSelected(true);
		
		// Menu Outil
		mOutil.add(mOutilRemplir);
		mOutil.addSeparator();
		mOutil.add(mOutilPinceau);
		mOutil.add(mOutilPot);
		mOutil.addSeparator();
		mOutil.add(mOutilNight);
		mOutil.add(mOutilGrille);
		
		// Menu Editeur 
		mEditeurs.add(mEditeursConfig);
		mEditeurs.add(mEditeursObjets);
		mEditeurs.add(mEditeursPnjs);
		mEditeurs.add(mEditeursShop);
		mEditeurs.add(mEditeursQuete);
		
		// Menu prop
		mProp.add(mPropNom);
		mPropTP.add(mPropTPHaut);
		mPropTP.add(mPropTPDroite);
		mPropTP.add(mPropTPBas);
		mPropTP.add(mPropTPGauche);
		mProp.add(mPropTP);
		
		// Menu
		menuBar.add(mFichier);
		menuBar.add(mCouche);
		menuBar.add(mOutil);
		menuBar.add(mProp);
		menuBar.add(mEditeurs);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == mFichierQuitter) {
			System.exit(0);
		} else if(source == mFichierCharger) {
			try {
				
				String[] tempList = new String[EditeurBoard.map.size()];
				for(int i = 0; i < EditeurBoard.map.size(); i++) {
					tempList[i] = i + " " +EditeurBoard.map.get(i).nom;
				}
				String name = (String) JOptionPane.showInputDialog(null,"Choississez une Map",
						"Choississez une Map",JOptionPane.QUESTION_MESSAGE, null, tempList, tempList[0]);
				if(name != null) {
					int num = EditeurBoard.getNumToList(tempList, name);
	
					outil.SelectMap = num;
					if(outil.SelectMap >= EditeurBoard.map.size()) {
						outil.SelectMap = 0;
						JOptionPane.showMessageDialog(null, "Map non trouvé.");
					}
					EditeurBoard.map.set(num, DataLoader.load("map"+num));
				}
			} catch(NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "Aucune map chargé.");
			}
			repaint();
		} else if(source == mFichierNouv) {
			/*try {
				int maph = Integer.parseInt(JOptionPane.showInputDialog("Hauteur: ", "20"));
				int mapw = Integer.parseInt(JOptionPane.showInputDialog("Largeur: ", "20"));
				outil.map.add(new DataMap(maph, mapw));
			} catch(NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "Suite a une erreur la map a Ã©tÃ© initialiser par defaud.");
				outil.map.add(new DataMap());
			}*/
			EditeurBoard.map.add(new DataMap());
			outil.SelectMap = EditeurBoard.map.size()-1;
			repaint();
		} else if(source == mFichierSave) {
			DataLoader.save(outil.SelectMap, EditeurBoard.map.get(outil.SelectMap));
		} else if(source == mFichierCompile) {
			DataClient dc = new DataClient();
			dc.data = EditeurBoard.map;
			DataLoader.compileClient("Cartes", dc);
			dc = new DataClient();
			dc.data = EditeurBoard.item;
			DataLoader.compileClient("Objets", dc);
			dc = new DataClient();
			dc.data = EditeurBoard.pnj;
			DataLoader.compileClient("PNJs", dc);
			dc = new DataClient();
			dc.data = EditeurBoard.shop;
			DataLoader.compileClient("Magasins", dc);
			dc = new DataClient();
			dc.data = EditeurBoard.quete;
			DataLoader.compileClient("Quetes", dc);
			dc = new DataClient();
			dc.data = EditeurBoard.dcs;
			DataLoader.compileClient("Serveur", dc);
			dc = new DataClient();
			dc.data = EditeurBoard.dcs.dcc;
			DataLoader.compileClient("Client", dc);
			System.out.println("Compilation Terminé !");
		} else if(source == mCoucheSol) {
			outil.Selectcouche = 0;
		} else if(source == mCoucheSolAnim) {
			outil.Selectcouche = 1;
		} else if(source == mCoucheMasque1) {
			outil.Selectcouche = 2;
		} else if(source == mCoucheMasque1Anim) {
			outil.Selectcouche = 3;
		} else if(source == mCoucheMasque2) {
			outil.Selectcouche = 4;
		} else if(source == mCoucheMasque2Anim) {
			outil.Selectcouche = 5;
		} else if(source == mCoucheFrange1) {
			outil.Selectcouche = 6;
		} else if(source == mCoucheFrange1Anim) {
			outil.Selectcouche = 7;
		} else if(source == mCoucheFrange2) {
			outil.Selectcouche = 8;
		} else if(source == mCoucheFrange2Anim) {
			outil.Selectcouche = 9;
		} else if(source == mCoucheAttributNone) {
			outil.Selectcouche = 100;
		} else if(source == mCoucheAttributBloque) {
			outil.Selectcouche = 101;
		} else if(source == mCoucheAttributTP) {
			outil.Selectcouche = 102;
		} else if(source == mCoucheAttributSpawnPNJ) {
			outil.Selectcouche = 103;
		} else if(source == mCoucheAttributVendeur) {
			outil.Selectcouche = 104;
		} else if(source == mCoucheAttributLumiere) {
			outil.Selectcouche = 1000;
		} else if(source == mEditeursObjets) {
			editeurItem.setVisible(true);
			editeurItem.outil.relaodComponent();
		} else if(source == mEditeursPnjs) {
			editeurPnj.setVisible(true);
			editeurPnj.outil.relaodComponent();
		} else if(source == mEditeursShop) {
			editeurShop.setVisible(true);
			editeurShop.outil.relaodComponent();
		} else if(source == mEditeursConfig) {
			editeurConfig.night.setSelected(!EditeurBoard.dcs.isTime);
			editeurConfig.textName.setName(EditeurBoard.dcs.dcc.gameName);
			editeurConfig.setVisible(true);
		} else if(source == mEditeursQuete) {
			
		} else if(source == mOutilRemplir) {
			outil.remplirMap();
		} else if(source == mOutilPinceau) {
			outil.outil = 0;
		} else if(source == mOutilPot) {
			outil.outil = 1;
		} else if(source == mOutilNight) {
			outil.isNight = !outil.isNight;
			if(outil.isNight) outil.createNight();
		} else if(source == mOutilGrille) {
			outil.isGrille = !outil.isGrille;
		} else if(source == mPropNom) {
			EditeurBoard.map.get(outil.SelectMap).nom = JOptionPane.showInputDialog("Nom de la map: ", "");
		} else if(source == mPropTPHaut) {
			try {
				
				String[] tempList = new String[EditeurBoard.map.size()+1];
				tempList[0] = "aucune TP";
				for(int i = 0; i < EditeurBoard.map.size(); i++) {
					tempList[i+1] = i + " " +EditeurBoard.map.get(i).nom;
				}
				String name = (String) JOptionPane.showInputDialog(null,"Choississez une Map",
						"Choississez une Map",JOptionPane.QUESTION_MESSAGE, null, tempList, tempList[EditeurBoard.map.get(outil.SelectMap).border[0]+1]);
				if(name == null) name = tempList[0];
				int num = EditeurBoard.getNumToList(tempList, name);
				EditeurBoard.map.get(outil.SelectMap).border[0] = num-1;
			} catch(NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "Aucune map chargé.");
			}
			repaint();
		} else if(source == mPropTPDroite) {
			try {
				
				String[] tempList = new String[EditeurBoard.map.size()+1];
				tempList[0] = "aucune TP";
				for(int i = 0; i < EditeurBoard.map.size(); i++) {
					tempList[i+1] = i + " " +EditeurBoard.map.get(i).nom;
				}
				String name = (String) JOptionPane.showInputDialog(null,"Choississez une Map",
						"Choississez une Map",JOptionPane.QUESTION_MESSAGE, null, tempList, tempList[EditeurBoard.map.get(outil.SelectMap).border[1]+1]);
				if(name == null) name = tempList[0];
				int num = EditeurBoard.getNumToList(tempList, name);
				EditeurBoard.map.get(outil.SelectMap).border[1] = num-1;
			} catch(NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "Aucune map chargé.");
			}
			repaint();
		} else if(source == mPropTPBas) {
			try {
				
				String[] tempList = new String[EditeurBoard.map.size()+1];
				tempList[0] = "aucune TP";
				for(int i = 0; i < EditeurBoard.map.size(); i++) {
					tempList[i+1] = i + " " +EditeurBoard.map.get(i).nom;
				}
				String name = (String) JOptionPane.showInputDialog(null,"Choississez une Map",
						"Choississez une Map",JOptionPane.QUESTION_MESSAGE, null, tempList, tempList[EditeurBoard.map.get(outil.SelectMap).border[2]+1]);
				if(name == null) name = tempList[0];
				int num = EditeurBoard.getNumToList(tempList, name);
				EditeurBoard.map.get(outil.SelectMap).border[2] = num-1;
			} catch(NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "Aucune map chargé.");
			}
			repaint();
		} else if(source == mPropTPGauche) {
			try {
				
				String[] tempList = new String[EditeurBoard.map.size()+1];
				tempList[0] = "aucune TP";
				for(int i = 0; i < EditeurBoard.map.size(); i++) {
					tempList[i+1] = i + " " +EditeurBoard.map.get(i).nom;
				}
				String name = (String) JOptionPane.showInputDialog(null,"Choississez une Map",
						"Choississez une Map",JOptionPane.QUESTION_MESSAGE, null, tempList, tempList[EditeurBoard.map.get(outil.SelectMap).border[3]+1]);
				if(name == null) name = tempList[0];
				int num = EditeurBoard.getNumToList(tempList, name);
				EditeurBoard.map.get(outil.SelectMap).border[3] = num-1;
			} catch(NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "Aucune map chargé.");
			}
			repaint();
		}
	}
}
