package fr.mimus.jorpg.editeur;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;

public class EditeurPNJBoard extends JPanel implements ActionListener,AdjustmentListener {
	private static final long serialVersionUID = 1L;
	
	// Composant
	Dimension size;
	Insets insets;
	JLabel labelId = new JLabel("ID du pnj: 400");
	JLabel labelNom = new JLabel("Nom du pnj:");
	JTextField textBoxNom = new JTextField("");
	JLabel labelDisc = new JLabel("Discution:");
	JTextField textBoxDisc = new JTextField("");
	JLabel labelType = new JLabel("Type: ");
	JScrollBar scrlType = new JScrollBar(JScrollBar.HORIZONTAL);
	JLabel labelPic = new JLabel("Image: ");
	JScrollBar scrlPic = new JScrollBar(JScrollBar.HORIZONTAL);
	
	// Bonus
	JLabel labelVie = new JLabel("Vie: 999");
	JScrollBar scrlVie = new JScrollBar(JScrollBar.HORIZONTAL);
	JLabel labelForce = new JLabel("Force: 999");
	JScrollBar scrlForce = new JScrollBar(JScrollBar.HORIZONTAL);
	JLabel labelDex = new JLabel("Dexteriter: 999");
	JScrollBar scrlDex = new JScrollBar(JScrollBar.HORIZONTAL);
	JLabel labelEnd = new JLabel("Endurence: 999");
	JScrollBar scrlEnd = new JScrollBar(JScrollBar.HORIZONTAL);
	JLabel labelEne = new JLabel("Energie: 999");
	JScrollBar scrlEne = new JScrollBar(JScrollBar.HORIZONTAL);
	JLabel labelExp = new JLabel("Exp: 100000");
	JScrollBar scrlExp = new JScrollBar(JScrollBar.HORIZONTAL);
	JLabel labelChance = new JLabel("Chance: 999");
	JScrollBar scrlChance = new JScrollBar(JScrollBar.HORIZONTAL);
	JLabel labelItem = new JLabel("Objet: 999(Nom objet)");
	JScrollBar scrlItem = new JScrollBar(JScrollBar.HORIZONTAL);
	JLabel labelNombre = new JLabel("Nombre: 100");
	JScrollBar scrlNombre = new JScrollBar(JScrollBar.HORIZONTAL);
	
	// Variable
	int SelectId = 0;
	String TypePNJ[] = {"Ami", "Ennemi"};
	
