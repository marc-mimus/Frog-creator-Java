package fr.mimus.jorpg.editeur;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class EditeurConfigMain extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	Dimension size;
	Insets insets;
	JLabel labelClient = new JLabel("-- Config Client --");
	JTextField textName = new JTextField("Frog Creator Bêta");
	JTextField textIP = new JTextField("127.0.0.1");
	JTextField textPort = new JTextField("2500");
	JLabel labelServeur = new JLabel("-- Config Serveur --");
	JCheckBox night = new JCheckBox("Desactiver nuit");
	public EditeurConfigMain() {
		this.setTitle("Editeur FCL - Configuration");
		this.setSize(170, 255);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		this.setLayout(null);
		insets = this.getInsets();
		
		this.add(labelClient);
		size = labelClient.getPreferredSize();
		labelClient.setBounds(7 + insets.left, 5 + insets.top,
		             size.width, size.height);
		
		textName.addActionListener(this);
		this.add(textName);
		size = textName.getPreferredSize();
		textName.setBounds(5 + insets.left, 20 + insets.top,
	             150, size.height);

		this.add(textIP);
		size = textIP.getPreferredSize();
		textIP.setBounds(5 + insets.left, 40 + insets.top,
	             150, size.height);
		textIP.setEnabled(false);
		
		this.add(textPort);
		size = textPort.getPreferredSize();
		textPort.setBounds(5 + insets.left, 60 + insets.top,
	             150, size.height);
		textPort.setEnabled(false);
		
		this.add(labelServeur);
		size = labelServeur.getPreferredSize();
		labelServeur.setBounds(10 + insets.left, 80 + insets.top,
		             size.width, size.height);
		night.addActionListener(this);
		this.add(night);
		size = night.getPreferredSize();
		night.setBounds(10 + insets.left, 100 + insets.top,
		             size.width, size.height);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == night) {
			EditeurBoard.dcs.isTime = !night.isSelected();
		}
		if(arg0.getSource() == textName) {
			EditeurBoard.dcs.dcc.gameName = textName.getText();
		}
		
	}

}
