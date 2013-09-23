package fr.mimus.jorpg.editeur;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.mimus.jorpg.client.ClientMain;
import fr.mimus.jorpg.commun.*;

public class EditeurBoard extends JPanel implements ActionListener,MouseListener,MouseMotionListener, MouseWheelListener, KeyListener {
	private static final long serialVersionUID = 1L;	
	// Composent
	Dimension size;
	Insets insets;
	JComboBox<?> tilesList;
	
	// Image
	ImageIcon ii;
	ArrayList<BufferedImage[]> tex;
	ArrayList<Image> texture;
	ArrayList<ImageIcon> iitexture;
	static ImageIcon iiitem;
	static ArrayList<BufferedImage[]> sprite;
	static ArrayList<BufferedImage[]> paperdolls;
	BufferedImage mapping;
	
	// Nuit
	BufferedImage night;
	boolean isNight = false;
	
	// grille
	BufferedImage grille;
	boolean isGrille = true;
	
	// Variable Editeur
	int SelectX = 0;
	int SelectY = 0;
	int SelectX2 = 0;
	int SelectY2 = 0;
	int SelectMap = 0;
	int Selectcouche = 0;
	int SelectTiles = 0;
	int defx = 0, defy = 0;
	int addx = 0, addy = 0;
	int outil = 0;
	
	// Souris
	boolean boutonLeft = false;
	boolean boutonRight = false;
	Point coor = new Point();
	
	// clavier
	boolean keyShift = false;
	
	// Variable jeu
	static ArrayList<DataMap> map;
	static ArrayList<DataItem> item;
	static BufferedImage[] itemsTile;
	static ArrayList<DataPNJ> pnj;
	static ArrayList<DataShop> shop;
	static ArrayList<DataQuest> quete;
	static DataConfigServeur dcs = new DataConfigServeur();
	
