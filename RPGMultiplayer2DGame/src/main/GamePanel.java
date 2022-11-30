package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import rayCasting.Boundary;
import rayCasting.Particle;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
	
	// SCREEN SETTINGS
	final int originalTileSize = 16;
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 20;
	public final int maxScreenRow = 14;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
	
	// WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = maxWorldCol * tileSize;
	public final int worldHeight = maxWorldRow * tileSize;
	
	// RAY CASTING
	public Boundary[] walls = new Boundary[2 * maxWorldCol * maxWorldRow + maxWorldCol + maxWorldRow];
	public Particle particle = new Particle(this);
	
	// SYSTEM
	int FPS = 60;
	Thread gameThread;
	public KeyHandler keyH = new KeyHandler(this);
	public TileManager tileM = new TileManager(this);
	public CollisionChecker cChecker = new CollisionChecker(this);
	public UI ui = new UI(this);
	
	// PLAYER
	public Player player = new Player(this);
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
		setup();
	}
	
	public void setup() {
		int index = 0;
		for (int i = 0; i < maxWorldCol; i++) {
			for (int j = 0; j < maxWorldRow; j++) {
				Boundary wall1 = new Boundary(i, j, i+1, j, "horizontal", false);
				Boundary wall2 = new Boundary(i, j, i, j+1, "vertical", false);
				walls[index] = wall1;
				walls[index+1] = wall2; 
				index += 2;
				if (i == maxWorldCol) {
					Boundary wall = new Boundary(maxWorldCol+1, j, maxWorldCol+1, j+1, "vertical", false);
					walls[index] = wall;
				}
			}
			
			Boundary wall = new Boundary(i, maxWorldRow, i+1, maxWorldRow, "horizontal", false);
			walls[index] = wall;
			index++;
		}
		walls[index] = new Boundary(0, 0, maxWorldCol, 0, "horizontal", true);
		walls[index+1] = new Boundary(0, 0, 0, maxWorldRow, "vertical", true);
		walls[index+2] = new Boundary(maxWorldCol, 0, maxWorldCol, maxWorldRow, "vertical", true);
		walls[index+3] = new Boundary(0, maxWorldRow, maxWorldCol, maxWorldRow, "horizontal", true);
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0; 
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		while (gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if (delta >= 1) {
				update();
				repaint();
				delta--;
			}
			
			if (timer >= 1000000000) {
				timer = 0;
			}
			
		}
		
	}
	
	public void update() {
		
		player.update();
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(Color.white);
		
		tileM.draw(g2);
		
		for (Boundary wall : walls) {
			if (wall != null) {
				wall.show(g2, this);
			}
 		}
		
		player.draw(g2);
		
		ui.draw(g2);
		
		particle.look(g2, walls);
		
	}

}
