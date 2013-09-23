package fr.mimus.jorpg.editeur;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import fr.mimus.jorpg.commun.DataItem;
import fr.mimus.jorpg.commun.DataLoader;

public class EditeurItemMain extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
		//composent
		JMenuBar menuBar = new JMenuBar();
		JMenu mFichier = new JMenu("Fichier");
		JMenuItem mFichierQuitter = new JMenuItem("Quitter tout");
		JMenuItem mFichierNouv = new JMenuItem("Nouveau");
		JMenuItem mFichierSave = new JMenuItem("Sauvegarder");
		JMenuItem mFichierCharger = new JMenuItem("Charger");
		
		//
		EditeurItemBoard outil = new EditeurItemBoard();
		
	public EditeurItemMain() {
		this.setTitle("Editeur FCL - Objet");
		this.setSize(440, 470);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		initMenuBar();
		this.setJMenuBar(menuBar);
		this.add(outil);
		//this.setVisible(true);
	}
	
	public void initMenuBar() {	
		// Action Menu Fichier
		mFichierNouv.addActionListener(this);
		mFichierSave.addActionListener(this);
		mFichierCharger.addActionListener(this);
		mFichierQuitter.addActionListener(this);
		
		// Menu Fichier
		mFichier.add(mFichierNouv);
		mFichier.add(mFichierSave);
		mFichier.add(mFichierCharger);
		mFichier.add(mFichierQuitter);
		
		// Menu
		menuBar.add(mFichier);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		if(source == mFichierQuitter) {
			System.exit(0);
		}
		if(source == mFichierCharger) {
			try {
				String[] tempList = new String[EditeurBoard.item.size()];
				for(int i = 0; i < EditeurBoard.item.size(); i++) {
					tempList[i] = i + " " +EditeurBoard.item.get(i).nom;
				}
				String name = (String) JOptionPane.showInputDialog(null,"Choississez un Objet",
						"Choississez un Objet",JOptionPane.QUESTION_MESSAGE, null, tempList, tempList[0]);
				if(name == null) name = tempList[0];
				int num = EditeurBoard.getNumToList(tempList, name);
				
				outil.SelectId = num;//Integer.parseInt(JOptionPane.showInputDialog("Item num: ", "0"));
				if(outil.SelectId >= EditeurBoard.item.size()) {
					outil.SelectId = 0;
					JOptionPane.showMessageDialog(null, "Item non trouvé.");
				}
			} catch(NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "Aucun Item chargé.");
			}
			outil.relaodComponent();
			repaint();
		}
		if(source == mFichierNouv) {
			EditeurBoard.item.add(new DataItem());
			outil.SelectId = EditeurBoard.item.size()-1;
			outil.relaodComponent();
			repaint();
		}
		if(source == mFichierSave) {
			EditeurBoard.item.get(outil.SelectId).nom = outil.textBoxNom.getText();
			EditeurBoard.item.get(outil.SelectId).desc = outil.textBoxDesc.getText();
			EditeurBoard.item.get(outil.SelectId).type = outil.scrlType.getValue();
			EditeurBoard.item.get(outil.SelectId).dur = outil.scrlDur.getValue();
			EditeurBoard.item.get(outil.SelectId).pic = outil.scrlPic.getValue();
			
			EditeurBoard.item.get(outil.SelectId).dommage = outil.scrlDmg.getValue();
			EditeurBoard.item.get(outil.SelectId).defense = outil.scrlDef.getValue();
			
			EditeurBoard.item.get(outil.SelectId).levelReq = outil.scrlNivReq.getValue();
			EditeurBoard.item.get(outil.SelectId).forceReq = outil.scrlForceReq.getValue();
			EditeurBoard.item.get(outil.SelectId).dexteriteReq = outil.scrlDexReq.getValue();
			EditeurBoard.item.get(outil.SelectId).endurenceReq = outil.scrlEndReq.getValue();
			EditeurBoard.item.get(outil.SelectId).energieReq = outil.scrlEneReq.getValue();
			
			EditeurBoard.item.get(outil.SelectId).vie = outil.scrlVie.getValue();
			EditeurBoard.item.get(outil.SelectId).magie = outil.scrlMagie.getValue();
			EditeurBoard.item.get(outil.SelectId).force = outil.scrlForce.getValue();
			EditeurBoard.item.get(outil.SelectId).dexterite = outil.scrlDex.getValue();
			EditeurBoard.item.get(outil.SelectId).endurence = outil.scrlEnd.getValue();
			EditeurBoard.item.get(outil.SelectId).energie = outil.scrlEne.getValue();
			
			DataLoader.saveItem(outil.SelectId, EditeurBoard.item.get(outil.SelectId));
		}
	}

}
