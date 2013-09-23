package fr.mimus.jorpg.editeur;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;

import fr.mimus.jorpg.commun.DataLoader;
import fr.mimus.jorpg.commun.DataShop;

public class EditeurShopBoard extends JPanel implements ActionListener,AdjustmentListener {
	private static final long serialVersionUID = 1L;

	// Composant
	Dimension size;
	Insets insets;
	JLabel labelId = new JLabel("ID du magasin: 400");
	JLabel labelNom = new JLabel("Nom du Magasin:");
	JTextField textBoxNom = new JTextField("");
	JLabel labelNum = new JLabel("Ref. Mag: ");
	JScrollBar scrlNum = new JScrollBar(JScrollBar.HORIZONTAL);
	JLabel labelItem = new JLabel("Objet: 0 hakuna matata !!!");
	JScrollBar scrlItem = new JScrollBar(JScrollBar.HORIZONTAL);
	JLabel labelVal = new JLabel("Valeur: 100000000  ");
	JScrollBar scrlVal = new JScrollBar(JScrollBar.HORIZONTAL);
	JButton btnCreer = new JButton("Créer Ref.");
	JButton btnMod = new JButton("Modifier Ref.");
	JButton btnSup = new JButton("Supprimer Ref.");
	
	// Variable
	int SelectId = 0;
	
	public EditeurShopBoard() {
		this.setLayout(null);
		insets = this.getInsets();
		
		add(labelId);
		size = labelId.getPreferredSize();
		labelId.setBounds(5 + insets.left, 5 + insets.top,
		             size.width, size.height);
		labelId.setText("ID du magasin: "+SelectId);
		
		add(labelNom);
		size = labelNom.getPreferredSize();
		labelNom.setBounds(5 + insets.left, 20 + insets.top,
		             size.width, size.height);
		
		add(textBoxNom);
		size = textBoxNom.getPreferredSize();
		textBoxNom.setBounds(5 + insets.left, 35 + insets.top,
		             200, size.height);
		textBoxNom.setText(EditeurBoard.shop.get(SelectId).name);
		
		add(labelNum);
		size = labelNum.getPreferredSize();
		labelNum.setBounds(5 + insets.left, 55 + insets.top,
		             200, size.height);
		
		scrlNum.addAdjustmentListener(this);
		scrlNum.setUnitIncrement(1);
		scrlNum.setMinimum(0);
		scrlNum.setMaximum(EditeurBoard.shop.get(SelectId).article.size()+10);
		scrlNum.setValue(0);
		//scrlType.setEnabled(false);
		add(scrlNum);
		scrlNum.setBounds(5 + insets.left, 70 + insets.top,
	             200, size.height);
		if(scrlNum.getValue() < EditeurBoard.shop.get(SelectId).article.size()) {
			labelNum.setText("Ref Num: "+scrlNum.getValue());
		} else {
			labelNum.setText("Ref Num: "+scrlNum.getValue()+"(new)");
		}
		
		add(labelItem);
		size = labelItem.getPreferredSize();
		labelItem.setBounds(5 + insets.left, 90 + insets.top,
		             200, size.height);
		
		scrlItem.addAdjustmentListener(this);
		scrlItem.setUnitIncrement(1);
		scrlItem.setMinimum(0);
		scrlItem.setMaximum(EditeurBoard.item.size()+9);
		if(EditeurBoard.shop.get(SelectId).article.size() > 0) {
			scrlItem.setValue(EditeurBoard.shop.get(SelectId).article.get(scrlNum.getValue()));
		} else {
			scrlItem.setValue(0);
		}
		//scrlType.setEnabled(false);
		add(scrlItem);
		scrlItem.setBounds(5 + insets.left, 105 + insets.top,
	             200, size.height);
		labelItem.setText("Objet: "+scrlItem.getValue()+" "+EditeurBoard.item.get(scrlItem.getValue()).nom);
	
		add(labelVal);
		size = labelVal.getPreferredSize();
		labelVal.setBounds(5 + insets.left, 125 + insets.top,
		             200, size.height);
		
		scrlVal.addAdjustmentListener(this);
		scrlVal.setUnitIncrement(1);
		scrlVal.setMinimum(0);
		scrlVal.setMaximum(100000010);
		if(EditeurBoard.shop.get(SelectId).article.size() > 0) {
			scrlVal.setValue(EditeurBoard.shop.get(SelectId).valeur.get(scrlNum.getValue()));
		} else {
			scrlVal.setValue(0);
		}
		//scrlType.setEnabled(false);
		add(scrlVal);
		scrlVal.setBounds(5 + insets.left, 140 + insets.top,
	             200, size.height);
		labelVal.setText("Valeur: "+scrlVal.getValue());
		
		btnCreer.addActionListener(this);
		add(btnCreer);
		size = btnCreer.getPreferredSize();
		btnCreer.setBounds(5 + insets.left, 160 + insets.top,
	             200, size.height-5);
		
		btnMod.addActionListener(this);
		add(btnMod);
		size = btnMod.getPreferredSize();
		btnMod.setBounds(5 + insets.left, 185 + insets.top,
	             200, size.height-5);
		
		btnSup.addActionListener(this);
		add(btnSup);
		size = btnSup.getPreferredSize();
		btnSup.setBounds(5 + insets.left, 210 + insets.top,
	             200, size.height-5);
	}
	
