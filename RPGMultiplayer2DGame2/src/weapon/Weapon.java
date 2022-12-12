package weapon;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Weapon {
	public GamePanel gp;
	public String owner;
	public int damage;
	public int speed;
	public float speedX;
	public float speedY;
	public float rotate;
	public int range;
	public int worldX, worldY;
	public BufferedImage image;
	public Weapon() {
	}

	public BufferedImage setup(String imagePath) {
		
		BufferedImage image = null;
		try {
			BufferedImage tempImage = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
			image=rotate(tempImage,(double) rotate);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	public static BufferedImage rotate(BufferedImage bimg, Double angle) {
	    double sin = Math.abs(Math.sin(Math.toRadians(angle))),
	           cos = Math.abs(Math.cos(Math.toRadians(angle)));
	    int w = bimg.getWidth();
	    int h = bimg.getHeight();
	    int neww = (int) Math.floor(w*cos + h*sin),
	        newh = (int) Math.floor(h*cos + w*sin);
	    BufferedImage rotated = new BufferedImage(neww, newh, bimg.getType());
	    Graphics2D graphic = rotated.createGraphics();
	    graphic.translate((neww-w)/2, (newh-h)/2);
	    graphic.rotate(Math.toRadians(angle), w/2, h/2);
	    graphic.drawRenderedImage(bimg, null);
	    graphic.dispose();
	    return rotated;
	}
	public boolean update() {
		range-=speed;
		if(range<=0) {
			gp.player.fire=0;
			return false;
		}
		worldX+=speedX;
		worldY+=speedY;
		if(worldX<=0 || worldX>=gp.worldWidth) {
			gp.player.fire=0;
			return false;
		}
		else if(worldY<=0 || worldY>=gp.worldHeight) {
			gp.player.fire=0;
			return false;
		}
		if(gp.WeaponCheck.checkTile(this)==true) {
			gp.player.fire=0;
			return false;
		}
	
		return true;
	}
	public void draw(Graphics2D g2) {
		int a =0;
		int screenX=0;
		int screenY=0;
		if(gp.player.worldX-gp.player.screenX<worldX && gp.player.worldX+(gp.screenWidth-gp.player.screenX)>worldX) {
			a++;
			if(gp.player.screenX<gp.screenWidth/2-(gp.tileSize/2)) {
				screenX=worldX;
			}
			else {
				screenX=(worldX-gp.player.worldX-gp.screenWidth/2)+gp.screenWidth;
			}
		}
		if(gp.player.worldY-gp.screenHeight/2<worldY && gp.player.worldY+gp.screenHeight/2>worldX) {
			a++;
			if(gp.player.screenY<gp.screenHeight/2-(gp.tileSize/2)) {
				screenY=worldY;
			}
			else {
				screenY=(worldY-gp.player.worldY-gp.screenHeight/2)+gp.screenHeight;
			}
		}

		if(a==2) {
		g2.drawImage(image, screenX, screenY,gp.tileSize, gp.tileSize, null);
	}}

}
