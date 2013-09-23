package fr.mimus.jorpg.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import fr.mimus.jorpg.commun.DataItem;

public class InfoItem extends JFrame {
	private static final long serialVersionUID = 1L;
	public DataItem di;
	public InfoItem() {
		this.setTitle("Information");
		this.setSize(210, 255);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		//this.setUndecorated(true);
	}
	
	public void view(DataItem d) {
		di = d;
		if(di.type == 5) {
			this.setSize(210, 120);
		} else {
			this.setSize(210, 275);
		}
		this.setVisible(true);
		repaint();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
			g2d.drawString("Nom: "+di.nom, 5, 35);
			if(di.type == 5) {
				g2d.drawString("Requis: ", 5, 50);
				g2d.drawString(" Niveau: "+di.levelReq, 5, 65);
				g2d.drawString("Regénère: ", 5, 80);
				g2d.drawString(" vie: "+di.vie, 5, 95);
				g2d.drawString(" magie: "+di.magie, 5, 110);
			} else {
				g2d.drawString("Requis: ", 5, 50);
				g2d.drawString(" Niveau: "+di.levelReq, 5, 65);
				g2d.drawString(" Force: "+di.forceReq, 5, 80);
				g2d.drawString(" Dextérité: "+di.dexteriteReq, 5, 95);
				g2d.drawString(" Endurence: "+di.endurenceReq, 5, 110);
				g2d.drawString(" Energie: "+di.energieReq, 5, 125);
				g2d.drawString("Bonus:", 5, 145);
				g2d.drawString(" Dommage: "+di.dommage, 5, 160);
				g2d.drawString(" Defense: "+di.defense, 5, 175);
				g2d.drawString(" vie: "+di.vie, 5, 190);
				g2d.drawString(" magie: "+di.magie, 5, 205);
				g2d.drawString(" Force: "+di.force, 5, 220);
				g2d.drawString(" Dextérité: "+di.dexterite, 5, 235);
				g2d.drawString(" Endurence: "+di.endurence, 5, 250);
				g2d.drawString(" Energie: "+di.energie, 5, 265);
			}
		g2d.dispose();
	}
}