	public void relaodComponent() {
		labelId.setText("ID du magasin: "+SelectId);
		textBoxNom.setText(EditeurBoard.shop.get(SelectId).name);
		scrlNum.setMaximum(EditeurBoard.shop.get(SelectId).article.size()+10);
		scrlNum.setValue(0);
		if(scrlNum.getValue() < EditeurBoard.shop.get(SelectId).article.size()) {
			labelNum.setText("Ref Num: "+scrlNum.getValue());
		} else {
			labelNum.setText("Ref Num: "+scrlNum.getValue()+"(new)");
		}
		scrlItem.setMaximum(EditeurBoard.item.size()+9);
		if(EditeurBoard.shop.get(SelectId).article.size() > 0) {
			scrlItem.setValue(EditeurBoard.shop.get(SelectId).article.get(scrlNum.getValue()));
		} else {
			scrlItem.setValue(0);
		}
		labelItem.setText("Objet: "+scrlItem.getValue()+" "+EditeurBoard.item.get(scrlItem.getValue()).nom);
		if(EditeurBoard.shop.get(SelectId).article.size() > 0) {
			scrlVal.setValue(EditeurBoard.shop.get(SelectId).valeur.get(scrlNum.getValue()));
		} else {
			scrlVal.setValue(0);
		}
		labelVal.setText("Valeur: "+scrlVal.getValue());
	}
	
	public void relaodComponentOtherName() {
		scrlNum.setMaximum(EditeurBoard.shop.get(SelectId).article.size()+10);
		scrlNum.setValue(0);
		if(scrlNum.getValue() < EditeurBoard.shop.get(SelectId).article.size()) {
			labelNum.setText("Ref Num: "+scrlNum.getValue());
		} else {
			labelNum.setText("Ref Num: "+scrlNum.getValue()+"(new)");
		}
		scrlItem.setMaximum(EditeurBoard.item.size()+9);
		if(EditeurBoard.shop.get(SelectId).article.size() > 0) {
			scrlItem.setValue(EditeurBoard.shop.get(SelectId).article.get(scrlNum.getValue()));
		} else {
			scrlItem.setValue(0);
		}
		labelItem.setText("Objet: "+scrlItem.getValue()+" "+EditeurBoard.item.get(scrlItem.getValue()).nom);
		if(EditeurBoard.shop.get(SelectId).article.size() > 0) {
			scrlVal.setValue(EditeurBoard.shop.get(SelectId).valeur.get(scrlNum.getValue()));
		} else {
			scrlVal.setValue(0);
		}
		labelVal.setText("Valeur: "+scrlVal.getValue());
	}
	
	@Override
	public void adjustmentValueChanged(AdjustmentEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == scrlNum) {
			if(scrlNum.getValue() < EditeurBoard.shop.get(SelectId).article.size()) {
				labelNum.setText("Ref Num: "+scrlNum.getValue());
				scrlItem.setValue(EditeurBoard.shop.get(SelectId).article.get(scrlNum.getValue()));
				scrlVal.setValue(EditeurBoard.shop.get(SelectId).valeur.get(scrlNum.getValue()));
			} else {
				labelNum.setText("Ref Num: "+scrlNum.getValue()+"(new)");
				scrlItem.setValue(0);
				scrlVal.setValue(0);
			}
		}
		if(arg0.getSource() == scrlItem) {
			labelItem.setText("Objet: "+scrlItem.getValue()+" "+EditeurBoard.item.get(scrlItem.getValue()).nom);
		}
		
		if(arg0.getSource() == scrlVal) {
			labelVal.setText("Valeur: "+scrlVal.getValue());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnCreer) {
			EditeurBoard.shop.get(SelectId).article.add(scrlItem.getValue());
			EditeurBoard.shop.get(SelectId).valeur.add(scrlVal.getValue());
			relaodComponentOtherName();
		}
		
		if(e.getSource() == btnMod) {
			EditeurBoard.shop.get(SelectId).article.set(scrlNum.getValue(), scrlItem.getValue());
			EditeurBoard.shop.get(SelectId).valeur.set(scrlNum.getValue(), scrlVal.getValue());
			relaodComponentOtherName();
		}
		
		if(e.getSource() == btnSup) {
			EditeurBoard.shop.get(SelectId).article.remove(scrlNum.getValue());
			EditeurBoard.shop.get(SelectId).valeur.remove(scrlNum.getValue());
			relaodComponentOtherName();
		}
	}
	

}
