package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class UI {
	
	Graphics2D g2;
	GamePanel gp;
	Font maruMonica;
	BufferedImage heart_full, heart_half, heart_blank;
	public boolean messageOn = false;
	
	
	public UI(GamePanel gp) {
		
		this.gp = gp;
	}
	
	public void addMessage(String text) {
		/*
		message.add(text);
		messageCounter.add(0);*/
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(maruMonica);
		g2.setColor(Color.white);
		
		drawPlayScreen();
	}
	
	public void drawPlayScreen() {
		
		final int frameX = gp.tileSize*1;
		final int frameY = gp.tileSize*1;
		final int frameWidth = gp.tileSize * 2;
		final int frameHeight = gp.tileSize * 2;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		BufferedImage image1 = null;
		try {
			image1 = ImageIO.read(getClass().getResourceAsStream("/items/sword01.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		g2.drawImage(image1, frameX + 23, frameY + 23, gp.tileSize, gp.tileSize, null);
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
		
		Color c  = new Color(0,0,0,210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
	}
}
