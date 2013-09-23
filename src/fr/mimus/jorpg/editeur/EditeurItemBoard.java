package fr.mimus.jorpg.editeur;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;

public class EditeurItemBoard extends JPanel implements ActionListener,AdjustmentListener {
	private static final long serialVersionUID = 1L;
	
	// Composant
	Dimension size;
	Insets insets;
	JLabel labelId = new JLabel("ID de l'objet: 400");
	JLabel labelNom = new JLabel("Nom de l'objet:");
	JTextField textBoxNom = new JTextField("");
	JLabel labelDesc = new JLabel("Description de l'objet:");
	JTextField textBoxDesc = new JTextField("");
	JLabel labelType = new JLabel("Type: ");
	JScrollBar scrlType = new JScrollBar(JScrollBar.HORIZONTAL);
	JLabel labelDur = new JLabel("DurabilitÃ©: Indestructible");
	JScrollBar scrlDur = new JScrollBar(JScrollBar.HORIZONTAL);
	JLabel labelDmg= new JLabel("Dommage: 999");
	JScrollBar scrlDmg = new JScrollBar(JScrollBar.HORIZONTAL);
	JLabel labelDef = new JLabel("Defense: 999");
	JScrollBar scrlDef = new JScrollBar(JScrollBar.HORIZONTAL);
	
	JLabel labelPic = new JLabel("Image: ");
	JScrollBar scrlPic = new JScrollBar(JScrollBar.HORIZONTAL);
	
	// Requis
	JLabel labelReq = new JLabel(">Les Requis");
	JLabel labelNivReq = new JLabel("Niv: 999");
	JScrollBar scrlNivReq = new JScrollBar(JScrollBar.HORIZONTAL);
	JLabel labelForceReq = new JLabel("Force: 999");
	JScrollBar scrlForceReq = new JScrollBar(JScrollBar.HORIZONTAL);
	JLabel labelDexReq = new JLabel("Dexteriter: 999");
	JScrollBar scrlDexReq = new JScrollBar(JScrollBar.HORIZONTAL);
	JLabel labelEndReq = new JLabel("Endurence: 999");
	JScrollBar scrlEndReq = new JScrollBar(JScrollBar.HORIZONTAL);
	JLabel labelEneReq = new JLabel("Energie: 999");
	JScrollBar scrlEneReq = new JScrollBar(JScrollBar.HORIZONTAL);
	
	// Bonus
	JLabel labelBonus = new JLabel(">Les Bonus");
	JLabel labelVie = new JLabel("Vie: 999");
	JScrollBar scrlVie = new JScrollBar(JScrollBar.HORIZONTAL);
	JLabel labelMagie = new JLabel("Magie: 999");
	JScrollBar scrlMagie = new JScrollBar(JScrollBar.HORIZONTAL);
	JLabel labelForce = new JLabel("Force: 999");
	JScrollBar scrlForce = new JScrollBar(JScrollBar.HORIZONTAL);
	JLabel labelDex = new JLabel("Dexteriter: 999");
	JScrollBar scrlDex = new JScrollBar(JScrollBar.HORIZONTAL);
	JLabel labelEnd = new JLabel("Endurence: 999");
	JScrollBar scrlEnd = new JScrollBar(JScrollBar.HORIZONTAL);
	JLabel labelEne = new JLabel("Energie: 999");
	JScrollBar scrlEne = new JScrollBar(JScrollBar.HORIZONTAL);
	JLabel labelExp = new JLabel("Exp: 100%");
	JScrollBar scrlExp = new JScrollBar(JScrollBar.HORIZONTAL);
	
	// Variable
	int SelectId = 0;
	String TypeItem[] = {"Amulette", "Casque", "Dos", "Arme/Main1", "Torse", "Bouclier/Main2", "Anneau 1", "Ceinture", "Anneau 2", "Jambe", "Botte", "Famillier", "Relique 1", "Relique 2", "Relique 3", "Relique 4", "Relique 5", "Potion"};
	
