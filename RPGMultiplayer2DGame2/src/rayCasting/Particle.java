package rayCasting;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import main.GamePanel;

public class Particle {

    Point pos;
    public Ray[] rays = new Ray[360];
    GamePanel gp;
    public float[] rayDistances;

	public Particle(GamePanel gp) {
		
		this.gp = gp;
        this.pos = new Point(gp.maxScreenCol*gp.tileSize/2, gp.maxScreenRow*gp.tileSize/2);
        int index = 0;
        for (int a = 0; a < 360; a += 1) {
            this.rays[index] = new Ray(this.pos, (float) Math.toRadians(a), gp);
            index++;
        }
        rayDistances = new float[rays.length];
    }

    public void look(Graphics2D g2, Boundary[] walls) {
    	
   /* 	int index = 0;
    	
        for (Ray ray : this.rays) {
        	
            Point closest = null;
            Boundary closestWall = null;
            float record = 1000000;
            
         /*   for (Boundary wall : gp.walls) {
            	if (wall != null) {
            		if (wall.isDrawn) {
                    	int tile1 = 0;
                    	int tile2 = 0;
                    	if (wall.a.x < 50 && wall.a.y < 50) {
                			tile1 = gp.tileM.mapTileNum[wall.a.x][wall.a.y];
                		} else {
                			tile1 = 2;
                		}
                    	if (wall.orientation == "vertical") {
                    		if (wall.a.x > 0) {
                    			tile2 = gp.tileM.mapTileNum[wall.a.x-1][wall.a.y];
                    		} else {
                        		tile2 = 2;
                    		}
                    	} else if (wall.orientation == "horizontal") {
                    		if (wall.a.y > 0) {
                    			tile2 = gp.tileM.mapTileNum[wall.a.x][wall.a.y-1];
                    		} else {
                    			tile2 = 2;
                    		}
                    	}
                    	if (gp.tileM.tileA[tile1].opaque || gp.tileM.tileA[tile2].opaque) {
                            final Point pt = ray.cast(wall);
                            if (pt != null) {
                            	final float d = (float) Math.sqrt((pt.x-pos.x)*(pt.x-pos.x) + (pt.y-pos.y)*(pt.y-pos.y));
                                if (d < record) {
                                    record = d;
                                    closest = pt;
                                    closestWall = wall;
                                }
                            }
                    	}
            		}
            	}
            }
        	rayDistances[index] = record;
            index++;
            if (closest != null) {
                closestWall.isTouched = true;
                //g2.setColor(Color.white);
                //g2.drawLine(this.pos.x, this.pos.y, closest.x, closest.y);
            }
        }*/
    }

    public void show(Graphics2D g2, Boundary[] walls) {
        g2.setColor(Color.white);
        for (Ray ray : this.rays) {
            ray.show(g2);
        }
    }
}
