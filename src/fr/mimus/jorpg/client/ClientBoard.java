package fr.mimus.jorpg.client;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import fr.mimus.jorpg.commun.DataCase;
import fr.mimus.jorpg.commun.DataClient;
import fr.mimus.jorpg.commun.DataItem;
import fr.mimus.jorpg.commun.DataItemSlot;
import fr.mimus.jorpg.commun.DataLoader;
import fr.mimus.jorpg.commun.DataMap;
import fr.mimus.jorpg.commun.DataPNJ;
import fr.mimus.jorpg.commun.DataPNJMap;
import fr.mimus.jorpg.commun.DataPersonnage;
import fr.mimus.jorpg.commun.DataQuest;
import fr.mimus.jorpg.commun.DataShop;
import fr.mimus.jorpg.commun.MsgMap;
import fr.mimus.jorpg.commun.TextureSpliter;

public class ClientBoard extends JPanel implements ActionListener,MouseListener,KeyListener{
	private static final long serialVersionUID = 1L;
	// Parent
	ClientMain cm;
	
	// Composent
	Dimension size;
	Insets insets;
	JButton btn_login = new JButton("Connexion");
	JButton btn_insc = new JButton("Inscription");
	JButton btn_cp = new JButton("créer Personnage");
	JTextField textBoxLogin = new JTextField("");
	JPasswordField textBoxMDP = new JPasswordField("");
	JTextField textBoxNom = new JTextField("nom");
	JLabel labelLogin = new JLabel("Nom de Compte:");
	JLabel labelMDP = new JLabel("Mot de Passe");
	JLabel labelNom = new JLabel("Nom du perso:");
	
	//JTextField textChat = new JTextField("");
	String textChat = "";
	boolean focusChat = false;
	// Image
	ImageIcon ii;
	ArrayList<BufferedImage[]> tex;
	ArrayList<BufferedImage[]> sprite;
	ArrayList<BufferedImage[]> paperdolls;
	BufferedImage[] itemsTile;
	Image background;
	Image[] inventaire = new Image[3];
	Image fiche;
	Image[] magasin = new Image[2];
	
	// Variable de jeu
	static ArrayList<DataMap> map;
	ArrayList<DataItem> item;
	static DataItemSlot[][] itemMap;
	static ArrayList<DataPNJMap> pnjMap;
	static ArrayList<DataPNJ> pnj;
	static ArrayList<DataShop> shop;
	static ArrayList<DataQuest> quete;
	static int animTime = 0;
	static ArrayList<String> chatBox;
	static ArrayList<MsgMap> msgMap;
	static long chatTime;
	static int lastfps = 60;
	static int fps = 0;
	static long lastTime = 0;
	DataCase tempCase;
	BufferedImage tempImg;
	
	// Nuit
	static BufferedImage night;
	static boolean isNight = false;
	
	// - interface
	static boolean inInventaire = false;
	static int selectInventaireID = 0;
	static boolean inFiche = false;
	static boolean inShop = false;
	static int selectShop = 0;
	static int selectShopPage = 0;
	static int selectShopId = 0;
	static InfoItem infoItem = new InfoItem();
	