	public EditeurItemBoard() {
		this.setLayout(null);
		insets = this.getInsets();
		
		add(labelId);
		size = labelId.getPreferredSize();
		labelId.setBounds(5 + insets.left, 5 + insets.top,
		             size.width, size.height);
		labelId.setText("ID de l'objet: "+SelectId); 
		
		add(labelNom);
		size = labelNom.getPreferredSize();
		labelNom.setBounds(5 + insets.left, 20 + insets.top,
		             size.width, size.height);
		
		add(textBoxNom);
		size = textBoxNom.getPreferredSize();
		textBoxNom.setBounds(5 + insets.left, 35 + insets.top,
		             200, size.height);
		textBoxNom.setText(EditeurBoard.item.get(SelectId).nom);
		
		add(labelDesc);
		size = labelDesc.getPreferredSize();
		labelDesc.setBounds(5 + insets.left, 55 + insets.top,
		             size.width, size.height);
		
		add(textBoxDesc);
		size = textBoxDesc.getPreferredSize();
		textBoxDesc.setBounds(5 + insets.left, 70 + insets.top,
		             200, size.height);
		textBoxDesc.setText(EditeurBoard.item.get(SelectId).desc);
		
		add(labelType);
		size = labelType.getPreferredSize();
		labelType.setBounds(5 + insets.left, 90 + insets.top,
		             200, size.height);
		
		scrlType.addAdjustmentListener(this);
		scrlType.setUnitIncrement(1);
		scrlType.setMinimum(0);
		scrlType.setMaximum(TypeItem.length+9);
		scrlType.setValue(EditeurBoard.item.get(SelectId).type);
		//scrlType.setEnabled(false);
		add(scrlType);
		scrlType.setBounds(5 + insets.left, 105 + insets.top,
	             200, size.height);
		labelType.setText("Type: ("+scrlType.getValue()+") "+TypeItem[scrlType.getValue()]);

		add(labelDur);
		size = labelDur.getPreferredSize();
		labelDur.setBounds(5 + insets.left, 125 + insets.top,
		             200, size.height);
		
		scrlDur.addAdjustmentListener(this);
		scrlDur.setUnitIncrement(1);
		scrlDur.setMinimum(0);
		scrlDur.setMaximum(210);
		scrlDur.setValue(EditeurBoard.item.get(SelectId).dur);
		add(scrlDur);
		scrlDur.setBounds(5 + insets.left, 140 + insets.top,
	             200, size.height);
		labelDur.setText("DurabilitÃ©: " +((scrlDur.getValue()) > 0?scrlDur.getValue()+"/200": "Indestructible"));
	
		add(labelPic);
		size = labelPic.getPreferredSize();
		labelPic.setBounds(35 + insets.left, 160 + insets.top,
		             size.width, size.height);
		
		scrlPic.addAdjustmentListener(this);
		scrlPic.setUnitIncrement(1);
		scrlPic.setMinimum(0);
		scrlPic.setMaximum(EditeurBoard.itemsTile.length+10);
		scrlPic.setValue(EditeurBoard.item.get(SelectId).pic);
		add(scrlPic);
		size = scrlPic.getPreferredSize();
		scrlPic.setBounds(35 + insets.left, 175 + insets.top,
	             165, size.height);
		
		// Requis
		add(labelReq);
		size = labelReq.getPreferredSize();
		labelReq.setBounds(5 + insets.left, 220 + insets.top,
		             size.width, size.height);
		
		add(labelNivReq);
		size = labelNivReq.getPreferredSize();
		labelNivReq.setBounds(5 + insets.left, 235 + insets.top,
		             size.width, size.height);
		
		scrlNivReq.addAdjustmentListener(this);
		scrlNivReq.setUnitIncrement(1);
		scrlNivReq.setMinimum(0);
		scrlNivReq.setMaximum(110);
		scrlNivReq.setValue(EditeurBoard.item.get(SelectId).levelReq);
		add(scrlNivReq);
		size = scrlNivReq.getPreferredSize();
		scrlNivReq.setBounds(5 + insets.left, 250 + insets.top,
	             200, size.height);
		labelNivReq.setText("Niv. "+ scrlNivReq.getValue());
		
		add(labelForceReq);
		size = labelForceReq.getPreferredSize();
		labelForceReq.setBounds(5 + insets.left, 270 + insets.top,
		             size.width, size.height);
		
		scrlForceReq.addAdjustmentListener(this);
		scrlForceReq.setUnitIncrement(1);
		scrlForceReq.setMinimum(0);
		scrlForceReq.setMaximum(110);
		scrlForceReq.setValue(EditeurBoard.item.get(SelectId).forceReq);
		add(scrlForceReq);
		size = scrlForceReq.getPreferredSize();
		scrlForceReq.setBounds(5 + insets.left, 285 + insets.top,
	             200, size.height);
		labelForceReq.setText("Force: "+ scrlForceReq.getValue());
		
		add(labelDexReq);
		size = labelDexReq.getPreferredSize();
		labelDexReq.setBounds(5 + insets.left, 305 + insets.top,
		             size.width, size.height);
		
		scrlDexReq.addAdjustmentListener(this);
		scrlDexReq.setUnitIncrement(1);
		scrlDexReq.setMinimum(0);
		scrlDexReq.setMaximum(110);
		scrlDexReq.setValue(EditeurBoard.item.get(SelectId).dexteriteReq);
		add(scrlDexReq);
		size = scrlDexReq.getPreferredSize();
		scrlDexReq.setBounds(5 + insets.left, 320 + insets.top,
	             200, size.height);
		labelDexReq.setText("Dexterite: "+ scrlDexReq.getValue());
		
		add(labelEndReq);
		size = labelEndReq.getPreferredSize();
		labelEndReq.setBounds(5 + insets.left, 340 + insets.top,
		             size.width, size.height);
		
		scrlEndReq.addAdjustmentListener(this);
		scrlEndReq.setUnitIncrement(1);
		scrlEndReq.setMinimum(0);
		scrlEndReq.setMaximum(110);
		scrlEndReq.setValue(EditeurBoard.item.get(SelectId).endurenceReq);
		add(scrlEndReq);
		size = scrlEndReq.getPreferredSize();
		scrlEndReq.setBounds(5 + insets.left, 355 + insets.top,
	             200, size.height);
		labelEndReq.setText("Endurence: "+ scrlEndReq.getValue());
		
		add(labelEneReq);
		size = labelEneReq.getPreferredSize();
		labelEneReq.setBounds(5 + insets.left, 375 + insets.top,
		             size.width, size.height);
		
		scrlEneReq.addAdjustmentListener(this);
		scrlEneReq.setUnitIncrement(1);
		scrlEneReq.setMinimum(0);
		scrlEneReq.setMaximum(110);
		scrlEneReq.setValue(EditeurBoard.item.get(SelectId).energieReq);
		add(scrlEneReq);
		size = scrlEneReq.getPreferredSize();
		scrlEneReq.setBounds(5 + insets.left, 390 + insets.top,
	             200, size.height);
		labelEneReq.setText("Energie: "+ scrlEneReq.getValue());
		
		// Colone 2
		add(labelDmg);
		size = labelDmg.getPreferredSize();
		labelDmg.setBounds(210 + insets.left, 5 + insets.top,
		             size.width, size.height);
		
		scrlDmg.addAdjustmentListener(this);
		scrlDmg.setUnitIncrement(1);
		scrlDmg.setMinimum(0);
		scrlDmg.setMaximum(110);
		scrlDmg.setValue(EditeurBoard.item.get(SelectId).dommage);
		add(scrlDmg);
		size = scrlDmg.getPreferredSize();
		scrlDmg.setBounds(210 + insets.left, 20 + insets.top,
	             200, size.height);
		labelDmg.setText("Dommage: "+ scrlDmg.getValue());
		
		add(labelDef);
		size = labelDef.getPreferredSize();
		labelDef.setBounds(210 + insets.left, 40 + insets.top,
		             size.width, size.height);
		
		scrlDef.addAdjustmentListener(this);
		scrlDef.setUnitIncrement(1);
		scrlDef.setMinimum(0);
		scrlDef.setMaximum(110);
		scrlDef.setValue(EditeurBoard.item.get(SelectId).defense);
		add(scrlDef);
		size = scrlDef.getPreferredSize();
		scrlDef.setBounds(210 + insets.left, 55 + insets.top,
	             200, size.height);
		labelDef.setText("Defense: "+ scrlDef.getValue());
		
		add(labelBonus);
		size = labelBonus.getPreferredSize();
		labelBonus.setBounds(210 + insets.left, 70 + insets.top,
		             size.width, size.height);
		
		add(labelVie);
		size = labelVie.getPreferredSize();
		labelVie.setBounds(210 + insets.left, 85 + insets.top,
		             size.width, size.height);
		
		scrlVie.addAdjustmentListener(this);
		scrlVie.setUnitIncrement(1);
		scrlVie.setMinimum(0);
		scrlVie.setMaximum(110);
		scrlVie.setValue(EditeurBoard.item.get(SelectId).vie);
		add(scrlVie);
		size = scrlVie.getPreferredSize();
		scrlVie.setBounds(210 + insets.left, 100 + insets.top,
	             200, size.height);
		labelVie.setText("Force: "+ scrlVie.getValue());
		
		add(labelMagie);
		size = labelMagie.getPreferredSize();
		labelMagie.setBounds(210 + insets.left, 120 + insets.top,
		             size.width, size.height);
		
		scrlMagie.addAdjustmentListener(this);
		scrlMagie.setUnitIncrement(1);
		scrlMagie.setMinimum(0);
		scrlMagie.setMaximum(110);
		scrlMagie.setValue(EditeurBoard.item.get(SelectId).magie);
		add(scrlMagie);
		size = scrlMagie.getPreferredSize();
		scrlMagie.setBounds(210 + insets.left, 135 + insets.top,
	             200, size.height);
		labelMagie.setText("Force: "+ scrlMagie.getValue());
		
		add(labelForce);
		size = labelForce.getPreferredSize();
		labelForce.setBounds(210 + insets.left, 155 + insets.top,
		             size.width, size.height);
		
		scrlForce.addAdjustmentListener(this);
		scrlForce.setUnitIncrement(1);
		scrlForce.setMinimum(0);
		scrlForce.setMaximum(110);
		scrlForce.setValue(EditeurBoard.item.get(SelectId).force);
		add(scrlForce);
		size = scrlForce.getPreferredSize();
		scrlForce.setBounds(210 + insets.left, 170 + insets.top,
	             200, size.height);
		labelForce.setText("Force: "+ scrlForce.getValue());
		
		add(labelDex);
		size = labelDex.getPreferredSize();
		labelDex.setBounds(210 + insets.left, 190 + insets.top,
		             size.width, size.height);
		
		scrlDex.addAdjustmentListener(this);
		scrlDex.setUnitIncrement(1);
		scrlDex.setMinimum(0);
		scrlDex.setMaximum(110);
		scrlDex.setValue(EditeurBoard.item.get(SelectId).dexterite);
		add(scrlDex);
		size = scrlDex.getPreferredSize();
		scrlDex.setBounds(210 + insets.left, 205 + insets.top,
	             200, size.height);
		labelDex.setText("Dexterite: "+ scrlDex.getValue());
		
		add(labelEnd);
		size = labelEnd.getPreferredSize();
		labelEnd.setBounds(210 + insets.left, 225 + insets.top,
		             size.width, size.height);
		
		scrlEnd.addAdjustmentListener(this);
		scrlEnd.setUnitIncrement(1);
		scrlEnd.setMinimum(0);
		scrlEnd.setMaximum(110);
		scrlEnd.setValue(EditeurBoard.item.get(SelectId).endurence);
		add(scrlEnd);
		size = scrlEnd.getPreferredSize();
		scrlEnd.setBounds(210 + insets.left, 240 + insets.top,
	             200, size.height);
		labelEnd.setText("Endurence: "+ scrlEnd.getValue());
		
		add(labelEne);
		size = labelEne.getPreferredSize();
		labelEne.setBounds(210+ insets.left, 260 + insets.top,
		             size.width, size.height);
		
		scrlEne.addAdjustmentListener(this);
		scrlEne.setUnitIncrement(1);
		scrlEne.setMinimum(0);
		scrlEne.setMaximum(110);
		scrlEne.setValue(EditeurBoard.item.get(SelectId).energie);
		add(scrlEne);
		size = scrlEne.getPreferredSize();
		scrlEne.setBounds(210 + insets.left, 275 + insets.top,
	             200, size.height);
		labelEne.setText("Energie: "+ scrlEne.getValue());
	}
	
