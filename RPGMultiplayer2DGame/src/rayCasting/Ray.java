package rayCasting;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import main.GamePanel;

public class Ray {
	
	Point pos;
	Vector dir;
	GamePanel gp;

    public Ray(Point pos, float angle, GamePanel gp) {
    	this.gp = gp;
        this.pos = pos;
        double x = Math.cos(angle);
        double y = Math.sin(angle);
        this.dir = new Vector(x, y);
    }

    public void lookAt(int x, int y) {
        this.dir.dX = x - this.pos.x;
        this.dir.dX = y - this.pos.y;
        this.dir.normalize();
    }

    public void show(Graphics2D g2) {
    	g2.setColor(Color.white);
        g2.drawLine(gp.maxScreenCol*gp.tileSize/2, gp.maxScreenRow*gp.tileSize/2, (int) (this.dir.dX * 10000), (int) (this.dir.dY * 10000));
 
    }

    public Point cast(Boundary wall) {
    	
        int worldXa = wall.a.x * gp.tileSize;
		int worldYa = wall.a.y * gp.tileSize;
		int screenXa = worldXa - gp.player.worldX + gp.player.screenX;
		int screenYa = worldYa - gp.player.worldY + gp.player.screenY;
        int worldXb = wall.b.x * gp.tileSize;
		int worldYb= wall.b.y * gp.tileSize;
		int screenXb = worldXb - gp.player.worldX + gp.player.screenX;
		int screenYb = worldYb - gp.player.worldY + gp.player.screenY;
		
    	final double x1 = screenXa;
    	final double y1 = screenYa;
    	final double x2 = screenXb;
    	final double y2 = screenYb;

    	final double x3 = this.pos.x;
    	final double y3 = this.pos.y;
    	final double x4 = this.pos.x + this.dir.dX;
    	final double y4 = this.pos.y + this.dir.dY;

    	final double den = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if (den == 0) {
            return null;
        }

        final double t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / den;
        final double u = -((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) / den;

        if (t > 0 && t < 1 && u > 0) {
        	final Point pt = new Point(0, 0);
            pt.x = (int) (x1 + t * (x2 - x1));
            pt.y = (int) (y1 + t * (y2 - y1));
            return pt;
        } else {
            return null;
        }
    }
}
