package fr.mimus.jorpg.commun;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class TextureSpliter {
	public static BufferedImage[] getAllTexture(ImageIcon img) {
		
		BufferedImage image = new BufferedImage(img.getIconWidth(), img.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D conv = image.createGraphics();
		conv.drawImage(img.getImage(), 0, 0, null);
		conv.dispose();
		
		int rows = img.getIconHeight()/32;
		int cols = img.getIconWidth()/32;
		int chunks = rows * cols;

		int chunkWidth = image.getWidth() / cols;
		int chunkHeight = image.getHeight() / rows;

		int count = 0;
		BufferedImage imgs[] = new BufferedImage[chunks];

		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());
		
				Graphics2D gr = imgs[count++].createGraphics();
				gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
				gr.dispose();
			}
		}
		return imgs;
	}
	public static BufferedImage[] getAllTextureFrame(ImageIcon img) {
		
		BufferedImage image = new BufferedImage(img.getIconWidth(), img.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D conv = image.createGraphics();
		conv.drawImage(img.getImage(), 0, 0, null);
		conv.dispose();
		
		int rows = 1;
		int cols = img.getIconWidth()/32;
		int chunks = rows * cols;

		int chunkWidth = image.getWidth() / cols;
		int chunkHeight = image.getHeight() / rows;

		int count = 0;
		BufferedImage imgs[] = new BufferedImage[chunks];

		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());
		
				Graphics2D gr = imgs[count++].createGraphics();
				gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
				gr.dispose();
			}
		}
		return imgs;
	}
	
public static BufferedImage[] getAllTextureWith(ImageIcon img, int rows, int cols) {
		
		BufferedImage image = new BufferedImage(img.getIconWidth(), img.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D conv = image.createGraphics();
		conv.drawImage(img.getImage(), 0, 0, null);
		conv.dispose();
	
		int chunks = rows * cols;

		int chunkWidth = image.getWidth() / cols;
		int chunkHeight = image.getHeight() / rows;

		int count = 0;
		BufferedImage imgs[] = new BufferedImage[chunks];

		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());
		
				Graphics2D gr = imgs[count++].createGraphics();
				gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
				gr.dispose();
			}
		}
		return imgs;
	}
}
