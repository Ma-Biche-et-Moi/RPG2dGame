package rayShadowing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Random;

import main.GamePanel;

public class Edge {
	
	Point a;
	public Point b;
	GamePanel gp;
	Color color;
	
	public Edge(Point start, Point end, GamePanel gp) {
		
		this.gp = gp;
		this.a = start;
		this.b = end;
		// Random random = new Random();
		color = new Color(0, 0, 255);
	}
	
	public void draw(Graphics2D g2) {
		
		g2.setStroke(new BasicStroke(8));
        g2.setColor(color);
        
		int screenXa = a.x - gp.player.worldX + gp.player.screenX;
		int screenYa = a.y - gp.player.worldY + gp.player.screenY;
		int screenXb = b.x - gp.player.worldX + gp.player.screenX;
		int screenYb = b.y - gp.player.worldY + gp.player.screenY;
		
		g2.drawLine(screenXa, screenYa, screenXb, screenYb);
	}
}