	public void relaodComponent() {
		labelId.setText("ID de l'objet: "+SelectId);
		textBoxNom.setText(EditeurBoard.item.get(SelectId).nom);
		textBoxDesc.setText(EditeurBoard.item.get(SelectId).desc);
		scrlType.setValue(EditeurBoard.item.get(SelectId).type);
		labelType.setText("Type: ("+scrlType.getValue()+") "+TypeItem[scrlType.getValue()]);
		scrlDur.setValue(EditeurBoard.item.get(SelectId).dur);
		labelDur.setText("Durabilité: " +((scrlDur.getValue()) > 0?scrlDur.getValue()+"/200": "Indestructible"));
		scrlPic.setValue(EditeurBoard.item.get(SelectId).pic);
		
		scrlDmg.setValue(EditeurBoard.item.get(SelectId).dommage);
		labelDmg.setText("Dommage: "+ scrlDmg.getValue());
		scrlDef.setValue(EditeurBoard.item.get(SelectId).defense);
		labelDef.setText("Defense: "+ scrlDef.getValue());
		// Requis
		scrlNivReq.setValue(EditeurBoard.item.get(SelectId).levelReq);
		labelNivReq.setText("Niv. "+ scrlNivReq.getValue());
		scrlForceReq.setValue(EditeurBoard.item.get(SelectId).forceReq);
		labelForceReq.setText("Force: "+ scrlForceReq.getValue());
		scrlDexReq.setValue(EditeurBoard.item.get(SelectId).dexteriteReq);
		labelDexReq.setText("Dexterite: "+ scrlDexReq.getValue());
		scrlEndReq.setValue(EditeurBoard.item.get(SelectId).endurenceReq);
		labelEndReq.setText("Endurence: "+ scrlEndReq.getValue());
		scrlEneReq.setValue(EditeurBoard.item.get(SelectId).energieReq);
		labelEneReq.setText("Energie: "+ scrlEneReq.getValue());
		// Bonus
		scrlVie.setValue(EditeurBoard.item.get(SelectId).vie);
		labelVie.setText("Vie "+ scrlVie.getValue());
		scrlMagie.setValue(EditeurBoard.item.get(SelectId).magie);
		labelMagie.setText("Magie. "+ scrlMagie.getValue());
		scrlForce.setValue(EditeurBoard.item.get(SelectId).force);
		labelForce.setText("Force: "+ scrlForce.getValue());
		scrlDex.setValue(EditeurBoard.item.get(SelectId).dexterite);
		labelDex.setText("Dexterite: "+ scrlDex.getValue());
		scrlEnd.setValue(EditeurBoard.item.get(SelectId).endurence);
		labelEnd.setText("Endurence: "+ scrlEnd.getValue());
		scrlEne.setValue(EditeurBoard.item.get(SelectId).energie);
		labelEne.setText("Energie: "+ scrlEne.getValue());
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(EditeurBoard.itemsTile[scrlPic.getValue()], 2, 160, null);
		if(scrlType.getValue() == TypeItem.length) {
			scrlDur.setEnabled(false);
			scrlDmg.setEnabled(false);
			scrlDef.setEnabled(false);
			scrlForce.setEnabled(false);
			scrlDex.setEnabled(false);
			scrlEnd.setEnabled(false);
			scrlEne.setEnabled(false);
			scrlForceReq.setEnabled(false);
			scrlDexReq.setEnabled(false);
			scrlEndReq.setEnabled(false);
			scrlEneReq.setEnabled(false);
		} else {
			if(scrlType.getValue() >= TypeItem.length-7) {
				scrlDur.setEnabled(false);
			} else {
				scrlDur.setEnabled(true);
			}
			scrlDmg.setEnabled(true);
			scrlDef.setEnabled(true);
			scrlForce.setEnabled(true);
			scrlDex.setEnabled(true);
			scrlEnd.setEnabled(true);
			scrlEne.setEnabled(true);
			scrlForceReq.setEnabled(true);
			scrlDexReq.setEnabled(true);
			scrlEndReq.setEnabled(true);
			scrlEneReq.setEnabled(true);
		}
		//Graphics2D g2d = (Graphics2D) g;
		//g2d.drawImage(EditeurBoard.itemsTile[scrlPic.getValue()], 2, 160, null);
		//super.paintComponent(g);
		//g2d.dispose();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void adjustmentValueChanged(AdjustmentEvent arg0) {
		// TODO Auto-generated method stub
		Object source = arg0.getSource();
		if(source == scrlType){
			labelType.setText("Type: ("+scrlType.getValue()+") "+TypeItem[scrlType.getValue()]);	
		}
		if(source == scrlDur) {
			labelDur.setText("Durabilité: " +((scrlDur.getValue()) > 0?scrlDur.getValue()+"/200": "Indestructible"));
		}
		
		if(source == scrlDmg) {
			labelDmg.setText("Dommage: "+ scrlDmg.getValue());
		}
		if(source == scrlDef) {
			labelDef.setText("Defense: "+ scrlDef.getValue());
		}
		
		if(source == scrlNivReq) {
			labelNivReq.setText("Niv. "+ scrlNivReq.getValue());
		}
		if(source == scrlForceReq) {
			labelForceReq.setText("Force: "+ scrlForceReq.getValue());
		}
		if(source == scrlDexReq) {
			labelDexReq.setText("Dexterite: "+ scrlDexReq.getValue());
		}
		if(source == scrlEndReq) {
			labelEndReq.setText("Endurence: "+ scrlEndReq.getValue());
		}
		if(source == scrlEneReq) {
			labelEneReq.setText("Energie: "+ scrlEneReq.getValue());
		}
		if(source == scrlVie) {
			labelVie.setText("Vie "+ scrlVie.getValue());
		}
		if(source == scrlMagie) {
			labelMagie.setText("Magie "+ scrlMagie.getValue());
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
		repaint();
	}

}
