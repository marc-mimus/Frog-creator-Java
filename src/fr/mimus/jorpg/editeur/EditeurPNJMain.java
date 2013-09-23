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
import fr.mimus.jorpg.commun.DataPNJ;

public class EditeurPNJMain extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	//composent
	JMenuBar menuBar = new JMenuBar();
	JMenu mFichier = new JMenu("Fichier");
	JMenuItem mFichierQuitter = new JMenuItem("Quitter tout");
	JMenuItem mFichierNouv = new JMenuItem("Nouveau");
	JMenuItem mFichierSave = new JMenuItem("Sauvegarder");
	JMenuItem mFichierCharger = new JMenuItem("Charger");
	
	//
	EditeurPNJBoard outil = new EditeurPNJBoard();
	
public EditeurPNJMain() {
	this.setTitle("Editeur FCL - PNJ");
	this.setSize(440, 450);
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
			String[] tempList = new String[EditeurBoard.pnj.size()];
			for(int i = 0; i < EditeurBoard.pnj.size(); i++) {
				tempList[i] = i + " " +EditeurBoard.pnj.get(i).nom;
			}
			String name = (String) JOptionPane.showInputDialog(null,"Choississez une PNJ",
					"Choississez une PNJ",JOptionPane.QUESTION_MESSAGE, null, tempList, tempList[0]);
			if(name == null) name = tempList[0];
			int num = EditeurBoard.getNumToList(tempList, name);
			
			outil.SelectId = num;//Integer.parseInt(JOptionPane.showInputDialog("PNJ num: ", "0"));
			if(outil.SelectId >= EditeurBoard.pnj.size()) {
				outil.SelectId = 0;
				JOptionPane.showMessageDialog(null, "PNJ non trouvé.");
			}
		} catch(NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null, "Aucun PNJ chargé.");
		}
		outil.relaodComponent();
		repaint();
	}
	if(source == mFichierNouv) {
		EditeurBoard.pnj.add(new DataPNJ());
		outil.SelectId = EditeurBoard.pnj.size()-1;
		outil.relaodComponent();
		repaint();
	}
	if(source == mFichierSave) {
		EditeurBoard.pnj.get(outil.SelectId).nom = outil.textBoxNom.getText();
		EditeurBoard.pnj.get(outil.SelectId).disc = outil.textBoxDisc.getText();
		EditeurBoard.pnj.get(outil.SelectId).type = outil.scrlType.getValue();
		EditeurBoard.pnj.get(outil.SelectId).pic = outil.scrlPic.getValue();
		
		EditeurBoard.pnj.get(outil.SelectId).vie = outil.scrlVie.getValue();
		EditeurBoard.pnj.get(outil.SelectId).exp = outil.scrlExp.getValue();
		EditeurBoard.pnj.get(outil.SelectId).force = outil.scrlForce.getValue();
		EditeurBoard.pnj.get(outil.SelectId).dexterite = outil.scrlDex.getValue();
		EditeurBoard.pnj.get(outil.SelectId).endurence = outil.scrlEnd.getValue();
		EditeurBoard.pnj.get(outil.SelectId).energie = outil.scrlEne.getValue();
		EditeurBoard.pnj.get(outil.SelectId).drop1[0] = outil.scrlChance.getValue();
		EditeurBoard.pnj.get(outil.SelectId).drop1[1] = outil.scrlItem.getValue();
		EditeurBoard.pnj.get(outil.SelectId).drop1[2] = outil.scrlNombre.getValue();
		
		DataLoader.savePNJ(outil.SelectId, EditeurBoard.pnj.get(outil.SelectId));
	}
}


}
