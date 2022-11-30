package rayCasting;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import main.GamePanel;

public class Boundary {
	
	public Point a, b;
	public String orientation;
	boolean isTouched = false;
	boolean isDrawn = false;
	boolean bordure = false;

    public Boundary(int x1, int y1, int x2, int y2, String orientation, boolean bordure) {
        this.a = new Point(x1, y1);
        this.b = new Point(x2, y2);
        this.orientation = orientation;
        this.bordure = bordure;
    }

    public void show(Graphics2D g2, GamePanel gp) {
        g2.setColor(Color.white);
        
        int worldXa = a.x * gp.tileSize;
		int worldYa= a.y * gp.tileSize;
		int screenXa = worldXa - gp.player.worldX + gp.player.screenX;
		int screenYa = worldYa - gp.player.worldY + gp.player.screenY;
        int worldXb = b.x * gp.tileSize;
		int worldYb= b.y * gp.tileSize;
		int screenXb = worldXb - gp.player.worldX + gp.player.screenX;
		int screenYb = worldYb - gp.player.worldY + gp.player.screenY;
		
		if (bordure) {
			isDrawn = true;
			//g2.drawLine(worldXa, worldYa, worldXb, worldYb);
		} 
		else if (worldXa + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			worldXa - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			worldYa + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			worldYa - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			
			isDrawn = true;
			//g2.drawLine(screenXa, screenYa, screenXb, screenYb);
		} 
		else {
			isDrawn = false;
		}
		
    }
}