	public EditeurPNJBoard() {
		this.setLayout(null);
		insets = this.getInsets();
		
		add(labelId);
		size = labelId.getPreferredSize();
		labelId.setBounds(5 + insets.left, 5 + insets.top,
		             size.width, size.height);
		labelId.setText("ID du pnj: "+SelectId);
		
		add(labelNom);
		size = labelNom.getPreferredSize();
		labelNom.setBounds(5 + insets.left, 20 + insets.top,
		             size.width, size.height);
		
		add(textBoxNom);
		size = textBoxNom.getPreferredSize();
		textBoxNom.setBounds(5 + insets.left, 35 + insets.top,
		             200, size.height);
		textBoxNom.setText(EditeurBoard.pnj.get(SelectId).nom);
		
		add(labelDisc);
		size = labelDisc.getPreferredSize();
		labelDisc.setBounds(5 + insets.left, 55 + insets.top,
		             size.width, size.height);
		
		add(textBoxDisc);
		size = textBoxDisc.getPreferredSize();
		textBoxDisc.setBounds(5 + insets.left, 70 + insets.top,
		             200, size.height);
		textBoxDisc.setText(EditeurBoard.pnj.get(SelectId).disc);
		
		add(labelType);
		size = labelType.getPreferredSize();
		labelType.setBounds(5 + insets.left, 90 + insets.top,
		             200, size.height);
		
		scrlType.addAdjustmentListener(this);
		scrlType.setUnitIncrement(1);
		scrlType.setMinimum(0);
		scrlType.setMaximum(11);
		scrlType.setValue(EditeurBoard.pnj.get(SelectId).type);
		//scrlType.setEnabled(false);
		add(scrlType);
		scrlType.setBounds(5 + insets.left, 105 + insets.top,
	             200, size.height);
		labelType.setText("Type: ("+scrlType.getValue()+") "+TypePNJ[scrlType.getValue()]);

		add(labelPic);
		size = labelPic.getPreferredSize();
		labelPic.setBounds(35 + insets.left, 125 + insets.top,
		             size.width, size.height);
		
		scrlPic.addAdjustmentListener(this);
		scrlPic.setUnitIncrement(1);
		scrlPic.setMinimum(0);
		scrlPic.setMaximum(EditeurBoard.sprite.size()+9);
		scrlPic.setValue(EditeurBoard.pnj.get(SelectId).pic);
		add(scrlPic);
		size = scrlPic.getPreferredSize();
		scrlPic.setBounds(35 + insets.left, 140 + insets.top,
	             165, size.height);
		
		
		add(labelVie);
		size = labelVie.getPreferredSize();
		labelVie.setBounds(210 + insets.left, 5 + insets.top,
		             size.width, size.height);
		
		scrlVie.addAdjustmentListener(this);
		scrlVie.setUnitIncrement(1);
		scrlVie.setMinimum(0);
		scrlVie.setMaximum(110);
		scrlVie.setValue(EditeurBoard.pnj.get(SelectId).vie);
		add(scrlVie);
		size = scrlVie.getPreferredSize();
		scrlVie.setBounds(210 + insets.left, 20 + insets.top,
	             200, size.height);
		labelVie.setText("Force: "+ scrlVie.getValue());
		
		add(labelExp);
		size = labelExp.getPreferredSize();
		labelExp.setBounds(210 + insets.left, 40 + insets.top,
		             200, size.height);
		
		scrlExp.addAdjustmentListener(this);
		scrlExp.setUnitIncrement(1);
		scrlExp.setMinimum(0);
		scrlExp.setMaximum(100010);
		scrlExp.setValue(EditeurBoard.pnj.get(SelectId).exp);
		add(scrlExp);
		size = scrlExp.getPreferredSize();
		scrlExp.setBounds(210 + insets.left, 55 + insets.top,
	             200, size.height);
		labelExp.setText("Exp. "+ scrlExp.getValue());
		
		add(labelForce);
		size = labelForce.getPreferredSize();
		labelForce.setBounds(210 + insets.left, 75 + insets.top,
		             size.width, size.height);
		
		scrlForce.addAdjustmentListener(this);
		scrlForce.setUnitIncrement(1);
		scrlForce.setMinimum(0);
		scrlForce.setMaximum(110);
		scrlForce.setValue(EditeurBoard.pnj.get(SelectId).force);
		add(scrlForce);
		size = scrlForce.getPreferredSize();
		scrlForce.setBounds(210 + insets.left, 90 + insets.top,
	             200, size.height);
		labelForce.setText("Force: "+ scrlForce.getValue());
		
		add(labelDex);
		size = labelDex.getPreferredSize();
		labelDex.setBounds(210 + insets.left, 105 + insets.top,
		             size.width, size.height);
		
		scrlDex.addAdjustmentListener(this);
		scrlDex.setUnitIncrement(1);
		scrlDex.setMinimum(0);
		scrlDex.setMaximum(110);
		scrlDex.setValue(EditeurBoard.pnj.get(SelectId).dexterite);
		add(scrlDex);
		size = scrlDex.getPreferredSize();
		scrlDex.setBounds(210 + insets.left, 120 + insets.top,
	             200, size.height);
		labelDex.setText("Dexterite: "+ scrlDex.getValue());
		
		add(labelEnd);
		size = labelEnd.getPreferredSize();
		labelEnd.setBounds(210 + insets.left, 140 + insets.top,
		             size.width, size.height);
		
		scrlEnd.addAdjustmentListener(this);
		scrlEnd.setUnitIncrement(1);
		scrlEnd.setMinimum(0);
		scrlEnd.setMaximum(110);
		scrlEnd.setValue(EditeurBoard.pnj.get(SelectId).endurence);
		add(scrlEnd);
		size = scrlEnd.getPreferredSize();
		scrlEnd.setBounds(210 + insets.left, 155 + insets.top,
	             200, size.height);
		labelEnd.setText("Endurence: "+ scrlEnd.getValue());
		
		add(labelEne);
		size = labelEne.getPreferredSize();
		labelEne.setBounds(210+ insets.left, 175 + insets.top,
		             size.width, size.height);
		
		scrlEne.addAdjustmentListener(this);
		scrlEne.setUnitIncrement(1);
		scrlEne.setMinimum(0);
		scrlEne.setMaximum(110);
		scrlEne.setValue(EditeurBoard.pnj.get(SelectId).energie);
		add(scrlEne);
		size = scrlEne.getPreferredSize();
		scrlEne.setBounds(210 + insets.left, 190 + insets.top,
	             200, size.height);
		labelEne.setText("Energie: "+ scrlEne.getValue());
		
		add(labelChance);
		size = labelChance.getPreferredSize();
		labelChance.setBounds(210+ insets.left, 210 + insets.top,
					200, size.height);
		
		scrlChance.addAdjustmentListener(this);
		scrlChance.setUnitIncrement(1);
		scrlChance.setMinimum(0);
		scrlChance.setMaximum(110);
		scrlChance.setValue(EditeurBoard.pnj.get(SelectId).drop1[0]);
		add(scrlChance);
		size = scrlChance.getPreferredSize();
		scrlChance.setBounds(210 + insets.left, 225 + insets.top,
	             200, size.height);
		labelChance.setText("Chance: "+ scrlChance.getValue()+"%");
		
		add(labelItem);
		size = labelItem.getPreferredSize();
		labelItem.setBounds(210+ insets.left, 245 + insets.top,
				200, size.height);
		
		scrlItem.addAdjustmentListener(this);
		scrlItem.setUnitIncrement(1);
		scrlItem.setMinimum(0);
		scrlItem.setMaximum(EditeurBoard.item.size()+9);
		scrlItem.setValue(EditeurBoard.pnj.get(SelectId).drop1[1]);
		add(scrlItem);
		size = scrlItem.getPreferredSize();
		scrlItem.setBounds(210 + insets.left, 260 + insets.top,
	             200, size.height);
		labelItem.setText("Objet: ("+ scrlItem.getValue()+")"+EditeurBoard.item.get(scrlItem.getValue()).nom);
		
		add(labelNombre);
		size = labelNombre.getPreferredSize();
		labelNombre.setBounds(210+ insets.left, 280 + insets.top,
		             200, size.height);
		
		scrlNombre.addAdjustmentListener(this);
		scrlNombre.setUnitIncrement(1);
		scrlNombre.setMinimum(0);
		scrlNombre.setMaximum(110);
		scrlNombre.setValue(EditeurBoard.pnj.get(SelectId).drop1[2]);
		add(scrlNombre);
		size = scrlNombre.getPreferredSize();
		scrlNombre.setBounds(210 + insets.left, 295 + insets.top,
	             200, size.height);
		labelNombre.setText("Nombre: "+ scrlNombre.getValue());
	}
	
