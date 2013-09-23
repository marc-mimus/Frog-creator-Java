package fr.mimus.jorpg.editeur;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import fr.mimus.jorpg.commun.DataLoader;
import fr.mimus.jorpg.commun.DataShop;

public class EditeurShopMain extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	//composent
	JMenuBar menuBar = new JMenuBar();
	JMenu mFichier = new JMenu("Fichier");
	JMenuItem mFichierQuitter = new JMenuItem("Quitter tout");
	JMenuItem mFichierNouv = new JMenuItem("Nouveau");
	JMenuItem mFichierSave = new JMenuItem("Sauvegarder");
	JMenuItem mFichierCharger = new JMenuItem("Charger");
	
	//
	EditeurShopBoard outil = new EditeurShopBoard();
	
	public EditeurShopMain() {
		this.setTitle("Editeur FCL - Magasin");
		this.setSize(210, 255);
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
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Object source = arg0.getSource();
		if(source == mFichierQuitter) {
			System.exit(0);
		}
		if(source == mFichierCharger) {
			try {
				String[] tempList = new String[EditeurBoard.shop.size()];
				for(int i = 0; i < EditeurBoard.shop.size(); i++) {
					tempList[i] = i + " " +EditeurBoard.shop.get(i).name;
				}
				String name = (String) JOptionPane.showInputDialog(null,"Choississez un Shop",
						"Choississez un Shop",JOptionPane.QUESTION_MESSAGE, null, tempList, tempList[0]);
				if(name == null) name = tempList[0];
				int num = EditeurBoard.getNumToList(tempList, name);
				
				outil.SelectId = num; //Integer.parseInt(JOptionPane.showInputDialog("Shop num: ", "0"));
				if(outil.SelectId >= EditeurBoard.shop.size()) {
					outil.SelectId = 0;
					JOptionPane.showMessageDialog(null, "Shop non trouvé.");
				}
			} catch(NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "Aucun Shop chargé.");
			}
			EditeurBoard.shop.set(outil.SelectId, DataLoader.loadShop("Magasin"+outil.SelectId));
			outil.relaodComponent();
			repaint();
		}
		if(source == mFichierNouv) {
			EditeurBoard.shop.add(new DataShop());
			outil.SelectId = EditeurBoard.shop.size()-1;
			outil.relaodComponent();
			repaint();
		}
		if(source == mFichierSave) {
			EditeurBoard.shop.get(outil.SelectId).name = outil.textBoxNom.getText();
			DataLoader.saveShop(outil.SelectId, EditeurBoard.shop.get(outil.SelectId));
		}
	}
}