	@SuppressWarnings("unchecked")
	public ClientBoard(ClientMain c) {
		chatBox = new ArrayList<String>();
		msgMap = new ArrayList<MsgMap>();
		cm = c;
		this.setLayout(null);
		
		insets = this.getInsets();
		
		btn_insc.addActionListener(this);
		add(btn_insc);
		size = btn_insc.getPreferredSize();
		btn_insc.setBounds(269 + insets.left, 355 + insets.top,
		             size.width, size.height);
		
		btn_login.addActionListener(this);
		add(btn_login);
		size = btn_login.getPreferredSize();
		btn_login.setBounds(269 + insets.left, 385 + insets.top,
		             size.width, size.height);
		btn_cp.addActionListener(this);
		
		add(btn_cp);
		size = btn_cp.getPreferredSize();
		btn_cp.setBounds(229 + insets.left, 355 + insets.top,
		             size.width, size.height);
		
		add(labelLogin);
		size = labelLogin.getPreferredSize();
		labelLogin.setBounds(214 + insets.left, 226 + insets.top,
		             size.width, size.height);
		
		add(textBoxLogin);
		size = textBoxLogin.getPreferredSize();
		textBoxLogin.setBounds(218 + insets.left, 241 + insets.top,
		             200, size.height);
		
		add(labelNom);
		size = labelNom.getPreferredSize();
		labelNom.setBounds(214 + insets.left, 226 + insets.top,
		             size.width, size.height);
		
		add(textBoxNom);
		size = textBoxNom.getPreferredSize();
		textBoxNom.setBounds(218 + insets.left, 241 + insets.top,
		             200, size.height);
		
		add(labelMDP);
		size = labelMDP.getPreferredSize();
		labelMDP.setBounds(214 + insets.left, 261 + insets.top,
		             size.width, size.height);
		
		add(textBoxMDP);
		size = textBoxMDP.getPreferredSize();
		textBoxMDP.setBounds(218 + insets.left, 276 + insets.top,
		             200, size.height);
		
		
		/*add(textChat);
		size = textChat.getPreferredSize();
		textChat.setBounds(5 + insets.left, 620 + insets.top,
		             200, size.height);
		textChat.setVisible(false);*/
		
		// Chargement Image
		tex = new ArrayList<BufferedImage[]>();
		String[] listefichiers = (new File("Data/Texture/")).list(); 
		for(int i=0;i<listefichiers.length;i++){
			if((new File("Data/Texture/Tiles"+i+".png")).exists()) {
				ii = new ImageIcon("Data/Texture/Tiles"+i+".png");
				tex.add(TextureSpliter.getAllTexture(ii));
			}
		} 
		//ii = new ImageIcon("Data/Texture/Tiles0.png");
		//tex = TextureSpliter.getAllTexture(ii);
		ii = new ImageIcon("Data/Interfaces/background.png");
		background = ii.getImage();
		ii = new ImageIcon("Data/Interfaces/inventaire.png");
		inventaire[0] = ii.getImage();
		ii = new ImageIcon("Data/Interfaces/inventaire_select.png");
		inventaire[1] = ii.getImage();
		ii = new ImageIcon("Data/Interfaces/inventaire_stuff.png");
		inventaire[2] = ii.getImage();
		ii = new ImageIcon("Data/Interfaces/fiche.png");
		fiche = ii.getImage();
		ii = new ImageIcon("Data/Interfaces/shop.png");
		magasin[0] = ii.getImage();
		ii = new ImageIcon("Data/Interfaces/shop_select.png");
		magasin[1] = ii.getImage();
		// Initialisation Map
		map = new ArrayList<DataMap>();
		item = new ArrayList<DataItem>();
		itemMap = new DataItemSlot[20][20];
		pnjMap = new ArrayList<DataPNJMap>();
		pnj = new ArrayList<DataPNJ>();
		shop = new ArrayList<DataShop>();
		quete = new ArrayList<DataQuest>();
		DataClient dc = new DataClient();
		dc = (DataClient) DataLoader.decompileClient("Cartes");
		map = (ArrayList<DataMap>) dc.data;
		dc = (DataClient) DataLoader.decompileClient("Objets");
		item = (ArrayList<DataItem>) dc.data;
		dc = (DataClient) DataLoader.decompileClient("PNJs");
		pnj = (ArrayList<DataPNJ>) dc.data;
		dc = (DataClient) DataLoader.decompileClient("Magasins");
		shop = (ArrayList<DataShop>) dc.data;
		dc = (DataClient) DataLoader.decompileClient("Quetes");
		quete = (ArrayList<DataQuest>) dc.data;
		for(int x = 0; x < itemMap.length; x++) {
			for(int y = 0; y < itemMap[x].length; y++) {
				itemMap[x][y] = new DataItemSlot();
			}
		}
		
		if(quete.size() == 0) {
			quete.add(new DataQuest());
		}
		if(shop.size() == 0) {
			shop.add(new DataShop());
		}
		if(pnj.size() == 0) {
			pnj.add(new DataPNJ());
		}
		if(item.size() == 0) {
			item.add(new DataItem());
		}
		if(map.size() == 0) {
			JOptionPane.showMessageDialog(null, "Erreur aucune map trouvé !");
			System.exit(0);
		}
		
		sprite = new ArrayList<BufferedImage[]>();
		listefichiers = (new File("Data/Sprites/")).list(); 
		for(int i=0;i<listefichiers.length;i++){
			if((new File("Data/Sprites/"+i+".png")).exists()) {
				ii = new ImageIcon("Data/Sprites/"+i+".png");
				sprite.add(TextureSpliter.getAllTextureWith(ii, 8, 4));
			}
			/*if(listefichiers[i].endsWith(".png")==true){
				System.out.println(listefichiers[i]);
				ii = new ImageIcon("Data/Sprites/"+listefichiers[i]);
				sprite.add(TextureSpliter.getAllTextureWith(ii, 8, 4));
			}*/
		} 
		
		paperdolls = new ArrayList<BufferedImage[]>();
		listefichiers = (new File("Data/Paperdolls/")).list(); 
		for(int i=0;i<listefichiers.length;i++){
			if((new File("Data/Paperdolls/Paperdolls"+i+".png")).exists()) {
				ii = new ImageIcon("Data/Paperdolls/Paperdolls"+i+".png");
				paperdolls.add(TextureSpliter.getAllTextureWith(ii, 8, 4));
			}
		} 
		
		ii = new ImageIcon("Data/items.png");
		itemsTile = TextureSpliter.getAllTexture(ii);
		
		// Action Fenetre
		this.setFocusable(true);
		addMouseListener(this);
		addKeyListener(this);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
			if(cm.menu == 5) {
				btn_insc.setVisible(false);
				btn_login.setVisible(false);
				textBoxLogin.setVisible(false);
				textBoxMDP.setVisible(false);
				labelLogin.setVisible(false);
				labelMDP.setVisible(false);
				btn_cp.setVisible(false);
				textBoxNom.setVisible(false);
				labelNom.setVisible(false);
				
				for(int y = 0; y < map.get(ClientMain.joueur[ClientMain.client.myid].map).height; y++) {
					for(int x = 0; x < map.get(ClientMain.joueur[ClientMain.client.myid].map).width; x++) {
						tempCase = map.get(ClientMain.joueur[ClientMain.client.myid].map).Case[x][y];
						g2d.drawImage(tex.get(tempCase.solTile[0])[tempCase.sol[0]], x*32, y*32, null);
						if(tempCase.sol[1] != 0) g2d.drawImage(tex.get(tempCase.solTile[1])[tempCase.sol[1]], x*32, y*32, null);

						if(tempCase.masque[0] != 0) g2d.drawImage(tex.get(tempCase.masqueTile[0])[tempCase.masque[0]], x*32, y*32, null);
						if(tempCase.masque[2] != 0 && animTime == 0) g2d.drawImage(tex.get(tempCase.masqueTile[2])[tempCase.masque[2]], x*32, y*32, null);
						if(tempCase.masque[1] != 0) g2d.drawImage(tex.get(tempCase.masqueTile[1])[tempCase.masque[1]], x*32, y*32, null);
						if(tempCase.masque[3] != 0 && animTime == 1) g2d.drawImage(tex.get(tempCase.masqueTile[3])[tempCase.masque[3]], x*32, y*32, null);
						

						if(itemMap[x][y].id >= 0 && itemMap[x][y].id < item.size()) {
							g2d.drawImage(itemsTile[item.get(itemMap[x][y].id).pic], x*32, y*32, null);
						}
					}
				}
				g2d.setColor(Color.red);
				for(int i = 0; i < ClientMain.joueur.length; i++) {
					if(ClientMain.joueur[i].nom != null) {
						if(ClientMain.joueur[i].nom.length() > 0 && ClientMain.joueur[i].map == ClientMain.joueur[ClientMain.client.myid].map) {
							//System.out.println(i);
							if(ClientMain.joueur[i].sprite >= 0 && ClientMain.joueur[i].sprite < sprite.size()) {
								tempImg = sprite.get(ClientMain.joueur[i].sprite)[4+ClientMain.joueur[i].anim];
								if(ClientMain.joueur[i].dir == 0) tempImg = sprite.get(ClientMain.joueur[i].sprite)[28+ClientMain.joueur[i].anim];
								if(ClientMain.joueur[i].dir == 1) tempImg = sprite.get(ClientMain.joueur[i].sprite)[20+ClientMain.joueur[i].anim];
								if(ClientMain.joueur[i].dir == 3) tempImg = sprite.get(ClientMain.joueur[i].sprite)[12+ClientMain.joueur[i].anim];
								g2d.drawImage(tempImg, ClientMain.joueur[i].x*32+ClientMain.joueur[i].offsetX, ClientMain.joueur[i].y*32+ClientMain.joueur[i].offsetY, null);
							} else {
								g2d.drawString("J", ClientMain.joueur[i].x*32+14, ClientMain.joueur[i].y*32+18);
							}
						}
					}
				}
				for(int i = 0; i < pnjMap.size(); i++) {
					if(pnjMap.get(i).id >= 0 && pnjMap.get(i).vie > 0) {
						DataPNJ p = pnj.get(pnjMap.get(i).id);
						DataPNJMap pm = pnjMap.get(i);
						if(p.pic >= 0 && p.pic < sprite.size()) {
							tempImg = sprite.get(p.pic)[4+pm.anim];
							if(pm.dir == 0) tempImg = sprite.get(p.pic)[28+pm.anim];
							if(pm.dir == 1) tempImg = sprite.get(p.pic)[20+pm.anim];
							if(pm.dir == 3) tempImg = sprite.get(p.pic)[12+pm.anim];
							g2d.drawImage(tempImg, pm.x*32+pm.offsetX, pm.y*32+pm.offsetY, null);
						} else {
							g2d.drawString("IA", pm.x*32+14, pm.y*32+18);
						}
					}
				}
				for(int i = 0; i < ClientMain.joueur.length; i++) {
					if(ClientMain.joueur[i].nom != null) {
						if(ClientMain.joueur[i].nom.length() > 0 && ClientMain.joueur[i].map == ClientMain.joueur[ClientMain.client.myid].map) {
							//System.out.println(i);
							if(ClientMain.joueur[i].sprite >= 0 && ClientMain.joueur[i].sprite < sprite.size()) {
								tempImg = sprite.get(ClientMain.joueur[i].sprite)[0+ClientMain.joueur[i].anim];

								if(ClientMain.joueur[i].dir == 0) tempImg = sprite.get(ClientMain.joueur[i].sprite)[24+ClientMain.joueur[i].anim];
								if(ClientMain.joueur[i].dir == 1) tempImg = sprite.get(ClientMain.joueur[i].sprite)[16+ClientMain.joueur[i].anim];
								if(ClientMain.joueur[i].dir == 3) tempImg = sprite.get(ClientMain.joueur[i].sprite)[8+ClientMain.joueur[i].anim];
								g2d.drawImage(tempImg, ClientMain.joueur[i].x*32+ClientMain.joueur[i].offsetX, ClientMain.joueur[i].y*32-tempImg.getHeight()+ClientMain.joueur[i].offsetY, null);
							} else {
								g2d.drawString("J", ClientMain.joueur[i].x*32+14, ClientMain.joueur[i].y*32+18);
							}
						}
					}
				}
				
				for(int i = 0; i < pnjMap.size(); i++) {
					if(pnjMap.get(i).id >= 0 && pnjMap.get(i).vie > 0) {
						DataPNJ p = pnj.get(pnjMap.get(i).id);
						DataPNJMap pm = pnjMap.get(i);
						if(p.pic >= 0 && p.pic < sprite.size()) {
							tempImg = sprite.get(p.pic)[0+pm.anim];
							if(pm.dir == 0) tempImg = sprite.get(p.pic)[24+pm.anim];
							if(pm.dir == 1) tempImg = sprite.get(p.pic)[16+pm.anim];
							if(pm.dir == 3) tempImg = sprite.get(p.pic)[8+pm.anim];
							g2d.drawImage(tempImg, pm.x*32+pm.offsetX, pm.y*32+pm.offsetY-tempImg.getHeight(), null);
							if(p.type == 0) g2d.setColor(Color.green);
							else g2d.setColor(Color.red);
							g2d.drawString(p.nom, pm.x*32+pm.offsetX, pm.y*32-tempImg.getHeight()+pm.offsetY-10);
						} else {
							g2d.drawString("IA", pm.x*32+14, pm.y*32+18);
						}
					}
				}
				
				g2d.setColor(Color.black);
				for(int i = 0; i < ClientMain.joueur.length; i++) {
					if(ClientMain.joueur[i].nom != null) {
						if(ClientMain.joueur[i].nom.length() > 0  && ClientMain.joueur[i].map == ClientMain.joueur[ClientMain.client.myid].map) {
							//System.out.println(i);
							if(ClientMain.joueur[i].sprite >= 0 && ClientMain.joueur[i].sprite < sprite.size()) {
								tempImg = sprite.get(ClientMain.joueur[i].sprite)[0+ClientMain.joueur[i].anim];
								g2d.drawString(ClientMain.joueur[i].nom, ClientMain.joueur[i].x*32+ClientMain.joueur[i].offsetX, ClientMain.joueur[i].y*32-tempImg.getHeight()+ClientMain.joueur[i].offsetY-10);
							}
						}
					}
				}
				
				for(int y = 0; y < map.get(ClientMain.joueur[ClientMain.client.myid].map).height; y++) {
					for(int x = 0; x < map.get(ClientMain.joueur[ClientMain.client.myid].map).width; x++) {
						tempCase = map.get(ClientMain.joueur[ClientMain.client.myid].map).Case[x][y];
						if(tempCase.frange[0] != 0) g2d.drawImage(tex.get(tempCase.frangeTile[0])[tempCase.frange[0]], x*32, y*32, null);
						if(tempCase.frange[2] != 0 && animTime == 0) g2d.drawImage(tex.get(tempCase.frangeTile[2])[tempCase.frange[2]], x*32, y*32, null);
						if(tempCase.frange[1] != 0) g2d.drawImage(tex.get(tempCase.frangeTile[1])[tempCase.frange[1]], x*32, y*32, null);
						if(tempCase.frange[3] != 0 && animTime == 1) g2d.drawImage(tex.get(tempCase.frangeTile[3])[tempCase.frange[3]], x*32, y*32, null);
					}
				}
				
				// Night
				if(isNight) g2d.drawImage(night, 0, 0, null);
				
				g2d.setColor(Color.red);
				g2d.drawString("Vie: "+ClientMain.joueur[ClientMain.client.myid].vie+"/"+getMaxVie(), 5, 10);
				g2d.setColor(Color.blue);
				g2d.drawString("Magie: "+ClientMain.joueur[ClientMain.client.myid].magie+"/"+getMaxMagie(), 5, 24);
				g2d.setColor(Color.yellow);
				g2d.drawString("XP: "+ClientMain.joueur[ClientMain.client.myid].exp+"/"+ClientMain.joueur[ClientMain.client.myid].nextLevel(), 5, 38);
				
				if(ThreadGameLoop.getTime() - chatTime < 60000) {
					for(int i = 0; i < chatBox.size(); i++) {
						
						g2d.setColor(Color.white);
						g2d.drawString(chatBox.get(i), 6, 601-(chatBox.size()*15)+(i*15));
						if(chatBox.get(i).startsWith("(PNJ)")) {
							g2d.setColor(Color.yellow);
						} else if(chatBox.get(i).startsWith("(INFO)")) {
							g2d.setColor(Color.red);
						} else {
							g2d.setColor(Color.black);
						}
						g2d.drawString(chatBox.get(i), 5, 600-(chatBox.size()*15)+(i*15));
					}
				}
				for(int i = 0; i < msgMap.size(); i++) {
					if(System.currentTimeMillis() - msgMap.get(i).time > 1000) {
						msgMap.remove(i);
					} else {
						g2d.setColor(msgMap.get(i).c);
						g2d.drawString(msgMap.get(i).msg, msgMap.get(i).x*32,msgMap.get(i).y*32-8-((System.currentTimeMillis() - msgMap.get(i).time)/30));
					}
				}
				if(focusChat) {
					g2d.setColor(Color.white);
					g2d.drawString(textChat, 6, 611);
					g2d.setColor(Color.black);
					g2d.drawString(textChat, 5, 610);
				}
				if(inInventaire) {
					g2d.drawImage(inventaire[0], 50, 50, null);
					g2d.setColor(Color.black);
					g2d.drawString(ClientMain.joueur[ClientMain.client.myid].nom, 316, 250);
					g2d.drawString("Niv. "+ClientMain.joueur[ClientMain.client.myid].level, 316, 265);
					g2d.drawString("Argent: "+ClientMain.joueur[ClientMain.client.myid].money, 316, 280);
					
					for(int i = 0; i < ClientMain.joueur[ClientMain.client.myid].inventaire.length; i++) {
						if(ClientMain.joueur[ClientMain.client.myid].inventaire[i].id >= 0) {
							int id = ClientMain.joueur[ClientMain.client.myid].inventaire[i].id;
							DataItem di = item.get(id);
							if(di.type < 17) { // equipement
								if(i == ClientMain.joueur[ClientMain.client.myid].equipement[item.get(id).type])
									g2d.drawImage(inventaire[2], 52 + Math.round(i-Math.round(i/8)*8)*33, 71 + 179 + Math.round(i/8)*33, null);
							}
							g2d.drawImage(itemsTile[di.pic], 52 + Math.round(i-Math.round(i/8)*8)*33, 71 + 179 + Math.round(i/8)*33, null);
							if(di.type == 17) {
								if(ClientMain.joueur[ClientMain.client.myid].inventaire[i].quantite > 1) {
									g2d.drawString(""+ClientMain.joueur[ClientMain.client.myid].inventaire[i].quantite, 52 + Math.round(i-Math.round(i/8)*8)*33, 81 + 179 + Math.round(i/8)*33);
								}
							}
						}
					}
					g2d.drawImage(inventaire[1], 52 + Math.round(selectInventaireID-Math.round(selectInventaireID/8)*8)*33, 71+179 + Math.round(selectInventaireID/8)*33, null);
					
					for(int i = 0; i < ClientMain.joueur[ClientMain.client.myid].equipement.length; i++) {
						int id = ClientMain.joueur[ClientMain.client.myid].equipement[i];
						if(id > -1) {
							if(ClientMain.joueur[ClientMain.client.myid].inventaire[id].id >= 0) {
								DataItem di = item.get(ClientMain.joueur[ClientMain.client.myid].inventaire[id].id);
								if(i == 0) {
									g2d.drawImage(itemsTile[di.pic], 259, 71, null);
								} else if(i == 1) {
									g2d.drawImage(itemsTile[di.pic], 292, 71, null);
								} else if(i == 2) {
									g2d.drawImage(itemsTile[di.pic], 325, 71, null);
								} else if(i == 3) {
									g2d.drawImage(itemsTile[di.pic], 259, 104, null);
								} else if(i == 4) {
									g2d.drawImage(itemsTile[di.pic], 292, 104, null);
								} else if(i == 5) {
									g2d.drawImage(itemsTile[di.pic], 325, 104, null);
								} else if(i == 6) {
									g2d.drawImage(itemsTile[di.pic], 259, 137, null);
								} else if(i == 7) {
									g2d.drawImage(itemsTile[di.pic], 292, 137, null);
								} else if(i == 8) {
									g2d.drawImage(itemsTile[di.pic], 325, 137, null);
								} else if(i == 9) {
									g2d.drawImage(itemsTile[di.pic], 292, 170, null);
								} else if(i == 10) {
									g2d.drawImage(itemsTile[di.pic], 292, 203, null);
								} else if(i == 11) {
									g2d.drawImage(itemsTile[di.pic], 325, 203, null);
								} else if(i == 12) {
									g2d.drawImage(itemsTile[di.pic], 391, 71, null);
								} else if(i == 13) {
									g2d.drawImage(itemsTile[di.pic], 391, 104, null);
								} else if(i == 14) {
									g2d.drawImage(itemsTile[di.pic], 391, 137, null);
								} else if(i == 15) {
									g2d.drawImage(itemsTile[di.pic], 391, 170, null);
								} else if(i == 16) {
									g2d.drawImage(itemsTile[di.pic], 391, 203, null);
								}
							}
						}
					}
					
					int idItem = ClientMain.joueur[ClientMain.client.myid].inventaire[selectInventaireID].id;
					if(idItem >= 0) {
						if(item.get(idItem).type < ClientMain.joueur[ClientMain.client.myid].equipement.length) {
							if(ClientMain.joueur[ClientMain.client.myid].equipement[item.get(idItem).type] == selectInventaireID) {
								g2d.setColor(Color.cyan);
							} else {
								g2d.setColor(Color.black);
							}
						} else {
							g2d.setColor(Color.black);
						}
						DataItem di = item.get(idItem);
						g2d.drawString("Desc. de l'objet: "+di.nom, 54, 86);
						g2d.drawString("____________", 54, 88);
						g2d.setColor(Color.black);
						//g2d.drawString(item.get(idItem).desc, 54, 101);
						if(di.desc.length() > 30) {
							String str[]=di.desc.split(" ");
							String text = "";
							byte ligne = 0;
							for(int i = 0; i < str.length; i++) {
								if((text.length() + str[i].length() >= 36) || (i+1 == str.length)) {
									if(i+1 == str.length) text+= str[i];
									g2d.drawString(text, 54, 103+ligne*15);
									text = str[i] + " ";
									ligne++;
								} else {
									text+= str[i] + " ";
								}
							}
							
						} else {
							g2d.drawString(di.desc, 54, 103);
						}
					}
					
				}
				if(inFiche) {
					g2d.drawImage(fiche, 50, 50, null);
					g2d.setColor(Color.black);
					int force = 0;
					int dex = 0;
					int end = 0;
					int ene = 0;
					for(int i = 0; i < ClientMain.joueur[ClientMain.client.myid].equipement.length; i++) {
						if(ClientMain.joueur[ClientMain.client.myid].equipement[i] != -1) {
							DataItem di = item.get(ClientMain.joueur[ClientMain.client.myid].inventaire[ClientMain.joueur[ClientMain.client.myid].equipement[i]].id);
							force += di.force;
							dex += di.dexterite;
							end += di.endurence;
							ene += di.energie;
						}
					}
					g2d.drawString(""+ClientMain.joueur[ClientMain.client.myid].level, 130, 83);
					g2d.drawString(""+(ClientMain.joueur[ClientMain.client.myid].force + force)+"(+"+force+")", 130, 98);
					g2d.drawString(""+(ClientMain.joueur[ClientMain.client.myid].dexterite + dex)+"(+"+dex+")", 130, 113);
					g2d.drawString(""+(ClientMain.joueur[ClientMain.client.myid].endurence + end)+"(+"+end+")", 130, 128);
					g2d.drawString(""+(ClientMain.joueur[ClientMain.client.myid].energie + ene)+"(+"+ene+")", 130, 143);
					g2d.drawString(""+ClientMain.joueur[ClientMain.client.myid].pts, 130, 171);
					
					if(ClientMain.joueur[ClientMain.client.myid].pts > 0) {
						g2d.drawString("+", 170, 98);
						g2d.drawString("+", 170, 113);
						g2d.drawString("+", 170, 128);
						g2d.drawString("+", 170, 143);
					}
				}
				if(inShop) {
					g2d.drawImage(magasin[0], 50, 50, null);
					g2d.setColor(Color.black);
					g2d.drawString(""+shop.get(selectShopId).name, 52, 65);
					g2d.drawImage(magasin[1], 52 + Math.round(selectShop-Math.round(selectShop/3)*3)*33, 71 + Math.round(selectShop/3)*33, null);
					for(int i = 0; i < 18; i++) {
						if(i+selectShopPage * 18 < shop.get(selectShopId).article.size()) {
							int id = shop.get(selectShopId).article.get(i+selectShopPage * 18);
							DataItem di = item.get(id);
							g2d.drawImage(itemsTile[di.pic], 52 + Math.round(i-Math.round(i/3)*3)*33, 71 + Math.round(i/3)*33, null);
						}
					}
					if(selectShop+selectShopPage * 18 < shop.get(selectShopId).article.size()) {
						g2d.drawString(""+shop.get(selectShopId).valeur.get(selectShop+selectShopPage * 18), 184, 177);
						DataItem di = item.get(shop.get(selectShopId).article.get(selectShop+selectShopPage * 18));
						g2d.drawString(" "+di.nom, 190, 96);
						g2d.drawImage(itemsTile[di.pic], 156, 71, null);
						if(di.desc.length() > 30) {
							String str[]=di.desc.split(" ");
							String text = "";
							byte ligne = 0;
							for(int i = 0; i<str.length; i++) {
								if(text.length() + str[i].length() +1 >= 30) {
									if(ligne == 2){
										text = text.substring(0, text.length()-4);
										text += "...";
									}
									g2d.drawString(text, 158, 130+ligne*15);
									text = str[i];
									ligne++;
									if(ligne == 3) break;
									// TODO
								} else {
									text+= " " + str[i];
								}
							}
							
						} else {
							g2d.drawString(di.desc, 158, 130);
						}
					}
					g2d.drawString("Page:" + selectShopPage+"/"+(int)Math.floor(shop.get(selectShopId).article.size()/18), 76, 280);
				}
				
			} else if(cm.menu == 2) {
				btn_insc.setVisible(false);
				btn_login.setVisible(false);
				textBoxLogin.setVisible(false);
				textBoxMDP.setVisible(false);
				labelLogin.setVisible(false);
				labelMDP.setVisible(false);
				btn_cp.setVisible(true);
				textBoxNom.setVisible(true);
				labelNom.setVisible(true);
				g2d.drawImage(background, 0, 0, null);
				super.paintComponents(g);
			} else {
				btn_insc.setVisible(true);
				btn_login.setVisible(true);
				textBoxLogin.setVisible(true);
				textBoxMDP.setVisible(true);
				labelLogin.setVisible(true);
				labelMDP.setVisible(true);
				btn_cp.setVisible(false);
				textBoxNom.setVisible(false);
				labelNom.setVisible(false);
				g2d.drawImage(background, 0, 0, null);
				super.paintComponents(g);
			}
			fps++;
			if(ThreadGameLoop.getTime() - lastTime > 1000) {
				lastfps = fps;
				fps = 0;
				lastTime = ThreadGameLoop.getTime();
			}
			g2d.setColor(Color.black);
			g2d.drawString(""+lastfps,600, 15);
			g2d.drawString(""+pnjMap.size(),600, 30);
			
		g2d.dispose();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Object Source = arg0.getSource();
		
		if(Source == btn_insc && cm.menu == 0) {
			String packet = "nc"+ClientMain.client.SEP+textBoxLogin.getText()+ClientMain.client.SEP+textBoxMDP.getText()+ClientMain.client.SEP+ClientMain.client.END;
			ClientMain.client.send(packet);
		}
		if(Source == btn_login && cm.menu == 0) {
			String packet = "lc"+ClientMain.client.SEP+textBoxLogin.getText()+ClientMain.client.SEP+textBoxMDP.getText()+ClientMain.client.SEP+ClientMain.client.END;
			ClientMain.client.send(packet);
		}
		if(Source == btn_cp  && cm.menu == 2) {
			String packet = "cp"+ClientMain.client.SEP+textBoxNom.getText()+ClientMain.client.SEP+ClientMain.client.END;
			ClientMain.client.send(packet);
		}
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getButton() == MouseEvent.BUTTON1) {
			if(inInventaire) {
				if(arg0.getX() >= 52 && arg0.getX() <= 316) {
					if(arg0.getY() >= 71+179 && arg0.getY() <= 203+179) {
						int x = (arg0.getX()-52) / 33;
						int y = (arg0.getY()-71-179) / 33;
						if(selectInventaireID == y * 8 + x) {
							ClientMain.client.send("useitem"+ClientMain.client.SEP+selectInventaireID+ClientMain.client.SEP+ClientMain.client.END);
						} else {
							selectInventaireID = y * 8 + x;
						}
					}
				}
				
				if(arg0.getX() >= 406 && arg0.getX() <= 424) {
					if(arg0.getY() >= 51 && arg0.getY() <= 69) {
						inInventaire = false;
					}
				}
				if(arg0.getY() >= 365 && arg0.getY() <= 381) {
					if(arg0.getX() >= 318 && arg0.getX() <= 374) {
						// Use
						ClientMain.client.send("useitem"+ClientMain.client.SEP+selectInventaireID+ClientMain.client.SEP+ClientMain.client.END);
					}
					if(arg0.getX() >= 381 && arg0.getX() <= 443) {
						// Jeter
						ClientMain.client.send("jeteritem"+ClientMain.client.SEP+selectInventaireID+ClientMain.client.SEP+ClientMain.client.END);
					}
				}
			}
			if(inFiche) {
				if(arg0.getX() >= 167 && arg0.getX() <= 185) {
					if(arg0.getY() >= 51 && arg0.getY() <= 69) {
						inFiche = false;
					}
				}
				if(arg0.getX() >= 170 && arg0.getX() <= 185) {
					if(arg0.getY() >= 84 && arg0.getY() <= 158) {
						int y = (arg0.getY()-84) / 15;
						if(y >= 0 && y <= 3 && ClientMain.joueur[ClientMain.client.myid].pts > 0) {
							ClientMain.client.send("usepts"+ClientMain.client.SEP+y+ClientMain.client.SEP+ClientMain.client.END);
						}
					}
				}
			}
			if(inShop) {
				if(arg0.getX() >= 52 && arg0.getX() <= 150) {
					if(arg0.getY() >= 71 && arg0.getY() <= 268) {
						int x = (arg0.getX()-52) / 33;
						int y = (arg0.getY()-71) / 33;
						selectShop = y * 3 + x;
					}
				}
				if(arg0.getX() >= 274 && arg0.getX() <= 292) {
					if(arg0.getY() >= 51 && arg0.getY() <= 69) {
						inShop = false;
					}
				}
				if(arg0.getY() >= 270 && arg0.getY() <= 273) {
					if(arg0.getX() >= 53 && arg0.getX() <= 66) {
						selectShopPage--;
						if(selectShopPage < 0) {
							selectShopPage = (int)Math.floor(shop.get(selectShopId).article.size()/18);
						}
					}
					if(arg0.getX() >= 137 && arg0.getX() <= 150) {
						selectShopPage++;
						if(selectShopPage > (int)Math.floor(shop.get(selectShopId).article.size()/18)) {
							selectShopPage = 0;
						}
					}
				}
				if(arg0.getX() >= 171 && arg0.getX() <= 278) {
					if(arg0.getY() >= 215 && arg0.getY() <= 234) {
						infoItem.view(item.get(shop.get(selectShopId).article.get(selectShop+selectShopPage * 18)));
					}
					if(arg0.getY() >= 238 && arg0.getY() <= 257) {
						int invID = searchIdtoInv(shop.get(selectShopId).article.get(selectShop+selectShopPage * 18));
						if(invID >= 0) {
							ClientMain.client.send("saleshop"+ClientMain.client.SEP+selectShopId+ClientMain.client.SEP+invID+ClientMain.client.SEP+ClientMain.client.END);
						} else {
							JOptionPane.showMessageDialog(null, "Vous ne possedez pas cette objet pour le vendre...");
						}
					}
					if(arg0.getY() >= 261 && arg0.getY() <= 280) {
						if(ClientMain.joueur[ClientMain.client.myid].money >= shop.get(selectShopId).valeur.get(selectShop+selectShopPage * 18)) {
							ClientMain.client.send("buyshop"+ClientMain.client.SEP+selectShopId+ClientMain.client.SEP+(selectShop+selectShopPage * 18)+ClientMain.client.SEP+ClientMain.client.END);
						} else {
							JOptionPane.showMessageDialog(null, "Vous n'avez pas assez d'argent !");
						}
						
					}
				}
			}
		}
		if(arg0.getButton() == MouseEvent.BUTTON3){
			if(inInventaire) {
				if(arg0.getX() >= 52 && arg0.getX() <= 316) {
					if(arg0.getY() >= 71+179 && arg0.getY() <= 203+179) {
						int x = (arg0.getX()-52) / 33;
						int y = (arg0.getY()-71-179) / 33;
						selectInventaireID = y * 8 + x;
						if(ClientMain.joueur[ClientMain.client.myid].inventaire[selectInventaireID].id >= 0) {
							infoItem.view(item.get(ClientMain.joueur[ClientMain.client.myid].inventaire[selectInventaireID].id));
						}
					}
				}
			}
		}
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(!ClientMain.client.isMoved && cm.menu == 5 && !focusChat) {
			if(arg0.getKeyCode() == KeyEvent.VK_UP) {
				ClientMain.client.send("move"+ClientMain.client.SEP+0+ClientMain.client.SEP+ClientMain.client.END);
				ClientMain.client.isMoved = true;
			}
			if(arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
				ClientMain.client.send("move"+ClientMain.client.SEP+1+ClientMain.client.SEP+ClientMain.client.END);
				ClientMain.client.isMoved = true;
			}
			if(arg0.getKeyCode() == KeyEvent.VK_DOWN) {
				ClientMain.client.send("move"+ClientMain.client.SEP+2+ClientMain.client.SEP+ClientMain.client.END);
				ClientMain.client.isMoved = true;
			}
			if(arg0.getKeyCode() == KeyEvent.VK_LEFT) {
				ClientMain.client.send("move"+ClientMain.client.SEP+3+ClientMain.client.SEP+ClientMain.client.END);
				ClientMain.client.isMoved = true;
			}
			if(arg0.getKeyCode() == KeyEvent.VK_S && !ClientMain.client.isAttack) {
				ClientMain.client.send("attack"+ClientMain.client.SEP+ClientMain.client.END);
				ClientMain.client.isAttack = true;
			}
			if(arg0.getKeyCode() == KeyEvent.VK_D) {
				ClientMain.client.send("action"+ClientMain.client.SEP+ClientMain.client.END);
			}
		} else {
			if(focusChat) {
				if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
					textChat = "";
				}
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					if(textChat.equalsIgnoreCase("") == false) {
						String text = textChat;
						text = text.replace(ClientMain.client.SEP," ");
						text = text.replace(ClientMain.client.END," ");
						ClientMain.client.send("say"+ClientMain.client.SEP+text+ClientMain.client.SEP+ClientMain.client.END);
						textChat = "";
					}
				} else if(arg0.getKeyCode() == 8) {
					textChat = textChat.substring(0, textChat.length()-1);
				} else {
					//System.out.println(arg0.getKeyCode());
					textChat += arg0.getKeyChar();
				}
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(!ClientMain.client.isMoved && cm.menu == 5 && !focusChat) {
			if(arg0.getKeyCode() == KeyEvent.VK_I) {
				if(inInventaire) {
					inInventaire = false;
				} else {
					ClientMain.client.send("inv"+ClientMain.client.SEP+ClientMain.client.END);
				}
			}
			if(arg0.getKeyCode() == KeyEvent.VK_C) {
				inFiche = !inFiche;
			}
			
			if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
				focusChat = true;
				repaint();
			}
		} else {
			if(arg0.getKeyCode() == KeyEvent.VK_ENTER && focusChat) {
				focusChat = false;
				repaint();
			}
			if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
				focusChat = false;
				repaint();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public int searchIdtoInv(int id) {
		for(int i = 0; i < ClientMain.joueur[ClientMain.client.myid].inventaire.length; i++) {
			if(ClientMain.joueur[ClientMain.client.myid].inventaire[i].id == id && isNotUseItem(i)) {
				return i;
			}
		}
		return -1;
	}
	
	public int getMaxVie() {
		int add = 0;
		for(int i = 0; i < ClientMain.joueur[ClientMain.client.myid].equipement.length; i++) {
			if(ClientMain.joueur[ClientMain.client.myid].equipement[i] != -1) {
				DataItem di = item.get(ClientMain.joueur[ClientMain.client.myid].inventaire[ClientMain.joueur[ClientMain.client.myid].equipement[i]].id);
				add += di.vie;
			}
		}
		return (ClientMain.joueur[ClientMain.client.myid].endurence*10 + ClientMain.joueur[ClientMain.client.myid].level * 5 + add);
	}
	
	public int getMaxMagie() {
		int add = 0;
		for(int i = 0; i < ClientMain.joueur[ClientMain.client.myid].equipement.length; i++) {
			if(ClientMain.joueur[ClientMain.client.myid].equipement[i] != -1) {
				DataItem di = item.get(ClientMain.joueur[ClientMain.client.myid].inventaire[ClientMain.joueur[ClientMain.client.myid].equipement[i]].id);
				add += di.magie;
			}
		}
		return (ClientMain.joueur[ClientMain.client.myid].energie*4 + ClientMain.joueur[ClientMain.client.myid].level * 2 + add);
	}
	
	public boolean isNotUseItem(int item) {
		DataPersonnage dp = ClientMain.joueur[ClientMain.client.myid];
		boolean used = true;
		for(int i = 0; i < dp.equipement.length; i++) {
			if(dp.equipement[i] == item) {
				used = false;
			}
		}
		return used;
	}
	
	static public void createNight(){
		// nettoie la nuit '--
		nightClear();
		
		// Seach light map
		DataCase tempCase;
		for(int y = 0; y < map.get(ClientMain.joueur[ClientMain.client.myid].map).height; y++) {
			for(int x = 0; x < map.get(ClientMain.joueur[ClientMain.client.myid].map).width; x++) {
				tempCase = map.get(ClientMain.joueur[ClientMain.client.myid].map).Case[x][y];
				if(tempCase.light) {
					addLight(x, y);
				}
			}
		}
	}
	
	static public void addLight(int x, int y) {
		int cx = x*32+16;
		int cy = y*32+16;
		for(int i = 0; i < 360; i++) {
			for(int j = 0; j < 64; j++) {
				for(int k = 0; k < 64; k++) {
					night.setRGB((int) (cx+Math.cos(i*(Math.PI/180))*j),(int) (cy+Math.sin(i*(Math.PI/180))*k), (new Color(0, 0, 0, 0)).getRGB());
				}
			}
		}
	}
	
	static public void nightClear(){
		night = new BufferedImage(map.get(ClientMain.joueur[ClientMain.client.myid].map).width*32, map.get(ClientMain.joueur[ClientMain.client.myid].map).height * 32,BufferedImage.TYPE_INT_ARGB);
		Graphics2D gr = night.createGraphics();
			AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.80f);				
			gr.setComposite(alphaComposite);
			gr.setColor(Color.black);
			gr.fillRect(0, 0, map.get(ClientMain.joueur[ClientMain.client.myid].map).width*32, map.get(ClientMain.joueur[ClientMain.client.myid].map).height * 32);
		gr.dispose();
	}
}