	public void relaodComponent() {
		labelId.setText("ID du pnj: "+SelectId);
		textBoxNom.setText(EditeurBoard.pnj.get(SelectId).nom);
		textBoxDisc.setText(EditeurBoard.pnj.get(SelectId).disc);
		scrlType.setValue(EditeurBoard.pnj.get(SelectId).type);
		labelType.setText("Type: ("+scrlType.getValue()+") "+TypePNJ[scrlType.getValue()]);
		scrlPic.setValue(EditeurBoard.pnj.get(SelectId).pic);
		
		scrlVie.setValue(EditeurBoard.pnj.get(SelectId).vie);
		labelVie.setText("Vie "+ scrlVie.getValue());
		scrlExp.setValue(EditeurBoard.pnj.get(SelectId).exp);
		labelExp.setText("Exp. "+ scrlExp.getValue());
		scrlForce.setValue(EditeurBoard.pnj.get(SelectId).force);
		labelForce.setText("Force: "+ scrlForce.getValue());
		scrlDex.setValue(EditeurBoard.pnj.get(SelectId).dexterite);
		labelDex.setText("Dexterite: "+ scrlDex.getValue());
		scrlEnd.setValue(EditeurBoard.pnj.get(SelectId).endurence);
		labelEnd.setText("Endurence: "+ scrlEnd.getValue());
		scrlEne.setValue(EditeurBoard.pnj.get(SelectId).energie);
		labelEne.setText("Energie: "+ scrlEne.getValue());
		scrlChance.setValue(EditeurBoard.pnj.get(SelectId).drop1[0]);
		labelChance.setText("Chance: "+ scrlChance.getValue()+"%");
		scrlItem.setMaximum(EditeurBoard.item.size()+9);
		scrlItem.setValue(EditeurBoard.pnj.get(SelectId).drop1[1]);
		labelItem.setText("Objet: ("+ scrlItem.getValue()+")"+EditeurBoard.item.get(scrlItem.getValue()).nom);
		scrlNombre.setValue(EditeurBoard.pnj.get(SelectId).drop1[2]);
		labelNombre.setText("Nombre: "+ scrlNombre.getValue());
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		g.drawImage(EditeurBoard.sprite.get(scrlPic.getValue())[0], 2, 160, null);
	}
	
	@Override
	public void adjustmentValueChanged(AdjustmentEvent arg0) {
		// TODO Auto-generated method stub
		Object source = arg0.getSource();
		if(source == scrlType){
			labelType.setText("Type: ("+scrlType.getValue()+") "+TypePNJ[scrlType.getValue()]);	
		}
		if(source == scrlVie) {
			labelVie.setText("Vie "+ scrlVie.getValue());
		}
		if(source == scrlExp) {
			labelExp.setText("Exp. "+ scrlExp.getValue());
		}
		if(source == scrlForce) {
			labelForce.setText("Force: "+ scrlForce.getValue());
		}
		if(source == scrlDex) {
			labelDex.setText("Dexterite: "+ scrlDex.getValue());
		}
		if(source == scrlEnd) {
			labelEnd.setText("Endurence: "+ scrlEnd.getValue());
		}
		if(source == scrlEne) {
			labelEne.setText("Energie: "+ scrlEne.getValue());
		}
		if(source == scrlChance) {
			labelChance.setText("Chance: "+ scrlChance.getValue()+"%");
		}
		if(source == scrlItem) {
			labelItem.setText("Objet: ("+ scrlItem.getValue()+")"+EditeurBoard.item.get(scrlItem.getValue()).nom);
			if(EditeurBoard.item.get(scrlItem.getValue()).type <= 4) {
				scrlNombre.setMaximum(11);
			} else {
				scrlNombre.setMaximum(110);
			}
		}
		if(source == scrlNombre) {
			labelNombre.setText("Nombre: "+ scrlNombre.getValue());
		}
		
		repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