	public EditeurBoard() {
		this.setLayout(null);
		insets = this.getInsets();
		
		String [] listefichiers;
		// Chargement Image
		ii = new ImageIcon("Data/Texture/Tiles0.png");
		//texture = ii.getImage();
		texture = new ArrayList<Image>();
		tex = new ArrayList<BufferedImage[]>();
		iitexture = new ArrayList<ImageIcon>();
		//TextureSpliter.getAllTexture(ii);
		listefichiers = (new File("Data/Texture/")).list(); 
		for(int i=0;i<listefichiers.length;i++){
			if((new File("Data/Texture/Tiles"+i+".png")).exists()) {
				ii = new ImageIcon("Data/Texture/Tiles"+i+".png");
				texture.add(ii.getImage());
				tex.add(TextureSpliter.getAllTexture(ii));
				iitexture.add(ii);
				System.out.println("Texture loaded: Data/Texture/Tiles"+i);
			}
		} 
		Object[] entityType = new Object[texture.size()];
		for(int i=0;i<texture.size();i++){
			entityType[i] = "Tile "+i;
		}
		tilesList = new JComboBox<Object>(entityType);
		tilesList.addActionListener(this);
		add(tilesList);
		size = tilesList.getPreferredSize();
		tilesList.setBounds(640 + insets.left, 0 + insets.top,
		             200, size.height);
		
		ii = new ImageIcon("Data/items.png");
		itemsTile = TextureSpliter.getAllTexture(ii);
		iiitem = ii;
		
		sprite = new ArrayList<BufferedImage[]>();
		listefichiers = (new File("Data/Sprites/")).list(); 
		for(int i=0;i<listefichiers.length;i++){
			if((new File("Data/Sprites/"+i+".png")).exists()) {
				ii = new ImageIcon("Data/Sprites/"+i+".png");
				sprite.add(TextureSpliter.getAllTextureWith(ii, 4, 4));
			}
		} 
		
		paperdolls = new ArrayList<BufferedImage[]>();
		listefichiers = (new File("Data/Paperdolls/")).list(); 
		for(int i=0;i<listefichiers.length;i++){
			if((new File("Data/Paperdolls/Paperdolls"+i+".png")).exists()) {
				ii = new ImageIcon("Data/Paperdolls/Paperdolls"+i+".png");
				paperdolls.add(TextureSpliter.getAllTextureWith(ii, 4, 4));
			}
		} 
		
		// Initialisation des maps
		map = new ArrayList<DataMap>();
		item = new ArrayList<DataItem>();
		pnj = new ArrayList<DataPNJ>();
		shop = new ArrayList<DataShop>();
		quete = new ArrayList<DataQuest>();
		
		listefichiers = (new File("Data/Maps/")).list(); 
		for(int i=0;i<listefichiers.length;i++){
			if((new File("Data/Maps/map"+i+".ns")).exists()) {
				map.add(DataLoader.load("map"+i));
			} else {
				map.add(new DataMap());
			}
		} 
		listefichiers = (new File("Data/Objets/")).list(); 
		for(int i=0;i<listefichiers.length;i++){
			if((new File("Data/Objets/objet"+i+".ns")).exists()) {
				item.add(DataLoader.loadItem("objet"+i));
			} else {
				item.add(new DataItem());
			}
		} 
		listefichiers = (new File("Data/PNJs/")).list(); 
		for(int i=0;i<listefichiers.length;i++){
			if((new File("Data/PNJs/pnj"+i+".ns")).exists()) {
				pnj.add(DataLoader.loadPNJ("pnj"+i));
			} else {
				pnj.add(new DataPNJ());
			}
		} 
		listefichiers = (new File("Data/Magasins/")).list(); 
		for(int i=0;i<listefichiers.length;i++){
			if((new File("Data/Magasins/Magasin"+i+".ns")).exists()) {
				shop.add(DataLoader.loadShop("Magasin"+i));
			} else {
				shop.add(new DataShop());
			}
		} 
		listefichiers = (new File("Data/Quetes/")).list(); 
		for(int i=0;i<listefichiers.length;i++){
			if((new File("Data/Quetes/Quete"+i+".ns")).exists()) {
				quete.add(DataLoader.loadQuete("Quete"+i));
			} else {
				quete.add(new DataQuest());
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
			try {
				//int maph = Integer.parseInt(JOptionPane.showInputDialog("Hauteur: ", "20"));
				//int mapw = Integer.parseInt(JOptionPane.showInputDialog("Largeur: ", "20"));
				//map.add(new DataMap(maph, mapw));
				map.add(new DataMap());
			} catch(NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "Suite a une erreur la map a été initialiser par defaud.");
				map.add(new DataMap());
			}
			SelectMap = 0;
			DataLoader.save(SelectMap, map.get(SelectMap));
		}
		if((new File("Data/Serveur.wns")).exists()) {
			DataClient dc = (DataClient) DataLoader.decompileClient("Serveur");
			dcs = (DataConfigServeur) dc.data;
		}
		
		// Action
		this.setFocusable(true);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		this.addKeyListener(this);
		
		// Create grille
		createGrille();
		
		// créate night
		createNight();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		// Selection
		g2d.setColor(Color.white);  
		g2d.fillRect(640, 0, 200, 660);
		g2d.drawImage(texture.get(SelectTiles), 640-addx, (int) (tilesList.getPreferredSize().getHeight()-addy), null);
		g2d.setColor(Color.red);  
		g2d.drawRect(640+SelectX*32-addx, (int) (SelectY*32-addy+tilesList.getPreferredSize().getHeight()), (SelectX2 - SelectX)*32+32, (SelectY2 - SelectY)*32+32);
		// Map
		g2d.setColor(Color.BLACK);  
		g2d.fillRect(0, 0, 640, 660);
		for(int y = 0; y < map.get(SelectMap).height; y++) {
			for(int x = 0; x < map.get(SelectMap).width; x++) {
				DataCase tempCase = map.get(SelectMap).Case[x][y];
				g2d.drawImage(tex.get(tempCase.solTile[0])[tempCase.sol[0]], x*32, y*32, null);
				if(tempCase.sol[1] != 0) g2d.drawImage(tex.get(tempCase.solTile[1])[tempCase.sol[1]], x*32, y*32, null);
				if(tempCase.masque[0] != 0) g2d.drawImage(tex.get(tempCase.masqueTile[0])[tempCase.masque[0]], x*32, y*32, null);
				if(tempCase.masque[2] != 0 && EditeurMain.animTime == 0) g2d.drawImage(tex.get(tempCase.masqueTile[2])[tempCase.masque[2]], x*32, y*32, null);
				if(tempCase.masque[1] != 0) g2d.drawImage(tex.get(tempCase.masqueTile[1])[tempCase.masque[1]], x*32, y*32, null);
				if(tempCase.masque[3] != 0 && EditeurMain.animTime == 1) g2d.drawImage(tex.get(tempCase.masqueTile[3])[tempCase.masque[3]], x*32, y*32, null);
				if(tempCase.frange[0] != 0) g2d.drawImage(tex.get(tempCase.frangeTile[0])[tempCase.frange[0]], x*32, y*32, null);
				if(tempCase.frange[2] != 0 && EditeurMain.animTime == 0) g2d.drawImage(tex.get(tempCase.frangeTile[2])[tempCase.frange[2]], x*32, y*32, null);
				if(tempCase.frange[1] != 0) g2d.drawImage(tex.get(tempCase.frangeTile[1])[tempCase.frange[1]], x*32, y*32, null);
				if(tempCase.frange[3] != 0 && EditeurMain.animTime == 1) g2d.drawImage(tex.get(tempCase.frangeTile[3])[tempCase.frange[3]], x*32, y*32, null);
				g2d.setColor(Color.black);
				if(tempCase.attribut == 1) g2d.drawString("B", x*32+15, y*32+19);
				if(tempCase.attribut == 2) g2d.drawString("T", x*32+15, y*32+19);
				if(tempCase.attribut == 3) g2d.drawString("P"+tempCase.data[0], x*32+8, y*32+19);
				if(tempCase.attribut == 4) g2d.drawString("V"+tempCase.data[0], x*32+8, y*32+19);
				g2d.setColor(Color.red);
				if(tempCase.attribut == 1) g2d.drawString("B", x*32+14, y*32+18);
				g2d.setColor(Color.blue);
				if(tempCase.attribut == 2) g2d.drawString("T", x*32+14, y*32+18);
				g2d.setColor(Color.red);
				if(tempCase.attribut == 3) g2d.drawString("P"+tempCase.data[0], x*32+7, y*32+18);
				g2d.setColor(Color.green);
				if(tempCase.attribut == 4) g2d.drawString("V"+tempCase.data[0], x*32+7, y*32+18);
				g2d.setColor(Color.yellow);
				if(tempCase.light) g2d.drawString("L", x*32+17, y*32+26);
			}
		}
		
		// grille
		if(isGrille) g2d.drawImage(grille, 0, 0, null);
		// Night
		if(isNight) g2d.drawImage(night, 0, 0, null);
		
		if((SelectX != SelectX2 || SelectY != SelectY2) && Selectcouche < 10) {
			g2d.drawImage(mapping, coor.x*32, coor.y*32, null);
		}
		g2d.setColor(Color.red);
		g2d.drawString((map.get(SelectMap).nom != null)?map.get(SelectMap).nom:"Pas de nom", 5, 15);
		switch(Selectcouche) {
		case 0:
			g2d.drawString("Sol", 5, 30);
			break;
		case 1:
			g2d.drawString("Sol Animation", 5, 30);
			break;
		case 2:
			g2d.drawString("Masque 1", 5, 30);
			break;
		case 3:
			g2d.drawString("Masque 1 Animation", 5, 30);
			break;
		case 4:
			g2d.drawString("Masque 2", 5, 30);
			break;
		case 5:
			g2d.drawString("Masque 2 Animation", 5, 30);
			break;
		case 6:
			g2d.drawString("Frange 1", 5, 30);
			break;
		case 7:
			g2d.drawString("Frange 1 Animation", 5, 30);
			break;
		case 8:
			g2d.drawString("Frange 2", 5, 30);
			break;
		case 9:
			g2d.drawString("Frange 2 Animation", 5, 30);
			break;
		case 100:
			g2d.drawString("Aucune Attribut", 5, 30);
			break;
		case 101:
			g2d.drawString("Attribut Bloquer", 5, 30);
			break;
		case 102:
			g2d.drawString("Attribut Téléportation", 5, 30);
			break;
		case 103:
			g2d.drawString("Attribut Spawn PNJ", 5, 30);
			break;
		case 104:
			g2d.drawString("Attribut Vendeur", 5, 30);
			break;
		case 1000:
			g2d.drawString("Lumière on/off", 5, 30);
			break;
		}
		g2d.drawString(coor.x + "," + coor.y, 5, 45);
		// fermeture dla matrice
		super.paintComponents(g);
		g2d.dispose();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == tilesList) {
			SelectTiles = tilesList.getSelectedIndex();
			System.out.println(SelectTiles);
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
		if(arg0.getButton() == MouseEvent.BUTTON1) {
			boutonLeft = true;
		}
		if(arg0.getButton() == MouseEvent.BUTTON3) {
			boutonRight = false;
			if(arg0.getX() >= 640) {
				boutonRight = true;
				defx = arg0.getX();
				defy = arg0.getY();
			}
			repaint();
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		boutonLeft = false;
		boutonRight = false;
		if(arg0.getButton() == MouseEvent.BUTTON2 || (arg0.getButton() == MouseEvent.BUTTON1 && keyShift)) {
			if(arg0.getX() >= 640) {
				SelectX2 = (arg0.getX()-640+addx)/32;
				SelectY2 = (int) ((arg0.getY()+addy-tilesList.getPreferredSize().getHeight())/32);
				if(SelectX2 < SelectX) {
					SelectX2 = SelectX;
					SelectX = (arg0.getX()-640+addx)/32;
				}
				if(SelectY2 < SelectY) {
					SelectY2 = SelectY;
					SelectY = (int) ((arg0.getY()+addy-tilesList.getPreferredSize().getHeight())/32);;
				}
				mapping = new BufferedImage((SelectX2-SelectX+1)*32, (SelectY2-SelectY+1)*32,BufferedImage.TYPE_INT_ARGB);
				Graphics2D gr = mapping.createGraphics();
					AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f);				
					gr.setComposite(alphaComposite);
					int sizeX = iitexture.get(SelectTiles).getIconWidth()/32;
					for(int ty = 0; ty < (SelectY2-SelectY+1); ty++) {
						for(int tx = 0; tx < (SelectX2-SelectX+1); tx++) {
							gr.drawImage(tex.get(SelectTiles)[(SelectY+ty) * sizeX + SelectX+tx], tx*32, ty*32, null);
						}
					}
				gr.dispose();
				repaint();
			}
		} else if(arg0.getButton() == MouseEvent.BUTTON1) {
			if(arg0.getX() >= 640) {
				SelectX = (arg0.getX()-640+addx)/32;
				SelectY = (int) ((arg0.getY()+addy-tilesList.getPreferredSize().getHeight())/32);
				SelectX2 = SelectX;
				SelectY2 = SelectY;
				//System.out.println(SelectX +","+SelectY);
				repaint();
			} else {
				if(arg0.getX() >= 0 && arg0.getX() < 640 && arg0.getY() >= 0 && arg0.getY() < 640) {
					int x = arg0.getX()/32;
					int y = arg0.getY()/32;
					clickMap(x, y, false);
					repaint();
				}
			}
		}
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(boutonLeft) {
			if(arg0.getY() >= 0 && arg0.getY() <= 840)
				if(arg0.getX() >= 0 && arg0.getX() < 640) {
					int x = arg0.getX()/32;
					int y = arg0.getY()/32;
					clickMap(x, y, true);
					repaint();
				}
		}
		if(boutonRight) {
			if(arg0.getX() >= 640) {
				addx += defx-arg0.getX();
				if(addx < 0) addx = 0;
				defx = arg0.getX();
				addy += defy-arg0.getY();
				if(addy < 0) addy = 0;
				defy = arg0.getY();
				repaint();
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		coor.x = arg0.getX()/32;
		coor.y = arg0.getY()/32;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getWheelRotation() < 0) {
			Selectcouche--;
			if(Selectcouche < 0) Selectcouche = 0;
			if(Selectcouche == 1) Selectcouche = 0;
			if(Selectcouche == 99) Selectcouche = 9;
		}
		if(arg0.getWheelRotation() > 0) {
			Selectcouche++;
			if(Selectcouche == 1) Selectcouche = 2;
			if(Selectcouche == 10) Selectcouche = 100;
			if(Selectcouche > 104) Selectcouche = 1000;
			if(Selectcouche > 1000) Selectcouche = 1000;
		}
		
		
	}
	public void clickMap(int x, int y, boolean move) {
		int sizeX = iitexture.get(SelectTiles).getIconWidth()/32;
		switch(Selectcouche) {
		case 0:
			if(SelectY != SelectY2 || SelectX != SelectX2) {
				for(int ty = 0; ty < (SelectY2-SelectY+1); ty++) {
					for(int tx = 0; tx < (SelectX2-SelectX+1); tx++) {
						if(x+tx < 20 && y+ty < 20) {
							map.get(SelectMap).Case[x+tx][y+ty].sol[0] = (SelectY+ty) * sizeX + SelectX+tx;
							map.get(SelectMap).Case[x+tx][y+ty].solTile[0] = SelectTiles;
						}
					}
				}
			} else if(outil == 0) {
				map.get(SelectMap).Case[x][y].sol[0] = SelectY * sizeX + SelectX;
				map.get(SelectMap).Case[x][y].solTile[0] = SelectTiles;
			} else {
				potPeinture(map.get(SelectMap).Case[x][y].sol[0], x, y);
			}
			break;
		case 1:
			if(SelectY != SelectY2 || SelectX != SelectX2) {
				for(int ty = 0; ty < (SelectY2-SelectY+1); ty++) {
					for(int tx = 0; tx < (SelectX2-SelectX+1); tx++) {
						if(x+tx < 20 && y+ty < 20) {
							map.get(SelectMap).Case[x+tx][y+ty].sol[1] = (SelectY+ty) * sizeX + SelectX+tx;
							map.get(SelectMap).Case[x+tx][y+ty].solTile[1] = SelectTiles;
						}
					}
				}
			} else if(outil == 0) {
				map.get(SelectMap).Case[x][y].sol[1] = SelectY * sizeX + SelectX;
				map.get(SelectMap).Case[x][y].solTile[1] = SelectTiles;
			} else {
				potPeinture(map.get(SelectMap).Case[x][y].sol[1], x, y);
			}
			break;
		case 2:
			if(SelectY != SelectY2 || SelectX != SelectX2) {
				for(int ty = 0; ty < (SelectY2-SelectY+1); ty++) {
					for(int tx = 0; tx < (SelectX2-SelectX+1); tx++) {
						if(x+tx < 20 && y+ty < 20) {
							map.get(SelectMap).Case[x+tx][y+ty].masque[0] = (SelectY+ty) * sizeX + SelectX+tx;
							map.get(SelectMap).Case[x+tx][y+ty].masqueTile[0] = SelectTiles;
						}
					}
				}
			} else if(outil == 0) {
				map.get(SelectMap).Case[x][y].masque[0] = SelectY * sizeX + SelectX;
				map.get(SelectMap).Case[x][y].masqueTile[0] = SelectTiles;
			} else {
				potPeinture(map.get(SelectMap).Case[x][y].masque[0], x, y);
			}
			break;
		case 3:
			if(SelectY != SelectY2 || SelectX != SelectX2) {
				for(int ty = 0; ty < (SelectY2-SelectY+1); ty++) {
					for(int tx = 0; tx < (SelectX2-SelectX+1); tx++) {
						if(x+tx < 20 && y+ty < 20) {
							map.get(SelectMap).Case[x+tx][y+ty].masque[2] = (SelectY+ty) * sizeX + SelectX+tx;
							map.get(SelectMap).Case[x+tx][y+ty].masqueTile[2] = SelectTiles;
						}
					}
				}
			} else if(outil == 0) {
				map.get(SelectMap).Case[x][y].masque[2] = SelectY * sizeX + SelectX;
				map.get(SelectMap).Case[x][y].masqueTile[2] = SelectTiles;
			} else {
				potPeinture(map.get(SelectMap).Case[x][y].masque[2], x, y);
			}
			break;
		case 4:
			if(SelectY != SelectY2 || SelectX != SelectX2) {
				for(int ty = 0; ty < (SelectY2-SelectY+1); ty++) {
					for(int tx = 0; tx < (SelectX2-SelectX+1); tx++) {
						if(x+tx < 20 && y+ty < 20) {
							map.get(SelectMap).Case[x+tx][y+ty].masque[1] = (SelectY+ty) * sizeX + SelectX+tx;
							map.get(SelectMap).Case[x+tx][y+ty].masqueTile[1] = SelectTiles;
						}
					}
				}
			} else if(outil == 0) {
				map.get(SelectMap).Case[x][y].masque[1] = SelectY * sizeX + SelectX;
				map.get(SelectMap).Case[x][y].masqueTile[1] = SelectTiles;
			} else {
				potPeinture(map.get(SelectMap).Case[x][y].masque[1], x, y);
			}
			break;
		case 5:
			if(SelectY != SelectY2 || SelectX != SelectX2) {
				for(int ty = 0; ty < (SelectY2-SelectY+1); ty++) {
					for(int tx = 0; tx < (SelectX2-SelectX+1); tx++) {
						if(x+tx < 20 && y+ty < 20) {
							map.get(SelectMap).Case[x+tx][y+ty].masque[3] = (SelectY+ty) * sizeX + SelectX+tx;
							map.get(SelectMap).Case[x+tx][y+ty].masqueTile[3] = SelectTiles;
						}
					}
				}
			} else if(outil == 0) {
				map.get(SelectMap).Case[x][y].masque[3] = SelectY * sizeX + SelectX;
				map.get(SelectMap).Case[x][y].masqueTile[3] = SelectTiles;
			} else {
				potPeinture(map.get(SelectMap).Case[x][y].masque[3], x, y);
			}
			break;
		case 6:
			if(SelectY != SelectY2 || SelectX != SelectX2) {
				for(int ty = 0; ty < (SelectY2-SelectY+1); ty++) {
					for(int tx = 0; tx < (SelectX2-SelectX+1); tx++) {
						if(x+tx < 20 && y+ty < 20) {
							map.get(SelectMap).Case[x+tx][y+ty].frange[0] = (SelectY+ty) * sizeX + SelectX+tx;
							map.get(SelectMap).Case[x+tx][y+ty].frangeTile[0] = SelectTiles;
						}
					}
				}
			} else if(outil == 0) {
				map.get(SelectMap).Case[x][y].frange[0] = SelectY * sizeX + SelectX;
				map.get(SelectMap).Case[x][y].frangeTile[0] = SelectTiles;
			} else {
				potPeinture(map.get(SelectMap).Case[x][y].frange[0], x, y);
			}
			break;
		case 7:
			if(SelectY != SelectY2 || SelectX != SelectX2) {
				for(int ty = 0; ty < (SelectY2-SelectY+1); ty++) {
					for(int tx = 0; tx < (SelectX2-SelectX+1); tx++) {
						if(x+tx < 20 && y+ty < 20) {
							map.get(SelectMap).Case[x+tx][y+ty].frange[2] = (SelectY+ty) * sizeX + SelectX+tx;
							map.get(SelectMap).Case[x+tx][y+ty].frangeTile[2] = SelectTiles;
						}
					}
				}
			} else if(outil == 0) {
				map.get(SelectMap).Case[x][y].frange[2] = SelectY * sizeX + SelectX;
				map.get(SelectMap).Case[x][y].frangeTile[2] = SelectTiles;
			} else {
				potPeinture(map.get(SelectMap).Case[x][y].frange[2], x, y);
			}
			break;
		case 8:
			if(SelectY != SelectY2 || SelectX != SelectX2) {
				for(int ty = 0; ty < (SelectY2-SelectY+1); ty++) {
					for(int tx = 0; tx < (SelectX2-SelectX+1); tx++) {
						if(x+tx < 20 && y+ty < 20) {
							map.get(SelectMap).Case[x+tx][y+ty].frange[1] = (SelectY+ty) * sizeX + SelectX+tx;
							map.get(SelectMap).Case[x+tx][y+ty].frangeTile[1] = SelectTiles;
						}
					}
				}
			} else if(outil == 0) {
				map.get(SelectMap).Case[x][y].frange[1] = SelectY * sizeX + SelectX;
				map.get(SelectMap).Case[x][y].frangeTile[1] = SelectTiles;
			} else {
				potPeinture(map.get(SelectMap).Case[x][y].frange[1], x, y);
			}
			break;
		case 9:
			if(SelectY != SelectY2 || SelectX != SelectX2) {
				for(int ty = 0; ty < (SelectY2-SelectY+1); ty++) {
					for(int tx = 0; tx < (SelectX2-SelectX+1); tx++) {
						if(x+tx < 20 && y+ty < 20) {
							map.get(SelectMap).Case[x+tx][y+ty].frange[3] = (SelectY+ty) * sizeX + SelectX+tx;
							map.get(SelectMap).Case[x+tx][y+ty].frangeTile[3] = SelectTiles;
						}
					}
				}
			} else if(outil == 0) {
				map.get(SelectMap).Case[x][y].frange[3] = SelectY * sizeX + SelectX;
				map.get(SelectMap).Case[x][y].frangeTile[3] = SelectTiles;
			} else {
				potPeinture(map.get(SelectMap).Case[x][y].frange[3], x, y);
			}
			break;
		case 100:
			map.get(SelectMap).Case[x][y].attribut = 0;
			break;
		case 101:
			map.get(SelectMap).Case[x][y].attribut = 1;
			break;
		case 102:
			if(!move) {
				map.get(SelectMap).Case[x][y].attribut = 2;
				String[] tempList = new String[EditeurBoard.map.size()];
				for(int i = 0; i < EditeurBoard.map.size(); i++) {
					tempList[i] = i + " " +EditeurBoard.map.get(i).nom;
				}
				String name = (String) JOptionPane.showInputDialog(null,"Choississez une Map",
						"Choississez une Map",JOptionPane.QUESTION_MESSAGE, null, tempList, tempList[0]);
				if(name == null) name = tempList[0];
				int mapnum = EditeurBoard.getNumToList(tempList, name);
				//int mapnum = Integer.parseInt(JOptionPane.showInputDialog("Numéro de la map: ", ""));
				int xnum = Integer.parseInt(JOptionPane.showInputDialog("Numéro x: ", ""));
				int ynum = Integer.parseInt(JOptionPane.showInputDialog("Numéro y: ", ""));
				map.get(SelectMap).Case[x][y].data[0] = mapnum;
				map.get(SelectMap).Case[x][y].data[1] = xnum;
				map.get(SelectMap).Case[x][y].data[2] = ynum;
				System.out.println(mapnum+":"+xnum+","+ynum);
			}
			break;
		case 103:
			if(!move) {
				map.get(SelectMap).Case[x][y].attribut = 3;
				String[] tempList = new String[pnj.size()];
				for(int i = 0; i < pnj.size(); i++) {
					tempList[i] = i + " " +pnj.get(i).nom;
				}
				String npcName = (String) JOptionPane.showInputDialog(null,"Choississez un npc",
						"	NPC Num",JOptionPane.QUESTION_MESSAGE, null, tempList, tempList[0]);
				
				int npcnum = getNumToList(tempList, npcName);//Integer.parseInt(JOptionPane.showInputDialog("NumÃ©ro du pnj: ", ""));
				map.get(SelectMap).Case[x][y].data[0] = npcnum;
			}
			break;
		case 104:
			if(!move) {
				map.get(SelectMap).Case[x][y].attribut = 4;
				String[] tempList2 = new String[shop.size()];
				for(int i = 0; i < shop.size(); i++) {
					tempList2[i] = i + " " +shop.get(i).name;
				}
				String shopName = (String) JOptionPane.showInputDialog(null,"Choississez un magasin",
						"	Magasin Num",JOptionPane.QUESTION_MESSAGE, null, tempList2, tempList2[0]);
				
				int shopnum = getNumToList(tempList2, shopName);//Integer.parseInt(JOptionPane.showInputDialog("NumÃ©ro du pnj: ", ""));
				map.get(SelectMap).Case[x][y].data[0] = shopnum;
			}
			break;
		case 1000:
			map.get(SelectMap).Case[x][y].light = !map.get(SelectMap).Case[x][y].light;
			if(isNight) createNight();
			break;
		}
	}
	public void remplirMap() {
		for(int y = 0; y < 20; y++) {
			for(int x = 0; x < 20; x++) {
				clickMap(x, y, true);
			}
		}
		
		repaint();
	}
	public void potPeinture(int texID, int x, int y) {
		int sizeX = iitexture.get(SelectTiles).getIconWidth()/32;
		switch(Selectcouche) {
		case 0:
			map.get(SelectMap).Case[x][y].sol[0] = SelectY * sizeX + SelectX;
			map.get(SelectMap).Case[x][y].solTile[0] = SelectTiles;
			if(x > 0) {
				if(map.get(SelectMap).Case[x-1][y].sol[0] == texID) {
					potPeinture(texID, x-1, y);
				}
			}
			if(y > 0) {
				if(map.get(SelectMap).Case[x][y-1].sol[0] == texID) {
					potPeinture(texID, x, y-1);
				}
			}
			if(x < map.get(SelectMap).width) {
				if(map.get(SelectMap).Case[x+1][y].sol[0] == texID) {
					potPeinture(texID, x+1, y);
				}
			}
			if(y < map.get(SelectMap).height) {
				if(map.get(SelectMap).Case[x][y+1].sol[0] == texID) {
					potPeinture(texID, x, y+1);
				}
			}
			break;
		case 1:
			map.get(SelectMap).Case[x][y].sol[1] = SelectY * sizeX + SelectX;
			map.get(SelectMap).Case[x][y].solTile[1] = SelectTiles;
			if(x > 0) {
				if(map.get(SelectMap).Case[x-1][y].sol[1] == texID) {
					potPeinture(texID, x-1, y);
				}
			}
			if(y > 0) {
				if(map.get(SelectMap).Case[x][y-1].sol[1] == texID) {
					potPeinture(texID, x, y-1);
				}
			}
			if(x < map.get(SelectMap).width) {
				if(map.get(SelectMap).Case[x+1][y].sol[1] == texID) {
					potPeinture(texID, x+1, y);
				}
			}
			if(y < map.get(SelectMap).height) {
				if(map.get(SelectMap).Case[x][y+1].sol[1] == texID) {
					potPeinture(texID, x, y+1);
				}
			}
			break;
		case 2:
			map.get(SelectMap).Case[x][y].masque[0] = SelectY * sizeX + SelectX;
			map.get(SelectMap).Case[x][y].masqueTile[0] = SelectTiles;
			if(x > 0) {
				if(map.get(SelectMap).Case[x-1][y].masque[0] == texID) {
					potPeinture(texID, x-1, y);
				}
			}
			if(y > 0) {
				if(map.get(SelectMap).Case[x][y-1].masque[0] == texID) {
					potPeinture(texID, x, y-1);
				}
			}
			if(x < map.get(SelectMap).width) {
				if(map.get(SelectMap).Case[x+1][y].masque[0] == texID) {
					potPeinture(texID, x+1, y);
				}
			}
			if(y < map.get(SelectMap).height) {
				if(map.get(SelectMap).Case[x][y+1].masque[0] == texID) {
					potPeinture(texID, x, y+1);
				}
			}
			break;
		case 3:
			map.get(SelectMap).Case[x][y].masque[2] = SelectY * sizeX + SelectX;
			map.get(SelectMap).Case[x][y].masqueTile[2] = SelectTiles;
			if(x > 0) {
				if(map.get(SelectMap).Case[x-1][y].masque[2] == texID) {
					potPeinture(texID, x-1, y);
				}
			}
			if(y > 0) {
				if(map.get(SelectMap).Case[x][y-1].masque[2] == texID) {
					potPeinture(texID, x, y-1);
				}
			}
			if(x < map.get(SelectMap).width) {
				if(map.get(SelectMap).Case[x+1][y].masque[2] == texID) {
					potPeinture(texID, x+1, y);
				}
			}
			if(y < map.get(SelectMap).height) {
				if(map.get(SelectMap).Case[x][y+1].masque[2] == texID) {
					potPeinture(texID, x, y+1);
				}
			}
			break;
		case 4:
			map.get(SelectMap).Case[x][y].masque[1] = SelectY * sizeX + SelectX;
			map.get(SelectMap).Case[x][y].masqueTile[1] = SelectTiles;
			if(x > 0) {
				if(map.get(SelectMap).Case[x-1][y].masque[1] == texID) {
					potPeinture(texID, x-1, y);
				}
			}
			if(y > 0) {
				if(map.get(SelectMap).Case[x][y-1].masque[1] == texID) {
					potPeinture(texID, x, y-1);
				}
			}
			if(x < map.get(SelectMap).width) {
				if(map.get(SelectMap).Case[x+1][y].masque[1] == texID) {
					potPeinture(texID, x+1, y);
				}
			}
			if(y < map.get(SelectMap).height) {
				if(map.get(SelectMap).Case[x][y+1].masque[1] == texID) {
					potPeinture(texID, x, y+1);
				}
			}
			break;
		case 5:
			map.get(SelectMap).Case[x][y].masque[3] = SelectY * sizeX + SelectX;
			map.get(SelectMap).Case[x][y].masqueTile[3] = SelectTiles;
			if(x > 0) {
				if(map.get(SelectMap).Case[x-1][y].masque[3] == texID) {
					potPeinture(texID, x-1, y);
				}
			}
			if(y > 0) {
				if(map.get(SelectMap).Case[x][y-1].masque[3] == texID) {
					potPeinture(texID, x, y-1);
				}
			}
			if(x < map.get(SelectMap).width) {
				if(map.get(SelectMap).Case[x+1][y].masque[3] == texID) {
					potPeinture(texID, x+1, y);
				}
			}
			if(y < map.get(SelectMap).height) {
				if(map.get(SelectMap).Case[x][y+1].masque[3] == texID) {
					potPeinture(texID, x, y+1);
				}
			}
			break;
		case 6:
			map.get(SelectMap).Case[x][y].frange[0] = SelectY * sizeX + SelectX;
			map.get(SelectMap).Case[x][y].frangeTile[0] = SelectTiles;
			if(x > 0) {
				if(map.get(SelectMap).Case[x-1][y].frange[0] == texID) {
					potPeinture(texID, x-1, y);
				}
			}
			if(y > 0) {
				if(map.get(SelectMap).Case[x][y-1].frange[0] == texID) {
					potPeinture(texID, x, y-1);
				}
			}
			if(x < map.get(SelectMap).width) {
				if(map.get(SelectMap).Case[x+1][y].frange[0] == texID) {
					potPeinture(texID, x+1, y);
				}
			}
			if(y < map.get(SelectMap).height) {
				if(map.get(SelectMap).Case[x][y+1].frange[0] == texID) {
					potPeinture(texID, x, y+1);
				}
			}
			break;
		case 7:
			map.get(SelectMap).Case[x][y].frange[2] = SelectY * sizeX + SelectX;
			map.get(SelectMap).Case[x][y].frangeTile[2] = SelectTiles;
			if(x > 0) {
				if(map.get(SelectMap).Case[x-1][y].frange[2] == texID) {
					potPeinture(texID, x-1, y);
				}
			}
			if(y > 0) {
				if(map.get(SelectMap).Case[x][y-1].frange[2] == texID) {
					potPeinture(texID, x, y-1);
				}
			}
			if(x < map.get(SelectMap).width) {
				if(map.get(SelectMap).Case[x+1][y].frange[2] == texID) {
					potPeinture(texID, x+1, y);
				}
			}
			if(y < map.get(SelectMap).height) {
				if(map.get(SelectMap).Case[x][y+1].frange[2] == texID) {
					potPeinture(texID, x, y+1);
				}
			}
			break;
		case 8:
			map.get(SelectMap).Case[x][y].frange[1] = SelectY * sizeX + SelectX;
			map.get(SelectMap).Case[x][y].frangeTile[1] = SelectTiles;
			if(x > 0) {
				if(map.get(SelectMap).Case[x-1][y].frange[1] == texID) {
					potPeinture(texID, x-1, y);
				}
			}
			if(y > 0) {
				if(map.get(SelectMap).Case[x][y-1].frange[1] == texID) {
					potPeinture(texID, x, y-1);
				}
			}
			if(x < map.get(SelectMap).width) {
				if(map.get(SelectMap).Case[x+1][y].frange[1] == texID) {
					potPeinture(texID, x+1, y);
				}
			}
			if(y < map.get(SelectMap).height) {
				if(map.get(SelectMap).Case[x][y+1].frange[1] == texID) {
					potPeinture(texID, x, y+1);
				}
			}
			break;
		case 9:
			map.get(SelectMap).Case[x][y].frange[3] = SelectY * sizeX + SelectX;
			map.get(SelectMap).Case[x][y].frangeTile[3] = SelectTiles;
			if(x > 0) {
				if(map.get(SelectMap).Case[x-1][y].frange[3] == texID) {
					potPeinture(texID, x-1, y);
				}
			}
			if(y > 0) {
				if(map.get(SelectMap).Case[x][y-1].frange[3] == texID) {
					potPeinture(texID, x, y-1);
				}
			}
			if(x < map.get(SelectMap).width) {
				if(map.get(SelectMap).Case[x+1][y].frange[3] == texID) {
					potPeinture(texID, x+1, y);
				}
			}
			if(y < map.get(SelectMap).height) {
				if(map.get(SelectMap).Case[x][y+1].frange[3] == texID) {
					potPeinture(texID, x, y+1);
				}
			}
			break;
		}
	}
	public static int getNumToList(String[] t, String s) {
		for(int i = 0; i < t.length; i++) {
			if(s.equals(t[i])) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyCode() == KeyEvent.VK_SHIFT) {
			keyShift = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyCode() == KeyEvent.VK_SHIFT) {
			keyShift = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void createGrille() {
		grille = new BufferedImage(map.get(SelectMap).width*32, map.get(SelectMap).height * 32,BufferedImage.TYPE_INT_ARGB);
		Graphics2D gr = grille.createGraphics();
		for(int y = 0; y < map.get(SelectMap).height; y++) {
			for(int x = 0; x < map.get(SelectMap).width; x++) {
				gr.setColor(Color.white);
				gr.drawLine(x*32, y*32, x*32+32, y*32);
				gr.drawLine(x*32, y*32, x*32, y*32+32);
				gr.setColor(Color.black);
				gr.drawLine(x*32, y*32+32, x*32+32, y*32+32);
				gr.drawLine(x*32+32, y*32, x*32+32, y*32+32);
			}
		}
		gr.dispose();
	}
	
	public void createNight(){
		// nettoie la nuit '--
		nightClear();
		
		// Seach light map
		DataCase tempCase;
		for(int y = 0; y < map.get(SelectMap).height; y++) {
			for(int x = 0; x < map.get(SelectMap).width; x++) {
				tempCase = map.get(SelectMap).Case[x][y];
				if(tempCase.light) {
					addLight(x, y);
				}
			}
		}
	}
	
	public void addLight(int x, int y) {
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
	
	public void nightClear(){
		night = new BufferedImage(map.get(SelectMap).width*32, map.get(SelectMap).height * 32,BufferedImage.TYPE_INT_ARGB);
		Graphics2D gr = night.createGraphics();
			AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.85f);				
			gr.setComposite(alphaComposite);
			gr.setColor(Color.black);
			gr.fillRect(0, 0, map.get(SelectMap).width*32, map.get(SelectMap).height * 32);
		gr.dispose();
	}
}
